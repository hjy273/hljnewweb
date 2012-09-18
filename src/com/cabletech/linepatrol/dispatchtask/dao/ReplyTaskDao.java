package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.ReplyTask;

@Repository
public class ReplyTaskDao extends HibernateDao<ReplyTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public void saveReplyTask(ReplyTask replyTask) {
		super.initObject(replyTask);
		super.getSession().save(replyTask);
	}

	public void updateReplyTask(ReplyTask replyTask) {
		super.initObject(replyTask);
		super.getSession().saveOrUpdate(replyTask);
	}

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select d.id,r.id as replyid,acceptdept.id as subid,u.deptid, ";
		sql += " u.username as replyusername,r.replyresult,'0' as is_reader, ";
		sql += " decode(r.replyresult,'00','未完成','01','部分完成','02','基本完成','03','全部完成','全部完成') as replyresultlabel, ";
		sql += " abs(round(to_number(d.processterm - r.replytime)*24,1)) as processterm_cal, ";
		sql += " decode(sign(round(to_number(d.processterm - r.replytime)*24,1)),'-1','-','+') as is_out_time, ";
		sql += " to_char(r.replytime,'yyyy-mm-dd hh24:mi:ss') as replytime ";
		sql += " from lp_sendtask d,lp_sendtask_acceptdept acceptdept, ";
		sql += " lp_sendtaskreply r,userinfo u ";
		sql += " where d.id=acceptdept.sendtaskid and acceptdept.id=r.sendaccid ";
		sql += " and r.replyuserid=u.userid ";
		sql += condition;
		sql += " order by r.id ";
		logger.info("查询反馈派单信息sql：" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public ReplyTask viewReplyTask(String replyId) {
		// TODO Auto-generated method stub
		ReplyTask replyTask = super.get(replyId);
		super.initObject(replyTask);
		return replyTask;
	}
}
