package com.cabletech.linepatrol.dispatchtask.module;

public class DispatchTaskConstant {
	public static String CONTENT_TYPE = "application/vnd.ms-excel";

	// 派单
	public static final String LP_SENDTASK = "LP_SENDTASK";// 任务派单
	public static final String LP_SENDTASKENDORSE = "LP_SENDTASKENDORSE";// 任务派单签收
	public static final String LP_SENDTASKREPLY = "LP_SENDTASKREPLY";// 派单任务回复
	public static final String LP_VALIDATEREPLY = "LP_VALIDATEREPLY";// 派单任务验证

	public static final String NOT_TRANSFER = "0";
	public static final String IS_TRANSFER = "1";
	public static final String NOT_ALLOW_TRANSFER = "2";

	public static final String REFUSE_ACTION = "01";
	public static final String SIGNIN_ACTION = "00";
	public static final String TRANSFER_ACTION = "02";
	
	public static final String NOT_PASSED = "1";
	public static final String PASSED = "0";
	public static final String TRANFER_APPROVE = "2";
	
	public static final String ALWAYS_CHECKED = "1";
	public static final String NOT_END_STATE = "000";
	public static final String END_STATE = "999";
	public static final String CANCEL_STATE = "888";//取消状态
	
	public static final String OUT_TIME_CONDITION = "-1";
	public static final String IN_TIME_CONDITION = "0";
	
	public static final String CHECK_OUT_TIME_STD = "2";

	public static final String USER_SUMMIZE_CONDITION = "user_summize";
	public static final String DEPT_SUMMIZE_CONDITION = "dept_summize";
	
	public static final String UNEXAM = "0";	//未考核
	public static final String EXAMED = "1";	//已考核
}
