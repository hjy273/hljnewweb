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
import com.cabletech.linepatrol.project.service.RemedyItemManager;
import com.cabletech.linepatrol.project.service.RemedyTypeManager;

public class RemedyItemAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(RemedyItemAction.class
			.getName());
	private UserInfo userInfo;
	private WebApplicationContext ctx;
	private RemedyItemManager bo;
	private RemedyTypeManager typeBO;

	public void initialize(HttpServletRequest request) {
		ctx = getWebApplicationContext();
		userInfo = (UserInfo) request.getSession().getAttribute("LOGIN_USER");
		bo = (RemedyItemManager) ctx.getBean("remedyItemManager");
		typeBO = (RemedyTypeManager) ctx.getBean("remedyTypeManager");
	}

	/**
	 * 转到增加修缮项的页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addRemedyItemForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List regions = bo.getRegions(userInfo);
		request.setAttribute("regions", regions);
		return mapping.findForward("addItemForm");
	}

	/**
	 * 保存修缮项的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addRemedyItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		RemedyItemBean bean = (RemedyItemBean) form;
		String regionID = bean.getRegionID();
		String itemName = bean.getItemName();
		String continueAddType = request.getParameter("continue_add_type");
		boolean flag = bo.isHaveItem(regionID, itemName);
		if (flag) {
			boolean result = bo.addItem(bean);
			if (result) {
				if ("0".equals(continueAddType)) {
					return super.forwardInfoPage(mapping, request, "511add");
				} else {
					request.setAttribute("item_id", bean.getTid());
					request.setAttribute("item_name", bean.getItemName());
					return mapping.findForward("addItemTypeForm");
				}
			} else {
				return super.forwardErrorPage(mapping, request, "511addE");
			}
		} else {
			return super.forwardInfoPage(mapping, request, "511repeat");
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
	public ActionForward queryRemedyItemForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		List regions = bo.getRegions(userInfo);
		request.setAttribute("regions", regions);
		return mapping.findForward("queryItemForm");
	}

	/**
	 * 得到查询的修缮项目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getRemedyItems(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		initialize(request);
		RemedyItemBean bean = (RemedyItemBean) form;
		super.setPageReset(request);
		request.getSession().setAttribute("querybean", bean);
		List items = bo.getItems(bean);
		request.getSession().setAttribute("items", items);
		return mapping.findForward("listItems");
	}

	/**
	 * 根据修缮项查询修缮类别列表的返回到原条件查询的修缮项列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getRemedyItemsByBack(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		RemedyItemBean bean = (RemedyItemBean) request.getSession()
				.getAttribute("querybean");
		super.setPageReset(request);
		List items = bo.getItems(bean);
		request.getSession().setAttribute("items", items);
		return mapping.findForward("listItems");
	}

	/**
	 * 根据修缮项id查询修缮类别
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getTypesByItemID(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		String id = request.getParameter("id");
		List types = typeBO.getTypeByItemID(id);
		request.getSession().setAttribute("types", types);
		return mapping.findForward("listTypesByItemID");
	}

	/**
	 * 查看修缮项目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTypesByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String id = request.getParameter("id");
		RemedyItemBean bean = bo.getItemById(id);
		String regionId = bean.getRegionID();
		String regionName = bo.getRegionNameById(regionId);
		request.setAttribute("bean", bean);
		request.setAttribute("regionName", regionName);
		return mapping.findForward("viewRemedyItem");
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
	public ActionForward editRemedyItemForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initialize(request);
		String id = request.getParameter("id");
		RemedyItemBean bean = bo.getItemById(id);
		request.setAttribute("bean", bean);
		List regions = bo.getRegions(userInfo);
		request.setAttribute("regions", regions);
		return mapping.findForward("editRemedyItemForm");
	}

	/**
	 * 修改修缮项目
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editRemedyItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initialize(request);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		RemedyItemBean bean = (RemedyItemBean) form;
		boolean result = bo.isHaveItem(bean);
		if (!result) {
			return super.forwardInfoPage(mapping, request, "511editRepeat",
					null, url);
		}
		boolean flag = bo.editItem(bean);
		if (flag) {
			return super.forwardInfoPageWithUrl(mapping, request, "511edit",
					url);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request, "511editE",
					url);
		}
	}

	/**
	 * 删除修缮项
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteRemedyItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		initialize(request);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		String id = request.getParameter("id");
		boolean result = bo.getItemByApply(id);
		if (result) {
			return super.forwardInfoPageWithUrl(mapping, request, "511deleN",
					url);
		}
		boolean flag = bo.deleteItem(id);
		if (flag) {
			return super.forwardInfoPageWithUrl(mapping, request, "511dele",
					url);
		} else {
			return super.forwardErrorPageWithUrl(mapping, request, "511deleE",
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
	public ActionForward exportItemList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("导出修缮项目=========action===");
		List list = (List) request.getSession().getAttribute("items");
		initialize(request);
		bo.exportStorage(list, response);
		return null;
	}

}
