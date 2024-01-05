package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pisight.pimoney.repository.entities.RiderPremiumDTO;

public class InsuranceDTO {
	
	private UUID id;
	
	private UUID userId;
	
	private String carrierName;
	
	private String planName;
	
	private String planType;

	private String policyNumber;
	
	private String policyPlanId;
	
	private Double basicFaceValue;
	
	private Double death;
	
	private String deathCurrency;

	private Double permanentDisability;
	
	private String permanentDisabilityCurrency;
	
	private Double criticalIllness;
	
	private String criticalIllnessCurrency;
	
	private Double accidentalDeath;
	
	private String accidentalDeathCurrency;

	private String currency;
	
	private Date maturityDate;
	
	private Double premium;
	
	private String premiumCurrency;
	
	private Double loadingPremium;
	
	private String loadingPremiumCurrency;

	private String premiumFrequency;
	
	private String uniqueId;
	
	List<RiderPremiumDTO> riderPremium = new ArrayList<>();

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
	 * @return the userId
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(UUID userId) {
		this.userId = userId;
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
	 * @return the policyPlanId
	 */
	public String getPolicyPlanId() {
		return policyPlanId;
	}

	/**
	 * @param policyPlanId the policyPlanId to set
	 */
	public void setPolicyPlanId(String policyPlanId) {
		this.policyPlanId = policyPlanId;
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
	 * @return the riderPremium
	 */
	public List<RiderPremiumDTO> getRiderPremium() {
		return riderPremium;
	}

	/**
	 * @param riderPremium the riderPremium to set
	 */
	public void setRiderPremium(List<RiderPremiumDTO> riderPremium) {
		this.riderPremium = riderPremium;
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
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * @return the planType
	 */
	public String getPlanType() {
		return planType;
	}

	/**
	 * @param planType the planType to set
	 */
	public void setPlanType(String planType) {
		this.planType = planType;
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
