package com.cabletech.linepatrol.resource.tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.SpringContext;
import com.cabletech.linepatrol.resource.service.TrunkManager;

public class TrunksTag extends BodyTagSupport{
	private String id = "";
	private String cols = "45";
	private String rows = "2";
	private String state = "";
	private String value = "";
	private UserInfo user = null;
	private TrunkManager trunkManager = (TrunkManager)SpringContext.getApplicationContext().getBean("trunkManager");;
	
	public int doEndTag() throws JspException{
		try{
			 user =(UserInfo)this.pageContext.getSession().getAttribute("LOGIN_USER");
			if(state.toLowerCase().equals("view_simple")){
				this.pageContext.getOut().print(getHtml());
			}else{
				this.pageContext.getOut().print(getScript() + getHtml());
			}
			if(state.toLowerCase().equals("edit")){
				this.pageContext.getSession().setAttribute("sTrunkId", value);
			}
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return this.EVAL_PAGE;
	}
	
	public String getScript(){
		StringBuffer script = new StringBuffer();
		script.append("<link rel='stylesheet' type='text/css' href='/WebApp/js/extjs/resources/css/ext-all.css' />");
		script.append("<link rel='stylesheet' type='text/css' href='/WebApp/js/extjs/resources/css/xtheme-gray.css'/>");
		script.append("<script type='text/javascript' src='/WebApp/js/extjs/adapter/ext/ext-base.js'></script>");
		script.append("<script type='text/javascript' src='/WebApp/js/extjs/ext-all.js'></script>");
		script.append("<script type='text/javascript'>" );
		script.append("var win;");
		script.append("function choose(){");
		script.append("    var trunkList = $('"+id+"').value;");
		script.append("	   var url = '/WebApp/trunkAction.do?method=link&id="+id+"&trunkList='+trunkList;");
		script.append("	   win = new Ext.Window({");
		script.append("	 	   	layout : 'fit',");
		script.append("		   	width:500,");
		script.append("		   	height:300, ");
		script.append("		   	resizable:true,");
		script.append("		   	closeAction : 'close',");
		script.append("		   	modal:true,");
		script.append("       	html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src=\"'+url+'\" />',");
		script.append("		   	plain: true");
		script.append("	   });");
		script.append("	   win.show(Ext.getBody());");
		script.append("}");
		script.append("function close(){");
		script.append("    win.close();");
		script.append("}");
		script.append("</script>");
		return script.toString();
	}
	
	public String getHtml(){
		StringBuffer html = new StringBuffer();
		if(state.toLowerCase().equals("view")){
			if(StringUtils.isNotBlank(value)){
				String[] ids = value.split(",");
				Set<String> trunkIds = new HashSet<String>(Arrays.asList(ids));
				html.append(trunkManager.getTrunkNameString(trunkIds));
			}
			return html.toString();
		}else if(state.toLowerCase().equals("view_simple")){
			if(StringUtils.isNotBlank(value)){
				String[] ids = value.split(",");
				Set<String> trunkIds = new HashSet<String>(Arrays.asList(ids));
				html.append(trunkManager.getTrunkNameString(trunkIds));
			}
			return html.toString();
		}else {
			html.append("<textarea name='textarea' class='inputtextarea' id='trunknames' cols='");
			html.append(cols);
			html.append("' rows='");
			html.append(rows);
			html.append("' readonly>");
			html.append(getContent());
			html.append("</textarea>");
			if(state.toLowerCase().equals("edit")){
				html.append("<input type='hidden' id='"+id+"' name='"+id+"' value='"+value+"'/>");
			}else{
				html.append("<input type='hidden' id='"+id+"' name='"+id+"' />");
			}
			html.append("<input type='button' value='Ñ¡Ôñ' onclick='choose()' />");
			return html.toString();
		}
	}
	
	public String getContent(){
		StringBuffer sb = new StringBuffer();
		if(state.toLowerCase().equals("edit")){
			if(StringUtils.isNotBlank(value)){
				String[] ids = value.split(",");
				Set<String> trunkIds = new HashSet<String>(Arrays.asList(ids));
				sb.append(trunkManager.getTrunkNameString(trunkIds));
			}
			return sb.toString();
		}else
			return sb.toString();
	}
	
	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
