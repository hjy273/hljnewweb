package com.cabletech.linepatrol.commons.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class EvaluateTag extends BodyTagSupport{
	
	private static final long serialVersionUID = 8518374478605199466L;
	private Integer colspan = 0;
	private String labelStyle="";
	private String valueStyle="";
	private String areaStyle="";
	public int doEndTag() throws JspException {
		
		StringBuilder html = new StringBuilder();
		
//		html.append("<script src=\"/WebApp/js/star_rating/jquery.js\" type=\"text/javascript\"></script>");
		html.append("<script src=\"/WebApp/js/star_rating/jquery.rating.js\" type=\"text/javascript\" language=\"javascript\"></script>");
		html.append("<link href=\"/WebApp/js/star_rating/jquery.rating.css\" type=\"text/css\" rel=\"stylesheet\"/>");
		
		html.append("<tr class=\"trcolor\">");
		html.append("<td class=\""+labelStyle+"\">");
		html.append("考核评分：");
		html.append("</td>");
		html.append("<td colspan=\""+colspan+"\" class=\""+valueStyle+"\">");
		html.append("	<input type=\"radio\" class=\"star\" name=\"entityScore\" value=\"1\"/>");
		html.append("	<input type=\"radio\" class=\"star\" name=\"entityScore\" value=\"2\"/>");
		html.append("	<input type=\"radio\" class=\"star\" name=\"entityScore\" value=\"3\"/>");
		html.append("	<input type=\"radio\" class=\"star\" name=\"entityScore\" value=\"4\"/>");
		html.append("	<input type=\"radio\" class=\"star\" name=\"entityScore\" value=\"5\"/>");
		html.append("</td>");
		html.append("</tr>");
		html.append("<tr class=\"trwhite\">");
		html.append("<td class=\""+labelStyle+"\">");
		html.append("评分意见：");
		html.append("</td>");
		html.append("<td colspan=\""+colspan+"\" class=\""+valueStyle+"\">");
		html.append("<textarea name=\"entityRemark\" id=\"entityRemark\" rows=\"4\" class=\""+areaStyle+"\"></textarea>");
		html.append("</td>");
		html.append("</tr>");
		try {
			super.pageContext.getOut().print(html.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	public String getLabelStyle() {
		return labelStyle;
	}
	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}
	public String getValueStyle() {
		return valueStyle;
	}
	public void setValueStyle(String valueStyle) {
		this.valueStyle = valueStyle;
	}
	public String getAreaStyle() {
		return areaStyle;
	}
	public void setAreaStyle(String areaStyle) {
		this.areaStyle = areaStyle;
	}
	
	
}
