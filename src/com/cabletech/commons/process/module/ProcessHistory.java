package com.cabletech.commons.process.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class ProcessHistory extends BaseDomainObject {
	private String id;
	private String objectId;
	private String objectType;
	private String operateUserId;
	private String nextOperateUserId;
	private Date handledTime;
	private String handledTaskId;
	private String handledTaskName;
	private String executionId;
	private String processAction;
	private String taskOutCome;

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

	public Date getHandledTime() {
		return handledTime;
	}

	public void setHandledTime(Date handledTime) {
		this.handledTime = handledTime;
	}

	public String getHandledTaskId() {
		return handledTaskId;
	}

	public void setHandledTaskId(String handledTaskId) {
		this.handledTaskId = handledTaskId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getHandledTaskName() {
		return handledTaskName;
	}

	public void setHandledTaskName(String handledTaskName) {
		this.handledTaskName = handledTaskName;
	}

	public String getProcessAction() {
		return processAction;
	}

	public void setProcessAction(String processAction) {
		this.processAction = processAction;
	}

	public String getTaskOutCome() {
		return taskOutCome;
	}

	public void setTaskOutCome(String taskOutCome) {
		this.taskOutCome = taskOutCome;
	}

}
