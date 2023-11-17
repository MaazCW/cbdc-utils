package com.opl.cbdc.utils.config;

import com.opl.cbdc.utils.common.EncryptionUtils;
import com.opl.cbdc.utils.common.OPLUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UrlsAns {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("urls");

	private static final String DOMAIN_URL = "ANS_DOMAIN_URL";
	private static final String ANS_ENVIRONMENT_NAME = "ANS_ENVIRONMENT_NAME";
	private static final String ANS_API_GATEWAY = "ANS_API_GATEWAY";
	private static final String ANS_APPLICATION_DOMAIN_URL = "ANS_APPLICATION_DOMAIN_URL";
	private static final String ANS_WEB_DOMAIN_URL = "ANS_WEB_DOMAIN_URL";
//	private static final String DOMAIN_URL = "ANS_DOMAIN_URL_QA";
	
	private static final String ANS_PRESCREEN_DOMAIN_URL = "ANS_PRESCREEN_DOMAIN_URL";

	public static final List<String> DOMAIN_URLS = new ArrayList<>(1);

	static {
		DOMAIN_URLS.add("SIT");
		DOMAIN_URLS.add("QA");
		DOMAIN_URLS.add("UAT");
		DOMAIN_URLS.add("jansamarth");
	}

	public static String decryptKey(String key) {
		String value = System.getenv(key);
		EncryptionUtils cryptoConverter = new EncryptionUtils();
		return cryptoConverter.convertToEntityAttribute(value);
	}

	public static String getEnvName() {
		return System.getenv(ANS_ENVIRONMENT_NAME);
	}
	
	public static String getPrescreenDomainURL() {
		return System.getenv(ANS_PRESCREEN_DOMAIN_URL);
	}
	
	public static String getAnsApplicationDomainURL() {
		return System.getenv(ANS_APPLICATION_DOMAIN_URL);
	}

	public static String getAnsWebDomainURL() {
		return System.getenv(ANS_WEB_DOMAIN_URL);
	}

	public static String getEnvNameForApplicationCode() {
		String envName = System.getenv(ANS_ENVIRONMENT_NAME);
		if(OPLUtils.isObjectNullOrEmpty(envName)) {
			return "";
		}
		return "-".concat(envName);
	}

	public static String getLocalIpAddress(UrlType urlType) {
		try {
			String domainUrl = System.getenv(DOMAIN_URL);
			String ansApiGateway = System.getenv(ANS_API_GATEWAY);
//			domainUrl = "https://preprod-opl-atmanirbhar.instantmseloans.in/";
//			domainUrl = "https://uat-opl-atmanirbhar.instantmseloans.in/";
//			domainUrl = "https://www.jansamarth.in";
//			domainUrl = "https://qa-ans.instantmseloans.in/gateway";
//			domainUrl = "https://sit-ans.instantmseloans.in/gateway";
	
			//https://qa-ans.instantmseloans.in/loans/loans/getApplicationDetails/11296
//			System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//			System.err.println(domainUrl);
//			System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			if ("localhost".equalsIgnoreCase(domainUrl)) {
				String serverIp = InetAddress.getLocalHost().getHostAddress().trim();
				StringBuilder strUrl = new StringBuilder().append(bundle.getString(UrlType.HTTP.getPortUrl()))
						.append(serverIp);
				return strUrl.append(bundle.getString(urlType.getPortUrl())).toString();
			} else {
				if (!OPLUtils.isListNullOrEmpty(DOMAIN_URLS)) {
					for (String s : DOMAIN_URLS) {
						if (!OPLUtils.isObjectNullOrEmpty(ANS_API_GATEWAY) && !OPLUtils.isObjectNullOrEmpty(getEnvName()) && s.equalsIgnoreCase(getEnvName())) {
							return domainUrl.concat(decryptKey(ANS_API_GATEWAY).concat(removePort(bundle.getString(urlType.getPortUrl())).toString()));
						}
					}
				}
				return domainUrl.concat(removePort(bundle.getString(urlType.getPortUrl())).toString());
			}
		} catch (UnknownHostException e) {
//			e.printStackTrace();
		}
		return null;
	}
	
	private static String removePort(String value) {
		String url = "";
		String[] split = value.split("/");
		for (int i = 1; i < split.length; i++) {
			String string = split[i];
			url = url + "/" + string; 
		}
		return url;
	}

	public static String getDomainSpecificUrl(DomainUrlType urlType) {
		try {
			StringBuilder strUrl = new StringBuilder().append(bundle.getString(DomainUrlType.DOMAIN_NAME.getPortUrl()));

			return strUrl.append(bundle.getString(urlType.getPortUrl())).toString();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public static String getDomainUrl(DomainUrlType urlType) {
		try {
			return bundle.getString(DomainUrlType.DOMAIN_NAME.getPortUrl());
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public enum UrlType {
		HTTP(0, "http"), ONEFORM(1, "oneform"), WORKFLOW(2, "workflow"), MATCHENGINE_RETAIL(3, "matchengine_retail"),
		ELIGIBILITY(4, "eligibility"), NOTIFICATION(5, "notification"), ANALYZER_RETAILS(6, "analyzer_retail"),
		THIRDPARTY(7, "thirdparty"), AUTH(8, "auth"), GST(9, "gst"), USERS(10, "users"), CIBIL(11, "cibil"),
		REPORTS(12, "reports"), DMS(14, "dms"), SCORING_RETAIL(15, "scoring_retail"), ITR_RETAIL(16, "itr_retail"),
		OTP(17, "otp"), PROFILE(18, "profile"), ITRSCRAPE(19, "itrscrape"), ITRSCRAPEPDF(20, "itrscrapepdf"),
		LOANS(21, "loans"), LOANS_RETAIL(22, "loans_retail"), AADHAR(23, "aadhar"), SCHEME_MASTER(24, "scheme_master"),
		REPORTS_RETAIL(25, "reports_retail"), CIBIL_RETAIL(26, "cibil_retail"), PRODUCT(27, "product"),
		LOANS_MSME(28, "loans_msme"), ANALYZER_MSME(29, "analyzer_msme"), SCORING_MSME(30, "scoring_msme"), ITR_MSME(31, "itr_msme"),
		MATCHENGINE_MSME(32, "matchengine_msme"), REPORTS_MSME(33, "reports_msme"), CIBIL_MSME(34, "cibil_msme"),SCORING(35, "scoring"),
		THIRDPARTY_MSME(36, "thirdparty_msme"),
		LOANS_AGRI(37,"loans_agri"),
		ANALYZER_AGRI(38,"analyzer_agri"),
		ITR_AGRI(39,"itr_agri"),
		MATCHENGINE_AGRI(40,"matchengine_agri"),
		CIBIL_AGRI(41,"cibil_agri"),
		PROPOSAL_AGRI(42,"proposal_agri"),THIRDPARTY_AGRI(43, "thirdparty_agri"),
		VOTER(44, "voter"),ANS_LOGS(45,"ans_logs"),
		LOANS_LIVELIHOOD(46,"loans_livelihood"),
		MATCHENGINE_LIVELIHOOD(47,"matchengine_livelihood"),
		ANALYZER_LIVELIHOOD(48,"analyzer_livelihood"),
		CIBIL_LIVELIHOOD(49,"cibil_livelihood"),
		PROPOSAL_LIVELIHOOD(50,"proposal_livelihood"),
		CONSENT(51,"consent"), PENNYDROP(52,"pennydrop"),
		NSDL_PAN(53,"nsdl_pan"),
		UDHYAM(54,"udhyam"),
		CBDT(55,"cbdt"),
		PARTNER(56,"partner"),
		STP(57,"stp"), 
		KCC(58,"kcc_process"),
		STP_ANS(59,"stp_ans"),
		HUNTER(60,"hunter"),
		NAME_MATCH(61,"namematch"),
		FIT_RANK(62,"fitrank");

		// PAYMENT_GATEWAY(13,
		// "payment_gateway"),

		UrlType(int id, String portUrl) {
			this.id = id;
			this.portUrl = portUrl;
		}

		private final int id;

		private final String portUrl;

		public int getId() {
			return id;
		}

		public String getPortUrl() {
			return portUrl;
		}
	}

	public enum DomainUrlType {

		DOMAIN_NAME(30, "domainName"), DOMAIN_URL_ONEFORM(31, "domainUrloneform"),
		DOMAIN_URL_WORKFLOW(32, "domainUrlworkflow"), DOMAIN_URL_MATCHENGINE_RETAIL(33, "domainUrlmatchengineRetail"),
		DOMAIN_URL_ELIGIBILITY(34, "domainUrleligibility"), DOMAIN_URL_NOTIFICATION(35, "domainUrlnotification"),
		DOMAIN_URL_ANALYZER_RETAIL(36, "domainUrlanalyzerRetail"), DOMAIN_URL_THIRDPARTY(37, "domainUrlthirdparty"),
		DOMAIN_URL_AUTH(38, "domainUrlauth"), DOMAIN_URL_GST(39, "domainUrlgst"),
		DOMAIN_URL_USERS(40, "domainUrlusers"), DOMAIN_URL_CIBIL(41, "domainUrlcibil"),
		DOMAIN_URL_REPORTS(42, "domainUrlreports"), // DOMAIN_URL_PAYMENT_GATEWAY(43, "domainUrlpayment_gateway"),
		DOMAIN_URL_CONNECT(44, "domainUrlconnect"), DOMAIN_URL_DMS(45, "domainUrldms"),
		DOMAIN_URL_SCORING_RETAIL(46, "domainUrlscoringRetail"), DOMAIN_URL_ITR_RETAIL(47, "domainUrlitrRetail"),
		DOMAIN_URL_OTP(48, "domainUrlotp"), DOMAIN_URL_EKYC(49, "domainUrlekyc"),
		DOMAIN_URL_ADMIN(50, "domainUrlAdmin"), DOMAIN_URL_PROFILE(51, "domainUrlProfile"),
		DOMAIN_URL_PENNYDROPAPI(52, "domainUrlPennydropApi"),
		DOMAIN_URL_ANALYZERREPORTS(53, "domainUrlAnalyzerReports"), DOMAIN_URL_LOANS(54, "domainUrlloans"),
		DOMAIN_URL_LOANS_RETAIL(55, "domainUrlLoansRetail"), DOMAIN_URL_AADHAR(56, "domainUrlAadhar"),
		DOMAIN_URL_SCHEME(56, "domainUrlScheme"), DOMAIN_URL_REPORTS_RETAIL(57, "domainUrlreports"),
		DOMAIN_URL_CIBIL_RETAIL(58, "domainUrlCibilRetail"),DOMAIN_URL_PRODUCT(59, "domainUrlProduct"),
		DOMAIN_URL_MATCHENGINE_MSME(60, "domainUrlmatchengineMsme"),
		DOMAIN_URL_ANALYZER_MSME(61, "domainUrlanalyzerMsme"),
		DOMAIN_URL_LOANS_MSME(62, "domainUrlLoansMsme"),
		DOMAIN_URL_SCORING_MSME(63, "domainUrlscoringMsme"),
		DOMAIN_URL_ITR_MSME(64, "domainUrlitrMsme"),
		DOMAIN_URL_REPORTS_MSME(65, "domainUrlReportsMsme"),
		DOMAIN_URL_CIBIL_MSME(66, "domainUrlCibilMsme"),
		DOMAIN_URL_SCORING(67, "domainUrlScoring"),
		DOMAIN_URL_LOANS_AGRI(68,"domainUrlLoansAgri"),
		DOMAIN_URL_ANALYZER_AGRI(69,"domainUrlAnalyzerAgri"),
		DOMAIN_URL_ITR_AGRI(70,"domainUrlItrAgri"),
		DOMAIN_URL_MATCH_ENGINE_AGRI(71,"domainUrlMatchengineAgri"),
		DOMAIN_URL_CIBIL_AGRI(72,"domainUrlCibilAgri"),
		DOMAIN_URL_PROPOSAL_AGRI(73,"domainUrlProposalAgri"),
		DOMAIN_URL_VOTER(74, "domainUrlVoter"),DOMAIN_URL_ANS_LOGS(76,"domainUrlAnsLogs"),
		DOMAIN_URL_LOANS_LIVELIHOOD(77, "domainUrlLoansLivelihood"),
		DOMAIN_URL_ANALYZER_LIVELIHOOD(78, "domainUrlAnalyzerLivelihood"),
		DOMAIN_URL_MATCHENGINE_LIVELIHOOD(79, "domainUrlMatchengineLivelihood"),
		DOMAIN_URL_CIBIL_LIVELIHOOD(80, "domainUrlCibilLivelihood"),
		DOMAIN_URL_PROPOSAL_LIVELIHOOD(81, "domainUrlProposalLivelihood"),
		DOMAIN_URL_CONSENT(82,"domainUrlConsent"),
		DOMAIN_URL_NSDL(83,"domainUrlNsdl"),
		DOMAIN_URL_UDHYAM(83,"domainUrlUdhyam"),
		CBDT(84,"domainUrlCbdt"),
		STP(85,"domainUrlSTP"),
		HUNTER(86,"domainUrlHUNTER"),
		FITRANK(87,"domainUrlFITRANK");


		DomainUrlType(int id, String portUrl) {

			this.id = id;
			this.portUrl = portUrl;
		}

		private final int id;

		private final String portUrl;

		public int getId() {
			return id;
		}

		public String getPortUrl() {
			return portUrl;
		}
	}

}
