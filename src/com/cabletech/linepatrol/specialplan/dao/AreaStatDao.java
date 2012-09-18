package com.cabletech.linepatrol.specialplan.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.specialplan.module.AreaStat;

@Repository
public class AreaStatDao extends HibernateDao<AreaStat, String>{
	
	public List<Map> getValidWatch(String id){
		String sql = "select SP.PATROL_GROUP_ID as deptId,sp.start_time as starttime,sp.end_time endtime,sp.space,watch_area_id,sim_id,terminal_id,valid_watch_gps_time from LP_SPEC_VALID_WATCH, LP_WATCH_POLYGON lw, LP_SPECIAL_WATCH sp where SPEC_PLAN_ID = '" + id + "'  and lw.POLYGONID = watch_area_id and spec_plan_id = sp.plan_id";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map> getInvalidWatch(String id){
		String sql = "select  SP.PATROL_GROUP_ID as deptId,sp.start_time as starttime,sp.end_time endtime,sp.space,watch_area_id,terminal_id,sim_id,invalid_watch_gps_time,invalid_watch_type from LP_SPEC_INVALID_WATCH, LP_WATCH_POLYGON lw, LP_SPECIAL_WATCH sp where SPEC_PLAN_ID = '" + id + "' and lw.POLYGONID = watch_area_id and spec_plan_id = sp.plan_id";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
