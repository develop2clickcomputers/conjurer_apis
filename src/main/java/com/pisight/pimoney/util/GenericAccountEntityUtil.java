package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

import com.pisight.pimoney.models.GenericAccount;
import com.pisight.pimoney.repository.entities.GenericAccountEntity;

public class GenericAccountEntityUtil {

	@SuppressWarnings("rawtypes")
	public static void copyToGenericAccountEntity(GenericAccount account, GenericAccountEntity entity) 
			throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		ScriptLogger.writeInfo("copyToGenericContainerAccountEntity convert");
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
	
	public static GenericAccountEntity toGenericAccountEntity(GenericAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		GenericAccountEntity entity = new GenericAccountEntity();

		if(account == null) {
			return null;
		}

		copyToGenericAccountEntity(account, entity);
		entity.setAccountNumber(EntityUtil.maskString(account.getAccountNumber()));
		entity.setAccountDataFields(account.getAccountDataFields());
		entity.setChildData(account.getChildData());

		return entity;
	}
	
	
	public static GenericAccountEntity toGenericAccountEntityDC(GenericAccount account) throws ParseException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		GenericAccountEntity entity = toGenericAccountEntity(account);

		return entity;
	}

	@SuppressWarnings("rawtypes")
	public static void copyToGenericAccount(GenericAccountEntity entity, GenericAccount account) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		ScriptLogger.writeInfo("copyToGenericAccount convert");
		account.setAccountDataFields(entity.getAccountDataFields());
		account.setChildData(entity.getChildData());
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
