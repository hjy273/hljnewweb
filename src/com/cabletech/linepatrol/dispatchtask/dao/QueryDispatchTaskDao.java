package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

@Repository
public class QueryDispatchTaskDao extends HibernateDao<DispatchTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * <br>
	 * 功能:获得指定用户的电话号码 <br>
	 * 参数:用户编号 <br>
	 * 返回:指定用户的电话号码
	 * */
	public String getSendPhone(String userid) {
		String sql = "select phone from userinfo where userid='" + userid + "'";
		// System.out.println( "SQL:" + sql );
		List list = super.getJdbcTemplate().queryForList(sql, String.class);
		if (list != null && !list.isEmpty()) {
			return (String) list.get(0);
		} else {
			return "";
		}
	}

	public String getDepartTableSql() {
		String sql = " ( ";
		sql += " select deptid as departid,deptname as departname,regionid ";
		sql += " from deptinfo ";
		sql += " union ";
		sql += " select contractorid as departid,contractorname as departname,regionid ";
		sql += " from contractorinfo ";
		sql += " ) ";
		return sql;
	}

	public String getDepartName(String departId) {
		String sql = " select deptname as departname ";
		sql += " from deptinfo where deptid='" + departId + "' ";
		sql += " union ";
		sql += " select contractorname as departname ";
		sql += " from contractorinfo where contractorid='" + departId + "'";
		logger.info("查询部门名称sql：" + sql);
		List departList = super.getJdbcTemplate().queryForList(sql,
				String.class);
		if (departList != null && !departList.isEmpty()) {
			String departName = (String) departList.get(0);
			return departName;
		}
		return "";
	}

	public String getUserName(String userId) {
		String sql = " select username ";
		sql += " from userinfo where userid='" + userId + "' ";
		logger.info("查询用户名称sql：" + sql);
		List userList = super.getJdbcTemplate().queryForList(sql, String.class);
		if (userList != null && !userList.isEmpty()) {
			String userName = (String) userList.get(0);
			return userName;
		}
		return "";
	}

	/**
	 * <br>
	 * 功能:获得受理部门列表 <br>
	 * 参数:用户对象 <br>
	 * 返回:受理部门编号和名称的List
	 */
	public List getAcceptdept(UserInfo userinfo) {
		// List lDept = null;
		List<Object> values = new ArrayList<Object>();
		String sql = "";
		// guixy modify by 2008-12-02 需求要求代维之间、移动部门间可能派单
		sql = "select * from "
				+ "(select d.DEPTID,d.DEPTNAME from deptinfo d where d.state is null ";
		// values.add(userinfo.getRegionID());
		if ("1".equals(userinfo.getDeptype())) {
			sql += " and d.regionid='" + userinfo.getRegionid() + "' ";
		} else {
			sql += " and d.regionid='" + userinfo.getRegionid() + "' ";
		}
		sql += " ) union ";
		sql += "(select c.CONTRACTORID deptid,c.CONTRACTORNAME deptname from contractorinfo c where c.state is null ";
		// values.add(userinfo.getRegionID());
		if ("1".equals(userinfo.getDeptype())) {
			sql += " and c.regionid='" + userinfo.getRegionid() + "' ";
		} else {
			sql += " and c.contractorid='" + userinfo.getDeptID() + "' ";
		}
		sql += ")";
		logger.info("查询受理部门信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, values.toArray());

	}

	/**
	 * <br>
	 * 功能:获得受理人列表 <br>
	 * 参数:用户对象 <br>
	 * 返回:受理人编号和名称的List
	 */
	public List getAcceptUser(UserInfo userinfo) {
		List<Object> values = new ArrayList<Object>();
		// List luser = null;
		String sql = "";
		// guixy modify by 2008-12-02 需求要求代维之间、移动部门间可能派单
		sql = "select u.USERID,u.USERNAME,u.deptid from userinfo u where u.state is null and (u.deptype='1' or u.deptype='2') ";
		// values.add(userinfo.getRegionID());
		if ("1".equals(userinfo.getDeptype())) {
			sql += " and u.regionid='" + userinfo.getRegionid() + "' ";
		} else {
			sql += " and u.deptid='" + userinfo.getDeptID() + "' ";
		}
		logger.info("查询受理用户信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql, values.toArray());
	}

	public List queryApproverList(String condition) {
		String sql = " select * from ( ";
		sql += " select d.departid as deptid,d.departname as departname,u.username as username ";
		sql += " from LP_REPLY_APPROVER approver,userinfo u,"
				+ getDepartTableSql() + " d ";
		sql += " where approver.approver_id=u.userid and u.deptid=d.departid ";
		sql += condition;
		sql += " ) order by deptid ";
		logger.info("查询审核人信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryDispatchTaskTypeList(String condition) {
		String sql = " select code,lable,sort from dictionary_formitem  ";
		sql += " where (assortment_id='dispatch_task' or assortment_id='dispatch_task_con') ";
		sql += condition;
		sql += " order by code ";
		logger.info("查询所有派单类型信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据查询条件进行统计查询
	 * 
	 * @param condition
	 * @return
	 */
	public List queryTotalList(String condition) {
		String sql = " select * from (";
		sql += " select distinct d.serialnumber,d.sendtopic,d.sendtype,to_char(d.processterm,'yyyy-mm-dd hh24:mi:ss') as processterm, ";
		// sql += " r.replytime,c.validatetime, ";
		sql += " d.sendtime,d.id,dic.lable as sendtypelabel,senddept.departname,u.username ";
		// sql +=
		// " decode(sign(d.processterm-r.replytime),'-1','-','+') as is_reply_out_time, ";
		// sql += " decode(sign(c.validatetime-r.replytime-";
		// sql += DispatchTaskConstant.CHECK_OUT_TIME_STD;
		// sql += "),'1','-','+') as is_check_out_time, ";
		// sql +=
		// " round(abs(d.processterm-r.replytime)*24,3) as reply_time_length, ";
		// sql += " round(abs(c.validatetime-r.replytime-";
		// sql += DispatchTaskConstant.CHECK_OUT_TIME_STD;
		// sql += ")*24,3) as check_time_length ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		// sql += " lp_sendtaskreply r,lp_sendtask_check c, ";
		sql += " dictionary_formitem dic,userinfo u, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di ";
		sql += " where d.id=acceptdept.sendtaskid ";
		sql += " and d.sendtype=dic.code ";
		sql += " and (dic.assortment_id='dispatch_task' or dic.assortment_id='dispatch_task_con') ";
		sql += " and senddept.departid=d.senddeptid and u.userid=d.senduserid ";
		// sql += " and r.id=c.replyid and c.validateresult='"
		// + DispatchTaskConstant.PASSED +
		// "' and r.replyuserid=u.userid and acceptdept.id=r.sendaccid ";
		sql += " and di.departid=acceptdept.deptid ";
		sql += condition;
		sql += " ) order by sendtime desc,id desc ";
		logger.info("根据查询条件进行统计查询SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户处理的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryDispatchTaskList(String condition, String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid,u.username, ";
		} else {
			sql += " select di.departid,di.departname, ";
		}
		sql += " to_char(count(distinct d.id)) as sum_num, ";
		sql += " '0' as wait_sign_in_num,'0' as wait_reply_num,'0' as wait_check_num, ";
		sql += " '0' as refuse_num,'0' as transfer_num,'0' as complete_num, ";
		sql += " '0' as reply_out_time_num,'0' as check_out_time_num, ";
		sql += " '0' as reply_in_time_ratio,'0' as check_in_time_ratio ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,userinfo u, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di ";
		sql += " where d.id=acceptdept.sendtaskid ";
		sql += " and acceptdept.deptid=di.departid";
		sql += " and acceptdept.userid=u.userid ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid,u.username ";
		} else {
			sql += " group by di.departid,di.departname ";
		}
		logger.info("根据条件查询所有用户处理的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有等待用户处理的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryWaitHandleDispatchTaskList(String condition,
			String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(sum(decode(task.name_,'sign_in_task',1,'tranfer_sign_in_task',1,0))) as wait_sign_in_num, ";
		sql += " to_char(sum(decode(task.name_,'reply_task',1,0))) as wait_reply_num, ";
		sql += " to_char(sum(decode(task.name_,'check_task',1,0))) as wait_check_num ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,jbpm4_task task,userinfo u, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di ";
		sql += " where d.id=acceptdept.sendtaskid and 'dispatchtask.'||acceptdept.id=task.execution_id_ ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and acceptdept.deptid=di.departid";
		sql += " and acceptdept.userid=u.userid ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有等待用户处理的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户拒签的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryRefuseDispatchTaskList(String condition, String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(count(distinct d.id)) as refuse_num ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,lp_sendtaskendorse s, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=s.sendaccid ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and s.deptid=di.departid";
		sql += " and s.userid=u.userid ";
		sql += " and s.result='" + DispatchTaskConstant.REFUSE_ACTION + "' ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有用户拒签的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户转派的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryTransferDispatchTaskList(String condition, String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(count(distinct d.id)) as transfer_num ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,lp_sendtaskendorse s, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=s.sendaccid ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and s.deptid=di.departid";
		sql += " and s.userid=u.userid ";
		sql += " and s.result='" + DispatchTaskConstant.TRANSFER_ACTION + "' ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有用户转派的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户处理完成的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryCheckDispatchTaskList(String condition, String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(count(distinct d.id)) as complete_num ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,lp_sendtaskendorse s, ";
		sql += " lp_sendtask_check c, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=c.sendaccid ";
		sql += " and acceptdept.id=s.sendaccid and s.result='"
				+ DispatchTaskConstant.SIGNIN_ACTION + "' ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and s.deptid=di.departid";
		sql += " and s.userid=u.userid ";
		sql += " and c.validateresult='" + DispatchTaskConstant.PASSED + "' ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有用户处理完成的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户反馈超时的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryReplyOutTimeDispatchTaskList(String condition,
			String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(count(distinct d.id)) as reply_out_time_num";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,lp_sendtaskendorse s, ";
		sql += " lp_sendtaskreply r, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=r.sendaccid ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and acceptdept.id=s.sendaccid and s.result='"
				+ DispatchTaskConstant.SIGNIN_ACTION + "' ";
		sql += " and decode(sign(d.processterm-r.replytime),'-1','-','+')='-' ";
		sql += " and s.deptid=di.departid";
		sql += " and s.userid=u.userid ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有用户反馈超时的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * 根据条件查询所有用户验证超时的派单列表信息
	 * 
	 * @param condition
	 * @param queryType
	 * @return
	 */
	public List queryCheckOutTimeDispatchTaskList(String condition,
			String queryType) {
		String sql = "";
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " select u.userid, ";
		} else {
			sql += " select di.departid, ";
		}
		sql += " to_char(count(distinct d.id)) as check_out_time_num ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept,lp_sendtaskendorse s, ";
		sql += " lp_sendtaskreply r,lp_sendtask_check c,userinfo u, ";
		sql += " " + getDepartTableSql() + " senddept, ";
		sql += " " + getDepartTableSql() + " di ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=r.sendaccid ";
		sql += " and senddept.departid=d.senddeptid ";
		sql += " and acceptdept.id=s.sendaccid and s.result='"
				+ DispatchTaskConstant.SIGNIN_ACTION + "' ";
		sql += " and r.id=c.replyid ";
		sql += " and decode(sign(c.validatetime-r.replytime-"
				+ DispatchTaskConstant.CHECK_OUT_TIME_STD
				+ "),'1','-','+')='-' ";
		sql += " and s.deptid=di.departid";
		sql += " and s.userid=u.userid ";
		sql += condition;
		if (DispatchTaskConstant.USER_SUMMIZE_CONDITION.equals(queryType)) {
			sql += " group by u.userid ";
		} else {
			sql += " group by di.departid ";
		}
		logger.info("根据条件查询所有用户验证超时的派单列表信息SQL:" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

}
