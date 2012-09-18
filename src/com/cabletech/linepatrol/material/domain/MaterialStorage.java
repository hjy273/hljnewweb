package com.cabletech.linepatrol.material.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class MaterialStorage extends BaseDomainObject {
	private String id;

	private String materialId;

	private String materialName;

	private String contractorId;

	private Float oldStock = new Float(0.0);

	private Float newStock = new Float(0.0);

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

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

}
