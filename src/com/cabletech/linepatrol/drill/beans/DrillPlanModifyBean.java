package com.cabletech.linepatrol.drill.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class DrillPlanModifyBean extends BaseCommonBean {

	private String id;
	private String planId;// ��������Id
	private String prevStartTime;// ԭ������ʼʱ��
	private String prevEndTime;// ԭ��������ʱ��
	private String nextStartTime;// �����ʼʱ��
	private String nextEndTime;// ��������ʱ��
	private String modifyCase;// ���ԭ��
	private String modifyMan;// ���������
	private String modifyDate;// �������ʱ��
	
	private String operator;//��ά��Ա�Ĳ���������˻���ת��
	private String approveResult;//��˽����0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//�������
	private String approveId;//�����ԱId
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	
	private String conId;//��ά��˾Id
	private String taskId;//����Id
	
	private String deadline;//�����ܽ��ύʱ��
	private String[] readerPhones;//�����˵绰

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPrevStartTime() {
		return prevStartTime;
	}

	public void setPrevStartTime(String prevStartTime) {
		this.prevStartTime = prevStartTime;
	}

	public String getPrevEndTime() {
		return prevEndTime;
	}

	public void setPrevEndTime(String prevEndTime) {
		this.prevEndTime = prevEndTime;
	}

	public String getNextStartTime() {
		return nextStartTime;
	}

	public void setNextStartTime(String nextStartTime) {
		this.nextStartTime = nextStartTime;
	}

	public String getNextEndTime() {
		return nextEndTime;
	}

	public void setNextEndTime(String nextEndTime) {
		this.nextEndTime = nextEndTime;
	}

	public String getModifyCase() {
		return modifyCase;
	}

	public void setModifyCase(String modifyCase) {
		this.modifyCase = modifyCase;
	}

	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

	public String getApproveId() {
		return approveId;
	}

	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
