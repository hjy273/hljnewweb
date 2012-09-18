package com.cabletech.analysis.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.StatForWholeDAO;
import com.cabletech.commons.exception.SubtrahendException;
import com.cabletech.commons.util.DateUtil;

/**
 * ȫʡѲ��ƻ���ͳ��
 */
public class StatForWholeBO {
	private StatForWholeDAO dao = new StatForWholeDAO();
	private Logger logger = Logger.getLogger("StatForWholeBO");

	/**
	 * ����ȫʡ������Ϣ��ѯ���
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return List
	 */
	private String constructCollectivitySql(String year, String month) {
		String sql = "select m.EXAMINERESULT examineresult,r.regionname regionname from PLAN_MSTAT m,deptinfo d,region r "
				+ " where r.regionid=d.regionid and d.deptid=m.deptid "
				+ " and m.factdate between to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd') and last_day(to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd'))"
				+ "order by m.EXAMINERESULT desc";
		logger.info("sql:"+sql);
		return sql;
	}

	/**
	 * ���ͼ����ʾ���ݣ��ϸ�����ƶ���Ŀ�벻�ϸ������Ŀ��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return Map
	 */
	public Map getChartData(String year, String month) {
		String sql = "select m.EXAMINERESULT EXAMINERESULT ,count(m.EXAMINERESULT) num "
				+ " from PLAN_MSTAT m,deptinfo d,region r "
				+ " where r.regionid=d.regionid and d.deptid=m.deptid"
				+ " and m.factdate between to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd') and last_day(to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd'))"
				+ "group by m.EXAMINERESULT order by m.EXAMINERESULT ";
		Map map = new HashMap();
		ResultSet rs = dao.queryCheckOutRate(sql);
		try {
			while (rs.next()) {
				map.put(rs.getString("examineresult") + "��", new Integer(rs.getInt("num")));
			}
		} catch (SQLException e) {
			map = null;
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * ���ĳ��ĳ�µ�������Ϣ���������Ŀ��˽����
	 * <li>���ؽ���а������򡢿��˽��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return List
	 */
	public List getCollectivityInfo(String year, String month) {
		String sql = constructCollectivitySql(year, month);
		return dao.queryBeans(sql);
	}

	/**
	 * ���ȫʡÿ������ļƻ�ִ�м�������, ��������\�ƻ�Ѳ����\ʵ��Ѳ����\��������\���˽��
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return List
	 */
	public List getExecuteForAllArea(String year, String month) {
		String sql = "select sum(c.PLANPOINT) PLANPOINT ,sum(c.FACTPOINT) FACTPOINT,sum(c.trouble) troublenum,m.EXAMINERESULT examineresult,concat(m.overallpatrolp,'%') overallpatrolp,r.regionname regionname"
				+ " from PLAN_CSTAT  c,region r ,PLAN_MSTAT m,PLANSTAT_MC mc"
				+ " where c.regionid=r.regionid and mc.CSTATID=c.CSTATID and mc.MSTATID = m.MSTATID"
				+ " and m.factdate between to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd') and last_day(to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd'))"
				+ " group by r.regionname,m.EXAMINERESULT,m.overallpatrolp "
				+ " order by r.regionname";
		logger.info("sql "+sql);	
		return dao.queryBeans(sql);
	}

	/**
	 * ���ݲ�ѯ�·ݻ�ø��·���ǰ�����µĶԱ����.
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return List
	 * @throws SubtrahendException 
	 */
	public List getMonthlyContrast(String year, String month) throws SubtrahendException {
		String endDateStr = year+"-"+month+"-01";
		Date endDate = DateUtil.parseDate(endDateStr,"yyyy-MM-dd");
		String sql = "select to_char(m.factdate,'MM') factdate, m.examineresult examineresult" +
				" from Plan_PMstat m " +
				" where m.factdate between to_date('"
				+ DateUtil.getAfterNMonth(endDate,-2)
				+ "','yyyy-mm-dd') and last_day(to_date('"
				+ year
				+ "-"
				+ month
				+ "-01','yyyy-mm-dd'))"
				+ " order by m.factdate";
		logger.info("sql "+sql);
		return dao.queryBeans(sql);
	}
	
	
	/**
	 * ���ȫʡ��������ĶԱ���Ϣ����.
	 * 
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return List
	 */
	public List getAreaContrast(String year, String month) {
		return getExecuteForAllArea(year,month);
	}

	/**
	 * ���Ѳ���߶ζԱ��������,
	 * δ�ƻ����߶�����δ�ϸ���߶������ϸ���߶���
	 * @param year
	 *            ��
	 * @param month
	 *            ��
	 * @return Map
	 */
	public Map getSublinePatrolForChart(String year, String month) {
		// δ�ƻ���
		String nonPlanSQL = "select  count(*) num"
				+ " from nonplansubline nl,sublineinfo sl,lineinfo l,lineclassdic lc,patrolmaninfo pm"
				+ " where sl.patrolid=pm.patrolid and sl.sublineid=nl.sublineid and l.lineid = sl.lineid"
				+ " and l.linetype=lc.code "
				+ " and nl.factdate between to_date('" + year + "-" + month
				+ "-01','yyyy-mm-dd') and last_day(to_date('" + year + "-"
				+ month + "-01','yyyy-mm-dd'))";
		// δ�ϸ��
		String nonPassSQL = "select count(*) num "
				+ " from plan_sublinestat pss,patrolmaninfo pm ,sublineinfo sl,lineinfo l,lineclassdic lc"
				+ " where  sl.patrolid=pm.patrolid and sl.sublineid=pss.sublineid "
				+ " and l.lineid = sl.lineid and l.linetype=lc.code "
				+ " and pss.factdate between to_date('" + year + "-" + month
				+ "-01','yyyy-mm-dd') and last_day(to_date('" + year + "-"
				+ month + "-01','yyyy-mm-dd')) and pss.examineresult <'3'";
		// �ϸ��
		String passSQL = "select count(*) num"
				+ " from plan_sublinestat pss,patrolmaninfo pm ,sublineinfo sl,lineinfo l,lineclassdic lc "
				+ " where  sl.patrolid=pm.patrolid and sl.sublineid=pss.sublineid and l.lineid = sl.lineid and l.linetype=lc.code "
				+ " and pss.factdate between to_date('" + year + "-" + month
				+ "-01','yyyy-mm-dd') and last_day(to_date('" + year + "-"
				+ month + "-01','yyyy-mm-dd'))"
				+ " and pss.examineresult >='3'";
		Integer nonPlanNum = dao.countSublineNum(nonPlanSQL);
		Integer nonPassNum = dao.countSublineNum(nonPassSQL);
		Integer passNum = dao.countSublineNum(passSQL);
		Map map = new HashMap();
		map.put("δ�ƻ��߶� ", nonPlanNum);
		map.put("δ�ϸ��߶� ", nonPassNum);
		map.put("�ϸ��߶� ", passNum);
		return map;
	}
}
