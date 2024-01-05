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
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.util.AccountUtil;

@Entity
@Table(name="card_transaction")
@JsonIgnoreProperties({"createdAt", "updatedAt", "transactionHash",  "fingerprint", "account", "categoryObj", "subCategoryObj"})
public class CardTransactionEntity extends TransactionBaseEntity  implements Serializable{

	private static final long serialVersionUID = -3357390092933424175L;

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type="uuid-char")
	private UUID id;

	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;

	@Column(name = "status", columnDefinition="bit(1) default 0")
	private boolean status;

	@Column(name = "confirmed", columnDefinition="bit(1) default 0")
	private boolean confirmed;

	@Column(name = "fingerprint")
	private String fingerprint = null;

	@Column(name = "hash")
	private String transactionHash = null;

	@Column(name = "account_number")
	private String accountNumber = null;

	@Column(name = "currency")
	private String currency = null;

	@Column(name = "type")
	private String transactionType = null;

	@Column(name = "amount")
	private String amount = null;

	@Column(name = "txn_date")
	private Date transDate = null;

	@Column(name = "post_date")
	private Date postDate = null;

	@Column(name = "description")
	private String description = null;
	
	@Column(name = "narration")
	private String narration = null;

	@ManyToOne
	@JoinColumn(name="account_id")
	private CardAccountEntity account;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category categoryObj;
	
	@ManyToOne
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategoryObj;
	
	@Column(name="merchant_name")
	private String merchantName;
	
	@Column(name="statement_id")
	private String statementId;

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
	 * @return the transactionHash
	 */
	@Override
	public String getTransactionHash() {
		return transactionHash;
	}

	/**
	 * @param transactionHash the transactionHash to set
	 */
	@Override
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
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
	 * @return the currency
	 */
	@Override
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
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = AccountUtil.formatAmount(amount);
	}

	@Override
	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public CardAccountEntity getAccount() {
		return account;
	}

	public void setAccount(CardAccountEntity account) {
		this.account = account;
	}

	@Override
	public String getTag() {
		return Constants.TAG_CARD;
	}

	@Override
	public void setTag(String tag) {

	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	@Override
	public Category getCategoryObj() {
		return categoryObj;
	}

	@Override
	public void setCategoryObj(Category category) {
		this.categoryObj = category;
	}

	@Override
	public SubCategory getSubCategoryObj() {
		return subCategoryObj;
	}


	@Override
	public void setSubCategoryObj(SubCategory subCategory) {
		this.subCategoryObj = subCategory;
	}

	@Override
	public String getMerchantName() {
		return merchantName;
	}

	@Override
	public void setMerchantName(String merchant) {
		this.merchantName = merchant;
	}

	@Override
	public String getStatementId() {
		return statementId;
	}

	@Override
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
	
}
