package com.pisight.pimoney.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.CarrierDTO;
import com.pisight.pimoney.dto.InsuranceDTO;
import com.pisight.pimoney.dto.PolicyPlanDTO;
import com.pisight.pimoney.dto.RiderDTO;
import com.pisight.pimoney.repository.entities.Carrier;
import com.pisight.pimoney.repository.entities.Insurance;
import com.pisight.pimoney.repository.entities.InsuranceRiderMap;
import com.pisight.pimoney.repository.entities.PolicyPlan;
import com.pisight.pimoney.repository.entities.Rider;
import com.pisight.pimoney.repository.entities.RiderPremium;
import com.pisight.pimoney.repository.entities.RiderPremiumDTO;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class InsuranceServiceImpl {
	
	@Autowired
	private K2DAO k2DAO = null;
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	/**
	 * This method is used to add carrier.
	 * @param request {@link CarrierDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject addCarrier(CarrierDTO request) {
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		try {
			
			Carrier carrier = new Carrier();
			carrier.setName(request.getName());
			carrier.setAbbreviation(request.getAbbreviation());
			k2DAO.saveCarrier(carrier);

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in addCarrier Budget", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in addCarrier Budget";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
	
	/**
	 * This method is used to get all carriers.
	 * @param request {@link CarrierDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getCarrier(CarrierDTO request) {
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		List<JSONObject> data = new ArrayList<>();
		try {
			
			List<Carrier> carriers = k2DAO.fetchCarriers();
			for (Carrier carrier : carriers) {
				
				JSONObject details = new JSONObject();
				details.put("id", carrier.getId());
				details.put("name", carrier.getName());
				details.put("abbreviation", carrier.getAbbreviation());
				
				data.add(details);
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in addCarrier", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in addCarrier";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}

	/**
	 * This method is used to add policy plan information
	 * @param request {@link PolicyPlanDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject addPolicyPlan(PolicyPlanDTO request) {
		// TODO Auto-generated method stub
		
		ScriptLogger.writeInfo("addPolicyPlan Called....");
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		try {

			Carrier carrier = k2DAO.fetchCarrierById(request.getCarrierId());
			if(carrier != null) {
			
				PolicyPlan plan = new PolicyPlan();
				plan.setCarrier(carrier);
				plan.setName(request.getName());
				plan.setType(request.getType());
				plan.setPlanType(request.getPlanType());
				k2DAO.savePolicyPlan(plan);
				
				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";

			}else {
				ScriptLogger.writeInfo("1000");
				ScriptLogger.writeInfo("Invalid Carrier....." + request.getCarrierId());
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in addCarrier", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in addCarrier";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to get all policy plans
	 * @param request {@link PolicyPlanDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getPolicyPlan(PolicyPlanDTO request) {
		// TODO Auto-generated method stub
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		List<JSONObject> data = new ArrayList<>();
		try {
			
			UUID carrierId = request.getCarrierId();
			
			List<PolicyPlan> policyPlans = k2DAO.fetchPlanByCarrierId(carrierId);
			for (PolicyPlan plan : policyPlans) {
				
				JSONObject details = new JSONObject();
				details.put("id", plan.getId());
				details.put("carrierId", plan.getCarrier().getId());
				details.put("name", plan.getName());
				details.put("type", plan.getType());
				details.put("planType", plan.getPlanType());
				
				data.add(details);
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in getPolicyPlan", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in getPolicyPlan";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}
	
	/**
	 * This method is used to add rider information
	 * @param request {@link RiderDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject addRider(RiderDTO request) {
		// TODO Auto-generated method stub
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		try {
			Carrier carrier = k2DAO.fetchCarrierById(request.getCarrierId());
			PolicyPlan plan = k2DAO.fetchPlanById(request.getPolicyPlanId());
			
			if(carrier != null && plan != null) {
			
				Rider rider = new Rider();
				rider.setCarrier(carrier);
				rider.setPolicyPlan(plan);
				rider.setName(request.getName());
				k2DAO.addRider(rider);
				
				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Success";

			}else {
				ScriptLogger.writeInfo("Invalid Carrier....." + request.getCarrierId());
				ScriptLogger.writeInfo("Invalid Plan....." + request.getPolicyPlanId());
			}
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in addRider", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in addRider";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to get all rider information
	 * @param request {@link RiderDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getRider(RiderDTO request) {
		// TODO Auto-generated method stub

		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		List<JSONObject> data = new ArrayList<>();
		try {
			
			UUID carrierId = request.getCarrierId();
			UUID planId = request.getPolicyPlanId();
			
			List<Rider> riderList = k2DAO.fetchRiderByCarrierAndPlanId(carrierId, planId);
			for (Rider rider : riderList) {
				
				JSONObject riderDetail = new JSONObject();
				riderDetail.put("id", rider.getId());
				riderDetail.put("name", rider.getName());
				
				data.add(riderDetail);
			}
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in getRider", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in getRider";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}

	/**
	 * This method is used to add insurance in conjurer side
	 * @param request {@link InsuranceDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	@Transactional
	public JSONObject addInsurance(InsuranceDTO request) {
		// TODO Auto-generated method stub
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Error in Adding Insurance";
		
		try {
			
			UUID userId = request.getUserId();
			User user = k2ServiceImpl.fetchUserById(userId);
			
			if(user != null) {
				
				String policyPlanId = request.getPolicyPlanId();
				PolicyPlan plan = k2DAO.fetchPlanById(UUID.fromString(policyPlanId));
				
				if(plan != null) {
					
					Random random = new Random();
					int randomNumber = random.nextInt(999999 - 100000) + 100000;
					
					Insurance insurance = new Insurance();
					insurance.setUser(user);
					insurance.setPolicyNumber(request.getPolicyNumber());
					insurance.setPolicyPlan(plan);
					insurance.setBasicFaceValue(request.getBasicFaceValue());
					insurance.setDeath(request.getDeath());
					insurance.setDeathCurrency(request.getCurrency());
					insurance.setPermanentDisability(request.getPermanentDisability());
					insurance.setPermanentDisabilityCurrency(request.getCurrency());
					insurance.setCriticalIllness(request.getCriticalIllness());
					insurance.setCriticalIllnessCurrency(request.getCurrency());
					insurance.setAccidentalDeath(request.getAccidentalDeath());
					insurance.setAccidentalDeathCurrency(request.getCurrency());
					insurance.setCurrency(request.getCurrency());
					insurance.setMaturityDate(request.getMaturityDate());
					insurance.setPremium(request.getPremium());
					insurance.setPremiumCurrency(request.getPremiumCurrency());
					insurance.setLoadingPremium(request.getLoadingPremium());
					insurance.setLoadingPremiumCurrency(request.getLoadingPremiumCurrency());
					insurance.setPremiumFrequency(request.getPremiumFrequency());
					insurance.setUniqueId(Integer.toString(randomNumber));
					
					insurance = k2DAO.addInsurance(insurance);
					ScriptLogger.writeInfo("Insurance Id = " + insurance.getId());
					
					// Store Insurance Rider Primium
					List<RiderPremiumDTO> riderPremiumList = request.getRiderPremium();
					for (RiderPremiumDTO riderPremium : riderPremiumList) {
						
						Rider rider = k2DAO.fetchRiderById(riderPremium.getId());
						if(rider != null) {
							
							// Store Insurance and Rider Map
							InsuranceRiderMap insuranceRiderMap = new InsuranceRiderMap();
							insuranceRiderMap.setRider(rider);
							insuranceRiderMap.setInsurance(insurance);
							insuranceRiderMap = k2DAO.saveInsuranceRiderMap(insuranceRiderMap);
							ScriptLogger.writeInfo("IRMAP Id = " + insuranceRiderMap.getId());							
							
							RiderPremium premium = new RiderPremium();
							premium.setInsuranceRiderMap(insuranceRiderMap);
							premium.setPremium(riderPremium.getPremium());
							premium.setPremiumCurrency(request.getCurrency());
							premium.setLoadingPremium(riderPremium.getLoadingPremium());
							premium.setLoadingPremiumCurrency(request.getCurrency());
							premium.setDeath(riderPremium.getDeath());
							premium.setDeathCurrency(request.getCurrency());
							premium.setPermanentDisability(riderPremium.getPermanentDisability());
							premium.setPermanentDisabilityCurrency(request.getCurrency());
							premium.setCriticalIllness(riderPremium.getCriticalIllness());
							premium.setCriticalIllnessCurrency(request.getCurrency());
							premium.setAccidentalDeath(riderPremium.getAccidentalDeath());
							premium.setAccidentalDeathCurrency(request.getCurrency());
							k2DAO.saveRiderPremium(premium);
							
							errorCode = 0;
							responseStatus = Constants.SUCCESS;
							responseMessage ="Success";
							
						}else {
							ScriptLogger.writeInfo("Rider Not Found = " + riderPremium.getId());
						}
					}
				}else {
					ScriptLogger.writeInfo("Invalid Policy Plan = " + policyPlanId);
				}
				
			}else {
				ScriptLogger.writeInfo("Invalid User = " + userId);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in addRider", e);
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}

	/**
	 * This method is used to get all insurance from conjurer side
	 * @param request {@link InsuranceDTO}
	 * @return JSONObject {@link JSONObject} 
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getInsurance(InsuranceDTO request) {
		// TODO Auto-generated method stub
		
		int errorCode = 100;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		
		List<InsuranceDTO> insuranceDTOList = new ArrayList<>();
		try {
			
			UUID userId = request.getUserId();
			List<Insurance> insurances = k2DAO.fetchInsuranceByUserId(userId);			
			for (Insurance insurance : insurances) {
				
				InsuranceDTO insuranceDTO = new InsuranceDTO();
				insuranceDTO.setId(insurance.getId());
				insuranceDTO.setPlanName(insurance.getPolicyPlan().getName());
				insuranceDTO.setPlanType(insurance.getPolicyPlan().getPlanType());
				insuranceDTO.setCarrierName(insurance.getPolicyPlan().getCarrier().getName());
				insuranceDTO.setPolicyNumber(insurance.getPolicyNumber());
				insuranceDTO.setBasicFaceValue(insurance.getBasicFaceValue());
				insuranceDTO.setDeath(insurance.getDeath());
				insuranceDTO.setDeathCurrency(insurance.getDeathCurrency());
				insuranceDTO.setPermanentDisability(insurance.getAccidentalDeath());
				insuranceDTO.setPermanentDisabilityCurrency(insurance.getPermanentDisabilityCurrency());
				insuranceDTO.setCriticalIllness(insurance.getCriticalIllness());
				insuranceDTO.setCriticalIllnessCurrency(insurance.getCriticalIllnessCurrency());
				insuranceDTO.setAccidentalDeath(insurance.getAccidentalDeath());
				insuranceDTO.setAccidentalDeathCurrency(insurance.getAccidentalDeathCurrency());
				insuranceDTO.setCurrency(insurance.getCurrency());
				insuranceDTO.setMaturityDate(insurance.getMaturityDate());
				insuranceDTO.setPremium(insurance.getPremium());
				insuranceDTO.setPremiumCurrency(insurance.getPremiumCurrency());
				insuranceDTO.setLoadingPremium(insurance.getLoadingPremium());
				insuranceDTO.setLoadingPremiumCurrency(insurance.getLoadingPremiumCurrency());
				insuranceDTO.setPremiumFrequency(insurance.getPremiumFrequency());
				insuranceDTO.setUniqueId(insurance.getUniqueId());
				
				List<RiderPremiumDTO> riderPremiumList = new ArrayList<>();
				
				List<InsuranceRiderMap> insuranceRiders = insurance.getInsuranceRiderMap();
				for (InsuranceRiderMap insuranceRider : insuranceRiders) {
					
					RiderPremium riderPremium = k2DAO.fetchRiderPremiumByIRMapId(insuranceRider.getId());
					
					if(riderPremium != null) {
						
						RiderPremiumDTO riderPremiumData = new RiderPremiumDTO();
						riderPremiumData.setId(riderPremium.getId());
						riderPremiumData.setName(riderPremium.getInsuranceRiderMap().getRider().getName());
						riderPremiumData.setPremium(riderPremium.getPremium());
						riderPremiumData.setPremiumCurrency(riderPremium.getPremiumCurrency());
						riderPremiumData.setLoadingPremium(riderPremium.getLoadingPremium());
						riderPremiumData.setLoadingPremiumCurrency(riderPremium.getLoadingPremiumCurrency());
						riderPremiumData.setDeath(riderPremium.getDeath());
						riderPremiumData.setDeathCurrency(riderPremium.getDeathCurrency());
						riderPremiumData.setPermanentDisability(riderPremium.getPermanentDisability());
						riderPremiumData.setPermanentDisabilityCurrency(riderPremium.getPermanentDisabilityCurrency());
						riderPremiumData.setCriticalIllness(riderPremium.getCriticalIllness());
						riderPremiumData.setCriticalIllnessCurrency(riderPremium.getCriticalIllnessCurrency());
						riderPremiumData.setAccidentalDeath(riderPremium.getAccidentalDeath());
						riderPremiumData.setAccidentalDeathCurrency(riderPremium.getAccidentalDeathCurrency());
						
						riderPremiumList.add(riderPremiumData);
						
					}else {
						ScriptLogger.writeInfo("Rider Premium Not Found for Insurance Rider Map Id = " + insuranceRider.getId());
					}
				}
				insuranceDTO.setRiderPremium(riderPremiumList);
				insuranceDTOList.add(insuranceDTO);
			}
			
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "Success";

		}catch (Exception e) {
			// TODO: handle exception
			
			ScriptLogger.writeError("Error in getRider", e);
			errorCode = 100;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in getRider";
		}
		JSONObject response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", insuranceDTOList);
		return response;
	}
}
