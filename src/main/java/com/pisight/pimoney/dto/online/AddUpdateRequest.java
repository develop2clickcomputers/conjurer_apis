package com.pisight.pimoney.dto.online;

import com.fasterxml.jackson.databind.JsonNode;
import com.pisight.pimoney.util.ScriptLogger;


public class AddUpdateRequest {
	
	public static final String DATA_FLOW_PIMONEY = "pimoney";
	
	public static final String DATA_FLOW_XML = "gx";
	
	public static final String DATA_FLOW_RAW = "raw";
	
	private String institutionCode = null;
	
	private String userId = null;
	
	private JsonNode fields = null;
	
	private String requestCode = null;
	
	private String process = null;
	
	private String trackerId = null;
	
	private String accountId = null;
	
	private String tag = null;
	
	private String dataFlow = DATA_FLOW_PIMONEY;
	
	/**
	 * @return the institutionCode
	 */
	public String getInstitutionCode() {
		return institutionCode;
	}

	/**
	 * @param institutionCode the institutionCode to set
	 */
	public void setInstitutionCode(String institutionCode) {
		ScriptLogger.writeInfo("setInst :: " + institutionCode);
		this.institutionCode = institutionCode;
	}

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
		ScriptLogger.writeInfo("set user");
		this.userId = userId;
	}

	/**
	 * @return the fields
	 */
	public JsonNode getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(JsonNode fields) {
		this.fields = fields;
	}

	/**
	 * @return the requestCode
	 */
	public String getRequestCode() {
		return requestCode;
	}

	/**
	 * @param requestCode the requestCode to set
	 */
	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}

	/**
	 * @return the trackerId
	 */
	public String getTrackerId() {
		return trackerId;
	}

	/**
	 * @param trackerId the trackerId to set
	 */
	public void setTrackerId(String trackerId) {
		this.trackerId = trackerId;
	}

	/**
	 * @return the dataFlow
	 */
	public String getDataFlow() {
		return dataFlow;
	}

	/**
	 * @param dataFlow the dataFlow to set
	 */
	public void setDataFlow(String dataFlow) {
		this.dataFlow = dataFlow;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
