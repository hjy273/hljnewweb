package com.cabletech.linepatrol.acceptance.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.QueryReinspectBean;
import com.cabletech.linepatrol.acceptance.model.ApplyCable;

@Repository
public class ApplyCableDao extends HibernateDao<ApplyCable, String> {
	public boolean hasDuplicateCableNo(String cableId){
		String hql = "from ApplyCable a where a.cableNo = ?";
		return find(hql, cableId).isEmpty() ? false : true;
	}
	
	public List<ApplyCable> getCompletedCable(String applyId, String contractorId){
		String hql = "from ApplyCable ac where ac.id in (select ct.cableId from CableTask ct where ct.taskId in (select at.id from ApplyTask at where at.applyId = ? and at.contractorId = ? and at.id in (select pc.taskId from PayCable pc)))";
		hql = "select ac from ApplyCable ac ,CableTask ct, ApplyTask at, PayCable pc where ac.id=ct.cableId and ct.taskId=at.id and pc.taskId=ct.id and at.applyId = ? and at.contractorId = ?";
		return find(hql, applyId, contractorId);
	}
	
	public List<ApplyCable> getCableForDept(String applyId, String contractorId){
		String hql = "from ApplyCable ac where ac.id in (select ct.cableId from CableTask ct where ct.taskId in (select at.id from ApplyTask at where at.applyId = ? and at.contractorId = ? ))";
		hql = "select ac from ApplyCable ac ,CableTask ct, ApplyTask at where ac.id=ct.cableId and ct.taskId=at.id and at.applyId = ? and at.contractorId = ?";
		return find(hql, applyId, contractorId);
	}
	
	public List<ApplyCable> getNotPassedCable(String applyId, String contractorId){
		String hql = "from ApplyCable ac where ac.id in (select ct.cableId from CableTask ct where ct.taskId in (select at.id from ApplyTask at where at.applyId = ? and at.contractorId = ? and at.id in (select pc.taskId from PayCable pc where pc.passed = '0')))";
		hql = "select ac from ApplyCable ac ,CableTask ct, ApplyTask at, PayCable pc where ac.id=ct.cableId and ct.taskId=at.id and pc.taskId=ct.id and pc.passed = '0' and at.applyId = ? and at.contractorId = ?";
		return find(hql, applyId, contractorId);
	}
	public List<ApplyCable> findByTaskId(String taskId){
		String hql = "from ApplyCable where id in(select cableId from CableTask where taskId=?)";
		return findBy(hql, taskId);
	}
	
	public List<ApplyCable> getNotPassedApplyCable(QueryReinspectBean queryInBean){
		List<String> param = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("from ApplyCable ac where 1=1 ");
		if(StringUtils.isNotBlank(queryInBean.getCableLevel())){
			sb.append("and ac.cableLevel = ? ");
			param.add(queryInBean.getCableLevel());
		}
		if(StringUtils.isNotBlank(queryInBean.getPrcpm())){
			sb.append("and ac.prcpm = ? ");
			param.add(queryInBean.getPrcpm());
		}
		if(StringUtils.isNotBlank(queryInBean.getIssueNumber())){
			sb.append("and ac.issueNumber = ? ");
			param.add(queryInBean.getIssueNumber());
		}
		sb.append("and ac.id in (select ct.cableId from CableTask ct where ct.id in (");
		sb.append("select pc.taskId from PayCable pc where pc.passed = '0' ");
		if(StringUtils.isNotBlank(queryInBean.getBegintime()) && StringUtils.isNotBlank(queryInBean.getEndtime())){
			sb.append("and pc.acceptanceDate >= to_Date(?,'yyyy/MM/dd') ");
			sb.append("and pc.acceptanceDate <= to_Date(?,'yyyy/MM/dd') ");
			param.add(queryInBean.getBegintime());
			param.add(queryInBean.getEndtime());
		}
		sb.append("))");
		return find(sb.toString(), param.toArray());
	}
	
	public List<Map> getNotClaimedCable(String applyId){
		String[] param = new String[]{applyId, applyId};
		String sql = "select ac.ID from LP_ACCEPTANCE_CABLE ac where ac.APPLY_ID = ? and ac.ID not in (select ct.CABLE_ID from LP_ACCEPTANCE_CTASK ct where ct.TASK_ID in (select at.ID from LP_ACCEPTANCE_TASK at where at.APPLY_ID = ?))";
		return this.getJdbcTemplate().queryForList(sql, param);
	}

	public ApplyCable findbyCable4RSId(String rsId) {
		String hql = "select cable from PayCable paycable,CableTask ctask ,ApplyCable cable where paycable.taskId=ctask.id and ctask.cableId=cable.id and paycable.cableId=?";
		return this.findUnique(hql,rsId);
	}
}
