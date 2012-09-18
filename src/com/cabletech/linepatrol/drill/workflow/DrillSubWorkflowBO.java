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
import com.cabletech.linepatrol.drill.dao.DrillPlanModifyDao;

@Service
public class DrillSubWorkflowBO extends BaseWorkFlow {
	public static final String DRILL_SUB_WORKFLOW = "drill_sub_workflow";
	public static final String CHANGE_DRILL_PROJ_TASK = "change_drill_proj_task";
	public static final String APPROVE_CHANGE_DRILL_PROJ_TASK = "approve_change_drill_proj_task";

	@Resource(name = "drillPlanModifyDao")
	private DrillPlanModifyDao drillPlanModifyDao;
	
	public DrillSubWorkflowBO(){
		super(DRILL_SUB_WORKFLOW);
	}
	/**
	 * ����ĳ���ض���������˲�ѯ���˿�����������ĳЩ����ʵ���б�
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @return List<T> ���ظ��˿�����������ĳЩ����ʵ���б�
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
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = drillPlanModifyDao.getAgentTask(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf("."))
								.equals(DRILL_SUB_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("modify_id"))) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean != null
							&& bean.get("modify_id").equals(tmpBean.get("modify_id"))) {
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
	
	public List queryForHandleListBeanDetail(String assignee, String condition,
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
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = drillPlanModifyDao.getAgentTaskOrg(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf("."))
								.equals(DRILL_SUB_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("modify_id"))) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				flag = true;
				for (int j = 0; resultList != null && j < resultList.size(); j++) {
					tmpBean = (DynaBean) resultList.get(j);
					if (bean != null
							&& bean.get("modify_id").equals(tmpBean.get("modify_id"))) {
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
			Task task = null;
			ProcessDefinitionQuery query;
			List<ProcessDefinition> list;
			ProcessDefinition pdf;
			String executionId;

			for (int i = 0; taskList != null && i < taskList.size(); i++) {
				task = (Task) taskList.get(i);
				String eid = task.getExecutionId();
				if (eid.equals(DRILL_SUB_WORKFLOW + "." + objectId)) {
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
	 * ���ݹ���������͹�����������Ϣ��ȡ��Ӧ��ʵ�����
	 * 
	 * @param task
	 *            Task ����������
	 * @param execution
	 *            Execution ������������Ϣ
	 * @return T ���ض�Ӧ��ʵ�����
	 */
	public Object getEntityObject(Task task, Execution execution) {
		return null;
	}
}
