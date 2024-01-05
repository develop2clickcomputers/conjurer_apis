package com.pisight.pimoney.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.jxpath.JXPathContext;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.SecurityMaster;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;

public class EntityFinder {

	// change methods to work with AccountBaseEntity
	// create method to find online and manual transaction

	// for testing purpose
	/*@SuppressWarnings("unchecked")
	public static void main(String args[]) {

		String objectPath = "/home/kumar/userfiles/XaaloDa9qk6F/object/";
		String filename = objectPath + "account.ser";

		List<InvestmentAccountEntity> accounts = null;

		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			accounts = (List<InvestmentAccountEntity>) in.readObject();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println(findAssetById("4e9d6a29-fb39-422c-974d-13309dbf2739", accounts));
		System.out.println(findTransactionById("c90d493e-7ae6-48f6-837c-74abe4c6d6ee", accounts));
		System.out.println(findAccountById("a61480c0-ea9c-4666-942e-a0d9962568a8", accounts));
		System.out.println(findAssetsByCategory(HoldingAsset.CATEGORY_BOND, accounts));
		System.out.println(filterInvestmentAccount(accounts));

	}*/

	public static HoldingAssetEntity findAssetById(String id, Collection<InvestmentAccountEntity> accountList) {

		HoldingAssetEntity entity = null;
		try {
			entity = (HoldingAssetEntity) JXPathContext.newContext(accountList).getValue("assets[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e = ", e);
		}
		return entity;
	}

	public static TransactionBaseEntity findTransactionById(String id, Collection<AccountBaseEntity> accountList) {

		TransactionBaseEntity entity = null;
		try {
			entity = (TransactionBaseEntity) JXPathContext.newContext(accountList).getValue("transactions[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}
	
	public static InvestmentTransactionEntity findInvestmentTransactionById(String id, Collection<InvestmentAccountEntity> accountList) {

		InvestmentTransactionEntity entity = null;
		try {
			entity = (InvestmentTransactionEntity) JXPathContext.newContext(accountList).getValue("transactions[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}
	
	public static BankTransactionEntity findBankTransactionById(String id, Collection<BankAccountEntity> accountList) {

		BankTransactionEntity entity = null;
		try {
			entity = (BankTransactionEntity) JXPathContext.newContext(accountList).getValue("transactions[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}
	
	public static CardTransactionEntity findCardTransactionById(String id, Collection<CardAccountEntity> accountList) {

		CardTransactionEntity entity = null;
		try {
			entity = (CardTransactionEntity) JXPathContext.newContext(accountList).getValue("transactions[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}
	
	public static LoanTransactionEntity findLoanTransactionById(String id, Collection<LoanAccountEntity> accountList) {

		LoanTransactionEntity entity = null;
		try {
			entity = (LoanTransactionEntity) JXPathContext.newContext(accountList).getValue("transactions[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}

	public static SecurityMaster findMasterById(String id, Collection<InvestmentAccountEntity> accountList) {

		SecurityMaster entity = null;
		try {
			entity = (SecurityMaster) JXPathContext.newContext(accountList).getValue("scxList[id='" + id +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeInfo("Exception e =", e);
		}
		return entity;
	}

	public static AccountBaseEntity findAccountById(String id, Collection<AccountBaseEntity> accountList, String tag) {

		AccountBaseEntity entity = null;

		for(AccountBaseEntity account: accountList) {
			if(account.getId().toString().equals(id) && account.getTag().equals(tag)) {
				entity = account;
				break;
			}
		}
		return entity;
	}

	public static AccountBaseEntity findAccountByAccountNumber(String accountNumber, Collection<? extends AccountBaseEntity> accountList, String tag) {

		ScriptLogger.writeInfo("Account number => " + accountNumber);
		AccountBaseEntity entity = null;

		for(AccountBaseEntity account: accountList) {
			ScriptLogger.writeInfo("Account number entity => " + account.getAccountNumber());
			if(account.getAccountNumber().toString().equals(accountNumber) && account.getTag().equals(tag)) {
				entity = account;
				break;
			}
		}
		return entity;
	}

	public static List<InvestmentAccountEntity> filterInvestmentAccount(Collection<? extends AccountBaseEntity> accountList) {

		List<InvestmentAccountEntity> entity = new ArrayList<InvestmentAccountEntity>();;
		for(AccountBaseEntity account: accountList) {
			if(account.getTag().equals(Constants.TAG_INVESTMENT)) {
				entity.add((InvestmentAccountEntity) account);
			}
		}
		return entity;
	}

	public static List<BankAccountEntity> filterBankAccount(Collection<? extends AccountBaseEntity> accountList) {

		List<BankAccountEntity> entity = new ArrayList<BankAccountEntity>();;
		for(AccountBaseEntity account: accountList) {
			if(account.getTag().equals(Constants.TAG_BANK)) {
				entity.add((BankAccountEntity) account);
			}
		}
		return entity;
	}

	public static List<CardAccountEntity> filterCardAccount(Collection<? extends AccountBaseEntity> accountList) {

		List<CardAccountEntity> entity = new ArrayList<CardAccountEntity>();;
		for(AccountBaseEntity account: accountList) {
			if(account.getTag().equals(Constants.TAG_CARD)) {
				entity.add((CardAccountEntity) account);
			}
		}
		return entity;
	}

	public static List<LoanAccountEntity> filterLoanAccount(Collection<? extends AccountBaseEntity> accountList) {

		List<LoanAccountEntity> entity = new ArrayList<LoanAccountEntity>();;
		for(AccountBaseEntity account: accountList) {
			if(account.getTag().equals(Constants.TAG_LOAN)) {
				entity.add((LoanAccountEntity) account);
			}
		}
		return entity;
	}
	
	public static List<FixedDepositAccountEntity> filterFDAccount(Collection<? extends AccountBaseEntity> accountList) {

		List<FixedDepositAccountEntity> entity = new ArrayList<FixedDepositAccountEntity>();;
		for(AccountBaseEntity account: accountList) {
			if(account.getTag().equals(Constants.TAG_FIXED_DEPOSIT)) {
				entity.add((FixedDepositAccountEntity) account);
			}
		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	public static List<HoldingAssetEntity> findAssetsByCategory(String category, Collection<InvestmentAccountEntity> accountList){

		List<HoldingAssetEntity> result = new ArrayList<HoldingAssetEntity>();
		try {
			result = JXPathContext.newContext(accountList).selectNodes("assets[holdingAssetCategory='" + category +"']");
		}
		catch(Exception e) {
			ScriptLogger.writeError("Exception e = ", e);
		}
		return result;
	}
}
