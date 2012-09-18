package com.cabletech.linepatrol.overhaul.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.model.OverHaulCut;

@Repository
public class OverHaulCutDao extends HibernateDao<OverHaulCut, String> {
	public void deleteCut(String applyId){
		String hql = "delete from OverHaulCut o where o.overHaulApply = (select a from OverHaulApply a where a.id = ?)";
		this.batchExecute(hql, applyId);
	}
}
