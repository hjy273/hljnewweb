package com.cabletech.linepatrol.trouble.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleAccidents;

@Repository
public class TroubleAccidentsDAO extends HibernateDao<TroubleAccidents, String> {
	
	/**
	 * 根据反馈单id删除反馈单对应的隐患
	 * @param replyid 反馈单id
	 */ 
	public void deleteByReplyId(String replyid){
		String sql=" delete from lp_trouble_accidents acc where acc.trouble_reply_id='"+replyid+"'";
		this.getJdbcTemplate().execute(sql);		
	}
	
}
