package com.opl.cbdc.utils.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opl.cbdc.utils.enums.ConstitutionEnum;
import com.opl.cbdc.utils.enums.SwmsConstitution;

public class OPLUtils {

	private static final Logger logger = LoggerFactory.getLogger(OPLUtils.class);

	public static final String REQUEST_HEADER_AUTHENTICATE = "req_auth";
	public static final String REQUEST_HEADER_AUTHENTICATE_VALUE = "true";
	public static final String PATTERN = "\\B(?=\\d(?=(?:\\d{2})+(?!\\d)))";
	 public static final String REPLACE = ",";

	public static final Double DEFAULT_ZERO = 0.0d;

	public static boolean isListNullOrEmpty(Collection<?> data) {
		return (data == null || data.isEmpty());
	}

	public static String isStringEmpty(String value) {
		value = value.trim();
		if (value.isEmpty() || value == null || "undefined".equals(value) || "null".equals(value) || "".equals(value)) {
			return "";
		}
		return value;
	}

	public static boolean isObjectNullOrEmpty(Object value) {
		return (value == null || (value instanceof String ? (((String) value).isEmpty() || "".equals(((String) value).trim()) || "null".equals(value) || "undefined".equals(value)) : false));
	}

	public static boolean isObjectNullOrEmptyOrDash(Object value) {
		return (value == null || (value instanceof String ? (((String) value).isEmpty() || "".equals(((String) value).trim()) || "null".equals(value) || "-".equals(value) || "undefined".equals(value)) : false));
	}

	@SuppressWarnings("rawtypes")
	public static boolean isObjectListNull(Object... args) {
		for (Object object : args) {
			boolean flag = false;
			if (object instanceof List) {
				flag = isListNullOrEmpty((List) object);
				if (flag)
					return true;
				else
					continue;
			}
			flag = isObjectNullOrEmpty(object);
			if (flag)
				return true;
		}
		return false;
	}

	public static Double getPMTCalculationByLoanAmt(double roi, double tenure, double circularLoanAmount) {
		return roi / (1.0D - Math.pow(1.0D + roi, -tenure)) * circularLoanAmount * 12.0D;
	}

	public static Double convertRoundOffValue(Double finalLoanAmountNew) {
		// logger.info("ENTER CONVERT RounfOFF Value======>"+finalLoanAmountNew);
		// THIS IS USE FOR BEFORE VALE DIGIT 3 IS BASED ON 500 CONDITION EX. 4714415.0
		// RES.4714000.0

		Double finalEligibleValue;
		Long firstDgt;
		if (finalLoanAmountNew != null) {
			String s1 = String.valueOf(finalLoanAmountNew);
			s1 = s1.substring(0, s1.indexOf('.'));
			Long val = 0l;
			Long l1 = 0l;
			String s2 = s1;

			val = Long.parseLong(s2);
			if (val > 3) {
				s2 = String.valueOf(val);
//		                    logger.info("ACTUAL VALUE------->"+s2);
				if (s2.length() > 3) {

					l1 = Long.parseLong(s2.substring(s2.length() - 3));
					if (l1 > 500) {
						s2 = s2.substring(0, s2.length() - 3);
						firstDgt = Long.parseLong(s2);
//		                    logger.info("IF VALUE IS MORE THEN--(500)----->>>>>" + firstDgt);
						firstDgt++;
					} else {
						s2 = s2.substring(0, s2.length() - 3);
						firstDgt = Long.parseLong(s2);
						// firstDgt = Long.parseLong(s2);
//		                    logger.info("IF VALUE IS Less THEN---(500)---->>>>>" + firstDgt);
					}

					s1 = firstDgt.toString() + "000";
					finalEligibleValue = Double.parseDouble(s1);
//		        logger.info("FINAL ACTUAL VALUE AFTER ROUNDOFF---->>>>>" + finalEligibleValue);
					return finalEligibleValue;
				}
			}
		}
		return finalLoanAmountNew;
	}

	public static Double checkInfinityValueAndIsNAN(Double value) {
		if (value == null || Double.isNaN(value) || Double.isInfinite(value)) {
			return DEFAULT_ZERO;
		} else {
			return value;
		}
	}

