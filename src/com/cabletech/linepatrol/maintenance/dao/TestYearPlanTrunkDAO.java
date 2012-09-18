package com.cabletech.linepatrol.maintenance.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.tags.module.Dictionary;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestYearPlan;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTrunk;
import com.cabletech.linepatrol.maintenance.service.MainTenanceConstant;


@Repository
public class TestYearPlanTrunkDAO extends HibernateDao<TestYearPlanTrunk, String> {
	
	/**
	 * 根据年计划任务得到变更中继段
	 * @param taskId
	 * @return
	 */
	public List<TestYearPlanTrunk> getTrunksByTaskId(String taskId){
		String hql = " from TestYearPlanTrunk t where t.yearTaskId=?";
		return find(hql,taskId);
	}
	
	/**
	 * 根据年计划任务删除变更中继段
	 * @param taskId
	 * @return
	 */
	public void deleteTrunksByTaskId(String taskId){
		String sql = " delete lp_test_year_plan_trunk_detail t where yeartask_id='"+taskId+"'";
		this.getJdbcTemplate().execute(sql);
	}
}
