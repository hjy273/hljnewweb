package com.cabletech.linepatrol.material.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * ≤ƒ¡œ≈Ãµ„
 * 
 */
public class MaterialUsedInfoBean extends BaseBean {
	
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
