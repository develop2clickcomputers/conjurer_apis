package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pisight.pimoney.dto.AssetEditFields;
import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.models.InvestmentAccount;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetHistory;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;

public class AssetEntityUtil {

	public static void copyToAssetEntity(HoldingAsset asset, HoldingAssetEntity entity) throws ParseException {

		if(asset == null) {
			return;
		}

		entity.setFingerprint(asset.getFingerprint());
		entity.setHoldingAssetAccountNumber(EntityUtil.maskString(asset.getHoldingAssetAccountNumber()));
		entity.setHoldingAssetSubAccountNumber(asset.getHoldingAssetSubAccountNumber());
		entity.setHoldingAssetSecurityId(asset.getHoldingAssetSecurityId());
		//		entity.setHoldingAssetName(asset.getHoldingAssetName());
		entity.setHoldingAssetDescription(asset.getHoldingAssetDescription());
		//		entity.setHoldingAssetCategory(asset.getHoldingAssetCategory());
		entity.setHoldingAssetSubCategory(asset.getHoldingAssetSubCategory());
		//		entity.setHoldingAssetCurrency(asset.getHoldingAssetCurrency());
		entity.setHoldingAssetYield(asset.getHoldingAssetYield());
		entity.setHoldingAssetQuantity(asset.getHoldingAssetQuantity());
		entity.setHoldingAssetAverageUnitCost(asset.getHoldingAssetAverageUnitCost());
		entity.setHoldingAssetIndicativePrice(asset.getHoldingAssetIndicativePrice());
		entity.setHoldingAssetCost(asset.getHoldingAssetCost());
		entity.setHoldingAssetCurrentValue(asset.getHoldingAssetCurrentValue());
		entity.setHoldingAssetIndicativePriceDate(WebUtil.convertToDate(asset.getHoldingAssetIndicativePriceDate()));
		entity.setHoldingAssetProfit(asset.getHoldingAssetProfit());
		entity.setHoldingAssetProfitPerc(asset.getHoldingAssetProfitPerc());
		entity.setHoldingAssetCustodian(asset.getHoldingAssetCustodian());
		entity.setHoldingAssetMaturityDate(WebUtil.convertToDate(asset.getHoldingAssetMaturityDate()));
		entity.setHoldingAssetIssuer(asset.getHoldingAssetIssuer());
		entity.setHoldingAssetAccruedInterest(asset.getHoldingAssetAccruedInterest());
		entity.setHoldingAssetLastFxRate(asset.getHoldingAssetLastFxRate());
		entity.setHoldingAssetFxAccruedInterest(asset.getHoldingAssetFxAccruedInterest());
		entity.setHoldingAssetStartDate(WebUtil.convertToDate(asset.getHoldingAssetStartDate()));
		entity.setHoldingAssetFxMarketValue(asset.getHoldingAssetFxMarketValue());
		entity.setHoldingAssetUnrealizedProfitLoss(asset.getHoldingAssetUnrealizedProfitLoss());
		entity.setHoldingAssetUnrealizedProfitLossCurrency(asset.getHoldingAssetUnrealizedProfitLossCurrency());
		entity.setHoldingAssetISIN(asset.getHoldingAssetISIN());
		entity.setHoldingAssetCommencingDate(WebUtil.convertToDate(asset.getHoldingAssetCommencingDate()));
		entity.setHoldingAssetCoupon(asset.getHoldingAssetCoupon());
		entity.setHoldingAssetStrikePrice(asset.getHoldingAssetStrikePrice());
		entity.setHoldingAssetInterestTillMaturity(asset.getHoldingAssetInterestTillMaturity());
		entity.setHoldingAssetOption(asset.getHoldingAssetOption());
		entity.setTransCode(asset.getTransCode());
		entity.setAssetCostDate(WebUtil.convertToDate(asset.getAssetCostDate()));
		entity.setClosingMethodCode(asset.getClosingMethodCode());
		entity.setVersusDate(WebUtil.convertToDate(asset.getVersusDate()));
		entity.setSrcDstType(asset.getSrcDstType());
		entity.setSrcDstSymbol(asset.getSrcDstSymbol());
		entity.setTradeDateFxRate(asset.getTradeDateFxRate());
		entity.setValuationDateFxRate(asset.getValuationDateFxRate());
		entity.setAssetOriginalFxRate(asset.getAssetOriginalFxRate());
		//		entity.setMarkToMarket(asset.isMarkToMarket());
		entity.setAssetMTM(asset.getAssetMTM());
		entity.setAssetWithholdingTax(asset.getAssetWithholdingTax());
		entity.setExchange(asset.getExchange());
		entity.setExchangeFee(asset.getExchangeFee());
		entity.setCommission(asset.getCommission());
		entity.setOtherFees(asset.getOtherFees());
		//		entity.setImpliedCommission(asset.isImpliedCommission());
		entity.setCommissionPurpose(asset.getCommissionPurpose());
		//		entity.setAssetPledge(asset.getAssetPledge());
		entity.setDestinationPledge(asset.getDestinationPledge());
		entity.setDestinationCustodian(asset.getDestinationCustodian());
		entity.setAssetDuration(asset.getAssetDuration());
		entity.setRecordDate(WebUtil.convertToDate(asset.getRecordDate()));
		entity.setStrategy(asset.getStrategy());
		entity.setReclaimAmount(asset.getReclaimAmount());
		entity.setAccrualAccount(asset.getAccrualAccount());
		entity.setDividendAccrualMethod(asset.getDividendAccrualMethod());
		entity.setMgmtFeePeriodDate(WebUtil.convertToDate(asset.getMgmtFeePeriodDate()));
		//		entity.setRecallable(asset.isRecallable());
		entity.setBrokerFirmSymbol(asset.getBrokerFirmSymbol());
		entity.setCommittedCapital(asset.getCommittedCapital());
		entity.setContributedCapital(asset.getContributedCapital());
		entity.setTradeDate(WebUtil.convertToDate(asset.getTradeDate()));
		entity.setSettlementDate(WebUtil.convertToDate(asset.getSettlementDate()));
		entity.setSecurityType(asset.getSecurityType());
		entity.setDestinationCurrency(asset.getDestinationCurrency());
		entity.setAssetTicker(asset.getAssetTicker());
		entity.setAssetCUSIP(asset.getAssetCUSIP());
		entity.setAssetSEDOL(asset.getAssetSEDOL());
		entity.setAssetQUIK(asset.getAssetQUIK());
		entity.setAssetRIC(asset.getAssetRIC());
		entity.setFxDenominatorCurrency(asset.getFxDenominatorCurrency());
		entity.setFxNumeratorCurrency(asset.getFxNumeratorCurrency());
		entity.setBloombergTicker(asset.getBloombergTicker());
		entity.setUnderBloombergTicker(asset.getUnderBloombergTicker());
		entity.setUnderCUSIP(asset.getUnderCUSIP());
		entity.setUnderISIN(asset.getUnderISIN());
		entity.setUnderRIC(asset.getUnderRIC());
		entity.setUnderSEDOL(asset.getUnderSEDOL());
		entity.setUnderTicker(asset.getUnderTicker());
		entity.setPriceFactor(asset.getPriceFactor());
		entity.setBasePrice(asset.getBasePrice());
		entity.setAccountCurrency(asset.getAccountCurrency());
		entity.setSettlementCurrency(asset.getSettlementCurrency());
		entity.setLastTradeDate(WebUtil.convertToDate(asset.getLastTradeDate()));
		entity.setDividendReInvestIndicator(asset.getDividendReInvestIndicator());
		entity.setOptionType(asset.getOptionType());
		entity.setSecurityCountryCode(asset.getSecurityCountryCode());
		entity.setCorpActionsIndicator(asset.getCorpActionsIndicator());
		entity.setVAT(asset.getVAT());
		entity.setInvestmentObjective(asset.getInvestmentObjective());
		entity.setBondNature(asset.isBondNature());
		

	}

