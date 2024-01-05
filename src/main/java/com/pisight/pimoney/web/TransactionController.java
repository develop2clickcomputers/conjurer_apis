package com.pisight.pimoney.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.AddTransactionDTO;
import com.pisight.pimoney.dto.CategoryRequest;
import com.pisight.pimoney.dto.TransactionRequest;
import com.pisight.pimoney.repository.entities.Category;
import com.pisight.pimoney.repository.entities.Merchant;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;
import com.pisight.pimoney.service.TransactionServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transacrtionServiceImpl = null;

	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public List<Category> getCategoryList (@RequestBody CategoryRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getCategories called");
		return transacrtionServiceImpl.getCategoryList(request);
	}
	
	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST)
	public List<Merchant> getMerchantList(@RequestBody CategoryRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getMarchants called");
		return transacrtionServiceImpl.getMerchantList(request);
	}
	
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "/getTransactions", method = RequestMethod.POST)
	public JSONObject getTransactions(@RequestBody TransactionRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("get transactions Called");
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";

		JSONObject response = new JSONObject();
		List<TransactionBaseEntity> transactions = new ArrayList<TransactionBaseEntity>();

		try {

			String userId = request.getUserId();
			String tag = request.getTag();
			
			if(tag.equals("ALL")) {
				
				transactions.addAll(transacrtionServiceImpl.getTransactions(userId, Constants.TAG_BANK));
				transactions.addAll(transacrtionServiceImpl.getTransactions(userId, Constants.TAG_CARD));
				transactions.addAll(transacrtionServiceImpl.getTransactions(userId, Constants.TAG_LOAN));
				
			}else {
				transactions = transacrtionServiceImpl.getTransactions(userId, tag);
			}
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "transaction details fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in getInvestments", e);
		}

		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("transactions", transactions);

		return response;

	}
	
	@RequestMapping(value = "/deleteTransaction", method = RequestMethod.POST)
	public JSONObject deleteTransaction(@RequestBody TransactionRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("delete transactions Called");
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Transaction Deletion Failed";

		JSONObject response = new JSONObject();

		try {

			String userId = request.getUserId();
			String tag = request.getTag();
			String txnId = request.getTransactionId();
			
			boolean result =  transacrtionServiceImpl.deleteTransaction(userId, tag, txnId);
			ScriptLogger.writeInfo("REsult = " + result);
			if(result) {
				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "transaction details deleted";
			}
		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in delete Transaction", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
	public JSONObject addTransaction(@RequestBody AddTransactionDTO request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("add transactions Called");
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Transaction add Failed";
		TransactionBaseEntity txn = null;

		JSONObject response = new JSONObject();

		try {
			txn = transacrtionServiceImpl.addTransaction(request);
			ScriptLogger.writeWarning("TXN =>>> " + txn);
			if(txn != null) {
				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Transaction add Success";
			}
		}
		catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in add Transaction", e);
		}
		
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", txn);
		return response;
	}
	
	@RequestMapping(value = "/expensesCategoryGraph", method = RequestMethod.POST)
	public JSONObject expensesCategoryGraph(@RequestBody TransactionRequest request) throws JsonProcessingException, ParseException  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("expensesCategoryGraph Called");
		return transacrtionServiceImpl.expensesCategoryGraph(request);
	}
	
	@RequestMapping(value = "/transactionCategoryGraph", method = RequestMethod.POST)
	public JSONObject transactionCategoryGraph(@RequestBody TransactionRequest request) throws JsonProcessingException, ParseException  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("transactionCategoryGraph Called");
		return transacrtionServiceImpl.transactionCategoryGraph(request);
	}
}
