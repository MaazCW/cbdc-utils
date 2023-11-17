package com.opl.cbdc.utils.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilterUtils {

	public static final Map<String, String> FILTER_MAP = new HashMap<>();
	private static final String OPTIONS = "OPTIONS";
	private static final String TRACE = "TRACE";

	static {
		FILTER_MAP.put("Access-Control-Allow-Origin", "*");
		FILTER_MAP.put("Access-Control-Allow-Methods", "*");
		FILTER_MAP.put("Access-Control-Max-Age", "3600");
		FILTER_MAP.put("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, X-Requested-With,auth_token,tk_ac,tk_rc,ur_cu,tk_lg,ck_obj,req_auth,is_decrypt");
		FILTER_MAP.put("X-Frame-Options", "SAMEORIGIN");
		FILTER_MAP.put("x-xss-protection", "1;mode=block");
		FILTER_MAP.put("Content-Security-Policy", "frame-ancestors script-src 'self'");
		FILTER_MAP.put("Strict-Transport-Security", "max-age=63072000; includeSubDomains; preload");
		FILTER_MAP.put("X-Content-Type-Options", "no sniff");
		FILTER_MAP.put("Cache-control", "no-store, no-cache");
		FILTER_MAP.put("Pragma", "no-cache");
	}

	public static boolean setHeader(HttpServletResponse response, HttpServletRequest request) {
		Set<Entry<String, String>> entrySet = CorsFilterUtils.FILTER_MAP.entrySet();
		for (Entry<String, String> entery : entrySet) {
			response.setHeader(entery.getKey(), entery.getValue());
		}
		if (OPTIONS.equalsIgnoreCase(request.getMethod()) || TRACE.equalsIgnoreCase(request.getMethod())) {
			String origin = request.getHeader("origin");
			if ((origin.contains("http://localhost:") || origin.contains("https://localhost:"))) {
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
