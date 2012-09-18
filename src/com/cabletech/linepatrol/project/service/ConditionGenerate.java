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
	
	private static final String PLAN_CANCEL = "999";//取消状态

	/**
	 * 执行根据请求表单获取用户输入的查询条件
	 * 
	 * @param request
	 *            HttpServletRequest 用户请求信息
	 * @param formBean
	 *            ActionForm 用户请求表单信息
	 * @return String 用户输入的查询条件
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
		
		//代维单位
		buf.append(getCondition("remedy.contractorid", applyBean.getContractorId(),
				"remedy.contractorid", "="));
		//是否取消
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
	 * 执行根据用户信息产生用户查询的限制条件
	 * 
	 * @param request
	 *            HttpServletRequest 用户请求信息
	 * @param formBean
	 *            ActionForm 用户请求表单信息
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
	 * 执行获取修缮申请的排序条件
	 * 
	 * @return String 修缮申请的排序条件
	 */
	public String getOrderCondition() {
		StringBuffer buf = new StringBuffer(" order by ");
		buf.append(" remedy.remedydate desc,remedy.id desc ");
		return buf.toString();
	}

	/**
	 * 执行获取当前月份查询条件
	 * 
	 * @return String 当前月份查询条件
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
