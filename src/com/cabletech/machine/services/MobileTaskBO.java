package com.cabletech.machine.services;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.machine.beans.MobileTaskBean;
import com.cabletech.machine.dao.MobileTaskDAO;

public class MobileTaskBO {
	private MobileTaskDAO dao = new MobileTaskDAO();

	public String addMobileTask(MobileTaskBean bean) {
		return dao.addMobileTask(bean);
	}

	public List doQuery(String condition, UserInfo userinfo) {
		return dao.doQuery(condition, userinfo);
	}

	public boolean delMobileTask(String tid) {
		return dao.delMobileTask(tid);
	}

	public boolean modMobileTask(String tid, MobileTaskBean bean) {
		return dao.modMobileTask(tid, bean);
	}

	public List getAllTaskForSign(UserInfo userinfo) {
		return dao.getAllTaskForSign(userinfo);
	}

	public List showTaskForRestore(UserInfo userinfo) {
		return dao.showTaskForRestore(userinfo);
	}

	public boolean modTaskState(String state, String tid) {
		return dao.modTaskState(state, tid);
	}

	public List getUserByConId(String conid) {
		return dao.getUserByConId(conid);
	}

	public List getMobileUser() {
		return dao.getMobileUser();
	}

	public List getEqu(String layer, String conid) {
		return dao.getEqu(layer, conid);
	}

	public List getTaskForCon(UserInfo userinfo) {
		return dao.getAllTaskForSign(userinfo);
	}

	public MobileTaskBean getOneInfo(String tid) {
		return dao.getOneInfo(tid);
	}

	public List doQueryForRestore(String condition, UserInfo userinfo) {
		return dao.doQueryForRestore(condition, userinfo);
	}
	
	public List getTastForCheck() {
		return dao.getTastForCheck();
	}
	
}
