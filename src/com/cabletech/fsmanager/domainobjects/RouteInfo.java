package com.cabletech.fsmanager.domainobjects;

import com.cabletech.commons.base.BaseDomainObject;

public class RouteInfo extends BaseDomainObject{
	
	private String id;
	private String routeName;
	private String regionID;
	
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public String getRegionID() {
		return regionID;
	}
	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	
}
