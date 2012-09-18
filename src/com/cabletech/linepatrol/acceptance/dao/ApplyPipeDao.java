package com.cabletech.linepatrol.acceptance.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.beans.QueryReinspectBean;
import com.cabletech.linepatrol.acceptance.model.ApplyPipe;

@Repository
public class ApplyPipeDao extends HibernateDao<ApplyPipe, String> {
	public List<ApplyPipe> getCompletedPipe(String applyId, String contractorId){
		String hql = "select ap from ApplyPipe ap,PayPipe pp,PipeTask pt,ApplyTask at where pp.taskId=pt.id and pt.taskId=at.id and ap.id=pt.pipeId and at.applyId=? and at.contractorId = ?";
		logger.info("参数："+applyId +" ,"+contractorId);
		return find(hql, applyId, contractorId);
	}
	
	public List<ApplyPipe> getNotPassedPipe(String applyId, String contractorId){
		String hql = "select ap from ApplyPipe ap,PayPipe pp,PipeTask pt,ApplyTask at where pp.taskId=pt.id and pt.taskId=at.id and ap.id=pt.pipeId and pp.passed = '0' and at.applyId=? and at.contractorId = ?";
		logger.info("参数："+applyId +" ,"+contractorId);
		return find(hql, applyId, contractorId);
	}
	
	public List<ApplyPipe> getPipeForDept(String applyId, String contractorId){
		String hql = "select ap from ApplyPipe ap,PipeTask pt,ApplyTask at where pt.taskId=at.id and ap.id=pt.pipeId and at.applyId=? and at.contractorId = ?";
		logger.info("参数："+applyId +" ,"+contractorId);
		return find(hql, applyId, contractorId);
	}
	public List<ApplyPipe> findByTaskId(String taskId){
		String hql = "from ApplyPipe where id in(select cableId from PipeTask where taskId=?)";
		return findBy(hql, taskId);
	}
	public boolean hasDuplicatePipeNo(String pipeNumber){
		String hql = "from ApplyPipe a where a.assetno = ?";
		return find(hql, pipeNumber).isEmpty() ? false : true;
	}
	
	public List<ApplyPipe> getNotPassedApplyPipe(QueryReinspectBean queryInBean){
		List<String> param = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("from ApplyPipe ap where 1=1 ");
		if(StringUtils.isNotBlank(queryInBean.getPcpm())){
			sb.append("and ap.pcpm = ? ");
			param.add(queryInBean.getPcpm());
		}
		if(StringUtils.isNotBlank(queryInBean.getIssueNumber())){
			sb.append("and ap.issueNumber = ? ");
			param.add(queryInBean.getIssueNumber());
		}
		sb.append("and ap.id in (select pt.pipeId from PipeTask pt where pt.id in (");
		sb.append("select pp.taskId from PayPipe pp where pp.passed = '0' ");
		if(StringUtils.isNotBlank(queryInBean.getBegintime()) && StringUtils.isNotBlank(queryInBean.getEndtime())){
			sb.append("and pp.acceptanceDate >= to_Date(?,'yyyy/MM/dd') ");
			sb.append("and pp.acceptanceDate <= to_Date(?,'yyyy/MM/dd') ");
			param.add(queryInBean.getBegintime());
			param.add(queryInBean.getEndtime());
		}
		sb.append("))");
		return find(sb.toString(), param.toArray());
	}
	
	public List<Map> getNotClaimedPipe(String applyId){
		String[] param = new String[]{applyId, applyId};
		String sql = "select ap.ID from LP_ACCEPTANCE_PIPE ap where ap.APPLY_ID = ? and ap.ID not in (select pt.PIPE_ID from LP_ACCEPTANCE_PTASK pt where pt.TASK_ID in (select at.ID from LP_ACCEPTANCE_TASK at where at.APPLY_ID = ?))";
		return this.getJdbcTemplate().queryForList(sql, param);
	}
}
