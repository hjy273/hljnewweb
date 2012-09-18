package com.cabletech.analysis.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 处理当前指定时刻的巡检人数\短信数\ 以及各地区\代维单位\巡检组的在线人数
 */
public class CurrentTimeBean {
	private String intersectPoint;// 时间交叉点
	private int onlineNum;// 在线人数
	private int noteNum;// 短信数
	/* 可以是 区域在线人数列表 代维单位在线人数列表 巡检组在线人数列表 */
	private List numList;

	public CurrentTimeBean() {
		numList = new ArrayList();
	}

	public String getIntersectPoint() {
		return intersectPoint;
	}

	public void setIntersectPoint(String intersectPoint) {
		this.intersectPoint = intersectPoint;
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	public int getNoteNum() {
		return noteNum;
	}

	public void setNoteNum(int noteNum) {
		this.noteNum = noteNum;
	}

	public List getNumList() {
		return numList;
	}

	public void setNumList(List numList) {
		this.numList = numList;
	}

	public String toSting() {
		return ToStringBuilder.reflectionToString(this);
	}
}
