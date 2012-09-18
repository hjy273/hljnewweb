package com.cabletech.linepatrol.acceptance.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.acceptance.model.PayCable;

@Repository
public class PayCableDao extends HibernateDao<PayCable, String> {
	public PayCable getPayCableFromTaskId(String ctaskId){
		String hql ="select pc from PayCable pc where pc.taskId=?";
//		String hql ="select pc from PayCable pc,CableTask ct where pc.taskId=ct.id and ct.taskId=?";
		return findUnique(hql, ctaskId);
	}
	public List<PayCable> getPayCablesByTaskId(String taskId){
		String hql ="select pc from PayCable pc,CableTask ct where pc.taskId=ct.id and ct.taskId=?";
		return this.find(hql,taskId);
	}
	public PayCable getLastPayCable(String cableId){
		String hql = "from PayCable p where p.cableId = ? order by p.acceptanceTimes desc";
		List<PayCable> list = find(hql, cableId);
		return list == null || list.isEmpty() ? null : list.get(0);
	}
	
}
