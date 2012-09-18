package com.cabletech.statistics.dao;

import java.sql.*;
import java.util.*;
import javax.servlet.http.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.struts.util.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;

public class StatDao {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public StatDao() {
	}

	/**
	 * <br>
	 * ����:��õ�ǰ��½�û���Ϣ <br>
	 * ����:request <br>
	 * ����:�û�����
	 */
	private UserInfo getUserInfo(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		return userinfo;
	}

	/**
	 * <br>
	 * ����:���ָ��������ִ�мƻ���� <br>
	 * ����:��ѯ������:ִ����,ִ�е�λ,��ʼʱ��,����ʱ��,Ѳ���߶�,Ѳ����· <br>
	 * ����:String,�ö��ŷָ�.
	 */
	private String getPlanIdArr(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		// �жϲ����ĺϷ���:��ʼ���ںͽ������ڲ���Ϊ��
		if (begindate.equals("") || begindate == null || enddate.equals("")
				|| enddate == null) {
			logger.warn("���ָ��������ִ�мƻ���ŷ���ִ���в������Ϸ�");
			return null;
		}
		String planIdArr = " ('";
		String sql = "";
		sql = "select distinct p.id from plan p where begindate >= TO_DATE('"
				+ begindate + "','yyyy-MM-dd')" + " and enddate <= TO_DATE('"
				+ enddate + "','yyyy-MM-dd')";
		if (sublineid != null && !sublineid.equals("")) {
			sql += " and p.ID in "
					+ " (select distinct planid from plantasklist "
					+ " where taskid in "
					+ "          (select distinct taskid from subtaskinfo "
					+ "            where objectid in "
					+ "                   (select pointid from pointinfo where sublineid ='"
					+ sublineid + "')" + "           ) )";
		} else {
			if (lineid != null && !lineid.equals("")) {
				sql += " and p.ID in "
						+ " (select distinct planid from plantasklist "
						+ " where taskid in "
						+ "       (select distinct taskid from subtaskinfo "
						+ "         where objectid in "
						+ "               (select pointid from pointinfo "
						+ "                  where sublineid  in "
						+ "                                   (select sublineid from sublineinfo where lineid = '"
						+ lineid + "')" + "                  ) ) ) ";
			}
		}

		if (executorid != null && !executorid.equals("")) {
			sql += " and p.EXECUTORID = '" + executorid + "'";
		} else {
			if (contractorid != null && !contractorid.equals("")) {
				sql += " and p.EXECUTORID in (select patrolid from patrolmaninfo where parentid = '"
						+ contractorid + "')";
			}
		}
		ResultSet rst;
		try {
			QueryUtil qu = new QueryUtil();
			rst = qu.executeQuery(sql);
			while (rst != null && rst.next()) {
				planIdArr += rst.getString("id") + "','";
			}
			planIdArr += "aa')";
			// System.out.println( "���ָ��������ִ�мƻ����:" + planIdArr );
			return planIdArr;
		} catch (Exception e) {
			logger.warn("���ָ��������ִ�мƻ�����쳣:" + e.getMessage());
			return "( 'aa' )";
		}
	}

	/**
	 * <br>
	 * ����:���� <br>
	 * ����:��ѯ������:ִ����,ִ�е�λ,��ʼʱ��,����ʱ��,Ѳ���߶�,Ѳ����· <br>
	 * ����:��ѯ���,��ѯ����ǵ��ź�ִ�еĴ���
	 */
	public String buildExePointSQL(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		String sql = "";
		sql = "(select p.pid,count(p.pid) realexec   from planexecute p where executetime >= TO_DATE('"
				+ begindate
				+ "','yyyy-MM-dd')"
				+ " and executetime <= TO_DATE('"
				+ enddate
				+ " 23:59:59','yyyy-MM-dd HH24:MI:SS')";
		if (sublineid != null && !sublineid.equals("")) {
			sql += " and p.lid = '" + sublineid + "'";
		} else {
			if (lineid != null && !lineid.equals("")) {
				sql += " and p.lid in "
						+ " ( select distinct sublineid from sublineinfo where lineid='"
						+ lineid + "')";
			}
		}

		if (executorid != null && !executorid.equals("")) {
			sql += " and p.EXECUTORID = '" + executorid + "'";
		} else {
			if (contractorid != null && !contractorid.equals("")) {
				sql += " and p.EXECUTORID in (select patrolid from patrolmaninfo where parentid = '"
						+ contractorid + "')";
			}
		}
		sql += "group by p.pid  order by p.pid )";
		// System.out.println( "�����ѯָ�������ļƻ�ִ�е���Ϣ���SQL:" + sql );
		return sql;
	}

