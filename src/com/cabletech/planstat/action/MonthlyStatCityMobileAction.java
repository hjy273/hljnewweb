package com.cabletech.planstat.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.services.MonthlyStatBO;
import com.cabletech.planstat.services.MonthlyStatCityMobileBO;
import com.cabletech.planstat.util.PlanStatCommon;
import com.cabletech.power.CheckPower;

/**
 * 功能：市移动月统计的控制类
 */
public class MonthlyStatCityMobileAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;

	private PlanStatCommon common = new PlanStatCommon();

	private MonthlyStatCityMobileBO bo = new MonthlyStatCityMobileBO();

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
	public ActionForward queryMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 判断是否有市移动公司月统计的权限
		try {
			// 得到查询页面所需载入的区域列表
			List regionList = common.getRegionList();
			request.getSession().setAttribute("reginfo", regionList);
			return mapping.findForward("queryMonthlyStatCityMobile");
		} catch (Exception e) {
			logger.error("查询区域范围信息时出错:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
	}

	/**
	 * 显示市移动公司月统计结果中的计划信息
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
	public ActionForward showPlanInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionName = request.getParameter("regionname");
		String regionNameInSession = (String) request.getSession()
				.getAttribute("LOGIN_USER_REGION_NAME");
		// 组织最终bean并存入session，以后很多地方会用到这个bean
		bean.getOrganizedBean(userInfo, regionName, regionNameInSession);
		List listGeneral = bo.getGeneralInfo(bean); // 得到月统计总体信息返回结果
		if (listGeneral == null || listGeneral.size() == 0) { // 判断有没有该月统计结果
			return forwardInfoPage(mapping, request, "120207");
		}
		// 得到线路级别类型字典值map（各省可能有区别）
		Map lineTypeDictMap = bo.getLineTypeDictMap();
		List list = bo.getPlanInfo(bean, lineTypeDictMap); // 得到计划信息
		request.getSession().setAttribute("planforcmmonthlystatinfo", list);
		request.getSession().setAttribute("lineTypeDictMap", lineTypeDictMap);
		request.getSession().setAttribute("listCMMOnthlyStatGeneral",
				listGeneral);
		request.getSession().setAttribute("CMMonthlyStatConBean", bean);
		return mapping.findForward("showMonthlyStatCityMobile");
	}

	/**
	 * 显示市移动公司月统计结果中的总体信息
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
	public ActionForward showGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute(
				"listCMMOnthlyStatGeneral");// 从seesion取出结果
		BasicDynaBean cmmonthlystatgeneralinfo = (BasicDynaBean) list.get(0); // 只有唯一一行记录
		request.getSession().setAttribute("cmmonthlystatgeneralinfo",
				cmmonthlystatgeneralinfo);
		return mapping.findForward("showGeneralInfoMonthlyStatCM");
	}

	/**
	 * 显示市移动公司月统计结果中的各代维公司月执行情况
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
	public ActionForward showContractorExeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getContractorExeInfo(bean); // 得到代维执行信息
		request.getSession().setAttribute("cmmonthlystatconexe", list);
		return mapping.findForward("showConExeMonthlyStatCM");
	}

	/**
	 * 显示市移动公司月统计结果中的所有计划执行情况
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
	public ActionForward showPlanExeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getPlanExeInfo(bean); // 得到本市所有计划执行信息
		request.getSession().setAttribute("cmmonthlystatplanexe", list);
		return mapping.findForward("showPlanExeMonthlyStatCM");
	}

	/**
	 * 得到市移动公司月统计结果中的三类线段对比饼图数据
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
	public ActionForward showCompDataAllTypeSubline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		Map map = bo.getAllTypeSublinesInfo(bean); // 得到本市所有线段数据(含合格，未合格及未计划)
		request.getSession().setAttribute("cmmonthlystat3sublinesmap", map);
		return mapping.findForward("showSublineCompMonthlyStatCM");
	}

	/**
	 * 显示市移动公司月统计结果中的对比分析信息（纵向）
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
	public ActionForward showCompAnalysisInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getVCompAnalysisInfo(bean); // 得到本市所有对比分析信息（纵向）
		if (list == null || list.size() == 0) {
			log.info("list is null");
			return forwardInfoPage(mapping, request, "statcommon");
		}
		request.getSession().setAttribute("cmmonthlystatVcomp", list);
		return mapping.findForward("showVCompMonthlyStatCM");
	}

	public ActionForward showSublinePatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		String type = request.getParameter("type");
		JFreeChart chart = bo.createSublinePatrolRateChart(bean, type);
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showLineLevelSublinePatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		String type = request.getParameter("type");
		JFreeChart chart = bo.createLineLevelSublinePatrolRateChart(bean, type);
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showContractorPatrolRateChart(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		JFreeChart chart = bo.createContractorPatrolRateChart(bean);
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showMonthLayingMethodExecuteResultInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.showMonthLayingMethodExecuteResultInfo(bean); // 得到本市所有计划执行信息
		request.setAttribute("month_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_city_mobile_month_laying_method_exeucte_result");
	}

}
