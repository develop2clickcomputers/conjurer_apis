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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="statement_parsing_details")
@JsonIgnoreProperties({"user", "createdAt", "updatedAt", "status"})
public class StatementParsingDetail implements Serializable {

	private static final long serialVersionUID = 5705817057827706194L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	protected UUID id;

	@ManyToOne
	@JoinColumn(name="user_id")
	protected User user;

	@Column(name = "country_code", nullable = false)
	protected String countryCode = null;

	@Column(name = "institution_name", nullable = false)
	protected String institutionName = null;

	@Column(name = "start_time", nullable = false)
	protected Date startTime = null;
	
	@Column(name = "end_time")
	protected Date endTime = null;
	
	@Column(name="file_blob")
	private byte[] fileByte = null;
	
	@Column(name = "file_password")
	protected String filePassword = null;
	
	@Column(name = "status", nullable = false)
	protected String status = null;
	
	@Column(name = "timezone", nullable = false)
	protected String timezone = null;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the filePassword
	 */
	public String getFilePassword() {
		return filePassword;
	}

	/**
	 * @param filePassword the filePassword to set
	 */
	public void setFilePassword(String filePassword) {
		this.filePassword = filePassword;
	}

	/**
	 * @return the fileByte
	 */
	public byte[] getFileByte() {
		return fileByte;
	}

	/**
	 * @param fileByte the fileByte to set
	 */
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
}
