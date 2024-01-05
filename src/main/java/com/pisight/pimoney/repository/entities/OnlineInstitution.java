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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="online_institutions")
@JsonIgnoreProperties({"createdAt", "updatedAt",
"country", "investmentAccounts", "statements"})
public class OnlineInstitution extends Institution implements Serializable  {
	
	private static final long serialVersionUID = 6894800729665681725L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	protected UUID id;
	
	@Column(name="institution_name")
	private String name = null;
	
	@Column(name="inst_code")
	private String code = null;
	
	@Column(name="image_url")
	private String imageURL = null;
	
	@Column(name="enabled")
	private boolean enabled = false;
	
	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	protected Date updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@OneToMany(mappedBy="onlineInstitution",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<InvestmentAccountEntity> investmentAccounts = new ArrayList<InvestmentAccountEntity>();
	
	@OneToMany(mappedBy="onlineInstitution",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<BankAccountEntity> bankAccounts = new ArrayList<BankAccountEntity>();
	
	@OneToMany(mappedBy="onlineInstitution",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<CardAccountEntity> cardAccounts = new ArrayList<CardAccountEntity>();
	
	@OneToMany(mappedBy="onlineInstitution",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<LoanAccountEntity> loanAccounts = new ArrayList<LoanAccountEntity>();
	
	@OneToMany(mappedBy="onlineInstitution",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<FixedDepositAccountEntity> fdAccounts = new ArrayList<FixedDepositAccountEntity>();
	
	
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
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	@Override
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the imageURL
	 */
	@Override
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	@Override
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 * @return the enabled
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
	 * @return the country
	 */
	@Override
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	@Override
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * @return the investmentAccounts
	 */
	@Override
	public List<InvestmentAccountEntity> getInvestmentAccounts() {
		return investmentAccounts;
	}

	/**
	 * @param accounts the investmentAccounts to set
	 */
	@Override
	public void setInvestmentAccounts(List<InvestmentAccountEntity> accounts) {
		this.investmentAccounts = accounts;
	}

	/**
	 * @return the bankAccounts
	 */
	@Override
	public List<BankAccountEntity> getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * @param bankAccounts the bankAccounts to set
	 */
	@Override
	public void setBankAccounts(List<BankAccountEntity> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * @return the cardAccounts
	 */
	@Override
	public List<CardAccountEntity> getCardAccounts() {
		return cardAccounts;
	}

	/**
	 * @param cardAccounts the cardAccounts to set
	 */
	@Override
	public void setCardAccounts(List<CardAccountEntity> cardAccounts) {
		this.cardAccounts = cardAccounts;
	}

	/**
	 * @return the loanAccounts
	 */
	@Override
	public List<LoanAccountEntity> getLoanAccounts() {
		return loanAccounts;
	}

	/**
	 * @param loanAccounts the loanAccounts to set
	 */
	@Override
	public void setLoanAccounts(List<LoanAccountEntity> loanAccounts) {
		this.loanAccounts = loanAccounts;
	}

	/**
	 * @return the fdAccounts
	 */
	public List<FixedDepositAccountEntity> getFdAccounts() {
		return fdAccounts;
	}

	/**
	 * @param fdAccounts the fdAccounts to set
	 */
	public void setFdAccounts(List<FixedDepositAccountEntity> fdAccounts) {
		this.fdAccounts = fdAccounts;
	}

}
