package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.PipeTask;

@Repository
public class PipeTaskDao extends HibernateDao<PipeTask, String> {
	public PipeTask loadPipeTask(String taskId){
		String hql = "from PipeTask ct where ct.taskId = ?";
		return findUnique(hql, taskId);
	}
	
	public List<PipeTask> getPipeTasksFromPipeId(String pipeId){
		String hql = "from PipeTask pt where pt.pipeId = ?";
		return find(hql, pipeId);
	}
	
	public PipeTask getPipeTaskFromPipeId(String pipeId){
		String hql = "from PipeTask pt where pt.pipeId = ?";
		return findUnique(hql, pipeId);
	}
	
	public PipeTask loadPipeTask(String taskId, String pipeId){
		String hql = "from PipeTask ct where ct.taskId = ? and ct.pipeId = ?";
		return findUnique(hql, taskId, pipeId);
	}
	
	public PipeTask loadPipeTaskFromApplyId(String applyId, String pipeId){
		String hql = "from PipeTask ct where ct.taskId in (from ApplyTask at where at.applyId = ?) and ct.pipeId = ?";
		return findUnique(hql, applyId, pipeId);
	}
	
	public void deleteTaskFromApplyId(String applyId){
		String hql = "delete from PipeTask pt where pt.taskId in (select at.id from ApplyTask at where at.applyId = ?)";
		batchExecute(hql, applyId);
	}
}
