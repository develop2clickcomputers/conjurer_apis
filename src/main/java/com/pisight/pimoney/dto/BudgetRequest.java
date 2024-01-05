package com.pisight.pimoney.dto;

import java.util.UUID;

public class BudgetRequest {

	private String userId = null;
	
	private UUID budgetId = null;
	
	private String category = null;
	
	private String currency = null;
	
	private String amount = null;
	
	private String month;
	
	private String year;
	
	private boolean thisMonth;

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
	 * @return the budgetId
	 */
	public UUID getBudgetId() {
		return budgetId;
	}

	/**
	 * @param budgetId the budgetId to set
	 */
	public void setBudgetId(UUID budgetId) {
		this.budgetId = budgetId;
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
	 * @return the thisMonth
	 */
	public boolean getThisMonth() {
		return thisMonth;
	}

	/**
	 * @param thisMonth the thisMonth to set
	 */
	public void setThisMonth(boolean thisMonth) {
		this.thisMonth = thisMonth;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the thisMonth
	 */
	public boolean isThisMonth() {
		return thisMonth;
	}
	
	
}
