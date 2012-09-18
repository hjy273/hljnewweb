package com.cabletech.linepatrol.appraise.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;

@Repository
public class AppraiseTableDao extends HibernateDao<AppraiseTable, String> {
	public void saveAppraiseTable(AppraiseTable appraiseTable) {
		save(appraiseTable);
	}
	/**
	 * 通过年份和表的类型查询表
	 * @param year
	 * @param type
	 * @return
	 */
	public List<AppraiseTable> queryAppraiseTableByYear(String year, String type) {
		String hql = "from AppraiseTable where year=? and type=?";
		return this.find(hql, year, type);
	}
	/**
	 * 通过类型获得所有该类型的表
	 * @param type
	 * @return
	 */
	public List<AppraiseTable> getAllTable(String type) {
		String hql = "from AppraiseTable where type=?";
		return this.find(hql, type);
	}
	/**
	 * 删除表
	 * @param appraiseTableId
	 */
	public void deleteTable(String appraiseTableId) {
		this.delete(appraiseTableId);
	}
	/**
	 * 通过表id获得表对象
	 * @param id
	 * @return
	 */
	public AppraiseTable getTableById(String id) {
		return findByUnique("id", id);
	}
	public List<AppraiseTable> queryTableByDate(Date startDate,Date endDate, String type) {
		String hql = "from AppraiseTable where startDate<=? and endDate>=? and type=?";
		return this.find(hql,startDate,endDate,type);
	}
	public List<AppraiseTable> queryTableInDate(Date startDate,Date endDate, String type) {
		String hql = "from AppraiseTable where startDate>=? and endDate<=? and type=?";
		return this.find(hql,startDate,endDate,type);
	}
}
