package com.cabletech.linepatrol.safeguard.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;

@Repository
public class SafeguardSummaryDao extends HibernateDao<SafeguardSummary, String> {

	/**
	 * 保障总结未通过次数加1
	 * 
	 * @param summaryId：保障总结ID
	 */
	public void setUnpassTimes(String summaryId) {
		SafeguardSummary safeguardSummary = this.findByUnique("id", summaryId);
		int unpassTimes = safeguardSummary.getAuditingNum();
		safeguardSummary.setAuditingNum(++unpassTimes);
		this.save(safeguardSummary);
	}

	/**
	 * 保存保障总结实体
	 * 
	 * @param safeguardSummary：保障总结实体
	 * @return
	 */
	public SafeguardSummary saveSafeguardSummary(
			SafeguardSummary safeguardSummary) {
		this.save(safeguardSummary);
		this.initObject(safeguardSummary);
		return safeguardSummary;
	}

	/**
	 * 由保障总结ID获得保障计划ID
	 * 
	 * @param summaryId：保障总结ID
	 * @return：保障计划ID
	 */
	public String getPlanIdBySummaryId(String summaryId) {
		return this.findByUnique("id", summaryId).getPlanId();
	}
	
	/**
	 * 由保障总结ID获得保障任务与代维关系ID
	 * @param summaryId：保障总结ID
	 * @return
	 */
	public String getTaskConIdBySummaryId(String summaryId){
		String taskConId = "";
		String sql = "select taskcon.id taskcon_id from lp_safeguard_task task,lp_safeguard_con taskcon,lp_safeguard_scheme plan,"
				+ " lp_safeguard_sum sum where task.id=taskcon.safeguard_id and taskcon.contractor_id=plan.contractor_id"
				+ " and task.id=plan.safeguard_id and plan.id=sum.scheme_id and sum.id='" + summaryId + "'";
		logger.info("由保障总结获得保障代维关系ID：" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			taskConId = (String)bean.get("taskcon_id");
		}
		return taskConId;
	}
}
