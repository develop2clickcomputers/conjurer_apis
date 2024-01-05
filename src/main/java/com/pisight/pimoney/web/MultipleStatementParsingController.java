package com.pisight.pimoney.web;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.MultipleStatementParsingDTO;
import com.pisight.pimoney.service.MultipleStatementParsingServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class MultipleStatementParsingController {

	@Autowired
	private MultipleStatementParsingServiceImpl mspServiceImple = null;
	
	@RequestMapping(value = "/storeStatement", method = RequestMethod.POST)
	public JSONObject storeStatement(@RequestBody MultipleStatementParsingDTO request) throws Exception{

		ScriptLogger.writeInfo("storeStatement Api Call....");
		return mspServiceImple.storeStatement(request);
	}
	
	@RequestMapping(value = "/multipleStatementParsing", method = RequestMethod.POST)
	public JSONObject multipleStatementParsing(@RequestBody MultipleStatementParsingDTO request) throws Exception{

		ScriptLogger.writeInfo("multipleStatementParsing Api Call....");
		return mspServiceImple.multipleStatementParsing(request);
	}
	
	@RequestMapping(value = "/getBatchFileDetail", method = RequestMethod.POST)
	public JSONObject getBatchFileDetail (@RequestBody MultipleStatementParsingDTO request) {

		Thread.currentThread().setName(request.getUserId().toString());
		ScriptLogger.writeDebug("getBatchFileDetail called....");
		return mspServiceImple.getBatchFileDetail(request);
	}
	
	@RequestMapping(value = "/getFileDetail", method = RequestMethod.POST)
	public JSONObject getFileDetail (@RequestBody MultipleStatementParsingDTO request) throws IOException, InterruptedException {

		Thread.currentThread().setName(request.getUserId().toString());
		ScriptLogger.writeDebug("getFileDetail called....");
		return mspServiceImple.getFileDetail(request);
	}
	
	@RequestMapping(value = "/getFile", method = RequestMethod.POST)
	public JSONObject getFile (@RequestBody MultipleStatementParsingDTO request) throws IOException, InterruptedException {

		Thread.currentThread().setName(request.getUserId().toString());
		ScriptLogger.writeDebug("getFile called....");
		return mspServiceImple.getFile(request);
	}
	
	@RequestMapping(value = "/deleteBatchFileDetail", method = RequestMethod.POST)
	public JSONObject deleteBatchFileDetail (@RequestBody MultipleStatementParsingDTO request) throws IOException, InterruptedException {

		Thread.currentThread().setName(request.getUserId().toString());
		ScriptLogger.writeDebug("deleteBatchFileDetail called....");
		return mspServiceImple.deleteBatchFileDetail(request);
	}
}
