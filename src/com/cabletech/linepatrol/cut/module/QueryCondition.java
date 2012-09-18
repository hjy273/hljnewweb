package com.cabletech.linepatrol.cut.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ��Ӳ�ѯ������
 * @author Administrator
 *
 */
public class QueryCondition extends BaseDomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 950344641998036469L;
	private String contractorId;// ��Ӵ�ά
	private String[] cutLevels;// ��Ӽ���
	private String[] cableLevels;// ���¼���
	private String isInterrupt;// �Ƿ��ж�ҵ��
	private String[] states;// ״̬
	private String isTimeOut;// �Ƿ�ʱ
	private String timeType;//ʱ������ 1������ʱ�� 2��ʵ�ʿ�ʼʱ�� 3������ʱ��
	private String beginTime;// ��ʼʱ��
	private String endTime;// ����ʱ��
	private String taskNames;
	private String[] taskStates;
	private String cancelState;//ȡ��״̬

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String[] getCutLevels() {
		return cutLevels;
	}

	public void setCutLevels(String[] cutLevels) {
		this.cutLevels = cutLevels;
	}

	public String[] getCableLevels() {
		return cableLevels;
	}

	public void setCableLevels(String[] cableLevels) {
		this.cableLevels = cableLevels;
	}

	public String getIsInterrupt() {
		return isInterrupt;
	}

	public void setIsInterrupt(String isInterrupt) {
		this.isInterrupt = isInterrupt;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}

	public String getIsTimeOut() {
		return isTimeOut;
	}

	public void setIsTimeOut(String isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}

	public String[] getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(String[] taskStates) {
		this.taskStates = taskStates;
		this.taskNames = "";
		for (int i = 0; taskStates != null && i < taskStates.length; i++) {
			this.taskNames += taskStates[i];
			if (i < taskStates.length - 1) {
				this.taskNames += ",";
			}
		}
	}

	public String getCancelState() {
		return cancelState;
	}

	public void setCancelState(String cancelState) {
		this.cancelState = cancelState;
	}
}
