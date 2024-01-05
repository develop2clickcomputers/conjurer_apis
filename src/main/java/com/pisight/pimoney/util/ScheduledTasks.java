package com.pisight.pimoney.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pisight.pimoney.repository.StatementParsingDetailRepository;
import com.pisight.pimoney.repository.entities.StatementParsingDetail;
import com.pisight.pimoney.service.K2OnlineServiceImpl;
import com.pisight.pimoney.service.K2ServiceImpl;

@Component
public class ScheduledTasks {
	
	@Autowired
	private K2OnlineServiceImpl k2OnlineServiceImpl = null;
	
	@Autowired
	private StatementParsingDetailRepository stmtParsingDetailRepo = null;
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
   private static HashMap<String, Thread> threadMap = new HashMap<String, Thread>();

//    @Scheduled(fixedRate = 50000)
//    public void reportCurrentTime() {
//        ScriptLogger.writeInfo("The time is now " + dateFormat.format(new Date()));
//    }
    
    
    @Scheduled(fixedRate = 5000)
    public void updateRequestStatus() {
    	
//    	ScriptLogger.writeWarning("updating request status");
    	Iterator<Entry<String, Thread>> itr = threadMap.entrySet().iterator();
    	
    	while(itr.hasNext()) {
    		Entry<String, Thread> entry = itr.next();
    		Thread t = entry.getValue();
    		String key = entry.getKey();
    		
    		if(t != null) {
//    			ScriptLogger.writeInfo("thread to be updated => " + t.getName());
    			if(t.isAlive() && t.getName().equals(key)) {
    				k2OnlineServiceImpl.updateRequestStatus(key, "alive");
    			}
    			else {
    				k2OnlineServiceImpl.updateRequestStatus(key, "dead");
    				ScriptLogger.writeInfo("thread to be removed => " + t.getName());
    				itr.remove();
    			}
    		}
    		else {
    			k2OnlineServiceImpl.updateRequestStatus(key, "dead");
    			ScriptLogger.writeInfo("thread to be removed => " + t);
    			itr.remove();
    			threadMap.remove(key);
    		}
    	}
    }
    
    //@Scheduled(fixedRate = 30000
    //second, minute, hour, day of month, month, day(s) of week
    @Scheduled(cron = "0 59 23 * * ?")
    public void sendParserUsageDetailsMail() throws AddressException, MessagingException, IOException, InterruptedException, ParseException {
    	
    		ScriptLogger.writeInfo("sendParserUsageDetailsMail........");

    		String needToSend = k2ServiceImpl.getConfigurationValue("MAIL", "NEED_TO_SEND");
    		
    		if("Yes".equals(needToSend)) {
    			Calendar cal = Calendar.getInstance();
        		System.out.println(WebUtil.removeTimeStatmp(cal.getTime()));
        		List<StatementParsingDetail> stmtParsingDetails = stmtParsingDetailRepo.fetchDetailsByDate(WebUtil.removeTimeStatmp(cal.getTime()));
        		
        		System.out.println("Number of Record = " + stmtParsingDetails.size());
        		String randomString = RandomStringUtils.randomAlphanumeric(8);
        		Path filepath = Paths.get(System.getProperty("user.home"), "public", randomString + ".xlsx");

        		XSSFWorkbook workbook = new XSSFWorkbook();
        		XSSFSheet sheet = workbook.createSheet("Usages Details");

        		XSSFCellStyle cs = workbook.createCellStyle();
        		cs.setFillForegroundColor(new XSSFColor(new java.awt.Color(201, 204, 181)));
        		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

        		XSSFFont font = workbook.createFont();
        		font.setFontHeightInPoints((short) 14);
        		font.setBold(true);
        		cs.setFont(font);
        		
        		int rowNum = 1;
        		Row header = null;
        		for (StatementParsingDetail detail : stmtParsingDetails) {
        			
        			Row row = sheet.createRow(rowNum++);
        			if (rowNum == 2) {
        				header = sheet.createRow(0);
        			}
        			
        			if (rowNum == 2) {
        				
        				for (int i = 0; i< 7; i++) {
        					Cell cell = header.createCell(i);
        					
        					if(i==0) {
        						cell.setCellValue("Name");
        					}else if(i==1) {
        						cell.setCellValue("Country");
        					}else if(i==2) {
        						cell.setCellValue("Institution Name");
        					}else if(i==3) {
        						cell.setCellValue("Start Time");
        					}else if(i==4) {
        						cell.setCellValue("End Time");
        					}else if(i==5) {
        						cell.setCellValue("Status");
        					}else if(i==6) {
        						cell.setCellValue("TimeZone");
        					}
        					cell.setCellStyle(cs);
        				}
        			}	
        			for (int i = 0; i< 7; i++) {
        				
        				Cell cell = row.createCell(i);
        				
        				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        				String startTime = sdf.format(detail.getStartTime());
        				
        				String endTime = "";
        				if(detail.getEndTime() != null) {
        					endTime = sdf.format(detail.getEndTime());
        				}
        				
        				if(i==0) {
        					cell.setCellValue(detail.getUser().getUsername());
        				}else if(i==1) {
        					cell.setCellValue(detail.getCountryCode());
        				}else if(i==2) {
        		    			cell.setCellValue(detail.getInstitutionName());
        				}else if(i==3) {
        					cell.setCellValue(startTime);
        				}else if(i==4) {
        					cell.setCellValue(endTime);
        				}else if(i==5) {
        		    			cell.setCellValue(detail.getStatus());
        				}else if(i==6) {
    		    				cell.setCellValue(detail.getTimezone());
        				}
        			}
        		}
        		
        		for (int i = 0; i < 100; i++) {
        			sheet.autoSizeColumn(i);
        		}

        	    //Write the workbook in file system
        		FileOutputStream outputStream = new FileOutputStream(filepath.toFile());
        		workbook.write(outputStream);
        		outputStream.close();
        		System.out.println("Writesheet.xlsx written successfully");

        		String emailAddresses = k2ServiceImpl.getConfigurationValue("MAIL", "PARSER_DETAIL_SEND_EMAILS");
        		// For Sending Email
        		Boolean status = EmailUtil.sendEmailwithAttachment(filepath.toString(), emailAddresses);
        		
        		if(status) {
            		String deleteExcelFileCommand = "rm -rf " + filepath.toString();
            		ScriptLogger.writeInfo("Delete Excel File Command = " + deleteExcelFileCommand);
            		Process deleteExcelFileProcess = Runtime.getRuntime().exec(deleteExcelFileCommand);
            		deleteExcelFileProcess.waitFor();
        		}else {
        			System.out.println("Email not Send Successfully");
        		}
    		}else {
    			return; 
    		}	
    }
    
    public static void registerThread(String pitrackerId, Thread thread) {
    		threadMap.put(pitrackerId, thread);
    }
    
    public static void deregisterThread(String pitrackerId) {
    		threadMap.remove(pitrackerId);
    }   
}
