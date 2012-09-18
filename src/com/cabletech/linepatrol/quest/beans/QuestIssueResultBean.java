package com.cabletech.linepatrol.quest.beans;

import com.cabletech.linepatrol.commons.beans.BaseCommonBean;

public class QuestIssueResultBean extends BaseCommonBean {
	private String comItemId;// 参评公司与评分项联合ID
	private String showValue;// 页面显示的值
	private String trueValue;// 数据库中存储的值

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
