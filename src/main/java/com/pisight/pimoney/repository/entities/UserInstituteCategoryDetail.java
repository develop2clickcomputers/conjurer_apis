package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity
@Table(name="user_inst_category_detail")
public class UserInstituteCategoryDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878302950774524215L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	@Column(name="pitracker_id", nullable=false)
	private String pitrackerId = null;

	@Column(name="category", nullable=false)
	private String category = null;
	
	@Column(name="state", nullable=false)
	private String state = null;
	
	@Column(name="error_code", nullable=false)
	private int errorCode = 0;
	
	@Column(name="message")
	private String message = null;
	
	@Column(name="active")
	private boolean active = false;
	
	@Column(name="last_successful_request", columnDefinition="TIMESTAMP NULL")
	private Date lastSuccessfulRequest = null;
	
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
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
