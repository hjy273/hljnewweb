package com.cabletech.commons.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class TabPanelTag extends AbstractUiTag{
	private String styleClass = "";
	private String orientation = "down";//tab panel Œª÷√
	private String width = "100%";
	private String hight = "19";
	private String contentHigth = "80%";
	private String defaultContent = "";
	private String contentName = "";
	
	//private static String css = "tabpanel_top.css"; //to do property
	public int doEndTag() throws JspException{
		StringBuffer tabPanel = new StringBuffer();
		StringBuffer tabContent = new StringBuffer();
		StringBuffer tabHtml = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		
		tabPanel.append("<table class=\""+styleClass+"\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\""+width+"\" height=\""+hight+"\">\n");
		tabPanel.append("<tr>\n");
		String content = "";
		if( bodyContent != null ){
			 content = bodyContent.getString();
             bodyContent.clearBody();
        }
		tabPanel.append(content);
		tabPanel.append("<td width=\"100%\">&nbsp;</td>\n");
		tabPanel.append("</tr>\n");
		tabPanel.append("<table>\n");
		tabContent.append("<div id=\"content\"  style=\"color:red\">\n");
		tabContent.append("<iframe id=\""+contentName+"\" marginWidth=\"0\" marginHeight=\"0\" src=\""+defaultContent+"\" frameBorder=0 width=\""+width+"\" scrolling=auto height=\""+contentHigth+"\"> </iframe>");
		//tabContent.append("<%@include file=\""+defaultContent+"\" %>");
		tabContent.append("</div>\n");
		if(TOP.equals(orientation)){
			tabHtml.append("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/css/tabpanel_top.css\" type=\"text/css\" media=\"screen, print\" />\n");
			tabHtml.append(tabPanel.toString());
			tabHtml.append(tabContent.toString());
		}else{
			tabHtml.append("<link rel=\"stylesheet\" href=\""+request.getContextPath()+"/css/tabpanel_down.css\" type=\"text/css\" media=\"screen, print\" />\n");
			tabHtml.append(tabContent.toString());
			tabHtml.append(tabPanel.toString());
		}
		System.out.println(tabHtml.toString());
		println( tabHtml.toString() );
		return EVAL_PAGE;
	}
	
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHight() {
		return hight;
	}
	public void setHight(String hight) {
		this.hight = hight;
	}
	public String getDefaultContent() {
		return defaultContent;
	}
	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}

	public String getContentHigth() {
		return contentHigth;
	}

	public void setContentHigth(String contentHigth) {
		this.contentHigth = contentHigth;
	}
	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

}

