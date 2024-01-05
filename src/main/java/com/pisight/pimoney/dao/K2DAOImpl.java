package com.pisight.pimoney.dao;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pisight.pimoney.repository.AccountGroupDetailsRepository;
import com.pisight.pimoney.repository.BankHistoryRepository;
import com.pisight.pimoney.repository.BatchFileDetailRepository;
import com.pisight.pimoney.repository.BudgetRepository;
import com.pisight.pimoney.repository.CardHistoryRepository;
import com.pisight.pimoney.repository.CarrierRepository;
import com.pisight.pimoney.repository.CurrencyRateRepo;
import com.pisight.pimoney.repository.FixedDepositHistoryRepository;
import com.pisight.pimoney.repository.HoldingAssetHistoryRepository;
import com.pisight.pimoney.repository.HoldingAssetRepository;
import com.pisight.pimoney.repository.InsuranceRepository;
import com.pisight.pimoney.repository.InsuranceRiderMapRepository;
import com.pisight.pimoney.repository.InvestmentHistoryRepository;
import com.pisight.pimoney.repository.K2Repository;
import com.pisight.pimoney.repository.LoanAccountRepository;
import com.pisight.pimoney.repository.LoanHistoryRepository;
import com.pisight.pimoney.repository.ManualInstitutionRepository;
import com.pisight.pimoney.repository.PolicyPlanRepository;
import com.pisight.pimoney.repository.PropertyRepository;
import com.pisight.pimoney.repository.RiderPremiumRepository;
import com.pisight.pimoney.repository.RiderRepository;
import com.pisight.pimoney.repository.SecurityMasterRepo;
import com.pisight.pimoney.repository.StatementParsingDetailRepository;
import com.pisight.pimoney.repository.StatementRepoEntityRepository;
import com.pisight.pimoney.repository.TransactionRepository;
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
import com.pisight.pimoney.util.ScriptLogger;


@Repository
public class K2DAOImpl implements K2DAO{

	@PersistenceContext(unitName="k2EntityManager")
	EntityManager k2EntityManager;

	@Autowired
	K2Repository k2Repository;
	
	@Autowired
	SecurityMasterRepo secMasterRepo;
	
	@Autowired
	PropertyRepository propertyRepo;
	
	@Autowired
	CurrencyRateRepo rateRepo;
	
	@Autowired
	BudgetRepository budgetRepo;
	
	@Autowired
	TransactionRepository txnRepo;
	
	@Autowired
	HoldingAssetRepository holdingAssetRepo;
	
	@Autowired
	StatementParsingDetailRepository stmtParsingDetailRepo;
	
	@Autowired
	private AccountGroupDetailsRepository accountGroupDetailsRepo = null;
	
	@Autowired
	private StatementRepoEntityRepository stmtRepo = null;
	
	@Autowired
	private CarrierRepository carrierRepo = null;
	
	@Autowired
	private PolicyPlanRepository policyPlanRepo = null;
	
	@Autowired
	private RiderRepository riderRepo = null;
	
	@Autowired
	private InsuranceRepository insuranceRepo = null;
	
	@Autowired
	private InsuranceRiderMapRepository insuranceRiderMapRepo = null;
	
	@Autowired
	private RiderPremiumRepository riderPremiumRepo = null;
	
	@Autowired
	private BankHistoryRepository bankHistoryRepo = null;
	
	@Autowired
	private CardHistoryRepository cardHistoryRepo = null;
	
	@Autowired
	private LoanHistoryRepository loanHistoryRepo = null;
	
	@Autowired
	private InvestmentHistoryRepository invHistoryRepo = null;
	
	@Autowired
	private FixedDepositHistoryRepository fdHistoryRepo = null;
	
	@Autowired
	private HoldingAssetHistoryRepository holdingAssetHistoryRepo = null;
	
	@Autowired
	private ManualInstitutionRepository manualInstRepo = null;
	
