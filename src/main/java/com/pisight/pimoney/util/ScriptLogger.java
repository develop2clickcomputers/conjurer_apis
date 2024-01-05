package com.pisight.pimoney.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class ScriptLogger {

	private static Logger logger = Logger.getLogger(ScriptLogger.class);

	private static void initializeLoggerAppender(){

		PatternLayout layout = new PatternLayout();

		String conversionPattern = "[%p] %d [%t] - %m%n";
		layout.setConversionPattern(conversionPattern);

		DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
		//rollingAppender.setFile(System.getProperty("user.home") + "/logs/conjurerlog/script.log");
		//TODO: need to be customized
		rollingAppender.setFile("D:\\log\\pcf-services\\conjurerlog_apis.log");
		rollingAppender.setDatePattern("'.'yyyy-MM-dd");
		rollingAppender.setLayout(layout);
		rollingAppender.activateOptions();

		logger.setLevel(Level.DEBUG);
		logger.addAppender(rollingAppender);
	}

	static{
		initializeLoggerAppender();
	}

	public static void writeInfo(Object message){
		logger.info(getCallerDetail() + "  <<i>>  " + message);
	}

	public static void writeInfo(Object message, Throwable t){
		logger.info(getCallerDetail() + " <<i>> " + message, t);
	}

	public static void writeWarning(Object message){
		logger.warn(getCallerDetail() + " >>e<< " + message);
	}

	public static void writeWarning(Object message, Throwable t){
		logger.warn(getCallerDetail() + " >>e<< " + message, t);
	}

	public static void writeError(Object message){
		logger.error(getCallerDetail() + " >>e<< " + message);
	}

	public static void writeError(Object message, Throwable t){
		logger.error(getCallerDetail() + " >>e<< " + message, t);
	}
	
	public static void writeDebug(Object message){
		logger.debug(getCallerDetail() + " <<d>> " + message);
	}

	public static void writeDebug(Object message, Throwable t){
		logger.debug(getCallerDetail() + " <<d>> " + message, t);
	}
	
	public static String getCallerDetail(){
		 StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		 StackTraceElement e = stacktrace[3];//maybe this number needs to be corrected
		 return "[" + e.getFileName() + "." + e.getMethodName()  + "::"+ e.getLineNumber() + "]" ;
	}
	
	@SuppressWarnings("unused")
	private static String maskString(String string, ArrayList<String> regexList){
		
		for(String regex: regexList){
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(string);

			while(matcher.find()){
				string = string.replaceAll(matcher.group(1), buildStringWithStars(matcher.group(1).length()));
			}
		}
		return string;
	}
	
	private static String buildStringWithStars(int i) {
		// TODO Auto-generated method stub
		CharSequence[] array = new CharSequence[i];
		Arrays.fill(array, "*");
		return String.join("", array);
	}
}
