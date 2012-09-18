package com.cabletech.planstat.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.services.WholeNetStatBO;
import com.cabletech.planstat.util.PlanStatCommon;

/**
 * 功能：市移动月统计的控制类
 */
public class WholeNetStatAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	private PlanStatCommon common = new PlanStatCommon();

	/**
	 * 市移动公司月统计查询页面
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward queryMonthStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断是否有市移动公司月统计的权限
		// 得到查询页面所需载入的区域列表
		List regionList = common.getRegionList();
		request.getSession().setAttribute("reginfo", regionList);
		return mapping.findForward("query_whole_net_month_stat_form");
	}

	/**
	 * 市移动公司月统计查询页面
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward queryYearStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断是否有市移动公司月统计的权限
		// 得到查询页面所需载入的区域列表
		List regionList = common.getRegionList();
		request.getSession().setAttribute("reginfo", regionList);
		return mapping.findForward("query_whole_net_year_stat_form");
	}

	/**
	 * 市移动公司月统计查询页面
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward showMonthStatInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if ("12".equals(userInfo.getType())) {
			bean.setRegionID(userInfo.getRegionID());
		}
		request.getSession().setAttribute("CMMonthlyStatConBean", bean);
		return mapping.findForward("show_whole_net_month_stat_result");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMonthGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if ("12".equals(userInfo.getType())) {
			bean.setRegionID(userInfo.getRegionID());
		}
		WholeNetStatBO bo = new WholeNetStatBO();
		DynaBean resultBean = bo.showMonthGeneralInfo(bean, userInfo);
		request.setAttribute("month_general_info", resultBean);
		return mapping.findForward("show_whole_net_month_general_info");
	}

	public ActionForward showMonthExecuteResultInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if ("12".equals(userInfo.getType())) {
			bean.setRegionID(userInfo.getRegionID());
		}
		WholeNetStatBO bo = new WholeNetStatBO();
		List list = bo.showMonthExecuteResultInfo(bean, userInfo);
		request.setAttribute("month_execute_result_list", list);
		return mapping.findForward("show_whole_net_month_execute_result_info");
	}

	/**
	 * 市移动公司月统计查询页面
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            请求
	 * @param response
	 *            回复
	 * @return ActionForward
	 */
	public ActionForward showYearStatInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		if ("12".equals(userInfo.getType())) {
			bean.setRegionID(userInfo.getRegionID());
		}
		request.getSession().setAttribute("CMMonthlyStatConBean", bean);
		return mapping.findForward("show_whole_net_year_stat_result");
	}

	public ActionForward showWholeYearGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WholeNetStatBO bo = new WholeNetStatBO();
		DynaBean resultBean=bo.showWholeYearGeneralInfo(bean,userInfo);
		request.setAttribute("general_info", resultBean);
		return mapping.findForward("show_whole_net_year_general_info");
	}

	public ActionForward showMonthLayingMethodExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WholeNetStatBO bo = new WholeNetStatBO();
		List list=bo.showMonthLayingMethodExecuteResultInfo(bean,userInfo);
		request.setAttribute("month_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_whole_net_month_laying_method_exeucte_result");
	}

	public ActionForward showYearLayingMethodExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WholeNetStatBO bo = new WholeNetStatBO();
		List list=bo.showYearLayingMethodExecuteResultInfo(bean,userInfo);
		request.setAttribute("year_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_whole_net_year_laying_method_exeucte_result");
	}

	public ActionForward showWholeYearLineTypeExecuteResult(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		WholeNetStatBO bo = new WholeNetStatBO();
		List list=bo.showWholeYearLineTypeExecuteResult(bean,userInfo);
		request.setAttribute("line_type_execute_result_list", list);
		return mapping.findForward("show_whole_net_year_line_type_execute_result_info");
	}

	public ActionForward showLineLevelMonthPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		WholeNetStatBO bo = new WholeNetStatBO();
		JFreeChart chart = bo.createLineLevelMonthPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelYearPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		WholeNetStatBO bo = new WholeNetStatBO();
		JFreeChart chart = bo.createLineLevelYearPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelDifferentMonthPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		WholeNetStatBO bo = new WholeNetStatBO();
		JFreeChart chart = bo
				.createLineLevelDifferentMonthPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelFiveYearPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String endMonth = request.getParameter("endMonth");
		bean.setEndMonth(endMonth);
		WholeNetStatBO bo = new WholeNetStatBO();
		JFreeChart chart = bo.createLineLevelFiveYearPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelInputTimeAreaPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		bean.setBeginTime(beginTime);
		bean.setEndTime(endTime);
		WholeNetStatBO bo = new WholeNetStatBO();
		JFreeChart chart = bo.createLineLevelInputTimeAreaPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

}