	/**
	 * Accepts double value like 14.55 and return 10 and if 15 than 20
	 * 
	 * @param finalLoanAmountNew
	 * @return
	 */
	public static Double convertRoundOffValueToTen(Double finalLoanAmountNew) {
		finalLoanAmountNew = checkInfinityValueAndIsNAN(finalLoanAmountNew);
		Double finalEligibleValue;
		Long firstDgt;
		if (finalLoanAmountNew != null) {

			String s1 = String.valueOf(finalLoanAmountNew);
			s1 = s1.substring(0, s1.indexOf('.'));

			Long val = 0l;
			Long l1 = 0l;
			String s2 = s1;

			val = Long.parseLong(s2);
			if (val > 1) {
				s2 = String.valueOf(val);
				if (s2.length() > 1) {

					l1 = Long.parseLong(s2.substring(s2.length() - 1));

					if (l1 > 4) {
						s2 = s2.substring(0, s2.length() - 1);
						firstDgt = Long.parseLong(s2);
						firstDgt++;
					} else {
						s2 = s2.substring(0, s2.length() - 1);
						firstDgt = Long.parseLong(s2);
					}

					s1 = firstDgt.toString() + "0";
					finalEligibleValue = Double.parseDouble(s1);
					return finalEligibleValue;
				}
			}
		}
		return finalLoanAmountNew;
	}

	public static boolean isNumeric(String strNum) {
		if (isObjectNullOrEmpty(strNum)) {
			return false;
		}

		try {
			if (strNum == null || Double.isNaN(Double.valueOf(strNum)) || Double.isInfinite(Double.valueOf(strNum))) {
				return false;
			}
			Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static List<String> getConstitutionByPan(String panNo) {
		logger.info("getConstitutionByPan :-- " + panNo);
		if (!isObjectNullOrEmpty(panNo) && panNo.length() == 10) {
			String charAt = Character.toString(panNo.charAt(3));

			switch (charAt.toUpperCase()) {
			case "A": {
				return Arrays.asList(ConstitutionEnum.ASSOCIATION_OF_PERSONS.getValue());
			}
			case "B": {
				return Arrays.asList(ConstitutionEnum.BODY_OF_INDIVIDUALS.getValue());
			}

			case "C": {
				return Arrays.asList(ConstitutionEnum.PRIVATE_LIMITED.getValue(), ConstitutionEnum.PUBLIC_LTD_UNLISTED.getValue(), ConstitutionEnum.ONE_PERSON_COMPANY.getValue());
			}
			case "E": {
				return Arrays.asList(ConstitutionEnum.LIMITED_LIABILITY_PARTNERSHIP.getValue());
			}

			case "F": {
				return Arrays.asList(ConstitutionEnum.PARTNERSHIP.getValue(), ConstitutionEnum.LIMITED_LIABILITY_PARTNERSHIP_LLP.getValue());
			}

			case "G": {
				return Arrays.asList(ConstitutionEnum.GOVERNMENT_ENTITY.getValue(), ConstitutionEnum.OTHERS.getValue());
			}

			case "H": {
				return Arrays.asList(ConstitutionEnum.HUF.getValue());
			}

			case "P": {
				return Arrays.asList(ConstitutionEnum.SOLE_PROPRIETORSHIP.getValue());
			}

			case "T": {
				return Arrays.asList(ConstitutionEnum.TRUST.getValue());
			}
			default: {
				logger.warn("pan number did not match,  PAN:-- " + panNo);
				return Collections.emptyList();
			}

			}
		}
		logger.warn("pan number is not valid  PAN:-- " + panNo);

		return Collections.emptyList();
	}

	public static Integer findIndiNonIndiByPan(String panNo) {
		logger.info("getConstitutionByPan :-- " + panNo);
		if (!isObjectNullOrEmpty(panNo) && panNo.length() == 10) {
			String charAt = Character.toString(panNo.charAt(3));
			switch (charAt.toUpperCase()) {

			case "P": {
				return SwmsConstitution.INDIVIDUAL.getId();
			}

			default: {
				return SwmsConstitution.NONINDIVIDUAL.getId();
			}

			}
		}
		logger.warn("pan number is not valid  PAN:-- " + panNo);
		return null;
	}

	public static String generateUUIDOfLength20() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 20);
	}

