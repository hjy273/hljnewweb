package com.cabletech.planstat.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.cabletech.commons.hb.QueryUtil;

public class MonthlyStatCityMobileDAOImpl extends PlanStatBaseDAO {
	/**
	 * ��ѯѲ���߶�����
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
			logger.info("���ݿ������쳣��" + e.getMessage());
		}
		map.put("δ�ƻ��߶� ", noPlanNum);
		map.put("δ�ϸ��߶� ", failNum);
		map.put("�ϸ��߶� ", passNum);
		return map;
	}
	
	/**
	 * ��lineclassdic���еõ����ݿ�䣬����code��nameֵ
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
			logger.info("���ݿ������쳣��" + e.getMessage());
		}
		return map;
	}
}
