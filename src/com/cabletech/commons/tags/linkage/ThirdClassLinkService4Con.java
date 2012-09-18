package com.cabletech.commons.tags.linkage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.tags.Thirdlinkage;

public class ThirdClassLinkService4Con extends ThirdClassLinkService {

	public ThirdClassLinkService4Con(Thirdlinkage parameter, UserInfo user) {
		super(parameter, user);
		// TODO Auto-generated constructor stub
	}

	public String createHtml() {
		String sql = "select patrolid,patrolname from patrolmaninfo where parentid='"+user.getDeptID()+"' ";
		Map map = getOptions(sql,"patrolname","patrolid");

		html2.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable2()+"：</td><td class=\"tdulright\">\n");
		html2.append("<select name=\""+parameter.getName2()+"\" Class=\"inputtext\" style=\"width:225\">\n");
		html2.append("<option value=\"\">请选择...</option>\n");
		//初始化区域
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
    		String value = (String)map.get(key);
    		html2.append("			<option value=\""+value+"\">"+key+"</option>\n");
		}
		html2.append("</select><br>\n");
		html2.append("</td></tr>\n");
		return html2.toString();
	}
	
}
