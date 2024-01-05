package com.pisight.pimoney.repository.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="rider_premium")
public class RiderPremium implements Serializable{

	private static final long serialVersionUID = 6411073414308488747L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="insurance_rider_id", nullable = false)
	private InsuranceRiderMap insuranceRiderMap;
	
	@Column(name = "premium", nullable = false)
	private double premium;
	
	@Column(name = "premium_currency", nullable = false)
	private String premiumCurrency;
	
	@Column(name = "loading_premium", nullable = false)
	private double loadingPremium;
	
	@Column(name = "loading_premium_currency", nullable = false)
	private String loadingPremiumCurrency;
	
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
	
	@Column(name = "createdAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updatedAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;

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
	 * @return the insuranceRiderMap
	 */
	public InsuranceRiderMap getInsuranceRiderMap() {
		return insuranceRiderMap;
	}

	/**
	 * @param insuranceRiderMap the insuranceRiderMap to set
	 */
	public void setInsuranceRiderMap(InsuranceRiderMap insuranceRiderMap) {
		this.insuranceRiderMap = insuranceRiderMap;
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
}
