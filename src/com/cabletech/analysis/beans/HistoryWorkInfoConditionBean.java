package com.cabletech.analysis.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.cabletech.commons.base.BaseBean;

/**
 * �û����������������Bean
 * 
 */
public class HistoryWorkInfoConditionBean extends BaseBean {
	private Logger logger =Logger.getLogger(this.getClass().getName()); // ����logger�������;
	/*
	 * ��ΧID��ʡ�ƶ���¼ʱ������Ϊ��11�����߾������ID���� ���ƶ���¼ʱ������Ϊ��12�����߹�Ͻ����ά��˾ID��
	 * �д�ά��¼ʱ������Ϊ��22������������ά����ID
	 */
	private String rangeID;

	/**
	 * ��Χ���ƣ�ʡ�ƶ���¼ʱ������Ϊ��ȫʡ���򡰻��߾���������ƣ��� ���ƶ���¼ʱ������Ϊ�û����ڵ������ƻ��߹�Ͻ����ά��˾���ƣ�
	 * �д�ά��¼ʱ������Ϊ�û����ڴ�ά��˾���ƻ���������ά��������
	 */
	private String rangeName;

	private String startDate; //��ʼ����

	private String endDate; //��������

	/**
	 * �õ���������
	 * 
	 * @return String
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * ���ý�������
	 * 
	 * @param endDate
	 *            ��������
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * �õ�rangeID
	 * 
	 * @return String
	 */
	public String getRangeID() {
		return rangeID;
	}

	/**
	 * ����rangeID
	 * 
	 * @param rangeID ��ΧID
	 */
	public void setRangeID(String rangeID) {
		this.rangeID = rangeID;
	}

	/**
	 * �õ�RangeName
	 * 
	 * @return String
	 */
	public String getRangeName() {
		return rangeName;
	}

	/**
	 * ����RangeName
	 * 
	 * @param rangeName
	 *            ��ΧID
	 */
	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}

	/**
	 * �õ���ʼ����
	 * 
	 * @return String
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * ���ÿ�ʼ����
	 * 
	 * @param startDate ��ʼ����
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
