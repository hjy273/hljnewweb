package com.cabletech.linepatrol.safeguard.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class SpecialEndPlanBean extends BaseCommonBean {

	private String id;
	private String planId;// ��Ѳ�ƻ�ID
	private String endType;// ��������
	private String prevEndDate;// ��ֹǰ�ƻ�����ʱ��
	private String endDate;// ��ֹ�ƻ�����ʱ��
	private String reason;// ��ֹԭ��
	private String creater;// ������
	private String createTime;// ����ʱ��
	
	private String approveId;//�����ԱId
	private String approvers;//ת����ά��ԱId
	private String mobiles;//��ˡ�ת����ά��Ա�绰����
	private String operator;//��ά��Ա�Ĳ���������˻���ת��
	private String approveResult;//��˽����0 ��ͨ�� 1 ͨ�� 2 ת��
	private String approveRemark;//�������
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

	public String getEndType() {
		return endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	public String getPrevEndDate() {
		return prevEndDate;
	}

	public void setPrevEndDate(String prevEndDate) {
		this.prevEndDate = prevEndDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String[] getReaderPhones() {
		return readerPhones;
	}

	public void setReaderPhones(String[] readerPhones) {
		this.readerPhones = readerPhones;
	}
}
