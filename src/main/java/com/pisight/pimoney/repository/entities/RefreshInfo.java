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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="refresh_info")
@JsonIgnoreProperties({"createdAt", "updatedAt",
"country", "accounts", "statements"})
public class RefreshInfo implements Serializable{

	private static final long serialVersionUID = 79522161437449934L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	private String refreshStatus = null;
	
	private int statusCode = 0;
	
	private String statusMessage = null;
	
	private Date nextRefreshScheduled = null;
	
	private Date lastRefreshed =  null;
	

	/**
	 * @return the refreshStatus
	 */
	public String getRefreshStatus() {
		return refreshStatus;
	}

	/**
	 * @param refreshStatus the refreshStatus to set
	 */
	public void setRefreshStatus(String refreshStatus) {
		this.refreshStatus = refreshStatus;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @return the nextRefreshScheduled
	 */
	public Date getNextRefreshScheduled() {
		return nextRefreshScheduled;
	}

	/**
	 * @param nextRefreshScheduled the nextRefreshScheduled to set
	 */
	public void setNextRefreshScheduled(Date nextRefreshScheduled) {
		this.nextRefreshScheduled = nextRefreshScheduled;
	}

	/**
	 * @return the lastRefreshed
	 */
	public Date getLastRefreshed() {
		return lastRefreshed;
	}

	/**
	 * @param lastRefreshed the lastRefreshed to set
	 */
	public void setLastRefreshed(Date lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}
	
	
}
