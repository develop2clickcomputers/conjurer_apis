package com.pisight.pimoney.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.pisight.pimoney.dto.SecurityMasterEditFields;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.SecurityMaster;

public class SecurityMasterUtil {

	public static List<SecurityMaster> createSecurityMaster(InvestmentAccountEntity entity){

		List<SecurityMaster> result = new ArrayList<SecurityMaster>();

		List<SecurityMaster> masterList = entity.getScxList();

		List<HoldingAssetEntity> assets = entity.getAssets();

		List<InvestmentTransactionEntity> txns = entity.getTransactions();

		HashSet<String> masterSet = new HashSet<String>();

		if(masterList != null) {
			for(SecurityMaster master: masterList) {
				masterSet.add(master.getFingerprint());
			}
		}

		result.addAll(createSecuritymasterFromAssets(assets, masterSet));
		result.addAll(createSecurityMasterFromTransactions(txns, masterSet));
		return result;
	}

	private static List<SecurityMaster> createSecuritymasterFromAssets(List<HoldingAssetEntity> assets, HashSet<String> masterSet){

		List<SecurityMaster> result = new ArrayList<SecurityMaster>();

		for(HoldingAssetEntity asset: assets) {

			String raw = asset.getHoldingAssetSecurityId() + asset.getHoldingAssetCurrency() + asset.getHoldingAssetDescription() +
					asset.getHoldingAssetIssuer() + asset.getHoldingAssetStartDate() + asset.getHoldingAssetMaturityDate() +
					asset.getSecurityType() + asset.getHoldingAssetCoupon();
			
			String fingerprint = BasicEncodeDecodeUtil.encodeString(raw);
		
			if(masterSet.contains(fingerprint)) {
				ScriptLogger.writeInfo("Finger Print Matched for Asset.....");
				continue;
			}else {
				masterSet.add(fingerprint);
			}

			SecurityMaster master = new SecurityMaster();
			master.setAccount(asset.getAccount());
			master.setSecurityId(asset.getHoldingAssetSecurityId());
			master.setCurrency(asset.getHoldingAssetCurrency());
			master.setDescription(asset.getHoldingAssetDescription());
			master.setIssuer(asset.getHoldingAssetIssuer());
			master.setStartDate(asset.getHoldingAssetStartDate());
			master.setMaturityDate(asset.getHoldingAssetMaturityDate());
			master.setSecurityType(asset.getSecurityType());
			master.setCoupon(asset.getHoldingAssetCoupon());
			master.setFingerprint(fingerprint);
			result.add(master);
		}
		return result;
	}


	private static List<SecurityMaster> createSecurityMasterFromTransactions(List<InvestmentTransactionEntity> txns, HashSet<String> masterSet){

		List<SecurityMaster> result = new ArrayList<SecurityMaster>();

		for(InvestmentTransactionEntity txn: txns) {

			String raw = txn.getSecurityId()+txn.getCurrency()+txn.getDescription()+
					txn.getAssetIssuer()+txn.getStartDate()+txn.getMaturityDate()+
					txn.getSecurityType()+txn.getCoupon();
			String fingerprint = BasicEncodeDecodeUtil.encodeString(raw);
			
			if(masterSet.contains(fingerprint)) {
				ScriptLogger.writeInfo("Finger Print Matched for Transaction.....");
				continue;
			}

			SecurityMaster master = new SecurityMaster();
			master.setAccount(txn.getAccount());
			master.setSecurityId(txn.getSecurityId());
			master.setCurrency(txn.getCurrency());
			master.setDescription(txn.getDescription());
			master.setIssuer(txn.getAssetIssuer());
			master.setStartDate(txn.getStartDate());
			master.setMaturityDate(txn.getMaturityDate());
			master.setSecurityType(txn.getSecurityType());
			master.setCoupon(txn.getCoupon());
			master.setFingerprint(fingerprint);
			result.add(master);

		}

		return result;
	}

	public static void updateSecurityMaster(SecurityMaster master, SecurityMasterEditFields masterNew) {
		
		if(master == null || masterNew == null) {
			return;
		}
		
		master.setSecurityId(masterNew.getSecurityId());
//		master.setDescription(masterNew.getDescription()); // currently description is not a editable field
		master.setIssuer(masterNew.getIssuer());
		master.setStartDate(masterNew.getStartDate());
		master.setMaturityDate(masterNew.getMaturityDate());
		master.setSecurityType(masterNew.getSecurityType());
		master.setCoupon(masterNew.getCoupon());
		
	}

}
