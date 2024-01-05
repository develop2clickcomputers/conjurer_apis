package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.util.AccountUtil;

@Entity
@Table(name="bank_account")
/*@JsonIgnoreProperties({"user", "accountHolder", "branch", "bankId", "accountHash", "fingerprint",
	"createdAt","updatedAt", "manualInstitution", "onlineInstitution", "statements"})*/
@JsonIgnoreProperties({"user", "manualInstitution", "onlineInstitution", "statements"})
public class BankAccountEntity extends AccountBaseEntity implements Serializable {

	private static final long serialVersionUID = 5705817057827706194L;

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

	@Column(name = "account_balance")
	private String accountBalance = null;

	@Column(name = "statement_date")
	private Date billDate = null;

	@Column(name = "available_balance")
	private String availableBalance = null;

	@Column(name = "account_classification")
	private String accountClassification = null;

	@Column(name = "current_balance")
	private String currentBalance = null;

	@Column(name = "interest_rate")
	private String interestRate = null;

	@Column(name = "nickname")
	private String nickname = null;

	@Column(name = "account_type")
	private String accountType = null;

	@Column(name = "overdraft_limit")
	private String overdraftLimit = null;

	@OneToOne(cascade = CascadeType.ALL)
	private RefreshInfo refreshInfo = null;
	
	@Column(name = "manually_added", columnDefinition="bit(1) default 0")
	private boolean manuallyAdded;
	
	@ManyToOne
	@JoinColumn(name = "manual_institution_id")
	private ManualInstitution manualInstitution;
	
	@ManyToOne
	@JoinColumn(name = "online_institution_id")
	private OnlineInstitution onlineInstitution;
	
	@ManyToMany(mappedBy = "bankAccounts")
	private Set<StatementRepositoryEntity> statements = new HashSet<StatementRepositoryEntity>();
	
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<BankTransactionEntity> transactions = new ArrayList<BankTransactionEntity>();

	/**
	 * @return the id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	@Override
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the accountHolder
	 */
	@Override
	public String getAccountHolder() {
		return accountHolder;
	}

	/**
	 * @param accountHolder the accountHolder to set
	 */
	@Override
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	/**
	 * @return the branch
	 */
	@Override
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	@Override
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the currency
	 */
	@Override
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	@Override
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the bankId
	 */
	@Override
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	@Override
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the accountHash
	 */
	@Override
	public String getAccountHash() {
		return accountHash;
	}

	/**
	 * @param accountHash the accountHash to set
	 */
	@Override
	public void setAccountHash(String accountHash) {
		this.accountHash = accountHash;
	}

	/**
	 * @return the fingerprint
	 */
	@Override
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param fingerprint the fingerprint to set
	 */
	@Override
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	/**
	 * @return the createdAt
	 */
	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the status
	 */
	@Override
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	@Override
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the confirmed
	 */
	@Override
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	@Override
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the accountNumber
	 */
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountName
	 */
	@Override
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @param accountName the accountName to set
	 */
	@Override
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * @return the accountBalance
	 */
	public String getAccountBalance() {
		return accountBalance;
	}

	/**
	 * @param accountBalance the accountBalance to set
	 */
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = AccountUtil.formatAmount(accountBalance);
	}

	/**
	 * @return the billDate
	 */
	@Override
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate the billDate to set
	 */
	@Override
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return the availableBalance
	 */
	public String getAvailableBalance() {
		return availableBalance;
	}

	/**
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = AccountUtil.formatAmount(availableBalance);
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
	 * @return the currentBalance
	 */
	public String getCurrentBalance() {
		return currentBalance;
	}

	/**
	 * @param currentBalance the currentBalance to set
	 */
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = AccountUtil.formatAmount(currentBalance);
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
	 * @return the overdraftLimit
	 */
	public String getOverdraftLimit() {
		return overdraftLimit;
	}

	/**
	 * @param overdraftLimit the overdraftLimit to set
	 */
	public void setOverdraftLimit(String overdraftLimit) {
		this.overdraftLimit = AccountUtil.formatAmount(overdraftLimit);
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
	 * @return the manualInstitution
	 */
	@Override
	public ManualInstitution getManualInstitution() {
		return manualInstitution;
	}

	/**
	 * @param manualInstitution the manualInstitution to set
	 */
	@Override
	public void setManualInstitution(ManualInstitution manualInstitution) {
		this.manualInstitution = manualInstitution;
	}

	/**
	 * @return the onlineInstitution
	 */
	@Override
	public OnlineInstitution getOnlineInstitution() {
		return onlineInstitution;
	}

	/**
	 * @param onlineInstitution the onlineInstitution to set
	 */
	@Override
	public void setOnlineInstitution(OnlineInstitution onlineInstitution) {
		this.onlineInstitution = onlineInstitution;
	}

	/**
	 * @return the statements
	 */
	@Override
	public Set<StatementRepositoryEntity> getStatements() {
		return statements;
	}

	/**
	 * @param statements the statements to set
	 */
	@Override
	public void setStatements(Set<StatementRepositoryEntity> statements) {
		this.statements = statements;
	}

	/**
	 * @return the transactions
	 */
	public List<BankTransactionEntity> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<BankTransactionEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return Constants.TAG_BANK;
	}
	
	public void setTag(String tag) {
		
	}
	
	@Override
	public String getInstitutionName() {

		String name = "";
		if(manualInstitution != null) {
			name =  manualInstitution.getName();
		}
		else if(onlineInstitution != null) {
			name  = onlineInstitution.getName();
		}

		return name;

	}

	/**
	 * @return the manuallyAdded
	 */
	@Override
	public boolean isManuallyAdded() {
		return manuallyAdded;
	}

	/**
	 * @param manuallyAdded the manuallyAdded to set
	 */
	@Override
	public void setManuallyAdded(boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}
}
