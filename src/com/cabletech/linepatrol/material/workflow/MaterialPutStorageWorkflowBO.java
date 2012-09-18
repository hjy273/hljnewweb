package com.cabletech.linepatrol.material.workflow;

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
import com.cabletech.linepatrol.material.dao.MaterialApplyDao;

@Service
@Transactional
public class MaterialPutStorageWorkflowBO extends BaseWorkFlow {
	public static final String MATERIAL_PUT_STORAGE_WORKFLOW = "material";
	public static final String APPLY_TASK = "apply_task";
	public static final String APPLY_APPROVE_TASK = "apply_approve_task";
	public static final String PUT_STORAGE_TASK = "put_storage_task";
	public static final String PUT_STORAGE_CONFIRM_TASK = "put_storage_confirm_task";

	@Resource(name = "materialApplyDao")
	private MaterialApplyDao dao;

	public MaterialPutStorageWorkflowBO(){
		super(MATERIAL_PUT_STORAGE_WORKFLOW);
	}
	/**
	 * ����ĳ���ض���������˲�ѯ���˿�����������ĳЩ����ʵ���б�
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param taskName
	 * @return List<T> ���ظ��˿�����������ĳЩ����ʵ���б�
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
		String keyId;
		boolean flag = false;
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		List prevResultList = new ArrayList();
		prevResultList = dao.queryList(condition);
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
								.equals(MATERIAL_PUT_STORAGE_WORKFLOW)) {
					keyId = executionId.substring(executionId.indexOf(".") + 1);
					if (keyId != null && keyId.equals(bean.get("id"))) {
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
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿�����������ĳЩ����Bean�б�
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param condition
	 *            String ��ѯ����
	 * @param taskName
	 * @return List<DynaBean>���ظ��˿�����������ĳЩ����Bean�б�
	 */
	public List queryForNoDistinctHandleListBean(String assignee,
			String condition, String taskName) {
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
		prevResultList = dao.queryList(condition);
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
								.equals(MATERIAL_PUT_STORAGE_WORKFLOW)) {
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
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿����������Ķ�������
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param condition
	 *            String ��ѯ����
	 * @return int �����������Ķ�������
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
	 * ����ĳ���ض���������˺�ĳ���ض������Ų�ѯ���˿����������ض�����Ĺ���������
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param objectId
	 *            String ĳ���ض�������
	 * @param taskName
	 * @return Task ���ظ��˿����������ض�����Ĺ���������
	 */
	public Task getHandleTaskForId(String assignee, String objectId,
			String taskName) {
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
				// String eid = task.getExecutionId();
				// if(eid.equals(TROUBLE_WORKFLOW+"."+objectId)){
				// System.out.println("true:"+eid +"  :"+objectId);
				// return task;
				// }else{
				// System.out.println("false:"+eid +"  :"+objectId);
				// return null;
				// }
				execution = executionService.findExecutionById(task
						.getExecutionId());
				query = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(execution.getProcessDefinitionId());
				list = query.list();
				for (int j = 0; list != null && j < list.size(); j++) {
					pdf = list.get(j);
					System.out.println(pdf.getKey());
					if (pdf.getKey().equals(MATERIAL_PUT_STORAGE_WORKFLOW)) {
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
