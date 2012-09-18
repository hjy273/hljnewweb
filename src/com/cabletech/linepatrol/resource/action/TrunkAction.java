package com.cabletech.linepatrol.resource.action;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.upload.module.UploadFileInfo;
import com.cabletech.commons.web.ClientException;
import com.cabletech.linepatrol.acceptance.service.AcceptanceConstant;
import com.cabletech.linepatrol.resource.model.RepeaterSection;
import com.cabletech.linepatrol.resource.service.TrunkManager;
/**
 * 中继段选择工具
 * @author Administrator
 *
 */
public class TrunkAction extends BaseInfoBaseDispatchAction {
	/**
	 * 加载中继段选择窗口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward link(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		String id = request.getParameter("id");
		String trunkList = request.getParameter("trunkList");
		
		request.setAttribute("id", id);
		HttpSession session = request.getSession();
		
		Set<String> trunkIds = new HashSet<String>();
		if(StringUtils.isNotBlank(trunkList)){
			trunkIds = new HashSet<String>(Arrays.asList(trunkList.split(",")));
		}
		session.setAttribute("trunkIds", trunkIds);
		return mapping.findForward("link");
	}
	
	/**
	 * 中继段选择控件-按照条件模糊搜索中继段
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward queryTrunks(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		UserInfo user = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		
		String filter = request.getParameter("filter");
		String level = request.getParameter("level");
		
		HttpSession session = request.getSession();
		Set<String> trunkIds = (Set<String>)session.getAttribute("trunkIds");
		List<RepeaterSection> trunkList = trunkManager.getTrunks(filter, level, user);
		request.setAttribute("trunkList", trunkManager.getTrunkstr(trunkIds, trunkList));
		
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("filter", filter);
		request.setAttribute("level", level);
		
		return mapping.findForward("link");
	}
	
	public ActionForward checkall(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String filter = request.getParameter("filter");
		String level = request.getParameter("level");
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo)session.getAttribute("LOGIN_USER");
		Set<String> trunkIds = (Set<String>)session.getAttribute("trunkIds");
		
		List<RepeaterSection> trunkList = trunkManager.getTrunks(filter, level, user);
		trunkIds = trunkManager.setTrunks(trunkIds, trunkList);
		session.setAttribute("trunkIds", trunkIds);
		
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("filter", filter);
		request.setAttribute("level", level);
		request.setAttribute("trunkList", trunkManager.getTrunkstr(trunkIds, trunkList));
		return mapping.findForward("link");
	}
	public ActionForward cancelCheckAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception{
		request.getSession().setAttribute("trunkIds", new HashSet<String>());
		return mapping.findForward("queryTrunks");
	}
	public ActionForward addTrunks(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
		Set<String> trunkIds = (Set<String>)session.getAttribute("trunkIds");
		
		if(type.equals("add")){
			trunkIds.add(id);
		}else if(type.equals("delete")){
			if(trunkIds.contains(id))
				trunkIds.remove(id);
		}
		session.setAttribute("trunkIds", trunkIds);
		
		return null;
	}
	
	/**
	 * 中继段选择控件保存到sesion中
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		
		HttpSession session = request.getSession();
		Set<String> trunkIds = (Set<String>)session.getAttribute("trunkIds");
		
		if(trunkIds.isEmpty()){
			return forwardInfoPage(mapping, request, "trunkSaveError");
		}else{
			request.setAttribute("id", id);
			request.setAttribute("trunkIds", StringUtils.join(trunkIds.iterator(),","));
			request.setAttribute("trunknames", trunkManager.getTrunkNameString(trunkIds));
			return mapping.findForward("close");
		}
	}
	
	public ActionForward updateTrunk(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		session.setAttribute("name", trunkManager.getTrunk(id));
		session.setAttribute("addOnes", trunkManager.getAnnexAddOneList(id, type, AcceptanceConstant.USABLE));
		return mapping.findForward("updateTrunk");
	}
	
	public ActionForward updateFile(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute("LOGIN_USER");
		
		List<FileItem> files = (List<FileItem>) session.getAttribute("FILES");
		List<UploadFileInfo> ufiles = (List<UploadFileInfo>) session.getAttribute("OLDFILE");
		int size = 0;
		if(files != null){
			size += files.size();
		}
		if(ufiles != null){
			size += ufiles.size();
		}
		
		session.removeAttribute("FILES");
		if(size == 0){
			return forwardInfoPage(mapping, request, "updateFileNull");
		}else if(size>1){
			return forwardInfoPage(mapping, request, "updateFileTooManyBack");
		}else{
			trunkManager.updateFile(files, id, userInfo, type);
			return forwardInfoPage(mapping, request, "updateSuccess");
		}
	}
	
	public ActionForward updateTrunkApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		session.setAttribute("name", trunkManager.getTrunk(id));
		session.setAttribute("addOnes", trunkManager.getAnnexAddOneList(id, type, AcceptanceConstant.USABLE));
		session.setAttribute("addOnesApprove", trunkManager.getAnnexAddOneList(id, type, AcceptanceConstant.UNUSABLE));
		return mapping.findForward("updateTrunkApprove");
	}
	
	public ActionForward updateApprove(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String result = request.getParameter("approve");
		
		if(result.equals(AcceptanceConstant.PASSED)){
			trunkManager.updateApprove(id, type);
			return forwardInfoPage(mapping, request, "updateApprovePass");
		}else{
			return forwardInfoPage(mapping, request, "updateApproveNoPass");
		}
	}
	
	public ActionForward updateTrunkView(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws ClientException,
            Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		TrunkManager trunkManager = (TrunkManager) ctx.getBean("trunkManager");
		
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		
		HttpSession session = request.getSession();
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		session.setAttribute("name", trunkManager.getTrunk(id));
		session.setAttribute("addOnes", trunkManager.getAnnexAddOneList(id, type, AcceptanceConstant.USABLE));
		session.setAttribute("addOnesApprove", trunkManager.getAnnexAddOneList(id, type, AcceptanceConstant.UNUSABLE));
		return mapping.findForward("updateTrunkView");
	}
}
