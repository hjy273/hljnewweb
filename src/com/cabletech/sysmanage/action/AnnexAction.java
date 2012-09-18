package com.cabletech.sysmanage.action;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.apache.struts.action.*;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.commons.upload.module.AnnexAddOne;
import com.cabletech.commons.upload.service.UploadFileService;
import com.cabletech.sysmanage.services.AnnexService;

public class AnnexAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger
			.getLogger(AnnexAction.class.getName());

	public AnnexAction() {

	}

	/**
	 * �����ѯ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAnnexForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AnnexService annexService = (AnnexService) ctx.getBean("annexService");
		if (request.getSession().getAttribute("requestMap") != null) {
			request.setAttribute("requestMap", request.getSession()
					.getAttribute("requestMap"));
			request.getSession().setAttribute("requestMap", null);
		}
		request.getSession().setAttribute("module_catalog_list",
				annexService.getModuleCatalog());
		return mapping.findForward("queryAnnex");
	}

	/**
	 * �޸ĸ������Ʊ�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateFileNameForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AnnexService annexService = (AnnexService) ctx.getBean("annexService");
		String fileId = request.getParameter("file_id");
		AnnexAddOne info = annexService.get(fileId);
		String name = annexService.getFileNameById(info.getFileId())
				.getOriginalName();
		request.setAttribute("annex_id", fileId);
		request.setAttribute("file_name", name.substring(0, name
				.lastIndexOf(".")));
		return mapping.findForward("updateFileName");
	}

	/**
	 * �޸ĸ�������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateAnnexFileName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		AnnexService annexService = (AnnexService) ctx.getBean("annexService");
		String fileId = request.getParameter("annex_id");
		String oldName = request.getParameter("old_name");
		String name = request.getParameter("original_name");
		annexService.updateFileName(fileId, name);
		log(request, "�޸ĸ���������ԭ����Ϊ��" + oldName + "��������Ϊ��" + name + "��", "ϵͳ����");
		PrintWriter out = response.getWriter();
		out.print("<script type='text/javascript'>");
		out.print("window.opener.location.href=window.opener.location.href;");
		out.print("window.close();");
		out.print("</script>");
		return null;
	}

	/**
	 * ������ѯ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		AnnexService annexService = (AnnexService) ctx.getBean("annexService");
		String moduleCatalog;
		String uploader;
		String originalName;
		String beginTime;
		String endTime;
		Map map = new HashMap();
		if (request.getSession().getAttribute("requestMap") == null) {
			moduleCatalog = request.getParameter("module_catalog");
			uploader = request.getParameter("uploader");
			originalName = request.getParameter("original_name");
			beginTime = request.getParameter("beginTime");
			endTime = request.getParameter("endTime");
			map.put("moduleCatalog", moduleCatalog);
			map.put("uploader", uploader);
			map.put("originalName", originalName);
			map.put("beginTime", beginTime);
			map.put("endTime", endTime);
		} else {
			map = (Map) request.getSession().getAttribute("requestMap");
			moduleCatalog = (String) map.get("moduleCatalog");
			uploader = (String) map.get("uploader");
			originalName = (String) map.get("originalName");
			beginTime = (String) map.get("beginTime");
			endTime = (String) map.get("endTime");
		}
		List list = annexService.findAnnex(moduleCatalog, uploader,
				originalName, beginTime, endTime);

		request.getSession().setAttribute("annex_list", list);
		request.getSession().setAttribute("requestMap", map);
		return mapping.findForward("view");
	}

	/**
	 * ����ɾ�� ������������upload/recycle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteAnnex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext ctx = getWebApplicationContext();
		UploadFileService uploadFileService = (UploadFileService) ctx
				.getBean("uploadFileService");
		AnnexService annexService = (AnnexService) ctx.getBean("annexService");
		String[] ids = request.getParameterValues("id");
		if (ids != null) {
			for (String id : ids) {
				AnnexAddOne info = annexService.get(id);
				String name = annexService.getFileNameById(info.getFileId())
						.getOriginalName();
				uploadFileService.delAnnexFile(id);
				log(request, "ɾ����������������Ϊ��" + name + "��", "ϵͳ����");
			}
		}
		return mapping.findForward("querytoAnnex");
	}
}
