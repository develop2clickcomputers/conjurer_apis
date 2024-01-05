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

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author kumar
 *
 */
@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {

	private static final long serialVersionUID = -4812977746941423314L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type = "uuid-char")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "policy_plan_id")
	private PolicyPlan policyPlan;

	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "name")
	private String name;

	@Column(name = "policy_number")
	private String policyNumber;

	@Column(name = "maturity_date")
	private Date maturityDate;

	@Column(name = "death")
	private Double death;

	@Column(name = "death_currency")
	private String deathCurrency;

	@Column(name = "permanent_disability")
	private Double permanentDisability;

	@Column(name = "permanent_disability_currency")
	private String permanentDisabilityCurrency;

	@Column(name = "critical_Illness")
	private Double criticalIllness;

	@Column(name = "critical_Illness_currency")
	private String criticalIllnessCurrency;

	@Column(name = "accidental_death")
	private Double accidentalDeath;

	@Column(name = "accidental_death_currency")
	private String accidentalDeathCurrency;

	@Column(name = "basic_face_value")
	private Double basicFaceValue;
	
	@Column(name = "premium")
	private Double premium;
	
	@Column(name = "premium_currency")
	private String premiumCurrency;
	
	@Column(name = "loading_premium")
	private Double loadingPremium;
	
	@Column(name = "loading_premium_currency")
	private String loadingPremiumCurrency;
	
	@Column(name = "premium_frequency")
	private String premiumFrequency;
	
	@Column(name = "uniqueId")
	private String uniqueId;

	@Column(name = "createdAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updatedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;
	
	@OneToMany(mappedBy="insurance",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<InsuranceRiderMap> insuranceRiderMap = new ArrayList<InsuranceRiderMap>();

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
	 * @return the policyPlan
	 */
	public PolicyPlan getPolicyPlan() {
		return policyPlan;
	}

	/**
	 * @param policyPlan the policyPlan to set
	 */
	public void setPolicyPlan(PolicyPlan policyPlan) {
		this.policyPlan = policyPlan;
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
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
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
	 * @return the death
	 */
	public Double getDeath() {
		return death;
	}

	/**
	 * @param death the death to set
	 */
	public void setDeath(Double death) {
		this.death = death;
	}

	/**
	 * @return the deathCurrency
	 */
	public String getDeathCurrency() {
		return deathCurrency;
	}

	/**
	 * @param deathCurrency the deathCurrency to set
	 */
	public void setDeathCurrency(String deathCurrency) {
		this.deathCurrency = deathCurrency;
	}

	/**
	 * @return the permanentDisability
	 */
	public Double getPermanentDisability() {
		return permanentDisability;
	}

	/**
	 * @param permanentDisability the permanentDisability to set
	 */
	public void setPermanentDisability(Double permanentDisability) {
		this.permanentDisability = permanentDisability;
	}

	/**
	 * @return the permanentDisabilityCurrency
	 */
	public String getPermanentDisabilityCurrency() {
		return permanentDisabilityCurrency;
	}

	/**
	 * @param permanentDisabilityCurrency the permanentDisabilityCurrency to set
	 */
	public void setPermanentDisabilityCurrency(String permanentDisabilityCurrency) {
		this.permanentDisabilityCurrency = permanentDisabilityCurrency;
	}

	/**
	 * @return the criticalIllness
	 */
	public Double getCriticalIllness() {
		return criticalIllness;
	}

	/**
	 * @param criticalIllness the criticalIllness to set
	 */
	public void setCriticalIllness(Double criticalIllness) {
		this.criticalIllness = criticalIllness;
	}

	/**
	 * @return the criticalIllnessCurrency
	 */
	public String getCriticalIllnessCurrency() {
		return criticalIllnessCurrency;
	}

	/**
	 * @param criticalIllnessCurrency the criticalIllnessCurrency to set
	 */
	public void setCriticalIllnessCurrency(String criticalIllnessCurrency) {
		this.criticalIllnessCurrency = criticalIllnessCurrency;
	}

	/**
	 * @return the accidentalDeath
	 */
	public Double getAccidentalDeath() {
		return accidentalDeath;
	}

	/**
	 * @param accidentalDeath the accidentalDeath to set
	 */
	public void setAccidentalDeath(Double accidentalDeath) {
		this.accidentalDeath = accidentalDeath;
	}

	/**
	 * @return the accidentalDeathCurrency
	 */
	public String getAccidentalDeathCurrency() {
		return accidentalDeathCurrency;
	}

	/**
	 * @param accidentalDeathCurrency the accidentalDeathCurrency to set
	 */
	public void setAccidentalDeathCurrency(String accidentalDeathCurrency) {
		this.accidentalDeathCurrency = accidentalDeathCurrency;
	}

	/**
	 * @return the basicFaceValue
	 */
	public Double getBasicFaceValue() {
		return basicFaceValue;
	}

	/**
	 * @param basicFaceValue the basicFaceValue to set
	 */
	public void setBasicFaceValue(Double basicFaceValue) {
		this.basicFaceValue = basicFaceValue;
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
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * @return the premium
	 */
	public Double getPremium() {
		return premium;
	}

	/**
	 * @param premium the premium to set
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}

	/**
	 * @return the loadingPremium
	 */
	public Double getLoadingPremium() {
		return loadingPremium;
	}

	/**
	 * @param loadingPremium the loadingPremium to set
	 */
	public void setLoadingPremium(Double loadingPremium) {
		this.loadingPremium = loadingPremium;
	}

	/**
	 * @return the premiumFrequency
	 */
	public String getPremiumFrequency() {
		return premiumFrequency;
	}

	/**
	 * @param premiumFrequency the premiumFrequency to set
	 */
	public void setPremiumFrequency(String premiumFrequency) {
		this.premiumFrequency = premiumFrequency;
	}

	/**
	 * @return the insuranceRiderMap
	 */
	public List<InsuranceRiderMap> getInsuranceRiderMap() {
		return insuranceRiderMap;
	}

	/**
	 * @param insuranceRiderMap the insuranceRiderMap to set
	 */
	public void setInsuranceRiderMap(List<InsuranceRiderMap> insuranceRiderMap) {
		this.insuranceRiderMap = insuranceRiderMap;
	}

	/**
	 * @return the premiumCurrency
	 */
	public String getPremiumCurrency() {
		return premiumCurrency;
	}

	/**
	 * @param premiumCurrency the premiumCurrency to set
	 */
	public void setPremiumCurrency(String premiumCurrency) {
		this.premiumCurrency = premiumCurrency;
	}

	/**
	 * @return the loadingPremiumCurrency
	 */
	public String getLoadingPremiumCurrency() {
		return loadingPremiumCurrency;
	}

	/**
	 * @param loadingPremiumCurrency the loadingPremiumCurrency to set
	 */
	public void setLoadingPremiumCurrency(String loadingPremiumCurrency) {
		this.loadingPremiumCurrency = loadingPremiumCurrency;
	}

}
