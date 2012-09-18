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
			Execution execution = executionService.findExecutionById(executionId);
			Object object = getEntityObject(task, execution);
			if (resultList != null && !resultList.contains(object) && object != null) {
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
			//��ø��û������̵�����
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
				//����¼ƻ�����
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
				//�����ֹ����
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
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿����������Ķ�������
	 * 
	 * @param assignee
	 *            String �ض����������
	 * @param condition
	 *            String ��ѯ����
	 * @param taskName
	 * @return int �����������Ķ�������
	 */
	public int queryForHandleNumber(String assignee, String condition, String taskName) {
		List list = queryForHandleListBean(assignee, condition, taskName);
		/*List list2 = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				BasicDynaBean bean = (BasicDynaBean) list.get(i);
				String spplanId = (String) bean.get("sp_end_id");
				// ������ƶ������ܽᣬ����Ѳ����ʱ��ҪС�ڵ�ǰʱ��
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
						bean.set("transact_state", "��ֹ�������");
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
