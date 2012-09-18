package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * 接入层SDH与核心层的巡检内容
 * @author haozi
 *
 */
public class PollingContentBean extends BaseBean {
	private String cid;

	private String pid;

	private String isClean;// 设备表面是否清洁(是/否)

	private String obveTemperature;// 设备表面温度

	private String isToppilotlamp;// 机柜顶端指示灯状态（亮/不亮）

	private String isVeneerpilotlamp;// 单板指示灯状态（亮/不亮）

	private String isDustproofClean;// 设备防尘网清洁（是/否）

	private String fanState;// 风扇运行状态

	private String machineFloorClean;// 机房地面清洁

	private String machineTemperature;// 机房温度(正常15～30℃)

	private String machineHumidity;// 机房湿度(正常40％～65％)

	private String ddfColligation;// DDF架/走线架线缆绑扎

	private String fiberProtect;// 尾纤保护

	private String ddfCableFast;// DDF架线缆接头检查紧固

	private String odfInterfacefast;// ODF架尾纤接头检查紧固

	private String odfLabelCheck;// ODF/设备侧尾纤标签核查补贴

	private String ddfCabelCheck;// DDF架线缆标签核查补贴

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getDdfCabelCheck() {
		return ddfCabelCheck;
	}

	public void setDdfCabelCheck(String ddfCabelCheck) {
		this.ddfCabelCheck = ddfCabelCheck;
	}

	public String getDdfCableFast() {
		return ddfCableFast;
	}

	public void setDdfCableFast(String ddfCableFast) {
		this.ddfCableFast = ddfCableFast;
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

	public String getFiberProtect() {
		return fiberProtect;
	}

	public void setFiberProtect(String fiberProtect) {
		this.fiberProtect = fiberProtect;
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

	public String getMachineFloorClean() {
		return machineFloorClean;
	}

	public void setMachineFloorClean(String machineFloorClean) {
		this.machineFloorClean = machineFloorClean;
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

	public String getObveTemperature() {
		return obveTemperature;
	}

	public void setObveTemperature(String obveTemperature) {
		this.obveTemperature = obveTemperature;
	}

	public String getOdfInterfacefast() {
		return odfInterfacefast;
	}

	public void setOdfInterfacefast(String odfInterfacefast) {
		this.odfInterfacefast = odfInterfacefast;
	}

	public String getOdfLabelCheck() {
		return odfLabelCheck;
	}

	public void setOdfLabelCheck(String odfLabelCheck) {
		this.odfLabelCheck = odfLabelCheck;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
