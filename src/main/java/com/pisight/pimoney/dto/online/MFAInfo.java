package com.pisight.pimoney.dto.online;

import java.util.ArrayList;
import java.util.List;

public class MFAInfo {
	
	private String piTrackerId = null;
	
	private String mfaType = null;
	
	private int maxTime = 120;
	
	private String message = null;
	
	private List<MFAField> fields = new ArrayList<MFAField>();

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
	 * @return the mfaType
	 */
	public String getMfaType() {
		return mfaType;
	}

	/**
	 * @param mfaType the mfaType to set
	 */
	public void setMfaType(String mfaType) {
		this.mfaType = mfaType;
	}

	/**
	 * @return the maxTime
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the fields
	 */
	public List<MFAField> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<MFAField> fields) {
		this.fields = fields;
	}
	
	public void addField(MFAField field) {
		fields.add(field);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
