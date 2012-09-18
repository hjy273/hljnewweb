package com.cabletech.linepatrol.trouble.module;

import java.util.Date;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleInfo entity. @author MyEclipse Persistence Tools
 */

public class TroubleEoms extends BaseDomainObject  {

	// Fields

	private String id;
	private String troubleId;//π ’œµ•±‡∫≈
	private String eomsCode;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTroubleId() {
		return troubleId;
	}
	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}
	public String getEomsCode() {
		return eomsCode;
	}
	public void setEomsCode(String eomsCode) {
		this.eomsCode = eomsCode;
	}
	
	

}