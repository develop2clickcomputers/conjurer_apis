package com.pisight.pimoney.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.models.HoldingAsset;
import com.pisight.pimoney.repository.entities.HoldingAssetEntity;

public class TestUtil {
	
	
	@SuppressWarnings("rawtypes")
	public static void convert(HoldingAssetEntity entity, HoldingAsset asset) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
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
					dstMethod = asset.getClass().getMethod("get"+methodName);
				}catch(NoSuchMethodException e) {
					try {
						dstMethod = asset.getClass().getMethod("is"+methodName);
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
					String date = convertToDateString((Date) value);
					Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(asset, date);
					
				}else if(srcType.equals(Boolean.TYPE)) {
					if(dstType.equals(Boolean.TYPE)) {
						Method dstMethod1 = asset.getClass().getMethod("set"+methodName, Boolean.class);
						dstMethod1.invoke(asset, (Boolean) value);
					}else {
						String tempVal = "N";
						if((Boolean) value) {
							tempVal = "Y";
						}
						Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
						dstMethod1.invoke(asset,  tempVal);
					}
				}else {
					Method dstMethod1 = asset.getClass().getMethod("set"+methodName, String.class);
					dstMethod1.invoke(asset,  (String) value);
				}
			}
		}
	}
	
	public static String convertToDateString(Date date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_MM_DD);
		return sdf.format(date);
	}
}
