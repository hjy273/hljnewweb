package com.cabletech.commons.process.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.commons.process.module.ProcessHistory;
import com.cabletech.ctf.dao.HibernateDao;

@Repository
public class ProcessHistoryDao extends HibernateDao<ProcessHistory, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public List queryForList(String condition, String orderString) {
		// TODO Auto-generated method stub
		String sql = " select history.next_operate_user_id,history.operate_user_id, ";
		sql += " to_char(history.handled_time,'yyyy-mm-dd hh24:mi:ss') as handled_time_dis, ";
		sql += " history.execUtion_id,history.handled_task_id,history.handled_task_name, ";
		sql += " history.process_action,u.username as operate_user_name, ";
		sql += " '' as next_operate_user_name ";
		sql += " from process_history_info history,userinfo u,jbpm4_hist_task history_task ";
		sql += " where history.operate_user_id=u.userid ";
		sql += " and history.handled_task_id=history_task.dbid_(+) ";
		sql += condition;
		sql += orderString;
		logger.info("≤È—Ø–≈œ¢sql£∫" + sql);
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	public ProcessHistory viewProcessHistory(String historyId) {
		// TODO Auto-generated method stub
		ProcessHistory history = super.get(historyId);
		super.initObject(history);
		return history;
	}

}
