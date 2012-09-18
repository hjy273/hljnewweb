package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

/**
 * ���ƶ���ͳ�Ƶ�DAO��Ļ���
 */
public abstract class PlanStatBaseDAO extends HibernateDaoSupport {
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	protected QueryUtil query = null;

	/**
	 * �������ݿ⣬ÿ����¼Ϊ��̬Bean
	 * 
	 * @param sql
	 *            sql
	 * @return List
	 */
	public List queryInfo(String sql) {

		List list = null;
		try {
			query = new QueryUtil();
			logger.info(sql);
			list = query.queryBeans(sql);
		} catch (Exception ex) {
			logger.error("��ѯ�쳣��" + ex.getMessage());
			list = null;
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * JDBC�����������ݿ⣬ÿ����¼ΪResultSet����
	 * 
	 * @param sql
	 *            sql
	 * @return ResultSet
	 */
	public ResultSet queryInfoJDBC(String sql) {
		ResultSet resultset = null;
		try {
			query = new QueryUtil();
			resultset = query.executeQuery(sql);
		} catch (Exception ex) {
			logger.error("��ѯ�쳣��" + ex.getMessage());
			ex.printStackTrace();
		}
		return resultset;
	}

	public abstract Map getSublineNumMap(String sql); // ��ѯѲ���߶�����

	public abstract Map queryLineTypeDictMap(String sql); // ��lineclassdic���еõ����ݿ�䣬����code��nameֵ
}
