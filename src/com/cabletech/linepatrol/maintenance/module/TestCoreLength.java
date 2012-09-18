package com.cabletech.linepatrol.maintenance.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTestCorelength entity. @author MyEclipse Persistence Tools
 * ��о���ȷ���
 */

public class TestCoreLength extends BaseDomainObject {

	// Fields

	private String id;
	private String anaylseId;
	private float refractiveIndex;//����������
	private float pulseWidth;//��������
	private float coreLength;//о��km
	private String isProblem;
	private String problemAnalyseLen;//�������
	private String lengremark;
	
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
	public float getRefractiveIndex() {
		return refractiveIndex;
	}
	public void setRefractiveIndex(float refractiveIndex) {
		this.refractiveIndex = refractiveIndex;
	}
	public float getPulseWidth() {
		return pulseWidth;
	}
	public void setPulseWidth(float pulseWidth) {
		this.pulseWidth = pulseWidth;
	}
	public float getCoreLength() {
		return coreLength;
	}
	public void setCoreLength(float coreLength) {
		this.coreLength = coreLength;
	}
	public String getIsProblem() {
		return isProblem;
	}
	public void setIsProblem(String isProblem) {
		this.isProblem = isProblem;
	}
	public String getProblemAnalyseLen() {
		return problemAnalyseLen;
	}
	public void setProblemAnalyseLen(String problemAnalyseLen) {
		this.problemAnalyseLen = problemAnalyseLen;
	}
	public String getLengremark() {
		return lengremark;
	}
	public void setLengremark(String lengremark) {
		this.lengremark = lengremark;
	}
	
	

	
}