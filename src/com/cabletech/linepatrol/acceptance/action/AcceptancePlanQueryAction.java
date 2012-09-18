package com.cabletech.linepatrol.acceptance.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.acceptance.beans.ApplyBean;
import com.cabletech.linepatrol.acceptance.model.Apply;
import com.cabletech.linepatrol.acceptance.service.AcceptancePlanQueryManager;

/**
 * 修改验收交维计划
 * 
 * @author liusq
 * 
 */
public class AcceptancePlanQueryAction extends BaseInfoBaseDispatchAction {

	private static final long serialVersionUID = -908907927236749409L;

	/**
	 * 转向查询列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardPlanQueryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("list", null);
		return mapping.findForward("forwardPlanQueryPage");
	}

	/**
	 * 根据条件查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryAcceptancePlanResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplyBean bean = (ApplyBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
				.getBean("acceptancePlanQueryManager");
		List<DynaBean> list = am.queryAcceptancePlanResult(bean, userInfo);
		super.setPageReset(request);
		request.getSession().setAttribute("list", list);
		return mapping.findForward("forwardPlanQueryPage");
	}

	/**
	 * 转向编辑页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEditAcceptancePlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String applyId = request.getParameter("applyId");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String userName = userInfo.getUserName();
		ApplyBean applyBean = new ApplyBean();
		HttpSession session = request.getSession();
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
				.getBean("acceptancePlanQueryManager");
		Apply apply = am.get(applyId);
		session.setAttribute("apply", apply);
		request.setAttribute("userName", userName);
		request.setAttribute("applyBean", applyBean);
		return mapping.findForward("editAcceptancePlanForm");
	}

	/**
	 * 修改验收交维申请
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editAcceptancePlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplyBean applyBean = (ApplyBean)form;
		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
				.getBean("acceptancePlanQueryManager");
		am.saveApply(applyBean, apply);
		String url = (String) request.getSession().getAttribute("S_BACK_URL");
		return forwardInfoPageWithUrl(mapping, request, "editAcceptancePlanSuccess", url);
	}
	
	/**
	 * 获得光缆管道列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward getCablePipeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String applyId = request.getParameter("applyId");
		ApplyBean applyBean = new ApplyBean();
		HttpSession session = request.getSession();
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
				.getBean("acceptancePlanQueryManager");
		Apply apply = am.get(applyId);
		session.setAttribute("apply", apply);
		request.setAttribute("applyBean", applyBean);
		String canDelete = request.getParameter("canDelete");
		request.getSession().setAttribute("canDelete", canDelete);
		if(StringUtils.equals("cable", type)){
			return mapping.findForward("cableList");
		} else {
			return mapping.findForward("pipeList");
		}
	}

	/**
	 * 删除光缆信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deleteCableData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
		.getBean("acceptancePlanQueryManager");

		String id = request.getParameter("cableId");

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");
		apply = am.deleteCable(apply, id);
		session.setAttribute("apply", apply);

		request.setAttribute("canDelete", "can");
		return mapping.findForward("cableList");
	}

	/**
	 * 删除管道信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientException
	 * @throws Exception
	 */
	public ActionForward deletePipeData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WebApplicationContext ctx = getWebApplicationContext();
		AcceptancePlanQueryManager am = (AcceptancePlanQueryManager) ctx
		.getBean("acceptancePlanQueryManager");

		String id = request.getParameter("pipeId");

		HttpSession session = request.getSession();
		Apply apply = (Apply) session.getAttribute("apply");
		apply = am.deletePipe(apply, id);
		session.setAttribute("apply", apply);

		request.setAttribute("canDelete", "can");
		return mapping.findForward("pipeList");
	}
}
