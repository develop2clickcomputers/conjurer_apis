package com.pisight.pimoney.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.online.AddUpdateRequest;
import com.pisight.pimoney.repository.K2Repository;
import com.pisight.pimoney.repository.UserInstituteCategoryDetailRepository;
import com.pisight.pimoney.repository.UserInstituteDetailRepository;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.repository.entities.UserInstituteCategoryDetail;
import com.pisight.pimoney.repository.entities.UserInstituteDetail;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;

@Service
public class K2OnlineServiceImpl {
	
	@Autowired
	private UserInstituteDetailRepository userInstDetailRepo = null;

	@Autowired
	private UserInstituteCategoryDetailRepository userInstCatRepo = null;
	
	@Autowired
	private K2Repository k2Repo = null;
	
	
	@Autowired
	private K2ServiceImpl k2Service = null;
	
	
	@Transactional
	public UserInstituteDetail fetchUserInstituteDetailByTrackerId(String trackerId){
		ScriptLogger.writeInfo("trackerId :: " + trackerId);
		return userInstDetailRepo.fetchUserInstituteDetailByTrackerId(trackerId);
	}

	@Transactional
	public UserInstituteDetail fetchUserInstituteDetailPTID(String piTrackerId){
//		ScriptLogger.writeInfo("pitrackerId :: " + piTrackerId);
		return userInstDetailRepo.fetchUserInstituteDetailByPID(piTrackerId);
	}

	@Transactional
	public Set<UserInstituteDetail> fetchUserInstituteDetails(String userId, String institutionCode){
		ScriptLogger.writeInfo("userId :: " + userId + " :: institutionCode :: " + institutionCode);
		return userInstDetailRepo.fetchUserInstituteDetailByUserInst(UUID.fromString(userId), institutionCode);
	}

	@Transactional
	public Set<UserInstituteDetail> fetchUserInstituteDetailsByUserId(String userId){
		ScriptLogger.writeInfo("userId :: " + userId);
		return userInstDetailRepo.fetchUserInstituteDetailByUserId(UUID.fromString(userId));
	}

	@Transactional
	public Set<UserInstituteCategoryDetail> fetchUserInstCategoryDetails(String piTrackerId){
		ScriptLogger.writeInfo("piTracker Id :: " + piTrackerId);
		return userInstCatRepo.fetchUserInstitutecategoryDetailsByPID(piTrackerId);
	}

	@Transactional
	public UserInstituteCategoryDetail fetchUserInstCategoryDetail(String piTrackerId, String category){
		ScriptLogger.writeInfo("piTracker Id :: " + piTrackerId);
		return userInstCatRepo.fetchUserInstitutecategoryDetailsByPIDCategory(piTrackerId, category);
	}
	
	@Transactional
	public List<AccountBaseEntity> fetchAccountsByBankId(String bankId, String tag){
		List<AccountBaseEntity> accounts = new ArrayList<AccountBaseEntity>();
		
		if(Constants.TAG_BANK.equals(tag)) {
			accounts.addAll(k2Repo.fetchBankAccountByBankId(bankId));
		}
		else if(Constants.TAG_CARD.equals(tag)) {
			accounts.addAll(k2Repo.fetchCardAccountByBankId(bankId));
		}
		else if(Constants.TAG_LOAN.equals(tag)) {
			accounts.addAll(k2Repo.fetchLoanAccountByBankId(bankId));
		}
		else if(Constants.TAG_FIXED_DEPOSIT.equals(tag)) {
			accounts.addAll(k2Repo.fetchFixedDepositAccountByBankId(bankId));
		}
		else if(Constants.CATEGORY_INVESTMENT.equals(tag)) {
			accounts.addAll(k2Repo.fetchInvestmentAccountByBankId(bankId));
		}
		
		return accounts;
	}
	
	
	public boolean deleteUserDetail(String trackerId) throws Exception {
		
		UserInstituteDetail detail = fetchUserInstituteDetailByTrackerId(trackerId);

		if(detail == null){
			ScriptLogger.writeError("invalid tracker id");
			return false;
		}

		String piTrackerId = detail.getPitrackerId();

		deleteUserInstitutionDetail(piTrackerId);
		deleteUserInstCategoryDetail(piTrackerId);
		deleteCredentials(piTrackerId);
		
		ScriptLogger.writeInfo("deletion of user inst detail and cerdential done");
		
		return true;
		
	}
	
