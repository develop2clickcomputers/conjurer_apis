package com.pisight.pimoney.repository.entities;

import java.util.Date;
import java.util.UUID;

public class RepositoryBase {

	private UUID id;
	
	private Date statementDate = null;
	
	private String statementType = null;
	
	private byte[] bytecode = null;
	
	private String statementName = null;
	
	private User user = null;
	
	protected Date createdAt;
	
	protected Date updatedAt;
	
	private ManualInstitution institution = null;

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
	 * @return the statementDate
	 */
	public Date getStatementDate() {
		return statementDate;
	}

	/**
	 * @param statementDate the statementDate to set
	 */
	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	/**
	 * @return the statementType
	 */
	public String getStatementType() {
		return statementType;
	}

	/**
	 * @param statementType the statementType to set
	 */
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	/**
	 * @return the bytecode
	 */
	public byte[] getBytecode() {
		return bytecode;
	}

	/**
	 * @param bytecode the bytecode to set
	 */
	public void setBytecode(byte[] bytecode) {
		this.bytecode = bytecode;
	}

	/**
	 * @return the statementName
	 */
	public String getStatementName() {
		return statementName;
	}

	/**
	 * @param statementName the statementName to set
	 */
	public void setStatementName(String statementName) {
		this.statementName = statementName;
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
	 * @return the institution
	 */
	public ManualInstitution getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	public void setInstitution(ManualInstitution institution) {
		this.institution = institution;
	}
	
}
