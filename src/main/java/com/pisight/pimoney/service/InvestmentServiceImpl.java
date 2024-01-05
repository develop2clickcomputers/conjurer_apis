package com.pisight.pimoney.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.InvHeroGraphDTO;
import com.pisight.pimoney.dto.InvPerformanceDTO;
import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.repository.HoldingAssetRepository;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class InvestmentServiceImpl {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl= null;
	
	@Autowired
	private K2DAO k2DAO= null;

	@Autowired
	private HoldingAssetRepository holdingRepo = null;

	public List<HoldingAssetEntity> findAssetByUserId(UUID userId){
		return holdingRepo.findByUserId(userId);
	}

	/**
	 * This method is used to show investment page hero graph
	 * @param assets List of Assets
	 * @param view InstitutionName/Asset Distribution
	 * @param userId UserId
	 * @return JSONObject {@link JSONObject}
	 * @throws ParseException {@link ParseException}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getHeroGraphChartData(List<HoldingAssetEntity> assets, String view, String userId) throws ParseException {

		JSONObject response = new JSONObject();

		HashMap<String, InvHeroGraphDTO> assetMap = new HashMap<>();

		User user = k2ServiceImpl.fetchUserById(UUID.fromString(userId));

		Double total = 0d;
		ScriptLogger.writeInfo("total assets => " + assets.size());

		for(HoldingAssetEntity asset: assets) {

			String category = asset.getHoldingAssetCategory();
			String institution = asset.getAccount().getInstitutionName();
			if(StringUtils.isEmpty(category)) {
				category = "uncategorized";
			}

			String value = asset.getHoldingAssetCurrentValue();
			String currency = asset.getHoldingAssetCurrency();
			if(StringUtils.isEmpty(value) || "0.00".equals(value) || "0.0".equals(value) || "0".equals(value)) {
				String quantity = asset.getHoldingAssetQuantity();
				String unitPrice = asset.getHoldingAssetIndicativePrice();
				if(asset.isBondNature()) {
					ScriptLogger.writeInfo("Bond Nature = true");
					double price = Double.parseDouble(unitPrice);
					if(price > 10) {
						price = price/100;
						unitPrice = String.valueOf(price);
					}
				}
				if(HoldingAsset.CATEGORY_CASH.equals(category)) {
					value = quantity;
				}
				else if(StringUtils.isNotEmpty(quantity) && StringUtils.isNotEmpty(unitPrice)) {
					value = (Double.parseDouble(quantity) * Double.parseDouble(unitPrice)) + "";
				}
			}

			Double newValue = k2ServiceImpl.currencyConvert(currency, user.getPreferredCurrency(), value, new Date());
			ScriptLogger.writeInfo("Qty = " + asset.getHoldingAssetQuantity() + " and value = " + value);
			if(category.equals(HoldingAsset.CATEGORY_LOAN)) {
				total -= newValue;
			}else {
				total += newValue;
			}
			value = newValue + "";
			asset.setHoldingAssetCurrentValue(value);

			if("institutionName".equals(view)) {
				if(assetMap.containsKey(institution)) {
					InvHeroGraphDTO temp = assetMap.get(institution);
					Double newTotal =  Double.valueOf(temp.getValue()) + newValue; 
					temp.setValue(newTotal);

				}
				else {
					InvHeroGraphDTO temp = new InvHeroGraphDTO();
					temp.setLabel(institution);
					temp.setValue(newValue);
					assetMap.put(institution, temp);
				}
			}
			else {
				if(assetMap.containsKey(category)) {
					InvHeroGraphDTO temp = assetMap.get(category);
					Double newTotal =  Double.valueOf(temp.getValue()) + newValue; 
					temp.setValue(newTotal);

				}
				else {
					InvHeroGraphDTO temp = new InvHeroGraphDTO();
					temp.setLabel(category);
					temp.setValue(newValue);
					assetMap.put(category, temp);
				}
			}
		}

		int size = assetMap.size();
		InvHeroGraphDTO[] result = new InvHeroGraphDTO[size];

		Set<String> keySet = assetMap.keySet();

		int i  = 0;
		for(String key: keySet) {
			result[i] = assetMap.get(key);
			i++;
		}

		response.put("totalAmount", total);
		response.put("currency", user.getPreferredCurrency());
		response.put("chartData", result);

		return response;
	}

	/**
	 * This method is used to show performance of individual assets in invement page(Top Section)
	 * @param userId UserId
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getPerformanceData(String userId) {

		JSONObject response = new JSONObject();

		List<InvPerformanceDTO> result = new ArrayList<InvPerformanceDTO>();

		List<HoldingAssetEntity> assets = findAssetByUserId(UUID.fromString(userId));

		for(HoldingAssetEntity asset: assets) {
			
			if(HoldingAsset.CATEGORY_CASH.equals(asset.getHoldingAssetCategory())) {
				continue;
			}

			String unitCost = asset.getHoldingAssetAverageUnitCost();
			String unitPrice = asset.getHoldingAssetIndicativePrice();
			String value = asset.getHoldingAssetCurrentValue();
			String cost = asset.getHoldingAssetCost();
			String quantity = asset.getHoldingAssetQuantity();
			Double computeCost = null;
			Double computeValue = null;
			boolean usingUnitCost  = false;
			boolean usingUnitPrice = false;
			ScriptLogger.writeInfo("asset => " + asset.getId());
			ScriptLogger.writeInfo("inst => " + asset.getAccount().getManualInstitution().getName());
			ScriptLogger.writeInfo("cost => " + unitCost);
			ScriptLogger.writeInfo("value => " + unitPrice);
			if(StringUtils.isNotEmpty(unitCost)) {
				unitCost = unitCost.replaceAll(",", "");
				computeCost = Double.parseDouble(unitCost);
				usingUnitCost = true;
			}
//			else {
//				quantity = quantity.replaceAll(",", "");
//				Double q = Double.parseDouble(quantity);
//				cost = cost.replaceAll(",", "");
//				Double c = Double.parseDouble(cost);
//				computeCost = c;
//				if(StringUtils.isNotEmpty(quantity)) {
//					if(q != 0) {
//						computeCost = c/q;
//						usingUnitCost = true;
//					}
//				}
//			}
			
			if(StringUtils.isNotEmpty(unitPrice)) {
				unitPrice = unitPrice.replaceAll(",", "");
				computeValue = Double.parseDouble(unitPrice);
				usingUnitPrice = true;
			}
//			else {
//				quantity = quantity.replaceAll(",", "");
//				Double q = Double.parseDouble(quantity);
//				value = value.replaceAll(",", "");
//				Double v = Double.parseDouble(value);
//				computeValue = v;
//				if(StringUtils.isNotEmpty(quantity)) {
//					if(q != 0) {
//						computeValue = v/q;
//						usingUnitPrice = true;
//					}
//				}
//			}
			
			Double profit = 0d;
			ScriptLogger.writeInfo("ccost => " + computeCost);
			ScriptLogger.writeInfo("cvalue => " + computeValue);
			
			if((usingUnitCost && usingUnitPrice)) {
				 profit = ((computeValue/computeCost)-1)*100;
			}
			else {
				continue;
			}
				
			result.add(new InvPerformanceDTO(asset.getHoldingAssetDescription(), profit));
			
		}
		
		response.put("result", result);

		return response;
	}
	
	public int deleteAccountAsset(UUID accountId) {
		
		return k2DAO.deleteHoldingAssets(accountId);
	}
	
	public int hardDeleteAccountAsset(UUID accountId, String stmtId) {
		
		return k2DAO.deleteHoldingAssets(accountId, stmtId);
	}

	public Integer hardDeleteSecurityMaster(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return k2DAO.deleteSecurityMaster(accountId, stmtId);
	}

	@SuppressWarnings("unchecked")
	public JSONObject fetchTransactionCode(DocumentRequest request) {
		
		int errorCode = 0;
		String responseStatus = Constants.SUCCESS;
		String responseMessage = "txn code fetched successfully";

		JSONObject response = new JSONObject();
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("txnCodeList", Constants.getTransCodeList());
		return response;
	}

	/**
	 * This method is used to get all investment details(holdings) in investment page
	 * @param doc {@link DocumentRequest}
	 * @param authorization AuthorizationKey
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getInvestments(DocumentRequest doc, String authorization) {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";

		JSONObject response = new JSONObject();

		List<JSONObject> investments = null;
		try {

			String userId = doc.getUserId();
			
			// Check for UserId and Set Proper UserId 
			boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
			if(!userResult) {
				responseMessage = "UnAuthorized";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			userId  = doc.getUserId();
			
			investments = k2ServiceImpl.getInvestmentDetails(userId);
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "investment details fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in getInvestments", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("investments", investments);
		return response;
	}

	/**
	 * This method is used to get investment transactions
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject} 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getInvestmentTransactions(DocumentRequest request) {
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";

		JSONObject response = new JSONObject();
		List<InvestmentTransactionEntity> transactions = null;
		try {

			String userId = request.getUserId();
			transactions = k2ServiceImpl.getInvestmentTransactions(userId);
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "investment details fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in getInvestments", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("transactions", transactions);
		return response;
	}

	/**
	 * This method is used to delete investment transaction
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject deleteInvestmentTransaction(DocumentRequest request) {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";

		JSONObject response = new JSONObject();
		try {

			String flow = request.getFlow();
			String txnId = request.getTransactionId();
			boolean isPimoneyFlow = true;

			if(!Constants.FLOW_PIMONEY.equals(flow)) {
				isPimoneyFlow = false;
			}

			if(isPimoneyFlow) {
				ScriptLogger.writeInfo("Statement Repo flow");
				TransactionBaseEntity txn = k2ServiceImpl.fetchTransaction(txnId, Constants.TAG_INVESTMENT);
				k2ServiceImpl.deleteTransaction(txn);
			}else {
				ScriptLogger.writeInfo("File repo flow");
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "txn deleted successfully";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in deleteInvestmentTransaction", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
}
