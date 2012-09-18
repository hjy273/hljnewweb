package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * 接入层FSO巡检内容
 * 
 * @author haozi
 * 
 */
public class PollingConFsoBean extends BaseBean {
	private String fid;

	private String pid;

	private String isClean;// 设备表面是否清洁(是/否)

	private String obveTemperature;// 设备表面温度

	private String isMachinePilotlamp;// 设备指示灯状态

	private String searchLightPower;// 光功率查询

	private String powerColligation;// 尾纤/电源线绑扎

	private String powerLabelCheck;// 电源线/尾纤标签核查补贴

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getIsClean() {
		return isClean;
	}

	public void setIsClean(String isClean) {
		this.isClean = isClean;
	}

	public String getIsMachinePilotlamp() {
		return isMachinePilotlamp;
	}

	public void setIsMachinePilotlamp(String isMachinePilotlamp) {
		this.isMachinePilotlamp = isMachinePilotlamp;
	}

	public String getObveTemperature() {
		return obveTemperature;
	}

	public void setObveTemperature(String obveTemperature) {
		this.obveTemperature = obveTemperature;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPowerColligation() {
		return powerColligation;
	}

	public void setPowerColligation(String powerColligation) {
		this.powerColligation = powerColligation;
	}

	public String getPowerLabelCheck() {
		return powerLabelCheck;
	}

	public void setPowerLabelCheck(String powerLabelCheck) {
		this.powerLabelCheck = powerLabelCheck;
	}

	public String getSearchLightPower() {
		return searchLightPower;
	}

	public void setSearchLightPower(String searchLightPower) {
		this.searchLightPower = searchLightPower;
	}
}
