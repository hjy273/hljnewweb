package com.cabletech.linepatrol.remedy.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialStorage extends BaseDomainObject {
    private int id;

    private int materialId;

    private String contractorId;

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

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
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