	@Transactional
	public void deleteUserInstitutionDetail(String piTrackerId){
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		ScriptLogger.writeInfo("deleting userinst detail");
		if(detail != null){
			userInstDetailRepo.delete(detail);
		}
	}
	
	@Transactional
	public void deleteUserInstCategoryDetail(String piTrackerId){
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		Set<UserInstituteCategoryDetail> details = fetchUserInstCategoryDetails(piTrackerId);
		ScriptLogger.writeInfo("^^^^^^^^^^^^^^deleting userinstcategory detail^^^^^^^^^^^^^^^^^^^ ->>> " + details.size());
		for(UserInstituteCategoryDetail detail: details){
			userInstCatRepo.delete(detail);
		}
	}

	@Transactional
	public void deleteCredentials(String piTrackerId) throws Exception{
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		changeCredentialsPresentStatus(piTrackerId, false);
		String url =  k2Service.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "deleteCredential";

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("piTrackerId", piTrackerId);
		client.getResponse();
	}


	/**This method checks whether persist credentials flag is on or off and delete the credentials if it is off
	 * @param piTrackerId PiTracker Identifier
	 * @throws Exception {@link Exception}
	 */
	@Transactional
	public void deleteCredentialsIfRequired(String piTrackerId) throws Exception{
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		if(detail != null){
			boolean persistCred = detail.isPersistCredentials();
			if(!persistCred){
				ScriptLogger.writeInfo("persistCred is off so deleting the credentials");
				deleteCredentials(piTrackerId);
			}
		}
	}

	@Transactional
	public void changeCredentialsPresentStatus(String piTrackerId, boolean isCredentialsPresent) {
		// TODO Auto-generated method stub
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		ScriptLogger.writeInfo("changing credentials present status userinst detail");

		if(detail != null){
			detail.setCredentialsPresent(isCredentialsPresent);
			userInstDetailRepo.save(detail);
		}
	}

	@Transactional
	public void changeErrorCode(String piTrackerId, int errorCode) {
		// TODO Auto-generated method stub
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		ScriptLogger.writeInfo("changing error code userinst detail");
		detail.setErrorCode(errorCode);
		userInstDetailRepo.save(detail);
	}

	@Transactional
	public void changeErrorCodeAndMessage(String piTrackerId, int errorCode, String message) {
		// TODO Auto-generated method stub
		ScriptLogger.writeInfo("piTracker id :: " + piTrackerId);
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		ScriptLogger.writeInfo("changing error code userinst detail");

		detail.setErrorCode(errorCode);
		detail.setMessage(message);
		userInstDetailRepo.save(detail);

	}

	public void setProcess(String piTrackerId, String process){
		ScriptLogger.writeInfo("setting process for pitracker id " + piTrackerId + " to " + process);

		if(StringUtils.isEmpty(process)){
			ScriptLogger.writeInfo("process is empty");
			return;
		}
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		if(detail == null){
			ScriptLogger.writeError("No detail found for the pitrackerId " + piTrackerId);
			return;
		}
		detail.setProcess(process);
		userInstDetailRepo.save(detail);
		ScriptLogger.writeInfo("process set");
	}


	public JsonNode getMarkerField(JsonNode fields) {
		// TODO Auto-generated method stub
		ScriptLogger.writeInfo("getting marker value");
		int size = fields.size();
		ScriptLogger.writeInfo("size of fields is :: " + size);

		for(int i = 0; i< size; i++){
			JsonNode node = fields.get(i);
			ScriptLogger.writeInfo("node :: " + i + " :: " + node);
			boolean marker = node.get("marker").asBoolean();
			if(marker){
				ScriptLogger.writeInfo("marker field found. returning its value");
				return node;
			}
		}
		ScriptLogger.writeError("no marker field found. so returning null");
		return null;
	}


