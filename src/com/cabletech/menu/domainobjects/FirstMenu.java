package com.cabletech.menu.domainobjects;

import com.cabletech.commons.base.*;

public class FirstMenu extends BaseDomainObject {
	public FirstMenu() {
	}

	private String id;
	private String lablename;
	private String imgurl;
	private String showno;
	private String hrefurl;
	private String remark;
	private String subMenuId;
	private String waitHandleNumber;
	private String businessType;

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getHrefurl() {
		return hrefurl;
	}

	public String getId() {
		return id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public String getLablename() {
		return lablename;
	}

	public String getRemark() {
		return remark;
	}

	public void setHrefurl(String hrefurl) {
		this.hrefurl = hrefurl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public void setLablename(String lablename) {
		this.lablename = lablename;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShowno() {
		return showno;
	}

	public void setShowno(String showno) {
		this.showno = showno;
	}

	public String getWaitHandleNumber() {
		return waitHandleNumber;
	}

	public void setWaitHandleNumber(String waitHandleNumber) {
		this.waitHandleNumber = waitHandleNumber;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

}
