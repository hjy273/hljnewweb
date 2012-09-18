package com.cabletech.linepatrol.material.beans;

import com.cabletech.commons.base.BaseBean;

public class MaterialStatBean extends BaseBean {
	public static final String YEAR_QUERY_MODE = "YEAR";
	public static final String MONTH_QUERY_MODE = "MONTH";
	public static final String QUARTER_QUERY_MODE = "QUARTER";
	public static final String OTHER_QUERY_MODE = "OTHER";

	private String beginDate;
	private String endDate;
	private String contractorId;
	private String materialType;
	private String materialMode;
	private String material;
	private String queryMode = OTHER_QUERY_MODE;
	private String year;
	private String month;
	private String quarter;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getMaterialMode() {
		return materialMode;
	}

	public void setMaterialMode(String materialMode) {
		this.materialMode = materialMode;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
}
