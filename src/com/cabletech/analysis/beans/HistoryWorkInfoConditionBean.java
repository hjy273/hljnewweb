package com.cabletech.analysis.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBean;

/**
 * 用户界面上输入的条件Bean
 * 
 */
public class HistoryWorkInfoConditionBean extends BaseBean {
	private Logger logger =Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;
	/*
	 * 范围ID（省移动登录时，可能为“11“或者具体地市ID）， 市移动登录时，可能为”12“或者管辖各代维公司ID，
	 * 市代维登录时，可能为”22“或者下属各维护组ID
	 */
	private String rangeID;

	/**
	 * 范围名称（省移动登录时，可能为“全省区域“或者具体地市名称）， 市移动登录时，可能为用户所在地市名称或者管辖各代维公司名称，
	 * 市代维登录时，可能为用户所在代维公司名称或者下属各维护组名称
	 */
	private String rangeName;

	private String startDate; //开始日期

	private String endDate; //结束日期

	/**
	 * 得到结束日期
	 * 
	 * @return String
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 * 
	 * @param endDate
	 *            结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 得到rangeID
	 * 
	 * @return String
	 */
	public String getRangeID() {
		return rangeID;
	}

	/**
	 * 设置rangeID
	 * 
	 * @param rangeID 范围ID
	 */
	public void setRangeID(String rangeID) {
		this.rangeID = rangeID;
	}

	/**
	 * 得到RangeName
	 * 
	 * @return String
	 */
	public String getRangeName() {
		return rangeName;
	}

	/**
	 * 设置RangeName
	 * 
	 * @param rangeName
	 *            范围ID
	 */
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	/**
	 * 得到开始日期
	 * 
	 * @return String
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 设置开始日期
	 * 
	 * @param startDate 开始日期
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setBeanAccordtoCondition(String rangeID,String year,String month){
		this.rangeID = rangeID;
		int intMonth = Integer.parseInt(month);
		int lastMonthDay = 31;
//		logger.info(rangeID + "," + year + "," + month);
		this.startDate = year + "-" + month + "-01";
		 GregorianCalendar gc = new GregorianCalendar();
		 switch(intMonth)   
         {   
             case   1:   
             case   3:   
             case   5:   
             case   7:   
             case   8:   
             case   10:   
             case   12:   
            	 lastMonthDay = 31;   
                 break;   
             case   4:   
             case   6:   
             case   9:   
             case   11:   
            	 lastMonthDay = 30;   
                 break;   
             case   2:   
                 if (gc.isLeapYear(Integer.parseInt(year))){
                	 lastMonthDay   =   29;   
                 }else {
                	 lastMonthDay   =   28;   
                 }
                 break;   
         }   
        this.endDate = year + "-" + month + "-" + lastMonthDay;
	}
}
