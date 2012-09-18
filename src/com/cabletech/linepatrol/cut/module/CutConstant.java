package com.cabletech.linepatrol.cut.module;

public class CutConstant {
	public static final String LP_CUT = "LP_CUT";//割接申请表
	public static final String LP_CUT_FEEDBACK = "LP_CUT_FEEDBACK";//割接申请反馈表
	public static final String LP_CUT_ACCEPTANCE = "LP_CUT_ACCEPTANCE";//割接申请验收结算表
	
	public static final String LP_APPROVE_INFO = "LP_APPROVE_INFO";//审核信息表
	
	public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "03";//抄送人
    
    public static final String LP_CUT_MODULE = "cut";//割接模块
	public static final String LP_CUT_FEEDBACK_MODULE = "feedback";//割接申请反馈模块
	public static final String LP_CUT_ACCEPTANCE_MODULE = "acceptance";//割接申请验收结算模块
	
	//派单
	public static final String LP_SENDTASK = "LP_SENDTASK";//任务派单
	public static final String LP_SENDTASKENDORSE = "LP_SENDTASKENDORSE";//任务派单签收
	public static final String LP_SENDTASKREPLY = "LP_SENDTASKREPLY";//派单任务恢复
	
	public static final String CUT_MODULE="cut";//割接模块
	
	public static final String LP_EVALUATE_CUT="03";//割接评分模块
	
	public static final String APPROVE_RESULT_NO = "0";// '0表示审核不通过
	public static final String APPROVE_RESULT_PASS = "1";// 1表示审核通过
	public static final String APPROVE_RESULT_TRANSFER = "2";// 2表示转审
}
