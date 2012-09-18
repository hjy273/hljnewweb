package com.cabletech.linepatrol.material.action;

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
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.material.beans.MaterialModelBean;
import com.cabletech.linepatrol.material.service.MaterialModelBo;

/**
 * ���Ϲ��
 * 
 * @author fjj
 * 
 */
public class MaterialModelAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialModelAction.class
			.getName());

	/**
	 * ���MaterialModelBo����
	 * 
	 * @return
	 */
	private MaterialModelBo getConPersonalService() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (MaterialModelBo) ctx.getBean("materialModelBo");
	}

	/**
	 * ת�����Ӳ��Ϲ���ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addMaterialModelForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List lists = getConPersonalService().getTypesByRegionID(user);
		request.setAttribute("lists", lists);
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
	 */
	public ActionForward addMaterialModel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialModelBo materialModelBO = getConPersonalService();
		MaterialModelBean bean = (MaterialModelBean) form;
		int typeid = bean.getTypeID();
		String modelname = bean.getModelName();
		boolean flag = materialModelBO.isHaveMaterialMode(typeid, modelname);
		if (flag) {
			try {
				materialModelBO.addMaterialModel(bean);
				log(request,"������Ϲ����Ϣ�����Ϲ������Ϊ��"+bean.getModelName()+"��","���Ϲ���");
				return super.forwardInfoPage(mapping, request, "809add");
			} catch (ServiceException e) {
				return super.forwardErrorPage(mapping, request, "809addE");
			}
		} else {
			return super.forwardInfoPage(mapping, request, "809repeat");
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
	public ActionForward queryMaterialModelForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MaterialModelBo materialModelBO = getConPersonalService();
		List regions = materialModelBO.getRegions(user);
		request.setAttribute("regions", regions);
		List types = materialModelBO.getTypesByRegionID(user);
		request.setAttribute("types", types);
		return mapping.findForward("queryMaterialModelForm");
	}

	/**
	 * �õ���ѯ�Ĳ��Ϲ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getMaterialModels(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialModelBean bean = (MaterialModelBean) form;
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String isQuery=request.getParameter("isQuery");
		if(isQuery!=null){
			bean=(MaterialModelBean)request.getSession().getAttribute("MaterialModelBean");
		}
		List models = getConPersonalService().getMaterialModels(bean, user);
		request.getSession().setAttribute("MaterialModelBean", bean);
		request.getSession().setAttribute("materialModels", models);
		super.setPageReset(request);
		return mapping.findForward("listMaterialModels");
	}

	/**
	 * �鿴���Ϲ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewModelByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		MaterialModelBean bean = new MaterialModelBean();
		BasicDynaBean basicBean = getConPersonalService().getMaterialModelById(
				id);
		String typename = "";
		if (basicBean != null) {
			String modelName = (String) basicBean.get("modelname");
			typename = (String) basicBean.get("typename");
			String remark = (String) basicBean.get("remark");
			String unit = (String) basicBean.get("unit");
			bean.setUnit(unit);
			bean.setRemark(remark);
			bean.setModelName(modelName);
		}
		request.setAttribute("bean", bean);
		request.setAttribute("typename", typename);
		return mapping.findForward("viewMaterialModel");
	}

	/**
	 * ת���޸�ҳ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editMaterialModelForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialModelBo materialModelBO = getConPersonalService();
		String id = request.getParameter("id");
		MaterialModelBean bean = new MaterialModelBean();
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		BasicDynaBean basicBean = materialModelBO.getMaterialModelById(id);
		if (basicBean != null) {
			BigDecimal tid = (BigDecimal) basicBean.get("id");
			String modelName = (String) basicBean.get("modelname");
			BigDecimal typeID = (BigDecimal) basicBean.get("typeid");
			String remark = (String) basicBean.get("remark");
			String unit = (String) basicBean.get("unit");
			bean.setTid(tid.intValue());
			bean.setUnit(unit);
			bean.setTypeID(typeID.intValue());
			bean.setRemark(remark);
			bean.setModelName(modelName);
		}
		request.setAttribute("bean", bean);
		List lists = materialModelBO.getTypesByRegionID(user);
		logger.info("***********lists:" + lists);
		request.setAttribute("lists", lists);
		return mapping.findForward("editMaterialModelForm");
	}

	/**
	 * �޸Ĳ��Ϲ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editMaterialModel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialModelBo materialModelBO = getConPersonalService();
		Object[] args = new Object[1];
		args[0] = request.getSession().getAttribute("S_BACK_URL")+"&isQuery=isQuery";
		MaterialModelBean bean = (MaterialModelBean) form;
		boolean result = materialModelBO.getMaterialModelsByBean(bean);
		if (!result) {
			return super.forwardInfoPage(mapping, request, "809editRepeat",
					null, args);
		}
		try {
			materialModelBO.editMaterialModel(bean);
			log(request,"�޸Ĳ��Ϲ�񣨲��Ϲ������Ϊ��"+bean.getModelName()+"��","���Ϲ���");
			return super.forwardInfoPage(mapping, request, "809edit", null,
					args);
		} catch (ServiceException e) {
			return super.forwardErrorPage(mapping, request, "809editE", args);
		}
	}

	/**
	 * ɾ�����Ϲ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteMeterialModel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialModelBo materialModelBO = getConPersonalService();
		Object[] args = new Object[1];
		args[0] = request.getSession().getAttribute("S_BACK_URL")+"&isQuery=isQuery";
		String id = request.getParameter("id");
		String name=materialModelBO.get(Integer.valueOf(id)).getModelName();
		try {
			getConPersonalService().deleteModel(id);
			log(request,"ɾ�����Ϲ�񣨲��Ϲ������Ϊ��"+name+"��","���Ϲ���");
			return super.forwardInfoPage(mapping, request, "809dele", null,
					args);
		} catch (ServiceException e) {
			return super.forwardErrorPage(mapping, request, "809deleE", args);
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
	public ActionForward exportModelList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list = (List) request.getSession().getAttribute("materialModels");
		getConPersonalService().exportStorage(list, response);
		return null;
	}

}
