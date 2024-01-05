package com.pisight.pimoney.web;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.RegisterRequest;
import com.pisight.pimoney.dto.online.AddUpdateRequest;
import com.pisight.pimoney.dto.online.StatusUpdateRequest;
import com.pisight.pimoney.repository.StatementParsingDetailRepository;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.service.K2OnlineServiceImpl;
import com.pisight.pimoney.service.K2ServiceImpl;
import com.pisight.pimoney.util.EncodeDecodeUtil;
import com.pisight.pimoney.util.ScriptLogger;

@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Autowired
	private K2OnlineServiceImpl k2OnlineServiceImpl = null;
	
	@Autowired
	private OnlineController onlineController = null;
	
	@Autowired
	private StatementParsingDetailRepository stmtRepo = null;

	@Value("${jwt.key}")
	private String secret;
	
	@RequestMapping(value = "/encrypt", method = RequestMethod.POST)
	public String encrypt(@RequestBody RegisterRequest register) throws Exception{
	
		String password = register.getPassword();
		return EncodeDecodeUtil.encrypt(password);
	}

	@RequestMapping(value = "/aurbatao", method = RequestMethod.GET)
	public String hello() throws Exception{
		ScriptLogger.writeInfo("Application is Running");
		return "Sab Badhiya, Tum Batao.";
	}
	
	@RequestMapping(value = "/decrypt", method = RequestMethod.GET)
	public String decrypt() throws Exception{
		ScriptLogger.writeInfo("Application is Running");
		String password = EncodeDecodeUtil.decrypt("cC6puZkW56ldqZwCH4d9YY5YpfA749OiIYjEwpPOLP0=");
		return password;
	}
		
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public String updateState(@RequestBody StatusUpdateRequest updateRequest){
		
		String piTrackerId = updateRequest.getPiTrackerId();
		String status = updateRequest.getStatus();
		
		if(StringUtils.isNotEmpty(status)){
			status = Constants.getAcaStatusMap().get(status);
		}
		k2OnlineServiceImpl.changeErrorCodeAndMessage(piTrackerId, 0, status);
		return "Success";
	}
	
	@RequestMapping(value = "/fetchRates", method = RequestMethod.POST)
	public void updateState(){
		k2ServiceImpl.fetchAndStoreCurrencyRates(new Date());
	}
	
	
	@RequestMapping(value = "/startPrivateBanking", method = RequestMethod.GET)
	public boolean startPrivateBanking() throws Exception{
		
		String s = "{\"institutionCode\":\"8001\",\"requestCode\":\"pimoney\",\"userId\":\"5406746a-b54e-47d4-88ac-11355185d9d9\",\"process\":"
				+ "\"add\",\"fields\":[{\"loginFieldCode\":\"lf_pb_ww_01\",\"field\":\"username\",\"marker\":true,\"value\":\"kumar\",\"optional\":false,"
				+ "\"options\":[]},{\"loginFieldCode\":\"lf_pb_ww_02\",\"field\":\"password\",\"marker\":false,\"value\":\"Product@123\",\"optional\":false,"
				+ "\"options\":[]},{\"loginFieldCode\":\"8001_persistCredentials\",\"field\":\"persistCredentials\",\"marker\":false,\"value\":true,\"optional\":false},"
				+ "{\"loginFieldCode\":\"8001_typeChoice\",\"field\":\"typeChoice\",\"marker\":false,\"optional\":false,\"options\":[{\"isDefaultOption\":true,"
				+ "\"loginFieldCode\":\"8001_typeChoice\",\"optionCode\":\"Generic\",\"optionText\":\"Generic\",\"selected\":true}]},{\"loginFieldCode\":"
				+ "\"8001_scrapingPref\",\"field\":\"scrapingPref\",\"marker\":false,\"value\":\"allDetail\",\"optional\":false,\"options\":[{\"isDefaultOption"
				+ "\":false,\"loginFieldCode\":\"8001_scrapingPref\",\"optionCode\":\"accountDetail\",\"optionText\":\"Balance Only\"},{\"isDefaultOption"
				+ "\":true,\"loginFieldCode\":\"8001_scrapingPref\",\"optionCode\":\"allDetail\",\"optionText\":\"Balance and Transactions Both\"}]}]}";
		
		
		ObjectMapper mapper = new ObjectMapper();
		AddUpdateRequest add = mapper.readValue(s, AddUpdateRequest.class);
		
		JSONObject response = onlineController.addAccount(add, null);
		
		if(response.containsKey(Constants.ERROR_CODE_STRING)) {
			int code =  (int) response.get(Constants.ERROR_CODE_STRING);
			if(code == 0) {
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/getBase64ForStatement", method = RequestMethod.GET)
	public JSONObject getBase64ForStatement(@RequestParam("id") UUID id) throws Exception{
		ScriptLogger.writeInfo("getBase64ForStatement Api Called..........");
		ScriptLogger.writeInfo("Id = " + id);
		JSONObject response = new JSONObject();
		byte[] docByte = null;
		StatementParsingDetail stmt = null; //stmtRepo.findOne(id);
		if(stmt != null) {
			docByte = stmt.getFileByte();
			response.put("data", docByte);
		}else {
			ScriptLogger.writeInfo("Statement Not Found");
		}
		return response;
	}
	
}
