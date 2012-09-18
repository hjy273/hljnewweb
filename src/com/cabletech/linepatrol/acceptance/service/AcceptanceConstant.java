package com.cabletech.linepatrol.acceptance.service;

public class AcceptanceConstant {
	public static final String MODULE = "LP_ACCEPTANCE_APPLY";
	public static final String SUBMODULE = "LP_ACCEPTANCE_SUBPROCESS";
//	public static final String FILES = "LP_ACCEPTANCE";
	public static final String CABLEFILE = "LP_ACCEPTANCE_CABLE";
	public static final String PIPEFILE = "LP_ACCEPTANCE_PIPE";
	
	public static final String CABLE = "1";         //光缆资源
	public static final String PIPE = "2";          //管道资源
	
	public static final String COMPLETED = "1";     //任务完成
	public static final String UNCOMPLETED = "0";   //任务未完成
	
//	public static final String MAINTENANCE = "y";   //已交维
//	public static final String NOTMAINTENANCE = "n";//未交维
	
	public static final String PASS = "y";          //通过验收
	public static final String NOPASS = "n";        //未通过验收
	
	public static final String ISRECORD = "1";      //已录入
	public static final String NOTRECORD = "0";     //未录入
	
	public static final String USABLE = "1";        //可用
	public static final String UNUSABLE = "0";      //不可用
	
	public static final String SPECIFY = "1";       //指定代维
	public static final String CHOOSE = "2";        //选择代维
	
	public static final String NOTPASSED = "0";     //验收未通过，审核未通过
	public static final String PASSED = "1";        //验收通过,审核通过
	public static final String REINSPECT = "2";     //复验中
	
	public static final String ALLOT = "10";        //开始，需要核准--主流程状态
	public static final String CLAIM = "20";        //选择，需要认领任务--主流程状态
	public static final String CHECK = "30";        //认领，需要核准任务--主流程状态
	public static final String RECORD = "40";       //核准，需要录入数据--主流程状态，子流程状态
	public static final String PUSH = "41";         //某一代维全部录完数据，需要移动审核--子流程状态
	public static final String APPROVE = "42";      //移动审批，需要评分--子流程状态
	public static final String EXAM = "43";         //评分
	public static final String SUBCOMPLETE = "45";  //子流程结束,等待其他子流程
	public static final String RECHECK = "46";      //复验核准  
	public static final String RERECORD = "44";     //复验录入
	public static final String ALLCOMPLETE = "00";  //全部完成--主流程状态
}
