package com.cabletech.linepatrol.appraise.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cabletech.linepatrol.appraise.module.AppraiseRuleResult;
import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class AppraiseResultBean extends BaseCommonBean {
	private String id;
	private String contractorId;
	private String contractNO;
	private String appraiseTime;
	private String startDate;
	private String endDate;
	private String tableId;
	private float result;
	private Date appraiseDate;
	private String appraiser;
	private String confirmResult;
	private String fromDate;
	private String toDate;
	private String score;
	private String scope=">=";
	private List<AppraiseRuleResult> appraiseRuleResults = new ArrayList<AppraiseRuleResult>();
	private String ids;
	private String mark;
	private String type;
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

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

	public String getAppraiseTime() {
		return appraiseTime;
	}

	public void setAppraiseTime(String appraiseTime) {
		this.appraiseTime = appraiseTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
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

	public List<AppraiseRuleResult> getAppraiseRuleResults() {
		return appraiseRuleResults;
	}

	public void setAppraiseRuleResults(List<AppraiseRuleResult> appraiseRuleResults) {
		this.appraiseRuleResults = appraiseRuleResults;
	}

	public void addAppraiseRuleResult(AppraiseRuleResult appraiseRuleResult) {
		this.appraiseRuleResults.add(appraiseRuleResult);
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	public void setAppriaseMonthFormat(){
		String[] months=appraiseTime.split("/");
		if(months.length>0){
			appraiseTime=months[0]+"/"+months[1];
		}
	}
	public void setAppraiseDateFormat(){
		String[] startDates=startDate.split(" ");
		if(startDates.length>0){
			startDate=startDates[0];
		}
		String[] endDates=endDate.split(" ");
		if(endDates.length>0){
			endDate=endDates[0];
		}
	}


	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setIdsByStrs(String[] strs){
		if(strs.length>0){
			ids="'"+strs[0]+"'";
			for(int index=1;index<strs.length;index++){
				ids+=",'"+strs[index]+"'";
			}
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
