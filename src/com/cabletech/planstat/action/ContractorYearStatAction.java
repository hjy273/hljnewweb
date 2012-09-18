package com.cabletech.planstat.action;

import com.cabletech.commons.base.BaseDispatchAction;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.services.YearMonthPlanBO;
import com.cabletech.baseinfo.domainobjects.UserInfo;

import java.util.List;
import java.util.Vector;

import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.cabletech.planstat.services.ContractorYearStatBO;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.io.OutputStream;

public class ContractorYearStatAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(ContractorYearStatAction.class
			.getName());

	public ContractorYearStatAction() {
	}

	// 市代维公司月统计查询
	public ActionForward queryYearStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ContractorYearStatBO bo = new ContractorYearStatBO();
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		List contractorList = bo.getContractorInfoList(userinfo);
		if (userinfo.getType().equals("11")) {
			List regionList = bo.getRegionInfoList();
			request.getSession().setAttribute("reginfo", regionList);
		}

		// logger.info( "size: " + contractorList.size() );
		request.getSession().setAttribute("coninfo", contractorList);
		return mapping.findForward("query_contractor_year_stat_form");
	}

	// 市代维公司月统计查询
	public ActionForward showYearStatInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) form;
		String conname = request.getParameter("conname");
		bean.setConName(conname);
		request.getSession().setAttribute("queryCon", bean);
		return mapping.findForward("show_contractor_year_stat_result");
	}

	public ActionForward showYearGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = (HttpSession) request.getSession();
		session.removeAttribute("tasklist");
		session.removeAttribute("YearMonthPlanBean");
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		ContractorYearStatBO bo = new ContractorYearStatBO();
		YearMonthPlanBean mbean;
		try {
			mbean = bo.getYearPlan(userinfo, bean);
		} catch (Exception e1) {
			logger.info("年计划信息为找到 " + e1.getMessage());
			logger.error(e1);
			return forwardInfoPage(mapping, request, "21506S1");
		}
		YearMonthPlanBO ymbo = new YearMonthPlanBO();
		try {
			Vector taskListVct = ymbo.getTasklistByPlanID(mbean.getId(),
					"YMPLAN");
			session.setAttribute("tasklist", taskListVct);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		request.setAttribute("looktype", "stat");
		session.setAttribute("YearMonthPlanBean", mbean);
		request.setAttribute("fID", "2");
		return mapping.findForward("show_contractor_year_general_info");
	}

	// 显示代维公司月统计查询结果
	public ActionForward showYearExecuteResultInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		ContractorYearStatBO bo = new ContractorYearStatBO();
		List contractorYearExecuteResult = bo
				.getContractorYearExecuteResult(bean);
		if (contractorYearExecuteResult != null
				&& contractorYearExecuteResult.size() == 0) {
			return forwardInfoPage(mapping, request, "21502S1");
		}
		request.setAttribute("monthlyconstatDynaBean",
				contractorYearExecuteResult.get(0));
		return mapping.findForward("show_contractor_year_execute_result_info");
	}

	public ActionForward showYearLineLevelExecuteResultInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		PatrolStatConditionBean bean = (PatrolStatConditionBean) session
				.getAttribute("queryCon");
		UserInfo userinfo = (UserInfo) session.getAttribute("LOGIN_USER");
		ContractorYearStatBO bo = new ContractorYearStatBO();
		List contractorYearExecuteResult = bo
				.getContractorYearLineLevelExecuteResult(bean);
		if (contractorYearExecuteResult != null
				&& contractorYearExecuteResult.size() == 0) {
			return forwardInfoPage(mapping, request, "21502S1");
		}
		request.setAttribute("contractor_year_line_level_execute_result_list",
				contractorYearExecuteResult);
		return mapping.findForward("show_contractor_year_line_level_execute_result_info");
	}
	
	public ActionForward showCurrentYearPatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ContractorYearStatBO bo = new ContractorYearStatBO();
		JFreeChart chart = bo.createContractorYearPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showFiveYearPatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String endMonth=request.getParameter("endMonth");
		bean.setEndMonth(endMonth);
		ContractorYearStatBO bo = new ContractorYearStatBO();
		JFreeChart chart = bo.createContractorFiveYearPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showYearLayingMethodExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PatrolStatConditionBean bean = (PatrolStatConditionBean) request
				.getSession().getAttribute("queryCon");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		ContractorYearStatBO bo = new ContractorYearStatBO();
		List list=bo.showYearLayingMethodExecuteResultInfo(bean,userInfo);
		request.setAttribute("year_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_contractor_year_laying_method_exeucte_result");
	}
}
