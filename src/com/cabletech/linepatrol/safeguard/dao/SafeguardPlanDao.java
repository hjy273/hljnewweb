package com.cabletech.linepatrol.safeguard.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;

@Repository
public class SafeguardPlanDao extends HibernateDao<SafeguardPlan, String> {

	/**
	 * 由保障计划ID获得保障任务ID
	 * 
	 * @param planId：保障计划ID
	 * @return：保障任务ID
	 */
	public String getTaskIdByPlanId(String planId) {
		SafeguardPlan safeguardPlan = this.findByUnique("id", planId);
		return safeguardPlan.getSafeguardId();
	}

	/**
	 * 保障计划未通过次数加1
	 * 
	 * @param planId：保障计划ID
	 * @throws ParseException 
	 */
	public void setUnpassTimessAndDeadline(String planId, String deadline) throws ParseException {
		SafeguardPlan safeguardPlan = this.findByUnique("id", planId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if(deadline != null && !"".equals(deadline)){
			Date deadlineDate = sdf.parse(deadline);
			safeguardPlan.setDeadline(deadlineDate);
		}
		int unpassTimes = safeguardPlan.getAuditingNum();
		safeguardPlan.setAuditingNum(++unpassTimes);
		this.save(safeguardPlan);
	}

	/**
	 * 由保障任务ID和代维ID获得保障计划ID
	 * 
	 * @param taskId：保障任务ID
	 * @param conId：代维单位ID
	 * @return：保障计划实体
	 */
	public SafeguardPlan getPlanByTaskIdAndConId(String taskId, String conId) {
		String hql = "from SafeguardPlan plan where plan.safeguardId=? and plan.contractorId=?";
		List list = find(hql,new String[] { taskId, conId });
		if (list != null && list.size() > 0) {
			return (SafeguardPlan) list.get(0);
		}
		return null;
	}

	/**
	 * 保存保障计划实体
	 * 
	 * @param safeguardPlan：保障计划实体
	 * @return：保障计划实体
	 */
	public SafeguardPlan saveSafeguardPlan(SafeguardPlan safeguardPlan) {
		this.save(safeguardPlan);
		this.initObject(safeguardPlan);
		return safeguardPlan;
	}

	/**
	 * 设置保障计划中的特巡ID
	 * 
	 * @param planId：保障计划ID
	 * @param specialPlanId：特巡计划ID
	 */
	public void setSpecIdByPlanId(String planId, String specialPlanId) {
		SafeguardPlan safeguardPlan = this.findByUnique("id", planId);
		safeguardPlan.setSpecialPlanId(specialPlanId);
		this.save(safeguardPlan);
	}
	
	/**
	 * 由保障任务ID和代维ID获得保障方案ID
	 * @param taskId：保障任务ID
	 * @param conId：代维单位ID
	 * @return
	 */
	public List getPlanByTaskIdConId(String taskId, String conId){
		String hql = "from SafeguardPlan plan where plan.safeguardId=? and plan.contractorId=?";
		return this.find(hql, taskId, conId);
	}
	
	/**
	 * 设置终止时间
	 * @param planId
	 * @param deadline
	 */
	public void setDeadline(String planId, String deadline){
		String sql = "update lp_safeguard_scheme set deadline=to_date('" + deadline 
			+ "','yyyy/mm/dd hh24:mi:ss') where id='" + planId + "'";
		logger.info("设置保障方案截止时间：" + sql);
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 获得审核用户信息
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
	 * 
	 * @param spid:特巡计划ID
	 * @return
	 */
	public List<Map> getSafeguardPlan(String spid){
		String sql = "select sp.id,sp.plan_name,to_char(sp.start_date,'yyyy-mm-dd hh24:mi:ss') start_date,"
			+ " '' as flag,to_char(sp.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,stat.patrol_stat_state" 
			+ " from lp_special_plan sp left join lp_spec_plan_stat stat on sp.id=stat.spec_plan_id"
			+ " where sp.id='" + spid + "' and sp.plan_type='002'";
		logger.info("sql:" + sql);
		List list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	
	public String whetherPlaned(String planId){
		String flag = "";
		String sql = "select 1 from lp_reply_approver t where t.object_id=? and t.object_type='LP_SAFEGUARD_PLAN'";
		List list = getJdbcTemplate().queryForBeans(sql, planId);
		if(list != null && list.size() > 0){
			flag = "exists";
		}
		return flag;
	}
	
	/**
	 * 查找未审批的抄送人ID
	 * @param objectId：业务ID
	 * @param approverType：审批类型 审批、抄送、转审
	 * @param objectType：业务类型
	 * @param condition：添加查询条件
	 * @return
	 */
	public String getApproverIds(String objectId, String approverType, String objectType, String condition){
		String approverIds = "";
		String sql = "select t.approver_id from lp_reply_approver t where t.object_type=? and t.object_id=? and t.approver_type=? ";
		sql += condition;
		List list = getJdbcTemplate().queryForBeans(sql, objectType, objectId, approverType);
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				BasicDynaBean bean = (BasicDynaBean)list.get(i);
				approverIds += bean.get("approver_id") + ",";
			}
		}
		if(!"".equals(approverIds)){
			approverIds = approverIds.substring(0, approverIds.length() - 1);
		}
		return approverIds;
	}
}
