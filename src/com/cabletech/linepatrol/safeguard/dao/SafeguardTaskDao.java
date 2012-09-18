package com.cabletech.linepatrol.safeguard.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;

@Repository
public class SafeguardTaskDao extends HibernateDao<SafeguardTask, String> {

	/**
	 * 保存保障任务实体
	 * 
	 * @param safeguardTask：保障任务实体
	 * @return：保障任务实体
	 */
	public SafeguardTask addSafeguardTask(SafeguardTask safeguardTask) {
		this.save(safeguardTask);
		this.initObject(safeguardTask);
		return safeguardTask;
	}

	/**
	 * 获得该部门本年度制定保障数
	 * 
	 * @param deptId：部门ID
	 * @return
	 */
	public Integer getSafeguardTaskNumber(String deptId) {
		StringBuffer buf = new StringBuffer();
		buf.append(" select count(*) num ");
		buf.append(" from lp_safeguard_task where 1=1 ");
		buf.append(" and to_char(send_date,'yyyy')=to_char(sysdate,'yyyy')");
		buf.append(" and sender in (select userID from userinfo where deptid='"
				+ deptId + "')");
		logger.info("*************buf.toString():" + buf.toString());
		int num = this.getJdbcTemplate().queryForInt(buf.toString());
		return num + 1;
	}

	/**
	 * 由保障任务ID获得保障任务名称
	 * 
	 * @param taskId：保障任务ID
	 * @return：保障任务名称
	 */
	public String getTaskNameByTaskId(String taskId) {
		SafeguardTask safeguardTask = this.findByUnique("id", taskId);
		return safeguardTask.getSafeguardName();
	}
	
