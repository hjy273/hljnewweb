package com.cabletech.datum.bean;

import com.cabletech.commons.util.DateUtil;
import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public abstract class BaseDatum extends BaseFileFormBean {
	
	public BaseDatum(){
		 super();
	}
	/**
	 * 资料 ID
	 */
	private java.lang.String id;

	/**
	 * 资料名称
	 */
	private java.lang.String filename;
	private String documentname;
	/**
	 * 资料类型
	 */
	private java.lang.String documenttype;

	/**
	 * 文档描述
	 */
	private java.lang.String description;

	/**
	 * 有效日期
	 */
	private String validatetime=DateUtil.getNowDateString( "yyyy/MM/dd" );

	/**
	 * 文件ID
	 */
	private java.lang.String adjunct;

	/**
	 * 登记记录人ID
	 */
	private java.lang.String userid;

	/**
	 * 创建时间
	 */
	private String createdate=DateUtil.getNowDateString( "yyyy/MM/dd" );

	/**
	 * 区域ID
	 */
	private java.lang.String regionid;

	public java.lang.String getAdjunct() {
		return adjunct;
	}

	public void setAdjunct(java.lang.String adjunct) {
		this.adjunct = adjunct;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(java.lang.String documenttype) {
		this.documenttype = documenttype;
	}

	public java.lang.String getFilename() {
		return filename;
	}

	public void setFilename(java.lang.String filename) {
		this.filename = filename;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getRegionid() {
		return regionid;
	}

	public void setRegionid(java.lang.String regionid) {
		this.regionid = regionid;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public String getValidatetime() {
		return validatetime;
	}

	public void setValidatetime(String validatetime) {
		this.validatetime = validatetime;
	}

	public String getDocumentname() {
		return documentname;
	}

	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}

}
