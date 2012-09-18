package com.cabletech.commons.tags.linkage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.tags.Thirdlinkage;

public abstract class ThirdClassLinkService {
	StringBuffer html0 = new StringBuffer();
	StringBuffer html1 = new StringBuffer();
	StringBuffer html2 = new StringBuffer();
	StringBuffer script = new StringBuffer();
	Thirdlinkage parameter ;
	UserInfo user ;
	
	public ThirdClassLinkService(Thirdlinkage parameter){
		this.parameter = parameter;
	}
	public ThirdClassLinkService(Thirdlinkage parameter,UserInfo user){
		this.parameter = parameter;
		this.user = user;
	}
	public abstract String createHtml();
	public String replace(String evenUrl){
		if(evenUrl.indexOf("?")!= -1){
			return "&";
		}else {
			return "?";
		}
	}
	Map getOptions(String sql ,String name ,String value){
		BasicDynaBean bdb;
        ArrayList lableValueList = new ArrayList();
        Map map = new HashMap();
        try{
        QueryUtil jutil = new QueryUtil();
        Iterator it = jutil.queryBeans( sql ).iterator();
        while( it.hasNext() ){
            bdb = ( BasicDynaBean )it.next();
            map.put(bdb.get( name ), bdb.get(value));
        }
        	return map;
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
	}

}
