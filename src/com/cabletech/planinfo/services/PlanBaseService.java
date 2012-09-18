package com.cabletech.planinfo.services;

import java.util.Date;

import org.apache.log4j.Logger;

import com.cabletech.planinfo.dao.PlanDAO;
import com.cabletech.planinfo.dao.PlanDAOImpl;
import com.cabletech.planinfo.dao.YearMonthPlanDAOImpl;

public class PlanBaseService {
	private Logger logger = Logger.getLogger("PlanBaseService");
	PlanDAO dao;

	/**
	 * 验证在制定月计划时年计划是否制定
	 * 
	 * @param year
	 * @param deptid
	 * @return 通过审批 true 否则 false
	 */
	public boolean isInstituteYPlan(String year, String deptid) {
		dao = new YearMonthPlanDAOImpl();
		String sql = "select count(id) c from yearmonthplan where plantype='1' and status='1' and deptid='"
				+ deptid + "' and year='" + year + "'";
		logger.info(sql);
		int count = dao.queryPlanCount(sql);
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证制定计划时月计划是否制定
	 * 
	 * @param year
	 * @param deptid
	 * @param month
	 * @return
	 */
	public boolean isInstituteMPlan(String month, String year, String deptid) {
		dao = new PlanDAOImpl();
		String sql = "select count(id) c from yearmonthplan where plantype='2' and status='1' and deptid='"
				+ deptid
				+ "' and year='"
				+ year
				+ "' and month='"
				+ month
				+ "'";
		logger.info(sql);
		int count = dao.queryPlanCount(sql);
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证批量计划是否已经单独制定
	 * 
	 * @param begindate
	 *            计划开始时间
	 * @param deptid
	 *            代维单位id
	 * @return 已经制定 true 否则 false
	 */
	public boolean checkDate(String begindate, String deptid) {
		dao = new PlanDAOImpl();
		String sql = " select  count(id) c from PLAN where executorid in (select patrolid from patrolmaninfo where parentid='"
				+ deptid
				+ "') and  begindate <= to_date('"
				+ begindate
				+ "','yyyy/mm/dd') and enddate >= to_date('"
				+ begindate
				+ "','yyyy/mm/dd')";
		logger.info(sql);
		int count = dao.queryPlanCount(sql);
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证巡检员在begindate开始时间又没有重复计划
	 * 
	 * @param begindate
	 *            计划开始时间
	 * @param patrolID
	 *            巡检员id
	 * @return 已经制定 true 否则 false
	 */
	public boolean checkDatePatrol(String begindate, String patrolID) {
		dao = new PlanDAOImpl();
		String sql = " select  count(id) c from PLAN where executorid in ('"
				+ patrolID + "') and  begindate <= to_date('" + begindate
				+ "','yyyy/mm/dd') and enddate >= to_date('" + begindate
				+ "','yyyy/mm/dd')";
		logger.info(sql);
		int count = dao.queryPlanCount(sql);
		if (count >= 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较计划制定的日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean isOldYMPlan(String year, String month) {
		// TODO Auto-generated method stub
		Date date = new Date();
		Date planDate = new Date();
		planDate.setYear(Integer.parseInt(year) - 1900);
		if (month != null && !"".equals(month)) {
			int intMonth = Integer.parseInt(month) - 1;
			if (intMonth >= 0 && intMonth < 12) {
				planDate.setMonth(intMonth);
			}
		}
		if (planDate.compareTo(date) < 0) {
			return true;
		}
		return false;
	}

}
