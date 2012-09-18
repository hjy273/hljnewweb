package com.cabletech.linepatrol.maintenance.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTestCoredata entity. @author MyEclipse Persistence Tools
 * œÀ–æ ˝æ›∑÷Œˆ±Ì
 */

public class TestCoreData extends BaseDomainObject {

	// Fields

	private String id;
	private String coreId;
	private String coreOrder;
	private String abEnd;//≤‚ ‘∂À
	private String baseStation;//≤‚ ‘ª˘’æ
	private String fileRemark;
	private Date testDate;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCoreId() {
		return coreId;
	}
	public void setCoreId(String coreId) {
		this.coreId = coreId;
	}
	public String getCoreOrder() {
		return coreOrder;
	}
	public void setCoreOrder(String coreOrder) {
		this.coreOrder = coreOrder;
	}
	public String getAbEnd() {
		return abEnd;
	}
	public void setAbEnd(String abEnd) {
		this.abEnd = abEnd;
	}
	public String getBaseStation() {
		return baseStation;
	}
	public void setBaseStation(String baseStation) {
		this.baseStation = baseStation;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public String getFileRemark() {
		return fileRemark;
	}
	public void setFileRemark(String fileRemark) {
		this.fileRemark = fileRemark;
	}
    
	

}