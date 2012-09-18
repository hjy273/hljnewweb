package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class CountyInfo extends BaseDomainObject {

	private String id;

	private String town;

	private String remark;

	private String regionid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

}
