package com.pisight.pimoney.dto;

import java.util.ArrayList;
import java.util.List;

import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;

public class PreviewResponse {
	
	private int errorCode;
	
	private String responseStatus = null;
	
	private String responseMessage = null;
	
	List<InvestmentAccountEntity> investments = new ArrayList<InvestmentAccountEntity>();
	
	List<BankAccountEntity> banks = new ArrayList<BankAccountEntity>();
	
	List<CardAccountEntity> cards = new ArrayList<CardAccountEntity>();
	
	List<LoanAccountEntity> loans = new ArrayList<LoanAccountEntity>();
	
	List<FixedDepositAccountEntity> fixedDeposits = new ArrayList<FixedDepositAccountEntity>();

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * @return the investments
	 */
	public List<InvestmentAccountEntity> getInvestments() {
		return investments;
	}

	/**
	 * @param investments the investments to set
	 */
	public void setInvestments(List<InvestmentAccountEntity> investments) {
		this.investments = investments;
	}

	/**
	 * @return the banks
	 */
	public List<BankAccountEntity> getBanks() {
		return banks;
	}

	/**
	 * @param banks the banks to set
	 */
	public void setBanks(List<BankAccountEntity> banks) {
		this.banks = banks;
	}

	/**
	 * @return the cards
	 */
	public List<CardAccountEntity> getCards() {
		return cards;
	}

	/**
	 * @param cards the cards to set
	 */
	public void setCards(List<CardAccountEntity> cards) {
		this.cards = cards;
	}

	/**
	 * @return the loans
	 */
	public List<LoanAccountEntity> getLoans() {
		return loans;
	}

	/**
	 * @param loans the loans to set
	 */
	public void setLoans(List<LoanAccountEntity> loans) {
		this.loans = loans;
	}

	/**
	 * @return the fixedDeposits
	 */
	public List<FixedDepositAccountEntity> getFixedDeposits() {
		return fixedDeposits;
	}

	/**
	 * @param fixedDeposits the fixedDeposits to set
	 */
	public void setFixedDeposits(List<FixedDepositAccountEntity> fixedDeposits) {
		this.fixedDeposits = fixedDeposits;
	}
	
}