	public List getAgentTask(String condition){
		String sql = " select distinct task.start_date,task.end_date,task.id task_id,'' plan_id,'' summary_id,taskcon.id con_id,task.safeguard_name task_name,null as sp_end_date,coninfo.contractorname,userinfo.username,'' sp_end_id,'' sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_con taskcon ,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=taskcon.safeguard_id and coninfo.contractorid=taskCon.contractor_id and userinfo.userid=task.sender " + condition
			+ " union"
			//有保障方案而没有特巡计划
			+ " select distinct task.start_date,task.end_date,task.id task_id,plan.id plan_id,'' summary_id,taskcon.id con_id,task.safeguard_name task_name,null as sp_end_date,coninfo.contractorname,userinfo.username,'' sp_end_id,'' sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_scheme plan,lp_safeguard_con taskcon,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and coninfo.contractorid=taskCon.contractor_id and plan.contractor_id=taskcon.contractor_id and userinfo.userid=task.sender " + condition
			+ " union"
			//有保障方案有特巡计划
			+ " select distinct task.start_date,task.end_date,task.id task_id,plan.id plan_id,'' summary_id,taskcon.id con_id,task.safeguard_name task_name,spplan.end_date as sp_end_date,coninfo.contractorname,userinfo.username,'' sp_end_id,sp.plan_id sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_scheme plan,lp_safeguard_con taskcon,lp_special_plan spplan,contractorinfo coninfo,userinfo userinfo,lp_safeguard_plan sp "
			+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and spplan.id=sp.plan_id and plan.id=sp.scheme_id and coninfo.contractorid=taskCon.contractor_id and plan.contractor_id=taskcon.contractor_id and userinfo.userid=task.sender " + condition 
			+ " union"
			//有特巡计划终止
			+ " select distinct task.start_date,task.end_date,task.id task_id,plan.id plan_id,'' summary_id,taskcon.id con_id,task.safeguard_name task_name,spplan.end_date as sp_end_date,coninfo.contractorname,userinfo.username,spend.id sp_end_id,sp.plan_id sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_scheme plan,lp_safeguard_con taskcon,lp_special_plan spplan,contractorinfo coninfo,userinfo userinfo,lp_safeguard_plan sp,lp_special_endplan spend "
			+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and spplan.id=sp.plan_id and plan.id=sp.scheme_id and coninfo.contractorid=taskCon.contractor_id and plan.contractor_id=taskcon.contractor_id and userinfo.userid=task.sender and spend.plan_id=spplan.id " + condition 
			+ " union"
			//有保障总结没保障特巡计划
			+ " select distinct task.start_date,task.end_date,task.id task_id,plan.id plan_id,summary.id summary_id,taskcon.id con_id,task.safeguard_name task_name,null sp_end_date,coninfo.contractorname,userinfo.username,'' sp_end_id,'' sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_scheme plan,lp_safeguard_sum summary,lp_safeguard_con taskcon,contractorinfo coninfo,userinfo userinfo "
			+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and summary.scheme_id=plan.id and coninfo.contractorid=taskCon.contractor_id and plan.contractor_id=taskcon.contractor_id and userinfo.userid=task.sender " + condition
			+ " union"
			//有保障总结也有特巡计划
			+ " select distinct task.start_date,task.end_date,task.id task_id,plan.id plan_id,summary.id summary_id,taskcon.id con_id,task.safeguard_name task_name,spplan.end_date as sp_end_date,coninfo.contractorname,userinfo.username,'' sp_end_id,sp.plan_id sp_id,"
			+ " decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, "
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement requirement,task.sender,'' as page_flag,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state,taskCon.transact_state as safeguard_state"
			+ " from lp_safeguard_task task,lp_safeguard_scheme plan,lp_safeguard_sum summary,lp_safeguard_con taskcon,lp_special_plan spplan ,contractorinfo coninfo,userinfo userinfo,lp_safeguard_plan sp "
			+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and summary.scheme_id=plan.id and spplan.id=sp.plan_id and plan.id=sp.scheme_id and coninfo.contractorid=taskCon.contractor_id and plan.contractor_id=taskcon.contractor_id and userinfo.userid=task.sender " + condition;
		sql += " order by task_createtime desc";
		logger.info("查询条件：" + condition);
		logger.info("获得保障任务：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 获得保障任务暂存列表
	 * @param creator：保障任务创建人
	 * @return
	 */
	public List getPerfectList(String creator){
		String sql = " select distinct task.id task_id,task.safeguard_name task_name,"
			+ " decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,task.requirement,"
			+ " to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') task_createtime,'' as isread,'' as flow_state"
			+ " from lp_safeguard_task task where task.sender=? and task.save_flag='1' order by task_createtime desc";
		return this.getJdbcTemplate().queryForBeans(sql, creator);
	}
	
	/**
	 * 由用户ID获得该用户的电话号码
	 * @param approver：用户ID
	 * @return
	 */
	public String getMobileByUserId(String approver){
		String hql = "from UserInfo userInfo where userInfo.userID=?";
		List list = this.getHibernateTemplate().find(hql,approver);
		UserInfo userInfo = (UserInfo)list.get(0);
		return userInfo.getPhone();
	}
	
	/**
	 * 由多个用户获得用户电话号码
	 * @param approvers：用户IDS
	 * @return
	 */
	public String getMobiles(String approvers){
		if(approvers != null && !"".equals(approvers)){
			String[] approves = approvers.split(",");
			String mobiles = "";
			for (int i = 0; i < approves.length; i++) {
				String mobile = getMobileByUserId(approves[i]);
				mobiles = mobile + ",";
			}
			return mobiles.substring(0, mobiles.length()-1);
		}
		return null;
	}
	
	/**
	 * 由部门ID获得该部门电话号码
	 * @param conId：部门ID
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
	 * 获得已办工作列表
	 * @param condition
	 * @return
	 */
	public List queryFinishHandledSafeguardList(String condition){
		String sql="select * from " +
				"(select task.id task_id,con.id con_id,task.safeguard_name task_name,coninfo.contractorname," +
				" decode(con.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state, " +
				" decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,task.region,to_char(task.send_date,'yyyy-mm-dd hh24:mi') task_createtime,con.transact_state  safeguard_state," +
				" task.safeguard_code,task.start_date,task.end_date,task.sender,task.deadline,userinfo.username username, con.contractor_id contractorid" +
				" from lp_safeguard_task task,lp_safeguard_con con, contractorinfo coninfo,userinfo userinfo" +
				" where task.id = con.safeguard_id and coninfo.contractorid=con.contractor_id and userinfo.userid=task.sender " +
				" and con.id in(select distinct object_id from process_history_info process where process.object_type='safeguard' "+condition+")) t," +
				" (select scheme.id plan_id,summary.id summary_id,scheme.safeguard_id,scheme.contractor_id " +
				" from lp_safeguard_scheme scheme,lp_safeguard_sum summary where summary.scheme_id(+)=scheme.id) s" +
				" where task_id=safeguard_id(+) and contractorid=contractor_id(+) order by task_createtime desc";
		
		logger.info("获得保障任务：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	/**
	 * 由保障任务ID获得附件ID
	 * @param taskId：保障任务ID
	 * @return
	 */
	public List getAffixIdByTaskId(String taskId){
		List list = new ArrayList();
		String sql = "select one.fileid from annex_add_one one where one.entity_id='" + taskId + "' and one.entity_type='LP_SAFEGUARD_TASK' and one.module='safeguard'";
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
