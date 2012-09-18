package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorageApply 备件申请实体子类
 * 
 */
public class SparepartApplyS extends BaseDomainObject {
	
    private String tid;
    private String ftid;//父表id
  
    private String serialNumber;   //备件序列号
    private String usedSerialNumber;//被更换备件编号   
    private String storageId;//备件库存编号 
    private String takenOutStorage;//取出的位置 
  
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
