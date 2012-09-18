package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialAddressStorage extends BaseDomainObject {
    private String id;

    private String materialId;

    private String addressId;

    private Float oldStock;

    private Float newStock;

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

	public Float getOldStock() {
        return oldStock;
    }

    public void setOldStock(Float oldStock) {
        this.oldStock = oldStock;
    }

    public Float getNewStock() {
        return newStock;
    }

    public void setNewStock(Float newStock) {
        this.newStock = newStock;
    }

}
