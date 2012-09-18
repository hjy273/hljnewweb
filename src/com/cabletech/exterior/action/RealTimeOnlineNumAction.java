package com.cabletech.exterior.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.RealTimeOnlineBO;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.config.UserType;

public class RealTimeOnlineNumAction extends BaseInfoBaseDispatchAction {
	private RealTimeOnlineBO onlineservice = new RealTimeOnlineBO();
	private UserInfoBO ubo = new UserInfoBO();
	public ActionForward getOnlineNum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
	String range = request.getParameter("range");
	String userid = request.getParameter("uid");
	UserInfo user = ubo.loadUserInfo(userid);
	user.setType(UserType.PROVINCE);
	Map onlineNum = null;
	onlineNum = onlineservice.getAllOnlineNum(user,range);
	session.setAttribute("currentRegion", range);
	session.setAttribute("onlineNum", onlineNum);
	session.setAttribute("token", range);
	session.setAttribute("LOGIN_USER", user);
	return mapping.findForward("onlinenum");
	}
}
