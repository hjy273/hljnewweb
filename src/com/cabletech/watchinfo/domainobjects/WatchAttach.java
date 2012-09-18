package com.cabletech.watchinfo.domainobjects;

public class WatchAttach {
  private String id;
  private String placeId;
  private String attachPath;
  private int flag;
  private String remark;
  private String mmsRoot;
  private String uploadtime;
  
  
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPlaceId() {
	return placeId;
}
public void setPlaceId(String placeId) {
	this.placeId = placeId;
}
public String getAttachPath() {
	return attachPath;
}
public void setAttachPath(String attachPath) {
	this.attachPath = attachPath;
}
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getMmsRoot() {
	return mmsRoot;
}
public void setMmsRoot(String mmsRoot) {
	this.mmsRoot = mmsRoot;
}

public String getFullMmsUrl(){
	return this.mmsRoot+'/'+attachPath;
}
public String getUploadtime() {
	return uploadtime;
}
public void setUploadtime(String uploadtime) {
	this.uploadtime = uploadtime;
}


  
  
}
