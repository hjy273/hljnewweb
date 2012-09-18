package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.RefuseConfirmTask;

@Repository
public class RefuseConfirmTaskDao extends
		HibernateDao<RefuseConfirmTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public void saveRefuseConfirmTask(RefuseConfirmTask refuseConfirmTask) {
		super.initObject(refuseConfirmTask);
		super.getSession().save(refuseConfirmTask);
	}

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select d.id,s.id as signinid,r.id as confirmid,u.username as confirmusername, ";
		sql += " to_char(r.confirmtime,'yyyy-mm-dd hh24:mi:ss') as confirmtime, ";
		sql += " r.confirmresult,r.confirmremark, ";
		sql += " decode(r.confirmresult,'0','拒签通过','1','拒签不通过','拒签通过') as resultlabel ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		sql += " lp_sendtaskendorse s,lp_sendtask_refuse r,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=s.sendaccid ";
		sql += " and r.confirmuserid=u.userid and s.id=r.signinid ";
		sql += condition;
		sql += " order by r.id ";
		logger.info("查询拒签派单确认信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public RefuseConfirmTask viewRefuseConfirmTask(String refuseConfirmId) {
		// TODO Auto-generated method stub
		RefuseConfirmTask refuseConfirmTask = super.get(refuseConfirmId);
		super.initObject(refuseConfirmTask);
		return refuseConfirmTask;
	}

	public RefuseConfirmTask viewRefuseConfirmTaskBySignInId(String signInId) {
		// TODO Auto-generated method stub
		String hql = " from RefuseConfirmTask ";
		hql += " where signInId= '" + signInId + "' ";
		List list = super.find(hql);
		if (list != null && !list.isEmpty()) {
			RefuseConfirmTask refuseConfirmTask = (RefuseConfirmTask) list
					.get(0);
			super.initObject(refuseConfirmTask);
			return refuseConfirmTask;
		}
		return null;
	}
}
