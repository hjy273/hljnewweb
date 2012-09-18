package com.cabletech.exterior.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.RealTimeNoteBO;
import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UserInfoBO;
import com.cabletech.commons.config.UserType;

public class RealTimeNoteAction extends BaseInfoBaseDispatchAction {
	private RealTimeNoteBO noteService = new RealTimeNoteBO();
	private UserInfoBO ubo = new UserInfoBO();
	public ActionForward getNoteNumInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String range = request.getParameter("range");
		String userid = request.getParameter("uid");
		UserInfo user = ubo.loadUserInfo(userid);
		user.setType(UserType.PROVINCE);
		List noteNum;
		noteNum = noteService.getAllNoteNum(range, user);
		session.setAttribute("queryRegion", range);
		session.setAttribute("noteNum", noteNum);
		session.setAttribute("type", "SECTION");
		session.setAttribute("LOGIN_USER", user);
		return mapping.findForward("noteinfo");
	}
}
