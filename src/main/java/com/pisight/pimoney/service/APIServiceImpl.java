package com.pisight.pimoney.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.AssetEditFields;
import com.pisight.pimoney.dto.CountryRequest;
import com.pisight.pimoney.dto.CountryResponse;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.InstitutionRequest;
import com.pisight.pimoney.dto.ManualInstitutionResponse;
import com.pisight.pimoney.dto.ParserResponse;
import com.pisight.pimoney.dto.PreviewAccount;
import com.pisight.pimoney.dto.PreviewActionRequest;
import com.pisight.pimoney.dto.PreviewId;
import com.pisight.pimoney.dto.PreviewResponse;
import com.pisight.pimoney.dto.SecurityMasterEditFields;
import com.pisight.pimoney.dto.TransactionEditFields;
import com.pisight.pimoney.dto.UpdateInvestmentRequest;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.Country;
import com.pisight.pimoney.repository.entities.FileRepositoryEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.repository.entities.RepositoryBase;
import com.pisight.pimoney.repository.entities.SecurityMaster;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.EncodeDecodeUtil;
import com.pisight.pimoney.util.EntityFinder;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class APIServiceImpl {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl;
	
	@Autowired
	private K2DAO k2DAO;

	/**
	 * This method is used to get country list for pdf statements
	 * @param request {@link CountryRequest}
	 * @return {@link CountryResponse}
	 */
	@SuppressWarnings("unchecked")
	public CountryResponse getCountryList(CountryRequest request) {
		
		List<Country> countries = k2ServiceImpl.fetchAllCountries();

		List<JSONObject> countryList  = new ArrayList<JSONObject>();

		CountryResponse response = new CountryResponse();

		for(Country country: countries) {
			JSONObject obj = new JSONObject();
			obj.put("name", country.getName());
			obj.put("code", country.getCode());
			countryList.add(obj);
		}
		response.setCountries(countryList);
		return response;
	}

	/**
	 * This method is used to get all institution for given country code
	 * @param request {@link InstitutionRequest}
	 * @return {@link ManualInstitutionResponse}
	 */
	@SuppressWarnings("unchecked")
	public ManualInstitutionResponse getManualInstitutions(InstitutionRequest request) {

		List<ManualInstitution> manInsts = k2ServiceImpl.fetchManualInstitutionsByCountryCode(request.getCode());

		List<JSONObject> manInstList  = new ArrayList<JSONObject>();

		ManualInstitutionResponse response = new ManualInstitutionResponse();

		for(ManualInstitution inst: manInsts) {
			JSONObject obj = new JSONObject();
			obj.put("name", inst.getName());
			obj.put("code", inst.getCode());
			manInstList.add(obj);
		}
		response.setInstitutions(manInstList);
		return response;
	}

	/**
	 * This method is used to check pdf statement is password protected or not.
	 * @param doc {@link DocumentRequest}
	 * @return {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject checkEncryption(DocumentRequest doc) {

		int errorCode = 0;
		String responseStatus = "";
		String responseMessage = "";
		boolean res = false;

		try {

			String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "checkencryption";
			ScriptLogger.writeInfo("URL _>> " + url);

			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
			client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
			client.setDataField("docByte", doc.getDocByte());

			String result = client.getResponseForPostRequest();

			res = Boolean.parseBoolean(result.trim());

			responseStatus = Constants.SUCCESS;
			responseMessage = "process success";

		}catch(Exception e) {
			ScriptLogger.writeError("Error in checkEncryption ", e);
			errorCode = 99;
			responseStatus = Constants.FAILED;
			responseMessage = "Error while processing.";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("isPasswordProtected", res);
		return response;
	}

	/**
	 * This method is used to parse the pdf statment.
	 * <br> Method used pdfparser engine to parser statement.
	 * @param doc {@link DocumentRequest}
	 * @param authorization Authorization key
	 * @return {@link JSONObject}
	 * @throws Exception Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject statementParsing(DocumentRequest doc, String authorization) throws Exception {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		JSONObject response = new JSONObject();
		RepositoryBase docRepo = null;
		StatementParsingDetail stmtDetails = null;
		char[] pdfPassword = null;
		
		try {
		
			String userId = doc.getUserId();
			String instCode = doc.getInstitutionCode();
			pdfPassword = doc.getPswd();

			if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(instCode)) {
				responseMessage = "UserId or InstCode is not valid";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			
			// Check for UserId and Set Proper UserId 
			boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
			if(!userResult) {
				responseMessage = "UnAuthorized";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			userId  = doc.getUserId();
			ScriptLogger.writeInfo("USSSSSSERRRRRR = " + doc.getUserId());

			docRepo = k2ServiceImpl.createRepositoryEntity(doc);
			
			// For Store Statement Parsing Details
			stmtDetails = k2ServiceImpl.createStatementDetailEntity(doc);

			doc.setResponseFormat(DocumentRequest.JSON);
			String data = WebUtil.convertToJSON(doc);

			String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "parse";

			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
			client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
			client.setStringEntity(data);

			String result = client.getResponseForPostRequest();
			ObjectMapper mapper = new ObjectMapper();

			//ScriptLogger.writeInfo(result);

			ParserResponse parserResponse = mapper.readValue(result, ParserResponse.class);

			if(parserResponse.getErrorCode() == 0) {
				if(docRepo instanceof StatementRepositoryEntity) {
					ScriptLogger.writeInfo("Statement Repository Flow.......");
					response = k2ServiceImpl.storeParserResoponse(parserResponse, (StatementRepositoryEntity) docRepo, userId, instCode);
				}else {
					ScriptLogger.writeInfo("File Repository Flow.......");
					response = k2ServiceImpl.storeParserResoponse(parserResponse, (FileRepositoryEntity) docRepo, userId, instCode);
				}
			}else {
				if(docRepo instanceof StatementRepositoryEntity) {
					k2ServiceImpl.deleteStatementRepository((StatementRepositoryEntity) docRepo);
				}else {
					k2ServiceImpl.deleteFileRepository((FileRepositoryEntity) docRepo);
				}
				
				errorCode = parserResponse.getErrorCode();
				responseStatus = parserResponse.getStatus();
				responseMessage = parserResponse.getMessage();
			}
			
			// For Store EndTime and Status in StatementParsingDetails
			if(response.containsKey(Constants.ERROR_CODE_STRING)) {
				if((int)response.get(Constants.ERROR_CODE_STRING) == 0) {
					stmtDetails.setStatus(Constants.SUCCESS);
				}else {
					stmtDetails.setFilePassword(EncodeDecodeUtil.encrypt(String.valueOf(pdfPassword)));
					stmtDetails.setFileByte(Base64.decodeBase64(doc.getDocByte()));
					stmtDetails.setStatus(Constants.FAILED);
				}
			}else {
				stmtDetails.setFilePassword(EncodeDecodeUtil.encrypt(String.valueOf(pdfPassword)));
				stmtDetails.setFileByte(Base64.decodeBase64(doc.getDocByte()));
				stmtDetails.setStatus(Constants.FAILED);
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			}
			Calendar cal = Calendar.getInstance();
			stmtDetails.setEndTime(cal.getTime());
			stmtDetails =  k2DAO.saveStatementParsingDetail(stmtDetails);
			
			if(docRepo != null) {
				response.put("repoId", docRepo.getId());
			}
			return response;
			
		}catch(Exception e) {
			ScriptLogger.writeError("Error in statement parsing", e);
			responseMessage = "Error while processing.";
			
			Calendar cal = Calendar.getInstance();
			stmtDetails.setFilePassword(EncodeDecodeUtil.encrypt(String.valueOf(pdfPassword)));
			stmtDetails.setFileByte(Base64.decodeBase64(doc.getDocByte()));
			stmtDetails.setStatus(Constants.FAILED);
			stmtDetails.setEndTime(cal.getTime());
			k2DAO.saveStatementParsingDetail(stmtDetails);
			
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
	}

	/**
	 * This method is used showing details of uploaded statement in statement preview page
	 * @param doc {@link DocumentRequest}
	 * @param authorization AuthorizationKey
	 * @return PreviewResponse {@link PreviewResponse}
	 * @throws Exception {@link Exception}
	 */
	public PreviewResponse statementPreviewDetails(DocumentRequest doc, String authorization) throws Exception {

		PreviewResponse response = new PreviewResponse();
		
		// Check for UserId and Set Proper UserId 
		boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
		if(!userResult) {
			response.setErrorCode(99);
			response.setResponseMessage("UnAuthorized");
			response.setResponseStatus(Constants.FAILED);
			return response;
		}
		String userId = doc.getUserId();

		try {
			
			User user = k2ServiceImpl.fetchUserById(UUID.fromString(userId));
			ScriptLogger.writeInfo("Name = " + user.getUsername());
			String flow = doc.getFlow();
			
			List<AccountBaseEntity> accounts = new ArrayList<AccountBaseEntity>();
			if(Constants.FLOW_PIMONEY.equals(flow)) {
				accounts = user.getAllAccounts();
			}else {
				String fileRepoId = doc.getFileRepoId();
				accounts = k2ServiceImpl.fetchAccountListByFileRepoId(fileRepoId);
			}
			response = k2ServiceImpl.getResponseForReview(accounts, flow);
		}catch(Exception e) {
			ScriptLogger.writeInfo("Error in statementPreviewDetails", e);
		}
		
		response.setErrorCode(0);
		response.setResponseStatus(Constants.SUCCESS);
		return response;
	}

	/**
	 * This method is used for comfirm account and their transaction related details.
	 * @param request {@link PreviewActionRequest}
	 * @param authorization AuthorizationKey
	 * @return JSONObject {@link JSONObject}
	 * @throws IOException {@link IOException}
	 */
	public JSONObject confirmAction(PreviewActionRequest request, String authorization) throws IOException {

		long start = System.currentTimeMillis();
		
		JSONObject response = new JSONObject();
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		
		String fileRepoId = request.getFileRepoId();
		String flow = request.getFlow();
		boolean isPimoneyFlow = true;

		if(!Constants.FLOW_PIMONEY.equals(flow)) {
			isPimoneyFlow = false;
		}
		
		ScriptLogger.writeInfo("isPimoney flow => " + isPimoneyFlow);

		Set<StatementRepositoryEntity> stmts = null;

		List<AccountBaseEntity> accountList = null;

		List<InvestmentAccountEntity> accountListInv = new ArrayList<InvestmentAccountEntity>();

		if(isPimoneyFlow) {
			stmts = new HashSet<StatementRepositoryEntity>();
		}else {
			accountList = k2ServiceImpl.fetchAccountListByFileRepoId(fileRepoId);
			accountListInv = EntityFinder.filterInvestmentAccount(accountList);
		}

		try {
			
			// Check for UserId and Set Proper UserId 
			DocumentRequest doc = new DocumentRequest();
			doc.setUserId(request.getUserId());
			doc.setUid(request.getUid());
			boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
			if(!userResult) {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "UnAuthorized";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			String userId = doc.getUserId();
			
			List<PreviewAccount> accounts = request.getAccounts();
			ScriptLogger.writeInfo("userId -> " + userId);
			for(PreviewAccount account: accounts) {

				String accountType = account.getAccountType();
				String id = account.getId();

				ScriptLogger.writeInfo("AccountType -> " + accountType);
				ScriptLogger.writeInfo("id -> " +id);

				List<PreviewId> transactions = account.getTransactions();
				List<PreviewId> assets = account.getAssets();
				List<PreviewId> masters = account.getScxList();

				ScriptLogger.writeInfo("transaction size -> " + transactions.size());
				ScriptLogger.writeInfo("assets size -> " + assets.size());
				ScriptLogger.writeInfo("masters size -> " + masters.size());

				for(PreviewId txnid : transactions) {
					ScriptLogger.writeInfo("txn id -> " + txnid.getId());
					TransactionBaseEntity txn = null;

					if(isPimoneyFlow) {
						txn = k2ServiceImpl.fetchTransaction(txnid.getId(), accountType);
					}else {
						txn = EntityFinder.findTransactionById(txnid.getId(), accountList);
					}

					if(txn != null){
						txn.setConfirmed(true);
						if(isPimoneyFlow) {
							k2ServiceImpl.saveTransaction(txn);
						}
					}else {
						ScriptLogger.writeInfo("txn is not present for the given id");
					}
				}

				for(PreviewId assetid: assets) {
					ScriptLogger.writeInfo("assets id -> " + assetid.getId());
					HoldingAssetEntity asset = null;
					if(isPimoneyFlow) {
						asset = k2ServiceImpl.fetchHoldingAsset(assetid.getId());
					}else {
						asset = EntityFinder.findAssetById(assetid.getId(), accountListInv);
					}

					if(asset != null) {
						asset.setConfirmed(true);
						if(isPimoneyFlow) {
							k2ServiceImpl.saveHoldingAsset(asset);
						}
					}else {
						ScriptLogger.writeInfo("asset is not present for the given id");
					}
				}

				for(PreviewId masterid: masters) {
					ScriptLogger.writeInfo("master id -> " + masterid.getId());
					SecurityMaster master = null;
					if(isPimoneyFlow) {
						master = k2ServiceImpl.fetchSecurityMaster(masterid.getId());
					}
					else {
						master = EntityFinder.findMasterById(masterid.getId(), accountListInv);
					}

					if(master != null) {
						master.setConfirmed(true);
						if(isPimoneyFlow) {
							k2ServiceImpl.saveSecurityMaster(master);
						}
					}
					else {
						ScriptLogger.writeInfo("master is not present for the given id");
					}
				}

				AccountBaseEntity acctEntity = null;
				if(isPimoneyFlow) {
					acctEntity = k2ServiceImpl.fetchAccount(id, accountType);
				}else {
					acctEntity = EntityFinder.findAccountById(id, accountList, accountType);
				}

				if(acctEntity != null) {
					acctEntity.setConfirmed(true);
					if(isPimoneyFlow) {
						k2ServiceImpl.saveAccount(acctEntity);
						stmts.addAll(acctEntity.getStatements());
					}
				}else {
					ScriptLogger.writeInfo("account is not present for the given id");
				}
			}

			if(!isPimoneyFlow) {
				k2ServiceImpl.updateFileRepo(accountList, fileRepoId);
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Details Confirmed Successfully ";

		}catch(Exception e) {
			ScriptLogger.writeError("Error in conform action", e);
			responseMessage = "Error while processing";
		}

		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}

	/**
	 * This method is used for reject account and their transaction
	 * @param request {@link PreviewActionRequest}
	 * @param authorization AuthorizationKey
	 * @return JSONObject {@link JSONObject}
	 * @throws IOException {@link IOException}
	 */
	public JSONObject rejectAction(PreviewActionRequest request, String authorization) throws IOException {

		JSONObject response = new JSONObject();
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		
		String fileRepoId = request.getFileRepoId();
		String flow = request.getFlow();
		boolean isPimoneyFlow = true;

		if(!Constants.FLOW_PIMONEY.equals(flow)) {
			isPimoneyFlow = false;
		}

		List<AccountBaseEntity> accountList = new ArrayList<AccountBaseEntity>();

		List<InvestmentAccountEntity> accountListInv = new ArrayList<InvestmentAccountEntity>();

		if(!isPimoneyFlow) {
			accountList = k2ServiceImpl.fetchAccountListByFileRepoId(fileRepoId);
			accountListInv = EntityFinder.filterInvestmentAccount(accountList);
		}

		try {
			
			// Check for UserId and Set Proper UserId 
			DocumentRequest doc = new DocumentRequest();
			doc.setUserId(request.getUserId());
			doc.setUid(request.getUid());
			boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
			if(!userResult) {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "UnAuthorized";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			String userId = doc.getUserId();
			
			List<PreviewAccount> accounts = request.getAccounts();
			ScriptLogger.writeInfo("userId -> " + userId);
			
			for(PreviewAccount account: accounts) {

				String accountType = account.getAccountType();
				String id = account.getId();

				AccountBaseEntity accountEntity =EntityFinder.findAccountById(id, accountList, accountType);

				ScriptLogger.writeInfo("AccountType -> " + accountType);
				ScriptLogger.writeInfo("id -> " +id);

				List<PreviewId> transactions = account.getTransactions();
				List<PreviewId> assets = account.getAssets();
				List<PreviewId> masters = account.getScxList();

				ScriptLogger.writeInfo("transaction size -> " + transactions.size());
				ScriptLogger.writeInfo("assets size -> " + assets.size());
				ScriptLogger.writeInfo("masters size -> " + masters.size());

				for(PreviewId txnid : transactions) {
					ScriptLogger.writeInfo("txn id -> " + txnid.getId());
					TransactionBaseEntity txn = null;
					if(isPimoneyFlow) {
						txn = k2ServiceImpl.fetchTransaction(txnid.getId(), accountType);
					}else {
						txn = EntityFinder.findTransactionById(txnid.getId(), accountList);
					}
					ScriptLogger.writeInfo("txn found => " + (txn != null));

					if(txn != null){
						if(isPimoneyFlow) {
							k2ServiceImpl.deleteTransaction(txn);
						}else {
							k2ServiceImpl.deleteTransactionFromFileRepo(txn, accountEntity);
						}
					}else {
						ScriptLogger.writeInfo("txn is not present for the given id");
					}
				}

				for(PreviewId assetid: assets) {
					ScriptLogger.writeInfo("assets id -> " + assetid.getId());
					HoldingAssetEntity asset = null;
					if(isPimoneyFlow) {
						asset = k2ServiceImpl.fetchHoldingAsset(assetid.getId());
					}else {
						asset = EntityFinder.findAssetById(assetid.getId(), accountListInv);
					}

					if(asset != null) {
						if(isPimoneyFlow) {
							k2ServiceImpl.deleteHoldingAsset(asset);
						}else {
							if(k2ServiceImpl.isInvestmentAccountEntity(accountEntity)) {
								k2ServiceImpl.deleteAssetFromFileRepo(asset, (InvestmentAccountEntity) accountEntity);
							}
						}
					}else {
						ScriptLogger.writeInfo("asset is not present for the given id");
					}
				}

				for(PreviewId masterid: masters) {
					ScriptLogger.writeInfo("master id -> " + masterid.getId());
					SecurityMaster master = k2ServiceImpl.fetchSecurityMaster(masterid.getId());
					if(isPimoneyFlow) {
						master = k2ServiceImpl.fetchSecurityMaster(masterid.getId());
					}
					else {
						master = EntityFinder.findMasterById(masterid.getId(), accountListInv);
					}
					
					if(master != null) {
						if(isPimoneyFlow) {
							k2ServiceImpl.deleteSecurityMaster(master);
						}
						else {
							if(k2ServiceImpl.isInvestmentAccountEntity(accountEntity)) {
								k2ServiceImpl.deleteMasterFromFileRepo(master, (InvestmentAccountEntity) accountEntity);
							}
						}
					}else {
						ScriptLogger.writeInfo("master is not present for the given id");
					}
				}

				AccountBaseEntity acctEntity = null;
				if(isPimoneyFlow) {
					acctEntity = k2ServiceImpl.fetchAccount(id, accountType);
				}else {
					acctEntity = EntityFinder.findAccountById(id, accountList, accountType);
				}

				if(acctEntity != null) {
					if(k2ServiceImpl.isAccountElegibleForDeletion(acctEntity)) {
						if(isPimoneyFlow) {
							k2ServiceImpl.deleteAccount(acctEntity); 
						}else {
							accountList.remove(accountEntity);
						}
					}else {
						ScriptLogger.writeInfo("account is not empty so not deleting.");
					}
				}else {
					ScriptLogger.writeInfo("account is not present for the given id");
				}
			}

			if(!isPimoneyFlow) {
				k2ServiceImpl.updateFileRepo(accountList, fileRepoId);
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Details Rejected Successfully ";

		}catch(Exception e) {
			ScriptLogger.writeError("Error in reject action", e);
			responseMessage = "Error while processing";
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used for update account and their transasction information in statement preview page
	 * @param request {@link UpdateInvestmentRequest}
	 * @param authorization AuthorizationKey
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject updateAccounts(UpdateInvestmentRequest request, String authorization) {
		
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		
		Object dataObj = null;
		String flow = request.getFlow();
		String fileRepoId = request.getFileRepoId();
		String tag = request.getTag();
		boolean isPimoneyFlow = true;
		if(!Constants.FLOW_PIMONEY.equals(flow)) {
			isPimoneyFlow = false;
		}

		try {
			// Check for UserId and Set Proper UserId 
			DocumentRequest doc = new DocumentRequest();
			doc.setUserId(request.getUserId());
			doc.setUid(request.getUid());
			boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
			if(!userResult) {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "UnAuthorized";
				response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				return response;
			}
			String userId = doc.getUserId();

			AssetEditFields assetNew= request.getAssetFields();
			TransactionEditFields txnNew= request.getTransactionFields();
			SecurityMasterEditFields masterNew= request.getSecurityMasterFields();

			if(assetNew != null) {
				ScriptLogger.writeInfo("updating assets");
				if(isPimoneyFlow) {
					dataObj = k2ServiceImpl.updateAssetFields(assetNew, userId);
				}else {
					dataObj = k2ServiceImpl.updateAssetFieldsInFileRepo(assetNew, fileRepoId);
				}
			}else if(txnNew != null) {
				ScriptLogger.writeInfo("updating transactions");
				if(isPimoneyFlow) {
					dataObj = k2ServiceImpl.updateTransactionFields(txnNew, userId, tag);
				}else {
					dataObj = k2ServiceImpl.updateTransactionFieldsInFileRepo(txnNew, fileRepoId);
				}
			}else if(masterNew != null) {
				ScriptLogger.writeInfo("updating masters");
				if(isPimoneyFlow) {
					dataObj = k2ServiceImpl.updateMasterFields(masterNew, userId);
				}else {
					dataObj = k2ServiceImpl.updateMasterFieldsInFileRepo(masterNew, fileRepoId);
				}
			}else {
				ScriptLogger.writeInfo("Nothing to update.");
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Investment Updated Successfully";

		}catch(Exception e) {
			ScriptLogger.writeError("Error in update investment ", e);
			responseMessage ="Error while processing";
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", dataObj);
		return response;
	}

	/**
	 * This method is used to get all currency list store in database
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCurrenyList(DocumentRequest request) {

		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		Object dataObj = null;

		try {
			dataObj = k2ServiceImpl.fetchCurrencyList();
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Currency List Fetched Successfully";
		}catch(Exception e) {
			ScriptLogger.writeError("Error in fetching currency list", e);
			responseMessage = "Error while processing";
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("currencyList", dataObj);
		return response;
	}

	/**
	 * This method is used to download pdf statement
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject downloadPdfStatement(DocumentRequest request) {

		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		byte[] docByte = null;
		try {
			String flow = request.getFlow();
			String repoId = request.getRepoId();
			boolean isPimoneyFlow = true;

			if(!Constants.FLOW_PIMONEY.equals(flow)) {
				isPimoneyFlow = false;
			}

			RepositoryBase repo = null;
			if(isPimoneyFlow) {
				ScriptLogger.writeInfo("Statement Repo flow");
				repo = k2ServiceImpl.fetchStatementRepositoryById(repoId);
			}else {
				ScriptLogger.writeInfo("File repo flow");
				repo = k2ServiceImpl.fetchFileRepositoryById(repoId);
			}

			if(repo != null) {
				docByte = repo.getBytecode();
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "file fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in download pdf statement", e);
		}

		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("byteCode", docByte);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}

	/**
	 * This method is used to download the file.
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject downloadFile(DocumentRequest request) {
		
		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		byte[] docByte = null;
		try {
			String repoId = request.getRepoId();
			String fileName = request.getName();

			if(StringUtils.isEmpty(fileName)) {
				throw new Exception("Empty File name");
			}

			FileRepositoryEntity repo = k2ServiceImpl.fetchFileRepositoryById(repoId);
			if(repo != null) {

				String filePath = repo.getFilePath();
				if(StringUtils.isNotEmpty(filePath)) {
					File dir = new File(filePath);
					if(dir != null) {
						String[]entries = dir.list();
						if(entries != null) {
							for(String s: entries){
								File currentFile = new File(dir.getPath(),s);
								if(fileName.equals(currentFile.getName())) {
									Path path = Paths.get(dir.getPath(),s);
									docByte = Files.readAllBytes(path);
								}
							}
						}
					}
				}
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "file fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in download pdf statement", e);
		}

		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("byteCode", docByte);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}

	
}
