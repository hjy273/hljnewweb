package com.cabletech.commons.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TabPageTag extends AbstractUiTag{
	private String defaultTab = "false";
	private String tabId = "";
	private String tabName = "";
	private String baseUrl = "";
	
	public int doStartTag() throws JspException{
		StringBuffer body = new StringBuffer();
		String style = "ooihs";
		if("true".equals(defaultTab)){
			style = "ooihj";
		}
		body.append("<td class=\""+style+"\" id=\""+tabId+"\" nowrap onclick=\"ghbq(this)\">"+tabName+"</td>");
		
		println( body.toString() );
		return this.EVAL_BODY_INCLUDE;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
