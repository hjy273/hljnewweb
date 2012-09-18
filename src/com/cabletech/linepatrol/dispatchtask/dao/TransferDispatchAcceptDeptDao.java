package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.TransferDispatchAcceptDept;

@Repository
public class TransferDispatchAcceptDeptDao extends
		HibernateDao<TransferDispatchAcceptDept, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public String[] saveTransferDispatchAcceptDept(String signInId,
			String[] acceptDeptList, String[] acceptUserList) {
		String[] seqIds = null;
		TransferDispatchAcceptDept dispatchAcceptDept;
		if (acceptDeptList != null) {
			seqIds = new String[acceptDeptList.length];
		}
		for (int i = 0; acceptDeptList != null && i < acceptDeptList.length; i++) {
			dispatchAcceptDept = new TransferDispatchAcceptDept();
			dispatchAcceptDept.setDeptid(acceptDeptList[i]);
			dispatchAcceptDept.setSignInId(signInId);
			dispatchAcceptDept.setUserid(acceptUserList[i].split(";")[1]);
			dispatchAcceptDept.setWorkstate("");
			super.initObject(dispatchAcceptDept);
			super.getSession().save(dispatchAcceptDept);
			seqIds[i] = dispatchAcceptDept.getTid();
			logger.info(seqIds[i] + "=============================");
		}

		return seqIds;
	}

	public void deleteTransferDispatchAcceptDept(String dispatchId) {
		String hql = "delete from TransferDispatchAcceptDept d where sendtaskid=? ";
		super.batchExecute(hql, dispatchId);
	}

	public List queryForDepartList(String condition) {
		String sql = " select * from ( ";
		sql += " select distinct acceptdept.sign_in_task_id,acceptdept.deptid, ";
		sql += " c.contractorname as departname ";
		sql += " from lp_sendtask_transferdept acceptdept,contractorinfo c ";
		sql += " where acceptdept.deptid=c.contractorid ";
		sql += condition;
		sql += " union ";
		sql += " select distinct acceptdept.sign_in_task_id,acceptdept.deptid, ";
		sql += " d.deptname as departname ";
		sql += " from lp_sendtask_transferdept acceptdept,deptinfo d ";
		sql += " where acceptdept.deptid=d.deptid ";
		sql += condition;
		sql += " ) where 1=1 ";
		sql += " order by deptid,id desc ";
		logger.info("查询转发派单部门sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryForUserList(String condition) {
		String sql = " select * from ( ";
		sql += " select acceptdept.id,acceptdept.sign_in_task_id,acceptdept.deptid, ";
		sql += " acceptdept.userid, ";
		sql += " u.username,c.contractorname as departname ";
		sql += " from lp_sendtask_transferdept acceptdept,userinfo u,contractorinfo c ";
		sql += " where acceptdept.deptid=c.contractorid and acceptdept.userid=u.userid ";
		sql += condition;
		sql += " union ";
		sql += " select acceptdept.id,acceptdept.sign_in_task_id,acceptdept.deptid, ";
		sql += " acceptdept.userid, ";
		sql += " u.username,d.deptname as departname ";
		sql += " from lp_sendtask_transferdept acceptdept,userinfo u,deptinfo d ";
		sql += " where acceptdept.deptid=d.deptid and acceptdept.userid=u.userid ";
		sql += condition;
		sql += " ) where 1=1 ";
		sql += " order by deptid,id desc ";
		logger.info("查询转发派单用户sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}
}
