package com.cabletech.linepatrol.appraise.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * 代维单位日常考核扣分情况实体类
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyMark extends BaseDomainObject {

	private static final long serialVersionUID = 4658430798181751279L;

	private String id; // 系统编号
	private AppraiseDaily appraiseDaily; // 日常考核ID
	private String ruleId; // 考核细则
	private String markDeductions; // 日常扣分值
	private String remark; // 扣分说明

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AppraiseDaily getAppraiseDaily() {
		return appraiseDaily;
	}

	public void setAppraiseDaily(AppraiseDaily appraiseDaily) {
		this.appraiseDaily = appraiseDaily;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getMarkDeductions() {
		return markDeductions;
	}

	public void setMarkDeductions(String markDeductions) {
		this.markDeductions = markDeductions;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
