package com.cabletech.linepatrol.material.domain;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialPutStorage extends BaseDomainObject {
	public static final String LP_MT_STORAGE = "LP_MT_STORAGE";// 审核表

	private String id;// 材料入库id

	private String creator;// 申请人

	private String contractorId;// 申请人

	private Date createDate;// 创建时间

	private String remark;// 用途

	private String applyId;

	private String type;
	
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public static String getLpMtStorage() {
		return LP_MT_STORAGE;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
