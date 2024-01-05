package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

public class PreviewAccount {
	
	String accountType = null;
	
	String id = null;
	
	List<PreviewId> transactions = new ArrayList<PreviewId>();
	
	List<PreviewId> assets = new ArrayList<PreviewId>();
	
	List<PreviewId> scxList = new ArrayList<PreviewId>();

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the transactions
	 */
	public List<PreviewId> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<PreviewId> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the assets
	 */
	public List<PreviewId> getAssets() {
		return assets;
	}

	/**
	 * @param assets the assets to set
	 */
	public void setAssets(List<PreviewId> assets) {
		this.assets = assets;
	}

	/**
	 * @return the scxList
	 */
	public List<PreviewId> getScxList() {
		return scxList;
	}

	/**
	 * @param scxList the scxList to set
	 */
	public void setScxList(List<PreviewId> scxList) {
		this.scxList = scxList;
	}
	
}
