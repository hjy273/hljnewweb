package com.cabletech.linepatrol.trouble.services;

/**
 *故障模块常量
 */
public class TroubleConstant {
	public static final String TROUBLE_TYPE_SIM="0";//巡回
	public static final String TROUBLE_TYPE_SYS="1";//告知
	public static final String IS_GREAT_TROUBLE_N="0";//否
	public static final String IS_GREAT_TROUBLE_Y="1";//是重大故障
	
//	public static final String OUTSKIRT="1";//城区平台
//	public static final String CITY="0";//效区平台
	public static final String TEL_CITY="82893086 60775205";//城区平台联系电话
	public static final String TEL_OUTSKIRT="60515400";//效区平台联系电话
	
	
    public static final String TROUBLE_WATI_REPLY = "0";//待反馈
  //  public static final String TROUBLE_WATI_CLOSE = "1";//待关闭  无 主办的单子不需要审核，需要手动关闭
    public static final String TROUBLE_REPLY_WAIT_APPROVE = "10";//待平台核准
    public static final String TROUBLE_FEEDBACK = "30";//待审核反馈
   // public static final String TROUBLE_REPLY_APPROVE_PASS = "31";//审核通过
    public static final String TROUBLE_REPLY_WATI_EXAM = "40";//待考核
    public static final String TROUBLE_END = "50";//完成
   
    public static final String PROCESSUNIT_WAIT = "0";//待处理
    public static final String PROCESSUNIT_END = "1"; //处理结束
    
    
    
    public static final String REPLY_ROLE_JOIN = "0";//反馈单处理人角色协办
    public static final String REPLY_ROLE_HOST = "1";//反馈单处理人角色 主办

    public static final String TERMINAL_ADDRESS_CITY = "G20";//设备所属地城区
    public static final String TERMINAL_ADDRESS_OUTSKIRT = "G21";//设备所属地郊区
    
    //反馈单页面的处理人员类型
    public static final String REPLY_PROCESSER_RESPONSIBLE = "001";//001表示负责人
    public static final String REPLY_PROCESSER_TEST_MAN = "002";//002表示故障测试人员
    public static final String REPLY_PROCESSER_MEND_MAN = "003";//003表示光缆接续人员
    
    public static final String TEMP_SAVE = "00";//反馈单临时保存
    public static final String REPLY_APPROVE_WAIT = "0";//0反馈单待审核
    public static final String REPLY_APPROVE_NO = "1";//反馈单表示审核不通过
    public static final String REPLY_APPROVE_PASS = "2";//反馈单 表示审核通过
   // public static final String REPLY_APPROVE_CLOSE = "3";//反馈单 关闭
    public static final String REPLY_APPROVE_DISPATCH = "01";//反馈单等待平台审核
    
    public static final String REPLY_EXAM_STATE_NO="0";//0表示未进行评分；
    public static final String REPLY_EXAM_STATE_Y="1";//1表示已经进行评分
    
    public static final String APPROVE_RESULT_NO = "0";//'0表示审核不通过
    public static final String APPROVE_RESULT_PASS = "1";//1表示审核通过
    public static final String APPROVE_RESULT_TRANSFER = "2";//2表示转审'
    
    
    public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "03";//抄送人
    
   //public static final String IS_TRANSFER_N = "N";//是否进行过转审
   // public static final String IS_TRANSFER_Y = "Y";//是否进行过转审
    
    public static final String LP_TROUBLE_REPLY="LP_TROUBLE_REPLY";//反馈表
    public static final String LP_APPROVE_INFO="LP_APPROVE_INFO";//审核表
    public static final String LP_TROUBLE_INFO="LP_TROUBLE_INFO";//故障表
    public static final String LP_EVALUATE="LP_EVALUATE";//考核表
    
    
    public static final String LP_EVALUATE_TROUBLE="02";//02表示为故障管理评分
    
    
    //材料表
  //  public static final String MATERIAL_USE_TYPE="trouble";//材料使用类型
    
    public static final String TROUBLE_MODULE="trouble";//故障模块
    
    //数据字典 
    public static final String ASSORTMENT_TROUBLE_TYPE="lp_trouble_type";//故障类型
    public static final String ASSORTMENT_TERMINAL_ADDR="terminal_address";//故障设备所属地
    public static final String ASSORTMENT_TROUBLE_REASON="trouble_reason_id";//故障原因
    
    //故障指标
    public static final String QUOTA="1";//一干故障指标
    public static final String QUOTA_CITY="0";//城域网故障指标
    
    public static final double REPLY_RATE=5.5;//抢修历时（基准值5.5）	
    public static final double GREAT_TROUBLE_TIME=24;//城域网故障指标 故障反馈及时率（重大24小时；普通48小时）
    public static final double COMMON_TROUBLE_TIME=48;//
    
    
    
    
    
    
    
    
}
