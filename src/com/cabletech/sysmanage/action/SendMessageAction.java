package com.cabletech.sysmanage.action;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import com.cabletech.baseinfo.action.*;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.*;
import com.cabletech.sysmanage.beans.SendMessageBean;
import com.cabletech.sysmanage.services.SendMessageBO;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

public class SendMessageAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger("LoginAction");
	private WebApplicationContext ctx;

	public ActionForward sendMessageForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		return mapping.findForward("send_message_add");
	}

	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		SendMessageBean bean = (SendMessageBean) form;
		String content = bean.getSendContent();
		String sim = bean.getMobileIds();
		String sendMethod = request.getParameter("sendMethod");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String intervalType = request.getParameter("sendIntervalType");
		String interval = request.getParameter("sendTimeSpace");
		String sendCycleRule = request.getParameter("sendCycleRule");
		String[] inputDate = request.getParameterValues("inputDate");
		ctx = getWebApplicationContext();
		SendMessageBO bo = (SendMessageBO) ctx.getBean("sendMessageBO");
		String sendObjectName = "定时发送短信";
		bo.sendMessage(content, sim, sendMethod, beginDate, endDate,
				intervalType, interval, userInfo.getUserID(), inputDate,
				sendObjectName, sendCycleRule);
		String url = request.getContextPath()
				+ "/sendmessage/send_message.do?method=sendMessageForm";
		if ("0".equals(sendMethod)) {
			return super
					.forwardInfoPageWithUrl(mapping, request, "75001s", url);
		} else {
			return super
					.forwardInfoPageWithUrl(mapping, request, "75001S", url);
		}
	}
}
