package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.models.DataField;
import com.pisight.pimoney.models.GenericChild;

@Component("GenericAccountEntity")
/*@JsonIgnoreProperties({"user", "accountHolder", "branch", "bankId", "accountHash", "fingerprint",
	"createdAt","updatedAt", "manualInstitution", "onlineInstitution", "statements"})*/
@JsonIgnoreProperties({"user", "manualInstitution", "onlineInstitution", "statements"})
public class GenericAccountEntity extends AccountBaseEntity implements Serializable {

	private static final long serialVersionUID = -1692191383938829899L;

	protected UUID id;

	protected User user;


	protected String accountHolder = null;

	protected String branch = null;

	protected String currency = null;

	protected String bankId = null;

	protected String accountHash = null;

	protected String fingerprint = null;

	protected Date createdAt;

	protected Date updatedAt;

	protected boolean status;

	protected boolean confirmed;

	private String accountNumber = null;


	private Date billDate = null;

	private RefreshInfo refreshInfo = null;
	
	private boolean manuallyAdded;
	
	private ManualInstitution manualInstitution;
	
	private OnlineInstitution onlineInstitution;
	
	private String sheetName;
	
	private HashMap<String, DataField> accountDataFields = new HashMap<String, DataField>();
	
	private List<GenericChild> childData = new ArrayList<GenericChild>();

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
	 * @return the accountHolder
	 */
	@Override
	public String getAccountHolder() {
		return accountHolder;
	}

	/**
	 * @param accountHolder the accountHolder to set
	 */
	@Override
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	/**
	 * @return the branch
	 */
	@Override
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	@Override
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return the currency
	 */
	@Override
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	@Override
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the bankId
	 */
	@Override
	public String getBankId() {
		return bankId;
	}

	/**
	 * @param bankId the bankId to set
	 */
	@Override
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * @return the accountHash
	 */
	@Override
	public String getAccountHash() {
		return accountHash;
	}

	/**
	 * @param accountHash the accountHash to set
	 */
	@Override
	public void setAccountHash(String accountHash) {
		this.accountHash = accountHash;
	}

	/**
	 * @return the fingerprint
	 */
	@Override
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param fingerprint the fingerprint to set
	 */
	@Override
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
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
	 * @return the status
	 */
	@Override
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	@Override
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the confirmed
	 */
	@Override
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	@Override
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the accountNumber
	 */
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the billDate
	 */
	@Override
	public Date getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate the billDate to set
	 */
	@Override
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	/**
	 * @return the refreshInfo
	 */
	public RefreshInfo getRefreshInfo() {
		return refreshInfo;
	}

	/**
	 * @param refreshInfo the refreshInfo to set
	 */
	public void setRefreshInfo(RefreshInfo refreshInfo) {
		this.refreshInfo = refreshInfo;
	}

	/**
	 * @return the manuallyAdded
	 */
	@Override
	public boolean isManuallyAdded() {
		return manuallyAdded;
	}

	/**
	 * @param manuallyAdded the manuallyAdded to set
	 */
	@Override
	public void setManuallyAdded(boolean manuallyAdded) {
		this.manuallyAdded = manuallyAdded;
	}

	/**
	 * @return the manualInstitution
	 */
	@Override
	public ManualInstitution getManualInstitution() {
		return manualInstitution;
	}

	/**
	 * @param manualInstitution the manualInstitution to set
	 */
	@Override
	public void setManualInstitution(ManualInstitution manualInstitution) {
		this.manualInstitution = manualInstitution;
	}

	/**
	 * @return the onlineInstitution
	 */
	@Override
	public OnlineInstitution getOnlineInstitution() {
		return onlineInstitution;
	}

	/**
	 * @param onlineInstitution the onlineInstitution to set
	 */
	@Override
	public void setOnlineInstitution(OnlineInstitution onlineInstitution) {
		this.onlineInstitution = onlineInstitution;
	}

	/**
	 * @return the accountDataFields
	 */
	public HashMap<String, DataField> getAccountDataFields() {
		return accountDataFields;
	}

	/**
	 * @param accountDataFields the accountDataFields to set
	 */
	public void setAccountDataFields(HashMap<String, DataField> accountDataFields) {
		if(accountDataFields != null) {
			this.accountDataFields = accountDataFields;
		}
	}
	
	public void addAccountDataField(String key, DataField dataField) {
		this.accountDataFields.put(key, dataField);
	}
	
	public void addAccountDataField(String key, String value) {
		this.accountDataFields.put(key, new DataField(key, value));
	}
	
	public void addAccountDataField(String key, String value, String type) throws Exception {
		this.accountDataFields.put(key, new DataField(key, value, type));
	}

	/**
	 * @return the childData
	 */
	public List<GenericChild> getChildData() {
		return childData;
	}

	/**
	 * @param childData the childData to set
	 */
	public void setChildData(List<GenericChild> childData) {
		if(childData != null) {
			this.childData = childData;
		}
	}
	
	public void addChildData(GenericChild childData) {
		this.childData.add(childData);
	}

	@Override
	public String getTag() {
		return Constants.TAG_GENERIC;
	}
	
	public void setTag(String tag) {
		
	}

	@Override
	public String getInstitutionName() {

		String name = "";
		if(manualInstitution != null) {
			name =  manualInstitution.getName();
		}
		else if(onlineInstitution != null) {
			name  = onlineInstitution.getName();
		}

		return name;

	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @param sheetName the sheetName to set
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
