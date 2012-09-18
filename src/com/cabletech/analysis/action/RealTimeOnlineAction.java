package com.cabletech.analysis.action;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.beans.CurrentTimeBean;
import com.cabletech.analysis.services.RealTimeOnlineBO;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.UserType;

/**
 * 实时在线人数
 */
public class RealTimeOnlineAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger("RealTimeOnlineAction");
	private RealTimeOnlineBO onlineservice ;

	/**
	 * 获取不同时刻全省的巡检人员人数状态
	 * 
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getOnlineNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		onlineservice = new RealTimeOnlineBO();
		String range = request.getParameter("range");
		String type = request.getParameter("s");
		//logger.info("type" + type);
		if (range != null && "".equals(range)) {
			range = null;
		}
		logger.info("range" + range);
		Map onlineNum = null;
		if (UserType.PROVINCE.equals(user.getType())) {
			onlineNum = onlineservice.getAllOnlineNum(user, range);
			session.setAttribute("currentRegion", range);
			session.setAttribute("onlineNum", onlineNum);
			session.setAttribute("token", range);
			logger.info("1user.getType()" + user.getType());

		} else if (UserType.SECTION.equals(user.getType())) {
			onlineNum = onlineservice.getAreaOnlineNum(user, range);
			session.setAttribute("connid", range);
			session.setAttribute("onlineNum", onlineNum);
			session.setAttribute("token", range);
			logger.info("2user.getType()" + user.getType());
		} else {
			onlineNum = onlineservice.getConOnlineNum(user, range);
			session.setAttribute("onlineNum", onlineNum);
			session.setAttribute("token", range);
			session.setAttribute("range", range);
			logger.info("range = " + range);
		}
		if (type != null && !"".equals(type)) {
			return mapping.findForward("RealTime");
		} else {
			return mapping.findForward("RealTimeForm");
		}
	}

	/**
	 * 获取指定时间点的在线人数信息
	 * 
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getspecifyTimeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String time = request.getParameter("time");
		String currentRegion = (String) session.getAttribute("currentRegion");
		String connid = (String) session.getAttribute("connid");
		CurrentTimeBean bean = onlineservice.getSegmentOnlineNum(user, time,
				currentRegion, connid);
		System.out.println("bean :" + bean.toString());
		String html = onlineservice.compagesHtmlText(bean);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();
		return null;
	}

//	
//	public ActionForward showRealTime(ActionForm form,
//			HttpServletRequest request, HttpServletResponse response,
//			ActionMapping mapping) {
//		return mapping.findForward("RealTime");
//	}
}
