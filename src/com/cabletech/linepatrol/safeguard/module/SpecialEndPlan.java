package com.cabletech.linepatrol.safeguard.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class SpecialEndPlan extends BaseDomainObject {
	public static final String ENDPLAN = "1";
	public static final String REDO = "2";
	
	private String id;
	private String planId;// ��Ѳ�ƻ�ID
	private String endType;// ��������  1��������������Ѳ����ֹ�� 2�������ƶ�
	private Date prevEndDate;// ��ֹǰ�ƻ�����ʱ��
	private Date endDate;// ��ֹ�ƻ�����ʱ��
	private String reason;// ��ֹԭ��
	private String creater;// ������
	private Date createTime;// ����ʱ��

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

	public Date getPrevEndDate() {
		return prevEndDate;
	}

	public void setPrevEndDate(Date prevEndDate) {
		this.prevEndDate = prevEndDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
