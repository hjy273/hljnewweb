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
	 * ִ�и����û���Ϣ�����û���ѯ��������������ѯ�ɷ�����ά��λ���ɵ�ʱʹ�ã�
	 * 
	 * @param userInfo
	 * @return String �û���ѯ����������
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
	public static String getCondition(String conditionName, String inputValue,
			String defaultValue, String logicOperator) {
		return getCondition(conditionName, inputValue, defaultValue,
				logicOperator, "'", "'");
	}
}
