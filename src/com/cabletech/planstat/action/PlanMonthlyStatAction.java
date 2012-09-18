package com.cabletech.planstat.action;

import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.commons.exceltemplates.Template;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cabletech.linepatrol.dispatchtask.module.DispatchTaskConstant;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.services.YearMonthPlanBO;
import com.cabletech.planstat.services.PlanExeResultBO;
import com.cabletech.planstat.services.PlanExeResultBOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.PatrolManBO;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.cabletech.planstat.services.MonthlyStatBO;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import org.apache.commons.beanutils.BasicDynaBean;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.util.Map;
import java.io.OutputStream;
import java.io.PrintWriter;

public class PlanMonthlyStatAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(PlanMonthlyStatAction.class
			.getName());

	public PlanMonthlyStatAction() {
	}

	// 巡检人员月统计查询
	public ActionForward queryPatrolmanMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		// request.getSession().removeAttribute("uinfo");
		List patrolmanList = monthlyStatBO.getPatrolmanInfoList(userinfo);
		logger.info("size: " + patrolmanList.size());
		request.getSession().setAttribute("uinfo", patrolmanList);
		return mapping.findForward("queryPatrolMonthlyStat");
	}

	// 显示巡检人员月统计查询结果
	public ActionForward showPatrolMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String patrolname = request.getParameter("patrolname");
		List planMonthlyStatAll = monthlyStatBO.getPlanMonthlyStatOverAll(
				userinfo, bean);
		List planMonthlyStatDetail = monthlyStatBO.getPlanMonthlyStatDetail(
				userinfo, bean);
		if (planMonthlyStatAll.size() == 0) {
			return forwardInfoPage(mapping, request, "21501");
		}
		for (int i = 0; i < planMonthlyStatAll.size(); i++) {
			BasicDynaBean MonthlyStatAllDynaBean = (BasicDynaBean) planMonthlyStatAll
					.get(i);
			request.setAttribute("monthlystatallDynaBean",
					MonthlyStatAllDynaBean);
		}

		// request.getSession().setAttribute( "planexecuteinfoall",
		// planMonthlyStatAll);
		request.getSession().setAttribute("patrolname", patrolname);
		request.getSession().setAttribute("endYearStat", bean.getEndYear()); // new
		request.getSession().setAttribute("endMonthStat", bean.getEndMonth()); // new
		request.getSession().setAttribute("patrolID", bean.getPatrolID()); // new
		logger.info("patrolname is " + patrolname + ",endyear is "
				+ bean.getEndYear() + ",endmonth is " + bean.getEndMonth()
				+ ",patrolID is " + bean.getPatrolID());
		request.getSession().setAttribute("monthlystatdetail",
				planMonthlyStatDetail);
		return mapping.findForward("showPatrolMonthlyStat");
	}

	// 市代维公司月统计查询
	public ActionForward queryConMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List contractorList = monthlyStatBO.getContractorInfoList(userinfo);
		if (userinfo.getType().equals("11")) {
			List regionList = monthlyStatBO.getRegionInfoList();
			request.getSession().setAttribute("reginfo", regionList);
		}
		// logger.info( "size: " + contractorList.size() );
		request.getSession().setAttribute("coninfo", contractorList);
		return mapping.findForward("queryConMonthlyStat");
	}

	// 。。。。。。。。。。。。。代为公司月统计。。。。。。。。。。。。。。。

	// 显示代维公司月统计查询结果
	public ActionForward showConMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute("queryCon");
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String conname = request.getParameter("conname");
		bean.setConName(conname);
		List planConMonthlyStat = monthlyStatBO.getPlanConMonthlyStat(userinfo,
				bean);
		if (planConMonthlyStat.size() == 0) {
			return forwardInfoPage(mapping, request, "21502");
		}
		for (int i = 0; i < planConMonthlyStat.size(); i++) {
			BasicDynaBean MonthlyStatAllDynaBean = (BasicDynaBean) planConMonthlyStat
					.get(i);
			request.setAttribute("monthlyconstatDynaBean",
					MonthlyStatAllDynaBean);
			session.setAttribute("monthlyconstatDynaBeanSession",
                    MonthlyStatAllDynaBean);
		}

		request.setAttribute("conname", conname);
		session.setAttribute("connameSession", conname);
		session.setAttribute("queryCon", bean);
		return mapping.findForward("showConMonthlyStat");
	}

	// 计划执行结果
	public ActionForward showExceuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		List planExceute = monthlyStatBO.getExceuteResult(userinfo, bean);
		logger.info("bean " + bean.getConName() + bean.getConID());
		session.setAttribute("planexceute", planExceute);
		logger.info(" this is showExceuteResult");
		return mapping.findForward("showExceuteResult");
	}

	// 计划执行结果
	public ActionForward showPlanSublineExecuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String planId = request.getParameter("plan_id");
		PlanExeResultBO planExeResultBO = new PlanExeResultBOImpl();
		String planName = planExeResultBO.getPlanStat(planId).getPlanname();
		List list = planExeResultBO.getSubLineInfo(planId);
		request.setAttribute("sublinepatrollist", list);
		request.setAttribute("plan_name", planName);
		logger.info("bean " + bean.getConName() + bean.getConID());
		logger.info(" this is showExceuteResult");
		return mapping.findForward("showPlanSublineExecuteResult");
	}

	// 巡检线段结果
	public ActionForward showPlanExecuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String patrolId = request.getParameter("patrol_id");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		PatrolManBO patrolManBO = new PatrolManBO();
		bean.setPatrolID(patrolId);
		bean.setPatrolName(patrolManBO.loadPatrolMan(patrolId).getPatrolName());
		List list = monthlyStatBO.getExceuteResult(userinfo, bean);
		request.setAttribute("planexceute", list);
		request.setAttribute("patrol_name", bean.getPatrolName());
		logger.info("bean " + bean.getConName() + bean.getConID());
		logger.info(" this is showExceuteResult");
		return mapping.findForward("showPlanExecuteResult");
	}

	// 巡检线段结果
	public ActionForward showSubLinePatrol(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		String type = request.getParameter("type");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();

		if ("nonpass".equals(type)) {
			List nonpasssubline = monthlyStatBO.getNonPassSubline(userinfo,
					bean);
			session.setAttribute("nonpasssubline", nonpasssubline);
			return mapping.findForward("showNonPassSubline");
		} else if ("nonplan".equals(type)) {
			List nonplansubline = monthlyStatBO.getNonPlanSubline(userinfo,
					bean);
			session.setAttribute("nonplansubline", nonplansubline);
			return mapping.findForward("showNonPlanSubline");
		} else {
			List sublinepatrol = monthlyStatBO.getSubLinePatrol(userinfo, bean);
			session.setAttribute("sublinepatrollist", sublinepatrol);
			return mapping.findForward("showSubLinePatrol");
		}

	}

	// 计划信息
	public ActionForward showMonthPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute("tasklist");
		session.removeAttribute("YearMonthPlanBean");
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		YearMonthPlanBean mbean;
		try {
			mbean = monthlyStatBO.getMonthPlan(userinfo, bean);
		} catch (Exception e1) {
			logger.info("月计划信息为找到 " + e1.getMessage());
			e1.printStackTrace();
			return forwardInfoPage(mapping, request, "21506");
		}
		YearMonthPlanBO ymbo = new YearMonthPlanBO();
		try {
			Vector taskListVct = ymbo.getTasklistByPlanID(mbean.getId(),
					"YMPLAN");
			session.setAttribute("tasklist", taskListVct);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("looktype", "stat");
		session.setAttribute("YearMonthPlanBean", mbean);
		request.setAttribute("fID", "2");
		request.setAttribute("ctime", mbean.getCreatedate().substring(0,
				mbean.getCreatedate().indexOf(" ")));
		return mapping.findForward("showMonthPlan");
	}

	// 巡检组信息
	public ActionForward showPatrolMan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		List patrolman = monthlyStatBO.getPatrolMan(userinfo, bean);
		session.setAttribute("patrolmanlist", patrolman);
		return mapping.findForward("showPatrolMan");
	}

	public ActionForward showChartAnalyse(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		List monthcontrast = monthlyStatBO.getMonthPatrolResult(userinfo, bean);
		List concontrast = monthlyStatBO.getConPatrolResult(userinfo, bean);
		session.setAttribute("monthcontrast", monthcontrast);
		session.setAttribute("concontrast", concontrast);
		return mapping.findForward("showChartAnalyse");
	}

	// 。。。。。。。。。。。。。。。。。代为公司月统计end。。。。。。。。。

	// 市移动公司月统计查询
	public ActionForward queryMobileMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List MobileList = monthlyStatBO.getMobileInfoList(userinfo);
		logger.info("size: " + MobileList.size());
		request.getSession().setAttribute("mobileinfo", MobileList);
		return mapping.findForward("queryMobileMonthlyStat");
	}

	// 显示市移动公司月统计查询结果
	public ActionForward showMobileMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String mobilename = request.getParameter("mobilename");
		List MobileMonthlyStatAll = monthlyStatBO.getMobileMonthlyStatOverAll(
				userinfo, bean);
		List MobileMonthlyStatDetail = monthlyStatBO
				.getMobileMonthlyStatDetail(userinfo, bean);
		if (MobileMonthlyStatAll.size() == 0) {
			return forwardInfoPage(mapping, request, "21503");
		}
		for (int i = 0; i < MobileMonthlyStatAll.size(); i++) {
			BasicDynaBean MonthlyStatAllDynaBean = (BasicDynaBean) MobileMonthlyStatAll
					.get(i);
			request.setAttribute("mobilemonthlyallDynaBean",
					MonthlyStatAllDynaBean);
		}
		request.setAttribute("mobilename", mobilename);
		request.getSession().setAttribute("mobilemonthlydetail",
				MobileMonthlyStatDetail);
		return mapping.findForward("showMobileMonthlyStat");
	}

	// 线段月统计查询
	public ActionForward querySublineMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List LineList = monthlyStatBO.getLineInfoList(userinfo);
		// List SublineList = monthlyStatBO.getSublineInfoList( userinfo );
		if (userinfo.getType().equals("11")) {
			List regionList = monthlyStatBO.getRegionInfoList();
			request.getSession().setAttribute("reginfo", regionList);
		}
		// String[][] SublineArray = monthlyStatBO.getSublineInfoList(userinfo);
		// request.getSession().setAttribute( "sublineinfo", SublineList );
		request.getSession().setAttribute("lineinfo", LineList);
		return mapping.findForward("querySublineMonthlyStat");
	}

	// 显示线段月统计查询结果
	public ActionForward showSublineMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List SublineMonthlyStat = monthlyStatBO.getSublineMonthlyStat(userinfo,
				bean);
		String linename = request.getParameter("linename");
		String sublinename = request.getParameter("sublinename");
		if (SublineMonthlyStat.size() == 0) {
			return forwardInfoPage(mapping, request, "21504");
		}
		request.setAttribute("linename", linename);
		request.setAttribute("sublinename", sublinename);
		for (int i = 0; i < SublineMonthlyStat.size(); i++) {
			BasicDynaBean MonthlyStatAllDynaBean = (BasicDynaBean) SublineMonthlyStat
					.get(i);
			request.setAttribute("sublinemonthlyallDynaBean",
					MonthlyStatAllDynaBean);
		}
		return mapping.findForward("showSublineMonthlyStat");
	}

	// 线路月统计查询
	public ActionForward queryLineMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List LineList = monthlyStatBO.getLineInfoList(userinfo);
		if (userinfo.getType().equals("11")) {
			List regionList = monthlyStatBO.getRegionInfoList();
			request.getSession().setAttribute("reginfo", regionList);
		}
		request.getSession().setAttribute("lineinfo", LineList);
		return mapping.findForward("queryLineMonthlyStat");
	}

	// 显示线路月统计查询结果
	public ActionForward showLineMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List LineMonthlyStat = monthlyStatBO.getLineMonthlyStat(userinfo, bean);
		String linename = request.getParameter("linename");
		if (LineMonthlyStat.size() == 0) {
			return forwardInfoPage(mapping, request, "21505");
		}
		request.setAttribute("linename", linename);
		for (int i = 0; i < LineMonthlyStat.size(); i++) {
			BasicDynaBean MonthlyStatAllDynaBean = (BasicDynaBean) LineMonthlyStat
					.get(i);
			request.setAttribute("linemonthlyallDynaBean",
					MonthlyStatAllDynaBean);
		}
		return mapping.findForward("showLineMonthlyStat");
	}

	public ActionForward loadSubline(ActionMapping mapping, ActionForm inForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Get a list of characters associated with the select TV show
		String lineID = (String) request.getParameter("lineID");
		List SublineList = null;
		String html;
		logger.info("lineid:" + lineID);
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if (lineID == null) {
			lineID = "";
			html = "<select name=\"sublineID\" class=\"inputtext\" style=\"width:180px\" id=\"sId\">";
			html += "<option value=\"\">" + "请选择..." + "</option>";
			html += "</select>";
			logger.info("html:" + html);
		} else {
			SublineList = monthlyStatBO.getSublineByLineAjax(userinfo, lineID);
			request.getSession().setAttribute("sublineinfo", SublineList);

			html = "<select name=\"sublineID\" class=\"inputtext\" style=\"width:180px\" id=\"sId\">";
			Iterator itr = SublineList.iterator();
			// Iterator keyItr;
			// Object key,val;
			Object sublineID, sublineName;
			Map row;
			while (itr.hasNext()) {
				row = (Map) itr.next();
				// keyItr=row.keySet().iterator();
				sublineID = (String) row.get("SUBLINEID");
				sublineName = (String) row.get("SUBLINENAME");
				html += "<option value=\"" + sublineID + "\">" + sublineName
						+ "</option>";

				/*
				 * while(keyItr.hasNext()){ key=(String)keyItr.next();
				 * val=row.get(key);
				 * logger.info("key="+key.toString()+" val="+val.toString()); }
				 */
			}
			/*
			 * for (Iterator it = SublineList.iterator(); it.hasNext();) {
			 * 
			 * //for ( int i =0;i <SublineList.size();i++){ //
			 * logger.info("sublineid:"
			 * +((HashMap)(SublineList.get(0))).get("sublineid")); //}
			 * 
			 * String sublineID = (String)((Map)it.next()).get("SUBLINEID");
			 * String sublineName = (String)((Map)it.next()).get("SUBLINENAME");
			 * //logger.info("sublineid:" + sublineID +
			 * "-----------sublinename:" + sublineName); html +=
			 * "<option value=\"" + sublineID + "\">" + sublineName +
			 * "</option>"; }
			 */
			html += "</select>";
			logger.info("html:" + html);
		}

		// Write the HTML to response
		response.setContentType("text/html; charset=GBK");
		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();

		return null; // Not forwarding to anywhere, response is fully-cooked

	} // End execute()

	// 显示选定月份工作日列表
	public ActionForward showWorkDaysInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String patrolid = (String) request.getSession()
				.getAttribute("patrolID");
		String endyear = (String) request.getSession().getAttribute(
				"endYearStat");
		String endmonth = (String) request.getSession().getAttribute(
				"endMonthStat");
		logger.info("the value is:" + endyear + endmonth + patrolid);
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		List mWorkDaysInfoList = monthlyStatBO.getWorkdaysInfoList(patrolid,
				endyear, endmonth);
		request.getSession().setAttribute("mWorkDaysInfoList",
				mWorkDaysInfoList);
		return mapping.findForward("showMonthlyWorkDaysInfo");
	}

	public ActionForward showPerCardPerDay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String strSim = request.getParameter("simid");
		String strHandleState = request.getParameter("handlestate");
		String operatedate = request.getParameter("operatedate");
		// String operatedate =
		// (String)request.getSession().getAttribute("stroperatedate");
		// Object displaytagline =
		// (Object)request.getSession().getAttribute("displaytagline");
		// logger.info("the displaytag line is :" + displaytagline.toString());
		String endyear = (String) request.getSession().getAttribute(
				"endYearStat");
		String endmonth = (String) request.getSession().getAttribute(
				"endMonthStat");
		// logger.info("before org:" + strSim + strHandleState + operatedate);
		operatedate = endyear + "-" + endmonth + "-"
				+ operatedate.substring(0, operatedate.length() - 1);
		// logger.info("after org:" + operatedate);
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();

		List perCardPerDayInfoList = monthlyStatBO.getPerCardPerDayInfoList(
				strSim, strHandleState, operatedate);
		request.getSession().setAttribute("perCardPerDayInfoList",
				perCardPerDayInfoList);
		return mapping.findForward("showPerCardPerDay");

	}

	// 显示所涉及的巡检线段的信息
	public ActionForward showSublineinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String patrolid = (String) request.getSession()
				.getAttribute("patrolID");
		String endyear = (String) request.getSession().getAttribute(
				"endYearStat");
		String endmonth = (String) request.getSession().getAttribute(
				"endMonthStat");
		logger.info("the value is:" + endyear + endmonth + patrolid);
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		List sublineInfoList = monthlyStatBO.getSubLineInfo(patrolid, endyear,
				endmonth);
		List sublineInfoListFailure = monthlyStatBO.getSubLineInfoFailure(
				patrolid, endyear, endmonth);
		List sublineInfoListNoPlan = monthlyStatBO.getSubLineInfoNoPlan(
				patrolid, endyear, endmonth);
		request.getSession().setAttribute("sublineInfoList", sublineInfoList);
		request.getSession().setAttribute("sublineInfoListFailure",
				sublineInfoListFailure);
		request.getSession().setAttribute("sublineInfoListNoPlan",
				sublineInfoListNoPlan);
		return mapping.findForward("showPmMonthlySublineInfo");
	}

	// 显示巡检员月统计的纵向与横向对比分析
	public ActionForward showCompAnalysisinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		String endmonth = (String) request.getSession().getAttribute(
				"endMonthStat");
		String endyear = (String) request.getSession().getAttribute(
				"endYearStat");
		String patrolid = (String) request.getSession()
				.getAttribute("patrolID");
		List monthlyStatPmVComp = monthlyStatBO.getMonthlyStatPmVComp(patrolid,
				endyear, endmonth);
		List monthlyStatPmHComp = monthlyStatBO.getMonthlyStatPmHComp(userinfo
				.getDeptID(), endyear, endmonth);
		request.getSession().setAttribute("monthlyStatPmVComp",
				monthlyStatPmVComp);
		request.getSession().setAttribute("monthlyStatPmHComp",
				monthlyStatPmHComp);
		return mapping.findForward("queryMonthlyStatPmComp");
	}

	public ActionForward exportPlanExecuteResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OutputStream out;
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		initResponse(response, "计划执行结果信息一览表.xls");
		out = response.getOutputStream();
		List list = (List) request.getSession().getAttribute("planexceute");
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		logger.info("得到list");
		Template template = monthlyStatBO.exportPlanExecuteResult(list, bean);
		template.write(out);
		return null;
	}

	public ActionForward showPatrolManPatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		OutputStream out;
		out = response.getOutputStream();
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		JFreeChart chart = monthlyStatBO.createPatrolManPatrolRateChart(bean);
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelPatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		OutputStream out;
		out = response.getOutputStream();
		MonthlyStatBO monthlyStatBO = new MonthlyStatBO();
		JFreeChart chart = monthlyStatBO.createLineLevelPatrolRateChart(bean);
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showMonthLayingMethodExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		MonthlyStatBO bo = new MonthlyStatBO();
		List list = bo.showMonthLayingMethodExecuteResultInfo(bean, userInfo);
		request.setAttribute("month_laying_method_exeucte_result_list", list);
		return mapping
				.findForward("show_contractor_month_laying_method_exeucte_result");
	}

	public void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		response.reset();
		response.setContentType(DispatchTaskConstant.CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
	}

}
