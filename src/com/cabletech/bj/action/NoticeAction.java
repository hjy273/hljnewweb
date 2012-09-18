package com.cabletech.bj.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.bj.services.InitDisplayBO;
import com.cabletech.commons.web.ClientException;

public class NoticeAction extends BaseInfoBaseDispatchAction {
	// //获得所有的公告信息
	// public ActionForward showAllNotice(ActionMapping mapping,ActionForm form,
	// HttpServletRequest request ,HttpServletResponse response) throws
	// ClientException ,Exception{
	// InitDisplayBO listnoticebo = new InitDisplayBO();
	// HttpSession session = request.getSession();
	// UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
	// String rootregionid = (String) session.getAttribute("REGION_ROOT");
	// // String str = listnoticebo.getNoticeInfoForNew(rootregionid,
	// userinfo.getRegionID(),"all");
	// // request.setAttribute("notice", str);
	// return mapping.findForward("show_all_notice_list");
	// }
	//	 
	// //获得所有的公告信息
	// public ActionForward showNewNotice(ActionMapping mapping,ActionForm form,
	// HttpServletRequest request ,HttpServletResponse response) throws
	// ClientException ,Exception{
	// InitDisplayBO listnoticebo = new InitDisplayBO();
	// HttpSession session = request.getSession();
	// UserInfo userinfo = (UserInfo)session.getAttribute("LOGIN_USER");
	// String rootregionid = (String) session.getAttribute("REGION_ROOT");
	//		 
	// // String noticestr = listnoticebo.getNoticeInfoForNew(rootregionid,

	// // userinfo.getRegionID(), "");
	// // request.setAttribute("noticeli", noticestr);
	// return mapping.findForward("show_new_notice_list");
	// }

	public ActionForward showMeetCalendar(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ClientException, Exception {
		InitDisplayBO initbo = new InitDisplayBO();
		String meetStr = initbo.getMeetInfo();
		request.setAttribute("meet_string", meetStr);
		return mapping.findForward("show_meet_calendar");
	}
}
