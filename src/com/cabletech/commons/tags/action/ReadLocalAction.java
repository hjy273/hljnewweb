package com.cabletech.commons.tags.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

public class ReadLocalAction extends BaseDispatchAction {
	public ActionForward readLocal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			WebApplicationContext applicationContext = getWebApplicationContext();
			String imageId = (String) request.getParameter("fId");
			UploadFileService uploadFileService = (UploadFileService) applicationContext.getBean("uploadFileService");
			UploadFileInfo imageFile = uploadFileService.getFileId(imageId);
			//œ‘ æÕº∆¨
			String absolutePathFile = UploadFileService.UPLOADROOT + File.separator + imageFile.getSavePath();

			InputStream input = new FileInputStream(absolutePathFile);
			byte[] image = new byte[input.available()];
			ServletOutputStream out = response.getOutputStream();
			int len = 0;
			while ((len = input.read(image)) != -1) {
				out.write(image, 0, len);
			}
			out.flush();
			out.close();
		} catch (Exception ex) {
			response.getWriter().write("");
		}
		return null;
	}

}
