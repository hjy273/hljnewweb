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
     * ִ�и����������ȡ�û�����Ĳ�ѯ����
     * 
     * @param request
     *            HttpServletRequest �û�������Ϣ
     * @param formBean
     *            ActionForm �û��������Ϣ
     * @return String �û�����Ĳ�ѯ����
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
     * ִ�л�ȡ�����������������
     * 
     * @return String �����������������
     */
    public String getOrderCondition() {
        // TODO Auto-generated method stub
        StringBuffer buf = new StringBuffer(" order by ");
        buf.append(" mt.typename,mm.modelname,mb.name,remedy.id desc ");
        return buf.toString();
    }

    /**
     * ִ�и����û���Ϣ�����û���ѯ����������
     * 
     * @param request
     *            HttpServletRequest �û�������Ϣ
     * @param formBean
     *            ActionForm �û��������Ϣ
     * @return String �û���ѯ����������
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
     * ִ�л�ȡ�鵵��������Ĳ�ѯ����
     * 
     * @return String ��ȡ�鵵��������Ĳ�ѯ����
     */
    public String getAchieveCondition() {
        StringBuffer buf = new StringBuffer("");
        buf.append(" and remedy.state='" + RemedyWorkflowConstant.ACHIEVED_STATE + "' ");
        return buf.toString();
    }

    /**
     * ִ�и�������ָ���С�����ֵ����������ȱʡֵ��ǰ׺�ͺ�׺����sql���Ĳ�ѯ����
     * 
     * @param conditionName
     *            String ����ָ����
     * @param inputValue
     *            String ����ֵ
     * @param defaultValue
     *            String ȱʡֵ
     * @param logicOperator
     *            String ������
     * @param prefix
     *            String ǰ׺
     * @param suffix
     *            String ��׺
     * @return String sql���Ĳ�ѯ����
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
     * ִ�и�������ָ���С�����ֵ����������ȱʡֵ����sql���Ĳ�ѯ����
     * 
     * @param conditionName
     *            String ����ָ����
     * @param inputValue
     *            String ����ֵ
     * @param defaultValue
     *            String ȱʡֵ
     * @param logicOperator
     *            String ������
     * @return String sql���Ĳ�ѯ����
     */
    private String getCondition(String conditionName, String inputValue, String defaultValue,
            String logicOperator) {
        return getCondition(conditionName, inputValue, defaultValue, logicOperator, "'", "'");
    }
}
