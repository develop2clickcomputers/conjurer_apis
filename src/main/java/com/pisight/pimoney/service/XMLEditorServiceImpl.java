package com.pisight.pimoney.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.DocumentRequest;
import com.pisight.pimoney.dto.XMLDTO;
import com.pisight.pimoney.repository.entities.FileRepositoryEntity;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class XMLEditorServiceImpl {

	@Autowired
	private K2ServiceImpl k2ServiceImpl;
	
	/**
	 * This method is used to get XML File in XML View Page
	 * @param request {@link DocumentRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getXMLFileContent(DocumentRequest request) {

		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "";
		String fileAsString = null;
		try {
			String repoId = request.getRepoId();
			String fileName = request.getName();

			if(StringUtils.isEmpty(fileName)) {
				throw new Exception("Empty File name");
			}

			FileRepositoryEntity repo = k2ServiceImpl.fetchFileRepositoryById(repoId);
			if(repo != null) {

				String filePath = repo.getFilePath();
				if(StringUtils.isNotEmpty(filePath)) {
					File dir = new File(filePath);
					if(dir != null) {
						String[]entries = dir.list();
						if(entries != null) {
							for(String s: entries){
								File currentFile = new File(dir.getPath(),s);
								if(fileName.equals(currentFile.getName())) {
									Path path = Paths.get(dir.getPath(),s);
									InputStream is = null;
									BufferedReader buf = null;
									try {
										is = new FileInputStream(path.toString());
										try {
											buf = new BufferedReader(new InputStreamReader(is)); 
											String line = buf.readLine(); 
											StringBuilder sb = new StringBuilder(); 
											while(line != null){ 
												sb.append(line); 
												line = buf.readLine(); 
												} 
											if(buf != null) {
												buf.close();
											}
											fileAsString = sb.toString();
										}catch (Exception e) {
											// TODO: handle exception
											ScriptLogger.writeError("Exception e = ", e);
										}finally {
											if(buf != null) {
												buf.close();
											}
										}
									}catch (Exception e) {
										// TODO: handle exception
										ScriptLogger.writeError("Exception e = ", e);
									}finally {
										if(is != null) {
											is.close();
										}
									}
								}
							}
						}
					}
				}
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage = "file fetched";

		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in getXMLFileContent", e);
		}

		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("xmlString", fileAsString);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}

	/**
	 * This is used to update XML file in XML Viewer Page
	 * @param request {@link XMLDTO}
	 * @return JSONObject {@link JSONObject}
	 */
	public JSONObject updateXMLFile(XMLDTO request) {
		
		long start = System.currentTimeMillis();
		JSONObject response = new JSONObject();
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage = "Error in updating XML";
		
		try {
			boolean result = k2ServiceImpl.updateXMLFile(request.getRepoId(), request.getFileName(), request.getXml());
			if(result) {
				errorCode = 0;
				responseStatus = Constants.SUCCESS;
				responseMessage = "XML Saved Successfully";
			}
		}catch(Exception e) {
			responseMessage = "Error while processing";
			ScriptLogger.writeError("Error in saveXMLFile", e);
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		ScriptLogger.writeInfo("Total Time Taken -> " + (System.currentTimeMillis()-start) + " ms");
		return response;
	}
}
