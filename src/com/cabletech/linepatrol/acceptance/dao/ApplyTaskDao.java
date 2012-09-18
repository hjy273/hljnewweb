package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.ApplyTask;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;

@Repository
public class ApplyTaskDao extends HibernateDao<ApplyTask, String> {
	
	public boolean isExistTask(String id, String contractorId){
		return getNevelRecordTasksList(id, contractorId).isEmpty() ? false : true;
	}
	
	public List<ApplyTask> getNevelRecordTasksList(String id, String contractorId){
		String hql = "from ApplyTask a where a.applyId = ? and a.contractorId = ? and a.isComplete = 0";
		return find(hql, id, contractorId);
	}
	
	public List<ApplyTask> getRecordTasksList(String id, String contractorId){
		String hql = "from ApplyTask a where a.applyId = ? and a.contractorId = ? and a.isComplete = 1";
		return find(hql, id, contractorId);
	}
	
	public List<ApplyTask> getAllRecordTasksList(String id, String contractorId){
		String hql = "from ApplyTask a where a.applyId = ? and a.contractorId = ?";
		return find(hql, id, contractorId);
	}
	
	public List<ApplyTask> getTasksList(String id){
		String hql = "from ApplyTask a where a.applyId = ?";
		return find(hql, id);
	}
	
	public ApplyTask getApplyTaskFromPipeId(String pipeId){
		String hql = "from ApplyTask at where at.id in (select pt.taskId from PipeTask pt where pt.pipeId = ? )";
		return this.findUnique(hql, pipeId);
	}
	
	public ApplyTask getApplyTaskFromCableId(String cableId){
		String hql = "from ApplyTask at where at.id in (select ct.taskId from CableTask ct where ct.cableId = ? )";
		return this.findUnique(hql, cableId);
	}
	
	public void deleteTaskFromApplyId(String applyId){
		String hql = "delete from ApplyTask at where at.applyId = ?";
		batchExecute(hql, applyId);
	}

	public List findbyPipeConditon(String applyName, String resourceName,String contractorId) {
		String sql = "select task.id taskid,apply.id applyid,task.contractor_id contractorid,apply.name applyname," +
				" subtask.id staskid,res.id rsid,res.project_name project_name,res.PCPM pcmanager,res.pipe_address||'-'||res.pipe_route address," +
				" apply.code,apply.resource_type type,con.contractorname,res.isrecord" +
				" from LP_ACCEPTANCE_TASK task,Lp_Acceptance_ptask subtask,LP_ACCEPTANCE_PIPE res,LP_ACCEPTANCE_APPLY apply,contractorinfo con" +
				" where apply.id=task.apply_id and task.id = subtask.task_id and res.id=subtask.pipe_id and con.contractorid=task.contractor_id" +
				" and name like '%"+applyName+"%' and (pipe_route like '%"+resourceName+"%' or pipe_address like '%"+resourceName+"%') and rownum<15 and process_state='40'" ;
			if(!"".equals(contractorId)	){
				sql +=" and task.contractor_id='"+contractorId+"'";
			}
				
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List findbyCableConditon(String applyName, String resourceName,String contractorId) {
		String sql = "select task.id taskid,apply.id applyid,task.contractor_id contractorid,apply.name applyname," +
				" subtask.id staskid,res.id rsid,res.issuenumber project_name,res.PRCPM pcmanager,res.cable_NO||'-'||res.trunk address," +
				" apply.code,apply.resource_type type,con.contractorname,res.isrecord" +
				" from LP_ACCEPTANCE_TASK task,Lp_Acceptance_ctask subtask,LP_ACCEPTANCE_CABLE res,LP_ACCEPTANCE_APPLY apply,contractorinfo con" +
				" where apply.id=task.apply_id and task.id = subtask.task_id and res.id=subtask.cable_id and con.contractorid=task.contractor_id" +
				" and name like '"+applyName+"%' and (Cable_NO like '%"+resourceName+"%' or trunk like '%"+resourceName+"%') and rownum<15 and process_state='40'";
		if(!"".equals(contractorId)	){
			sql +=" and task.contractor_id='"+contractorId+"'";
		}
		return this.getJdbcTemplate().queryForList(sql);
	}

	public ApplyTask query4subtask(String taskId,String resourceType) {
		if(AcceptanceConstant.CABLE.equals(resourceType)){
			String hql = "from ApplyTask at where at.id in (select ct.taskId from CableTask ct where ct.taskId = ? )";
			return this.findUnique(hql, taskId);
		}else{
			String hql = "from ApplyTask at where at.id in (select pt.taskId from PipeTask pt where pt.taskId = ? )";
			return this.findUnique(hql, taskId);
		}
			
		
	}
}