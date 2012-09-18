package com.cabletech.report.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.report.beans.Report;
import com.cabletech.report.services.ReportServiceImpl;
import com.cabletech.uploadfile.SaveUploadFile;
import com.cabletech.uploadfile.UploadFile;
import com.cabletech.uploadfile.UploadUtil;

public class ReportAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger("ReportAction");
	ReportServiceImpl service = new ReportServiceImpl();

	// 添加
	public ActionForward addReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IllegalAccessException, InvocationTargetException {
		Report report = (Report) form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String datumid = uploadFile(form, new ArrayList(), user);
		report.setReporturl(datumid);
		report.setUserid(user.getUserID());
		report.setRegionid(user.getRegionid());
		boolean br = service.addReport(report);
		if (br) {
			return forwardInfoPage(mapping, request, "r140101s");
		} else {
			return forwardInfoPage(mapping, request, "r140101f");
		}

	}

	// 修改
	public ActionForward updateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Report report = (Report) form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		ArrayList fileIdList = new ArrayList();
		String[] delfileid = request.getParameterValues("delfileid"); // 要删除的文件id号数组
		StringTokenizer st = new StringTokenizer(report.getReporturl(), ",");
		while (st.hasMoreTokens()) {
			fileIdList.add(st.nextToken());
		}
		if (delfileid != null) {
			for (int i = 0; i < delfileid.length; i++) {
				fileIdList.remove(delfileid[i]);
			}
		}
		// 上传附件
		String datumid = uploadFile(form, fileIdList, user);
		report.setReporturl(datumid);
		report.setUserid(user.getUserID());
		boolean br = service.updateReport(report);
		if (br) {
			return forwardInfoPage(mapping, request, "r140103s");
		} else {
			return forwardInfoPage(mapping, request, "r140103f");
		}
	}

	// del
	public ActionForward delReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");

		if (id != null && !id.equals("")) {
			service.delReport(id);
			return forwardInfoPage(mapping, request, "r140102s");
		} else {
			return forwardInfoPage(mapping, request, "r140102f");
		}
	}

	// auditing report
	public ActionForward auditingReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Report report = (Report)form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		report.setAuditingman(user.getUserID());
		boolean br = service.auditingReport(report);
		if(br){
			return forwardInfoPage(mapping, request, "r140201s");
		}else{
			return forwardInfoPage(mapping, request, "r140201f");
		}
	}

	// get one Report
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String type = request.getParameter("t");//
		Report report = service.getReport(id);
		request.setAttribute("report", report);
		if(type.equals("edit")){
			return mapping.findForward("edit");
		}else if(type.equals("auditing")){
			return mapping.findForward("auditing");
		}else{
			return mapping.findForward("read");
		}
	}

	public ActionForward queryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Report report = (Report)form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
		"LOGIN_USER");
		String con = request.getParameter("con");
		List resultlist = service.getReportList(user, report,con);
		request.getSession().setAttribute("queryresult", resultlist);
		request.getSession().setAttribute("con",con);
		System.out.println("sssssssssssssssss***************************************");
		this.setPageReset(request);
		return mapping.findForward("queryreport");
	}

	/**
	 * 文件上传
	 * 
	 * @param form
	 *            ActionForm
	 * @return String
	 */
	public String uploadFile(ActionForm form, ArrayList fileIdList,
			UserInfo user) {
		Report formbean = (Report) form;
		// 开始处理上传文件================================
		List attachments = formbean.getAttachments();
		String fileId;
		for (int i = 0; i < attachments.size(); i++) {
			UploadFile uploadFile = (UploadFile) attachments.get(i);
			FormFile file = uploadFile.getFile();
			if (file == null) {
				log.info("file is null");
			} else {
				// 将文件存储到服务器并将路径写入数据库，返回记录ID
				fileId = SaveUploadFile.saveFile(file, user.getRegionid(),
						"报告");
				if (fileId != null) {
					fileIdList.add(fileId);
				}
			}
		}
		// 处理上传文件结束=======================================

		// 获取ID字符串列表(以逗号分隔的文件ID字符串)======================
		String fileIdListStr = UploadUtil.getFileIdList(fileIdList);
		// String fileIdListStr =processFileUpload(request);
		String datumid = "";
		if (fileIdListStr != null) {
			// logger.info( "FileIdListStr=" + fileIdListStr );
			datumid = fileIdListStr;
		}
		return datumid;
	}
}
