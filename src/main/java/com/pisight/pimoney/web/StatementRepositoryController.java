package com.pisight.pimoney.web;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.DashboardRequest;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.AccountGroupDetails;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankAccountHistory;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardAccountHistory;
import com.pisight.pimoney.repository.entities.FileRepositoryEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetHistory;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountHistoryEntity;
import com.pisight.pimoney.repository.entities.RepositoryBase;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;
import com.pisight.pimoney.service.InvestmentServiceImpl;
import com.pisight.pimoney.service.K2ServiceImpl;
import com.pisight.pimoney.service.TransactionServiceImpl;
import com.pisight.pimoney.util.BankAccountEntityUtil;
import com.pisight.pimoney.util.CardAccountEntityUtil;
import com.pisight.pimoney.util.InvestmentAccountEntityUtil;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;


@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class StatementRepositoryController {

	@Autowired 
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Autowired 
	private TransactionServiceImpl txnServiceImpl = null;
	
	@Autowired
	private InvestmentServiceImpl investmentServiceImpl = null;
	
	@Autowired
	private K2DAO k2DAO = null;
	
	/**
	 * This method is used to get all statement repository details in "Statement Repo" page
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statementDetails", method = RequestMethod.POST)
	public JSONObject getStatementDetails(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("fetch statementDetails Called");
		long start = System.currentTimeMillis();
		
		JSONObject response = new JSONObject();
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";

		List<JSONObject> statementDetails = new ArrayList<JSONObject>();

		try {
			String userId = request.getUserId();
			String flow = request.getFlow();
			boolean isPimoneyFlow = true;

			if(!Constants.FLOW_PIMONEY.equals(flow)) {
				isPimoneyFlow = false;
			}

			List<? extends RepositoryBase> repos = k2ServiceImpl.fetchStatementRepositoryByUserId(userId);
			if(isPimoneyFlow) {
				ScriptLogger.writeInfo("pimoney flow");
				repos = k2ServiceImpl.fetchStatementRepositoryByUserId(userId);
			}else {
				ScriptLogger.writeInfo("file flow");
				repos = k2ServiceImpl.fetchFileRepositoryByUserId(userId);
			}

			for(RepositoryBase repo: repos) {

				JSONObject jsonRepo = new JSONObject();
				jsonRepo.put("id", repo.getId().toString());
				jsonRepo.put("institutionName", repo.getInstitution().getName());
				jsonRepo.put("country", repo.getInstitution().getCountry().getCode());
				jsonRepo.put("statementDate", repo.getStatementDate());
				jsonRepo.put("fileName", repo.getStatementName());
				jsonRepo.put("fileUploaded", repo.getCreatedAt());

				if(repo instanceof StatementRepositoryEntity) {
					StatementRepositoryEntity stmtRepo = (StatementRepositoryEntity) repo;
					Set<AccountBaseEntity> accts = stmtRepo.getAllAccounts();
					String accountNumber = "";
					String tag = "";
					for(AccountBaseEntity acct: accts) {
						if(StringUtils.isEmpty(accountNumber)) {
							accountNumber = accountNumber + acct.getAccountNumber();
						}else {
							accountNumber = accountNumber + "; " + acct.getAccountNumber();
						}
						
						if(StringUtils.isEmpty(tag)) {
							tag = acct.getTag();
						}
//						JSONObject repoTemp = (JSONObject) jsonRepo.clone();
//						repoTemp.put("accountNumber", acct.getAccountNumber());
//						repoTemp.put("tag", acct.getTag());
//						statementDetails.add(repoTemp);
					}
					jsonRepo.put("accountNumber", accountNumber);
					jsonRepo.put("tag",tag);
					statementDetails.add(jsonRepo);
					
				}else if(repo instanceof FileRepositoryEntity) {
					FileRepositoryEntity fileRepo = (FileRepositoryEntity) repo;
					String filePath = fileRepo.getFilePath();
					if(StringUtils.isNotEmpty(filePath)) {
						File oldDir = new File(filePath);
						List<String> fileList = new ArrayList<String>();
						if(oldDir != null) {
							String[]entries = oldDir.list();
							if(entries != null) {
								for(String s: entries){
									File currentFile = new File(oldDir.getPath(),s);
									fileList.add(currentFile.getName());
								}
							}
						}
						jsonRepo.put("files", fileList);
					}
					statementDetails.add(jsonRepo);
				}
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Statement Details Fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in fetching statement details", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("statementDetails", statementDetails);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
	
	/**
	 * This method is used to delete statement repository entry
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@RequestMapping(value = "/deleteRepository", method = RequestMethod.POST)
	public JSONObject deletePdfStatement(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deletePdfStatement Called....");
		long start = System.currentTimeMillis();
		
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {
			String flow = request.getFlow();
			String repoId = request.getRepoId();
			String tag = request.getTag();
			boolean isPimoneyFlow = true;

			if(!Constants.FLOW_PIMONEY.equals(flow)) {
				isPimoneyFlow = false;
			}

			if(isPimoneyFlow) {
				ScriptLogger.writeInfo("Statement Repo flow");				
				boolean status =  deleteStatement(repoId, tag);
			
			}else {
				ScriptLogger.writeInfo("File repo flow");
				FileRepositoryEntity repo = k2ServiceImpl.fetchFileRepositoryById(repoId);
				if(repo != null)	{
					ScriptLogger.writeInfo("deleting file repo");
					k2ServiceImpl.deleteFileRepository(repo);
				}
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Statement Deleted";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error while deleting respository", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
	
	@Transactional
	private boolean deleteStatement(String repoId, String tag) {
		
		ScriptLogger.writeInfo("inside service deleteStatement");
		try {
			
			StatementRepositoryEntity repo = k2ServiceImpl.fetchStatementRepositoryById(repoId);
			if(repo == null) {
				return false;
			}
			
			ScriptLogger.writeInfo("type => " + tag); 
			
			Set<? extends AccountBaseEntity> accounts = new HashSet<>();
			if(Constants.TAG_BANK.equals(tag)) {
				accounts = repo.getBankAccounts();
				
			}else if(Constants.TAG_CARD.equals(tag)) {
				accounts = repo.getCardAccounts();
				
			}else if(Constants.TAG_LOAN.equals(tag)) {
				accounts = repo.getLoanAccounts();
				
			}else if(Constants.TAG_FIXED_DEPOSIT.equals(tag)) {
				accounts = repo.getFdAccounts();
				
			}else if(Constants.TAG_INVESTMENT.equals(tag)) {
				accounts = repo.getInvestmentAccounts();
			}
			ScriptLogger.writeInfo("total accounts -> " + accounts.size());
			
			int size = accounts.size();
			
			AccountBaseEntity[] accts =   accounts.toArray(new AccountBaseEntity[0]);
			ScriptLogger.writeInfo("gwsdhvnsjkldhvnjsdov =>>> " + accts.length);
			
			for(int i = 0; i<size; i++) {
				AccountBaseEntity account = accts[i];
				ScriptLogger.writeInfo("Delete Account = " + account.getId() + " with Tag = " + tag);
				deleteAccount(account.getId().toString(), tag, repo);
			}
			
			accounts.clear();
			repo = k2ServiceImpl.saveStatementRepository(repo);
			
			// Delete Statement Repository
			k2ServiceImpl.deleteStatementRepository(repo);
			
			// For Delete Account Gropup Details Table
			List<StatementRepositoryEntity> stmtRepoEntries = k2DAO.fetchStmtRepoByGroupId(repo.getUser().getId(), repo.getGroupId());
			if(stmtRepoEntries.size() == 0) {
				
				List<AccountGroupDetails> accountGroupDetails = k2DAO.fetchDetailsByGroupId(repo.getUser().getId(), repo.getGroupId());
				for (AccountGroupDetails detail : accountGroupDetails) {
					k2DAO.deleteAccountGroupDetails(detail);
				}
				ScriptLogger.writeInfo("Number of Entry Deleted for Account Group Details = " + accountGroupDetails.size());
			}else {
				ScriptLogger.writeInfo("Account Statement Details Still Available....");
			}
		}catch(Exception e) {
			ScriptLogger.writeError("error", e);
			return false;
		}
		return true;
	}
	
	@Transactional
	private boolean deleteAccount(String accountId, String tag, StatementRepositoryEntity repo) {
		
		ScriptLogger.writeInfo("inside deleteAccount function");
		try {
			
			String stmtId = repo.getId().toString();
			
			AccountBaseEntity account = null;
			account = k2ServiceImpl.fetchAccount(accountId, tag);

			if(account != null) {
				
				Set<StatementRepositoryEntity> stmtRepos = account.getStatements();
				
				if(stmtRepos.size() > 1) {
					ScriptLogger.writeInfo("Account Link with Other Statement........");
					stmtRepos.remove(repo);
				}else {
					k2ServiceImpl.deleteAccount(account);
					return true;
				}

				Integer txnCount = txnServiceImpl.hardDeleteAccountTransaction(UUID.fromString(accountId), tag, stmtId);
				ScriptLogger.writeInfo("deleted txns => " + txnCount);

				ScriptLogger.writeInfo("Tag in Delete Account = " + tag);
				if(tag.equals(Constants.CATEGORY_INVESTMENT)) {
					Integer assetCount = investmentServiceImpl.hardDeleteAccountAsset(UUID.fromString(accountId), stmtId);
					ScriptLogger.writeInfo("deleted Holding Asset => " + assetCount);
					
					Integer securityMasterRecord = investmentServiceImpl.hardDeleteSecurityMaster(UUID.fromString(accountId), stmtId);
					ScriptLogger.writeInfo("deleted security master record => " + securityMasterRecord);
				}
				account = k2DAO.saveAccount(account);

				// Update Table from History Table
				if(account.getBillDate().equals(repo.getStatementDate())) {
					ScriptLogger.writeInfo("Latest Statement Delete flow");
					updateFromHistory(account, tag);
				}else {
					deleteAccountHistory(account.getAccountHash(), repo.getStatementDate(), tag, account.getUser().getId());
				}
			} 
		}catch (Exception e) {
			ScriptLogger.writeError("Exception e = ", e);
			return false;
		}
		return true;
	}
	
	private void deleteAccountHistory(String accountHash, Date stmtDate, String tag, UUID userId) {
		
		ScriptLogger.writeInfo("deleteAccountHistory called.....");
		if(Constants.TAG_BANK.equals(tag)) {
			k2DAO.deleteBankHistory(accountHash, stmtDate, userId);
		}else if(Constants.TAG_CARD.equals(tag)) {
			k2DAO.deleteCardHistory(accountHash, stmtDate, userId);
		}else if(Constants.TAG_INVESTMENT.equals(tag)) {
			k2DAO.deleteInvestmentHistory(accountHash, stmtDate, userId);
		}
	}

	@Transactional
	private void updateFromHistory(AccountBaseEntity account, String tag) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException {
	
		ScriptLogger.writeInfo("updateFromHistory called...");
		UUID userId = account.getUser().getId();
		
		// TODO Auto-generated method stub
		if(Constants.TAG_BANK.equals(tag)) {
			
			List<BankAccountHistory> bankHistories = k2DAO.fetchBankAccountHistory(account.getAccountHash(), userId);
			ScriptLogger.writeInfo("history size => " + bankHistories.size());
			
			if(bankHistories.size() > 0) {
				
				BankAccountHistory bankHistory = bankHistories.get(0);
				BankAccountEntity entity = (BankAccountEntity) account;
				
				String fingerPrint = entity.getFingerprint();
				String accountHash = entity.getAccountHash();
				
				ScriptLogger.writeInfo("Before Id = " + entity.getId());
				BankAccountEntityUtil.copyToBankAccountEntity(bankHistory, entity);
				ScriptLogger.writeInfo("After Id = " + entity.getId());
				
				k2DAO.saveBankAccount(entity);

				// Delete Current Record if Available History Table
				BankAccountHistory currentRecord = k2DAO.fetchBankHistory(fingerPrint, accountHash);
				if(currentRecord != null) {
					ScriptLogger.writeInfo("Current Record is Available in History Table");
					k2DAO.deleteBankHistory(currentRecord);
				}
				
				// For Delete Old Record that is insert in main table
				k2DAO.deleteBankHistory(bankHistory);
			}
			
		}else if(Constants.TAG_CARD.equals(tag)) {
			
			List<CardAccountHistory> cardHistories = k2DAO.fetchCardAccountHistory(account.getAccountHash(), userId);
			ScriptLogger.writeInfo("history size => " + cardHistories.size());
			
			if(cardHistories.size() > 0) {
				
				CardAccountHistory cardHistory = cardHistories.get(0);
				CardAccountEntity entity = (CardAccountEntity) account;
				
				String fingerPrint = entity.getFingerprint();
				String accountHash = entity.getAccountHash();
				
				ScriptLogger.writeInfo("Before Id = " + entity.getId());
				CardAccountEntityUtil.copyToCardAccountEntity(cardHistory, entity);
				ScriptLogger.writeInfo("After Id = " + entity.getId());
				
				k2DAO.saveCardAccount(entity);

				// Delete Current Record if Available History Table
				CardAccountHistory currentRecord = k2DAO.fetchCardHistory(fingerPrint, accountHash);
				if(currentRecord != null) {
					ScriptLogger.writeInfo("Current Record is Available in History Table");
					k2DAO.deleteCardHistory(currentRecord);
				}
				
				// For Delete Old Record that is insert in main table
				k2DAO.deleteCardHistory(cardHistory);
			}
			
		}else if(Constants.TAG_LOAN.equals(tag)) {
			
			// Code Already Written..
			ScriptLogger.writeInfo("Currently Not Implemented...");
			
		}else if(Constants.TAG_FIXED_DEPOSIT.equals(tag)) {
			
			// Code Already Written..
			ScriptLogger.writeInfo("Currently Not Implemented...");
			
		}else if(Constants.TAG_INVESTMENT.equals(tag)) {
			
			List<InvestmentAccountHistoryEntity> invHistories = k2DAO.fetchInvestmentAccountHistory(account.getAccountHash(), userId);
			ScriptLogger.writeInfo("history size => " + invHistories.size());
			
			if(invHistories.size() > 0) {
				
				InvestmentAccountHistoryEntity investmentHistory = invHistories.get(0);
				InvestmentAccountEntity entity = (InvestmentAccountEntity) account;
				
				String fingerPrint = entity.getFingerprint();
				String accountHash = entity.getAccountHash();
				
				ScriptLogger.writeInfo("Before Id = " + entity.getId());
				InvestmentAccountEntityUtil.copyToInvestmentAccountEntity(investmentHistory, entity);
				ScriptLogger.writeInfo("After Id = " + entity.getId());
				
				entity =  k2DAO.saveInvestmentAccount(entity);

				// Delete Current Record if Available History Table
				InvestmentAccountHistoryEntity currentRecord = k2DAO.fetchInvestmentHistory(fingerPrint, accountHash);
				if(currentRecord != null) {
					ScriptLogger.writeInfo("Current Record is Available in History Table");
					k2DAO.deleteInvestmentHistory(currentRecord);
				}
				
				// For Delete Old Record that is insert in main table
				k2DAO.deleteInvestmentHistory(investmentHistory);
			
				
				// Delete Record from Asset Table
				StatementRepositoryEntity stmtRepo = k2DAO.fetchStatementRepository(entity.getBillDate(), entity.getManualInstitution().getId(), userId);
				if(stmtRepo != null) {
					
					List<HoldingAssetHistory> assetHistories = k2DAO.fetchAssetHistory(stmtRepo.getId().toString());
					ScriptLogger.writeInfo("Number of Asset Found in Asset History = " + assetHistories.size());
					
					for (HoldingAssetHistory assetHistory : assetHistories) {
					}
					
				}else {
					ScriptLogger.writeInfo("No Record Found for Given Data = "+ entity.getBillDate() + " ; " + entity.getManualInstitution().getName() + " ; " + userId); 
				}				
			}
		}
	}

	/**
	 * This method is used to get account details that was added using pdf statement
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getManageAccountsDetails", method = RequestMethod.POST)
	public JSONObject getManageAccounts(@RequestBody DashboardRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getManageAccounts Called....");
		long start = System.currentTimeMillis();
		
		JSONObject response = new JSONObject();
		
		List<JSONObject> data = new ArrayList<>();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {
			
			String userId = request.getUserId();
			List<AccountGroupDetails> accountGroupDetails = k2DAO.fetchDetailsByUserId(UUID.fromString(userId));
			
			HashMap<String, AccountGroupDetails> groupMap = new HashMap<>();
			for (AccountGroupDetails detail : accountGroupDetails) {
				
				if(!groupMap.containsKey(detail.getGroupId())) {
					JSONObject information = new JSONObject();
					information.put("id", detail.getId());
					information.put("tag", detail.getTag());
					information.put("stmtGroupId", detail.getGroupId());
					information.put("institutionName",detail.getInstitution().getName());
					data.add(information);
				}
				groupMap.put(detail.getGroupId(), detail);
			}
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Statement Deleted";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error while getManageAccounts Apis", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
	
	/**
	 * This method is used to delete group statements in manage account tab
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@Transactional
	@RequestMapping(value = "/deleteGroupStatements", method = RequestMethod.POST)
	public JSONObject deleteGroupStatements(@RequestBody DashboardRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteGroupStatements Called....");
		long start = System.currentTimeMillis();
		
		JSONObject response = new JSONObject();
		
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {
			
			String userId = request.getUserId();
			String groupId = request.getStmtGroupId();
			String tag = request.getTag();
			
			List<StatementRepositoryEntity> stmtRepoEntries = k2DAO.fetchStmtRepoByGroupId(UUID.fromString(userId), groupId);
			ScriptLogger.writeInfo("Number of Entry in Statement Repository = " + stmtRepoEntries.size());
			
			for (StatementRepositoryEntity stmtRepoEntry : stmtRepoEntries) {
				deleteStatement(stmtRepoEntry.getId().toString(), tag);
			}
			
			List<AccountGroupDetails> accountGroupDetails = k2DAO.fetchDetailsByGroupId(UUID.fromString(userId), groupId);
			for (AccountGroupDetails detail : accountGroupDetails) {
				k2DAO.deleteAccountGroupDetails(detail);
			}
			ScriptLogger.writeInfo("Number of Entry Deleted for Account Group Details = " + accountGroupDetails.size());
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Statement Deleted";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error while deleting respository", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
}
