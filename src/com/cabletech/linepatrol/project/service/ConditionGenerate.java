package com.cabletech.linepatrol.project.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.project.workflow.ProjectRemedyWorkflowBO;
import com.cabletech.linepatrol.project.beans.RemedyApplyBean;

public class ConditionGenerate {
	private static Logger logger = Logger.getLogger(ConditionGenerate.class
			.getName());

	public static final String PROVINCE_MOBILE_USER = "11";

	public static final String PROVINCE_CONTRACTOR_USER = "21";

	public static final String PROVINCE_SUPERVISE_USER = "31";

	public static final String CITY_MOBILE_USER = "12";

	public static final String CITY_CONTRACTOR_USER = "22";

	public static final String CITY_SUPERVISE_USER = "32";
	
	private static final String PLAN_CANCEL = "999";//ȡ��״̬

	/**
	 * ִ�и����������ȡ�û�����Ĳ�ѯ����
	 * 
	 * @param request
	 *            HttpServletRequest �û�������Ϣ
	 * @param formBean
	 *            ActionForm �û��������Ϣ
	 * @return String �û�����Ĳ�ѯ����
	 */
	public String getInputCondition(RemedyApplyBean applyBean) {
		StringBuffer buf = new StringBuffer("");
		if(applyBean!=null){
			if(applyBean.getRemedyCode().equals("%")){
				buf.append(getCondition("remedy.remedycode","\\"+ applyBean.getRemedyCode(),
						"'%'", "like", "'%", "%'"));
			}else{
			buf.append(getCondition("remedy.remedycode", applyBean.getRemedyCode(),
					"'%'", "like", "'%", "%'"));
			}
			if(applyBean.getProjectName().equals("%")){
				buf.append(getCondition("nvl(remedy.projectname,'#')","\\"+ applyBean
						.getProjectName(), "'%'", "like", "'%", "%'"));
			}else{
			buf.append(getCondition("nvl(remedy.projectname,'#')", applyBean
					.getProjectName(), "'%'", "like", "'%", "%'"));
			}
			buf.append(getCondition("remedy.totalfee", applyBean.getTotalFeeMin(),
					"remedy.totalfee", ">=", "", ""));
			buf.append(getCondition("remedy.totalfee", applyBean.getTotalFeeMax(),
					"remedy.totalfee", "<=", "", ""));
			buf.append(getCondition("remedy.mtotalfee", applyBean.getMtotalFeeMin(),
					"remedy.mtotalfee", ">=", "", ""));
			buf.append(getCondition("remedy.mtotalfee", applyBean.getMtotalFeeMax(),
					"remedy.mtotalfee", "<=", "", ""));
			buf.append(getDateCondition("remedy.remedydate", applyBean
					.getRemedyDateMin(), "remedy.remedydate", ">=", "00:00:00"));
			buf.append(getDateCondition("remedy.remedydate", applyBean
					.getRemedyDataMax(), "remedy.remedydate", "<=", "23:59:59"));
			if (applyBean.getTaskStates() != null) {
				String[] taskStates = applyBean.getTaskStates();
				buf.append(" and exists( ");
				buf.append(" select dbid_ from jbpm4_task jbpm_task ");
				buf.append(" where jbpm_task.execution_id_='");
				buf.append(ProjectRemedyWorkflowBO.WORKFLOWNAME);
				buf.append(".'||remedy.id ");
				buf.append(" and ( ");
				for (int i = 0; i < taskStates.length; i++) {
					buf.append(" jbpm_task.name_='");
					buf.append(taskStates[i] + "' ");
					if (i < taskStates.length - 1) {
						buf.append(" or ");
					}
				}
				buf.append(" ) ");
				buf.append(" ) ");
			}
		}
		
		//��ά��λ
		buf.append(getCondition("remedy.contractorid", applyBean.getContractorId(),
				"remedy.contractorid", "="));
		//�Ƿ�ȡ��
		String state = applyBean.getState();
		if(StringUtils.isNotBlank(state)){
			if(StringUtils.equals(state, PLAN_CANCEL)){
				buf.append(" and remedy.state='");
				buf.append(state);
				buf.append("'");
			} else {
				buf.append(" and (remedy.state<>'");
				buf.append(PLAN_CANCEL);
				buf.append("' or remedy.state is null)");
			}
		}
		
		// buf.append(getCondition("remedy.state", applyBean.getState(),
		// "remedy.state", "="));
		return buf.toString();
	}

	/**
	 * ִ�и����û���Ϣ�����û���ѯ����������
	 * 
	 * @param request
	 *            HttpServletRequest �û�������Ϣ
	 * @param formBean
	 *            ActionForm �û��������Ϣ
	 * @param userInfo
	 * @return String �û���ѯ����������
	 */
	public String getUserQueryCondition(UserInfo userInfo) {
		StringBuffer buf = new StringBuffer("");
		logger.info("user dept type=" + userInfo.getDeptype()
				+ "................");
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
		buf.append(getCondition("remedy.remedydate", "", "trunc(sysdate,'mm')",
				">="));
		buf.append(getCondition("remedy.remedydate", "",
				"trunc(add_months(sysdate,1),'mm')", "<"));
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
	public String getCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator, String prefix,
			String suffix) {
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
	public String getDateCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator, String compareHour) {
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
	public String getCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator) {
		return getCondition(conditionName, inputValue, defaultValue,
				logicOperator, "'", "'");
	}
}
