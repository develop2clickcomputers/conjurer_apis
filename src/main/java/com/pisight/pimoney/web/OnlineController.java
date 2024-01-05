package com.pisight.pimoney.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.online.AddUpdateRequest;
import com.pisight.pimoney.dto.online.CheckMFAResponse;
import com.pisight.pimoney.dto.online.MFARequest;
import com.pisight.pimoney.dto.online.PSVRequest;
import com.pisight.pimoney.dto.online.ProcessMFARequest;
import com.pisight.pimoney.dto.online.PromptResponse;
import com.pisight.pimoney.repository.entities.UserInstituteCategoryDetail;
import com.pisight.pimoney.repository.entities.UserInstituteDetail;
import com.pisight.pimoney.service.K2OnlineServiceImpl;
import com.pisight.pimoney.service.K2ServiceImpl;
import com.pisight.pimoney.util.InstanceFactory;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ResponseManager;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/securek2")
public class OnlineController {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Autowired
	private K2OnlineServiceImpl k2OnlineServiceImpl = null;
	
	/**
	 * This method is used to get online account institutions.
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getInstitutions", method = RequestMethod.POST)
	public JSONObject getInstitutions() throws Exception{
		
		ScriptLogger.writeInfo("inside getInstitutions");
		
		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;
		
		Object institutions = null;

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "getInstitutions";
		try{
			ScriptLogger.writeInfo("calling getInstitutions of ACAEngine");
			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_GET);
			String result = client.getResponse();
			errorCode = 0;
			responseMessage = "Get Instituions success";
			ScriptLogger.writeInfo(result);
			responseStatus = Constants.SUCCESS;
			JSONParser parser = new JSONParser(); 
			institutions =  parser.parse(result);

		}catch(Exception e){
			ScriptLogger.writeInfo("getInstitution ACAEngine failed");
			ScriptLogger.writeError("Error ", e);
			errorCode = 99;
			responseMessage = "Some error at server while processing the request";
			responseStatus = Constants.FAILED;
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("institutions", institutions);
		return response;
	}

	/**
	 * This method is used to get authentication fields for each institutions
	 * @param psvRequest {@link PSVRequest}
	 * @param request {@link HttpServletRequest}
	 * @return PromptResponse {@link PromptResponse}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPrompt", method = RequestMethod.POST)
	public PromptResponse getPrompt(@RequestBody PSVRequest psvRequest, HttpServletRequest request) throws Exception{

		ScriptLogger.writeInfo("inside getPrompt");
		String institutionCode = psvRequest.getInstitutionCode();
		String trackerId = psvRequest.getTrackerId();
		PromptResponse response = new PromptResponse();

		String piTrackerId = null;
		boolean persistCredentials = true;
		if(StringUtils.isEmpty(institutionCode) && StringUtils.isNotEmpty(trackerId)){
			UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);
			piTrackerId = detail.getPitrackerId();
			persistCredentials = detail.isPersistCredentials();
			if(StringUtils.isEmpty(institutionCode)){
				institutionCode = detail.getInstitutionCode();
			}
		}

		if(StringUtils.isEmpty(institutionCode)){
			ScriptLogger.writeError("institution code is either null or empty :: " + institutionCode);
			response.setErrorCode(99);
			response.setMessage("institution code is incorrect");
			response.setStatus(Constants.FAILED);
			return response;
		}
		response.setInstitutionCode(institutionCode);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "getLoginFields";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		String result = client.getResponse();
		ObjectMapper mapper = new ObjectMapper();

		JSONObject data = mapper.readValue(result, JSONObject.class);
		ScriptLogger.writeInfo("response from aca engine for getFields " + data);	
		List<HashMap<String, String>> fieldData = (List<HashMap<String, String>>) data.get("loginFields");
		if(StringUtils.isNotEmpty(piTrackerId)){
			fieldData = insertFieldValue(fieldData, piTrackerId);
		}
		List<HashMap<String, String>> preferrence = getPreferenceField(institutionCode, piTrackerId);
		int mfaLevel = (int) data.get("mfaLevel");

		ScriptLogger.writeInfo("$$$$$$$$$");
		ScriptLogger.writeInfo(fieldData);
		boolean isInserted = false;
		if(fieldData != null) {
			for(int i = 0; i < fieldData.size(); i++){
				HashMap<String, String> field = fieldData.get(i);
				if(StringUtils.isNotEmpty(field.get("field")) && field.get("field").toLowerCase().contains("mfafield")){
					fieldData.addAll(i, preferrence);
					isInserted = true;
					break;
				}
			}
			if(!isInserted){
				fieldData.addAll(preferrence);
			}
		}
	
		JsonNode fieldNode = mapper.convertValue(fieldData, JsonNode.class);
		ScriptLogger.writeInfo(fieldNode);
		if(data == null || data.size() == 0){
			ScriptLogger.writeError("unable to get fields. Either service is down or incorrect instittution code is entered");
			response.setErrorCode(99);
			response.setMessage("unable to get login prompt");
			response.setStatus(Constants.FAILED);
			return response;
		}

		response.setInstitutionCode(institutionCode);
		response.setFields(fieldNode);
		response.setErrorCode(0);
		response.setMessage("Get Prompt Success");
		response.setMfaLevel(mfaLevel);
		response.setPersistCredentials(persistCredentials);
		response.setStatus(Constants.SUCCESS);
		return response;
	}


	@SuppressWarnings("unused")
	private List<HashMap<String, String>> insertFieldValue(List<HashMap<String, String>> fieldData, String piTrackerId){

		ScriptLogger.writeInfo("##########******** :: " + fieldData);
		if(fieldData != null){
			for(HashMap<String, String> field: fieldData){
				String loginFieldCode = field.get("loginFieldCode");
				field.put("value", "");
			}
		}
		return fieldData;
	}

	
	@SuppressWarnings("unchecked")
	private List<HashMap<String, String>> getPreferenceField(String institutionCode, String piTrackerId) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap<String, String>> fields = new ArrayList<HashMap<String, String>>();

		List<HashMap<String, String>> options = new ArrayList<HashMap<String, String>>();

		boolean isTransactionRequird = true;
		boolean persistCredentials = true;
		if(StringUtils.isNotEmpty(piTrackerId)){
			UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailPTID(piTrackerId);
			isTransactionRequird = detail.isTransactionRequired();
			persistCredentials = detail.isPersistCredentials();
		}

		JSONObject scrapingInfoField = new JSONObject();
		scrapingInfoField.put("loginFieldCode", institutionCode+"_scrapingPref");
		scrapingInfoField.put("field", "scrapingPref");
		scrapingInfoField.put("marker", false);
		scrapingInfoField.put("instituteCode", institutionCode);
		scrapingInfoField.put("optional", false);
		scrapingInfoField.put("label", "Select the information you want to see");
		scrapingInfoField.put("type", "userScrapePref");
		scrapingInfoField.put("options", options);
		if(isTransactionRequird){
			scrapingInfoField.put("value", "allDetail");
		}else{
			scrapingInfoField.put("value", "accountDetail");
		}

		JSONObject accountField = new JSONObject();
		accountField.put("isDefaultOption", false);
		accountField.put("loginFieldCode", institutionCode+"_scrapingPref");
		accountField.put("optionCode", "accountDetail");
		accountField.put("optionText", "Balance Only");

		JSONObject transField = new JSONObject();
		transField.put("isDefaultOption", true);
		transField.put("loginFieldCode", institutionCode+"_scrapingPref");
		transField.put("optionCode", "allDetail");
		transField.put("optionText", "Balance and Transactions Both");
		
		JSONObject persistCredInfoField = new JSONObject();
		persistCredInfoField.put("loginFieldCode", institutionCode+"_persistCredentials");
		persistCredInfoField.put("field", "persistCredentials");
		persistCredInfoField.put("marker", false);
		persistCredInfoField.put("instituteCode", institutionCode);
		persistCredInfoField.put("optional", false);
		persistCredInfoField.put("type", "userPersistCredPref");
		persistCredInfoField.put("value", persistCredentials);

		options.add(accountField);
		options.add(transField);

		JSONObject tagField = getTagField(institutionCode, piTrackerId);
		fields.add(persistCredInfoField);
		fields.add(tagField);
		fields.add(scrapingInfoField);

		return fields;
	}


	@SuppressWarnings("unchecked")
	private JSONObject getTagField(String institutionCode, String piTrackerId) throws Exception {
		// TODO Auto-generated method stub

		List<JSONObject> options = new ArrayList<JSONObject>();

		JSONObject typeInfoField = new JSONObject();
		typeInfoField.put("loginFieldCode", institutionCode+"_typeChoice");
		typeInfoField.put("field", "typeChoice");
		typeInfoField.put("marker", false);
		typeInfoField.put("instituteCode", institutionCode);
		typeInfoField.put("optional", false);
		typeInfoField.put("label", "Select Account Types");
		typeInfoField.put("type", "userAccountTypePref");
		typeInfoField.put("options", options);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "getInstCategoryList";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		String result = client.getResponse();
		ObjectMapper mapper = new ObjectMapper();

		JSONObject data = mapper.readValue(result, JSONObject.class);

		List<String> tagList = (List<String>) data.get("tagList");

		if(StringUtils.isNotEmpty(piTrackerId)){
			Set<UserInstituteCategoryDetail> catDetails = k2OnlineServiceImpl.fetchUserInstCategoryDetails(piTrackerId);
			for(String tag: tagList){
				UserInstituteCategoryDetail detail = getUserInstCategoryForCategory(catDetails, tag);
				JSONObject tagField = new JSONObject();
				tagField.put("isDefaultOption", true);
				tagField.put("loginFieldCode", institutionCode+"_typeChoice");
				tagField.put("optionCode", tag);
				tagField.put("optionText", tag);
				if(detail != null){
					tagField.put("selected", detail.isActive());
				}
				else{
					tagField.put("selected", false);
				}
				options.add(tagField);
			}
		}
		else{
			for(String tag: tagList){
				JSONObject tagField = new JSONObject();
				tagField.put("isDefaultOption", true);
				tagField.put("loginFieldCode", institutionCode+"_typeChoice");
				tagField.put("optionCode", tag);
				tagField.put("optionText", tag);
				tagField.put("selected", true);
				options.add(tagField);
			}
		}
		return typeInfoField;
	}
	
	private UserInstituteCategoryDetail getUserInstCategoryForCategory(Set<UserInstituteCategoryDetail> catDetails, String category){
		UserInstituteCategoryDetail detail = null;

		if(catDetails != null){

			for(UserInstituteCategoryDetail catDetail: catDetails){
				if(catDetail.getCategory().equals(category)){
					return catDetail;
				}
			}
		}
		return detail;
	}
	
	
	/**
	 * This method is used to get accounts that is added by users
	 * @param userInstRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
	public JSONObject getUserInstDetail(@RequestBody AddUpdateRequest userInstRequest, HttpServletRequest request) throws Exception{
		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;

		String userId = userInstRequest.getUserId();

		if(userId == null){
			errorCode = 99;
			responseMessage = "Invalid Details";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}

		Set<UserInstituteDetail> details = k2OnlineServiceImpl.fetchUserInstituteDetailsByUserId(userId);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "getInstitutions";

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_GET);
		String result = client.getResponse();
		JSONParser parser = new JSONParser(); 
		JSONArray institutions = (JSONArray) parser.parse(result);
		HashMap<String, String> instMap = new HashMap<String, String>();
		for(int i = 0; i< institutions.size(); i++){
			JSONObject object = (JSONObject) institutions.get(i);
			String institutionName = (String) object.get("instituteName");
			String institutionCode = (String) object.get("instituteCode");
			instMap.put(institutionCode, institutionName);
		}

		List<JSONObject> userInstDetails = new ArrayList<JSONObject>();
		for(UserInstituteDetail detail: details){
			JSONObject object = new JSONObject();
			object.put("institutionCode", detail.getInstitutionCode());
			object.put("institutionName", instMap.get(detail.getInstitutionCode()));
			object.put("trackerId", detail.getTrackerId());
			object.put("userId", detail.getUser());
			object.put("errorCode", detail.getErrorCode());
			userInstDetails.add(object);
		}

		errorCode = 0;
		responseMessage = "Success";
		responseStatus = Constants.SUCCESS;

		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("details", userInstDetails);
		return response;
	}

	/**
	 * This method is used to check if institution has a multi factor authentication or not
	 * @param checkMFARequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return CheckMFAResponse {@link CheckMFAResponse}
	 * @throws Exception {@link Exception}
	 */
	@RequestMapping(value = "/checkMFA", method = RequestMethod.POST)
	public CheckMFAResponse checkMFA(@RequestBody AddUpdateRequest checkMFARequest, HttpServletRequest request) throws Exception{

		ScriptLogger.writeInfo("inside check mfa ");
		CheckMFAResponse mfaResponse = new CheckMFAResponse();

		String institutionCode = checkMFARequest.getInstitutionCode();
		String userId = checkMFARequest.getUserId();
		String trackerId = checkMFARequest.getTrackerId();
		String piTrackerId = null;


		mfaResponse.setTrackerId(trackerId);

		UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);

