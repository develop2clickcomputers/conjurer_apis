package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

import com.pisight.pimoney.constants.Constants;

public class PreviewActionRequest {
	
	String userId = null;
	
	String flow = Constants.FLOW_PIMONEY;
	
	String fileRepoId = null;
	
	List<PreviewAccount> accounts = new ArrayList<PreviewAccount>();
	
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
	 * @return the accounts
	 */
	public List<PreviewAccount> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<PreviewAccount> accounts) {
		this.accounts = accounts;
	}

	/**
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}

	/**
	 * @param flow the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}

	/**
	 * @return the fileRepoId
	 */
	public String getFileRepoId() {
		return fileRepoId;
	}

	/**
	 * @param fileRepoId the fileRepoId to set
	 */
	public void setFileRepoId(String fileRepoId) {
		this.fileRepoId = fileRepoId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}

