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
	public DynaBean getEntityBean(Task task, Execution execution,
			String condition) {
		String keyId = execution.getKey();
		DynaBean bean;
		//cut.id为LP_CUT的主键
		condition = condition + " and remedy.id='" + keyId + "' ";
		List list;
		list = new ArrayList();
		//调用查询待办割接页面的dao查询方法
		list = dao.queryList(condition);
		if (list != null && list.size() > 0) {
			bean = (DynaBean) list.get(0);
			bean.set("flow_state", task.getName());
			return bean;
		}
		return null;
	}
	
	/**
	 * 保存流程历史
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
