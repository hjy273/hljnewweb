package com.cabletech.linepatrol.hiddanger.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerPlan;

@Repository
public class HiddangerPlanDao extends HibernateDao<HiddangerPlan, String> {
	public boolean getExistHiddangerPlan(String hiddangerId, String specialPlanId){
		String hql = "from HiddangerPlan h where h.hiddangerId = ? and h.planId = ?";
		List<HiddangerPlan> list = find(hql, hiddangerId, specialPlanId);
		return list.isEmpty() ? false : true;
	}
}
