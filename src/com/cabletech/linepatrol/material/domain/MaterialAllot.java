package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialAllot extends BaseDomainObject {
	/**
	 * 材料调拨信息
	 */
	private static final long serialVersionUID = -1168930234619650306L;
	private int tid ;
	private String changer;//调拨人
	private String changedate;
	private String remark;
	
	private int[] oldaddressid;
	private int[] newaddressid;
	private String[] oldcontractorid;
	private String[] newcontractorid;
	private int[] materialID;
	private float[] newstock;
	private float[] oldstock;
	private int[]  addrstockid;//材料存放地点库存id
	private float[] newstockAddr;//原来材料存放地点库存新增数量
	private float[] oldstockAddr;//原来材料存放地点库存利旧数量
	private int[] mtstockid; //材料库存表的id
	
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getChanger() {
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int[] getOldaddressid() {
		return oldaddressid;
	}
	public void setOldaddressid(int[] oldaddressid) {
		this.oldaddressid = oldaddressid;
	}
	public int[] getNewaddressid() {
		return newaddressid;
	}
	public void setNewaddressid(int[] newaddressid) {
		this.newaddressid = newaddressid;
	}
	public String[] getOldcontractorid() {
		return oldcontractorid;
	}
	public void setOldcontractorid(String[] oldcontractorid) {
		this.oldcontractorid = oldcontractorid;
	}
	public String[] getNewcontractorid() {
		return newcontractorid;
	}
	public void setNewcontractorid(String[] newcontractorid) {
		this.newcontractorid = newcontractorid;
	}
	
	
	
	public int[] getMaterialID() {
		return materialID;
	}
	public void setMaterialID(int[] materialID) {
		this.materialID = materialID;
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
	
	public int[] getAddrstockid() {
		return addrstockid;
	}
	public void setAddrstockid(int[] addrstockid) {
		this.addrstockid = addrstockid;
	}
	public float[] getOldstockAddr() {
		return oldstockAddr;
	}
	public void setOldstockAddr(float[] oldstockAddr) {
		this.oldstockAddr = oldstockAddr;
	}
	public float[] getNewstockAddr() {
		return newstockAddr;
	}
	public void setNewstockAddr(float[] newstockAddr) {
		this.newstockAddr = newstockAddr;
	}
	public int[] getMtstockid() {
		return mtstockid;
	}
	public void setMtstockid(int[] mtstockid) {
		this.mtstockid = mtstockid;
	}
}
