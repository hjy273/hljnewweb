package com.cabletech.commons.tags;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.tags.linkage.ThridClassTagContext;

public class Thirdlinkage extends AbstractUiTag {
	private String lable0 = "所属区域";
	private String lable1 = "代维单位";
	private String lable2 = "巡检维护组";
	private String name0 = "";
	private String name1 = "";
	private String name2 = "";
	private String evenUrl0 = "";
	private String evenUrl1 = "";
	private String userDisable = "false";
	private String display0 = "";
	private String display1 = "";
	private String display2 = "";
	public int doStartTag() throws JspException{
		HttpSession session = this.pageContext.getSession();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		println(ThridClassTagContext.getInstance(this, user).createHtml());
		return this.EVAL_PAGE;
	}
	
	public String getLable0() {
		return lable0;
	}

	public void setLable0(String lable0) {
		this.lable0 = lable0;
	}

	public String getLable1() {
		return lable1;
	}

	public void setLable1(String lable1) {
		this.lable1 = lable1;
	}

	public String getLable2() {
		return lable2;
	}

	public void setLable2(String lable2) {
		this.lable2 = lable2;
	}

	public String getName0() {
		return name0;
	}

	public void setName0(String name0) {
		this.name0 = name0;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getEvenUrl0() {
		return evenUrl0;
	}

	public void setEvenUrl0(String evenUrl0) {
		this.evenUrl0 = evenUrl0;
	}

	public String getEvenUrl1() {
		return evenUrl1;
	}

	public void setEvenUrl1(String evenUrl1) {
		this.evenUrl1 = evenUrl1;
	}

	public String getUserDisable() {
		return userDisable;
	}

	public void setUserDisable(String userDisable) {
		this.userDisable = userDisable;
	}

	public String getDisplay0() {
		return display0;
	}

	public void setDisplay0(String display0) {
		this.display0 = display0;
	}

	public String getDisplay1() {
		return display1;
	}

	public void setDisplay1(String display1) {
		this.display1 = display1;
	}

	public String getDisplay2() {
		return display2;
	}

	public void setDisplay2(String display2) {
		this.display2 = display2;
	}
	
}