	/**
	 * <br>
	 * ����:�������, <br>
	 * ����:��ѯ������:ִ����,ִ�е�λ,��ʼʱ��,����ʱ��,Ѳ���߶�,Ѳ����· <br>
	 * ����:��ѯ���,��ѯ����ǵ��źͼƻ�ִ�еĴ���
	 */
	private String buildPlanPointSQL(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		String sql = "(select s.objectid,  SUM(t.EXCUTETIMES) planexe from subtaskinfo s,taskinfo t"
				+ " where  s.taskid=t.id "
				+ " and s.TASKID in ( select  taskid from plantasklist where planid in "
				+ getPlanIdArr(executorid, contractorid, begindate, enddate,
						sublineid, lineid) + ")" + " group by s.objectid  ) ";
		return sql;
	}

	/**
	 * <br>
	 * ����:�������, <br>
	 * ����:��ѯ������:ִ����,ִ�е�λ,��ʼʱ��,����ʱ��,Ѳ���߶�,Ѳ����· <br>
	 * ����:��ѯ���,��ѯ����ǵ��źͼƻ�ִ�еĴ���
	 */
	private String buildAllPlanPointSQL(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		String sql = "(select aa.objectid,aa.planexe,p.POINTNAME,p.ADDRESSINFO,p.ISFOCUS,sub.SUBLINENAME,lin.LINENAME,par.PATROLNAME "
				+ " from pointinfo p,sublineinfo sub,lineinfo lin,patrolmaninfo par,"
				+ buildPlanPointSQL(executorid, contractorid, begindate,
						enddate, sublineid, lineid)
				+ " aa "
				+ " where p.POINTID = aa.objectid  and p.SUBLINEID = sub.SUBLINEID and lin.LINEID = sub.LINEID and sub.PATROLID=par.patrolid  )";
		return sql;
	}

	// //////////////////////////////////////////////

