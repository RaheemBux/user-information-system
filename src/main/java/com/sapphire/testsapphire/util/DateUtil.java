package com.sapphire.testsapphire.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String dateFormate = "MM/dd/yyyy";
	public static final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);


	public static String dateToString(Date date) {
		if (date != null) {
			String formatedDate = sdf.format(date);

			return formatedDate;
		}
		return null;
	}

	public static Date stringToDate(String date) {
		Date parsedDate = null;
		if (date != null && !date.equals("")) {
			try {
				parsedDate = sdf.parse(date);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception : ",e);
			}
		}
		return parsedDate;
	}

	public static Double stringToDouble(String doubleValue) {
		Double test = 0.0;
		if (doubleValue != null && !doubleValue.equals("") && isDouble(doubleValue)) {
			test = Double.parseDouble(doubleValue);
		}
		return test;
	}

	static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("Exception : ",e);
			return false;
		}
	}
	public static Date stringToDate(String date, String format) {
		Date parsedDate = null;
		if (date != null && !date.equals("")) {
			try {
				parsedDate = new SimpleDateFormat(format).parse(date);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception : ",e);
			}
		}
		return parsedDate;
	}
	public static String dateToString(Date date, String format) {
		if (date != null) {
			String formatedDate = new SimpleDateFormat(format).format(date);

			return formatedDate;
		}
		return null;
	}
}
