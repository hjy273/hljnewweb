package com.cabletech.menu.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

public class Resource extends BaseDomainObject{
	private String code;
	private String resourceName;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
