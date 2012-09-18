package com.cabletech.linepatrol.material.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.material.beans.MaterialAllotBean;
import com.cabletech.linepatrol.material.domain.MaterialChangeItem;
import com.cabletech.linepatrol.material.service.MaterialAllotBo;

/**
 * ���ϵ�����
 * @author fjj
 *
 */
public class MaterialAllotAction extends BaseInfoBaseDispatchAction{
	private static Logger logger = Logger.getLogger(MaterialAllotAction.class.getName());

	private MaterialAllotBo getMaterialAllotService(){
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialAllotBo)ctx.getBean("materialAllotBo");
	}
	
	/**
	 * ת�����ϵ�����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addMaterialAllotForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		MaterialAllotBo materialAllotBo = getMaterialAllotService();
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		List list = new ArrayList();
		if(deptype.equals("1")){//�ƶ�
			List cons = materialAllotBo.getConsByDeptId(user);
			list = materialAllotBo.getAllMTAddr();
			request.setAttribute("cons",cons);
		}
		if(deptype.equals("2")){//��ά
			list = materialAllotBo.getMTAddrByConId(deptid);
		}
		request.setAttribute("address",list);
		List materials = materialAllotBo.getMaterialsByAddreId(user,"");
		request.setAttribute("materials",materials);
		log(request,"���ϵ���","���Ϲ���");
		if(deptype.equals("1")){//�ƶ�
			return mapping.findForward("addAllotMobileForm");
		}else{
			return mapping.findForward("addAllotContraForm");
		}
	}

	/**
	 * ת���������ӻ��ύҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveMaterialsAllot(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		MaterialAllotBean bean =(MaterialAllotBean)form;
		boolean flag = getMaterialAllotService().addMaterialAllot(bean, user);
		if(flag){
			return super.forwardInfoPage(mapping, request, "810add");
		}else{
			return super.forwardErrorPage(mapping, request, "810addE");
		}
	}




	/**
	 * ת�����ϵ����鿴ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryMaterialAllotForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		MaterialAllotBo materialAllotBo = getMaterialAllotService();
		UserInfo user = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptype = user.getDeptype();
		String deptid = user.getDeptID();
		List list = new ArrayList();
		if(deptype.equals("1")){//�ƶ�
			List cons = materialAllotBo.getConsByDeptId(user);
			list = materialAllotBo.getAllMTAddr();
			request.setAttribute("cons",cons);
		}
		if(deptype.equals("2")){//��ά
			list = materialAllotBo.getMTAddrByConId(deptid);
		}
		request.setAttribute("address",list);
		return mapping.findForward("queryMaterialAllotForm");
	}
	
	/**
	 * ���ݴ�ά��ѯ��ַ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAddressByCon(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String id = request.getParameter("constactid");
		List address = getMaterialAllotService().getMTAddrByConId(id);
		JSONArray ja = JSONArray.fromObject(address);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "����");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
		return null;
	}
	
	/**
	 * �õ���ѯ�Ĳ��ϵ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMaterialAllots(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String allotState = request.getParameter("allotState");
		String addrID = request.getParameter("addrID");
		String constactid = request.getParameter("constactid");
		String changedate = request.getParameter("changedate");
		MaterialChangeItem changeItem = new MaterialChangeItem();
		changeItem.setChangedate(changedate);
		changeItem.setContractorid(constactid);
		changeItem.setState(allotState);
		changeItem.setAddreid(addrID);
		List allots = getMaterialAllotService().getMaterialAllots(changeItem);
		request.getSession().setAttribute("materialAllots",allots);
		super.setPageReset(request);
		return mapping.findForward("listMaterialAllots");
	}	
	
	/**
	 * �õ���ѯ�Ĳ��ϵ�����ϸ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMaterialAllotItems(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String allotid = request.getParameter("allotid");
		List allotItems = getMaterialAllotService().getMaterialAllotItems(allotid);
		request.getSession().setAttribute("materialAllotItemss",allotItems);
		return mapping.findForward("listMaterialAllotItems");
	}	
	

	/**
	 * ������ѯ�Ĳ��ϵ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportAllotList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("materialAllots");
		getMaterialAllotService().exportStorageAllot(list, response);
		return null;
	}

	 /*
	  * �������ϵ�����ϸ
	  *  @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportAllotDetailList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("materialAllotItemss");
		getMaterialAllotService().exportStorageItems(list, response);
		return null;
	}


}
