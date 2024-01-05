package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="country_list")
@JsonIgnoreProperties({"createdAt", "updatedAt",
"manualInstitutions", "onlineInstitutions"})
public class Country  implements Serializable {
	
	private static final long serialVersionUID = -4020664539601125468L;

	@Id
	@Column(name="id", nullable = false)
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id = null;
	
	@Column(name="country_name", nullable = false)
	private String name = null;
	
	@Column(name="country_code", nullable = false)
	private String code;
	
	@Column(name="automatic_manual_flag")
	private int  automaticManualFlag=1;
	
	@Column(name="currency_code")
	private String currency = null;
	
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;
	
	@OneToMany(mappedBy="country",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<ManualInstitution> manualInstitutions = new ArrayList<ManualInstitution>();
	
	@OneToMany(mappedBy="country",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
	private List<OnlineInstitution> onlineInstitutions = new ArrayList<OnlineInstitution>();

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	 * @return the automaticManualFlag
	 */
	public int getAutomaticManualFlag() {
		return automaticManualFlag;
	}

	/**
	 * @param automaticManualFlag the automaticManualFlag to set
	 */
	public void setAutomaticManualFlag(int automaticManualFlag) {
		this.automaticManualFlag = automaticManualFlag;
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
	 * @return the manualInstitutions
	 */
	public List<ManualInstitution> getManualInstitutions() {
		return manualInstitutions;
	}

	/**
	 * @param manualInstitutions the manualInstitutions to set
	 */
	public void setManualInstitutions(List<ManualInstitution> manualInstitutions) {
		this.manualInstitutions = manualInstitutions;
	}

	/**
	 * @return the onlineInstitutions
	 */
	public List<OnlineInstitution> getOnlineInstitutions() {
		return onlineInstitutions;
	}

	/**
	 * @param onlineInstitutions the onlineInstitutions to set
	 */
	public void setOnlineInstitutions(List<OnlineInstitution> onlineInstitutions) {
		this.onlineInstitutions = onlineInstitutions;
	}
	
	
}
