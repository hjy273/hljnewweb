package com.cabletech.commons.util;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public class Popedom {
	/**
	 * ֻҪ�㴫����һ��UserInfo���󣬾Ϳɻ�����ݲ�ѯʱ�ļ�������ַ�������sql where������
	 * ע�����ش��в�����where����
	 * @param userinfo
	 * @return ��������ַ���
	 */
	public static String getPopedom (UserInfo userinfo){
		String condition="";
//		���ƶ�
		if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		   condition = " regionid IN ('"+userinfo.getRegionID()+"') AND state <> 1";
		}
//		�д�ά
		if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = "  state <> 1  and parentid='"+userinfo.getDeptID()+"' ";
		}
//		ʡ�ƶ�
		if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = "  state <> 1";
		}
//		ʡ��ά
		if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = " state <> 1 and parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
		}
		return condition;
	}
	
}
