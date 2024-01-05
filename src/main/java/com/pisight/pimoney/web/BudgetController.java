package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.BudgetRequest;

import com.pisight.pimoney.service.BudgetServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class BudgetController {

	@Autowired
	private BudgetServiceImpl budgetServiceImpl = null;

	@RequestMapping(value = "/addBudget", method = RequestMethod.POST)
	public JSONObject addBudget (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("addBudget Service called");
		return budgetServiceImpl.addBudget(request);
	}
	
	@RequestMapping(value = "/editBudget", method = RequestMethod.POST)
	public JSONObject editBudget (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("editBudget Service called");
		return budgetServiceImpl.editBudget(request);
	}
	
	@RequestMapping(value = "/deleteBudget", method = RequestMethod.POST)
	public JSONObject deleteBudget (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteBudget Service called");
		return budgetServiceImpl.deleteBudget(request);
	}
	
	@RequestMapping(value = "/getBudget", method = RequestMethod.POST)
	public JSONObject getBudget (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteBudget Service called");
		return budgetServiceImpl.getBudget(request);
	}
	
	@RequestMapping(value = "/spentvsbudgetGraph", method = RequestMethod.POST)
	public JSONObject spentvsbudgetGraph (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("spentvsbudgetGraph Service called");
		return budgetServiceImpl.spentvsbudgetGraph(request);
	}
	
	@RequestMapping(value = "/categoryWiseSpendingChart", method = RequestMethod.POST)
	public JSONObject categoryWiseSpendingChart (@RequestBody BudgetRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("categoryWiseSpendingChart Service called");
		return budgetServiceImpl.categoryWiseSpendingChart(request);
	}
}
