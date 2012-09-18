package com.cabletech.sysmanage.beans;

import com.cabletech.commons.base.BaseBean;

public class PointOutputBean   extends BaseBean{
    private String regionID;
	private String startDate; //开始日期

	private String endDate; //结束日期

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}

