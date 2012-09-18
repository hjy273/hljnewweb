package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//指标明细
public class QuestGuidelineItem extends BaseDomainObject {
	public static final String RADIO = "2";
	public static final String CHECKBOX = "3";
	public static final String NUMBER = "1";
	public static final String TEXT = "4";
	
	private String id;
	private String sortId;//分类ID
	private String item;//指标项名称
	private int weightVal;//权重值
	private String remark;//备注
	private String options;//选项类型：2 单选、3 多选、1 数值 4 文本

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getWeightVal() {
		return weightVal;
	}

	public void setWeightVal(int weightVal) {
		this.weightVal = weightVal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
}
