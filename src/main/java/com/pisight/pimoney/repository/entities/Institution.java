package com.pisight.pimoney.repository.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class Institution {
	
	protected UUID id;
	
	private String name = null;
	
	private String code = null;
	
	private String imageURL = null;
	
	private boolean enabled = false;
	
	protected Date createdAt;

	protected Date updatedAt;
	
	private Country country;
	
	private List<InvestmentAccountEntity> investmentAccounts = new ArrayList<InvestmentAccountEntity>();
	
	private List<BankAccountEntity> bankAccounts = new ArrayList<BankAccountEntity>();
	
	private List<CardAccountEntity> cardAccounts = new ArrayList<CardAccountEntity>();
	
	private List<LoanAccountEntity> loanAccounts = new ArrayList<LoanAccountEntity>();
	
	private List<GenericAccountEntity> genericAccounts = new ArrayList<GenericAccountEntity>();
	
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the investmentAccounts
	 */
	public List<InvestmentAccountEntity> getInvestmentAccounts() {
		return investmentAccounts;
	}

	/**
	 * @param accounts the investmentAccounts to set
	 */
	public void setInvestmentAccounts(List<InvestmentAccountEntity> accounts) {
		this.investmentAccounts = accounts;
	}

	/**
	 * @return the bankAccounts
	 */
	public List<BankAccountEntity> getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * @param bankAccounts the bankAccounts to set
	 */
	public void setBankAccounts(List<BankAccountEntity> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * @return the cardAccounts
	 */
	public List<CardAccountEntity> getCardAccounts() {
		return cardAccounts;
	}

	/**
	 * @param cardAccounts the cardAccounts to set
	 */
	public void setCardAccounts(List<CardAccountEntity> cardAccounts) {
		this.cardAccounts = cardAccounts;
	}

	/**
	 * @return the loanAccounts
	 */
	public List<LoanAccountEntity> getLoanAccounts() {
		return loanAccounts;
	}

	/**
	 * @param loanAccounts the loanAccounts to set
	 */
	public void setLoanAccounts(List<LoanAccountEntity> loanAccounts) {
		this.loanAccounts = loanAccounts;
	}

	/**
	 * @return the genericAccounts
	 */
	public List<GenericAccountEntity> getGenericAccounts() {
		return genericAccounts;
	}

	/**
	 * @param genericAccounts the genericAccounts to set
	 */
	public void setGenericAccounts(List<GenericAccountEntity> genericAccounts) {
		this.genericAccounts = genericAccounts;
	}
}