	public static void copyToAssetEntity(HoldingAssetHistory asset, HoldingAssetEntity entity) throws ParseException {

		if(asset == null) {
			return;
		}

		entity.setFingerprint(asset.getFingerprint());
		entity.setHoldingAssetAccountNumber(EntityUtil.maskString(asset.getHoldingAssetAccountNumber()));
		entity.setHoldingAssetSubAccountNumber(asset.getHoldingAssetSubAccountNumber());
		entity.setHoldingAssetSecurityId(asset.getHoldingAssetSecurityId());
		//		entity.setHoldingAssetName(asset.getHoldingAssetName());
		entity.setHoldingAssetDescription(asset.getHoldingAssetDescription());
		//		entity.setHoldingAssetCategory(asset.getHoldingAssetCategory());
		entity.setHoldingAssetSubCategory(asset.getHoldingAssetSubCategory());
		//		entity.setHoldingAssetCurrency(asset.getHoldingAssetCurrency());
		entity.setHoldingAssetYield(asset.getHoldingAssetYield());
		entity.setHoldingAssetQuantity(asset.getHoldingAssetQuantity());
		entity.setHoldingAssetAverageUnitCost(asset.getHoldingAssetAverageUnitCost());
		entity.setHoldingAssetIndicativePrice(asset.getHoldingAssetIndicativePrice());
		entity.setHoldingAssetCost(asset.getHoldingAssetCost());
		entity.setHoldingAssetCurrentValue(asset.getHoldingAssetCurrentValue());
		entity.setHoldingAssetIndicativePriceDate(asset.getHoldingAssetIndicativePriceDate());
		entity.setHoldingAssetProfit(asset.getHoldingAssetProfit());
		entity.setHoldingAssetProfitPerc(asset.getHoldingAssetProfitPerc());
		entity.setHoldingAssetCustodian(asset.getHoldingAssetCustodian());
		entity.setHoldingAssetMaturityDate(asset.getHoldingAssetMaturityDate());
		entity.setHoldingAssetIssuer(asset.getHoldingAssetIssuer());
		entity.setHoldingAssetAccruedInterest(asset.getHoldingAssetAccruedInterest());
		entity.setHoldingAssetLastFxRate(asset.getHoldingAssetLastFxRate());
		entity.setHoldingAssetFxAccruedInterest(asset.getHoldingAssetFxAccruedInterest());
		entity.setHoldingAssetStartDate(asset.getHoldingAssetStartDate());
		entity.setHoldingAssetFxMarketValue(asset.getHoldingAssetFxMarketValue());
		entity.setHoldingAssetUnrealizedProfitLoss(asset.getHoldingAssetUnrealizedProfitLoss());
		entity.setHoldingAssetUnrealizedProfitLossCurrency(asset.getHoldingAssetUnrealizedProfitLossCurrency());
		entity.setHoldingAssetISIN(asset.getHoldingAssetISIN());
		entity.setHoldingAssetCommencingDate(asset.getHoldingAssetCommencingDate());
		entity.setHoldingAssetCoupon(asset.getHoldingAssetCoupon());
		entity.setHoldingAssetStrikePrice(asset.getHoldingAssetStrikePrice());
		entity.setHoldingAssetInterestTillMaturity(asset.getHoldingAssetInterestTillMaturity());
		entity.setHoldingAssetOption(asset.getHoldingAssetOption());
		entity.setTransCode(asset.getTransCode());
		entity.setAssetCostDate(asset.getAssetCostDate());
		entity.setClosingMethodCode(asset.getClosingMethodCode());
		entity.setVersusDate(asset.getVersusDate());
		entity.setSrcDstType(asset.getSrcDstType());
		entity.setSrcDstSymbol(asset.getSrcDstSymbol());
		entity.setTradeDateFxRate(asset.getTradeDateFxRate());
		entity.setValuationDateFxRate(asset.getValuationDateFxRate());
		entity.setAssetOriginalFxRate(asset.getAssetOriginalFxRate());
		//		entity.setMarkToMarket(asset.isMarkToMarket());
		entity.setAssetMTM(asset.getAssetMTM());
		entity.setAssetWithholdingTax(asset.getAssetWithholdingTax());
		entity.setExchange(asset.getExchange());
		entity.setExchangeFee(asset.getExchangeFee());
		entity.setCommission(asset.getCommission());
		entity.setOtherFees(asset.getOtherFees());
		//		entity.setImpliedCommission(asset.isImpliedCommission());
		entity.setCommissionPurpose(asset.getCommissionPurpose());
		//		entity.setAssetPledge(asset.getAssetPledge());
		entity.setDestinationPledge(asset.getDestinationPledge());
		entity.setDestinationCustodian(asset.getDestinationCustodian());
		entity.setAssetDuration(asset.getAssetDuration());
		entity.setRecordDate(asset.getRecordDate());
		entity.setStrategy(asset.getStrategy());
		entity.setReclaimAmount(asset.getReclaimAmount());
		entity.setAccrualAccount(asset.getAccrualAccount());
		entity.setDividendAccrualMethod(asset.getDividendAccrualMethod());
		entity.setMgmtFeePeriodDate(asset.getMgmtFeePeriodDate());
		//		entity.setRecallable(asset.isRecallable());
		entity.setBrokerFirmSymbol(asset.getBrokerFirmSymbol());
		entity.setCommittedCapital(asset.getCommittedCapital());
		entity.setContributedCapital(asset.getContributedCapital());
		entity.setTradeDate(asset.getTradeDate());
		entity.setSettlementDate(asset.getSettlementDate());
		entity.setSecurityType(asset.getSecurityType());
		entity.setDestinationCurrency(asset.getDestinationCurrency());
		entity.setAssetTicker(asset.getAssetTicker());
		entity.setAssetCUSIP(asset.getAssetCUSIP());
		entity.setAssetSEDOL(asset.getAssetSEDOL());
		entity.setAssetQUIK(asset.getAssetQUIK());
		entity.setAssetRIC(asset.getAssetRIC());
		entity.setFxDenominatorCurrency(asset.getFxDenominatorCurrency());
		entity.setFxNumeratorCurrency(asset.getFxNumeratorCurrency());
		entity.setBloombergTicker(asset.getBloombergTicker());
		entity.setUnderBloombergTicker(asset.getUnderBloombergTicker());
		entity.setUnderCUSIP(asset.getUnderCUSIP());
		entity.setUnderISIN(asset.getUnderISIN());
		entity.setUnderRIC(asset.getUnderRIC());
		entity.setUnderSEDOL(asset.getUnderSEDOL());
		entity.setUnderTicker(asset.getUnderTicker());
		entity.setPriceFactor(asset.getPriceFactor());
		entity.setBasePrice(asset.getBasePrice());
		entity.setAccountCurrency(asset.getAccountCurrency());
		entity.setSettlementCurrency(asset.getSettlementCurrency());
		entity.setLastTradeDate(asset.getLastTradeDate());
		entity.setDividendReInvestIndicator(asset.getDividendReInvestIndicator());
		entity.setOptionType(asset.getOptionType());
		entity.setSecurityCountryCode(asset.getSecurityCountryCode());
		entity.setCorpActionsIndicator(asset.getCorpActionsIndicator());
		entity.setVAT(asset.getVAT());
		entity.setInvestmentObjective(asset.getInvestmentObjective());
		entity.setBondNature(asset.isBondNature());
	
	}

	
	public static HoldingAssetEntity toAssetEntity(HoldingAsset asset) throws ParseException {

		HoldingAssetEntity entity = new HoldingAssetEntity();

		if(asset == null) {
			return null;
		}

		copyToAssetEntity(asset, entity);
		entity.setHoldingAssetName(asset.getHoldingAssetName());
		entity.setHoldingAssetCategory(asset.getHoldingAssetCategory());
		entity.setHoldingAssetCurrency(asset.getHoldingAssetCurrency());

		return entity;

	}

