package com.pisight.pimoney.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.AssetEditFields;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.ParserResponse;
import com.pisight.pimoney.dto.PreviewResponse;
import com.pisight.pimoney.dto.RateDTO;
import com.pisight.pimoney.dto.SecurityMasterEditFields;
import com.pisight.pimoney.dto.TransactionEditFields;
import com.pisight.pimoney.dto.online.ContainerResponse;
import com.pisight.pimoney.dto.online.FinalResponse;
import com.pisight.pimoney.models.BankAccount;
import com.pisight.pimoney.models.BankTransaction;
import com.pisight.pimoney.models.CardAccount;
import com.pisight.pimoney.models.CardTransaction;
import com.pisight.pimoney.models.Container;
import com.pisight.pimoney.models.FixedDepositAccount;
import com.pisight.pimoney.models.GenericAccount;
import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.models.InvestmentAccount;
import com.pisight.pimoney.models.InvestmentTransaction;
import com.pisight.pimoney.models.LoanAccount;
import com.pisight.pimoney.models.LoanTransaction;
import com.pisight.pimoney.models.TransactionBase;
import com.pisight.pimoney.repository.CountryRepository;
import com.pisight.pimoney.repository.FileRepoEntityRepository;
import com.pisight.pimoney.repository.K2Repository;
import com.pisight.pimoney.repository.ManualInstitutionRepository;
import com.pisight.pimoney.repository.OnlineInstitutionRepository;
import com.pisight.pimoney.repository.SecurityMasterRepo;
import com.pisight.pimoney.repository.StatementRepoEntityRepository;
import com.pisight.pimoney.repository.UserRepository;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.AccountGroupDetails;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.Configuration;
import com.pisight.pimoney.repository.entities.Country;
import com.pisight.pimoney.repository.entities.Currency;
import com.pisight.pimoney.repository.entities.CurrencyRate;
import com.pisight.pimoney.repository.entities.FileRepositoryEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.GenericAccountEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.Institution;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.repository.entities.OnlineInstitution;
import com.pisight.pimoney.repository.entities.RepositoryBase;
import com.pisight.pimoney.repository.entities.SecurityMaster;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.EntityFinder;
import com.pisight.pimoney.util.EntityUtil;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class K2ServiceImpl {

	@Autowired
	private K2Repository k2Repo = null;

	@Autowired
	private ManualInstitutionRepository manInstRepo = null;

	@Autowired
	private OnlineInstitutionRepository onlineInstRepo = null;

	@Autowired
	private CountryRepository countryRepo = null;

	@Autowired
	private UserRepository userRepo = null;

	@Autowired
	private SecurityMasterRepo secMasterRepo = null;

	@Autowired
	private StatementRepoEntityRepository stmtRepo = null;

	@Autowired
	private FileRepoEntityRepository fileRepo = null;
	
	@Autowired
	private TransactionServiceImpl txnServiceImpl = null;
	
	@Autowired
	private K2DAO k2DAO = null;

	
	@Value("${rates.app_id}")
	private String rateAppId;

	@Transactional
	public User fetchUser(String username) {
		return k2Repo.fetchUser(username);
	}

	@Transactional
	public User fetchUserById(UUID id) {
		return userRepo.findById(id).get();
	}

	@Transactional
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	@Transactional
	public List<Currency> fetchCurrencyList(){
		return k2Repo.fetchCurrencyList();
	}

	@Transactional
	public HoldingAssetEntity fetchHoldingAsset(UUID id) {
		return k2Repo.fetchHoldingAsset(id);
	}

	public SecurityMaster fetchSecurityMaster(UUID id) {
		return secMasterRepo.findById(id).get();
	}

	@Transactional
	public AccountBaseEntity saveAccount(AccountBaseEntity account) {
		return k2DAO.saveAccount(account);
	}

	@Transactional
	public HoldingAssetEntity saveHoldingAsset(HoldingAssetEntity asset) {
		return k2DAO.saveAsset(asset);
	}

	@Transactional
	public TransactionBaseEntity saveTransaction(TransactionBaseEntity txn) {
		return k2DAO.saveTransaction(txn);
	}

	@Transactional
	public SecurityMaster saveSecurityMaster(SecurityMaster master) {
		return k2DAO.saveSecurityMaster(master);
	}

	@Transactional
	public void deleteAccount(AccountBaseEntity account) {
		Set<StatementRepositoryEntity> repos = account.getStatements();
		
		if(isBankAccountEntity(account)) {
			for(StatementRepositoryEntity repo: repos) {
				repo.getBankAccounts().remove(account);
				repo = saveStatementRepository(repo);
				ScriptLogger.writeInfo("account size now => " + repo.getBankAccounts().size() );
				if(repo.getBankAccounts().size() == 0) { // deleting statement repository if there is not accounts linked to it.
					deleteStatementRepository(repo);
				}
			}
//			account.getStatements().removeAll(repos);
//			saveAccount(account);
			k2DAO.deleteBankAccount((BankAccountEntity) account);
			
		}else if(isCardAccountEntity(account)) {
			for(StatementRepositoryEntity repo: repos) {
				repo.getCardAccounts().remove(account);
				repo = saveStatementRepository(repo);
				if(repo.getCardAccounts().size() == 0) { // deleting statement repository if there is not accounts linked to it.
					deleteStatementRepository(repo);
				}
			}
//			account.getStatements().removeAll(repos);
//			saveAccount(account);
			k2DAO.deleteCardAccount((CardAccountEntity) account);
			
		}else if(isLoanAccountEntity(account)) {
			for(StatementRepositoryEntity repo: repos) {
				repo.getLoanAccounts().remove(account);
				repo = saveStatementRepository(repo);
				if(repo.getLoanAccounts().size() == 0) { // deleting statement repository if there is not accounts linked to it.
					deleteStatementRepository(repo);
				}
			}
//			account.getStatements().removeAll(repos);
//			saveAccount(account);
			k2DAO.deleteLoanAccount((LoanAccountEntity) account);
			
		}else if(isFDAccountEntity(account)) {
			for(StatementRepositoryEntity repo: repos) {
				repo.getFdAccounts().remove(account);
				repo = saveStatementRepository(repo);
				if(repo.getFdAccounts().size() == 0) { // deleting statement repository if there is not accounts linked to it.
					deleteStatementRepository(repo);
				}
			}
//			account.getStatements().removeAll(repos);
//			saveAccount(account);
			k2DAO.deleteFDAccount((FixedDepositAccountEntity) account);
			
		}else if(isInvestmentAccountEntity(account)) {
			for(StatementRepositoryEntity repo: repos) {
				repo.getInvestmentAccounts().remove(account);
				repo = saveStatementRepository(repo);
				if(repo.getInvestmentAccounts().size() == 0) { // deleting statement repository if there is not accounts linked to it.
					deleteStatementRepository(repo);
				}
			}
//			account.getStatements().removeAll(repos);
//			saveAccount(account);
			k2DAO.deleteInvestmentAccount((InvestmentAccountEntity) account);
		}
		
		// For Delete Account Gropup Details Table
		for (StatementRepositoryEntity repo : repos) {
			List<StatementRepositoryEntity> stmtRepoEntries = k2DAO.fetchStmtRepoByGroupId(account.getUser().getId(), repo.getGroupId());
			if(stmtRepoEntries.size() == 0) {
				
				List<AccountGroupDetails> accountGroupDetails = k2DAO.fetchDetailsByGroupId(repo.getUser().getId(), repo.getGroupId());
				for (AccountGroupDetails detail : accountGroupDetails) {
					k2DAO.deleteAccountGroupDetails(detail);
				}
				ScriptLogger.writeInfo("Number of Entry Deleted for Account Group Details = " + accountGroupDetails.size());
			}else {
				ScriptLogger.writeInfo("Account Statement Details Still Available....");
			}
		}
	}

	@Transactional
	public void deleteHoldingAsset(HoldingAssetEntity asset) {
		k2DAO.deleteHoldingAsset(asset);
	}

	@Transactional
	public void deleteTransaction(TransactionBaseEntity txn) {
		if(txn == null) {
			return;
		}
		
		if(txn instanceof BankTransactionEntity) {
			k2DAO.deleteBankTransaction((BankTransactionEntity) txn);
		}
		else if(txn instanceof CardTransactionEntity) {
			k2DAO.deleteCardTransaction((CardTransactionEntity) txn);
		}
		else if(txn instanceof LoanTransactionEntity) {
			k2DAO.deleteLoanTransaction((LoanTransactionEntity) txn);
		}
		else if(txn instanceof InvestmentTransactionEntity) {
			k2DAO.deleteInvestmentTransaction((InvestmentTransactionEntity) txn);
		}
	}

	@Transactional
	public void deleteSecurityMaster(SecurityMaster master) {
		k2DAO.deleteSecurityMaster(master);
	}

	public StatementRepositoryEntity saveStatementRepository(StatementRepositoryEntity statement) {
		ScriptLogger.writeInfo("saving repository with id from " + ScriptLogger.getCallerDetail()  + "=> " + statement.getId());
		return stmtRepo.save(statement);
	}

	public FileRepositoryEntity saveFileRepository(FileRepositoryEntity file) {
		if(file != null) {
			ScriptLogger.writeInfo("#############################");
			ScriptLogger.writeInfo("#############################");
			ScriptLogger.writeInfo(file.getObjectPath());
			ScriptLogger.writeInfo(file.getFilePath());
			ScriptLogger.writeInfo("#############################");
			ScriptLogger.writeInfo("#############################");
		}
		return fileRepo.save(file);
	}

	public List<StatementRepositoryEntity> fetchStatementRepositoryByUserId(String userId){
		return stmtRepo.fetchStatementRepoByUserId(UUID.fromString(userId));
	}

	public StatementRepositoryEntity fetchStatementRepositoryById(String id){
		try {
			return stmtRepo.findById(UUID.fromString(id)).get();
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
			return null;
		}
	}

	public void deleteStatementRepository(StatementRepositoryEntity repo) {
		try {
			stmtRepo.delete(repo);
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
	}

	public List<FileRepositoryEntity> fetchFileRepositoryByUserId(String userId){
		return fileRepo.fetchFileRepoByUserId(UUID.fromString(userId));
	}

	public FileRepositoryEntity fetchFileRepositoryById(String id){
		try {
			return fileRepo.findById(UUID.fromString(id)).get();
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
			return null;
		}
	}

	public void deleteFileRepository(FileRepositoryEntity repo) {
		try {
			if(repo != null) {
				deleteXMLPath(repo.getFilePath());
				deleteObjectPath(repo.getObjectPath());
			}
			fileRepo.delete(repo);
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
	}
	
	
	private void deleteXMLPath(String xmlPath) {
		if(StringUtils.isNotEmpty(xmlPath)) {
			xmlPath = xmlPath.replace("xml/", "");
		}
		WebUtil.deleteDirectory(xmlPath, true);
	}
	
	private void deleteObjectPath(String objectPath) {
		if(StringUtils.isNotEmpty(objectPath)) {
			objectPath = objectPath.replace("object/", "");
		}
		WebUtil.deleteDirectory(objectPath, true);
	}

	@Transactional
	public AccountBaseEntity fetchAccount(String id, String tag) {
		AccountBaseEntity account = null;

		try {
			if(Constants.TAG_BANK.equals(tag)) {
				account = k2Repo.fetchBankAccount(UUID.fromString(id));
			}
			else if(Constants.TAG_CARD.equals(tag)) {
				account = k2Repo.fetchCardAccount(UUID.fromString(id));
			}
			else if(Constants.TAG_LOAN.equals(tag)) {
				account = k2Repo.fetchLoanAccount(UUID.fromString(id));
			}
			else if(Constants.TAG_FIXED_DEPOSIT.equals(tag)) {
				account = k2Repo.fetchFixedDepositAccount(UUID.fromString(id));
			}
			else if(Constants.TAG_INVESTMENT.equals(tag)) {
				account = k2Repo.fetchInvestmentAccount(UUID.fromString(id));
			}
		}
		catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return account;
	}

	@Transactional
	public HoldingAssetEntity fetchHoldingAsset(String id) {
		HoldingAssetEntity asset = null;

		try {
			asset = fetchHoldingAsset(UUID.fromString(id));
		}
		catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return asset;
	}

	@Transactional
	public TransactionBaseEntity fetchTransaction(String id, String tag) {
		TransactionBaseEntity txn = null;

		try {
			ScriptLogger.writeInfo("Tag = " + tag);
			if(Constants.TAG_BANK.equals(tag)) {
				ScriptLogger.writeInfo("dshafkashdf");
				txn = k2Repo.fetchBankTransaction(UUID.fromString(id));
			}
			else if(Constants.TAG_CARD.equals(tag)) {
				txn = k2Repo.fetchCardTransaction(UUID.fromString(id));
			}
			else if(Constants.TAG_LOAN.equals(tag)) {
				txn = k2Repo.fetchLoanTransaction(UUID.fromString(id));
			}
			else if(Constants.TAG_INVESTMENT.equals(tag)) {
				txn = k2Repo.fetchInvestmentTransaction(UUID.fromString(id));
			}
		}
		catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return txn;
	}

	@Transactional
	public SecurityMaster fetchSecurityMaster(String id) {
		SecurityMaster master = null;

		try {
			master = fetchSecurityMaster(UUID.fromString(id));
		}
		catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return master;

	}

	@Transactional
	public boolean verifyToken(String source, String token) {

		String tokenStored = getConfigurationValue("TOKEN", "TOKEN_PSV");

		if(StringUtils.isNotEmpty(tokenStored)) {
			return tokenStored.equals(token);
		}

		return false;

	}

	@Transactional
	public Configuration fetchConfiguration(String type, String key){
		return k2Repo.fetchConfiguration(type, key);
	}

	/**
	 * This method return configuration value from configuration table
	 * @param type Type
	 * @param key Key
	 * @return Configuration Value
	 */
	public String getConfigurationValue(String type, String key){
		String value = null;

		Configuration config = fetchConfiguration(type, key);

		if(config != null){
			value = "";
		}
		else {
			ScriptLogger.writeWarning("Config is null ");
		}

		return value;
	}

	public List<ManualInstitution> fetchAllManualInstitutions(){

		return manInstRepo.findAll();

	}

	public List<ManualInstitution> fetchManualInstitutionsByCountryCode(String countryCode){
		if(countryCode.equals("ALL")) {
			return manInstRepo.findAll();
		}else {
			return manInstRepo.findByCountryCode(countryCode);
		}
	}

	public ManualInstitution fetchManualInstitutionByCode(String instCode) {
		return manInstRepo.findByInstCode(instCode);
	}

	public List<OnlineInstitution> fetchOnlineInstitutionsByCountryCode(String countryCode){
		return onlineInstRepo.findByCountryCode(countryCode);
	}

	public OnlineInstitution fetchOnlineInstitutionByCode(String instCode) {
		return onlineInstRepo.findByInstCode(instCode);
	}

	public List<Country> fetchAllCountries(){
		return countryRepo.findAll();

	}


	@Transactional
	public JSONObject storeParserResoponse(ParserResponse parserResponse, StatementRepositoryEntity stmt, String userId, String instCode) throws Exception {

		ScriptLogger.writeInfo("inside storeParserResoponse statement flow");
		JSONObject response = new JSONObject();
		
		HashMap<String, Set<AccountBaseEntity>> accountSet = new HashMap<String, Set<AccountBaseEntity>>(); // for use in Statement Repository

		List<Container> accounts = parserResponse.getAllAccounts();

		response = storeResponse(accounts, accountSet, userId, instCode, "pdf", stmt);
		if((int)response.get(Constants.ERROR_CODE_STRING) == 0) {
			updateStatementRepository(stmt, parserResponse, accountSet);
		}else {
			//Delete Repository Entry
			deleteStatementRepository(stmt);
		}
		return response;
	}


	@Transactional
	public JSONObject storeOnlineResoponse(FinalResponse parserResponse, String userId, String trackerId) throws Exception {

		ScriptLogger.writeInfo("inside storeOnlineResoponse pimoney flow");
		JSONObject response = new JSONObject();

		String instCode = parserResponse.getInstitutionCode();

		HashMap<String, ContainerResponse> accountContainer = parserResponse.getAccountContainer();

		Set<String> tags = accountContainer.keySet();

		List<Container> accounts = new ArrayList<Container>();

		if(tags == null || tags.size() == 0){
			ScriptLogger.writeInfo("no account container present in the response");
			tags = new HashSet<String>();
		}

		for(String tag: tags){
			ContainerResponse containerResponse = accountContainer.get(tag);
			List<Container> accts = containerResponse.getAccounts();
			ScriptLogger.writeInfo("tag -->> " + tag + " ### account size -->> " + accts.size());
			for(Container acct: accts) {
				acct.setBankId(trackerId);
			}
			accounts.addAll(accts);
		}

		HashMap<String, Set<AccountBaseEntity>> accountSet = new HashMap<String, Set<AccountBaseEntity>>(); // for use in Statement Repository

		response = storeResponse(accounts, accountSet, userId, instCode, "online", null);
		return response;
	}


	@Transactional
	public JSONObject storeResponse(List<Container> accounts, HashMap<String, Set<AccountBaseEntity>> accountSet, String userId,
			String instCode, String source, StatementRepositoryEntity stmt) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, Exception {

		User user = fetchUserById(UUID.fromString(userId));
		Institution inst = null;
		boolean needToConfirm = false;
		JSONObject response = new JSONObject();
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		
		if("online".equals(source)) {
			inst = fetchOnlineInstitutionByCode(instCode);
			needToConfirm = true;
		}else if("pdf".equals(source)) {
			inst = fetchManualInstitutionByCode(instCode);
		}

		HashMap<String, AccountBaseEntity> oldAccountMap = new HashMap<String, AccountBaseEntity>();
		List<AccountBaseEntity> oldAccounts = user.getAllAccounts();

		ScriptLogger.writeInfo("Total accounts for the user :: " + oldAccounts.size());
		oldAccounts = filterAccountsByInstCode(oldAccounts, instCode);
		fillAccountMap(oldAccountMap, oldAccounts);

		ScriptLogger.writeInfo("map size -> " + oldAccountMap.size());
		ScriptLogger.writeInfo("Total accounts for the inst :: " + oldAccounts.size());

		boolean result = false;
		int alreadyUploadedCount = 0;
		ScriptLogger.writeInfo("Number of Accounts = " + accounts.size());
		for(Container account : accounts) {

			ScriptLogger.writeInfo("Account number :: " + account.getAccountNumber());
			String key = getAccountKey(account);
			if(oldAccountMap.containsKey(key)) {
				
				ScriptLogger.writeInfo("flow for accounts already present........");
				AccountBaseEntity oldEntity = oldAccountMap.get(key);
				Set<AccountBaseEntity> set = accountSet.get(account.getTag());
				if(set == null) {
					set = new HashSet<AccountBaseEntity>();
					accountSet.put(account.getTag(), set);
				}
				set.add(oldEntity);
				
				if(!oldEntity.getFingerprint().equals(account.getFingerprint())) {
					ScriptLogger.writeInfo("FingerPrint does not match.. so copying new details");
					EntityUtil.copyToAccountEntity(account, oldEntity);
				}
				oldEntity.setStatus(true);
				if(needToConfirm) {
					oldEntity.setConfirmed(needToConfirm);
				}
				
				String stmtId =  stmt != null?stmt.getId().toString():null;
				List<? extends TransactionBaseEntity> txnEntities = EntityUtil.handleTransactionEntitiesForOldAccount(account, oldEntity);
				result = insertOldAccountTransation(txnEntities, oldEntity, needToConfirm, stmtId);
				if(!result) {
					break;
				}

				boolean alreadyExistAsset = true;
				if(account instanceof InvestmentAccount) {
					InvestmentAccount acct = (InvestmentAccount) account;
					InvestmentAccountEntity oldEntityInv = (InvestmentAccountEntity) oldEntity;
					List<HoldingAssetEntity> assetEntities = EntityUtil.handleAssetEntitiesForOldAccount(acct, oldEntityInv);
					result = insertOldAccountAssets(assetEntities, oldEntityInv, needToConfirm, stmtId);
					if(!result) {
						break;
					}
					List<SecurityMaster> masterList = EntityUtil.createSecurityMaster(oldEntityInv);
					result = insertSecurityMaster(masterList, oldEntityInv, needToConfirm, stmtId);
					
					ScriptLogger.writeInfo("Modify or New Entry for Assets = " + assetEntities.size());
					if(assetEntities.size() > 0) {
						alreadyExistAsset = false;
					}
				}
				
				//  logic for accounts already present.
				ScriptLogger.writeInfo("Old Entry statement Date = " + oldEntity.getBillDate());
				ScriptLogger.writeInfo("Current Statement Date = " + account.getBillDate());
				
				Date oldDate = new Date(oldEntity.getBillDate().getTime());
				Date currentBillDate = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_MM_DD).parse(account.getBillDate());
				
				ScriptLogger.writeInfo("Current Statement Date for Comparision = " + currentBillDate);
				ScriptLogger.writeInfo("Old Date For Comparision = " + oldDate);
				
				// Check for Duplication of Statement
				if(oldDate.equals(currentBillDate)){
						
					if(oldEntity.isStatus() && (txnEntities.size() == 0) && alreadyExistAsset) {
						ScriptLogger.writeInfo("Account already added and in active state");
						if(!oldEntity.isConfirmed()) {
							ScriptLogger.writeInfo("Account Not Confirmed so that goto Transaction Preview Page");
						}else {
							alreadyUploadedCount++;
						}
					}else {
						   ScriptLogger.writeInfo("Deleted statement being uploaded again, hence status is being changed");
					}
				}
				
				if(alreadyUploadedCount == accounts.size()) {
					ScriptLogger.writeInfo("This account statement has already been uploaded and added to your account previously.  Please upload a new statement.");
					errorCode = 0;
					responseMessage = "This account statement has already been uploaded and added to your account previously.  Please upload a new statement.";
					responseStatus = Constants.FAILED;
					return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				}else {
					ScriptLogger.writeInfo("Data Stord Successfully....");
				}
				// End
			}else {
				ScriptLogger.writeInfo("New Account.....");
				result = insertNewAccountDetails(account, accountSet, user, inst, stmt);
				if(!result) {
					break;
				}
			}
		}
		errorCode = 0;
		responseStatus = Constants.SUCCESS;
		responseMessage = "Statement Parsed and stored successfully";
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	@Transactional
	public JSONObject storeParserResoponse(ParserResponse response, FileRepositoryEntity stmt, String userId, String instCode) throws ParseException {

		ScriptLogger.writeInfo("inside storeParserResoponse statement flow");
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {
			response = WebUtil.getParserResponseFromObjectFile(response);
			String statementDate = response.getStatementDate();
			String stmtType = response.getStatementType();
			ManualInstitution inst = fetchManualInstitutionByCode(instCode);

			if(StringUtils.isNotEmpty(statementDate)) {
				Date stmtDate = WebUtil.convertToDate(statementDate);
				stmt.setStatementDate(stmtDate);
			}else {
				
			}
			stmt.setStatementType(stmtType);

			List<Container> accounts = response.getAllAccounts();

			ArrayList<AccountBaseEntity> entityList = new ArrayList<AccountBaseEntity>();

			boolean generateOutput = false;
			
			ScriptLogger.writeInfo("account size => " + accounts.size());

			for(Container account: accounts) {

				AccountBaseEntity acctEntity = EntityUtil.toAccountEntity(account);
				ScriptLogger.writeInfo("account type " + account.getTag() + " and " + acctEntity.getTag());
				acctEntity.setManualInstitution(inst);
				if(account instanceof InvestmentAccount) {
					InvestmentAccount inv = (InvestmentAccount) account;
					InvestmentAccountEntity acctEntityInv = (InvestmentAccountEntity) acctEntity;
					List<InvestmentTransaction> txns = inv.getInvestmentTransactions();
					List<InvestmentTransactionEntity> txnEntityList = new ArrayList<InvestmentTransactionEntity>();

					List<HoldingAsset> assets = inv.getAssets();
					List<HoldingAssetEntity> assetEntityList = new ArrayList<HoldingAssetEntity>();
					List<SecurityMaster> masterList = null;

					for(HoldingAsset asset: assets) {
						HoldingAssetEntity assetEntity = EntityUtil.toAssetEntity(asset);
						assetEntity.setId(UUID.randomUUID());
						assetEntity.setCreatedAt(new Date());
						assetEntity.setUpdatedAt(new Date());
						assetEntity.setAccount(acctEntityInv);
						assetEntityList.add(assetEntity);
					}

					for(InvestmentTransaction txn: txns) {
						InvestmentTransactionEntity txnEntity = EntityUtil.toInvestmentTransactionEntity(txn);
						txnEntity.setId(UUID.randomUUID());
						txnEntity.setCreatedAt(new Date());
						txnEntity.setUpdatedAt(new Date());
						txnEntity.setAccount(acctEntityInv);
						txnEntityList.add(txnEntity);
					}
					acctEntityInv.setTransactions(txnEntityList);
					acctEntityInv.setAssets(assetEntityList);

					masterList = EntityUtil.createSecurityMaster(acctEntityInv);
					acctEntityInv.setScxList(masterList);
					for(SecurityMaster master: masterList) {
						master.setId(UUID.randomUUID());
						master.setCreatedAt(new Date());
						master.setUpdatedAt(new Date());
						master.setAccount(acctEntityInv);
					}
					ScriptLogger.writeInfo("asset size -> " + assetEntityList.size());
					ScriptLogger.writeInfo("txn size -> " + txnEntityList.size());
					ScriptLogger.writeInfo("master size -> " +  masterList.size());
				}
				else if(account instanceof BankAccount) {
					BankAccount bank = (BankAccount) account;
					BankAccountEntity acctEntityBank = (BankAccountEntity) acctEntity;
					List<BankTransactionEntity> txnEntityList= new ArrayList<BankTransactionEntity>();
					List<BankTransaction> txns =bank.getTransactions();

					for(BankTransaction txn: txns) {
						BankTransactionEntity txnEntity = EntityUtil.toBankTransactionEntity(txn);
						txnEntity.setId(UUID.randomUUID());
						txnEntity.setCreatedAt(new Date());
						txnEntity.setUpdatedAt(new Date());
						txnEntity.setAccount(acctEntityBank);
						txnEntityList.add(txnEntity);
					}
					acctEntityBank.setTransactions(txnEntityList);
					ScriptLogger.writeInfo("txn size -> " + txnEntityList.size());
				}
				else if(account instanceof CardAccount) {
					CardAccount card = (CardAccount) account;
					CardAccountEntity acctEntityCard = (CardAccountEntity) acctEntity;
					List<CardTransactionEntity> txnEntityList= new ArrayList<CardTransactionEntity>();
					List<CardTransaction> txns =card.getTransactions();

					for(CardTransaction txn: txns) {
						CardTransactionEntity txnEntity = EntityUtil.toCardTransactionEntity(txn);
						txnEntity.setId(UUID.randomUUID());
						txnEntity.setCreatedAt(new Date());
						txnEntity.setUpdatedAt(new Date());
						txnEntity.setAccount(acctEntityCard);
						txnEntityList.add(txnEntity);
					}
					acctEntityCard.setTransactions(txnEntityList);
					ScriptLogger.writeInfo("txn size -> " + txnEntityList.size());
				}
				else if(account instanceof LoanAccount) {
					LoanAccount loan = (LoanAccount) account;
					LoanAccountEntity acctEntityLoan = (LoanAccountEntity) acctEntity;
					List<LoanTransactionEntity> txnEntityList= new ArrayList<LoanTransactionEntity>();
					List<LoanTransaction> txns =loan.getTransactions();

					for(LoanTransaction txn: txns) {
						LoanTransactionEntity txnEntity = EntityUtil.toLoanTransactionEntity(txn);
						txnEntity.setId(UUID.randomUUID());
						txnEntity.setCreatedAt(new Date());
						txnEntity.setUpdatedAt(new Date());
						txnEntity.setAccount(acctEntityLoan);
						txnEntityList.add(txnEntity);
					}
					acctEntityLoan.setTransactions(txnEntityList);
					ScriptLogger.writeInfo("txn size -> " + txnEntityList.size());
				}
				else if(account instanceof GenericAccount) {
					ScriptLogger.writeInfo("generic account found...");
					generateOutput = true;
				}
				
				acctEntity.setId(UUID.randomUUID());
				acctEntity.setCreatedAt(new Date());
				acctEntity.setUpdatedAt(new Date());
				entityList.add(acctEntity);
			}

			FileOutputStream fos = null;
			ObjectOutputStream out = null;

			String randomString = RandomStringUtils.randomAlphanumeric(12);
			String objectPath = Constants.STATEMENT_HOME + randomString + "/object/";
			String filename = objectPath + "account.ser";

			File directory = new File(objectPath	);
			if(!directory.exists()){
				directory.mkdirs();
			}

			try {
				fos = new FileOutputStream(filename);
				
				try {
					out = new ObjectOutputStream(fos);
					out.writeObject(entityList);
				}catch (Exception e) {
					// TODO: handle exception
					ScriptLogger.writeInfo("Exception e =", e);
				}finally {
					if(out != null) {
						out.close();
					}
				}
				
			} catch (Exception ex) {
				ScriptLogger.writeError("Exception e ", ex);
				throw ex;
			}finally {
				if(fos != null) {
					fos.close();
				}
			}

			deleteObjectPath(stmt.getObjectPath());
			stmt.setObjectPath(objectPath);
			saveFileRepository(stmt);
			
			ScriptLogger.writeInfo("generate output => " + generateOutput);
			if(generateOutput) {
				generateXML(entityList, stmt);
				saveFileRepository(stmt);
				ScriptLogger.writeInfo("saved");
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";
			
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}

		return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
	}

	private boolean insertSecurityMaster(List<SecurityMaster> masterList, InvestmentAccountEntity oldEntity, boolean needToConfirm, String stmtId) {

		ScriptLogger.writeInfo("inside insertSecurityMaster");
		boolean result = false;
		try {
			for(SecurityMaster master: masterList) {
				master.setAccount(oldEntity);
				master.setStatus(true);
				master.setStatementId(stmtId);
				if(needToConfirm) {
					master.setConfirmed(needToConfirm);
				}
				k2DAO.saveSecurityMaster(master);
			}
			result = true;

		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return result;
	}

	private boolean insertOldAccountTransation(List<? extends TransactionBaseEntity> txnEntities, AccountBaseEntity oldEntity, boolean needToConfirm, String stmtId) {

		ScriptLogger.writeInfo("inside insertOldAccountTransation");
		boolean result = false;
		boolean isCategorizationRequired = true;
		String tag = null;

		try {
			ScriptLogger.writeInfo("Insert Transaction = " + txnEntities.size());
			for(TransactionBaseEntity txn: txnEntities) {
				txn.setStatus(true);
				txn.setStatementId(stmtId);
				if(needToConfirm) {
					txn.setConfirmed(needToConfirm);
				}
				if(txn instanceof InvestmentTransactionEntity) {
					isCategorizationRequired = false;
					tag = Constants.TAG_INVESTMENT;
					InvestmentTransactionEntity transaction = (InvestmentTransactionEntity) txn;
					InvestmentAccountEntity entity = (InvestmentAccountEntity) oldEntity;
					transaction.setAccount(entity);
					k2DAO.saveInvestmentTransaction(transaction);
					
				}else if(txn instanceof BankTransactionEntity) {
					tag = Constants.TAG_BANK;
					BankTransactionEntity transaction = (BankTransactionEntity) txn;
					BankAccountEntity entity = (BankAccountEntity) oldEntity;
					transaction.setAccount(entity);
					k2DAO.saveBankTransaction(transaction);
					
				}else if(txn instanceof CardTransactionEntity) {
					tag = Constants.TAG_CARD;
					CardTransactionEntity transaction = (CardTransactionEntity) txn;
					CardAccountEntity entity = (CardAccountEntity) oldEntity;
					transaction.setAccount(entity);
					k2DAO.saveCardTransaction(transaction);
					
				}else if(txn instanceof LoanTransactionEntity) {
					tag = Constants.TAG_LOAN;
					LoanTransactionEntity transaction = (LoanTransactionEntity) txn;
					LoanAccountEntity entity = (LoanAccountEntity) oldEntity;
					transaction.setAccount(entity);
					k2DAO.saveLoanTransaction(transaction);
					
				}
			}
			result = true;
			if(isCategorizationRequired) {
				txnServiceImpl.categorizeTransactions(oldEntity.getUser().getId().toString(), tag, txnEntities);
			}

		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return result;
	}

	private boolean insertOldAccountAssets(List<HoldingAssetEntity> assetEntities, InvestmentAccountEntity oldEntity, boolean needToConfirm, String stmtId) {

		ScriptLogger.writeInfo("inside insertOldAccountAssets");
		boolean result = false;

		try {
			for(HoldingAssetEntity asset: assetEntities) {
				asset.setAccount(oldEntity);
				asset.setStatus(true);
				asset.setStatementId(stmtId);
				if(needToConfirm) {
					asset.setConfirmed(needToConfirm);
				}
				k2DAO.saveAsset(asset);
			}
			result = true;

		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}
		return result;
	}

	private boolean insertNewAccountDetails(Container account, HashMap<String, Set<AccountBaseEntity>> accountSet, 
			User user, Institution inst, StatementRepositoryEntity stmt) throws Exception {

		boolean result = false;
		boolean isCategorizationRequired = true;
		String tag = null;
		boolean needToConfirm = false;
		String stmtId = stmt != null?stmt.getId().toString():null;

		AccountBaseEntity entity = EntityUtil.toAccountEntityDC(account); // Deep Copy
		entity.setUser(user);
		if(inst instanceof ManualInstitution) {
			ManualInstitution mi = (ManualInstitution) inst;
			entity.setManualInstitution(mi);
		}else if(inst instanceof OnlineInstitution) {
			OnlineInstitution oi = (OnlineInstitution) inst;
			entity.setOnlineInstitution(oi);
			needToConfirm = true;
		}
		
		entity.setConfirmed(needToConfirm);

		ObjectMapper mapper = new ObjectMapper();
		ScriptLogger.writeInfo(mapper.writeValueAsString(entity));
		
		ScriptLogger.writeInfo("Account converted to Entity");

		List<TransactionBaseEntity> txns = new ArrayList<TransactionBaseEntity>();

		if(entity instanceof InvestmentAccountEntity) {
			tag = Constants.TAG_INVESTMENT;
			isCategorizationRequired = false;
			InvestmentAccountEntity entityInv = (InvestmentAccountEntity) entity;
			user.getInvestmentAccounts().add(entityInv);

			List<HoldingAssetEntity> assets = entityInv.getAssets();

			txns.addAll(entityInv.getTransactions());

			ScriptLogger.writeInfo("Asset size :: " + assets.size());
			for(HoldingAssetEntity asset: assets) {
				asset.setStatus(true);
				asset.setConfirmed(needToConfirm);
				asset.setStatementId(stmtId);
				ScriptLogger.writeInfo("asset fingerprint size -> " + asset.getFingerprint().length());
				k2DAO.saveAsset(asset);
			}

			List<SecurityMaster> masterList = EntityUtil.createSecurityMaster(entityInv);
			result = insertSecurityMaster(masterList, entityInv, needToConfirm, stmtId);

		}
		else if(entity instanceof BankAccountEntity) {
			tag = Constants.TAG_BANK;
			BankAccountEntity entityBank = (BankAccountEntity) entity;
			user.getBankAccounts().add(entityBank);
			txns.addAll(entityBank.getTransactions());

		}
		else if(entity instanceof CardAccountEntity) {
			tag = Constants.TAG_CARD;
			CardAccountEntity entityCard = (CardAccountEntity) entity;
			user.getCardAccounts().add(entityCard);
			txns.addAll(entityCard.getTransactions());
		}
		else if(entity instanceof LoanAccountEntity) {
			tag = Constants.TAG_LOAN;
			LoanAccountEntity entityLoan = (LoanAccountEntity) entity;
			user.getLoanAccounts().add(entityLoan);
			txns.addAll(entityLoan.getTransactions());
		}
		else if(entity instanceof FixedDepositAccountEntity) {
			tag = Constants.TAG_FIXED_DEPOSIT;
			FixedDepositAccountEntity entityFD = (FixedDepositAccountEntity) entity;
			user.getFdAccounts().add(entityFD);
		}

		ScriptLogger.writeInfo("Trans size :: " + txns.size());

		for(TransactionBaseEntity txn: txns) {
			txn.setStatus(true);
			txn.setConfirmed(needToConfirm);
			txn.setStatementId(stmtId);
			ScriptLogger.writeInfo("txn fingerprint size -> " + txn.getFingerprint().length());
			k2DAO.saveTransaction(txn);
		}

		entity.setStatus(true);
		ScriptLogger.writeInfo("account fingerprint size -> " + entity.getFingerprint().length());
		k2DAO.saveAccount(entity);

		k2DAO.createOrUpdateUser(user);
		result = true;
		Set<AccountBaseEntity> set = accountSet.get(account.getTag());
		if(set == null) {
			set = new HashSet<AccountBaseEntity>();
			accountSet.put(entity.getTag(), set);
		}
		set.add(entity);
		
		if(isCategorizationRequired) {
			txnServiceImpl.categorizeTransactions(user.getId().toString(), tag, txns);
		}
		return result;
	}

	private List<AccountBaseEntity> filterAccountsByInstCode(List<AccountBaseEntity> oldAccounts, String instCode) {

		List<AccountBaseEntity> accounts = new ArrayList<AccountBaseEntity>();
		for(AccountBaseEntity account: oldAccounts) {

			if((account.getManualInstitution() != null && account.getManualInstitution().getCode().equals(instCode)) ||
					(account.getOnlineInstitution() != null && account.getOnlineInstitution().getCode().equals(instCode))) {
				accounts.add(account);
			}
		}
		return accounts;
	}

	/**
	 * 
	 * @param newAccountMap
	 * @param ias
	 */
	private void fillAccountMap(HashMap<String, AccountBaseEntity> newAccountMap, List<AccountBaseEntity> ias) {

		for(AccountBaseEntity act: ias) {
			newAccountMap.put(getAccountKey(act), act);
		}
	}

	public HoldingAssetEntity updateAssetFields(AssetEditFields assetNew, String userId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if(assetNew == null) {
			return null;
		}

		HoldingAssetEntity asset = fetchHoldingAsset(assetNew.getId());

		if(asset.getAccount().getUser().getId().toString().equals(userId)) {
			ScriptLogger.writeInfo("User Matched");
			EntityUtil.updateHoldingAssetEntity(asset, assetNew);
			asset = k2DAO.saveAsset(asset);
		}else {
			ScriptLogger.writeWarning("Invalid asset detail");
		}
		return asset;
	}

	@Transactional
	public TransactionBaseEntity updateTransactionFields(TransactionEditFields txnNew, String userId, String tag) {

		if(txnNew == null) {
			return null;
		}

		TransactionBaseEntity txn = fetchTransaction(txnNew.getId(), tag);
		if(txn == null) {
			ScriptLogger.writeError("txn not found with given txn id");
			return null;
		}

		if(txn.getAccount().getUser().getId().toString().equals(userId)) {
			ScriptLogger.writeInfo("User Matched");
			EntityUtil.updateTransactionEntity(txn, txnNew);
			txn = k2DAO.saveTransaction(txn);
		}else {
			ScriptLogger.writeWarning("Invalid transaction detail");
		}
		return txn;
	}

	public SecurityMaster updateMasterFields(SecurityMasterEditFields masterNew, String userId) {

		if(masterNew == null) {
			return null;
		}

		SecurityMaster master = fetchSecurityMaster(masterNew.getId());
		if(master.getAccount().getUser().getId().toString().equals(userId)) {
			ScriptLogger.writeInfo("User Matched");
			EntityUtil.updateSecurityMaster(master, masterNew);
			master = k2DAO.saveSecurityMaster(master);
		}else {
			ScriptLogger.writeWarning("Invalid master detail");
		}
		return master;
	}

	public RepositoryBase createRepositoryEntity(DocumentRequest doc) throws Exception {

		ScriptLogger.writeInfo("inside createStatementRepositoryEntity");

		if(doc == null) {
			return null;
		}

		String flow = doc.getFlow();
		RepositoryBase statement = null;

		if(Constants.FLOW_PIMONEY.equals(flow)) {
			statement = new StatementRepositoryEntity();
		}else {
			statement = new FileRepositoryEntity();
		}

		String userId = doc.getUserId();
		String instCode = doc.getInstitutionCode();
		byte[] docByte = Base64.decodeBase64(doc.getDocByte());
		String fileName = doc.getStatementFileName();
		if(StringUtils.isEmpty(fileName)) {
			fileName = doc.getName();
		}
		User user = fetchUserById(UUID.fromString(userId));
		ManualInstitution institution = fetchManualInstitutionByCode(instCode);

		statement.setUser(user);
		statement.setInstitution(institution);
		statement.setBytecode(docByte);
		statement.setStatementName(fileName);
		

		if(statement instanceof StatementRepositoryEntity) {
			return saveStatementRepository((StatementRepositoryEntity) statement);
		}else {
			FileRepositoryEntity fileStatement = (FileRepositoryEntity) statement;
			fileStatement.setHeaderGroup(doc.getHeaderGroup());
			fileStatement.setOutputFileGroup(doc.getOutputFileGroup());
			return saveFileRepository(fileStatement);
		}
	}

	public void updateStatementRepository(StatementRepositoryEntity stmt, ParserResponse response, HashMap<String, Set<AccountBaseEntity>> accountSet) throws ParseException {

		String statementDate = response.getStatementDate();
		String stmtType = response.getStatementType();
		
		ScriptLogger.writeInfo("stmt Date =<<<<<<<<??????????????????? " + statementDate);

		if(StringUtils.isNotEmpty(statementDate)) {
			Date stmtDate = WebUtil.convertToDate(statementDate);
			stmt.setStatementDate(stmtDate);
		}
		stmt.setStatementType(stmtType);
		stmt.setXmlPath("demo xml path");

		Set<InvestmentAccountEntity> setInv = stmt.getInvestmentAccounts();
		Set<BankAccountEntity> setBank = stmt.getBankAccounts();
		Set<CardAccountEntity> setCard = stmt.getCardAccounts();
		Set<LoanAccountEntity> setLoan = stmt.getLoanAccounts();
		Set<FixedDepositAccountEntity> setFD = stmt.getFdAccounts();

		Set<AccountBaseEntity> set = new HashSet<AccountBaseEntity>();

		Set<String> keySet = accountSet.keySet();

		for(String key: keySet) {
			Set<AccountBaseEntity> tempSet = accountSet.get(key);
			if(tempSet != null) {
				set.addAll(tempSet);
			}
		}

		for(AccountBaseEntity baseEntity: set) {

			if(baseEntity instanceof BankAccountEntity) {
				BankAccountEntity entity = (BankAccountEntity) baseEntity;
				setBank.add(entity);
				Set<StatementRepositoryEntity> stmts = entity.getStatements();
				stmts.add(stmt);
				
			}else if(baseEntity instanceof CardAccountEntity) {
				CardAccountEntity entity = (CardAccountEntity) baseEntity;
				setCard.add(entity);
				Set<StatementRepositoryEntity> stmts = entity.getStatements();
				stmts.add(stmt);
				
			}else if(baseEntity instanceof LoanAccountEntity) {
				LoanAccountEntity entity = (LoanAccountEntity) baseEntity;
				setLoan.add(entity);
				Set<StatementRepositoryEntity> stmts = entity.getStatements();
				stmts.add(stmt);
				
			}else if(baseEntity instanceof FixedDepositAccountEntity) {
				FixedDepositAccountEntity entity = (FixedDepositAccountEntity) baseEntity;
				setFD.add(entity);
				Set<StatementRepositoryEntity> stmts = entity.getStatements();
				stmts.add(stmt);
				
			}else if(baseEntity instanceof InvestmentAccountEntity) {
				InvestmentAccountEntity entity = (InvestmentAccountEntity) baseEntity;
				setInv.add(entity);
				Set<StatementRepositoryEntity> stmts = entity.getStatements();
				stmts.add(stmt);
			}
			k2DAO.saveAccount(baseEntity);
		}
		saveAccountsToStatement(accountSet, stmt);
		
		String groupId = fetchGroupId(stmt);
		ScriptLogger.writeInfo("Group Id = " + groupId);
		stmt.setGroupId(groupId);
		
		saveStatementRepository(stmt);
	}

	private String fetchGroupId(StatementRepositoryEntity stmt) {
		// TODO Auto-generated method stub
		
		ScriptLogger.writeInfo("Fetch Group Id Called...");
		UUID institutionId = stmt.getInstitution().getId();
		Set<AccountBaseEntity> accounts = stmt.getAllAccounts();
		UUID userId = stmt.getUser().getId();
		
		String groupId = "";
		for (AccountBaseEntity account : accounts) {
			
			String accountNumber = account.getAccountNumber();
			String tag = account.getTag();
			
			AccountGroupDetails accountGroupDetail = k2DAO.fetchDetails(userId, institutionId, accountNumber);
			if(accountGroupDetail != null) {
				ScriptLogger.writeInfo("Group Id Available for Given Account....");
				groupId = accountGroupDetail.getGroupId();
			}else {
				ScriptLogger.writeInfo("Details not Available so Creating New Entry...");
				Random random = new Random();
				int randomNumber = random.nextInt(999999 - 100000) + 100000;
				
				AccountGroupDetails detail = new AccountGroupDetails();
				detail.setUser(stmt.getUser());
				detail.setInstitution(stmt.getInstitution());
				detail.setAccountNumber(accountNumber);
				detail.setTag(tag);
				if(StringUtils.isEmpty(groupId)) {
					detail.setGroupId(Integer.toString(randomNumber));
				}else {
					detail.setGroupId(groupId);
				}
				
				AccountGroupDetails storeDetails =  k2DAO.saveAccountGroupDetails(detail);
				if(storeDetails != null) {
					groupId = storeDetails.getGroupId();
				}
			}
			
		}
		return groupId;
	}

	private void saveAccountsToStatement(HashMap<String, Set<AccountBaseEntity>> accountSet, StatementRepositoryEntity stmt) {

		Set<String> keySet = accountSet.keySet();

		for(String key: keySet) {
			Set<AccountBaseEntity> tempSet = accountSet.get(key);
			if(tempSet != null) {
				if(key.equals(Constants.TAG_BANK)) {
					Set<BankAccountEntity> setBank = new HashSet<BankAccountEntity>();
					for(AccountBaseEntity base: tempSet) {
						BankAccountEntity account = (BankAccountEntity) base;
						setBank.add(account);
					}
					stmt.setBankAccounts(setBank);
					
				}else if(key.equals(Constants.TAG_CARD)) {
					Set<CardAccountEntity> setCard = new HashSet<CardAccountEntity>();
					for(AccountBaseEntity base: tempSet) {
						CardAccountEntity account = (CardAccountEntity) base;
						setCard.add(account);
					}
					stmt.setCardAccounts(setCard);
					
				}else if(key.equals(Constants.TAG_LOAN)) {
					Set<LoanAccountEntity> setLoan = new HashSet<LoanAccountEntity>();
					for(AccountBaseEntity base: tempSet) {
						LoanAccountEntity account = (LoanAccountEntity) base;
						setLoan.add(account);
					}
					stmt.setLoanAccounts(setLoan);
					
				}else if(key.equals(Constants.TAG_FIXED_DEPOSIT)) {
					Set<FixedDepositAccountEntity> setFD = new HashSet<FixedDepositAccountEntity>();
					for(AccountBaseEntity base: tempSet) {
						FixedDepositAccountEntity account = (FixedDepositAccountEntity) base;
						setFD.add(account);
					}
					stmt.setFdAccounts(setFD);
					
				}else if(key.equals(Constants.TAG_INVESTMENT)) {
					Set<InvestmentAccountEntity> setInv = new HashSet<InvestmentAccountEntity>();
					for(AccountBaseEntity base: tempSet) {
						InvestmentAccountEntity account = (InvestmentAccountEntity) base;
						setInv.add(account);
					}
					stmt.setInvestmentAccounts(setInv);
				}
			}
		}
	}

	public String generateXML(List<AccountBaseEntity> accountsEntity, FileRepositoryEntity repo) throws Exception {

		ScriptLogger.writeInfo("inside generateXML");

			ParserResponse pr = new ParserResponse();

			String instName = repo.getInstitution().getName();
			for(AccountBaseEntity entity: accountsEntity) {
				
				if(StringUtils.isEmpty(instName)) {
					instName = entity.getInstitutionName();
				}
				
				if(isBankAccountEntity(entity)) {
					BankAccountEntity entityBank = (BankAccountEntity) entity;
					BankAccount account = new BankAccount();
					EntityUtil.copyToAccount(entity, account);
					pr.addBankAccount(account);
					List<BankTransactionEntity> txnEntities = entityBank.getTransactions();
					for(BankTransactionEntity txnE: txnEntities) {
						BankTransaction txn = new BankTransaction();
						EntityUtil.copyToTransaction(txnE, txn);
						account.addTransaction(txn);
					}
				}else if(isCardAccountEntity(entity)) {
					CardAccountEntity entityCard = (CardAccountEntity) entity;
					CardAccount account = new CardAccount();
					EntityUtil.copyToAccount(entity, account);
					pr.addCardAccount(account);
					List<CardTransactionEntity> txnEntities = entityCard.getTransactions();
					for(CardTransactionEntity txnE: txnEntities) {
						CardTransaction txn = new CardTransaction();
						EntityUtil.copyToTransaction(txnE, txn);
						account.addTransaction(txn);
					}
				}else if(isLoanAccountEntity(entity)) {
					LoanAccountEntity entityLoan = (LoanAccountEntity) entity;
					LoanAccount account = new LoanAccount();
					EntityUtil.copyToAccount(entity, account);
					pr.addLoanAccount(account);
					List<LoanTransactionEntity> txnEntities = entityLoan.getTransactions();
					for(LoanTransactionEntity txnE: txnEntities) {
						LoanTransaction txn = new LoanTransaction();
						EntityUtil.copyToTransaction(txnE, txn);
						account.addTransaction(txn);
					}
				}else if(isFDAccountEntity(entity)) {
					FixedDepositAccount account = new FixedDepositAccount();
					EntityUtil.copyToAccount(entity, account);
					pr.addFdAccount(account);
					
				}else if(isInvestmentAccountEntity(entity)) {
					InvestmentAccountEntity entityInv = (InvestmentAccountEntity) entity;
					InvestmentAccount account = new InvestmentAccount();
					EntityUtil.copyToAccount(entity, account);
					
					pr.addInvestmentAccount(account);
					List<HoldingAssetEntity> assetEntities = entityInv.getAssets();
					List<InvestmentTransactionEntity> txnEntities = entityInv.getTransactions();

					for(HoldingAssetEntity assetE: assetEntities) {
						HoldingAsset asset = new HoldingAsset();
						EntityUtil.copyToAsset(assetE, asset);
						account.addAsset(asset);
					}
					for(InvestmentTransactionEntity txnE: txnEntities) {
						InvestmentTransaction txn = new InvestmentTransaction();
						EntityUtil.copyToInvestmentTransaction(txnE, txn);
						account.addTransaction(txn);
					}
					
				}else if(isGenericAccountEntity(entity)) {
					GenericAccount account = new GenericAccount();
					EntityUtil.copyToAccount(entity, account);
					pr.addGenericContainer(account);
				}
			}
			
			pr.setInstitutionName(instName);
			pr.setFromObject(true);
			pr.setOutputFileGroup(repo.getOutputFileGroup());
			pr.setHeaderGroup(repo.getHeaderGroup());
			pr = WebUtil.getParserResponseObjectByte(pr);
			
			String data = WebUtil.convertToJSON(pr);
			
//			ScriptLogger.writeInfo("data =>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//			ScriptLogger.writeInfo(data);
			
			ScriptLogger.writeInfo("Going to call convertToXML API");
			
			String url = getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "convertToXML";

			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);

			client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
			//ScriptLogger.writeInfo(data);
			client.setStringEntity(data);

			String result = client.getResponseForPostRequest();
			ScriptLogger.writeInfo("convertToXML API response recieved");
			ObjectMapper mapper = new ObjectMapper();

			ScriptLogger.writeInfo(result);

			pr = mapper.readValue(result, ParserResponse.class);

//			XMLUtil.convertToXML(pr, instName, null);

			String randomString = RandomStringUtils.randomAlphanumeric(12);
			String xmlPath = Constants.STATEMENT_HOME + randomString + "/xml/";
			
			ScriptLogger.writeInfo("xmlPath => " + xmlPath);
			String xmlTrans = pr.getXmlTrans();
			String xmlAsset = pr.getXmlAsset();
			String xmlSCX = pr.getXmlAssetSCX();
			String xmlPSX = pr.getXmlAssetPSX();
			String xlsxByteTrans = pr.getXlsxByteTrans();
			String xlsxByteAssets = pr.getXlsxByteAssets();
			String xlsxByteSCX = pr.getXlsxByteSCX();
			String csvByteAssets = pr.getCsvByteAsstes();
			String csvByteTrans = pr.getCsvByteTrans();
			String csvByteSCX = pr.getCsvByteSCX();
			String jsonByteAssets = pr.getJSONByteAsstes();
			String jsonByteTrans = pr.getJSONByteTrans();
			String jsonByteSCX = pr.getJSONByteSCX();
			//TODO: check below three methods
			String xlsxByteGeneric = pr.getXlsxByteSCX();
			String csvByteGeneric = pr.getCsvByteSCX();
			String jsonByteGeneric = pr.getJSONByteSCX();

			File directory = new File(xmlPath);
			if(!directory.exists()){
				directory.mkdirs();
			}

			// For XML 
			if(StringUtils.isNotEmpty(xmlTrans)) {
				String filename = "xx-" + pr.getStatementDate() + "-trn-" + instName + ".trx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xmlTrans);
			}
			if(StringUtils.isNotEmpty(xmlAsset)) {
				String filename = "xx-" + pr.getStatementDate() + "-psn-" + instName + ".psx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xmlAsset);
			}
			if(StringUtils.isNotEmpty(xmlSCX)) {
				String filename = "xx-" + pr.getStatementDate() + "-sec-" + instName + ".scx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xmlSCX);
			}
			if(StringUtils.isNotEmpty(xmlPSX)) {
				String filename = "xx-" + pr.getStatementDate() + "-trn-init-" + instName + ".trx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xmlPSX);
			}
			
			// For XLSX
			if(StringUtils.isNotEmpty(xlsxByteTrans)) {
				String filename = "xx-" + pr.getStatementDate() + "-trn-" + instName + ".xlsx";
				String path = xmlPath + filename;
//				byte[] byteXLSX = new sun.misc.BASE64Decoder().decodeBuffer(xlsxByteTrans);
//				ScriptLogger.writeInfo("base 64 decoded =>>> " + new String(byteXLSX));
				WebUtil.writeFile(path, xlsxByteTrans);
			}
			
			if(StringUtils.isNotEmpty(xlsxByteAssets)) {
				String filename = pr.getStatementDate() + "-asset-" + instName + ".xlsx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xlsxByteAssets);
			}
						
			if(StringUtils.isNotEmpty(xlsxByteSCX)) {
				String filename = pr.getStatementDate() + "-scx-" + instName + ".xlsx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xlsxByteSCX);
			}
			
			// For CSV
			if(StringUtils.isNotEmpty(csvByteAssets)) {
				String filename = pr.getStatementDate() + "-asset-" + instName + ".csv";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, csvByteAssets);
			}
			
			if(StringUtils.isNotEmpty(csvByteTrans)) {
				String filename = pr.getStatementDate() + "-trn-" + instName + ".csv";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, csvByteTrans);
			}
			
			if(StringUtils.isNotEmpty(csvByteSCX)) {
				String filename = pr.getStatementDate() + "-scx-" + instName + ".csv";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, csvByteSCX);
			}
			
			// For JSON
			if(StringUtils.isNotEmpty(jsonByteAssets)) {
				String filename = pr.getStatementDate() + "-asset-" + instName + ".json";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, jsonByteAssets);
			}
			
			if(StringUtils.isNotEmpty(jsonByteTrans)) {
				String filename = pr.getStatementDate() + "-trn-" + instName + ".json";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, jsonByteTrans);
			}
			
			if(StringUtils.isNotEmpty(jsonByteSCX)) {
				String filename = pr.getStatementDate() + "-scx-" + instName + ".json";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, jsonByteSCX);
			}
			
			// For Generic
			if(StringUtils.isNotEmpty(xlsxByteGeneric)) {
				String filename = pr.getStatementDate() + "-untransformed-" + instName + ".xlsx";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, xlsxByteGeneric);
			}
			
			if(StringUtils.isNotEmpty(csvByteGeneric)) {
				String filename = pr.getStatementDate() + "-untransformed-" + instName + ".csv";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, csvByteGeneric);
			}
			
			if(StringUtils.isNotEmpty(jsonByteGeneric)) {
				String filename = pr.getStatementDate() + "-untransformed-" + instName + ".json";
				String path = xmlPath + filename;
				WebUtil.writeFile(path, jsonByteGeneric);
			}
			
			deleteXMLPath(repo.getFilePath()); //delete old xml directory if it exists
			repo.setFilePath(xmlPath);
			
			return xmlPath;
	}
	
	public boolean updateXMLFile(String repoId, String fileName, String xml) throws FileNotFoundException, IOException {
		
		FileRepositoryEntity repo = fetchFileRepositoryById(repoId);
		
		if(repo == null) {
			return false;
		}
		
		String xmlPath = repo.getFilePath();

		File directory = new File(xmlPath	);
		if(!directory.exists()){
			directory.mkdirs();
		}
	
		if(StringUtils.isNotEmpty(xml)) {
			String path = xmlPath + fileName;
			WebUtil.writeFile(path, xml);
			return true;
		}

		return false;
		
	}
	

	@SuppressWarnings("unchecked")
	public List<AccountBaseEntity> fetchAccountListByFileRepoId(String fileRepoId) throws IOException{

		List<AccountBaseEntity> accounts = new ArrayList<AccountBaseEntity>();

		FileRepositoryEntity fileRepo = fetchFileRepositoryById(fileRepoId);
		if(fileRepo == null) {
			return accounts;
		}
		String objectPath = fileRepo.getObjectPath();
		String filename = objectPath + "account.ser";

		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			accounts = (List<AccountBaseEntity>) in.readObject();
			
		} catch (Exception ex) {
			ScriptLogger.writeError("Exception e = ", ex);
		}finally {
			if(in != null) {
				in.close();
			}
			if(fis != null) {
				fis.close();
			}
		}
		return accounts;
	}

	public List<BankAccountEntity> fetchBankAccountListByFileRepoId(String fileRepoId) throws IOException{
		return EntityFinder.filterBankAccount(fetchAccountListByFileRepoId(fileRepoId));
	}

	public List<CardAccountEntity> fetchCardAccountListByFileRepoId(String fileRepoId) throws IOException{
		return EntityFinder.filterCardAccount(fetchAccountListByFileRepoId(fileRepoId));
	}

	public List<LoanAccountEntity> fetchLoanAccountListByFileRepoId(String fileRepoId) throws IOException{
		return EntityFinder.filterLoanAccount(fetchAccountListByFileRepoId(fileRepoId));
	}
	
	public List<FixedDepositAccountEntity> fetchFDAccountListByFileRepoId(String fileRepoId) throws IOException{
		return EntityFinder.filterFDAccount(fetchAccountListByFileRepoId(fileRepoId));
	}

	public List<InvestmentAccountEntity> fetchInvestmentAccountListByFileRepoId(String fileRepoId) throws IOException{
		return EntityFinder.filterInvestmentAccount(fetchAccountListByFileRepoId(fileRepoId));
	}

	public boolean updateFileRepo(List<AccountBaseEntity> accountList, String fileRepoId) {

		boolean result = false;

		FileRepositoryEntity fileRepo = fetchFileRepositoryById(fileRepoId);

		try {

			for(AccountBaseEntity account: accountList) {

				List<TransactionBaseEntity> txnEntityList = new ArrayList<TransactionBaseEntity>();

				if(account instanceof BankAccountEntity) {
					BankAccountEntity bank = (BankAccountEntity) account;
					txnEntityList.addAll(bank.getTransactions());
				}
				else if(account instanceof CardAccountEntity) {
					CardAccountEntity card = (CardAccountEntity) account;
					txnEntityList.addAll(card.getTransactions());
				}
				else if(account instanceof LoanAccountEntity) {
					LoanAccountEntity loan = (LoanAccountEntity) account;
					txnEntityList.addAll(loan.getTransactions());
				}
				else if(account instanceof InvestmentAccountEntity) {
					InvestmentAccountEntity inv = (InvestmentAccountEntity) account;
					List<HoldingAssetEntity> assetEntityList = inv.getAssets();

					List<SecurityMaster> masterList = inv.getScxList();

					for(HoldingAssetEntity asset: assetEntityList) {
						asset.setUpdatedAt(new Date());
					}

					for(SecurityMaster master: masterList) {
						master.setUpdatedAt(new Date());
					}

				}

				for(TransactionBaseEntity txn: txnEntityList) {
					txn.setUpdatedAt(new Date());
				}

				account.setUpdatedAt(new Date());

			}

			FileOutputStream fos = null;
			ObjectOutputStream out = null;

			String objectPath = fileRepo.getObjectPath();
			String filename = objectPath + "account.ser";

			File directory = new File(objectPath	);
			if(!directory.exists()){
				directory.mkdirs();
			}

			try {
				fos = new FileOutputStream(filename);
				try {
					ArrayList<AccountBaseEntity> accountList1 = 
							(ArrayList<AccountBaseEntity>) accountList;
					out = new ObjectOutputStream(fos);
					out.writeObject(accountList1);
					out.close();
				}catch (Exception e) {
					// TODO: handle exception
					ScriptLogger.writeInfo("Exception e = ", e);
				}finally {
					if(out != null) {
						out.close();
					}
				}
			} catch (Exception ex) {
				ScriptLogger.writeError("Exception e = ", ex);
				throw ex;
			}finally {
				if(fos != null) {
					fos.close();
				}
			}
			
			generateXML(accountList, fileRepo);

			saveFileRepository(fileRepo);
			result = true;
		}catch(Exception e) {
			ScriptLogger.writeError("Error", e);
		}

		return result;

	}

	public void deleteInvestmentTransactionFromFileRepo(InvestmentTransactionEntity txn, InvestmentAccountEntity accountEntity) {

		if(txn == null || accountEntity == null) {
			return;
		}

		List<InvestmentTransactionEntity> txnList = accountEntity.getTransactions();

		txnList.remove(txn);

	}
	
	public void deleteTransactionFromFileRepo(TransactionBaseEntity txn, AccountBaseEntity accountEntity) {

		if(txn == null || accountEntity == null) {
			return;
		}

		if(isBankAccountEntity(accountEntity)) {
			((BankAccountEntity) accountEntity).getTransactions().remove(txn);
		}
		else if(isCardAccountEntity(accountEntity)) {
			((CardAccountEntity) accountEntity).getTransactions().remove(txn);
		}
		else if(isLoanAccountEntity(accountEntity)) {
			((LoanAccountEntity) accountEntity).getTransactions().remove(txn);
		}
		else if(isInvestmentAccountEntity(accountEntity)) {
			((InvestmentAccountEntity) accountEntity).getTransactions().remove(txn);
		}
		
	}

	public void deleteAssetFromFileRepo(HoldingAssetEntity asset, InvestmentAccountEntity accountEntity) {

		if(asset == null || accountEntity == null) {
			return;
		}

		List<HoldingAssetEntity> assetList = accountEntity.getAssets();
		assetList.remove(asset);
	}

	public void deleteMasterFromFileRepo(SecurityMaster master, InvestmentAccountEntity accountEntity) {

		if(master == null || accountEntity == null) {
			return;
		}
		List<SecurityMaster> assetList = accountEntity.getScxList();
		assetList.remove(master);
	}

	public HoldingAssetEntity updateAssetFieldsInFileRepo(AssetEditFields assetNew, String fileRepoId) throws IOException {

		if(assetNew == null) {
			return null;
		}
		List<AccountBaseEntity> accountList = fetchAccountListByFileRepoId(fileRepoId);

		List<InvestmentAccountEntity> accountListInv = EntityFinder.filterInvestmentAccount(accountList);

		HoldingAssetEntity asset = EntityFinder.findAssetById(assetNew.getId(), accountListInv);

		EntityUtil.updateHoldingAssetEntity(asset, assetNew);

		updateFileRepo(accountList, fileRepoId);

		return asset;
	}

	public TransactionBaseEntity updateTransactionFieldsInFileRepo(TransactionEditFields txnNew, String fileRepoId) throws IOException {

		if(txnNew == null) {
			return null;
		}

		List<AccountBaseEntity> accountList = fetchAccountListByFileRepoId(fileRepoId);

		TransactionBaseEntity txn = EntityFinder.findTransactionById(fileRepoId, accountList);

		EntityUtil.updateTransactionEntity(txn, txnNew);

		updateFileRepo(accountList, fileRepoId);

		return txn;
	}

	public SecurityMaster updateMasterFieldsInFileRepo(SecurityMasterEditFields masterNew, String fileRepoId) throws IOException {

		if(masterNew == null) {
			return null;
		}

		List<AccountBaseEntity> accountList = fetchAccountListByFileRepoId(fileRepoId);

		List<InvestmentAccountEntity> accountListInv = EntityFinder.filterInvestmentAccount(accountList);

		SecurityMaster master = EntityFinder.findMasterById(masterNew.getId(), accountListInv);

		EntityUtil.updateSecurityMaster(master, masterNew);

		updateFileRepo(accountList, fileRepoId);

		return master;
	}

	@SuppressWarnings("unchecked")
	public List<JSONObject> getInvestmentDetails(String userId) {

		List<JSONObject> result = new ArrayList<JSONObject>();

		User user = fetchUserById(UUID.fromString(userId));

		List<InvestmentAccountEntity> accounts = user.getInvestmentAccounts();

		List<String> cateories = HoldingAsset.CATEGORY_LIST;

		ScriptLogger.writeInfo("account size -> " + accounts.size());
		ScriptLogger.writeInfo("categories size -> " + cateories.size());

		for(InvestmentAccountEntity account: accounts) {

			if(account.isConfirmed()) {
				List<InvestmentAccountEntity> accSoloList = new ArrayList<InvestmentAccountEntity>();
				accSoloList.add(account);

				JSONObject investment = new JSONObject();
				investment.put("id", account.getId());
				investment.put("institutionName", account.getInstitutionName());
				investment.put("accountName", account.getAccountName());
				investment.put("accountNumber", account.getAccountNumber());
				investment.put("currency", account.getCurrency());

				List<JSONObject> investmentHoldingAssetData = new ArrayList<JSONObject>();

				for(String category: cateories) {

					List<HoldingAssetEntity> confirmed = new ArrayList<HoldingAssetEntity>();
					List<HoldingAssetEntity> assets = EntityFinder.findAssetsByCategory(category, accSoloList);

					ScriptLogger.writeInfo("assets size -> "  + assets.size() + " for category " + category);
					for(HoldingAssetEntity asset: assets) {
						if(asset.isConfirmed()) {
							
							// For Currency Converted Value
							String value = asset.getHoldingAssetCurrentValue();
							String currency = asset.getHoldingAssetCurrency();
							if(StringUtils.isEmpty(value) || "0.00".equals(value) || "0.0".equals(value) || "0".equals(value)) {
								String quantity = asset.getHoldingAssetQuantity();
								String unitPrice = asset.getHoldingAssetIndicativePrice();
								if(asset.isBondNature()) {
									ScriptLogger.writeInfo("Bond Nature = true");
									double price = Double.parseDouble(unitPrice);
									if(price > 10) {
										price = price/100;
										unitPrice = String.valueOf(price);
									}
								}
								if(HoldingAsset.CATEGORY_CASH.equals(category)) {
									value = quantity;
								}else if(StringUtils.isNotEmpty(quantity) && StringUtils.isNotEmpty(unitPrice)) {
									value = (Double.parseDouble(quantity) * Double.parseDouble(unitPrice)) + "";
								}
							}
							
							Double convertedValue = currencyConvert(currency, user.getPreferredCurrency(), value, new Date());
							asset.setConvertedValue(convertedValue.toString());
							
							confirmed.add(asset);
						}
					}

					if(confirmed.size() > 0) {
						JSONObject asset = new JSONObject();
						asset.put("holdingAssetCategory", category);
						asset.put("investmentHoldingAssetByCategoryData", confirmed);
						investmentHoldingAssetData.add(asset);
					}
				}
				investment.put("investmentHoldingAssetData", investmentHoldingAssetData);
				result.add(investment);
			}
		}
		return result;
	}

	public List<InvestmentTransactionEntity> getInvestmentTransactions(String userId) {

		List<InvestmentTransactionEntity> result = new ArrayList<InvestmentTransactionEntity>();

		User user = fetchUserById(UUID.fromString(userId));

		List<InvestmentAccountEntity> accounts = user.getInvestmentAccounts();
		for(InvestmentAccountEntity account: accounts) {
			if(account.isConfirmed()) {
				List<InvestmentTransactionEntity> txns = account.getTransactions();
				for(InvestmentTransactionEntity txn:txns) {
					if(txn.isConfirmed()) {
						result.add(txn);
					}
				}
			}
		}
		return result;
	}

	public PreviewResponse getResponseForReview(List<AccountBaseEntity> accounts, String flow) {

		PreviewResponse response = new PreviewResponse();

		List<InvestmentAccountEntity> resultAccountsInv = new ArrayList<InvestmentAccountEntity>();
		List<BankAccountEntity> resultAccountsBank = new ArrayList<BankAccountEntity>();
		List<CardAccountEntity> resultAccountsCard = new ArrayList<CardAccountEntity>();
		List<LoanAccountEntity> resultAccountsLoan = new ArrayList<LoanAccountEntity>();
		List<FixedDepositAccountEntity> resultAccountsFD = new ArrayList<FixedDepositAccountEntity>();

		response.setInvestments(resultAccountsInv);
		response.setBanks(resultAccountsBank);
		response.setCards(resultAccountsCard);
		response.setLoans(resultAccountsLoan);
		response.setFixedDeposits(resultAccountsFD);
		
		for(AccountBaseEntity account: accounts) {

			if(account instanceof BankAccountEntity) {

				BankAccountEntity bank = (BankAccountEntity) account;
 				if((!bank.isConfirmed() && bank.isStatus()) || !Constants.FLOW_PIMONEY.equals(flow)) {
					resultAccountsBank.add(bank);
					ScriptLogger.writeInfo("Bank = " + bank.getAccountNumber());
					continue;
				}

				List<BankTransactionEntity> txnsOld = bank.getTransactions();
				List<BankTransactionEntity> txnsNew = new ArrayList<BankTransactionEntity>();

				for(BankTransactionEntity txn: txnsOld) {
					if(!txn.isConfirmed() && txn.isStatus()) {
						txnsNew.add(txn);
					}
				}
				bank.setTransactions(txnsNew);

				if(txnsNew.size() > 0) {
					resultAccountsBank.add(bank);
				}

			}else if(account instanceof CardAccountEntity) {

				CardAccountEntity card = (CardAccountEntity) account;

				if((!card.isConfirmed() && card.isStatus()) || !Constants.FLOW_PIMONEY.equals(flow)) {
					resultAccountsCard.add(card);
					continue;
				}

				List<CardTransactionEntity> txnsOld = card.getTransactions();
				List<CardTransactionEntity> txnsNew = new ArrayList<CardTransactionEntity>();

				for(CardTransactionEntity txn: txnsOld) {
					if(!txn.isConfirmed() && txn.isStatus()) {
						txnsNew.add(txn);
					}
				}
				card.setTransactions(txnsNew);

				if(txnsNew.size() > 0) {
					resultAccountsCard.add(card);
				}

			}else if(account instanceof LoanAccountEntity) {

				LoanAccountEntity loan = (LoanAccountEntity) account;

				if((!loan.isConfirmed() && loan.isStatus()) || !Constants.FLOW_PIMONEY.equals(flow)) {
					resultAccountsLoan.add(loan);
					continue;
				}

				List<LoanTransactionEntity> txnsOld = loan.getTransactions();
				List<LoanTransactionEntity> txnsNew = new ArrayList<LoanTransactionEntity>();

				for(LoanTransactionEntity txn: txnsOld) {
					if(!txn.isConfirmed() && txn.isStatus()) {
						txnsNew.add(txn);
					}
				}
				loan.setTransactions(txnsNew);

				if(txnsNew.size() > 0) {
					resultAccountsLoan.add(loan);
				}

			}else if(account instanceof FixedDepositAccountEntity) {

				FixedDepositAccountEntity fd = (FixedDepositAccountEntity) account;

				if((!fd.isConfirmed() && fd.isStatus()) || !Constants.FLOW_PIMONEY.equals(flow)) {
					resultAccountsFD.add(fd);
					continue;
				}

			}else if(account instanceof InvestmentAccountEntity) {

				InvestmentAccountEntity inv = (InvestmentAccountEntity) account;

				if((!inv.isConfirmed() && inv.isStatus()) || !Constants.FLOW_PIMONEY.equals(flow)) {
					resultAccountsInv.add(inv);
					continue;
				}

				List<HoldingAssetEntity> assetsOld = inv.getAssets();
				List<HoldingAssetEntity> assetsNew = new ArrayList<HoldingAssetEntity>();

				List<SecurityMaster> mastersOld = inv.getScxList();
				List<SecurityMaster> mastersNew = new ArrayList<SecurityMaster>();

				List<InvestmentTransactionEntity> txnsOld = inv.getTransactions();
				List<InvestmentTransactionEntity> txnsNew = new ArrayList<InvestmentTransactionEntity>();

				for(HoldingAssetEntity asset: assetsOld) {
					if(!asset.isConfirmed() && asset.isStatus()) {
						assetsNew.add(asset);
					}
				}

				for(InvestmentTransactionEntity txn: txnsOld) {
					if(!txn.isConfirmed() && txn.isStatus()) {
						txnsNew.add(txn);
					}
				}

				for(SecurityMaster master: mastersOld) {
					if(!master.isConfirmed() && master.isStatus()) {
						mastersNew.add(master);
					}
				}

				inv.setAssets(assetsNew);
				inv.setTransactions(txnsNew);
				inv.setScxList(mastersNew);

				if(assetsNew.size() > 0 || txnsNew.size() > 0 ||  mastersNew.size() > 0) {
					resultAccountsInv.add(inv);
				}
			}
		}
		return response;
	}
	
	
	public boolean isAccountElegibleForDeletion(AccountBaseEntity entity) {
		
		if(entity == null) {
			return false;
		}
		
		int count = 0;
		if(isBankAccountEntity(entity)) {
			count += ((BankAccountEntity) entity).getTransactions().size();
			
		}else if(isCardAccountEntity(entity)) {
			count += ((CardAccountEntity) entity).getTransactions().size();
			
		}else if(isLoanAccountEntity(entity)) {
			count += ((LoanAccountEntity) entity).getTransactions().size();
			
		}else if(isInvestmentAccountEntity(entity)) {
			count += ((InvestmentAccountEntity) entity).getTransactions().size();
			count += ((InvestmentAccountEntity) entity).getAssets().size();
			count += ((InvestmentAccountEntity) entity).getScxList().size();
		}
		ScriptLogger.writeInfo("total size -> " + count +" => " +  (count == 0));
		return (count == 0);
	}
	
	@Transactional
	public boolean deleteStatementRepository(String repoId, String accountNumber, String tag) {
		
		ScriptLogger.writeInfo("inside service deleteStatementRepository");
		try {
			
			StatementRepositoryEntity repo = fetchStatementRepositoryById(repoId);
			if(repo == null) {
				return false;
			}
			
			ScriptLogger.writeInfo("type => " + tag); 
			Set<? extends AccountBaseEntity> accounts = new HashSet<>();
			
			if(Constants.TAG_BANK.equals(tag)) {
				accounts = repo.getBankAccounts();
				ScriptLogger.writeInfo("type => " + tag);
				
			}else if(Constants.TAG_CARD.equals(tag)) {
				accounts = repo.getCardAccounts();
				ScriptLogger.writeInfo("type => " + tag);
				
			}else if(Constants.TAG_LOAN.equals(tag)) {
				accounts = repo.getLoanAccounts();
				ScriptLogger.writeInfo("type => " + tag);
				
			}else if(Constants.TAG_FIXED_DEPOSIT.equals(tag)) {
				accounts = repo.getFdAccounts();
				ScriptLogger.writeInfo("type => " + tag);
				
			}else if(Constants.TAG_INVESTMENT.equals(tag)) {
				accounts = repo.getInvestmentAccounts();
				ScriptLogger.writeInfo("type => " + tag);
			}
			ScriptLogger.writeInfo("total accounts -> " + accounts.size());
			
			AccountBaseEntity entity = EntityFinder.findAccountByAccountNumber(accountNumber, accounts, tag);
			if(entity != null) {
				ScriptLogger.writeInfo("entity type => " + entity.getTag());
				Set<StatementRepositoryEntity> repos = entity.getStatements();
				ScriptLogger.writeInfo("Repository Size = " + repos.size());
				repos.remove(repo);
				saveAccount(entity);
				accounts.remove(entity);
			}
			if(accounts.size() == 0) {
				ScriptLogger.writeInfo("no more accounts associated with this repo so deleting the repo");
				saveStatementRepository(repo);
				deleteStatementRepository(repo);
			}else {
				ScriptLogger.writeInfo("saving repo");
				saveStatementRepository(repo);
			}
		}catch(Exception e) {
			ScriptLogger.writeError("error", e);
			return false;
		}
		return true;
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Methods to check instance types ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public boolean isBankAccount(Container account) {
		return (account != null && account instanceof BankAccount);
	}
	
	public boolean isCardAccount(Container account) {
		return (account != null && account instanceof CardAccount);
	}
	
	public boolean isLoanAccount(Container account) {
		return (account != null && account instanceof LoanAccount);
	}
	
	public boolean isGenericAccount(Container account) {
		return (account != null && account instanceof GenericAccount);
	}
	
	public boolean isFDAccount(Container account) {
		return (account != null && account instanceof FixedDepositAccount);
	}
	
	public boolean isInvestmentAccount(Container account) {
		return (account != null && account instanceof InvestmentAccount);
	}
	
	public boolean isBankAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof BankAccountEntity);
	}
	
	public boolean isCardAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof CardAccountEntity);
	}
	
	public boolean isLoanAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof LoanAccountEntity);
	}
	
	public boolean isFDAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof FixedDepositAccountEntity);
	}
	
	public boolean isGenericAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof GenericAccountEntity);
	}
	
	public boolean isInvestmentAccountEntity(AccountBaseEntity account) {
		return (account != null && account instanceof InvestmentAccountEntity);
	}
	
	public boolean isBankTransaction(TransactionBase txn) {
		return (txn != null && txn instanceof BankTransaction);
	}
	
	public boolean isCardTransaction(TransactionBase txn) {
		return (txn != null && txn instanceof CardTransaction);
	}
	
	public boolean isLoanTransaction(TransactionBase txn) {
		return (txn != null && txn instanceof LoanTransaction);
	}
	
	public boolean isInvestmentTransaction(TransactionBase txn) {
		return (txn != null && txn instanceof InvestmentTransaction);
	}
	
	public boolean isBankTransactionEntity(TransactionBaseEntity txn) {
		return (txn != null && txn instanceof BankTransactionEntity);
	}
	
	public boolean isCardTransactionEntity(TransactionBaseEntity txn) {
		return (txn != null && txn instanceof CardTransactionEntity);
	}
	
	public boolean isLoanTransactionEntity(TransactionBaseEntity txn) {
		return (txn != null && txn instanceof LoanTransactionEntity);
	}
	
	public boolean isInvestmentTransactionEntity(TransactionBaseEntity txn) {
		return (txn != null && txn instanceof InvestmentTransactionEntity);
	}
	
	public String getAccountKey(AccountBaseEntity account) {
		return EntityUtil.maskString(account.getAccountNumber()) + "|" + account.getTag();
	}
	
	public String getAccountKey(Container account) {
		return EntityUtil.maskString(account.getAccountNumber()) + "|" + account.getTag();
	}
	
	public boolean fetchAndStoreCurrencyRates(Date date) {
		ScriptLogger.writeInfo("inside fetchAndStoreCurrencyRates");
		boolean result = false;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_DASH_MM_DASH_DD);
			String dateStr = sdf.format(date);
			String url = getConfigurationValue("CURRENCY_RATES", "CURRENCY_RATES_API") + dateStr + ".json?app_id=" + rateAppId;
			ScriptLogger.writeInfo("inside test1");
			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_GET);
			
			String rates = client.getResponseForGetRequest();
			ScriptLogger.writeInfo("inside test2");
			ObjectMapper mapper = new ObjectMapper();
			JSONObject json = mapper.readValue(rates, JSONObject.class);
			ScriptLogger.writeInfo("inside test3");
			// For if Response not get Contains Currency
			Object statusObj = json.get("status");
			if(statusObj != null && "400".equals(statusObj.toString())) {
				ScriptLogger.writeError("Currency Rate is not Available for Date = " + date);
				return false;
			}
			ScriptLogger.writeInfo("inside test4");
			String ratesString = mapper.writeValueAsString(json.get("rates"));
			HashMap<String, String> rateMap = mapper.readValue(ratesString, new TypeReference<HashMap<String, String>>() { });
			ScriptLogger.writeInfo(rateMap);
			ScriptLogger.writeInfo("inside test5");
			Set<String> keySet = rateMap.keySet();
			for(String currency: keySet) {
				CurrencyRate rateObj = null;
				Double rate = Double.valueOf(rateMap.get(currency));
				Date dateObj = sdf.parse(dateStr);
				rateObj = k2DAO.fetchRateByCurrencyAndDate(currency, dateObj);
				if(rateObj != null) {
					break;
				}
				rateObj = new CurrencyRate();
				rateObj.setCurrency(currency);
				rateObj.setRate(rate);
				rateObj.setDate(dateObj);
				k2DAO.saveRate(rateObj);
				rateObj = null;
			}
			ScriptLogger.writeInfo("inside test6");
			result = true;
		}
		catch(Exception e) {
			ScriptLogger.writeError("Error in fetching currency rates from server", e);
		}
		return result;	
	}
	
	public Double currencyConvert(String fromCurrency, String toCurrency, String amount, Date date) {
		
		long start = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		
		Double toCurrnecyAmount = 0d;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_DASH_MM_DASH_DD);
			String dateStr = sdf.format(date);
			Date dateObj = sdf.parse(dateStr);
			ScriptLogger.writeInfo(dateStr);
			ScriptLogger.writeInfo(dateObj);
			
			// if current Date and Date is Same then Set Date to Previous Date Date
			if(dateObj.equals(WebUtil.removeTimeStatmp(new Date()))) {
				ScriptLogger.writeInfo("############## Date is Same as Current Date #################");
				cal.add(Calendar.DAY_OF_YEAR, -1);
				dateObj = cal.getTime();
				dateObj = WebUtil.removeTimeStatmp(dateObj);
				ScriptLogger.writeInfo("Same Date Scenario : Date = " + dateObj);
				ScriptLogger.writeInfo("fromCurrency => " + fromCurrency + " :: toCurrency => " + toCurrency);
				Double fromRate = RateDTO.getRate(fromCurrency);
				if(fromRate == null) {
					ScriptLogger.writeInfo("going to fetch again");
					fetchAndStoreCurrencyRates(dateObj);
					k2DAO.fetchRateByDate(dateObj);
					fromRate = RateDTO.getRate(fromCurrency);
				}
				Double toRate = RateDTO.getRate(toCurrency);
				ScriptLogger.writeInfo("amount => " + amount + " :: fromRate => " + fromRate +" :: torate => " + toRate);
				if(StringUtils.isNotEmpty(amount)) {
					Double fromCurrencyToUSDAmount = Double.parseDouble(amount) * toRate;
					toCurrnecyAmount = fromCurrencyToUSDAmount / fromRate;
				}
				return toCurrnecyAmount;
			}
			
			CurrencyRate fromRate = k2DAO.fetchRateByCurrencyAndDate(fromCurrency, dateObj);
			ScriptLogger.writeInfo(fromRate);
			
			if(fromRate == null) {
				ScriptLogger.writeInfo("Currency Rate is not Available in Database : Date = " + dateObj);
				fetchAndStoreCurrencyRates(dateObj);
				fromRate = k2DAO.fetchRateByCurrencyAndDate(fromCurrency, dateObj);
			}
			CurrencyRate toRate = k2DAO.fetchRateByCurrencyAndDate(toCurrency, dateObj);
			
			if(fromRate != null && toRate != null) {
				ScriptLogger.writeInfo("To Rates = " + toRate.getRate());
				ScriptLogger.writeInfo("from Rates = " + fromRate.getRate());
				
				Double fromCurrencyToUSDAmount = Double.parseDouble(amount) * toRate.getRate();
				toCurrnecyAmount = fromCurrencyToUSDAmount / fromRate.getRate();
			}else {
				// return 0 if Date is not in Database
				ScriptLogger.writeError("Currency Rate not Found in DB");
				return toCurrnecyAmount;
			}	
		}catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeError("Error in fetching currency rates from Database", e);
		}
		ScriptLogger.writeInfo("Total Time Taken in converting currency -> " + (System.currentTimeMillis()-start) + " ms");
		return toCurrnecyAmount;	
	}	
	
	public StatementParsingDetail createStatementDetailEntity(DocumentRequest doc) throws Exception {
		// TODO Auto-generated method stub
		
		ScriptLogger.writeInfo("CreateStatementDetailEntity Called....");
		String countryCode = doc.getLocale();
		String institutionName = doc.getName();
		String status = Constants.PROCESSING;
		
		UUID userId = UUID.fromString(doc.getUserId());
		User user = fetchUserById(userId);
		
		Calendar cal = Calendar.getInstance();
		
		StatementParsingDetail stmtDetails = new StatementParsingDetail();
		stmtDetails.setUser(user);
		stmtDetails.setCountryCode(countryCode);
		stmtDetails.setInstitutionName(institutionName);
		stmtDetails.setStatus(status);
		stmtDetails.setStartTime(cal.getTime());
		stmtDetails.setTimezone(cal.getTimeZone().getID());
		k2DAO.saveStatementParsingDetail(stmtDetails);
		
		return stmtDetails;
	}

	public boolean setUserId(DocumentRequest doc, String authorization) throws Exception {
		// TODO Auto-generated method stub
		
		String platform = Constants.AUCTOR_PLATFORM_KEY;
		if(doc.getUid() == null) {
			ScriptLogger.writeInfo("Upload Statement by Client");
			platform = Constants.CONJURER_PLATFORM_KEY;
			
		}else if(doc.getUserId().equals(doc.getUid())) {
			ScriptLogger.writeInfo("Statement Upload by Advisor It Self");
		}else {
			User user = fetchUserById(UUID.fromString(doc.getUid()));
			if(user != null) {
				ScriptLogger.writeInfo("Statement Upload by Advisor For Client = " + user.getUsername());
				doc.setUserId(doc.getUid());
			}else {
				
				String url = getConfigurationValue(Constants.AUTH_SERVICE,Constants.AUTH_SERVICE_URL) + "verifyUser";
				
				PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
				client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
				client.setDataField("platform", platform);
				client.setDataField("userId", doc.getUserId());
				client.setDataField("uid", doc.getUid());
				client.setDataField("clientPlatform", Constants.CONJURER_PLATFORM_KEY);
				client.addHeader("secretkey", Constants.getSECRET_KEY());
				client.addHeader("Authorization", authorization);
				
				String result = client.getResponseForPostRequest();
				ObjectMapper mapper = new ObjectMapper();
				JSONObject verifyTokenResponse = mapper.readValue(result, JSONObject.class);
				
				if(verifyTokenResponse != null){
					
					int status = (int) verifyTokenResponse.get("status");
					if(status == 0) {	
						User createUser = createUser(doc.getUid(), verifyTokenResponse.get("message").toString());
						doc.setUserId(doc.getUid());
					}else {
						ScriptLogger.writeInfo("################ UnAuthorized User ###################");
						return false;
					}
				}
			}
		}
		return true;
	}	
	
	public User createUser(String userId, String username) {
		
		ScriptLogger.writeInfo("Creating User.....");
		User usr = new User();
		usr.setId(UUID.fromString(userId));
		usr.setUsername(username);
		usr.setPreferredCurrency("SGD");
		saveUser(usr);
		return usr;
	}
}
