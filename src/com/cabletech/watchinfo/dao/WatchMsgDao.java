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

	/**<br>����:��ô�ά�б�
	 *  <br>����:�û�����
	 *  <br>����:�����ű�ź����Ƶ�List
	 * */
	public List getdeptInfo(UserInfo userinfo) {
		List lDept = null;
		String sql = "";
		
		if (userinfo.getDeptype().equals("2")) {
			// ��ά�û�
			sql = "select c.CONTRACTORID conid,c.CONTRACTORNAME conname from contractorinfo c where c.state is null and c.contractorid='"
					+ userinfo.getDeptID() + "'";
		} else {			
			// �ƶ��û�
			sql = "select c.CONTRACTORID conid,c.CONTRACTORNAME conname from contractorinfo c where c.state is null and c.regionid='"
				+ userinfo.getRegionID() + "'";
		}
		logger.info("��ô�ά�б�: " + sql);
		try {
			QueryUtil qu = new QueryUtil();
			lDept = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("��ô�ά�б����:" + ex.getMessage());
		}
		
		return lDept;
	}
	
	
	/**
	 * ȡ��Ѳ�����б�
	 * @param conId
	 * @return
	 */
	public List getPatrolInfo(String conId) {
		List lPatrol = null;
		String sql = "select p.patrolid, p.patrolname from patrolmaninfo p "
					+ "where p.state is null and p.parentid = '" + conId + "'";
		
		try {
			QueryUtil qu = new QueryUtil();
			
			logger.info("ȡ��Ѳ�����б�: " + sql);
			
			lPatrol = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("ȡ��Ѳ�����б����:" + ex.getMessage());
		}
		
		return lPatrol;
	}
	
	
	/**
	 * ȡ�ö��������б�
	 * @param conId
	 * @return
	 */
	public List getWacthInfo(String queryType, String conId) {
		List lwatch = null;
		//String sql = "select distinct  w.placeid, w.placename from watchexecute we join watchinfo w on we.watchid = w.placeid  ";
		String sql = "select distinct  w.placeid, w.placename from watchinfo w left join watchexecute we  on we.watchid = w.placeid  ";
		if ("0".equals(queryType)) {
			// ���ڽ��еĶ�����Ϣ
			sql += "where w.dealstatus != '2' and begindate <= sysdate ";
		} else {
			// �����Ķ�����Ϣ
			sql += "where w.dealstatus = '2' ";
		}
		
		//sql += "and w.executorid =  '" + conId + "'"; principal
		sql += "and w.principal =  '" + conId + "'";
		
		logger.info("ȡ�ö��������б�: " + sql);
		
		try {
			QueryUtil qu = new QueryUtil();
			lwatch = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("ȡ�ö��������б�:" + ex.getMessage());
		}
		
		return lwatch;
	}
	
	
	/**
	 * ȡ�ö����Ķ�����Ϣ�б�
	 * @param conId
	 * @return
	 */
	public List getWacthMsgInfo(WatchMsgBean bean) {
		List lMsgWatch = null;
		//String sql = "select we.simid, to_char(we.executetime,'yyyy-mm-dd HH24:MI:SS') executetime, we.gpscoordinate xx,  we.gpscoordinate yy,we.id from watchinfo w join watchexecute we on w.placeid = we.watchid ";
		String sql = "select we.simid, to_char(we.executetime,'yyyy-mm-dd HH24:MI:SS') executetime, we.gpscoordinate xx,  we.gpscoordinate yy,we.id from watchinfo w left join watchexecute we on w.placeid = we.watchid ";
		if ("0".equals(bean.getPagetype())) {
			// ���ڽ��еĶ�����Ϣ
			sql += "where w.dealstatus != '2' and begindate <= sysdate ";
		} else {
			// �����Ķ�����Ϣ
			sql += "where w.dealstatus = '2' ";
		}
		// ����id
		String watchId = bean.getWatchid();
		if(watchId != null && !"".equals(watchId)) {
			sql += "and we.watchid = '" + watchId + "'";
		}
		
		// ��ѯ��ʼʱ��
		String beginDate = bean.getBegindate();
		if(beginDate != null && !"".equals(beginDate)) {		
			sql += " and we.executetime >=TO_DATE('" + beginDate
			+ " 00:00:00','YYYY-MM-DD HH24:MI:SS ')";
		}
		// ��ѯ����ʱ��
		String endDate = bean.getEnddate();
		if(endDate != null && !"".equals(endDate)) {
			sql += " and we.executetime <= TO_DATE('" + endDate
			+ " 23:59:59','YYYY-MM-DD HH24:MI:SS')";			
		}
		
		sql += " order by we.executetime";
		logger.info("��ѯ����������־��sql: " + sql);
		
		try {
			QueryUtil qu = new QueryUtil();
			lMsgWatch = qu.queryBeans(sql);			
		} catch (Exception ex) {
			logger.warn("ȡ�ö����Ķ�����Ϣ�б�:" + ex.getMessage());
		}
		
		return lMsgWatch;
	}

}
