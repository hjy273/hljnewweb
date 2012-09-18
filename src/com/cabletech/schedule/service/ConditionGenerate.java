package com.cabletech.schedule.service;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public class ConditionGenerate {
	private static Logger logger = Logger.getLogger(ConditionGenerate.class
			.getName());

	public static final String PROVINCE_MOBILE_USER = "11";

	public static final String PROVINCE_CONTRACTOR_USER = "21";

	public static final String PROVINCE_SUPERVISE_USER = "31";

	public static final String CITY_MOBILE_USER = "12";

	public static final String CITY_CONTRACTOR_USER = "22";

	public static final String CITY_SUPERVISE_USER = "32";

	/**
	 * 执行根据用户信息产生用户查询的限制条件（查询派发给代维单位的派单时使用）
	 * 
	 * @param userInfo
	 * @return String 用户查询的限制条件
	 */
	public static String getUserQueryCondition(UserInfo userInfo) {
		StringBuffer buf = new StringBuffer("");
		logger.info("user dept type=" + userInfo.getDeptype()
				+ "................");
		if (CITY_MOBILE_USER.equals(userInfo.getType())) {
			buf.append(" and ci.regionid='" + userInfo.getRegionid() + "' ");
		}
		if (CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
			buf.append(" and ci.contractorid='" + userInfo.getDeptID() + "' ");
		}

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
	public static String getConditionByInputValues(String conditionName,
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
	public static String getCondition(String conditionName, String inputValue,
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
	public static String getDateCondition(String conditionName,
			String inputValue, String defaultValue, String logicOperator,
			String compareHour) {
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
	public static String getCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator) {
		return getCondition(conditionName, inputValue, defaultValue,
				logicOperator, "'", "'");
	}
}
