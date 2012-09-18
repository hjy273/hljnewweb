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
 * 全省巡检计划月统计
 */
public class StatForWholeBO {
	private StatForWholeDAO dao = new StatForWholeDAO();
	private Logger logger = Logger.getLogger("StatForWholeBO");

	/**
	 * 构造全省总体信息查询语句
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
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
	 * 获得图表显示数据，合格地市移动数目与不合格地市数目。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
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
				map.put(rs.getString("examineresult") + "分", new Integer(rs.getInt("num")));
			}
		} catch (SQLException e) {
			map = null;
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 获得某年某月的总体信息，各地区的考核结果。
	 * <li>返回结果中包括区域、考核结果
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return List
	 */
	public List getCollectivityInfo(String year, String month) {
		String sql = constructCollectivitySql(year, month);
		return dao.queryBeans(sql);
	}

	/**
	 * 获得全省每个区域的计划执行及完成情况, 包括区域\计划巡检点次\实际巡检点次\隐患数量\考核结果
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
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
	 * 根据查询月份获得该月份以前几个月的对比情况.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
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
	 * 获得全省所有区域的对比信息数据.
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return List
	 */
	public List getAreaContrast(String year, String month) {
		return getExecuteForAllArea(year,month);
	}

	/**
	 * 获得巡检线段对比情况数据,
	 * 未计划的线段数，未合格的线段数，合格的线段数
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return Map
	 */
	public Map getSublinePatrolForChart(String year, String month) {
		// 未计划的
		String nonPlanSQL = "select  count(*) num"
				+ " from nonplansubline nl,sublineinfo sl,lineinfo l,lineclassdic lc,patrolmaninfo pm"
				+ " where sl.patrolid=pm.patrolid and sl.sublineid=nl.sublineid and l.lineid = sl.lineid"
				+ " and l.linetype=lc.code "
				+ " and nl.factdate between to_date('" + year + "-" + month
				+ "-01','yyyy-mm-dd') and last_day(to_date('" + year + "-"
				+ month + "-01','yyyy-mm-dd'))";
		// 未合格的
		String nonPassSQL = "select count(*) num "
				+ " from plan_sublinestat pss,patrolmaninfo pm ,sublineinfo sl,lineinfo l,lineclassdic lc"
				+ " where  sl.patrolid=pm.patrolid and sl.sublineid=pss.sublineid "
				+ " and l.lineid = sl.lineid and l.linetype=lc.code "
				+ " and pss.factdate between to_date('" + year + "-" + month
				+ "-01','yyyy-mm-dd') and last_day(to_date('" + year + "-"
				+ month + "-01','yyyy-mm-dd')) and pss.examineresult <'3'";
		// 合格的
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
		map.put("未计划线段 ", nonPlanNum);
		map.put("未合格线段 ", nonPassNum);
		map.put("合格线段 ", passNum);
		return map;
	}
}
