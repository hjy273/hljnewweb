package com.cabletech.linepatrol.appraise.service;

public class AppraiseConstant {
	
	//考核表类型
	public static final String APPRAISE_MONTH="1"; //月考核表
	public static final String APPRAISE_SPECIAL="2"; //专项考核表
	public static final String APPRAISE_YEAR="3";  //年终考核表
	public static final String APPRAISE_YEAREND="4";//年终检查
	
	public static final String APPRAISE_TABLE_VM_PATH="com/cabletech/linepatrol/appraise/vm/appraiseTable.vm";
	public static final String APPRAISE_GRADE_VM_PATH="com/cabletech/linepatrol/appraise/vm/appraiseGrade.vm";
	
	public static final String FLAG_VIEW="view";
	public static final String FLAG_IMPORT="import";
	public static final String FLAG_EDIT="edit";
	public static final String FLAG_SUBMIT="submit";
	public static final String FLAG_INPUT="input";
	public static final String FLAG_VERIFY="verify";
	
	public static final String TYPE_MONTH="Month";
	public static final String TYPE_SPECIAL="Special";
	public static final String TYPE_YEAR="Year";
	public static final String TYPE_DAILY="Daily";
	public static final String TYPE_YEAREND="YearEnd";
	
	public static final String APPRAISENAME_DAILY="日常考核";
	public static final String APPRAISENAME_SPECIAL="专项日常考核";
	public static final String APPRAISENAME_YEAREND="年终检查";
	
	public static final String STATE_WAIT_SEND="0";//待下发确认
	public static final String STATE_WAIT_VERIFY="1";//待代维确认
	public static final String STATE_VERIFY_PASS="2";//代维确认通过
	public static final String STATE_VERIFY_NOT_PASS="3";//代维确认未通过
	public static final String CONTENT_TYPE = "application/vnd.ms-excel";
}
