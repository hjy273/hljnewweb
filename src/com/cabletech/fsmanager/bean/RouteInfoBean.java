//create by chengwenfeng
//Date: 2009-08-17
package com.cabletech.fsmanager.bean;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class RouteInfoBean extends BaseBean{
	// 为上传文件添加
    private FormFile file = null;
    private String filename = "";
    private String filesize = "";
	
	
	
	private String id;
	private String routeName;
	private String regionID;
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}
	public FormFile getFile() {
		return this.file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getFilename() {
		return this.filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilesize() {
		return this.filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	
	
	
}
