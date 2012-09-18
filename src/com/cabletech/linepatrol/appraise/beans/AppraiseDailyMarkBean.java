package com.cabletech.linepatrol.appraise.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

/**
 * ��ά��λ�ճ����˿۷����Bean
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyMarkBean extends BaseCommonBean {

	private static final long serialVersionUID = 4429093499187917615L;
	
	private String id; // ϵͳ���
	private String dailyId; // �ճ�����ID
	private String ruleId; // ����ϸ��
	private String markDeductions; // �ճ��۷�ֵ
	private String remark; // �۷�˵��

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
