package com.cabletech.commons.process.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.process.service.ProcessHistoryBO;

public class ProcessHistoryAction extends BaseDispatchAction {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showProcessHistoryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String objectId = request.getParameter("object_id");
		String objectType = request.getParameter("object_type");
		String showClose = request.getParameter("show_close");
		if (showClose == null || "".equals(showClose)) {
			showClose = "1";
		}
		WebApplicationContext ctx = getWebApplicationContext();
		ProcessHistoryBO bo = (ProcessHistoryBO) ctx
				.getBean("processHistoryBO");
		List list = bo.queryProcessHistoryList(objectType, objectId, userInfo);
		request.setAttribute("process_history_list", list);
		request.setAttribute("show_close", showClose);
		return mapping.findForward("process_history_list");
	}
}
