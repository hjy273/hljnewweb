package com.cabletech.watchinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.watchinfo.beans.UploadFromMMSBean;
import com.cabletech.watchinfo.domainobjects.WatchAttach;
import com.cabletech.watchinfo.services.WatchPicBO;

public class UploadFromMMSAction extends WatchinfoBaseAction {
	private static Logger logger = Logger.getLogger(UploadFromMMSAction.class);
	private WatchPicBO bo = new WatchPicBO();

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		UploadFromMMSBean myBean = (UploadFromMMSBean) form;

		if (myBean.getRelateUrl() == null) {
			logger.info("mmsPic is null");
			return null;
		} else {

			logger.info("relativePathFile*****************:"
					+ myBean.getRelateUrl());
			WatchAttach attach = new WatchAttach();
			attach.setPlaceId(myBean.getWatchId());
			attach.setRemark(myBean.getAttachRemark());
			attach.setFlag(1);
			attach.setAttachPath(myBean.getRelateUrl());
			bo.saveWatchInfoAttachEx(attach);

		}

		request.setAttribute("watchid", myBean.getWatchId());
		return mapping.findForward("viewLinkPicEx");
	}

}
