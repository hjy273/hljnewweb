package com.cabletech.linepatrol.maintenance.module;

import com.cabletech.commons.base.BaseDomainObject;

/**˥����������
 * LpTestDecayconstantId entity. @author MyEclipse Persistence Tools
 */

public class TestDecayConstant extends BaseDomainObject {

	// Fields
	private String id;
	private String anaylseId;
	private float decayConstant;//˥������dB/km
	private String isStandardDec;//�Ƿ�ϸ�
	private String problemAnalyseDec;//�������
	private String decayRemark;
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
	public float getDecayConstant() {
		return decayConstant;
	}
	public void setDecayConstant(float decayConstant) {
		this.decayConstant = decayConstant;
	}
	public String getIsStandardDec() {
		return isStandardDec;
	}
	public void setIsStandardDec(String isStandardDec) {
		this.isStandardDec = isStandardDec;
	}
	public String getProblemAnalyseDec() {
		return problemAnalyseDec;
	}
	public void setProblemAnalyseDec(String problemAnalyseDec) {
		this.problemAnalyseDec = problemAnalyseDec;
	}
	public String getDecayRemark() {
		return decayRemark;
	}
	public void setDecayRemark(String decayRemark) {
		this.decayRemark = decayRemark;
	}
	
	

}