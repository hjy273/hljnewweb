package com.cabletech.watchinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.web.ClientException;
import com.cabletech.watchinfo.services.WatchPicBO;

public class ListMmsPicAction  extends WatchinfoBaseAction {
	private static Logger logger = Logger.getLogger(ListMmsPicAction.class);
	private WatchPicBO bo = new WatchPicBO();
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		// TODO Auto-generated method stub
		String watchId=request.getParameter("watchid");
		List lst=bo.listMmsPicEx(watchId);
		request.setAttribute("mmsPicList", lst);
		request.setAttribute("watchid",watchId);
		request.getSession().setAttribute("watchid",watchId);
		return mapping.findForward("showPage");
	}

}
