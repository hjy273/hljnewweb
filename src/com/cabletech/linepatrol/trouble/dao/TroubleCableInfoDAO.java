package com.cabletech.linepatrol.trouble.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleCableInfo;

@Repository
public class TroubleCableInfoDAO extends HibernateDao<TroubleCableInfo, String> {
	
	/**
	 * ���ݷ��������ɾ�������м̶�
	 * @param replyid
	 */
	public void deleteList(String replyid){
		String sql = "delete from lp_trouble_cable_info cable where cable.trouble_reply_id='"+replyid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
}
