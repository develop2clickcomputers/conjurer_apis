package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.models.InvestmentAccount;
import com.pisight.pimoney.models.InvestmentTransaction;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountHistoryEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;

public class InvestmentAccountEntityUtil {

	public static void copyToInvestmentAccountEntity(InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {

		if(account == null) {
			return;
		}
		entity.setAccountHolder(account.getAccountHolder());
		entity.setBranch(account.getBranch());
		entity.setCurrency(account.getCurrency());
		entity.setBankId(account.getBankId());
		entity.setFingerprint(account.getFingerprint());
		entity.setAccountName(account.getAccountName());
		entity.setSecondaryAccountHolder(account.getSecondaryAccountHolder());
		entity.setBalance(account.getBalance());
		entity.setAvailableBalance(account.getAvailableBalance());
		entity.setBillDate(WebUtil.convertToDate(account.getBillDate()));
		entity.setRelationshipManager(account.getRelationshipManager());
	}
	
	public static void copyToInvestmentAccountEntity(InvestmentAccountHistoryEntity account, InvestmentAccountEntity entity) throws ParseException {

		if(account == null) {
			return;
		}
		entity.setAccountHolder(account.getAccountHolder());
		entity.setBranch(account.getBranch());
		entity.setCurrency(account.getCurrency());
		entity.setBankId(account.getBankId());
		entity.setFingerprint(account.getFingerprint());
		entity.setAccountName(account.getAccountName());
		entity.setSecondaryAccountHolder(account.getSecondaryAccountHolder());
		entity.setBalance(account.getBalance());
		entity.setAvailableBalance(account.getAvailableBalance());
		entity.setBillDate(account.getBillDate());
		entity.setRelationshipManager(account.getRelationshipManager());
	}

	public static InvestmentAccountEntity toInvestmentAccountEntity(InvestmentAccount account) throws ParseException {

		InvestmentAccountEntity entity = new InvestmentAccountEntity();

		if(account == null) {
			return null;
		}

		copyToInvestmentAccountEntity(account, entity);
		entity.setAccountNumber(EntityUtil.maskString(account.getAccountNumber()));
		return entity;
	}


	public static InvestmentAccountEntity toInvestmentAccountEntityDC(InvestmentAccount account) throws ParseException {

		InvestmentAccountEntity entity = toInvestmentAccountEntity(account);

		List<HoldingAsset> assets = new ArrayList<>();
		List<InvestmentTransaction> txns = new ArrayList<>();
		if(account != null) {
			assets = account.getAssets();
			txns = account.getInvestmentTransactions();

			ScriptLogger.writeInfo("Total new Asset Size :: " + assets.size());
			ScriptLogger.writeInfo("Total new txnx size :: " + txns.size());
		}
		
		List<HoldingAssetEntity> assetEntities = new ArrayList<HoldingAssetEntity>();
		List<InvestmentTransactionEntity> txnEntities = new ArrayList<InvestmentTransactionEntity>();

		ScriptLogger.writeInfo("asset entities size before :: " + assetEntities.size() );
		ScriptLogger.writeInfo("txns entities size before :: " + txnEntities.size());

		for(HoldingAsset asset: assets) {
			HoldingAssetEntity assetEntity = AssetEntityUtil.toAssetEntity(asset);
			if(assetEntity != null) {
				assetEntities.add(assetEntity);
				assetEntity.setAccount(entity);
			}
		}

		for(InvestmentTransaction txn: txns) {
			InvestmentTransactionEntity txnEntity = InvestmentTransactionEntityUtil.toInvestmentTransactionEntity(txn);
			if(txnEntity != null) {
				txnEntities.add(txnEntity);
				txnEntity.setAccount(entity);
			}
		}

		ScriptLogger.writeInfo("asset entities size after :: " + assetEntities.size() );
		ScriptLogger.writeInfo("txns entities size after :: " + txnEntities.size());

		if(entity != null) {
			entity.setAssets(assetEntities);
			entity.setTransactions(txnEntities);
		}
		return entity;
	}

	@SuppressWarnings("rawtypes")
	public static void copyToInvestmentAccount(InvestmentAccountEntity entity, InvestmentAccount account) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo("test convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
				continue;
			}
			Object value = method.invoke(entity);

			ScriptLogger.writeInfo("source method name -> " + methodName);
			ScriptLogger.writeInfo("source method type -> " + srcType);
			ScriptLogger.writeInfo("source value -> " + value);

			if (value != null) {

				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3);
				}
				else {
					methodName = methodName.substring(2);
				}

				Method dstMethod = null;
				try {
					dstMethod = account.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = account.getClass().getMethod("is"+methodName);
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
					Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(account, date);
				}
				else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = account.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(account, (Boolean) value);
					}
					else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(account,  tempVal);
					}
				}
				else {
					Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(account,  (String) value);
				}
			}
		}
	}

}
