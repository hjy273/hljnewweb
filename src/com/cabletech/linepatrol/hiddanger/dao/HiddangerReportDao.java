package com.cabletech.linepatrol.hiddanger.dao;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerReport;

@Repository
public class HiddangerReportDao extends HibernateDao<HiddangerReport, String> {
	
	public HiddangerReport getReportFromHiddangerId(String id){
		return findUniqueByProperty("hiddangerId", id);
	}
	
}
