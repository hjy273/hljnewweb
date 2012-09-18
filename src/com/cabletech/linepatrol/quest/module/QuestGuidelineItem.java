package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//ָ����ϸ
public class QuestGuidelineItem extends BaseDomainObject {
	public static final String RADIO = "2";
	public static final String CHECKBOX = "3";
	public static final String NUMBER = "1";
	public static final String TEXT = "4";
	
	private String id;
	private String sortId;//����ID
	private String item;//ָ��������
	private int weightVal;//Ȩ��ֵ
	private String remark;//��ע
	private String options;//ѡ�����ͣ�2 ��ѡ��3 ��ѡ��1 ��ֵ 4 �ı�

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
