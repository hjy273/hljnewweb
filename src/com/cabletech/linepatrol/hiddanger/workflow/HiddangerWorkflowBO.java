package com.cabletech.linepatrol.hiddanger.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.hiddanger.dao.HiddangerRegistDao;
import com.cabletech.linepatrol.hiddanger.model.HiddangerRegist;

@Service
public class HiddangerWorkflowBO extends BaseWorkFlow {

	public static final String CONFIRM_TASK = "confirm_task";
	public static final String SELF_PROCESS_TASK = "self_process_task";
	public static final String REPORT_TASK = "report_task";
	public static final String ADD_APPROVE_TASK = "add_approve_task";
	public static final String MAKE_PLAN_TASK="make_plan";
	public static final String MAKE_PLAN_APPROVE_TASK="plan_approve";
	public static final String END_PLAN_TASK="end_plan";
	public static final String END_PLAN_APPROVE_TASK="end_approve";
	public static final String CLOSE_TASK = "close_task";
	public static final String CLOSE_APPROVE_TASK = "close_approve_task";
	public static final String EVALUATE_TASK = "evaluate_task";
	public static final String HIDDANGER_WORKFLOW = "hiddanger";

	@Resource(name = "hiddangerRegistDao")
	private HiddangerRegistDao dao;
	
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	public HiddangerWorkflowBO(){
		super(HIDDANGER_WORKFLOW);
	}
	
	/**
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿�����������ĳЩ����Bean�б�
	 */
	public List<HiddangerRegist> queryForHandleList(String assignee,
			String taskName) {
		List<HiddangerRegist> resultList = new ArrayList<HiddangerRegist>();
		List<Task> taskList = getTaskList(assignee, taskName);

		if(taskList != null && !taskList.isEmpty()){
			for(Task task : taskList){
				String executionId = task.getExecutionId();
				if (executionId.substring(0, executionId.indexOf(".")).equals(HIDDANGER_WORKFLOW)){
					String id = executionId.substring(executionId.indexOf(".") + 1);
					HiddangerRegist regist = getRegist(id);
					if(regist != null){
						regist.setFlowTaskName(task.getName());
						resultList.add(regist);
					}
				}
			}
		}
		return resultList;
	}

	/**
	 * ����ĳ���ض���������˺Ͳ��ű�Ų�ѯ���˿����������Ķ�������
	 */
	public int queryForHandleNumber(String assignee, String condition, String taskName) {
		List<HiddangerRegist> list = queryForHandleList(assignee, taskName);
		return list == null ? 0 : list.size();
	}

	/**
	 * ����ĳ���ض���������˺�ĳ���ض������Ų�ѯ���˿����������ض�����Ĺ���������
	 */
	public Task getHandleTaskForId(String assignee, String objectId) {
		List<Task> taskList = taskService.findPersonalTasks(assignee);
		if (taskList == null || taskList.isEmpty()) {
			taskList = taskService.findGroupTasks(assignee);
		}
		for(Task task : taskList){
			String eid = task.getExecutionId();
			if (eid.equals(HIDDANGER_WORKFLOW + "." + objectId)) {
				return task;
			}
		}
		return null;
	}

	/**
	 * ���ݹ���������͹�����������Ϣ��ȡ��Ӧ��ʵ�����
	 */
	public HiddangerRegist getRegist(String id) {
		return dao.findUniqueByProperty("id", id);
	}
	
	/**
	 * ����������ʷ
	 */
	public void saveProcessHistory(UserInfo userInfo, HiddangerRegist regist, Task task, String nextObj, String transition, String infomation, String taskName){
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.initial(task, userInfo, regist.getProcessInstanceId(), ModuleCatalog.HIDDTROUBLEWATCH);
		processHistoryBean.setObjectId(regist.getId());
		processHistoryBean.setNextOperateUserId(nextObj);
		processHistoryBean.setHandledTaskName(taskName);
		processHistoryBean.setProcessAction(infomation);
		processHistoryBean.setTaskOutCome(transition);
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}
}
