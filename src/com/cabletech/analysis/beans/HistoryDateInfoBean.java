package com.cabletech.analysis.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * 起止日期界面上折线图热点后显示的信息
 * 
 */
public class HistoryDateInfoBean extends BaseBean {
	private String intersectPoint; // 时间点，指日期

	private String onlineNumber; // 在线人数

	private String smTotal; // 短信总数

	private String distanceTotal; // 巡检总距离

	/**
	 * 得到巡检距离
	 * @return String
	 */
	public String getDistanceTotal() {
		return distanceTotal;
	}

	/**
	 * 设置巡检距离
	 * @param distanceTotal 巡检总距离
	 */
	public void setDistanceTotal(String distanceTotal) {
		this.distanceTotal = distanceTotal;
	}

	/**
	 * 得到时间点，指日期
	 * @return String
	 */
	public String getIntersectPoint() {
		return intersectPoint;
	}

	/**
	 * 设置时间点，指日期
	 * @param intersectPoint 时间点，指日期
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
	 * @param onlineNumber 在线人数
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
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
	 * @param smTotal 短信总数
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}
	
	/**
	 * 返回组织好的最终tooltip显示信息
	 * @param givenDate 选定日期
	 * @return String
	 */
	public  String getBackString(String givenDate){
		String backString = "所选日期:" + givenDate + "<br>" + "在线人数:"
		+ getOnlineNumber() + "<br>" + "短信总数："
		+ getSmTotal() + "<br>" + "巡检里程:"
		+ getDistanceTotal();
		return backString;
	}
}
