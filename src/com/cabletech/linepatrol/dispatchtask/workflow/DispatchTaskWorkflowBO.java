package com.cabletech.linepatrol.dispatchtask.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.dispatchtask.dao.DispatchTaskDao;

@Service
@Transactional
public class DispatchTaskWorkflowBO extends BaseWorkFlow {
	public static final String START_TASK = "start";
	public static final String CANCEL_TASK = "any";
	public static final String SIGN_IN_TASK = "sign_in_task";
	public static final String REFUSE_CONFIRM_TASK = "refuse_confirm_task";
	public static final String TRANSFER_SIGN_IN_TASK = "tranfer_sign_in_task";
	public static final String REPLY_TASK = "reply_task";
	public static final String CHECK_TASK = "check_task";
	public static final String REFUSE_OUT_COME = "refuse";
	public static final String SIGN_IN_OUT_COME = "reply";
	public static final String TRANSFER_OUT_COME = "transfer_dispatch";
	public static final String DISPATCH_TASK_WORKFLOW = "dispatchtask";

	@Resource(name = "dispatchTaskDao")
	private DispatchTaskDao dao;
	public DispatchTaskWorkflowBO(){
		super(DISPATCH_TASK_WORKFLOW);
	}
	/**
	 * 根据某个特定任务完成人查询该人可以完成任务的某些对象实体列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param taskName
	 * @return List<T> 返回该人可以完成任务的某些对象实体列表
	 */
	public List queryForHandleList(String assignee, String taskName) {
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		Task task;
		for (int i = 0; taskList != null && i < taskList.size(); i++) {
			task = (Task) taskList.get(i);
			String executionId = task.getExecutionId();
			Execution execution = executionService.findExecutionById(executionId);
			Object object = getEntityObject(task, execution);
			if (resultList != null && !resultList.contains(object) && object != null) {
				resultList.add(object);
			}
		}

		return resultList;
	}

	/**
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的某些对象Bean列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @param taskName
	 * @return List<DynaBean>返回该人可以完成任务的某些对象Bean列表
	 */
	public List queryForHandleListBean(String assignee, String condition, String taskName) {
		Task task;
		String executionId;
		Execution execution;
		ProcessDefinitionQuery query;
		List<ProcessDefinition> list;
		ProcessDefinition pdf;
		DynaBean bean;
		DynaBean tmpBean;
		String keyId;
		boolean flag = false;
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = new ArrayList();
		prevResultList = dao.queryForList(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				// execution = executionService.findExecutionById(executionId);
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf(".")).equals(DISPATCH_TASK_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("subid"))) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean != null && bean.get("id").equals(tmpBean.get("id"))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					bean.set("flow_state", task.getName());
					resultList.add(bean);
				}
			}
		}
		return resultList;
	}

	/**
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的某些对象Bean列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @param taskName
	 * @return List<DynaBean>返回该人可以完成任务的某些对象Bean列表
	 */
	public List queryForNoDistinctHandleListBean(String assignee, String condition, String taskName) {
		Task task;
		String executionId;
		Execution execution;
		ProcessDefinitionQuery query;
		List<ProcessDefinition> list;
		ProcessDefinition pdf;
		DynaBean bean;
		DynaBean tmpBean;
		String keyId;
		boolean flag = false;
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = new ArrayList();
		prevResultList = dao.queryForList(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				// execution = executionService.findExecutionById(executionId);
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf(".")).equals(DISPATCH_TASK_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("subid"))) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				bean.set("flow_state", task.getName());
				resultList.add(bean);
			}
		}
		return resultList;
	}

	/**
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的对象数量
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @return int 可以完成任务的对象数量
	 */
	public int queryForHandleNumber(String assignee, String condition) {
		List list = queryForHandleListBean(assignee, condition, "");
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 根据某个特定任务完成人和某个特定对象编号查询该人可以完成这个特定对象的工作流任务
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param objectId
	 *            String 某个特定对象编号
	 * @param taskName
	 * @return Task 返回该人可以完成这个特定对象的工作流任务
	 */
	public Task getHandleTaskForId(String assignee, String objectId, String taskName) {
		if (objectId != null && !objectId.equals("")) {
			List taskList = super.getTaskList(assignee, taskName);
			if (taskList == null || taskList.size() == 0) {
				return null;
			}
			Execution execution;
			Task task;
			ProcessDefinitionQuery query;
			List<ProcessDefinition> list;
			ProcessDefinition pdf;
			String executionId;
			for (int i = 0; taskList != null && i < taskList.size(); i++) {
				task = (Task) taskList.get(i);
				execution = executionService.findExecutionById(task.getExecutionId());
				query = repositoryService.createProcessDefinitionQuery().processDefinitionId(
						execution.getProcessDefinitionId());
				list = query.list();
				for (int j = 0; list != null && j < list.size(); j++) {
					pdf = list.get(j);
					if (pdf.getKey().equals(DISPATCH_TASK_WORKFLOW)) {
						executionId = execution.getKey();
						if (objectId.equals(executionId)) {
							return task;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据工作流任务和工作流整体信息获取对应的实体对象
	 * 
	 * @param task
	 *            Task 工作流任务
	 * @param execution
	 *            Execution 工作流整体信息
	 * @return T 返回对应的实体对象
	 */
	public Object getEntityObject(Task task, Execution execution) {
		// SendAcceptDept dept = dao.get(execution.getKey());
		// return dept;
		return null;
	}

	/**
	 * 根据工作流任务、工作流整体信息和用户部门编号获取对应的对象Bean
	 * 
	 * @param task
	 *            Task 工作流任务
	 * @param execution
	 *            Execution 工作流整体信息
	 * @param condition
	 *            String 查询条件
	 * @return DynaBean 返回对应的对象Bean
	 */
	public DynaBean getEntityBean(Task task, Execution execution, String condition) {
		String keyId = execution.getKey();
		DynaBean bean;
		// condition = condition + " and unit.id='" + keyId + "' ";
		List list;
		list = new ArrayList();
		// list = dao.findWaitReplys(condition);
		if (list != null && list.size() > 0) {
			bean = (DynaBean) list.get(0);
			bean.set("flow_state", task.getName());
			return bean;
		}
		return null;
	}
}
