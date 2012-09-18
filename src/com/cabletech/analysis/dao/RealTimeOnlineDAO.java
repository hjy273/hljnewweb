package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;
/**
 * RealTimeOnlineDAO.java
 */
public class RealTimeOnlineDAO {
	private QueryUtil query;
	private Logger logger = Logger.getLogger("RealTimeOnlineDAO");

	/**
	 * 通过QueryUtil类查询在线人数，返回的list中包含交叉时间、sim卡号
	 * 
	 * @param sql String sql语句
	 * @return List 返回list结果
	 */
	public List queryOnlineNum(String sql) {
		List result = new ArrayList();
		try {
			query = new QueryUtil();
			result = query.queryBeans(sql);
			return result;
		} catch (Exception e) {
			logger.info("query 对象初始化失败：" + e);
			e.printStackTrace();
			return null;
		}
	}

	// /**
	// * 查询在线人员（每个sim卡号）当前的工作状态 返回结果中包括时间段，该时刻的状态
	// *
	// * @param sql
	// * @return Map
	// */
	// public Map queryPatrolOnline(String sql) {
	// return null;
	// }

	/**
	 * 根据传入的sql查询出短信数目。
	 * 
	 * @param sql String sql语句
	 * @return int 返回短信数量。
	 */
	public int queryNoteNum(String sql) {
		int num = 0;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return num;
		} catch (Exception e) {
			logger.info("query 对象初始化失败：" + e);
			return num;
		}

	}
}
