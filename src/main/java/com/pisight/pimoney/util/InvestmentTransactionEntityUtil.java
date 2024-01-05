package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pisight.pimoney.dto.TransactionEditFields;
import com.pisight.pimoney.models.InvestmentAccount;
import com.pisight.pimoney.models.InvestmentTransaction;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;

public class InvestmentTransactionEntityUtil {

	public static void copyToInvestmentTransactionEntity(InvestmentTransaction txn, InvestmentTransactionEntity entity) throws ParseException {

		if(txn == null) {
			return;
		}

		entity.setFingerprint(txn.getFingerprint());
		//		entity.setTransactionHash(txn.getTransactionHash());
		entity.setAccountNumber(EntityUtil.maskString(txn.getAccountNumber()));
		entity.setSubAccountNumber(txn.getSubAccountNumber());
		entity.setCurrency(txn.getCurrency());
		entity.setType(txn.getType());
		entity.setAmount(txn.getAmount());
		entity.setTransactionDate(WebUtil.convertToDate(txn.getTransactionDate()));
		entity.setPostDate(WebUtil.convertToDate(txn.getPostDate()));
		entity.setDescription(txn.getDescription());
		entity.setAssetCategory(txn.getAssetCategory());
		entity.setAssetName(txn.getAssetName());
		entity.setAssetMarket(txn.getAssetMarket());
		entity.setAssetInstrument(txn.getAssetInstrument());
		entity.setAssetYield(txn.getAssetYield());
		entity.setAssetQuantity(txn.getAssetQuantity());
		entity.setAssetUnitCost(txn.getAssetUnitCost());
		entity.setAssetCost(txn.getAssetCost());
		entity.setAssetTradeDate(WebUtil.convertToDate(txn.getAssetTradeDate()));
		entity.setAssetCustodian(txn.getAssetCustodian());
		entity.setAssetIssuer(txn.getAssetIssuer());
		entity.setAssetISIN(txn.getAssetISIN());
		entity.setValuationDate(WebUtil.convertToDate(txn.getValuationDate()));
		entity.setStartDate(WebUtil.convertToDate(txn.getStartDate()));
		entity.setMaturityDate(WebUtil.convertToDate(txn.getMaturityDate()));
		entity.setCoupon(txn.getCoupon());
		entity.setAccruedInterest(txn.getAccruedInterest());
		entity.setStrikePrice(txn.getStrikePrice());
		entity.setExpiryDate(WebUtil.convertToDate(txn.getExpiryDate()));
		entity.setBrokerageAndLevies(txn.getBrokerageAndLevies());
		entity.setTransCode(txn.getTransCode());
		entity.setAssetCostDate(WebUtil.convertToDate(txn.getAssetCostDate()));
		entity.setClosingMethodCode(txn.getClosingMethodCode());
		entity.setVersusDate(WebUtil.convertToDate(txn.getVersusDate()));
		entity.setSrcDstType(txn.getSrcDstType());
		entity.setSrcDstSymbol(txn.getSrcDstSymbol());
		entity.setTradeDateFxRate(txn.getTradeDateFxRate());
		entity.setValuationDateFxRate(txn.getValuationDateFxRate());
		entity.setAssetOriginalFxRate(txn.getAssetOriginalFxRate());
		//		entity.setMarkToMarket(txn.isMarkToMarket());
		entity.setAssetMTM(txn.getAssetMTM());
		entity.setAssetWithholdingTax(txn.getAssetWithholdingTax());
		entity.setExchange(txn.getExchange());
		entity.setExchangeFee(txn.getExchangeFee());
		entity.setCommission(txn.getCommission());
		entity.setOtherFees(txn.getOtherFees());
		//		entity.setImpliedCommission(txn.isImpliedCommission());
		entity.setCommissionPurpose(txn.getCommissionPurpose());
		//		entity.setAssetPledge(txn.getAssetPledge());
		entity.setDestinationPledge(txn.getDestinationPledge());
		entity.setAssetDuration(txn.getAssetDuration());
		entity.setRecordDate(WebUtil.convertToDate(txn.getRecordDate()));
		entity.setStrategy(txn.getStrategy());
		entity.setReclaimAmount(txn.getReclaimAmount());
		entity.setAccrualAccount(txn.getAccrualAccount());
		entity.setDividendAccrualMethod(txn.getDividendAccrualMethod());
		entity.setMgmtFeePeriodDate(WebUtil.convertToDate(txn.getMgmtFeePeriodDate()));
		//		entity.setShortPositions(txn.isShortPositions());
		entity.setDividendStatus(txn.getDividendStatus());
		//		entity.setRecallable(txn.isRecallable());
		entity.setBrokerFirmSymbol(txn.getBrokerFirmSymbol());
		entity.setCommittedCapital(txn.getCommittedCapital());
		entity.setContributedCapital(txn.getContributedCapital());
		entity.setSecurityType(txn.getSecurityType());
		entity.setSecurityId(txn.getSecurityId());
		entity.setDestinationCurrency(txn.getDestinationCurrency());
		entity.setAssetTicker(txn.getAssetTicker());
		entity.setAssetCUSIP(txn.getAssetCUSIP());
		entity.setAssetSEDOL(txn.getAssetSEDOL());
		entity.setAssetQUIK(txn.getAssetQUIK());
		entity.setFxNumeratorCurrency(txn.getFxNumeratorCurrency());
		entity.setFxDenominatorCurrency(txn.getFxDenominatorCurrency());
		entity.setUnderBloombergTicker(txn.getUnderBloombergTicker());
		entity.setUnderCUSIP(txn.getUnderCUSIP());
		entity.setUnderISIN(txn.getUnderISIN());
		entity.setUnderRIC(txn.getUnderRIC());
		entity.setUnderSEDOL(txn.getUnderSEDOL());
		entity.setUnderTicker(txn.getUnderTicker());
		entity.setPriceFactor(txn.getPriceFactor());
		entity.setBasePrice(txn.getBasePrice());
		entity.setAccountCurrency(txn.getAccountCurrency());
		entity.setSettlementCurrency(txn.getSettlementCurrency());
		entity.setCorpActionsIndicator(txn.getCorpActionsIndicator());

	}

