package com.pisight.pimoney.service;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

/**
 * This controller is intermediate controller and api's of this controller is in pdf_parser project.  
 * @author nitinharsoda
 *
 */
@Service
public class OutputHeaderServiceImpl {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

	public JSONObject addCustomeHeaders(JSONObject request) throws Exception {

		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "addCustomeHeaders";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
		
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}

	public JSONObject getPimoneyFields(JSONObject request) throws Exception {
		
		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "getPimoneyFields";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
		
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}

	public JSONObject getCustomFields(JSONObject request) throws Exception {

		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "getCustomFields";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
	
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}

	public JSONObject getCustomHeaderList(JSONObject request) throws Exception {

		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "getCustomHeaderList";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
	
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}

	public JSONObject deleteCustomeHeaders(JSONObject request) throws Exception {
		
		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "deleteCustomeHeaders";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
	
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}

	public JSONObject deleteHeaderMap(JSONObject request) throws Exception {

		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE,Constants.PARSER_ENGINE_URL) + "deleteHeaderMap";
		ScriptLogger.writeInfo("URL _>> " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(WebUtil.convertToJSON(request));
	
		String result = client.getResponseForPostRequest();
		
		return WebUtil.convertToJSONObject(result);
	}
}
