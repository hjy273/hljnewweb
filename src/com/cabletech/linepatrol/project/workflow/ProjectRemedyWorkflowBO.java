package com.cabletech.linepatrol.project.workflow;

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

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

@Service
@Transactional
public class ProjectRemedyWorkflowBO extends BaseWorkFlow {
	public static final String APPLY_TASK = "remedy_apply_task";
	public static final String APPLY_APPROVE_TASK = "remedy_approve_task";
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String WORKFLOWNAME = "project";

	@Resource(name = "remedyApplyDao")
	private RemedyApplyDao dao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	public ProjectRemedyWorkflowBO(){
		super(WORKFLOWNAME);
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
	public List<ProjectRemedyApply> queryForHandleListBean(String assignee, String condition,
			String taskName) {
		List<ProjectRemedyApply> resultList = new ArrayList<ProjectRemedyApply>();
		List<Task> taskList = super.getTaskList(assignee, taskName);
		if (taskList == null || taskList.size() == 0) {
			return resultList;
		}
		for(Task task : taskList){
			String executionId = task.getExecutionId();
			if(executionId != null && executionId.substring(0, executionId.indexOf(".")).equals(WORKFLOWNAME)){
				String id = executionId.substring(executionId.indexOf(".") + 1);
				ProjectRemedyApply apply = dao.viewOneApply(id);
				if(apply != null)
					apply.setFlowTaskName(task.getName());
					resultList.add(apply);
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
				if (eid.equals(WORKFLOWNAME + "." + objectId)) {
					System.out.println("true:" + eid + "  :" + objectId);
					return task;
				} else {
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
		//cut.idΪLP_CUT������
		condition = condition + " and remedy.id='" + keyId + "' ";
		List list;
		list = new ArrayList();
		//���ò�ѯ������ҳ���dao��ѯ����
		list = dao.queryList(condition);
		if (list != null && list.size() > 0) {
			bean = (DynaBean) list.get(0);
			bean.set("flow_state", task.getName());
			return bean;
		}
		return null;
	}
	
	/**
	 * ����������ʷ
	 */
	public void saveProcessHistory(UserInfo userInfo, ProjectRemedyApply apply, Task task, String nextObj, String transition, String infomation, String taskName){
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.initial(task, userInfo, WORKFLOWNAME+"."+apply.getId(), ModuleCatalog.PROJECT);
		processHistoryBean.setObjectId(apply.getId());
		processHistoryBean.setNextOperateUserId(nextObj);
		processHistoryBean.setHandledTaskName(taskName);
		processHistoryBean.setProcessAction(infomation);
		processHistoryBean.setTaskOutCome(transition);
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}
}
