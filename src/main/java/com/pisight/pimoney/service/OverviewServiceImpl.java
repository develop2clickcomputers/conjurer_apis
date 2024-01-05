package com.pisight.pimoney.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.BudgetResponse;
import com.pisight.pimoney.dto.DashboardRequest;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.PropertyRequest;
import com.pisight.pimoney.repository.TransactionRepository;
import com.pisight.pimoney.repository.UserRepository;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.Budget;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.Property;
import com.pisight.pimoney.repository.entities.StatementRepositoryEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class OverviewServiceImpl {

	@Autowired
	private UserRepository userRepo = null;

	@Autowired
	private K2DAO k2DAO = null;

	@Autowired
	private K2ServiceImpl k2ServiceImpl= null;

	@Autowired
	private K2OnlineServiceImpl k2OnlineServiceImpl= null;

	@Autowired
	private TransactionServiceImpl txnServiceImpl= null;

	@Autowired
	private InvestmentServiceImpl investmentServiceImpl = null;

	@Autowired
	private TransactionRepository txnRepo= null;

	@Transactional
	public User fetchUserById(UUID id) {
		return userRepo.findById(id).get();
	}

	/**
	 * This method is used to add property in overview page
	 * @param request {@link PropertyRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject addProperty(PropertyRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";

		try {
			String userId = request.getUserId();
			User user = fetchUserById(UUID.fromString(userId));
			
			boolean loanFlag = request.isLoanFlag();
			UUID loanId = request.getLoanId();
			
			LoanAccountEntity loan = null;
			if(loanFlag) {
				
				if(loanId != null) {
					
					loan = k2DAO.fetchLoanById(loanId);
				}else {
					
					ScriptLogger.writeInfo("Create New Loan Account");
					loan = new LoanAccountEntity();
					loan.setAccountNumber(request.getLoanAccountNumber());
					loan.setBankId("Manually Added");
					loan.setBalance(request.getLoanOutstandingValue());
					loan.setCurrency(request.getCurrency());
					loan.setLoanResidualTerms(request.getLoanResidualTerms());
					loan.setInterestType(request.getInterestType());
					loan.setLastResetDate(request.getLastResetDate());
					loan.setInterestRate(request.getInterestRate());
					loan.setResetFrequency(request.getResetFrequency());
					loan = k2DAO.saveLoanAccount(loan);
				}
			}

			Property property = new Property();
			property.setUser(user);
			property.setLoan(loan);
			property.setName(request.getName());
			property.setType(request.getPropertyType());
			property.setPurchaseDate(request.getPurchaseDate());
			property.setOwnership(request.getOwnership());
			property.setPurpose(request.getPurpose());
			property.setCurrency(request.getCurrency());
			property.setAmount(request.getAmount());
			property.setMarketValue(request.getMarketValue());
			property.setMarketValueCurrency(request.getCurrency());
			property.setLoanOutstandingValue(request.getLoanOutstandingValue());
			property.setLoanOutstandingCurrency(request.getCurrency());
			property.setLoanResidualTerms(request.getLoanResidualTerms());
			property.setInterestType(request.getInterestType());
			property.setLastResetDate(request.getLastResetDate());
			property.setInterestRate(request.getInterestRate());
			property.setResetFrequency(request.getResetFrequency());
			property.setAddress(request.getAddress());
			property.setRemarks(request.getRemarks());			
			property.setStatus(true);
			k2DAO.saveProperty(property); 

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in add Property", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Add Property";
		}

		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to edit property
	 * @param request {@link PropertyRequest}
	 * @return JSONObject {@link JSONObject} 
	 */
	public JSONObject editProperty(PropertyRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Error in Edit Property";

		try {
			UUID propertyId = request.getPropertyId();
			Property property = k2DAO.fetchPropertyById(propertyId);

			if(property != null) {

				property.setName(request.getName());
				property.setType(request.getPropertyType());
				property.setPurchaseDate(request.getPurchaseDate());
				property.setOwnership(request.getOwnership());
				property.setPurpose(request.getPurpose());
				property.setCurrency(request.getCurrency());
				property.setAmount(request.getAmount());
				property.setMarketValue(request.getMarketValue());
				property.setMarketValueCurrency(request.getCurrency());
				property.setLoanOutstandingValue(request.getLoanOutstandingValue());
				property.setLoanOutstandingCurrency(request.getCurrency());
				property.setLoanResidualTerms(request.getLoanResidualTerms());
				property.setInterestType(request.getInterestType());
				property.setLastResetDate(request.getLastResetDate());
				property.setInterestRate(request.getInterestRate());
				property.setResetFrequency(request.getResetFrequency());
				property.setAddress(request.getAddress());
				property.setRemarks(request.getRemarks());
				k2DAO.saveProperty(property);

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";
			}
		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in edit Property", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Edit Property";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to delete property
	 * @param request {@link PropertyRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject deleteProperty(PropertyRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";

		try {
			UUID propertyId = request.getPropertyId();
			Property property = k2DAO.fetchPropertyById(propertyId);

			if(property != null) {

				k2DAO.deleteProperty(property);

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";
			}
		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in Delete Property", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Delete Property";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to display all information in overview page and overview page graphs
	 * @param request {@link DashboardRequest}
	 * @param authorization AuthorizationKey
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getDashboardDetails(DashboardRequest request, String authorization) throws Exception {
		// TODO Auto-generated method stub

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		
		JSONObject response = null;
		
		// Check for UserId and Set Proper UserId 
		String userId = request.getUserId();
		DocumentRequest doc = new DocumentRequest();
		doc.setUserId(request.getUserId());
		doc.setUid(request.getUid());
		boolean userResult = k2ServiceImpl.setUserId(doc, authorization);
		if(!userResult) {
			responseMessage = "UnAuthorized";
			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			return response;
		}
		userId  = doc.getUserId();
		ScriptLogger.writeInfo("USSSSSSERRRRRR = " + userId);
		request.setUserId(userId);

		JSONObject data = new JSONObject();
		try {
			User user = fetchUserById(UUID.fromString(userId));

			JSONObject bankDetails = getBankDetails(request);
			JSONObject cardDetails = getCardDetails(request);
			JSONObject investmentDetails = getInvestmentDetails(request);
			JSONObject loanDetails = getLoanDetails(request);
			JSONObject propertyDetails = getPropertyDetails(request);
			JSONObject depositDetails = getFDDetails(request);

			data.put("preferredCurrency", user.getPreferredCurrency());
			data.put("banks", bankDetails);
			data.put("creditcards", cardDetails);
			data.put("investments", investmentDetails);
			data.put("loans", loanDetails);
			data.put("deposits", depositDetails);
			data.put("properties", propertyDetails);

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			
			ScriptLogger.writeError("Error in Dashboard Service", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Dashboard Service";
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getBankDetails(DashboardRequest request) {
		// TODO Auto-generated method stub

		JSONObject bankDetails = new JSONObject();

		String userId = request.getUserId();
		User user = fetchUserById(UUID.fromString(userId));

		List<BankAccountEntity> responseAccounts = new ArrayList<BankAccountEntity>();

		Double totalBalance = 0d;
		List<BankAccountEntity> accounts = user.getBankAccounts();
		for(BankAccountEntity account: accounts) {
			if(account.isConfirmed() && account.isStatus() && !account.isManuallyAdded()) {
				Double convetedBalance =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getAccountBalance(), new Date());
				account.setConvertedBalance(convetedBalance.toString());
				totalBalance += convetedBalance;
				account.setTransactions(null);
				responseAccounts.add(account);
			}
		}
		bankDetails.put("totalBalance",totalBalance.toString());
		bankDetails.put("accounts", responseAccounts);
		return bankDetails;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getCardDetails(DashboardRequest request) {

		JSONObject cardDetails = new JSONObject();

		String userId = request.getUserId();
		User user = fetchUserById(UUID.fromString(userId));

		List<CardAccountEntity> responseAccounts = new ArrayList<CardAccountEntity>();

		Double totalBalance = 0d;
		List<CardAccountEntity> accounts = user.getCardAccounts();
		for(CardAccountEntity account: accounts) {
			if(account.isConfirmed() && account.isStatus() && !account.isManuallyAdded()) {
				Double convetedBalance =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getAmountDue(), new Date());
				account.setConvertedBalance(convetedBalance.toString());
				totalBalance += convetedBalance;
				account.setTransactions(null);
				responseAccounts.add(account);
			}
		}
		cardDetails.put("totalBalance",totalBalance.toString());
		cardDetails.put("accounts", responseAccounts);
		return cardDetails;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getInvestmentDetails(DashboardRequest request) {

		JSONObject investmentDetails = new JSONObject();

		String userId = request.getUserId();
		User user = fetchUserById(UUID.fromString(userId));

		List<InvestmentAccountEntity> responseAccounts = new ArrayList<InvestmentAccountEntity>();

		Double totalBalance = 0d;
		List<InvestmentAccountEntity> accounts = user.getInvestmentAccounts();
		for(InvestmentAccountEntity account: accounts) {
			if(account.isConfirmed() && account.isStatus()) {
				Double convetedBalance =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getBalance(), new Date());
				account.setConvertedBalance(convetedBalance.toString());
				totalBalance += convetedBalance;
				account.setAssets(null);
				account.setScxList(null);
				account.setTransactions(null);
				responseAccounts.add(account);
			}
		}
		investmentDetails.put("totalBalance",totalBalance.toString());
		investmentDetails.put("accounts", responseAccounts);
		return investmentDetails;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getLoanDetails(DashboardRequest request) {

		JSONObject loanDetails = new JSONObject();

		String userId = request.getUserId();
		User user = fetchUserById(UUID.fromString(userId));

		List<LoanAccountEntity> responseAccounts = new ArrayList<LoanAccountEntity>();

		Double totalBalance = 0d;
		List<LoanAccountEntity> accounts = user.getLoanAccounts();
		for(LoanAccountEntity account: accounts) {
			if(account.isConfirmed() && account.isStatus() && !account.isManuallyAdded()) {
				Double convetedBalance =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getBalance(), new Date());
				account.setConvertedBalance(convetedBalance.toString());
				totalBalance += convetedBalance;
				account.setTransactions(null);
				responseAccounts.add(account);
			}
		}
		loanDetails.put("totalBalance",totalBalance.toString());
		loanDetails.put("accounts", responseAccounts);
		return loanDetails;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject getFDDetails(DashboardRequest request) {

		JSONObject loanDetails = new JSONObject();

		String userId = request.getUserId();
		User user = fetchUserById(UUID.fromString(userId));

		List<FixedDepositAccountEntity> responseAccounts = new ArrayList<FixedDepositAccountEntity>();

		Double totalBalance = 0d;
		List<FixedDepositAccountEntity> accounts = user.getFdAccounts();
		for(FixedDepositAccountEntity account: accounts) {
			if(account.isConfirmed() && account.isStatus()) {
				Double convetedBalance =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getPrincipleAmount(), new Date());
				account.setConvertedBalance(convetedBalance.toString());
				totalBalance += convetedBalance;
				responseAccounts.add(account);
			}
		}
		loanDetails.put("totalBalance",totalBalance.toString());
		loanDetails.put("accounts", responseAccounts);
		return loanDetails;
	}

	@SuppressWarnings("unchecked")
	private JSONObject getPropertyDetails(DashboardRequest request) {
		// TODO Auto-generated method stub

		JSONObject propertyDetails = new JSONObject();

		try {
			String userId = request.getUserId();
			User user = fetchUserById(UUID.fromString(userId));

			List<Property> properties = new ArrayList<Property>();

			Double totalBalance = 0d;
			List<Property> accounts = user.getProperty();
			for(Property account: accounts) {
				if(account.isStatus()) {
					Double convetedAmount =  k2ServiceImpl.currencyConvert(account.getCurrency(), user.getPreferredCurrency(), account.getAmount(), new Date());
					account.setConvertedBalance(convetedAmount.toString());
					totalBalance += convetedAmount;
					properties.add(account);
				}
			}
			propertyDetails.put("totalBalance",totalBalance.toString());
			propertyDetails.put("property", properties);
		}catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeError("Exception e = ", e);
		}

		return propertyDetails;
	}

	/**
	 * This method is used to get all budget related data
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getBudgetData(DashboardRequest request) {
		// TODO Auto-generated method stub

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";

		JSONObject data = new JSONObject();
		Calendar cal = Calendar.getInstance();

		try {
			UUID userId = UUID.fromString(request.getUserId());
			User user = fetchUserById(userId);

			cal.set(Calendar.DATE, 1);
			Date startDate = WebUtil.removeTimeStatmp(cal.getTime());
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			Date endDate = WebUtil.removeTimeStatmp(cal.getTime());

			List<Budget> budgets = k2DAO.fetchBudgetsByDATE(userId, startDate);

			ScriptLogger.writeInfo("Month Start Date = " + startDate);
			ScriptLogger.writeInfo("Month End Date Date = " + endDate);

			List<BudgetResponse> budgetResponses = new ArrayList<BudgetResponse>();
			Double totalSpentAmount = 0d;
			Double totalBudgetAmount = 0d;
			for(Budget budget : budgets) {

				Double currencyConvetedBudgetAmount = k2ServiceImpl.currencyConvert(budget.getCurrency(), user.getPreferredCurrency(), budget.getAmount(), new Date());
				totalBudgetAmount += currencyConvetedBudgetAmount;

				List<TransactionBaseEntity> transactions = new ArrayList<TransactionBaseEntity>();
				List<BankTransactionEntity> bankTransactions = txnRepo.getBankTransactionDetailsByDateInterval(userId, startDate, endDate);
				List<CardTransactionEntity> cardTransactions = txnRepo.getCardTransactionDetailsByDateInterval(userId, startDate, endDate);
				List<LoanTransactionEntity> loanTransactions = txnRepo.getLoanTransactionDetailsByDateInterval(userId, startDate, endDate);

				ScriptLogger.writeInfo("bank txn size => " + bankTransactions.size());
				for(BankTransactionEntity txn:bankTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				ScriptLogger.writeInfo("card txn size => " + cardTransactions.size());
				for(CardTransactionEntity txn:cardTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				ScriptLogger.writeInfo("loan txn size => " + loanTransactions.size());
				for(LoanTransactionEntity txn:loanTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}

				Double spentAmount = 0d;
				ScriptLogger.writeInfo("total txns => " + transactions.size());
				for(TransactionBaseEntity txn:transactions) {

					// Convert transation in Budget Currency
					spentAmount += k2ServiceImpl.currencyConvert(txn.getCurrency(),budget.getCurrency(), txn.getAmount(), txn.getTransDate());

					// Convert transaction Amount in User Preferred Currency
					totalSpentAmount += k2ServiceImpl.currencyConvert(txn.getCurrency(), user.getPreferredCurrency(), txn.getAmount(), txn.getTransDate()); 

				}
				BudgetResponse budgetResponse = new BudgetResponse();
				budgetResponse.setCurrency(budget.getCurrency());
				budgetResponse.setAmount(budget.getAmount());
				budgetResponse.setConvertedAmount(currencyConvetedBudgetAmount.toString());
				budgetResponse.setCategory(budget.getCategory().getName());

				budgetResponses.add(budgetResponse);
			}
			Double totalRemainingAmount = totalBudgetAmount - totalSpentAmount;

			Double budgetSpentPerc = 0d;
			if(totalBudgetAmount > 0) {
				budgetSpentPerc = (totalSpentAmount / totalBudgetAmount) * 100;
			}

			data.put("totalBudgetAmount", totalBudgetAmount.toString());
			data.put("totalSpentAmount", totalSpentAmount.toString());
			data.put("remainingBudgetAmount", totalRemainingAmount.toString());
			data.put("BudgetSpentPercentage", budgetSpentPerc.toString());
			data.put("budgets", budgetResponses);

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			responseMessage = "getBudgetData Service Failed";
			ScriptLogger.writeError("Exception e = ", e);
		}

		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}

	/**
	 * This method is used to change preferred currency 
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject changePreferredCurrency(DashboardRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";

		try {
			String userId = request.getUserId();
			User user = fetchUserById(UUID.fromString(userId));

			if(user != null) {

				user.setPreferredCurrency(request.getPreferredCurrency());
				k2ServiceImpl.saveUser(user);

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";
			}else {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "Oops! Something went wrong please try again..";
			}
		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in edit Property", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in changePreferredCurrency";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to delete account added through pdf statement
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject deleteManualAccount(DashboardRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		AccountBaseEntity account = null;
		try {
			List<JSONObject> accountObjectList = request.getAccountDetails();

			for(JSONObject accountObj : accountObjectList) {
				String accountId = accountObj.get("accountId").toString();
				String tag = accountObj.get("tag").toString();

				account = k2ServiceImpl.fetchAccount(accountId, tag);

				if(account != null) {

					account.setStatus(false);
					account.setConfirmed(false);
					k2DAO.saveAccount(account);

					Integer txnCount = txnServiceImpl.deleteAccountTransaction(UUID.fromString(accountId), tag);
					ScriptLogger.writeInfo("Account Found for Account Id = " + accountId);
					ScriptLogger.writeInfo("deleted txns => " + txnCount);

					if(tag.equals(Constants.CATEGORY_INVESTMENT)) {
						Integer assetCount = investmentServiceImpl.deleteAccountAsset(UUID.fromString(accountId));
						ScriptLogger.writeInfo("deleted Holding Asset => " + assetCount);
					}

					Set<StatementRepositoryEntity> statements = account.getStatements();
					ScriptLogger.writeInfo("Statement Repository Entry = " + statements.size());
					Iterator<StatementRepositoryEntity> stmtItr = statements.iterator();
					//ScriptLogger.writeInfo("Size = " + Iterators.size(stmtItr));
					ScriptLogger.writeInfo("True / False = " + stmtItr.hasNext());
					while(stmtItr.hasNext()) {
						StatementRepositoryEntity statement = stmtItr.next();
						if(statement != null) {
							ScriptLogger.writeInfo("Statement Id = " + statement.getId());
							k2ServiceImpl.deleteStatementRepository(statement.getId().toString(), account.getAccountNumber(), tag);
						}
					}

					errorCode = 0;
					responseStatus = Constants.SUCCESS;
					responseMessage = "Success";
				}else {
					errorCode = 99;
					responseStatus = Constants.FAILED;
					responseMessage = "Oops! Something went wrong please try again..";
				}
			}
		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in delete Manual Institutions", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in delete Manual Institutions";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", account);
		return response;
	}

	/**
	 * This method is used to delete online account added through Link Account in Overview Page
	 * @param request {@link DashboardRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject deleteOnlineAccount(DashboardRequest request) {

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		try {
			UUID accountId = request.getAccountId();
			String tag = request.getTag();
			String trackerId = request.getTrackerId();
			
			User user = k2ServiceImpl.fetchUserById(UUID.fromString(request.getUserId()));
			int totalAccounts = user.getAllAccounts().size();
			ScriptLogger.writeInfo("Total Account = " + totalAccounts);

			List<AccountBaseEntity> accounts = k2OnlineServiceImpl.fetchAccountsByBankId(trackerId, tag);
			
			for(AccountBaseEntity account: accounts) {

				boolean result = false;
				if(accountId.equals(account.getId())) {

					result = true;
					if(totalAccounts == 1) {
						result = k2OnlineServiceImpl.deleteUserDetail(trackerId);
					}

					if(result) {
						account.setStatus(false);
						account.setConfirmed(false);
						k2DAO.saveAccount(account);

						Integer txnCount = txnServiceImpl.deleteAccountTransaction(accountId, tag);
						ScriptLogger.writeInfo("Account Found for Account Id = " + accountId);
						ScriptLogger.writeInfo("deleted txns => " + txnCount);

						if(tag.equals(Constants.CATEGORY_INVESTMENT)) {
							Integer assetCount = investmentServiceImpl.deleteAccountAsset(accountId);
							ScriptLogger.writeInfo("deleted Holding Asset => " + assetCount);
						}

						errorCode = 0;
						responseStatus = Constants.SUCCESS;
						responseMessage = "Success";
					}
					else {
						errorCode = 100;
						responseStatus = Constants.FAILED;
						responseMessage ="Error in delete Online Institutions..";
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception

			ScriptLogger.writeError("Error in delete Online Account", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in delete Online Institutions";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
}
