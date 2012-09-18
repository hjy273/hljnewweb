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
	 * ��ѯǰ��������
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
		// ���session�еĲ�ѯ�����Ͳ�ѯ���
		request.getSession().removeAttribute("queryCondition");
		request.getSession().removeAttribute("result");
		if ("query".equals(operator)) {
			return mapping.findForward("drillQuery");
		} else {
			return mapping.findForward("drillStat");
		}
	}

	/**
	 * ������ѯ
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
		// �ж�formbean���Ƿ��в�ѯ��Ҫ��������ݣ����û����FormBean�е����ݷ���session�У������session��ȡ����ǰ������
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
	 * ����ͳ��
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
		// �ж�formbean���Ƿ��в�ѯ��Ҫ��������ݣ����û����FormBean�е����ݷ���session�У������session��ȡ����ǰ������
		if (null == request.getParameter("isQuery")) {
			drillQueryCondition = (DrillQueryCondition) request.getSession()
					.getAttribute("queryCondition");
		} else {
			request.getSession().setAttribute("queryCondition",
					drillQueryCondition);
		}
		// �ж϶����Ƿ�Ϊ�գ����Ϊ�գ��򴴽�����
		if (drillQueryCondition == null) {
			drillQueryCondition = new DrillQueryCondition();
		}
		List list = getQueryStatBo().drillStat(drillQueryCondition, userInfo);
		request.getSession().setAttribute("result", list);
		return mapping.findForward("statList");
	}

	/**
	 * ��װ��ѯ����
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
	 * ���������б�
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
	 * �鿴����
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
