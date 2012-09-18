package com.cabletech.commons.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	//����ȫ�ֿ��� ��һ�ܣ����ܣ���һ�ܵ������仯
    private  int weeks = 0;
    private int MaxDate;//һ���������
    private int MaxYear;//һ���������
	
	/**
	    * �õ��������ڼ�ļ������
	    */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	    * ����һ�����ڣ����������ڼ����ַ���
	    * 
	    * @param sdate
	    * @return
	    */
	public static String getWeek(String sdate) {
		// ��ת��Ϊʱ��
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour�д�ľ������ڼ��ˣ��䷶Χ 1~7
		// 1=������ 7=����������������
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	    * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd 
	    * 
	    * @param strDate
	    * @return
	    */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	    * ����ʱ��֮�������
	    * 
	    * @param date1
	    * @param date2
	    * @return
	    */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// ת��Ϊ��׼ʱ��
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// ���㵱�����һ��,�����ַ���
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, 1);//��һ���£���Ϊ���µ�1��
		lastDate.add(Calendar.DATE, -1);//��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ���µ�һ��
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//��Ϊ��ǰ�µ�1��
		lastDate.add(Calendar.MONTH, -1);//��һ���£���Ϊ���µ�1��
		//lastDate.add(Calendar.DATE,-1);//��ȥһ�죬��Ϊ�������һ��

		str = sdf.format(lastDate.getTime());
		return str;
	}

	//��ȡ���µ�һ��
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//��Ϊ��ǰ�µ�1��
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// ��ñ��������յ�����  
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	//��ȡ����ʱ�� 
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//���Է�����޸����ڸ�ʽ   
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// ��õ�ǰ�����뱾������������
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// ��ý�����һ�ܵĵڼ��죬�������ǵ�һ�죬���ڶ��ǵڶ���......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //��Ϊ���й����һ��Ϊ��һ�����������1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	//��ñ���һ������
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * �����Ӧ�ܵ�����������
	 * @return
	 */
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * ������������յ�����
	 * @return
	 */
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * �����������һ������
	 * @return
	 */
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * �����������һ������
	 * @return
	 */
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * ������������յ�����
	 * @return
	 */
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);//����������Ϊ���µ�һ�� 
		cd.roll(Calendar.DATE, -1);//���ڻع�һ�죬Ҳ�������һ�� 
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	/**
	 * ����������һ�������
	 * @return
	 */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);//��һ����
		lastDate.set(Calendar.DATE, 1);//����������Ϊ���µ�һ�� 
		lastDate.roll(Calendar.DATE, -1);//���ڻع�һ�죬Ҳ���Ǳ������һ�� 
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * ����¸��µ�һ�������
	 * @return String
	 */
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//��һ����
		lastDate.set(Calendar.DATE, 1);//����������Ϊ���µ�һ�� 
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * ����¸������һ�������
	 * @return
	 */
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//��һ����
		lastDate.set(Calendar.DATE, 1);//����������Ϊ���µ�һ�� 
		lastDate.roll(Calendar.DATE, -1);//���ڻع�һ�죬Ҳ���Ǳ������һ�� 
		str = sdf.format(lastDate.getTime());
		return str;
	}
	//��õ�ǰʱ�����һ��
	public static String getLastYear(){
		String str="";
		Calendar lastYear=Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);
		str=DateToString(lastYear.getTime(), "yyyy-MM-dd");
		return str;
	}

	//����������һ�������
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//��һ����
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	//��������һ�������
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//��һ����
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	//��ñ����ж�����
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//��������Ϊ�����һ��
		cd.roll(Calendar.DAY_OF_YEAR, -1);//�����ڻع�һ�졣
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//��õ�����һ���еĵڼ���
		cd.set(Calendar.DAY_OF_YEAR, 1);//��������Ϊ�����һ��
		cd.roll(Calendar.DAY_OF_YEAR, -1);//�����ڻع�һ�졣
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	//��ñ����һ�������
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	//��ñ������һ������� *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//���Է�����޸����ڸ�ʽ   
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	//��������һ������� *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//���Է�����޸����ڸ�ʽ   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	//����������һ�������
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		getThisSeasonTime(11);
		return preYearDay;
	}

	//��ñ�����
	public String getThisSeasonTime(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//���Է�����޸����ڸ�ʽ   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
				+ "-" + end_days;
		return seasonDate;

	}

	/**
	* ��ȡĳ��ĳ�µ����һ��
	* @param year ��
	* @param month ��
	* @return ���һ��
	*/
	private int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * �Ƿ�����
	 * @param year ��
	 * @return 
	 */
	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	///���ϵĴ���Ϊ�������ڲ���

	/**
	 * ȡ��ϵͳ��ǰʱ��,����ΪTimestamp
	 * @return Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		java.util.Date d = new java.util.Date();
		Timestamp numTime = new Timestamp(d.getTime());
		return numTime;
	}

	/**
	 * ȡ��ϵͳ��ǰʱ��,����ΪTimestamp
	 * @return Timestamp
	 */
	public static String getNowDateString() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		return formatter.format(date);
	}

	public static String getNowYearStr() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return formatter.format(date);
	}

	public static String getNowMonthStr() {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		return formatter.format(date);
	}

	/**
	 * ȡ��ϵͳ�ĵ�ǰʱ��,����Ϊjava.sql.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getNowDate() {
		java.util.Date d = new java.util.Date();
		return new java.sql.Date(d.getTime());
	}

	/**
	 * ��Timestamp����ת��Ϊyyyy/mm/dd���͵��ַ���
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String TimestampToString(Timestamp date, String strDefault) {
		String strTemp = strDefault;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	/**
	 * ��Timestamp����ת��Ϊyyyy/mm/dd���͵��ַ���,���Ϊnull,��Ż�""
	 * @param date
	 * @return
	 */
	public static String TimestampToString(Timestamp date) {
		return TimestampToString(date, null);
	}


	public static String DateToString(java.sql.Date date) {
		return DateToString(date, "yyyy/MM/dd");
	}

	/**
	 *date��ת��ΪString ��ʽΪyyyy/MM/dd
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString(java.sql.Date date, String fmt) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(fmt);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	public static String DateToString(java.util.Date date) {
		return DateToString(date, "yyyy/MM/dd");
	}

	/**
	 *date��ת��ΪString ��ʽΪyyyy/MM/dd
	 * @param date
	 * @param strDefault
	 * @return
	 */
	public static String DateToString(java.util.Date date, String strFmt) {
		String strTemp = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
			strTemp = formatter.format(date);
		}
		return strTemp;
	}

	


	/**
	 *Stringת��ΪTimestamp����
	 * @param strDefault
	 * @param date
	 * @return
	 */
	public static Timestamp StringToTimestamp(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date d = formatter.parse(strDate);
				Timestamp numTime = new Timestamp(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * ��ʱ���ַ���ת��Ϊjava.util.Date��ʽ
	 * @param strDate String ʱ���ַ���
	 * @param strFmt String  ʱ���ַ����ĸ�ʽ
	 * @return Date
	 */
	public static java.util.Date Str2UtilDate(String strDate, String strFmt) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
				java.util.Date dt = formatter.parse(strDate);
				return dt;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}

	public static String UtilDate2Str(java.util.Date dt, String strFmt) {
		String strDt = null;
		if (dt != null) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
				strDt = formatter.format(dt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return strDt;
	}

	public static String getNowDateString(String strFmt) {
		String strDt = null;
		java.util.Date dt = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
		strDt = formatter.format(dt);
		return strDt;
	}

	/**
	 * Stringת��Ϊjava.sql.date���ͣ�
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date StringToDate(String strDate, String strFmt) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
				java.util.Date d = formatter.parse(strDate);
				java.sql.Date numTime = new java.sql.Date(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * Stringת��Ϊjava.util.date���ͣ�
	 * @param strDate
	 * @return
	 */
	public static java.util.Date StringToUtilDate(String strDate, String strFmt) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(strFmt);
				return formatter.parse(strDate);
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * Stringת��Ϊjava.sql.date���ͣ�
	 * @param strDate
	 * @return
	 */
	public static java.sql.Date StringToDate(String strDate) {
		if (strDate != null && !strDate.equals("")) {
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date d = formatter.parse(strDate);
				java.sql.Date numTime = new java.sql.Date(d.getTime());
				return numTime;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * ȡ��������
	 * @param type int
	 * @param dateStr String
	 * @return String[]
	 */
	public static String[] parseStringForDate(int type, String dateStr) {
		String[] dateArr = new String[6];
		//type : 1, "2004/05/06" 2, "2004/05/06 11:22:33" , 3, "11:22:33"

		if (type == 1) {
			dateArr[0] = dateStr.substring(0, 4);
			dateArr[1] = dateStr.substring(5, 7);
			dateArr[2] = dateStr.substring(8, 10);

		} else {
			if (type == 2) {
				dateArr[0] = dateStr.substring(0, 4);
				dateArr[1] = dateStr.substring(5, 7);
				dateArr[2] = dateStr.substring(8, 10);

				dateArr[3] = dateStr.substring(11, 13);
				dateArr[4] = dateStr.substring(14, 16);
				dateArr[5] = dateStr.substring(17, 19);

			} else {
				if (type == 3) {
					dateArr[0] = dateStr.substring(0, 2);
					dateArr[1] = dateStr.substring(3, 5);
					dateArr[2] = dateStr.substring(6, 8);

				}
			}
		}

		return dateArr;

	}

	/**
	 * �Ƚ����������Ƿ���ָ���ķ�Χ֮��
	 * �����ָ����Χ�ڷ���ture������Ϊfalse
	 * @param dateStr
	 * @param i
	 * @return
	 */
	public static boolean compare(String dateStr, int i) {
		java.util.Date date = Str2UtilDate(dateStr, "yyyy-MM-dd");
		java.util.Date nowDate = new java.util.Date();
		long s = nowDate.getTime() - date.getTime();
		s = s / (24 * 60 * 60 * 1000);
		if (s <= 3)
			return true;
		else
			return false;
	}
	public static final int MONTH_NUM = 12;

	/**
	 * ��ʱ��ת��Ϊ�������� ���磺6:30 ת��Ϊ�����ʱ���������
	 * 
	 * @param time
	 *            ʱ���ַ��� 6:30
	 * @return long ���ص���6:30ʱ�ĺ�����
	 */
	public long strTimeToLong(String time) {

		String strCurrentTime = DateUtil.getNowDateString("yyyy-MM-dd") + " " + time;
		return DateUtil.StringToUtilDate(strCurrentTime, "yyyy-MM-dd HH:mm:ss").getTime();
	}

	/**
	 * ����������ת��Ϊʱ���ַ�����
	 * 
	 * @param time
	 *            long�͵�ʱ�������
	 * @param format
	 *            ���ڸ�ʽ�ַ�����
	 *            <li>���磺yyyy-MM-dd hh:mm:ss 2007-10-23 13:32:12
	 * @return String ����ʱ���ַ��� ��ʽHH:mm:ss
	 */
	public String longTostrTime(long time, String format) {
		return DateUtil.DateToTimeString(new Date(time), format);
	}

	/**
	 * ���ַ���ʱ�� ��format ��ʽ����ת����ת��Ϊlong�͡�
	 * @param date String
	 *            �������ڣ��磺2007-11-11
	 * @param format
	 *            ��ʽ :yyyy-mm-dd
	 * @return ����date��longֵ
	 */
	public long strDateToLong(String date, String format) {
		return DateUtil.StringToUtilDate(date, format).getTime();
	}

	/**
	 * ������ʱ���ʽת��Ϊ�������� ���磺2007-11-01 6:30:00ת��Ϊ��������
	 * 
	 * @param dateAndTime 
	 *            ʱ���ַ��� ��ʽ : 2007-11-01 6:30:00
	 * @return ����time��longֵ
	 */
	public long strDateAndTimeToLong(String dateAndTime) {
		return DateUtil.StringToUtilDate(dateAndTime, "yyyy-MM-dd HH:mm:ss").getTime();
	}

	/**
	 * ��ָ��������֮��N���µ��·� ���ظ�ʽ
	 * @param date  ����
	 * @param n N����
	 * @return  ���ص�N���µ��·������ַ��� 
	 */
	public static String getAfterNMonth(Date date, int n){
		return getAfterNMonth(date,"yyyy-MM-01",n);//year + "-" + startMonth + "-01";
	}
	/**
	 * ��ָ��������֮��N���µ��·� ���ظ�ʽ
	 * @param date ��׼����
	 * @param fmt �������ڸ�ʽ
	 * @param n
	 * @return ����fmt ��ʽ���з�������
	 */
	public static String getAfterNMonth(Date date,String fmt, int n)  {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(c.getTime());//year + "-" + startMonth + "-01";
	}

	/**
	 * ָ���·ݼ�����������ؼ����·�
	 * @param endMonth �·�
	 * @param subtrahend ����
	 * @return ���ؼ����·�
	 */
	@Deprecated
	public static int monthSubtract(int endMonth, int subtrahend) {
		int startMonth = endMonth - subtrahend;
		if (startMonth <= 0) {
			startMonth = MONTH_NUM - (subtrahend - endMonth); //12-(6-3)=10
		}
		return startMonth;
	}

	/**
	 * ��ʽ������
	 * @param dateStr �ַ�������,Ҫ�� ���ڸ�ʽΪ��yyyy/MM/dd
	 * @return ��������
	 */
	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}
	
	public static java.util.Date parseDateTime(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd HH:mm:ss");
	}
	/**
	 * ���ڸ�ʽת��Ϊ�����ַ���
	 * @param date
	 * @return Ĭ�ϸ�ʽΪ"yyyy-MM-dd HH:mm"
	 */
	public static String DateToTimeString(java.util.Date date) {
		return DateToTimeString(date,"yyyy-MM-dd HH:mm");
		
	}
	/**
	 * ���ڸ�ʽ��ȷ�����뼶ת��Ϊstring��
	 * @param date
	 * @param strFormat ע������ĸ�ʽ,yyyy-MM-dd HH:mm:ss
	 * @return  ���ش˸�ʽyyyy-MM-dd HH:mm:ss�ַ���
	 */
	public static String DateToTimeString(java.util.Date date,String fmt) {
		SimpleDateFormat formatter = new SimpleDateFormat(fmt);
		return formatter.format(date);
		
	}
	
	
	
	/**
	 * ��ʽ������
	 * 
	 * @param dateStr
	 *            �ַ�������
	 * @param formatStr
	 *            ��ʽ
	 * @return ��������
	 */
	public static java.util.Date parseDate(String dateStr, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡĳ���µ�����
	 * @param yearmonth  ��ʽ��yyyy/MM
	 * @return
	 */
	public static int getMonth4Days(String yearmonth) {
		String[] yearMonth = yearmonth.split("/");
		int year = Integer.parseInt(yearMonth[0]);
		int mont = Integer.parseInt(yearMonth[1]);
		Calendar calen = Calendar.getInstance();
		calen.set(Calendar.DATE, 1);
		calen.set(Calendar.YEAR, year);
		calen.set(Calendar.MONTH, mont - 1);
		int day = calen.getActualMaximum(Calendar.DAY_OF_MONTH);//���·ݵ�����
		return day;
	}

	/**
	 * ��ȡĳ�����ڵ���һ����
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @return
	 */
	public static String getNextMonth(Date date) {
		return getNextMonth(date,"yyyy/MM");
	}
	/**
	 * ��ȡĳ�����ڵ���һ����,������Ҫ�ĸ�ʽ����
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @param fmt ���ڸ�ʽ:yyyy/MM/dd
	 * @return
	 */
	public static String getNextMonth(Date date,String fmt) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.MONTH, +1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(calen.getTime());
	}

	/**
	 * ��ȡĳ�����ڵ���һ����
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @return 
	 */
	public static String getPrevMonth(Date date) {
		return getPrevMonth(date,"yyyy/MM");
	}
	/**
	 * ��ȡĳ�����ڵ���һ����,������Ҫ�ĸ�ʽ����
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @param fmt ���ڸ�ʽ:yyyy/MM/dd
	 * @return
	 */
	public static String getPrevMonth(Date date,String fmt) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.MONTH, -1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(calen.getTime());
	}
	/**
	 * ��ȡĳ�����ڵ���һ��
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @return
	 */
	public static String getNextYear(Date date){
		return getNextYear(date,"yyyy");
	}
	/**
	 * ��ȡĳ�����ڵ���һ��
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @param fmt ���ڸ�ʽ:yyyy/MM/dd
	 * @return
	 */
	public static String getNextYear(Date date,String fmt){
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.YEAR, +1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(calen.getTime());
	}
	/**
	 * ��ȡĳ�����ڵ���һ��
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @return
	 */
	public static String getPrevYear(Date date) {
		return getPrevYear(date,"yyyy");
	}
	/**
	 * ��ȡĳ�����ڵ���һ��
	 * @param date ���ڸ�ʽjava.util.Date 
	 * @param fmt ���ڸ�ʽ:yyyy/MM/dd
	 * @return
	 */
	public static String getPrevYear(Date date,String fmt) {
		Calendar calen = Calendar.getInstance();
		calen.setTime(date);
		calen.add(Calendar.YEAR, -1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(calen.getTime());
	}
	
	/**
	 * �õ�ָ����ǰ���󣩼������������������
	 * @param num
	 * @return
	 */
	public static Calendar getBeforeYears(int num){
		Calendar tmpCalendar=Calendar.getInstance();
		tmpCalendar.add(tmpCalendar.YEAR,-(num-1));
		return tmpCalendar;
	}
	/**
	 * �õ�ָ����ǰ���󣩼����µ�����,��������
	 * @param num
	 * @return
	 */
	public static Calendar getBeforeMonths(int num){
		Calendar calendar = Calendar.getInstance();
		String endTime = DateUtil.DateToString(calendar.getTime(), "yyyy/MM");
		Calendar tmpCalendar=Calendar.getInstance();
		tmpCalendar.add(tmpCalendar.MONTH,-num);
		return tmpCalendar;
	}
	
	/**
	 * ��õ�ǰ��ȥ���·�����
	 * @return
	 */
	public static int getElapseMonthNum(){
		Calendar calen = Calendar.getInstance();
		int num = calen.get(Calendar.MONTH);
		if(num < 1){
			return 12;
		}else{
			return num;
		}
	}
	
	
	/** 
	* �������ڼ������ 
	*  
	* @param days 
	*            ��������֮������� 
	* @return Date:��������֮��������������; 
	*/
	public static int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return (int) (ei / (1000 * 60 * 60 * 24));
	}

	public static void main(String[] args) {
		String startTime = "12:23:22";
		System.out.println("ʱ�䣺" + DateUtil.getNowDateString("yyyy-MM-dd") + " " + startTime);
		java.util.Date date = new java.util.Date();
		System.out.println("" + DateUtil.DateToTimeString(date, "HH:mm:ss"));
		compare("2010-01-29", 3);
			DateUtil tt = new DateUtil();
			System.out.println("��ȡ��������:"+tt.getNowTime("yyyy-MM-dd"));
			System.out.println("��ȡ����һ����:"+tt.getMondayOFWeek());
			System.out.println("��ȡ�����յ�����~:"+tt.getCurrentWeekday());
			System.out.println("��ȡ����һ����:"+tt.getPreviousWeekday());
			System.out.println("��ȡ����������:"+tt.getPreviousWeekSunday());
			System.out.println("��ȡ����һ����:"+tt.getNextMonday());
			System.out.println("��ȡ����������:"+tt.getNextSunday());
			System.out.println("�����Ӧ�ܵ�����������:"+tt.getNowTime("yyyy-MM-dd"));
			System.out.println("��ȡ���µ�һ������:"+tt.getFirstDayOfMonth());
			System.out.println("��ȡ�������һ������:"+tt.getDefaultDay());
			System.out.println("��ȡ���µ�һ������:"+tt.getPreviousMonthFirst());
			System.out.println("��ȡ�������һ�������:"+tt.getPreviousMonthEnd());
			System.out.println("��ȡ���µ�һ������:"+tt.getNextMonthFirst());
			System.out.println("��ȡ�������һ������:"+tt.getNextMonthEnd());
			System.out.println("��ȡ����ĵ�һ������:"+tt.getCurrentYearFirst());
			System.out.println("��ȡ�������һ������:"+tt.getCurrentYearEnd());
			System.out.println("��ȡȥ��ĵ�һ������:"+tt.getPreviousYearFirst());
			System.out.println("��ȡȥ������һ������:"+tt.getPreviousYearEnd());
			System.out.println("��ȡ�����һ������:"+tt.getNextYearFirst());
			System.out.println("��ȡ�������һ������:"+tt.getNextYearEnd());
			System.out.println("��ȡ�����ȵ�һ�쵽���һ��:"+tt.getThisSeasonTime(11));
			System.out.println("��ȡ��������֮��������2008-12-1~2008-9.29:"+DateUtil.getTwoDay("2008-12-1","2008-9-29"));
			System.out.println("��õ�ǰ���ڵ���һ�꣺"+tt.getLastYear());
	}

}
