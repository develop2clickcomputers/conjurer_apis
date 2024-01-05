package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.PropertyRequest;
import com.pisight.pimoney.service.OverviewServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class PropertyController {

	@Autowired
	private OverviewServiceImpl overviewServiceImpl = null;
	
	
	@RequestMapping(value = "/addProperty", method = RequestMethod.POST)
	public JSONObject addProperty (@RequestBody PropertyRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("addProperty called");
		return overviewServiceImpl.addProperty(request);
	}
	
	@RequestMapping(value = "/editProperty", method = RequestMethod.POST)
	public JSONObject editProperty (@RequestBody PropertyRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("editProperty called");
		return overviewServiceImpl.editProperty(request);
	}
	
	@RequestMapping(value = "/deleteProperty", method = RequestMethod.POST)
	public JSONObject deleteProperty (@RequestBody PropertyRequest request) {

		Thread.currentThread().setName(request.getUserId());
		ScriptLogger.writeDebug("deleteProperty called");
		return overviewServiceImpl.deleteProperty(request);
	}
}
