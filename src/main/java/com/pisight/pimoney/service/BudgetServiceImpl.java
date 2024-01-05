package com.pisight.pimoney.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.BudgetRequest;
import com.pisight.pimoney.dto.BudgetResponse;
import com.pisight.pimoney.repository.TransactionRepository;
import com.pisight.pimoney.repository.UserRepository;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.Budget;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class BudgetServiceImpl {

	@Autowired
	private UserRepository userRepo = null;	
	
	@Autowired
	private TransactionServiceImpl txnServiceImpl = null;
	
	@Autowired
	private K2DAO k2DAO = null;
	
	@Autowired
	private TransactionRepository txnRepo = null;
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;


	@Transactional
	public User fetchUserById(UUID id) {
		return userRepo.findById(id).get();
	}
	
	/**
	 * This method is used to add budget
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject addBudget(BudgetRequest request) {
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		
		try {
			UUID userId = UUID.fromString(request.getUserId());
			User user = fetchUserById(userId);
			
			String category = request.getCategory();
			String amount = request.getAmount();
			String currency = request.getCurrency();
			boolean thisMonth = request.getThisMonth();
			
			if(thisMonth) {
				ScriptLogger.writeInfo("Create Budget for This Month");
				Budget alreadyExistbudget = k2DAO.fetchBudgetByCategoryAndDATE(userId, category, WebUtil.removeTimeStatmp(cal.getTime()));
				
				if(alreadyExistbudget != null) {
					ScriptLogger.writeError("Budget Already Created For This Month");
					errorCode = 100;
					responseStatus = Constants.FAILED;
					responseMessage ="Budget Already Created For This Month";
				}else {
					Budget budget = new Budget();
					budget.setUser(user);
					budget.setCategory(txnServiceImpl.fetchCategoryByName(category));
					budget.setCurrency(currency);
					budget.setAmount(amount);
					budget.setBudgetTiming(WebUtil.removeTimeStatmp(cal.getTime()));
					budget.setStatus(true);
					k2DAO.saveBudget(budget);
					
					errorCode = 0;
					responseStatus = Constants.SUCCESS;
					responseMessage = "Success";
				}
			}else {
				ScriptLogger.writeInfo("Create Budget For Future Months");
				
				for(int i = 0;i<60;i++) {
					
					ScriptLogger.writeInfo("Date = " + cal.getTime());
					Budget alreadyExistbudget = k2DAO.fetchBudgetByCategoryAndDATE(userId, category, WebUtil.removeTimeStatmp(cal.getTime()));
					
					if(alreadyExistbudget != null) {
						ScriptLogger.writeError("All Month = Budget Already Created Month = " + cal.getTime());
				
					}else {
						Budget budget = new Budget();
						budget.setUser(user);
						budget.setCategory(txnServiceImpl.fetchCategoryByName(category));
						budget.setCurrency(currency);
						budget.setAmount(amount);
						budget.setBudgetTiming(WebUtil.removeTimeStatmp(cal.getTime()));
						budget.setStatus(true);
						k2DAO.saveBudget(budget);
						
						errorCode = 0;
						responseStatus = Constants.SUCCESS;
						responseMessage = "Success";
					}
					cal.add(Calendar.MONTH, 1);
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in add Budget", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Add Property";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
	
	/**
	 * This method is used to edit budget information
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject editBudget(BudgetRequest request) {
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		
		try {
			UUID userId = UUID.fromString(request.getUserId());
			UUID budgetId = request.getBudgetId();
			String amount = request.getAmount();
			boolean thisMonth = request.getThisMonth();
			
			Budget budget = k2DAO.fetchBudgetById(budgetId);
			if(budget!=null) {
				
				if(thisMonth) {
					ScriptLogger.writeInfo("Edit Budget for This Month");
					budget.setAmount(amount);
					k2DAO.saveBudget(budget);
				}else {
					ScriptLogger.writeInfo("Edit Budget for Future Month");
					String category = budget.getCategory().getName();
					Date budgetTime = budget.getBudgetTiming();
					
					List<Budget> budgets = k2DAO.getFutureBudgets(userId, category, budgetTime);
					ScriptLogger.writeInfo("Number of Budgert Updated = " + budgets.size());
					for(Budget bud : budgets) {
						bud.setAmount(amount);
						k2DAO.saveBudget(bud);
					}
				}

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";
				
			}else {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "Budget Update Failed";
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in edit Budget", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Edit Budget";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	
	/**
	 * This method is used to delete budget.
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject deleteBudget(BudgetRequest request) {
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Invalid type";
		
		try {
			UUID userId = UUID.fromString(request.getUserId());
			UUID budgetId = request.getBudgetId();
			boolean thisMonth = request.getThisMonth();
			
			Budget budget = k2DAO.fetchBudgetById(budgetId);
			if(budget!=null) {
				
				if(thisMonth) {
					ScriptLogger.writeInfo("Delete Budget for This Month");
					budget.setStatus(false);
					k2DAO.saveBudget(budget);
				}else {
					ScriptLogger.writeInfo("Delete Budget for Future Month");
					String category = budget.getCategory().getName();
					Date budgetTime = budget.getBudgetTiming();
					
					List<Budget> budgets = k2DAO.getFutureBudgets(userId, category, budgetTime);
					ScriptLogger.writeInfo("Number of Budgert Deleted = " + budgets.size());
					for(Budget bud : budgets) {
						bud.setStatus(false);
						k2DAO.saveBudget(bud);
					}
				}

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";
				
			}else {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage = "Budget Deleted Failed";
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in Delete Budget", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in Delete Budget";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
	
	/**
	 * This method is used to get all budgets
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getBudget(BudgetRequest request) {
		// TODO Auto-generated method stub
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage ="Error in Get Budget";
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		JSONObject data = new JSONObject();
		try {
			
			UUID userId = UUID.fromString(request.getUserId());
			User user = fetchUserById(userId);
			
			// For Page Display or not
			List<Budget> userBudgets = k2DAO.fetchBudgetsByUserId(userId);
			boolean isUserBudget = true;
			if(userBudgets == null) {
				isUserBudget = false;
			}
			
			cal.set(Calendar.MONTH,Integer.parseInt(request.getMonth())-1);
			cal.set(Calendar.YEAR,Integer.parseInt(request.getYear()));
			
			Date budgetDate = WebUtil.removeTimeStatmp(cal.getTime());
			ScriptLogger.writeInfo("Budget Date = " + budgetDate);
			
			cal.setTime(budgetDate);
			Date startDate = cal.getTime();
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			Date endDate = WebUtil.removeTimeStatmp(cal.getTime());
			
			ScriptLogger.writeInfo("Transaction From Date = " + startDate);
			ScriptLogger.writeInfo("Transaction To Date = " + endDate);
			
			List<BankTransactionEntity> bankTransactions = txnRepo.getBankTransactionDetailsByDateInterval(userId, startDate, endDate);
			List<CardTransactionEntity> cardTransactions = txnRepo.getCardTransactionDetailsByDateInterval(userId, startDate, endDate);
			List<LoanTransactionEntity> loanTransactions = txnRepo.getLoanTransactionDetailsByDateInterval(userId, startDate, endDate);
			
			ScriptLogger.writeInfo("bank txn size => " + bankTransactions.size());
			ScriptLogger.writeInfo("card txn size => " + cardTransactions.size());
			ScriptLogger.writeInfo("loan txn size => " + loanTransactions.size());
			
			List<Budget> budgets = k2DAO.fetchBudgetsByDATE(userId, budgetDate);
			ScriptLogger.writeInfo("Number of Budget = " + budgets.size());
			List<BudgetResponse> budgetResponses = new ArrayList<BudgetResponse>();
			Double totalSpentAmount = 0d;
			Double totalBudgetAmount = 0d;
			for(Budget budget : budgets) {
				
				Double currencyConvetedBudgetAmount = k2ServiceImpl.currencyConvert(budget.getCurrency(), user.getPreferredCurrency(), budget.getAmount(), new Date());
				totalBudgetAmount += currencyConvetedBudgetAmount;
				
				List<TransactionBaseEntity> transactions = new ArrayList<TransactionBaseEntity>();
				for(BankTransactionEntity txn:bankTransactions) {
					ScriptLogger.writeInfo(txn.getAmount());
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				
				for(CardTransactionEntity txn:cardTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				
				for(LoanTransactionEntity txn:loanTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && budget.getCategory().getName().equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				
				Double spentAmount = 0d;
				ScriptLogger.writeInfo("Total Transaction for Category = " + budget.getCategory().getName() + " : " + transactions.size());
				for(TransactionBaseEntity txn:transactions) {
				
					// Convert transation in Budget Currency
					spentAmount += k2ServiceImpl.currencyConvert(txn.getCurrency(),budget.getCurrency(), txn.getAmount(), txn.getTransDate());
					
					// Convert transaction Amount in User Preferred Currency
					totalSpentAmount += k2ServiceImpl.currencyConvert(txn.getCurrency(), user.getPreferredCurrency(), txn.getAmount(), txn.getTransDate()); 
					
				}
				Double remainingAmount = Double.parseDouble(budget.getAmount()) - spentAmount;
				
				BudgetResponse budgetResponse = new BudgetResponse();
				budgetResponse.setId(budget.getId());
				budgetResponse.setCurrency(budget.getCurrency());
				budgetResponse.setAmount(budget.getAmount());
				budgetResponse.setConvertedAmount(currencyConvetedBudgetAmount.toString());
				budgetResponse.setCategory(budget.getCategory().getName());
				budgetResponse.setImageUrl(budget.getCategory().getUrl());
				budgetResponse.setSpentAmount(spentAmount.toString());
				budgetResponse.setRemainingAmount(remainingAmount.toString());
				budgetResponse.setTransactions(transactions);
				
				budgetResponses.add(budgetResponse);
			}
			
			Double totalRemainingAmount = totalBudgetAmount - totalSpentAmount;
			data.put("totalBudgetAmount", totalBudgetAmount.toString());
			data.put("totalSpentAmount", totalSpentAmount.toString());
			data.put("totalRemainingAmount", totalRemainingAmount.toString());
			data.put("IsUserBudget", isUserBudget);
			data.put("budgets", budgetResponses);
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage ="Success";
			
		}catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeError("Error in Get Budget", e);
			responseMessage ="Error in Get Budget";
		}
		
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		
		return response;
	}
	
	/**
	 * This method is used to showing details of spentvsbudget graph.
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject spentvsbudgetGraph(BudgetRequest request) {
		// TODO Auto-generated method stub
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage ="Error in spentvsbudgetGraph";
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		List<JSONObject> data = new ArrayList<JSONObject>();
		try {
			
			UUID userId = UUID.fromString(request.getUserId());
			UUID budgetId = request.getBudgetId();
			
			Budget budget = k2DAO.fetchBudgetById(budgetId);
			if(budget != null) {
				cal.setTime(budget.getBudgetTiming());
			}
			
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
				
				Budget currentBudget = k2DAO.fetchBudgetByCategoryAndDATE(userId, budget.getCategory().getName(), startDate);
				
				Double budgetAmount = 0d;
				if(currentBudget != null) {
					budgetAmount = Double.parseDouble(budget.getAmount());
				}

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
				}
				
				JSONObject graphDetails = new JSONObject();
				graphDetails.put("budgetAmount", budgetAmount.toString());
				graphDetails.put("spentAmount", spentAmount.toString());
				graphDetails.put("date", startDate);
				data.add(graphDetails);	
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage ="Success";
			
		}catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeError("Error in spentvsbudgetGraph", e);
			responseMessage ="Error in spentvsbudgetGraph";
		}
		
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		
		return response;
	}
	
	/**
	 * This method is used to add budget page for showing graph category wise spending graph
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject categoryWiseSpendingChart(BudgetRequest request) {
		// TODO Auto-generated method stub
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage ="Error in spentvsbudgetGraph";
		
		Calendar cal = Calendar.getInstance();
		Double averageSpentAmount = 0d;
		List<JSONObject> data = new ArrayList<JSONObject>();
		
		cal.set(Calendar.DATE, 1);
		try {
			
			UUID userId = UUID.fromString(request.getUserId());
			User user = fetchUserById(userId);
			String category = request.getCategory();
			
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-3);
			int count = 0;
			Double spentAmount = 0d;
			for(int i = 3; i>0; i--) {

				cal.add(Calendar.MONTH, 1);
				cal.set(Calendar.DAY_OF_MONTH,1);
				Date startDate = WebUtil.removeTimeStatmp(cal.getTime());
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DAY_OF_MONTH, -1);
				Date endDate = WebUtil.removeTimeStatmp(cal.getTime());

				ScriptLogger.writeInfo("Month Start Date = " + startDate);
				ScriptLogger.writeInfo("Month End Date = " + endDate);
				

				List<TransactionBaseEntity> transactions = new ArrayList<TransactionBaseEntity>();
				List<BankTransactionEntity> bankTransactions = txnRepo.getBankTransactionDetailsByDateInterval(userId, startDate, endDate);
				List<CardTransactionEntity> cardTransactions = txnRepo.getCardTransactionDetailsByDateInterval(userId, startDate, endDate);
				List<LoanTransactionEntity> loanTransactions = txnRepo.getLoanTransactionDetailsByDateInterval(userId, startDate, endDate);
				
				ScriptLogger.writeInfo("bank txn size => " + bankTransactions.size());
				for(BankTransactionEntity txn:bankTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && category.equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				ScriptLogger.writeInfo("card txn size => " + cardTransactions.size());
				for(CardTransactionEntity txn:cardTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && category.equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				ScriptLogger.writeInfo("loan txn size => " + loanTransactions.size());
				for(LoanTransactionEntity txn:loanTransactions) {
					if(txn.isConfirmed() && txn.isStatus() && category.equals(txn.getCategory())) {
						transactions.add(txn);
					}
				}
				
				ScriptLogger.writeInfo("total txns => " + transactions.size());
				for(TransactionBaseEntity txn:transactions) {
					// Convert transation in Budget Currency
					spentAmount += k2ServiceImpl.currencyConvert(txn.getCurrency(),user.getPreferredCurrency(), txn.getAmount(), txn.getTransDate());
				}
				
				if(spentAmount > 0) {
					count++;
				}
			
				JSONObject graphDetails = new JSONObject();
				graphDetails.put("spentAmount", spentAmount.toString());
				graphDetails.put("date", startDate);
				data.add(graphDetails);	
			}
			
			if(count > 0) {
				averageSpentAmount = spentAmount / count;
			}
		
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage ="Success";
			
		}catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeError("Error in spentvsbudgetGraph", e);
			responseMessage ="Error in spentvsbudgetGraph";
		}
		
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		response.put("averageSpentAmount", averageSpentAmount);
		
		return response;
	}
}
