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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="statement_repository")
@JsonIgnoreProperties({"createdAt", "updatedAt", "user",
"institution", "investmentAccounts"})
public class StatementRepositoryEntity extends RepositoryBase implements Serializable{
	
	private static final long serialVersionUID = 914486313842341813L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	
	@Column(name="statement_date")
	private Date statementDate = null;
	
	@Column(name="statement_type")
	private String statementType = null;
	
	@Column(name="file_blob")
	private byte[] bytecode = null;
	
	@Column(name="statement_name")
	private String statementName = null;
	
	@Column(name="xml_path")
	private String xmlPath = null;
	
	@Column(name="group_id")
	private String groupId = null;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = null;
	
	@ManyToOne
	@JoinColumn(name = "institution_id")
	private ManualInstitution institution = null;
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inv_account_statement", joinColumns = @JoinColumn(name = "statement_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
	private Set<InvestmentAccountEntity> investmentAccounts = new HashSet<InvestmentAccountEntity>();
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bank_account_statement", joinColumns = @JoinColumn(name = "statement_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
	private Set<BankAccountEntity> bankAccounts = new HashSet<BankAccountEntity>();
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "card_account_statement", joinColumns = @JoinColumn(name = "statement_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
	private Set<CardAccountEntity> cardAccounts = new HashSet<CardAccountEntity>();
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "loan_account_statement", joinColumns = @JoinColumn(name = "statement_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
	private Set<LoanAccountEntity> loanAccounts = new HashSet<LoanAccountEntity>();
	
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "fd_account_statement", joinColumns = @JoinColumn(name = "statement_id", referencedColumnName = "id"), 
    inverseJoinColumns = @JoinColumn(name = "account_id", referencedColumnName = "id"))
	private Set<FixedDepositAccountEntity> fdAccounts = new HashSet<FixedDepositAccountEntity>();
	
	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	protected Date updatedAt;
	
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
	 * @return the statementDate
	 */
	@Override
	public Date getStatementDate() {
		return statementDate;
	}

	/**
	 * @param statementDate the statementDate to set
	 */
	@Override
	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	/**
	 * @return the statementType
	 */
	@Override
	public String getStatementType() {
		return statementType;
	}

	/**
	 * @param statementType the statementType to set
	 */
	@Override
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	/**
	 * @return the bytecode
	 */
	@Override
	public byte[] getBytecode() {
		return bytecode;
	}

	/**
	 * @param docByte the bytecode to set
	 */
	@Override
	public void setBytecode(byte[] docByte) {
		this.bytecode = docByte;
	}

	/**
	 * @return the statementName
	 */
	@Override
	public String getStatementName() {
		return statementName;
	}

	/**
	 * @param statementName the statementName to set
	 */
	@Override
	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}

	/**
	 * @return the xmlPath
	 */
	public String getXmlPath() {
		return xmlPath;
	}

	/**
	 * @param xmlPath the xmlPath to set
	 */
	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
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
	 * @return the institution
	 */
	@Override
	public ManualInstitution getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	@Override
	public void setInstitution(ManualInstitution institution) {
		this.institution = institution;
	}

	/**
	 * @return the investmentAccounts
	 */
	public Set<InvestmentAccountEntity> getInvestmentAccounts() {
		return investmentAccounts;
	}

	/**
	 * @param accounts the investmentAccounts to set
	 */
	public void setInvestmentAccounts(Set<InvestmentAccountEntity> accounts) {
		this.investmentAccounts = accounts;
	}
	

	/**
	 * @return the bankAccounts
	 */
	public Set<BankAccountEntity> getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * @param bankAccounts the bankAccounts to set
	 */
	public void setBankAccounts(Set<BankAccountEntity> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * @return the cardAccounts
	 */
	public Set<CardAccountEntity> getCardAccounts() {
		return cardAccounts;
	}

	/**
	 * @param cardAccounts the cardAccounts to set
	 */
	public void setCardAccounts(Set<CardAccountEntity> cardAccounts) {
		this.cardAccounts = cardAccounts;
	}

	/**
	 * @return the loanAccounts
	 */
	public Set<LoanAccountEntity> getLoanAccounts() {
		return loanAccounts;
	}

	/**
	 * @param loanAccounts the loanAccounts to set
	 */
	public void setLoanAccounts(Set<LoanAccountEntity> loanAccounts) {
		this.loanAccounts = loanAccounts;
	}
	
	/**
	 * @return the fdAccounts
	 */
	public Set<FixedDepositAccountEntity> getFdAccounts() {
		return fdAccounts;
	}

	/**
	 * @param fdAccounts the fdAccounts to set
	 */
	public void setFdAccounts(Set<FixedDepositAccountEntity> fdAccounts) {
		this.fdAccounts = fdAccounts;
	}

	public Set<AccountBaseEntity> getAllAccounts(){
		Set<AccountBaseEntity> accounts = new HashSet<AccountBaseEntity>();
		
		accounts.addAll(bankAccounts);
		accounts.addAll(cardAccounts);
		accounts.addAll(loanAccounts);
		accounts.addAll(fdAccounts);
		accounts.addAll(investmentAccounts);
		
		return accounts;
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
