package com.cabletech.watchinfo.services;

import java.util.List;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.exterior.beans.WatchExeStaCondtionBean;
import com.cabletech.watchinfo.beans.WatchStaBean;
import com.cabletech.watchinfo.beans.WatchStaResultBean;
import com.cabletech.watchinfo.dao.WatchexeDAOImpl;

public class WatchStaBO extends BaseBisinessObject {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	WatchexeDAOImpl staBo = new WatchexeDAOImpl();

	/**
	 * ͳ�������������
	 * @param conditionBean WatchStaBean
	 * @return WatchStaResultBean
	 * @throws Exception
	 */
	public WatchStaResultBean getStaResultBean(WatchStaBean conditionBean, UserInfo userinfo) throws Exception {
		return staBo.getStaResultBean(conditionBean, userinfo);
	}

	/**
	 * ͳ�������������(ʡ�ƶ��û� by steven)
	 * @param conditionBean WatchExeStaCondtionBean
	 * @return List
	 * @throws Exception
	 */
	public List getStaResultBeanForAA(WatchExeStaCondtionBean conditionBean) throws Exception {
		return staBo.getStaResultBeanForAA(conditionBean);
	}
}
