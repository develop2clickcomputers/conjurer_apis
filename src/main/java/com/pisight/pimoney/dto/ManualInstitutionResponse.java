package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class ManualInstitutionResponse {
	
	List<JSONObject> institutions = new ArrayList<JSONObject>();

	/**
	 * @return the institutions
	 */
	public List<JSONObject> getInstitutions() {
		return institutions;
	}

	/**
	 * @param institutions the institutions to set
	 */
	public void setInstitutions(List<JSONObject> institutions) {
		this.institutions = institutions;
	}

}
