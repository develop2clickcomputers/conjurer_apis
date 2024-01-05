package com.pisight.pimoney.dto.online;

public class StatusUpdateRequest {
	
	private String piTrackerId = null;
	
	private String status = null;

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
	 * @return the state
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
