package com.cabletech.linepatrol.maintenance.beans;

import java.util.Date;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

/**
 * �����Ϣ
 * @author 
 *
 */
public class TestPlanApproveBean extends BaseFileFormBean{
	private String id;
	private String objectId;
	private String objectType;//����
	private String approverId;//�����
	private Date approveTime;
	private String approveResult;//'0��ʾ��˲�ͨ����1��ʾ���ͨ����2��ʾת��';

	private String approveRemark;
	
	private String transfer;//ת����
	
	private String mobiles;



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

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
}
