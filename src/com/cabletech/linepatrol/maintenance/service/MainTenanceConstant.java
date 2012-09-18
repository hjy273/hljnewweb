package com.cabletech.linepatrol.maintenance.service;

/**
 *技术维护模块常量
 */
public class MainTenanceConstant {
	
	//计划类型
    public static final String LINE_TEST="1";//  备纤测试
    public static final String STATION_TEST="2";//    接地电阻测试
    public static final String STATION_METAL_TEST="3";//金属护套绝缘电阻测试
    
 /*   //计划测试端
    public static final String CABLE_PORT_X="0";//  XX端
    public static final String CABLE_PORT_y="1";//  YY端
*/	
    
    public static final String PLAN_NO_SUBMITTED="1";//  计划编辑状态
    public static final String PLAN_YES_SUBMITTED="0";//  计划编辑状态
    
	//计划状态
    public static final String PLAN_WAIT_SUBMIT="00";//  计划编辑状态
    public static final String PLAN_WAIT_APPROVE="0";//  计划待审核
    public static final String PLAN_APPROVE_NO="1";//  计划审核不通过
    public static final String LOGGING_DATA_WAIT="20";//   录入数据
    public static final String LOGGING_DATA_NO_OVER="21";//未录完数据
    public static final String LOGGING_WAIT_APPROVE="30";//录入待审核
    public static final String LOGGING_APPROVE_NO_PASS="31";//录入审核不通过
    public static final String WAIT_EXAM="40";//待考核
    public static final String TEST_PLAN_END="50";//完成
    
    public static final String APPROVE_RESULT_NO = "0";//'0表示审核不通过
    public static final String APPROVE_RESULT_PASS = "1";//1表示审核通过
    public static final String APPROVE_RESULT_TRANSFER = "2";//2表示转审'
    

    //接地电阻信息表、备纤信息表 的录入状态
    public static final String ENTERING_N="0";//未录入
    public static final String ENTERING_Y="1";//已录入
    public static final String ENTERING_TEMP="2";//暂存
    
    //数据录入
    public static final String TEST_DATA_APPROVE_WAIT="0";//待审核
    public static final String TEST_DATA_APPROVE_NO_PASS="1";//不通过审核
    public static final String TEST_DATA_APPROVE_PASS="2";//审核通过

    //电阻录入数据测试方法
    public static final String TEST_METHOD_TAB="0";// 钳表法
    public static final String TEST_METHOD_THREE="1";//三极法
    
    
    //问题中继段状态
    public static final String PROBLEM_STATE_N="0";//未解决
    public static final String PROBLEM_STATE_Y="1";//已解决
    
    
    
    public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "03";//抄送人
    
    public static final String LP_TEST_PLAN="LP_TEST_PLAN";//测试计划表
    public static final String LP_TEST_DATA="LP_TEST_DATA";//测试录入数据信息表
    public static final String LP_TEST_CHIP_DATA="LP_TEST_CHIP_DATA";//纤芯录入数据信息表
  
    public static final String MAINTENANCE_MODULE="maintence";//技术维护模块
    public static final String LP_APPROVE_INFO="LP_APPROVE_INFO";//审核表
    public static final String LP_EVALUATE="LP_EVALUATE";//考核表
    
    
    public static final String LP_EVALUATE_TEST_PLAN="06";//06表示为技术维护 考核评分

    
    
  //年计划状态
    public static final String YEAR_PLAN_WAIT_APPROVE="0";//  年计划待待审核
    public static final String YEAR_PLAN_APPROVE_NO="1";//  计划审核不通过
    public static final String YEAR_PLAN_APPROVE_PASS="2";//  计划审核通过
    public static final String LP_TEST_YEAR_PLAN="LP_TEST_YEAR_PLAN";//年计划表
    
    
    
    
    
    
    
    
    
    
}
