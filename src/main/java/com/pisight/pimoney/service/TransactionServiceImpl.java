package com.pisight.pimoney.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.ParserException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.AddTransactionDTO;
import com.pisight.pimoney.dto.CategorizationRequestDTO;
import com.pisight.pimoney.dto.CategoryRequest;
import com.pisight.pimoney.dto.TransactionCategoryDTO;
import com.pisight.pimoney.dto.TransactionRequest;
import com.pisight.pimoney.repository.CategoryRepository;
import com.pisight.pimoney.repository.K2Repository;
import com.pisight.pimoney.repository.MerchantRepository;
import com.pisight.pimoney.repository.SubCategoryRepository;
import com.pisight.pimoney.repository.TransactionRepository;
import com.pisight.pimoney.repository.UserRepository;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.Category;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.repository.entities.Merchant;
import com.pisight.pimoney.repository.entities.SubCategory;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.EntityUtil;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class TransactionServiceImpl {

	@Autowired
	private UserRepository userRepo = null;

	@Autowired
	private K2Repository k2Repo = null;

	@Autowired
	private CategoryRepository categoryRepo = null;

	@Autowired
	private SubCategoryRepository subCategoryRepo = null;

	@Autowired
	private MerchantRepository merchantRepo = null;

	@Autowired
	private TransactionRepository transactionRepo = null;

	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Autowired
	private K2DAO k2DAO = null;

	@Transactional
	public User fetchUserById(UUID id) {
		return userRepo.findById(id).get();
	}

	@Transactional
	public List<Category> fetchAllCategories(){
		return categoryRepo.findAll();
	}

	@Transactional
	public Category fetchCategoryByName(String name) {
		return categoryRepo.findCategoryByName(name);
	}

	@Transactional
	public SubCategory fetchSubCategoryByNameAndCategoryId(String name, Long categoryId) {
		return subCategoryRepo.findSubCategoryByNameAndCategoryId(name, categoryId);
	}

	@Transactional
	public List<Merchant> fetchAllMerchants(){
		return merchantRepo.findAll();
	}

	@Transactional
	public ManualInstitution fetchManualInstitution(String institutionCode) {
		return k2ServiceImpl.fetchManualInstitutionByCode(institutionCode);
	}

	@Transactional
	public TransactionBaseEntity saveTransaction(TransactionBaseEntity txn) {
		return k2ServiceImpl.saveTransaction(txn);
	}

	@Transactional
	public AccountBaseEntity saveAccount(AccountBaseEntity account) {
		return k2ServiceImpl.saveAccount(account);
	}

	@Transactional
	public AccountBaseEntity fetchManualAccount(String accountType, String userId) {

		AccountBaseEntity entity = null;
		boolean newEntity = false;
		if(Constants.TAG_BANK.equals(accountType)) {
			entity = k2Repo.fetchBankManualAccount(UUID.fromString(userId));
			if(entity == null) {
				entity = new BankAccountEntity();
				newEntity = true;
			}
		}
		else if(Constants.TAG_CARD.equals(accountType)) {
			entity = k2Repo.fetchCardManualAccount(UUID.fromString(userId));
			if(entity == null) {
				entity = new CardAccountEntity();
				newEntity = true;
			}
		}
		else if(Constants.TAG_LOAN.equals(accountType)) {
			entity = k2Repo.fetchLoanManualAccount(UUID.fromString(userId));
			if(entity == null) {
				entity = new LoanAccountEntity();
				newEntity = true;
			}
		}

		if(newEntity) {
			User user = k2ServiceImpl.fetchUserById(UUID.fromString(userId));
			entity.setAccountNumber("Account - Manual");
			entity.setUser(user);
			entity.setManuallyAdded(true);
			entity.setConfirmed(true);
			entity.setStatus(true);
		}

		return entity;
	}

	/**
	 * This method is used to return all trasaction that includes BankTransaction, CardTransaction, InvestmentTransaction, LoanTransaction
	 * @param userId UserId
	 * @param tag Tags
	 * @return Transaction List
	 */
	public List<TransactionBaseEntity> getTransactions(String userId, String tag) {

		List<TransactionBaseEntity> result = new ArrayList<TransactionBaseEntity>();

		User user = fetchUserById(UUID.fromString(userId));

		if(Constants.TAG_BANK.equals(tag)){
			List<BankAccountEntity> accounts = user.getBankAccounts();
			for(BankAccountEntity account: accounts) {
				if(account.isConfirmed() && account.isStatus()) {
					List<BankTransactionEntity> txns = account.getTransactions();
					for(TransactionBaseEntity txn:txns) {
						if(txn.isConfirmed() && txn.isStatus()) {
							result.add(txn);
						}
					}
				}
			}
		}else if(Constants.TAG_CARD.equals(tag)){
			List<CardAccountEntity> accounts = user.getCardAccounts();
			for(CardAccountEntity account: accounts) {
				if(account.isConfirmed() && account.isStatus()) {
					List<CardTransactionEntity> txns = account.getTransactions();
					for(TransactionBaseEntity txn:txns) {
						if(txn.isConfirmed() && txn.isStatus()) {
							result.add(txn);
						}
					}
				}
			}
		}else if(Constants.TAG_LOAN.equals(tag)){
			List<LoanAccountEntity> accounts = user.getLoanAccounts();
			for(LoanAccountEntity account: accounts) {
				if(account.isConfirmed() && account.isStatus()) {
					List<LoanTransactionEntity> txns = account.getTransactions();
					for(TransactionBaseEntity txn:txns) {
						if(txn.isConfirmed() && txn.isStatus()) {
							result.add(txn);
						}
					}
				}
			}
		}else if(Constants.TAG_INVESTMENT.equals(tag)){
			List<InvestmentAccountEntity> accounts = user.getInvestmentAccounts();
			for(InvestmentAccountEntity account: accounts) {
				if(account.isConfirmed() && account.isStatus()) {
					List<InvestmentTransactionEntity> txns = account.getTransactions();
					for(TransactionBaseEntity txn:txns) {
						if(txn.isConfirmed() && txn.isStatus()) {
							result.add(txn);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * This method is used to delete any transaction
	 * @param userId UserId
	 * @param tag Tags
	 * @param txnId Transaction Identifier
	 * @return {@link Boolean}
	 */
	public boolean deleteTransaction(String userId, String tag, String txnId) {
		// TODO Auto-generated method stub
		ScriptLogger.writeInfo("Txn ID = " + txnId);
		TransactionBaseEntity txn = k2ServiceImpl.fetchTransaction(txnId, tag);
		ScriptLogger.writeInfo("Txn = " + txn);
		if(txn != null) {
			txn.setStatus(false);
			k2ServiceImpl.saveTransaction(txn);
			return true;
		}
		return false;
	}
	
	/**
	 * This method is used to delete account related transactions.
	 * @param accountId Account Identifier
	 * @param tag Tags
	 * @return Number of Transaction Deleted
	 */
	public int deleteAccountTransaction(UUID accountId, String tag) {
		ScriptLogger.writeInfo("deleting txns for accountId => " + accountId + " and tag => " + tag);
		
		int result = 0;
		if(Constants.TAG_BANK.equals(tag)) {
			result = k2DAO.deleteBankTxns(accountId);
		}
		else if(Constants.TAG_CARD.equals(tag)) {
			result = k2DAO.deleteCardTxns(accountId);
		}
		else if(Constants.TAG_LOAN.equals(tag)) {
			result = k2DAO.deleteLoanTxns(accountId);
		}
		else if(Constants.TAG_INVESTMENT.equals(tag)) {
			result = k2DAO.deleteInvTxns(accountId);
		}
		return result;
	}
	
	// For Hard Delete of Account
	/**
	 * This method is used to hard delete all transaction related to account
	 * @param accountId Account Identifier
	 * @param tag Tags
	 * @param stmtId Statment Identifier
	 * @return Number of Transaction Deleted
	 */
	public int hardDeleteAccountTransaction(UUID accountId, String tag, String stmtId) {
		ScriptLogger.writeInfo("deleting txns for accountId => " + accountId + " and tag => " + tag);
		
		int result = 0;
		if(Constants.TAG_BANK.equals(tag)) {
			result = k2DAO.deleteBankTxns(accountId, stmtId);
		}
		else if(Constants.TAG_CARD.equals(tag)) {
			result = k2DAO.deleteCardTxns(accountId, stmtId);
		}
		else if(Constants.TAG_LOAN.equals(tag)) {
			result = k2DAO.deleteLoanTxns(accountId, stmtId);
		}
		else if(Constants.TAG_INVESTMENT.equals(tag)) {
			result = k2DAO.deleteInvTxns(accountId, stmtId);
		}
		return result;
	}

	/**
	 * This method is used to show expanses vs category graph
	 * @param request {@link TransactionRequest}
	 * @return JSONObject {@link JSONObject}
	 * @throws JsonProcessingException {@link JsonProcessingException}
	 * @throws ParseException {@link ParseException}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject expensesCategoryGraph(TransactionRequest request) throws JsonProcessingException, ParseException {

		JSONObject response = new JSONObject();

		UUID userId = UUID.fromString(request.getUserId());
		User user = fetchUserById(userId);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = WebUtil.removeTimeStatmp(cal.getTime());
		ScriptLogger.writeInfo("Month Start Date = " + startDate);

		Date endDate = new Date();
		List<BankTransactionEntity> bankTransactions = transactionRepo.getBankTransactionDetailsByDateInterval(userId, startDate, endDate);
		List<CardTransactionEntity> cardTransactions = transactionRepo.getCardTransactionDetailsByDateInterval(userId, startDate, endDate);
		List<LoanTransactionEntity> loanTransactions = transactionRepo.getLoanTransactionDetailsByDateInterval(userId, startDate, endDate);

		List<TransactionBaseEntity> trans = new ArrayList<TransactionBaseEntity>();
		ScriptLogger.writeInfo("bank txn size => " + bankTransactions.size());
		for(BankTransactionEntity txn:bankTransactions) {
			if(txn.isConfirmed() && txn.isStatus() && !"credit".equals(txn.getTransactionType()) && isExpenseTxn(txn.getCategory())) {
				trans.add(txn);
			}
		}
		ScriptLogger.writeInfo("card txn size => " + cardTransactions.size());
		for(CardTransactionEntity txn:cardTransactions) {
			if(txn.isConfirmed() && txn.isStatus() && !"credit".equals(txn.getTransactionType()) && isExpenseTxn(txn.getCategory())) {
				trans.add(txn);
			}
		}
		ScriptLogger.writeInfo("loan txn size => " + loanTransactions.size());
		for(LoanTransactionEntity txn:loanTransactions) {
			if(txn.isConfirmed() && txn.isStatus() && !"credit".equals(txn.getTransactionType()) && isExpenseTxn(txn.getCategory())) {
				trans.add(txn);
			}
		}

		bankTransactions = null;
		cardTransactions = null;
		loanTransactions = null;

		HashMap<String, Double> expensesCategoryMap = new HashMap<>();
		HashMap<TransactionBaseEntity, Double> topTransactionMap = new HashMap<>();
		Double currencyConvertedAmount = 0d;
		Double totalAmount = 0d;
		ScriptLogger.writeInfo("total txns => " + trans.size());
		for(TransactionBaseEntity txn:trans) {
			ScriptLogger.writeInfo("Transaction Category = " + txn.getCategory());
			currencyConvertedAmount = k2ServiceImpl.currencyConvert(txn.getCurrency(),user.getPreferredCurrency(), txn.getAmount(), txn.getTransDate());
			totalAmount += currencyConvertedAmount;
			if(expensesCategoryMap.containsKey(txn.getCategory())) {
				ScriptLogger.writeInfo("Category Already Exist in Map : " + txn.getCategory());
				Double previousAmount = expensesCategoryMap.get(txn.getCategory());
				expensesCategoryMap.put(txn.getCategory(), previousAmount + currencyConvertedAmount);
			}else {
				ScriptLogger.writeInfo("New Category for Map : " + txn.getCategoryObj());
				expensesCategoryMap.put(txn.getCategory(), currencyConvertedAmount);
			}
			topTransactionMap.put(txn, currencyConvertedAmount);
		}

		List<JSONObject> graphDetails = new ArrayList<JSONObject>();
		for (Map.Entry<String,Double> entry : expensesCategoryMap.entrySet()) {
			JSONObject details = new JSONObject();
			details.put("label", entry.getKey());
			details.put("value", entry.getValue().toString());
			graphDetails.add(details);
		}

		List<TransactionBaseEntity> topTransasctionDetails = new ArrayList<TransactionBaseEntity>(); 
		List<Entry<TransactionBaseEntity, Double>> greatest = findGreatest(topTransactionMap, 3);
		for (Entry<TransactionBaseEntity, Double> entry : greatest){
			TransactionBaseEntity txn = k2ServiceImpl.fetchTransaction(entry.getKey().getId().toString(), entry.getKey().getTag());
			txn.setConvertedAmount(entry.getValue().toString());
			topTransasctionDetails.add(txn);
		}

		response.put("graphData", graphDetails);
		response.put("totalExpense", totalAmount.toString());
		response.put("topTransaction", topTransasctionDetails);

		return response;
	}

	/**
	 * This method is used to transaction category graph
	 * @param request {@link TransactionRequest}
	 * @return JSONObject {@link JSONObject}
	 * @throws JsonProcessingException {@link JsonProcessingException}
	 * @throws ParseException {@link ParserException}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject transactionCategoryGraph(TransactionRequest request) throws JsonProcessingException, ParseException {

		JSONObject response = new JSONObject();

		UUID userId = UUID.fromString(request.getUserId());
		User user = fetchUserById(userId);

		ScriptLogger.writeInfo("txn id => " + request.getTransactionId() + " :: " + request.getTag());
		TransactionBaseEntity transaction = k2ServiceImpl.fetchTransaction(request.getTransactionId(), request.getTag());
		ScriptLogger.writeInfo("txn => " +transaction );
		
		List<JSONObject> graphDetails = new ArrayList<JSONObject>();

		if(transaction == null) {
			ScriptLogger.writeError("Transaction is null");
			response.put("graphData", graphDetails);
			return response;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-3);
		for(int i = 3; i>0; i--) {

			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH,1);
			Date startDate = WebUtil.removeTimeStatmp(cal.getTime());
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = WebUtil.removeTimeStatmp(cal.getTime());

			ScriptLogger.writeInfo("Month Start Date = " + startDate);
			ScriptLogger.writeInfo("Month End Date = " + endDate);

			List<BankTransactionEntity> bankTransactions = transactionRepo.getBankTransactionDetailsByDateInterval(userId, startDate, endDate);
			List<CardTransactionEntity> cardTransactions = transactionRepo.getCardTransactionDetailsByDateInterval(userId, startDate, endDate);
			List<LoanTransactionEntity> loanTransactions = transactionRepo.getLoanTransactionDetailsByDateInterval(userId, startDate, endDate);

			List<TransactionBaseEntity> trans = new ArrayList<TransactionBaseEntity>();
			ScriptLogger.writeInfo("bank txn size => " + bankTransactions.size());
			for(BankTransactionEntity txn:bankTransactions) {
				if(txn.isConfirmed() && txn.isStatus() && StringUtils.isNotEmpty(txn.getCategory()) 
						&&txn.getCategory().equals(transaction.getCategory())) {
					trans.add(txn);
				}
			}
			ScriptLogger.writeInfo("cart"
					+ "d txn size => " + cardTransactions.size());
			for(CardTransactionEntity txn:cardTransactions) {
				if(txn.isConfirmed() && txn.isStatus() && txn.getCategory().equals(transaction.getCategory())) {
					trans.add(txn);
				}
			}
			ScriptLogger.writeInfo("loan txn size => " + loanTransactions.size());
			for(LoanTransactionEntity txn:loanTransactions) {
				if(txn.isConfirmed() && txn.isStatus() && txn.getCategory().equals(transaction.getCategory())) {
					trans.add(txn);
				}
			}

			Double totalAmount = 0d;
			ScriptLogger.writeInfo("total txns => " + trans.size());
			for(TransactionBaseEntity txn:trans) {
				Double currencyConvertedAmount = k2ServiceImpl.currencyConvert(txn.getCurrency(),user.getPreferredCurrency(), txn.getAmount(), txn.getTransDate());
				totalAmount += currencyConvertedAmount;
			}
			JSONObject josnObject = new JSONObject();
			josnObject.put("label", new SimpleDateFormat("MMM").format(cal.getTime()));
			josnObject.put("value", totalAmount.toString());

			graphDetails.add(josnObject);		
		}

		response.put("graphData", graphDetails);

		return response;
	}

	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> findGreatest(Map<K, V> map, int n){

		Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>()
		{
			@Override
			public int compare(Entry<K, V> e0, Entry<K, V> e1)
			{
				V v0 = e0.getValue();
				V v1 = e1.getValue();
				return v0.compareTo(v1);
			}
		};
		PriorityQueue<Entry<K, V>> highest = new PriorityQueue<Entry<K,V>>(n, comparator);
		for (Entry<K, V> entry : map.entrySet())
		{
			highest.offer(entry);
			while (highest.size() > n)
			{
				highest.poll();
			}
		}

		List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
		while (highest.size() > 0)
		{
			result.add(highest.poll());
		}
		return result;
	}

	private boolean isExpenseTxn(String category) {
		if(Constants.CATEGORY_INCOME.equals(category) || Constants.CATEGORY_TRANSFER.equals(category) || Constants.CATEGORY_INVESTMENT.equals(category)) {
			return false;
		}
		return true;
	}

	/**
	 * This method calls categorization engine and populate the categorized response for the transaction.
	 * @param userId UserId
	 * @param txn {@link TransactionBaseEntity}
	 * @throws Exception {@link Exception}
	 */
	public void categorizeTransaction(String userId, TransactionBaseEntity txn) throws Exception {

		List<TransactionBaseEntity> txns = new ArrayList<>();
		txns.add(txn);
		categorizeTransactions(userId, txn.getTag(), txns);
	}

	/**
	 * This method calls categorization engine and populate the categorized response for the transactions.
	 * 
	 * @param userId UserId
	 * @param txnEntities Transaction Entities
	 * @throws Exception {@link Exception}
	 */
	public void categorizeTransactions(String userId, List<? extends TransactionBaseEntity> txnEntities) throws Exception {

		String tag = null;
		if(txnEntities != null && txnEntities.size() > 0) {
			TransactionBaseEntity txn = txnEntities.get(0);
			if(txn != null) {
				tag = txn.getTag();
			}
		}
		categorizeTransactions(userId, tag, txnEntities);
	}


	/**
	 * This method calls categorization engine and populate the categorized response for the transactions.
	 * 
	 * @param userId UserId
	 * @param tag Bank/Card/Loan/Deposit/Investment
	 * @param txnEntities Transaction Entities
	 * @throws Exception {@link Exception}
	 */
	public void categorizeTransactions(String userId, String tag, List<? extends TransactionBaseEntity> txnEntities) throws Exception {

		ScriptLogger.writeInfo("inside categorizeTransactions");
		ScriptLogger.writeInfo("total txns => " + txnEntities.size());
		CategorizationRequestDTO catReq = new CategorizationRequestDTO();
		List<TransactionCategoryDTO> transactions = new ArrayList<>();
		userId = "\""  + userId + "\"";
		catReq.setUserID(userId);
		catReq.setAccountType(tag);
		catReq.setTransactions(transactions);


		for(TransactionBaseEntity txn: txnEntities) {
			TransactionCategoryDTO transaction = new TransactionCategoryDTO();
			transaction.setId(txn.getId().toString());
			transaction.setDescription(txn.getDescription());
			transactions.add(transaction);
		}

		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(catReq);
		ScriptLogger.writeInfo(data);

		String url = k2ServiceImpl.getConfigurationValue(Constants.PIMONEY_SERVICE, Constants.PIMONEY_SERVICE_URL) + "api/categoryengine";

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(data);

		String result = client.getResponseForPostRequest();

		CategorizationRequestDTO response = mapper.readValue(result, CategorizationRequestDTO.class);

		transactions = response.getTransactions();
		for(TransactionCategoryDTO txn: transactions) {
			Category cat = fetchCategoryByName(txn.getCategory());
			SubCategory subCat = null;
			if(cat != null) {
				subCat = fetchSubCategoryByNameAndCategoryId(txn.getSubcategory(), cat.getId());
				ScriptLogger.writeInfo("cat => " + cat.getId());
				ScriptLogger.writeInfo("subcat => " + txn.getSubcategory());
			}
			TransactionBaseEntity txnEntity = k2ServiceImpl.fetchTransaction(txn.getId(), tag);
			txnEntity.setCategoryObj(cat);
			txnEntity.setSubCategoryObj(subCat);
			txnEntity.setMerchantName(txn.getMerchantName());
			k2ServiceImpl.saveTransaction(txnEntity);
		}
	}

	public TransactionBaseEntity addTransaction(AddTransactionDTO request) {
		return EntityUtil.addTransaction(request);
	}

	/**
	 * This method is used to get all category from category table
	 * @param request {@link CategoryRequest}
	 * @return Category List
	 */
	public List<Category> getCategoryList(CategoryRequest request) {
		List<Category> categories = fetchAllCategories();
		return categories;
	}

	/**
	 * This method is used to get all merchant from merchant table
	 * @param request {@link CategoryRequest}
	 * @return Merchant List
	 */
	public List<Merchant> getMerchantList(CategoryRequest request) {
		List<Merchant> merchants = fetchAllMerchants();
		return merchants;
	}
}
