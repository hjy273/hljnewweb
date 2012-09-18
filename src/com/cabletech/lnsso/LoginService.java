package com.cabletech.lnsso;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public interface LoginService {

	/**
	 * 通过webservice 获得用户信息
	 * 要求必须提提供以下信息：用户名，姓名，部门id，区域id或区域名称
	 */
	public UserInfo getUserInfo(String username, String usertype);
}
