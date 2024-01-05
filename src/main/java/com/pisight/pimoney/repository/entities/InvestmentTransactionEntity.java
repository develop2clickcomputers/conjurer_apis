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

@Entity
@Table(name="investment_transaction")
@JsonIgnoreProperties({"createdAt", "updatedAt", "transactionHash",  "fingerprint", "assetMarket", "assetInstrument", "assetCustodian", "valuationDate", "expiryDate", 
	"assetCostDate", "closingMethodCode", "versusDate", "srcDstType", "srcDstSymbol", "tradeDateFxRate", "valuationDateFxRate", "assetOriginalFxRate", "markToMarket", 
	"assetMTM", "assetWithholdingTax", "exchange", "exchangeFee", "commission", "otherFees", "impliedCommission", "commissionPurpose", "assetPledge", "destinationPledge",
	"destinationCustodian", "assetDuration", "recordDate", "strategy", "reclaimAmount", "accrualAccount", "dividendAccrualMethod", "mgmtFeePeriodDate", "shortPositions", 
	"dividendStatus", "recallable", "brokerFirmSymbol", "committedCapital", "contributedCapital", "securityType", "securityId", "destinationCurrency", "assetTicker", "assetCUSIP", 
	"assetSEDOL", "assetQUIK", "fxDenominatorCurrency", "fxNumeratorCurrency", "underBloombergTicker", "underCUSIP", "underISIN", "underRIC", "underSEDOL", "underTicker", 
	"priceFactor", "basePrice", "accountCurrency", "settlementCurrency", "corpActionsIndicator", "account", "categoryObj", "subCategoryObj"})
public class InvestmentTransactionEntity extends TransactionBaseEntity  implements Serializable {


	private static final long serialVersionUID = -2662680050815508858L;

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

	@Column(name = "sub_account_number")
	private String subAccountNumber = null;

	@Column(name = "currency")
	private String currency = null;

	@Column(name = "type")
	private String type = null;

	@Column(name = "amount")
	private String amount = null;

	@Column(name = "txn_date")
	private Date transactionDate = null;

	@Column(name = "post_date")
	private Date postDate = null;

	@Column(name = "description")
	private String description = null;

	@Column(name = "categoryObj")
	private String assetCategory = null;

	@Column(name = "asset_name")
	private String assetName = null;

	@Column(name = "market")
	private String assetMarket = null;

	@Column(name = "instrument")
	private String assetInstrument = null;

	@Column(name = "yield")
	private String assetYield = null;

	@Column(name = "quantity")
	private String assetQuantity = null;

	@Column(name = "unit_cost")
	private String assetUnitCost = null;

	@Column(name = "total_cost")
	private String assetCost = null;

	@Column(name = "trade_date")
	private Date assetTradeDate = null;

	@Column(name = "custodian")
	private String assetCustodian = null;

	@Column(name = "issuer")
	private String assetIssuer = null;

	@Column(name = "ISIN")
	private String assetISIN = null;

	@Column(name = "valuation_date")
	private Date valuationDate = null;

	@Column(name = "start_date")
	private Date startDate = null;

	@Column(name = "maturity_date")
	private Date maturityDate = null;

	@Column(name = "coupon")
	private String coupon = null;

	@Column(name = "accrued_interest")
	private String accruedInterest = null;

	@Column(name = "strike_price")
	private String strikePrice = null;

	@Column(name = "expiry_date")
	private Date expiryDate = null;

	@Column(name = "brokerage_and_levies")
	private String brokerageAndLevies = null;
	
	@Column(name="statement_id")
	private String statementId;

	//new fields added on 17th May 2017

	/**
	 * two letters transaction code to identify the transaction type
	 */
	@Column(name = "txn_code")
	private String transCode = null;

	/**
	 * open date for the position
	 */
	@Column(name = "cost_date")
	private Date assetCostDate = null;

	/**
	 * method for closing the transactions.<br>
	 * examples are FIFO, LIFO<br>
	 * choose from the FieldOptions class
	 */
	@Column(name = "closing_method_code")
	private String closingMethodCode = null;

	/**
	 * You can close against a position opened on a specific date.<br>
	 * Practically it is an extension of closing method code
	 */
	@Column(name = "versus_date")
	private Date versusDate = null;

	/**
	 * Enter the security type code of the source or destination cash account for this transaction.
	 */
	@Column(name = "src_dst_type")
	private String srcDstType = null;

	/**
	 * Enter the symbol for the source or destination cash account for the transaction.  
	 */
	@Column(name = "src_dst_symbol")
	private String srcDstSymbol = null;

