package com.cabletech.commons.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import com.cabletech.commons.config.SpringContext;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.upload.service.UploadFileService;

/**
 * 上传附件，不限制附件格式； 使用：<apptag:upload cssClass="uploadbutton" entityId="000000012"
 * entityType="LP_TROUBLE" state="edit"/> 注意：已上传的附件存在session中的 FILES变量中。
 * 
 * @author zhj
 * 
 */
public class UploadFileTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String cssClass = "";
	private String entityId = "";
	private String entityType = "";
	private String useable = "1";
	private List<FileItem> value;
	private String isOutScript = "true";
	private String state = "";// add； edit；look；

	public int doStartTag() throws JspException {
		HttpSession session = this.pageContext.getSession();
		session.setAttribute("FILES", null);
		UploadFileService uploadFileService = (UploadFileService) SpringContext
				.getApplicationContext().getBean("uploadFileService");
		List<UploadFileInfo> fileList = uploadFileService.getFilesByIds(
				entityId, entityType, getUseable());
		StringBuilder builder = new StringBuilder();
		if ("look".equals(state)) {
			builder.append("<div id=\"lookfile\">");
			int i = 0;
			for (UploadFileInfo upfile : fileList) {
				if (upfile != null
						&& StringUtils.isNotBlank(upfile.getFileType())) {
					if (upfile.getFileType().toLowerCase().equals("jpeg")
							|| upfile.getFileType().toLowerCase().equals("jpg")
							|| upfile.getFileType().toLowerCase().equals("gif")) {// 判断是否是图片
						// 是就显示出来
						builder.append("<a href='/bspweb/download.do?method=download&fileid="
								+ upfile.getFileId() + "'>");
						builder.append("<img src=\"/WebApp/imageServlet?imageId="
								+ upfile.getFileId()
								+ "\"  height=\"120\" width=\"200\">");
						String imageId = upfile.getFileId();
						uploadFileService = (UploadFileService) SpringContext
								.getApplicationContext().getBean(
										"uploadFileService");
						UploadFileInfo imageFile = uploadFileService
								.getFileId(imageId);
						// 显示图片
						String absolutePathFile = UploadFileService.UPLOADROOT
								+ File.separator + imageFile.getSavePath();
						ServletResponse response = this.pageContext
								.getResponse();
						InputStream input;
						byte[] image;	
						int len = 0;
						try {
							input = new FileInputStream(absolutePathFile);
							image = new byte[input.available()]; 
							ServletOutputStream out = response
									.getOutputStream();
							while ((len = input.read(image)) != -1) {
								out.write(image, 0, len);
							}
							out.flush();
							out.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						builder.append("</a><br/>");
					} else {
						builder.append("<a href='/bspweb/download.do?method=download&fileid="
								+ upfile.getFileId()
								+ "'><img border='0' src='/bspweb/images/attachment.gif' width='15' height='15'>"
								+ upfile.getOriginalName() + "</a><br>");
					}
				}
				i++;
			}
			if (i == 0) {
				builder.append("<font style='color:red'>无任何附件</font>");
			}
			builder.append("</div>");
		} else {
			if ("true".equals(isOutScript)) {
				StringBuilder script = new StringBuilder();
				script.append("<script type='text/javascript' src='/WebApp/js/uploader/ajaxupload.js'></script>");
				script.append("<script type='text/javascript'>");
				script.append("function removefile(delid){jQuery.ajax({type:\"POST\",url:'/WebApp/UploadServlet?type=remove&delid='+delid,cache : false,success:function(result){jQuery('#newfile').empty();jQuery('#newfile').html(result);}	});	}");
				script.append("function del(delid){jQuery.ajax({type:\"POST\",url:'/WebApp/UploadServlet?type=del&delid='+delid,cache : false,success:function(result){jQuery('#oldfile').empty();jQuery('#oldfile').html(result);}	});	}");

				script.append("jQuery(document).ready(function(){var button = jQuery('#upload'), interval;new AjaxUpload(button, {action: '/WebApp/UploadServlet?type=upload', name: 'myfile',");
				script.append("onSubmit : function(file, ext){	button.text('正在上传');	jQuery('#msg').empty();this.disable();	interval = window.setInterval(function(){var text = button.text();if (text.length < 13){button.text(text + '.');}");
				script.append("else {button.text('正在上传');}}, 200);},");
				script.append("onComplete: function(file, response){button.text('添加附件');window.clearInterval(interval);this.enable();if(response.indexOf(\"msg:\") == -1){jQuery('#msg').empty();jQuery('#newfile').empty();");
				script.append("jQuery('#newfile').html(response);}else{error = response.split(\":\");jQuery('#msg').html(error[1]);	}}});});");
				script.append("/*]]>*/</script>");

				// script.append("<style type=\"text/css\">");
				// script.append(".wrapper{ width: 120px; height:20px; font-size: 12px;}");
				// script.append("div.upfbtn{height: 20px;width: 100px;font-size: 12px;color: #C7D92C;  text-align: right; padding-top: 4px;padding-bottom:3px;}");
				// script.append("div.upfbtn.hover {color: #95A226;}");
				// script.append("</style>");
				builder.append(script);
			}
			builder.append("<a href=\"javascript:;\" id=\"upload\">添加附件</a><span id=\"msg\" style=\"color:red;\"></span>");
			// builder.append("<div class=\"wrapper\"><div id=\"upload\" class=\"upfbtn\">添加附件</div><span id=\"msg\" style=\"color:red;\"></span></div>");
			if ("edit".equals(state)) {
				session.setAttribute("OLDFILE", fileList);
				builder.append("<div id=\"oldfile\">");
				int i = 0;
				for (UploadFileInfo upfile : fileList) {
					if (upfile != null
							&& StringUtils.isNotBlank(upfile.getFileType())) {
						if (upfile.getFileType().toLowerCase().equals("jpeg")
								|| upfile.getFileType().toLowerCase()
										.equals("jpg")
								|| upfile.getFileType().toLowerCase()
										.equals("gif")) {// 判断是否是图片
						/*	builder.append("<img src=\"/WebApp/imageServlet?imageId="
									+ upfile.getFileId()
									+ "\"  height=\"120\" width=\"200\">");*/
							
							String imageId = upfile.getFileId();
							uploadFileService = (UploadFileService) SpringContext
									.getApplicationContext().getBean(
											"uploadFileService");
							UploadFileInfo imageFile = uploadFileService
									.getFileId(imageId);
							// 显示图片
							String absolutePathFile = UploadFileService.UPLOADROOT
									+ File.separator + imageFile.getSavePath();
							ServletResponse response = this.pageContext
									.getResponse();
							InputStream input;
							byte[] image;	
							int len = 0;
							try {
								input = new FileInputStream(absolutePathFile);
								image = new byte[input.available()]; 
								ServletOutputStream out = response
										.getOutputStream();
								while ((len = input.read(image)) != -1) {
									out.write(image, 0, len);
								}
								out.flush();
								out.close();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							builder.append("<br/>");
						}
					}
					builder.append("<a href='javascript:;' onclick='del(\""
							+ i
							+ "\")'><img src=\"/WebApp/js/uploader/del.gif\" border=\"0\" alt=\"移除\"/></a>");
					builder.append("<a href='/WebApp/download.do?method=download&fileid="
							+ upfile.getFileId()
							+ "'><img border='0' src='/WebApp/images/attachment.gif' width='15' height='15'>"
							+ upfile.getOriginalName() + "</a><br>");
					i++;
				}
				builder.append("</div>");
			}
			builder.append("<div id=\"newfile\" class=\"files\">");
			if (value != null) {
				session.setAttribute("FILES", value);
				int i = 0;
				for (FileItem item : value) {
					builder.append("<a href='javascript:;' onclick='remove(\""
							+ i
							+ "\")'><img src=\"/WebApp/js/uploader/del.gif\"/  border=\"0\" alt=\"移除\"></a>  "
							+ item.getName() + "<br>");// 终于成功了,还不到你的上传文件中看看,你要的东西都到齐了吗
					i++;
				}
			}
			builder.append("</div>");
		}

		try {
			this.pageContext.getOut().print(builder.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIsOutScript() {
		return isOutScript;
	}

	public void setIsOutScript(String isOutScript) {
		this.isOutScript = isOutScript;
	}

	public List<FileItem> getValue() {
		return value;
	}

	public void setValue(List<FileItem> value) {
		this.value = value;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

}
