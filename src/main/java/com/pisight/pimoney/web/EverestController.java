package com.pisight.pimoney.web;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.CategoryRequest;
import com.pisight.pimoney.dto.CountryRequest;
import com.pisight.pimoney.dto.CountryResponse;
import com.pisight.pimoney.dto.DashboardRequest;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.InstitutionRequest;
import com.pisight.pimoney.dto.ManualInstitutionResponse;
import com.pisight.pimoney.dto.PreviewActionRequest;
import com.pisight.pimoney.dto.PreviewResponse;
import com.pisight.pimoney.dto.UpdateInvestmentRequest;
import com.pisight.pimoney.repository.entities.Category;
import com.pisight.pimoney.repository.entities.Merchant;
import com.pisight.pimoney.service.APIServiceImpl;
import com.pisight.pimoney.service.InvestmentServiceImpl;
import com.pisight.pimoney.service.OverviewServiceImpl;
import com.pisight.pimoney.service.TransactionServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

/**
 * This Controller API's is used in auctor side.
 * @author nitinharsoda
 * 
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/api/v1/everest")
public class EverestController {

	@Autowired
	private OverviewServiceImpl overviewServiceImpl;
	
	@Autowired
	private APIServiceImpl apiServiceImpl;
	
	@Autowired
	private TransactionServiceImpl transactionServiceImpl;
	
	@Autowired
	private InvestmentServiceImpl invServiceImpl;
	
	@RequestMapping(value = "/dashboardDetails", method = RequestMethod.POST)
	public JSONObject getDashboardDetails (@RequestBody DashboardRequest request, @RequestHeader(value = "authorization") String authorization) throws Exception {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("dashboard Details Service called");
		return overviewServiceImpl.getDashboardDetails(request, authorization);
	}
	
	@RequestMapping(value = "/getInvestments", method = RequestMethod.POST)
	public JSONObject getInvestments(@RequestBody DocumentRequest doc, @RequestHeader(value = "authorization") String authorization)  {

		Thread.currentThread().setName(doc.getUserId());
		ScriptLogger.writeDebug("getInvestment Called");
		return invServiceImpl.getInvestments(doc, authorization);
	}
	
	@RequestMapping(value = "/pdfCountryList", method = RequestMethod.POST)
	public CountryResponse getCountryList (@RequestBody CountryRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("pdfCountryList called");
		return apiServiceImpl.getCountryList(request);
	}
	
	@RequestMapping(value = "/pdfInstitutions", method = RequestMethod.POST)
	public ManualInstitutionResponse getManualInstitutions (@RequestBody InstitutionRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("pdfInstitutions called");
		return apiServiceImpl.getManualInstitutions(request);
	}
	
	@RequestMapping(value = "/checkFileEncryption", method = RequestMethod.POST)
	public JSONObject checkEncryption(@RequestBody DocumentRequest doc) throws Exception{

		Thread.currentThread().setName(doc.getUserId());
		ScriptLogger.writeDebug("checkFileEncryption called");
		return apiServiceImpl.checkEncryption(doc);
	}


	@RequestMapping(value = "/statementParsing", method = RequestMethod.POST)
	public JSONObject parseStatement(@RequestBody DocumentRequest doc, @RequestHeader(value = "authorization") String authorization) throws Exception {
		
		Thread.currentThread().setName(doc.getUserId());
		ScriptLogger.writeDebug("statementParsing called");
		return apiServiceImpl.statementParsing(doc, authorization);
	}

	@RequestMapping(value = "/statementPreviewDetails", method = RequestMethod.POST)
	public PreviewResponse statementPreviewDetails(@RequestBody DocumentRequest doc, @RequestHeader(value = "authorization") String authorization) throws Exception {
		
		Thread.currentThread().setName(doc.getUserId());
		ScriptLogger.writeDebug("statementPreviewDetails called");
		return apiServiceImpl.statementPreviewDetails(doc, authorization);
	}

	@RequestMapping(value = "/confirmAction", method = RequestMethod.POST)
	public JSONObject confirmAction(@RequestBody PreviewActionRequest request, @RequestHeader(value = "authorization") String authorization) throws Exception {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("confirmAction called");
		return apiServiceImpl.confirmAction(request, authorization);
	}

	@RequestMapping(value = "/rejectAction", method = RequestMethod.POST)
	public JSONObject rejectAction(@RequestBody PreviewActionRequest request, @RequestHeader(value = "authorization") String authorization) throws Exception {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("rejectAction called");
		return apiServiceImpl.rejectAction(request, authorization);
	}

	@RequestMapping(value = "/updateAccounts", method = RequestMethod.POST)
	public JSONObject updateAccounts(@RequestBody UpdateInvestmentRequest request, @RequestHeader(value = "authorization") String authorization)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("Update Investment Called");
		return apiServiceImpl.updateAccounts(request, authorization);
	}

	@RequestMapping(value = "/currencyList", method = RequestMethod.POST)
	public JSONObject getCurrenyList(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("fetch currency Called");
		return apiServiceImpl.getCurrenyList(request);
	}

	@RequestMapping(value = "/getCategories", method = RequestMethod.POST)
	public List<Category> getCategoryList (@RequestBody CategoryRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getCategories called");
		return transactionServiceImpl.getCategoryList(request);
	}
	
	@RequestMapping(value = "/getMerchants", method = RequestMethod.POST)
	public List<Merchant> getMerchantList(@RequestBody CategoryRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("getMarchants called");
		return transactionServiceImpl.getMerchantList(request);
	}
	
	@RequestMapping(value = "/fetchTransactionCode", method = RequestMethod.POST)
	public JSONObject fetchTransactionCode(@RequestBody DocumentRequest request)  {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("fetchTransactionCode Called");
		return invServiceImpl.fetchTransactionCode(request);
	}
	
}
