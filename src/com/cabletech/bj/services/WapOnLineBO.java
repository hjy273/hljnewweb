package com.cabletech.bj.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.dao.FrameCommonDao;
import com.cabletech.commons.base.BaseBisinessObject;

@Service
public class WapOnLineBO extends BaseBisinessObject {
	private FrameCommonDao dao = new FrameCommonDao();

	/**
	 * 获得在线巡检员
	 * 
	 * @param user
	 * @return
	 */
	public String getOnlinePatrolman(UserInfo user) {
		String deptname = user.getDeptName();
		String deptid = user.getDeptID();
		List contracts = dao.getCons(user, " order by contractorid desc ");
		List patrolGroups;
		List sims;
		StringBuffer mobileHTML = new StringBuffer();
		DynaBean contractor;
		DynaBean patrolGroup;
		DynaBean sim;
		String contractorId;
		String contractorName;
		String patrolGroupId;
		String patrolGroupName;
		String simId;
		String holder;
		String activeTime;
		int departAllSimNum = 0;
		int contractorAllSimNum = 0;
		int patrolGroupAllSimNum = 0;
		int departOnlineSimNum = 0;
		int contractorOnlineSimNum = 0;
		int patrolGroupOnlineSimNum = 0;
		Stack stack = new Stack();
		Map departMap = new HashMap();
		Map contractorMap = new HashMap();
		Map patrolGroupMap = new HashMap();
		Map simMap = new HashMap();
		for (int i = 0; contracts != null && i < contracts.size(); i++) {
			contractorMap = new HashMap();
			contractorAllSimNum = 0;
			contractorOnlineSimNum = 0;
			contractor = (DynaBean) contracts.get(i);
			contractorId = (String) contractor.get("contractorid");
			contractorName = (String) contractor.get("contractorname");
			patrolGroups = dao.getPatrolGroup(contractorId, " order by patrolid desc ");
			for (int j = 0; patrolGroups != null && j < patrolGroups.size(); j++) {
				patrolGroupMap = new HashMap();
				patrolGroupAllSimNum = 0;
				patrolGroupOnlineSimNum = 0;
				patrolGroup = (DynaBean) patrolGroups.get(j);
				patrolGroupId = (String) patrolGroup.get("patrolid");
				patrolGroupName = (String) patrolGroup.get("patrolname");
				sims = dao.getPatrolSim(patrolGroupId);
				patrolGroupAllSimNum = dao.getPatrolSimNUM(patrolGroupId);
				for (int k = 0; sims != null && k < sims.size(); k++) {
					simMap = new HashMap();
					sim = (DynaBean) sims.get(k);
					simId = (String) sim.get("simid");
					holder = (String) sim.get("holder");
					activeTime = (String) sim.get("activetime");
					patrolGroupOnlineSimNum++;
					stack.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (holder == null ? "" : holder) + "(" + simId
							+ ")-" + activeTime + "<br />");
				}
				contractorOnlineSimNum += patrolGroupOnlineSimNum;
				stack.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + patrolGroupName + "(" + patrolGroupOnlineSimNum + "/"
						+ patrolGroupAllSimNum + ")<br />");
			}
			contractorAllSimNum = dao.getPatrolSimNumByConid(contractorId);
			departAllSimNum += contractorAllSimNum;
			departOnlineSimNum += contractorOnlineSimNum;
			stack.push("&nbsp;&nbsp;&nbsp;&nbsp;" + contractorName + "(" + contractorOnlineSimNum + "/" + contractorAllSimNum
					+ ")<br />");
		}
		stack.push(deptname + "(" + departOnlineSimNum + "/" + departAllSimNum + ")<br />");
		while (stack != null && !stack.isEmpty()) {
			mobileHTML.append(stack.pop());
		}
		return mobileHTML.toString();
	}
}
