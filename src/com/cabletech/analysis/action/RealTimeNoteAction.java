package com.cabletech.analysis.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.analysis.services.RealTimeNoteBO;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.config.UserType;

/**
 * 短信实时发送情况
 */
public class RealTimeNoteAction extends BaseDispatchAction {
	private RealTimeNoteBO noteService = new RealTimeNoteBO();

	/**
	 * 获取短信发送信息。
	 * 
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getNoteNumInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String range = request.getParameter("range");
		request.setAttribute("rangeid", range);
		String type = request.getParameter("s");
		if (range != null && "".equals(range)) {
			range = null;
		}
		List noteNum;
		if (UserType.PROVINCE.equals(user.getType())) {
			noteNum = noteService.getAllNoteNum(range, user);
			session.setAttribute("queryRegion", range);
			session.setAttribute("noteNum", noteNum);
			session.setAttribute("type", "SECTION");
			// 返回省级用户页
		} else if (UserType.SECTION.equals(user.getType())) {
			noteNum = noteService.getAreaNoteNum(range, user);
			session.setAttribute("queryconnid", range);
			session.setAttribute("noteNum", noteNum);
			if (range != null) {
				session.setAttribute("type", "sim");
			} else {
				session.setAttribute("type", "SECTION");
			}
			// 返回市级用户页
		} else {
			noteNum = noteService.getConNoteNum(range, user);
			session.setAttribute("noteNum", noteNum);
			session.setAttribute("type", "sim");
			// 返回代维用户页
		}
		if (type != null && !"".equals(type)) {
			return mapping.findForward("DisplayNote");
		} else {
			return mapping.findForward("NoteInfo");
		}

	}
	/**
	 * 获取短信发送量情况列表。
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward showChartNoteInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String range = request.getParameter("range");
		String type = request.getParameter("s");
		if (range != null && "".equals(range)) {
			range = null;
		}

		List noteNum;
		if (UserType.PROVINCE.equals(user.getType())) {
			noteNum = noteService.getAllNoteNum(range, user);
			session.setAttribute("queryRegion", range);
			session.setAttribute("noteNum", noteNum);

			// 返回省级用户页
		} else if (UserType.SECTION.equals(user.getType())) {
			noteNum = noteService.getAreaNoteNum(range, user);
			session.setAttribute("queryconnid", range);
			session.setAttribute("noteNum", noteNum);
			// 返回市级用户页
		} else {
			noteNum = noteService.getConNoteNum(range, user);
			session.setAttribute("noteNum", noteNum);
			// 返回代维用户页
		}
		if (type != null && !"".equals(type)) {
			return mapping.findForward("ChartNotePic");
		} else {
			return mapping.findForward("ChartNoteInfo");
		}

	}
}
