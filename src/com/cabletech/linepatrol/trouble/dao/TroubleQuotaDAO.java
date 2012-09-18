package com.cabletech.linepatrol.trouble.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;

@Repository
public class TroubleQuotaDAO extends HibernateDao<TroubleNormGuide, String> {
	
	/**
	 * ��ѯ����ָ�����
	 * @return
	 */
	public List<TroubleNormGuide> getQuotas(){
		String hql="from TroubleNormGuide";
		return find(hql);
	}
	
	/**
	 * ��ѯ����ָ�����
	 * @return
	 */
	public TroubleNormGuide getQuotaByType(String type){
		String hql="from TroubleNormGuide t where t.guideType='"+type+"'";
		return (TroubleNormGuide) getSession().createQuery(hql).uniqueResult();
	}
}
