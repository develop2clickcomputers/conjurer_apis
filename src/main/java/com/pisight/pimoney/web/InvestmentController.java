package com.pisight.pimoney.web;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.InvestmentHeroRequestDTO;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.service.InvestmentServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class InvestmentController {
	
	@Autowired
	private InvestmentServiceImpl invServiceImpl= null;
	
	@RequestMapping(value = "/getInvestments", method = RequestMethod.POST)
	public JSONObject getInvestments(@RequestBody DocumentRequest doc, @RequestHeader(value = "authorization") String authorization)  {

		Thread.currentThread().setName(doc.getUserId());
		ScriptLogger.writeDebug("getInvestment Called");
		return invServiceImpl.getInvestments(doc, authorization);
	}

	@RequestMapping(value = "/getInvestmentTransactions", method = RequestMethod.POST)
	public JSONObject getInvestmentTransactions(@RequestBody DocumentRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getInvestment Called");
		return invServiceImpl.getInvestmentTransactions(request);
	}

	@RequestMapping(value = "/deleteInvestmentTransaction", method = RequestMethod.POST)
	public JSONObject deleteInvestmentTransaction(@RequestBody DocumentRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteInvestmentTransaction Called");
		return invServiceImpl.deleteInvestmentTransaction(request);
	}
	
	@RequestMapping(value = "/investmentHeroGraph", method = RequestMethod.POST)
	public JSONObject getDashboardDetails (@RequestBody InvestmentHeroRequestDTO request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("investmentHeroGraph Service called");
		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		
		try {
			List<HoldingAssetEntity> assets = invServiceImpl.findAssetByUserId(UUID.fromString(request.getUserId()));
			response = invServiceImpl.getHeroGraphChartData(assets, request.getView(), request.getUserId());
			
		}catch(Exception e) {
			ScriptLogger.writeError("error in investmentHeroGraph", e);
		}
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		
		return response;
	}
	
	@RequestMapping(value = "/investmentPerformance", method = RequestMethod.POST)
	public JSONObject getInvestmentPerformance (@RequestBody InvestmentHeroRequestDTO request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("investmentPerformance Service called");
		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		
		try {
			response = invServiceImpl.getPerformanceData(request.getUserId());
		}catch(Exception e) {
			ScriptLogger.writeError("error in investmentPerformance", e);
		}
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
	
	@RequestMapping(value = "/fetchTransactionCode", method = RequestMethod.POST)
	public JSONObject fetchTransactionCode(@RequestBody DocumentRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("fetchTransactionCode Called");
		return invServiceImpl.fetchTransactionCode(request);
	}

}
