package com.pisight.pimoney.dto;

import java.util.HashMap;

public class RateDTO {
	
	private static HashMap<String, Double> currentRateMap = new HashMap<String, Double>();
	
	public RateDTO(String currency, Double rate) {
		currentRateMap.put(currency, rate);
	}
	
	public static Double getRate(String currency) {
		return currentRateMap.get(currency);
	}

	public static HashMap<String, Double> getCurrentRateMap() {
		return currentRateMap;
	}

	public static void setCurrentRateMap(HashMap<String, Double> currentRateMap) {
		RateDTO.currentRateMap = currentRateMap;
	}
}
