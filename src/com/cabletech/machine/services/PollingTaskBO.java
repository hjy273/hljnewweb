package com.cabletech.machine.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.cabletech.machine.beans.PollingTaskBean;
import com.cabletech.machine.dao.PollingTaskDAO;

public class PollingTaskBO {
	private PollingTaskDAO dao = new PollingTaskDAO();

	public boolean addPollingTaskForCoreAndSDH(PollingTaskBean bean) {
		return dao.addPollingTaskForCoreAndSDH(bean);
	}
	
	public boolean addPollingTaskForCoreAndSDH(List pollingTaskBeanList)
	{
		return dao.addPollingTaskForCoreAndSDH(pollingTaskBeanList);
	}
	public boolean addPollingTaskForMicro(List pollingTaskBeanList){
		return dao.addPollingTaskForMicro(pollingTaskBeanList);
	}
	public boolean addPollingTaskForMicro(PollingTaskBean bean) {
		return dao.addPollingTaskForMicro(bean);
	}
	
	public boolean addPollingTaskForFSO(PollingTaskBean bean) {
		return dao.addPollingTaskForFSO(bean);
	}
	
	public boolean addPollingTaskForFSO(List pollingTaskBeanList) {
		return dao.addPollingTaskForFSO(pollingTaskBeanList);
	}
	
	public List getEquTaskList(String id,String type,HttpSession session) {
		return dao.getEquTaskList(id, type,session);
	}
	
	public List backToPrePage(String sql) {
		return dao.backToPrePage(sql);
	}

	public boolean delPollingTask(String pid) {
		return dao.delPollingTask(pid);
	}

	public boolean modPollingTask(String pid, PollingTaskBean bean) {
		return dao.modPollingTask(pid, bean);
	}
	
	public boolean modEquState(String pid) {
		return dao.modEquState(pid);
	}
	
	public boolean judgeIsEnd(String tid) {
		return dao.judgeIsEnd(tid);
	}
	
	public List getEquTaskListForRestore(String id, String type, HttpSession session) {
		return dao.getEquTaskListForRestore(id, type, session);
	}
	
	public List getEquTaskListForCheck(String id, String type, HttpSession session) {
		return dao.getEquTaskListForCheck(id, type, session);
	}
	
	public boolean addCheck(String pid,String auditResult, String checkRemark) {
		return dao.addCheck(pid, auditResult, checkRemark);
	}
	
	public boolean judgeCheckIsEnd(String tid) {
		return dao.judgeCheckIsEnd(tid);
	}
	
	public PollingTaskBean getOneTaskInfo(String pid) {
		return dao.getOneTaskInfo(pid);
	}
	public List back(String sql) {
		return dao.back(sql);
	}
}
