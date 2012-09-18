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
	 * ͨ����ݺͱ�����Ͳ�ѯ��
	 * @param year
	 * @param type
	 * @return
	 */
	public List<AppraiseTable> queryAppraiseTableByYear(String year, String type) {
		String hql = "from AppraiseTable where year=? and type=?";
		return this.find(hql, year, type);
	}
	/**
	 * ͨ�����ͻ�����и����͵ı�
	 * @param type
	 * @return
	 */
	public List<AppraiseTable> getAllTable(String type) {
		String hql = "from AppraiseTable where type=?";
		return this.find(hql, type);
	}
	/**
	 * ɾ����
	 * @param appraiseTableId
	 */
	public void deleteTable(String appraiseTableId) {
		this.delete(appraiseTableId);
	}
	/**
	 * ͨ����id��ñ����
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
