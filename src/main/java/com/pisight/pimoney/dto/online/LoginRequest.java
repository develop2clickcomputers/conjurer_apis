package com.pisight.pimoney.dto.online;

public class LoginRequest {
	
	String userId = null;
	
	String authToken = null;
	
	String platform = null;

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
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String loginToken) {
		this.authToken = loginToken;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	

}
