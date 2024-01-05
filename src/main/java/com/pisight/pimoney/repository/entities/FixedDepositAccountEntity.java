package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pisight.pimoney.constants.Constants;

@Entity
@Table(name="fixed_deposit_account")
/*@JsonIgnoreProperties({"user", "accountHolder", "branch", "bankId", "accountHash", "fingerprint",
	"createdAt","updatedAt", "manualInstitution", "onlineInstitution", "statements"})*/
@JsonIgnoreProperties({"user", "manualInstitution", "onlineInstitution", "statements"})
public class FixedDepositAccountEntity extends AccountBaseEntity  implements Serializable {

	private static final long serialVersionUID = -7463165148257313991L;

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

	@Column(name = "principle_amount")
	private String principleAmount = null;

	@Column(name = "maturity_amount")
	private String maturityAmount = null;

	@Column(name = "start_date")
	private String startDate = null;

	@Column(name = "maturity_date")
	private String maturityDate = null;

	@Column(name = "interest_rate")
	private String interestRate = null;

	@Column(name = "interest_payout")
	private String interestPayout = null;

	@Column(name = "interest_computation_frequency")
	private String interestComputationFrequency  = null;
	
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
	
	@ManyToMany(mappedBy = "fdAccounts")
	private Set<StatementRepositoryEntity> statements = new HashSet<StatementRepositoryEntity>();

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
	 * @return the principleAmount
	 */
	public String getPrincipleAmount() {
		return principleAmount;
	}

	/**
	 * @param principleAmount the principleAmount to set
	 */
	public void setPrincipleAmount(String principleAmount) {
		this.principleAmount = principleAmount;
	}

	/**
	 * @return the maturityAmount
	 */
	public String getMaturityAmount() {
		return maturityAmount;
	}

	/**
	 * @param maturityAmount the maturityAmount to set
	 */
	public void setMaturityAmount(String maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the maturityDate
	 */
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
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
	 * @return the interestPayout
	 */
	public String getInterestPayout() {
		return interestPayout;
	}

	/**
	 * @param interestPayout the interestPayout to set
	 */
	public void setInterestPayout(String interestPayout) {
		this.interestPayout = interestPayout;
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

	@Override
	public String getTag() {
		return Constants.TAG_FIXED_DEPOSIT;
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
	
}
