package com.pisight.pimoney.dto;

public class InvPerformanceDTO {

	private String name;

	private Double value;

	public InvPerformanceDTO(String name, Double value) {
		this.name = name;
		this.value = value;
	}
	
	public InvPerformanceDTO() {
		
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

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
