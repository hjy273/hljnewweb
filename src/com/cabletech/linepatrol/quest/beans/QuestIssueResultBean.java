package com.cabletech.linepatrol.quest.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class QuestIssueResultBean extends BaseCommonBean {
	private String comItemId;// ������˾������������ID
	private String showValue;// ҳ����ʾ��ֵ
	private String trueValue;// ���ݿ��д洢��ֵ

	public String getComItemId() {
		return comItemId;
	}

	public void setComItemId(String comItemId) {
		this.comItemId = comItemId;
	}

	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}

	public String getTrueValue() {
		return trueValue;
	}

	public void setTrueValue(String trueValue) {
		this.trueValue = trueValue;
	}
}
