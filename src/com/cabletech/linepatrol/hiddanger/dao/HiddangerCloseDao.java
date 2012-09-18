package com.cabletech.linepatrol.hiddanger.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerClose;

@Repository
public class HiddangerCloseDao extends HibernateDao<HiddangerClose, String>{
	public HiddangerClose getCloseFromHiddangerId(String id){
		return findUniqueByProperty("hiddangerId", id);
	}
}
