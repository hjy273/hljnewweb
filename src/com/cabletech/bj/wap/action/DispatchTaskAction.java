package com.cabletech.bj.wap.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;

public class DispatchTaskAction extends BaseInfoBaseDispatchAction{
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		// ÅÐ¶ÏÊÇ·ñ´ÓPDAµÇÂ¼
		String env = request.getParameter("env");
		request.setAttribute("env", env);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		if (userInfo == null) {
			return mapping.findForward("relogin");
		}
		return mapping.findForward("taskindex");
	}
}
