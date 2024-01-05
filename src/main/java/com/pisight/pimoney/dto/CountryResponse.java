package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class CountryResponse {
	
	List<JSONObject> countries = new ArrayList<JSONObject>();

	/**
	 * @return the countries
	 */
	public List<JSONObject> getCountries() {
		return countries;
	}

	/**
	 * @param countryList the countries to set
	 */
	public void setCountries(List<JSONObject> countryList) {
		this.countries = countryList;
	}
	

}
