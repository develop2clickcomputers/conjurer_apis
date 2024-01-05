package com.pisight.pimoney.dto;

import com.pisight.pimoney.constants.Constants;

public class UpdateInvestmentRequest {
	
	private String userId = null;
	
	private AssetEditFields assetFields = null;
	
	private TransactionEditFields transactionFields = null;
	
	private SecurityMasterEditFields securityMasterFields = null;
	
	String flow = Constants.FLOW_PIMONEY;
	
	String fileRepoId = null;
	
	String tag = null;
	
	String uid = null;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the assetFields
	 */
	public AssetEditFields getAssetFields() {
		return assetFields;
	}

	/**
	 * @param assetFields the assetFields to set
	 */
	public void setAssetFields(AssetEditFields assetFields) {
		this.assetFields = assetFields;
	}

	/**
	 * @return the transactionFields
	 */
	public TransactionEditFields getTransactionFields() {
		return transactionFields;
	}

	/**
	 * @param transactionFields the transactionFields to set
	 */
	public void setTransactionFields(TransactionEditFields transactionFields) {
		this.transactionFields = transactionFields;
	}

	public SecurityMasterEditFields getSecurityMasterFields() {
		return securityMasterFields;
	}

	public void setSecurityMasterFields(SecurityMasterEditFields masterFields) {
		this.securityMasterFields = masterFields;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}


	public String getFileRepoId() {
		return fileRepoId;
	}


	public void setFileRepoId(String fileRepoId) {
		this.fileRepoId = fileRepoId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
