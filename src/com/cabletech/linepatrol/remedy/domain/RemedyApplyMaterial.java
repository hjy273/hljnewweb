package com.cabletech.linepatrol.remedy.domain;

public class RemedyApplyMaterial extends RemedyMaterialBase {
	private String id;

	private String remedyId;

	private int materialId;

	private Float materialUseNumber;

	private int addressId;

	private String materialStorageType;

	private Float materialStorageNumber;

	private Float materialUnitPrice;

	private Float materialPrice;


	public Float getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(Float materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Float getMaterialUnitPrice() {
		return materialUnitPrice;
	}

	public void setMaterialUnitPrice(Float materialUnitPrice) {
		this.materialUnitPrice = materialUnitPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemedyId() {
		return remedyId;
	}

	public void setRemedyId(String remedyId) {
		this.remedyId = remedyId;
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

	public Float getMaterialStorageNumber() {
		return materialStorageNumber;
	}

	public void setMaterialStorageNumber(Float materialStorageNumber) {
		this.materialStorageNumber = materialStorageNumber;
	}

}
