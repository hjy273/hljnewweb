package com.cabletech.linepatrol.cut.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.cut.dao.CutDao;

@Service
public class CutWorkflowBO extends BaseWorkFlow {
	

	public static final String APPLY_TASK = "apply_task";
	public static final String APPLY_APPROVE_TASK = "apply_approve_task";
	public static final String WORK_TASK = "work_task";
	public static final String WORK_APPROVE_TASK = "work_approve_task";
	public static final String CHECK_TASK = "check_task";
	public static final String CHECK_APPROVE_TASK = "check_approve_task";
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String CUT_WORKFLOW = "linechange";

	@Resource(name = "cutDao")
	private CutDao dao;

	public CutWorkflowBO() {
		super(CUT_WORKFLOW);
	}
	
	/**
	 * ����ĳ���ض���������˲�ѯ���˿�����������ĳЩ����ʵ���б�
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @return List<T> ���ظ��˿�����������ĳЩ����ʵ���б�
	 */
	public List queryForHandleList(String assignee) {
		return getTaskList(assignee,"");
	}
	public Map<String,Task> toMap(List<Task> original){
		Map<String,Task> toMap = new HashMap<String,Task>();
		for(Task task:original){
			toMap.put(task.getExecutionId(), task);
		}
		return toMap;
	}
	public Map<String,Task> query4HandleTask(String assignee,String taskName){
		List<Task> taskList = super.findTasks(assignee, taskName);
		return toMap(taskList);
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
		List<?> taskList = super.findTasks(assignee, taskName);
		
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = dao.getList(condition);
		for (int i = 0; prevResultList != null && i < prevResultList.size(); i++) {
			bean = (DynaBean) prevResultList.get(i);
			flag = false;
			task = null;
			for (int j = 0; taskList != null && j < taskList.size(); j++) {
				task = (Task) taskList.get(j);
				executionId = task.getExecutionId();
				// execution = executionService.findExecutionById(executionId);
				if (executionId != null
						&& executionId.substring(0, executionId.indexOf("."))
								.equals(CUT_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("id"))) {
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
							&& bean.get("id").equals(tmpBean.get("id"))) {
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
		List<?> findTasks = this.findTasks(assignee, "");
		if (findTasks != null) {
			return findTasks.size();
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
				if (eid.equals(CUT_WORKFLOW + "." + objectId)) {
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
		// SendAcceptDept dept = dao.get(execution.getKey());
		// return dept;
		return null;
	}

	/**
	 * ���ݹ��������񡢹�����������Ϣ���û����ű�Ż�ȡ��Ӧ�Ķ���Bean
	 * 
	 * @param task
	 *            Task ����������
	 * @param execution
	 *            Execution ������������Ϣ
	 * @param condition
	 *            String ��ѯ����
	 * @return DynaBean ���ض�Ӧ�Ķ���Bean
	 */
	public DynaBean getEntityBean(Task task, Execution execution,
			String condition) {
		String keyId = execution.getKey();
		DynaBean bean;
		// TODO: cut.idΪLP_CUT������
		condition = condition + " and cut.id='" + keyId + "' ";
		List list;
		list = new ArrayList();
		// TODO: ���ò�ѯ������ҳ���dao��ѯ����
		list = dao.getList(condition);
		if (list != null && list.size() > 0) {
			bean = (DynaBean) list.get(0);
			bean.set("flow_state", task.getName());
			return bean;
		}
		return null;
	}
	
	public List getTaskList1(String assignee, String taskName) {
		List taskList =  taskService.createTaskQuery().processDefinitionId("hiddanger-1").candidate(assignee).activityName(taskName).list();
//		List taskList =  taskService.createTaskQuery().processDefinitionId("linechange-1").candidate(assignee).activityName(taskName).list();
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderDesc(
				ProcessDefinitionQuery.PROPERTY_VERSION).processDefinitionName("overhaul").list();
		
		return taskList;
	}
}
