package com.cabletech.linepatrol.hiddanger.service;

public class HiddangerConstant {
	//隐患评级
	public static final String IGNORE = "0";
	public static final String FIRST = "1";
	public static final String SECOND = "2";
	public static final String THIRD = "3";
	public static final String FOURTH = "4";
	
	public static final String REGIST = "10";         //需要评级
	public static final String REPORT = "20";         //需要上报
	public static final String GENERAL = "30";        //需要上报
	public static final String TREAT = "40";          //需要处理
	public static final String NEEDAPPROVE = "50";    //需要审核
	public static final String MAKEPLAN = "51";       //需要制定计划
	public static final String PLANAPPROVE = "52";    //需要计划审核
	public static final String WAIT = "53";           //等待计划完成
	public static final String ENDPLAN = "54";        //等待终止计划审核
	public static final String CLOSE = "60";          //需要关闭
	public static final String CLOSEAPPROVED = "70";  //需要审核
	public static final String EVALUATE = "00";       //需要评估
	public static final String COMPLETE = "0";        //已完成
	
	//盯防计划状态
	public static final String NEEDMAKEPLAN = "0";    //需要制定计划
	public static final String MADEPLAN = "1";  	  //已经完成盯防计划
	
	//评估类型
	public static final String EVALUATETYPE = "01";
	
	public static final String MODULE = "hiddtroubleWatch";
}
