package com.cabletech.linepatrol.maintenance.beans;

import java.util.Date;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

/**
 * 审核信息
 * @author 
 *
 */
public class TestPlanApproveBean extends BaseFileFormBean{
	private String id;
	private String objectId;
	private String objectType;//表名
	private String approverId;//审核人
	private Date approveTime;
	private String approveResult;//'0表示审核不通过，1表示审核通过，2表示转审';

	private String approveRemark;
	
	private String transfer;//转审人
	
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
