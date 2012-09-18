package com.cabletech.planstat.services;

import java.util.List;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.dao.PointConfirmDAOImpl;

public class PointConfirmBO extends BaseBisinessObject {
	Logger logger = Logger.getLogger(this.getClass().getName());

	private String sql = null;

	private PointConfirmDAOImpl pointConfirmDAOImpl = null;

	public PointConfirmBO() {
		pointConfirmDAOImpl = new PointConfirmDAOImpl();
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
		List list = pointConfirmDAOImpl.getBackInfoList(sql);
		return list;
	}

	// 显示所选巡检线段的巡检点信息列表
	public List getPointInfo(String sublineName) {
		sql = "SELECT distinct p.pointid,p.pointname, p.addressinfo, DECODE(p.isfocus,'1','是','') isfocus "
				+ "FROM POINTINFO p,subtaskinfo s "
				+ "WHERE p.pointid=s.objectid and p.sublineid = '"
				+ sublineName
				+ "' " + "and p.STATE is not null " + "ORDER BY p.pointname ";
		logger.info("显示所选巡检线段的巡检点信息列表SQL:" + sql);
		List list = pointConfirmDAOImpl.getBackInfoList(sql);
		return list;
	}

}