	public static InvestmentTransactionEntity toInvestmentTransactionEntity(InvestmentTransaction transaction) throws ParseException {

		InvestmentTransactionEntity entity = new InvestmentTransactionEntity();

		if(transaction == null) {
			return null;
		}
		copyToInvestmentTransactionEntity(transaction, entity);
		return entity;
	}

	public static List<InvestmentTransactionEntity> handleInvestmentTransactionEntitiesForOldAccount(
			InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {

		List<InvestmentTransactionEntity> result = new ArrayList<InvestmentTransactionEntity>();

		HashMap<String, InvestmentTransactionEntity> map = new HashMap<String, InvestmentTransactionEntity>();

		List<InvestmentTransactionEntity> txnEntities = entity.getTransactions();
		List<InvestmentTransaction> txns = account.getInvestmentTransactions();

		for(InvestmentTransactionEntity txnEntity: txnEntities) {
			map.put(txnEntity.getFingerprint(), txnEntity);
		}

		for(InvestmentTransaction txn: txns) {
			InvestmentTransactionEntity newTxnEntity = null;
			if(!map.containsKey(txn.getFingerprint())) {
				newTxnEntity = toInvestmentTransactionEntity(txn);
				newTxnEntity.setStatus(true);
				result.add(newTxnEntity);
			}
			else {
				newTxnEntity = map.get(txn.getFingerprint());
				if(!newTxnEntity.isStatus()) {
					newTxnEntity.setStatus(true);
					result.add(newTxnEntity);
				}
			}
		}
		return result;
	}

	public static void updateInvestmentTransactionEntity(InvestmentTransactionEntity txn,
			TransactionEditFields txnNew) {

		if(txn == null || txnNew == null) {
			return;
		}
		txn.setTransactionDate(txnNew.getTransactionDate());
		txn.setAssetCategory(txnNew.getAssetCategory());
		txn.setAssetQuantity(txnNew.getAssetQuantity());
		txn.setAssetUnitCost(txnNew.getAssetUnitCost());
		txn.setCurrency(txnNew.getCurrency());
		txn.setAmount(txnNew.getAmount());

	}

	@SuppressWarnings("rawtypes")
	public static void copyToInvestmentTransaction(InvestmentTransactionEntity entity,
			InvestmentTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo("test convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set")) {
				continue;
			}
			Object value = method.invoke(entity);

		//	ScriptLogger.writeInfo("source method name -> " + methodName);
		//	ScriptLogger.writeInfo("source method type -> " + srcType);
		//	ScriptLogger.writeInfo("source value -> " + value);

			if (value != null) {

				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3);
				}
				else {
					methodName = methodName.substring(2);
				}

				Method dstMethod = null;
				try {
					dstMethod = transaction.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = transaction.getClass().getMethod("is"+methodName);
					}catch(NoSuchMethodException eInner) {
						ScriptLogger.writeInfo("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				ScriptLogger.writeInfo("dest method name -> " + dstMethod);
				ScriptLogger.writeInfo("dest method type -> " + dstType);

				if(srcType.equals(Date.class)) {
					String date = EntityUtil.convertToDateString((Date) value);
					Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(transaction, date);
				}
				else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(transaction, (Boolean) value);
					}
					else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(transaction,  tempVal);
					}
				}
				else {
					Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(transaction,  (String) value);
				}
			}
		}
	}
}
