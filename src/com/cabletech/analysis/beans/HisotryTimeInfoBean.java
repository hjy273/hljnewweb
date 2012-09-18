package com.cabletech.analysis.beans;

import java.util.Map;

import com.cabletech.commons.base.BaseBean;

/**
 * 选定具体日期界面上折线图热点后显示的信息
 * 
 */
public class HisotryTimeInfoBean extends BaseBean {
	private String intersectPoint; // 时间点，如：6：00：00

	private String onlineNumber; // 在线人数

	private String smTotal; // 短信总数

	private Map onlineStatList; // 在线信息列表

	private String onlineInfo; // 在线信息（当市代维用户登录时，数据库中已存组织好的结果）

	/**
	 * 得到时间点
	 * @return String
	 */
	public String getIntersectPoint() {
		return intersectPoint;
	}

	/**
	 * 设置时间点
	 * @param intersectPoint
	 *            时间点，如：06：00：00
	 */
	public void setIntersectPoint(String intersectPoint) {
		this.intersectPoint = intersectPoint;
	}

	/**
	 * 得到在线人数
	 * @return String
	 */
	public String getOnlineNumber() {
		return onlineNumber;
	}

	/**
	 * 设置在线人数
	 * @param onlineNumber
	 *            在线人数
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	/**
	 * 得到在线信息（用于非市代维用户）
	 * @return Map
	 */
	public Map getOnlineStatList() {
		return onlineStatList;
	}

	/**
	 * 设置在线信息（用于非市代维用户）
	 * @param onlineStatList
	 *            在线信息列表
	 */
	public void setOnlineStatList(Map onlineStatList) {
		this.onlineStatList = onlineStatList;
	}

	/**
	 * 得到短信总数
	 * @return String
	 */
	public String getSmTotal() {
		return smTotal;
	}

	/**
	 * 设置短信总数
	 * @param smTotal
	 *            短信总数
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}

	/**
	 * 得到在线信息（用于市代维用户）
	 * @return String
	 */
	public String getOnlineInfo() {
		return onlineInfo;
	}

	/**
	 * 设置在线信息（用于市代维用户）
	 * @param onlineInfo
	 *            在线信息（从数据库中取，用于市代维使用，已经组织好）
	 */
	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

}
