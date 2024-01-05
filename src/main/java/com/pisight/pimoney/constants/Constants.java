package com.pisight.pimoney.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pisight.pimoney.service.K2ServiceImpl;

@Component
public class Constants {
	
	@Autowired
	private K2ServiceImpl k2ServiceImpl = null;

	// TimeZone
	public static final String IST_TIMEZONE_KEY = "IST";
	
	public static final String CONJURER_PLATFORM_KEY = "conjurer";
	public static final String AUCTOR_PLATFORM_KEY = "auctor";
	
	public static final String TAG_BANK = "Bank";
	public static final String TAG_CARD = "Card";
	public static final String TAG_LOAN = "Loan";
	public static final String TAG_FIXED_DEPOSIT = "Fixed Deposit";
	public static final String TAG_INVESTMENT = "Investment";
	public static final String TAG_GENERIC = "Generic";

	public static final String FIELD_TYPE_STRING = "String";
	public static final String FIELD_TYPE_BOOLEAN = "Boolean";
	public static final String FIELD_TYPE_DATE = "Date";
	public static final String FIELD_TYPE_INTEGER = "Integer";
	public static final String FIELD_TYPE_DOUBLE = "Double";

	public static final String CATEGORY_INCOME = "Income";
	public static final String CATEGORY_TRANSFER = "Transfer";
	public static final String CATEGORY_INVESTMENT = "Investments";
	
	public static final String ADMIN_ACCESS_TYPE = "ADMIN_ACCESS";
	public static final String ADMIN_EMAILS_KEY = "EMAILS";

	public static final String DATEFORMAT_MM_SPACE_DD_SPACE_YYYY = "MM dd yyyy";
	public static final String DATEFORMAT_DD_SPACE_MMM_SPACE_YYYY = "dd MMM yyyy";
	public static final String DATEFORMAT_MMM_SPACE_DD_COMMA_YYYY = "MMM dd,yyyy";
	public static final String DATEFORMAT_DD_DASH_MM_DASH_YYYY = "dd-MM-yyyy";
	public static final String DATEFORMAT_DD_DASH_MMM_DASH_YYYY = "dd-MMM-yyyy";
	public static final String DATEFORMAT_DD_DASH_MM_DASH_YY = "dd-MM-yy";
	public static final String DATEFORMAT_MMMM_SPACE_DD_COMMA_YYYY = "MMMM dd,yyyy";
	public static final String DATEFORMAT_MMMM_SPACE_DD_COMMA_SPACE_YYYY = "MMMM dd, yyyy";
	public static final String DATEFORMAT_DD_SPACE_MMM_COMMA_YYYY = "dd MMM,yyyy";
	public static final String DATEFORMAT_DD_SPACE_MMM_COMMA_SPACE_YYYY = "dd MMM, yyyy";
	public static final String DATEFORMAT_MMM_SPACE_DD_SPACE_YYYY = "MMM dd yyyy";
	public static final String DATEFORMAT_DD_SLASH_MM_SLASH_YYYY = "dd/MM/yyyy";
	public static final String DATEFORMAT_YYYY_DASH_MM_DASH_DD = "yyyy-MM-dd";
	public static final String DATEFORMAT_DD_DOT_MM_DOT_YYYY = "dd.MM.yyyy";
	public static final String DATEFORMAT_DD_DOT_MM_DOT_YY = "dd.MM.yy";
	public static final String DATEFORMAT_DD_MMM_YY = "ddMMMyy";
	public static final String DATEFORMAT_DD_SPACE_MMMM_SPACE_YYYY = "dd MMMM yyyy";
	public static final String DATEFORMAT_MM_SLASH_DD_SLASH_YYYY = "MM/dd/yyyy";
	public static final String DATEFORMAT_DD_DASH_MMM_DASH_YY = "dd-MMM-yy";
	public static final String DATEFORMAT_DD_SPACE_MMM_SPACE_YY = "dd MMM yy";
	public static final String DATEFORMAT_DD_SLASH_MM_SLASH_YY = "dd/MM/yy";
	public static final String DATEFORMAT_DD_MMM_YYYY = "ddMMMyyyy";
	public static final String DATEFORMAT_DD_MM_YY = "ddMMyy";
	public static final String DATEFORMAT_YYYY_MM_DD = "yyyyMMdd";
	public static final String DATEFORMAT_MMM_SPACE_DD_SPACE_YY = "MMM dd yy";

	public static final String DATEFORMAT_MM_SPACE_DD = "MM dd";
	public static final String DATEFORMAT_DD_SPACE_MMM = "dd MMM";
	public static final String DATEFORMAT_MMMM_SPACE_DD = "MMMM dd";
	public static final String DATEFORMAT_DD_SLASH_MM = "dd/MM";
	public static final String DATEFORMAT_DD_DASH_MM = "dd-MM";
	public static final String DATEFORMAT_DD_DOT_MM = "dd.MM";
	public static final String DATEFORMAT_DD_SPACE_MMMM = "dd MMMM";

