package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorageApply ��������ʵ������
 * 
 */
public class SparepartApplyS extends BaseDomainObject {
	
    private String tid;
    private String ftid;//����id
  
    private String serialNumber;   //�������к�
    private String usedSerialNumber;//�������������   
    private String storageId;//��������� 
    private String takenOutStorage;//ȡ����λ�� 
  
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

    public void clone(SparepartApplyS newApply) {       
        this.serialNumber=newApply.getSerialNumber();       
        this.storageId=newApply.getStorageId();
        this.takenOutStorage=newApply.getTakenOutStorage();
        this.usedSerialNumber=newApply.getUsedSerialNumber();
    }

	
	public String getFtid() {
		return ftid;
	}

	public void setFtid(String ftid) {
		this.ftid = ftid;
	}
   

}
