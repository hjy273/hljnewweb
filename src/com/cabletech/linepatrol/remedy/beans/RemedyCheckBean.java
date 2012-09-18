package com.cabletech.linepatrol.remedy.beans;

import java.util.HashMap;
import java.util.Map;

import com.cabletech.commons.base.BaseBean;

public class RemedyCheckBean extends BaseBean {
    private String id;

    private String applyId;

    private String state;

    private String checkMan;

    private String checkDate;

    private String remark;

    private String nextProcessMan;

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

    private String totalFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNextProcessMan() {
        return nextProcessMan;
    }

    public void setNextProcessMan(String nextProcessMan) {
        this.nextProcessMan = nextProcessMan;
    }

    public String[] getItemId() {
        return itemId;
    }

    public String[] getItemTypeId() {
        return itemTypeId;
    }

    public String[] getItemName() {
        return itemName;
    }

    public String[] getItemType() {
        return itemType;
    }

    public String[] getItemUnit() {
        return itemUnit;
    }

    public String[] getItemUnitPrice() {
        return itemUnitPrice;
    }

    public String[] getItemWorkNumber() {
        return itemWorkNumber;
    }

    public String[] getItemFee() {
        return itemFee;
    }

    public String[] getMaterialType() {
        return materialType;
    }

    public String[] getMaterialModel() {
        return materialModel;
    }

    public String[] getMaterial() {
        return material;
    }

    public String[] getMaterialStorageAddr() {
        return materialStorageAddr;
    }

    public String[] getMaterialStorageType() {
        return materialStorageType;
    }

    public String[] getMaterialStorageNumber() {
        return materialStorageNumber;
    }

    public String[] getMaterialUseNumber() {
        return materialUseNumber;
    }

    public void setItemId(String[] itemId) {
        this.itemId = itemId;
    }

    public void setItemTypeId(String[] itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public void setItemName(String[] itemName) {
        this.itemName = itemName;
    }

    public void setItemType(String[] itemType) {
        this.itemType = itemType;
    }

    public void setItemUnit(String[] itemUnit) {
        this.itemUnit = itemUnit;
    }

    public void setItemUnitPrice(String[] itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    public void setItemWorkNumber(String[] itemWorkNumber) {
        this.itemWorkNumber = itemWorkNumber;
    }

    public void setItemFee(String[] itemFee) {
        this.itemFee = itemFee;
    }

    public void setMaterialType(String[] materialType) {
        this.materialType = materialType;
    }

    public void setMaterialModel(String[] materialModel) {
        this.materialModel = materialModel;
    }

    public void setMaterial(String[] material) {
        this.material = material;
    }

    public void setMaterialStorageAddr(String[] materialStorageAddr) {
        this.materialStorageAddr = materialStorageAddr;
    }

    public void setMaterialStorageType(String[] materialStorageType) {
        this.materialStorageType = materialStorageType;
    }

    public void setMaterialStorageNumber(String[] materialStorageNumber) {
        this.materialStorageNumber = materialStorageNumber;
    }

    public void setMaterialUseNumber(String[] materialUseNumber) {
        this.materialUseNumber = materialUseNumber;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
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

}
