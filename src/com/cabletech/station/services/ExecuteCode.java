package com.cabletech.station.services;

/**
 * 中继站管理执行动作和编号常量类
 * 
 * @author admin
 * 
 */
public class ExecuteCode {
    // 中继站计划的提交动作
    public static final String SUBMIT_ACTION = "A001";

    // 中继站计划的未提交动作
    public static final String NOT_SUBMIT_ACTION = "A002";

    // 中继站计划的修改动作
    public static final String EDIT_ACTION = "A003";

    // 中继站计划的审核动作
    public static final String AUDITING_ACTION = "A004";

    // 中继站计划的审核通过动作
    public static final String PASSED_ACTION = "0";

    // 中继站计划的审核不通过动作
    public static final String NOT_PASSED_ACTION = "1";

    // 执行动作成功编号
    public static final String SUCCESS_CODE = "S001";

    // 执行动作失败编号
    public static final String FAIL_CODE = "F001";

    // 不做动作编号
    public static final String NO_OPERATION_CODE = "S002";

    // 中继站存在的错误编号
    public static final String EXIST_STATION_ERR_CODE = "F002";

    // 中继站不存在的错误编号
    public static final String NOT_EXIST_STATION_ERR_CODE = "F003";

    // 中继站计划存在的错误编号
    public static final String EXIST_PLAN_ERR_CODE = "F004";

    // 中继站计划不存在的错误编号
    public static final String NOT_EXIST_PLAN_ERR_CODE = "F005";

    // 中继站计划不能编辑或者删除的错误编号
    public static final String NOT_EDIT_PLAN_ERR_CODE = "F006";

    // 中继站计划不能审核的错误编号
    public static final String NOT_AUDITING_PLAN_ERR_CODE = "F007";

    // 中继站计划审核没有通过的错误编号
    public static final String NOT_PASSED_PLAN_ERR_CODE = "F008";

    // 中继站计划已经完成的错误编号
    public static final String FINISHED_PLAN_ERR_CODE = "F009";

}
