package com.cabletech.linepatrol.safeguard.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.WebApplicationContext;

import com.cabletech.baseinfo.action.BaseInfoBaseDispatchAction;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.linepatrol.commons.module.Evaluate;
import com.cabletech.linepatrol.safeguard.beans.SafeguardTaskBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardPlan;
import com.cabletech.linepatrol.safeguard.module.SafeguardSummary;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;
import com.cabletech.linepatrol.safeguard.services.SafeguardQueryStatBo;

public class SafeguardQueryStatAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger
			.getLogger(SafeguardQueryStatAction.class.getName());

	private SafeguardQueryStatBo getSafeguardQueryStatBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (SafeguardQueryStatBo) ctx.getBean("safeguardQueryStatBo");
	}

	/**
	 * 保障查询统计前加载数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward safeguardQueryStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String operator = request.getParameter("operator");
		Map map = getSafeguardQueryStatBo().safeguardQueryStatForm(userInfo);
		request.getSession().setAttribute("map", map);
		request.getSession().setAttribute("operator", operator);
		// 清空session中的查询条件和查询结果
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("Query_List");
		return mapping.findForward("safeguardQuery");
	}

	/**
	 * 保障查询统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SafeguardTaskBean safeguardTaskBean = (SafeguardTaskBean) form;
		String operator = safeguardTaskBean.getOperator();
		if ("query".equals(operator)) {
			safeguardQuery(form, request);
			return mapping.findForward("safeguardQueryList");
		} else {
			safeguardStat(form, request);
			return mapping.findForward("safeguardStatList");
		}
	}

	/**
	 * 保障查询
	 * 
	 * @param form
	 * @param request
	 */
	public void safeguardQuery(ActionForm form, HttpServletRequest request) {
		SafeguardTaskBean safeguardTaskBean = (SafeguardTaskBean) form;
//		String[] levels = request.getParameterValues("levels");
//		if (levels != null && levels.length > 0) {
//			String level = levels[0];
//			for (int i = 1; i < levels.length; i++) {
//				level += "," + levels[i];
//			}
//			safeguardTaskBean.setLevel(level);
//		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			safeguardTaskBean = (SafeguardTaskBean) request.getSession()
					.getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition",
					safeguardTaskBean);
		}
		if (safeguardTaskBean == null) {
			safeguardTaskBean = new SafeguardTaskBean();
		}
		super.setPageReset(request);
		List list = getSafeguardQueryStatBo().safeguardQuery(safeguardTaskBean,
				userInfo);
		request.getSession().setAttribute("Query_List", list);
		request.setAttribute("task_names", safeguardTaskBean.getTaskNames());
	}

	/**
	 * 保障统计
	 * 
	 * @param form
	 * @param request
	 */
	public void safeguardStat(ActionForm form, HttpServletRequest request) {
		SafeguardTaskBean safeguardTaskBean = (SafeguardTaskBean) form;
		String[] levels = request.getParameterValues("levels");
		if (levels != null && levels.length > 0) {
			String level = levels[0];
			for (String s : levels) {
				level += "," + s;
			}
			safeguardTaskBean.setLevel(level);
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			safeguardTaskBean = (SafeguardTaskBean) request.getSession()
					.getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition",
					safeguardTaskBean);
		}
		if (safeguardTaskBean == null) {
			safeguardTaskBean = new SafeguardTaskBean();
		}
		super.setPageReset(request);
		List list = getSafeguardQueryStatBo().safeguardStat(safeguardTaskBean,
				userInfo);
		request.getSession().setAttribute("Query_List", list);
	}

	/**
	 * 导出查询列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportSafeguardList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute("Query_List");
		getSafeguardQueryStatBo().exportSafeguardList(list, response);
		return null;
	}

	/**
	 * 查看保障
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewSafeguard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		String planId = request.getParameter("planId");
		String summaryId = request.getParameter("summaryId");
		String conId = request.getParameter("conId");
		Map map = getSafeguardQueryStatBo().viewSafeguard(taskId, planId,
				summaryId);
		SafeguardTask safeguardTask = (SafeguardTask) map.get("safeguardTask");
		SafeguardPlan safeguardPlan = null;
		SafeguardSummary safeguardSummary = null;
		Evaluate evaluate = null;
		String sublineIds = (String) map.get("sublineIds");
		Object safeguardPlan2 = map.get("safeguardPlan");
		Object safeguardSummary2 = map.get("safeguardSummary");
		Object evaluate2 = map.get("evaluate");
		List list = null;
		Object list2 = map.get("list");
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		if (safeguardPlan2 != null && !"".equals(safeguardPlan)) {
			safeguardPlan = (SafeguardPlan) safeguardPlan2;
			evaluate = (Evaluate) evaluate2;
		}
		if (safeguardSummary2 != null && !"".equals(safeguardSummary2)) {
			safeguardSummary = (SafeguardSummary) safeguardSummary2;
		}
		Object safeguardSpObj = map.get("safeguardSps");
		Object specialPlanObj = map.get("specialPlans");
		List safeguardSps = null;
		List specialPlans = null;
		if (safeguardSpObj != null) {
			safeguardSps = (List) safeguardSpObj;
			specialPlans = (List) specialPlanObj;
		}
		request.setAttribute("safeguardTask", safeguardTask);
		request.setAttribute("safeguardPlan", safeguardPlan);
		request.setAttribute("safeguardSummary", safeguardSummary);
		request.setAttribute("evaluate", evaluate);
		request.setAttribute("sublineIds", sublineIds);
		request.setAttribute("safeguardSps", safeguardSps);
		request.setAttribute("specialPlans", specialPlans);
		request.setAttribute("list", list);
		request.setAttribute("conId", conId);
		return mapping.findForward("viewSafeguard");
	}
}
