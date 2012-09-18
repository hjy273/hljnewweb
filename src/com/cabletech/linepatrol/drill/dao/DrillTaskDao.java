package com.cabletech.linepatrol.drill.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.module.DrillTask;

@Repository
public class DrillTaskDao extends HibernateDao<DrillTask, String> {

	/**
	 * 添加演练任务实体
	 * 
	 * @param drillTask：演练任务实体
	 * @return：演练任务实体
	 */
	public DrillTask addDrillTask(DrillTask drillTask) {
		this.save(drillTask);
		return drillTask;
	}

	/**
	 * 由演练任务ID获得演练任务名称
	 * 
	 * @param taskId
	 * @return
	 */
	public String getTaskNameByTaskId(String taskId) {
		return this.findByUnique("id", taskId).getName();
	}
	
	/**
	 * 获得代办工作列表
	 * @param condition：限制条件
	 * @return
	 */
	public List getAgentTask(String condition){
		String sql = "select distinct task.id task_id,'' as plan_id,'' as summary_id,taskcon.id con_id,'' modify_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drilltask_con taskcon,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=taskcon.drill_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator " + condition
			+ " union"
			+ " select distinct task.id task_id,plan.id plan_id,'' as summary_id,taskcon.id con_id,'' modify_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and plan.contractor_id=taskcon.contractor_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator " + condition 
			+ " union"
			+ " select distinct task.id task_id,plan.id plan_id,'' as summary_id,taskcon.id con_id,mod.id modify_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo,lp_drillplan_modify mod "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and plan.contractor_id=taskcon.contractor_id "
			+ " and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator and mod.plan_id=plan.id " + condition
			+ " union"
			+ " select distinct task.id task_id,plan.id plan_id,summary.id summary_id,taskcon.id con_id,'' modify_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drillsummary summary,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and summary.plan_id=plan.id and plan.contractor_id=taskcon.contractor_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator " + condition;
		sql += " order by task_createtime desc";
		logger.info("查询条件：" + condition);
		logger.info("获得演练任务：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得完善演练列表
	 * @param creator
	 * @return
	 */
	public List perfectDrillTaskList(String creator){
		String sql = "select task.id,task.name,decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level," +
				" to_char(task.begintime,'yyyy-mm-dd hh24:mi:ss') task_begin_time," +
				" to_char(task.endtime,'yyyy-mm-dd hh24:mi:ss') task_end_time,task.locale,task.demand,task.remark," +
				" to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime" +
				" from lp_drilltask task where task.creator='" + creator +"' and task.save_flag='1'";
		sql += " order by task_createtime desc";
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得电话号码信息
	 * @param conId
	 * @return
	 */
	public String getPhoneByConId(String conId){
		String hql = "from UserInfo userInfo where userInfo.deptID=?";
		List list = this.getHibernateTemplate().find(hql,conId);
		if(list!=null){
			UserInfo userInfo = (UserInfo)list.get(0);
			return userInfo.getPhone();
		}
		return "";
	}
	
	/**
	 * 获得用户信息
	 * @param userId
	 * @return
	 */
	public String[] getUserIdAndUserNameByUserId(String userId){
		String[] idAndName = new String[3];
		String sql = "select userid,username,phone from userinfo where userid='" + userId + "'";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			idAndName[0] = (String)bean.get("userid");
			idAndName[1] = (String)bean.get("username");
			idAndName[2] = (String)bean.get("phone");
		}
		return idAndName;
	}
	
	/**
	 * 获得已办工作列表
	 * @param condition
	 * @return
	 */
	public List queryFinishHandledDrillList(String condition){
		String sql = "select distinct task.id task_id,'' as plan_id,'' as summary_id,taskcon.id con_id,process.handled_task_name,process.task_out_come,process.id as history_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drilltask_con taskcon,contractorinfo coninfo,userinfo userinfo,process_history_info process "
			+ " where task.id=taskcon.drill_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator and process.object_id=taskcon.id and process.object_type='drill' " + condition
			+ " union"
			+ " select distinct task.id task_id,plan.id plan_id,'' as summary_id,taskcon.id con_id,process.handled_task_name,process.task_out_come,process.id as history_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo,process_history_info process "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and plan.contractor_id=taskcon.contractor_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator and process.object_id=taskcon.id and process.object_type='drill' " + condition 
			+ " union"
			+ " select distinct task.id task_id,plan.id plan_id,summary.id summary_id,taskcon.id con_id,process.handled_task_name,process.task_out_come,process.id as history_id,"
			+ " decode(taskcon.state,'1','制定方案','2','方案审核','3','方案审核不通过','4','制定总结','5','总结审核','6','总结审核不通过','7','评分','8','完成') con_state,"
			+ " task.name task_name,coninfo.contractorname,taskcon.state as drill_state,"
			+ " decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drillsummary summary,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo,process_history_info process "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and summary.plan_id=plan.id and plan.contractor_id=taskcon.contractor_id and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator and process.object_id=taskcon.id and process.object_type='drill' " + condition;
		sql += " order by task_createtime desc";
		logger.info("查询条件：" + condition);
		logger.info("获得演练任务：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得附件ID
	 * @param taskId
	 * @return
	 */
	public List getAffixIdByTaskId(String taskId){
		List list = new ArrayList();
		String sql = "select one.fileid from annex_add_one one where one.entity_id='" + taskId + "' and one.entity_type='LP_DRILLTASK' and one.module='drill'";
		List list2 = this.getJdbcTemplate().queryForBeans(sql);
		if(list2 != null && list2.size() > 0){
			for (Iterator iterator = list2.iterator(); iterator.hasNext();) {
				BasicDynaBean bean = (BasicDynaBean) iterator.next();
				String id = (String)bean.get("fileid");
				list.add(id);
			}
		}
		return list;
	}
}
