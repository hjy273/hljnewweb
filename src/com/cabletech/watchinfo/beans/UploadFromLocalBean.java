package com.cabletech.watchinfo.beans;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class UploadFromLocalBean extends BaseBean {
  	private String watchId;
	private String attachRemark;
    private FormFile attachFile;
	public String getWatchId() {
		return watchId;
	}
	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}
	public String getAttachRemark() {
		return attachRemark;
	}
	public void setAttachRemark(String attachRemark) {
		this.attachRemark = attachRemark;
	}
	public FormFile getAttachFile() {
		return attachFile;
	}
	public void setAttachFile(FormFile attachFile) {
		this.attachFile = attachFile;
	}
  
  
}
