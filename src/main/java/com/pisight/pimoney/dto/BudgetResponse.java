package com.pisight.pimoney.dto;

import java.util.List;
import java.util.UUID;

import com.pisight.pimoney.repository.entities.TransactionBaseEntity;

public class BudgetResponse {
	
	private UUID id = null;
	
	private String category = null;
	
	private String currency = null;
	
	private String imageUrl = null;
	
	private String amount = null;
	
	private String convertedAmount = null;

	private String spentAmount = null;
	
	private String remainingAmount = null;

	private List<TransactionBaseEntity> transactions = null;
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the spentAmount
	 */
	public String getSpentAmount() {
		return spentAmount;
	}

	/**
	 * @param spentAmount the spentAmount to set
	 */
	public void setSpentAmount(String spentAmount) {
		this.spentAmount = spentAmount;
	}

	/**
	 * @return the remainingAmount
	 */
	public String getRemainingAmount() {
		return remainingAmount;
	}

	/**
	 * @param remainingAmount the remainingAmount to set
	 */
	public void setRemainingAmount(String remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	/**
	 * @return the transactions
	 */
	public List<TransactionBaseEntity> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<TransactionBaseEntity> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the convertedAmount
	 */
	public String getConvertedAmount() {
		return convertedAmount;
	}

	/**
	 * @param convertedAmount the convertedAmount to set
	 */
	public void setConvertedAmount(String convertedAmount) {
		this.convertedAmount = convertedAmount;
	}
	
}
