package com.cabletech.linepatrol.hiddanger.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerEndplan;

@Repository
public class HiddangerEndplanDao extends HibernateDao<HiddangerEndplan, String> {
	public HiddangerEndplan getEndplan(String id){
		String hql = "from HiddangerEndplan h where h.planId = ?";
		List<HiddangerEndplan> list = find(hql, id);
		return list.isEmpty() ? null : list.get(0);
	}
}
