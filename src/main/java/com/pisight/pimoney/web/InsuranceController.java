package com.pisight.pimoney.web;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pisight.pimoney.dto.CarrierDTO;
import com.pisight.pimoney.dto.InsuranceDTO;
import com.pisight.pimoney.dto.PolicyPlanDTO;
import com.pisight.pimoney.dto.RiderDTO;
import com.pisight.pimoney.service.InsuranceServiceImpl;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class InsuranceController {

	@Autowired
	private InsuranceServiceImpl insuranceServiceImpl = null;
	
	@RequestMapping(value = "/addCarrier", method = RequestMethod.POST)
	public JSONObject addCarrier (@RequestBody CarrierDTO request) {

		ScriptLogger.writeDebug("addCarrier called");
		return insuranceServiceImpl.addCarrier(request);
	}
	
	@RequestMapping(value = "/getCarrier", method = RequestMethod.POST)
	public JSONObject getCarrier (@RequestBody CarrierDTO request) {

		ScriptLogger.writeDebug("getCarrier called");		
		return insuranceServiceImpl.getCarrier(request);
	}
	
	@RequestMapping(value = "/addPolicyPlan", method = RequestMethod.POST)
	public JSONObject addPolicyPlan (@RequestBody PolicyPlanDTO request) {

		ScriptLogger.writeDebug("addPolicyPlan called");
		return insuranceServiceImpl.addPolicyPlan(request);
	}
	
	@RequestMapping(value = "/getPolicyPlan", method = RequestMethod.POST)
	public JSONObject getPolicyPlan (@RequestBody PolicyPlanDTO request) {

		ScriptLogger.writeDebug("getPolicyPlan called");
		return insuranceServiceImpl.getPolicyPlan(request);
	}
	
	@RequestMapping(value = "/addRider", method = RequestMethod.POST)
	public JSONObject addRider (@RequestBody RiderDTO request) {

		ScriptLogger.writeDebug("addRider called");
		return insuranceServiceImpl.addRider(request);
	}
	
	@RequestMapping(value = "/getRider", method = RequestMethod.POST)
	public JSONObject getRider (@RequestBody RiderDTO request) {

		ScriptLogger.writeDebug("getRider called");
		return insuranceServiceImpl.getRider(request);
	}
	
	@RequestMapping(value = "/addInsurance", method = RequestMethod.POST)
	public JSONObject addInsurance (@RequestBody InsuranceDTO request) {

		ScriptLogger.writeDebug("addInsurance called");
		return insuranceServiceImpl.addInsurance(request);
	}
	
	@RequestMapping(value = "/getInsurance", method = RequestMethod.POST)
	public JSONObject getInsurance (@RequestBody InsuranceDTO request) {

		ScriptLogger.writeDebug("getInsurance called");
		return insuranceServiceImpl.getInsurance(request);
	}
}
