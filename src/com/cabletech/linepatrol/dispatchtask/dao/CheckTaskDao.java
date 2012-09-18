package com.cabletech.linepatrol.dispatchtask.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.dispatchtask.module.CheckTask;
import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;

@Repository
public class CheckTaskDao extends HibernateDao<CheckTask, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public void saveCheckTask(CheckTask checkTask) {
		super.initObject(checkTask);
		super.getSession().save(checkTask);
	}

	public List queryForList(String condition) {
		// TODO Auto-generated method stub
		String sql = " select r.id,c.id as checkid,u.username as checkmanname, ";
		sql += " to_char(c.validatetime,'yyyy-mm-dd hh24:mi:ss') as checktime,c.validateresult,c.validateremark, ";
		sql += " decode(c.validateresult,'0','���ͨ��','1','���δͨ��','2','ת��','���ͨ��') as validateresultlabel";
		sql += " from lp_sendtaskreply r,lp_sendtask_check c,userinfo u ";
		sql += " where r.id=c.replyid and c.validateuserid=u.userid ";
		sql += condition;
		sql += " order by c.id ";
		logger.info("��ѯ��֤�ɵ���Ϣsql��" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public CheckTask viewCheckTask(String checkId) {
		// TODO Auto-generated method stub
		CheckTask signInTask = super.get(checkId);
		super.initObject(signInTask);
		return signInTask;
	}

	/**
	 * ���»ظ�����տ���״̬
	 * @param replyId
	 * @param finishState
	 */
	public void updateExamFlagByReplyIdAndFinishState(String replyId,
			String finishState) {
		String sql = "update lp_sendtask_check set exam_flag='"
				+ DispatchTaskConstant.EXAMED + "' where replyid='" + replyId
				+ "' and validateresult='" + finishState + "'";
		getJdbcTemplate().update(sql);
	}
}
