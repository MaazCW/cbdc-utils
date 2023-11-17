package com.opl.cbdc.utils.common;

import org.slf4j.*;

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class DateUtils {

	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public interface DateFormat {
		public static final String DD_MMMM_YYYY_HH_MM_AAA = "dd MMMM yyyy hh:mm aaa"; // 02 May 2020 11:00 AM
		public static final String DD_MM_YYYY = "dd/MM/yyyy"; // 25/08/2020
		public static final String DD_MMM_YYYY = "dd-MMM-yyyy"; // 29-Sep-1988
		public static final String YYYY_MM_DD = "yyyy-MM-dd"; // 1988-09-29
		public static final String DD_M_YYYY_HH_MM_SS = "dd-M-yyyy hh:mm:ss"; // 02-1-2018 06:07:59
		public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"; // 1988-09-29 06:07:59
		
	}

	public static String setDateFormat(String dateFormat, Date date) {
		try {
			if (OPLUtils.isObjectNullOrEmpty(dateFormat)) {
				dateFormat = DateFormat.DD_MMMM_YYYY_HH_MM_AAA;
			}
			return new SimpleDateFormat(dateFormat).format(date);
		} catch (Exception e) {
			logger.error("Exception while set date format " + e.getMessage());
			return null;
		}
	}
	
	public static Date parse(String date, String dateFormat) {
		try {
			return new SimpleDateFormat(dateFormat).parse(date);
		} catch (Exception e) {
			logger.info("Input date -->" + date + "-----Dateformat----->" + dateFormat);
			logger.error("Exception while parse date -->" + e.getMessage());
		}
		return null;
	}

	public static Integer monthDiff(Date fromDate, Date toDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(toDate);

		Period between = Period.between(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1),
				LocalDate.of(c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), 1));
		return between.getMonths();
	}

	public static long dateDiff(Date from, Date to) {
		long diffMiliSec = from.getTime() - to.getTime();
		return TimeUnit.DAYS.convert(diffMiliSec, TimeUnit.MILLISECONDS);
	}

	public static Integer getAgeFromBirthDate(Date date) {
		if (date != null) {
			Integer years = 0;
			Calendar birthDay = Calendar.getInstance();
			birthDay.setTime(date);
			Calendar today = Calendar.getInstance();
			today.setTime(new Date());

			Integer yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
			Integer monthsDiff = 1;
			// monthsDiff = monthsDiff + today.get(Calendar.MONTH) - 12;
			monthsDiff = monthsDiff + today.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
			Integer ageInMonths = yearsInBetween * 12 + monthsDiff;
			years = ageInMonths / 12;
			return years;
		} else {
			return null;
		}
	}

}
