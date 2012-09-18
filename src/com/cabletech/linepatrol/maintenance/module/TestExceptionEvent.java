package com.cabletech.linepatrol.maintenance.module;

import java.math.BigDecimal;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * �쳣�¼�����
 * LpTestExceptionevent entity. @author MyEclipse Persistence Tools
 */

public class TestExceptionEvent extends BaseDomainObject {

	// Fields

	private String id;
	private String anaylseId;
	private int orderNumberExe;//���
	private String eventStation;//�¼�λ��
	private float wasteExe;//���ֵdB
	private String problemAnalyseExe;//�������
	private String remarkExe;
	
	
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
	public int getOrderNumberExe() {
		return orderNumberExe;
	}
	public void setOrderNumberExe(int orderNumberExe) {
		this.orderNumberExe = orderNumberExe;
	}
	public String getEventStation() {
		return eventStation;
	}
	public void setEventStation(String eventStation) {
		this.eventStation = eventStation;
	}
	public float getWasteExe() {
		return wasteExe;
	}
	public void setWasteExe(float wasteExe) {
		this.wasteExe = wasteExe;
	}
	public String getProblemAnalyseExe() {
		return problemAnalyseExe;
	}
	public void setProblemAnalyseExe(String problemAnalyseExe) {
		this.problemAnalyseExe = problemAnalyseExe;
	}
	public String getRemarkExe() {
		return remarkExe;
	}
	public void setRemarkExe(String remarkExe) {
		this.remarkExe = remarkExe;
	}
	

	
}