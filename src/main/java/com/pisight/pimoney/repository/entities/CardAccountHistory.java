package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name="card_account_history")

public class CardAccountHistory implements Serializable {

	private static final long serialVersionUID = 2227733894965444942L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	protected UUID id;

	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;

	@Column(name = "account_holder")
	protected String accountHolder = null;

	@Column(name = "branch")
	protected String branch = null;

	@Column(name = "currency")
	protected String currency = null;

	@Column(name = "bank_id")
	protected String bankId = null;

	@Column(name = "account_hash")
	protected String accountHash = null;

	@Column(name = "fingerprint")
	protected String fingerprint = null;

	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	protected Date updatedAt;

	@Column(name = "status", columnDefinition="bit(1) default 0")
	protected boolean status;

	@Column(name = "confirmed", columnDefinition="bit(1) default 0")
	protected boolean confirmed;

	@Column(name = "account_number")
	private String accountNumber = null;

	@Column(name = "account_name")
	private String accountName = null;

	@Column(name = "total_limit")
	private String totalLimit = null;

	@Column(name = "statement_date")
	private Date billDate = null;

	@Column(name = "due_date")
	private Date dueDate = null;

	@Column(name = "amount_due")
	private String amountDue = null;

	@Column(name = "min_amount_due")
	private String minAmountDue = "";

	@Column(name = "available_credit")
	private String availableCredit = "";

	@Column(name = "last_statement_balance")
	private String lastStatementBalance = "";

	@Column(name = "apr")
	private String apr = null;

	@Column(name = "available_cash")
	private String availableCash = null;

	@Column(name = "balance")
	private String balance = null;

	@Column(name = "account_classification")
	private String accountClassification = null;

	@Column(name = "last_payment_amount")
	private String lastPaymentAmount = null;

	@Column(name = "last_payment_date")
	private Date lastPaymentDate  = null;

	@Column(name = "nickname")
	private String nickname = null;

	@Column(name = "running_balance")
	private String runningBalance = null;

	@Column(name = "total_cash_limit")
	private String totalCashLimit = null;

	@Column(name = "account_type")
	private String accountType = null;

	@Column(name = "frequency")
	private String frequncy = null;

	@OneToOne(cascade = CascadeType.ALL)
	private RefreshInfo refreshInfo = null;
	
	@Column(name = "manually_added", columnDefinition="bit(1) default 0")
	private boolean manuallyAdded;

	@Column(name = "manual_institution_id")
	private String manualInstitution;

	@Column(name = "online_institution_id")
	private String onlineInstitution;

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
	 * @return the accountHolder
	 */
	public String getAccountHolder() {
		return accountHolder;
	}

	/**
	 * @param accountHolder the accountHolder to set
	 */
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
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
	 * @return the bankId
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the accountHash
	 */
	public String getAccountHash() {
		return accountHash;
	}

	/**
	 * @param accountHash the accountHash to set
	 */
	public void setAccountHash(String accountHash) {
		this.accountHash = accountHash;
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
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the totalLimit
	 */
	public String getTotalLimit() {
		return totalLimit;
	}

	/**
	 * @param totalLimit the totalLimit to set
	 */
	public void setTotalLimit(String totalLimit) {
		this.totalLimit = totalLimit;
	}

	/**
	 * @return the billDate
	 */
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the amountDue
	 */
	public String getAmountDue() {
		return amountDue;
	}

	/**
	 * @param amountDue the amountDue to set
	 */
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}

	/**
	 * @return the minAmountDue
	 */
	public String getMinAmountDue() {
		return minAmountDue;
	}

	/**
	 * @param minAmountDue the minAmountDue to set
	 */
	public void setMinAmountDue(String minAmountDue) {
		this.minAmountDue = minAmountDue;
	}

	/**
	 * @return the availableCredit
	 */
	public String getAvailableCredit() {
		return availableCredit;
	}

	/**
	 * @param availableCredit the availableCredit to set
	 */
	public void setAvailableCredit(String availableCredit) {
		this.availableCredit = availableCredit;
	}

	/**
	 * @return the lastStatementBalance
	 */
	public String getLastStatementBalance() {
		return lastStatementBalance;
	}

	/**
	 * @param lastStatementBalance the lastStatementBalance to set
	 */
	public void setLastStatementBalance(String lastStatementBalance) {
		this.lastStatementBalance = lastStatementBalance;
	}

	/**
	 * @return the apr
	 */
	public String getApr() {
		return apr;
	}

	/**
	 * @param apr the apr to set
	 */
	public void setApr(String apr) {
		this.apr = apr;
	}

	/**
	 * @return the availableCash
	 */
	public String getAvailableCash() {
		return availableCash;
	}

	/**
	 * @param availableCash the availableCash to set
	 */
	public void setAvailableCash(String availableCash) {
		this.availableCash = availableCash;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the accountClassification
	 */
	public String getAccountClassification() {
		return accountClassification;
	}

	/**
	 * @param accountClassification the accountClassification to set
	 */
	public void setAccountClassification(String accountClassification) {
		this.accountClassification = accountClassification;
	}

	/**
	 * @return the lastPaymentAmount
	 */
	public String getLastPaymentAmount() {
		return lastPaymentAmount;
	}

	/**
	 * @param lastPaymentAmount the lastPaymentAmount to set
	 */
	public void setLastPaymentAmount(String lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}

	/**
	 * @return the lastPaymentDate
	 */
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	/**
	 * @param lastPaymentDate the lastPaymentDate to set
	 */
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the runningBalance
	 */
	public String getRunningBalance() {
		return runningBalance;
	}

	/**
	 * @param runningBalance the runningBalance to set
	 */
	public void setRunningBalance(String runningBalance) {
		this.runningBalance = runningBalance;
	}

	/**
	 * @return the totalCashLimit
	 */
	public String getTotalCashLimit() {
		return totalCashLimit;
	}

	/**
	 * @param totalCashLimit the totalCashLimit to set
	 */
	public void setTotalCashLimit(String totalCashLimit) {
		this.totalCashLimit = totalCashLimit;
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
	 * @return the frequncy
	 */
	public String getFrequncy() {
		return frequncy;
	}

	/**
	 * @param frequncy the frequncy to set
	 */
	public void setFrequncy(String frequncy) {
		this.frequncy = frequncy;
	}

	/**
	 * @return the refreshInfo
	 */
	public RefreshInfo getRefreshInfo() {
		return refreshInfo;
	}

	/**
	 * @param refreshInfo the refreshInfo to set
	 */
	public void setRefreshInfo(RefreshInfo refreshInfo) {
		this.refreshInfo = refreshInfo;
	}

	/**
	 * @return the manuallyAdded
	 */
	public boolean isManuallyAdded() {
		return manuallyAdded;
	}

	/**
	 * @param manuallyAdded the manuallyAdded to set
	 */
	public void setManuallyAdded(boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}

	/**
	 * @return the manualInstitution
	 */
	public String getManualInstitution() {
		return manualInstitution;
	}

	/**
	 * @param manualInstitution the manualInstitution to set
	 */
	public void setManualInstitution(String manualInstitution) {
		this.manualInstitution = manualInstitution;
	}

	/**
	 * @return the onlineInstitution
	 */
	public String getOnlineInstitution() {
		return onlineInstitution;
	}

	/**
	 * @param onlineInstitution the onlineInstitution to set
	 */
	public void setOnlineInstitution(String onlineInstitution) {
		this.onlineInstitution = onlineInstitution;
	}

}
