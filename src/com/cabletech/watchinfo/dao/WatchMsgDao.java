package com.cabletech.watchinfo.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.watchinfo.beans.WatchMsgBean;

public class WatchMsgDao {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public WatchMsgDao() {
	}

	/**<br>功能:获得代维列表
	 *  <br>参数:用户对象
	 *  <br>返回:受理部门编号和名称的List
	 * */
	public List getdeptInfo(UserInfo userinfo) {
		List lDept = null;
		String sql = "";
		
		if (userinfo.getDeptype().equals("2")) {
			// 代维用户
			sql = "select c.CONTRACTORID conid,c.CONTRACTORNAME conname from contractorinfo c where c.state is null and c.contractorid='"
					+ userinfo.getDeptID() + "'";
		} else {			
			// 移动用户
			sql = "select c.CONTRACTORID conid,c.CONTRACTORNAME conname from contractorinfo c where c.state is null and c.regionid='"
				+ userinfo.getRegionID() + "'";
		}
		logger.info("获得代维列表: " + sql);
		try {
			QueryUtil qu = new QueryUtil();
			lDept = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("获得代维列表出错:" + ex.getMessage());
		}
		
		return lDept;
	}
	
	
	/**
	 * 取得巡检组列表
	 * @param conId
	 * @return
	 */
	public List getPatrolInfo(String conId) {
		List lPatrol = null;
		String sql = "select p.patrolid, p.patrolname from patrolmaninfo p "
					+ "where p.state is null and p.parentid = '" + conId + "'";
		
		try {
			QueryUtil qu = new QueryUtil();
			
			logger.info("取得巡检组列表: " + sql);
			
			lPatrol = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("取得巡检组列表出错:" + ex.getMessage());
		}
		
		return lPatrol;
	}
	
	
	/**
	 * 取得盯防名称列表
	 * @param conId
	 * @return
	 */
	public List getWacthInfo(String queryType, String conId) {
		List lwatch = null;
		//String sql = "select distinct  w.placeid, w.placename from watchexecute we join watchinfo w on we.watchid = w.placeid  ";
		String sql = "select distinct  w.placeid, w.placename from watchinfo w left join watchexecute we  on we.watchid = w.placeid  ";
		if ("0".equals(queryType)) {
			// 正在进行的盯防信息
			sql += "where w.dealstatus != '2' and begindate <= sysdate ";
		} else {
			// 结束的盯防信息
			sql += "where w.dealstatus = '2' ";
		}
		
		//sql += "and w.executorid =  '" + conId + "'"; principal
		sql += "and w.principal =  '" + conId + "'";
		
		logger.info("取得盯防名称列表: " + sql);
		
		try {
			QueryUtil qu = new QueryUtil();
			lwatch = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("取得盯防名称列表:" + ex.getMessage());
		}
		
		return lwatch;
	}
	
	
	/**
	 * 取得盯防的短信信息列表
	 * @param conId
	 * @return
	 */
	public List getWacthMsgInfo(WatchMsgBean bean) {
		List lMsgWatch = null;
		//String sql = "select we.simid, to_char(we.executetime,'yyyy-mm-dd HH24:MI:SS') executetime, we.gpscoordinate xx,  we.gpscoordinate yy,we.id from watchinfo w join watchexecute we on w.placeid = we.watchid ";
		String sql = "select we.simid, to_char(we.executetime,'yyyy-mm-dd HH24:MI:SS') executetime, we.gpscoordinate xx,  we.gpscoordinate yy,we.id from watchinfo w left join watchexecute we on w.placeid = we.watchid ";
		if ("0".equals(bean.getPagetype())) {
			// 正在进行的盯防信息
			sql += "where w.dealstatus != '2' and begindate <= sysdate ";
		} else {
			// 结束的盯防信息
			sql += "where w.dealstatus = '2' ";
		}
		// 盯防id
		String watchId = bean.getWatchid();
		if(watchId != null && !"".equals(watchId)) {
			sql += "and we.watchid = '" + watchId + "'";
		}
		
		// 查询开始时间
		String beginDate = bean.getBegindate();
		if(beginDate != null && !"".equals(beginDate)) {		
			sql += " and we.executetime >=TO_DATE('" + beginDate
			+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
		}
		// 查询结束时间
		String endDate = bean.getEnddate();
		if(endDate != null && !"".equals(endDate)) {
			sql += " and we.executetime <= TO_DATE('" + endDate
			+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		
		sql += " order by we.executetime";
		logger.info("查询盯防短信日志的sql: " + sql);
		
		try {
			QueryUtil qu = new QueryUtil();
			lMsgWatch = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("取得盯防的短信信息列表:" + ex.getMessage());
		}
		
		return lMsgWatch;
	}

}
