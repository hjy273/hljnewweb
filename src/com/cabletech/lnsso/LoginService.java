package com.cabletech.lnsso;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public interface LoginService {

	/**
	 * ͨ��webservice ����û���Ϣ
	 * Ҫ��������ṩ������Ϣ���û���������������id������id����������
	 */
	public UserInfo getUserInfo(String username, String usertype);
}
