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
	 * ����ĳ���ض���������˺�ĳ���ض������Ų�ѯ���˿����������ض�����Ĺ���������
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param objectId
	 *            String ĳ���ض�������
	 * @return Task ���ظ��˿����������ض�����Ĺ���������
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
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿�����������ĳЩ����Bean�б�
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param condition
	 *            String ��ѯ����
	 * @param taskName
	 * @return List<DynaBean>���ظ��˿�����������ĳЩ����Bean�б�
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
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿����������Ķ�������
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param condition
	 *            String ��ѯ����
	 * @param taskName
	 * @return int �����������Ķ�������
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
