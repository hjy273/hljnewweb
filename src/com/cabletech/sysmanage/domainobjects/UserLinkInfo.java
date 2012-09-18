package com.cabletech.sysmanage.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

public class UserLinkInfo extends BaseDomainObject{
	private static final long serialVersionUID = 7692044395841206279L;
	private String id;
	private String linkName;
	private String linkAddress;
	private String linkUserId;
	private String linkType;
	private int orderNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getLinkUserId() {
		return linkUserId;
	}

	public void setLinkUserId(String linkUserId) {
		this.linkUserId = linkUserId;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
}
