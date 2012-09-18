package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

/**
 * 全省巡检计划月统计
 */
public class StatForWholeDAO {
	private Logger logger = Logger.getLogger("StatForWholeDAO");
	private QueryUtil query = null;
	/**
	 * 将结果封装为动态bean,包含区域\达标情况
	 * 
	 * @param sql
	 * @return List
	 */
	public List queryBeans(String sql) {
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("数据库连接异常："+e.getMessage());
			return null;
		}
	}

	/**
	 * 查询合格区域数与不合格数
	 * 分级别显示5个级别的区域数
	 * 
	 * @param sql
	 * @return ResultSet
	 */
	public ResultSet queryCheckOutRate(String sql) {
		try {
			query = new QueryUtil();
			return query.executeQuery(sql);
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("数据库连接异常："+e.getMessage());
			return null;
		}
	}

	/**
	 * 查询巡检线段数量
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer countSublineNum(String sql) {
		Integer num = new Integer(0);
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				num = new Integer(rs.getInt("num"));
			}
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("数据库连接异常："+e.getMessage());
			
		}
		return num;
	}
}
