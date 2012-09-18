package com.cabletech.linepatrol.trouble.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleProcesser;

@Repository
public class TroubleProcesserDAO extends HibernateDao<TroubleProcesser, String> {
	
	/**
	 * �����ɵ�idɾ��������Ա��Ϣ
	 * @param processer
	 */
	public void deleteProcesser(String replyid){
		String sql=" delete LP_TROUBLE_PROCESSER tp where tp.trouble_reply_id='"+replyid+"'";
		this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * ���洦����Ա��Ϣ
	 * @param processer
	 */
	public void saveProcesser(List<TroubleProcesser> processer){
		if(processer!=null && processer.size()>0){
			for(int i = 0;i<processer.size();i++){
				TroubleProcesser pro = processer.get(i);
				save(pro);
			}
		}
	}
	
	/**
	 * ���ݷ�������id��ѯ������Ա��Ϣ
	 * @param replyid
	 * @return
	 */
	public List<TroubleProcesser> getProcesserByReplyId(String replyid){
		String hql=" from TroubleProcesser tp where tp.troubleReplyId='"+replyid+"'";
		List<TroubleProcesser> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	
}
