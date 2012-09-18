package com.cabletech.sparepartmanage.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.sparepartmanage.beans.SparepartBaseInfoBean;
import com.cabletech.sparepartmanage.services.SeparepartBaseInfoService;

public class SeparepartBaseInfoAction extends BaseDispatchAction {
	Logger logger = Logger.getLogger(this.getClass().getName());
	SeparepartBaseInfoService service = new SeparepartBaseInfoService();
	
	/**
	 * ��ʾ���ӱ�����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showAddSeparepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("showAddSparepart");
	}
	
	
	/**
	 * ���ӱ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addSeparepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SparepartBaseInfoBean bean = (SparepartBaseInfoBean)form;
		bean.setSparePartState("0");
		//����������Ϣ�����ظ�
		boolean result = service.judgeIsHaveSparePart(bean,"");
		if(result){
			return super.forwardInfoPage(mapping, request, "90802Fail");
		}
		if(service.addSeparepart(bean)) {
			return forwardInfoPage( mapping, request, "90802" );
		} else {
			return forwardErrorPage( mapping, request, "90802error" );
		}
	}
	
	/**
	 * ת����ѯ����ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward showQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List facList = service.getAllFactory();
		request.setAttribute("facList", facList);		
		return mapping.findForward("showQuery");
	}
	
	
	/**
	 * ���ݳ��̻�ȡ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getNameByFac(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String fac = request.getParameter("productFactory");
		List nameList = service.getNameByFactory(fac);
		JSONArray ja = JSONArray.fromObject(nameList);
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
	 * �������ƣ����̻�ȡ�ͺ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getModelByName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String name = request.getParameter("sparePartName");
		String fac = request.getParameter("productFactory");
		List modelList = service.getModelByName(name, fac);
		JSONArray ja = JSONArray.fromObject(modelList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "�ͺ�");
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
	 * �����ͺţ����̣����ƻ�ȡ����汾
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getVersionByModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String model = request.getParameter("sparePartModel");
		String fac = request.getParameter("productFactory"); 
		String name = request.getParameter("sparePartName");
		List versionlList = service.getVersionByModel(model,fac, name);
		JSONArray ja = JSONArray.fromObject(versionlList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			logger.info(ja.toString() + "����汾");
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
	 * ���ز�ѯ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showQueryResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SparepartBaseInfoBean bean = (SparepartBaseInfoBean)form;
		List queryList = service.doQuery(bean,request.getSession());
		request.getSession().setAttribute("resList", queryList);
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		String deptType = userinfo.getDeptype();
		request.getSession().setAttribute("deptType", deptType);
		super.setPageReset(request);
		return mapping.findForward("showQueryRes");
	}
	
	/**
	 * ��ȡָ����������ϸ��Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getOneSeparepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tid = request.getParameter("id");
		SparepartBaseInfoBean bean = service.getOneSparepart(tid);
		request.setAttribute("oneInfo", bean);
		return mapping.findForward("showOneInfo");
	}
	
	/**
	 * ��ȡָ��ָ����������ϸ��Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward ModOneSeparepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tid = request.getParameter("id");
		SparepartBaseInfoBean bean = service.getOneSparepart(tid);
		request.setAttribute("oneInfo", bean);
		return mapping.findForward("showEdit");
	}
	
	/**
	 * ִ���޸ı���������Ϣ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SparepartBaseInfoBean bean = (SparepartBaseInfoBean)form;
		//����������Ϣ�����ظ�
		boolean result = service.judgeIsHaveSparePart(bean,"edit");
		if(result){
			return super.forwardInfoPage(mapping, request, "90802FailEdit");
		}
		if(service.modifySeparepar(bean)) {
			return forwardInfoPage( mapping, request, "90804" );
		} else {
			return forwardErrorPage(mapping, request, "90804error");
		}
	}
	
	/**
	 * ɾ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delSeparepart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String tid = request.getParameter("id");
		boolean result = service.judgeStorageIsHave(tid);//�жϿ����Ƿ��Ѿ�ʹ���˴˱���Ϣ
		if(result){
			return forwardErrorPage( mapping, request, "90805Fail" );
		}
		if(service.delSeparepar(tid)) {
			return forwardInfoPage( mapping, request, "90805" );
		} else {
			return forwardErrorPage(mapping, request, "90805error");
		}
	}
	
	/**
	 * ����ɾ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delMore(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String idStr = request.getParameter("idStr");
		if(service.delMore(idStr)) {
			logger.info(idStr + "~~~~~~~~~~~~~~~");
			return forwardInfoPage( mapping, request, "90805" );
		} else {
			return forwardErrorPage(mapping, request, "90805error");
		}
	}
	
	public ActionForward showQueryAfterMod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//String sql = (String) request.getSession().getAttribute("spbQueryCon");
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		List resList = (List) request.getSession().getAttribute("resList");
		List ids = new ArrayList();
		if(resList !=null && resList.size()>0){
			for(int i = 0;i<resList.size();i++){
				BasicDynaBean bean = (BasicDynaBean) resList.get(i);
				String id = (String) bean.get("tid");
				ids.add(id);
			}
		}
		
		List queryList =  service.doQueryAfterMod(ids);
		String deptType = userinfo.getDeptype();
		request.setAttribute("deptType", deptType);	
		request.getSession().setAttribute("resList", queryList);
		super.setPageReset(request);
		return mapping.findForward("showQueryRes");
	}
	
	/**
	 * �������ƣ����̻�ȡ�ͺ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getModelByNameForStockSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String name = request.getParameter("sparePartName");
		String fac = request.getParameter("productFactory");
		List modelList = service.getModelByNameForStockSave(name, fac);
		JSONArray ja = JSONArray.fromObject(modelList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			System.out.println(ja.toString() + "�ͺ���ID");
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
	 * ���ݳ��̻�ȡ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getNameByFacForStockQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/json; charset=GBK");
		String fac = request.getParameter("productFactory");
		List nameList = service.getNameByFacForStockQuery(fac);
		JSONArray ja = JSONArray.fromObject(nameList);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(ja.toString());
			out.flush();
			System.out.println(ja.toString() + "����");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				out.close();
			}
		}
		return null;
	}
	
	
}
