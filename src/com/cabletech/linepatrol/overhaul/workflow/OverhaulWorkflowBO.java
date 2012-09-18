package com.cabletech.linepatrol.overhaul.workflow;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.cabletech.linepatrol.overhaul.dao.OverHaulApplyDao;
import com.cabletech.linepatrol.overhaul.dao.OverHaulDao;
import com.cabletech.linepatrol.overhaul.model.OverHaul;
import com.cabletech.linepatrol.overhaul.model.OverHaulApply;

@Service
public class OverhaulWorkflowBO extends BaseWorkFlow{
	public final static String PROCESSINGNAME = "overhaul";
	public final static String SUBPROCESSINGNAME = "overhaulsubflow";
	
	@Resource(name="overHaulDao")
	private OverHaulDao dao;
	@Resource(name="overHaulApplyDao")
	private OverHaulApplyDao overHaulApplyDao;
	@Resource(name = "processHistoryBO")
	private ProcessHistoryBO processHistoryBO;
	
	public OverhaulWorkflowBO(){
		super(PROCESSINGNAME);
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
	
	@Transactional(readOnly = true)
	public List<OverHaul> queryForHandleList(String assignee, String taskName) {
		List<OverHaul> resultList = new ArrayList<OverHaul>();
		
		List<Task> taskList = new ArrayList<Task>();
		if(StringUtils.isBlank(taskName)){
			taskList = getTaskList(assignee, taskName);
		}else{
			List<String> params = Arrays.asList(taskName.split(","));
			for(String name : params){
				List<Task> tempList = getTaskList(assignee, name);
				//对tempList进行是否为空的判断
				if(tempList != null){
					taskList.addAll(tempList);
				}
				
			}
		}
		
		if (taskList != null && !taskList.isEmpty()) {
			for (Task task : taskList) {
				String executionId = task.getExecutionId();
				if (executionId.indexOf(SUBPROCESSINGNAME) != -1) {
					OverHaulApply apply = overHaulApplyDao.getFromProcessInstanceId(executionId);
					if (apply != null){
						OverHaul overHaul = dao.findUniqueByProperty("id", apply.getTaskId());
						if(overHaul != null){
							OverHaul noverHaul = new OverHaul();
							BeanUtil.copyProperties(overHaul, noverHaul);
							noverHaul.setSubflowId(apply.getProcessInstanceId());
							noverHaul.setState(apply.getState());
							noverHaul.setFlowTaskName(task.getName());
							resultList.add(noverHaul);
						}
					}
				}else if (executionId.indexOf(PROCESSINGNAME) != -1) {
					OverHaul overHaul = dao.getFromProcessInstanceId(executionId);
					if (overHaul != null){
						if(executionId.indexOf(SUBPROCESSINGNAME)==-1){
							overHaul.setFlowTaskName(task.getName());
						}
						
						resultList.add(overHaul);
					}
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 保存流程历史
	 */
	public void saveProcessHistory(UserInfo userInfo, OverHaul overHaul, Task task, String nextObj, String transition, String infomation, String taskName){
		ProcessHistoryBean processHistoryBean = new ProcessHistoryBean();
		processHistoryBean.initial(task, userInfo, overHaul.getProcessInstanceId(), ModuleCatalog.OVERHAUL);
		processHistoryBean.setObjectId(overHaul.getId());
		processHistoryBean.setNextOperateUserId(nextObj);
		processHistoryBean.setHandledTaskName(taskName);
		processHistoryBean.setProcessAction(infomation);
		processHistoryBean.setTaskOutCome(transition);
		processHistoryBO.saveProcessHistory(processHistoryBean);
	}
}
