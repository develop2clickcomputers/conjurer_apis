package com.pisight.pimoney.dto;

import java.util.Date;
import java.util.UUID;

public class PropertyRequest {

	String userId = null;
	
	UUID propertyId = null;

	String name = null;
	
	String propertyType = null;
	
	Date purchaseDate = null;
	
	String ownership = null;
	
	String purpose = null;

	String currency = null;
	
	String amount = null;
	
	String marketValue = null;
	
	String loanAccountNumber = null;
	
	String loanOutstandingValue = null;
	
	String loanResidualTerms = null;
	
	String interestType = null;
	
	String interestRate = null;
	
	String resetFrequency = null;
	
	Date lastResetDate = null;	
	
	String address = null;
	
	String remarks = null;
	
	boolean loanFlag = false;
	
	UUID loanId = null;

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
	 * @return the propertyId
	 */
	public UUID getPropertyId() {
		return propertyId;
	}

	/**
	 * @param propertyId the propertyId to set
	 */
	public void setPropertyId(UUID propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the propertyType
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * @param propertyType the propertyType to set
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return the ownership
	 */
	public String getOwnership() {
		return ownership;
	}

	/**
	 * @param ownership the ownership to set
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	 * @return the marketValue
	 */
	public String getMarketValue() {
		return marketValue;
	}

	/**
	 * @param marketValue the marketValue to set
	 */
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	/**
	 * @return the loanOutstandingValue
	 */
	public String getLoanOutstandingValue() {
		return loanOutstandingValue;
	}

	/**
	 * @param loanOutstandingValue the loanOutstandingValue to set
	 */
	public void setLoanOutstandingValue(String loanOutstandingValue) {
		this.loanOutstandingValue = loanOutstandingValue;
	}

	/**
	 * @return the loanResidualTerms
	 */
	public String getLoanResidualTerms() {
		return loanResidualTerms;
	}

	/**
	 * @param loanResidualTerms the loanResidualTerms to set
	 */
	public void setLoanResidualTerms(String loanResidualTerms) {
		this.loanResidualTerms = loanResidualTerms;
	}

	/**
	 * @return the interestType
	 */
	public String getInterestType() {
		return interestType;
	}

	/**
	 * @param interestType the interestType to set
	 */
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	/**
	 * @return the interestRate
	 */
	public String getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 */
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * @return the resetFrequency
	 */
	public String getResetFrequency() {
		return resetFrequency;
	}

	/**
	 * @param resetFrequency the resetFrequency to set
	 */
	public void setResetFrequency(String resetFrequency) {
		this.resetFrequency = resetFrequency;
	}

	/**
	 * @return the lastResetDate
	 */
	public Date getLastResetDate() {
		return lastResetDate;
	}

	/**
	 * @param lastResetDate the lastResetDate to set
	 */
	public void setLastResetDate(Date lastResetDate) {
		this.lastResetDate = lastResetDate;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the loanFlag
	 */
	public boolean isLoanFlag() {
		return loanFlag;
	}

	/**
	 * @param loanFlag the loanFlag to set
	 */
	public void setLoanFlag(boolean loanFlag) {
		this.loanFlag = loanFlag;
	}

	/**
	 * @return the loanAccountNumber
	 */
	public String getLoanAccountNumber() {
		return loanAccountNumber;
	}

	/**
	 * @param loanAccountNumber the loanAccountNumber to set
	 */
	public void setLoanAccountNumber(String loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}

	/**
	 * @return the loanId
	 */
	public UUID getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(UUID loanId) {
		this.loanId = loanId;
	}
	
}
