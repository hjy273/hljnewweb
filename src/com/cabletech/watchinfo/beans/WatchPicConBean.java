package com.cabletech.watchinfo.beans;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class WatchPicConBean extends BaseFileFormBean {
	private String ID;
	private String URL;
	private String PlaceID;
	private int flag;
	private String endDate = "";
    private String beginDate = "";
    private String fileIDs; //���������������ID��һ��ID��Ӧһ������������������","�ŷֿ�
    private java.util.List remarks;
    
  public WatchPicConBean(){
	  super();
  }
public String getID() {
	return ID;
}
public void setID(String id) {
	ID = id;
}
public String getPlaceID() {
	return PlaceID;
}
public void setPlaceID(String placeID) {
	PlaceID = placeID;
}
public String getURL() {
	return URL;
}
public void setURL(String url) {
	URL = url;
}
public String getBeginDate() {
	return beginDate;
}
public void setBeginDate(String beginDate) {
	this.beginDate = beginDate;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public String getFileIDs() {
	return fileIDs;
}
public void setFileIDs(String fileIDs) {
	this.fileIDs = fileIDs;
}
public java.util.List getRemarks() {
	return remarks;
}
public void setRemarks(java.util.List remarks) {
	this.remarks = remarks;
}
}
