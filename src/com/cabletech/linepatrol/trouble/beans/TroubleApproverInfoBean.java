package com.cabletech.linepatrol.trouble.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class TroubleApproverInfoBean extends BaseBean{
	private String id;
	private String troubleReplyId;//���Ϸ�����ϵͳ���
	private String approverId;
	private Date approveTime;
	private String approveResult;//'0��ʾ��˲�ͨ����1��ʾ���ͨ����2��ʾת��';
	private String approveRemark;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTroubleReplyId() {
		return troubleReplyId;
	}
	public void setTroubleReplyId(String troubleReplyId) {
		this.troubleReplyId = troubleReplyId;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
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
}
