package com.pisight.pimoney.dto;

import java.util.UUID;

public class RiderDTO {
	
	private UUID id;
	
	private UUID carrierId;
	
	private UUID policyPlanId;

	private String name;

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the carrierId
	 */
	public UUID getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId the carrierId to set
	 */
	public void setCarrierId(UUID carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return the policyPlanId
	 */
	public UUID getPolicyPlanId() {
		return policyPlanId;
	}

	/**
	 * @param policyPlanId the policyPlanId to set
	 */
	public void setPolicyPlanId(UUID policyPlanId) {
		this.policyPlanId = policyPlanId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
