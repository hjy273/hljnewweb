package com.cabletech.linepatrol.maintenance.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTestOtheranalyse entity. @author MyEclipse Persistence Tools
 * ��������
 */

public class TestOtherAnalyse extends BaseDomainObject {

	// Fields

	private String id;
	private String anaylseId;
	private String analyseOther;//��������
	private String analyseResultOther;//�������
	private String remarkOther;
	
	
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
	public String getAnalyseOther() {
		return analyseOther;
	}
	public void setAnalyseOther(String analyseOther) {
		this.analyseOther = analyseOther;
	}
	public String getAnalyseResultOther() {
		return analyseResultOther;
	}
	public void setAnalyseResultOther(String analyseResultOther) {
		this.analyseResultOther = analyseResultOther;
	}
	public String getRemarkOther() {
		return remarkOther;
	}
	public void setRemarkOther(String remarkOther) {
		this.remarkOther = remarkOther;
	}


}