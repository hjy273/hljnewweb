package com.cabletech.linepatrol.maintenance.workflow;

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
import com.cabletech.linepatrol.maintenance.dao.TestPlanDAO;

@Service
public class MaintenanceWorkflowBO extends BaseWorkFlow {
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String CREATE_PLAN_TASK = "create_test_plan_task";
	public static final String APPROVE_PLAN_TASK = "approve_test_plan_task";
	public static final String RECORD_TASK = "record_test_data_task";
	public static final String APPROVE_DATA_TASK = "approve_test_data_task";
	public static final String MAINTENANCE_WORKFLOW = "maintence";

	@Resource(name = "testPlanDAO")
	private TestPlanDAO dao;

	public MaintenanceWorkflowBO(){
		super(MAINTENANCE_WORKFLOW);
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
			Task task;
			ProcessDefinitionQuery query;
			List<ProcessDefinition> list;
			ProcessDefinition pdf;
			String executionId;
			for (int i = 0; taskList != null && i < taskList.size(); i++) {
				task = (Task) taskList.get(i);
				execution = executionService.findExecutionById(task
						.getExecutionId());
				query = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(execution.getProcessDefinitionId());
				list = query.list();
				for (int j = 0; list != null && j < list.size(); j++) {
					pdf = list.get(j);
					System.out.println(pdf.getKey());
					if (pdf.getKey().equals(MAINTENANCE_WORKFLOW)) {
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
		DynaBean bean;
		DynaBean tmpBean;
		String keyId;
		boolean flag = false;
		List resultList = new ArrayList();
		// List taskList = taskService.findPersonalTasks(assignee);
		// if (taskList == null || taskList.size() < 1) {
		// taskList = taskService.findGroupTasks(assignee);
		// if (taskList == null || taskList.size() < 1) {
		// return resultList;
		// }
		// }
		List taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = dao.getWaitWork("");
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf("."))
								.equals(MAINTENANCE_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("id"))) {
						flag = true;
						break;
					}
				}
			}
			/*if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean != null
							&& bean.get("id").equals(tmpBean.get("id"))) {
						flag = false;
						break;
					}
				}*/
				if (flag) {
					bean.set("flow_state", task.getName());
					resultList.add(bean);
				}
			//}
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

}
