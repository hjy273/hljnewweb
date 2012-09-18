package com.cabletech.linepatrol.acceptance.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.process.bean.ProcessHistoryBean;
import com.cabletech.commons.process.service.ProcessHistoryBO;
import com.cabletech.commons.upload.ModuleCatalog;
import com.cabletech.ctf.workflow.BaseWorkFlow;
import com.cabletech.linepatrol.acceptance.dao.ApplyDao;
import com.cabletech.linepatrol.acceptance.dao.SubflowDao;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.model.Subflow;

@Service
public class AcceptanceFlow extends BaseWorkFlow {
	public static final String ALLOT_TASK="allotTasks";
	public static final String CLAIM_TASK="claimTask";
	public static final String APPROVE_TASK="approve";
	public static final String RECORD_DATA_TASK="subrecord";
	public static final String SUBAPPROVE="subapprove";
	public static final String EXAM_TASK="subexam";
	public static final String PROCESSING_NAME = "acceptance";
	public static final String SUB_PROCESSING_NAME = "acceptancesubflow";
	public static final String APPEDIX = "sub";
	
	@Resource(name = "applyDao")
	private ApplyDao dao;
	@Resource(name = "subflowDao")
	private SubflowDao subflowDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;

	private String PROCESSINGNAME = "acceptance";
	private String SUBPROCESSINGNAME = "acceptancesubflow";

	public AcceptanceFlow(){
		super("acceptance");
	}
	@Transactional(readOnly = true)
	public int queryForHandleNumber(String assignee, String condition,
			String taskName) {
		List<Apply> list = queryForHandleList(assignee, taskName, "");
		return list == null ? 0 : list.size();
	}

	@Transactional(readOnly = true)
	public List<Apply> queryForHandleList(String assignee, String taskName, String processName) {
		List<Apply> resultList = new ArrayList<Apply>();
		List<Task> taskList = super.getTaskList(assignee, taskName);
		if (taskList != null && !taskList.isEmpty()) {
			for (Task task : taskList) {
				String executionId = task.getExecutionId();
				if(StringUtils.isBlank(processName) || processName.equals(PROCESSING_NAME)){
					if (executionId.indexOf(PROCESSINGNAME) != -1) {
						Apply apply = dao.getApplyFromProcessInstanceId(executionId);
						if (apply != null){
							if(executionId.indexOf(SUBPROCESSINGNAME)==-1){
								apply.setFlowTaskName(task.getName());
							}
							resultList.add(apply);
						}
					}
				}
				if(StringUtils.isBlank(processName) || processName.equals(SUBPROCESSINGNAME)){
					if (executionId.indexOf(SUBPROCESSINGNAME) != -1) {
						Subflow subflow = subflowDao.findByUnique("processInstanceId", executionId);
						if (subflow != null) {
							Apply napply = dao.findByUnique("id", subflow.getApplyId());
							if (napply != null){
								Apply apply = new Apply();
								BeanUtil.copyProperties(napply, apply);
								apply.setSubflowId(subflow.getId());
								apply.setSubProcessState(subflow.getProcessState());
								apply.setFlowTaskName(APPEDIX+task.getName());
								resultList.add(apply);
							}
						}
					}
				}
			}
		}
		return resultList;
	}

	@Transactional(readOnly = true)
	public Task getHandleTaskForId(String assignee, String objectId) {
		List<Task> taskList = taskService.findPersonalTasks(assignee);
		if (taskList == null || taskList.isEmpty()) {
			taskList = taskService.findGroupTasks(assignee);
		}
		for (Task task : taskList) {
			String executionId = task.getExecutionId();
			if (objectId.equals(executionId))
				return task;
		}
		return null;
	}
	
	/**
	 * 保存流程历史
	 */
	public void saveProcessHistory(UserInfo userInfo, Apply apply, Task task, String nextObj, String transition, String infomation, String taskName){
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.initial(task, userInfo, apply.getProcessInstanceId(), ModuleCatalog.INSPECTION);
		processHistoryBean.setObjectId(apply.getId());
		processHistoryBean.setNextOperateUserId(nextObj);
		processHistoryBean.setHandledTaskName(taskName);
		processHistoryBean.setProcessAction(infomation);
		processHistoryBean.setTaskOutCome(transition);
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}
}