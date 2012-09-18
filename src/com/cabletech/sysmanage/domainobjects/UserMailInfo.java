package com.cabletech.sysmanage.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

public class UserMailInfo extends BaseDomainObject{
	private static final long serialVersionUID = 7692044395841206279L;
	private String id;
	private String mailName;
	private String emailAddress;
	private String mailUserId;
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

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
}
