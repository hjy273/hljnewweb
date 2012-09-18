package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialAddressStorage extends BaseDomainObject {
    private int id;

    private int materialId;

    private int addressId;

    private Float oldStock;

    private Float newStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
