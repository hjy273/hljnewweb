package com.cabletech.commons.tags.linkage;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.tags.Thirdlinkage;


public class ThridClassTagContext {
	private static ThirdClassLinkService create(Thirdlinkage para ,UserInfo user){
		if("false".equals(para.getUserDisable())){
			if (UserType.PROVINCE.equals(user.getType())) {
				return  new ThirdClassLinkService4Province(para ,user);
			}
			if (UserType.CONTRACTOR.equals(user.getType())) {
				return new ThirdClassLinkService4Con(para ,user);
			}
			if (UserType.SECTION.equals(user.getType())) {
				return new ThirdClassLinkService4Section(para ,user);
			}
			
		}else{
			if("false".equals(para.getDisplay2())){
				return new ThirdClassLinkService4RC(para );//区域、代为
			}
			if("false".equals(para.getDisplay1())){
				return new ThirdClassLinkService4RP(para );//区域、巡检组 
			}
			if("false".equals(para.getDisplay0())){
				return new ThirdClassLinkService4CP(para );//代为、巡检组
			}
		}
		return null;
	}
	public static ThirdClassLinkService getInstance(Thirdlinkage para ,UserInfo user){
		return create(para ,user);
	}
}
