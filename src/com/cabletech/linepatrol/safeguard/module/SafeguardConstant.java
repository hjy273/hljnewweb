package com.cabletech.linepatrol.safeguard.module;

public class SafeguardConstant {
	public static final String LP_SAFEGUARD_TASK = "LP_SAFEGUARD_TASK";//保障方案表
	public static final String LP_SAFEGUARD_PLAN = "LP_SAFEGUARD_PLAN";//保障总结表
	public static final String LP_SAFEGUARD_SUMMARY = "LP_SAFEGUARD_SUMMARY";//保障任务表
	public static final String LP_SPECIAL_ENDPLAN = "LP_SPECIAL_ENDPLAN";//保障方案终止表
	public static final String LP_SPECIAL_PLAN = "LP_SPECIAL_PLAN";//特巡计划表
	
	public static final String LP_APPROVE_INFO = "LP_APPROVE_INFO";//审核信息表
	
	public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "03";//抄送人
    
    public static final String LP_SAFEGUARD_TASK_MODULE = "task";//保障方案模块
	public static final String LP_SAFEGUARD_PLAN_MODULE = "plan";//保障总结模块
	public static final String LP_SAFEGUARD_SUMMARY_MODULE = "summary";//保障任务模块
	
	public static final String LP_SAFEGUARD_EVALUATE = "07";//保障模块评分实体类型
	
	public static final String APPROVE_RESULT_NO = "0";// '0表示审核不通过
	public static final String APPROVE_RESULT_PASS = "1";// 1表示审核通过
	public static final String APPROVE_RESULT_TRANSFER = "2";// 2表示转审
}
