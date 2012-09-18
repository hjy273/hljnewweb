package com.cabletech.linepatrol.drill.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.beans.DrillSummaryBean;
import com.cabletech.linepatrol.drill.module.DrillSummary;

@Repository
public class DrillSummaryDao extends HibernateDao<DrillSummary, String> {

	/**
	 * ���������ܽ�ʵ��
	 * 
	 * @param drillSummary�������ܽ�ʵ��
	 * @return�������ܽ�ʵ��
	 */
	public DrillSummary addDrillSummary(DrillSummary drillSummary) {
		this.save(drillSummary);
		this.initObject(drillSummary);
		return drillSummary;
	}

	/**
	 * �����ܽ�δͨ��������1
	 * 
	 * @param summaryId�������ܽ�ID
	 */
	public void setUnapproveNumberBySummaryId(String summaryId) {
		DrillSummary drillSummary = this.findByUnique("id", summaryId);
		int unapproveNumber = drillSummary.getUnapproveNumber();
		drillSummary.setUnapproveNumber(++unapproveNumber);
		this.save(drillSummary);
	}

	/**
	 * �������ܽ�ID�����������ID
	 * 
	 * @param summaryId
	 * @return
	 */
	public String getPlanIdBySummaryId(String summaryId) {
		return this.findByUnique("id", summaryId).getPlanId();
	}
	
	/**
	 * �������ܽ�ID����������ά��ϵID
	 * @param summaryId�������ܽ�ID
	 * @return
	 */
	public String getTaskConIdBySummaryId(String summaryId){
		String taskConId = "";
		String sql = "select taskcon.id taskcon_id from lp_drilltask task,lp_drilltask_con taskcon,lp_drillplan plan,lp_drillsummary summary"
				+ " where task.id=taskcon.drill_id and plan.task_id=task.id and plan.contractor_id=taskcon.contractor_id"
				+ " and plan.id=summary.plan_id and summary.id='" + summaryId + "'";
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			taskConId = (String)bean.get("taskcon_id");
		}
		return taskConId;
	}
	
	/**
	 * �Ƿ���������Ϣ
	 * @param objectId
	 * @return
	 */
	public String haveApproveInfo(String objectId){
		String str = "";
		String sql = "select 1 from lp_approve_info t where t.object_id=? and t.object_type='LP_DRILLSUMMARY'";
		List list = getJdbcTemplate().queryForBeans(sql, objectId);
		if(list != null && list.size() > 0){
			str = "yes";
		}else{
			str = "no";
		}
		return str;
	}
}
