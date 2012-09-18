package com.cabletech.linepatrol.material.action;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ExportBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.web.ClientException;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.material.beans.MaterialInfoBean;
import com.cabletech.linepatrol.material.domain.MaterialInfo;
import com.cabletech.linepatrol.material.service.MaterialInfoBo;

public class MaterialInfoAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialInfoAction.class
			.getName());

	private MaterialInfoBo getMaterialInfoService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialInfoBo) ctx.getBean("materialInfoBo");
	}

	/**
	 * ת�����ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addMaterialInfoForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List modellist = null;
		List typelist = materialInfoBo.getTypeList(user);

		// ��һ��װ��
		if (typelist != null && typelist.size() > 0) {
			DynaBean bean = (DynaBean) typelist.get(0);
			// logger.info("Bean Test : " + bean.get("id") + " End Test");
			String id = bean.get("id").toString();
			modellist = materialInfoBo.getModelByTypeId(id, user);
		}
		request.setAttribute("typelist", typelist);
		request.setAttribute("modellist", modellist);
		return mapping.findForward("addMaterialInfoForm");
	}

	/**
	 * ��ѯ���������б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryTypeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String id = request.getParameter("id");
		logger.info("-------id test------" + id);
		List modellist = getMaterialInfoService().getModelByTypeId(id, user);
		request.setAttribute("modellist", modellist);
		return mapping.findForward("queryAjaxMAterial");
	}

	/**
	 * ��Ӳ�����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward addPartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		MaterialInfoBean bean = (MaterialInfoBean) form;
		MaterialInfo materialInfo = new MaterialInfo();

		bean.setId(this.getDbService().getSeq("linepatrol_mt_model", 20));
		BeanUtil.objectCopy(bean, materialInfo);
		String name = request.getParameter("name");
		String modelid = request.getParameter("modelid");
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		boolean flag = materialInfoBo.isHaveMaterialName(name, modelid);
		if (flag) {
			try {
				materialInfoBo.addPartBase(materialInfo);
				log(request,"��Ӳ�����Ϣ����������Ϊ��"+bean.getName()+"��","���Ϲ���");
				return forwardInfoPage(mapping, request, "c111");
			} catch (ServiceException e) {
				return forwardErrorPage(mapping, request, "error");
			}
		} else {
			return forwardErrorPage(mapping, request, "crepeat");
		}
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward loadPartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		MaterialInfoBean bean1 = (MaterialInfoBean) form;
		try {
			String id = request.getParameter("id");
			// List modellist = dao.getModelList();

			List modellist = materialInfoBo.getModelList(user);
			List typelist = materialInfoBo.getTypeList(user);
			// int mid = 0;
			// ��һ��װ��
			// if (typelist != null && typelist.size() > 0) {
			// DynaBean dybean = (DynaBean) typelist.get(0);
			// mid = dybean.get("id").toString();
			// modellist = dao.getModelByTypeId((mid));
			// }
			MaterialInfo bean = new MaterialInfo();
			BeanUtil.objectCopy(bean1, bean);
			request.setAttribute("typelist", typelist);
			request.setAttribute("modellist", modellist);
			bean = materialInfoBo.getPartBaseById(id, bean);
			request.setAttribute("MaterialInfoBean", bean);
		} catch (Exception e) {
			logger.info("����·����Ϣʧ�ܣ�");
			e.printStackTrace();
		}
		return mapping.findForward("updatepartbase");
	}

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deletePartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		MaterialInfo materalInfo=new MaterialInfo();
		String id = request.getParameter("id");
		materialInfoBo.getPartBaseById(id, materalInfo);
		String name=materalInfo.getName();
		try {
			materialInfoBo.deletePartbaseById(id);
			String strQueryString = getTotalQueryString(
					"method=queryPartBase&name=", (MaterialInfoBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			log(request,"ɾ��������Ϣ����������Ϊ��"+name+"��","���Ϲ���");
			return forwardInfoPage(mapping, request, "c333", null, args);
		} catch (ServiceException e) {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * ת����ѯҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryMaterialInfoForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		List modellist = materialInfoBo.getModelList(user);
		List typelist = materialInfoBo.getTypeList(user);
		request.setAttribute("typelist", typelist);
		request.setAttribute("modellist", modellist);
		return mapping.findForward("querymaterialInfoForm");
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ActionForward queryPartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws InvocationTargetException, IllegalAccessException {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialInfoBean bean = (MaterialInfoBean) form;
		MaterialInfo materialInfo = new MaterialInfo();
		BeanUtil.objectCopy(bean, materialInfo);
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		try {
			List list = materialInfoBo.getPartBaseBean(materialInfo, user);
			request.getSession().setAttribute("queryresult", list);
			List modellist = materialInfoBo.getModelList(user);
			List typelist = materialInfoBo.getTypeList(user);
			request.setAttribute("typelist", typelist);
			request.setAttribute("modellist", modellist);
			return mapping.findForward("querypartbaseresult");
		} catch (Exception e) {
			logger.error("��ѯ������Ϣ�쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * �޸Ĳ�����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward updatePartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		MaterialInfoBean bean = (MaterialInfoBean) form;
		MaterialInfo materialInfo = new MaterialInfo();
		BeanUtil.objectCopy(bean, materialInfo);
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		boolean flag = materialInfoBo.isHaveMaterialType(materialInfo);
		if (flag) {
			try {
				materialInfoBo.updatePartBase(materialInfo);
				String strQueryString = getTotalQueryString(
						"method=queryPartBase&name=",
						(MaterialInfoBean) request.getSession().getAttribute(
								"theQueryBean"));
				Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
						strQueryString, (String) request.getSession()
								.getAttribute("S_BACK_URL"));
				log(request,"�޸Ĳ�����Ϣ����������Ϊ��"+bean.getName()+"��","���Ϲ���");
				return forwardInfoPage(mapping, request, "c222", null, args);
			} catch (ServiceException e) {
				return forwardErrorPage(mapping, request, "error");
			}
		} else {
			String strQueryString = getTotalQueryString(
					"method=queryPartBase&name=", (MaterialInfoBean) request
							.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
					strQueryString, (String) request.getSession().getAttribute(
							"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "editRepeat", null, args);
		}
	}

	public String getTotalQueryString(String queryString, MaterialInfoBean bean) {
		if (bean.getName() != null) {
			queryString = queryString + bean.getName();
		}
		if (bean.getId() != null) {
			queryString = queryString + "&id=" + bean.getId();
		}
		if (bean.getModelid() != 0) {
			queryString = queryString + "&modelid=" + bean.getModelid();
		}
		if (bean.getFactory() != null) {
			queryString = queryString + "&factory=" + bean.getFactory();
		}
		return queryString;
	}

	/**
	 * �������Ͳ�ѯ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getModelByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ClientException, Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialInfoBo materialInfoBo = getMaterialInfoService();
		String type = request.getParameter("id");
		List list = materialInfoBo.getModelByTypeId(type, user);
		JSONArray ja = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportMaterialInfoResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ExportBO export = new ExportBO();
		try {
			logger.info(" ����dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("�õ�list");
			export.exportMaterialInfoResult(list, response);
			logger.info("���excel�ɹ�");
			return null;
		} catch (Exception e) {
			logger.error("������Ϣ��������쳣:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
