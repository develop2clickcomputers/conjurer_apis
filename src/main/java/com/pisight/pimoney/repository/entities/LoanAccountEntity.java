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
@Table(name="loan_account")
/*@JsonIgnoreProperties({"user", "accountHolder", "branch", "bankId", "accountHash", "fingerprint",
	"createdAt","updatedAt", "manualInstitution", "onlineInstitution", "statements"})*/
@JsonIgnoreProperties({"user", "manualInstitution", "onlineInstitution", "statements"})
public class LoanAccountEntity extends AccountBaseEntity implements Serializable {

	private static final long serialVersionUID = -7143058028430202919L;

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

	@Column(name = "principal_amount")
	private String principalAmount = null;

	@Column(name = "tenure_months")
	private String tenureMonths = null;

	@Column(name = "loan_emi")
	private String loanEMI = null;

	@Column(name = "balance")
	private String balance = null;

	@Column(name = "interest_rate")
	private String interestRate = null;

	@Column(name = "start_date")
	private Date startDate = null;

	@Column(name = "maturity_date")
	private Date maturityDate = null;

	@Column(name = "interest_payout")
	private String interestPayout = null;

	@Column(name = "interest_type")
	private String interestType = null;

	@Column(name = "interest_computation_freq")
	private String interestComputationFrequency = null;

	@Column(name = "account_classification")
	private String accountClassification = null;

	@Column(name = "due_date")
	private Date dueDate = null;

	@Column(name = "last_payment_amount")
	private String lastPaymentAmount = null;

	@Column(name = "last_payment_date")
	private Date lastPaymentDate = null;

	@Column(name = "min_amount_due")
	private String minAmountDue = null;

	@Column(name = "nickname")
	private String nickname = null;

	@Column(name = "account_type")
	private String accountType = null;

	@Column(name = "escrow_balance")
	private String escrowBalance = null;

	@Column(name = "recurring_payment")
	private String recurringPayment = null;

	@Column(name = "frequency")
	private String frequency = null;
	
	@Column(name = "statement_date")
	private Date billDate = null;
	
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

	// New Field 
	@Column(name = "loan_residual_terms")
	private String loanResidualTerms = null;
	
	@Column(name = "reset_frequency")
	private String resetFrequency = null;
	
	@Column(name = "last_reset_date")
	private Date lastResetDate = null;
	
	//End New Fields
	
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

	@ManyToMany(mappedBy = "loanAccounts")
	private Set<StatementRepositoryEntity> statements = new HashSet<StatementRepositoryEntity>();

	@OneToMany(mappedBy="account",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<LoanTransactionEntity> transactions = new ArrayList<LoanTransactionEntity>();
	
	@OneToMany(mappedBy="loan",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Property> properties = new ArrayList<Property>();

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
	 * @return the principalAmount
	 */
	public String getPrincipalAmount() {
		return principalAmount;
	}

	/**
	 * @param principalAmount the principalAmount to set
	 */
	public void setPrincipalAmount(String principalAmount) {
		this.principalAmount = AccountUtil.formatAmount(principalAmount);
	}

	/**
	 * @return the tenureMonths
	 */
	public String getTenureMonths() {
		return tenureMonths;
	}

	/**
	 * @param tenureMonths the tenureMonths to set
	 */
	public void setTenureMonths(String tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

	/**
	 * @return the loanEMI
	 */
	public String getLoanEMI() {
		return loanEMI;
	}

	/**
	 * @param loanEMI the loanEMI to set
	 */
	public void setLoanEMI(String loanEMI) {
		this.loanEMI = loanEMI;
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
		this.balance = AccountUtil.formatAmount(balance);
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
		this.interestRate = AccountUtil.formatAmount(interestRate);
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the interestPayout
	 */
	public String getInterestPayout() {
		return interestPayout;
	}

	/**
	 * @param interestPayout the interestPayout to set
	 */
	public void setInterestPayout(String interestPayout) {
		this.interestPayout = AccountUtil.formatAmount(interestPayout);
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
	 * @return the interestComputationFrequency
	 */
	public String getInterestComputationFrequency() {
		return interestComputationFrequency;
	}

	/**
	 * @param interestComputationFrequency the interestComputationFrequency to set
	 */
	public void setInterestComputationFrequency(String interestComputationFrequency) {
		this.interestComputationFrequency = interestComputationFrequency;
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
	 * @return the lastPaymentAmount
	 */
	public String getLastPaymentAmount() {
		return lastPaymentAmount;
	}

	/**
	 * @param lastPaymentAmount the lastPaymentAmount to set
	 */
	public void setLastPaymentAmount(String lastPaymentAmount) {
		this.lastPaymentAmount = AccountUtil.formatAmount(lastPaymentAmount);
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
	 * @return the minAmountDue
	 */
	public String getMinAmountDue() {
		return minAmountDue;
	}

	/**
	 * @param minAmountDue the minAmountDue to set
	 */
	public void setMinAmountDue(String minAmountDue) {
		this.minAmountDue = AccountUtil.formatAmount(minAmountDue);
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
	 * @return the escrowBalance
	 */
	public String getEscrowBalance() {
		return escrowBalance;
	}

	/**
	 * @param escrowBalance the escrowBalance to set
	 */
	public void setEscrowBalance(String escrowBalance) {
		this.escrowBalance = AccountUtil.formatAmount(escrowBalance);
	}

	/**
	 * @return the recurringPayment
	 */
	public String getRecurringPayment() {
		return recurringPayment;
	}

	/**
	 * @param recurringPayment the recurringPayment to set
	 */
	public void setRecurringPayment(String recurringPayment) {
		this.recurringPayment = AccountUtil.formatAmount(recurringPayment);
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
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
	public List<LoanTransactionEntity> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<LoanTransactionEntity> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return Constants.TAG_LOAN;
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
	
}
