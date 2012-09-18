package com.cabletech.exterior.beans;

import com.cabletech.commons.base.BaseBean;

public class WatchExeStaResultBean extends BaseBean {
	private String regionName;

	private String regionID;

	private String contractorName;

	private String contractorID;

	private String infoNeeded;

	private String infoDone;

	private String watchExecuterate;

	private String alertCount;

	private String watchSize;

	public String getAlertCount() {
		return alertCount;
	}

	public void setAlertCount(String alertCount) {
		this.alertCount = alertCount;
	}

	public String getContractorID() {
		return contractorID;
	}

	public void setContractorID(String contractorID) {
		this.contractorID = contractorID;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getInfoDone() {
		return infoDone;
	}

	public void setInfoDone(String infoDone) {
		this.infoDone = infoDone;
	}

	public String getInfoNeeded() {
		return infoNeeded;
	}

	public void setInfoNeeded(String infoNeeded) {
		this.infoNeeded = infoNeeded;
	}

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getWatchExecuterate() {
		return watchExecuterate;
	}

	public void setWatchExecuterate(String watchExecuterate) {
		this.watchExecuterate = watchExecuterate;
	}

	public String getWatchSize() {
		return watchSize;
	}

	public void setWatchSize(String watchSize) {
		this.watchSize = watchSize;
	}

	public String toString() {
		String result = "";
		result = regionName + "," + contractorName + "," + watchSize + ","
				+ infoNeeded + "," + infoDone + "," + watchExecuterate + ","
				+ alertCount;
		return result;
	}
}
