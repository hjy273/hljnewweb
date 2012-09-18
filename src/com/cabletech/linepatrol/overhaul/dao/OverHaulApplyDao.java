package com.cabletech.linepatrol.overhaul.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;

@Repository
public class OverHaulApplyDao extends HibernateDao<OverHaulApply, String> {
	public OverHaulApply getFromProcessInstanceId(String processInstanceId){
		return this.findByUnique("processInstanceId", processInstanceId);
	}
	
	public List<OverHaulApply> getPassedApplyWithOverHaul(String taskId){
		String hql = "from OverHaulApply o where o.taskId = ? and o.state = 40 order by o.createTime asc";
		return this.find(hql, taskId);
	}
	
	public List<OverHaulApply> getApply(UserInfo userInfo, String taskId){
		StringBuffer sb = new StringBuffer();
		sb.append("from OverHaulApply o where o.taskId = ?");
		if(userInfo.getDeptype().equals("2")){
			sb.append(" and o.contractorId = '");
			sb.append(userInfo.getDeptID());
			sb.append("'");
		}
		sb.append(" order by o.createTime asc");
		return find(sb.toString(), taskId);
	}
	
	/**
	 * 改变大修项目申请状态
	 * @param applyId
	 * @param state
	 */
	public void changeApplyStateById(String applyId, String state) {
		OverHaulApply oha = findByUnique("id", applyId);
		oha.setState(state);
		save(oha);
	}
}
