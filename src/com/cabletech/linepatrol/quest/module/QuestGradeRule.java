package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//评分项
public class QuestGradeRule extends BaseDomainObject {
	private String id;
	private String itemId;//指标项ID
	private String gradeExplain;//评分说明
	private int mark;//分值

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getGradeExplain() {
		return gradeExplain;
	}

	public void setGradeExplain(String gradeExplain) {
		this.gradeExplain = gradeExplain;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}
