package com.cabletech.partmanage.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ExportBO;
import com.cabletech.commons.web.ClientException;
import com.cabletech.partmanage.beans.MaterialInfoBean;
import com.cabletech.partmanage.dao.MaterialInfoDao;

public class MaterialInfoAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(MaterialInfoAction.class
			.getName());

	/**
	 * 转到添加页面
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
		MaterialInfoDao dao = new MaterialInfoDao();
		List modellist = null;
		List typelist = dao.getTypeList();

		// 第一次装载
		if (typelist != null && typelist.size() > 0) {
			DynaBean bean = (DynaBean) typelist.get(0);
			// logger.info("Bean Test : " + bean.get("id") + " End Test");
			String id = bean.get("id").toString();
			modellist = dao.getModelByTypeId((id));
		}
		request.setAttribute("typelist", typelist);
		request.setAttribute("modellist", modellist);
		return mapping.findForward("addMaterialInfoForm");
	}

	/**
	 * 查询材料类型列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryTypeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialInfoDao dao = new MaterialInfoDao();
		String id = request.getParameter("id");
		logger.info("-------id test------" + id);
		List modellist = dao.getModelByTypeId((id));
		request.setAttribute("modellist", modellist);
		return mapping.findForward("queryAjaxMAterial");
	}

	/**
	 * 添加材料信息
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
		MaterialInfoDao dao = new MaterialInfoDao();
		bean.setId(this.getDbService().getSeq("linepatrol_mt_model", 20));
		String name = request.getParameter("name");
		String modelid = request.getParameter("modelid");
		boolean flag = dao.isHaveMaterialName(name, modelid);
		if(flag){
			boolean sucess = dao.addPartBase(bean);
			if (sucess) {
				return forwardInfoPage(mapping, request, "c111");
			} else {
				log(request, " 增加材料信息 ", " 材料管理 ");
				return forwardErrorPage(mapping, request, "error");
			}
		}else{
			return forwardErrorPage(mapping,request,"crepeat");
		}
	}

	/**
	 * 载入材料信息
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
		MaterialInfoDao dao = new MaterialInfoDao();
		MaterialInfoBean bean = (MaterialInfoBean) form;
		try {
			String id = request.getParameter("id");
			// List modellist = dao.getModelList();

			List modellist = dao.getModelList();
			List typelist = dao.getTypeList();
			//	int mid = 0;
			// 第一次装载
//			if (typelist != null && typelist.size() > 0) {
//			DynaBean dybean = (DynaBean) typelist.get(0);
//			mid = dybean.get("id").toString();
//			modellist = dao.getModelByTypeId((mid));
//			}
			request.setAttribute("typelist", typelist);
			request.setAttribute("modellist", modellist);
			bean = dao.getPartBaseById(id, bean);
			request.setAttribute("MaterialInfoBean", bean);
		} catch (Exception e) {
			logger.info("加载路由信息失败！");
			e.printStackTrace();
		}
		return mapping.findForward("updatepartbase");
	}

	/**
	 * 删除材料信息
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
		MaterialInfoDao dao = new MaterialInfoDao();
		String id = request.getParameter("id");
		boolean sucess = dao.deletePartbaseById(id);
		if (sucess) {
			log(request, " 删除材料信息 ", "材料管理 ");
			String strQueryString = getTotalQueryString(
					"method=queryPartBase&name=", (MaterialInfoBean) request
					.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
					strQueryString, (String) request.getSession().getAttribute(
					"S_BACK_URL"));
			return forwardInfoPage(mapping, request, "c333", null, args);
		} else {
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 转到查询页面
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
		MaterialInfoDao dao = new MaterialInfoDao();
		List modellist = dao.getModelList();
		List typelist = dao.getTypeList();
		request.setAttribute("typelist", typelist);
		request.setAttribute("modellist", modellist);
		return mapping.findForward("querymaterialInfoForm");
	}

	/**
	 * 查询材料信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryPartBase(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialInfoBean bean = (MaterialInfoBean) form;
		MaterialInfoDao dao = new MaterialInfoDao();
		request.getSession().setAttribute("S_BACK_URL", null); //
		request.getSession().setAttribute("theQueryBean", bean);//
		try {
			List list = dao.getPartBaseBean(bean);
			request.getSession().setAttribute("queryresult", list);
			super.setPageReset(request);
			List modellist = dao.getModelList();
			List typelist = dao.getTypeList();
			request.setAttribute("typelist", typelist);
			request.setAttribute("modellist", modellist);
			return mapping.findForward("querypartbaseresult");
		} catch (Exception e) {
			logger.error("查询材料信息异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 修改材料信息
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
		MaterialInfoDao dao = new MaterialInfoDao();
		boolean flag = dao.isHaveMaterialType(bean);
		if(flag){
			boolean sucess = dao.updatePartBase(bean);
			if (sucess) {
				log(request, " 更新材料存放信息 ", " 材料管理 ");
				String strQueryString = getTotalQueryString(
						"method=queryPartBase&name=", (MaterialInfoBean) request
						.getSession().getAttribute("theQueryBean"));
				Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
						strQueryString, (String) request.getSession().getAttribute(
						"S_BACK_URL"));
				return forwardInfoPage(mapping, request, "c222", null, args);
			} else {
				return forwardErrorPage(mapping, request, "error");
			}
		}else{
			String strQueryString = getTotalQueryString(
					"method=queryPartBase&name=", (MaterialInfoBean) request
					.getSession().getAttribute("theQueryBean"));
			Object[] args = getURLArgs("/WebApp/materialInfoAction.do",
					strQueryString, (String) request.getSession().getAttribute(
					"S_BACK_URL"));
			return forwardInfoPage(mapping,request,"editRepeat",null,args);
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
	 * 根据类型查询规格
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
		MaterialInfoDao dao = new MaterialInfoDao();
		String type = request.getParameter("id");
		List list = dao.getModelByTypeId(type);
		//System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
		JSONArray ja = JSONArray.fromObject(list);
		PrintWriter out = response.getWriter();
		out.write(ja.toString());
		out.flush();
		return null;
	}

	/**
	 * 导出材料信息
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
			logger.info(" 创建dao");
			List list = (List) request.getSession().getAttribute("queryresult");
			logger.info("得到list");
			export.exportMaterialInfoResult(list, response);
			logger.info("输出excel成功");
			return null;
		} catch (Exception e) {
			logger.error("导出信息报表出现异常:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}
}
