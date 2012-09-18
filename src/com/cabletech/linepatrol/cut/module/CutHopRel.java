package com.cabletech.linepatrol.cut.module;

import com.cabletech.commons.base.BaseDomainObject;

public class CutHopRel extends BaseDomainObject {
	/**
	 * 割接与中继段关系基类
	 */
	private static final long serialVersionUID = 2226936657665207512L;
	private String id;
	private String cutId;
	private String sublineId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutId() {
		return cutId;
	}

	public void setCutId(String cutId) {
		this.cutId = cutId;
	}

	public String getSublineId() {
		return sublineId;
	}

	public void setSublineId(String sublineId) {
		this.sublineId = sublineId;
	}
}
