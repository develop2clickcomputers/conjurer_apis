package com.pisight.pimoney.constants;

public interface QueryConstant {

	String GET_USER = "select u from User u where u.username = :username";

	String GET_INVESTMENT_ACCOUNT = "select account from InvestmentAccountEntity account where account.id = :id";

	String GET_INVESTMENT_TRANSACTION = "select txn from InvestmentTransactionEntity txn where txn.id = :id";

	String GET_BANK_ACCOUNT = "select account from BankAccountEntity account where account.id = :id";

	String GET_BANK_TRANSACTION = "select txn from BankTransactionEntity txn where txn.id = :id";

	String GET_CARD_ACCOUNT = "select account from CardAccountEntity account where account.id = :id";

	String GET_CARD_TRANSACTION = "select txn from CardTransactionEntity txn where txn.id = :id";

	String GET_LOAN_ACCOUNT = "select account from LoanAccountEntity account where account.id = :id";

	String GET_LOAN_TRANSACTION = "select txn from LoanTransactionEntity txn where txn.id = :id";
	
	String GET_FIXED_DEPOSIT_ACCOUNT = "select account from FixedDepositAccountEntity account where account.id = :id";

	String GET_HOLDING_ASSET = "select asset from HoldingAssetEntity asset where asset.id = :id";

	String GET_CONFIGURATION = "select detail from Configuration detail where detail.type = :type and detail.key = :key";

	String GET_MANUAL_INSTITUTIONS_BY_COUNTRY = "select mi from ManualInstitution mi where mi.country.code = :countryCode and mi.enabled = true";

	String GET_MANUAL_INSTITUTIONS_BY_CODE = "select mi from ManualInstitution mi where mi.code = :instCode";	

	String GET_ONLINE_INSTITUTIONS_BY_COUNTRY = "select oi from OnlineInstitution oi where oi.country.code = :countryCode";

	String GET_ONLINE_INSTITUTIONS_BY_CODE = "select oi from OnlineInstitution oi where oi.code = :instCode";	

	String GET_CURRENCY_LIST = "select currency from Currency currency";

	String GET_STATEMENT_REPO_BY_USER_ID = "select repo from StatementRepositoryEntity repo where repo.user.id  = :userId";

	String GET_FILE_REPO_BY_USER_ID = "select repo from FileRepositoryEntity repo where repo.user.id  = :userId";

	// for online
	String GET_USER_INST_DETAIL = "select detail from UserInstituteDetail detail";

	String GET_USER_INST_DETAIL_BY_PTID = "select detail from UserInstituteDetail detail where detail.pitrackerId = :pitrackerId";

	String GET_USER_INST_DETAIL_BY_USER_ID = "select detail from UserInstituteDetail detail where detail.user.id = :userId";

	String GET_USER_INST_DETAIL_BY_USER_INST = "select detail from UserInstituteDetail detail where detail.user.id = :userId and "
			+ "detail.institutionCode = :institutionCode";

	String GET_USER_INST_DETAIL_BY_TRACKER_ID = "select detail from UserInstituteDetail detail where detail.trackerId = :trackerId";

	String GET_USER_INST_CAT_DETAIL = "select detail from UserInstituteCategoryDetail detail";

	String GET_USER_INST_CAT_DETAIL_BY_PTID = "select detail from UserInstituteCategoryDetail detail where detail.pitrackerId= :pitrackerId";

	String GET_USER_INST_CAT_DETAIL_BY_PTID_CATEGORY = "select detail from UserInstituteCategoryDetail detail where detail.pitrackerId= :pitrackerId and detail.category= :category";

	String GET_PROPERTY_BY_ID = "select property from Property property where property.id= :id";

	String GET_CATEGORY_BY_NAME = "select cat from Category cat where cat.name = :name";

	String GET_SUB_CATEGORY_BY_NAME_AND_CATEGORY_ID = "select cat from SubCategory cat where cat.name = :name and cat.category.id= :categoryId";

	String GET_TXN_CAT_DETAIL_BY_TXN_ID = "select detail from TransactionCategorizationDetails detail where detail.transactionId = :transactionId";

	String GET_RATE_BY_CURRENCY_AND_DATE = "select rate from CurrencyRate rate where rate.currency = :currency and rate.date = :date";

	String GET_ASSET_BY_USER_ID = "select asset from HoldingAssetEntity asset where asset.account.user.id = :userId";

	String GET_RATE_BY_DATE = "select new com.pisight.pimoney.dto.RateDTO(rate.currency, rate.rate) from CurrencyRate rate where rate.date = :date";

