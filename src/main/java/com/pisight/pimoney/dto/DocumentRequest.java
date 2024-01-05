package com.pisight.pimoney.dto;

import com.pisight.pimoney.constants.Constants;

public class DocumentRequest {
	
	public static final String JSON = "json";
	public static final String XML = "xml";
	
	private String name = null;
	private String container = null;
	private String locale = null;
	private String type = null;
	private String docByte = null;
	private String userId = null;
	private String institutionCode = null;
	private String responseFormat = XML;
	private String statementFileName = null;
	private boolean isEncrypted = false;
	private String flow = Constants.FLOW_PIMONEY;
	private String fileRepoId = null;
	private String repoId = null;
	private String accountNumber = null;
	private char[] pswd = null;
	private String transactionId = null;
	private String tag = null;
	private String uid = null;
	private String outputFileGroup = null;
	private String headerGroup = null;
	private String batchId = null;
	
	public boolean isEncrypted() {
		return isEncrypted;
	}
	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}
	public char[] getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd.toCharArray();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDocByte() {
		return docByte;
	}
	public void setDocByte(String docByte) {
		this.docByte = docByte;
	}
	public String getInstitutionCode() {
		return institutionCode;
	}
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}
	/**
	 * returns the format in which the output for data model will be returned.(XML or JSON)
	 * @return response foramat
	 */
	public String getResponseFormat() {
		return responseFormat;
	}
	
	/**
	 * Set the format in which the output for data model will be returned.(XML or JSON)
	 * @param responseFormat Response Format(XML/JSON)
	 */
	public void setResponseFormat(String responseFormat) {
		this.responseFormat = responseFormat;
	}
	/**
	 * @return the statementFileName
	 */
	public String getStatementFileName() {
		return statementFileName;
	}
	/**
	 * @param statementFileName the statementFileName to set
	 */
	public void setStatementFileName(String statementFileName) {
		this.statementFileName = statementFileName;
	}
	/**
	 * @return the flow
	 */
	public String getFlow() {
		return flow;
	}
	/**
	 * @param flow the flow to set
	 */
	public void setFlow(String flow) {
		this.flow = flow;
	}
	/**
	 * @return the fileRepoId
	 */
	public String getFileRepoId() {
		return fileRepoId;
	}
	/**
	 * @param fileRepoId the fileRepoId to set
	 */
	public void setFileRepoId(String fileRepoId) {
		this.fileRepoId = fileRepoId;
	}
	/**
	 * @return the repoId
	 */
	public String getRepoId() {
		return repoId;
	}
	/**
	 * @param repoId the repoId to set
	 */
	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
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
	
}
