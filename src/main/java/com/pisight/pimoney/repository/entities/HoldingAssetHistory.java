package com.pisight.pimoney.repository.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * This class represent HoldingAssetEntity model.<br>
 * 
 * @author kumar
 *
 *
 */
@Entity
@Table(name="holding_asset_history", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"description", "category", "currency", "account_id"})
	})

public class HoldingAssetHistory implements Serializable {

	private static final long serialVersionUID = 8859927307300632507L;

	@Id
	//@GenericGenerator(name = "uuid", strategy = "uuid2")
	//@GeneratedValue(generator = "uuid")
	//@Column(name = "id", unique = true, nullable = false)
	@Column(name = "id", nullable = false)
	//@Type(type="uuid-char")
	private UUID id;

	@Column(name = "created_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;

	@Column(name = "status", columnDefinition="bit(1) default 0")
	private boolean status;

	@Column(name = "confirmed", columnDefinition="bit(1) default 0")
	private boolean confirmed;
	
	@Column(name = "fingerprint", columnDefinition="varchar(2000)")
	private String fingerprint = null;
	
	@Column(name = "account_number")
	private String  holdingAssetAccountNumber;

	@Column(name = "sub_account_number")
	private String  holdingAssetSubAccountNumber;

	@Column(name = "security_id")
	private String  holdingAssetSecurityId;

	@Column(name = "asset_name")
	private String  holdingAssetName;

	@Column(name = "description")
	private String  holdingAssetDescription;

	@Column(name = "category")
	private String  holdingAssetCategory;

	@Column(name = "sub_category")
	private String  holdingAssetSubCategory;

	@Column(name = "currency")
	private String  holdingAssetCurrency;

	@Column(name = "yield")
	private String  holdingAssetYield;

	@Column(name = "quantity")
	private String  holdingAssetQuantity;

	@Column(name = "unit_cost")
	private String  holdingAssetAverageUnitCost;

	@Column(name = "indicative_price")
	private String  holdingAssetIndicativePrice;

	@Column(name = "total_cost")
	private String  holdingAssetCost;

	@Column(name = "total_current_value")
	private String  holdingAssetCurrentValue;

	@Column(name = "indicative_price_date")
	private Date  holdingAssetIndicativePriceDate;

	@Column(name = "profit")
	private String  holdingAssetProfit;

	@Column(name = "profit_percentage")
	private String  holdingAssetProfitPerc;

	@Column(name = "custodian")
	private String  holdingAssetCustodian;

	@Column(name = "maturity_date")
	private Date  holdingAssetMaturityDate;

	@Column(name = "issuer")
	private String  holdingAssetIssuer;

	@Column(name = "accrued_interest")
	private String  holdingAssetAccruedInterest;

	@Column(name = "last_fx_rate")
	private String  holdingAssetLastFxRate;

	@Column(name = "fx_accrued_interest")
	private String  holdingAssetFxAccruedInterest;

	@Column(name = "start_date")
	private Date  holdingAssetStartDate;

	@Column(name = "fx_market_value")
	private String  holdingAssetFxMarketValue;

	@Column(name = "unrealized_profilt_loss")
	private String  holdingAssetUnrealizedProfitLoss;

	@Column(name = "unrealized_profit_loss_currency")
	private String  holdingAssetUnrealizedProfitLossCurrency;

	@Column(name = "ISIN")
	private String  holdingAssetISIN;

	@Column(name = "commencing_date")
	private Date  holdingAssetCommencingDate;

	@Column(name = "coupon")
	private String  holdingAssetCoupon;

	@Column(name = "strike_price")
	private String  holdingAssetStrikePrice;

	@Column(name = "interest_till_maturity")
	private String  holdingAssetInterestTillMaturity;

	@Column(name = "security_option")
	private String  holdingAssetOption;
	
	@Column(name="statement_id")
	private String statementId;

	//	new fields added on 31st May 2017

	/**
	 * two letters transaction code to identify the transaction type
	 */
	@Column(name = "txn_code")
	private String transCode = "li";

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
	@Column(name = "security_exchange")
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
	 * Indicate whether or not the distribution from a private equity fund is recallable. A recallable distribution increases
	 *  the amount of unfunded commitment. This flag appears when you use the adjust cost (ac), dividend (dv), or 
	 *  interest (in) transaction for a private equity security type:<br><br>
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
	 * trade Date
	 */
	@Column(name = "trade_date")
	private Date tradeDate = null;

	/**
	 * settlement date
	 */
	@Column(name = "settlement_date")
	private Date settlementDate = null;

	/**
	 * Security type code, such as cs, cb, etc. For details, see “Standard Advent Security Types”
	 */
	@Column(name = "security_type")
	private String securityType = null;

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
	 * RIC used to identify the security.
	 */
	@Column(name = "RIC")
	private String assetRIC = null;

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
	 * Bloomberg Ticker. 
	 */
	@Column(name = "bloomberg_ticker")
	private String bloombergTicker = null;

	/**
	 * Underlying Bloomberg Ticker. 
	 */
	@Column(name = "under_bloomberg_ticker")
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
	 * The last business day on which trading can occur for the given series, in mmddccyy format. 
	 * Depending on the product type, this day may be the same as the expiration day or the last business day before the expiration day.
	 */
	@Column(name = "last_trade_date")
	private Date lastTradeDate = null;

	/**
	 * Indicates that a transaction is a dividend reinvestment. Field may be populated for the “dv” 
	 * and subsequent “by” transaction codes. Valid values are “Y” to indicate a reinvestment.
	 */
	@Column(name = "dividend_reinvest_indicator")
	private String dividendReInvestIndicator = null;

	/**
	 * Indicates an American or European Option. Valid values are “A”, “E”, or <>. American options 
	 * can be exercised on any trading day before expiration and carry a premium on trade date. 
	 * European options can only be exercised on expiration and do not carry a premium on trade date.
	 */
	@Column(name = "option_type")
	private String optionType = null;

	/**
	 * The ISO 3166‐1 three character country code. This code identifies the country that issued 
	 * the security and differs from the currency code in ISO1 or ISO2. For instance, a French 
	 * security will have a securitycountrycode of “FRA” with an ISO1 of “EUR”.
	 */
	@Column(name = "security_country_code")
	private String securityCountryCode = null;

	/**
	 * Identifies a corporate action. Valid value is “Y” to indicate a corporate action.
	 */
	@Column(name = "corp_actions_indicator")
	private String corpActionsIndicator = null;

	/**
	 * A value‐added tax (vat) is a form of consumption tax.
	 */
	@Column(name = "VAT")
	private String VAT = null;

	/**
	 * Investment Objective – Maps to Goal. This is the investment objective of the account/portfolio and determines both the target model and style benchmark.<br>
	 * Investment objectives include:<br><ul>
	 * <li>Liquidity</li><li>Income Conservative</li><li>Income</li><li>Income with Growth</li><li>Balanced</li><li>Growth with Income</li>
	 * <li>Growth</li><li>Growth Aggressive</li><li>Tax Free Income Conservative</li><li>Tax Free Income</li><li>Tax Free Income with Growth</li>
	 * <li>Tax Free Balanced</li><li>Tax Free Growth with Income</li><li>Tax Free Growth</li><li>Alternatives Only</li><li>Estate</li>
	 * <li>To Be Determined</li><li>Exception</li></ul>
	 */
	@Column(name = "investment_objective")
	private String investmentObjective = null;

	@Column(name = "bond_nature")
	private boolean bondNature = false;
	
	@Column(name="account_id")
	private String account;

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
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the fingerprint
	 */
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * @param fingerprint the fingerprint to set
	 */
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	/**
	 * @return the holdingAssetAccountNumber
	 */
	public String getHoldingAssetAccountNumber() {
		return holdingAssetAccountNumber;
	}

	/**
	 * @param holdingAssetAccountNumber the holdingAssetAccountNumber to set
	 */
	public void setHoldingAssetAccountNumber(String holdingAssetAccountNumber) {
		this.holdingAssetAccountNumber = holdingAssetAccountNumber;
	}

	/**
	 * @return the holdingAssetSubAccountNumber
	 */
	public String getHoldingAssetSubAccountNumber() {
		return holdingAssetSubAccountNumber;
	}

	/**
	 * @param holdingAssetSubAccountNumber the holdingAssetSubAccountNumber to set
	 */
	public void setHoldingAssetSubAccountNumber(String holdingAssetSubAccountNumber) {
		this.holdingAssetSubAccountNumber = holdingAssetSubAccountNumber;
	}

	/**
	 * @return the holdingAssetSecurityId
	 */
	public String getHoldingAssetSecurityId() {
		return holdingAssetSecurityId;
	}

	/**
	 * @param holdingAssetSecurityId the holdingAssetSecurityId to set
	 */
	public void setHoldingAssetSecurityId(String holdingAssetSecurityId) {
		this.holdingAssetSecurityId = holdingAssetSecurityId;
	}

	/**
	 * @return the holdingAssetName
	 */
	public String getHoldingAssetName() {
		return holdingAssetName;
	}

	/**
	 * @param holdingAssetName the holdingAssetName to set
	 */
	public void setHoldingAssetName(String holdingAssetName) {
		this.holdingAssetName = holdingAssetName;
	}

	/**
	 * @return the holdingAssetDescription
	 */
	public String getHoldingAssetDescription() {
		return holdingAssetDescription;
	}

	/**
	 * @param holdingAssetDescription the holdingAssetDescription to set
	 */
	public void setHoldingAssetDescription(String holdingAssetDescription) {
		this.holdingAssetDescription = holdingAssetDescription;
	}

	/**
	 * @return the holdingAssetCategory
	 */
	public String getHoldingAssetCategory() {
		return holdingAssetCategory;
	}

	/**
	 * @param holdingAssetCategory the holdingAssetCategory to set
	 */
	public void setHoldingAssetCategory(String holdingAssetCategory) {
		this.holdingAssetCategory = holdingAssetCategory;
	}

	/**
	 * @return the holdingAssetSubCategory
	 */
	public String getHoldingAssetSubCategory() {
		return holdingAssetSubCategory;
	}

	/**
	 * @param holdingAssetSubCategory the holdingAssetSubCategory to set
	 */
	public void setHoldingAssetSubCategory(String holdingAssetSubCategory) {
		this.holdingAssetSubCategory = holdingAssetSubCategory;
	}

	/**
	 * @return the holdingAssetCurrency
	 */
	public String getHoldingAssetCurrency() {
		return holdingAssetCurrency;
	}

	/**
	 * @param holdingAssetCurrency the holdingAssetCurrency to set
	 */
	public void setHoldingAssetCurrency(String holdingAssetCurrency) {
		this.holdingAssetCurrency = holdingAssetCurrency;
	}

	/**
	 * @return the holdingAssetYield
	 */
	public String getHoldingAssetYield() {
		return holdingAssetYield;
	}

	/**
	 * @param holdingAssetYield the holdingAssetYield to set
	 */
	public void setHoldingAssetYield(String holdingAssetYield) {
		this.holdingAssetYield = holdingAssetYield;
	}

	/**
	 * @return the holdingAssetQuantity
	 */
	public String getHoldingAssetQuantity() {
		return holdingAssetQuantity;
	}

	/**
	 * @param holdingAssetQuantity the holdingAssetQuantity to set
	 */
	public void setHoldingAssetQuantity(String holdingAssetQuantity) {
		this.holdingAssetQuantity = holdingAssetQuantity;
	}

	/**
	 * @return the holdingAssetAverageUnitCost
	 */
	public String getHoldingAssetAverageUnitCost() {
		return holdingAssetAverageUnitCost;
	}

	/**
	 * @param holdingAssetAverageUnitCost the holdingAssetAverageUnitCost to set
	 */
	public void setHoldingAssetAverageUnitCost(String holdingAssetAverageUnitCost) {
		this.holdingAssetAverageUnitCost = holdingAssetAverageUnitCost;
	}

	/**
	 * @return the holdingAssetIndicativePrice
	 */
	public String getHoldingAssetIndicativePrice() {
		return holdingAssetIndicativePrice;
	}

	/**
	 * @param holdingAssetIndicativePrice the holdingAssetIndicativePrice to set
	 */
	public void setHoldingAssetIndicativePrice(String holdingAssetIndicativePrice) {
		this.holdingAssetIndicativePrice = holdingAssetIndicativePrice;
	}

	/**
	 * @return the holdingAssetCost
	 */
	public String getHoldingAssetCost() {
		return holdingAssetCost;
	}

	/**
	 * @param holdingAssetCost the holdingAssetCost to set
	 */
	public void setHoldingAssetCost(String holdingAssetCost) {
		this.holdingAssetCost = holdingAssetCost;
	}

	/**
	 * @return the holdingAssetCurrentValue
	 */
	public String getHoldingAssetCurrentValue() {
		return holdingAssetCurrentValue;
	}

	/**
	 * @param holdingAssetCurrentValue the holdingAssetCurrentValue to set
	 */
	public void setHoldingAssetCurrentValue(String holdingAssetCurrentValue) {
		this.holdingAssetCurrentValue = holdingAssetCurrentValue;
	}

	/**
	 * @return the holdingAssetIndicativePriceDate
	 */
	public Date getHoldingAssetIndicativePriceDate() {
		return holdingAssetIndicativePriceDate;
	}

	/**
	 * @param holdingAssetIndicativePriceDate the holdingAssetIndicativePriceDate to set
	 */
	public void setHoldingAssetIndicativePriceDate(Date holdingAssetIndicativePriceDate) {
		this.holdingAssetIndicativePriceDate = holdingAssetIndicativePriceDate;
	}

	/**
	 * @return the holdingAssetProfit
	 */
	public String getHoldingAssetProfit() {
		return holdingAssetProfit;
	}

	/**
	 * @param holdingAssetProfit the holdingAssetProfit to set
	 */
	public void setHoldingAssetProfit(String holdingAssetProfit) {
		this.holdingAssetProfit = holdingAssetProfit;
	}

	/**
	 * @return the holdingAssetProfitPerc
	 */
	public String getHoldingAssetProfitPerc() {
		return holdingAssetProfitPerc;
	}

	/**
	 * @param holdingAssetProfitPerc the holdingAssetProfitPerc to set
	 */
	public void setHoldingAssetProfitPerc(String holdingAssetProfitPerc) {
		this.holdingAssetProfitPerc = holdingAssetProfitPerc;
	}

	/**
	 * @return the holdingAssetCustodian
	 */
	public String getHoldingAssetCustodian() {
		return holdingAssetCustodian;
	}

	/**
	 * @param holdingAssetCustodian the holdingAssetCustodian to set
	 */
	public void setHoldingAssetCustodian(String holdingAssetCustodian) {
		this.holdingAssetCustodian = holdingAssetCustodian;
	}

	/**
	 * @return the holdingAssetMaturityDate
	 */
	public Date getHoldingAssetMaturityDate() {
		return holdingAssetMaturityDate;
	}

	/**
	 * @param holdingAssetMaturityDate the holdingAssetMaturityDate to set
	 */
	public void setHoldingAssetMaturityDate(Date holdingAssetMaturityDate) {
		this.holdingAssetMaturityDate = holdingAssetMaturityDate;
	}

	/**
	 * @return the holdingAssetIssuer
	 */
	public String getHoldingAssetIssuer() {
		return holdingAssetIssuer;
	}

	/**
	 * @param holdingAssetIssuer the holdingAssetIssuer to set
	 */
	public void setHoldingAssetIssuer(String holdingAssetIssuer) {
		this.holdingAssetIssuer = holdingAssetIssuer;
	}

	/**
	 * @return the holdingAssetAccruedInterest
	 */
	public String getHoldingAssetAccruedInterest() {
		return holdingAssetAccruedInterest;
	}

	/**
	 * @param holdingAssetAccruedInterest the holdingAssetAccruedInterest to set
	 */
	public void setHoldingAssetAccruedInterest(String holdingAssetAccruedInterest) {
		this.holdingAssetAccruedInterest = holdingAssetAccruedInterest;
	}

	/**
	 * @return the holdingAssetLastFxRate
	 */
	public String getHoldingAssetLastFxRate() {
		return holdingAssetLastFxRate;
	}

	/**
	 * @param holdingAssetLastFxRate the holdingAssetLastFxRate to set
	 */
	public void setHoldingAssetLastFxRate(String holdingAssetLastFxRate) {
		this.holdingAssetLastFxRate = holdingAssetLastFxRate;
	}

	/**
	 * @return the holdingAssetFxAccruedInterest
	 */
	public String getHoldingAssetFxAccruedInterest() {
		return holdingAssetFxAccruedInterest;
	}

	/**
	 * @param holdingAssetFxAccruedInterest the holdingAssetFxAccruedInterest to set
	 */
	public void setHoldingAssetFxAccruedInterest(String holdingAssetFxAccruedInterest) {
		this.holdingAssetFxAccruedInterest = holdingAssetFxAccruedInterest;
	}

	/**
	 * @return the holdingAssetStartDate
	 */
	public Date getHoldingAssetStartDate() {
		return holdingAssetStartDate;
	}

	/**
	 * @param holdingAssetStartDate the holdingAssetStartDate to set
	 */
	public void setHoldingAssetStartDate(Date holdingAssetStartDate) {
		this.holdingAssetStartDate = holdingAssetStartDate;
	}

	/**
	 * @return the holdingAssetFxMarketValue
	 */
	public String getHoldingAssetFxMarketValue() {
		return holdingAssetFxMarketValue;
	}

	/**
	 * @param holdingAssetFxMarketValue the holdingAssetFxMarketValue to set
	 */
	public void setHoldingAssetFxMarketValue(String holdingAssetFxMarketValue) {
		this.holdingAssetFxMarketValue = holdingAssetFxMarketValue;
	}

	/**
	 * @return the holdingAssetUnrealizedProfitLoss
	 */
	public String getHoldingAssetUnrealizedProfitLoss() {
		return holdingAssetUnrealizedProfitLoss;
	}

	/**
	 * @param holdingAssetUnrealizedProfitLoss the holdingAssetUnrealizedProfitLoss to set
	 */
	public void setHoldingAssetUnrealizedProfitLoss(String holdingAssetUnrealizedProfitLoss) {
		this.holdingAssetUnrealizedProfitLoss = holdingAssetUnrealizedProfitLoss;
	}

	/**
	 * @return the holdingAssetUnrealizedProfitLossCurrency
	 */
	public String getHoldingAssetUnrealizedProfitLossCurrency() {
		return holdingAssetUnrealizedProfitLossCurrency;
	}

	/**
	 * @param holdingAssetUnrealizedProfitLossCurrency the holdingAssetUnrealizedProfitLossCurrency to set
	 */
	public void setHoldingAssetUnrealizedProfitLossCurrency(String holdingAssetUnrealizedProfitLossCurrency) {
		this.holdingAssetUnrealizedProfitLossCurrency = holdingAssetUnrealizedProfitLossCurrency;
	}

	/**
	 * @return the holdingAssetISIN
	 */
	public String getHoldingAssetISIN() {
		return holdingAssetISIN;
	}

	/**
	 * @param holdingAssetISIN the holdingAssetISIN to set
	 */
	public void setHoldingAssetISIN(String holdingAssetISIN) {
		this.holdingAssetISIN = holdingAssetISIN;
	}

	/**
	 * @return the holdingAssetCommencingDate
	 */
	public Date getHoldingAssetCommencingDate() {
		return holdingAssetCommencingDate;
	}

	/**
	 * @param holdingAssetCommencingDate the holdingAssetCommencingDate to set
	 */
	public void setHoldingAssetCommencingDate(Date holdingAssetCommencingDate) {
		this.holdingAssetCommencingDate = holdingAssetCommencingDate;
	}

	/**
	 * @return the holdingAssetCoupon
	 */
	public String getHoldingAssetCoupon() {
		return holdingAssetCoupon;
	}

	/**
	 * @param holdingAssetCoupon the holdingAssetCoupon to set
	 */
	public void setHoldingAssetCoupon(String holdingAssetCoupon) {
		this.holdingAssetCoupon = holdingAssetCoupon;
	}

	/**
	 * @return the holdingAssetStrikePrice
	 */
	public String getHoldingAssetStrikePrice() {
		return holdingAssetStrikePrice;
	}

	/**
	 * @param holdingAssetStrikePrice the holdingAssetStrikePrice to set
	 */
	public void setHoldingAssetStrikePrice(String holdingAssetStrikePrice) {
		this.holdingAssetStrikePrice = holdingAssetStrikePrice;
	}

	/**
	 * @return the holdingAssetInterestTillMaturity
	 */
	public String getHoldingAssetInterestTillMaturity() {
		return holdingAssetInterestTillMaturity;
	}

	/**
	 * @param holdingAssetInterestTillMaturity the holdingAssetInterestTillMaturity to set
	 */
	public void setHoldingAssetInterestTillMaturity(String holdingAssetInterestTillMaturity) {
		this.holdingAssetInterestTillMaturity = holdingAssetInterestTillMaturity;
	}

	/**
	 * @return the holdingAssetOption
	 */
	public String getHoldingAssetOption() {
		return holdingAssetOption;
	}

	/**
	 * @param holdingAssetOption the holdingAssetOption to set
	 */
	public void setHoldingAssetOption(String holdingAssetOption) {
		this.holdingAssetOption = holdingAssetOption;
	}

	/**
	 * @return the statementId
	 */
	public String getStatementId() {
		return statementId;
	}

	/**
	 * @param statementId the statementId to set
	 */
	public void setStatementId(String statementId) {
		this.statementId = statementId;
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
	 * @return the tradeDate
	 */
	public Date getTradeDate() {
		return tradeDate;
	}

	/**
	 * @param tradeDate the tradeDate to set
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @return the settlementDate
	 */
	public Date getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
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
	 * @return the assetRIC
	 */
	public String getAssetRIC() {
		return assetRIC;
	}

	/**
	 * @param assetRIC the assetRIC to set
	 */
	public void setAssetRIC(String assetRIC) {
		this.assetRIC = assetRIC;
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
	 * @return the bloombergTicker
	 */
	public String getBloombergTicker() {
		return bloombergTicker;
	}

	/**
	 * @param bloombergTicker the bloombergTicker to set
	 */
	public void setBloombergTicker(String bloombergTicker) {
		this.bloombergTicker = bloombergTicker;
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
	 * @return the lastTradeDate
	 */
	public Date getLastTradeDate() {
		return lastTradeDate;
	}

	/**
	 * @param lastTradeDate the lastTradeDate to set
	 */
	public void setLastTradeDate(Date lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * @return the dividendReInvestIndicator
	 */
	public String getDividendReInvestIndicator() {
		return dividendReInvestIndicator;
	}

	/**
	 * @param dividendReInvestIndicator the dividendReInvestIndicator to set
	 */
	public void setDividendReInvestIndicator(String dividendReInvestIndicator) {
		this.dividendReInvestIndicator = dividendReInvestIndicator;
	}

	/**
	 * @return the optionType
	 */
	public String getOptionType() {
		return optionType;
	}

	/**
	 * @param optionType the optionType to set
	 */
	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	/**
	 * @return the securityCountryCode
	 */
	public String getSecurityCountryCode() {
		return securityCountryCode;
	}

	/**
	 * @param securityCountryCode the securityCountryCode to set
	 */
	public void setSecurityCountryCode(String securityCountryCode) {
		this.securityCountryCode = securityCountryCode;
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
	 * @return the vAT
	 */
	public String getVAT() {
		return VAT;
	}

	/**
	 * @param vAT the vAT to set
	 */
	public void setVAT(String vAT) {
		VAT = vAT;
	}

	/**
	 * @return the investmentObjective
	 */
	public String getInvestmentObjective() {
		return investmentObjective;
	}

	/**
	 * @param investmentObjective the investmentObjective to set
	 */
	public void setInvestmentObjective(String investmentObjective) {
		this.investmentObjective = investmentObjective;
	}

	/**
	 * @return the bondNature
	 */
	public boolean isBondNature() {
		return bondNature;
	}

	/**
	 * @param bondNature the bondNature to set
	 */
	public void setBondNature(boolean bondNature) {
		this.bondNature = bondNature;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}	
}
