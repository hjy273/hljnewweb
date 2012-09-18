package com.cabletech.linepatrol.resource.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.resource.beans.DatumInfoBean;
import com.cabletech.linepatrol.resource.model.DatumInfo;
import com.cabletech.linepatrol.resource.model.DatumType;
import com.cabletech.linepatrol.resource.service.DatumManager;

public class DatumAction extends BaseInfoBaseDispatchAction {
	
	public ActionForward add(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		HttpSession session = request.getSession();
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		
		DatumInfoBean datumInfo = (DatumInfoBean)form;
		datumManager.add(datumInfo, files, userInfo);
		log(request,"添加维护资料（维护资料名称为："+datumInfo.getName()+"）","资料管理");
		return forwardInfoPage(mapping, request, "addDatumInfoSuccess");
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		
		session.setAttribute("result", datumManager.getDatumList(userInfo));
		return mapping.findForward("list");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");		
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		session.setAttribute("datumInfo", datumManager.edit(id));
		session.setAttribute("addOnes", datumManager.historyPass(id));
		return mapping.findForward("edit");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		HttpSession session = request.getSession();
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		UserInfo userInfo = (UserInfo) session.getAttribute("LOGIN_USER");
		
		DatumInfoBean datumInfoBean = (DatumInfoBean)form;
		String name=datumManager.getDatumInfoById(datumInfoBean.getId()).getName();
		session.removeAttribute("FILES");
		if(files == null || files.isEmpty()){
			return forwardInfoPage(mapping, request, "updateFileNull");
		}else{
			if(files.size() != 1){
				return forwardInfoPage(mapping, request, "updateFileTooMany");
			}else{
				datumManager.update(datumInfoBean, files, userInfo);
				log(request,"更新维护资料（维护资料名称为："+name+"）","资料管理");
				return forwardInfoPage(mapping, request, "updateInfoSuccess");
			}
		}
	}
	
	public ActionForward approveLink(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		
		session.setAttribute("datumInfo", datumManager.edit(id));
		session.setAttribute("addOnes", datumManager.historyPass(id));
		session.setAttribute("addOnesApprove", datumManager.historyNotPass(id));
		return mapping.findForward("approve");
	}
	
	public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		String id = request.getParameter("id");
		String result = request.getParameter("approve");
		
		if(result.equals(AcceptanceConstant.PASSED)){
			String[] addOnes = request.getParameterValues("addOneId");
			try {
				datumManager.approve(id,addOnes);
			} catch (Exception e) {
				return forwardInfoPage(mapping, request, "datumApproveRepeatError");
			}
		}else{
			datumManager.approveNotPass(id);
		}
		log(request,"审核维护资料（维护资料名称为："+datumManager.get(id).getName()+"）","资料管理");
		return forwardInfoPage(mapping, request, "datumApproveSuccess");
	}
	
	public ActionForward history(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		String id = request.getParameter("id");
		DatumInfo datum = datumManager.getDatumInfoById(id);
		HttpSession session = request.getSession();
		session.setAttribute("datuminfo", datum);
		session.setAttribute("addOnes", datumManager.historyPass(id));
		return mapping.findForward("history");
	}
	
	public ActionForward addType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		DatumInfoBean datumInfo = (DatumInfoBean)form;
		if(datumManager.hadDulicateName(datumInfo.getName())){
			return forwardInfoPage(mapping, request, "duplicateName");
		}else{
			datumManager.addType(datumInfo.getName());
			return forwardInfoPage(mapping, request, "addTypeSuccess");
		}
	}
	
	public ActionForward getType(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		String html = datumManager.getTypeString();
		
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(html);
		out.flush();
		return null;
	}
	public ActionForward typelist(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		
		HttpSession session = request.getSession();
		session.setAttribute("typelist", datumManager.getType());
		return mapping.findForward("typelist");
	}
	
	public ActionForward delType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		datumManager.delType(id);
		return mapping.findForward("typelists");
	}
	public ActionForward upType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		WebApplicationContext ctx = getWebApplicationContext();
		HttpSession session = request.getSession();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");	
		session.setAttribute("datumtype", datumManager.getTypeById(id));
		return mapping.findForward("updateType");
	}
	public ActionForward updateType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		DatumType datumtype=new DatumType();
		datumtype.setId(id);
		datumtype.setName(name);
		WebApplicationContext ctx = getWebApplicationContext();
		DatumManager datumManager = (DatumManager) ctx.getBean("datumManager");
		datumManager.updateType(datumtype);
		return mapping.findForward("typelists");
	}
	/**
	 * 验证是否包含附件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validateFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		String html="";
		if (files==null||files.size()<=0) {
			html = "请添加一个附件";
		}

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(html);
		out.flush();
		return null;
	}
}
