package com.cabletech.linepatrol.commons.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

public class EvaluateStandard extends BaseDomainObject{
	private String id;
	private String stdType;
	private String stdRemark;
	private Date stdFileUsedDate;
	private String stdFileContent1;
	private String stdFileContent2;
	private String stdFileContent3;
	private String stdFileContent4;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStdType() {
		return stdType;
	}
	public void setStdType(String stdType) {
		this.stdType = stdType;
	}
	public String getStdRemark() {
		return stdRemark;
	}
	public void setStdRemark(String stdRemark) {
		this.stdRemark = stdRemark;
	}
	public Date getStdFileUsedDate() {
		return stdFileUsedDate;
	}
	public void setStdFileUsedDate(Date stdFileUsedDate) {
		this.stdFileUsedDate = stdFileUsedDate;
	}
	public String getStdFileContent1() {
		return stdFileContent1;
	}
	public void setStdFileContent1(String stdFileContent1) {
		this.stdFileContent1 = stdFileContent1;
	}
	public String getStdFileContent2() {
		return stdFileContent2;
	}
	public void setStdFileContent2(String stdFileContent2) {
		this.stdFileContent2 = stdFileContent2;
	}
	public String getStdFileContent3() {
		return stdFileContent3;
	}
	public void setStdFileContent3(String stdFileContent3) {
		this.stdFileContent3 = stdFileContent3;
	}
	public String getStdFileContent4() {
		return stdFileContent4;
	}
	public void setStdFileContent4(String stdFileContent4) {
		this.stdFileContent4 = stdFileContent4;
	}
	
}
