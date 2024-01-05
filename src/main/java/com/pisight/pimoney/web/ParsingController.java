package com.pisight.pimoney.web;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.pisight.pimoney.dto.BudgetRequest;
import com.pisight.pimoney.service.ParsingServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")  
public class ParsingController {

	@Autowired
	private ParsingServiceImpl  parsingServiceImpl = null;
	
	@RequestMapping(value = "/getStatementParsingDetails", method = RequestMethod.POST)
	public JSONObject getStatementParsingDetails (@RequestBody BudgetRequest request) {

		ScriptLogger.writeDebug("getStatementParsingDetails Api called....");
		return parsingServiceImpl.getStatementParsingDetails(request);
	}
	
	@RequestMapping(value = "/generatePDFFile", method = RequestMethod.POST)
	public JSONObject generatePDFFile (@RequestBody BudgetRequest request) throws FileNotFoundException, DocumentException {

		ScriptLogger.writeDebug("generatePDFFile Api called....");
		return parsingServiceImpl.generatePDFFile(request);
	}
}
