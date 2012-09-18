package com.cabletech.machine.beans;

import org.apache.struts.upload.FormFile;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class EquipmentInfoBean extends BaseFileFormBean {
	private String eid;

	private String equipmentName;

	private String contractorId;

	private String layer;
	
	private FormFile file = null;
	
	private String filename = "";
	
	private String filesize = "";

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
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

}
