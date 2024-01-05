package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.DashboardRequest;
import com.pisight.pimoney.service.OverviewServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class OverviewController {

	@Autowired
	private OverviewServiceImpl overviewServiceImpl = null;

	@RequestMapping(value = "/dashboardDetails", method = RequestMethod.POST)
	public JSONObject getDashboardDetails (@RequestBody DashboardRequest request, @RequestHeader(value = "authorization") String authorization) throws Exception {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("dashboard Details Service called");
		return overviewServiceImpl.getDashboardDetails(request, authorization);
		
	}
	
	@RequestMapping(value = "/getBudgetData", method = RequestMethod.POST)
	public JSONObject getBudgetData(@RequestBody DashboardRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getBudgetData Service called in Overview Page");
		return overviewServiceImpl.getBudgetData(request);
	}
	
	@RequestMapping(value = "/deleteManualAccount", method = RequestMethod.POST)
	public JSONObject deleteManualAccount(@RequestBody DashboardRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteManualAccount called in Overview Page");
		return overviewServiceImpl.deleteManualAccount(request);
	}
	
	@RequestMapping(value = "/deleteOnlineAccount", method = RequestMethod.POST)
	public JSONObject deleteOnlineAccount(@RequestBody DashboardRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteOnlineAccount called in Overview Page");
		return overviewServiceImpl.deleteOnlineAccount(request);
	}
	
	@RequestMapping(value = "/changePreferredCurrency", method = RequestMethod.POST)
	public JSONObject changePreferredCurrency (@RequestBody DashboardRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("changePreferredCurrency Service called");
		return overviewServiceImpl.changePreferredCurrency(request);
	}
}
