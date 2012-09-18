package com.cabletech.sparepartmanage.beans;

import com.cabletech.commons.base.BaseBean;

public class SparepartBaseInfoBean extends BaseBean {
	private String sparePartId;
	private String sparePartName;
	private String sparePartModel;
	private String softwareVersion;
	private String productFactory;
	private String sparePartRemark;
	private String sparePartState;//备件状态 1:表示使用中 0:表示未使用

	public String getSparePartName() {
		return sparePartName;
	}

	public void setSparePartName(String sparePartName) {
		this.sparePartName = sparePartName;
	}

	public String getSparePartModel() {
		return sparePartModel;
	}

	public void setSparePartModel(String sparePartModel) {
		this.sparePartModel = sparePartModel;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getProductFactory() {
		return productFactory;
	}

	public void setProductFactory(String productFactory) {
		this.productFactory = productFactory;
	}

	public String getSparePartRemark() {
		return sparePartRemark;
	}

	public void setSparePartRemark(String sparePartRemark) {
		this.sparePartRemark = sparePartRemark;
	}

	public String getSparePartId() {
		return sparePartId;
	}

	public void setSparePartId(String sparePartId) {
		this.sparePartId = sparePartId;
	}

	public String getSparePartState() {
		return sparePartState;
	}

	public void setSparePartState(String sparePartState) {
		this.sparePartState = sparePartState;
	}

}
