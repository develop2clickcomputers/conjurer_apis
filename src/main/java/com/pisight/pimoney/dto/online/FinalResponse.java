package com.pisight.pimoney.dto.online;

import java.util.HashMap;

public class FinalResponse {
	
	private String status = null;
	private int errorCode = 0;
	
	private String message = null;
	
	private String institutionCode = null;
	
	private String pitrackerId = null;
	
	private HashMap<String, ContainerResponse> accountContainer = new HashMap<String, ContainerResponse>();

	
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
	 * @return the pitrackerId
	 */
	public String getPitrackerId() {
		return pitrackerId;
	}

	/**
	 * @param pitrackerId the pitrackerId to set
	 */
	public void setPitrackerId(String pitrackerId) {
		this.pitrackerId = pitrackerId;
	}

	/**
	 * @return the accountContainer
	 */
	public HashMap<String, ContainerResponse> getAccountContainer() {
		return accountContainer;
	}

	/**
	 * @param accountContainer the accountContainer to set
	 */
	public void setAccountContainer(HashMap<String, ContainerResponse> accountContainer) {
		this.accountContainer = accountContainer;
	}

}
