package com.cabletech.linepatrol.appraise.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * 代维单位日常考核扣分情况Bean
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyMarkBean extends BaseCommonBean {

	private static final long serialVersionUID = 4429093499187917615L;
	
	private String id; // 系统编号
	private String dailyId; // 日常考核ID
	private String ruleId; // 考核细则
	private String markDeductions; // 日常扣分值
	private String remark; // 扣分说明

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
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
