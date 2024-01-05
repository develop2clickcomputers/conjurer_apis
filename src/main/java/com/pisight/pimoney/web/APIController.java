package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.CountryRequest;
import com.pisight.pimoney.dto.CountryResponse;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.InstitutionRequest;
import com.pisight.pimoney.dto.ManualInstitutionResponse;
import com.pisight.pimoney.dto.PreviewActionRequest;
import com.pisight.pimoney.dto.PreviewResponse;
import com.pisight.pimoney.dto.UpdateInvestmentRequest;
import com.pisight.pimoney.service.APIServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class APIController {
	
	@Autowired
	private APIServiceImpl apiServiceImpl = null;

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

	@RequestMapping(value = "/downloadPdfStatement", method = RequestMethod.POST)
	public JSONObject downloadPdfStatement(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("downloadPdfStatement Called");
		return apiServiceImpl.downloadPdfStatement(request);
	}

	@RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
	public JSONObject downloadFile(@RequestBody DocumentRequest request)  {
		
		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("downloadFile Called");
		return apiServiceImpl.downloadFile(request);
	}
}
