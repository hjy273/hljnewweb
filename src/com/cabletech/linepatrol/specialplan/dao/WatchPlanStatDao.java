package com.cabletech.linepatrol.specialplan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.specialplan.module.WatchPlanStat;

@Repository
public class WatchPlanStatDao extends HibernateDao<WatchPlanStat, String>{
	public WatchPlanStat getWatchPlanStatFromPlanId(String planId){
		return this.findUniqueByProperty("specPlanId", planId);
	}
	
	public List<WatchPlanStat> getPlansStat(String planIds){
		String hql = "from WatchPlanStat wps where wps.specPlanId in ('" + planIds.replace(",", "','") + "')";
		return find(hql);
	}
}