	/**
	 * If the process is add:<br>
	 * &nbsp; if the credentials already present in the record for this user id then returns the matched piTrackerId<br>
	 * &nbsp; else create a new piTrackerId and returns that<br>
	 * If the process is edit:<br>
	 * &nbsp; then it simply updates the credentials associated with the given piTrackerId and return the same id<br><br>
	 * 
	 * It also stores or updates preferences for the account<br>
	 * 
	 * @param addCredRequest {@link AddUpdateRequest}
	 * @param piTrackerId PiTracker Identifier
	 * @throws Exception {@link Exception}
	 * @return piTrackerId PiTracker Identifier
	 */
	public String saveCredentialAndPreferences(AddUpdateRequest addCredRequest, String piTrackerId) throws Exception {
	
		ScriptLogger.writeInfo("saving credentials >>> ");
		
		String userId = addCredRequest.getUserId();
		String institutionCode = addCredRequest.getInstitutionCode();
		String process = addCredRequest.getProcess();
		String requestCode = addCredRequest.getRequestCode();

		piTrackerId = saveCredentials(addCredRequest, piTrackerId, requestCode);

		if(StringUtils.isEmpty(piTrackerId)){
			return null;
		}

		User user = k2Service.fetchUserById(UUID.fromString(userId));
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);

		if(detail == null){
			ScriptLogger.writeInfo("pitrackerid is new. So generating a new trackerid");
			UserInstituteDetail detailNew = new UserInstituteDetail();
			String trackerId = generateTrackerId(requestCode);
			ScriptLogger.writeInfo("new trackerId :: " + trackerId);
			detailNew.setUser(user);
			detailNew.setPitrackerId(piTrackerId);
			detailNew.setTrackerId(trackerId);
			detailNew.setInstitutionCode(institutionCode);
			detailNew.setState("save cred");
			detailNew.setProcess(process);
			detailNew.setErrorCode(0);
			detailNew.setMfa(false);

			detail = userInstDetailRepo.save(detailNew);
			ScriptLogger.writeInfo("new user inst detail inserted");
		}

		JsonNode fields = addCredRequest.getFields();
		int size = fields.size();
		for(int i = 0; i< size; i++){
			JsonNode node = fields.get(i);
			ScriptLogger.writeInfo("node :: " + i + " :: " + node);
			String loginFieldCode = node.get("loginFieldCode").asText();
			if(loginFieldCode.contains("_typeChoice")){
				ScriptLogger.writeInfo("FIELD ::: preference fields");
				JsonNode options = node.get("options");
				ScriptLogger.writeInfo("@@@ options @@@@ " + options);
				int optionSize = options.size();
				for(int j = 0; j< optionSize;j++){
					JsonNode option = options.get(j);
					ScriptLogger.writeInfo("&&&& option &&&& " + option);
					String category = option.get("optionCode").asText();
					boolean isSelected = option.get("selected").asBoolean();
					UserInstituteCategoryDetail catDetail = fetchUserInstCategoryDetail(piTrackerId, category);

					if(catDetail == null){
						ScriptLogger.writeInfo("category detail not present. Creating a new one");
						catDetail = new UserInstituteCategoryDetail();
						catDetail.setPitrackerId(piTrackerId);
						catDetail.setCategory(category);
						catDetail.setState("new");
						catDetail.setErrorCode(0);
						catDetail.setMessage("new");
						catDetail.setLastSuccessfulRequest(null);
						catDetail.setActive(isSelected);
					}
					else{
						ScriptLogger.writeInfo("category detail present. updating the old one");
						catDetail.setActive(isSelected);
					}
					
					userInstCatRepo.save(catDetail);
				}
			}
			else if(loginFieldCode.contains("_scrapingPref")){
				ScriptLogger.writeInfo("FIELD ::: preference fields");
				String value = node.get("value").asText();
				if(value.equals("accountDetail")){
					detail.setTransactionRequired(false);
				}
				else if(value.equals("allDetail")){
					detail.setTransactionRequired(true);
				}
				detail = userInstDetailRepo.save(detail);
			}
			else if(loginFieldCode.contains("_persistCredentials")){
				ScriptLogger.writeInfo("FIELD ::: persistCredentials fields");
				boolean value = node.get("value").asBoolean();
				detail.setPersistCredentials(value);
				detail = userInstDetailRepo.save(detail);
			}
		}

