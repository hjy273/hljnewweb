package com.cabletech.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;

public class UploadDemoAction extends BaseDispatchAction {
	public ActionForward uploadfile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, IOException {
		//		UploadFileBean bean = (UploadFileBean)form;
		WebApplicationContext ctx = getWebApplicationContext();
		UploadFileDemoManager upload = (UploadFileDemoManager) ctx.getBean("uploadFileDemoManager");
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		//		String [] delIds = request.getParameterValues("delfileid");
		List<FileItem> files = (List) request.getSession().getAttribute("FILES");
		//		System.out.println("size:"+bean.getAttachments().size());
		upload.uploadFile(files, user);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.print("³É¹¦");
		out.flush();
		return null;
	}

}
