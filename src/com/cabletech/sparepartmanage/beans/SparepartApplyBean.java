package com.cabletech.sparepartmanage.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

/**
 * SparepartApplyBean ±¸¼þÉêÇëµÄFormBean
 */
public class SparepartApplyBean extends BaseBean {
    private String tid;

    private String sparePartId;
    
    private String sparePartName;
    
    private String usedSparePartId;
    
    private String usedSparePartName;
    
    private String usedPosition;

    private String patrolgroupId;
    
    private String patrolgroupName;

    private String storageId;

    private String useMode;

    private String replaceType;

    private String applyUsePosition;

    private Date applyDate;

    private String applyPerson;

    private String applyRemark;

    private int useNumber;
    
    private String takenOutStorage;
    
    private String serialNumber;
    
    private String usedSerialNumber;
    
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

    public String getPatrolgroupName() {
        return patrolgroupName;
    }

    public void setPatrolgroupName(String patrolgroupName) {
        this.patrolgroupName = patrolgroupName;
    }

    public String getSparePartName() {
        return sparePartName;
    }

    public void setSparePartName(String sparePartName) {
        this.sparePartName = sparePartName;
    }

    public String getUsedSparePartName() {
        return usedSparePartName;
    }

    public void setUsedSparePartName(String usedSparePartName) {
        this.usedSparePartName = usedSparePartName;
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
