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
import com.pisight.pimoney.models.CardAccount;
import com.pisight.pimoney.models.CardTransaction;
import com.pisight.pimoney.repository.entities.CardAccountEntity;
import com.pisight.pimoney.repository.entities.CardTransactionEntity;
import com.pisight.pimoney.repository.entities.Category;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.service.TransactionServiceImpl;

@Service
public class CardTransactionEntityUtil {

	@Autowired
	private TransactionServiceImpl txnServiceImpl = null;

	private static TransactionServiceImpl txnServiceImplStatic = null;

	@PostConstruct
	public void init() {
		txnServiceImplStatic = txnServiceImpl;
	}

	@SuppressWarnings("rawtypes")
	public static CardTransactionEntity copyToCardTransactionEntity(CardTransaction txn, CardTransactionEntity entity) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {

		if(txn == null) {
			return null;
		}

		System.out.println("toCardTransactionEntity convert");
		Method[] methods = ArrayUtils.addAll(txn.getClass().getDeclaredMethods(), txn.getClass().getSuperclass().getDeclaredMethods());
		for (Method method : methods) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
				continue;
			}
			Object value = method.invoke(txn);

			System.out.println("source method name -> " + methodName);
			System.out.println("source method type -> " + srcType);
			System.out.println("source value -> " + value);

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
						System.out.println("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				System.out.println("dest method name -> " + dstMethod);
				System.out.println("dest method type -> " + dstType);
				System.out.println();
				System.out.println();

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

	public static CardTransactionEntity toCardTransactionEntity(CardTransaction transaction) 
			throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		CardTransactionEntity entity = new CardTransactionEntity();

		if(transaction == null) {
			return null;
		}

		copyToCardTransactionEntity(transaction, entity);

		return entity;
	}

	public static List<CardTransactionEntity> handleCardTransactionEntitiesForOldAccount(
			CardAccount account, CardAccountEntity entity) throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		List<CardTransactionEntity> result = new ArrayList<CardTransactionEntity>();

		HashMap<String, CardTransactionEntity> map = new HashMap<String, CardTransactionEntity>();

		List<CardTransactionEntity> txnEntities = entity.getTransactions();
		List<CardTransaction> txns = account.getTransactions();

		for(CardTransactionEntity txnEntity: txnEntities) {
			map.put(txnEntity.getFingerprint(), txnEntity);
		}

		for(CardTransaction txn: txns) {
			CardTransactionEntity newTxnEntity = null;
			if(!map.containsKey(txn.getFingerprint())) {
				newTxnEntity = toCardTransactionEntity(txn);
				newTxnEntity.setStatus(true);
				result.add(newTxnEntity);
			}else {
				newTxnEntity = map.get(txn.getFingerprint());
				if(!newTxnEntity.isStatus()) {
					newTxnEntity.setStatus(true);
					result.add(newTxnEntity);
				}
			}
		}
		return result;
	}

	public static void updateCardTransactionEntity(CardTransactionEntity txn,
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
	public static void copyToCardTransaction(CardTransactionEntity entity,
			CardTransaction transaction) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		System.out.println("test convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set")) {
				continue;
			}
			Object value = method.invoke(entity);

			System.out.println("source method name -> " + methodName);
			System.out.println("source method type -> " + srcType);
			System.out.println("source value -> " + value);

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
						System.out.println("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				System.out.println("dest method name -> " + dstMethod);
				System.out.println("dest method type -> " + dstType);
				System.out.println();
				System.out.println();

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

	public static CardTransactionEntity addTransaction(AddTransactionDTO request) {

		CardAccountEntity account = (CardAccountEntity) txnServiceImplStatic.fetchManualAccount(request.getAccountType(), request.getUserId());
		ManualInstitution inst = txnServiceImplStatic.fetchManualInstitution(request.getInstitutionCode());

		account.setManualInstitution(inst);
		txnServiceImplStatic.saveAccount(account);
		CardTransactionEntity txn = new CardTransactionEntity();

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
		txn.setTransDate(request.getTransactionDate());
		txn.setDescription(request.getMerchantName());
		txn.setStatus(true);
		txn.setConfirmed(true);
		account.getTransactions().add(txn);
		txnServiceImplStatic.saveTransaction(txn);
		txnServiceImplStatic.saveAccount(account);
		return txn;
	}

}
