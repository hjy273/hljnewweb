package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * �����FSOѲ������
 * 
 * @author haozi
 * 
 */
public class PollingConFsoBean extends BaseBean {
	private String fid;

	private String pid;

	private String isClean;// �豸�����Ƿ����(��/��)

	private String obveTemperature;// �豸�����¶�

	private String isMachinePilotlamp;// �豸ָʾ��״̬

	private String searchLightPower;// �⹦�ʲ�ѯ

	private String powerColligation;// β��/��Դ�߰���

	private String powerLabelCheck;// ��Դ��/β�˱�ǩ�˲鲹��

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
