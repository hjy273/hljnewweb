package com.cabletech.watchinfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.watchinfo.services.WatchPicBO;

public class DeleteWatchAttachAction  extends WatchinfoBaseAction {
	private static Logger logger = Logger.getLogger(DeleteWatchAttachAction.class);
	private WatchPicBO bo = new WatchPicBO();
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		
    	UserInfo user = ( UserInfo )request.getSession().getAttribute( "LOGIN_USER" );  
    	/*
	    if( "11".equals(user.getType()) || "21".equals(user.getType())){  //如果是省移动或者省代维
            return mapping.findForward( "powererror" );
        }
	    */
    	String watchid=request.getParameter("watchid");
    	String fileid=request.getParameter("fileid"); 
    	String flag=request.getParameter("flag");
    	String attachPath=request.getParameter("attachpath");
    	
    	bo.deleteWatchAttachByID(fileid, flag,attachPath);
    	
    	request.getSession().setAttribute("watchid", watchid);
    	request.setAttribute("watchid", watchid);
    	
    	return mapping.findForward( "viewLinkPicEx" );
	}

}