		return piTrackerId;
	}


	/**
	 * If the process is add:<br>
	 *  &nbsp;&nbsp;if the credentials already present in the record for this user id then returns the matched piTrackerId
	 *  else create a new piTrackerId and returns that
	 * If the process is edit:<br>
	 * 	&nbsp;&nbsp;then it simply updates the credentials associated with the given piTrackerId and return the same id
	 * @param addCredRequest
	 * @param piTrackerId
	 * @param requestCode
	 * @return piTrackerId associated with the credentials
	 * @throws Exception
	 */
	private String saveCredentials(AddUpdateRequest addCredRequest, String piTrackerId, String requestCode) throws Exception{

		List<String> piTrackerIds = new ArrayList<String>();

		// only for add credentials
		if(Constants.PROCESS_ADD.equals(addCredRequest.getProcess())){
			Set<UserInstituteDetail> details = fetchUserInstituteDetails(addCredRequest.getUserId(), addCredRequest.getInstitutionCode());

			if(details != null){
				for(UserInstituteDetail detail: details){
					piTrackerIds.add(detail.getPitrackerId());
				}
			}
		}

		String url = k2Service.getConfigurationValue(Constants.ACA_ENGINE, Constants.ACA_ENGINE_URL) + "updateCredential";

		ScriptLogger.writeInfo("URL :::: " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setDataField("piTrackerIds", piTrackerIds);
		client.setDataField("piTrackerId", piTrackerId);
		client.setDataField("fields", addCredRequest.getFields());
		client.setDataField("requestCode", addCredRequest.getRequestCode());
		String result = client.getResponse();

		System.out.println("result => " + result);
		ObjectMapper mapper = new ObjectMapper();

		JSONObject data = mapper.readValue(result, JSONObject.class);

		String id = (String) data.get("piTrackerId");

		if(StringUtils.isNotEmpty(id)){
			return id;
		}

		return null;
	}


	@SuppressWarnings("unchecked")
	public JSONObject getActiveUserInstCategoryList(String piTrackerId){
		JSONObject tagDetails = new JSONObject();

		Set<UserInstituteCategoryDetail> details = fetchUserInstCategoryDetails(piTrackerId);
		if(details != null && details.size() > 0){

			for(UserInstituteCategoryDetail detail: details){
				String tag = detail.getCategory();
				if(detail.isActive()){
					JSONObject object = new JSONObject();
					object.put("errorCode", detail.getErrorCode());
					//					object.put("lastSuccessfulRequest", detail.getLastSuccessfulRequest());
					//					object.put("lastUpdated", detail.getLastUpdated());

					Date lastSuccessfulRequest = detail.getLastSuccessfulRequest();

					int diff = 90;// Default Date offset
					if(lastSuccessfulRequest != null){
						diff = (int) ((new Date().getTime() - lastSuccessfulRequest.getTime()) / (1000 * 60 * 60 * 24));
					}

					object.put("transactionStartDateOffset", diff);
					object.put("transactionEndDateOffset", 0);
					tagDetails.put(""+tag, object);
				}
			}
		}


		return tagDetails;
	}

	private String generateTrackerId(String requestCode) {
		// TODO Auto-generated method stub
		UUID id = UUID.randomUUID();
		String trackerId = id.toString();
		if(StringUtils.isNotEmpty(requestCode)){
			if(StringUtils.isNotEmpty(requestCode.trim())){
				trackerId = requestCode.trim() + "-" + id;
			}

		}

		return trackerId;
	}

	public void updateRequestStatus(String piTrackerId, String state) {
		
		UserInstituteDetail detail = fetchUserInstituteDetailPTID(piTrackerId);
		
		if(detail != null) {
//			ScriptLogger.writeInfo("request status updated to => " + state);
			detail.setState(state);
			detail.setUpdatedAt(new Date());
			userInstDetailRepo.save(detail);
		}
		
	}


}
