package com.pisight.pimoney.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.AccountGroupDetails;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankAccountHistory;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.BatchFileDetail;
import com.pisight.pimoney.repository.entities.Budget;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardAccountHistory;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.Carrier;
import com.pisight.pimoney.repository.entities.CurrencyRate;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountHistoryEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetHistory;
import com.pisight.pimoney.repository.entities.Insurance;
import com.pisight.pimoney.repository.entities.InsuranceRiderMap;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountHistoryEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanAccountHistory;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.repository.entities.PolicyPlan;
import com.pisight.pimoney.repository.entities.Property;
import com.pisight.pimoney.repository.entities.Rider;
import com.pisight.pimoney.repository.entities.RiderPremium;
import com.pisight.pimoney.repository.entities.SecurityMaster;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;

public interface K2DAO {
	
	User createOrUpdateUser(User user);
	
	AccountBaseEntity saveAccount(AccountBaseEntity account);
	
	InvestmentAccountEntity saveInvestmentAccount(InvestmentAccountEntity account);
	
	BankAccountEntity saveBankAccount(BankAccountEntity account);
	
	CardAccountEntity saveCardAccount(CardAccountEntity account);
	
	LoanAccountEntity saveLoanAccount(LoanAccountEntity account);
	
	FixedDepositAccountEntity saveFDAccount(FixedDepositAccountEntity account);
	
	HoldingAssetEntity saveAsset(HoldingAssetEntity asset);
	
	TransactionBaseEntity saveTransaction(TransactionBaseEntity transaction);
	
	InvestmentTransactionEntity saveInvestmentTransaction(InvestmentTransactionEntity transaction);
	
	BankTransactionEntity saveBankTransaction(BankTransactionEntity transaction);
	
	CardTransactionEntity saveCardTransaction(CardTransactionEntity transaction);
	
	LoanTransactionEntity saveLoanTransaction(LoanTransactionEntity transaction);
	
	SecurityMaster saveSecurityMaster(SecurityMaster master);
	
	void deleteInvestmentAccount(InvestmentAccountEntity account);
	
	void deleteBankAccount(BankAccountEntity account);
	
	void deleteCardAccount(CardAccountEntity account);
	
	void deleteLoanAccount(LoanAccountEntity account);
	
	void deleteFDAccount(FixedDepositAccountEntity account);
	
	void deleteHoldingAsset(HoldingAssetEntity asset);
	
	void deleteInvestmentTransaction(InvestmentTransactionEntity transaction);
	
	void deleteBankTransaction(BankTransactionEntity transaction);
	
	void deleteCardTransaction(CardTransactionEntity transaction);
	
	void deleteLoanTransaction(LoanTransactionEntity transaction);
	
	void deleteSecurityMaster(SecurityMaster master);
	
	Property saveProperty(Property property);
	
	Property fetchPropertyById(UUID id);
	
	void deleteProperty(Property property);
	
	CurrencyRate fetchRateByCurrencyAndDate(String currency, Date date);
	
	List<?> fetchRateByDate(Date date);
	
	CurrencyRate saveRate(CurrencyRate rate);
	
	Budget saveBudget(Budget budget);
	
	List<Budget> fetchBudgetsByUserId(UUID userId);
	
	Budget fetchBudgetById(UUID id);
	
	Budget fetchBudgetByCategoryAndDATE(UUID userId, String category, Date budgetDate);
	
	List<Budget> getFutureBudgets(UUID userId, String category, Date budgetDate);
	
	List<Budget> fetchBudgetsByDATE(UUID userId, Date budgetDate);
	
	int deleteBankTxns(UUID accountId);
	
	int deleteCardTxns(UUID accountId);
	
	int deleteLoanTxns(UUID accountId);
	
	int deleteInvTxns(UUID accountId);
	
	int deleteHoldingAssets(UUID accountId);
	
	int deleteBankTxns(UUID accountId, String stmtId);
	
	int deleteCardTxns(UUID accountId, String stmtId);
	
	int deleteLoanTxns(UUID accountId, String stmtId);
	
	int deleteInvTxns(UUID accountId, String stmtId);
	
	int deleteHoldingAssets(UUID accountId, String stmtId);
	
	StatementParsingDetail saveStatementParsingDetail(StatementParsingDetail statementParsingDetail);
	
