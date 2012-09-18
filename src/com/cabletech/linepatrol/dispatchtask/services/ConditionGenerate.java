package com.cabletech.linepatrol.dispatchtask.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.dispatchtask.beans.DispatchTaskBean;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.linepatrol.dispatchtask.workflow.DispatchTaskWorkflowBO;

public class ConditionGenerate {
	private static Logger logger = Logger.getLogger(ConditionGenerate.class
			.getName());

	public static final String PROVINCE_MOBILE_USER = "11";

	public static final String PROVINCE_CONTRACTOR_USER = "21";

	public static final String PROVINCE_SUPERVISE_USER = "31";

	public static final String CITY_MOBILE_USER = "12";

	public static final String CITY_CONTRACTOR_USER = "22";

	public static final String CITY_SUPERVISE_USER = "32";
	
	private static final String PLAN_CANCEL = "888";//ȡ��״̬

	/**
	 * ִ�и����������ȡ�û�����Ĳ�ѯ����
	 * 
	 * @param formBean
	 *            ActionForm �û��������Ϣ
	 * @return String �û�����Ĳ�ѯ����
	 */
	public String getInputCondition(DispatchTaskBean bean) {
		StringBuffer buf = new StringBuffer("");
		if (bean != null) {
			buf.append(getCondition("d.sendtopic", bean.getSendtopic(), "'%'",
					"like", "'%", "%'"));
			buf.append(getCondition("d.sendtype", bean.getSendtype(),
					"d.sendtype", "="));
			buf.append(getCondition("d.senddeptid", bean.getSenddeptid(),
					"d.senddeptid", "="));
			buf.append(getCondition("acceptdept.deptid",
					bean.getAcceptdeptid(), "acceptdept.deptid", "="));
			buf.append(getCondition("acceptdept.userid",
					bean.getAcceptuserid(), "acceptdept.userid", "="));
			buf.append(getCondition("u.username", bean.getSendusername(),
					"'%'", "like", "'%", "%'"));
			buf.append(getCondition("acceptdept.deptid",
					bean.getAcceptdeptid(), "acceptdept.deptid", "="));
			if (bean.getTaskStates() != null) {
				String[] taskStates = bean.getTaskStates();
				buf.append(" and exists( ");
				buf.append(" select dbid_ from jbpm4_task jbpm_task ");
				buf.append(" where jbpm_task.execution_id_='");
				buf.append(DispatchTaskWorkflowBO.DISPATCH_TASK_WORKFLOW);
				buf.append(".'||acceptdept.id ");
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
			if (bean.getProcessterm() != null
					&& DispatchTaskConstant.OUT_TIME_CONDITION.equals(bean
							.getProcessterm())) {
				buf.append(getCondition("d.processterm", "sysdate",
						"d.processterm", "<", "", ""));
			}
			if (bean.getProcessterm() != null
					&& DispatchTaskConstant.IN_TIME_CONDITION.equals(bean
							.getProcessterm())) {
				buf.append(getCondition("d.processterm", "sysdate",
						"d.processterm", ">=", "", ""));
			}
			buf.append(getDateCondition("d.sendtime", bean.getBegintime(),
					"d.sendtime", ">=", "00:00:00"));
			buf.append(getDateCondition("d.sendtime", bean.getEndtime(),
					"d.sendtime", "<=", "23:59:59"));
			//�Ƿ�ȡ��
			String workState = bean.getWorkstate();
			if(StringUtils.isNotBlank(workState)){
				if(StringUtils.equals(workState, PLAN_CANCEL)){
					buf.append(" and d.workstate='");
					buf.append(workState);
					buf.append("'");
				} else {
					buf.append(" and (d.workstate<>'");
					buf.append(PLAN_CANCEL);
					buf.append("' or d.workstate is null)");
				}
			}
		}
		return buf.toString();
	}

	/**
	 * ִ�и����û���Ϣ�����û���ѯ��������������ѯ�ɷ�����ά��λ���ɵ�ʱʹ�ã�
	 * 
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
			buf.append(" and di.regionid='" + userInfo.getRegionid() + "' ");
		}
		if (CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
			buf.append(" and (");
			buf.append(" di.departid='" + userInfo.getDeptID() + "' ");
			buf.append(" or ");
			buf.append(" senddept.departid='" + userInfo.getDeptID() + "' ");
			buf.append(" ) ");
		}
		if (CITY_SUPERVISE_USER.equals(userInfo.getType())) {
			buf.append(" and di.regionid='" + userInfo.getRegionid() + "' ");
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
		buf.append(" d.sendtime desc,d.id desc ");
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
	public String getConditionByInputValues(String conditionName,
			String inputValues, String defaultValue) {
		String condition = "";
		if (inputValues != null && !inputValues.equals("")) {
			condition += " and " + conditionName + " in ( ";
			if (inputValues.indexOf(",") != -1) {
				String[] inputValue = inputValues.split(",");
				for (int i = 0; inputValue != null && i < inputValue.length; i++) {
					condition += ("'" + inputValue[i] + "'");
					if (i < inputValue.length - 1) {
						condition += ",";
					}
				}
			} else {
				condition += ("'" + inputValues + "'");
			}
			condition = condition + " ) ";
		} else {
			condition += " and " + conditionName + " = ";
			condition += defaultValue;
		}

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
