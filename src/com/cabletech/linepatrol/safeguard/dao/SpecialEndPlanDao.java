package com.cabletech.linepatrol.safeguard.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SpecialEndPlan;

@Repository
public class SpecialEndPlanDao extends HibernateDao<SpecialEndPlan, String> {
	
	/**
	 * 保存特巡计划终止信息
	 * @param specialEndPlan：保障方案终止信息
	 * @return
	 */
	public SpecialEndPlan saveSpecialEndPlan(SpecialEndPlan specialEndPlan){
		this.save(specialEndPlan);
		return specialEndPlan;
	}
	
	/**
	 * 获得代办工作
	 * @param condition
	 * @return
	 */
	public List getAgentTask(String condition){
		String sql = "select plan.id scheme_id,spend.id sp_end_id,spend.plan_id plan_id,sp.plan_name,decode(spend.end_type,'1','巡检终止','2','重新制定') end_type,"
			+ " to_char(spend.prev_end_date,'yyyy-mm-dd hh24:mi:ss') prev_end_date,"
			+ " to_char(spend.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,"
			+ " spend.reason reason,userInfo.username creater,jtask.name_ jbpm_task_name,'' as flow_state,'' as isread,"
			+ " to_char(spend.create_time,'yyyy-mm-dd hh24:mi:ss') create_time "
			+ " from lp_special_endplan spend,lp_safeguard_scheme plan,lp_special_plan sp,lp_safeguard_plan spplan,jbpm4_task jtask,userinfo userInfo"
			+ " where spend.plan_id=sp.id and sp.id=spplan.plan_id and spplan.scheme_id=plan.id and jtask.execution_id_='safeguard_sub.'||spend.id and userInfo.userid=spend.creater";
		sql += condition;
		sql += " order by spend.create_time desc";
		logger.info("获取保障终止列表：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得保障方案终止列表
	 * @param condition
	 * @return
	 */
	public List getSpecialEndPlanList(String condition){
		String sql = "select spend.id sp_end_id,sp.plan_name,spend.plan_id plan_id,decode(spend.end_type,'1','巡检终止','2','重新制定') end_type,"
			+ " to_char(spend.prev_end_date,'yyyy-mm-dd hh24:mi:ss') prev_end_date,"
			+ " to_char(spend.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,"
			+ " spend.reason reason,userInfo.username creater,"
			+ " to_char(spend.create_time,'yyyy-mm-dd hh24:mi:ss') create_time "
			+ " from lp_special_endplan spend,lp_safeguard_scheme plan,lp_special_plan sp,lp_safeguard_plan spplan,userinfo userInfo "
			+ " where spend.plan_id=sp.id and sp.id=spplan.plan_id and spplan.scheme_id=plan.id and userInfo.userid=spend.creater ";
		sql += condition;
		sql += " order by spend.create_time desc";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 判断该特巡计划终止是否还在流程中
	 * @param spid
	 * @return
	 */
	public String judgeProcessing(String spid){
		List<SpecialEndPlan> list = this.findByProperty("planId", spid);
		if(list != null && list.size() > 0){
			for(SpecialEndPlan sep:list){
				String endId = sep.getId();
				String sql = "select 1 from jbpm4_task where execution_id_='safeguard_sub." + endId +"'";
				List list2 = this.getJdbcTemplate().queryForBeans(sql);
				if(list2 != null && list2.size() > 0){
					return "yes";
				}
			}
		}
		return "no";
	}
	
	/**
	 * 判断是否可以填写保障总结
	 * @param planId：保障方案ID
	 * @return
	 */
	public String whetherCanSummary(String planId){
		String sql = "select plan_id from lp_safeguard_plan where scheme_id='" + planId + "'";
		List list1 = this.getJdbcTemplate().queryForBeans(sql);
		List spids = new ArrayList();
		//获得所有的特巡计划ID
		if(list1 != null && list1.size() > 0){
			for (Iterator iterator = list1.iterator(); iterator.hasNext();) {
				BasicDynaBean bdb = (BasicDynaBean) iterator.next();
				String spid = (String)bdb.get("plan_id");
				spids.add(spid);
			}
		}
		if(spids != null && spids.size() > 0){
			for (Iterator iterator = spids.iterator(); iterator.hasNext();) {
				String spid = (String) iterator.next();
				
				sql = "select 1 from jbpm4_task where execution_id_='safeguard_replan_sub." + spid +"'";
				List list = this.getJdbcTemplate().queryForBeans(sql);
				if(list != null && list.size() > 0){
					return "yes";
				}
				
				sql = "select id from lp_special_endplan where plan_id='" + spid + "'";
				List list2 = this.getJdbcTemplate().queryForBeans(sql);
				if(list2 != null && list2.size() > 0){
					for (Iterator iterator2 = list2.iterator(); iterator2.hasNext();) {
						BasicDynaBean name = (BasicDynaBean) iterator2.next();
						String endId = (String)name.get("id");
						sql = "select 1 from jbpm4_task where execution_id_='safeguard_sub." + endId +"'";
						List list3 = this.getJdbcTemplate().queryForBeans(sql);
						if(list3 != null && list3.size() > 0){
							return "yes";
						}
					}
				}
			}
		}
		return "no";
	}
	
	/**
	 * 由保障方案终止ID获得保障任务与代维关系ID
	 * @param endId：保障方案终止ID
	 * @return
	 */
	public String getTaskConIdByEndId(String endId){
		String taskConId = "";
		String sql = "select taskcon.id taskcon_id from lp_safeguard_task task,lp_safeguard_con taskcon,lp_safeguard_scheme plan,lp_special_plan sp,"
				+ " lp_special_endplan end,lp_safeguard_plan spplan where task.id=taskcon.safeguard_id and taskcon.contractor_id=plan.contractor_id"
				+ " and task.id=plan.safeguard_id and plan.id=spplan.scheme_id and sp.id=spplan.plan_id and sp.id=end.plan_id"
				+ " and end.id='" + endId + "'";
		logger.info("获得保障代维关系Id：" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			taskConId = (String)bean.get("taskcon_id");
		}
		return taskConId;
	}
	
	/**
	 * 由特巡计划ID获得特巡计划名称
	 * @param spId：特巡计划ID
	 * @return
	 */
	public String getSpecialNameBySpecialId(String spId){
		String spName = "";
		String sql = "select t.plan_name from lp_special_plan t where t.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, spId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			spName = (String)bean.get("plan_name");
		}
		return spName;
	}
	
	/**
	 * 查询某特巡计划的其他终止ID
	 * @param planId：特巡计划ID
	 * @param endId：终止计划ID
	 * @return
	 */
	public String getOtherEndId(String planId, String endId){
		String otherEndId = "";
		String sql = "select t.id end_id from lp_special_endplan t where t.plan_id=? and t.id<>?";
		List list = this.getJdbcTemplate().queryForBeans(sql, planId, endId);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			otherEndId = (String)bean.get("end_id");
		}
		return otherEndId;
	}
}