	public static final String MFA_TYPE_OTP = "OTP";
	public static final String MFA_TYPE_IMAGE = "IMAGE";
	public static final String MFA_TYPE_SQ = "SQ";
	public static final String MFA_TYPE_RADIO = "RADIO";

	public static final String SERVICE_JAVA_PIMONEY = "java_pimoney";
	public static final String SERVICE_JAVA_ACA = "java_aca";

	public static final String FIELD_TYPE_RADIO_WITH_INPUT = "radio_with_input";
	public static final String FIELD_TYPE_RADIO_WITH_LABEL = "radio_with_label";

	public static final String PROCESS_ADD = "add";
	public static final String PROCESS_REFRESH = "refresh";
	public static final String PROCESS_EDIT = "edit";
	public static final String PROCESS_DELETE = "delete";

	public static final String FIELD_TYPE_TEXT = "text";
	public static final String FIELD_TYPE_PASS = "password";
	public static final String FIELD_TYPE_OPTION = "option";
	public static final String FIELD_TYPE_MFA_OPTION = "mfa_option";

	public static final String SELENIUM_HUB = "SELENIUM_HUB";
	public static final String SELENIUM_HUB_URL = "SELENIUM_HUB_URL";
	public static final String PSV_ENGINE = "PSV_ENGINE";
	public static final String PSV_ENGINE_URL = "PSV_ENGINE_URL";
	public static final String ENVIRONMENT = "ENVIRONMENT";
	public static final String ENVIRONMENT_NAME = "ENVIRONMENT_NAME";
	public static final String ENVIRONMENT_TEST = "TEST";
	public static final String PARSER_ENGINE = "PARSER_ENGINE";
	public static final String PARSER_ENGINE_URL = "PARSER_ENGINE_URL";
	public static final String ACA_ENGINE = "ACA_ENGINE";
	public static final String ACA_ENGINE_URL = "ACA_ENGINE_URL";
	public static final String PIMONEY_SERVICE = "PIMONEY_SERVICE";
	public static final String PIMONEY_SERVICE_URL = "PIMONEY_SERVICE_URL";
	public static final String PIMONEY_SERVICE_URL1 = "PIMONEY_SERVICE_URL1";
	public static final String ACCESS_KEY = "ACCESS_KEY";
	
	public static final String AUTH_SERVICE = "AUTH_SERVICE";
	public static final String AUTH_SERVICE_URL = "AUTH_SERVICE_URL";

	public static final String USER_ID = "userId";
	public static final String INSTITUTION_CODE = "institutionCode";

	public static final String MFA_SEPERATOR = "&&";

	private static final List<String> dateFormatList = new ArrayList<String>();

	public static final String FAILED = "Failed";
	public static final String SUCCESS = "Success";
	public static final String PROCESSING = "Processing";

	public static final int SUCCESS_CODE = 1;

	public static final int ERROR_CODE = 0;

	public static final float MARGIN_PERC = 2;

	public static final String PATH_SCREENSHOT = "/home/kumar/aca/logs/screenshots/";

	public static final String ERROR_CODE_STRING = "errorCode";
	public static final String RESPONSE_STATUS_STRING = "status";
	public static final String RESPONSE_MESSAGE_STRING = "message";

	public static final String STATE_ACA_IN_PROGRESS = "aca-in_progress";
	public static final String STATE_PIMONEY_SERVICE_IN_PROGRESS = "pimoney_service-in_progress";
	public static final String STATE_REFRESH_REQUEST_RECIEVIED = "refresh_request_recieved-in_progress";
	public static final String STATE_ACA_COMPLETED = "aca_completed_successfully-in_progeress";
	public static final String STATE_ACA_FAILED_WITH_ACA_EXCEPTION = "aca_failed_with_aca_exception-completed";
	public static final String STATE_ACA_FAILED_WITH_OTHER_EXCEPTION = "aca_failed_with_other_exception-completed";
	public static final String STATE_PIMONEY_FAILED = "pimoney_failed-completed";
	public static final String STATE_REFRESH_SUCCESS = "refresh_success-completed";
	public static final String STATE_REFRESH_FAILED = "refresh_failed-completed";
	public static final String STATE_IN_PROGRESS = "-in_progress";
	public static final String STATE_COMPLETED = "-completed";

	public static final String STATUS_AUTHENTICATION_IN_PROGRESS = "Authenticating";
	public static final String STATUS_GETTING_ACCOUNT_IN_PROGRESS = "Getting account details";
	public static final String STATUS_FETCHING_TRANSACTIONS = "Fetching transactions";
	public static final String STATUS_ACA_LOGIN = "login";
	public static final String STATUS_ACA_ACCOUNT = "accounts";
	public static final String STATUS_ACA_TRANSACTIONS = "transactions";

	public static final String STATEMENT_TYPE_DEFAULT = "default";
	public static final String STATEMENT_TYPE_TRANSACTION = "transaction";
	public static final String STATEMENT_TYPE_PORTFOLIO = "portfolio";

	public static final String STATEMENT_HOME = System.getProperty("user.home") + "/userfiles/";