	/**
	 * Fx rate as on the transaction date
	 */
	@Column(name = "trade_date_fx_rate")
	private String tradeDateFxRate = null;

	/**
	 * Fx rate as on the settlement/valuation date
	 */
	@Column(name = "valuation_date_fx_rate")
	private String valuationDateFxRate = null;

	/**
	 * Fx rate as on the opening date of the position
	 */
	@Column(name = "original_fx_rate")
	private String assetOriginalFxRate = null;

	/**
	 * If the traded security/currency is different than the portfolio's base currency (currency types in Sec. Type and Source/Dest. Type fields), 
	 * choose whether or not the currency gain or loss affects the security/currency cost basis.<br><br>
	 * Possible values: (y/n).
	 */
	@Column(name = "mark_to_market")
	private boolean  markToMarket = false;

	/**
	 * asset MTM 
	 */
	@Column(name = "mtm")
	private String assetMTM = null;

	/**
	 * amount of foreign tax withheld from an interest payment.
	 */
	@Column(name = "withholding_tax")
	private String assetWithholdingTax = null;

	/**
	 * exchange code of the securities exchange involved in the transaction.
	 */
	@Column(name = "exchange")
	private String exchange = null;

	/**
	 * amount paid to the SEC or exchange for this transaction.
	 */
	@Column(name = "exchange_fee")
	private String exchangeFee = null;

	/**
	 * currency amount of the commission paid to the broker for the trade.
	 */
	@Column(name = "commission")
	private String commission = null;

	/**
	 * amount of any other fees applicable to this transaction (Example: stamp fees).
	 */
	@Column(name = "other_fees")
	private String otherFees = null;

	/**
	 * Indicate whether or not the commission of this transaction is "implied," which means that it is 
	 * included in the price of the security, and therefore affects its cost basis. Example: An OTC trade has an implied commission.<br><br>
	 * Possible values: (y/n).
	 */
	@Column(name = "implied_commission")
	private boolean impliedCommission = false;

	/**
	 * purpose associated with the commission (Example: Payment to Broker, Soft Dollar--Research)
	 */
	@Column(name = "commission_purpose")
	private String commissionPurpose = null;

	/**
	 * Indicate whether or not this security is pledged as collateral.
	 */
	@Column(name = "asset_pledge")
	private boolean assetPledge = false;

	/**
	 * new pledge status of the security. This field appears in long change (lc) and short change (sc) transactions only.
	 */
	@Column(name = "destination_pledge")
	private String destinationPledge = null;

	/**
	 * the custodian or location to which you want to transfer the security.
	 */
	@Column(name = "destination_custodian")
	private String destinationCustodian = null;

	/**
	 * duration on cost.
	 */
	@Column(name = "duration")
	private String assetDuration = null;

	/**
	 * the date on which the shareholder must officially own shares of the security in order to be entitled to a dividend paid for the security.
	 */
	@Column(name = "record_date")
	private Date recordDate = null;

	/**
	 * the Strategy Code associated with this transaction. Example: You might choose "Stock A Straddle" 
	 * to associate Stock A option transactions with positions of the underlying security.
	 */
	@Column(name = "strategy")
	private String strategy = null;

	/**
	 * the amount of foreign withholding tax that is reclaimable from the issuer country. The reclaimable amount is defined by tax rules of the issuer country.
	 */
	@Column(name = "reclaim_amount")
	private String reclaimAmount = null;

	/**
	 * For a dividend (dv) transaction you enter, define the accrual account if you chose to accrue the dividend. 
	 */
	@Column(name = "accrual_account")
	private String accrualAccount = null;

	/**
	 * preferred accrual method for dividend<br>
	 * choose from FieldOptions class
	 */
	@Column(name = "dividend_accrual_method")
	private String dividendAccrualMethod = null;