	@Autowired
	private BatchFileDetailRepository batchFileDetailRepo = null;
	
	@Autowired
	private LoanAccountRepository loanAccountRepo = null;

	@Override
	@Transactional
	public User createOrUpdateUser(User user) {

		User userOld = k2Repository.fetchUser(user.getUsername());
		if(userOld != null){
			ScriptLogger.writeInfo("updating user...");
			user.setId(userOld.getId());
			user = k2EntityManager.merge(user);
		}else{
			ScriptLogger.writeInfo("creating user...");
			k2EntityManager.persist(user);
		}
		return user;
	}

	@Override
	@Transactional
	public InvestmentAccountEntity saveInvestmentAccount(InvestmentAccountEntity account) {

		InvestmentAccountEntity accountOld = k2Repository.fetchInvestmentAccount(account.getId());
		if(accountOld != null){
			ScriptLogger.writeInfo("updating inv account...");
			account.setId(accountOld.getId());
			account = k2EntityManager.merge(account);
		}else{
			ScriptLogger.writeInfo("creating inv account...");
			k2EntityManager.persist(account);
		}
		return account;
	}
	
	@Override
	@Transactional
	public BankAccountEntity saveBankAccount(BankAccountEntity account) {

		BankAccountEntity accountOld = k2Repository.fetchBankAccount(account.getId());
		if(accountOld != null){
			ScriptLogger.writeInfo("updating bank account...");
			account.setId(accountOld.getId());
			account = k2EntityManager.merge(account);
		}else{
			ScriptLogger.writeInfo("creating bank account...");
			k2EntityManager.persist(account);
		}
		return account;
	}
	
	@Override
	@Transactional
	public CardAccountEntity saveCardAccount(CardAccountEntity account) {

		CardAccountEntity accountOld = k2Repository.fetchCardAccount(account.getId());
		if(accountOld != null){
			ScriptLogger.writeInfo("updating card account...");
			account.setId(accountOld.getId());
			account = k2EntityManager.merge(account);
		}else{
			ScriptLogger.writeInfo("creating card account...");
			k2EntityManager.persist(account);
		}
		return account;
	}
	
	@Override
	@Transactional
	public LoanAccountEntity saveLoanAccount(LoanAccountEntity account) {

		LoanAccountEntity accountOld = k2Repository.fetchLoanAccount(account.getId());
		if(accountOld != null){
			ScriptLogger.writeInfo("updating account...");
			account.setId(accountOld.getId());
			account = k2EntityManager.merge(account);
		}else{
			ScriptLogger.writeInfo("creating account...");
			k2EntityManager.persist(account);
		}
		return account;
	}
	
	@Override
	@Transactional
	public FixedDepositAccountEntity saveFDAccount(FixedDepositAccountEntity account) {

		FixedDepositAccountEntity accountOld = k2Repository.fetchFixedDepositAccount(account.getId());
		if(accountOld != null){
			ScriptLogger.writeInfo("updating account...");
			account.setId(accountOld.getId());
			account = k2EntityManager.merge(account);
		}else{
			ScriptLogger.writeInfo("creating account...");
			k2EntityManager.persist(account);
		}
		return account;
	}

	@Override
	@Transactional
	public HoldingAssetEntity saveAsset(HoldingAssetEntity asset) {

		HoldingAssetEntity assetOld = k2Repository.fetchHoldingAsset(asset.getId());

		if(assetOld != null){
			ScriptLogger.writeInfo("updating asset...");
			asset.setId(assetOld.getId());
			asset = k2EntityManager.merge(asset);
		}else{
			ScriptLogger.writeInfo("creating asset...");
			k2EntityManager.persist(asset);
		}
		return asset;
	}

