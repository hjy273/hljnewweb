package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * ����΢�����Ѳ������
 * @author haozi
 *
 */
public class PollingConMicroBean extends BaseBean {
	private String mid;

	private String pid;

	private String isClean;// �豸�����Ƿ����(��/��)

	private String obveTemperature;// �豸�����¶�

	private String isToppilotlamp;// ���񶥶�ָʾ��״̬����/������

	private String isVeneerpilotlamp;// ����ָʾ��״̬����/������

	private String isDustproofClean;// �豸��������ࣨ��/��

	private String fanState;// ��������״̬

	private String outdoorFast;// ���ⵥԪ���ӹ�

	private String machineTemperature;// �����¶�(����15��30��)

	private String machineHumidity;// ����ʪ��(����40����65��)

	private String ddfColligation;// DDF��/���߼����°���

	private String labelCheck;// 2M���¡����߽�ͷ������

	private String cabelCheck;// 2M���±�ǩ�˲鲹��

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
