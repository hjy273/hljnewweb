package com.cabletech.statistics.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;

public class StatisticsCurrentPatrolDao {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public StatisticsCurrentPatrolDao() {
	}
	
	/**
	 * 取得当前正在执行计划信息
	 * @return
	 */
	public List getCurrentPlanInfo(UserInfo userinfo) {
		List lPlanList = null;
		
		// 组成sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id , p.planname, p.executorid, to_char(p.begindate,'yyyy-MM-dd') begindate, to_char(p.enddate,'yyyy-MM-dd') enddate,");
		sql.append(" pmi.patrolname, NVL(ps.planpointtimes, 0) plantimes, ");
		sql.append(" NVL(ps.actualpointtimes,0) patroltimes,  NVL((ps.planpointtimes-ps.actualpointtimes),0) leaktimes ");
		sql.append(" from plan p  ");
		sql.append(" left join patrolmaninfo pmi on p.executorid = pmi.patrolid ");
		sql.append(" left join plancurrent_stat ps on p.id = ps.curplanid ");
		// 真实用的
		//sql.append(" where p.begindate <= sysdate and p.enddate >= sysdate ");
		sql.append(" where p.begindate <= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd') and p.enddate >= to_date(to_char(sysdate,'yyyy-MM-dd'),'yyyy-MM-dd')");
		// 测试用的语句
		//sql.append(" where p.begindate <= to_date('2008-11-10','yyyy-MM-dd') and p.enddate >= to_date('2008-11-10','yyyy-MM-dd') ");
		
		sql.append(" and pmi.parentid = '" + userinfo.getDeptID() + "'");
		sql.append(" order by p.createdate ");
		
		logger.info("取得当前正在执行计划信息Sql语句：" + sql);

		try {
			QueryUtil qu = new QueryUtil();
			lPlanList = qu.queryBeans(sql.toString());		
		} catch (Exception ex) {
			logger.warn("取得当前正在执行计划信息出错:" + ex.getMessage());
		}
		return lPlanList;
	}
	
	
	/**
	 * 取得计划中巡检过的线段信息
	 * @return
	 */
	public List getSublinePatrolInfo(String planid) {
		List lSubline = null;
		
		// 组成sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id, sli.sublineid, sli.sublinename, cd.name codename, li.linename,  pp.patroltimes ");
		sql.append(" from plan p join plantasklist ptl on p.id = ptl.planid ");
		sql.append(" join plantasksubline ptsl on ptl.taskid = ptsl.taskid ");
		sql.append(" join sublineinfo sli on ptsl.sublineid = sli.sublineid ");
		sql.append(" join lineinfo li on sli.lineid = li.lineid ");
		sql.append(" left join lineclassdic cd on li.linetype = cd.code ");
		sql.append(" join (");
		sql.append(" 		select pt.sublineid, count(*) patroltimes from CURRENT_PATROL_DETAIL cpd, pointinfo pt ");
		sql.append("        where cpd.pointid = pt.pointid ");
		sql.append("        and cpd.planid='"+planid+"' ");
		sql.append("        group by sublineid ");
		sql.append("      ) pp on  pp.sublineid = sli.sublineid  ");
		sql.append(" where p.id = '" + planid + "'");
		sql.append(" order by sublineid ");
		
		logger.info("取得计划中巡检过的线段信息Sql语句：" + sql);

		try {
			QueryUtil qu = new QueryUtil();
			lSubline = qu.queryBeans(sql.toString());		
		} catch (Exception ex) {
			logger.warn("取得计划中巡检过的线段信息出错:" + ex.getMessage());
		}
		return lSubline;
	}
	
	
	/**
	 * 取得计划中巡检过的点信息
	 * @param planid 
	 * @return
	 */
	public List getPointPatrolInfo(String sublineid, String planid) {
		List lPoint = null;
		
		// 组成sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select cpd.planid, cpd.pointid, cpd.simid, to_char(cpd.patroldate, 'yyyy-MM-dd hh24:mi:ss') patroldate, pi.pointname ");
		sql.append(" from CURRENT_PATROL_DETAIL cpd, pointinfo pi ");
		sql.append(" where cpd.pointid = pi.pointid ");
		sql.append(" and cpd.planid='"+planid+"' ");
		sql.append(" and pi.sublineid = '" + sublineid + "'");
		sql.append(" order by patroldate");
		
		logger.info("取得计划中巡检过的点信息Sql语句：" + sql);

		try {
			QueryUtil qu = new QueryUtil();
			lPoint = qu.queryBeans(sql.toString());		
		} catch (Exception ex) {
			logger.warn("取得计划中巡检过的点信息出错:" + ex.getMessage());
		}
		return lPoint;
	}
	
	
	/**
	 * 取得计划中漏检的线段信息
	 * @return
	 */
	public List getSublineLeakInfo(String planid) {
		List lSubline = null;
		
		// 组成sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id, sli.sublineid, sli.sublinename, cd.name codename, li.linename, pp.leaktimes ");
		sql.append(" from plan p join plantasklist ptl on p.id = ptl.planid ");
		sql.append(" join plantasksubline ptsl on ptl.taskid = ptsl.taskid ");
		sql.append(" join sublineinfo sli on ptsl.sublineid = sli.sublineid ");
		sql.append(" join lineinfo li on sli.lineid = li.lineid ");
		sql.append(" left join lineclassdic cd on li.linetype = cd.code ");
		sql.append(" join (");
		sql.append(" 		select pt.sublineid, sum(cld.LEAKTIMES) leaktimes from CURRENT_LEAK_DETAIL cld, pointinfo pt ");
		sql.append("        where cld.pointid = pt.pointid ");
		sql.append("        and cld.planid='"+planid+"' ");
		sql.append("        group by sublineid ");
		sql.append("      ) pp on  pp.sublineid = sli.sublineid  ");
		sql.append(" where p.id = '" + planid + "'");
		sql.append(" order by sublineid ");
		
		logger.info("取得计划中漏检的线段信息Sql语句：" + sql);

		try {
			QueryUtil qu = new QueryUtil();
			lSubline = qu.queryBeans(sql.toString());		
		} catch (Exception ex) {
			logger.warn("取得计划中漏检的线段信息出错:" + ex.getMessage());
		}
		return lSubline;
	}
	
	/**
	 * 取得计划中漏检的点信息
	 * @return
	 */
	public List getPointLeakInfo(String sublineid,String planid) {
		List lPoint = null;
		
		// 组成sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select cld.planid, cld.pointid, cld.patroltimes, cld.leaktimes, cld.plantimes,  pi.pointname ");
		sql.append(" from current_leak_detail cld, pointinfo pi ");
		sql.append(" where cld.pointid = pi.pointid ");
		sql.append(" and cld.planid='"+planid+"' ");
		sql.append(" and pi.sublineid = '" + sublineid + "'");
		sql.append(" order by cld.leaktimes");
		
		logger.info("取得计划中漏检的点信息Sql语句：" + sql);

		try {
			QueryUtil qu = new QueryUtil();
			lPoint = qu.queryBeans(sql.toString());		
		} catch (Exception ex) {
			logger.warn("取得计划中漏检的点信息出错:" + ex.getMessage());
		}
		return lPoint;
	}
	
}
