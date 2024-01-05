package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = -5096268851495378740L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "createdAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updatedAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;

	@Column(name = "preferred_currency", nullable = false)
	private String preferredCurrency = "SGD";

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<InvestmentAccountEntity> investmentAccounts = new ArrayList<InvestmentAccountEntity>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<BankAccountEntity> bankAccounts = new ArrayList<BankAccountEntity>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<CardAccountEntity> cardAccounts = new ArrayList<CardAccountEntity>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<LoanAccountEntity>loanAccounts = new ArrayList<LoanAccountEntity>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<FixedDepositAccountEntity>fdAccounts = new ArrayList<FixedDepositAccountEntity>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<StatementRepositoryEntity> statements = new ArrayList<StatementRepositoryEntity>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<FileRepositoryEntity> files = new ArrayList<FileRepositoryEntity>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<UserInstituteDetail> onlineDetails = new ArrayList<UserInstituteDetail>();

	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Property> property = new ArrayList<Property>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Budget> budget = new ArrayList<Budget>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<Insurance> insurance = new ArrayList<Insurance>();
	
	@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<StatementParsingDetail> statementParsingDetails = new ArrayList<StatementParsingDetail>();
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

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	 * @return the investmentAccounts
	 */
	public List<InvestmentAccountEntity> getInvestmentAccounts() {
		return investmentAccounts;
	}

	public void setInvestmentAccounts(List<InvestmentAccountEntity> investmentAccountEntities) {
		this.investmentAccounts = investmentAccountEntities;
	}

	public List<BankAccountEntity> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(List<BankAccountEntity> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public List<CardAccountEntity> getCardAccounts() {
		return cardAccounts;
	}

	public void setCardAccounts(List<CardAccountEntity> cardAccounts) {
		this.cardAccounts = cardAccounts;
	}

	public List<LoanAccountEntity> getLoanAccounts() {
		return loanAccounts;
	}

	public void setLoanAccounts(List<LoanAccountEntity> loanAccounts) {
		this.loanAccounts = loanAccounts;
	}
	
	public List<FixedDepositAccountEntity> getFdAccounts() {
		return fdAccounts;
	}

	public void setFdAccounts(List<FixedDepositAccountEntity> fdAccounts) {
		this.fdAccounts = fdAccounts;
	}

	public List<Budget> getBudget() {
		return budget;
	}

	public void setBudget(List<Budget> budget) {
		this.budget = budget;
	}

	public List<AccountBaseEntity> getAllAccounts() {
		List<AccountBaseEntity> allAccounts = new ArrayList<AccountBaseEntity>();

		allAccounts.addAll(this.getInvestmentAccounts());
		allAccounts.addAll(this.getBankAccounts());
		allAccounts.addAll(this.getCardAccounts());
		allAccounts.addAll(this.getLoanAccounts());
		allAccounts.addAll(this.getFdAccounts());

		return allAccounts;
	}

	public List<AccountBaseEntity> getAllOnlineAccounts() {
		List<AccountBaseEntity> allAccounts = new ArrayList<AccountBaseEntity>();

		allAccounts.addAll(this.getInvestmentAccounts());
		allAccounts.addAll(this.getBankAccounts());
		allAccounts.addAll(this.getCardAccounts());
		allAccounts.addAll(this.getLoanAccounts());
		allAccounts.addAll(this.getFdAccounts());

		return allAccounts;
	}

	public List<AccountBaseEntity> getAllManualAccounts() {
		List<AccountBaseEntity> allAccounts = new ArrayList<AccountBaseEntity>();

		allAccounts.addAll(this.getInvestmentAccounts());
		allAccounts.addAll(this.getBankAccounts());
		allAccounts.addAll(this.getCardAccounts());
		allAccounts.addAll(this.getLoanAccounts());
		allAccounts.addAll(this.getFdAccounts());

		return allAccounts;
	}

	public List<StatementRepositoryEntity> getStatements() {
		return statements;
	}

	public void setStatements(List<StatementRepositoryEntity> statements) {
		this.statements = statements;
	}

	public List<FileRepositoryEntity> getFiles() {
		return files;
	}

	public void setFiles(List<FileRepositoryEntity> files) {
		this.files = files;
	}

	public List<UserInstituteDetail> getOnlineDetails() {
		return onlineDetails;
	}

	public void setOnlineDetails(List<UserInstituteDetail> onlineDetails) {
		this.onlineDetails = onlineDetails;
	}

	public String getPreferredCurrency() {
		return preferredCurrency;
	}

	public void setPreferredCurrency(String preferredCurrency) {
		this.preferredCurrency = preferredCurrency;
	}

	public List<Property> getProperty() {
		return property;
	}

	public void setProperty(List<Property> property) {
		this.property = property;
	}
}
