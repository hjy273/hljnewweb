package com.cabletech.linepatrol.drill.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.drill.module.DrillPlan;

@Repository
public class DrillPlanDao extends HibernateDao<DrillPlan, String> {

	/**
	 * 由演练方案ID获得演练任务ID
	 * 
	 * @param planId：演练方案ID
	 * @return：演练任务ID
	 */
	public String getTaskIdByPlanId(String planId) {
		return this.findByUnique("id", planId).getTaskId();
	}

	/**
	 * 添加演练方案实体
	 * 
	 * @param drillPlan：演练方案实体
	 * @return：演练方案实体
	 */
	public DrillPlan addDrillPlan(DrillPlan drillPlan) {
		this.save(drillPlan);
		this.initObject(drillPlan);
		return drillPlan;
	}

	/**
	 * 由演练任务ID和代维单位ID获得演练方案
	 * 
	 * @param taskId：演练任务ID
	 * @param conId：代维单位ID
	 * @return：演练方案
	 */
	public DrillPlan getDrillPlanByTaskIdAndContractorId(String taskId,
			String conId) {
		DrillPlan drillPlan = null;
		String hql = "from DrillPlan plan where plan.taskId=? and plan.contractorId=?";
		logger.info("*********planId:" + taskId + "*******conId:" + conId);
		List<DrillPlan> list = this.find(hql, taskId, conId);
		if(list != null && list.size() > 0){
			drillPlan = list.get(0);
		}
		return drillPlan;
	}

	/**
	 * 演练方案未通过次数加1
	 * 
	 * @param planId：演练方案ID
	 */
	public void setUnapproveNumberByPlanId(String planId) {
		DrillPlan drillPlan = this.findByUnique("id", planId);
		int unapproveNumber = drillPlan.getUnapproveNumber();
		drillPlan.setUnapproveNumber(++unapproveNumber);
		this.save(drillPlan);
	}
	
	public void updateDate(String nextStartTime, String nextEndTime, String deadline, String id){
		String sql = "update lp_drillplan set begintime=to_date('" + nextStartTime 
			+ "','yyyy/mm/dd hh24:mi:ss'),endtime=to_date('" + nextEndTime 
			+ "','yyyy/mm/dd hh24:mi:ss'),deadline=to_date('" + deadline 
			+ "','yyyy/mm/dd hh24:mi:ss') where id='" + id + "'";
		logger.info("更新日期sql：" + sql);
		this.getJdbcTemplate().update(sql);
	}
	
	public void addDeadline(String planId, String deadline){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DrillPlan drillPlan = this.findByUnique("id", planId);
		Date deadlineDate = null;
		if(deadline != null && !"".equals(deadline)){
			try {
				deadlineDate = sdf.parse(deadline);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new ServiceException();
			}
		}
		drillPlan.setDeadline(deadlineDate);
		this.save(drillPlan);
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
