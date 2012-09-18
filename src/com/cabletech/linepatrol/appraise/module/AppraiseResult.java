package com.cabletech.linepatrol.appraise.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cabletech.commons.base.BaseDomainObject;
import com.cabletech.commons.util.DateUtil;


public class AppraiseResult extends BaseDomainObject{
	private String id;
	private String contractorId;
	private String contractNO;
	private Date appraiseTime;
	private Date startDate;
	private Date endDate;
	private String tableId;
	private float result;
	private Date appraiseDate;
	private String appraiser;
	private List<AppraiseRuleResult> appraiseRuleResults = new ArrayList<AppraiseRuleResult>();
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

	public Date getAppraiseTime() {
		return appraiseTime;
	}

	public void setAppraiseTime(Date appraiseTime) {
		this.appraiseTime = appraiseTime;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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
	public String getAppraiseMonth2Str(){
		return DateUtil.DateToString(appraiseTime, "yyyy-MM");
	}
	public Map<String,Object> getAppraiseRuleResultForMap(){
		Map<String,Object> map=new HashMap<String, Object>();
		for(AppraiseRuleResult appraiseRuleResult:appraiseRuleResults){
			map.put(appraiseRuleResult.getRuleId(), appraiseRuleResult);
		}
		map.put("result", result);
		return map;
	}
	public String getConfirmResult() {
		return confirmResult;
	}

	public void setConfirmResult(String confirmResult) {
		this.confirmResult = confirmResult;
	}

}
