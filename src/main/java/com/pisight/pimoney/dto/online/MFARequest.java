package com.pisight.pimoney.dto.online;

public class MFARequest {

	private String institutionCode = null;

	private String pitrackerId = null;
	
	private boolean mfaRequired = false;

	private MFAInfo mfaInfo = null;
	
	private boolean mfaUser = false;

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
	

	public boolean isMfaRequired() {
		return mfaRequired;
	}

	public void setMfaRequired(boolean mfaRequired) {
		this.mfaRequired = mfaRequired;
	}

	/**
	 * @return the mfaInfo
	 */
	public MFAInfo getMfaInfo() {
		return mfaInfo;
	}

	/**
	 * @param mfaInfo the mfaInfo to set
	 */
	public void setMfaInfo(MFAInfo mfaInfo) {
		this.mfaInfo = mfaInfo;
	}

	/**
	 * @return the mfaUser
	 */
	public boolean isMfaUser() {
		return mfaUser;
	}

	/**
	 * @param mfaUser the mfaUser to set
	 */
	public void setMfaUser(boolean mfaUser) {
		this.mfaUser = mfaUser;
	}
	
	
	
	
	
}
