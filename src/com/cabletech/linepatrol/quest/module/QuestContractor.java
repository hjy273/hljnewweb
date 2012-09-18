package com.cabletech.linepatrol.quest.module;

import com.cabletech.commons.base.BaseDomainObject;

//问卷参评部门
public class QuestContractor extends BaseDomainObject {
	public static final String ISSUE = "1";
	public static final String TEMPSAVE = "2";
	public static final String SAVE = "3";
	
	private String id;
	private String questId;//问卷ID
	private String conId;//代维单位ID
	private String state;//1：新发布问卷；2：已保存的 3：已提交的

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestId() {
		return questId;
	}

	public void setQuestId(String questId) {
		this.questId = questId;
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
