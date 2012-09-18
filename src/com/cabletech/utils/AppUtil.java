package com.cabletech.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppUtil {

	// �ַ���Ϊ��ʱ�ĸ�ֵ
	public static String nvl(String strChk) {
		return nvl(strChk, "");
	}

	// �ַ���Ϊ��ʱ�ĸ�ֵ
	public static String nvl(String strChk, String strExp) {
		if (isNull(strChk))
			return strExp;
		return strChk;
	}

	// �ж��ַ����Ƿ�Ϊ��
	public static boolean isNull(String strExp) {
		if (strExp == null || strExp.length() == 0)
			return true;
		return false;
	}

	// �ж��ַ����Ƿ������ڣ������գ�
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

	// �ж��Ƿ������ڣ������գ�
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

	// �ж��ַ����Ƿ���һ���ڹ涨�����ڵ�float
	public static boolean isFloat(String strExp, int intWidth) {
		if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
			return false;
		return isFloat(strExp);
	}

	// �ж��ַ����Ƿ���һ���ڹ涨�����ڵ�float
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

	// �ж��ַ����Ƿ���һ��float
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

	// �ж��ַ����Ƿ���һ���ڹ涨�����ڵ�integer
	public static boolean isInteger(String strExp, int intWidth) {
		if (isNull(strExp) || intWidth <= 0 || strExp.length() > intWidth)
			return false;
		return isInteger(strExp);
	}

	// �ж��ַ����Ƿ���һ��integer
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

	// �ж��ַ����Ƿ���һ����ֵ
	public static boolean isNumeric(String strExp) {
		if (isFloat(strExp) || isInteger(strExp))
			return true;
		return false;
	}

	// �ж��ַ����Ƿ��ڹ涨������
	public static boolean isLegalLen(String strExp, int intWidth) {
		if (isNull(strExp))
			return true;

		if (strExp.length() > intWidth)
			return false;
		return true;
	}

	// �ж��ַ����Ƿ���һ����������
	public static boolean isEmail(String strExp) {
		int intFind = strExp.indexOf("@");
		if (intFind <= 0 || intFind >= strExp.length() - 1)
			return false;
		return true;
	}
}
