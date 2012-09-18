package com.cabletech.commons.util;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public class Popedom {
	/**
	 * 只要你传给它一个UserInfo对象，就可获得数据查询时的级别控制字符串，用sql where条件后
	 * 注：返回串中不含”where“。
	 * @param userinfo
	 * @return 级别控制字符串
	 */
	public static String getPopedom (UserInfo userinfo){
		String condition="";
//		市移动
		if( userinfo.getDeptype().equals( "1" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		   condition = " regionid IN ('"+userinfo.getRegionID()+"') AND state <> 1";
		}
//		市代维
		if( userinfo.getDeptype().equals( "2" ) && !userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = "  state <> 1  and parentid='"+userinfo.getDeptID()+"' ";
		}
//		省移动
		if( userinfo.getDeptype().equals( "1" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = "  state <> 1";
		}
//		省代维
		if( userinfo.getDeptype().equals( "2" ) && userinfo.getRegionID().substring( 2, 6 ).equals( "0000" ) ){
		  condition = " state <> 1 and parentid in( SELECT contractorid FROM contractorinfo CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"+userinfo.getDeptID()+"')";
		}
		return condition;
	}
	
}
