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
	
	private static final String PLAN_CANCEL = "888";//取消状态

	/**
	 * 执行根据请求表单获取用户输入的查询条件
	 * 
	 * @param formBean
	 *            ActionForm 用户请求表单信息
	 * @return String 用户输入的查询条件
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
			//是否取消
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
	 * 执行根据用户信息产生用户查询的限制条件（查询派发给代维单位的派单时使用）
	 * 
	 * @param userInfo
	 * @return String 用户查询的限制条件
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
	 * 执行获取修缮申请的排序条件
	 * 
	 * @return String 修缮申请的排序条件
	 */
	public String getOrderCondition() {
		StringBuffer buf = new StringBuffer(" order by ");
		buf.append(" d.sendtime desc,d.id desc ");
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
	 * 执行根据条件指定列、输入值、操作符、缺省值和比较方式生成sql语句的日期查询条件
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
	public String getCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator) {
		return getCondition(conditionName, inputValue, defaultValue,
				logicOperator, "'", "'");
	}
}
