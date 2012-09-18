package com.cabletech.linepatrol.maintenance.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTestEndwaste entity. @author MyEclipse Persistence Tools
 * 成端损耗分析
 */

public class TestEndWaste extends BaseDomainObject{

	private String id;
	private String anaylseId;
	private float endWaste;//成端损耗dB
	private String isStandardEnd;//是否合格
	private String problemAnalyseEnd;
	private String endRemark;
	
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
	public float getEndWaste() {
		return endWaste;
	}
	public void setEndWaste(float endWaste) {
		this.endWaste = endWaste;
	}
	public String getIsStandardEnd() {
		return isStandardEnd;
	}
	public void setIsStandardEnd(String isStandardEnd) {
		this.isStandardEnd = isStandardEnd;
	}
	public String getProblemAnalyseEnd() {
		return problemAnalyseEnd;
	}
	public void setProblemAnalyseEnd(String problemAnalyseEnd) {
		this.problemAnalyseEnd = problemAnalyseEnd;
	}
	public String getEndRemark() {
		return endRemark;
	}
	public void setEndRemark(String endRemark) {
		this.endRemark = endRemark;
	}
	
	
}