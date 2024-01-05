package com.pisight.pimoney.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.ParserResponse;
import com.pisight.pimoney.processor.ProcessorUtil;
import com.pisight.pimoney.service.K2ServiceImpl;

@Service
public class WebUtil {

	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

	private static K2ServiceImpl k2ServiceImplStatic = null;

	@PostConstruct
	public void init() {
		k2ServiceImplStatic = k2ServiceImpl;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getResponse(int errorCode, String responseStatus, String responseMessage) {
		JSONObject response = new JSONObject();
		response.put(Constants.ERROR_CODE_STRING, errorCode);
		response.put(Constants.RESPONSE_STATUS_STRING, responseStatus);
		response.put(Constants.RESPONSE_MESSAGE_STRING, responseMessage);
		return response;
	}

	/**
	 * This method converts the given date String to the Pimoney Format. This
	 * method requires format of the date string passed.
	 * 
	 * @param oldDate OldDate
	 * @param format Format
	 * @return formated date string
	 * @throws ParseException {@link ParseException}
	 */
	public static String convertToPimoneyDate(String oldDate, String format) throws ParseException {

		ScriptLogger.writeInfo(
				"inside convertDateStringToPimoneyFormat with date string :: " + oldDate + " and format :: " + format);

		if (StringUtils.isEmpty(oldDate)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = sdf.parse(oldDate);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * Watson is always here for you even if you don't have the date format with
	 * you. This method also converts the given date String to the Pimoney
	 * Format. It does not requires date format for the date String. It matches
	 * with the predefined set of format defined in the Constants.java class
	 * 
	 * @param dateString Date String
	 * @return formated date string
	 * @throws Exception {@link Exception}
	 */
	public static String convertToPimoneyDate(String dateString) throws Exception {

		ScriptLogger.writeInfo("inside convertDateStringToPimoneyFormat with only date string :: " + dateString);
		Date newDate = null;
		int failureCount = 0;

		if (StringUtils.isEmpty(dateString)) {
			return null;
		}
		SimpleDateFormat sdf = null;
		if (dateString != null) {
			for (String parse : Constants.getDateFormatList()) {
				sdf = new SimpleDateFormat(parse);
				try {
					newDate = sdf.parse(dateString);
					break;
				} catch (ParseException e) {
					failureCount++;
				}
			}
		}

		if (failureCount == Constants.getDateFormatList().size()) {
			ScriptLogger.writeError("Date Format for the date string " + dateString + " is not in the supported dateList");
			throw new Exception("Date Format for the date string " + dateString + " is not in the supported dateList");
		}

		if (newDate != null) {
			sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_DASH_MM_DASH_DD);
			return sdf.format(newDate);
		} else {
			ScriptLogger.writeError("error in parsing date " + dateString);
			throw new Exception("error in parsing date");
		}

	}

	public static String formatDate1(String dateString, String format) throws ParseException {

		ScriptLogger.writeInfo("converting data string to yyyyMMdd");
		ScriptLogger.writeInfo("inside formatDate1 with date string :: " + dateString + " and format :: " + format);
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Date date = sdf.parse(dateString);

		sdf = new SimpleDateFormat("yyyyMMdd");

		return sdf.format(date);
	}

	public static String formatAmount(String amount) {
		if (amount == null) {
			return null;
		}

		return amount.replace(",", "");
	}

	public static String getConfigValue(String key, String value) {
		return k2ServiceImplStatic.getConfigurationValue(key, value);
	}

	public static JSONObject convertToJSONObject(String string)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(string, JSONObject.class);
	}

	public static String convertToJSON(Object object) throws JsonProcessingException {
		ScriptLogger.writeInfo("converting to JSON");
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	public static Date convertToDate(String date) throws ParseException {

		if (StringUtils.isEmpty(date) || date.equalsIgnoreCase("tbd")) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_MM_DD);

		return sdf.parse(date);

	}

	public static void writeFile(String filepath, String output) throws FileNotFoundException, IOException {
		FileWriter ofstream = new FileWriter(filepath);
		try (BufferedWriter out = new BufferedWriter(ofstream)) {
			out.write(output);
		}
	}

	/*
	 * public static TransactionCategoryDTO fetchCategoryDetails(UUID id) {
	 * return k2ServiceImplStatic.fetchCategoryDetails(id); }
	 */

	public static Date removeTimeStatmp(Date date) throws ParseException {

		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATEFORMAT_YYYY_DASH_MM_DASH_DD);
		return sdf.parse(sdf.format(date));
	}

