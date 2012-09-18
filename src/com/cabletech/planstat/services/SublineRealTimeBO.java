package com.cabletech.planstat.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.planstat.dao.SublineRealTimeDAOImpl;
import com.cabletech.planstat.domainobjects.SublineRequest;
import com.cabletech.planstat.domainobjects.SublineResponse;

public class SublineRealTimeBO extends BaseBisinessObject {
	Logger logger = Logger.getLogger(this.getClass().getName());

	private String sql = null;

	private SublineRealTimeDAOImpl sublineRealTimeDAOImpl = null;

	public SublineRealTimeBO() {
		sublineRealTimeDAOImpl = new SublineRealTimeDAOImpl();
	}

	// 依据界面输入查得符合条件的线段名称列表
	public List getSublineListByInput(String sublineName, UserInfo userinfo) {
		sql = "SELECT distinct s.sublineid, s.sublinename "
				+ "FROM SUBLINEINFO s,PLAN p,PATROLMANINFO pm "
				+ "WHERE p.EXECUTORID = pm.PATROLID " + "AND pm.PARENTID = '"
				+ userinfo.getDeptID()
				+ "' and s.state IS NULL AND pm.STATE IS NULL "
				+ "and s.sublinename like '%" + sublineName + "%' "
				+ "AND s.PATROLID = pm.PATROLID "
				+ "AND (trunc(SYSDATE) BETWEEN p.BEGINDATE AND p.ENDDATE) "
				+ "ORDER BY s.sublinename";
		logger.info("依据输入的线段名称得到线段列表SQL:" + sql);
		ResultSet rs = null;
		if (sql != null || !"".equals(sql)) {
			rs = sublineRealTimeDAOImpl.getReceiverListJDBC(sql);
		}
		if (rs == null) {
			logger.info("rs is null");
			return null;
		}
		Map map;
		List list = new ArrayList();
		try {
			while (rs.next()) {
				map = new HashMap();
				map.put("sublineid", rs.getString(1));
				map.put("sublinename", rs.getString(2));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查看是否在约定时间内有重复提交
	public String checkReCommit(UserInfo userinfo, String[] sublineList) {
		GisConInfo config = GisConInfo.newInstance();
		String minutesforresubmit = config.getMinutesforresubmit();
		String strSublineId = "";
		for (int i = 0; i < sublineList.length; i++) {
			if (i == sublineList.length - 1) {
				strSublineId = strSublineId + "'" + sublineList[i] + "'";
			} else {
				strSublineId = strSublineId + "'" + sublineList[i] + "'" + ",";
			}
		}
		sql = "SELECT TO_CHAR(t.requesttime,'yyyy-mm-dd hh24:mi:ss') requesttime, e.sublineid "
				+ "FROM subline_request t, subline_response e "
				+ "WHERE t.requestid = e.requestid "
				+ "AND e.sublineid in("
				+ strSublineId.trim()
				+ ") "
				+ "AND t.userid = '"
				+ userinfo.getUserID()
				+ "' "
				+ "AND t.requesttime > SYSDATE - INTERVAL '"
				+ minutesforresubmit + "' MINUTE";
		logger.info("查看是否在约定时间内有重复提交SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		if (list.size() == 0) {
			logger.info("list is null");
			return "0";
		}
		logger.info("list is not null");
		return "1";
	}

	// 查看请求线段列表
	public List getRequestSublineList(UserInfo userinfo) {
		sql = "SELECT t.requestid,to_char(t.requesttime,'hh24:mi:ss') requesttime,"
				+ "s.sublinename,e.sublineid,e.state "
				+ "FROM subline_request t, subline_response e,sublineinfo s "
				+ "WHERE t.requestid = e.requestid "
				+ "AND e.SUBLINEID = s.SUBLINEID "
				+ "AND t.USERID='"
				+ userinfo.getUserID()
				+ "' "
				+ "AND TRUNC(SYSDATE) = TRUNC(t.requesttime) "
				+ "ORDER BY requesttime";
		logger.info("查看请求线段列表SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		return list;
	}

	// 查看每条巡检线段的回复结果
	public List getResponsePerSubline(String sublineId, String requestId) {
		sql = "SELECT e.nonpatrolnum, e.planpointnum, e.actualpointnum, e.todaypointnum,"
				+ "DECODE (e.deadlinedays, NULL, ' ', e.deadlinedays) deadlinedays,e.sublineid "
				+ "FROM subline_response e "
				+ "WHERE e.requestid = '"
				+ requestId + "' AND e.sublineid = '" + sublineId + "'";
		logger.info("查看每条巡检线段的回复结果SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		return list;
	}

	// 查看指定巡检线段未巡检的点
	public List getResponseUnpatrol(String sublineId) {
		sql = "SELECT p.pointname, p.addressinfo, DECODE(p.isfocus,'1','是','') isfocus,sr.lacktimes "
				+ "FROM subline_responsenonpatrol sr, pointinfo p "
				+ "WHERE sr.sublineid = p.sublineid AND sr.sublineid = '"
				+ sublineId
				+ "' AND p.pointid = sr.pointid "
				+ "ORDER BY sr.lacktimes";
		logger.info("查看指定巡检线段未巡检的点SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		return list;
	}

	// 查看指定巡检线段今天的巡检点
	public List getResponseToday(String sublineId) {
		sql = "SELECT TO_CHAR(sr.patroltime,'hh24:mi:ss') patroltime,p.pointname, p.addressinfo, DECODE(p.isfocus,'1','是','') isfocus "
				+ "FROM subline_responsetoday sr, pointinfo p "
				+ "WHERE sr.sublineid = p.sublineid AND sr.sublineid = '"
				+ sublineId
				+ "' AND p.pointid = sr.pointid "
				+ "ORDER BY sr.patroltime";
		logger.info("查看指定巡检线段今天的巡检点SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		return list;
	}

	public void addRealTimeSublineRequest(SublineRequest data) throws Exception {
		sublineRealTimeDAOImpl.addRealTimeSublineRequest(data);
	}

	public void addRealTimeSublineResponse(SublineResponse data)
			throws Exception {
		sublineRealTimeDAOImpl.addRealTimeSublineResponse(data);
	}
	
	// 依据界面输入查得符合条件的线段名称列表
	public List getSublineListByInputAjax(String sublineName, UserInfo userinfo) {
		sql = "SELECT   s.sublineid, s.sublinename "
				+ "FROM SUBLINEINFO s,PLAN p,PATROLMANINFO pm "
				+ "WHERE p.EXECUTORID = pm.PATROLID " + "AND pm.PARENTID = '"
				+ userinfo.getDeptID()
				+ "' and s.state IS NULL AND pm.STATE IS NULL "
				+ "and s.sublinename like '%" + sublineName + "%' "
				+ "AND s.PATROLID = pm.PATROLID "
				//+ "AND (SYSDATE BETWEEN p.BEGINDATE AND p.ENDDATE) "
				+ "ORDER BY s.sublinename";
		logger.info("依据输入的线段名称得到线段列表SQL:" + sql);
		List list = sublineRealTimeDAOImpl.getResultList(sql);
		return list;
	}
}
