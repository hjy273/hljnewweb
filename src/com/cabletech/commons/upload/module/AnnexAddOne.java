package com.cabletech.commons.upload.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class AnnexAddOne extends BaseDomainObject {
	private static final long serialVersionUID = -1474830026457430808L;
	private String id;
	private String entityId;
	private String entityType;
	private String fileId;
	private String module;
	private String moduleCatalog;
	private Date timeStamp;
	private String uploader;
	private Date uploadDate;
	private String isUsable="1";
	private String state="0";//"0" 为可用，"1" 为删除
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getModuleCatalog() {
		return moduleCatalog;
	}
	public void setModuleCatalog(String moduleCatalog) {
		this.moduleCatalog = moduleCatalog;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUploader() {
		return uploader;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getIsUsable() {
		return isUsable;
	}
	public void setIsUsable(String isUsable) {
		this.isUsable = isUsable;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
