package com.cabletech.commons.process.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.dao.ProcessHistoryDao;
import com.cabletech.commons.process.module.ProcessHistory;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.ctf.workflow.AbstractProcessService;
import com.cabletech.linepatrol.commons.dao.UserInfoDAOImpl;
import com.cabletech.linepatrol.material.service.ConditionGenerate;

@Service
@Transactional
public class ProcessHistoryBO extends EntityManager<ProcessHistory, String> {
	@Resource(name = "processHistoryDao")
	private ProcessHistoryDao processHistoryDao;
	@Resource(name = "userInfoDao")
	private UserInfoDAOImpl userInfoDao;

	/**
	 * 保存流程历史信息
	 * 
	 * @param executionId
	 * @param task
	 * @param nextOperateUserId
	 * @param operateUserId
	 * @param objectId
	 * @param objectType
	 */
	public void saveProcessHistory(String executionId, Task task,
			String nextOperateUserId, String operateUserId, String objectId,
			String objectType) {
		ProcessHistory history = new ProcessHistory();
		history.setExecutionId(executionId);
		if (task != null) {
			history.setHandledTaskId(task.getId());
			history.setHandledTaskName(task.getName());
		} else {
			history.setHandledTaskId("");
			history.setHandledTaskName("");
		}
		history.setHandledTime(new Date());
		history.setNextOperateUserId(nextOperateUserId);
		history.setObjectId(objectId);
		history.setObjectType(objectType);
		history.setOperateUserId(operateUserId);
		processHistoryDao.save(history);
	}

	/**
	 * 保存流程历史信息
	 * 
	 * @param bean
	 */
	public void saveProcessHistory(ProcessHistoryBean bean){
		ProcessHistory history = new ProcessHistory();
		BeanUtil.copyProperties(bean, history);
		history.setHandledTime(new Date());
		processHistoryDao.save(history);
	}

	/**
	 * 执行显示流程处理过程列表信息
	 * 
	 * @param objectType
	 * @param objectId
	 * @param userInfo
	 * @return
	 */
	public List queryProcessHistoryList(String objectType, String objectId,
			UserInfo userInfo) {
		String orderString = " order by history.id,history.handled_time ";
		String condition = "";
		// if
		// (ConditionGenerate.CITY_CONTRACTOR_USER.equals(userInfo.getType())) {
		// condition += " and ( ";
		// condition += " (u.deptid='" + userInfo.getDeptID() + "' ";
		// condition += " and u.deptype='2') ";
		// condition += " or ";
		// condition += " (u.deptype='1') ";
		// condition += " ) ";
		// }
		// if (ConditionGenerate.CITY_MOBILE_USER.equals(userInfo.getType())) {
		// condition += " and u.regionid='" + userInfo.getRegionid() + "' ";
		// }
		condition += " and object_id='" + objectId + "' ";
		condition += " and object_type='" + objectType + "'";
		List prevList = processHistoryDao.queryForList(condition, orderString);
		List list = processList(prevList);
		return list;
	}

	/**
	 * 执行查询之后的处理过程
	 * 
	 * @param prevList
	 * @return
	 */
	public List processList(List prevList) {
		List list = new ArrayList();
		List userList = userInfoDao.getUserList("");
		List departList = userInfoDao.getDepartList("");
		DynaBean bean;
		DynaBean tmpBean;
		String processTaskName = "";
		String realTaskName = "";
		String nextOperateUserIds;
		String[] nextOperateUserId;
		String nextOperateUserName = "";
		Map taskNameMap = new HashMap();
		for (int i = 0; prevList != null && i < prevList.size(); i++) {
			nextOperateUserName = "";
			bean = (DynaBean) prevList.get(i);
			if (bean != null) {
				nextOperateUserIds = (String) bean.get("next_operate_user_id");
				if (nextOperateUserIds != null) {
					if (nextOperateUserIds.indexOf(",") != -1) {
						nextOperateUserId = nextOperateUserIds.split(",");
						for (int k = 0; nextOperateUserId != null
								&& k < nextOperateUserId.length; k++) {
							for (int j = 0; userList != null
									&& j < userList.size(); j++) {
								tmpBean = (DynaBean) userList.get(j);
								if (tmpBean != null
										&& nextOperateUserId[k].equals(tmpBean
												.get("userid"))) {
									nextOperateUserName += (String) tmpBean
											.get("name");
									break;
								}
							}
							for (int j = 0; departList != null
									&& j < departList.size(); j++) {
								tmpBean = (DynaBean) departList.get(j);
								if (tmpBean != null
										&& nextOperateUserId[k].equals(tmpBean
												.get("departid"))) {
									nextOperateUserName += (String) tmpBean
											.get("departname");
									break;
								}
							}
							if (k != nextOperateUserId.length - 1) {
								nextOperateUserName += ",";
							}
						}
					} else {
						for (int j = 0; userList != null && j < userList.size(); j++) {
							tmpBean = (DynaBean) userList.get(j);
							if (tmpBean != null
									&& nextOperateUserIds.equals(tmpBean
											.get("userid"))) {
								nextOperateUserName = (String) tmpBean
										.get("name");
								break;
							}
						}
						for (int j = 0; departList != null && j < departList.size(); j++) {
							tmpBean = (DynaBean) departList.get(j);
							if (tmpBean != null
									&& nextOperateUserIds.equals(tmpBean
											.get("departid"))) {
								nextOperateUserName = (String) tmpBean
										.get("departname");
								break;
							}
						}
					}
				}
				bean.set("next_operate_user_name", nextOperateUserName);
				list.add(bean);
			}
		}
		return list;
	}

	@Override
	protected HibernateDao<ProcessHistory, String> getEntityDao() {
		// TODO Auto-generated method stub
		return null;
	}

}