	/**
	 * When you enter a management fee transaction (dp), use the Fee Period Date field to accrue management 
	 * fees based on the number of calendar days in the billing period. The dates in the Trade Date and Fee Period 
	 * Date fields of the management fee transaction determine the billing period. When you define the Fee Period Date field, 
	 * the management fee is typically calculated using the number of calendar days between the trade date and the fee period date.
	 * <br><br>
	 * <table style="vertical-align: top; left: 0px; top: 300px;" cellspacing="0" width="576"><colgroup><col style="width: 10.377%;"><col style="width: 11.959%;">
	 *<col style="width: 13.396%;"><col style="width: 10.885%;"><col style="width: 13.02%;"><col style="width: 17.189%;"><col style="width: 10.528%;"><col style="width: 12.5%;"></colgroup><tbody><tr style="vertical-align: top; height: 48px;"><td style="border: Solid 1px #ffffff; width: 10.377%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Tran. Code</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 11.959%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Sec. Type</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 13.396%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Security Symbol</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 10.885%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Trade Date</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 13.02%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Trade Amount</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 17.189%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Fee Period Date</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 10.528%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Source Type</p></td><td style="border-top: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 12.5%; padding-right: 10px; padding-left: 10px;"><p class="TableHeading">Source<br>Symbol</p></td></tr><tr style="vertical-align: top; height: 33px;"><td style="border-left: Solid 1px #ffffff; border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 10.377%; padding-right: 10px; padding-left: 10px;"><p>dp</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 11.959%; padding-right: 10px; padding-left: 10px;"><p>epus</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 13.396%; padding-right: 10px; padding-left: 10px;"><p>manfee</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 10.885%; padding-right: 10px; padding-left: 10px;"><p>01/01/2007</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 13.02%; padding-right: 10px; padding-left: 10px;"><p>100</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 17.189%; padding-right: 10px; padding-left: 10px;"><p>03/31/2007</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 10.528%; padding-right: 10px; padding-left: 10px;"><p>caus</p></td><td style="border-right: Solid 1px #ffffff; border-bottom: Solid 1px #ffffff; width: 12.5%; padding-right: 10px; padding-left: 10px;"><p>cash</p></td></tr>
	 *</tbody></table><br>
	 *In this example, a prorated fee of $1.11 ($100/90 days in the period) a day is calculated.
	 */
	@Column(name = "mgmt_fee_period_date")
	private Date mgmtFeePeriodDate = null;


	/**
	 * identify whether a position is short or long<br>
	 * Possible values: (y/n).
	 */
	@Column(name = "short_positions")
	private boolean shortPositions = false;

	/**
	 * field to indicate whether or not the dividend is actual/final, or an estimate<br>
	 * choose from fieldOptions class
	 */
	@Column(name = "dividend_status")
	private String dividendStatus = null;

	/**
	 * Indicate whether or not the distribution from a private equity fund is recallable. A recallable distribution increases
	 *  the amount of unfunded commitment. This flag appears when you use the adjust cost (ac), dividend (dv), or 
	 *  interest (in) transaction for a private equity security type:<br>
	 * Possible values: (y/n).
	 */
	@Column(name = "recallable")
	private boolean recallable = false;

	/**
	 * symbol for the brokerage firm executing the transaction.
	 */
	@Column(name = "broker_firm_symbol")
	private String brokerFirmSymbol = null;

	/**<ol>
	 * <li>for opening transaction (by, li, ti) for a private equity security type, enter the commitment amount for a private equity fund in the fund's currency.<br> </li>
	 *  <li>for modified commitment (mc) for a private equity security type, enter the amount by which you want to reduce the total commitment amount.<br> </li>
	 *  <li>for sell transaction (sl), enter the amount that is being transferred to the new owner. </li>
	 * </ol>
	 */
	@Column(name = "commited_capital")
	private String committedCapital = null;

	/**
	 * amount of any contributions for a private equity fund at the time of the trade.
	 */
	@Column(name = "contributed_capital")
	private String contributedCapital = null;

	/**
	 * Security type code, such as cs, cb, etc. For details, see “Standard Advent Security Types”
	 */
	@Column(name = "security_type")
	private String securityType = null;

	/**
	 * Security identifier. As a rule, this field contains one of the following values:<br><ul>
	 *  <li>Ticker for options and for other security types when available. </li>
	 *  <li> CUSIP for USD securities. </li>
	 *  <li> SEDOL for non‐USD securities </li></ul>
	 */
	@Column(name = "security_id")
	private String securityId = null;

	/**
	 * ISO currency code for the source or destination security type
	 */
	@Column(name = "destination_currency")
	private String destinationCurrency = null;

	/**
	 * Ticker used to identify the security.
	 */
	@Column(name = "ticker")
	private String assetTicker = null;

	/**
	 *  CUSIP used to identify the security.
	 */
	@Column(name = "CUSIP")
	private String assetCUSIP = null;

	/**
	 * SEDOL used to identify the security.
	 */
	@Column(name = "SEDOL")
	private String assetSEDOL = null;

	/**
	 * QUIK used to identify the security.
	 */
	@Column(name = "QUIK")
	private String assetQUIK = null;

	/**
	 * FX relationship denominator currency (ISO code)
	 */
	@Column(name = "fx_denominator_currency")
	private String fxDenominatorCurrency = null;

	/**
	 *  FX relationship numerator currency (ISO code).
	 */
	@Column(name = "fx_numerator_currency")
	private String fxNumeratorCurrency = null;

