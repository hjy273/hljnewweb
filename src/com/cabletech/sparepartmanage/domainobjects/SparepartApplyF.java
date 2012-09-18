package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorageApply ��������ʵ��
 * 
 */
public class SparepartApplyF extends BaseDomainObject {

    private String tid;
    private String sparePartId;//�������
    private String patrolgroupId;//Ѳ��ά������  
    private String useMode;//ʹ�÷�ʽ 

    private String replaceType;//�������� 
    private String applyUsePosition;//����ʹ��λ�� 

    private Date applyDate;//�������� 

    private String applyPerson;//������ 

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
