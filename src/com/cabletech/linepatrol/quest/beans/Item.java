package com.cabletech.linepatrol.quest.beans;

import java.util.List;


public class Item {
	private String key;
	private String name;
	private String remark;
	private int score;

	private Type parentParent;//记录类别信息
	private Type parent;//记录分类信息
	
	private int parentParentScore;
	private int parentScore;
	
	//private List<String> manufValueList;

	public Item(String key, String name, String remark, int score) {
		this.key = key;
		this.name = name;
		this.remark = remark;
		this.score = score;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Type getParentParent() {
		return parentParent;
	}

	public void setParentParent(Type parentParent) {
		this.parentParent = parentParent;
	}

	public Type getParent() {
		return parent;
	}

	public void setParent(Type parent) {
		this.parent = parent;
	}

	public int getParentParentScore() {
		return parentParentScore;
	}

	public void setParentParentScore(int parentParentScore) {
		this.parentParentScore = parentParentScore;
	}

	public int getParentScore() {
		return parentScore;
	}

	public void setParentScore(int parentScore) {
		this.parentScore = parentScore;
	}
}
