package com.cabletech.report.beans;

import com.cabletech.commons.util.DateUtil;
import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class Report extends BaseFileFormBean {
	/**
	 * ��ʾ
	 * 
	 */
	private java.lang.String id;

	/**
	 * ��������
	 * 
	 */
	private java.lang.String reportname;

	/**
	 * ���渽��
	 * 
	 */
	private java.lang.String reporturl;

	/**
	 * ��������
	 * 
	 */
	private java.lang.String reporttype;

	/**
	 * ��ע��Ϣ
	 * 
	 * 
	 */
	private java.lang.String remark;

	/**
	 * ����˵��
	 * 
	 */
	private java.lang.String auditingRemark;

	/**
	 * �������
	 * 
	 */
	private java.lang.String auditingResult;
	private String userid;
	private String regionid;
	private String createdate;
	private String auditingman;

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public java.lang.String getAuditingRemark() {
		return auditingRemark;
	}

	public void setAuditingRemark(java.lang.String auditingRemark) {
		this.auditingRemark = auditingRemark;
	}

	public java.lang.String getAuditingResult() {
		return auditingResult;
	}

	public void setAuditingResult(java.lang.String auditingResult) {
		this.auditingResult = auditingResult;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	public java.lang.String getReportname() {
		return reportname;
	}

	public void setReportname(java.lang.String reportname) {
		this.reportname = reportname;
	}

	public java.lang.String getReporttype() {
		return reporttype;
	}

	public void setReporttype(java.lang.String reporttype) {
		this.reporttype = reporttype;
	}

	public java.lang.String getReporturl() {
		return reporturl;
	}

	public void setReporturl(java.lang.String reporturl) {
		this.reporturl = reporturl;
	}

	public String getAuditingman() {
		return auditingman;
	}

	public void setAuditingman(String auditingman) {
		this.auditingman = auditingman;
	}
}
