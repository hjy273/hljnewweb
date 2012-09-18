package com.cabletech.linepatrol.appraise.beans;

import java.util.Date;

import com.cabletech.commons.base.BaseBean;

public class AppraiseYearResultBean extends BaseBean {
	private String id;
	private String contractorId;
	private String contractNO;
	private String year;
	private Float month;
	private Float monthWeight;
	private Float monthResult;
	private Float special;
	private Float specialWeight;
	private Float specialResult;
	private Float trouble;
	private Float troubleWeight;
	private Float troubleResult;
	private Float yearend;
	private Float yearendWeight;
	private Float yearendResult;
	private Float result;
	private Date appraiseDate;
	private String appraiser;
	private String confirmResult;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractorId() {
		return contractorId;
	}
	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}
	public String getContractNO() {
		return contractNO;
	}
	public void setContractNO(String contractNO) {
		this.contractNO = contractNO;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Float getMonth() {
		return month;
	}
	public void setMonth(Float month) {
		this.month = month;
	}
	public Float getMonthWeight() {
		return monthWeight;
	}
	public void setMonthWeight(Float monthWeight) {
		this.monthWeight = monthWeight;
	}
	public Float getMonthResult() {
		return monthResult;
	}
	public void setMonthResult(Float monthResult) {
		this.monthResult = monthResult;
	}
	public Float getSpecial() {
		return special;
	}
	public void setSpecial(Float special) {
		this.special = special;
	}
	public Float getSpecialWeight() {
		return specialWeight;
	}
	public void setSpecialWeight(Float specialWeight) {
		this.specialWeight = specialWeight;
	}
	public Float getSpecialResult() {
		return specialResult;
	}
	public void setSpecialResult(Float specialResult) {
		this.specialResult = specialResult;
	}
	public Float getTrouble() {
		return trouble;
	}
	public void setTrouble(Float trouble) {
		this.trouble = trouble;
	}
	public Float getTroubleWeight() {
		return troubleWeight;
	}
	public void setTroubleWeight(Float troubleWeight) {
		this.troubleWeight = troubleWeight;
	}
	public Float getTroubleResult() {
		return troubleResult;
	}
	public void setTroubleResult(Float troubleResult) {
		this.troubleResult = troubleResult;
	}
	public Float getYearend() {
		return yearend;
	}
	public void setYearend(Float yearend) {
		this.yearend = yearend;
	}
	public Float getYearendWeight() {
		return yearendWeight;
	}
	public void setYearendWeight(Float yearendWeight) {
		this.yearendWeight = yearendWeight;
	}
	public Float getYearendResult() {
		return yearendResult;
	}
	public void setYearendResult(Float yearendResult) {
		this.yearendResult = yearendResult;
	}
	public Date getAppraiseDate() {
		return appraiseDate;
	}
	public void setAppraiseDate(Date appraiseDate) {
		this.appraiseDate = appraiseDate;
	}
	public String getAppraiser() {
		return appraiser;
	}
	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}
	public String getConfirmResult() {
		return confirmResult;
	}
	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}
	public Float getResult() {
		return result;
	}
	public void setResult(Float result) {
		this.result = result;
	}
	
}
