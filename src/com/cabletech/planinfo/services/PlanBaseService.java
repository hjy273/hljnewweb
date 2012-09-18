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
	 * ��֤���ƶ��¼ƻ�ʱ��ƻ��Ƿ��ƶ�
	 * 
	 * @param year
	 * @param deptid
	 * @return ͨ������ true ���� false
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
	 * ��֤�ƶ��ƻ�ʱ�¼ƻ��Ƿ��ƶ�
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
	 * ��֤�����ƻ��Ƿ��Ѿ������ƶ�
	 * 
	 * @param begindate
	 *            �ƻ���ʼʱ��
	 * @param deptid
	 *            ��ά��λid
	 * @return �Ѿ��ƶ� true ���� false
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
	 * ��֤Ѳ��Ա��begindate��ʼʱ����û���ظ��ƻ�
	 * 
	 * @param begindate
	 *            �ƻ���ʼʱ��
	 * @param patrolID
	 *            Ѳ��Աid
	 * @return �Ѿ��ƶ� true ���� false
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
	 * �Ƚϼƻ��ƶ�������
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
