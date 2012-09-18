package com.cabletech.linepatrol.trouble.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleCableInfo;

@Repository
public class TroubleCableInfoDAO extends HibernateDao<TroubleCableInfo, String> {
	
	/**
	 * 根据反馈单编号删除故障中继段
	 * @param replyid
	 */
	public void deleteList(String replyid){
		String sql = "delete from lp_trouble_cable_info cable where cable.trouble_reply_id='"+replyid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
}
