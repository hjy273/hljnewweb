package com.cabletech.linepatrol.overhaul.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.workflow.OverhaulWorkflowBO;
import com.cabletech.linepatrol.overhaul.beans.OverHaulQueryBean;
import com.cabletech.linepatrol.overhaul.model.OverHaul;

@Repository
public class OverHaulDao extends HibernateDao<OverHaul, String> {
	
	private static final String PLAN_CANCEL = "999";

	public OverHaul getFromProcessInstanceId(String processInstanceId) {
		return this.findByUnique("processInstanceId", processInstanceId);
	}

	public String getConName(String contractorid) {
		String sql = "select contractorName from contractorinfo where contractorid='"
				+ contractorid + "'";
		return (String) this.getJdbcTemplate()
				.queryForObject(sql, String.class);
	}

	public String getUserName(String userId) {
		String sql = "select username from userinfo where userid='" + userId
				+ "'";
		return (String) this.getJdbcTemplate()
				.queryForObject(sql, String.class);
	}

	public String getPhoneFromUserid(String userId) {
		String sql = "select phone from userinfo where userid='" + userId + "'";
		return (String) this.getJdbcTemplate()
				.queryForObject(sql, String.class);
	}

	public List<Map> getPrincipal(String deptId) {
		String sql = "select linkmaninfo from contractorinfo where contractorid = '"
				+ deptId + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<OverHaul> getResult(OverHaulQueryBean queryBean,
			UserInfo userInfo) {
		StringBuffer hql = new StringBuffer();
		hql.append("from OverHaul o where 1=1 ");
		List param = new ArrayList();

		if (userInfo.getDeptype().equals("2")) { // 代维要过滤掉不属于自己部门的和没有申请的
			hql
					.append("and o.id in (select c.taskid from OverHaulCon c where c.contractorId = ?) ");
			param.add(userInfo.getDeptID());
			hql
					.append("and o.id in (select a.taskId from OverHaulApply a where a.contractorId = ?) ");
			param.add(userInfo.getDeptID());
		}
		//代维单位
		if (!userInfo.getDeptype().equals("2")) {
			String contractorId = queryBean.getContractorId();
			if (StringUtils.isNotBlank(contractorId)) {
				hql.append("and o.id in (select c.taskid from OverHaulCon c where c.contractorId = ?) ");
				param.add(contractorId);
			}
		}
		//是否取消
		String state = queryBean.getState();
		if(StringUtils.isNotBlank(state)){
			if(StringUtils.equals(state, PLAN_CANCEL)){
				hql.append(" and o.state='");
				hql.append(state);
				hql.append("'");
			} else {
				hql.append(" and (o.state<>'");
				hql.append(PLAN_CANCEL);
				hql.append("' or o.state is null)");
			}
		}

		if (StringUtils.isNotBlank(queryBean.getProjectName())) {
			hql.append("and o.projectName like '%");
			hql.append(queryBean.getProjectName());
			hql.append("%' ");
		}
		if (StringUtils.isNotBlank(queryBean.getMinBudgetFee())) {
			hql.append("and o.budgetFee >= ?");
			param.add(Float.valueOf(queryBean.getMinBudgetFee()));
		}
		if (StringUtils.isNotBlank(queryBean.getMaxBudgetFee())) {
			hql.append("and o.budgetFee <= ?");
			param.add(Float.valueOf(queryBean.getMaxBudgetFee()));
		}
		if (StringUtils.isNotBlank(queryBean.getStartTime())) {
			hql.append("and o.startTime >= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getStartTime());
		}
		if (StringUtils.isNotBlank(queryBean.getEndTime())) {
			hql.append("and o.endTime <= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getEndTime());
		}
		if (queryBean.getTaskStates() != null) {
			String[] taskStates = queryBean.getTaskStates();
			hql.append(" and ( ");
			hql.append(" exists( ");
			hql
					.append(" select jbpm_task.dbid from org.jbpm.pvm.internal.task.TaskImpl jbpm_task ");
			hql.append(" where ");
			hql.append(" jbpm_task.executionId='");
			hql.append(OverhaulWorkflowBO.PROCESSINGNAME);
			hql.append(".'||o.id ");
			hql.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				hql.append(" jbpm_task.name='");
				hql.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					hql.append(" or ");
				}
			}
			hql.append(" ) ");
			hql.append(" ) ");
			hql.append(" or exists( ");
			hql
					.append(" select a.id from org.jbpm.pvm.internal.task.TaskImpl jbpm_task,OverHaulApply a ");
			hql.append(" where a.taskId=o.id and ");
			hql.append(" jbpm_task.executionId='");
			hql.append(OverhaulWorkflowBO.SUBPROCESSINGNAME);
			hql.append(".'||a.id ");
			hql.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				hql.append(" jbpm_task.name='");
				hql.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					hql.append(" or ");
				}
			}
			hql.append(" ) ");
			hql.append(" ) ");
			hql.append(" ) ");
		}
		logger.info(hql.toString());
		return this.find(hql.toString(), param.toArray());
	}

	public List<OverHaul> getFinishedWork(UserInfo userInfo) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select distinct o from OverHaul o,ProcessHistory ph where o.id = ph.objectId and ph.objectType = '");
		sb.append(ModuleCatalog.OVERHAUL);
		sb.append("' and ph.operateUserId = '");
		sb.append(userInfo.getUserID());
		sb.append("' ");
		return find(sb.toString());
	}
	
	public List<OverHaul> getOverHaulsByProcessInstanceIds(String processInstanceIds){
		String Hql="from OverHaul o where o.processInstanceId in ("+processInstanceIds+")";
		return this.find(Hql);
	}
	
	/**
	 * 根据用户查询代维
	 * @param user
	 * @return
	 */
	public List<Contractor> getContractors(UserInfo user){
		String hql = " from Contractor c  where c.regionid='"+user.getRegionid()+"'";
		return this.getHibernateTemplate().find(hql);
	}
}
