package com.cabletech.planstat.beans;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

public class MonthlyStatCityMobileConBean extends BaseBean {
	private String regionID; // ����ID

	private String regionName; // ��������

	private String beginYear; // ���

	private String beginMonth; // �·�

	private String endYear; // ���

	private String endMonth; // �·�

	private String beginTime; // ���

	private String endTime; // ���

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
	 * ��֯���յ�����bean
	 * 
	 * @param bean
	 *            ����bean
	 * @param userInfo
	 *            �û�
	 * @param regionName
	 *            �������ƣ����ƶ��û���¼ʱΪ��
	 * @return
	 */
	public void getOrganizedBean(UserInfo userInfo, String regionName,
			String regionNameInSession) {
		String userType = userInfo.getType();
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			this.regionName = regionName;
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			this.regionID = userInfo.getRegionid();
			this.regionName = regionNameInSession;
		} // �����û���Ȩ��
	}

	/**
	 * �������
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