	String GET_BANK_TXN_DETAILS_BY_DATE_INTERVAL = "select txn from BankTransactionEntity txn where txn.account.user.id = :id and txn.transDate BETWEEN :startDate AND :endDate";

	String GET_CARD_TXN_DETAILS_BY_DATE_INTERVAL = "select txn from CardTransactionEntity txn where txn.account.user.id = :id and txn.transDate BETWEEN :startDate AND :endDate";

	String GET_LOAN_TXN_DETAILS_BY_DATE_INTERVAL = "select txn from LoanTransactionEntity txn where txn.account.user.id = :id and txn.transDate BETWEEN :startDate AND :endDate";

	String GET_BUDGET_BY_ID = "select budget from Budget budget where budget.id= :id";

	String GET_BUDGET_BY_CATEGORY_AND_DATE = "select budget from Budget budget where budget.user.id = :userId and budget.category.name = :category and budget.budgetTiming = :budgetDate and budget.status = true";

	String GET_FUTURE_BUDGETS = "select budget from Budget budget where budget.user.id = :userId and budget.category.name = :category and budget.budgetTiming >= :budgetDate and budget.status = true";

	String GET_BANK_MANUAL_ACCOUNT = "select account from BankAccountEntity account where account.user.id = :userId and account.manuallyAdded = true";

	String GET_CARD_MANUAL_ACCOUNT = "select account from CardAccountEntity account where account.user.id = :userId and account.manuallyAdded = true";

	String GET_LOAN_MANUAL_ACCOUNT = "select account from LoanAccountEntity account where account.user.id = :userId and account.manuallyAdded = true";

	String GET_FIXED_DEPOSIT_MANUAL_ACCOUNT = "select account from FixedDepositAccountEntity account where account.user.id = :userId and account.manuallyAdded = true";

	String GET_BUDGET_BY_DATE = "select budget from Budget budget where budget.user.id = :userId and budget.budgetTiming = :budgetDate and budget.status = true";

	String GET_BUDGET_BY_USER_ID = "select budget from Budget budget where budget.user.id= :userId";

	String GET_BANK_ACCOUNT_BY_BANK_ID = "select account from BankAccountEntity account where account.bankId= :bankId and status = true";

	String GET_CARD_ACCOUNT_BY_BANK_ID = "select account from CardAccountEntity account where account.bankId= :bankId and status = true";

	String GET_LOAN_ACCOUNT_BY_BANK_ID = "select account from LoanAccountEntity account where account.bankId= :bankId and status = true";

	String GET_FIXED_DEPOSIT_ACCOUNT_BY_BANK_ID = "select account from FixedDepositAccountEntity account where account.bankId= :bankId and status = true";

	String GET_INVESTMENT_ACCOUNT_BY_BANK_ID = "select account from InvestmentAccountEntity account where account.bankId= :bankId and status = true";


	// Update queries

	String SOFT_DELETE_BANK_TXN = "update BankTransactionEntity txn set txn.status = false, txn.confirmed = false where txn.account.id  = :accountId";

	String SOFT_DELETE_CARD_TXN = "update CardTransactionEntity txn set txn.status = false, txn.confirmed = false  where txn.account.id  = :accountId";

	String SOFT_DELETE_LOAN_TXN = "update LoanTransactionEntity txn set txn.status = false, txn.confirmed = false  where txn.account.id  = :accountId";

	String SOFT_DELETE_INVESTMENT_TXN = "update InvestmentTransactionEntity txn set txn.status = false, txn.confirmed = false  where txn.account.id  = :accountId";

	String SOFT_DELETE_HOLDING_ASSET = "update HoldingAssetEntity asset set asset.status = false, asset.confirmed = false  where asset.account.id  = :accountId";

	
	// For Statement Details Query
	String GET_DETAILS_BY_DATE = "select details from StatementParsingDetail details where date(details.startTime) = :date";

	String FETCH_DETAILS = "select details from AccountGroupDetails details where details.user.id = :userId and details.institution.id = :institutionId and details.accountNumber = :accountNumber";

	String FETCH_DETAILS_BY_USER_ID = "select details from AccountGroupDetails details where details.user.id = :userId";

	String GET_STATEMENT_REPO_BY_GROUP_ID = "select repo from StatementRepositoryEntity repo where repo.user.id  = :userId and repo.groupId = :groupId";

	String FETCH_DETAILS_BY_GROUP_ID = "select details from AccountGroupDetails details where details.user.id = :userId and details.groupId = :groupId";

	// Api Releted Insurance
	
	String FETCH_CARRIER_BY_ID = "select carrier from Carrier carrier where carrier.id = :carrierId";