	public static final String FLOW_PIMONEY = "pimoney";
	public static final String FLOW_GENERATE_FILE = "gx";
	public static final String FLOW_REVIEW_AND_GENERATE_FILE = "rgx";

	private static final HashMap<String, String> ACA_STATUS_MAP = new HashMap<String, String>();
	private static final HashMap<String, String> TAG_CONTAINER_MAP = new HashMap<String, String>();

	private static final Set<String> TRANS_CODE_LIST = new HashSet<String>(Arrays.asList("pa", "sa", "ac", "as", "by",
			"lc", "sc", "cs", "li", "si", "lo", "so", "dp", "dv", "dr", "dw", "in", "ai", "ir", "iw", "pd", "ps", "cc",
			"mc", "va", "rc", "rs", "sl", "ss", "ti", "ts", "to", "tr", "wd"));

	static {
		dateFormatList.add(DATEFORMAT_MM_SPACE_DD_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_DD_SPACE_MMM_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_MMM_SPACE_DD_COMMA_YYYY);
		dateFormatList.add(DATEFORMAT_DD_DASH_MM_DASH_YYYY);
		dateFormatList.add(DATEFORMAT_DD_DASH_MMM_DASH_YYYY);
		dateFormatList.add(DATEFORMAT_MMMM_SPACE_DD_COMMA_YYYY);
		dateFormatList.add(DATEFORMAT_MMMM_SPACE_DD_COMMA_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_MMM_SPACE_DD_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_DD_SLASH_MM_SLASH_YYYY);
		dateFormatList.add(DATEFORMAT_YYYY_DASH_MM_DASH_DD);
		dateFormatList.add(DATEFORMAT_DD_DOT_MM_DOT_YYYY);
		dateFormatList.add(DATEFORMAT_DD_DOT_MM_DOT_YY);
		dateFormatList.add(DATEFORMAT_DD_MMM_YY);
		dateFormatList.add(DATEFORMAT_DD_SPACE_MMMM_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_DD_DASH_MMM_DASH_YY);
		dateFormatList.add(DATEFORMAT_DD_SPACE_MMM_SPACE_YY);
		dateFormatList.add(DATEFORMAT_DD_SLASH_MM_SLASH_YY);
		dateFormatList.add(DATEFORMAT_DD_MMM_YYYY);
		dateFormatList.add(DATEFORMAT_DD_DASH_MM_DASH_YY);
		dateFormatList.add(DATEFORMAT_DD_MM_YY);
		dateFormatList.add(DATEFORMAT_YYYY_MM_DD);
		dateFormatList.add(DATEFORMAT_MMM_SPACE_DD_SPACE_YY);
		dateFormatList.add(DATEFORMAT_DD_DOT_MM);
		dateFormatList.add(DATEFORMAT_MM_SLASH_DD_SLASH_YYYY);
		dateFormatList.add(DATEFORMAT_DD_SPACE_MMM_COMMA_SPACE_YYYY);
		dateFormatList.add(DATEFORMAT_DD_SPACE_MMM_COMMA_YYYY);

		TAG_CONTAINER_MAP.put(Constants.TAG_BANK, "com.pisight.pimoney.models.BankAccount");
		TAG_CONTAINER_MAP.put(Constants.TAG_CARD, "com.pisight.pimoney.models.CardAccount");
		TAG_CONTAINER_MAP.put(Constants.TAG_LOAN, "com.pisight.pimoney.models.LoanAccount");
		TAG_CONTAINER_MAP.put(Constants.TAG_FIXED_DEPOSIT, "com.pisight.pimoney.models.FixedDepositAccount");
		TAG_CONTAINER_MAP.put(Constants.TAG_INVESTMENT, "com.pisight.pimoney.models.InvestmentAccount");

		ACA_STATUS_MAP.put(STATUS_ACA_LOGIN, STATUS_AUTHENTICATION_IN_PROGRESS);
		ACA_STATUS_MAP.put(STATUS_ACA_ACCOUNT, STATUS_GETTING_ACCOUNT_IN_PROGRESS);
		ACA_STATUS_MAP.put(STATUS_ACA_TRANSACTIONS, STATUS_FETCHING_TRANSACTIONS);

	}
	
	public static List<String> getDateFormatList(){
		return dateFormatList;
	}
	
	public static HashMap<String, String> getAcaStatusMap() {
		return ACA_STATUS_MAP;
	}

	public static HashMap<String, String> getTagContainerMap() {
		return TAG_CONTAINER_MAP;
	}
	
	public static Set<String> getTransCodeList() {
		return TRANS_CODE_LIST;
	}
	
	// For OnLoad Application Set Key
	public static final String AUTHENTICATION_TYPE = "AUTHENTICATION";
	public static final String SECRETKEY_NAME = "SECRETKEY";
	
	private static String SECRET_KEY;
	
	@PostConstruct
	public void init() {
		SECRET_KEY = k2ServiceImpl.getConfigurationValue(AUTHENTICATION_TYPE, SECRETKEY_NAME);
	}

	public static String getSECRET_KEY() {
		return SECRET_KEY;
	}

}
