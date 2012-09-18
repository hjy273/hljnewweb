package com.cabletech.bj.wap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.bj.services.WapOnLineBO;
import com.cabletech.planstat.services.PlanProgressBO;

public class PatrolMonitorAction extends BaseInfoBaseDispatchAction {

	private static final long serialVersionUID = 1L;
	public ActionForward pmindex(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String env = request.getParameter("env");
		request.setAttribute("env", env);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		request.setAttribute("userInfo", userInfo);
		if ("wap".equals(env) && userInfo != null) {
			return mapping.findForward("pmindex");
		} else {
			return mapping.findForward("relogin");
		}
	}
	public ActionForward onlineMan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		WapOnLineBO onlineBo = (WapOnLineBO) ctx.getBean("wapOnLineBO");
		PlanProgressBO planProgressBO=new PlanProgressBO();
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String mobileHTML = onlineBo.getOnlinePatrolman(user);
		request.setAttribute("mobile_html", mobileHTML);
		return mapping.findForward("onlineMan");
	}
	
	public ActionForward planProgress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		WapOnLineBO onlineBo = (WapOnLineBO) ctx.getBean("wapOnLineBO");
		PlanProgressBO planProgressBO=new PlanProgressBO();
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		String planProgressHtml=planProgressBO.getPlanProgressFromPDA(user);
		request.setAttribute("PLANPROGRESS", planProgressHtml);
		return mapping.findForward("planProgress");
	}
}
