package com.pisight.pimoney.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.online.AddUpdateRequest;
import com.pisight.pimoney.dto.online.FinalResponse;
import com.pisight.pimoney.models.Container;
import com.pisight.pimoney.service.K2OnlineServiceImpl;
import com.pisight.pimoney.service.K2ServiceImpl;

@Configuration
@EnableAsync
public class ResponseManager {
	
	@Autowired
	private K2OnlineServiceImpl k2OnlineServiceImpl = null;
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Async
	public void handleACAResponse(PiHttpClient client, String piTrackerId, String trackerId, String userId, String flow, String dataFlow) throws Exception{
		
		String threadName = Thread.currentThread().getName();
		Thread.currentThread().setName(piTrackerId);
		ScheduledTasks.registerThread(piTrackerId, Thread.currentThread());
		
		int errorCode = 0;
		String responseMessage = null;
		
		try{
			String result = client.getResponse();
			ScriptLogger.writeInfo("result ->>> " + result);
			
			ObjectMapper mapper = new ObjectMapper();

			SimpleModule module = new SimpleModule();
			module.addDeserializer(Container.class, new ContainerDeserializer());
			mapper.registerModule(module);

			FinalResponse finalResponse = mapper.readValue(result, FinalResponse.class);

			errorCode = finalResponse.getErrorCode();
			responseMessage = finalResponse.getMessage();

			if(errorCode == 0){
				ScriptLogger.writeInfo("ACA request success. Now preparing request for Pimoney Service");
				k2OnlineServiceImpl.changeErrorCodeAndMessage(piTrackerId, 0, "Updating details");
				
				boolean finalStatus = processResponse(finalResponse, userId, trackerId, flow, dataFlow);
				if(finalStatus){
					ScriptLogger.writeInfo("Pimoney request success");
					ScriptLogger.writeInfo("STATUS :: SUCCESS");
				}else{
					ScriptLogger.writeError("Pimoney request failed");
					errorCode = 99;
					responseMessage = "Some error at server while updating the details";
					if(Constants.PROCESS_ADD.equals(flow)){
						ScriptLogger.writeInfo("Process is add so deleting credentials for unsuccessful request");
						k2OnlineServiceImpl.deleteCredentials(piTrackerId);
					}
					ScriptLogger.writeInfo("STATUS :: PIMONEY FAILED");
				}
			}else if(errorCode == 99){
				ScriptLogger.writeError("ACA failed with some unknown error");
				responseMessage = "We have encountered some error while processing your request";
				if(Constants.PROCESS_ADD.equals(flow)){
					ScriptLogger.writeInfo("Process is add so deleting credentials for unsuccessful request");
					k2OnlineServiceImpl.deleteCredentials(piTrackerId);
				}
			}else{
				ScriptLogger.writeError("ACA failed with error code :: " + errorCode);
				if(Constants.PROCESS_ADD.equals(flow)){
					ScriptLogger.writeInfo("Process is add so deleting credentials for unsuccessful request");
					k2OnlineServiceImpl.deleteCredentials(piTrackerId);
				}
			}
		}catch(Exception e){
			ScriptLogger.writeError("####################  Some error occurred");
			ScriptLogger.writeError(e.getMessage());
			ScriptLogger.writeError("####################  error printed", e);
			errorCode = 99;
			responseMessage = "Some error at server while processing the request";
			
		}finally {
			k2OnlineServiceImpl.deleteCredentialsIfRequired(piTrackerId);
			k2OnlineServiceImpl.changeErrorCodeAndMessage(piTrackerId, errorCode, responseMessage);
			k2OnlineServiceImpl.updateRequestStatus(piTrackerId, "dead");
//			Thread.sleep(10000);
//			k2OnlineServiceImpl.deleteUserInstCategoryDetail(piTrackerId);
//			k2OnlineServiceImpl.deleteUserInstitutionDetail(piTrackerId);
			Thread.currentThread().setName(threadName);
		}
	}

	private boolean processResponse(FinalResponse finalResponse, String userId, String trackerId, String flow, String dataFlow) throws Exception {
		
		ScriptLogger.writeInfo("DATA FLOW is ->>>> " + dataFlow);
		boolean result = false;
		if(AddUpdateRequest.DATA_FLOW_PIMONEY.equals(dataFlow)) {
			k2ServiceImpl.storeOnlineResoponse(finalResponse, userId, trackerId);
			result = true;
		}else if(AddUpdateRequest.DATA_FLOW_RAW.equals(dataFlow)) {
		}
		return result;
	}
}
