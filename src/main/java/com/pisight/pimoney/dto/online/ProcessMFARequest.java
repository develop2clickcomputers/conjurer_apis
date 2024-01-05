package com.pisight.pimoney.dto.online;

import java.util.HashMap;

public class ProcessMFARequest {
	
	private String institutionCode = null;
	
	private String userId = null;
	
	private String trackerId = null;
	
	private HashMap<String, String> mfaValueMap = new HashMap<String, String>();

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
	 * @return the mfaValueMap
	 */
	public HashMap<String, String> getMfaValueMap() {
		return mfaValueMap;
	}

	/**
	 * @param mfaValueMap the mfaValueMap to set
	 */
	public void setMfaValueMap(HashMap<String, String> mfaValueMap) {
		this.mfaValueMap = mfaValueMap;
	}
	
	

}
