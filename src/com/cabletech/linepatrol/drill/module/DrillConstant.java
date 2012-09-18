package com.cabletech.linepatrol.drill.module;

public class DrillConstant {
	public static final String LP_DRILLPLAN = "LP_DRILLPLAN";//演练方案表
	public static final String LP_DRILLSUMMARY = "LP_DRILLSUMMARY";//演练总结表
	public static final String LP_DRILLTASK = "LP_DRILLTASK";//演练任务表
	public static final String LP_DRILLPLAN_MODIFY = "LP_DRILLPLAN_MODIFY";//演练方案变更表
	
	public static final String LP_APPROVE_INFO = "LP_APPROVE_INFO";//审核信息表
	
	public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "03";//抄送人
    
    public static final String LP_DRILLPLAN_MODULE = "plan";//演练方案模块
	public static final String LP_DRILLSUMMARY_MODULE = "summary";//演练总结模块
	public static final String LP_DRILLTASK_MODULE = "task";//演练任务模块
	
	public static final String LP_DRILL_EVALUATE = "05";//演练模块评分实体类型
	
	public static final String APPROVE_RESULT_NO = "0";// '0表示审核不通过
	public static final String APPROVE_RESULT_PASS = "1";// 1表示审核通过
	public static final String APPROVE_RESULT_TRANSFER = "2";// 2表示转审
}
