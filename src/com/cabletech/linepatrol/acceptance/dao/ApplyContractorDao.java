package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.ApplyContractor;

@Repository
public class ApplyContractorDao extends HibernateDao<ApplyContractor, String> {
	public List<ApplyContractor> getContractors(String applyId){
		String hql = "from ApplyContractor a where a.applyId = ?";
		return find(hql, applyId);
	}
}
