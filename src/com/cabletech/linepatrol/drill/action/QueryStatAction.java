package com.cabletech.linepatrol.drill.action;

import java.text.SimpleDateFormat;
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
import com.cabletech.linepatrol.drill.module.DrillPlan;
import com.cabletech.linepatrol.drill.module.DrillQueryCondition;
import com.cabletech.linepatrol.drill.module.DrillSummary;
import com.cabletech.linepatrol.drill.module.DrillTask;
import com.cabletech.linepatrol.drill.services.QueryStatBo;

public class QueryStatAction extends BaseInfoBaseDispatchAction {
	private static Logger logger = Logger.getLogger(QueryStatAction.class
			.getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private QueryStatBo getQueryStatBo() {
		WebApplicationContext ctx = getWebApplicationContext();
		return (QueryStatBo) ctx.getBean("queryStatBo");
	}

	/**
	 * 查询前加载数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryStatForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String operator = (String) request.getParameter("operator");
		Map map = getQueryStatBo().queryStatForm(userInfo);
		Object listOrg = map.get("list");
		List list = null;
		if (listOrg != null) {
			list = (List) listOrg;
		}
		String conId = (String) map.get("conId");
		String conName = (String) map.get("conName");
		String deptype = (String) map.get("deptype");
		request.getSession().setAttribute("list", list);
		request.getSession().setAttribute("conId", conId);
		request.getSession().setAttribute("conName", conName);
		request.getSession().setAttribute("deptype", deptype);
		// 清空session中的查询条件和查询结果
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		if ("query".equals(operator)) {
			return mapping.findForward("drillQuery");
		} else {
			return mapping.findForward("drillStat");
		}
	}

	/**
	 * 演练查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DrillQueryCondition drillQueryCondition = this
				.getDrillQueryCondition(request);
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			drillQueryCondition = (DrillQueryCondition) request.getSession()
					.getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition",
					drillQueryCondition);
		}
		List list = getQueryStatBo().queryStat(drillQueryCondition, userInfo);
		request.getSession().setAttribute("result", list);
		request.setAttribute("task_names", drillQueryCondition.getTaskNames());
		return mapping.findForward("queryList");
	}

	/**
	 * 演练统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward drillStat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		DrillQueryCondition drillQueryCondition = this
				.getDrillQueryCondition(request);
		// 判断formbean中是否有查询需要的相关数据，如果没有则将FormBean中的数据发到session中，否则从session中取出以前的数据
		if (null == request.getParameter("isQuery")) {
			drillQueryCondition = (DrillQueryCondition) request.getSession()
					.getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition",
					drillQueryCondition);
		}
		// 判断对象是否为空，如果为空，则创建对象
		if (drillQueryCondition == null) {
			drillQueryCondition = new DrillQueryCondition();
		}
		List list = getQueryStatBo().drillStat(drillQueryCondition, userInfo);
		request.getSession().setAttribute("result", list);
		return mapping.findForward("statList");
	}

	/**
	 * 封装查询条件
	 * 
	 * @param request
	 * @return
	 */
	public DrillQueryCondition getDrillQueryCondition(HttpServletRequest request) {
		DrillQueryCondition drillQueryCondition = new DrillQueryCondition();
		drillQueryCondition.setName(request.getParameter("name"));
		drillQueryCondition.setBeginTime(request.getParameter("beginTime"));
		drillQueryCondition.setEndTime(request.getParameter("endTime"));
		drillQueryCondition.setTaskStates(request
				.getParameterValues("taskStates"));
		String[] levels = request.getParameterValues("levels");
		String[] states = request.getParameterValues("state");
		drillQueryCondition.setLevels(levels);
		drillQueryCondition.setState(states);
		drillQueryCondition.setConId(request.getParameter("conId"));
		drillQueryCondition.setDrillState(request.getParameter("drillState"));
		return drillQueryCondition;
	}

	/**
	 * 导出演练列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportDrillList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute("result");
		getQueryStatBo().exportDrillList(list, response);
		return null;
	}

	/**
	 * 查看演练
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward viewDrill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String taskId = request.getParameter("taskId");
		String planId = request.getParameter("planId");
		String summaryId = request.getParameter("summaryId");
		String conId = request.getParameter("conId");
		Map map = getQueryStatBo().viewDrill(taskId, planId, summaryId);
		DrillTask drillTask = (DrillTask) map.get("drillTask");
		DrillPlan drillPlan = null;
		DrillSummary drillSummary = null;
		Evaluate evaluate = null;
		List list = null;
		Object list2 = map.get("list");
		Object drillPlan2 = map.get("drillPlan");
		Object drillSummary2 = map.get("drillSummary");
		Object evaluate2 = map.get("evaluate");
		if (drillPlan2 != null && !"".equals(drillPlan)) {
			drillPlan = (DrillPlan) drillPlan2;
			evaluate = (Evaluate) evaluate2;
		}
		if (list2 != null && !"".equals(list2)) {
			list = (List) list2;
		}
		if (drillSummary2 != null && !"".equals(drillSummary2)) {
			drillSummary = (DrillSummary) drillSummary2;
		}
		request.setAttribute("drillTask", drillTask);
		request.setAttribute("drillPlan", drillPlan);
		request.setAttribute("drillSummary", drillSummary);
		request.setAttribute("evaluate", evaluate);
		request.setAttribute("list", list);
		request.setAttribute("conId", conId);
		return mapping.findForward("viewDrill");
	}
}
