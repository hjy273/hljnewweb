package com.cabletech.linepatrol.hiddanger.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerTreat;

@Repository
public class HiddangerTreatDao extends HibernateDao<HiddangerTreat, String> {
	public HiddangerTreat getTreatFromHiddangerId(String id){
		return findUniqueByProperty("hiddangerId", id);
	}
}
