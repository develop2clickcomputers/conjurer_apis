package com.pisight.pimoney.repository.entities;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({})
public abstract class TransactionBaseEntity {
	
	private UUID id;

	private Date createdAt;

	private Date updatedAt;

	private boolean status;

	private boolean confirmed;

	private String fingerprint = null;

	private String transactionHash = null;
	
	private String institutionName;
	
	private Category categoryObj;
	
	private SubCategory subCategoryObj;
	
	protected String convertedAmount;
	
	private String merchantName;


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
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the fingerprint
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param fingerprint the fingerprint to set
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	/**
	 * @return the transactionHash
	 */
	public String getTransactionHash() {
		return transactionHash;
	}

	/**
	 * @param transactionHash the transactionHash to set
	 */
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	
	/**
	 * @return the institutionName
	 */
	public String getInstitutionName() {
		if(StringUtils.isEmpty(institutionName) && getAccount() != null) {
			institutionName = getAccount().getInstitutionName();
		}
		return institutionName;
	}

	/**
	 * @param institutionName the institutionName to set
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * @return the categoryObj
	 */
	public Category getCategoryObj() {
		return categoryObj;
	}

	public void setCategoryObj(Category category) {
		this.categoryObj = category;
	}

	public SubCategory getSubCategoryObj() {
		return subCategoryObj;
	}

	public void setSubCategoryObj(SubCategory subCategory) {
		this.subCategoryObj = subCategory;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchant) {
		this.merchantName = merchant;
	}
	
	public String getCategory() {
		if(getCategoryObj() != null) {
			return getCategoryObj().getName();
		}
		
		return null;
	}
	
	public String getSubcategory() {
		if(getSubCategoryObj() != null) {
			return getSubCategoryObj().getName();
		}
		
		return null;
	}
	
	public String getCategoryImageUrl() {
		if(getCategoryObj() != null) {
			return getCategoryObj().getUrl();
		}
		
		return null;
	}
	
	

	public void setCategoryImageUrl(String categoryImageUrl) {
	}

	public abstract AccountBaseEntity getAccount();

	public abstract String getTag();

	public abstract String getDescription();

	public abstract void setTag(String tag);
	
	public abstract String getAmount();
	
	public abstract String getCurrency();
	
	public abstract Date getTransDate();

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

	/**
	 * @return the statementId
	 */
	public abstract String getStatementId();

	/**
	 * @param statementId the statementId to set
	 */
	public abstract void setStatementId(String statementId);		
	
}
