package com.cabletech.linepatrol.commons.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.commons.module.Evaluate;

/**
 * ����ҵ����ÿ����Ŀ���������
 * ʹ��˵����
 * ����������Ϣʱ������ҵ��ģ��ʵ�壬ҵ�����ͣ��Լ������ˡ�
 * @author zh
 *
 */
@Repository
public class EvaluateDao extends HibernateDao<Evaluate, String>{
	private static final String trouble="02";
	private static final String cut="03";
	private static final String hiddanger="01";
	private static final String drill="05";
	private static final String maintenance="06";
	private static final String safeguard="07";
	public void savaEvaluate(Evaluate eva){
		eva.setEvaluaterDate(new Date());
		super.save(eva);
	}
	
	public Evaluate getEvaluate(String id, String type){
		String hql = "from Evaluate e where e.entityId = ? and e.entityType = ?";
		return this.findUnique(hql, id, type);
	}
}
