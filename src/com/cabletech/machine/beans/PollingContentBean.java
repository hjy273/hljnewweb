package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * �����SDH����Ĳ��Ѳ������
 * @author haozi
 *
 */
public class PollingContentBean extends BaseBean {
	private String cid;

	private String pid;

	private String isClean;// �豸�����Ƿ����(��/��)

	private String obveTemperature;// �豸�����¶�

	private String isToppilotlamp;// ���񶥶�ָʾ��״̬����/������

	private String isVeneerpilotlamp;// ����ָʾ��״̬����/������

	private String isDustproofClean;// �豸��������ࣨ��/��

	private String fanState;// ��������״̬

	private String machineFloorClean;// �����������

	private String machineTemperature;// �����¶�(����15��30��)

	private String machineHumidity;// ����ʪ��(����40����65��)

	private String ddfColligation;// DDF��/���߼����°���

	private String fiberProtect;// β�˱���

	private String ddfCableFast;// DDF�����½�ͷ������

	private String odfInterfacefast;// ODF��β�˽�ͷ������

	private String odfLabelCheck;// ODF/�豸��β�˱�ǩ�˲鲹��

	private String ddfCabelCheck;// DDF�����±�ǩ�˲鲹��

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