		if(detail == null){
			ScriptLogger.writeError("invalid tracker id");
			return mfaResponse;
		}

		if(StringUtils.isEmpty(institutionCode)){
			institutionCode = detail.getInstitutionCode();
		}

		if(StringUtils.isEmpty(userId)){
			userId = detail.getUser().getId().toString();
		}

		piTrackerId = detail.getPitrackerId();
		mfaResponse.setInstitutionCode(institutionCode);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "checkMFARequried";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		client.setDataField("pitrackerId", piTrackerId);
		String result = client.getResponse();
		ScriptLogger.writeInfo("mfa request :: " + result);
		ObjectMapper mapper = new ObjectMapper();

		MFARequest data = mapper.readValue(result, MFARequest.class);
		mfaResponse.setMfaRequired(data.isMfaRequired());
		mfaResponse.setMfaInfo(data.getMfaInfo());

		if(data.isMfaUser() || detail.isMfa()){
			ScriptLogger.writeInfo("setting mfa user true");
			mfaResponse.setMfaUser(true);
		}

		return mfaResponse;
	}

	/**
	 * This method is used to pass value of MFA Fields
	 * @param processRequest {@link ProcessMFARequest}
	 * @param request {@link HttpServletRequest}
	 * @return boolean {@link Boolean} 
	 * @throws Exception {@link Exception}
	 */
	@RequestMapping(value = "/processMFA", method = RequestMethod.POST)
	public boolean processMFA(@RequestBody ProcessMFARequest processRequest, HttpServletRequest request) throws Exception{

		ScriptLogger.writeInfo("inside process MFA");
		String institutionCode = processRequest.getInstitutionCode();
		String userId = processRequest.getUserId();
		String trackerId = processRequest.getTrackerId();
		HashMap<String, String> mfaValueMap = processRequest.getMfaValueMap();
		String piTrackerId = null;

		UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);

		ScriptLogger.writeInfo("@@@@@@@@@@@@@ " + institutionCode + " ### " + userId + " ### " + trackerId);
		ScriptLogger.writeInfo("@@@@@@@@@@@@@ " + mfaValueMap);
		if(detail == null){
			ScriptLogger.writeError("invalid tracker id");
			return false;
		}

		if(StringUtils.isEmpty(institutionCode)){
			institutionCode = detail.getInstitutionCode();
		}

		if(StringUtils.isEmpty(userId)){
			userId = detail.getUser().getId().toString();
		}

		piTrackerId = detail.getPitrackerId();

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "processMFA";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		client.setDataField("pitrackerId", piTrackerId);
		client.setDataField("mfaValueMap", mfaValueMap);
		String result = client.getResponse();
		ScriptLogger.writeInfo("result ->>>> " +result);
		boolean res = Boolean.parseBoolean(result.trim());
		ScriptLogger.writeInfo("boolean value11 :: "  + res);

		return res;
	}

	/**
	 * This method is used to delete user account details for given tracker identification
	 * @param deleteRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return boolean {@link Boolean}
	 * @throws Exception {@link Exception}
	 */
	@RequestMapping(value="/deleteUserDetails	", method = RequestMethod.POST)
	public boolean deleteUserDetails(@RequestBody AddUpdateRequest deleteRequest, HttpServletRequest request) throws Exception{

		ScriptLogger.writeInfo("inside delete user details");
		String trackerId = deleteRequest.getTrackerId();
		return k2OnlineServiceImpl.deleteUserDetail(trackerId);
	}

	/**
	 * This method is used to update credential information of institutions
	 * @param addCredRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/updateCredential", method = RequestMethod.POST)
	public JSONObject addOrUpdateCredentialandPreferences(@RequestBody AddUpdateRequest addCredRequest, HttpServletRequest request) throws Exception{
		
		JsonNode fields = addCredRequest.getFields();
		String process = addCredRequest.getProcess();
		String trackerId = addCredRequest.getTrackerId();
		String piTrackerId = null;
		
		JSONObject response = new JSONObject();
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Error in updateCredential";

		try {
			if(fields == null && trackerId == null){
				ScriptLogger.writeError("required details missing");
				response.put(Constants.RESPONSE_STATUS_STRING, Constants.FAILED);
				return response;
			}
	
			if(process.equals(Constants.PROCESS_EDIT) && trackerId == null){
				ScriptLogger.writeError("process is edit but tracker id is null");
				response.put(Constants.RESPONSE_STATUS_STRING, Constants.FAILED);
				return response;
			}
	
			if(trackerId != null && process.equals(Constants.PROCESS_EDIT)){
				ScriptLogger.writeInfo("edit credential process");
				UserInstituteDetail	 detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);
				if(detail == null){
					ScriptLogger.writeInfo("no user detail found for this tracker id");
					response.put(Constants.RESPONSE_STATUS_STRING, Constants.FAILED);
					return response;
				}
	
				piTrackerId = detail.getPitrackerId();
				ScriptLogger.writeInfo("pitracker id :: " + piTrackerId);
			}
	
			ScriptLogger.writeInfo("process is add or update");
			piTrackerId = k2OnlineServiceImpl.saveCredentialAndPreferences(addCredRequest, piTrackerId);
	
	
	
			if(StringUtils.isNotEmpty(piTrackerId)){
				ScriptLogger.writeInfo("save credential success");
				k2OnlineServiceImpl.changeCredentialsPresentStatus(piTrackerId, true);
				response.put(Constants.RESPONSE_STATUS_STRING, Constants.SUCCESS);
				response.put("piTrackerId", piTrackerId);
			}
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Credential Updated";
		}
		catch(Exception e) {
			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			ScriptLogger.writeError("Error", e);
		}
		
		return response;
	}


	/**
	 * This method is used to get status of user account refresh
	 * @param getRefreshRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRefreshDetail", method = RequestMethod.POST)
	public JSONObject getRefreshDetail(@RequestBody AddUpdateRequest getRefreshRequest, HttpServletRequest request){

		ScriptLogger.writeInfo("inside get refresh detail");
		String trackerId = getRefreshRequest.getTrackerId();

		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;

		UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);

		if(detail == null){
			ScriptLogger.writeError("invalid tracker id");
			errorCode = 99;
			responseMessage = "invalid tracker id";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}

		errorCode =  detail.getErrorCode();
		responseMessage = detail.getMessage();
		JSONObject response = null;
	
		long now = new Date().getTime();
		long lastUpdate = detail.getUpdatedAt().getTime();
		long diff = now - lastUpdate;

		ScriptLogger.writeInfo("#####################################");
		ScriptLogger.writeInfo("state => " + detail.getState() + " :: diff => " + diff);
		
		if("alive".equals(detail.getState()) && diff < 90000){
			ScriptLogger.writeInfo("refresh in progress");
			responseStatus = "in progress";
			response = new JSONObject();
			response.put(Constants.RESPONSE_STATUS_STRING, responseStatus);
		}
		else{
			ScriptLogger.writeInfo("refresh completed");
			responseStatus  = "completed";
			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		
		response.put("message", detail.getMessage());
		return response;

	}

	/**
	 * This method is used to add user account and get all information related to account and store it in database
	 * @param addRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}
	 * @return JSONObject {@link JSONObject} 
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	public JSONObject addAccount(@RequestBody AddUpdateRequest addRequest, HttpServletRequest request) throws Exception{

		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;

		String institutionCode = addRequest.getInstitutionCode();
		String userId = addRequest.getUserId();
		String dataFlow = addRequest.getDataFlow();
		String trackerId = null;
		boolean isTransactionRequired = true;
		JSONObject tagDetails = null;
		JSONObject otherInfo = new JSONObject();

		JSONObject addCredResponse = addOrUpdateCredentialandPreferences(addRequest, request);
		String piTrackerId = (String) addCredResponse.get("piTrackerId");

		if(StringUtils.isEmpty(piTrackerId)){
			ScriptLogger.writeError("pitracker id is null :: " + piTrackerId);
			errorCode = 99;
			responseMessage = "Failed to store Credentials";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}

		UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailPTID(piTrackerId);
		trackerId = detail.getTrackerId();
		k2OnlineServiceImpl.setProcess(piTrackerId, Constants.PROCESS_ADD);
		isTransactionRequired = detail.isTransactionRequired();
		tagDetails = k2OnlineServiceImpl.getActiveUserInstCategoryList(piTrackerId);
		ScriptLogger.writeInfo("tagDetails :: " + tagDetails);

		//setting other info -- 
		otherInfo.put("trackerId", trackerId);
		otherInfo.put("userId", userId);
		otherInfo.put("raw", true);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "initiateACA";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);

		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		client.setDataField("pitrackerId", piTrackerId);
		client.setDataField("mfaUser", false);
		client.setDataField("transactionRequired", isTransactionRequired);
		client.setDataField("tagDetails", tagDetails);
		client.setDataField("otherInfo", otherInfo);
		k2OnlineServiceImpl.changeErrorCodeAndMessage(piTrackerId, 0, "Adding");
		
		ScriptLogger.writeInfo("sending ACA request Asynchronsly");
		ResponseManager responseManager = InstanceFactory.getResponseManagerInstance();
		responseManager.handleACAResponse(client, piTrackerId, trackerId, userId, Constants.PROCESS_ADD, dataFlow);
		responseMessage = "request initiated";
		responseStatus = "Success";
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("trackerId", trackerId);
		

		return response;
	}


	/**
	 * This method is used to refresh an account of user
	 * @param refreshRequest {@link AddUpdateRequest}
	 * @param request {@link HttpServletRequest}}
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/refreshAccount", method = RequestMethod.POST)
	public JSONObject refreshAccount(@RequestBody AddUpdateRequest refreshRequest, HttpServletRequest request) throws Exception{

		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;

		String trackerId = refreshRequest.getTrackerId();
		String institutionCode = refreshRequest.getInstitutionCode();
		String userId          = refreshRequest.getUserId();
		String dataFlow = refreshRequest.getDataFlow();
		boolean isTransactionRequired = true;
		HashMap<String, JSONObject> tagDetails = null;
		JSONObject otherInfo = new JSONObject();

		UserInstituteDetail detail = k2OnlineServiceImpl.fetchUserInstituteDetailByTrackerId(trackerId);

		if(detail == null){
			ScriptLogger.writeError("Invalid Tracker ID");
			errorCode = 99;
			responseMessage = "Invalid Tracker ID";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		
		if(!detail.isCredentialsPresent()){
			ScriptLogger.writeError("Login Details not present");
			errorCode = 98;
			responseMessage = "Login Details not present";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}

		if(StringUtils.isEmpty(institutionCode)){
			institutionCode = detail.getInstitutionCode();
		}

		if(StringUtils.isEmpty(userId)){
			userId = detail.getUser().getId().toString();
		}

		long now = new Date().getTime();
		long lastUpdate = detail.getUpdatedAt().getTime();
		long diff = now - lastUpdate;


		if("alive".equals(detail.getState()) && diff < 90000){
			ScriptLogger.writeError("refresh already in progress");
			errorCode = 99;
			responseMessage = "Refresh already in progress";
			responseStatus = Constants.FAILED;
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}

		String piTrackerId = detail.getPitrackerId();
		k2OnlineServiceImpl.changeErrorCodeAndMessage(piTrackerId, 0, "Refreshing");
		k2OnlineServiceImpl.setProcess(piTrackerId, Constants.PROCESS_REFRESH);

		isTransactionRequired = detail.isTransactionRequired();
		tagDetails = k2OnlineServiceImpl.getActiveUserInstCategoryList(piTrackerId);
		ScriptLogger.writeInfo("tagDetails :: " + tagDetails);
		
		//setting other info -- 
		otherInfo.put("trackerId", trackerId);
		otherInfo.put("userId", userId);
		otherInfo.put("raw", true);

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "initiateACA";

		ScriptLogger.writeInfo("url for aca service is :: " + url);
		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);

		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("institutionCode", institutionCode);
		client.setDataField("pitrackerId", piTrackerId);
		client.setDataField("mfaUser", detail.isMfa());
		client.setDataField("transactionRequired", isTransactionRequired);
		client.setDataField("tagDetails", tagDetails);
		client.setDataField("otherInfo", otherInfo);


		ScriptLogger.writeInfo("sending ACA request Asynchronsly");
		ResponseManager responseManager = InstanceFactory.getResponseManagerInstance();
		responseManager.handleACAResponse(client, piTrackerId, trackerId, userId, Constants.PROCESS_REFRESH, dataFlow);

		responseMessage = "request initiated";
		responseStatus = "Success";
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("trackerId", trackerId);

		return response;
	}

	/**
	 * This method is used to get all insurance data that was store in insurance data collection.
	 * @return JSONObject {@link JSONObject}
	 * @throws Exception {@link Exception}
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getInsuranceData", method = RequestMethod.POST)
	public JSONObject getInsuranceData() throws Exception{
		
		int errorCode = 0;
		String responseMessage = null;
		String responseStatus = null;
		
		Object data = null;

		ScriptLogger.writeInfo("inside getInsuranceData");

		String url = k2ServiceImpl.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "getInsuranceData";
		try{
			
			ScriptLogger.writeInfo("calling getInsuranceData of ACAEngine");
			PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_GET);
			String result = client.getResponse();
			
			errorCode = 0;
			responseMessage = "Get Instituions success";
			ScriptLogger.writeInfo(result);
			responseStatus = Constants.SUCCESS;
			
			JSONParser parser = new JSONParser(); 
			data =  parser.parse(result);

		}catch(Exception e){
			ScriptLogger.writeInfo("getInstitution ACAEngine failed");
			ScriptLogger.writeError("Error ", e);
			errorCode = 99;
			responseMessage = "Some error at server while processing the request";
			responseStatus = Constants.FAILED;
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}
}
