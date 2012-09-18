package com.cabletech.linepatrol.drill.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaBean;
import org.springframework.stereotype.Repository;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.module.DrillTaskCon;

@Repository
public class DrillTaskConDao extends HibernateDao<DrillTaskCon, String> {

	public DrillTaskCon saveDrillTaskCon(DrillTaskCon drillTaskCon) {
		this.save(drillTaskCon);
		this.initObject(drillTaskCon);
		return drillTaskCon;
	}

	/**
	 * 改变演练任务与代维关系状态
	 * 
	 * @param contractorId
	 *            ：代维单位ID
	 * @param taskId
	 *            ：演练任务ID
	 * @param state
	 *            ：状态
	 */
	public void setStateByContractorIdAndTaskId(String contractorId,
			String taskId, String state) {
		String hql = "from DrillTaskCon drillTaskCon where drillTaskCon.drillId=? and drillTaskCon.contractorId=?";
		logger
				.info("******contractorId:" + contractorId + "**taskId:"
						+ taskId);
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId, contractorId });
		if (list != null && !"".equals(list)) {
			DrillTaskCon drillTaskCon = (DrillTaskCon) list.get(0);
			drillTaskCon.setState(state);
			this.save(drillTaskCon);
		} else {
			logger.error("查询list为空！");
		}
	}

	public String getIdByConIdAndTaskId(String taskId, String conId) {
		String hql = "from DrillTaskCon drillTaskCon where drillTaskCon.drillId=? and drillTaskCon.contractorId=?";
		logger.info("******contractorId:" + conId + "**taskId:" + taskId);
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId, conId });
		if (list != null && !"".equals(list)) {
			DrillTaskCon drillTaskCon = (DrillTaskCon) list.get(0);
			return drillTaskCon.getId();
		} else {
			logger.error("查询list为空！");
			return "";
		}
	}

	public List getIdsByConIdAndTaskId(String taskId) {
		String hql = "from DrillTaskCon drillTaskCon where drillTaskCon.drillId=?";
		logger.info("********taskId:" + taskId);
		List list = this.getHibernateTemplate().find(hql,
				new String[] { taskId });
		return list;
	}

	public String getUserStrByConId(String conId, String conUser) {
		String userIds = "";
		if (conUser != null && !"".equals(conUser)) {
			String[] conUsers = conUser.split(",");
			for (int i = 0; i < conUsers.length; i++) {
				String[] conUserOne = conUsers[i].split(";");
				if (conUserOne[0].equals(conId)) {
					userIds += conUserOne[1] + ",";
				}
			}
		}
		if (!"".equals(userIds)) {
			userIds = userIds.substring(0, userIds.length() - 1);
		}
		return userIds;
	}

	public void delteTaskCon(String taskId, String conId) {
		String hql = "from DrillTaskCon taskCon where taskCon.drillId=? and taskCon.contractorId=?";
		List list = this.find(hql, taskId, conId);
		if (list != null && list.size() > 0) {
			DrillTaskCon drillTaskCon = (DrillTaskCon) list.get(0);
			String id = drillTaskCon.getId();
			this.delete(id);
		}
	}

	public void deleteTaskConByTaskId(String taskId) {
		List list = this.findBy("drillId", taskId);
		if (list != null && list.size() > 0) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				DrillTaskCon drillTaskCon = (DrillTaskCon) iterator.next();
				this.delete(drillTaskCon);
			}
		}
	}

	public String getConIdByPlanId(String planId) {
		String conId = "";
		String sql = "select con.id con_id from lp_drilltask_con con, lp_drillplan plan where con.contractor_id=plan.contractor_id and con.drill_id=plan.task_id and plan.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, planId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	public String getConIdBySummaryId(String summaryId) {
		String conId = "";
		String sql = "select con.id con_id from lp_drilltask_con con, lp_drillplan plan,lp_drillsummary summary "
				+ "where con.contractor_id=plan.contractor_id and con.drill_id=plan.task_id and plan.id=summary.plan_id and summary.id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, summaryId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	public String getConIdByTaskIdAndConId(String taskId, String contractorId) {
		String conId = "";
		String sql = "select t.id con_id from lp_drilltask_con t where t.drill_id=? and t.contractor_id=?";
		List list = this.getJdbcTemplate().queryForBeans(sql, taskId,
				contractorId);
		if (list != null && list.size() > 0) {
			BasicDynaBean bean = (BasicDynaBean) list.get(0);
			conId = (String) bean.get("con_id");
		}
		return conId;
	}

	/**
	 * 由演练任务ID获得用户ID和name
	 * 
	 * @param taskId
	 *            ：演练任务ID
	 * @return
	 */
	public String[] getConUserIdsByTaskId(String taskId) {
		String[] userIds = new String[4];
		String userId = "";
		String userName = "";
		String phone = "";
		String deptId = "";
		String sql = "select u.userid,u.username,u.phone,u.deptid from lp_drilltask_con t,userinfo u where t.contractor_id=u.deptid and t.drill_id='"
				+ taskId + "' order by u.deptid";
		List list = getJdbcTemplate().queryForBeans(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				userId += (String) bean.get("userid") + ",";
				phone += (String) bean.get("phone") + ",";
				userName += (String) bean.get("username") + ",";
				deptId += (String) bean.get("deptid") + ",";
			}
		}
		if (userId != null && !"".equals(userId)) {
			userId = userId.substring(0, userId.length() - 1);
		}
		if (phone != null && !"".equals(phone)) {
			phone = phone.substring(0, phone.length() - 1);
		}
		if (userName != null && !"".equals(userName)) {
			userName = userName.substring(0, userName.length() - 1);
		}
		if (deptId != null && !"".equals(deptId)) {
			deptId = deptId.substring(0, deptId.length() - 1);
		}
		userIds[0] = userId;
		userIds[1] = phone;
		userIds[2] = userName;
		userIds[3] = removeDuplicateData(deptId);
		return userIds;
	}

	/**
	 * 去掉重复的数据
	 * 
	 * @param str
	 * @return
	 */
	private String removeDuplicateData(String str) {
		List list = new ArrayList();
		String returnStr = "";
		if (str != null && !"".equals(str)) {
			String[] strArray = str.split(",");
			for (int i = 0; i < strArray.length; i++) {
				String value = strArray[i];
				if (list.contains(value)) {
					continue;
				} else {
					list.add(value);
				}
			}
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnStr += list.get(i) + ",";
			}
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	/**
	 * 由代维单位ID获得该代维单位下的所有用户
	 * 
	 * @param conId
	 * @return
	 */
	public String getUserIdByConId(String conId) {
		String userId = "";
		String sql = "select t.userid from userinfo t where t.deptid=?";
		List list = getJdbcTemplate().queryForBeans(sql, conId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				userId += (String) bean.get("userid") + ",";
			}
		}
		if (!"".equals(userId)) {
			userId = userId.substring(0, userId.length() - 1);
		}
		return userId;
	}
}
