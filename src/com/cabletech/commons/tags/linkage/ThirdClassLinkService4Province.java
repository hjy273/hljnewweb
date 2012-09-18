package com.cabletech.commons.tags.linkage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.tags.Thirdlinkage;

public class ThirdClassLinkService4Province extends ThirdClassLinkService{


	public ThirdClassLinkService4Province(Thirdlinkage parameter, UserInfo user) {
		super(parameter, user);
		// TODO Auto-generated constructor stub
	}

	public String createHtml() {
		String sql = "select regionname,regionid from region WHERE (  RegionID IN (SELECT RegionID FROM region CONNECT BY PRIOR RegionID=parentregionid START WITH RegionID='"+user.getRegionid()+"')   )  and substr(REGIONID,3,4) != '1111'  and state is null order by  regionname ";
		Map map = getOptions(sql,"regionname","regionid");
		html0.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable0()+"：</td><td class=\"tdulright\">\n");
		html0.append("		<select name=\""+parameter.getName0()+"\" class=\"selecttext\" style=\"width:225\">\n");
		html0.append("			<option value=\"\">请选择...</option>\n");
		//初始化区域
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
    		String value = (String)map.get(key);
    		html0.append("			<option value=\""+value+"\">"+key+"</option>\n");
		}
		html0.append("		</select><br>\n");	
		html0.append("</td></tr>\n");	 
		
		html1.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable1()+"：</td><td class=\"tdulright\">\n");
		html1.append("	<select name=\""+parameter.getName1()+"\" class=\"selecttext\" style=\"width:225\" id=\"WORKID\">\n");
		html1.append("		<option value=\"\">请选择...</option>\n");
		html1.append("</select><br>\n");
		html1.append("</td></tr>\n");
	
		
		html2.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable2()+"：</td><td class=\"tdulright\">\n");
		html2.append("<select name=\""+parameter.getName2()+"\" Class=\"inputtext\" style=\"width:225\">\n");
		html2.append("<option value=\"\">请选择...</option>\n");
		html2.append("</select><br>\n");
		html2.append("</td></tr>\n");
	
		
		script.append("<script>\n");
		//even0
		script.append("	new Form.Element.Observer(\""+parameter.getName0()+"\",1,toLoadInfo"+parameter.getName1()+");\n");
		script.append("	function toLoadInfo"+parameter.getName1()+"() {\n");
		script.append("	var url = '"+parameter.getEvenUrl0()+replace(parameter.getEvenUrl0())+"p='+$F('"+parameter.getName0()+"'); \n");
		script.append("		new Ajax.Request(url, {\n");
		script.append("		method: 'post',\n");
		script.append("		onSuccess: function(transport) {\n");
		script.append("			//alert(transport.responseXML);\n");
		script.append("			parsexml($('"+parameter.getName1()+"'),transport);\n");
		
		script.append("		}\n");
		script.append("		}); \n");
		
		script.append("		$('"+parameter.getName1()+"').options[0].selected = 'selected';\n    ");
		script.append("		//alert(url);\n");
		script.append("	}\n");
		//even1
		script.append("	new Form.Element.Observer(\""+parameter.getName1()+"\",1,toLoadInfo"+parameter.getName2()+");\n");
		script.append("	function toLoadInfo"+parameter.getName2()+"() {\n");
		script.append("	var url = '"+parameter.getEvenUrl1()+replace(parameter.getEvenUrl1())+"p='+$F('"+parameter.getName1()+"'); \n");
		script.append("		new Ajax.Request(url, {\n");
		script.append("		method: 'post',\n");
		script.append("		onSuccess: function(transport) {\n");
		script.append("			//alert(transport.responseXML);\n");
		script.append("			parsexml($('"+parameter.getName2()+"'),transport);\n");
		
		script.append("		}\n");
		script.append("		}); \n");
		script.append("		$('"+parameter.getName2()+"').options[0].selected = 'selected';\n    ");
		script.append("		//alert(url);\n");
		script.append("	}\n");
		
		script.append("</script>\n");
		
		return html0.toString()+html1.toString()+html2.toString()+script.toString();
	}
	
}
