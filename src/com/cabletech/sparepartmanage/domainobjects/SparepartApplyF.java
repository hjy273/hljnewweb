package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorageApply 备件申请实体
 * 
 */
public class SparepartApplyF extends BaseDomainObject {

    private String tid;
    private String sparePartId;//备件编号
    private String patrolgroupId;//巡检维护组编号  
    private String useMode;//使用方式 

    private String replaceType;//更换类型 
    private String applyUsePosition;//申请使用位置 

    private Date applyDate;//申请日期 

    private String applyPerson;//申请人 

    private String applyRemark;

    private int useNumber; 
    
    private String usedPosition;
    
    private String auditingState; 
    
    public String getSparePartId() {
		return sparePartId;
	}

	public void setSparePartId(String sparePartId) {
		this.sparePartId = sparePartId;
	}


    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }

    public String getApplyUsePosition() {
        return applyUsePosition;
    }

    public void setApplyUsePosition(String applyUsePosition) {
        this.applyUsePosition = applyUsePosition;
    }

    public String getPatrolgroupId() {
        return patrolgroupId;
    }

    public void setPatrolgroupId(String patrolgroupId) {
        this.patrolgroupId = patrolgroupId;
    }

    public String getReplaceType() {
        return replaceType;
    }

    public void setReplaceType(String replaceType) {
        this.replaceType = replaceType;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUseMode() {
        return useMode;
    }

    public void setUseMode(String useMode) {
        this.useMode = useMode;
    }

    public int getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(int useNumber) {
        this.useNumber = useNumber;
    }

   
    public void clone(SparepartApplyF newApply) {
        this.applyDate=newApply.getApplyDate();
        this.applyPerson=newApply.getApplyPerson();
        this.applyRemark=newApply.getApplyRemark();
        this.applyUsePosition=newApply.getApplyUsePosition();
        this.patrolgroupId=newApply.getPatrolgroupId();
        this.replaceType=newApply.getReplaceType();
        this.useMode=newApply.getUseMode();
        this.useNumber=newApply.getUseNumber();
    }

    public String getAuditingState() {
        return auditingState;
    }

    public void setAuditingState(String auditingState) {
        this.auditingState = auditingState;
    }

    public String getUsedPosition() {
        return usedPosition;
    }

    public void setUsedPosition(String usedPosition) {
        this.usedPosition = usedPosition;
    }

}
