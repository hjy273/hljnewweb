package com.cabletech.analysis.beans;

/*
 * 短信发送信息
 */
public class NoteInfoBean {
	private String titleName = "";
	private String titleId = "";
	private String firstTime = "";
	private String LastTime = "";
	private String simid = "";
	private int intTotal = 0;
	private int intPatrol = 0;
	private int intCollect = 0;
	private int intWatch = 0;
	private int intTrouble = 0;
	private int intOther = 0;

	public String getSimid() {
		return simid;
	}

	public void setSimid(String simid) {
		this.simid = simid;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}

	public String getLastTime() {
		return LastTime;
	}

	public void setLastTime(String lastTime) {
		LastTime = lastTime;
	}

	public int getIntTotal() {
		return intTotal;
	}

	public void setIntTotal(int intTotal) {
		this.intTotal += intTotal;
	}

	public int getIntPatrol() {
		return intPatrol;
	}

	public void setIntPatrol(int intPatrol) {
		this.intPatrol += intPatrol;
	}

	public int getIntCollect() {
		return intCollect;
	}

	public void setIntCollect(int intCollect) {
		this.intCollect += intCollect;
	}

	public int getIntWatch() {
		return intWatch;
	}

	public void setIntWatch(int intWatch) {
		this.intWatch += intWatch;
	}

	public int getIntTrouble() {
		return intTrouble;
	}

	public void setIntTrouble(int intTrouble) {
		this.intTrouble += intTrouble;
	}

	public int getIntOther() {
		return intOther;
	}

	public void setIntOther(int intOther) {
		this.intOther += intOther;
	}

	public void clear() {
		titleName = "";
		titleId = "";
		simid = "";
		firstTime = "";
		LastTime = "";
		intTotal = 0;
		intPatrol = 0;
		intCollect = 0;
		intWatch = 0;
		intTrouble = 0;
		intOther = 0;
	}
}
