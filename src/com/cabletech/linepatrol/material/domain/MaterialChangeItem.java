package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialChangeItem extends BaseDomainObject {
	/**
	 * ����������ϸ
	 */
	private static final long serialVersionUID = 4700328982018620676L;
	private int tid ;
	private int allotID;
	private int materialID;
	private String state;//����״̬ ���� ����
    private String changedate;//��������
    private String contractorid;//��ά
    private String addreid;//���ϴ�ŵ�ַ
	
	
	public String getChangedate() {
		return changedate;
	}
	public void setChangedate(String changedate) {
		this.changedate = changedate;
	}
	public String getContractorid() {
		return contractorid;
	}
	public void setContractorid(String contractorid) {
		this.contractorid = contractorid;
	}
	public String getAddreid() {
		return addreid;
	}
	public void setAddreid(String addreid) {
		this.addreid = addreid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getAllotID() {
		return allotID;
	}
	public void setAllotID(int allotID) {
		this.allotID = allotID;
	}
	public int getMaterialID() {
		return materialID;
	}
	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
	
}
