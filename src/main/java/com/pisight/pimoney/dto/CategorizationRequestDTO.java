package com.pisight.pimoney.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategorizationRequestDTO {
	
	private String userID;
	
	private String accountType;
	
	private List<TransactionCategoryDTO> transactions;

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userId the userID to set
	 */
	public void setUserID(String userId) {
		this.userID = userId;
	}

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
	 * @return the transactions
	 */
	public List<TransactionCategoryDTO> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<TransactionCategoryDTO> transactions) {
		this.transactions = transactions;
	}
}
