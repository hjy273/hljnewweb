package com.cabletech.linepatrol.commons.beans;

import com.cabletech.uploadfile.formbean.BaseFileFormBean;

public class BaseCommonBean extends BaseFileFormBean {
	public static final String APPROVE_MAN = "01";//审核人
    public static final String APPROVE_TRANSFER_MAN = "02";//转审人
    public static final String APPROVE_READ = "032";//抄送人
    
	String[] materialUse;
	String[] materialStorageTypeUse;
	String[] materialStorageAddrUse;
	String[] materialUseNumberUse;

	String[] materialRecycle;
	String[] materialStorageTypeRecycle;
	String[] materialStorageAddrRecycle;
	String[] materialUseNumberRecycle;

	String objectId;
	String objectType;
	String approver;
	String reader;
	String approverType;
	String isTransferApproved;

	public String[] getMaterialUse() {
		return materialUse;
	}

	public void setMaterialUse(String[] materialUse) {
		this.materialUse = materialUse;
	}

	public String[] getMaterialStorageTypeUse() {
		return materialStorageTypeUse;
	}

	public void setMaterialStorageTypeUse(String[] materialStorageTypeUse) {
		this.materialStorageTypeUse = materialStorageTypeUse;
	}

	public String[] getMaterialStorageAddrUse() {
		return materialStorageAddrUse;
	}

	public void setMaterialStorageAddrUse(String[] materialStorageAddrUse) {
		this.materialStorageAddrUse = materialStorageAddrUse;
	}

	public String[] getMaterialUseNumberUse() {
		return materialUseNumberUse;
	}

	public void setMaterialUseNumberUse(String[] materialUseNumberUse) {
		this.materialUseNumberUse = materialUseNumberUse;
	}

	public String[] getMaterialRecycle() {
		return materialRecycle;
	}

	public void setMaterialRecycle(String[] materialRecycle) {
		this.materialRecycle = materialRecycle;
	}

	public String[] getMaterialStorageTypeRecycle() {
		return materialStorageTypeRecycle;
	}

	public void setMaterialStorageTypeRecycle(
			String[] materialStorageTypeRecycle) {
		this.materialStorageTypeRecycle = materialStorageTypeRecycle;
	}

	public String[] getMaterialStorageAddrRecycle() {
		return materialStorageAddrRecycle;
	}

	public void setMaterialStorageAddrRecycle(
			String[] materialStorageAddrRecycle) {
		this.materialStorageAddrRecycle = materialStorageAddrRecycle;
	}

	public String[] getMaterialUseNumberRecycle() {
		return materialUseNumberRecycle;
	}

	public void setMaterialUseNumberRecycle(String[] materialUseNumberRecycle) {
		this.materialUseNumberRecycle = materialUseNumberRecycle;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getApproverType() {
		return approverType;
	}

	public void setApproverType(String approverType) {
		this.approverType = approverType;
	}

	public String getIsTransferApproved() {
		return isTransferApproved;
	}

	public void setIsTransferApproved(String isTransferApproved) {
		this.isTransferApproved = isTransferApproved;
	}
}
