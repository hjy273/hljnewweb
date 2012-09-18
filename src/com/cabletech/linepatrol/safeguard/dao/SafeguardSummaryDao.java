package com.cabletech.linepatrol.safeguard.dao;

import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;

@Repository
public class SafeguardSummaryDao extends HibernateDao<SafeguardSummary, String> {

	/**
	 * �����ܽ�δͨ��������1
	 * 
	 * @param summaryId�������ܽ�ID
	 */
	public void setUnpassTimes(String summaryId) {
		SafeguardSummary safeguardSummary = this.findByUnique("id", summaryId);
		int unpassTimes = safeguardSummary.getAuditingNum();
		safeguardSummary.setAuditingNum(++unpassTimes);
		this.save(safeguardSummary);
	}

	/**
	 * ���汣���ܽ�ʵ��
	 * 
	 * @param safeguardSummary�������ܽ�ʵ��
	 * @return
	 */
	public SafeguardSummary saveSafeguardSummary(
			SafeguardSummary safeguardSummary) {
		this.save(safeguardSummary);
		this.initObject(safeguardSummary);
		return safeguardSummary;
	}

	/**
	 * �ɱ����ܽ�ID��ñ��ϼƻ�ID
	 * 
	 * @param summaryId�������ܽ�ID
	 * @return�����ϼƻ�ID
	 */
	public String getPlanIdBySummaryId(String summaryId) {
		return this.findByUnique("id", summaryId).getPlanId();
	}
	
	/**
	 * �ɱ����ܽ�ID��ñ����������ά��ϵID
	 * @param summaryId�������ܽ�ID
	 * @return
	 */
	public String getTaskConIdBySummaryId(String summaryId){
		String taskConId = "";
		String sql = "select taskcon.id taskcon_id from lp_safeguard_task task,lp_safeguard_con taskcon,lp_safeguard_scheme plan,"
				+ " lp_safeguard_sum sum where task.id=taskcon.safeguard_id and taskcon.contractor_id=plan.contractor_id"
				+ " and task.id=plan.safeguard_id and plan.id=sum.scheme_id and sum.id='" + summaryId + "'";
		logger.info("�ɱ����ܽ��ñ��ϴ�ά��ϵID��" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		if(list != null && list.size() > 0){
			BasicDynaBean bean = (BasicDynaBean)list.get(0);
			taskConId = (String)bean.get("taskcon_id");
		}
		return taskConId;
	}
}
