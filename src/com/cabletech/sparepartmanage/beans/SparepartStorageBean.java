package com.cabletech.sparepartmanage.beans;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

/**
 * SparepartStorageBean 备件库存的FormBean
 */
public class SparepartStorageBean extends BaseFileFormBean {
	private String tid;//系统编号

	private String sparePartId;//备件编号

	private String sparePartName;//备件名称

	private String serialNumber;//设备序列号

	private String storagePosition;//保存位置

	private String sparePartState;//备件状态

	private String departId;//备件所在部门编号

	private String departName;

	private int storageNumber;//入库数量

	private Date storageDate;//入库日期

	private String takenOutStorage;//取出的位置
	
	private String storagePerson;//操作人
	
	private String drawPerson;//代维领用人

	private String storageRemark;//备注

	private String initStorage;//最初的位置

	private FormFile file = null;
	private String filename = "";
	private String filesize = "";

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

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getDrawPerson() {
		return drawPerson;
	}

	public void setDrawPerson(String drawPerson) {
		this.drawPerson = drawPerson;
	}

}
