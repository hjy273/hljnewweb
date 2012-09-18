package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

/**
 * 用于操作数据库，返回查询结果列表
 * 
 */
public class BaseAnalysisDAOImpl extends HibernateDaoSupport {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private QueryUtil query = null;

	/**
	 * 操作数据库，每条记录为动态Bean
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
			logger.error("查询异常：" + ex.getMessage());
			list = null;
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * JDBC方法操作数据库，每条记录为ResultSet类型
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
			logger.error("查询异常：" + ex.getMessage());
			ex.printStackTrace();
		}
		return resultset;
	}
}
