package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.service.OutputHeaderServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class OutputHeaderController {

	@Autowired
	private OutputHeaderServiceImpl outputHeaderServiceImpl = null;
	
	@RequestMapping(value = "/addCustomeHeaders", method = RequestMethod.POST)
	public JSONObject AddCustomeHeaders(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("addCustomeHeaders Api Call....");
		return outputHeaderServiceImpl.addCustomeHeaders(request);
	}
	
	@RequestMapping(value = "/getPimoneyFields", method = RequestMethod.POST)
	public JSONObject getPimoneyFields(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("getPimoneyFields Api Call....");
		return outputHeaderServiceImpl.getPimoneyFields(request);
	}
	
	@RequestMapping(value = "/getCustomFields", method = RequestMethod.POST)
	public JSONObject getCustomFields(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("getCustomFields Api Call....");
		return outputHeaderServiceImpl.getCustomFields(request);
	}
	
	@RequestMapping(value = "/getCustomHeaderList", method = RequestMethod.POST)
	public JSONObject getCustomHeaderList(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("getCustomHeaderList Api Call....");
		return outputHeaderServiceImpl.getCustomHeaderList(request);
	}
	
	@RequestMapping(value = "/deleteCustomeHeaders", method = RequestMethod.POST)
	public JSONObject deleteCustomeHeaders(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("deleteCustomeHeaders Api Call....");
		return outputHeaderServiceImpl.deleteCustomeHeaders(request);
	}
	
	@RequestMapping(value = "/deleteHeaderMap", method = RequestMethod.POST)
	public JSONObject deleteHeaderMap(@RequestBody JSONObject request) throws Exception{

		ScriptLogger.writeInfo("deleteHeaderMap Api Call....");
		return outputHeaderServiceImpl.deleteHeaderMap(request);
	}
}
