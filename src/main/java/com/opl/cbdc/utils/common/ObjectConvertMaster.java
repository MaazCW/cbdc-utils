package com.opl.cbdc.utils.common;

import org.codehaus.jackson.map.*;
import org.slf4j.*;

import java.io.*;
import java.math.*;
import java.util.*;


public class ObjectConvertMaster {

	private static final Logger logger = LoggerFactory.getLogger(ObjectConvertMaster.class);
	
	public static <T extends Object> T convert(Object obj, Class<T> type) {
		return type.cast(obj);
	}

	public static Long toLong(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				if (obj instanceof BigInteger) {
					BigInteger value = (BigInteger) obj;
					return value.longValue();
				} else if (obj instanceof Integer) {
					Integer value = toInteger(obj);
					if(value != null) {
						return value.longValue();
					}
				} else {
					return (Long) obj;
				}
			}
		} catch (Exception e) {
			logger.error("Exception While toLong :- " + obj + ",  Message :- " + e.getMessage());
		}
		return null;
	}

	public static String toString(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				if (obj instanceof String) {
					String value = (String) obj;
					return value;
				} else {
					return String.valueOf(obj);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while toString :- " + obj + " Message :- " + e.getMessage());
		}
		return null;
	}

	public static Boolean toBoolean(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				return (Boolean) obj;
			}
		} catch (Exception e) {
			logger.error("Exception while toBoolean :- " + obj + " Message :- " + e.getMessage());
		}
		return Boolean.FALSE;
	}

	public static Integer toInteger(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				if (obj instanceof BigInteger) {
					BigInteger value = (BigInteger) obj;
					return value.intValue();
				} else {
					return (Integer) obj;
				}
			}
		} catch (Exception e) {
			logger.error("Exception while toInteger :- " + obj + " Message :- " + e.getMessage());
		}
		return null;
	}

	public static Date toDate(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				if (obj instanceof Long) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis((Long) obj);
					return calendar.getTime();
				}
				return (Date) obj;
			}
		} catch (Exception e) {
			logger.error("Exception while toDate :- " + obj + " Message :- " + e.getMessage());
		}
		return null;
	}

	public static Double toDouble(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				if (obj instanceof BigDecimal) {
					BigDecimal value = (BigDecimal) obj;
					return value.doubleValue();
				} else {
					return (Double) obj;
				}
			}
		} catch (Exception e) {
			logger.error("Exception while toDouble :- " + obj + " Message :- " + e.getMessage());
		}
		return null;
	}

	public static String toJson(Object obj) {
		try {
			if (!OPLUtils.isObjectNullOrEmpty(obj)) {
				ObjectMapper Obj = new ObjectMapper();
				String jsonStr = Obj.writeValueAsString(obj);
				return jsonStr;
			}
		} catch (IOException e) {
			logger.error("Exception while toJson Message :- " + e.getMessage());
		}
		return null;
	}


}
