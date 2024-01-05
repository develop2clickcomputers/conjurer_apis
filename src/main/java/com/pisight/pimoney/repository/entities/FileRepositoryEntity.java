package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="file_repository")
@JsonIgnoreProperties({"createdAt", "updatedAt", "user",
"institution"})
public class FileRepositoryEntity extends RepositoryBase implements Serializable {
	
	private static final long serialVersionUID = 914486313842341813L;
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;
	
	
	@Column(name="statement_date")
	private Date statementDate = null;
	
	@Column(name="statement_type")
	private String statementType = null;
	
	@Column(name="file_blob")
	private byte[] bytecode = null;
	
	@Column(name="statement_name")
	private String statementName = null;
	
	@Column(name="object_path")
	private String objectPath = null;
	
	@Column(name="file_path")
	private String filePath = null;
	
	@Column(name="file_type")
	private String fileType = "xml";

	@Column(name = "output_file_group")
	private String outputFileGroup = null;
	
	@Column(name = "header_group")
	private String headerGroup = null;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user = null;
	
	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	protected Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	protected Date updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "institution_id")
	private ManualInstitution institution = null;
	
	/**
	 * @return the id
	 */
	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the statementDate
	 */
	@Override
	public Date getStatementDate() {
		return statementDate;
	}

	/**
	 * @param statementDate the statementDate to set
	 */
	@Override
	public void setStatementDate(Date statementDate) {
		this.statementDate = statementDate;
	}

	/**
	 * @return the statementType
	 */
	@Override
	public String getStatementType() {
		return statementType;
	}

	/**
	 * @param statementType the statementType to set
	 */
	@Override
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	/**
	 * @return the bytecode
	 */
	@Override
	public byte[] getBytecode() {
		return bytecode;
	}

	/**
	 * @param docByte the bytecode to set
	 */
	@Override
	public void setBytecode(byte[] docByte) {
		this.bytecode = docByte;
	}

	/**
	 * @return the statementName
	 */
	@Override
	public String getStatementName() {
		return statementName;
	}

	/**
	 * @param statementName the statementName to set
	 */
	@Override
	public void setStatementName(String statementName) {
		this.statementName = statementName;
	}

	/**
	 * @return the filePath
	 */
	public String getObjectPath() {
		return objectPath;
	}

	/**
	 * @param objectPath the filePath to set
	 */
	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
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
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

	/**
	 * @return the user
	 */
	@Override
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	@Override
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the institution
	 */
	@Override
	public ManualInstitution getInstitution() {
		return institution;
	}

	/**
	 * @param institution the institution to set
	 */
	@Override
	public void setInstitution(ManualInstitution institution) {
		this.institution = institution;
	}

	/**
	 * @return the createdAt
	 */
	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the outputFileGroup
	 */
	public String getOutputFileGroup() {
		return outputFileGroup;
	}

	/**
	 * @param outputFileGroup the outputFileGroup to set
	 */
	public void setOutputFileGroup(String outputFileGroup) {
		this.outputFileGroup = outputFileGroup;
	}

	/**
	 * @return the headerGroup
	 */
	public String getHeaderGroup() {
		return headerGroup;
	}

	/**
	 * @param headerGroup the headerGroup to set
	 */
	public void setHeaderGroup(String headerGroup) {
		this.headerGroup = headerGroup;
	}
	
}
