package com.cabletech.linepatrol.maintenance.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.maintenance.module.TestYearPlanTask;


@Repository
public class TestYearPlanTaskDAO extends HibernateDao<TestYearPlanTask, String> {

	/**
	 * 根据年计划查询年计划任务
	 * @param planId
	 * @return
	 */
	public List<TestYearPlanTask> getPlanTasksByPlanId(String planId){
		//Map<String,TestYearPlanTask> map = new 	HashMap<String,TestYearPlanTask>();			
		String hql = "from TestYearPlanTask t where t.yearPlanId=?";
		List list = find(hql,planId);
		/*if(list!=null && list.size()>0){
			for(int i = 0;i<list.size();i++){
				TestYearPlanTask task = (TestYearPlanTask)list.get(i);
				String code = task.getCableLevel();
				map.put(code,task);
			}
		}
		return map;*/
		return list;
	}
	
	/**
	 * 根据年计划id删除任务id
	 * @param planId
	 * @return
	 */
	public void deleteTasksByPlanId(String planId){
		String sql = " delete lp_test_year_plan_task t where year_plan_id='"+planId+"'";
		this.getJdbcTemplate().execute(sql);
	}


}
