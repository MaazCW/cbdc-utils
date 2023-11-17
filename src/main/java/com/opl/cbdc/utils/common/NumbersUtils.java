package com.opl.cbdc.utils.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumbersUtils {

	private NumbersUtils() {
	}

	private static final String[] tensNames = { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty",
			" Seventy", " Eighty", " Ninety" };
	private static final String[] numNames = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven",
			" Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };

	private static String convertIntoWordLessThanOneThousand(int number) {
		String soFar;
		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;
			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " Hundred" + soFar;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String convertIntoWord(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "Zero";
		}
		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 5));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(5, 7));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(7, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertIntoWordLessThanOneThousand(billions) + " Crore ";
			break;
		default:
			tradBillions = convertIntoWordLessThanOneThousand(billions) + " Crore ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertIntoWordLessThanOneThousand(millions) + " Lakh ";
			break;
		default:
			tradMillions = convertIntoWordLessThanOneThousand(millions) + " Lakh ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "One Thousand ";
			break;
		default:
			tradHundredThousands = convertIntoWordLessThanOneThousand(hundredThousands) + " Thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertIntoWordLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

}
