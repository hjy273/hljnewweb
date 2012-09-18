package com.cabletech.commons.upload;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;
/**
 * image 用于显示上传的图片。
 * 使用说明：<apptag:image entityId="0000000012" entityType="LP_TROUBLE" height="200" width="300"/>
 * @author zhj
 *
 */
public class ImageTag extends TagSupport  {
	private String entityId ="";
	private String entityType ="";
	private String height = "120";
	private String width = "200";
	public int doStartTag() throws JspException {
		UploadFileService  uploadFileService = (UploadFileService)SpringContext.getApplicationContext().getBean("uploadFileService");
		List<UploadFileInfo> fileList = uploadFileService.getImageFile(entityId,entityType);
		StringBuilder html = new StringBuilder();
		//html.append("<div>");
		int i= 1;
		for(UploadFileInfo image:fileList){
			html.append("<img src=\"/WebApp/imageServlet?imageId="+image.getFileId()+"\"  height=\""+height+"\" width=\""+width+"\">");
			if( (i%2)==0){
				html.append("<br>");
			}
			i++;
		}
		//html.append("</div>");
		try {
			this.pageContext.getOut().print(html.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this.SKIP_BODY;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
}
