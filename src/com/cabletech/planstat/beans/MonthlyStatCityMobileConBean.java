package com.cabletech.planstat.beans;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

public class MonthlyStatCityMobileConBean extends BaseBean {
	private String regionID; // 区域ID

	private String regionName; // 区域名称

	private String beginYear; // 年份

	private String beginMonth; // 月份

	private String endYear; // 年份

	private String endMonth; // 月份

	private String beginTime; // 年份

	private String endTime; // 年份

	public MonthlyStatCityMobileConBean() {
		endYear = DateUtil.getNowYearStr();
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getRegionID() {
		return regionID;
	}

	public void setRegionID(String regionID) {
		this.regionID = regionID;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 组织最终的条件bean
	 * 
	 * @param bean
	 *            界面bean
	 * @param userInfo
	 *            用户
	 * @param regionName
	 *            区域名称，市移动用户登录时为空
	 * @return
	 */
	public void getOrganizedBean(UserInfo userInfo, String regionName,
			String regionNameInSession) {
		String userType = userInfo.getType();
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			this.regionName = regionName;
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			this.regionID = userInfo.getRegionid();
			this.regionName = regionNameInSession;
		} // 其它用户无权限
	}

	/**
	 * 输入对象
	 */
	public String toString() {
		String backString = this.getRegionName() + "," + this.getRegionID()
				+ "," + this.getEndYear() + "," + this.getEndMonth();
		return backString;

	}

	public String getBeginYear() {
		return beginYear;
	}

	public void setBeginYear(String beginYear) {
		this.beginYear = beginYear;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}