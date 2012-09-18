package com.cabletech.linepatrol.appraise.module;

import com.cabletech.commons.base.BaseDomainObject;

/**
 * ��ά��λ�ճ����˿۷����ʵ����
 * 
 * @author liusq
 * 
 */
public class AppraiseDailyMark extends BaseDomainObject {

	private static final long serialVersionUID = 4658430798181751279L;

	private String id; // ϵͳ���
	private AppraiseDaily appraiseDaily; // �ճ�����ID
	private String ruleId; // ����ϸ��
	private String markDeductions; // �ճ��۷�ֵ
	private String remark; // �۷�˵��

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
