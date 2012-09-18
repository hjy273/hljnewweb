package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialStock extends BaseDomainObject {
	private String id;
	private String materialId;
	private String addressId;
	private double oldStock = 0.0;
	private double newStock = 0.0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public double getOldStock() {
		return oldStock;
	}

	public void setOldStock(double oldStock) {
		this.oldStock = oldStock;
	}

	public double getNewStock() {
		return newStock;
	}

	public void setNewStock(double newStock) {
		this.newStock = newStock;
	}
}