	/**
	 * Underlying Bloomberg Ticker. 
	 */
	@Column(name = "bloomberg_ticker")
	private String underBloombergTicker = null;

	/**
	 * Underlying Cusip. 
	 */
	@Column(name = "under_cusip")
	private String underCUSIP = null;

	/**
	 * Underlying ISIN. 
	 */
	@Column(name = "under_ISIN")
	private String underISIN = null;

	/**
	 *  Underlying RIC.
	 */
	@Column(name = "under_RIC")
	private String underRIC = null;

	/**
	 * Underlying SEDOL.
	 */
	@Column(name = "under_SEDOL")
	private String underSEDOL = null;

	/**
	 * Underlying Ticker.
	 */
	@Column(name = "under_ticker")
	private String underTicker = null;

	/**
	 * Price Factor to determine the amount. Amount is Quantity x Price x Price Factor. Typically 1 for equity and 100 for options
	 */
	@Column(name = "price_factor")
	private String priceFactor = null;

	/**
	 * Price in the base currency. 
	 */
	@Column(name = "base_price")
	private String basePrice = null;

	/**
	 * The ISO currency code of the account.
	 */
	@Column(name = "account_currency")
	private String accountCurrency = null;

	/**
	 * The settlement ISO currency code
	 */
	@Column(name = "settlement_currency")
	private String settlementCurrency = null;

	/**
	 * Identifies a corporate action. Valid value is “Y” to indicate a corporate action.
	 */
	@Column(name = "corp_actions_indicator")
	private String corpActionsIndicator = null;

	@ManyToOne
	@JoinColumn(name="account_id")
	private InvestmentAccountEntity account;

	@ManyToOne
	@JoinColumn(name="category_id")
	private Category categoryObj;
	
	@ManyToOne
	@JoinColumn(name="sub_category_id")
	private SubCategory subCategoryObj;
	
	@Column(name="merchant_name")
	private String merchantName;

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
	 * @return the subAccountNumber
	 */
	public String getSubAccountNumber() {
		return subAccountNumber;
	}

	/**
	 * @param subAccountNumber the subAccountNumber to set
	 */
	public void setSubAccountNumber(String subAccountNumber) {
		this.subAccountNumber = subAccountNumber;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the amount
	 */
	@Override
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the description
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the assetCategory
	 */
	public String getAssetCategory() {
		return assetCategory;
	}

	/**
	 * @param assetCategory the assetCategory to set
	 */
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}

	/**
	 * @return the assetName
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * @param assetName the assetName to set
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	/**
	 * @return the assetMarket
	 */
	public String getAssetMarket() {
		return assetMarket;
	}

	/**
	 * @param assetMarket the assetMarket to set
	 */
	public void setAssetMarket(String assetMarket) {
		this.assetMarket = assetMarket;
	}

	/**
	 * @return the assetInstrument
	 */
	public String getAssetInstrument() {
		return assetInstrument;
	}

	/**
	 * @param assetInstrument the assetInstrument to set
	 */
	public void setAssetInstrument(String assetInstrument) {
		this.assetInstrument = assetInstrument;
	}

	/**
	 * @return the assetYield
	 */
	public String getAssetYield() {
		return assetYield;
	}

	/**
	 * @param assetYield the assetYield to set
	 */
	public void setAssetYield(String assetYield) {
		this.assetYield = assetYield;
	}

	/**
	 * @return the assetQuantity
	 */
	public String getAssetQuantity() {
		return assetQuantity;
	}

	/**
	 * @param assetQuantity the assetQuantity to set
	 */
	public void setAssetQuantity(String assetQuantity) {
		this.assetQuantity = assetQuantity;
	}

	/**
	 * @return the assetUnitCost
	 */
	public String getAssetUnitCost() {
		return assetUnitCost;
	}

	/**
	 * @param assetUnitCost the assetUnitCost to set
	 */
	public void setAssetUnitCost(String assetUnitCost) {
		this.assetUnitCost = assetUnitCost;
	}

	/**
	 * @return the assetCost
	 */
	public String getAssetCost() {
		return assetCost;
	}

	/**
	 * @param assetCost the assetCost to set
	 */
	public void setAssetCost(String assetCost) {
		this.assetCost = assetCost;
	}

	/**
	 * @return the assetTradeDate
	 */
	public Date getAssetTradeDate() {
		return assetTradeDate;
	}

	/**
	 * @param assetTradeDate the assetTradeDate to set
	 */
	public void setAssetTradeDate(Date assetTradeDate) {
		this.assetTradeDate = assetTradeDate;
	}

