package com.cabletech.linepatrol.project.beans;

import java.util.HashMap;
import java.util.Map;

import com.cabletech.commons.base.BaseBean;

public class RemedyApplyBean extends BaseBean {
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

	private String mtotalFee;

	private String mtotalFeeMin;

	private String mtotalFeeMax;

	private String totalFee;

	private String totalFeeMin;

	private String totalFeeMax;

	private String remedyDateMin;

	private String remedyDataMax;

	private String contractorName;

	private String town;

	private String statusName;

	private String isSubmited;

	private String[] itemId;

	private String[] itemTypeId;

	private String[] itemName;

	private String[] itemType;

	private String[] itemUnit;

	private String[] itemUnitPrice;

	private String[] itemWorkNumber;

	private String[] itemFee;

	private String[] materialTypeUse;

	private String[] materialModelUse;

	private String[] materialUse;

	private String[] materialStorageAddrUse;

	private String[] materialStorageTypeUse;

	private String[] materialStorageNumberUse;

	private String[] materialUseNumberUse;

	private String[] materialUnitPriceUse;

	private String[] materialTypeRecycle;

	private String[] materialModeRecycle;

	private String[] materialRecycle;

	private String[] materialStorageAddrRecycle;

	private String[] materialStorageTypeRecycle;

	private String[] materialStorageNumberRecycle;

	private String[] materialUseNumberRecycle;

	private String approverId;

	private String reader;

	private String taskNames;
	private String[] taskStates;

	private String cancelUserId = "";
	private String cancelUserName = "";
	private String cancelReason = "";
	private String cancelTime;

	private String state;

	public String getId() {
		return id;
	}

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

	public String[] getMaterialTypeUse() {
		return materialTypeUse;
	}

	public void setMaterialTypeUse(String[] materialType) {
		this.materialTypeUse = materialType;
	}

	public String[] getMaterialModelUse() {
		return materialModelUse;
	}

	public void setMaterialModelUse(String[] materialModel) {
		this.materialModelUse = materialModel;
	}

	public String[] getMaterialUse() {
		return materialUse;
	}

	public void setMaterialUse(String[] material) {
		this.materialUse = material;
	}

	public String[] getMaterialStorageAddrUse() {
		return materialStorageAddrUse;
	}

	public void setMaterialStorageAddrUse(String[] materialStorageAddr) {
		this.materialStorageAddrUse = materialStorageAddr;
	}

	public String[] getMaterialStorageTypeUse() {
		return materialStorageTypeUse;
	}

	public void setMaterialStorageTypeUse(String[] materialStorageType) {
		this.materialStorageTypeUse = materialStorageType;
	}

	public String[] getMaterialStorageNumberUse() {
		return materialStorageNumberUse;
	}

	public void setMaterialStorageNumberUse(String[] materialStorageNumber) {
		this.materialStorageNumberUse = materialStorageNumber;
	}

	public String[] getMaterialUseNumberUse() {
		return materialUseNumberUse;
	}

	public void setMaterialUseNumberUse(String[] materialUseNumber) {
		this.materialUseNumberUse = materialUseNumber;
	}

	public String[] getMaterialUnitPriceUse() {
		return materialUnitPriceUse;
	}

	public void setMaterialUnitPriceUse(String[] materialUnitPrice) {
		this.materialUnitPriceUse = materialUnitPrice;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getIsSubmited() {
		return isSubmited;
	}

	public void setIsSubmited(String isSubmited) {
		this.isSubmited = isSubmited;
	}

	public String getMtotalFee() {
		return mtotalFee;
	}

	public void setMtotalFee(String mtotalFee) {
		this.mtotalFee = mtotalFee;
	}

	public String getMtotalFeeMin() {
		return mtotalFeeMin;
	}

	public void setMtotalFeeMin(String mtotalFeeMin) {
		this.mtotalFeeMin = mtotalFeeMin;
	}

	public String getMtotalFeeMax() {
		return mtotalFeeMax;
	}

	public void setMtotalFeeMax(String mtotalFeeMax) {
		this.mtotalFeeMax = mtotalFeeMax;
	}

	public String[] getMaterialTypeRecycle() {
		return materialTypeRecycle;
	}

	public void setMaterialTypeRecycle(String[] materialTypeRecycle) {
		this.materialTypeRecycle = materialTypeRecycle;
	}

	public String[] getMaterialModeRecycle() {
		return materialModeRecycle;
	}

	public void setMaterialModeRecycle(String[] materialModeRecycle) {
		this.materialModeRecycle = materialModeRecycle;
	}

	public String[] getMaterialRecycle() {
		return materialRecycle;
	}

	public void setMaterialRecycle(String[] materialRecycle) {
		this.materialRecycle = materialRecycle;
	}

	public String[] getMaterialStorageAddrRecycle() {
		return materialStorageAddrRecycle;
	}

	public void setMaterialStorageAddrRecycle(
			String[] materialStorageAddrRecycle) {
		this.materialStorageAddrRecycle = materialStorageAddrRecycle;
	}

	public String[] getMaterialStorageTypeRecycle() {
		return materialStorageTypeRecycle;
	}

	public void setMaterialStorageTypeRecycle(
			String[] materialStorageTypeRecycle) {
		this.materialStorageTypeRecycle = materialStorageTypeRecycle;
	}

	public String[] getMaterialStorageNumberRecycle() {
		return materialStorageNumberRecycle;
	}

	public void setMaterialStorageNumberRecycle(
			String[] materialStorageNumberRecycle) {
		this.materialStorageNumberRecycle = materialStorageNumberRecycle;
	}

	public String[] getMaterialUseNumberRecycle() {
		return materialUseNumberRecycle;
	}

	public void setMaterialUseNumberRecycle(String[] materialUseNumberRecycle) {
		this.materialUseNumberRecycle = materialUseNumberRecycle;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getTaskNames() {
		return taskNames;
	}

	public void setTaskNames(String taskNames) {
		this.taskNames = taskNames;
	}

	public String[] getTaskStates() {
		return taskStates;
	}

	public void setTaskStates(String[] taskStates) {
		this.taskStates = taskStates;
		this.taskNames = "";
		for (int i = 0; taskStates != null && i < taskStates.length; i++) {
			this.taskNames += taskStates[i];
			if (i < taskStates.length - 1) {
				this.taskNames += ",";
			}
		}
	}

	public String getCancelUserId() {
		return cancelUserId;
	}

	public void setCancelUserId(String cancelUserId) {
		this.cancelUserId = cancelUserId;
	}

	public String getCancelUserName() {
		return cancelUserName;
	}

	public void setCancelUserName(String cancelUserName) {
		this.cancelUserName = cancelUserName;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(String cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
