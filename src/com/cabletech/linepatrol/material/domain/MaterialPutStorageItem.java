package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialPutStorageItem extends BaseDomainObject {
	private String id;//

	private String putStorageId;

	private int materialId;// 申请人

	private int addressId;// 用途

	private double count;
	
	private double applyCount;

	private String state;// 材料类型 新增还是利旧

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPutStorageId() {
		return putStorageId;
	}

	public void setPutStorageId(String putStorageId) {
		this.putStorageId = putStorageId;
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(double applyCount) {
		this.applyCount = applyCount;
	}

}