	/**
	 * @return the assetCustodian
	 */
	public String getAssetCustodian() {
		return assetCustodian;
	}

	/**
	 * @param assetCustodian the assetCustodian to set
	 */
	public void setAssetCustodian(String assetCustodian) {
		this.assetCustodian = assetCustodian;
	}

	/**
	 * @return the assetIssuer
	 */
	public String getAssetIssuer() {
		return assetIssuer;
	}

	/**
	 * @param assetIssuer the assetIssuer to set
	 */
	public void setAssetIssuer(String assetIssuer) {
		this.assetIssuer = assetIssuer;
	}

	/**
	 * @return the assetISIN
	 */
	public String getAssetISIN() {
		return assetISIN;
	}

	/**
	 * @param assetISIN the assetISIN to set
	 */
	public void setAssetISIN(String assetISIN) {
		this.assetISIN = assetISIN;
	}

	/**
	 * @return the valuationDate
	 */
	public Date getValuationDate() {
		return valuationDate;
	}

	/**
	 * @param valuationDate the valuationDate to set
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the coupon
	 */
	public String getCoupon() {
		return coupon;
	}

	/**
	 * @param coupon the coupon to set
	 */
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	/**
	 * @return the accruedInterest
	 */
	public String getAccruedInterest() {
		return accruedInterest;
	}

	/**
	 * @param accruedInterest the accruedInterest to set
	 */
	public void setAccruedInterest(String accruedInterest) {
		this.accruedInterest = accruedInterest;
	}

	/**
	 * @return the strikePrice
	 */
	public String getStrikePrice() {
		return strikePrice;
	}

