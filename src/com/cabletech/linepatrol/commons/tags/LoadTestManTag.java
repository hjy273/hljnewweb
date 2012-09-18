package com.cabletech.linepatrol.commons.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.linepatrol.commons.services.TestManBO;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestStationData;

/**
 * 选择测试人员标签
 * 
 * @author Administrator
 * 
 */
public class LoadTestManTag extends BodyTagSupport {

	// 显示占位符的HTML页面id
	private String spanId = "";
	private String hiddenId="";
	// 输入域表格的所占表格列数
	private String colSpan = "3";
	private String state = "add";//add,edit,value
	private String value = "";//edit objcetid
	private String testman="";//带入测试人员
	private String tablename="";//基站数据表或者中继段数据表
	private ApplicationContext applicationContext;
	TestManBO bo;
	String testMan="";
	public int doEndTag() throws JspException{
		try{
			init();
			if(state!=null && !state.equals("add")){
				testMan = getTestMan();
			}
			if(state!=null && state.equals("view")){
				this.pageContext.getOut().print(getHtml());
			}else{
				System.out.println("testMan=============== "+testMan);
				this.pageContext.getOut().print(getScript() + getHtml());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.EVAL_PAGE;
	}

	public void init() {
		applicationContext = WebApplicationContextUtils
		.getWebApplicationContext(super.pageContext.getServletContext());
		bo = (TestManBO)applicationContext.getBean("testManBO");;
	}


	public String getScript(){
		StringBuffer script = new StringBuffer();
		script.append("<script type='text/javascript'>" );
		script.append("var win2;");
		script.append("function getUsers(){");
		script.append(" var newmans = document.getElementById('testMan').value;");
		script.append("var u=\"/WebApp/loadTestManAction.do?method=getTestMans&testMans="+testMan+"&newmans=\"+newmans;");
		script.append("	 win2 = new Ext.Window({");
		script.append("	 	layout : 'fit',");
		script.append("		width:500,");
		script.append("		height:330, ");
		script.append("		resizable:true,");
		script.append("		closeAction : 'close',");
		script.append("		modal:true,");
	//	script.append("     autoLoad:{url:'/WebApp/loadTestManAction.do?method=getTestMans',scripts:true},");
		script.append("    html:'<iframe scrolling=auto frameborder=0 width=100% height=100% src='+u+'></iframe>',");
		script.append("		plain: true");
		script.append("	 });");
		script.append("	 win2.show(Ext.getBody());");
		script.append("}");
		script.append(" function closeWin2(){");
		script.append("      win2.close();");
		script.append(" }");
		script.append("function submitForm(obj){");
		script.append("    obj.request({ ");
		script.append("    onComplete: function(originalRequest){ ");
		script.append("      var rst = originalRequest.responseText;");
		script.append("   	 $('"+hiddenId+"').value=rst;$('"+spanId+"').update(rst); ");
		script.append("     win2.close();");
		script.append("    }");
		script.append("  }); ");
		script.append("}");
		script.append("</script>");
		script.append("<input type=\"button\" class=\"button\" onclick=\"getUsers();\" value=\"选择用户\"/>");
		return script.toString();
	}

	public String getHtml(){
		StringBuffer sb = new StringBuffer();
		if(testMan==null){
			testMan="";
		}
		sb.append("<span id=\""+spanId+"\">"+testMan+"</span>");
		sb.append("<input  type=\"hidden\" id=\""+hiddenId+"\" name=\""+hiddenId+"\" value=\""+testMan+"\"/>");
		return sb.toString();
	}
	public String getTestMan(){
		String testMan="";
		if(testman!=null && !testman.equals("")){
			testMan=testman;
		}
		if(tablename.equals("cable")){
			TestCableData cabledata = bo.getLineDataById(value);
			testMan = cabledata.getTestMan();
		}
		if(tablename.equals("station")){
			TestStationData stationdata = bo.getStationDataById(value);
			testMan = stationdata.getTester();
		}
		return testMan;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getColSpan() {
		return colSpan;
	}

	public void setColSpan(String colSpan) {
		this.colSpan = colSpan;
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

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getTestman() {
		return testman;
	}

	public void setTestman(String testman) {
		this.testman = testman;
	}

}