	@Override
	@Transactional
	public BankTransactionEntity saveBankTransaction(BankTransactionEntity transaction) {

		BankTransactionEntity transactionOld = k2Repository.fetchBankTransaction(transaction.getId());

		if(transactionOld != null){
			ScriptLogger.writeInfo("updating bank transaction...");
			transaction.setId(transactionOld.getId());
			transaction = k2EntityManager.merge(transaction);
		}else{
			ScriptLogger.writeInfo("creating bank transaction...");
			k2EntityManager.persist(transaction);
		}
		return transaction;
	}
	
	@Override
	@Transactional
	public CardTransactionEntity saveCardTransaction(CardTransactionEntity transaction) {

		CardTransactionEntity transactionOld = k2Repository.fetchCardTransaction(transaction.getId());

		if(transactionOld != null){
			ScriptLogger.writeInfo("updating card transaction...");
			transaction.setId(transactionOld.getId());
			transaction = k2EntityManager.merge(transaction);
		}else{
			ScriptLogger.writeInfo("creating card transaction...");
			k2EntityManager.persist(transaction);
		}
		return transaction;
	}
	
	@Override
	@Transactional
	public LoanTransactionEntity saveLoanTransaction(LoanTransactionEntity transaction) {

		LoanTransactionEntity transactionOld = k2Repository.fetchLoanTransaction(transaction.getId());

		if(transactionOld != null){
			ScriptLogger.writeInfo("updating loan transaction...");
			transaction.setId(transactionOld.getId());
			transaction = k2EntityManager.merge(transaction);
		}else{
			ScriptLogger.writeInfo("creating loan transaction...");
			k2EntityManager.persist(transaction);
		}
		return transaction;
	}
	
	@Override
	@Transactional
	public InvestmentTransactionEntity saveInvestmentTransaction(InvestmentTransactionEntity transaction) {

		InvestmentTransactionEntity transactionOld = k2Repository.fetchInvestmentTransaction(transaction.getId());

		if(transactionOld != null){
			ScriptLogger.writeInfo("updating inv transaction...");
			transaction.setId(transactionOld.getId());
			transaction = k2EntityManager.merge(transaction);
		}else{
			ScriptLogger.writeInfo("creating inv transaction...");
			k2EntityManager.persist(transaction);
		}
		return transaction;
	}

	@Override
	@Transactional
	public SecurityMaster saveSecurityMaster(SecurityMaster master) {

		SecurityMaster masterOld = null;
		if(master.getId() != null) {
			masterOld = secMasterRepo.findById(master.getId()).get();
		}

		if(masterOld != null){
			ScriptLogger.writeInfo("updating master...");
			master.setId(masterOld.getId());
			master = k2EntityManager.merge(master);
		}else{
			ScriptLogger.writeInfo("creating master...");
			k2EntityManager.persist(master);
		}
		return master;
	}

	@Override
	@Transactional
	public void deleteInvestmentAccount(InvestmentAccountEntity account) {
		
		ScriptLogger.writeInfo("deleting investment account...");
		k2EntityManager.remove(account);
	}
	
	@Override
	@Transactional
	public void deleteBankAccount(BankAccountEntity account) {
		
		ScriptLogger.writeInfo("deleting bank account...");
		k2EntityManager.remove(account);
	}
	
	@Override
	@Transactional
	public void deleteCardAccount(CardAccountEntity account) {
		
		ScriptLogger.writeInfo("deleting card account...");
		k2EntityManager.remove(account);
	}
	
	@Override
	@Transactional
	public void deleteLoanAccount(LoanAccountEntity account) {
		
		ScriptLogger.writeInfo("deleting loan account...");
		k2EntityManager.remove(account);
	}
	
	@Override
	@Transactional
	public void deleteFDAccount(FixedDepositAccountEntity account) {
		
		ScriptLogger.writeInfo("deleting FD account...");
		k2EntityManager.remove(account);
	}

	@Override
	@Transactional
	public void deleteHoldingAsset(HoldingAssetEntity asset) {
		
		ScriptLogger.writeInfo("deleting holding asset...");
		k2EntityManager.remove(asset);
	}

