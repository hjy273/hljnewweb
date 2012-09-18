package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.DispatchAcceptDept;

@Repository
public class DispatchAcceptDeptDao extends
		HibernateDao<DispatchAcceptDept, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public String[] saveDispatchAcceptDept(String dispatchId,
			String[] acceptDeptList, String[] acceptUserList, String signInId) {
		String[] seqIds = null;
		DispatchAcceptDept dispatchAcceptDept;
		if (acceptUserList != null) {
			seqIds = new String[acceptUserList.length];
		}
		for (int i = 0; acceptUserList != null && i < acceptUserList.length; i++) {
			dispatchAcceptDept = new DispatchAcceptDept();
			dispatchAcceptDept.setDeptid(acceptUserList[i].split(";")[0]);
			dispatchAcceptDept.setSendtaskid(dispatchId);
			dispatchAcceptDept.setUserid(acceptUserList[i].split(";")[1]);
			dispatchAcceptDept.setWorkstate("");
			dispatchAcceptDept.setSignInId(signInId);
			super.initObject(dispatchAcceptDept);
			super.getSession().save(dispatchAcceptDept);
			seqIds[i] = dispatchAcceptDept.getTid();
			logger.info(seqIds[i] + "=============================");
		}

		return seqIds;
	}

	public void deleteDispatchAcceptDept(String dispatchId) {
		String hql = "delete from DispatchAcceptDept d where sendtaskid=? ";
		super.batchExecute(hql, dispatchId);
	}

	public List queryForDepartList(String condition) {
		String sql = " select * from ( ";
		sql += " select distinct acceptdept.sendtaskid,acceptdept.deptid, ";
		sql += " c.contractorname as departname ";
		sql += " from lp_sendtask_acceptdept acceptdept,contractorinfo c ";
		sql += " where acceptdept.deptid=c.contractorid ";
		sql += condition;
		sql += " union ";
		sql += " select distinct acceptdept.sendtaskid,acceptdept.deptid, ";
		sql += " d.deptname as departname ";
		sql += " from lp_sendtask_acceptdept acceptdept,deptinfo d ";
		sql += " where acceptdept.deptid=d.deptid ";
		sql += condition;
		sql += " ) where 1=1 ";
		sql += " order by deptid ";
		logger.info("查询派单受理部门信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryForUserList(String condition) {
		String sql = " select * from ( ";
		sql += " select acceptdept.id,acceptdept.sendtaskid,acceptdept.deptid, ";
		sql += " acceptdept.userid,acceptdept.workstate,task.name_ as task_state, ";
		sql += " u.username,c.contractorname as departname ";
		sql += " from lp_sendtask_acceptdept acceptdept,jbpm4_task task,userinfo u,contractorinfo c ";
		sql += " where acceptdept.deptid=c.contractorid and acceptdept.userid=u.userid ";
		sql += " and 'dispatchtask.'||acceptdept.id=task.execution_id_(+) ";
		sql += condition;
		sql += " union ";
		sql += " select acceptdept.id,acceptdept.sendtaskid,acceptdept.deptid, ";
		sql += " acceptdept.userid,acceptdept.workstate,task.name_ as task_state, ";
		sql += " u.username,d.deptname as departname ";
		sql += " from lp_sendtask_acceptdept acceptdept,jbpm4_task task,userinfo u,deptinfo d ";
		sql += " where acceptdept.deptid=d.deptid and acceptdept.userid=u.userid ";
		sql += " and 'dispatchtask.'||acceptdept.id=task.execution_id_(+) ";
		sql += condition;
		sql += " ) where 1=1 ";
		sql += " order by deptid ";
		logger.info("查询派单受理用户信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
