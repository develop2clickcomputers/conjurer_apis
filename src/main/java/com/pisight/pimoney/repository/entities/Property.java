package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="properties")
@JsonIgnoreProperties({ "createdAt", "updatedAt", "user"})
public class Property implements Serializable{

	private static final long serialVersionUID = -3705875662806838570L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	protected UUID id;

	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;
	
	@ManyToOne
	@JoinColumn(name="loan_id")
	protected LoanAccountEntity loan;
	
	@Column(name="name", nullable = false)
	private String name = null;

	@Column(name="ownership", nullable = false)
	private String ownership = null;
	
	@Column(name="type", nullable = false)
	private String type = null;
	
	@Column(name="purpose", nullable = false)
	private String purpose = null;
	
	@Column(name = "purchase_date")
	private Date purchaseDate = null;
	
	@Column(name="currency", nullable = false)
	private String currency = null;
	
	@Column(name = "amount", nullable = false)
	private String amount = null;
	
	@Column(name="market_value_currency")
	private String marketValueCurrency = null;
	
	@Column(name = "market_value")
	private String marketValue = null;
	
	@Column(name="loan_outstanding_currency")
	private String loanOutstandingCurrency = null;
	
	@Column(name = "loan_outstanding")
	private String loanOutstandingValue = null;
	
	@Column(name = "loan_residual_terms")
	private String loanResidualTerms = null;
	
	@Column(name = "interest_type")
	private String interestType = null;
	
	@Column(name = "reset_frequency")
	private String resetFrequency = null;
	
	@Column(name = "last_reset_date")
	private Date lastResetDate = null;
	
	@Column(name = "interest_rate")
	private String interestRate = null;
	
	@Column(name = "address")
	private String address = null;
	
	@Column(name = "remarks")
	private String remarks = null;
	
	@Transient
	protected String convertedBalance;
	
	@Column(name = "status", columnDefinition="bit(1) default 0")
	protected boolean status;
	
	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;

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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the marketValueCurrency
	 */
	public String getMarketValueCurrency() {
		return marketValueCurrency;
	}

	/**
	 * @param marketValueCurrency the marketValueCurrency to set
	 */
	public void setMarketValueCurrency(String marketValueCurrency) {
		this.marketValueCurrency = marketValueCurrency;
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
	 * @return the loanOutstandingCurrency
	 */
	public String getLoanOutstandingCurrency() {
		return loanOutstandingCurrency;
	}

	/**
	 * @param loanOutstandingCurrency the loanOutstandingCurrency to set
	 */
	public void setLoanOutstandingCurrency(String loanOutstandingCurrency) {
		this.loanOutstandingCurrency = loanOutstandingCurrency;
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
	 * @return the remark
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemarks(String remark) {
		this.remarks = remark;
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
	 * @return the convertedBalance
	 */
	public String getConvertedBalance() {
		return convertedBalance;
	}

	/**
	 * @param convertedBalance the convertedBalance to set
	 */
	public void setConvertedBalance(String convertedBalance) {
		this.convertedBalance = convertedBalance;
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
	 * @return the loan
	 */
	public LoanAccountEntity getLoan() {
		return loan;
	}

	/**
	 * @param loan the loan to set
	 */
	public void setLoan(LoanAccountEntity loan) {
		this.loan = loan;
	}
}
