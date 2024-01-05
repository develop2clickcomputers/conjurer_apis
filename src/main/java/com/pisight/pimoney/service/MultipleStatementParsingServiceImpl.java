package com.pisight.pimoney.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dao.K2DAO;
import com.pisight.pimoney.dto.BatchFileDetailDTO;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.FileDTO;
import com.pisight.pimoney.dto.MultipleStatementParsingDTO;
import com.pisight.pimoney.dto.ParserResponse;
import com.pisight.pimoney.processor.ProcessorUtil;
import com.pisight.pimoney.repository.entities.BatchFileDetail;
import com.pisight.pimoney.repository.entities.ManualInstitution;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.PiHttpClient;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class MultipleStatementParsingServiceImpl {

	@Autowired
	private K2DAO k2DAO = null;

	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

	/**
	 * This method is used to parser and store output of pdf statements.
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 * @throws IOException {@link IOException}
	 */
	@SuppressWarnings("unused")
	public JSONObject multipleStatementParsing(MultipleStatementParsingDTO request) throws IOException {
		// TODO Auto-generated method stub

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";
		JSONObject response = new JSONObject();

		UUID userId = request.getUserId();

		Random random = new Random();
		int randomNumber = random.nextInt(999999 - 100000) + 100000;
		ScriptLogger.writeInfo("Random Number = " + randomNumber);

		try {

			// For Batch Id
			String batchId = "Batch_" + randomNumber;
			ScriptLogger.writeInfo("Batch Id = " + batchId);

			String sourceFolder = System.getProperty("user.home") + "/source/" + userId + "/";
			ScriptLogger.writeInfo("Source Folder = " + sourceFolder);

			File dir = new File(sourceFolder);
			if (dir != null) {
				String[] entries = dir.list();
				if (entries != null) {
					for (String filename : entries) {
						if (filename.contains(".pdf") || filename.contains(".PDF")) {
							Path path = Paths.get(dir.getPath(), filename);
							ScriptLogger.writeInfo("File Path = " + path.toString());

							// For Convert to Base64 and Retrive File Information
							String base64 = ProcessorUtil.getBase64(path.toString(), false);

							String[] filenameArray = filename.split("_");
							ScriptLogger.writeInfo("filenameArray === length ==>>" + filenameArray.length);
							String countryCode = "", institutionName = "", fileIdentifier = "";
							if(filenameArray.length > 2) {
								countryCode = filenameArray[0];
								institutionName = filenameArray[1];
								fileIdentifier = filenameArray[2];
								
								ManualInstitution inst = k2DAO.fetchPDFInstitutionsByInstitutionNameAndCountryCode(
										institutionName.toUpperCase(), countryCode.toUpperCase());
								institutionName = inst.getName();

							}else {
								institutionName = filenameArray[0];
								fileIdentifier = filenameArray[1];
								
								List<ManualInstitution> inst = k2DAO.fetchPDFInstitutionsByInstitutionName(institutionName.toUpperCase());
								institutionName = inst.get(0).getName();
							}
							
							ScriptLogger.writeInfo("Country Code = " + countryCode);
							ScriptLogger.writeInfo("Instituton Name = " + institutionName);

							DocumentRequest doc = new DocumentRequest();
							doc.setUserId(userId.toString());
							doc.setLocale(countryCode);
							doc.setName(institutionName);
							doc.setDocByte(base64);
							doc.setType("pdf");
							doc.setPswd("");
							doc.setUserId(request.getUserId().toString());
							doc.setHeaderGroup("taurus");
							doc.setOutputFileGroup("taurus");
							doc.setBatchId(batchId);

							// Statement Parsing Api Call
							response = statementParsing(doc);
							ScriptLogger.writeInfo("***************Statement Parser Response*************");
							ObjectMapper mapper = new ObjectMapper();
							ScriptLogger.writeInfo(mapper.writeValueAsString(response));

							BatchFileDetail batchFileDetail = k2DAO.findByBatchId(userId, batchId, institutionName);
							if (batchFileDetail != null) {
								if (response.get(Constants.RESPONSE_STATUS_STRING).equals(Constants.SUCCESS)) {
									batchFileDetail.setStatus(Constants.SUCCESS);
								} else {
									batchFileDetail.setStatus(Constants.FAILED);
								}
								k2DAO.saveBatchFileDetail(batchFileDetail);
							}

						} else {
							ScriptLogger.writeInfo("File not Parsing = " + filename);
						}
					}

					String userFolder = System.getProperty("user.home") + "/source/" + userId;
					FileUtils.deleteDirectory(new File(userFolder));

					errorCode = 0;
					responseMessage = "File Stored Successfully";
					responseStatus = Constants.SUCCESS;

				} else {
					responseMessage = "Folder was emplty....";
					return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
				}
			} else {
				responseMessage = "Upload File before Batch Upload";
				return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			}
		}

		catch (ArrayIndexOutOfBoundsException e) {
			ScriptLogger.writeInfo("Error = ", e);
			responseMessage = "Please check File Format / File name format (It should be this way : CountryCode_InstituteName_Identifier)";
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		} catch (Exception e) {
			ScriptLogger.writeInfo("Exception e = ", e);
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}finally {
			try {
				String userFolder = System.getProperty("user.home") + "/source/" + userId;
				FileUtils.deleteDirectory(new File(userFolder));
			}catch (Exception e) {
				ScriptLogger.writeInfo("Exception e = ", e);
			}
			
		}
		return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
	}

	private JSONObject statementParsing(DocumentRequest doc) throws Exception {

		ScriptLogger.writeInfo("Statement Parsing Function Called...");
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		JSONObject response = new JSONObject();

		String data = WebUtil.convertToJSON(doc);
		// ScriptLogger.writeInfo("data = " + data);

		String url = k2ServiceImpl.getConfigurationValue(Constants.PARSER_ENGINE, Constants.PARSER_ENGINE_URL)
				+ "parse";
		ScriptLogger.writeInfo("Statement Parsing URL = " + url);

		PiHttpClient client = new PiHttpClient(url, PiHttpClient.REQUEST_TYPE_POST);
		client.setContentType(PiHttpClient.CONTENT_TYPE_JSON);
		client.setStringEntity(data);

		String result = client.getResponseForPostRequest();

		ObjectMapper mapper = new ObjectMapper();
		ParserResponse parserResponse = mapper.readValue(result, ParserResponse.class);

		if (parserResponse.getErrorCode() == 0) {

			// ScriptLogger.writeInfo("Parser Response = " + result);
			ScriptLogger.writeInfo("Error Code = " + parserResponse.getErrorCode());
			response = storeParserResoponse(parserResponse, doc);

		} else {
			errorCode = parserResponse.getErrorCode();
			responseStatus = parserResponse.getStatus();
			responseMessage = parserResponse.getMessage();
			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		return response;
	}

	private JSONObject storeParserResoponse(ParserResponse parserResponse, DocumentRequest doc)
			throws FileNotFoundException, IOException, InterruptedException {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {
			
			parserResponse = WebUtil.getParserResponseFromObjectFile(parserResponse);

			// Store Details in BatchFileDetail
			BatchFileDetail batchFileDetail = storeBatchFileDetail(parserResponse, doc);

			ScriptLogger.writeInfo("Store Parser Response Called....");
			String xmlTrans = parserResponse.getXmlTrans();
			String xmlAsset = parserResponse.getXmlAsset();
			String xmlSCX = parserResponse.getXmlAssetSCX();
			String xmlPSX = parserResponse.getXmlAssetPSX();
			String xlsxByteTrans = parserResponse.getXlsxByteTrans();
			String xlsxByteAssets = parserResponse.getXlsxByteAssets();

			String instName = parserResponse.getInstitutionName();

			if (StringUtils.isNotEmpty(xmlTrans)) {
				ScriptLogger.writeInfo("Content for trx file....");
				String filename = "xx-" + parserResponse.getStatementDate() + "-trn-" + instName + ".trx";
				storeXMLFile(parserResponse, doc, xmlTrans, filename);
			}
			if (StringUtils.isNotEmpty(xmlAsset)) {
				ScriptLogger.writeInfo("Content for psx file....");
				String filename = "xx-" + parserResponse.getStatementDate() + "-psn-" + instName + ".psx";
				storeXMLFile(parserResponse, doc, xmlAsset, filename);
			}
			if (StringUtils.isNotEmpty(xmlSCX)) {
				ScriptLogger.writeInfo("Content for scx file....");
				String filename = "xx-" + parserResponse.getStatementDate() + "-sec-" + instName + ".scx";
				storeXMLFile(parserResponse, doc, xmlSCX, filename);
			}
			if (StringUtils.isNotEmpty(xmlPSX)) {
				ScriptLogger.writeInfo("Content for init trx file....");
				String filename = "xx-" + parserResponse.getStatementDate() + "-trn-init-" + instName + ".trx";
				storeXMLFile(parserResponse, doc, xmlPSX, filename);
			}
			if (StringUtils.isNotEmpty(xlsxByteTrans)) {
				ScriptLogger.writeInfo("Content for xlsx file....");
				String filename = "xx-" + parserResponse.getStatementDate() + "-trn-" + instName + ".xlsx";
				storeXMLFile(parserResponse, doc, xlsxByteTrans, filename);
			}
			if (StringUtils.isNotEmpty(xlsxByteAssets)) {
				ScriptLogger.writeInfo("Content for xlsx Asset file....");
				String filename = instName + ".xlsx";
				storeXMLFile(parserResponse, doc, xlsxByteAssets, filename);
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = Constants.SUCCESS;

		} catch (Exception e) {
			ScriptLogger.writeInfo("Exception e = " , e);
		}
		return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
	}

	public JSONObject storeXMLFile(ParserResponse parserResponse, DocumentRequest doc, String content, String fileName)
			throws IOException, InterruptedException {

		ScriptLogger.writeInfo("Store XML File Called..");

		String userId = doc.getUserId();
		String batchId = doc.getBatchId();

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something Went Wrong";

		try {

			String instName = parserResponse.getInstitutionName();
			String portfolioNumber = parserResponse.getPortfolioNumber();
			if(StringUtils.isEmpty(portfolioNumber)) {
				portfolioNumber = "Portfolio";
			}
			portfolioNumber = portfolioNumber.replace("*", "").replace("/", "-");

			String stmtType = parserResponse.getStatementType();
			ScriptLogger.writeInfo("Statement Type = " + stmtType);

			String stmtCurrency = parserResponse.getStatementCurrency();
			if (StringUtils.isEmpty(stmtCurrency)) {
				stmtCurrency = "Currency";
			}

			if (stmtType.equals(Constants.STATEMENT_TYPE_DEFAULT)) {
			} else {
				portfolioNumber = portfolioNumber + "_" + stmtType;
			}

			String userFolder = System.getProperty("user.home") + "/destination/" + userId;
			File directory = new File(userFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String batchIdFolder = System.getProperty("user.home") + "/destination/" + userId + "/" + batchId;
			directory = new File(batchIdFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String instNameFolder = System.getProperty("user.home") + "/destination/" + userId + "/" + batchId + "/"
					+ instName;
			directory = new File(instNameFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String portfolioNumberFolder = System.getProperty("user.home") + "/destination/" + userId + "/" + batchId
					+ "/" + instName + "/" + portfolioNumber;
			directory = new File(portfolioNumberFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String currencyFolder = System.getProperty("user.home") + "/destination/" + userId + "/" + batchId + "/"
					+ instName + "/" + portfolioNumber + "/" + stmtCurrency;
			directory = new File(currencyFolder);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			String filePath = currencyFolder + "/" + fileName;
			WebUtil.writeFile(filePath, content);

			errorCode = 0;
			responseMessage = Constants.SUCCESS;
			responseStatus = Constants.SUCCESS;

		} catch (Exception e) {
			ScriptLogger.writeInfo("Exception e = " , e);
		}
		return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
	}

	private BatchFileDetail storeBatchFileDetail(ParserResponse parserResponse, DocumentRequest doc) {
		// TODO Auto-generated method stub

		ScriptLogger.writeInfo("TBD Found = " + parserResponse.isTBDFound());

		String userId = doc.getUserId();

		String stmtType = parserResponse.getStatementType();
		String portfolioNumber = parserResponse.getPortfolioNumber();
		String stmtCurrency = parserResponse.getStatementCurrency();
		if(StringUtils.isNotEmpty(portfolioNumber)) {
			portfolioNumber = portfolioNumber.replace("*", "").replace("/", "-");	
		}else {
			portfolioNumber = "Portfolio";
		}
		
		if(StringUtils.isEmpty(stmtCurrency)) {
			stmtCurrency = "Currency";
		}
		
		if (stmtType.equals(Constants.STATEMENT_TYPE_DEFAULT)) {
		} else {
			portfolioNumber = portfolioNumber + "_" + stmtType;
		}

		String filePath = "destination/" + userId + "/" + doc.getBatchId() + "/" + parserResponse.getInstitutionName()
				+ "/" + portfolioNumber + "/" + stmtCurrency;

		ScriptLogger.writeInfo("UserID = " + userId);
		User user = k2ServiceImpl.fetchUserById(UUID.fromString(userId));

		BatchFileDetail details = new BatchFileDetail();
		details.setUser(user);
		details.setBatchId(doc.getBatchId());
		details.setInstitutionName(parserResponse.getInstitutionName());
		details.setPortfolioNumber(portfolioNumber);
		details.setCurrency(stmtCurrency);
		details.setStatus(Constants.PROCESSING);
		details.setTbdFound(parserResponse.isTBDFound());
		details.setFilePath(filePath);

		return k2DAO.saveBatchFileDetail(details);
	}

	/**
	 * This method is used to get batch file uploads details
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getBatchFileDetail(MultipleStatementParsingDTO request) {
		// TODO Auto-generated method stub

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something went wrong";

		JSONObject response = new JSONObject();

		try {

			UUID userId = request.getUserId();

			List<BatchFileDetail> details = k2DAO.fetchBatchByuserId(userId);

			List<BatchFileDetailDTO> data = new ArrayList<BatchFileDetailDTO>();
			for (BatchFileDetail batchFileDetail : details) {

				BatchFileDetailDTO detailDTO = new BatchFileDetailDTO();
				detailDTO.setId(batchFileDetail.getId());
				detailDTO.setBatchId(batchFileDetail.getBatchId());
				detailDTO.setInstitutionName(batchFileDetail.getInstitutionName());
				detailDTO.setPortfolioNumber(batchFileDetail.getPortfolioNumber());
				detailDTO.setCurrency(batchFileDetail.getCurrency());
				detailDTO.setStatus(batchFileDetail.getStatus());
				detailDTO.setTbdFound(batchFileDetail.isTbdFound());
				detailDTO.setFilePath(batchFileDetail.getFilePath());
				data.add(detailDTO);
			}
			errorCode = 0;
			responseMessage = Constants.SUCCESS;
			responseStatus = Constants.SUCCESS;

			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			response.put("data", data);

		} catch (Exception e) {
			ScriptLogger.writeInfo("Exception e = ", e);
			responseMessage = "Something Went Wrong";
			response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		return response;
	}

	/**
	 * This method is used to get filenames of directory
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 * @throws IOException {@link IOException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getFileDetail(MultipleStatementParsingDTO request) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something went wrong";
		JSONObject response = new JSONObject();

		List<JSONObject> data = new ArrayList<JSONObject>();

		String filePath = System.getProperty("user.home") + "/" + request.getFilePath();
		ScriptLogger.writeInfo("File Path = " + filePath);

		try {

			File folder = new File(filePath);
			File[] listOfFiles = folder.listFiles();
			ScriptLogger.writeInfo("File Size = " + listOfFiles.length);
			for (int i = 0; i < listOfFiles.length; i++) {
				JSONObject dataObject = new JSONObject();
				if (listOfFiles[i].isFile()) {
					ScriptLogger.writeInfo("File = " + listOfFiles[i].getName());
					dataObject.put("name", listOfFiles[i].getName());
				}
				data.add(dataObject);
			}

			errorCode = 0;
			responseMessage = Constants.SUCCESS;
			responseStatus = Constants.SUCCESS;

		} catch (Exception e) {
			ScriptLogger.writeError("File not Found e = ", e);
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", data);
		return response;
	}

	/**
	 * This method is used to get file for specific file location
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 * @throws IOException {@link IOException}
	 * @throws InterruptedException {@link InterruptedException}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getFile(MultipleStatementParsingDTO request) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something went wrong";

		JSONObject response = new JSONObject();
		byte[] docByte = null;

		String filePath = request.getFilePath();
		String fileName = request.getFileName();

		try {

			String path = System.getProperty("user.home") + "/" + filePath + "/" + fileName;

			docByte = Files.readAllBytes(Paths.get(path));

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = Constants.SUCCESS;

		} catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeInfo("Exception e = " , e);
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", docByte);
		return response;
	}

	/** 
	 * This method is used to store PDF statement for batch files
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject storeStatement(MultipleStatementParsingDTO request) {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something went wrong";

		UUID userId = request.getUserId();
		List<FileDTO> files = request.getFiles();

		String userFolder = System.getProperty("user.home") + "/source/" + userId;
		File directory = new File(userFolder);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		String sourceFolder = System.getProperty("user.home") + "/source/" + userId + "/";
		ScriptLogger.writeInfo("Source Folder = " + sourceFolder);

		try {

			ScriptLogger.writeInfo("Number of File = " + files.size());
			for (FileDTO file : files) {

				String fileName = file.getFileName();
				ScriptLogger.writeInfo("File Name = " + fileName);

				File f = new File(sourceFolder + fileName);
				byte[] byteData = Base64.decodeBase64(file.getFile());

				FileOutputStream fos = new FileOutputStream(f);
				fos.write(byteData);
				fos.close();
			}

			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = Constants.SUCCESS;

		} catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeInfo("Exception e = " , e);
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
	}

	/**
	 * This method is used to delete batch file report and files
	 * @param request {@link MultipleStatementParsingDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject deleteBatchFileDetail(MultipleStatementParsingDTO request) {

		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Something went wrong";

		JSONObject response = new JSONObject();

		UUID userId = request.getUserId();
		String batchId = request.getBatchId();

		try {
			
			User user = k2ServiceImpl.fetchUserById(userId);
			if(user != null) {
				
				List<BatchFileDetail> batchFileDetails = k2DAO.fetchBatchByuserIdAndBatchId(userId, batchId);
				ScriptLogger.writeInfo("Number of Institution Deleted = " + batchFileDetails.size());
				for (BatchFileDetail batchFileDetail : batchFileDetails) {
					
					String deleteFolderPath = System.getProperty("user.home") + "/destination/" + user.getId() + "/" + batchFileDetail.getBatchId();
					ScriptLogger.writeInfo("Delete Folder Path = " + deleteFolderPath);
					FileUtils.deleteDirectory(new File(deleteFolderPath));
					
					k2DAO.deleteBatchFileDetail(batchFileDetail);
				}

				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "Successfully Deleted";
			}
		} catch (Exception e) {
			// TODO: handle exception
			ScriptLogger.writeInfo("Exception e = " , e);
			return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		return response;
	}
}
