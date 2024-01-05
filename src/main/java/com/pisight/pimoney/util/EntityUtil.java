package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.aspectj.weaver.patterns.ParserException;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.AddTransactionDTO;
import com.pisight.pimoney.dto.AssetEditFields;
import com.pisight.pimoney.dto.SecurityMasterEditFields;
import com.pisight.pimoney.dto.TransactionEditFields;
import com.pisight.pimoney.models.BankAccount;
import com.pisight.pimoney.models.BankTransaction;
import com.pisight.pimoney.models.CardAccount;
import com.pisight.pimoney.models.CardTransaction;
import com.pisight.pimoney.models.Container;
import com.pisight.pimoney.models.FixedDepositAccount;
import com.pisight.pimoney.models.GenericAccount;
import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.models.InvestmentAccount;
import com.pisight.pimoney.models.InvestmentTransaction;
import com.pisight.pimoney.models.LoanAccount;
import com.pisight.pimoney.models.LoanTransaction;
import com.pisight.pimoney.models.TransactionBase;
import com.pisight.pimoney.repository.entities.AccountBaseEntity;
import com.pisight.pimoney.repository.entities.BankAccountEntity;
import com.pisight.pimoney.repository.entities.BankTransactionEntity;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.GenericAccountEntity;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;
import com.pisight.pimoney.repository.entities.InvestmentAccountEntity;
import com.pisight.pimoney.repository.entities.InvestmentTransactionEntity;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.SecurityMaster;
import com.pisight.pimoney.repository.entities.TransactionBaseEntity;

