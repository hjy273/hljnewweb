package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.PipeResult;

@Repository
public class PipeResultDao extends HibernateDao<PipeResult, String> {
	public List<PipeResult> getPipeResults(String payPipeId){
		String hql = "from PipeResult p where p.payPipeId in (select pp.id from PayPipe pp where pp.pipeId= ?) order by p.times asc";
		return this.find(hql, payPipeId);
	}
	
	public PipeResult loadPipeResult(String payPipeId){
		String hql = "from PipeResult p where p.payPipeId = ?";
		return this.findUnique(hql, payPipeId);
	}
}
