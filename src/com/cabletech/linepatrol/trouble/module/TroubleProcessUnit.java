package com.cabletech.linepatrol.trouble.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * LpTroubleProcessUnit entity. @author MyEclipse Persistence Tools
 */

public class TroubleProcessUnit extends BaseDomainObject {

	// Fields

	private String id;
	private String troubleId;//故障单系统编号
	private String processUnitId;//故障单处理单位编号
	private String troublePid;//故障流程实例编号
	private String state;

	// Constructors

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/** default constructor */
	public TroubleProcessUnit() {
	}

	/** minimal constructor */
	public TroubleProcessUnit(String id) {
		this.id = id;
	}

	/** full constructor */
	public TroubleProcessUnit(String id, String troubleId,
			String processUnitId, String troublePid) {
		this.id = id;
		this.troubleId = troubleId;
		this.processUnitId = processUnitId;
		this.troublePid = troublePid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTroubleId() {
		return this.troubleId;
	}

	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}

	public String getProcessUnitId() {
		return this.processUnitId;
	}

	public void setProcessUnitId(String processUnitId) {
		this.processUnitId = processUnitId;
	}

	public String getTroublePid() {
		return this.troublePid;
	}

	public void setTroublePid(String troublePid) {
		this.troublePid = troublePid;
	}

}