package com.cabletech.station.services;

/**
 * 中继站计划的基本信息和状态常量类
 * 
 * @author admin
 * 
 */
public class StationConstant {
    // 波分日计划类型
    public static final String PLAN_DAY = "01";

    // 波分周计划类型
    public static final String PLAN_WEEK = "02";

    // 波分月计划类型
    public static final String PLAN_MONTH = "03";

    // 波分半年计划联系
    public static final String PLAN_HALF_YEAR = "04";

    // 未提交状态
    public static final String WAIT_SUBMIT_STATE = "01";

    // 待审核状态
    public static final String WAIT_AUDITING_STATE = "02";

    // 审核通过状态（正在填写状态）
    public static final String PASSED_STATE = "03";

    // 审核不通过状态
    public static final String NOT_PASSED_STATE = "04";

    // 计划全部完成状态
    public static final String FINISHED_STATE = "05";

    // 计划中中继站已经填写信息状态
    public static final String WRITED_STATE = "0";

    // 计划中中继站未填写信息状态
    public static final String NOT_WRITED_STATE = "1";

    // 计划是否全部完成的标记
    public static final String ALL_FINISHED_STATE = "00";

    // 中继站管理的功能模块编号
    public static final String SONMENU = "23101";

    // 中继站激活状态
    public static final String IS_ACTIVED = "0";

    // 中继站删除状态
    public static final String IS_DELETED = "1";
}
