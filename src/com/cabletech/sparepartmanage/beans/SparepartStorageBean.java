package com.cabletech.sparepartmanage.beans;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

/**
 * SparepartStorageBean ��������FormBean
 */
public class SparepartStorageBean extends BaseFileFormBean {
	private String tid;//ϵͳ���

	private String sparePartId;//�������

	private String sparePartName;//��������

	private String serialNumber;//�豸���к�

	private String storagePosition;//����λ��

	private String sparePartState;//����״̬

	private String departId;//�������ڲ��ű��

	private String departName;

	private int storageNumber;//�������

	private Date storageDate;//�������

	private String takenOutStorage;//ȡ����λ��
	
	private String storagePerson;//������
	
	private String drawPerson;//��ά������

	private String storageRemark;//��ע

	private String initStorage;//�����λ��

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
