package com.cabletech.linepatrol.maintenance.module;


import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTestConnectorwaste entity. @author MyEclipse Persistence Tools
 * ��ͷ��ķ���
 */

public class TestConnectorWaste extends BaseDomainObject {

	// Fields

	private String id;
	private String anaylseId;
	private int orderNumber;//���
	private String connectorStation;//��ͷλ��
	private float waste;//���ֵdB
	private String problemAnalyse;//�������
	private String remark;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAnaylseId() {
		return anaylseId;
	}
	public void setAnaylseId(String anaylseId) {
		this.anaylseId = anaylseId;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getConnectorStation() {
		return connectorStation;
	}
	public void setConnectorStation(String connectorStation) {
		this.connectorStation = connectorStation;
	}
	public float getWaste() {
		return waste;
	}
	public void setWaste(float waste) {
		this.waste = waste;
	}
	public String getProblemAnalyse() {
		return problemAnalyse;
	}
	public void setProblemAnalyse(String problemAnalyse) {
		this.problemAnalyse = problemAnalyse;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
}