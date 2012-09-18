package com.cabletech.analysis.beans;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;

public class HistoryWorkInfoCreateBOParam extends BaseBean {
	private HistoryWorkInfoConditionBean bean;  //用户界面上查询时输入的条件bean

	private UserInfo userInfo; //用户信息

	private String givenDate = "0"; //用户是否选择了某一天，默认没有选择，值为“0”

	private String givenDateAndTime; //时段信息

	private String smRangeID; //短信功能中区域ID

    /**
     * 构造方法
     * @param userInfo  用户信息
     * @param bean  用户界面上查询时输入的条件bean
     * @param givenDate 用户是否选择了某一天，默认没有选择，值为“0”
     * @param givenDateAndTime 时段信息
     * @param smRangeID  短信功能中区域ID
     */
	public HistoryWorkInfoCreateBOParam(UserInfo userInfo, HistoryWorkInfoConditionBean bean, String givenDate, String givenDateAndTime, String smRangeID) {
		super();
		this.bean = bean;
		this.userInfo = userInfo;
		this.givenDate = givenDate;
		this.givenDateAndTime = givenDateAndTime;
		this.smRangeID = smRangeID;
	}

	public HistoryWorkInfoConditionBean getBean() {
		return bean;
	}

	public void setBean(HistoryWorkInfoConditionBean bean) {
		this.bean = bean;
	}

	public String getGivenDate() {
		return givenDate;
	}

	public void setGivenDate(String givenDate) {
		this.givenDate = givenDate;
	}

	public String getGivenDateAndTime() {
		return givenDateAndTime;
	}

	public void setGivenDateAndTime(String givenDateAndTime) {
		this.givenDateAndTime = givenDateAndTime;
	}

	public String getSmRangeID() {
		return smRangeID;
	}

	public void setSmRangeID(String smRangeID) {
		this.smRangeID = smRangeID;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
}
