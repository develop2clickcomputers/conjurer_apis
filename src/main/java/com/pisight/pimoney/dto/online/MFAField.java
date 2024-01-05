package com.pisight.pimoney.dto.online;

public class MFAField {
	
	private String piTrackerId = null;
	
	private String fieldType = null;
	
	private String fieldValue = null;
	
	private String fieldLabel = null;
	
	private String fieldName = null;

	/**
	 * @return the piTrackerId
	 */
	public String getPiTrackerId() {
		return piTrackerId;
	}

	/**
	 * @param piTrackerId the piTrackerId to set
	 */
	public void setPiTrackerId(String piTrackerId) {
		this.piTrackerId = piTrackerId;
	}

	/**
	 * @return the filedType
	 */
	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}


	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
