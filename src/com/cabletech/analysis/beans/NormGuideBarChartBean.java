package com.cabletech.analysis.beans;

public class NormGuideBarChartBean {
	private String categoryTitle;
	private double normValue = 0.0D;
	private double dareValue = 0.0D;
	private double interdictionTime = 0.0D;
	private double interdictionTimes = 0.0D;
	private double avgTime = 0.0D;
	private double cityAreaOutStandardNumber = 0.0D;

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public double getNormValue() {
		return normValue;
	}

	public void setNormValue(double normValue) {
		this.normValue = normValue;
	}

	public double getDareValue() {
		return dareValue;
	}

	public void setDareValue(double dareValue) {
		this.dareValue = dareValue;
	}

	public double getInterdictionTime() {
		return interdictionTime;
	}

	public void setInterdictionTime(double interdictionTime) {
		this.interdictionTime = interdictionTime;
	}

	public double getInterdictionTimes() {
		return interdictionTimes;
	}

	public void setInterdictionTimes(double interdictionTimes) {
		this.interdictionTimes = interdictionTimes;
	}

	public double getAvgTime() {
		return avgTime;
	}

	public void setAvgTime(double avgTime) {
		this.avgTime = avgTime;
	}

	public double getCityAreaOutStandardNumber() {
		return cityAreaOutStandardNumber;
	}

	public void setCityAreaOutStandardNumber(double cityAreaOutStandardNumber) {
		this.cityAreaOutStandardNumber = cityAreaOutStandardNumber;
	}
}