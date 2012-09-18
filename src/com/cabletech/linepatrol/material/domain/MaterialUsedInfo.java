package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialUsedInfo extends BaseDomainObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7192076484040600160L;
	private String mtid;
	private String[] addrid;
	private float[] newstock;
	private float[] oldstock;

	
	public String getMtid() {
		return mtid;
	}
	public void setMtid(String mtid) {
		this.mtid = mtid;
	}
	public String[] getAddrid() {
		return addrid;
	}
	public void setAddrid(String[] addrid) {
		this.addrid = addrid;
	}
	public float[] getNewstock() {
		return newstock;
	}
	public void setNewstock(float[] newstock) {
		this.newstock = newstock;
	}
	public float[] getOldstock() {
		return oldstock;
	}
	public void setOldstock(float[] oldstock) {
		this.oldstock = oldstock;
	}
}