	/**
	 * @param strikePrice the strikePrice to set
	 */
	public void setStrikePrice(String strikePrice) {
		this.strikePrice = strikePrice;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the brokerageAndLevies
	 */
	public String getBrokerageAndLevies() {
		return brokerageAndLevies;
	}

	/**
	 * @param brokerageAndLevies the brokerageAndLevies to set
	 */
	public void setBrokerageAndLevies(String brokerageAndLevies) {
		this.brokerageAndLevies = brokerageAndLevies;
	}

	/**
	 * @return the transCode
	 */
	public String getTransCode() {
		return transCode;
	}

	/**
	 * @param transCode the transCode to set
	 */
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	/**
	 * @return the assetCostDate
	 */
	public Date getAssetCostDate() {
		return assetCostDate;
	}

	/**
	 * @param assetCostDate the assetCostDate to set
	 */
	public void setAssetCostDate(Date assetCostDate) {
		this.assetCostDate = assetCostDate;
	}

	/**
	 * @return the closingMethodCode
	 */
	public String getClosingMethodCode() {
		return closingMethodCode;
	}

	/**
	 * @param closingMethodCode the closingMethodCode to set
	 */
	public void setClosingMethodCode(String closingMethodCode) {
		this.closingMethodCode = closingMethodCode;
	}

	/**
	 * @return the versusDate
	 */
	public Date getVersusDate() {
		return versusDate;
	}

	/**
	 * @param versusDate the versusDate to set
	 */
	public void setVersusDate(Date versusDate) {
		this.versusDate = versusDate;
	}

	/**
	 * @return the srcDstType
	 */
	public String getSrcDstType() {
		return srcDstType;
	}

	/**
	 * @param srcDstType the srcDstType to set
	 */
	public void setSrcDstType(String srcDstType) {
		this.srcDstType = srcDstType;
	}

	/**
	 * @return the srcDstSymbol
	 */
	public String getSrcDstSymbol() {
		return srcDstSymbol;
	}

	/**
	 * @param srcDstSymbol the srcDstSymbol to set
	 */
	public void setSrcDstSymbol(String srcDstSymbol) {
		this.srcDstSymbol = srcDstSymbol;
	}

	/**
	 * @return the tradeDateFxRate
	 */
	public String getTradeDateFxRate() {
		return tradeDateFxRate;
	}

	/**
	 * @param tradeDateFxRate the tradeDateFxRate to set
	 */
	public void setTradeDateFxRate(String tradeDateFxRate) {
		this.tradeDateFxRate = tradeDateFxRate;
	}

	/**
	 * @return the valuationDateFxRate
	 */
	public String getValuationDateFxRate() {
		return valuationDateFxRate;
	}

	/**
	 * @param valuationDateFxRate the valuationDateFxRate to set
	 */
	public void setValuationDateFxRate(String valuationDateFxRate) {
		this.valuationDateFxRate = valuationDateFxRate;
	}

	/**
	 * @return the assetOriginalFxRate
	 */
	public String getAssetOriginalFxRate() {
		return assetOriginalFxRate;
	}

	/**
	 * @param assetOriginalFxRate the assetOriginalFxRate to set
	 */
	public void setAssetOriginalFxRate(String assetOriginalFxRate) {
		this.assetOriginalFxRate = assetOriginalFxRate;
	}

	/**
	 * @return the markToMarket
	 */
	public boolean isMarkToMarket() {
		return markToMarket;
	}

	/**
	 * @param markToMarket the markToMarket to set
	 */
	public void setMarkToMarket(boolean markToMarket) {
		this.markToMarket = markToMarket;
	}

	/**
	 * @return the assetMTM
	 */
	public String getAssetMTM() {
		return assetMTM;
	}

	/**
	 * @param assetMTM the assetMTM to set
	 */
	public void setAssetMTM(String assetMTM) {
		this.assetMTM = assetMTM;
	}

	/**
	 * @return the assetWithholdingTax
	 */
	public String getAssetWithholdingTax() {
		return assetWithholdingTax;
	}

	/**
	 * @param assetWithholdingTax the assetWithholdingTax to set
	 */
	public void setAssetWithholdingTax(String assetWithholdingTax) {
		this.assetWithholdingTax = assetWithholdingTax;
	}

	/**
	 * @return the exchange
	 */
	public String getExchange() {
		return exchange;
	}

	/**
	 * @param exchange the exchange to set
	 */
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	/**
	 * @return the exchangeFee
	 */
	public String getExchangeFee() {
		return exchangeFee;
	}

	/**
	 * @param exchangeFee the exchangeFee to set
	 */
	public void setExchangeFee(String exchangeFee) {
		this.exchangeFee = exchangeFee;
	}

	/**
	 * @return the commission
	 */
	public String getCommission() {
		return commission;
	}

	/**
	 * @param commission the commission to set
	 */
	public void setCommission(String commission) {
		this.commission = commission;
	}

	/**
	 * @return the otherFees
	 */
	public String getOtherFees() {
		return otherFees;
	}

	/**
	 * @param otherFees the otherFees to set
	 */
	public void setOtherFees(String otherFees) {
		this.otherFees = otherFees;
	}

	/**
	 * @return the impliedCommission
	 */
	public boolean isImpliedCommission() {
		return impliedCommission;
	}

	/**
	 * @param impliedCommission the impliedCommission to set
	 */
	public void setImpliedCommission(boolean impliedCommission) {
		this.impliedCommission = impliedCommission;
	}

	/**
	 * @return the commissionPurpose
	 */
	public String getCommissionPurpose() {
		return commissionPurpose;
	}

	/**
	 * @param commissionPurpose the commissionPurpose to set
	 */
	public void setCommissionPurpose(String commissionPurpose) {
		this.commissionPurpose = commissionPurpose;
	}

	/**
	 * @return the assetPledge
	 */
	public boolean isAssetPledge() {
		return assetPledge;
	}

	/**
	 * @param assetPledge the assetPledge to set
	 */
	public void setAssetPledge(boolean assetPledge) {
		this.assetPledge = assetPledge;
	}

	/**
	 * @return the destinationPledge
	 */
	public String getDestinationPledge() {
		return destinationPledge;
	}

	/**
	 * @param destinationPledge the destinationPledge to set
	 */
	public void setDestinationPledge(String destinationPledge) {
		this.destinationPledge = destinationPledge;
	}

	/**
	 * @return the destinationCustodian
	 */
	public String getDestinationCustodian() {
		return destinationCustodian;
	}

	/**
	 * @param destinationCustodian the destinationCustodian to set
	 */
	public void setDestinationCustodian(String destinationCustodian) {
		this.destinationCustodian = destinationCustodian;
	}

	/**
	 * @return the assetDuration
	 */
	public String getAssetDuration() {
		return assetDuration;
	}

	/**
	 * @param assetDuration the assetDuration to set
	 */
	public void setAssetDuration(String assetDuration) {
		this.assetDuration = assetDuration;
	}

	/**
	 * @return the recordDate
	 */
	public Date getRecordDate() {
		return recordDate;
	}

	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	/**
	 * @return the strategy
	 */
	public String getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	/**
	 * @return the reclaimAmount
	 */
	public String getReclaimAmount() {
		return reclaimAmount;
	}

	/**
	 * @param reclaimAmount the reclaimAmount to set
	 */
	public void setReclaimAmount(String reclaimAmount) {
		this.reclaimAmount = reclaimAmount;
	}

	/**
	 * @return the accrualAccount
	 */
	public String getAccrualAccount() {
		return accrualAccount;
	}

	/**
	 * @param accrualAccount the accrualAccount to set
	 */
	public void setAccrualAccount(String accrualAccount) {
		this.accrualAccount = accrualAccount;
	}

	/**
	 * @return the dividendAccrualMethod
	 */
	public String getDividendAccrualMethod() {
		return dividendAccrualMethod;
	}

	/**
	 * @param dividendAccrualMethod the dividendAccrualMethod to set
	 */
	public void setDividendAccrualMethod(String dividendAccrualMethod) {
		this.dividendAccrualMethod = dividendAccrualMethod;
	}

	/**
	 * @return the mgmtFeePeriodDate
	 */
	public Date getMgmtFeePeriodDate() {
		return mgmtFeePeriodDate;
	}

	/**
	 * @param mgmtFeePeriodDate the mgmtFeePeriodDate to set
	 */
	public void setMgmtFeePeriodDate(Date mgmtFeePeriodDate) {
		this.mgmtFeePeriodDate = mgmtFeePeriodDate;
	}

	/**
	 * @return the shortPositions
	 */
	public boolean isShortPositions() {
		return shortPositions;
	}

	/**
	 * @param shortPositions the shortPositions to set
	 */
	public void setShortPositions(boolean shortPositions) {
		this.shortPositions = shortPositions;
	}

	/**
	 * @return the dividendStatus
	 */
	public String getDividendStatus() {
		return dividendStatus;
	}

	/**
	 * @param dividendStatus the dividendStatus to set
	 */
	public void setDividendStatus(String dividendStatus) {
		this.dividendStatus = dividendStatus;
	}

	/**
	 * @return the recallable
	 */
	public boolean isRecallable() {
		return recallable;
	}

	/**
	 * @param recallable the recallable to set
	 */
	public void setRecallable(boolean recallable) {
		this.recallable = recallable;
	}

	/**
	 * @return the brokerFirmSymbol
	 */
	public String getBrokerFirmSymbol() {
		return brokerFirmSymbol;
	}

	/**
	 * @param brokerFirmSymbol the brokerFirmSymbol to set
	 */
	public void setBrokerFirmSymbol(String brokerFirmSymbol) {
		this.brokerFirmSymbol = brokerFirmSymbol;
	}

	/**
	 * @return the committedCapital
	 */
	public String getCommittedCapital() {
		return committedCapital;
	}

	/**
	 * @param committedCapital the committedCapital to set
	 */
	public void setCommittedCapital(String committedCapital) {
		this.committedCapital = committedCapital;
	}

	/**
	 * @return the contributedCapital
	 */
	public String getContributedCapital() {
		return contributedCapital;
	}

	/**
	 * @param contributedCapital the contributedCapital to set
	 */
	public void setContributedCapital(String contributedCapital) {
		this.contributedCapital = contributedCapital;
	}

	/**
	 * @return the securityType
	 */
	public String getSecurityType() {
		return securityType;
	}

	/**
	 * @param securityType the securityType to set
	 */
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	/**
	 * @return the securityId
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId the securityId to set
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return the destinationCurrency
	 */
	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	/**
	 * @param destinationCurrency the destinationCurrency to set
	 */
	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	/**
	 * @return the assetTicker
	 */
	public String getAssetTicker() {
		return assetTicker;
	}

	/**
	 * @param assetTicker the assetTicker to set
	 */
	public void setAssetTicker(String assetTicker) {
		this.assetTicker = assetTicker;
	}

	/**
	 * @return the assetCUSIP
	 */
	public String getAssetCUSIP() {
		return assetCUSIP;
	}

	/**
	 * @param assetCUSIP the assetCUSIP to set
	 */
	public void setAssetCUSIP(String assetCUSIP) {
		this.assetCUSIP = assetCUSIP;
	}

	/**
	 * @return the assetSEDOL
	 */
	public String getAssetSEDOL() {
		return assetSEDOL;
	}

	/**
	 * @param assetSEDOL the assetSEDOL to set
	 */
	public void setAssetSEDOL(String assetSEDOL) {
		this.assetSEDOL = assetSEDOL;
	}

	/**
	 * @return the assetQUIK
	 */
	public String getAssetQUIK() {
		return assetQUIK;
	}

	/**
	 * @param assetQUIK the assetQUIK to set
	 */
	public void setAssetQUIK(String assetQUIK) {
		this.assetQUIK = assetQUIK;
	}

	/**
	 * @return the fxDenominatorCurrency
	 */
	public String getFxDenominatorCurrency() {
		return fxDenominatorCurrency;
	}

	/**
	 * @param fxDenominatorCurrency the fxDenominatorCurrency to set
	 */
	public void setFxDenominatorCurrency(String fxDenominatorCurrency) {
		this.fxDenominatorCurrency = fxDenominatorCurrency;
	}

	/**
	 * @return the fxNumeratorCurrency
	 */
	public String getFxNumeratorCurrency() {
		return fxNumeratorCurrency;
	}

	/**
	 * @param fxNumeratorCurrency the fxNumeratorCurrency to set
	 */
	public void setFxNumeratorCurrency(String fxNumeratorCurrency) {
		this.fxNumeratorCurrency = fxNumeratorCurrency;
	}

	/**
	 * @return the underBloombergTicker
	 */
	public String getUnderBloombergTicker() {
		return underBloombergTicker;
	}

	/**
	 * @param underBloombergTicker the underBloombergTicker to set
	 */
	public void setUnderBloombergTicker(String underBloombergTicker) {
		this.underBloombergTicker = underBloombergTicker;
	}

	/**
	 * @return the underCUSIP
	 */
	public String getUnderCUSIP() {
		return underCUSIP;
	}

	/**
	 * @param underCUSIP the underCUSIP to set
	 */
	public void setUnderCUSIP(String underCUSIP) {
		this.underCUSIP = underCUSIP;
	}

	/**
	 * @return the underISIN
	 */
	public String getUnderISIN() {
		return underISIN;
	}

	/**
	 * @param underISIN the underISIN to set
	 */
	public void setUnderISIN(String underISIN) {
		this.underISIN = underISIN;
	}

	/**
	 * @return the underRIC
	 */
	public String getUnderRIC() {
		return underRIC;
	}

	/**
	 * @param underRIC the underRIC to set
	 */
	public void setUnderRIC(String underRIC) {
		this.underRIC = underRIC;
	}

	/**
	 * @return the underSEDOL
	 */
	public String getUnderSEDOL() {
		return underSEDOL;
	}

	/**
	 * @param underSEDOL the underSEDOL to set
	 */
	public void setUnderSEDOL(String underSEDOL) {
		this.underSEDOL = underSEDOL;
	}

	/**
	 * @return the underTicker
	 */
	public String getUnderTicker() {
		return underTicker;
	}

	/**
	 * @param underTicker the underTicker to set
	 */
	public void setUnderTicker(String underTicker) {
		this.underTicker = underTicker;
	}

	/**
	 * @return the priceFactor
	 */
	public String getPriceFactor() {
		return priceFactor;
	}

	/**
	 * @param priceFactor the priceFactor to set
	 */
	public void setPriceFactor(String priceFactor) {
		this.priceFactor = priceFactor;
	}

	/**
	 * @return the basePrice
	 */
	public String getBasePrice() {
		return basePrice;
	}

	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

	/**
	 * @return the accountCurrency
	 */
	public String getAccountCurrency() {
		return accountCurrency;
	}

	/**
	 * @param accountCurrency the accountCurrency to set
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * @return the settlementCurrency
	 */
	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	/**
	 * @param settlementCurrency the settlementCurrency to set
	 */
	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	/**
	 * @return the corpActionsIndicator
	 */
	public String getCorpActionsIndicator() {
		return corpActionsIndicator;
	}

	/**
	 * @param corpActionsIndicator the corpActionsIndicator to set
	 */
	public void setCorpActionsIndicator(String corpActionsIndicator) {
		this.corpActionsIndicator = corpActionsIndicator;
	}

	/**
	 * @return the account
	 */
	@Override
	public InvestmentAccountEntity getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(InvestmentAccountEntity account) {
		this.account = account;
	}

	@Override
	public String getTag() {
		return Constants.TAG_INVESTMENT;
	}

	@Override
	public void setTag(String tag) {

	}

	@Override
	public Date getTransDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the categoryObj
	 */
	@Override
	public Category getCategoryObj() {
		return categoryObj;
	}

	/**
	 * @param category the categoryObj to set
	 */
	@Override
	public void setCategoryObj(Category category) {
		this.categoryObj = category;
	}

	/**
	 * @return the subCategoryObj
	 */
	@Override
	public SubCategory getSubCategoryObj() {
		return subCategoryObj;
	}

	/**
	 * @param subCategory the subCategoryObj to set
	 */
	@Override
	public void setSubCategoryObj(SubCategory subCategory) {
		this.subCategoryObj = subCategory;
	}

	/**
	 * @return the merchantName
	 */
	@Override
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchant the merchantName to set
	 */
	@Override
	public void setMerchantName(String merchant) {
		this.merchantName = merchant;
	}

	/**
	 * @return the statementId
	 */
	@Override
	public String getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId the statementId to set
	 */
	@Override
	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
}
