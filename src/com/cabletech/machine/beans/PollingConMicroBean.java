package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * 接入微波层的巡检内容
 * @author haozi
 *
 */
public class PollingConMicroBean extends BaseBean {
	private String mid;

	private String pid;

	private String isClean;// 设备表面是否清洁(是/否)

	private String obveTemperature;// 设备表面温度

	private String isToppilotlamp;// 机柜顶端指示灯状态（亮/不亮）

	private String isVeneerpilotlamp;// 单板指示灯状态（亮/不亮）

	private String isDustproofClean;// 设备防尘网清洁（是/否）

	private String fanState;// 风扇运行状态

	private String outdoorFast;// 室外单元检查加固

	private String machineTemperature;// 机房温度(正常15～30℃)

	private String machineHumidity;// 机房湿度(正常40％～65％)

	private String ddfColligation;// DDF架/走线架线缆绑扎

	private String labelCheck;// 2M线缆、馈线接头检查紧固

	private String cabelCheck;// 2M线缆标签核查补贴

	public String getCabelCheck() {
		return cabelCheck;
	}

	public void setCabelCheck(String cabelCheck) {
		this.cabelCheck = cabelCheck;
	}

	public String getDdfColligation() {
		return ddfColligation;
	}

	public void setDdfColligation(String ddfColligation) {
		this.ddfColligation = ddfColligation;
	}

	public String getFanState() {
		return fanState;
	}

	public void setFanState(String fanState) {
		this.fanState = fanState;
	}

	public String getIsClean() {
		return isClean;
	}

	public void setIsClean(String isClean) {
		this.isClean = isClean;
	}

	public String getIsDustproofClean() {
		return isDustproofClean;
	}

	public void setIsDustproofClean(String isDustproofClean) {
		this.isDustproofClean = isDustproofClean;
	}

	public String getIsToppilotlamp() {
		return isToppilotlamp;
	}

	public void setIsToppilotlamp(String isToppilotlamp) {
		this.isToppilotlamp = isToppilotlamp;
	}

	public String getIsVeneerpilotlamp() {
		return isVeneerpilotlamp;
	}

	public void setIsVeneerpilotlamp(String isVeneerpilotlamp) {
		this.isVeneerpilotlamp = isVeneerpilotlamp;
	}

	public String getLabelCheck() {
		return labelCheck;
	}

	public void setLabelCheck(String labelCheck) {
		this.labelCheck = labelCheck;
	}

	public String getMachineHumidity() {
		return machineHumidity;
	}

	public void setMachineHumidity(String machineHumidity) {
		this.machineHumidity = machineHumidity;
	}

	public String getMachineTemperature() {
		return machineTemperature;
	}

	public void setMachineTemperature(String machineTemperature) {
		this.machineTemperature = machineTemperature;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getObveTemperature() {
		return obveTemperature;
	}

	public void setObveTemperature(String obveTemperature) {
		this.obveTemperature = obveTemperature;
	}

	public String getOutdoorFast() {
		return outdoorFast;
	}

	public void setOutdoorFast(String outdoorFast) {
		this.outdoorFast = outdoorFast;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
