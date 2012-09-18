package com.cabletech.linepatrol.project.action;

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
import com.cabletech.linepatrol.project.beans.RemedyItemBean;
import com.cabletech.linepatrol.project.beans.RemedyTypeBean;
import com.cabletech.linepatrol.project.service.RemedyItemManager;
import com.cabletech.linepatrol.project.service.RemedyTypeManager;

public class RemedyTypeAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(RemedyTypeAction.class
			.getName());
	private WebApplicationContext ctx;
	private RemedyItemManager itemBO;
	private RemedyTypeManager bo;
	private UserInfo userInfo;

	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bo = (RemedyTypeManager) ctx.getBean("remedyTypeManager");
		itemBO = (RemedyItemManager) ctx.getBean("remedyItemManager");
	}

	/**
	 * 转到增加修缮类别的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addRemedyTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List items = bo.getItemsByRegionID(userInfo);
		request.setAttribute("items", items);
		return mapping.findForward("addTypeForm");
	}

	/**
	 * 转到增加修缮类别的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addRemedyTypeFormByOneItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		String itemId = (String) request.getAttribute("item_id");
		String itemName = (String) request.getAttribute("item_name");
		if (request.getParameter("item_id") != null
				&& !request.getParameter("item_id").equals("")) {
			if (itemId == null || itemId.equals("")) {
				itemId = request.getParameter("item_id");
			}
		}
		if (request.getParameter("item_name") != null
				&& !request.getParameter("item_name").equals("")) {
			if (itemName == null || itemName.equals("")) {
				itemName = request.getParameter("item_name");
			}
		}
		request.setAttribute("item_id", itemId);
		request.setAttribute("item_name", itemName);
		return mapping.findForward("addTypeFormByOneItem");
	}

	/**
	 * 保存修缮类别的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRemedyType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		RemedyTypeBean bean = (RemedyTypeBean) form;
		int itemID = bean.getItemID();
		String typeName = bean.getTypeName();
		boolean flag = bo.getTypessByIIDAndTName(itemID, typeName);
		if (flag) {
			boolean result = bo.addType(bean);
			if (result) {
				return super.forwardInfoPage(mapping, request, "512add");
			} else {
				return super.forwardErrorPage(mapping, request, "512addE");
			}
		} else {
			return super.forwardInfoPage(mapping, request, "512repeat");
		}
	}

	/**
	 * 保存修缮类别的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRemedyTypeByOneItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		RemedyTypeBean bean = (RemedyTypeBean) form;
		int itemID = bean.getItemID();
		String typeName = bean.getTypeName();
		String continueAddType = request.getParameter("continue_add_type");
		boolean flag = bo.getTypessByIIDAndTName(itemID, typeName);
		if (flag) {
			boolean result = bo.addType(bean);
			if (result) {
				request.setAttribute("item_id", request.getParameter("itemID"));
				request.setAttribute("item_name", request
						.getParameter("itemName"));
				if ("0".equals(continueAddType)) {
					String backUrl = request.getContextPath()
							+ "/project/remedy_type.do?method=addRemedyItemForm";
					return super.forwardInfoPageWithUrl(mapping, request,
							"512addall", backUrl);
				} else {
					form = new RemedyTypeBean();
					return mapping.findForward("addTypeFormByOneItem");
				}
			} else {
				String backUrl = request.getContextPath()
						+ "/project/remedy_type.do?method=addRemedyTypeFormByOneItem&&item_id="
						+ request.getParameter("itemID") + "&&item_name="
						+ request.getParameter("itemName");
				return super.forwardErrorPageWithUrl(mapping, request,
						"512addE", backUrl);
			}
		} else {
			String backUrl = request.getContextPath()
					+ "/project/remedy_type.do?method=addRemedyTypeFormByOneItem&&item_id="
					+ request.getParameter("itemID") + "&&item_name="
					+ request.getParameter("itemName");
			return super.forwardInfoPageWithUrl(mapping, request, "512repeat",
					backUrl);
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
	public ActionForward queryRemedyTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List items = bo.getItemsByRegionID(userInfo);
		request.setAttribute("items", items);
		return mapping.findForward("queryTypeForm");
	}

	/**
	 * 得到查询的修缮类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getRemedyTypes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		initialize(request);
		super.setPageReset(request);
		RemedyTypeBean bean = (RemedyTypeBean) form;
		List types = bo.getTypes(bean);
		request.getSession().setAttribute("types", types);
		return mapping.findForward("listTypes");
	}

	/**
	 * 转到修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRemedyTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String id = request.getParameter("id");
		RemedyTypeBean bean = bo.getTypeById(id);
		request.setAttribute("bean", bean);
		List items = bo.getItemsByRegionID(userInfo);
		request.setAttribute("items", items);
		return mapping.findForward("editRemedyTypeForm");
	}

	/**
	 * 转到查看页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewRemedyTypeForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String id = request.getParameter("id");
		RemedyTypeBean bean = bo.getTypeById(id);
		request.setAttribute("bean", bean);
		int itemID = bean.getItemID();
		RemedyItemBean itemBean = itemBO.getItemById(itemID + "");
		request.setAttribute("itemBean", itemBean);
		return mapping.findForward("viewRemedyTypeForm");
	}

	/**
	 * 修改修缮类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRemedyType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		RemedyTypeBean bean = (RemedyTypeBean) form;
		boolean result = bo.getTypeByBean(bean);
		if (!result) {
			return super.forwardInfoPageWithUrl(mapping, request,
					"512editRepeat", url);
		}
		boolean flag = bo.editType(bean);
		if (flag) {
			return super.forwardInfoPageWithUrl(mapping, request, "512edit",
					url);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request, "512editE",
					url);
		}
	}

	/**
	 * 删除修缮类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteRemedyType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		String id = request.getParameter("id");
		// TODO 删除类别
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		boolean flag = bo.deleteType(id);
		if (flag) {
			return super.forwardInfoPageWithUrl(mapping, request, "512dele",
					url);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request, "512deleE",
					url);
		}
	}

	/**
	 * 执行导出Excel
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
		List list = (List) request.getSession().getAttribute("types");
		initialize(request);
		bo.exportStorage(list, response);
		return null;
	}
}
