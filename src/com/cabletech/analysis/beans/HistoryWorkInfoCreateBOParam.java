package com.cabletech.analysis.beans;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;

public class HistoryWorkInfoCreateBOParam extends BaseBean {
	private HistoryWorkInfoConditionBean bean;  //�û������ϲ�ѯʱ���������bean

	private UserInfo userInfo; //�û���Ϣ

	private String givenDate = "0"; //�û��Ƿ�ѡ����ĳһ�죬Ĭ��û��ѡ��ֵΪ��0��

	private String givenDateAndTime; //ʱ����Ϣ

	private String smRangeID; //���Ź���������ID

    /**
     * ���췽��
     * @param userInfo  �û���Ϣ
     * @param bean  �û������ϲ�ѯʱ���������bean
     * @param givenDate �û��Ƿ�ѡ����ĳһ�죬Ĭ��û��ѡ��ֵΪ��0��
     * @param givenDateAndTime ʱ����Ϣ
     * @param smRangeID  ���Ź���������ID
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
