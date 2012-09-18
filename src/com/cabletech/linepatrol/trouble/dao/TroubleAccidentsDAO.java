package com.cabletech.linepatrol.trouble.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleAccidents;

@Repository
public class TroubleAccidentsDAO extends HibernateDao<TroubleAccidents, String> {
	
	/**
	 * ���ݷ�����idɾ����������Ӧ������
	 * @param replyid ������id
	 */ 
	public void deleteByReplyId(String replyid){
		String sql=" delete from lp_trouble_accidents acc where acc.trouble_reply_id='"+replyid+"'";
		this.getJdbcTemplate().execute(sql);		
	}
	
}
