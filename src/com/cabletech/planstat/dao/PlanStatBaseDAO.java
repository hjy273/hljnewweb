package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;

/**
 * 市移动月统计等DAO类的基类
 */
public abstract class PlanStatBaseDAO extends HibernateDaoSupport {
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	protected QueryUtil query = null;

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
			logger.info(sql);
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

	public abstract Map getSublineNumMap(String sql); // 查询巡检线段数量

	public abstract Map queryLineTypeDictMap(String sql); // 从lineclassdic表中得到数据库典，各类code与name值
}
