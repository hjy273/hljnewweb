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
import com.cabletech.sysmanage.beans.UserMailInfoBean;
import com.cabletech.sysmanage.services.UserMailInfoBO;

public class UserMailInfoAction extends BaseInfoBaseDispatchAction {

	private static Logger logger = Logger.getLogger(UserMailInfoAction.class
			.getName());

	private UserMailInfoBO getBusinessService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (UserMailInfoBO) ctx.getBean("userMailInfoBO");
	}

	public ActionForward addUserMailForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("add_user_mail_info");
	}

	public ActionForward addUserMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserMailInfoBO bo = getBusinessService();
		UserMailInfoBean userMailInfoBean = (UserMailInfoBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		bo.addUserMailInfo(userMailInfoBean, userInfo);
		String url = request.getContextPath()
				+ "/user_mail.do?method=addUserMailForm";
		log(request,"添加邮箱（邮箱名称为："+userMailInfoBean.getMailName()+"）","系统管理");
		return super.forwardInfoPageWithUrl(mapping, request, "75501s", url);
	}

	public ActionForward updateUserMailForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userMailId = request.getParameter("id");
		UserMailInfoBO bo = getBusinessService();
		UserMailInfoBean userMailInfoBean = bo.viewUserMailInfo(userMailId);
		userMailInfoBean.setOrderNumberStr(Integer.toString(userMailInfoBean
				.getOrderNumber()));
		request.setAttribute("user_mail_info_bean", userMailInfoBean);
		return mapping.findForward("update_user_mail_info");
	}

	public ActionForward updateUserMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserMailInfoBO bo = getBusinessService();
		UserMailInfoBean userMailInfoBean = (UserMailInfoBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		bo.updateUserMailInfo(userMailInfoBean, userInfo);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"更新邮箱（邮箱名称为："+userMailInfoBean.getMailName()+"）","系统管理");
		return super.forwardInfoPageWithUrl(mapping, request, "75502s", url);
	}

	public ActionForward deleteUserMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String userMailId = request.getParameter("id");
		UserMailInfoBO bo = getBusinessService();
		String name=bo.get(userMailId).getMailName();
		bo.delete(userMailId);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		log(request,"删除邮箱（邮箱名称为："+name+"）","系统管理");
		return super.forwardInfoPageWithUrl(mapping, request, "75503s", url);
	}

	public ActionForward queryForList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		super.setPageReset(request);
		UserMailInfoBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List list = bo.queryUserMailInfo(userInfo);
		request.getSession().setAttribute("USER_MAIL_LIST", list);
		return mapping.findForward("user_mail_list");
	}

	public ActionForward queryLatestList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		UserMailInfoBO bo = getBusinessService();
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		String outHtml = bo.queryMailLatestList(userInfo);
		PrintWriter out = response.getWriter();
		out.print(outHtml);
		out.flush();
		out.close();
		return null;
	}
}
