package com.cabletech.commons.process.bean;

import org.jbpm.api.task.Task;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;

public class ProcessHistoryBean extends BaseBean {
	private String id;// �������
	private String objectId;// ��Ӧʵ����
	private String objectType;// ��Ӧʵ��ģ������
	private String operateUserId;// ��ǰ���������
	private String nextOperateUserId;// ��һ�����������
	private String handledTaskId;// ��ǰ����������
	private String handledTaskName;// ��ǰ������������
	private String executionId;// ��ǰ��������ʵ�����
	private String processAction;// ��ǰ���̴�����
	private String taskOutCome;// ��ǰ���������

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public String getNextOperateUserId() {
		return nextOperateUserId;
	}

	public void setNextOperateUserId(String nextOperateUserId) {
		this.nextOperateUserId = nextOperateUserId;
	}

	public String getHandledTaskId() {
		return handledTaskId;
	}

	public void setHandledTaskId(String handledTaskId) {
		this.handledTaskId = handledTaskId;
	}

	public String getHandledTaskName() {
		return handledTaskName;
	}

	public void setHandledTaskName(String handledTaskName) {
		this.handledTaskName = handledTaskName;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessAction() {
		return processAction;
	}

	public void setProcessAction(String processAction) {
		this.processAction = processAction;
	}

	public void initial(Task task, UserInfo userInfo, String processInstanceId,
			String objectType) {
		if (task != null) {
			this.setExecutionId(task.getExecutionId());
			this.setHandledTaskId(task.getId());
			this.setHandledTaskName(task.getName());
		} else {
			this.setExecutionId(processInstanceId);
			this.setHandledTaskId("");
			this.setHandledTaskName("");
		}
		this.setOperateUserId(userInfo.getUserID());
		this.setObjectType(objectType);
	}

	public String getTaskOutCome() {
		return taskOutCome;
	}

	public void setTaskOutCome(String taskOutCome) {
		this.taskOutCome = taskOutCome;
	}
}
