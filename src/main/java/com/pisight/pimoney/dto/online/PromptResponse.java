package com.pisight.pimoney.dto.online;

import com.fasterxml.jackson.databind.JsonNode;

public class PromptResponse {

	private String institutionCode = null;

	private String status = null;

	private int errorCode = 0;

	private String message = null;

	private JsonNode fields = null;
	
	private int mfaLevel = 0;
	
	private boolean persistCredentials = true;





	/**
	 * @return the persistCredentials
	 */
	public boolean isPersistCredentials() {
		return persistCredentials;
	}

	/**
	 * @param persistCredentials the persistCredentials to set
	 */
	public void setPersistCredentials(boolean persistCredentials) {
		this.persistCredentials = persistCredentials;
	}

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
		this.institutionCode = institutionCode;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the mfaLevel
	 */
	public int getMfaLevel() {
		return mfaLevel;
	}

	/**
	 * @param mfaLevel the mfaLevel to set
	 */
	public void setMfaLevel(int mfaLevel) {
		this.mfaLevel = mfaLevel;
	}



}