	// ///////////////////////////////////////////////
	/**
	 * <br>
	 * ����:�����ϸ, <br>
	 * ����:��ѯ������:ִ����,ִ�е�λ,��ʼʱ��,����ʱ��,Ѳ���߶�,Ѳ����· <br>
	 * ����:��ѯ���,
	 */
	public List getLossDetail(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		List lLoss = null;
		String sql = "select p.objectid,p.planexe,p.POINTNAME,p.ADDRESSINFO,decode(p.ISFOCUS,0,'��',1,'��','��') ISFOCUS,p.SUBLINENAME,p.LINENAME,p.PATROLNAME,p.planexe-NVL(e.realexec, 0) losstime"
				+ " from "
				+ this.buildAllPlanPointSQL(executorid, contractorid,
						begindate, enddate, sublineid, lineid)
				+ " p, "
				+ this.buildExePointSQL(executorid, contractorid, begindate,
						enddate, sublineid, lineid)
				+ " e "
				+ " where p.objectid=e.pid(+) and p.planexe >NVL(e.realexec, 0)";

		if (sublineid != null && !sublineid.equals("")) {
			sql += " and p.objectid in( select pointid from pointinfo where sublineid= '"
					+ sublineid + "')";
		} else {
			if (lineid != null && !lineid.equals("")) {
				sql += " and p.objectid in ( select pointid from pointinfo where sublineid in (select sublineid from sublineinfo where lineid='"
						+ lineid + "')) ";
			}
		}

		sql += " order by patrolname,linename,sublinename,pointname ";
		logger.info("SQLlm:" + sql);
		try {
			QueryUtil qu = new QueryUtil();
			lLoss = qu.queryBeans(sql);
			return lLoss;
		} catch (Exception e) {
			logger.warn("" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ָ��������Ѳ����Ϣ <br>
	 * ����:��ѯ�������� <br>
	 * ����:��ѯ�ɹ����� List ������null
	 */
	public List queryPatrolDetail(QueryCondition condition) {
		// ��ʼ������
		// prepareData(condition);
		String patrolManID = condition.getPatrolid();
		String sublineID = condition.getSublineid();
		String deptID = condition.getDeptid();
		String queryBy = condition.getQueryby();

		if (queryBy.equalsIgnoreCase("ByDepart")) {
			deptID = condition.getDeptid();
			patrolManID = null;
			sublineID = null;
		} else {
			if (queryBy.equalsIgnoreCase("ByPatrolMan")) {
				deptID = null;
				patrolManID = condition.getPatrolid();
				sublineID = null;
			} else {
				if (queryBy.equalsIgnoreCase("BySubline")) {
					deptID = null;
					patrolManID = null;
					sublineID = condition.getSublineid();
				}
			}
		}
		condition.setPatrolid(patrolManID);
		condition.setSublineid(sublineID);
		condition.setDeptid(deptID);

		String sql = "";
		sql += " select	d.patrolname patrolname, f.contractorname contractorname, c.sublinename sublinename, b.pointname pointname, b.addressinfo position, to_char(a.executetime, 'yy/mm/dd hh24:mi') executetime	\n";
		sql += " from  planexecute a, pointinfo b,sublineinfo c, patrolmaninfo d,contractorinfo f																						\n";
		String constant = " a.pid = b.pointid   and	a.lid = c.sublineid    and	a.executorid = d.patrolid  and	d.parentid = f.contractorid    \n";
		String orderby = " order by 	contractorname,   patrolname,   executetime desc \n";

		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild.addConstant(constant);
		sqlBuild.addConditionAnd("a.executorid={0}", condition.getPatrolid());
		sqlBuild.addConditionAnd("f.contractorid={0}", condition.getDeptid());
		sqlBuild.addDateFormatStrEnd("a.executetime", DateUtil
				.DateToString(condition.getBegindate()), ">=");
		sqlBuild.addDateFormatStrEnd("a.executetime", DateUtil
				.DateToString(condition.getEnddate()), "<=");
		sqlBuild.addConditionAnd("a.lid={0}", condition.getSublineid());
		sqlBuild.addConstant(orderby);
		logger.info("SQL:" + sqlBuild.toSql());

		try {
			QueryUtil queryUtil = new QueryUtil();
			List list = queryUtil.queryBeans(sqlBuild.toSql());
			return list;
		} catch (Exception e) {
			logger.warn("���ָ��������Ѳ����Ϣ�쳣" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ����д�ά��λ������Ϣ <br>
	 * ����:request <br>
	 * ����:List
	 */
	public List getContracorInfo(HttpServletRequest request) {
		List lCon = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// ���ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo  c where c.state is null and c.regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// ��ά��λ�û�
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null and c.contractorid ='"
					+ userinfo.getDeptID() + "'";
		}
		// ʡ�ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null";
		}
		// ʡ��ά�û�
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null and c.PARENTCONTRACTORID = '"
					+ userinfo.getDeptID() + "'";
		}
		// System.out.println( "���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ����д�ά��λ������ϢSQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			lCon = qu.queryBeans(sql);
			return lCon;
		} catch (Exception e) {
			logger.warn("���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ����д�ά��λ������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ������ߵ�������Ϣ <br>
	 * ����:request <br>
	 * ����:List
	 */
	public List getLineInfo(HttpServletRequest request) { // ////
		List lSubline = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// ���ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l "
					+ " where l.regionid='" + userinfo.getRegionID() + "'";
		}
		// ��ά��λ�û�
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = " select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l "
					+ " where l.LINEID in "
					+ " ( select s.LINEID from sublineinfo s "
					+ "   where s.PATROLID in "
					+ "         ( select p.PATROLID from patrolmaninfo p where  p.PARENTID='"
					+ userinfo.getDeptID() + "'))";

		}
		// ʡ�ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = " select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l ";
		}
		// ʡ��ά�û�
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = " select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l "
					+ " where l.LINEID in "
					+ " ( select s.LINEID from sublineinfo s "
					+ "   where s.PATROLID in "
					+ "        ( select p.PATROLID from patrolmaninfo p "
					+ "          where  p.PARENTID in "
					+ "               ( select con.contractorid from contractorinfo con where con.PARENTCONTRACTORID = '"
					+ userinfo.getDeptID() + "')))";

		}
		sql += " order by l.regionid,l.linename";
		logger.info("sql" + sql);
		try {
			QueryUtil qu = new QueryUtil();
			lSubline = qu.queryBeans(sql);
			return lSubline;
		} catch (Exception e) {
			logger.warn("���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ������ߵ�������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ������߶ε�������Ϣ <br>
	 * ����:request <br>
	 * ����:List
	 */
	public List getSublineInfo(HttpServletRequest request, String type) {
		List lSubline = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// ���ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// ��ά��λ�û�
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.PATROLID in ( select patrolid from patrolmaninfo where parentid = '"
					+ userinfo.getDeptID() + "')";
		}
		// ʡ�ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s";
		}
		// ʡ��ά�û�
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.PATROLID in ( select patrolid from patrolmaninfo where parentid in ( select contractorid from contractorinfo where parentcontractorid='"
					+ userinfo.getDeptID() + "'))";
		}
		logger.info("���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ������߶ε�������ϢSQL:" + sql);
		if (type.equals("loss")) {
			sql += " order by s.LINEID,s.sublinename";
		} else {
			sql += " order by s.REGIONID,s.sublinename";
		}

		try {
			QueryUtil qu = new QueryUtil();
			lSubline = qu.queryBeans(sql);
			return lSubline;
		} catch (Exception e) {
			logger.warn("���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ������߶ε�������Ϣ�쳣:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * ����:���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ�����Ѳ��ά�����������Ϣ <br>
	 * ����:request <br>
	 * ����:List
	 */
	public List getPatrolManInfo(HttpServletRequest request) {
		List lPatrolMan = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// ���ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// ��ά��λ�û�
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and p.PARENTID ='"
					+ userinfo.getDeptID() + "'";
		}
		// ʡ�ƶ��û�
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p ";
		}
		// ʡ��ά�û�
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and  p.PARENTID in (SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userinfo.getDeptID() + "')";
		}
		// System.out.println( "���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ�����Ѳ��ά�����������ϢSQL:" + sql );
		sql += " order by p.PARENTID,p.PATROLNAME";
		try {
			QueryUtil qu = new QueryUtil();
			lPatrolMan = qu.queryBeans(sql);
			return lPatrolMan;
		} catch (Exception e) {
			logger
					.warn("���ݵ�ǰ��½�û����ͼ������ù�Ͻ��Χ�ڵ�����Ѳ��ά�����������Ϣ�쳣:"
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * �����û���ѯ��ά��Ϣ��ƴ�ղ�ѯ����
	 * @param userinfo
	 * @return
	 */
	public String createSqlCondtion(UserInfo userinfo) {
		String condition = "";
		// ���ƶ�
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE regionid IN ('" + userinfo.getRegionID()
					+ "') AND state IS NULL";
		}
		// �д�ά
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE state IS NULL  and contractorid='"
					+ userinfo.getDeptID() + "' ";
		}
		// ʡ�ƶ�
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE state IS NULL";
		}
		// ʡ��ά
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userinfo.getDeptID() + "')";
		}
		condition += " and depttype='2' ";
		return condition;
	}

	/**
	 * ��������ѯ
	 * 
	 * @param name
	 *            ��ѯ�ֶ���
	 * @param value
	 * @param tablename
	 *            ����
	 * @param condition
	 *            ����
	 * @return
	 */
	public List getSelectForTag(String name, String value, String tablename,
			String condition) {
		boolean flag = false;
		if (name.equals("p.patrolname"))
			flag = true;

		String sql = "select " + name + " ," + value + " from " + tablename
				+ " " + condition;
		QueryUtil query = null;
		DynaBean dynaBean = null;
		ArrayList lableList = new ArrayList();
		logger.info("sql --> " + sql);
		try {
			query = new QueryUtil();
			Iterator it = query.queryBeans(sql).iterator();
			name = (String) ((name.indexOf(".") != -1) ? (name.subSequence(name
					.indexOf(".") + 1, name.length())) : name);
			value = (String) ((value.indexOf(".") != -1) ? (value.subSequence(
					value.indexOf(".") + 1, value.length())) : value);

			while (it.hasNext()) {
				dynaBean = (BasicDynaBean) it.next();
				if (flag)
					lableList.add(new LabelValueBean((String) (dynaBean
							.get(value))
							+ ", " + (String) (dynaBean.get(name)),
							(String) (dynaBean.get(value))));
				else
					lableList.add(new LabelValueBean((String) (dynaBean
							.get(name)), (String) (dynaBean.get(value))));
				// lableList.add(dynaBean);
			}
			// resultVct.add(lableList);
			// System.out.println( lableList );
			return lableList;
		} catch (Exception ex) {
			logger.error("��ѯ" + tablename + "����" + ex.getMessage());
			return null;
		}

	}

}
