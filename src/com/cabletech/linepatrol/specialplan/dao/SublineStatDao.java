package com.cabletech.linepatrol.specialplan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.specialplan.module.SublineStat;

@Repository
public class SublineStatDao extends HibernateDao<SublineStat, String>{
	
	public List<Map> getPatrolDetail(String id){
		String sql = "select point_id,subline_id,sim_id,terminal_id,patrol_time from LP_SPEC_PATROL_DETAIL where SPEC_PLAN_ID = '" + id + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map> getLeakDetail(String id){
		String sql = "select point_id,subline_id,plan_patrol_times,fact_patrol_times,leak_patrol_times from LP_SPEC_LEAK_DETAIL where SPEC_PLAN_ID = '" + id + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
