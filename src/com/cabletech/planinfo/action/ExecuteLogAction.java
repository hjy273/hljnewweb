package com.cabletech.planinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.planinfo.services.ExecuteLogBO;

public class ExecuteLogAction extends PlanInfoBaseDispatchAction {
	public ActionForward queryExecuteLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		ExecuteLogBO bo = new ExecuteLogBO();
		List list = bo.getExecuteLogs(user.getUserID());
		request.setAttribute("ExecuteLogList", list);
		return mapping.findForward("ExecuteLogResult");
	}
}
