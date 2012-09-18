package com.cabletech.linepatrol.appraise.module;

public class AppraiseRuleResult {
	private String id;
	private AppraiseResult appraiseResult;
	private String ruleId;
	private float result;
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppraiseResult getAppraiseResult() {
		return appraiseResult;
	}

	public void setAppraiseResult(AppraiseResult appraiseResult) {
		this.appraiseResult = appraiseResult;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
