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
	 * 功能:获得当前登陆用户信息 <br>
	 * 参数:request <br>
	 * 返回:用户对象
	 */
	private UserInfo getUserInfo(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		return userinfo;
	}

	/**
	 * <br>
	 * 功能:获得指定条件的执行计划编号 <br>
	 * 参数:查询的条件:执行人,执行单位,开始时间,结束时间,巡检线段,巡检线路 <br>
	 * 返回:String,用逗号分割.
	 */
	private String getPlanIdArr(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		// 判断参数的合法性:开始日期和结束日期不能为空
		if (begindate.equals("") || begindate == null || enddate.equals("")
				|| enddate == null) {
			logger.warn("获得指定条件的执行计划编号方法执行中参数不合法");
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
			// System.out.println( "获得指定条件的执行计划编号:" + planIdArr );
			return planIdArr;
		} catch (Exception e) {
			logger.warn("获得指定条件的执行计划编号异常:" + e.getMessage());
			return "( 'aa' )";
		}
	}

	/**
	 * <br>
	 * 功能:构造 <br>
	 * 参数:查询的条件:执行人,执行单位,开始时间,结束时间,巡检线段,巡检线路 <br>
	 * 返回:查询语句,查询结果是点编号和执行的次数
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
		// System.out.println( "构造查询指定条件的计划执行点信息语句SQL:" + sql );
		return sql;
	}

	/**
	 * <br>
	 * 功能:构造语句, <br>
	 * 参数:查询的条件:执行人,执行单位,开始时间,结束时间,巡检线段,巡检线路 <br>
	 * 返回:查询语句,查询结果是点编号和计划执行的次数
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
	 * 功能:构造语句, <br>
	 * 参数:查询的条件:执行人,执行单位,开始时间,结束时间,巡检线段,巡检线路 <br>
	 * 返回:查询语句,查询结果是点编号和计划执行的次数
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
	 * 功能:获得明细, <br>
	 * 参数:查询的条件:执行人,执行单位,开始时间,结束时间,巡检线段,巡检线路 <br>
	 * 返回:查询语句,
	 */
	public List getLossDetail(String executorid, String contractorid,
			String begindate, String enddate, String sublineid, String lineid) {
		List lLoss = null;
		String sql = "select p.objectid,p.planexe,p.POINTNAME,p.ADDRESSINFO,decode(p.ISFOCUS,0,'否',1,'是','否') ISFOCUS,p.SUBLINENAME,p.LINENAME,p.PATROLNAME,p.planexe-NVL(e.realexec, 0) losstime"
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
	 * 功能:获得指定条件的巡检信息 <br>
	 * 参数:查询条件对象 <br>
	 * 返回:查询成功返回 List 出错返回null
	 */
	public List queryPatrolDetail(QueryCondition condition) {
		// 初始化数据
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
			logger.warn("获得指定条件的巡检信息异常" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * 功能:根据当前登陆用户类型及级别获得管辖范围内的所有代维单位所有信息 <br>
	 * 参数:request <br>
	 * 返回:List
	 */
	public List getContracorInfo(HttpServletRequest request) {
		List lCon = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// 市移动用户
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo  c where c.state is null and c.regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// 代维单位用户
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null and c.contractorid ='"
					+ userinfo.getDeptID() + "'";
		}
		// 省移动用户
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null";
		}
		// 省代维用户
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select c.CONTRACTORID,c.CONTRACTORNAME,c.LINKMANINFO,c.PACTBEGINDATE,c.PACTTERM,c.PARENTCONTRACTORID,c.PRINCIPALINFO,c.REGIONID "
					+ " from contractorinfo c where c.state is null and c.PARENTCONTRACTORID = '"
					+ userinfo.getDeptID() + "'";
		}
		// System.out.println( "根据当前登陆用户类型及级别获得管辖范围内的所有代维单位所有信息SQL:" + sql );

		try {
			QueryUtil qu = new QueryUtil();
			lCon = qu.queryBeans(sql);
			return lCon;
		} catch (Exception e) {
			logger.warn("根据当前登陆用户类型及级别获得管辖范围内的所有代维单位所有信息异常:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * 功能:根据当前登陆用户类型及级别获得管辖范围内的所有线的所有信息 <br>
	 * 参数:request <br>
	 * 返回:List
	 */
	public List getLineInfo(HttpServletRequest request) { // ////
		List lSubline = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// 市移动用户
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l "
					+ " where l.regionid='" + userinfo.getRegionID() + "'";
		}
		// 代维单位用户
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = " select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l "
					+ " where l.LINEID in "
					+ " ( select s.LINEID from sublineinfo s "
					+ "   where s.PATROLID in "
					+ "         ( select p.PATROLID from patrolmaninfo p where  p.PARENTID='"
					+ userinfo.getDeptID() + "'))";

		}
		// 省移动用户
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = " select l.LINEID,l.LINENAME,l.LINETYPE,l.REGIONID,l.RULEDEPTID from lineinfo l ";
		}
		// 省代维用户
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
			logger.warn("根据当前登陆用户类型及级别获得管辖范围内的所有线的所有信息异常:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * 功能:根据当前登陆用户类型及级别获得管辖范围内的所有线段的所有信息 <br>
	 * 参数:request <br>
	 * 返回:List
	 */
	public List getSublineInfo(HttpServletRequest request, String type) {
		List lSubline = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// 市移动用户
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// 代维单位用户
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.PATROLID in ( select patrolid from patrolmaninfo where parentid = '"
					+ userinfo.getDeptID() + "')";
		}
		// 省移动用户
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s";
		}
		// 省代维用户
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select s.CHECKPOINTS,s.LINEID,s.LINETYPE,s.PATROLID,s.REGIONID,s.RULEDEPTID,s.SUBLINEID,s.SUBLINENAME  from sublineinfo s where s.PATROLID in ( select patrolid from patrolmaninfo where parentid in ( select contractorid from contractorinfo where parentcontractorid='"
					+ userinfo.getDeptID() + "'))";
		}
		logger.info("根据当前登陆用户类型及级别获得管辖范围内的所有线段的所有信息SQL:" + sql);
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
			logger.warn("根据当前登陆用户类型及级别获得管辖范围内的所有线段的所有信息异常:" + e.getMessage());
			return null;
		}
	}

	/**
	 * <br>
	 * 功能:根据当前登陆用户类型及级别获得管辖范围内的所有巡检维护组的所有信息 <br>
	 * 参数:request <br>
	 * 返回:List
	 */
	public List getPatrolManInfo(HttpServletRequest request) {
		List lPatrolMan = null;
		UserInfo userinfo = this.getUserInfo(request);
		String sql = "";
		// 市移动用户
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and regionid='"
					+ userinfo.getRegionID() + "'";
		}
		// 代维单位用户
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and p.PARENTID ='"
					+ userinfo.getDeptID() + "'";
		}
		// 省移动用户
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p ";
		}
		// 省代维用户
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			sql = "select p.patrolid,p.PATROLNAME,p.PARENTID,p.REGIONID  from patrolmaninfo p where p.state is null and  p.PARENTID in (SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userinfo.getDeptID() + "')";
		}
		// System.out.println( "根据当前登陆用户类型及级别获得管辖范围内的所有巡检维护组的所有信息SQL:" + sql );
		sql += " order by p.PARENTID,p.PATROLNAME";
		try {
			QueryUtil qu = new QueryUtil();
			lPatrolMan = qu.queryBeans(sql);
			return lPatrolMan;
		} catch (Exception e) {
			logger
					.warn("根据当前登陆用户类型及级别获得管辖范围内的所有巡检维护组的所有信息异常:"
							+ e.getMessage());
			return null;
		}
	}

	/**
	 * 根据用户查询代维信息，拼凑查询条件
	 * @param userinfo
	 * @return
	 */
	public String createSqlCondtion(UserInfo userinfo) {
		String condition = "";
		// 市移动
		if (userinfo.getDeptype().equals("1")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE regionid IN ('" + userinfo.getRegionID()
					+ "') AND state IS NULL";
		}
		// 市代维
		if (userinfo.getDeptype().equals("2")
				&& !userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE state IS NULL  and contractorid='"
					+ userinfo.getDeptID() + "' ";
		}
		// 省移动
		if (userinfo.getDeptype().equals("1")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE state IS NULL";
		}
		// 省代维
		if (userinfo.getDeptype().equals("2")
				&& userinfo.getRegionID().substring(2, 6).equals("0000")) {
			condition = " WHERE contractorid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
					+ userinfo.getDeptID() + "')";
		}
		condition += " and depttype='2' ";
		return condition;
	}

	/**
	 * 根条件查询
	 * 
	 * @param name
	 *            查询字段名
	 * @param value
	 * @param tablename
	 *            表名
	 * @param condition
	 *            条件
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
			logger.error("查询" + tablename + "出错：" + ex.getMessage());
			return null;
		}

	}

}
