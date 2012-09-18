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
	
	//用来全局控制 上一周，本周，下一周的周数变化
    private  int weeks = 0;
    private int MaxDate;//一月最大天数
    private int MaxYear;//一年最大天数
	
	/**
	    * 得到二个日期间的间隔天数
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
	    * 根据一个日期，返回是星期几的字符串
	    * 
	    * @param sdate
	    * @return
	    */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	    * 将短时间格式字符串转换为时间 yyyy-MM-dd 
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
	    * 两个时间之间的天数
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
		// 转换为标准时间
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

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);//加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);//减一个月，变为下月的1号
		//lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	//获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);//设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期  
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

	//获取当天时间 
	public String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);//可以方便地修改日期格式   
		String hehe = dateFormat.format(now);
		return hehe;
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; //因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	//获得本周一的日期
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
	 * 获得相应周的周六的日期
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
	 * 获得上周星期日的日期
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
	 * 获得上周星期一的日期
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
	 * 获得下周星期一的日期
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
	 * 获得下周星期日的日期
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
		cd.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		cd.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天 
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	/**
	 * 获得上月最后一天的日期
	 * @return
	 */
	public String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);//减一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月第一天的日期
	 * @return String
	 */
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//减一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 获得下个月最后一天的日期
	 * @return
	 */
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);//加一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天 
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天 
		str = sdf.format(lastDate.getTime());
		return str;
	}
	//获得当前时间的上一年
	public static String getLastYear(){
		String str="";
		Calendar lastYear=Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);
		str=DateToString(lastYear.getTime(), "yyyy-MM-dd");
		return str;
	}

	//获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	//获得明年第一天的日期
	public String getNextYearFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);//加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		str = sdf.format(lastDate.getTime());
		return str;

	}

	//获得本年有多少天
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);//把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);//把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	//获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	//获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式   
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	//获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	//获得上年最后一天的日期
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

	//获得本季度
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
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");//可以方便地修改日期格式   
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;//years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month
				+ "-" + end_days;
		return seasonDate;

	}

	/**
	* 获取某年某月的最后一天
	* @param year 年
	* @param month 月
	* @return 最后一天
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
	 * 是否闰年
	 * @param year 年
	 * @return 
	 */
	public boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	///以上的代码为新增日期操作

	/**
	 * 取得系统当前时间,类型为Timestamp
	 * @return Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		java.util.Date d = new java.util.Date();
		Timestamp numTime = new Timestamp(d.getTime());
		return numTime;
	}

	/**
	 * 取得系统当前时间,类型为Timestamp
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
	 * 取得系统的当前时间,类型为java.sql.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date getNowDate() {
		java.util.Date d = new java.util.Date();
		return new java.sql.Date(d.getTime());
	}

	/**
	 * 从Timestamp类型转化为yyyy/mm/dd类型的字符串
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
	 * 从Timestamp类型转化为yyyy/mm/dd类型的字符串,如果为null,侧放回""
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
	 *date型转化为String 格式为yyyy/MM/dd
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
	 *date型转化为String 格式为yyyy/MM/dd
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
	 *String转化为Timestamp类型
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
	 * 将时间字符串转化为java.util.Date格式
	 * @param strDate String 时间字符串
	 * @param strFmt String  时间字符串的格式
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
	 * String转化为java.sql.date类型，
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
	 * String转化为java.util.date类型，
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
	 * String转化为java.sql.date类型，
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
	 * 取得年月日
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
	 * 比较两个日期是否在指定的范围之内
	 * 如果在指定范围内返回ture，否则为false
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
	 * 将时间转换为毫秒数。 例如：6:30 转换为当天的时间毫秒数。
	 * 
	 * @param time
	 *            时间字符串 6:30
	 * @return long 返回当日6:30时的毫秒数
	 */
	public long strTimeToLong(String time) {

		String strCurrentTime = DateUtil.getNowDateString("yyyy-MM-dd") + " " + time;
		return DateUtil.StringToUtilDate(strCurrentTime, "yyyy-MM-dd HH:mm:ss").getTime();
	}

	/**
	 * 将毫秒数的转换为时间字符串。
	 * 
	 * @param time
	 *            long型的时间毫秒数
	 * @param format
	 *            日期格式字符串。
	 *            <li>例如：yyyy-MM-dd hh:mm:ss 2007-10-23 13:32:12
	 * @return String 返回时间字符串 格式HH:mm:ss
	 */
	public String longTostrTime(long time, String format) {
		return DateUtil.DateToTimeString(new Date(time), format);
	}

	/**
	 * 将字符串时间 以format 格式进行转换，转换为long型。
	 * @param date String
	 *            传入日期，如：2007-11-11
	 * @param format
	 *            格式 :yyyy-mm-dd
	 * @return 返回date的long值
	 */
	public long strDateToLong(String date, String format) {
		return DateUtil.StringToUtilDate(date, format).getTime();
	}

	/**
	 * 将传入时间格式转换为毫秒数。 例如：2007-11-01 6:30:00转换为毫秒数。
	 * 
	 * @param dateAndTime 
	 *            时间字符串 格式 : 2007-11-01 6:30:00
	 * @return 返回time的long值
	 */
	public long strDateAndTimeToLong(String dateAndTime) {
		return DateUtil.StringToUtilDate(dateAndTime, "yyyy-MM-dd HH:mm:ss").getTime();
	}

	/**
	 * 将指定日期数之后N个月的月份 返回格式
	 * @param date  日期
	 * @param n N个月
	 * @return  返回的N个月的月份日期字符串 
	 */
	public static String getAfterNMonth(Date date, int n){
		return getAfterNMonth(date,"yyyy-MM-01",n);//year + "-" + startMonth + "-01";
	}
	/**
	 * 将指定日期数之后N个月的月份 返回格式
	 * @param date 基准日期
	 * @param fmt 返回日期格式
	 * @param n
	 * @return 根据fmt 格式进行返回日期
	 */
	public static String getAfterNMonth(Date date,String fmt, int n)  {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt);
		return simpleDateFormat.format(c.getTime());//year + "-" + startMonth + "-01";
	}

	/**
	 * 指定月份减间隔数，返回减后月份
	 * @param endMonth 月份
	 * @param subtrahend 减数
	 * @return 返回减后月份
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
	 * 格式化日期
	 * @param dateStr 字符型日期,要求 日期格式为：yyyy/MM/dd
	 * @return 返回日期
	 */
	public static java.util.Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}
	
	public static java.util.Date parseDateTime(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd HH:mm:ss");
	}
	/**
	 * 日期格式转换为日期字符串
	 * @param date
	 * @return 默认格式为"yyyy-MM-dd HH:mm"
	 */
	public static String DateToTimeString(java.util.Date date) {
		return DateToTimeString(date,"yyyy-MM-dd HH:mm");
		
	}
	/**
	 * 日期格式精确到毫秒级转换为string型
	 * @param date
	 * @param strFormat 注意这里的格式,yyyy-MM-dd HH:mm:ss
	 * @return  返回此格式yyyy-MM-dd HH:mm:ss字符串
	 */
	public static String DateToTimeString(java.util.Date date,String fmt) {
		SimpleDateFormat formatter = new SimpleDateFormat(fmt);
		return formatter.format(date);
		
	}
	
	
	
	/**
	 * 格式化日期
	 * 
	 * @param dateStr
	 *            字符型日期
	 * @param formatStr
	 *            格式
	 * @return 返回日期
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
	 * 获取某个月的天数
	 * @param yearmonth  格式：yyyy/MM
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
		int day = calen.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
		return day;
	}

	/**
	 * 获取某个日期的下一个月
	 * @param date 日期格式java.util.Date 
	 * @return
	 */
	public static String getNextMonth(Date date) {
		return getNextMonth(date,"yyyy/MM");
	}
	/**
	 * 获取某个日期的下一个月,根据需要的格式返回
	 * @param date 日期格式java.util.Date 
	 * @param fmt 日期格式:yyyy/MM/dd
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
	 * 获取某个日期的上一个月
	 * @param date 日期格式java.util.Date 
	 * @return 
	 */
	public static String getPrevMonth(Date date) {
		return getPrevMonth(date,"yyyy/MM");
	}
	/**
	 * 获取某个日期的上一个月,根据需要的格式返回
	 * @param date 日期格式java.util.Date 
	 * @param fmt 日期格式:yyyy/MM/dd
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
	 * 获取某个日期的下一年
	 * @param date 日期格式java.util.Date 
	 * @return
	 */
	public static String getNextYear(Date date){
		return getNextYear(date,"yyyy");
	}
	/**
	 * 获取某个日期的下一年
	 * @param date 日期格式java.util.Date 
	 * @param fmt 日期格式:yyyy/MM/dd
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
	 * 获取某个日期的上一年
	 * @param date 日期格式java.util.Date 
	 * @return
	 */
	public static String getPrevYear(Date date) {
		return getPrevYear(date,"yyyy");
	}
	/**
	 * 获取某个日期的上一年
	 * @param date 日期格式java.util.Date 
	 * @param fmt 日期格式:yyyy/MM/dd
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
	 * 得到指定的前（后）几年的日历，包含今年
	 * @param num
	 * @return
	 */
	public static Calendar getBeforeYears(int num){
		Calendar tmpCalendar=Calendar.getInstance();
		tmpCalendar.add(tmpCalendar.YEAR,-(num-1));
		return tmpCalendar;
	}
	/**
	 * 得到指定的前（后）几个月的数字,不含本月
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
	 * 获得当前逝去的月份数。
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
	* 两个日期间的天数 
	*  
	* @param days 
	*            距离现在之后的天数 
	* @return Date:距离现在之后的若干天的日期; 
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
		System.out.println("时间：" + DateUtil.getNowDateString("yyyy-MM-dd") + " " + startTime);
		java.util.Date date = new java.util.Date();
		System.out.println("" + DateUtil.DateToTimeString(date, "HH:mm:ss"));
		compare("2010-01-29", 3);
			DateUtil tt = new DateUtil();
			System.out.println("获取当天日期:"+tt.getNowTime("yyyy-MM-dd"));
			System.out.println("获取本周一日期:"+tt.getMondayOFWeek());
			System.out.println("获取本周日的日期~:"+tt.getCurrentWeekday());
			System.out.println("获取上周一日期:"+tt.getPreviousWeekday());
			System.out.println("获取上周日日期:"+tt.getPreviousWeekSunday());
			System.out.println("获取下周一日期:"+tt.getNextMonday());
			System.out.println("获取下周日日期:"+tt.getNextSunday());
			System.out.println("获得相应周的周六的日期:"+tt.getNowTime("yyyy-MM-dd"));
			System.out.println("获取本月第一天日期:"+tt.getFirstDayOfMonth());
			System.out.println("获取本月最后一天日期:"+tt.getDefaultDay());
			System.out.println("获取上月第一天日期:"+tt.getPreviousMonthFirst());
			System.out.println("获取上月最后一天的日期:"+tt.getPreviousMonthEnd());
			System.out.println("获取下月第一天日期:"+tt.getNextMonthFirst());
			System.out.println("获取下月最后一天日期:"+tt.getNextMonthEnd());
			System.out.println("获取本年的第一天日期:"+tt.getCurrentYearFirst());
			System.out.println("获取本年最后一天日期:"+tt.getCurrentYearEnd());
			System.out.println("获取去年的第一天日期:"+tt.getPreviousYearFirst());
			System.out.println("获取去年的最后一天日期:"+tt.getPreviousYearEnd());
			System.out.println("获取明年第一天日期:"+tt.getNextYearFirst());
			System.out.println("获取明年最后一天日期:"+tt.getNextYearEnd());
			System.out.println("获取本季度第一天到最后一天:"+tt.getThisSeasonTime(11));
			System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"+DateUtil.getTwoDay("2008-12-1","2008-9-29"));
			System.out.println("获得当前日期的上一年："+tt.getLastYear());
	}

}
