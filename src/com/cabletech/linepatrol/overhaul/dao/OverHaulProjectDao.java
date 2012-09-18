package com.cabletech.linepatrol.overhaul.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.model.OverHaulProject;

@Repository
public class OverHaulProjectDao extends HibernateDao<OverHaulProject, String>{
	public void deleteProject(String applyId){
		String hql = "delete from OverHaulProject o where o.overHaulApply = (select a from OverHaulApply a where a.id = ?)";
		this.batchExecute(hql, applyId);
	}
}
