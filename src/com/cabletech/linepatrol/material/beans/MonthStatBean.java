package com.cabletech.linepatrol.material.beans;

import java.util.HashMap;
import java.util.Map;

import com.cabletech.commons.base.BaseBean;

public class MonthStatBean extends BaseBean {
    private String id;

    private String remedyCode;

    private String projectName;

    private String contractorId;

    private String townId;

    private String regionId;

    private String remedyAddress;

    private String remedyDate;

    private String remedyReason;

    private String remedySolve;

    private String creator;

    private String createTime;

    private String state;

    private String prevState;

    private String currentStepId;

    private String totalFee;

    private String totalFeeMin;

    private String totalFeeMax;

    private String remedyDateMin;

    private String remedyDataMax;

    private String contractorName;

    private String town;

    private String isSubmited;

    private String nextProcessMan;

    private String statusName;

    private String needToUpLevel;

    private String[] itemId;

    private String[] itemTypeId;

    private String[] itemName;

    private String[] itemType;

    private String[] itemUnit;

    private String[] itemUnitPrice;

    private String[] itemWorkNumber;

    private String[] itemFee;

    private String[] materialType;

    private String[] materialModel;

    private String[] material;

    private String[] materialStorageAddr;

    private String[] materialStorageType;

    private String[] materialStorageNumber;

    private String[] materialUseNumber;

    public String getIsSubmited() {
        return isSubmited;
    }

    public void setIsSubmited(String isSubmited) {
        this.isSubmited = isSubmited;
    }

    public String getNextProcessMan() {
        return nextProcessMan;
    }

    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public void setId(String id) {
        this.id = id;
    }

    public String getRemedyCode() {
        return remedyCode;
    }

    public void setRemedyCode(String remedyCode) {
        this.remedyCode = remedyCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRemedyAddress() {
        return remedyAddress;
    }

    public void setRemedyAddress(String remedyAddress) {
        this.remedyAddress = remedyAddress;
    }

    public String getRemedyDate() {
        return remedyDate;
    }

    public void setRemedyDate(String remedyDate) {
        this.remedyDate = remedyDate;
    }

    public String getRemedyReason() {
        return remedyReason;
    }

    public void setRemedyReason(String remedyReason) {
        this.remedyReason = remedyReason;
    }

    public String getRemedySolve() {
        return remedySolve;
    }

    public void setRemedySolve(String remedySolve) {
        this.remedySolve = remedySolve;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(String currentStepId) {
        this.currentStepId = currentStepId;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getNeedToUpLevel() {
        return needToUpLevel;
    }

    public void setNeedToUpLevel(String needToUpLevel) {
        this.needToUpLevel = needToUpLevel;
    }

    public String getTotalFeeMin() {
        return totalFeeMin;
    }

    public void setTotalFeeMin(String totalFeeMin) {
        this.totalFeeMin = totalFeeMin;
    }

    public String getTotalFeeMax() {
        return totalFeeMax;
    }

    public void setTotalFeeMax(String totalFeeMax) {
        this.totalFeeMax = totalFeeMax;
    }

    public String getRemedyDateMin() {
        return remedyDateMin;
    }

    public void setRemedyDateMin(String remedyDateMin) {
        this.remedyDateMin = remedyDateMin;
    }

    public String getRemedyDataMax() {
        return remedyDataMax;
    }

    public void setRemedyDataMax(String remedyDataMax) {
        this.remedyDataMax = remedyDataMax;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String[] getItemId() {
        return itemId;
    }

    public void setItemId(String[] itemId) {
        this.itemId = itemId;
    }

    public String[] getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(String[] itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String[] getItemName() {
        return itemName;
    }

    public void setItemName(String[] itemName) {
        this.itemName = itemName;
    }

    public String[] getItemType() {
        return itemType;
    }

    public void setItemType(String[] itemType) {
        this.itemType = itemType;
    }

    public String[] getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String[] itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String[] getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(String[] itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public String[] getItemWorkNumber() {
        return itemWorkNumber;
    }

    public void setItemWorkNumber(String[] itemWorkNumber) {
        this.itemWorkNumber = itemWorkNumber;
    }

    public String[] getItemFee() {
        return itemFee;
    }

    public void setItemFee(String[] itemFee) {
        this.itemFee = itemFee;
    }

    public Map packageInfo() {
        Map map = new HashMap();
        map.clear();
        map.put("item_id", itemId);
        map.put("item_type", itemType);
        map.put("item_unit", itemUnit);
        map.put("item_name", itemName);
        map.put("item_type_id", itemTypeId);
        map.put("item_unit_price", itemUnitPrice);
        map.put("item_work_number", itemWorkNumber);
        map.put("item_fee", itemFee);
        return map;
    }

    public String[] getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String[] materialType) {
        this.materialType = materialType;
    }

    public String[] getMaterialModel() {
        return materialModel;
    }

    public void setMaterialModel(String[] materialModel) {
        this.materialModel = materialModel;
    }

    public String[] getMaterial() {
        return material;
    }

    public void setMaterial(String[] material) {
        this.material = material;
    }

    public String[] getMaterialStorageAddr() {
        return materialStorageAddr;
    }

    public void setMaterialStorageAddr(String[] materialStorageAddr) {
        this.materialStorageAddr = materialStorageAddr;
    }

    public String[] getMaterialStorageType() {
        return materialStorageType;
    }

    public void setMaterialStorageType(String[] materialStorageType) {
        this.materialStorageType = materialStorageType;
    }

    public String[] getMaterialStorageNumber() {
        return materialStorageNumber;
    }

    public void setMaterialStorageNumber(String[] materialStorageNumber) {
        this.materialStorageNumber = materialStorageNumber;
    }

    public String[] getMaterialUseNumber() {
        return materialUseNumber;
    }

    public void setMaterialUseNumber(String[] materialUseNumber) {
        this.materialUseNumber = materialUseNumber;
    }

    public String getPrevState() {
        return prevState;
    }

    public void setPrevState(String prevState) {
        this.prevState = prevState;
    }
}
