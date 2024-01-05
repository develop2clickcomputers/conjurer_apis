package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
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

@Entity
@Table(name="batch_file_details")
public class BatchFileDetail implements Serializable{

	private static final long serialVersionUID = 514858842944847104L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;
	
	@Column(name="batch_id", nullable=false)
	private String batchId = null;
	
	@Column(name="institution_name", nullable=false)
	private String institutionName = null;
	
	@Column(name="portfolio_number", nullable=false)
	private String portfolioNumber = null;

	@Column(name="currency", nullable=false)
	private String currency = null;
	
	@Column(name="status", nullable=false)
	private String status = null;
	
	@Column(name="tbd_found", nullable=false)
	private boolean tbdFound = false;
	
	@Column(name="file_path", nullable=false)
	private String filePath = null;

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
	 * @return the batchId
	 */
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the institutionName
	 */
	public String getInstitutionName() {
		return institutionName;
	}

	/**
	 * @param institutionName the institutionName to set
	 */
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	/**
	 * @return the portfolioNumber
	 */
	public String getPortfolioNumber() {
		return portfolioNumber;
	}

	/**
	 * @param portfolioNumber the portfolioNumber to set
	 */
	public void setPortfolioNumber(String portfolioNumber) {
		this.portfolioNumber = portfolioNumber;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the tbdFound
	 */
	public boolean isTbdFound() {
		return tbdFound;
	}

	/**
	 * @param tbdFound the tbdFound to set
	 */
	public void setTbdFound(boolean tbdFound) {
		this.tbdFound = tbdFound;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
}
