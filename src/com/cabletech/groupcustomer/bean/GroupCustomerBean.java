package com.cabletech.groupcustomer.bean;

import org.apache.struts.upload.FormFile;

import com.cabletech.commons.base.BaseBean;

public class GroupCustomerBean extends BaseBean{
	// 为上传文件添加
    private FormFile file = null;
    private String filename = "";
    private String filesize = "";
    
    private String groupid = "";
    private String city = "";
    private String town = "";
    private String grouptype = "";
    private String groupname = "";
    private String groupaddr = "";
    private String regionid = "";
    private String x = "";
    private String y = "";
    private String customermanager = "";
    private String grouptel = "";
    private String tel = "";
    private String operationtype = "";
    private String regionname = "";
    private String bestrowrange = "";
    
    

    public String getBestrowrange() {
		return bestrowrange;
	}


	public void setBestrowrange(String bestrowrange) {
		this.bestrowrange = bestrowrange;
	}


	public String getRegionname() {
		return regionname;
	}


	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}


	public FormFile getFile(){
        return this.file;
    }


    public void setFile( FormFile file ){
        this.file = file;
    }


    public String getFilename(){
        return this.filename;
    }


    public void setFilename( String filename ){
        this.filename = filename;
    }


    public String getFilesize(){
        return this.filesize;
    }


    public void setFilesize( String filesize ){
        this.filesize = filesize;
    }


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCustomermanager() {
		return customermanager;
	}


	public void setCustomermanager(String customermanager) {
		this.customermanager = customermanager;
	}


	public String getGroupaddr() {
		return groupaddr;
	}


	public void setGroupaddr(String groupaddr) {
		this.groupaddr = groupaddr;
	}


	public String getGroupid() {
		return groupid;
	}


	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}


	public String getGroupname() {
		return groupname;
	}


	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}


	public String getGrouptel() {
		return grouptel;
	}


	public void setGrouptel(String grouptel) {
		this.grouptel = grouptel;
	}


	public String getGrouptype() {
		return grouptype;
	}


	public void setGrouptype(String grouptype) {
		this.grouptype = grouptype;
	}


	public String getOperationtype() {
		return operationtype;
	}


	public void setOperationtype(String operationtype) {
		this.operationtype = operationtype;
	}


	public String getRegionid() {
		return regionid;
	}


	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getTown() {
		return town;
	}


	public void setTown(String town) {
		this.town = town;
	}


	public String getX() {
		return x;
	}


	public void setX(String x) {
		this.x = x;
	}


	public String getY() {
		return y;
	}


	public void setY(String y) {
		this.y = y;
	}
    
    
}
