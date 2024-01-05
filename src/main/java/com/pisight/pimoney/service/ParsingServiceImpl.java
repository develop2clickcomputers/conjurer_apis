package com.pisight.pimoney.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pisight.pimoney.constants.Constants;
import com.pisight.pimoney.dto.BudgetRequest;
import com.pisight.pimoney.dto.StatementParsingDetailDTO;
import com.pisight.pimoney.repository.StatementParsingDetailRepository;
import com.pisight.pimoney.repository.UserRepository;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.repository.entities.User;
import com.pisight.pimoney.util.ScriptLogger;
import com.pisight.pimoney.util.WebUtil;

@Service
public class ParsingServiceImpl {

	@Autowired
	private UserRepository userRepo = null;
	
	@Autowired
	private StatementParsingDetailRepository stmtParsingDetailRepo = null;
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;
	
	@Transactional
	public User fetchUserById(UUID id) {
		return userRepo.findById(id).get();
	}
	
	/**
	 * This method is used to display pdf statement report to admin. In this also check for admin access.
	 * @param request {@link BudgetRequest}
	 * @return JSONObject {@link JSONObject}
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getStatementParsingDetails(BudgetRequest request) {
		// TODO Auto-generated method stub
		int errorCode = 99;
		String responseStatus = Constants.FAILED;
		String responseMessage ="Error in getStatementParsingDetails";
		JSONObject response = new JSONObject();
		List<StatementParsingDetailDTO> details = new ArrayList<>();
		
		try {
			
			UUID userId = UUID.fromString(request.getUserId());
			User user = fetchUserById(userId);
			
			// For Page Display or not
			Boolean isAuthorized = checkAdminAccess(user.getUsername());
			
			if(!isAuthorized) {
				errorCode = 99;
				responseStatus = Constants.FAILED;
				responseMessage ="UnAuthorized";
				return WebUtil.getResponse(errorCode, responseStatus, responseMessage);
			}
			
			List<StatementParsingDetail> stmtParsingDetails = stmtParsingDetailRepo.findAll();
			for (StatementParsingDetail statementParsingDetail : stmtParsingDetails) {
				
				StatementParsingDetailDTO detail = new StatementParsingDetailDTO();
				detail.setName(statementParsingDetail.getUser().getUsername());
				detail.setCountry(statementParsingDetail.getCountryCode());
				detail.setInstitutionName(statementParsingDetail.getInstitutionName());
				detail.setStartTime(statementParsingDetail.getStartTime());
				detail.setEndTime(statementParsingDetail.getEndTime());
				detail.setStatus(statementParsingDetail.getStatus());
				detail.setTimezone(statementParsingDetail.getTimezone());
				details.add(detail);
			}
			errorCode = 0;
			responseStatus = Constants.SUCCESS;
			responseMessage ="Success";
			
		}catch (Exception e) {
			ScriptLogger.writeError("Error in getStatementParsingDetails", e);
			errorCode = 99;
			responseStatus = Constants.FAILED;
			responseMessage ="Error in getStatementParsingDetails";
		}
		response = WebUtil.getResponse(errorCode, responseStatus, responseMessage);
		response.put("data", details);
		return response;
	}
	
	private Boolean checkAdminAccess(String emailId) {
		
		ScriptLogger.writeInfo("Email Address = " + emailId);
		String adminUrl = k2ServiceImpl.getConfigurationValue(Constants.ADMIN_ACCESS_TYPE, Constants.ADMIN_EMAILS_KEY);
		String[] emails = adminUrl.split(",");
		
		Boolean isAuthorized = false;
		if(Arrays.asList(emails).contains(emailId)){
			ScriptLogger.writeInfo("############## Authorized User #############");
			isAuthorized = true;
		}else {
			ScriptLogger.writeInfo("############## UnAuthorized User #############");
		}
		return isAuthorized;
	}

	public JSONObject generatePDFFile(BudgetRequest request) throws FileNotFoundException, DocumentException {
		// TODO Auto-generated method stub
		
		float left = 10;
	    float right = 10;
	    float top = 50;
	    float bottom = 10;
	    Document document = new Document(PageSize.A4, left, right, top, bottom);
		PdfWriter.getInstance(document, new FileOutputStream("/Users/nitinharsoda/iTextHelloWorld.pdf"));
		 
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("PiSight Pvt Ltd.\n", font);
		document.add(chunk);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(90);
		addTableHeader(table);
		addRows(table);
		document.add(table); 
		
		document.close();
		return null;
	}
	
	private void addTableHeader(PdfPTable table) {
	    Stream.of("Name", "Country", "Institution Name", "Start Time", "End Time", "Status")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle));
	        table.addCell(header);
	    });
	}
	
	private void addRows(PdfPTable table) {
		
		List<StatementParsingDetail> stmtParsingDetails = stmtParsingDetailRepo.findAll();
		for (StatementParsingDetail statementParsingDetail : stmtParsingDetails) {
			table.addCell(statementParsingDetail.getUser().getUsername());
			table.addCell(statementParsingDetail.getCountryCode());
			table.addCell(statementParsingDetail.getInstitutionName());
			table.addCell(statementParsingDetail.getStartTime().toString());
			table.addCell(statementParsingDetail.getEndTime().toString());
			table.addCell(statementParsingDetail.getStatus());
		}
	}
}
