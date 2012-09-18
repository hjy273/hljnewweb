package com.cabletech.linepatrol.acceptance.model;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class AllotLog extends BaseDomainObject {

	private static final long serialVersionUID = 1L;

	private String id;
	private String applyId;
	private String resourceId;
	private String oldContractorId;
	private String newContractorId;
	private Date createDate;
	private String alloter;
	public AllotLog(){
	}
	
	public AllotLog(String applyId, String resourceId, String oldContractorId, String newContractorId, String alloter) {
		super();
		this.applyId = applyId;
		this.resourceId = resourceId;
		this.oldContractorId = oldContractorId;
		this.newContractorId = newContractorId;
		this.alloter = alloter;
		this.createDate = new Date();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getOldContractorId() {
		return oldContractorId;
	}
	public void setOldContractorId(String oldContractorId) {
		this.oldContractorId = oldContractorId;
	}
	public String getNewContractorId() {
		return newContractorId;
	}
	public void setNewContractorId(String newContractorId) {
		this.newContractorId = newContractorId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getAlloter() {
		return alloter;
	}
	public void setAlloter(String alloter) {
		this.alloter = alloter;
	}
	@Override
	public String toString() {
		return "AllotLog [id=" + id + ", applyId=" + applyId + ", resourceId=" + resourceId + ", oldContractorId="
				+ oldContractorId + ", newContractorId=" + newContractorId + ", createDate=" + createDate
				+ ", alloter=" + alloter + "]";
	}
}
