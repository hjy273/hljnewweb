package com.cabletech.watchinfo.beans;

import com.cabletech.commons.base.BaseBean;

public class UploadFromMMSBean  extends BaseBean {

	private static final long serialVersionUID = 8437770345547025377L;
	private String watchmmsId;
	private String watchId;
	private String attachRemark;
    private String relateUrl;
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
	public String getRelateUrl() {
		return relateUrl;
	}
	public void setRelateUrl(String relateUrl) {
		this.relateUrl = relateUrl;
	}
	public String getWatchmmsId() {
		return watchmmsId;
	}
	public void setWatchmmsId(String watchmmsId) {
		this.watchmmsId = watchmmsId;
	}
	
    
}
