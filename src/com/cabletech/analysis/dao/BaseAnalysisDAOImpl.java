package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

/**
 * ���ڲ������ݿ⣬���ز�ѯ����б�
 * 
 */
public class BaseAnalysisDAOImpl extends HibernateDaoSupport {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private QueryUtil query = null;

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
}
