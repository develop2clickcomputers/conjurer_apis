package com.pisight.pimoney.dto;

import java.util.Date;

/**
 * @author kumar
 *
 */
public class TransactionEditFields {
	
	private String id = null;
	
	private Date transactionDate = null;
	
	private Date transDate;
	
	private String assetCategory = null;
	
	private String assetQuantity = null;
	
	private String assetUnitCost = null;
	
	private String currency = null;
	
	private String amount = null;
	
	private String category;
	
	private String subcategory;
	
	private String merchantName;
	

	/**
	 * @return the transDate
	 */
	public Date getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
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
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the assetCategory
	 */
	public String getAssetCategory() {
		return assetCategory;
	}

	/**
	 * @param assetCategory the assetCategory to set
	 */
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	/**
	 * @return the assetQuantity
	 */
	public String getAssetQuantity() {
		return assetQuantity;
	}

	/**
	 * @param assetQuantity the assetQuantity to set
	 */
	public void setAssetQuantity(String assetQuantity) {
		this.assetQuantity = assetQuantity;
	}

	/**
	 * @return the assetUnitCost
	 */
	public String getAssetUnitCost() {
		return assetUnitCost;
	}

	/**
	 * @param assetUnitCost the assetUnitCost to set
	 */
	public void setAssetUnitCost(String assetUnitCost) {
		this.assetUnitCost = assetUnitCost;
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
	 * @return the subcategory
	 */
	public String getSubcategory() {
		return subcategory;
	}

	/**
	 * @param subCategory the subcategory to set
	 */
	public void setSubcategory(String subCategory) {
		this.subcategory = subCategory;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
}
