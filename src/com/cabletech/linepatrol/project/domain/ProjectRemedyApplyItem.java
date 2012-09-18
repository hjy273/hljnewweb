package com.cabletech.linepatrol.project.domain;

import com.cabletech.commons.base.BaseDomainObject;

public class ProjectRemedyApplyItem extends BaseDomainObject {
	private String id;
	private int itemId;
	private int itemTypeId;
	private Float itemWorkNumber;
	private Float itemFee;
	private String applyId;
	private String itemTypeName;
	private Float itemUnitPrice = 0.0F;
	private String itemName;
	private String itemUnit;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	public Float getItemWorkNumber() {
		return itemWorkNumber;
	}
	public void setItemWorkNumber(Float itemWorkNumber) {
		this.itemWorkNumber = itemWorkNumber;
	}
	public Float getItemFee() {
		return itemFee;
	}
	public void setItemFee(Float itemFee) {
		this.itemFee = itemFee;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public Float getItemUnitPrice() {
		return itemUnitPrice;
	}
	public void setItemUnitPrice(Float itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

}
