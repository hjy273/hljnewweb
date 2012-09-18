package com.cabletech.linepatrol.acceptance.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.QueryBean;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.workflow.AcceptanceFlow;

@Repository
public class ApplyDao extends HibernateDao<Apply, String> {
	private static final String PLAN_CANCEL = "999";//取消状态
	
	public List<Apply> getApply4Type(String type){
		String hql = "from Apply a where resourceType=? and to_char(applyDate,'yyyy') = to_char(sysdate,'yyyy')";
		return this.find(hql, type);
	}
	public Apply getApplyFromProcessInstanceId(String pid){
		String hql = "from Apply a where a.processInstanceId = ?";
		List<Apply> list = find(hql, pid);
		if (list == null || list.isEmpty()) {
			return null;
		} else
			return list.get(0);
	}
	
	public List<Map> getDeptOptions(){
		String sql = "select contractorid,contractorname from contractorinfo where state is null and linkmaninfo is not null order by contractorname ";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	public String getDeptName(String deptId){
		String sql = "select contractorname from contractorinfo where contractorid = '" + deptId + "'";
		return (String)this.getJdbcTemplate().queryForMap(sql).get("contractorname");
	}
	
	public List<Map> getUsers(){
		String hql = "select userid, username from userinfo";
		return this.getJdbcTemplate().queryForList(hql);
	}
	
	public String getMobileFromUserId(String userId){
		String sql = "select phone from userinfo where userid='"+userId+"'";
		return (String)this.getJdbcTemplate().queryForObject(sql, String.class);
	}
	
	public List<String> getMobileFromDeptId(String deptId){
		//String sql = "select linkmaninfo from contractorinfo where contractorid='"+deptId+"'";
		//return (String)this.getJdbcTemplate().queryForObject(sql, String.class);
		String sql = "select phone from userinfo where deptid='"+deptId+"'";
		return this.getJdbcTemplate().queryForList(sql, String.class);
		
	}
	
	public List<Apply> query(QueryBean queryBean){
		StringBuffer hql = new StringBuffer();
		hql.append("from Apply a where 1=1");
		
		if(StringUtils.isNotBlank(queryBean.getContractorId())){
			hql.append(" and a.id in (select applyId from ApplyContractor");
			hql.append(" where contractorId='"+queryBean.getContractorId()+"') ");
		}
		
		//是否取消
		String processState = queryBean.getProcessState();
		if(StringUtils.isNotBlank(processState)){
			if(StringUtils.equals(processState, PLAN_CANCEL)){
				hql.append(" and a.processState='");
				hql.append(processState);
				hql.append("'");
			} else {
				hql.append(" and (a.processState<>'");
				hql.append(PLAN_CANCEL);
				hql.append("' or a.processState is null)");
			}
		}
		
		List<String> param = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(queryBean.getName())){
			hql.append("and a.name like '%");
			hql.append(queryBean.getName());
			hql.append("%' ");
		}
		if(StringUtils.isNotBlank(queryBean.getResourceType())){
			hql.append("and a.resourceType = ?");
			param.add(queryBean.getResourceType());
		}
		if(queryBean.getAcceptanceState() != null && queryBean.getAcceptanceState().length != 0){
			String state = joinString(queryBean.getAcceptanceState());
			if(StringUtils.isNotBlank(state)){
				hql.append("and a.processState in (" + state + ")");
			}
		}
		if(StringUtils.isNotBlank(queryBean.getBegintime()) && StringUtils.isNotBlank(queryBean.getEndtime())){
			hql.append("and a.applyDate >= to_Date(?,'yyyy/MM/dd')");
			hql.append("and a.applyDate <= to_Date(?,'yyyy/MM/dd')");
			param.add(queryBean.getBegintime());
			param.add(queryBean.getEndtime());
		}
		if (queryBean.getTaskStates() != null) {
			String[] taskStates = queryBean.getTaskStates();
			hql.append(" and ( ");
			hql.append(" exists( ");
			hql
					.append(" select jbpm_task.dbid from org.jbpm.pvm.internal.task.TaskImpl jbpm_task ");
			hql.append(" where ");
			hql.append(" jbpm_task.executionId='");
			hql.append(AcceptanceFlow.PROCESSING_NAME);
			hql.append(".'||a.id ");
			hql.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				hql.append(" jbpm_task.name='");
				hql.append(taskStates[i].split("__")[1] + "' ");
				if (i < taskStates.length - 1) {
					hql.append(" or ");
				}
			}
			hql.append(" ) ");
			hql.append(" ) ");
			hql.append(" or exists( ");
			hql
					.append(" select s.id from org.jbpm.pvm.internal.task.TaskImpl jbpm_task,Subflow s  ");
			hql.append(" where s.applyId=a.id and ");
			hql.append(" jbpm_task.executionId='");
			hql.append(AcceptanceFlow.SUB_PROCESSING_NAME);
			hql.append(".'||s.id ");
			hql.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				hql.append(" jbpm_task.name='");
				hql.append(taskStates[i].split("__")[1] + "' ");
				if (i < taskStates.length - 1) {
					hql.append(" or ");
				}
			}
			hql.append(" ) ");
			hql.append(" ) ");
			hql.append(" ) ");
		}
		hql.append("order by a.applyDate desc");
		return this.find(hql.toString(), param.toArray());
	}
	
	public String joinString(String[] str){
		List<String> list = new ArrayList<String>();
		for(String s : str){
			if(StringUtils.isNotBlank(s)){
				list.add("'"+s+"'");
			}
		}
		return StringUtils.join(list.iterator(), ",");
	}
	
	public List<Apply> getFinishedWork(UserInfo userInfo){
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct a from Apply a, ProcessHistory ph where a.id = ph.objectId and ph.objectType = '");
		sb.append(ModuleCatalog.INSPECTION);
		sb.append("' and ph.operateUserId = '");
		sb.append(userInfo.getUserID());
		sb.append("' ");
		return find(sb.toString());
	}
	public List<Map> getDeptOptions4Apply(String applyName) {
		String sql = "select contractorid,contractorname from contractorinfo where state is null and linkmaninfo is not null and contractorid in(select c.contractor_id from lp_acceptance_con c,lp_acceptance_apply a where c.apply_id=a.id and a.name like '"+applyName+"%') order by contractorname ";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
