package com.cabletech.linepatrol.commons.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpApproveInfo entity. @author MyEclipse Persistence Tools
 */

public class ApproveInfo extends BaseDomainObject{

	// Fields

	private String id;
	private String objectId;
	private String objectType;//表名
	private String approverId;
	private Date approveTime;
	private String approveResult;//'0表示审核不通过，1表示审核通过，2表示转审';

	private String approveRemark;

	// Constructors

	/** default constructor */
	public ApproveInfo() {
	}

	/** minimal constructor */
	public ApproveInfo(String id) {
		this.id = id;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	/** full constructor */
	public ApproveInfo(String id, String objectId, String approverId,
			Date approveTime, String approveResult, String approveRemark) {
		this.id = id;
		this.objectId = objectId;
		this.approverId = approverId;
		this.approveTime = approveTime;
		this.approveResult = approveResult;
		this.approveRemark = approveRemark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getApproverId() {
		return this.approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public Date getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveResult() {
		return this.approveResult;
	}

	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}

	public String getApproveRemark() {
		return this.approveRemark;
	}

	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}

}