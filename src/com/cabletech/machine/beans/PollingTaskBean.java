package com.cabletech.machine.beans;

import com.cabletech.commons.base.BaseBean;
/**
 * �ƶ��豸Ѳ������
 * @author haozi
 *
 */
public class PollingTaskBean extends BaseBean {
	private String pid;

	private String tid;

	private String eid;

	private String aeid;

	private String beid;

	private String layer;//�������

	private String equipmentModel;//�豸�ͺ�

	private String machineNo;//�����

	private String powerType;//��Դ�ͺ�
	
	private String auditResult; //�˲���
	
	private String checkRemark;//�˲鱸ע

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getAeid() {
		return aeid;
	}

	public void setAeid(String aeid) {
		this.aeid = aeid;
	}

	public String getBeid() {
		return beid;
	}

	public void setBeid(String beid) {
		this.beid = beid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}
