package com.cabletech.linepatrol.safeguard.workflow;

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
import com.cabletech.linepatrol.commons.dao.ReplyApproverDAO;
import com.cabletech.linepatrol.safeguard.dao.SafeguardTaskDao;

@Service
public class SafeguardWorkflowBO extends BaseWorkFlow {
	public static final String SAFEGUARD_WORKFLOW = "safeguard";
	public static final String CREATE_GUARD_PROJ_TASK = "create_guard_proj_task";
	public static final String APPROVE_GUARD_PROJ_TASK = "approve_guard_proj_task";
	public static final String GUARD_PLAN_EXECUTE_STATE = "guard_plan_execute_state";
	public static final String CREATE_GUARD_SUMMARY_TASK = "create_guard_summary_task";
	public static final String APPROVE_GUARD_SUMMARY_TASK = "approve_guard_summary_task";
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String SAFEGUARD_SUB_WORKFLOW = "safeguard_sub";
	public static final String SAFEGUARD_REPLAN_SUB = "safeguard_replan_sub";

	@Resource(name = "safeguardTaskDao")
	private SafeguardTaskDao dao;

	@Resource(name = "replyApproverDAO")
	private ReplyApproverDAO approverDAO;
	public SafeguardWorkflowBO(){
		super(SAFEGUARD_WORKFLOW );
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
		boolean flag = false;
		String keyId;
		List resultList = new ArrayList();
		List taskList = super.getTaskList(assignee, taskName);
		if (taskName != null && !"".equals(taskName)) {
			if (taskName.equals("create_guard_summary_task")) {
				taskList.addAll(super.getTaskList(assignee, "guard_plan_execute_state"));
				//taskList.addAll(super.getTaskList(assignee, "remake_guard_plan_task"));
			}
			if (taskName.equals("guard_plan_execute_state")) {
				taskList.addAll(super.getTaskList(assignee, "wait_replan_task"));
				taskList.addAll(super.getTaskList(assignee, "remake_guard_plan_task"));
			}
			if (taskName.equals("change_guard_plan_approve_task")) {
				taskList.addAll(super.getTaskList(assignee, "remake_guard_plan_approve_task"));
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
			//获得该用户主流程的任务
			if (bean.get("sp_end_id") == null || bean.get("sp_end_id").equals("")) {
				for (int j = 0; taskList != null && j < taskList.size(); j++) {
					task = (Task) taskList.get(j);
					executionId = task.getExecutionId();
					if (executionId != null
							&& executionId.substring(0, executionId.indexOf(".")).equals(SAFEGUARD_WORKFLOW)) {

						keyId = executionId.substring(executionId.indexOf(".") + 1);
						if (keyId != null && keyId.equals(bean.get("con_id"))) {
							flag = true;
							break;
						}
					}
				}
				if (flag) {
					flag = true;
					for (int j = 0; resultList != null && j < resultList.size(); j++) {
						tmpBean = (DynaBean) resultList.get(j);
						if (bean != null && bean.get("con_id").equals(tmpBean.get("con_id"))) {
							flag = false;
							break;
						}
					}
					if (flag) {
						bean.set("flow_state", task.getName());
						resultList.add(bean);
					}
				}
				//获得新计划任务
				if (bean.get("sp_id") != null && !"".equals(bean.get("sp_id"))) {
					for (int j = 0; taskList != null && j < taskList.size(); j++) {
						task = (Task) taskList.get(j);
						executionId = task.getExecutionId();
						if (executionId != null
								&& executionId.substring(0, executionId.indexOf(".")).equals(SAFEGUARD_REPLAN_SUB)) {

							keyId = executionId.substring(executionId.indexOf(".") + 1);
							if (keyId != null && keyId.equals(bean.get("sp_id"))) {
								flag = true;
								break;
							}
						}
					}
					if (flag) {
						flag = true;
						for (int j = 0; resultList != null && j < resultList.size(); j++) {
							tmpBean = (DynaBean) resultList.get(j);
							if (bean != null && bean.get("sp_id").equals(tmpBean.get("sp_id"))) {
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
			} else {
				//获得终止任务
				for (int j = 0; taskList != null && j < taskList.size(); j++) {
					task = (Task) taskList.get(j);
					executionId = task.getExecutionId();
					if (executionId != null
							&& executionId.substring(0, executionId.indexOf(".")).equals(SAFEGUARD_SUB_WORKFLOW)) {
						keyId = executionId.substring(executionId.indexOf(".") + 1);
						if (keyId != null && keyId.equals(bean.get("sp_end_id"))) {
							flag = true;
							break;
						}
					}
				}
				if (flag) {
					flag = true;
					for (int j = 0; resultList != null && j < resultList.size(); j++) {
						tmpBean = (DynaBean) resultList.get(j);
						if (bean != null && bean.get("sp_end_id").equals(tmpBean.get("sp_end_id"))) {
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
	public int queryForHandleNumber(String assignee, String condition, String taskName) {
		List list = queryForHandleListBean(assignee, condition, taskName);
		/*List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String spplanId = (String) bean.get("sp_end_id");
				// 如果是制定保障总结，则特巡结束时间要小于当前时间
				if("".equals(spplanId) || spplanId == null){
					Object planId = bean.get("plan_id");
					Object summaryId = bean.get("summary_id");
					if (summaryId != null) {
						boolean read = approverDAO.isReadOnly((String) summaryId,
								assignee, SafeguardConstant.LP_SAFEGUARD_SUMMARY);
						bean.set("isread", "" + read);
						if(read){
							if(judgeFinishRead((String)summaryId, SafeguardConstant.LP_SAFEGUARD_SUMMARY, assignee)){
								list2.add(bean);
							}
						}else{
							list2.add(bean);
						}
						continue;
					}
					if (planId != null) {
						boolean read = approverDAO.isReadOnly((String) planId,
								assignee, SafeguardConstant.LP_SAFEGUARD_PLAN);
						bean.set("isread", "" + read);
						if(read){
							if(judgeFinishRead((String)planId, SafeguardConstant.LP_SAFEGUARD_PLAN, assignee)){
								list2.add(bean);
							}
						}else{
							list2.add(bean);
						}
						continue;
					}
					bean.set("isread", "false");
					list2.add(bean);
				}else{
					boolean read = approverDAO.isReadOnly(spplanId,
							assignee, SafeguardConstant.LP_SPECIAL_ENDPLAN);
					bean.set("isread", "" + read);
					if(judgeFinishRead(spplanId, SafeguardConstant.LP_SPECIAL_ENDPLAN, assignee)){
						bean.set("transact_state", "终止方案审核");
						list2.add(bean);
					}
				}
			}
		}*/
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	/*private boolean judgeFinishRead(String objectId,
			String objectType, String userId) {
		ReplyApprover approver;
		boolean flag = true;
		List<ReplyApprover> approverList = approverDAO.getApprovers(objectId,
				objectType);
		for (int i = 0; approverList != null && i < approverList.size(); i++) {
			approver = approverList.get(i);
			if (approver != null && userId.equals(approver.getApproverId())) {
				if (CommonConstant.FINISH_READED.equals(approver
						.getFinishReaded())) {
					flag = false;
				} else {
					flag = true;
				}
			}
		}
		//logger.info("*******flag:::" + flag + " objectId::" + objectId + " objectType::" + objectType);
		return flag;
	}*/

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
				if (eid.equals(objectId)) {
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
