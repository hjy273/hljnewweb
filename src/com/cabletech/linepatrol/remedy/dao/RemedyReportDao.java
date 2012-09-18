package com.cabletech.linepatrol.remedy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

public class RemedyReportDao extends RemedyBaseDao {
	private static Logger logger = Logger.getLogger(RemedyReportDao.class
			.getName());
	/**
	 * 获取区县信息
	 * @return
	 */
	public List queryAllTown() {
		List list = new ArrayList();
		String sql = "select t.id,t.town from linepatrol_towninfo t";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query town error:" + e);
		}
		return list;
	}
	
	/**
	 * 获取施工单位信息
	 * @param regionId
	 * @return
	 */
	public List queryAllContractor(String regionId) {
		List list = new ArrayList();
		String sql = "select c.contractorid,c.contractorname from contractorinfo c where c.regionid='"
				+ regionId + "'" + " and depttype='2'" + " and state is null ";

		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error("query Contractor error:" + e);
		}
		return list;
	}
	
	/**
	 * 根据施工单位Id、区县Id、查询月份查询结算申请列表
	 * @param contractorId
	 * @param remedyDate
	 * @param townId
	 * @return
	 */
	public List getCheckReport(String contractorId, String remedyDate,
			String townId) {
		List list = new ArrayList();
		String sql = "select remedy.id id,remedy.remedycode remedycode,remedy.projectname projectname,balance.totalfee totalfee,strike.remark remark" +
				" from linepatrol_remedy remedy,linepatrol_remedy_balance balance,linepatrol_remedy_strike strike " +
				" where balance.remedyid=remedy.id and strike.remedyid=remedy.id " +
				" and remedy.contractorid='" + contractorId +
				"' and remedy.townid='" + townId +
				"' and to_char(remedy.remedydate,'yyyy-MM')='" + remedyDate + "'";
		try {
			QueryUtil query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception e) {
			logger.info("sql " + sql);
			logger.error(e);
		}
		logger.info(sql);
		return list;
	}

	public List queryList(String condition) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
