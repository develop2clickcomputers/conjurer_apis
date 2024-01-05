package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.dto.AddTransactionDTO;
import com.pisight.pimoney.dto.TransactionEditFields;
import com.pisight.pimoney.models.LoanAccount;
import com.pisight.pimoney.models.LoanTransaction;
import com.pisight.pimoney.repository.entities.Category;
import com.pisight.pimoney.repository.entities.LoanAccountEntity;
import com.pisight.pimoney.repository.entities.LoanTransactionEntity;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.service.TransactionServiceImpl;

@Service
public class LoanTransactionEntityUtil {

	@Autowired
	private TransactionServiceImpl txnServiceImpl = null;

	private static TransactionServiceImpl txnServiceImplStatic = null;

	@PostConstruct
	public void init() {
		txnServiceImplStatic = txnServiceImpl;
	}

	@SuppressWarnings("rawtypes")
	public static LoanTransactionEntity copyToLoanTransactionEntity(LoanTransaction txn, LoanTransactionEntity entity) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		if(txn == null) {
			return null;
		}

		ScriptLogger.writeInfo("toLoanTransactionEntity convert");
		Method[] methods = ArrayUtils.addAll(txn.getClass().getDeclaredMethods(), txn.getClass().getSuperclass().getDeclaredMethods());
		for (Method method : methods) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
				continue;
			}
			Object value = method.invoke(txn);

			ScriptLogger.writeInfo("source method name -> " + methodName);
			ScriptLogger.writeInfo("source method type -> " + srcType);
			ScriptLogger.writeInfo("source value -> " + value);

			if (value != null) {

				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3);
				}
				else {
					methodName = methodName.substring(2);
				}

				Method dstMethod = null;
				try {
					dstMethod = entity.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = entity.getClass().getMethod("is"+methodName);
					}catch(NoSuchMethodException eInner) {
						ScriptLogger.writeInfo("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				ScriptLogger.writeInfo("dest method name -> " + dstMethod);
				ScriptLogger.writeInfo("dest method type -> " + dstType);

				if(srcType.equals(Date.class)) {
					Method dstMethod1 = entity.getClass().getMethod("set"+methodName, Date.class);
					dstMethod1.invoke(entity, (Date) value);
				}
				else if(dstType.equals(Date.class) && srcType.equals(String.class)) {
					Date date = WebUtil.convertToDate((String) value);
					Method dstMethod1 = entity.getClass().getMethod("set"+methodName, Date.class);
					dstMethod1.invoke(entity, date);
				}
				else if(dstType.equals(Boolean.TYPE)) {
					Method dstMethod1 = entity.getClass().getMethod("set"+methodName, Boolean.class);
					if(srcType.equals(Boolean.TYPE)) {
						dstMethod1.invoke(entity, (Boolean) value);
					}
					else {
						boolean val = false;
						String valStr = ((String) value).toLowerCase();
						if(valStr.equals("y") || valStr.equals("yes") || valStr.equals("true")){
							val = false;
						}
						dstMethod1.invoke(entity,  val);
					}
				}
				else {
					String val = (String) value;
					if(methodName.contains("AccountNumber")){
						val = EntityUtil.maskString(val);
					}
					Method dstMethod1 = entity.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(entity,  val);
				}
			}
		}
		return entity;
	}

	public static LoanTransactionEntity toLoanTransactionEntity(LoanTransaction transaction) 
			throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		LoanTransactionEntity entity = new LoanTransactionEntity();

		if(transaction == null) {
			return null;
		}

		copyToLoanTransactionEntity(transaction, entity);

		return entity;
	}

	public static List<LoanTransactionEntity> handleLoanTransactionEntitiesForOldAccount(
			LoanAccount account, LoanAccountEntity entity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List<LoanTransactionEntity> result = new ArrayList<LoanTransactionEntity>();

		HashMap<String, LoanTransactionEntity> map = new HashMap<String, LoanTransactionEntity>();

		List<LoanTransactionEntity> txnEntities = entity.getTransactions();
		List<LoanTransaction> txns = account.getTransactions();

		for(LoanTransactionEntity txnEntity: txnEntities) {
			map.put(txnEntity.getFingerprint(), txnEntity);
		}

		for(LoanTransaction txn: txns) {
			LoanTransactionEntity newTxnEntity = null;
			if(!map.containsKey(txn.getFingerprint())) {
				newTxnEntity = toLoanTransactionEntity(txn);
				newTxnEntity.setStatus(true);
				result.add(newTxnEntity);
			}
			else {
				newTxnEntity = map.get(txn.getFingerprint());
				if(!newTxnEntity.isStatus()) {
					newTxnEntity.setStatus(true);
					result.add(newTxnEntity);
				}
			}
		}
		return result;
	}

	public static void updateLoanTransactionEntity(LoanTransactionEntity txn,
			TransactionEditFields txnNew) {

		if(txn == null || txnNew == null) {
			return;
		}
		txn.setTransDate(txnNew.getTransDate());
//		txn.setCurrency(txnNew.getCurrency());
		txn.setAmount(txnNew.getAmount());
		Category c = txnServiceImplStatic.fetchCategoryByName(txnNew.getCategory());
		txn.setCategoryObj(c);
		if(c != null) {
			txn.setSubCategoryObj(txnServiceImplStatic.fetchSubCategoryByNameAndCategoryId(txnNew.getSubcategory(), c.getId()));
		}
		txn.setMerchantName(txnNew.getMerchantName());
	}

	@SuppressWarnings("rawtypes")
	public static void copyToLoanTransaction(LoanTransactionEntity entity,
			LoanTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo("test convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set")) {
				continue;
			}
			Object value = method.invoke(entity);

			ScriptLogger.writeInfo("source method name -> " + methodName);
			ScriptLogger.writeInfo("source method type -> " + srcType);
			ScriptLogger.writeInfo("source value -> " + value);

			if (value != null) {

				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3);
				}
				else {
					methodName = methodName.substring(2);
				}

				Method dstMethod = null;
				try {
					dstMethod = transaction.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = transaction.getClass().getMethod("is"+methodName);
					}catch(NoSuchMethodException eInner) {
						ScriptLogger.writeInfo("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				ScriptLogger.writeInfo("dest method name -> " + dstMethod);
				ScriptLogger.writeInfo("dest method type -> " + dstType);

				if(srcType.equals(Date.class)) {
					String date = EntityUtil.convertToDateString((Date) value);
					Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(transaction, date);
				}
				else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(transaction, (Boolean) value);
					}
					else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(transaction,  tempVal);
					}
				}
				else {
					Method dstMethod1 = transaction.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(transaction,  (String) value);
				}
			}
		}
	}

	public static LoanTransactionEntity addTransaction(AddTransactionDTO request) {

		LoanAccountEntity account = (LoanAccountEntity) txnServiceImplStatic.fetchManualAccount(request.getAccountType(), request.getUserId());
		ManualInstitution inst = txnServiceImplStatic.fetchManualInstitution(request.getInstitutionCode());

		account.setManualInstitution(inst);
		txnServiceImplStatic.saveAccount(account);
		LoanTransactionEntity txn = new LoanTransactionEntity();

		txn.setAccountNumber("Manually Added");
		txn.setAccount(account);
		txn.setCurrency(request.getCurrency());
		txn.setTransactionType(request.getCreditDebit());
		Category c = txnServiceImplStatic.fetchCategoryByName(request.getCategory());
		txn.setCategoryObj(c);
		if(c != null) {
			txn.setSubCategoryObj(txnServiceImplStatic.fetchSubCategoryByNameAndCategoryId(request.getSubcategory(), c.getId()));
		}
		txn.setAmount(WebUtil.formatAmount(request.getAmount()));
		txn.setMerchantName(request.getMerchantName());
		txn.setDescription(request.getMerchantName());
		txn.setTransDate(request.getTransactionDate());
		txn.setStatus(true);
		txn.setConfirmed(true);
		account.getTransactions().add(txn);
		txnServiceImplStatic.saveTransaction(txn);
		txnServiceImplStatic.saveAccount(account);
		return txn;
	}

}