	List<StatementParsingDetail> fetchDetailsByDate(Date date);
	
	AccountGroupDetails fetchDetails(UUID userId, UUID instId, String accountNumber);
	
	AccountGroupDetails saveAccountGroupDetails(AccountGroupDetails details);
	
	List<AccountGroupDetails> fetchDetailsByUserId(UUID userId);
	
	List<AccountGroupDetails> fetchDetailsByGroupId(UUID userId, String groupId);
	
	List<StatementRepositoryEntity> fetchStmtRepoByGroupId(UUID userId, String groupId);
	
	void deleteAccountGroupDetails(AccountGroupDetails accountGroupDetail);
	
	Carrier saveCarrier(Carrier carrier);
	
	Carrier fetchCarrierById(UUID carrierId);
	
	List<Carrier> fetchCarriers();
	
	PolicyPlan savePolicyPlan(PolicyPlan plan);
	
	List<PolicyPlan> fetchPolicyPlans();
	
	PolicyPlan fetchPlanById(UUID planId);
	
	Rider addRider(Rider rider);
	
	List<Rider> fetchRiderByCarrierAndPlanId(UUID carrierId, UUID planId);
	
	Insurance addInsurance(Insurance insurance);
	
	Rider fetchRiderById(UUID riderId);
	
	InsuranceRiderMap saveInsuranceRiderMap(InsuranceRiderMap insuranceRiderMap);
	
	RiderPremium saveRiderPremium(RiderPremium riderPremium);
	
	List<Insurance> fetchInsuranceByUserId(UUID userId);
	
	RiderPremium fetchRiderPremiumByIRMapId(UUID irMapId);
	
	List<PolicyPlan> fetchPlanByCarrierId(UUID carrierId);

	Integer deleteSecurityMaster(UUID accountId, String stmtId);

	List<BankAccountHistory> fetchBankAccountHistory(String accountHash, UUID userId);
	
	void deleteBankHistory(BankAccountHistory history);
	
	BankAccountHistory fetchBankHistory(String fingerPrint, String accountHash);

	List<CardAccountHistory> fetchCardAccountHistory(String accountHash, UUID userId);

	CardAccountHistory fetchCardHistory(String fingerPrint, String accountHash);

	void deleteCardHistory(CardAccountHistory currentRecord);

	List<InvestmentAccountHistoryEntity> fetchInvestmentAccountHistory(String accountHash, UUID userId);
	
	InvestmentAccountHistoryEntity fetchInvestmentHistory(String fingerPrint, String accountHash);

	void deleteInvestmentHistory(InvestmentAccountHistoryEntity history);

	List<LoanAccountHistory> fetchLoanAccountHistory(String accountHash);
	
	LoanAccountHistory fetchLoanHistory(String fingerPrint, String accountHash);

	void deleteLoanHistory(LoanAccountHistory history);

	List<FixedDepositAccountHistoryEntity> fetchFDAccountHistory(String accountHash);
	
	FixedDepositAccountHistoryEntity fetchFDHistory(String fingerPrint, String accountHash);

	void deleteFDHistory(FixedDepositAccountHistoryEntity history);
	
	void deleteBankHistory(String accountHash, Date stmtDate, UUID userId);
	
	void deleteCardHistory(String accountHash, Date stmtDate, UUID userId);
	
	void deleteInvestmentHistory(String accountHash, Date stmtDate, UUID userId);

	StatementRepositoryEntity fetchStatementRepository(Date billDate, UUID instId, UUID userId);

	List<HoldingAssetHistory> fetchAssetHistory(String id);

	void deleteHoldingAssetHistory(HoldingAssetHistory history);

	ManualInstitution fetchPDFInstitutionsByInstitutionNameAndCountryCode(String upperCase, String upperCase2);
	
	List<ManualInstitution> fetchPDFInstitutionsByInstitutionName(String instName);

	BatchFileDetail findByBatchId(UUID userId, String batchId, String instName);

	BatchFileDetail saveBatchFileDetail(BatchFileDetail batchFileDetail);

	List<BatchFileDetail> fetchBatchByuserId(UUID userId);
	
	LoanAccountEntity fetchLoanById(UUID id);

	List<BatchFileDetail> fetchBatchByuserIdAndBatchId(UUID userId, String batchId);

	void deleteBatchFileDetail(BatchFileDetail batchFileDetail);
	
}
