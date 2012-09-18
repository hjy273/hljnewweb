package com.cabletech.commons.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

public class ViewPictureTag extends BodyTagSupport {
	private int number = 3;
	private int width=300;
	private int height=200;
	private String entityType;
	private String entityId;
	private ApplicationContext applicationContext;

	public int doEndTag() throws JspException {
		StringBuffer buf = new StringBuffer();
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(super.pageContext.getServletContext());
		UploadFileService service = (UploadFileService) applicationContext.getBean("uploadFileService");
		List<UploadFileInfo> fileInfos = service.getImageFile(entityId, entityType);
		String contextPath = super.pageContext.getServletContext()
		.getContextPath();
		if (fileInfos.size() <= 0) {
		} else {
			buf.append("<h4>Õº∆¨œ‘ æ<h4><center>");
			int size = fileInfos.size();
			for (int i = 0; i < size; i++) {
				if (i % number == 0) {
					buf.append("<br />");
				}
				UploadFileInfo uploadFileInfo = fileInfos.get(i);
				buf.append("<img  src=\""+contextPath+"/readLocalAction.do?method=readLocal&fId="+uploadFileInfo.getFileId()
						+ "\" width=\"" + width + "\" height=\"" + height + "\" />");
			}
			buf.append("</center>");
		}
		try {
			super.pageContext.getOut().print(buf.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.EVAL_PAGE;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
}
