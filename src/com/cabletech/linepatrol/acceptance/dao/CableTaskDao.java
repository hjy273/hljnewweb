package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.CableTask;

@Repository
public class CableTaskDao extends HibernateDao<CableTask, String> {
	public CableTask loadCableTask(String taskId){
		String hql = "from CableTask ct where ct.taskId = ?";
		return findUnique(hql, taskId);
	}
	
	//�����ʱ����һ�Զ�
	public List<CableTask> getCableTasksFromCableId(String cableId){
		String hql = "from CableTask ct where ct.cableId = ?";
		return find(hql, cableId);
	}
	
	//��׼����һ��һ
	public CableTask getCableTaskFromCableId(String cableId){
		String hql = "from CableTask ct where ct.cableId = ?";
		return findUnique(hql, cableId);
	}
	
	public CableTask loadCableTaskFromApplyId(String applyId, String cableId){
		String hql = "from CableTask ct where ct.taskId in (select at.id from ApplyTask at where at.applyId = ?) and ct.cableId = ?";
		return findUnique(hql, applyId, cableId);
	}
	
	public void deleteTaskFromApplyId(String applyId){
		String hql = "delete from CableTask ct where ct.taskId in (select at.id from ApplyTask at where at.applyId = ?)";
		batchExecute(hql, applyId);
	}
}
