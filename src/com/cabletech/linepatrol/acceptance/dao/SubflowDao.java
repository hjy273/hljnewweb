package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.Subflow;

@Repository
public class SubflowDao extends HibernateDao<Subflow, String>{
	public Subflow getSubflow(String applyId, String contractorId){
		String hql = "from Subflow s where s.applyId = ? and s.contractorId = ?";
		return this.findUnique(hql, applyId, contractorId);
	}
	
	public List<Subflow> isOtherSubflowAllCompleted(String subflowId){
		Subflow subflow = this.findUniqueByProperty("id", subflowId);
		String applyId = subflow.getApplyId();
		
		String hql = "from Subflow s where s.applyId = ? and s.processState != 45";
		return find(hql, applyId);
	}
}
