package com.cabletech.linepatrol.drill.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.module.DrillPlanModify;

@Repository
public class DrillPlanModifyDao extends HibernateDao<DrillPlanModify, String> {
	public DrillPlanModify saveDrillPlanModify(DrillPlanModify drillPlanModify){
		this.save(drillPlanModify);
		return drillPlanModify;
	}
	
	/**
	 * �������������칤���б�
	 * @param condition
	 * @return
	 */
	public List getAgentTask(String condition){
		String sql = "select distinct task.id task_id,plan.id plan_id,'' as summary_id,taskcon.id con_id,mod.id modify_id,"
			+ " decode(taskcon.state,'1','�ƶ�����','2','�������','3','������˲�ͨ��','4','�ƶ��ܽ�','5','�ܽ����','6','�ܽ���˲�ͨ��','7','����','8','���') con_state,"
			+ " task.name task_name,coninfo.contractorname,"
			+ " decode(task.drill_level,'0','�Զ�������','1','һ������','2','�ص�����') as drill_level,'' as page_flag, "
			+ " task.locale,task.demand,task.creator,userinfo.username,"
			+ " to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_drilltask task,lp_drillplan plan,lp_drilltask_con taskcon ,contractorinfo coninfo,userinfo userinfo,lp_drillplan_modify mod "
			+ " where task.id=plan.task_id and task.id=taskcon.drill_id and plan.contractor_id=taskcon.contractor_id "
			+ " and coninfo.contractorid=taskcon.contractor_id and userinfo.userid=task.creator and mod.plan_id=plan.id " + condition;
		sql += " order by task_createtime desc";
		logger.info("���������������б�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	public List getAgentTaskOrg(String condition){
		String sql = "select distinct mod.id modify_id,plan.id plan_id,task.name task_name,jtask.name_ jbpm_task_name,'' as isread," +
				" to_char(mod.prev_starttime,'yyyy-mm-dd hh24:mi:ss') prev_starttime," +
				" to_char(mod.prev_endtime,'yyyy-mm-dd hh24:mi:ss') prev_endtime," +
				" to_char(mod.next_starttime,'yyyy-mm-dd hh24:mi:ss') next_starttime," +
				" to_char(mod.next_endtime,'yyyy-mm-dd hh24:mi:ss') next_endtime," +
				" mod.modify_cause,userinfo.username,'' as flow_state," + 
				" to_char(mod.modify_date,'yyyy-mm-dd hh24:mi:ss') modify_date" +
				" from lp_drilltask task,lp_drilltask_con taskCon,lp_drillplan plan,lp_drillplan_modify mod,userinfo userinfo,jbpm4_task jtask" +
				" where task.id=taskCon.drill_id and plan.task_id=task.id and plan.contractor_id=taskCon.contractor_id" +
				" and mod.plan_id=plan.id and userinfo.userid=mod.modify_man and userinfo.deptid=taskCon.Contractor_Id and jtask.execution_id_='drill_sub_workflow.'||mod.id " + condition +
				" order by modify_date desc";
		logger.info("�������������칤���б�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	public List getQueryList(String condition){
		String sql = "select distinct mod.id modify_id,plan.id plan_id,task.name task_name," + 
				" to_char(mod.prev_starttime,'yyyy-mm-dd hh24:mi:ss') prev_starttime," +
				" to_char(mod.prev_endtime,'yyyy-mm-dd hh24:mi:ss') prev_endtime," +
				" to_char(mod.next_starttime,'yyyy-mm-dd hh24:mi:ss') next_starttime," +
				" to_char(mod.next_endtime,'yyyy-mm-dd hh24:mi:ss') next_endtime," +
				" mod.modify_cause,userinfo.username," + 
				" to_char(mod.modify_date,'yyyy-mm-dd hh24:mi:ss') modify_date" +
				" from lp_drilltask task,lp_drilltask_con taskCon,lp_drillplan plan,lp_drillplan_modify mod,userinfo userinfo" +
				" where task.id=taskCon.drill_id and plan.task_id=task.id and plan.contractor_id=taskCon.contractor_id" +
				" and mod.plan_id=plan.id and userinfo.userid=mod.modify_man and userinfo.deptid=taskCon.Contractor_Id " + condition +
				" order by modify_date desc";
		logger.info("�����������б�" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * �ж��Ƿ������д�����ܽ�
	 * @param planId����������ID
	 * @return
	 */
	public String whetherCanSummary(String planId){
		List<DrillPlanModify> list = this.findByProperty("planId", planId);
		if(list != null && list.size() > 0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DrillPlanModify drillPlanModify = (DrillPlanModify) iterator.next();
				String modifyId = drillPlanModify.getId();
				String sql = "select 1 from jbpm4_task where execution_id_='drill_sub_workflow." + modifyId +"'";
				List list2 = this.getJdbcTemplate().queryForBeans(sql);
				if(list2 != null && list2.size() > 0){
					return "yes";
				}
			}
		}
		return "no";
	}
	
	/**
	 * �������������ID����������ά��ϵID
	 * @param modifyId�������������ID
	 * @return
	 */
	public String getTaskConIdByModifyId(String modifyId){
		String taskConId = "";
		String sql = "select taskcon.id taskcon_id from lp_drilltask task,lp_drilltask_con taskcon,lp_drillplan plan,lp_drillplan_modify mod"
				+ " where task.id=taskcon.drill_id and plan.task_id=task.id and plan.contractor_id=taskcon.contractor_id"
				+ " and plan.id=mod.plan_id and mod.id='" + modifyId + "'";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			taskConId = (String)bean.get("taskcon_id");
		}
		return taskConId;
	}
}
