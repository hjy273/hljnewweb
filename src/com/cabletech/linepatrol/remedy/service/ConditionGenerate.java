package com.cabletech.linepatrol.remedy.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.remedy.beans.RemedyApplyBean;
import com.cabletech.linepatrol.remedy.constant.RemedyPowerConstant;
import com.cabletech.linepatrol.remedy.constant.RemedyWorkflowConstant;

public class ConditionGenerate {
    private static Logger logger = Logger.getLogger(ConditionGenerate.class.getName());

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
        StringBuffer buf = new StringBuffer("");
        RemedyApplyBean applyBean = (RemedyApplyBean) formBean;
        buf.append(getCondition("remedy.remedycode", applyBean.getRemedyCode(), "'%'", "like",
                "'%", "%'"));
        buf.append(getCondition("nvl(remedy.projectname,'#')", applyBean.getProjectName(), "'%'",
                "like", "'%", "%'"));
        buf.append(getCondition("remedy.totalfee", applyBean.getTotalFeeMin(), "remedy.totalfee",
                ">=", "", ""));
        buf.append(getCondition("remedy.totalfee", applyBean.getTotalFeeMax(), "remedy.totalfee",
                "<=", "", ""));
        buf.append(getDateCondition("remedy.remedydate", applyBean.getRemedyDateMin(),
                "remedy.remedydate", ">=", "00:00:00"));
        buf.append(getDateCondition("remedy.remedydate", applyBean.getRemedyDataMax(),
                "remedy.remedydate", "<=", "23:59:59"));
        buf.append(getCondition("remedy.state", applyBean.getState(), "remedy.state", "="));
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
            buf.append(" and r.regionid='" + userInfo.getRegionid() + "' ");
        }
        if (CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
            buf.append(" and c.contractorid='" + userInfo.getDeptID() + "' ");
        }
        if (CITY_SUPERVISE_USER.equals(userInfo.getType())) {
            buf.append(" and r.regionid='" + userInfo.getRegionid() + "' ");
        }

        return buf.toString();
    }

    /**
     * ִ�и���ָ����״̬��ȡ��������״̬���Ƶ�����
     * 
     * @param request
     *            HttpServletRequest �û�������Ϣ
     * @param formBean
     *            ActionForm �û��������Ϣ
     * @return String ��������״̬���Ƶ�����
     */
    public String getStateCondition(HttpServletRequest request, ActionForm formBean) {
        String power = request.getParameter("power");
        StringBuffer buf = new StringBuffer("");
        if (RemedyPowerConstant.APPROVE_POWER.equals(power)) {
            buf.append(" and remedy.state in ("+RemedyWorkflowConstant.APPROVE_STATUS_STRING+") ");
        }
        if (RemedyPowerConstant.CHECK_POWER.equals(power)) {
            buf.append(" and remedy.state='" + RemedyWorkflowConstant.WAIT_CHECKED_STATE + "' ");
        }
        if (RemedyPowerConstant.SQUARE_POWER.equals(power)) {
            buf.append(" and remedy.state='" + RemedyWorkflowConstant.WAIT_SQUARED_STATE + "' ");
        }
        if (RemedyPowerConstant.SQUARE_LIST_POWER.equals(power)) {
            buf.append(" and remedy.state>='" + RemedyWorkflowConstant.WAIT_SQUARED_STATE + "' ");
        }
        return buf.toString();
    }

    /**
     * ִ�и�����������ָ���Ĵ����˻�ȡ��������Ĵ�������������
     * 
     * @param request
     *            HttpServletRequest �û�������Ϣ
     * @param formBean
     *            ActionForm �û��������Ϣ
     * @return String ��������Ĵ�������������
     */
    public String getUserOperationCondition(HttpServletRequest request, ActionForm formBean) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
        StringBuffer buf = new StringBuffer("");
        buf.append(" and remedy.next_process_man='" + userInfo.getUserID() + "' ");
        return buf.toString();
    }

    /**
     * ִ�л�ȡ�����������������
     * 
     * @return String �����������������
     */
    public String getOrderCondition() {
        StringBuffer buf = new StringBuffer(" order by ");
        buf.append(" remedy.remedydate desc,remedy.id desc ");
        return buf.toString();
    }

    /**
     * ִ�л�ȡ��ǰ�·ݲ�ѯ����
     * 
     * @return String ��ǰ�·ݲ�ѯ����
     */
    public String getSysDateCondition() {
        StringBuffer buf = new StringBuffer();
        buf.append(getCondition("remedy.remedydate", "", "trunc(sysdate,'mm')", ">="));
        buf.append(getCondition("remedy.remedydate", "", "trunc(add_months(sysdate,1),'mm')", "<"));
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
     * ִ�и�������ָ���С�����ֵ����������ȱʡֵ�ͱȽϷ�ʽ����sql�������ڲ�ѯ����
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
    private String getDateCondition(String conditionName, String inputValue, String defaultValue,
            String logicOperator, String compareHour) {
        String condition = " and " + conditionName + " " + logicOperator + " ";
        if (inputValue != null && !inputValue.equals("")) {
            condition += "to_date('" + inputValue + " " + compareHour
                    + "','yyyy-mm-dd hh24:mi:ss') ";
        } else {
            condition += defaultValue;
        }
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
