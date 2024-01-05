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


@Entity
@Table(name="user_inst_detail")
public class UserInstituteDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -980654951025455544L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;
	
	@Column(name="pitracker_id", nullable=false)
	private String pitrackerId = null;
	
	@Column(name="tracker_id", nullable=false)
	private String trackerId = null;

	@Column(name="institute_code", nullable=false)
	private String institutionCode = null;
	
	@Column(name="state", nullable=false)
	private String state = null;
	
	@Column(name="progress")
	private String progress = null;
	
	@Column(name="process", nullable=false)
	private String process = null;
	
	@Column(name="error_code", nullable=false)
	private int errorCode = 0;
	
	@Column(name="is_mfa", nullable=false)
	private boolean mfa = false;
	
	@Column(name="last_successful_request", columnDefinition="TIMESTAMP NULL")
	private Date lastSuccessfulRequest = null;
	
	@Column(name = "createdAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updatedAt", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;
	
	@Column(name="message")
	private String message = null;
	
	@Column(name="transaction_required")
	private boolean transactionRequired = true;
	
	@Column(name="persist_credentials")
	private boolean persistCredentials = true;
	
	@Column(name="credentials_present")
	private boolean credentialsPresent = true;

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
	 * @return the pitrackerId
	 */
	public String getPitrackerId() {
		return pitrackerId;
	}

	/**
	 * @param pitrackerId the pitrackerId to set
	 */
	public void setPitrackerId(String pitrackerId) {
		this.pitrackerId = pitrackerId;
	}

	
	
	/**
	 * @return the trackerId
	 */
	public String getTrackerId() {
		return trackerId;
	}

	/**
	 * @param trackerId the trackerId to set
	 */
	public void setTrackerId(String trackerId) {
		this.trackerId = trackerId;
	}

	/**
	 * @return the institutionCode
	 */
	public String getInstitutionCode() {
		return institutionCode;
	}

	/**
	 * @param institutionCode the institutionCode to set
	 */
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return the progress
	 */
	public String getProgress() {
		return progress;
	}

	/**
	 * @param progress the progress to set
	 */
	public void setProgress(String progress) {
		this.progress = progress;
	}

	/**
	 * @return the process
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process the process to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the mfa
	 */
	public boolean isMfa() {
		return mfa;
	}

	/**
	 * @param mfa the mfa to set
	 */
	public void setMfa(boolean mfa) {
		this.mfa = mfa;
	}

	/**
	 * @return the lastSuccessfulRequest
	 */
	public Date getLastSuccessfulRequest() {
		return lastSuccessfulRequest;
	}

	/**
	 * @param lastSuccessfulRequest the lastSuccessfulRequest to set
	 */
	public void setLastSuccessfulRequest(Date lastSuccessfulRequest) {
		this.lastSuccessfulRequest = lastSuccessfulRequest;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the transactionRequired
	 */
	public boolean isTransactionRequired() {
		return transactionRequired;
	}

	/**
	 * @param transactionRequired the transactionRequired to set
	 */
	public void setTransactionRequired(boolean transactionRequired) {
		this.transactionRequired = transactionRequired;
	}
	
	/**
	 * @return the persistCredentials
	 */
	public boolean isPersistCredentials() {
		return persistCredentials;
	}

	/**
	 * @param persistCredentials the transactionRequired to set
	 */
	public void setPersistCredentials(boolean persistCredentials) {
		this.persistCredentials = persistCredentials;
	}

	/**
	 * @return the credentialsPresent
	 */
	public boolean isCredentialsPresent() {
		return credentialsPresent;
	}

	/**
	 * @param credentialsPresent the credentialsPresent to set
	 */
	public void setCredentialsPresent(boolean credentialsPresent) {
		this.credentialsPresent = credentialsPresent;
	}

	
	
	

}
