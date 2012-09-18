package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.SignInTask;

@Repository
public class SignInTaskDao extends HibernateDao<SignInTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public void saveSignInTask(SignInTask signInTask) {
		super.initObject(signInTask);
		super.getSession().save(signInTask);
	}

	public List queryForList(String condition, String orderString) {
		// TODO Auto-generated method stub
		String sql = " select d.id,s.id as signinid,u.username as signinusername, ";
		sql += " to_char(s.time,'yyyy-mm-dd hh24:mi:ss') as signintime, ";
		sql += " s.result,s.remark,s.transfer_user_id,s.sendaccid as subid, ";
		sql += " decode(s.result,'00','签收','01','拒签','02','转派','签收') as resultlabel ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		sql += " lp_sendtaskendorse s,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=s.sendaccid ";
		sql += " and s.userid=u.userid ";
		sql += condition;
		sql += orderString;
		logger.info("查询签收派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public List queryForList(String condition) {
		return queryForList(condition, " order by s.id ");
	}

	public SignInTask viewSignInTask(String signInId) {
		// TODO Auto-generated method stub
		SignInTask signInTask = super.get(signInId);
		super.initObject(signInTask);
		return signInTask;
	}

	public String getTransferUserId(String dispatchId, String deptId) {
		// TODO Auto-generated method stub
		String condition = " and d.id='" + dispatchId + "' ";
		condition += " and s.deptid='" + deptId + "' ";
		condition += " and (s.transfer_user_id is not null) ";
		// condition += " order by s.id desc ";
		List list = queryForList(condition);
		if (list != null && !list.isEmpty()) {
			DynaBean bean = (DynaBean) list.get(list.size() - 1);
			String transferUserId = (String) bean.get("transfer_user_id");
			return transferUserId;
		}
		return "";
	}
}