	@Override
	@Transactional
	public void deleteInvestmentTransaction(InvestmentTransactionEntity transaction) {
		
		ScriptLogger.writeInfo("deleting investment transaction...");
		k2EntityManager.remove(transaction);
	}
	
	@Override
	@Transactional
	public void deleteBankTransaction(BankTransactionEntity transaction) {
		
		ScriptLogger.writeInfo("deleting bank transaction...");
		k2EntityManager.remove(transaction);
	}
	
	@Override
	@Transactional
	public void deleteCardTransaction(CardTransactionEntity transaction) {
		
		ScriptLogger.writeInfo("deleting card transaction...");
		k2EntityManager.remove(transaction);
	}
	
	@Override
	@Transactional
	public void deleteLoanTransaction(LoanTransactionEntity transaction) {
		
		ScriptLogger.writeInfo("deleting loan transaction...");
		k2EntityManager.remove(transaction);
	}

	@Override
	@Transactional
	public void deleteSecurityMaster(SecurityMaster master) {
		
		ScriptLogger.writeInfo("deleting security master...");
		k2EntityManager.remove(master);
	}

	@Override
	@Transactional
	public AccountBaseEntity saveAccount(AccountBaseEntity account) {
		
		if(account instanceof BankAccountEntity) {
			return saveBankAccount((BankAccountEntity) account);
		}
		else if(account instanceof CardAccountEntity) {
			return saveCardAccount((CardAccountEntity) account);
		}
		else if(account instanceof LoanAccountEntity) {
			return saveLoanAccount((LoanAccountEntity) account);
		}
		else if(account instanceof FixedDepositAccountEntity) {
			return saveFDAccount((FixedDepositAccountEntity) account);
		}
		else if(account instanceof InvestmentAccountEntity) {
			return saveInvestmentAccount((InvestmentAccountEntity) account);
		}
		return null;
	}

	@Override
	@Transactional
	public TransactionBaseEntity saveTransaction(TransactionBaseEntity transaction) {
		
		if(transaction instanceof BankTransactionEntity) {
			return saveBankTransaction((BankTransactionEntity) transaction);
		}
		else if(transaction instanceof CardTransactionEntity) {
			return saveCardTransaction((CardTransactionEntity) transaction);
		}
		else if(transaction instanceof LoanTransactionEntity) {
			return saveLoanTransaction((LoanTransactionEntity) transaction);
		}
		else if(transaction instanceof InvestmentTransactionEntity) {
			return saveInvestmentTransaction((InvestmentTransactionEntity) transaction);
		}
		return null;
	}

	@Override
	public Property saveProperty(Property property) {
		return propertyRepo.save(property);
	}

	@Override
	public Property fetchPropertyById(UUID id) {
		return propertyRepo.fetchPropertyById(id);
	}

	@Override
	public CurrencyRate fetchRateByCurrencyAndDate(String currency, Date date) {
		return rateRepo.findFirstByCurrencyAndDate(currency, date);
	}

	@Override
	public CurrencyRate saveRate(CurrencyRate rate) {
		return rateRepo.save(rate);
	}

	@Override
	public List<?> fetchRateByDate(Date date) {
		return rateRepo.findByDate(date);
	}

	@Override
	public Budget saveBudget(Budget budget) {
		// TODO Auto-generated method stub
		return budgetRepo.save(budget);
	}

	@Override
	public Budget fetchBudgetById(UUID id) {
		// TODO Auto-generated method stub
		return budgetRepo.fetchBudgetById(id);
	}

	@Override
	public Budget fetchBudgetByCategoryAndDATE(UUID userId, String category, Date budgetDate) {
		// TODO Auto-generated method stub
		return budgetRepo.fetchBudgetByCategoryAndDATE(userId, category, budgetDate);
	}

	@Override
	public List<Budget> getFutureBudgets(UUID userId, String category, Date budgetDate) {
		// TODO Auto-generated method stub
		return budgetRepo.updateFutureBudgets(userId, category, budgetDate);	
	}

