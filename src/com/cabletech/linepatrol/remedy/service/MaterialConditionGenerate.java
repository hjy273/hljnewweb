package com.cabletech.linepatrol.remedy.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.beans.RemedyMaterialBean;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;

public class MaterialConditionGenerate {
    private static Logger logger = Logger.getLogger(MaterialConditionGenerate.class.getName());

    public static final String PROVINCE_MOBILE_USER = "11";

    public static final String PROVINCE_CONTRACTOR_USER = "21";

    public static final String PROVINCE_SUPERVISE_USER = "31";

    public static final String CITY_MOBILE_USER = "12";

    public static final String CITY_CONTRACTOR_USER = "22";

    public static final String CITY_SUPERVISE_USER = "32";

    /**
     * 执行根据请求表单获取用户输入的查询条件
     * 
     * @param request
     *            HttpServletRequest 用户请求信息
     * @param formBean
     *            ActionForm 用户请求表单信息
     * @return String 用户输入的查询条件
     */
    public String getInputCondition(HttpServletRequest request, ActionForm formBean) {
        // TODO Auto-generated method stub
        RemedyMaterialBean bean = (RemedyMaterialBean) formBean;
        StringBuffer buf = new StringBuffer("");
        buf.append(getCondition("remedy.remedycode", bean.getRemedyCode(), "remedy.remedycode",
                "like", "'%", "%'"));
        return buf.toString();
    }

    /**
     * 执行获取修缮申请的排序条件
     * 
     * @return String 修缮申请的排序条件
     */
    public String getOrderCondition() {
        // TODO Auto-generated method stub
        StringBuffer buf = new StringBuffer(" order by ");
        buf.append(" mt.typename,mm.modelname,mb.name,remedy.id desc ");
        return buf.toString();
    }

    /**
     * 执行根据用户信息产生用户查询的限制条件
     * 
     * @param request
     *            HttpServletRequest 用户请求信息
     * @param formBean
     *            ActionForm 用户请求表单信息
     * @return String 用户查询的限制条件
     */
    public String getUserQueryCondition(HttpServletRequest request, ActionForm formBean) {
        // TODO Auto-generated method stub
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        StringBuffer buf = new StringBuffer("");
        logger.info("user dept type=" + userInfo.getDeptype() + "................");
        if (PROVINCE_CONTRACTOR_USER.equals(userInfo.getType())) {
            // buf.append(" start with c.contractorid");
        }
        if (PROVINCE_SUPERVISE_USER.equals(userInfo.getType())) {
            // buf.append(" start with c.contractorid");
        }
        if (CITY_MOBILE_USER.equals(userInfo.getType())) {
            buf.append(" and remedy.regionid='" + userInfo.getRegionid() + "' ");
        }
        if (CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
            buf.append(" and remedy.contractorid='" + userInfo.getDeptID() + "' ");
        }
        if (CITY_SUPERVISE_USER.equals(userInfo.getType())) {
            buf.append(" and remedy.regionid='" + userInfo.getRegionid() + "' ");
        }

        return buf.toString();
    }

    /**
     * 执行获取归档修缮申请的查询条件
     * 
     * @return String 获取归档修缮申请的查询条件
     */
    public String getAchieveCondition() {
        StringBuffer buf = new StringBuffer("");
        buf.append(" and remedy.state='" + RemedyWorkflowConstant.ACHIEVED_STATE + "' ");
        return buf.toString();
    }

    /**
     * 执行根据条件指定列、输入值、操作符、缺省值、前缀和后缀生成sql语句的查询条件
     * 
     * @param conditionName
     *            String 条件指定列
     * @param inputValue
     *            String 输入值
     * @param defaultValue
     *            String 缺省值
     * @param logicOperator
     *            String 操作符
     * @param prefix
     *            String 前缀
     * @param suffix
     *            String 后缀
     * @return String sql语句的查询条件
     */
    private String getCondition(String conditionName, String inputValue, String defaultValue,
            String logicOperator, String prefix, String suffix) {
        String condition = " and " + conditionName + " " + logicOperator + " ";
        if (inputValue != null && !inputValue.equals("")) {
            condition += (prefix + inputValue + suffix);
        } else {
            condition += defaultValue;
        }
        condition = condition + " ";
        return condition;
    }

    /**
     * 执行根据条件指定列、输入值、操作符和缺省值生成sql语句的查询条件
     * 
     * @param conditionName
     *            String 条件指定列
     * @param inputValue
     *            String 输入值
     * @param defaultValue
     *            String 缺省值
     * @param logicOperator
     *            String 操作符
     * @return String sql语句的查询条件
     */
    private String getCondition(String conditionName, String inputValue, String defaultValue,
            String logicOperator) {
        return getCondition(conditionName, inputValue, defaultValue, logicOperator, "'", "'");
    }
}
