package com.cabletech.commons.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

public class ImageServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		image(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		image(request, response);
	}

	public void image(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String imageId = (String) request.getParameter("imageId");
			UploadFileService uploadFileService = (UploadFileService) SpringContext.getApplicationContext().getBean(
					"uploadFileService");
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
	}
}
