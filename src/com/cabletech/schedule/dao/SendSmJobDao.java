package com.cabletech.schedule.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.cabletech.schedule.module.SendSmJobInfo;
import com.cabletech.ctf.dao.HibernateDao;

@Repository
public class SendSmJobDao extends HibernateDao<SendSmJobInfo, String> {
	Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * ִ�и��ݶ����Key��ѯ��ʱ������Ϣ
	 * 
	 * @param key
	 * @return
	 */
	public List findByKey(String key) {
		String hql = " from SendSmJobInfo where sendObjectId='" + key + "' ";
		List list = super.find(hql);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	/**
	 * ִ�и��ݶ����SchedularName��ѯ��ʱ������Ϣ
	 * 
	 * @param schedularName
	 * @return
	 */
	public List findByName(String schedularName) {
		String hql = " from SendSmJobInfo where schedularName='"
				+ schedularName + "' ";
		List list = super.find(hql);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	/**
	 * ִ�и���������ѯ��ʱ������Ϣ
	 * 
	 * @param condition
	 * @return
	 */
	public List findByCondition(String condition) {
		String sql = " select id,sim_id,send_content,send_type,send_state,schedular_name,create_user_id, ";
		sql += " to_char(first_send_time,'yyyy-mm-dd hh24:mi:ss') as first_send_time, ";
		sql += " to_char(last_send_time,'yyyy-mm-dd hh24:mi:ss') as last_send_time, ";
		sql += " decode(send_type,'1','��ʱ�ظ�����','2','�̶����ڷ���','-1','��ʱ���η���','��ʱ���η���') as send_type_label, ";
		sql += " decode(send_state,'01','����ִ��','02','ִ�����','03','�Ѿ�ȡ��','����ִ��') as send_state_label ";
		sql += " from send_sm_job_info ";
		sql += " where 1=1 ";
		sql += condition;
		sql += " order by id desc ";
		return super.getJdbcTemplate().queryForBeans(sql);
	}

	/**
	 * ִ�и��ݶ�ʱ�������Ƹ��¶�ʱ������Ϣ���еĶ�ʱ�������״̬
	 * 
	 * @param triggerName
	 */
	public void updateSendSmFinishState(String triggerName) {
		String sql = " update send_sm_job_info ";
		sql += " set send_state='" + SendSmJobInfo.SENT_STATE + "' ";
		sql += " where schedular_name='" + triggerName + "' ";
		sql += " and send_state='" + SendSmJobInfo.NOT_SENT_STATE + "' ";
		logger.info("ִ�и��·��Ͷ�ʱ�������SQL��" + sql);
		super.getJdbcTemplate().execute(sql);
	}
}
