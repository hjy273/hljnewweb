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
	 * ����������ID�����������ID
	 * 
	 * @param planId����������ID
	 * @return����������ID
	 */
	public String getTaskIdByPlanId(String planId) {
		return this.findByUnique("id", planId).getTaskId();
	}

	/**
	 * �����������ʵ��
	 * 
	 * @param drillPlan����������ʵ��
	 * @return����������ʵ��
	 */
	public DrillPlan addDrillPlan(DrillPlan drillPlan) {
		this.save(drillPlan);
		this.initObject(drillPlan);
		return drillPlan;
	}

	/**
	 * ����������ID�ʹ�ά��λID�����������
	 * 
	 * @param taskId����������ID
	 * @param conId����ά��λID
	 * @return����������
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
	 * ��������δͨ��������1
	 * 
	 * @param planId����������ID
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
		logger.info("��������sql��" + sql);
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
	 * ����δ�����ĳ�����ID
	 * @param objectId��ҵ��ID
	 * @param approverType���������� ���������͡�ת��
	 * @param objectType��ҵ������
	 * @param condition����Ӳ�ѯ����
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
