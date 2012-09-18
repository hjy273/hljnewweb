package com.cabletech.linepatrol.drill.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.module.DrillTask;

@Repository
public class DrillExamDao extends HibernateDao<DrillTask, String> {

	/**
	 * 获得演练评分列表
	 * 
	 * @return
	 */
	public List getDrillExamList() {
		String sql = "select distinct task.id task_id,plan.id plan_id,summary.id summary_id,task.name task_name,task.drill_level,"
				+ "to_char(task.begintime,'yyyy-mm-dd hh24:mi:ss') task_begintime,to_char(task.endtime,'yyyy-mm-dd hh24:mi:ss') task_endtime,"
				+ "plan.person_number plan_person_number,summary.person_number summary_person_number,"
				+ "plan.car_number plan_car_number,summary.car_number summary_car_number,taskcon.state state,taskcon.contractor_id contractor_id "
				+ "from lp_drilltask task,lp_drillplan plan,lp_drillsummary summary,lp_drilltask_con taskcon "
				+ "where task.id=plan.task_id(+) and plan.id=summary.plan_id(+) and task.id=taskcon.drill_id(+) and task.id=taskcon.drill_id(+) and taskcon.state='7'";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