	String FETCH_RIDER_BY_CARRIER_PLAN_ID = "select rider from Rider rider where rider.carrier.id = :carrierId and rider.policyPlan.id = :planId";

	String FETCH_INSURANCE_BY_USER_ID = "select insurance from Insurance insurance where insurance.user.id = :userId";

	String FETCH_RIDER_PREMIUM_BY_IR_MAP_ID = "select riderPremium from RiderPremium riderPremium where riderPremium.insuranceRiderMap.id = :irMapId";

	String FETCH_PLAN_BY_CARRIER_ID = "select plan from PolicyPlan plan where plan.carrier.id = :carrierId";

	String DELETE_BANK_TXN = "delete from BankTransactionEntity bnkTxn where bnkTxn.account.id  = :accountId and bnkTxn.statementId = :stmtId";
	
	String DELETE_CARD_TXN = "delete from CardTransactionEntity cardTxn where cardTxn.account.id  = :accountId and cardTxn.statementId = :stmtId";

	String DELETE_LOAN_TXN = "delete from LoanTransactionEntity loanTxn where loanTxn.account.id  = :accountId and loanTxn.statementId = :stmtId";

	String DELETE_INVESTMENT_TXN = "delete from InvestmentTransactionEntity invTxn where invTxn.account.id  = :accountId and invTxn.statementId = :stmtId";

	String DELETE_HOLDING_ASSET = "delete from HoldingAssetEntity asset where asset.account.id  = :accountId and asset.statementId = :stmtId";

	String DELETE_SECURITY_MASTER = "delete from SecurityMaster sm where sm.account.id = :accountId and sm.statementId = :stmtId";

	// for History table
	String FETCH_BANK_ACCOUNT_HISTOTY  = "select history from BankAccountHistory history where history.user.id = :userId and "
			+ "history.accountHash = :accountHash order by history.billDate DESC";
	
	String FETCH_CARD_ACCOUNT_HISTOTY = "select history from CardAccountHistory history where history.user.id = :userId and "
			+ "history.accountHash = :accountHash order by history.billDate DESC";

	String FETCH_INVESTMENT_ACCOUNT_HISTOTY = "select history from InvestmentAccountHistoryEntity history where history.user.id = :userId and "
			+ "history.accountHash = :accountHash order by history.billDate DESC";
	
	String FETCH_LOAN_ACCOUNT_HISTOTY = "select history from LoanAccountHistory history where "
			+ "history.accountHash = :accountHash";

	String FETCH_FD_ACCOUNT_HISTOTY =  "select history from FixedDepositAccountHistoryEntity history where "
			+ "history.accountHash = :accountHash";

	// Delete From History
	String DELETE_BANK_ACCOUNT_HISTOTY = "delete from BankAccountHistory history where history.accountHash = :accountHash and history.billDate = :stmtDate and history.user.id = :userId";

	String DELETE_CARD_ACCOUNT_HISTOTY = "delete from CardAccountHistory history where history.accountHash = :accountHash and history.billDate = :stmtDate and history.user.id = :userId";

	String DELETE_INVESTMENT_ACCOUNT_HISTOTY = "delete from InvestmentAccountHistoryEntity history where history.accountHash = :accountHash and history.billDate = :stmtDate and history.user.id = :userId";

	String GET_STATEMENT_REPO_BY_ACCNO_STMTDATE_INSTID = "select repo from StatementRepositoryEntity repo where repo.user.id  = :userId and repo.statementDate = :stmtDate and"
			+ "	repo.institution.id = :instId";

	String GET_PDF_INSTITUTION_BY_INSTITUTIONNAME_COUNTRYCODE = "select inst from ManualInstitution inst where UPPER(inst.name) = :institutionName and inst.country.code = :countryCode and inst.enabled = true";
	
	String GET_PDF_INSTITUTION_BY_INSTITUTIONNAME = "select inst from ManualInstitution inst where UPPER(inst.name) = :institutionName and inst.enabled = true";

	// For Batch File 
	String FETCH_BATCH_FILE_DETAIL_BY_USER = "select bfd from BatchFileDetail bfd where bfd.user.id = :userId";
	
	String FETCH_BATCH_FILE_DETAIL_BY_BATCH_ID = "select bfd from BatchFileDetail bfd where bfd.user.id = :userId and bfd.batchId = :batchId and bfd.institutionName = :instName and bfd.status = 'Processing'";

	String FETCH_BATCH_FILE_DETAIL_BY_USERID_BATCH_ID = "select bfd from BatchFileDetail bfd where bfd.user.id = :userId and bfd.batchId = :batchId";
}
