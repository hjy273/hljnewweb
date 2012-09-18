package com.cabletech.bj.wap.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.Depart;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.baseinfo.services.DepartBO;
import com.cabletech.bj.services.InitDisplayBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.sysmanage.services.NoticeBo;

public class IndexAction extends BaseInfoBaseDispatchAction {
	//1、获取最新的公告，新闻，会议信息。
	public ActionForward indexInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		String num = "10";
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String rootregionid = (String) session.getAttribute("REGION_ROOT");
		WebApplicationContext ctx = getWebApplicationContext();
		NoticeBo bo = (NoticeBo) ctx.getBean("noticeBo");
		String noticeStr = bo.showNewNotice(rootregionid, userinfo.getRegionID(), num, "公告", "no");
		String newStr = bo.showNewNotice(rootregionid, userinfo.getRegionID(), num, "新闻", "no");
		String meetingStr = bo.showNewNotice(rootregionid, userinfo.getRegionID(), num, "会议", "no");
		StringBuffer html = new StringBuffer();
		html.append("<div style=\"font-size: 18px; font-weight:600;bottom:1\">公告</div><hr />");
		html.append(noticeStr);
		html.append("<div style=\"font-size: 18px; font-weight:600;bottom:1\">会议</div><hr />");
		html.append(meetingStr);
		html.append("<div style=\"font-size: 18px; font-weight:600;bottom:1\">新闻</div><hr />");
		html.append(newStr);
		request.setAttribute("info", html.toString());
		return mapping.findForward("index");
	}

	public ActionForward queryAddressBook(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse respingse) throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		InitDisplayBO listnoticebo = new InitDisplayBO();
		ContractorBO contractorBO=new ContractorBO();
		DepartBO departBO=new DepartBO();
		List<Contractor> contractorList=contractorBO.getAllContractor(user);
		List<Depart> departList=departBO.getAllDepart(user);
		StringBuffer html=new StringBuffer();
		setContractorHtml(contractorList, departList, html);
		request.setAttribute("contractorHtml", html);
		return mapping.findForward("queryAddressbook");
	}

	
	public ActionForward addressBook(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse respingse) throws ClientException, Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String name=request.getParameter("name");
		String contractorName=request.getParameter("contractor");
		InitDisplayBO listnoticebo = new InitDisplayBO();
		List list = listnoticebo.getAddressList(user, contractorName, name, null);
		session.setAttribute("addrList", list);
		return mapping.findForward("showAddressList");
	}
	
	private void setContractorHtml(List<Contractor> contractorList, List<Depart> departList, StringBuffer html) {
		html.append("<tr><td>单位：</td><td><select name=\"contractor\" style=\"width:140px\">");
		html.append("<option value=\"\">不限</option>");
		for(Depart depart:departList){
			html.append("<option value=\""+depart.getDeptName()+"\">"+depart.getDeptName()+"</option>");
		}
		for(Contractor contractor:contractorList){
			html.append("<option value=\""+contractor.getContractorName()+"\">"+contractor.getContractorName()+"</option>");
		}
		html.append("</select></td></tr>");
	}
}
