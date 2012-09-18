package com.cabletech.baseinfo.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.beans.UseTerminalBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.UseTerminalBO;
import com.cabletech.baseinfo.services.UseTerminalContext;

/**
 * UseTerminalAction
 */
public class UseTerminalAction extends BaseInfoBaseDispatchAction {

	/**
	 * 获取饼图显示需要的数据。
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getUseTerminal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		UseTerminalBean query = (UseTerminalBean) form;
		session.setAttribute("query", query);
		Map map = UseTerminalContext.getInstance(query).getUseTerminal();
		session.setAttribute("utMap", map);
		return mapping.findForward("UseTerminalInfo");
	}

	/**
	 * 按条件查询设备使用情况列表，条件包括：排列条件、分类查询条件
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getUseTerminalCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("LOGIN_USER");
		String condition = request.getParameter("conn");
		String section = request.getParameter("section");
		session.setAttribute("section", section);
		UseTerminalBean query = (UseTerminalBean) session.getAttribute("query");
		List useTerminalList = UseTerminalContext.getInstance(query).getUseTerminalCondition(condition, section);
		session.setAttribute("useterminal", useTerminalList);
		return mapping.findForward("UseTerminalList");
	}
}
