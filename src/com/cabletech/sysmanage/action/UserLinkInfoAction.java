package com.cabletech.sysmanage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.sysmanage.beans.UserLinkInfoBean;
import com.cabletech.sysmanage.services.UserLinkInfoBO;

public class UserLinkInfoAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(UserLinkInfoAction.class
			.getName());

	private UserLinkInfoBO getBusinessService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (UserLinkInfoBO) ctx.getBean("userLinkInfoBO");
	}

	public ActionForward addUserLinkForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("link_type", "1");
		return mapping.findForward("add_user_link_info");
	}

	public ActionForward addUserCommonLinkForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("link_type", "0");
		return mapping.findForward("add_user_link_info");
	}

	public ActionForward addUserLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserLinkInfoBO bo = getBusinessService();
		UserLinkInfoBean userLinkInfoBean = (UserLinkInfoBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		bo.addUserLinkInfo(userLinkInfoBean, userInfo);
		String url = request.getContextPath()
				+ "/user_link.do?method=addUserLinkForm";
		if ("0".equals(userLinkInfoBean.getLinkType())) {
			log(request,"添加公共链接（链接名称为："+userLinkInfoBean.getLinkName()+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76004s", url);
		} else {
			log(request,"添加常用链接（链接名称为："+userLinkInfoBean.getLinkName()+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76001s", url);
		}
	}

	public ActionForward updateUserLinkForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userLinkId = request.getParameter("id");
		UserLinkInfoBO bo = getBusinessService();
		UserLinkInfoBean userLinkInfoBean = bo.viewUserLinkInfo(userLinkId);
		userLinkInfoBean.setOrderNumberStr(Integer.toString(userLinkInfoBean
				.getOrderNumber()));
		request.setAttribute("user_link_info_bean", userLinkInfoBean);
		return mapping.findForward("update_user_link_info");
	}

	public ActionForward updateUserLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserLinkInfoBO bo = getBusinessService();
		UserLinkInfoBean userLinkInfoBean = (UserLinkInfoBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		bo.updateUserLinkInfo(userLinkInfoBean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if ("0".equals(userLinkInfoBean.getLinkType())) {
			log(request,"修改公共链接（链接名称为："+userLinkInfoBean.getLinkName()+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76005s", url);
		} else {
			log(request,"修改常用链接（链接名称为："+userLinkInfoBean.getLinkName()+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76002s", url);
		}
	}

	public ActionForward deleteUserLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userLinkId = request.getParameter("id");
		UserLinkInfoBO bo = getBusinessService();
		String name=bo.get(userLinkId).getLinkName();
		bo.delete(userLinkId);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		if ("0".equals(request.getParameter("link_type"))) {
			log(request,"删除公共链接（链接名称为："+name+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76006s", url);
		} else {
			log(request,"删除常用链接（链接名称为："+name+"）","系统管理");
			return super
					.forwardInfoPageWithUrl(mapping, request, "76003s", url);
		}
	}

	public ActionForward queryForList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		UserLinkInfoBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = bo.queryUserLinkInfo(userInfo);
		request.setAttribute("link_type", request.getParameter("link_type"));
		request.getSession().setAttribute("USER_LINK_LIST", list);
		return mapping.findForward("user_link_list");
	}

	public ActionForward queryCommonLinkForList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		UserLinkInfoBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = bo.queryUserCommonLinkInfo(userInfo);
		request.setAttribute("link_type", request.getParameter("link_type"));
		request.getSession().setAttribute("USER_LINK_LIST", list);
		return mapping.findForward("user_link_list");
	}

	public ActionForward queryLatestList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserLinkInfoBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		String outHtml = bo.queryLinkLatestList(userInfo);
		PrintWriter out = response.getWriter();
		out.print(outHtml);
		out.flush();
		out.close();
		return null;
	}
}
