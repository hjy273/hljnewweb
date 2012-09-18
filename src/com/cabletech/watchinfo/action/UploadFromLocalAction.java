package com.cabletech.watchinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.watchinfo.beans.UploadFromLocalBean;
import com.cabletech.watchinfo.domainobjects.WatchAttach;
import com.cabletech.watchinfo.services.WatchPicBO;

public class UploadFromLocalAction extends WatchinfoBaseAction {
	private static Logger logger = Logger
			.getLogger(UploadFromLocalAction.class);
	private WatchPicBO bo = new WatchPicBO();

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");

		UploadFromLocalBean myBean = (UploadFromLocalBean) form;

		FormFile file = myBean.getAttachFile();

		if (file == null) {
			logger.info("file is null");
			return null;
		} else {
			// ���ļ��洢������������·��д�����ݿ⣬���ؼ�¼ID
			String relativePathFile = SaveUploadFile.saveFile(file,
					"watchinfo_attach");


			if (relativePathFile != null) {
				logger.info("relativePathFile*****************:"
						+ relativePathFile);
				WatchAttach attach = new WatchAttach();
				if(myBean.getWatchId()==null){
					String watchId=(String)request.getSession().getAttribute("watchid");
					attach.setPlaceId(watchId);
				}else{
				    attach.setPlaceId(myBean.getWatchId());
				}
				attach.setRemark(myBean.getAttachRemark());
				attach.setFlag(2);
				attach.setAttachPath(relativePathFile);
				try{
					bo.saveWatchInfoAttachEx(attach);
				}catch(Exception ex){
					ex.printStackTrace();
				}

			}
		}

		request.setAttribute("watchid", myBean.getWatchId());
		return mapping.findForward("viewLinkPicEx");
	}

}
