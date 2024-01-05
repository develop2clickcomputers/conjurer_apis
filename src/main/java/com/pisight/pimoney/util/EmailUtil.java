/**
 * 
 */
package com.pisight.pimoney.util;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.pisight.pimoney.service.K2ServiceImpl;


/**
 * This class is used to send an email
 * @author nitinharsoda
 *
 */
@Service
public class EmailUtil {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

	private static String senderUserId = "";
	private static String senderSecret = "";

	//default values in case fetch from configurations fails
	private static String fromEmail = "";
	private static String emailHost = "";
	private static String emailPort = "";
	
	private static Session session = null;

	@PostConstruct
	public void init() {
		
		senderUserId = k2ServiceImpl.getConfigurationValue("MAIL", "EMAIL_ADDRESS");
		senderSecret = k2ServiceImpl.getConfigurationValue("MAIL", "PASSWORD");
		
		fromEmail = k2ServiceImpl.getConfigurationValue("EMAIL", "FROM_EMAIL");
		emailHost = k2ServiceImpl.getConfigurationValue("EMAIL", "EMAIL_HOST");
		emailPort = k2ServiceImpl.getConfigurationValue("EMAIL", "EMAIL_PORT");
		
		ScriptLogger.writeInfo("From Email = " + fromEmail);
		ScriptLogger.writeInfo("Email Host = " + emailHost);
		ScriptLogger.writeInfo("Email Port = " + emailPort);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.host", emailHost);
		props.put("mail.smtp.port", emailPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", true); 

		session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderUserId, senderSecret);
			}
		  });
	}

	public static void sendEmail(String userEmail, String subject, Template template, Map<String, Object> templateParams) {

		try {
			
	        ScriptLogger.writeInfo("Sending Email to " + userEmail);
			Properties vprops = new Properties();
			vprops.setProperty("resource.loader", "class");
			vprops.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			VelocityEngine velocityEngine = new VelocityEngine(vprops);
			velocityEngine.init();

			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setSession(session);

			MimeMessage message = sender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(userEmail);
			helper.setSubject(subject);
			String bodyText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template.getTemplate(), "UTF-8", templateParams);
			helper.setText(bodyText, true);

			InputStreamSource pisightlogo = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("templates/image/pisight_logo.png").getInputStream()));
			helper.addInline("pisightlogo", pisightlogo, "image/png");

			InputStreamSource linkedin = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("templates/image/linkedin.png").getInputStream()));
			helper.addInline("linkedin", linkedin, "image/png");

			InputStreamSource facebook = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("templates/image/facebook.png").getInputStream()));
			helper.addInline("facebook", facebook, "image/png");

			InputStreamSource twitter = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("templates/image/twitter.png").getInputStream()));
			helper.addInline("twitter", twitter, "image/png");

			InputStreamSource googleplus = new ByteArrayResource(IOUtils.toByteArray(new ClassPathResource("templates/image/google+.png").getInputStream()));
			helper.addInline("googleplus", googleplus, "image/png");

			sender.send(message);

			ScriptLogger.writeInfo("Email sent successfully to " + userEmail);

		} catch (Exception e) {
			ScriptLogger.writeInfo("Error sending email to " + userEmail, e);
		}
	}
	
	public static boolean sendEmailwithAttachment(String filePath, String emailAddresses) throws AddressException, MessagingException {
		
		ScriptLogger.writeInfo("File Path = " + filePath);
		try {
						
			 MimeMessage message = new MimeMessage(session);  
			 message.setFrom(new InternetAddress("pimoney.development@gmail.com"));  
			 message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddresses));  
			 message.setSubject("Parser Usages Details");  
			      
		     //3) create MimeBodyPart object and set your message text     
		     BodyPart messageBodyPart1 = new MimeBodyPart();  
		     messageBodyPart1.setText("PFA for Parser Usage Details");  
			      
			 //4) create new MimeBodyPart object and set DataHandler object to this object      
		     MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
			  
			// DataSource source = new FileDataSource(filePath);  
			 //messageBodyPart2.setDataHandler(new DataHandler(source));  
			 messageBodyPart2.setFileName("Details");  
			 messageBodyPart2.attachFile(filePath);
			     
			 //5) create Multipart object and add MimeBodyPart objects to this object      
			 Multipart multipart = new MimeMultipart();  
			 multipart.addBodyPart(messageBodyPart1);  
			 multipart.addBodyPart(messageBodyPart2);  
			  
			 //6) set the multiplart object to the message object  
			 message.setContent(multipart );  
			     
			 //7) send message  
			 Transport.send(message);
			 
			 return true;
			 
		}catch (Exception e) {
			ScriptLogger.writeInfo("Exception in Sending Mail With Attachment = ", e);
			// TODO: handle exception
			return false;
		}
	}

	public enum Template {
		CHANGEPASSWORD("templates/parserusagedetails.vm");

		private final String template;

		private Template(String template) {
			this.template = template;
		}

		public String getTemplate() {
			return this.template;
		}
	}
}
