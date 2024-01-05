package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class CategoryResponse {
	
	List<JSONObject> categories = new ArrayList<JSONObject>();

	/**
	 * @return the categories
	 */
	public List<JSONObject> getCategories() {
		return categories;
	}

	/**
	 * @param categoryList the categories to set
	 */
	public void setCategories(List<JSONObject> categoryList) {
		this.categories = categoryList;
	}
}
