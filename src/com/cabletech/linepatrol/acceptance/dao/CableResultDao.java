package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.CableResult;

@Repository
public class CableResultDao extends HibernateDao<CableResult, String> {
	public List<CableResult> getCableResults(String cableId){
		String hql = "from CableResult c where c.payCableId in (select pc.id from PayCable pc where pc.cableId = ?) order by c.times asc";
		return this.find(hql, cableId);
	}
	
	public CableResult loadCableResult(String payCableId){
		String hql = "from CableResult c where c.payCableId = ?";
		return this.findUnique(hql, payCableId);
	}
}
