package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorageApply 备件申请实体
 * 
 */
public class SparepartApply extends BaseDomainObject {
    private String tid;

    private String sparePartId;//备件编号
    
    private String usedSparePartId;//被更换备件编号

    private String patrolgroupId;//巡检维护组编号

    private String storageId;//备件库存编号

    private String useMode;//使用方式

    private String replaceType;//更换类型

    private String applyUsePosition;//申请使用位置

    private Date applyDate;//申请日期

    private String applyPerson;//申请人

    private String applyRemark;

    private int useNumber;

    private String takenOutStorage;//取出的位置

    private String serialNumber;
    
    private String usedSerialNumber;//被更换备件编号
    
    private String usedPosition;
    
    private String auditingState;

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

    public String getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(String sparePartId) {
        this.sparePartId = sparePartId;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
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

    public String getTakenOutStorage() {
        return takenOutStorage;
    }

    public void setTakenOutStorage(String takenOutStorage) {
        this.takenOutStorage = takenOutStorage;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getUsedSerialNumber() {
        return usedSerialNumber;
    }

    public void setUsedSerialNumber(String usedSerialNumber) {
        this.usedSerialNumber = usedSerialNumber;
    }

    public String getUsedSparePartId() {
        return usedSparePartId;
    }

    public void setUsedSparePartId(String usedSparePartId) {
        this.usedSparePartId = usedSparePartId;
    }

    public void clone(SparepartApply newApply) {
        this.applyDate=newApply.getApplyDate();
        this.applyPerson=newApply.getApplyPerson();
        this.applyRemark=newApply.getApplyRemark();
        this.applyUsePosition=newApply.getApplyUsePosition();
        this.patrolgroupId=newApply.getPatrolgroupId();
        this.replaceType=newApply.getReplaceType();
        this.serialNumber=newApply.getSerialNumber();
        this.sparePartId=newApply.getSparePartId();
        this.storageId=newApply.getStorageId();
        this.takenOutStorage=newApply.getTakenOutStorage();
        this.usedSerialNumber=newApply.getUsedSerialNumber();
        this.usedSparePartId=newApply.getUsedSparePartId();
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
