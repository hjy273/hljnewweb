package com.cabletech.sysmanage.beans;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class UserMailInfoBean extends BaseFileFormBean {
	private static final long serialVersionUID = 7692044395841206279L;
	private String id;
	private String mailName;
	private String emailAddress;
	private String mailUserId;
	private String orderNumberStr;
	private int orderNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMailUserId() {
		return mailUserId;
	}

	public void setMailUserId(String mailUserId) {
		this.mailUserId = mailUserId;
	}

	public String getOrderNumberStr() {
		return orderNumberStr;
	}

	public void setOrderNumberStr(String orderNumberStr) {
		this.orderNumberStr = orderNumberStr;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
}
