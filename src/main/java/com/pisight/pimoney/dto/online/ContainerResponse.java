package com.pisight.pimoney.dto.online;

import java.util.ArrayList;
import java.util.List;

import com.pisight.pimoney.models.Container;

public class ContainerResponse {
	
	private int errorCode = 0;
	
	private String message = null;
	
	private List<Container> accounts = new ArrayList<Container>();

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the accounts
	 */
	public List<Container> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(List<Container> accounts) {
		this.accounts = accounts;
	}
	
	public void addAccounts(Container account) {
		accounts.add(account);
	}
	
	
	

}
