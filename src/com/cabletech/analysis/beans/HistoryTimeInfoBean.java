package com.cabletech.analysis.beans;

import java.util.Iterator;
import java.util.Map;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * 选定具体日期界面上折线图热点后显示的信息
 * 
 */
public class HistoryTimeInfoBean extends BaseBean {
	private String intersectPoint; // 时间点，如：6：00：00

	private String onlineNumber; // 在线人数

	private String smTotal; // 短信总数

	private Map onlineStatList; // 在线信息列表

	private String onlineInfo; // 在线信息（当市代维用户登录时，数据库中已存组织好的结果）
    
	private GisConInfo config = GisConInfo.newInstance();

	private DateUtil dateUtil = new DateUtil();

	/**
	 * 得到时间点
	 * 
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
	 * 
	 * @param boParam  传入的BO参数（封装）
	 * @return interSectPoint
	 */
	public String getFinalIntersectPoint(HistoryWorkInfoCreateBOParam boParam) {
		String givenDateAndTime = boParam.getGivenDateAndTime();
		String endPoint = givenDateAndTime.substring(givenDateAndTime
				.indexOf(" ") + 1); // 得到终止时间时分秒
		long spacingtime = 1000L * 60 * Integer.parseInt(config
				.getSpacingTime());
		long startDateAndTimeLong = dateUtil
				.strDateAndTimeToLong(boParam.getGivenDateAndTime())
				- spacingtime; // 得到开始时间毫秒数
		String startPoint = dateUtil.longTostrTime(startDateAndTimeLong,
				"HH:mm:ss"); // 得到开始时间时分秒
		String interSectPoint = startPoint + "-" + endPoint;
        return interSectPoint;
	}
	
	/**
	 * 得到在线人数
	 * 
	 * @return String
	 */
	public String getOnlineNumber() {
		return onlineNumber;
	}

	/**
	 * 设置在线人数
	 * 
	 * @param onlineNumber
	 *            在线人数
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	/**
	 * 得到在线信息（用于非市代维用户）
	 * 
	 * @return Map
	 */
	public Map getOnlineStatList() {
		return onlineStatList;
	}

	/**
	 * 设置在线信息（用于非市代维用户）
	 * 
	 * @param onlineStatList
	 *            在线信息列表
	 */
	public void setOnlineStatList(Map onlineStatList) {
		this.onlineStatList = onlineStatList;
	}

	/**
	 * 得到短信总数
	 * 
	 * @return String
	 */
	public String getSmTotal() {
		return smTotal;
	}

	/**
	 * 设置短信总数
	 * 
	 * @param smTotal
	 *            短信总数
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}

	/**
	 * 得到在线信息（用于市代维用户）
	 * 
	 * @return String
	 */
	public String getOnlineInfo() {
		return onlineInfo;
	}

	/**
	 * 设置在线信息（用于市代维用户）
	 * 
	 * @param onlineInfo
	 *            在线信息（从数据库中取，用于市代维使用，已经组织好）
	 */
	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

	/**
	 * 返回组织好的最终tooltip显示信息
	 * 
	 * @param givenDate
	 *            选定时段
	 * @return String
	 */
	public String getBackString(UserInfo userInfo) {
		String backString = "所选时间:" + getIntersectPoint() + "<br>"
				+ "在线人数:" + getOnlineNumber() + "<br>" + "短信总数："
				+ getSmTotal() + "<br>";
		// 如果是市代维用户,查询所得数据中已经组织好了，不需再组织
		if (UserType.CONTRACTOR.equals(userInfo.getType())) {
			backString += "在线人员列表:" + "<br>" + getOnlineInfo();
		} else { // 不是市代维，需要组织“在线人员列表”返回信息
			backString += "在线人员列表:" + "<br>";
			Map map = getOnlineStatList();
			Iterator it = map.keySet().iterator();
			int i = 0; // 控制奇偶次数，保持每循环两次加上一个<br>
			while (it.hasNext()) {
				i++;
				// key可能为regionname,可能为contractorname
				String key = it.next().toString();
				String onlineNumber = (String) map.get(key); // 在线人数
				backString += key + ":" + onlineNumber + "人     ";
				if (i % 2 == 0) {
					backString += "<br>";
				}
			}
		}
		return backString;
	}

}
