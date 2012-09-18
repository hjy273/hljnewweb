package com.cabletech.linepatrol.drill.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.drill.dao.DrillTaskDao;

@Service
public class DrillWorkflowBO extends BaseWorkFlow {
	public static final String DRILL_WORKFLOW = "drill";
	public static final String CREATE_DRILL_PROJ_TASK = "create_drill_proj_task";
	public static final String APPROVE_DRILL_PROJ_TASK = "approve_drill_proj_task";
	public static final String CHANGE_DRILL_PROJ_TASK = "change_drill_proj_task";
	public static final String APPROVE_CHANGE_DRILL_PROJ_TASK = "approve_change_drill_proj_task";
	public static final String CREATE_DRILL_SUMMARY_TASK = "create_drill_summary_task";
	public static final String APPROVE_DRILL_SUMMARY_TASK = "approve_drill_summary_task";
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String DRILL_SUB_WORKFLOW = "drill_sub_workflow";

	@Resource(name = "drillTaskDao")
	private DrillTaskDao dao;
	
	public DrillWorkflowBO(){
		super(DRILL_WORKFLOW);
	}
	/**
	 * 根据某个特定任务完成人查询该人可以完成任务的某些对象实体列表
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @return List<T> 返回该人可以完成任务的某些对象实体列表
	 */
	public List queryForHandleList(String assignee) {
		List resultList = new ArrayList();
		List taskList = taskService.findPersonalTasks(assignee);
		if (taskList == null || taskList.size() < 1) {
			taskList = taskService.findGroupTasks(assignee);
			if (taskList == null || taskList.size() < 1) {
				return resultList;
			}
		}
		Task task;
		for (int i = 0; taskList != null && i < taskList.size(); i++) {
			task = (Task) taskList.get(i);
			String executionId = task.getExecutionId();
			Execution execution = executionService
					.findExecutionById(executionId);
			Object object = getEntityObject(task, execution);
			if (resultList != null && !resultList.contains(object)
					&& object != null) {
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
	public List queryForHandleListBean(String assignee, String condition,
			String taskName) {
		Task task;
		String executionId;
		Execution execution;
		ProcessDefinitionQuery query;
		List<ProcessDefinition> list;
		ProcessDefinition pdf;
		DynaBean bean;
		DynaBean tmpBean;
		boolean flag = false;
		String keyId;
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskName != null && taskName.indexOf(",") != -1) {
			String[] taskNames = taskName.split(",");
			taskList = new ArrayList();
			for (int i = 0; taskNames != null && i < taskNames.length; i++) {
				if (taskNames[i] != null && !taskNames[i].equals("")) {
					taskList.addAll(super.getTaskList(assignee, taskNames[i]));
				}
			}
		}
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = dao.getAgentTask(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				if (executionId != null){
					if(executionId.substring(0, executionId.indexOf(".")).equals(DRILL_WORKFLOW)) {
						keyId = executionId.substring(executionId.indexOf(".") + 1);
						if (keyId != null && keyId.equals(bean.get("con_id"))) {
							flag = true;
							break;
						}
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if(bean != null){
						if (tmpBean.get("con_id").equals(bean.get("con_id"))) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					bean.set("flow_state", task.getName());
					resultList.add(bean);
				}
			}
			//子流程
			flag = false;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				if (executionId != null){
					if(executionId.substring(0, executionId.indexOf(".")).equals(DRILL_SUB_WORKFLOW)) {
						keyId = executionId.substring(executionId.indexOf(".") + 1);
						if (keyId != null && keyId.equals(bean.get("modify_id")) && bean.get("modify_id") != null) {
							flag = true;
							break;
						}
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean.get("modify_id").equals(tmpBean.get("modify_id"))) {
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
	 * 根据某个特定任务完成人和部门编号查询该人可以完成任务的对象数量
	 * 
	 * @param assignee
	 *            String 特定任务完成人
	 * @param condition
	 *            String 查询条件
	 * @param taskName
	 * @return int 可以完成任务的对象数量
	 */
	public int queryForHandleNumber(String assignee, String condition,
			String taskName) {
		List list = queryForHandleListBean(assignee, condition, taskName);
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
	 * @return Task 返回该人可以完成这个特定对象的工作流任务
	 */
	public Task getHandleTaskForId(String assignee, String objectId) {
		if (objectId != null && !objectId.equals("")) {
			List taskList = taskService.findPersonalTasks(assignee);
			if (taskList == null || taskList.size() < 1) {
				taskList = taskService.findGroupTasks(assignee);
				if (taskList == null || taskList.size() < 1) {
					return null;
				}
			}
			Execution execution;
			Task task = null;
			ProcessDefinitionQuery query;
			List<ProcessDefinition> list;
			ProcessDefinition pdf;
			String executionId;

			for (int i = 0; taskList != null && i < taskList.size(); i++) {
				task = (Task) taskList.get(i);
				String eid = task.getExecutionId();
				if (eid.equals(DRILL_WORKFLOW + "." + objectId)) {
					System.out.println("true:" + eid + "  :" + objectId);
					return task;
				} else {
					System.out.println("false:" + eid + "  :" + objectId);
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
		return null;
	}
}
