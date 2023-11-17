package com.opl.cbdc.utils.common;

import org.apache.commons.lang.*;
import org.slf4j.*;

import java.lang.reflect.*;
import java.text.*;
import java.util.*;


public class CAMUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CAMUtils.class);

	static DecimalFormat decimal = new DecimalFormat("#,##0.00");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object printFields(Object obj, Map<String, Object> data) throws Exception {
		if (obj != null) {
			if (obj.getClass().isArray()) {
				// Do nothing because of X and Y.
			}
		} else {
			return obj;
		}
		if (obj instanceof List) {
			List<?> lst = (List) obj;
			for (Object o : lst) {
				o = printFields(o, data);
			}
		} else if (obj instanceof Map) {
			Map<Object, Object> map = (Map) obj;
			for (Map.Entry<Object, Object> setEntry : map.entrySet()) {
				setEntry.setValue(printFields(setEntry.getValue(), data));
			}
		} else if (obj instanceof String) {
			obj = StringEscapeUtils.escapeXml(((String) obj).replaceAll("--", ""));
			return obj;
		} else if (obj instanceof Double) {
			if (!Double.isNaN((Double) obj)) {
				return convertToDoubleForXml(obj, null);
			}
		} else {
			if (obj.getClass().getName().startsWith("com.opl")) {
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
						// Do nothing because of X and Y.
					} else {
						field.setAccessible(true);
						Object value = field.get(obj);
						field.set(obj, printFields(value, data));
					}
				}
			}
		}
		return obj;
	}

	public static Object convertToDoubleForXml(Object obj, Map<String, Object> data) throws Exception {
		try {
			if (obj == null) {
				return null;
			}
			DecimalFormat decim = new DecimalFormat("0.00");
			if (obj instanceof Double) {
				obj = Double.parseDouble(decim.format(obj));
				return obj;
			} else if (obj.getClass().getName().startsWith("com.opl")) {
				Field[] fields = obj.getClass().getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					Object value = field.get(obj);
					if (data != null) {
						data.put(field.getName(), value);
					}
					if (!OPLUtils.isObjectNullOrEmpty(value) && value instanceof Double
							&& !Double.isNaN((Double) value)) {
						value = Double.parseDouble(decim.format(value));
						if (data != null) {
							value = decimal.format(value);
							data.put(field.getName(), value);
						} else {
							field.set(obj, value);
						}
					}
				}
			}
			if (data != null) {
				return data;
			}
			return obj;
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	public static Double addNumbers(Double... a) {
		Double sum = 0.0;
		if (!OPLUtils.isObjectNullOrEmpty(a)) {
			for (Double b : a) {
				if (!OPLUtils.isObjectNullOrEmpty(b))
					sum += b;
			}
		}
		return sum;
	}

	public static Double divideNumbers(Double a1, Double a2) {
		return !OPLUtils.isObjectListNull(a1, a2) && a1 != 0 && a2 != 0 ? (a1 / a2) : 0.0;
	}


	public static Double substractNumbers(Double a, Double b) {
		a = OPLUtils.isObjectNullOrEmpty(a) ? 0.0 : a;
		b = OPLUtils.isObjectNullOrEmpty(b) ? 0.0 : b;
		return a - b;
	}

	public static Double multiplyNumbers(Double a1, Double a2) {
		return !OPLUtils.isObjectListNull(a1, a2) ? (a1 * a2) : 0.0;
	}

	public static Double substractThreeNumbers(Double a, Double b, Double c) {
		a = OPLUtils.isObjectNullOrEmpty(a) ? 0.0 : a;
		b = OPLUtils.isObjectNullOrEmpty(b) ? 0.0 : b;
		c = OPLUtils.isObjectNullOrEmpty(c) ? 0.0 : c;
		return a - b - c;
	}

	public static String getMonthValue(String mname){
    	if(!OPLUtils.isObjectNullOrEmpty(mname)){
			try{
				String fchar = mname.substring(0, 1).toUpperCase();
		    	String rchars =  mname.substring(1).toLowerCase();
		    	return fchar+""+rchars;
			}catch(Exception e){
				logger.info("Exception while getting month" + e);
			}
		}
		return "";
	}

}
