package com.pisight.pimoney.repository.entities;

import java.util.UUID;

public class RiderPremiumDTO {
	
	private UUID id;
	
	private String  name;
			
	private double premium;
	
	private String premiumCurrency;
	
	private double loadingPremium;
	
	private String loadingPremiumCurrency;
	
	private double death;
	
	private String deathCurrency;

	private double permanentDisability;
	
	private String permanentDisabilityCurrency;

	private double criticalIllness;
	
	private String criticalIllnessCurrency;

	private double accidentalDeath;
	
	private String accidentalDeathCurrency;

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
	 * @return the premium
	 */
	public double getPremium() {
		return premium;
	}

	/**
	 * @param premium the premium to set
	 */
	public void setPremium(double premium) {
		this.premium = premium;
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
	 * @return the loadingPremium
	 */
	public double getLoadingPremium() {
		return loadingPremium;
	}

	/**
	 * @param loadingPremium the loadingPremium to set
	 */
	public void setLoadingPremium(double loadingPremium) {
		this.loadingPremium = loadingPremium;
	}

	/**
	 * @return the death
	 */
	public double getDeath() {
		return death;
	}

	/**
	 * @param death the death to set
	 */
	public void setDeath(double death) {
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
	public double getPermanentDisability() {
		return permanentDisability;
	}

	/**
	 * @param permanentDisability the permanentDisability to set
	 */
	public void setPermanentDisability(double permanentDisability) {
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
	public double getCriticalIllness() {
		return criticalIllness;
	}

	/**
	 * @param criticalIllness the criticalIllness to set
	 */
	public void setCriticalIllness(double criticalIllness) {
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
	public double getAccidentalDeath() {
		return accidentalDeath;
	}

	/**
	 * @param accidentalDeath the accidentalDeath to set
	 */
	public void setAccidentalDeath(double accidentalDeath) {
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
