package com.cabletech.sparepartmanage.domainobjects;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * SparepartStorage 备件库存实体
 * 
 */
public class SparepartStorage extends BaseDomainObject {
    private String tid;//库存的ID

    private String sparePartId;//基本信息ID

    private String serialNumber;

    private String storagePosition;

    private String sparePartState;

    private String departId;

    private int storageNumber;

    private Date storageDate;

    private String takenOutStorage;//取出库的ID

    private String storagePerson;
    
    private String drawPerson;

    private String storageRemark;

    private String initStorage;//最初移动库ID

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(String sparePartId) {
        this.sparePartId = sparePartId;
    }

    public String getSparePartState() {
        return sparePartState;
    }

    public void setSparePartState(String sparePartState) {
        this.sparePartState = sparePartState;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public int getStorageNumber() {
        return storageNumber;
    }

    public void setStorageNumber(int storageNumber) {
        this.storageNumber = storageNumber;
    }

    public String getStoragePerson() {
        return storagePerson;
    }

    public void setStoragePerson(String storagePerson) {
        this.storagePerson = storagePerson;
    }

    public String getStoragePosition() {
        return storagePosition;
    }

    public void setStoragePosition(String storagePosition) {
        this.storagePosition = storagePosition;
    }

    public String getStorageRemark() {
        return storageRemark;
    }

    public void setStorageRemark(String storageRemark) {
        this.storageRemark = storageRemark;
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

    public String getInitStorage() {
        return initStorage;
    }

    public void setInitStorage(String initStorage) {
        this.initStorage = initStorage;
    }

    public void clone(SparepartStorage newStorage) {
        this.departId=newStorage.getDepartId();
        this.initStorage=newStorage.getInitStorage();
        this.serialNumber=newStorage.getSerialNumber();
        this.sparePartId=newStorage.getSparePartId();
        this.sparePartState=newStorage.getSparePartState();
        this.storageDate=newStorage.getStorageDate();
        this.storageNumber=newStorage.getStorageNumber();
        this.storagePerson=newStorage.getStoragePerson();
        this.storagePosition=newStorage.getStoragePosition();
        this.storageRemark=newStorage.getStorageRemark();
        this.takenOutStorage=newStorage.getTakenOutStorage();
    }

	public String getDrawPerson() {
		return drawPerson;
	}

	public void setDrawPerson(String drawPerson) {
		this.drawPerson = drawPerson;
	}

}
