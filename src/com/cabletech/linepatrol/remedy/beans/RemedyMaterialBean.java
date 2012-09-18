package com.cabletech.linepatrol.remedy.beans;

import com.cabletech.commons.base.BaseBean;

public class RemedyMaterialBean extends BaseBean {
    private String id;

    private String remedyCode;

    private String remedyId;

    private String remedyProjectName;

    private String materialId;

    private String materialName;

    private String materialStorageNumber;

    private String materialStorageAddress;

    private String materialStorageAddressId;

    private String materialStorageType;

    private String adjustOldNum;

    private String adjustNewNum;

    public String getRemedyCode() {
        return remedyCode;
    }

    public void setRemedyCode(String remedyCode) {
        this.remedyCode = remedyCode;
    }

    public String getRemedyId() {
        return remedyId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public String getMaterialStorageAddress() {
        return materialStorageAddress;
    }

    public String getMaterialStorageType() {
        return materialStorageType;
    }

    public String getAdjustOldNum() {
        return adjustOldNum;
    }

    public String getAdjustNewNum() {
        return adjustNewNum;
    }

    public void setRemedyId(String remedyId) {
        this.remedyId = remedyId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public void setMaterialStorageAddress(String materialStorageAddress) {
        this.materialStorageAddress = materialStorageAddress;
    }

    public void setMaterialStorageType(String materialStorageType) {
        this.materialStorageType = materialStorageType;
    }

    public void setAdjustOldNum(String adjustOldNum) {
        this.adjustOldNum = adjustOldNum;
    }

    public void setAdjustNewNum(String adjustNewNum) {
        this.adjustNewNum = adjustNewNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemedyProjectName() {
        return remedyProjectName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getMaterialStorageAddressId() {
        return materialStorageAddressId;
    }

    public void setRemedyProjectName(String remedyProjectName) {
        this.remedyProjectName = remedyProjectName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public void setMaterialStorageAddressId(String materialStorageAddressId) {
        this.materialStorageAddressId = materialStorageAddressId;
    }

    public String getMaterialStorageNumber() {
        return materialStorageNumber;
    }

    public void setMaterialStorageNumber(String materialStorageNumber) {
        this.materialStorageNumber = materialStorageNumber;
    }
}
