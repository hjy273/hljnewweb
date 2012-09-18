package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyMaterialBase extends BaseDomainObject {
    private String id;

    private int materialId;
    
    private Float materialUseNumber;

    private int addressId;
    
    private String materialStorageType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public Float getMaterialUseNumber() {
        return materialUseNumber;
    }

    public void setMaterialUseNumber(Float materialUseNumber) {
        this.materialUseNumber = materialUseNumber;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getMaterialStorageType() {
        return materialStorageType;
    }

    public void setMaterialStorageType(String materialStorageType) {
        this.materialStorageType = materialStorageType;
    }

}