	public static String removeSplChar(String value){
		return value.replaceAll("\n", "").replaceAll("\r", "")
				.replaceAll("\t", "").replaceAll("\"", " ");
	}
	
//	 public static String generateCurrency(String amount) {
//	        String newAmount = "";
//	        if(!OPLUtils.isObjectNullOrEmpty(amount) && !amount.contains(REPLACE)){
//	            amount = amount.replaceAll("\\..*$","");
//	            newAmount = amount.replaceAll(PATTERN, REPLACE);
//	        } else {
//	            newAmount = amount;
//	        }
//	        return newAmount;
//	    }
	public static String generateCurrency(String amount) {
		if (!OPLUtils.isObjectNullOrEmpty(amount)) {
			Double value = Double.valueOf(amount);
			if (value < 1000) {
				return formatToIndianCurrency("###", value);
			} else {
				double hundreds = value % 1000;
				int other = (int) (value / 1000);
				return formatToIndianCurrency(",##", other) + ',' + formatToIndianCurrency("000", hundreds);
			}
		}
		return amount;
	}

    private static String formatToIndianCurrency(String pattern, Object value) {
        return new DecimalFormat(pattern).format(value);
    }
	 
	 public static void getJsonFileFromObject(String Path, Object data){
		 
		 
//		 Path path = Paths.get("SandipData.txt");
		 Path path = Paths.get(Path);
			try {
				String stringfromObject = MultipleJSONObjectHelper.getStringfromObject(data);
				String contents = MultipleJSONObjectHelper.getStringfromObject(stringfromObject);
				Files.write(path, contents.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
	 }
	 
	 public static StringBuilder validateRequest(Object request) {
			StringBuilder errors = new StringBuilder();
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Object>> violations = validator.validate(request);
			if (!OPLUtils.isListNullOrEmpty(violations)) {
				ConstraintViolation<Object> validate = violations.stream().findFirst().get();
				if(!OPLUtils.isObjectNullOrEmpty(validate)) {
//					String fieldName = validate.getPropertyPath().toString();
//					errors.append(fieldName + ":" + validate.getMessage().toString() + " ");
					errors.append(validate.getMessage().toString() + " ");
					return errors;
				}
			}
			return null;
		}

	 /**
	  * Custom roundOff logic for scheme In-Principle amount round-off 
	  */
	 public static Double roundOffInpAmt(Double amount) {
		 amount = checkInfinityValueAndIsNAN(amount);
		 Double roundedVal = amount;
		 if (amount > 99 && amount <= 10000) {
			 roundedVal = roundOffNumberUpperLower(amount, 100, false);
		 } else if (amount > 10000 && amount <= 100000) {
			 roundedVal = roundOffNumberUpperLower(amount, 500, false);
		 } else if (amount > 100000) {
			 roundedVal = roundOffNumberUpperLower(amount, 1000, false);
		 }
		 return roundedVal;
	 }

	 public static Double roundOffNumberUpperLower(Double number, Integer multiple, Boolean isFloor) {
			Double result = multiple.doubleValue();
	        if (number % multiple == 0) {
	            return  number;
	        }

	        // If not already multiple of given number
	        if (number % multiple != 0) {

	            Integer division =  (int) (number / multiple);
	            
	            if (Boolean.TRUE.equals(isFloor)) {
	            	division = division + 1;
	            }
	            result = division.doubleValue() * multiple;
	        }
	        return result;
	    }

		// remove new line and unicode from string
		public static String makeCleanString(String value){
			return value.replace("\n", "").replace("\r", "").replaceAll("[\\p{Cf}]", "");
		}

		public static String getOrdinalNumber(Integer value) {
			
			if (OPLUtils.isObjectNullOrEmpty(value)) {
				return "";
			}

			if (value >= 11 && value <= 13) {
				return value + "th";    
			}
			
			int tenRemainder = value % 10;
			
			switch (tenRemainder) {
			case 1:
				return value + "st";
			case 2:
				return value + "nd";
			case 3:
				return value + "rd";
			default:
				return value + "th";
			}
		}
		
		public static String numberToWord(Object number) {
			Long longNumber = 0l;
	        try {
	        	if (OPLUtils.isObjectNullOrEmpty(number)) {
					return null;
				}
	            if (number instanceof Long)
	            	
	            	longNumber = ((Long) number);
	            else if (number instanceof Integer) {
	            	longNumber = ((Integer) number).longValue();
	            } else if (number instanceof Double) {
	            	longNumber = Double.valueOf(number.toString()).longValue();
	            } else if (number instanceof String) {
	            	longNumber = Long.valueOf((String) number);
	            }
	            return NumbersUtils.convertIntoWord(longNumber);
	        } catch (Exception e) {
	        	return null;
	        }
		}
		
		public static Double roundOffWithPlaces(Double value, Integer places) {
		    if (places < 0) throw new IllegalArgumentException();
		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}

}