	/**
	 * This method applies logic to create, update and delete for the assets of the already present account.
	 * It return the list of HoldingAssetEntities which need to be saved.
	 * 
	 * @param account {@link InvestmentAccount}
	 * @param entity {@link InvestmentAccountEntity}
	 * @return list of {@link HoldingAssetEntity}
	 * @throws ParseException {@link ParseException}
	 */
	public static List<HoldingAssetEntity> handleAssetEntitiesForOldAccount(InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {

		List<HoldingAssetEntity> assetEntities = entity.getAssets();
		List<HoldingAsset> assets = account.getAssets();

		List<HoldingAssetEntity> result = new ArrayList<HoldingAssetEntity>();

		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		HashMap<String, HoldingAsset> assetMap = new HashMap<String, HoldingAsset>();

		for(HoldingAsset asset: assets) {

			String description = asset.getHoldingAssetDescription();
			String category = asset.getHoldingAssetCategory();
			String currency = asset.getHoldingAssetCurrency();

			String hash = BasicEncodeDecodeUtil.encodeString(description+category+currency);
			map.put(hash, false);
			assetMap.put(hash, asset);
		}

		for(HoldingAssetEntity assetEntity: assetEntities) {

			String description = assetEntity.getHoldingAssetDescription();
			String category = assetEntity.getHoldingAssetCategory();
			String currency = assetEntity.getHoldingAssetCurrency();

			String hash = BasicEncodeDecodeUtil.encodeString(description+category+currency);

			if(map.containsKey(hash)) {
				HoldingAsset asset = assetMap.get(hash);
				if(!assetEntity.getFingerprint().equals(asset.getFingerprint())) {
					copyToAssetEntity(assetMap.get(hash), assetEntity);
					assetEntity.setStatus(true);
					result.add(assetEntity);
				}else {
					if(!assetEntity.isStatus()) {
						assetEntity.setStatus(true);
						result.add(assetEntity);
					}
				}
				map.put(hash, true);
			}else {
				// asset is no longer active, so setting the status to false. i.e. soft deleting
				ScriptLogger.writeInfo("Asset is no longer active so setting the stats to false....");
				assetEntity.setStatus(false);
				result.add(assetEntity);
			}

		}

		for(HoldingAsset asset: assets) {

			String description = asset.getHoldingAssetDescription();
			String category = asset.getHoldingAssetCategory();
			String currency = asset.getHoldingAssetCurrency();

			String hash = BasicEncodeDecodeUtil.encodeString(description+category+currency);

			if(!map.get(hash)) {
				HoldingAssetEntity newAssetEntity = toAssetEntity(asset);
				newAssetEntity.setStatus(true);
				result.add(newAssetEntity);
			}
		}
		return result;
	}

	public static void updateHoldingAssetEntity(HoldingAssetEntity asset, AssetEditFields assetNew) {

		if(asset == null || assetNew == null) {
			return;
		}

		asset.setHoldingAssetCategory(assetNew.getHoldingAssetCategory());
		asset.setHoldingAssetCoupon(assetNew.getHoldingAssetCoupon());
		asset.setHoldingAssetQuantity(assetNew.getHoldingAssetQuantity());
		asset.setHoldingAssetCurrentValue(assetNew.getHoldingAssetCurrentValue());
		asset.setHoldingAssetAverageUnitCost(assetNew.getHoldingAssetAverageUnitCost());
		asset.setHoldingAssetIndicativePrice(assetNew.getHoldingAssetIndicativePrice());
		asset.setHoldingAssetMaturityDate(assetNew.getHoldingAssetMaturityDate());
		asset.setHoldingAssetLastFxRate(assetNew.getHoldingAssetLastFxRate());
		asset.setHoldingAssetUnrealizedProfitLoss(assetNew.getHoldingAssetUnrealizedProfitLoss());
		asset.setHoldingAssetIndicativePriceDate(assetNew.getHoldingAssetIndicativePriceDate());
		asset.setHoldingAssetStrikePrice(assetNew.getHoldingAssetStrikePrice());
		asset.setHoldingAssetCost(assetNew.getHoldingAssetCost());
		//		asset.setHoldingAssetCurrency(assetNew.getHoldingAssetCurrency());
		//		asset.setHoldingAssetDescription(assetNew.getHoldingAssetDescription());
		asset.setHoldingAssetISIN(assetNew.getHoldingAssetISIN());
		asset.setHoldingAssetStartDate(assetNew.getHoldingAssetStartDate());
		asset.setHoldingAssetSubCategory(assetNew.getHoldingAssetSubCategory());
		asset.setHoldingAssetFxAccruedInterest(assetNew.getHoldingAssetFxAccruedInterest());
		asset.setHoldingAssetAccountNumber(EntityUtil.maskString(assetNew.getHoldingAssetAccountNumber()));
		asset.setHoldingAssetSubAccountNumber(assetNew.getHoldingAssetSubAccountNumber());
		asset.setHoldingAssetYield(assetNew.getHoldingAssetYield());
		asset.setHoldingAssetAccruedInterest(assetNew.getHoldingAssetAccruedInterest());
		asset.setHoldingAssetUnrealizedProfitLossCurrency(assetNew.getHoldingAssetUnrealizedProfitLossCurrency());
		asset.setTransCode(assetNew.getTransCode());
		asset.setCommission(assetNew.getCommission());
		asset.setOtherFees(assetNew.getOtherFees());

	}

	@SuppressWarnings("rawtypes")
	public static void copyToAsset(HoldingAssetEntity entity, HoldingAsset asset) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		System.out.println("test convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set")) {
				continue;
			}
			Object value = method.invoke(entity);

			System.out.println("source method name -> " + methodName);
			System.out.println("source method type -> " + srcType);
			System.out.println("source value -> " + value);

			if (value != null) {

				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3);
				}
				else {
					methodName = methodName.substring(2);
				}

				Method dstMethod = null;
				try {
					dstMethod = asset.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = asset.getClass().getMethod("is"+methodName);
					}catch(NoSuchMethodException eInner) {
						System.out.println("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				System.out.println("dest method name -> " + dstMethod);
				System.out.println("dest method type -> " + dstType);
				System.out.println();
				System.out.println();

				if(srcType.equals(Date.class)) {
					String date = EntityUtil.convertToDateString((Date) value);
					Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(asset, date);
				}
				else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = asset.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(asset, (Boolean) value);
					}
					else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(asset,  tempVal);
					}
				}
				else {
					Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(asset,  (String) value);
				}
			}
		}
	}

}