public class EntityUtil {

	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Account Entity ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static void copyToAccountEntity(Container container, AccountBaseEntity baseEntity) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if(container instanceof InvestmentAccount && baseEntity instanceof InvestmentAccountEntity) {
			InvestmentAccount account = (InvestmentAccount) container;
			InvestmentAccountEntity entity = (InvestmentAccountEntity) baseEntity;
			copyToInvestmentAccountEntity(account, entity);
		}
		else if(container instanceof BankAccount && baseEntity instanceof BankAccountEntity) {
			BankAccount account = (BankAccount) container;
			BankAccountEntity entity = (BankAccountEntity) baseEntity;
			copyToBankAccountEntity(account, entity);
		}
		else if(container instanceof CardAccount && baseEntity instanceof CardAccountEntity) {
			CardAccount account = (CardAccount) container;
			CardAccountEntity entity = (CardAccountEntity) baseEntity;
			copyToCardAccountEntity(account, entity);
		}
		else if(container instanceof LoanAccount && baseEntity instanceof LoanAccountEntity) {
			LoanAccount account = (LoanAccount) container;
			LoanAccountEntity entity = (LoanAccountEntity) baseEntity;
			copyToLoanAccountEntity(account, entity);
		}
		else if(container instanceof FixedDepositAccount && baseEntity instanceof FixedDepositAccountEntity) {
			FixedDepositAccount account = (FixedDepositAccount) container;
			FixedDepositAccountEntity entity = (FixedDepositAccountEntity) baseEntity;
			copyToFixedDepositAccountEntity(account, entity);
		}
		else if(container instanceof GenericAccount && baseEntity instanceof GenericAccountEntity) {
			GenericAccount account = (GenericAccount) container;
			GenericAccountEntity entity = (GenericAccountEntity) baseEntity;
			copyToGenericAccountEntity(account, entity);
		}
	}

	public static void copyToInvestmentAccountEntity(InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {
		InvestmentAccountEntityUtil.copyToInvestmentAccountEntity(account, entity);
	}

	public static void copyToBankAccountEntity(BankAccount account, BankAccountEntity entity) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		BankAccountEntityUtil.copyToBankAccountEntity(account, entity);
	}

	public static void copyToCardAccountEntity(CardAccount account, CardAccountEntity entity) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		CardAccountEntityUtil.copyToCardAccountEntity(account, entity);
	}

	public static void copyToLoanAccountEntity(LoanAccount account, LoanAccountEntity entity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		LoanAccountEntityUtil.copyToLoanAccount(entity, account);
	}
	
	public static void copyToFixedDepositAccountEntity(FixedDepositAccount account, FixedDepositAccountEntity entity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		FDAccountEntityUtil.copyToFixedDepositAccount(entity, account);
	}
	
	public static void copyToGenericAccountEntity(GenericAccount account, GenericAccountEntity entity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		GenericAccountEntityUtil.copyToGenericAccount(entity, account);
	}

	public static AccountBaseEntity toAccountEntity(Container container) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if(container instanceof InvestmentAccount) {
			InvestmentAccount account = (InvestmentAccount) container;
			return toInvestmentAccountEntity(account);
			
		}else if(container instanceof BankAccount) {
			BankAccount account = (BankAccount) container;
			return toBankAccountEntity(account);
		}
		else if(container instanceof CardAccount) {
			CardAccount account = (CardAccount) container;
			return toCardAccountEntity(account);
		}
		else if(container instanceof LoanAccount) {
			LoanAccount account = (LoanAccount) container;
			return toLoanAccountEntity(account);
		}
		else if(container instanceof FixedDepositAccount) {
			FixedDepositAccount account = (FixedDepositAccount) container;
			return toFixedDepositAccountEntity(account);
		}
		else if(container instanceof GenericAccount) {
			GenericAccount account = (GenericAccount) container;
			return toGenericAccountEntity(account);
		}
		return null;
	}

	public static InvestmentAccountEntity toInvestmentAccountEntity(InvestmentAccount account) throws ParseException {
		return InvestmentAccountEntityUtil.toInvestmentAccountEntity(account);
	}

	public static BankAccountEntity toBankAccountEntity(BankAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return BankAccountEntityUtil.toBankAccountEntity(account);
	}

	public static CardAccountEntity toCardAccountEntity(CardAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return CardAccountEntityUtil.toCardAccountEntity(account);
	}

	public static LoanAccountEntity toLoanAccountEntity(LoanAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return LoanAccountEntityUtil.toLoanAccountEntity(account);
	}
	
	public static FixedDepositAccountEntity toFixedDepositAccountEntity(FixedDepositAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return FDAccountEntityUtil.toFixedDepositAccountEntity(account);
	}
	
	public static GenericAccountEntity toGenericAccountEntity(GenericAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return GenericAccountEntityUtil.toGenericAccountEntity(account);
	}

	public static AccountBaseEntity toAccountEntityDC(Container container) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if(container instanceof InvestmentAccount) {
			InvestmentAccount account = (InvestmentAccount) container;
			return toInvestmentAccountEntityDC(account);
		}
		else if(container instanceof BankAccount) {
			BankAccount account = (BankAccount) container;
			return toBankAccountEntityDC(account);
		}
		else if(container instanceof CardAccount) {
			CardAccount account = (CardAccount) container;
			return toCardAccountEntityDC(account);
		}
		else if(container instanceof LoanAccount) {
			LoanAccount account = (LoanAccount) container;
			return toLoanAccountEntityDC(account);
		}
		else if(container instanceof FixedDepositAccount) {
			FixedDepositAccount account = (FixedDepositAccount) container;
			return toFixedDepositAccountEntityDC(account);
		}
		else if(container instanceof GenericAccount) {
			GenericAccount account = (GenericAccount) container;
			return toGenericAccountEntityDC(account);
		}
		return null;
	}


	public static InvestmentAccountEntity toInvestmentAccountEntityDC(InvestmentAccount account) throws ParseException {
		return InvestmentAccountEntityUtil.toInvestmentAccountEntityDC(account);
	}

	public static BankAccountEntity toBankAccountEntityDC(BankAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return BankAccountEntityUtil.toBankAccountEntityDC(account);
	}

	public static CardAccountEntity toCardAccountEntityDC(CardAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return CardAccountEntityUtil.toCardAccountEntityDC(account);
	}

	public static LoanAccountEntity toLoanAccountEntityDC(LoanAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return LoanAccountEntityUtil.toLoanAccountEntityDC(account);
	}
	
	public static FixedDepositAccountEntity toFixedDepositAccountEntityDC(FixedDepositAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return FDAccountEntityUtil.toFixedDepositAccountEntityDC(account);
	}
	
	public static GenericAccountEntity toGenericAccountEntityDC(GenericAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return GenericAccountEntityUtil.toGenericAccountEntityDC(account);
	}

	public static void copyToAssetEntity(HoldingAsset asset, HoldingAssetEntity entity) throws ParseException {
		AssetEntityUtil.copyToAssetEntity(asset, entity);
	}

	public static HoldingAssetEntity toAssetEntity(HoldingAsset asset) throws ParseException {
		return AssetEntityUtil.toAssetEntity(asset);
	}


	/**
	 * This method applies logic to create, update and delete for the assets of the already present account.
	 * It return the list of HoldingAssetEntities which need to be saved.
	 * 
	 * @param account {@link InvestmentAccount}
	 * @param entity {@link InvestmentAccountEntity}
	 * @return List of HoldingAssetEntity
	 * @throws ParseException {@link ParseException}
	 */
	public static List<HoldingAssetEntity>  handleAssetEntitiesForOldAccount(InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {
		return AssetEntityUtil.handleAssetEntitiesForOldAccount(account, entity);
	}

	public static void copyToTransactionEntity(TransactionBase transactionBase, TransactionBaseEntity baseEntity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(transactionBase instanceof InvestmentTransaction && baseEntity instanceof InvestmentTransactionEntity) {
			InvestmentTransaction transaction = (InvestmentTransaction) transactionBase;
			InvestmentTransactionEntity entity = (InvestmentTransactionEntity) baseEntity;
			copyToInvestmentTransactionEntity(transaction, entity);
		}
		else if(transactionBase instanceof BankTransaction && baseEntity instanceof BankTransactionEntity) {
			BankTransaction transaction = (BankTransaction) transactionBase;
			BankTransactionEntity entity = (BankTransactionEntity) baseEntity;
			copyToBankTransactionEntity(transaction, entity);
		}
		else if(transactionBase instanceof CardTransaction && baseEntity instanceof CardTransactionEntity) {
			CardTransaction transaction = (CardTransaction) transactionBase;
			CardTransactionEntity entity = (CardTransactionEntity) baseEntity;
			copyToCardTransactionEntity(transaction, entity);
		}
		else if(transactionBase instanceof LoanTransaction && baseEntity instanceof LoanTransactionEntity) {
			LoanTransaction transaction = (LoanTransaction) transactionBase;
			LoanTransactionEntity entity = (LoanTransactionEntity) baseEntity;
			copyToLoanTransactionEntity(transaction, entity);
		}
	}

	private static void copyToBankTransactionEntity(BankTransaction transaction, BankTransactionEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		BankTransactionEntityUtil.copyToBankTransactionEntity(transaction, entity);
	}

	private static void copyToCardTransactionEntity(CardTransaction transaction, CardTransactionEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		CardTransactionEntityUtil.copyToCardTransactionEntity(transaction, entity);
	}

	private static void copyToLoanTransactionEntity(LoanTransaction transaction, LoanTransactionEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		LoanTransactionEntityUtil.copyToLoanTransactionEntity(transaction, entity);
	}

	public static void copyToInvestmentTransactionEntity(InvestmentTransaction transaction, InvestmentTransactionEntity entity) throws ParseException {

		InvestmentTransactionEntityUtil.copyToInvestmentTransactionEntity(transaction, entity);
	}

	public static TransactionBaseEntity toTransactionEntity(TransactionBase transaction) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(transaction instanceof InvestmentTransaction) {
			InvestmentTransaction txn = (InvestmentTransaction) transaction;
			return toInvestmentTransactionEntity(txn);
		}
		else if(transaction instanceof BankTransaction) {
			BankTransaction txn = (BankTransaction) transaction;
			return toBankTransactionEntity(txn);
		}
		else if(transaction instanceof CardTransaction) {
			CardTransaction txn = (CardTransaction) transaction;
			return toCardTransactionEntity(txn);
		}
		else if(transaction instanceof LoanTransaction) {
			LoanTransaction txn = (LoanTransaction) transaction;
			return toLoanTransactionEntity(txn);
		}
		return null;
	}

	public static InvestmentTransactionEntity toInvestmentTransactionEntity(InvestmentTransaction transaction) throws ParseException {

		return InvestmentTransactionEntityUtil.toInvestmentTransactionEntity(transaction);
	}

	public static BankTransactionEntity toBankTransactionEntity(BankTransaction txn) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return BankTransactionEntityUtil.toBankTransactionEntity(txn);
	}

	public static CardTransactionEntity toCardTransactionEntity(CardTransaction txn) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return CardTransactionEntityUtil.toCardTransactionEntity(txn);
	}

	public static LoanTransactionEntity toLoanTransactionEntity(LoanTransaction txn) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		return LoanTransactionEntityUtil.toLoanTransactionEntity(txn);
	}

	/**
	 * This method applies logic to create, update and delete for the investment transactions of the already present account.
	 * It return the list of InvestmentTransactionEntity which need to be saved.
	 * 
	 * @param container {@link Container}
	 * @param baseEntity {@link AccountBaseEntity}
	 * @return List of Transaction Entities
	 * @throws ParseException {@link ParserException}
	 * @throws InvocationTargetException {@link InvocationTargetException}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws SecurityException {@link SecurityException}
	 * @throws NoSuchMethodException {@link NoSuchMethodException}
	 */
	public static List<? extends TransactionBaseEntity> handleTransactionEntitiesForOldAccount(Container container, AccountBaseEntity baseEntity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		if(container instanceof InvestmentAccount && baseEntity instanceof InvestmentAccountEntity) {
			InvestmentAccount account = (InvestmentAccount) container;
			InvestmentAccountEntity entity = (InvestmentAccountEntity) baseEntity;
			return handleInvestmentTransactionEntitiesForOldAccount(account, entity);
		}
		else if(container instanceof BankAccount && baseEntity instanceof BankAccountEntity) {
			BankAccount account = (BankAccount) container;
			BankAccountEntity entity = (BankAccountEntity) baseEntity;
			return handleBankTransactionEntitiesForOldAccount(account, entity);
		}
		else if(container instanceof CardAccount && baseEntity instanceof CardAccountEntity) {
			CardAccount account = (CardAccount) container;
			CardAccountEntity entity = (CardAccountEntity) baseEntity;
			return handleCardTransactionEntitiesForOldAccount(account, entity);
		}
		else if(container instanceof LoanAccount && baseEntity instanceof LoanAccountEntity) {
			LoanAccount account = (LoanAccount) container;
			LoanAccountEntity entity = (LoanAccountEntity) baseEntity;
			return handleLoanTransactionEntitiesForOldAccount(account, entity);
		}
		return null;
	}


	private static List<? extends TransactionBaseEntity> handleBankTransactionEntitiesForOldAccount(BankAccount account,
			BankAccountEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		return BankTransactionEntityUtil.handleBankTransactionEntitiesForOldAccount(account, entity);
	}

	private static List<? extends TransactionBaseEntity> handleCardTransactionEntitiesForOldAccount(CardAccount account,
			CardAccountEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		return CardTransactionEntityUtil.handleCardTransactionEntitiesForOldAccount(account, entity);
	}

	private static List<? extends TransactionBaseEntity> handleLoanTransactionEntitiesForOldAccount(LoanAccount account,
			LoanAccountEntity entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		return LoanTransactionEntityUtil.handleLoanTransactionEntitiesForOldAccount(account, entity);
	}

	/**
	 * This method applies logic to create, update and delete for the investment transactions of the already present account.
	 * It return the list of InvestmentTransactionEntity which need to be saved.
	 * 
	 * @param account {@link InvestmentAccount}
	 * @param entity {@link InvestmentAccountEntity}
	 * @return List of InvestmentTransactionEntity
	 * @throws ParseException {@link ParseException}
	 */
	public static List<InvestmentTransactionEntity>  handleInvestmentTransactionEntitiesForOldAccount(InvestmentAccount account, InvestmentAccountEntity entity) throws ParseException {

		return InvestmentTransactionEntityUtil.handleInvestmentTransactionEntitiesForOldAccount(account, entity);
	}

	public static List<SecurityMaster> createSecurityMaster(InvestmentAccountEntity entity){

		return SecurityMasterUtil.createSecurityMaster(entity);
	}

	//~~~~~~~~~~~~~~~~~Update Entity Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static void updateHoldingAssetEntity(HoldingAssetEntity asset, AssetEditFields assetNew) {

		AssetEntityUtil.updateHoldingAssetEntity(asset, assetNew);
	}


	public static void updateTransactionEntity(TransactionBaseEntity baseEntity, TransactionEditFields txnNew) {

		if(baseEntity instanceof InvestmentTransactionEntity) {
			InvestmentTransactionEntity entity = (InvestmentTransactionEntity) baseEntity;
			updateInvestmentTransactionEntity(entity, txnNew);
		}
		else if(baseEntity instanceof BankTransactionEntity) {
			BankTransactionEntity entity = (BankTransactionEntity) baseEntity;
			updateBankTransactionEntity(entity, txnNew);
		}
		else if(baseEntity instanceof CardTransactionEntity) {
			CardTransactionEntity entity = (CardTransactionEntity) baseEntity;
			updateCardTransactionEntity(entity, txnNew);
		}
		else if(baseEntity instanceof LoanTransactionEntity) {
			LoanTransactionEntity entity = (LoanTransactionEntity) baseEntity;
			updateLoanTransactionEntity(entity, txnNew);
		}

	}

	private static void updateBankTransactionEntity(BankTransactionEntity entity, TransactionEditFields txnNew) {

		BankTransactionEntityUtil.updateBankTransactionEntity(entity, txnNew);
	}

	private static void updateCardTransactionEntity(CardTransactionEntity entity, TransactionEditFields txnNew) {

		CardTransactionEntityUtil.updateCardTransactionEntity(entity, txnNew);
	}

	private static void updateLoanTransactionEntity(LoanTransactionEntity entity, TransactionEditFields txnNew) {

		LoanTransactionEntityUtil.updateLoanTransactionEntity(entity, txnNew);
	}

	public static void updateInvestmentTransactionEntity(InvestmentTransactionEntity txn,
			TransactionEditFields txnNew) {

		InvestmentTransactionEntityUtil.updateInvestmentTransactionEntity(txn, txnNew);
	}

	public static void updateSecurityMaster(SecurityMaster master, SecurityMasterEditFields masterNew) {

		SecurityMasterUtil.updateSecurityMaster(master, masterNew);
	}

	/**
	 * This method copy asset entity object to asset object
	 * 
	 * @param entity {@link HoldingAssetEntity}
	 * @param asset {@link HoldingAsset}
	 * @throws NoSuchMethodException {@link NoSuchMethodException}
	 * @throws SecurityException {@link SecurityException}
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws InvocationTargetException {@link InvocationTargetException}
	 */
	public static void copyToAsset(HoldingAssetEntity entity, HoldingAsset asset) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy Holding Asset<<<<");
		AssetEntityUtil.copyToAsset(entity, asset);
		ScriptLogger.writeInfo(">>>>End copy Holding Asset<<<<");
	}

	/**
	 * This method copy Account entity object to  Account object
	 * 
	 * @param baseEntity {@link AccountBaseEntity}
	 * @param container {@link Container}
	 * @throws NoSuchMethodException {@link NoSuchMethodException}
	 * @throws SecurityException {@link SecurityException}
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws InvocationTargetException {@link InvocationTargetException}
	 */
	public static void copyToAccount(AccountBaseEntity baseEntity, Container container) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if(container instanceof InvestmentAccount && baseEntity instanceof InvestmentAccountEntity) {
			InvestmentAccount account = (InvestmentAccount) container;
			InvestmentAccountEntity entity = (InvestmentAccountEntity) baseEntity;
			copyToInvestmentAccount(entity, account);
		}
		else if(container instanceof BankAccount && baseEntity instanceof BankAccountEntity) {
			BankAccount account = (BankAccount) container;
			BankAccountEntity entity = (BankAccountEntity) baseEntity;
			copyToBankAccount(entity, account);
		}
		else if(container instanceof CardAccount && baseEntity instanceof CardAccountEntity) {
			CardAccount account = (CardAccount) container;
			CardAccountEntity entity = (CardAccountEntity) baseEntity;
			copyToCardAccount(entity, account);
		}
		else if(container instanceof LoanAccount && baseEntity instanceof LoanAccountEntity) {
			LoanAccount account = (LoanAccount) container;
			LoanAccountEntity entity = (LoanAccountEntity) baseEntity;
			copyToLoanAccount(entity, account);
		}
		else if(container instanceof FixedDepositAccount && baseEntity instanceof FixedDepositAccountEntity) {
			FixedDepositAccount account = (FixedDepositAccount) container;
			FixedDepositAccountEntity entity = (FixedDepositAccountEntity) baseEntity;
			copyToFixedDepositAccount(entity, account);
		}
		else if(container instanceof GenericAccount && baseEntity instanceof GenericAccountEntity) {
			GenericAccount account = (GenericAccount) container;
			GenericAccountEntity entity = (GenericAccountEntity) baseEntity;
			copyToGenericAccount(entity, account);
		}
	}


	private static void copyToBankAccount(BankAccountEntity entity, BankAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy Bank Account<<<<");
		BankAccountEntityUtil.copyToBankAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy Bank Account<<<<");
	}

	private static void copyToCardAccount(CardAccountEntity entity, CardAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy Card Account<<<<");
		CardAccountEntityUtil.copyToCardAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy Card Account<<<<");
	}

	private static void copyToLoanAccount(LoanAccountEntity entity, LoanAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy Loan Account<<<<");
		LoanAccountEntityUtil.copyToLoanAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy Loan Account<<<<");
	}
	
	private static void copyToFixedDepositAccount(FixedDepositAccountEntity entity, FixedDepositAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy FixedDeposit Account<<<<");
		FDAccountEntityUtil.copyToFixedDepositAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy FixedDeposit Account<<<<");
	}
	
	private static void copyToGenericAccount(GenericAccountEntity entity, GenericAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy GenericAccount<<<<");
		GenericAccountEntityUtil.copyToGenericAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy GenericAccount<<<<");
	}

	/**
	 * This method copy Investment Account entity object to Investment Account object
	 * 
	 * @param entity {@link InvestmentAccountEntity}
	 * @param account {@link InvestmentAccount}
	 * @throws NoSuchMethodException {@link NoSuchMethodException}
	 * @throws SecurityException {@link SecurityException}
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws InvocationTargetException {@link InvocationTargetException}
	 */
	public static void copyToInvestmentAccount(InvestmentAccountEntity entity, InvestmentAccount account) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo(">>>>Start copy Investment Account<<<<");
		InvestmentAccountEntityUtil.copyToInvestmentAccount(entity, account);
		ScriptLogger.writeInfo(">>>>End copy Investment Account<<<<");
	}


	/**
	 * This method copy  transaction entity object to  transaction object
	 * 
	 * @param baseEntity {@link TransactionBaseEntity}
	 * @param transactionBase {@link TransactionBase}
	 * @throws IllegalAccessException {@link IllegalAccessException}
	 * @throws IllegalArgumentException {@link IllegalArgumentException}
	 * @throws InvocationTargetException {@link InvocationTargetException}
	 * @throws NoSuchMethodException {@link NoSuchMethodException}
	 * @throws SecurityException {@link SecurityException}
	 */
	public static void copyToTransaction(TransactionBaseEntity baseEntity, TransactionBase transactionBase) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		if(transactionBase instanceof InvestmentTransaction && baseEntity instanceof InvestmentTransactionEntity) {
			InvestmentTransaction transaction = (InvestmentTransaction) transactionBase;
			InvestmentTransactionEntity entity = (InvestmentTransactionEntity) baseEntity;
			copyToInvestmentTransaction(entity, transaction);
		}
		else if(transactionBase instanceof BankTransaction && baseEntity instanceof BankTransactionEntity) {
			BankTransaction transaction = (BankTransaction) transactionBase;
			BankTransactionEntity entity = (BankTransactionEntity) baseEntity;
			copyToBankTransaction(entity, transaction);
		}
		else if(transactionBase instanceof CardTransaction && baseEntity instanceof CardTransactionEntity) {
			CardTransaction transaction = (CardTransaction) transactionBase;
			CardTransactionEntity entity = (CardTransactionEntity) baseEntity;
			copyToCardTransaction(entity, transaction);
		}
		else if(transactionBase instanceof LoanTransaction && baseEntity instanceof LoanTransactionEntity) {
			LoanTransaction transaction = (LoanTransaction) transactionBase;
			LoanTransactionEntity entity = (LoanTransactionEntity) baseEntity;
			copyToLoanTransaction(entity, transaction);
		}
	}

	private static void copyToBankTransaction(BankTransactionEntity entity, BankTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo(">>>>Start copy Bank Transaction<<<<");
		BankTransactionEntityUtil.copyToBankTransaction(entity, transaction);
		ScriptLogger.writeInfo(">>>>End copy Bank Transaction<<<<");
	}

	private static void copyToCardTransaction(CardTransactionEntity entity, CardTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo(">>>>Start copy Card Transaction<<<<");
		CardTransactionEntityUtil.copyToCardTransaction(entity, transaction);
		ScriptLogger.writeInfo(">>>>End copy Card Transaction<<<<");
	}

	private static void copyToLoanTransaction(LoanTransactionEntity entity, LoanTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo(">>>>Start copy Loan Transaction<<<<");
		LoanTransactionEntityUtil.copyToLoanTransaction(entity, transaction);
		ScriptLogger.writeInfo(">>>>End copy Loan Transaction<<<<");
	}

	public static void copyToInvestmentTransaction(InvestmentTransactionEntity entity, InvestmentTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo(">>>>Start copy Investment Transaction<<<<");
		InvestmentTransactionEntityUtil.copyToInvestmentTransaction(entity, transaction);
		ScriptLogger.writeInfo(">>>>End copy Investment Transaction<<<<");
	}

	public static TransactionBaseEntity addTransaction(AddTransactionDTO request) {

		String tag = request.getAccountType();
		if(Constants.TAG_BANK.equals(tag)) {
			return BankTransactionEntityUtil.addTransaction(request);
		}
		else if(Constants.TAG_CARD.equals(tag)) {
			return CardTransactionEntityUtil.addTransaction(request);
		}
		else if(Constants.TAG_LOAN.equals(tag)) {
			return LoanTransactionEntityUtil.addTransaction(request);
		}
		return null;
	}

	public static String convertToDateString(Date date) {

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_MM_DD);
		return sdf.format(date);
	}

	public static String maskString(String string) {

		return string;
		
		/*if(StringUtils.isNotEmpty(string)) {
			String maskedString = null;
			if(string.length() > 6) {
				maskedString = buildStringWithStars(string.length()-6) + string.substring(string.length()-6);
			}
			else if(string.length() > 4) {
				maskedString = buildStringWithStars(string.length()-4) + string.substring(string.length()-4);
			}
			else if(string.length() > 2){
				maskedString = buildStringWithStars(string.length()-2) + string.substring(string.length()-2);
			}
			else {
				maskedString = string;
			}
			return maskedString;

		}else {
			return "";
		}*/
	}

	/*private static String buildStringWithStars(int i) {
		// TODO Auto-generated method stub
		CharSequence[] array = new CharSequence[i];
		Arrays.fill(array, "X");
		return String.join("", array);
	}*/

}
