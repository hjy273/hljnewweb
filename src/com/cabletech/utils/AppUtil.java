package com.cabletech.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppUtil {

	// 字符串为空时的赋值
	public static String nvl(String strChk) {
		return nvl(strChk, "");
	}

	// 字符串为空时的赋值
	public static String nvl(String strChk, String strExp) {
		if (isNull(strChk))
			return strExp;
		return strChk;
	}

	// 判断字符串是否为空
	public static boolean isNull(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return true;
		return false;
	}

	// 判断字符串是否是日期（年月日）
	public static boolean isDate(String strExp) {
		if (strExp.length() != 10)
			return false;

		try {
			Calendar cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.set(Integer.parseInt(strExp.substring(0, 4)), Integer.parseInt(strExp.substring(5, 7)) - 1, Integer
					.parseInt(strExp.substring(8, 10)));
			Date ud = cal.getTime();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 判断是否是日期（年月日）
	public static boolean isDate(int intYear, int intMonth, int intDay) {
		try {
			Calendar cal = new GregorianCalendar();
			cal.setLenient(false);
			cal.set(intYear, intMonth, intDay);
			Date ud = cal.getTime();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 判断字符串是否是一个在规定长度内的float
	public static boolean isFloat(String strExp, int intWidth) {
		if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
			return false;
		return isFloat(strExp);
	}

	// 判断字符串是否是一个在规定长度内的float
	public static boolean isFloat(String strExp, int intWidth, int intZero) {
		if (isFloat(strExp) == true) {
			int i = strExp.indexOf(".");
			if (i < 0) {
				if (strExp.length() > intWidth)
					return false;
				else
					return true;
			}

			if (strExp.length() - i - 1 > intZero)
				return false;
			return true;
		}
		return false;
	}

	// 判断字符串是否是一个float
	public static boolean isFloat(String strExp) {
		if (isNull(strExp))
			return false;

		try {
			Float.valueOf(strExp);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 判断字符串是否是一个在规定长度内的integer
	public static boolean isInteger(String strExp, int intWidth) {
		if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
			return false;
		return isInteger(strExp);
	}

	// 判断字符串是否是一个integer
	public static boolean isInteger(String strExp) {
		if (isNull(strExp))
			return false;

		try {
			Integer.valueOf(strExp);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// 判断字符串是否是一个数值
	public static boolean isNumeric(String strExp) {
		if (isFloat(strExp) || isInteger(strExp))
			return true;
		return false;
	}

	// 判断字符串是否在规定长度内
	public static boolean isLegalLen(String strExp, int intWidth) {
		if (isNull(strExp))
			return true;

		if (strExp.length() > intWidth)
			return false;
		return true;
	}

	// 判断字符串是否是一个电子信箱
	public static boolean isEmail(String strExp) {
		int intFind = strExp.indexOf("@");
		if (intFind <= 0 || intFind >= strExp.length() - 1)
			return false;
		return true;
	}
}
