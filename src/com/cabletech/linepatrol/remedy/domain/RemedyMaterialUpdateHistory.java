package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class RemedyMaterialUpdateHistory extends BaseDomainObject {
    private String id;

    private String updateMan;

    private String remedyId;

    private int materialId;

    private int addressId;

    private Float oldNumber;

    private Float newNumber;

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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUpdateMan() {
        return updateMan;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public Float getOldNumber() {
        return oldNumber;
    }

    public Float getNewNumber() {
        return newNumber;
    }

    public void setUpdateMan(String updateMan) {
        this.updateMan = updateMan;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

    public void setOldNumber(Float oldNumber) {
        this.oldNumber = oldNumber;
    }

    public void setNewNumber(Float newNumber) {
        this.newNumber = newNumber;
    }

}
