package com.cabletech.linepatrol.material.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.beans.MaterialTypeBean;
import com.cabletech.linepatrol.material.domain.MaterialType;
import com.cabletech.linepatrol.material.service.MaterialModelBo;
import com.cabletech.linepatrol.material.service.MaterialTypeBo;

/**
 * �������͹���
 * 
 * @author fjj
 * 
 */
public class MaterialTypeAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialTypeAction.class
			.getName());
	private OracleIDImpl ora = new OracleIDImpl();

	/**
	 * ���MaterialTypeBo����
	 */
	private MaterialTypeBo getMaterialTypeService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialTypeBo) ctx.getBean("materialTypeBo");
	}

	/**
	 * ���MaterialModelBo����
	 */
	private MaterialModelBo getMaterialModelService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialModelBo) ctx.getBean("materialModelBo");
	}

	/**
	 * ת�����Ӳ������͵�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addMaterialTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List regions = getMaterialTypeService().getRegions(userInfo);
		request.setAttribute("regions", regions);
		return mapping.findForward("addMaterialTypeForm");
	}

	/**
	 * ����������͵���Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ActionForward addMaterialType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException,
			IllegalAccessException {
		MaterialTypeBean bean = (MaterialTypeBean) form;
		MaterialType type = new MaterialType();
		String regionID = bean.getRegionID();
		String typeName = bean.getTypeName();
		boolean flag = getMaterialTypeService().isHaveMaterialType(regionID,
				typeName);
		if (flag) {
			type.setRegionID(regionID);
			type.setRemark(bean.getRemark());
			type.setTypeName(typeName);
			type.setState("1");
			try {
				MaterialType typeSave = getMaterialTypeService()
						.addMaterialType(type);
				String id = typeSave.getTid().toString();
				logger.info("********typeid:" + id);
				request.setAttribute("typeid", id);
				log(request,"����������ͣ���������Ϊ��"+typeName+"��","���Ϲ���");
				return this.addMaterialModelForm(mapping, form, request,
						response);
			} catch (ServiceException e) {
				return super.forwardErrorPage(mapping, request, "808addE");
			}
		} else {
			return super.forwardInfoPage(mapping, request, "808repeat");
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
	public ActionForward queryMaterialTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List regions = getMaterialTypeService().getRegions(user);
		request.setAttribute("regions", regions);
		return mapping.findForward("queryMaterialTypeForm");
	}

	/**
	 * �õ���ѯ�Ĳ�������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMaterialTypes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialTypeBean bean = (MaterialTypeBean) form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List types = getMaterialTypeService().getMaterialTypes(bean, user);
		logger.info("*****************" + types);
		request.getSession().setAttribute("materialTypes", types);
		super.setPageReset(request);
		return mapping.findForward("listMaterialTypes");
	}

	/**
	 * �ڲ�������ҳ��ת����Ӳ��Ϲ���ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ActionForward addMaterialModelForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException,
			IllegalAccessException {
		String typeid = (String) request.getAttribute("typeid");
		if (typeid == null || typeid.equals("")) {
			typeid = request.getParameter("typeid");
		}
		BasicDynaBean baseBean = getMaterialTypeService().getMaterialTypById(
				typeid);
		MaterialTypeBean bean = new MaterialTypeBean();
		bean = this.getMaterialTypeBeanFromBasicBean(baseBean);
		request.setAttribute("bean", bean);
		return mapping.findForward("addMaterialModelForm");
	}

	/**
	 * ������Ϲ�����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ActionForward addMaterialModel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException,
			IllegalAccessException {
		String act = request.getParameter("act");
		String modelName = request.getParameter("modelName");
		String remark = request.getParameter("remark");
		String unit = request.getParameter("unit");
		String id = request.getParameter("typeid");
		logger.info("**********id:" + id);
		if (act != null && act.equals("")) {// ���
			if (modelName == null || unit == null || modelName.equals("")
					|| unit.equals("")) {
				return super.forwardInfoPage(mapping, request, "8089addOver");
			}
		}
		MaterialModelBean bean = new MaterialModelBean();
		bean.setTypeID(Integer.parseInt(id));
		bean.setModelName(modelName);
		bean.setRemark(remark);
		bean.setUnit(unit);
		int typeid = bean.getTypeID();
		String modelname = bean.getModelName();
		boolean flag = getMaterialModelService().isHaveMaterialMode(typeid,
				modelname);
		if (flag) {
			try {
				getMaterialModelService().addMaterialModel(bean);
				log(request,"������Ϲ�񣨹������Ϊ��"+modelname+"��","���Ϲ���");
				if (act != null && act.equals("add")) {// ������ӹ��ҳ��
					request.setAttribute("typeid", id);
					return this.addMaterialModelForm(mapping, form, request,
							response);
				}
				return super.forwardInfoPage(mapping, request, "8089addOver");
			} catch (ServiceException e) {
				return super.forwardErrorPage(mapping, request, "8089addE");
			}
		} else {
			Object[] args = new Object[1];
			args[0] = "/WebApp/materialTypeAction.do?method=addMaterialModelForm&typeid="
					+ id;
			return super.forwardInfoPage(mapping, request, "8089repeat", null,
					args);
		}
	}

	/**
	 * �鿴��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ActionForward viewTypeByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws InvocationTargetException, IllegalAccessException {
		String id = request.getParameter("id");
		MaterialTypeBean bean = new MaterialTypeBean();
		BasicDynaBean basicBean = getMaterialTypeService().getMaterialTypById(
				id);
		if (basicBean != null) {
			BigDecimal tid = (BigDecimal) basicBean.get("id");
			String typename = (String) basicBean.get("typename");
			String regionid = (String) basicBean.get("regionid");
			String remark = (String) basicBean.get("remark");
			bean.setRegionID(regionid);
			bean.setRemark(remark);
			bean.setTypeName(typename);
			bean.setId(tid.intValue());
		}
		String regionId = bean.getRegionID();
		String regionName = getMaterialTypeService()
				.getRegionNameById(regionId);
		request.setAttribute("bean", bean);
		request.setAttribute("regionName", regionName);
		return mapping.findForward("viewMaterialType");
	}

	/**
	 * ת���޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ActionForward editMaterialTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws InvocationTargetException,
			IllegalAccessException {
		String id = request.getParameter("id");
		MaterialTypeBean bean = new MaterialTypeBean();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		BasicDynaBean basicBean = getMaterialTypeService().getMaterialTypById(
				id);
		bean = getMaterialTypeBeanFromBasicBean(basicBean);
		/*
		 * if(basicBean !=null){ BigDecimal tid = (BigDecimal)
		 * basicBean.get("id"); String typename =
		 * (String)basicBean.get("typename"); String regionid =
		 * (String)basicBean.get("regionid"); String remark =
		 * (String)basicBean.get("remark"); bean.setRegionID(regionid);
		 * bean.setRemark(remark); bean.setTypeName(typename);
		 * bean.setId(tid.intValue()); }
		 */
		request.setAttribute("bean", bean);
		List regions = getMaterialTypeService().getRegions(user);
		request.setAttribute("regions", regions);
		return mapping.findForward("editMaterialTypeForm");
	}

	public MaterialTypeBean getMaterialTypeBeanFromBasicBean(
			BasicDynaBean basicBean) {
		MaterialTypeBean bean = new MaterialTypeBean();
		BigDecimal tid = (BigDecimal) basicBean.get("id");
		String typename = (String) basicBean.get("typename");
		String regionid = (String) basicBean.get("regionid");
		String remark = (String) basicBean.get("remark");
		String state = (String) basicBean.get("state");
		bean.setRegionID(regionid);
		bean.setRemark(remark);
		bean.setTypeName(typename);
		bean.setId(tid.intValue());
		bean.setState(state);
		return bean;
	}

	/**
	 * �޸Ĳ�������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editMaterialType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		Object[] args = new Object[1];
		args[0] = request.getSession().getAttribute("S_BACK_URL");
		MaterialTypeBean bean = (MaterialTypeBean) form;
		boolean result = getMaterialTypeService().isHaveMaterialType(bean);
		if (!result) {
			return super.forwardInfoPage(mapping, request, "808editRepeat",
					null, args);
		}
		try {
			getMaterialTypeService().editMaterialType(bean);
			log(request,"�޸Ĳ������ͣ���������Ϊ��"+bean.getTypeName()+"��","���Ϲ���");
			return super.forwardInfoPage(mapping, request, "808edit", null,
					args);
		} catch (ServiceException e) {
			return super.forwardErrorPage(mapping, request, "808editE", args);
		}
	}

	/**
	 * ɾ����������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteMeterialType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		WebApplicationContext ctx=getWebApplicationContext();
		MaterialTypeBo mtb=(MaterialTypeBo)ctx.getBean("materialTypeBo");
		Object[] args = new Object[1];
		args[0] = request.getSession().getAttribute("S_BACK_URL");
		String id = request.getParameter("id");
		int typeId=Integer.valueOf(id);
		try {
			getMaterialTypeService().deleteType(id);
			log(request,"ɾ���������ͣ���������Ϊ��"+mtb.get(typeId).getTypeName()+"��","���Ϲ���");
			return super.forwardInfoPage(mapping, request, "808dele", null,
					args);
		} catch (ServiceException e) {
			return super.forwardErrorPage(mapping, request, "808deleE", args);
		}
	}

	/**
	 * ִ�е���Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportTypeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list = (List) request.getSession().getAttribute("materialTypes");
		getMaterialTypeService().exportStorage(list, response);
		return null;
	}

}