	@Override
	public List<Budget> fetchBudgetsByDATE(UUID userId, Date budgetDate) {
		// TODO Auto-generated method stub
		return budgetRepo.fetchBudgetsByDATE(userId, budgetDate);
	}

	@Override
	public List<Budget> fetchBudgetsByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return budgetRepo.fetchBudgetsByUserId(userId);
	}

	@Override
	public int deleteBankTxns(UUID accountId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteBankTxns(accountId);
	}

	@Override
	public int deleteCardTxns(UUID accountId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteCardTxns(accountId);
	}

	@Override
	public int deleteLoanTxns(UUID accountId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteLoanTxns(accountId);
	}

	@Override
	public int deleteInvTxns(UUID accountId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteInvTxns(accountId);
	}

	@Override
	public int deleteHoldingAssets(UUID accountId) {
		// TODO Auto-generated method stub
		return holdingAssetRepo.deleteHoldingAssets(accountId);
	}
	
	@Override
	public int deleteBankTxns(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteBankTxns(accountId, stmtId);
	}

	@Override
	public int deleteCardTxns(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteCardTxns(accountId, stmtId);
	}

	@Override
	public int deleteLoanTxns(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteLoanTxns(accountId, stmtId);
	}

	@Override
	public int deleteInvTxns(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return txnRepo.deleteInvTxns(accountId, stmtId);
	}

	@Override
	public int deleteHoldingAssets(UUID accountId, String stmtId) {
		// TODO Auto-generated method stub
		return holdingAssetRepo.deleteHoldingAssets(accountId, stmtId);
	}

	@Override
	public StatementParsingDetail saveStatementParsingDetail(StatementParsingDetail statementParsingDetail) {
		// TODO Auto-generated method stub
		return stmtParsingDetailRepo.save(statementParsingDetail);
	}

	@Override
	public List<StatementParsingDetail> fetchDetailsByDate(Date date) {
		// TODO Auto-generated method stub
		return stmtParsingDetailRepo.fetchDetailsByDate(date);
	}

	@Override
	public AccountGroupDetails fetchDetails(UUID userId, UUID instId, String accountNumber) {
		// TODO Auto-generated method stub
		return accountGroupDetailsRepo.fetchDetails(userId, instId, accountNumber);
	}

	@Override
	public AccountGroupDetails saveAccountGroupDetails(AccountGroupDetails details) {
		// TODO Auto-generated method stub
		return accountGroupDetailsRepo.save(details);
	}

	@Override
	public List<AccountGroupDetails> fetchDetailsByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return accountGroupDetailsRepo.fetchDetailsByUserId(userId);
	}

	@Override
	public List<StatementRepositoryEntity> fetchStmtRepoByGroupId(UUID userId, String groupId) {
		// TODO Auto-generated method stub
		return stmtRepo.fetchStmtRepoByGroupId(userId, groupId);
	}

	@Override
	public void deleteAccountGroupDetails(AccountGroupDetails accountGroupDetail) {
		// TODO Auto-generated method stub
		accountGroupDetailsRepo.delete(accountGroupDetail);
	}

	@Override
	public List<AccountGroupDetails> fetchDetailsByGroupId(UUID userId, String groupId) {
		// TODO Auto-generated method stub
		return accountGroupDetailsRepo.fetchDetailsByGroupId(userId, groupId);
	}

	@Override
	public Carrier saveCarrier(Carrier carrier) {
		// TODO Auto-generated method stub
		return carrierRepo.save(carrier);
	}

	@Override
	public PolicyPlan savePolicyPlan(PolicyPlan plan) {
		// TODO Auto-generated method stub
		return policyPlanRepo.save(plan);
	}

	@Override
	public List<Carrier> fetchCarriers() {
		// TODO Auto-generated method stub
		return carrierRepo.findAll();
	}

	@Override
	public Carrier fetchCarrierById(UUID carrierId) {
		// TODO Auto-generated method stub
		return carrierRepo.fetchCarrierById(carrierId);
	}

	@Override
	public List<PolicyPlan> fetchPolicyPlans() {
		// TODO Auto-generated method stub
		return policyPlanRepo.findAll();
	}

	@Override
	public PolicyPlan fetchPlanById(UUID planId) {
		// TODO Auto-generated method stub
		return policyPlanRepo.findById(planId).get();
	}

	@Override
	public Rider addRider(Rider rider) {
		// TODO Auto-generated method stub
		return riderRepo.save(rider);
	}

	@Override
	public Insurance addInsurance(Insurance insurance) {
		// TODO Auto-generated method stub
		return insuranceRepo.save(insurance);
	}

	@Override
	public List<Rider> fetchRiderByCarrierAndPlanId(UUID carrierId, UUID planId) {
		// TODO Auto-generated method stub
		return riderRepo.fetchRiderByCarrierAndPlanId(carrierId, planId);
	}

	@Override
	public Rider fetchRiderById(UUID riderId) {
		// TODO Auto-generated method stub
		return riderRepo.findById(riderId).get();
	}

	@Override
	public InsuranceRiderMap saveInsuranceRiderMap(InsuranceRiderMap insuranceRiderMap) {
		return insuranceRiderMapRepo.save(insuranceRiderMap);
	}

	@Override
	public RiderPremium saveRiderPremium(RiderPremium riderPremium) {
		return riderPremiumRepo.save(riderPremium);
	}

	@Override
	public List<Insurance> fetchInsuranceByUserId(UUID userId) {
		return insuranceRepo.fetchInsuranceByUserId(userId);
	}

	@Override
	public RiderPremium fetchRiderPremiumByIRMapId(UUID irMapId) {
		return riderPremiumRepo.fetchRiderPremiumByIRMapId(irMapId);
	}

	@Override
	public List<PolicyPlan> fetchPlanByCarrierId(UUID carrierId) {
		return policyPlanRepo.fetchPlanByCarrierId(carrierId);
	}

	@Override
	public void deleteProperty(Property property) {
		propertyRepo.delete(property);
	}

	@Override
	public Integer deleteSecurityMaster(UUID accountId, String stmtId) {
		return holdingAssetRepo.deleteSecurityMaster(accountId, stmtId);
	}

	@Override
	public List<BankAccountHistory> fetchBankAccountHistory(String accountHash, UUID userId) {
		return bankHistoryRepo.fetchBankAccountHistory(accountHash, userId);
	}

	@Override
	public void deleteBankHistory(BankAccountHistory history) {
		bankHistoryRepo.delete(history);
	}

	@Override
	public BankAccountHistory fetchBankHistory(String fingerPrint, String accountHash) {
		return bankHistoryRepo.findByFingerprintAndAccountHash(fingerPrint, accountHash);
	}

	@Override
	public List<CardAccountHistory> fetchCardAccountHistory(String accountHash, UUID userId) {
		return cardHistoryRepo.fetchCardAccountHistory(accountHash, userId);
	}

	@Override
	public CardAccountHistory fetchCardHistory(String fingerPrint, String accountHash) {
		return cardHistoryRepo.findByFingerprintAndAccountHash(fingerPrint, accountHash);
	}

	@Override
	public void deleteCardHistory(CardAccountHistory history) {
		cardHistoryRepo.delete(history);
	}

	@Override
	public List<InvestmentAccountHistoryEntity> fetchInvestmentAccountHistory(String accountHash, UUID userId) {
		// TODO Auto-generated method stub
		return invHistoryRepo.fetchInvestmentAccountHistory(accountHash, userId);
	}

	@Override
	public InvestmentAccountHistoryEntity fetchInvestmentHistory(String fingerPrint, String accountHash) {
		// TODO Auto-generated method stub
		return invHistoryRepo.findByFingerprintAndAccountHash(fingerPrint, accountHash);
	}

	@Override
	public void deleteInvestmentHistory(InvestmentAccountHistoryEntity history) {
		invHistoryRepo.delete(history);
	}

	@Override
	public List<LoanAccountHistory> fetchLoanAccountHistory(String accountHash) {
		return loanHistoryRepo.fetchLoanAccountHistory(accountHash);
	}

	@Override
	public LoanAccountHistory fetchLoanHistory(String fingerPrint, String accountHash) {
		return loanHistoryRepo.findByFingerprintAndAccountHash(fingerPrint, accountHash);
	}

	@Override
	public void deleteLoanHistory(LoanAccountHistory history) {
		loanHistoryRepo.delete(history);
	}

	@Override
	public List<FixedDepositAccountHistoryEntity> fetchFDAccountHistory(String accountHash) {
		return fdHistoryRepo.fetchFDAccountHistory(accountHash);
	}

	@Override
	public FixedDepositAccountHistoryEntity fetchFDHistory(String fingerPrint, String accountHash) {
		return fdHistoryRepo.findByFingerprintAndAccountHash(fingerPrint, accountHash);
	}

	@Override
	public void deleteFDHistory(FixedDepositAccountHistoryEntity history) {
		fdHistoryRepo.delete(history);
	}

	@Override
	public void deleteBankHistory(String accountHash, Date stmtDate, UUID userId) {
		bankHistoryRepo.deleteBankHistory(accountHash, stmtDate, userId);
	}

	@Override
	public void deleteCardHistory(String accountHash, Date stmtDate, UUID userId) {
		cardHistoryRepo.deleteCardHistory(accountHash, stmtDate, userId);
	}

	@Override
	public void deleteInvestmentHistory(String accountHash, Date stmtDate, UUID userId) {
		invHistoryRepo.deleteInvestmentHistory(accountHash, stmtDate, userId);
	}

	@Override
	public StatementRepositoryEntity fetchStatementRepository(Date billDate, UUID instId, UUID userId) {
		return stmtRepo.fetchStmtRepo(billDate, instId, userId);
	}

	@Override
	public List<HoldingAssetHistory> fetchAssetHistory(String stmtId) {
		return holdingAssetHistoryRepo.findBystatementId(stmtId);
	}

	@Override
	public void deleteHoldingAssetHistory(HoldingAssetHistory history) {
		holdingAssetHistoryRepo.delete(history);
	}

	@Override
	public ManualInstitution fetchPDFInstitutionsByInstitutionNameAndCountryCode(String instName, String countryCode) {
		return manualInstRepo.findByInstitutionName(instName, countryCode);
	}
	
	@Override
	public List<ManualInstitution> fetchPDFInstitutionsByInstitutionName(String instName) {
		return manualInstRepo.findByInstitutionName(instName);
	}

	@Override
	public BatchFileDetail findByBatchId(UUID userId, String batchId, String instName) {
		return batchFileDetailRepo.findByBatchId(userId, batchId, instName);
	}

	@Override
	public BatchFileDetail saveBatchFileDetail(BatchFileDetail batchFileDetail) {
		return batchFileDetailRepo.save(batchFileDetail);
	}

	@Override
	public List<BatchFileDetail> fetchBatchByuserId(UUID userId) {
		return batchFileDetailRepo.fetchBatchByuserId(userId);
	}

	@Override
	public LoanAccountEntity fetchLoanById(UUID id) {
		return loanAccountRepo.findById(id).get();
	}

	@Override
	public List<BatchFileDetail> fetchBatchByuserIdAndBatchId(UUID userId, String batchId) {
		return batchFileDetailRepo.fetchBatchByuserIdAndBatchId(userId, batchId);
	}

	@Override
	public void deleteBatchFileDetail(BatchFileDetail batchFileDetail) {
		batchFileDetailRepo.delete(batchFileDetail);
	}
}
