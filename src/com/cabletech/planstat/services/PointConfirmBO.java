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

	// ���ݽ��������÷����������߶������б�
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
		logger.info("����������߶����Ƶõ��߶��б�SQL:" + sql);
		List list = pointConfirmDAOImpl.getBackInfoList(sql);
		return list;
	}

	// ��ʾ��ѡѲ���߶ε�Ѳ�����Ϣ�б�
	public List getPointInfo(String sublineName) {
		sql = "SELECT distinct p.pointid,p.pointname, p.addressinfo, DECODE(p.isfocus,'1','��','') isfocus "
				+ "FROM POINTINFO p,subtaskinfo s "
				+ "WHERE p.pointid=s.objectid and p.sublineid = '"
				+ sublineName
				+ "' " + "and p.STATE is not null " + "ORDER BY p.pointname ";
		logger.info("��ʾ��ѡѲ���߶ε�Ѳ�����Ϣ�б�SQL:" + sql);
		List list = pointConfirmDAOImpl.getBackInfoList(sql);
		return list;
	}

}
