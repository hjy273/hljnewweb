package com.cabletech.commons.tags.template;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class DisplayOrHide  extends TagSupport{
	private Logger logger = Logger.getLogger(DisplayOrHide.class);
	private String title="显示/隐藏查询条件";
	private String hide="隐藏";
	private String display="显示";
	private String formDisplay="";
	private String styleId="";
	public int doStartTag() throws JspException{
        try{
        	StringBuffer bf = new StringBuffer();
        	bf.append("<script type=\"text/javascript\">");
        	bf.append("	function displayHideQuery(){");
        	bf.append("		if(document.getElementById('"+styleId+"').style.display==\"none\"){");
        	bf.append("			document.getElementById('"+styleId+"').style.display=\"\";");
        	bf.append("			document.getElementById('showSpan').innerHTML=\""+hide+"\";");
        	bf.append("		}else{");
        	bf.append("			document.getElementById('"+styleId+"').style.display=\"none\";");
        	bf.append("			document.getElementById('showSpan').innerHTML=\""+display+"\";");
        	bf.append("		}");
        	bf.append("	}");
        	//bf.append(styleId+".style.display=\""+formDisplay+"\";");
        	bf.append("</script>");
        	bf.append("<div align=\"right\" style=\"margin-right: 20px;\"><span id=\"showSpan\" style=\"font-weight:bold;color:blue;cursor:pointer;\" title=\""+title+"\" onclick=\"displayHideQuery();\">");
        	if("".equals(getFormDisplay())){
        		bf.append(hide);
        	}else{
        		bf.append(display);
        	}
        	
        	bf.append("</span></div>");
        	this.pageContext.getOut().print( bf.toString() );
        }catch(Exception e){
        	logger.error(e);
        }
        return this.SKIP_BODY;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHide() {
		return hide;
	}
	public void setHide(String hide) {
		this.hide = hide;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
	public String getFormDisplay() {
		return formDisplay;
	}
	public void setFormDisplay(String formDisplay) {
		this.formDisplay = formDisplay;
	}
	
}
