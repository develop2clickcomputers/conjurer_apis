package com.pisight.pimoney.dto;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

public class DashboardRequest {

	String userId = null;
	
	String uid = null;
	
	String preferredCurrency = null;
	
	UUID accountId = null;
	
	String tag = null;
	
	String trackerId = null;
	
	String stmtGroupId = null;
	
	List<JSONObject> accountDetails = null;

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
	 * @return the preferredCurrency
	 */
	public String getPreferredCurrency() {
		return preferredCurrency;
	}

	/**
	 * @param preferredCurrency the preferredCurrency to set
	 */
	public void setPreferredCurrency(String preferredCurrency) {
		this.preferredCurrency = preferredCurrency;
	}

	/**
	 * @return the accountId
	 */
	public UUID getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(UUID accountId) {
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

	/**
	 * @return the accountDetails
	 */
	public List<JSONObject> getAccountDetails() {
		return accountDetails;
	}

	/**
	 * @param accountDetails the accountDetails to set
	 */
	public void setAccountDetails(List<JSONObject> accountDetails) {
		this.accountDetails = accountDetails;
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
	 * @return the stmtGroupId
	 */
	public String getStmtGroupId() {
		return stmtGroupId;
	}

	/**
	 * @param stmtGroupId the stmtGroupId to set
	 */
	public void setStmtGroupId(String stmtGroupId) {
		this.stmtGroupId = stmtGroupId;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

}
