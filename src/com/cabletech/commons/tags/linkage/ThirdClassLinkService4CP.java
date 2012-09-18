package com.cabletech.commons.tags.linkage;


import java.util.Iterator;
import java.util.Map;
import com.cabletech.commons.tags.Thirdlinkage;

public class ThirdClassLinkService4CP extends ThirdClassLinkService{

	public ThirdClassLinkService4CP(Thirdlinkage parameter) {
		super(parameter);
	}

	public String createHtml() {
		String sql = "select contractorid,contractorname from contractorinfo and STATE is null order by contractorname";
		System.out.println("sql"+sql);
		Map map = getOptions(sql,"contractorname","contractorid");
		
		html1.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable1()+"：</td><td class=\"tdulright\">\n");
		html1.append("	<select name=\""+parameter.getName1()+"\" class=\"selecttext\" style=\"width:225\">\n");
		html1.append("		<option value=\"\">请选择...</option>\n");
		//初始化区域
		Iterator it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
    		String value = (String)map.get(key);
    		html1.append("			<option value=\""+value+"\">"+key+"</option>\n");
		}
		html1.append("</select><br>\n");
		html1.append("</td></tr>\n");
	
		
		html2.append("<tr class=trwhite ><td class=\"tdulleft\">"+parameter.getLable2()+"：</td><td class=\"tdulright\">\n");
		html2.append("<select name=\""+parameter.getName2()+"\" Class=\"inputtext\" style=\"width:225\">\n");
		html2.append("<option value=\"\">请选择...</option>\n");
		html2.append("</select><br>\n");
		html2.append("</td></tr>\n");
	
		
		script.append("<script>\n");
		
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
		
		return html1.toString()+html2.toString()+script.toString();
	}
	
	

}