	public static ParserResponse getParserResponseFromObjectFile(ParserResponse pr) throws IOException {
		ScriptLogger.writeInfo("inside getParserResponseFromObjectFile");
		ScriptLogger.writeInfo("isFromObject => " + pr.isFromObject());
		if (pr.isFromObject()) {
			String randomString = RandomStringUtils.randomAlphanumeric(12);
			String objectPath = Constants.STATEMENT_HOME + randomString + "/object/";
			String path = objectPath + "account.ser";
			File file = new File(path);
			File directory = new File(objectPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			byte[] decodeByte = Base64.decodeBase64(pr.getObjectByte());
			writeFile(file, decodeByte);
			FileInputStream fis = null;
			ObjectInputStream in = null;
			try {
				fis = new FileInputStream(path);
				try {
					in = new ObjectInputStream(fis);
					pr = (ParserResponse) in.readObject();
				}catch (Exception e) {
					ScriptLogger.writeError("Exception e  = ", e);
				}finally {
					if(in != null) {
						in.close();
					}
				}
			} catch (Exception ex) {
				ScriptLogger.writeError("Error in getParserResponseFromObjectFile => ", ex);
			} finally {
				file = new File(path);
				if (file != null && file.exists()) {
					if(!file.delete()) {
						ScriptLogger.writeInfo("File not Deleted");
					}
				}
				if (directory != null && directory.exists()) {
					if(!directory.delete()){
						ScriptLogger.writeInfo("File not Deleted");
					}
				}
				if(fis != null) {
					fis.close();
				}
			}
		}
		return pr;
	}

	public static ParserResponse getParserResponseObjectByte(ParserResponse parserResponse) throws Exception {

		ScriptLogger.writeInfo("inside getParserResponseObjectByte");
		ScriptLogger.writeInfo("isFromObject => " + parserResponse.isFromObject());

		if (parserResponse.isFromObject()) {
			String filename = writeOject(parserResponse);

			ScriptLogger.writeInfo("Object path => " + filename);
			parserResponse.setFromObject(true);
			parserResponse.getBankAccounts().clear();
			parserResponse.getCardAccounts().clear();
			parserResponse.getLoanAccounts().clear();
			parserResponse.getFdAccounts().clear();
			parserResponse.getInvestmentAccounts().clear();
			parserResponse.getGenericContainers().clear();
			parserResponse.setXmlAsset(null);
			parserResponse.setXmlTrans(null);
			parserResponse.setXmlAssetPSX(null);
			parserResponse.setXmlAssetSCX(null);
			parserResponse.setXlsxByteAssets(null);
			parserResponse.setXlsxByteTrans(null);
			parserResponse.setObjectByte(ProcessorUtil.getBase64(filename));
			filename = filename.substring(0, filename.lastIndexOf("/object"));
			deleteDirectory(filename, true);
		}

		return parserResponse;
	}

	private static void writeFile(File file, byte[] decodeByte) throws IOException {
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(decodeByte);
		}catch (Exception e) {
			ScriptLogger.writeError("Exception e = ", e);
		}finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	private static String writeOject(ParserResponse response) throws Exception {

		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		String randomString = RandomStringUtils.randomAlphanumeric(12);
		String objectPath = System.getProperty("user.home") + "/userfiles/" + randomString + "/object/";
		String filename = objectPath + "account.ser";

		File directory = new File(objectPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		try {
			fos = new FileOutputStream(filename);
			try {
				out = new ObjectOutputStream(fos);
				out.writeObject(response);
			}catch (Exception e) {
				ScriptLogger.writeError("Exception e =", e);
			}finally {
				if(out != null) {
					out.close();
				}
			}
		} catch (Exception ex) {
			ScriptLogger.writeError("Exception e =", ex);
			throw ex;
		}finally {
			if(fos != null) {
				fos.close();
			}
		}
		return filename;
	}

	public static Date getTimeZoneCurrentTime(String timezone) throws ParseException {

		Calendar cal = Calendar.getInstance();
		ScriptLogger.writeInfo("Current Time = ");
		ScriptLogger.writeInfo(cal.getTime());
		
		TimeZone tz  = TimeZone.getDefault() ;
		TimeZone.setDefault(TimeZone.getTimeZone(timezone));

		Date convetedTime = cal.getTime();
		ScriptLogger.writeInfo("Converted Time = ");
		ScriptLogger.writeInfo(convetedTime);
		TimeZone.setDefault(tz); // Set to Default Time 
		
		return convetedTime;
	}
	
	/**
	 * This method deletes given directory. If the directory is empty then it will delete the directory right away
	 * but if it is not empty then make sure the <b>forceDelete</b> flag is true otherwise it will not delete the directory and will return false.
	 * @param path Directory Path
	 * @param forceDelete ForceDelete Flag
	 * @return <b>true if directory deleted otherwise false</b>
	 */
	public static boolean deleteDirectory(String path, boolean forceDelete) {
		
		ScriptLogger.writeInfo("directory to be deleted => " + path + " with forceDelete flag as " + forceDelete);
		
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		
		File directory = new File(path);
		if (directory != null && directory.isDirectory()) {
			File[] files = directory.listFiles();
			ScriptLogger.writeInfo("Total number of files in the directory is " + files.length);
			if (files.length > 0 && !forceDelete) {
				return false;
			}
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isDirectory()) {
					boolean tempResult = deleteDirectory(file.getAbsolutePath(), forceDelete);
					if (!tempResult) {
						return false;
					}
				} else {
					if(!file.delete()) {
						ScriptLogger.writeInfo("File Not Deleted...");
					}
				}
			}
			if(!directory.delete()) {
				ScriptLogger.writeInfo("Directory Not Deleted...");
			}
			return true;
		}
		return false;
	}
}
