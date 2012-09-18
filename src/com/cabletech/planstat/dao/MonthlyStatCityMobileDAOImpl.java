package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;

public class MonthlyStatCityMobileDAOImpl extends PlanStatBaseDAO {
	/**
	 * 查询巡检线段数量
	 * 
	 * @param sql
	 * @return Map
	 */
	public Map getSublineNumMap(String sql) {
		Integer passNum = new Integer(0);
		Integer failNum = new Integer(0);
		Integer noPlanNum = new Integer(0);
		Map map = new HashMap();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				passNum = new Integer(rs.getInt("pass"));
				failNum = new Integer(rs.getInt("fail"));
				noPlanNum = new Integer(rs.getInt("noplan"));
			}
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("数据库连接异常：" + e.getMessage());
		}
		map.put("未计划线段 ", noPlanNum);
		map.put("未合格线段 ", failNum);
		map.put("合格线段 ", passNum);
		return map;
	}
	
	/**
	 * 从lineclassdic表中得到数据库典，各类code与name值
	 * @return map
	 */
	public Map queryLineTypeDictMap(String sql){
		Map map = new HashMap();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				map.put(rs.getString("code"), rs.getString("name"));
			}
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("数据库连接异常：" + e.getMessage());
		}
		return map;
	}
}
