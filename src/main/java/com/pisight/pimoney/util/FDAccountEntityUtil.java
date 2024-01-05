package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.pisight.pimoney.models.FixedDepositAccount;
import com.pisight.pimoney.repository.entities.FixedDepositAccountEntity;
import com.pisight.pimoney.repository.entities.FixedDepositAccountHistoryEntity;

public class FDAccountEntityUtil {

	@SuppressWarnings("rawtypes")
	public static void copyToFixedDepositAccountEntity(FixedDepositAccount account, FixedDepositAccountEntity entity) 
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo("copyToFixedDepositAccountEntity convert");
		Method[] methods = ArrayUtils.addAll(account.getClass().getDeclaredMethods() , account.getClass().getSuperclass().getDeclaredMethods());
		for (Method method : methods) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || methodName.equals("getAccountNumber") || methodName.equals("getProperty")
					|| (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
				continue;
			}
			
			ScriptLogger.writeInfo("source method name -> " + methodName);
			ScriptLogger.writeInfo("source method type -> " + srcType);
			
			Object value = method.invoke(account);

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
				ScriptLogger.writeInfo("");
				ScriptLogger.writeInfo("");

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
					Method dstMethod1 = entity.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(entity,  (String) value);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void copyToFixedDepositAccountEntity(FixedDepositAccountHistoryEntity account, FixedDepositAccountEntity entity) 
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo("copyToFixedDepositAccountEntity convert");
		Method[] methods = ArrayUtils.addAll(account.getClass().getDeclaredMethods() , account.getClass().getSuperclass().getDeclaredMethods());
		for (Method method : methods) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || methodName.equals("getAccountNumber") || methodName.equals("getProperty")
					|| (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
				continue;
			}
			
			ScriptLogger.writeInfo("source method name -> " + methodName);
			ScriptLogger.writeInfo("source method type -> " + srcType);
			
			Object value = method.invoke(account);

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
				ScriptLogger.writeInfo("");
				ScriptLogger.writeInfo("");

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
					try {
						Method dstMethod1 = entity.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(entity,  (String) value);
					}catch(Exception e) {
						ScriptLogger.writeInfo("method exception for '" + methodName + "'");
					}
				}
			}
		}
	}
	
	public static FixedDepositAccountEntity toFixedDepositAccountEntity(FixedDepositAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		FixedDepositAccountEntity entity = new FixedDepositAccountEntity();

		if(account == null) {
			return null;
		}

		copyToFixedDepositAccountEntity(account, entity);
		entity.setAccountNumber(EntityUtil.maskString(account.getAccountNumber()));

		return entity;
	}
	
	
	public static FixedDepositAccountEntity toFixedDepositAccountEntityDC(FixedDepositAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		FixedDepositAccountEntity entity = toFixedDepositAccountEntity(account);

		return entity;
	}

	@SuppressWarnings("rawtypes")
	public static void copyToFixedDepositAccount(FixedDepositAccountEntity entity, FixedDepositAccount account) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo("copyToInvestmentAccount convert");
		for (Method method : entity.getClass().getDeclaredMethods()) {

			String methodName = method.getName();
			Class srcType = method.getReturnType();

			if(methodName.startsWith("set") || (!srcType.equals(String.class) && !srcType.equals(Boolean.class) && !srcType.equals(Date.class))) {
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
					dstMethod = account.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = account.getClass().getMethod("is"+methodName);
					}catch(NoSuchMethodException eInner) {
						ScriptLogger.writeInfo("Method is entity specific");
						continue;
					}
				}
				Class dstType = dstMethod.getReturnType();

				ScriptLogger.writeInfo("dest method name -> " + dstMethod);
				ScriptLogger.writeInfo("dest method type -> " + dstType);
				ScriptLogger.writeInfo("");
				ScriptLogger.writeInfo("");

				if(srcType.equals(Date.class)) {
					String date = EntityUtil.convertToDateString((Date) value);
					Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(account, date);
				}
				else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = account.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(account, (Boolean) value);
					}
					else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(account,  tempVal);
					}
				}
				else {
					Method dstMethod1 = account.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(account,  (String) value);
				}
			}
		}
	}

}
