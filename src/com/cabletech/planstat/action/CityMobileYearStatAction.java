package com.cabletech.planstat.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseDispatchAction;
import com.cabletech.planstat.beans.MonthlyStatCityMobileConBean;
import com.cabletech.planstat.services.CityMobileYearStatBO;
import com.cabletech.planstat.util.PlanStatCommon;

/**
 * ���ܣ����ƶ���ͳ�ƵĿ�����
 */
public class CityMobileYearStatAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

	private PlanStatCommon common = new PlanStatCommon();

	private CityMobileYearStatBO bo = new CityMobileYearStatBO();

	/**
	 * ���ƶ���˾��ͳ�Ʋ�ѯҳ��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward queryYearStatForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// �ж��Ƿ������ƶ���˾��ͳ�Ƶ�Ȩ��
		// �õ���ѯҳ����������������б�
		List regionList = common.getRegionList();
		request.getSession().setAttribute("reginfo", regionList);
		return mapping.findForward("query_city_mobile_year_stat_form");
	}

	/**
	 * ���ƶ���˾��ͳ�Ʋ�ѯҳ��
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
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
		return mapping.findForward("show_city_mobile_year_stat_result");
	}

	/**
	 * ��ʾ���ƶ���˾��ͳ�ƽ���е�������Ϣ
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward showYearGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		Map lineTypeDictMap = bo.getLineTypeDictMap();
		List list = bo.getContractorPlanInfoList(bean, lineTypeDictMap);
		request.getSession().setAttribute("CITY_MOBILE_YEAR_STAT_GENERAL_INFO",
				list);
		request.getSession().setAttribute("lineTypeDictMap", lineTypeDictMap);
		return mapping.findForward("show_city_mobile_year_general_info");
	}

	/**
	 * ��ʾ���ƶ���˾��ͳ�ƽ���еļƻ���Ϣ
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            ����
	 * @param response
	 *            �ظ�
	 * @return ActionForward
	 */
	public ActionForward showYearExeuteResultInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getContractorExecuteResultInfo(bean);
		request.getSession().setAttribute(
				"CITY_MOBILE_YEAR_STAT_CONTRACTOR_PATROL_LIST", list);
		return mapping.findForward("show_city_mobile_year_execute_result_info");
	}

	public ActionForward showYearLineLevelExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getYearLineLevelExecuteResultInfo(bean);
		request.setAttribute("city_mobile_year_line_level_stat_list", list);
		return mapping
				.findForward("show_city_mobile_year_line_level_execute_result_info");
	}

	public ActionForward showYearContractorPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		JFreeChart chart = bo.createContractorPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showYearContractorLineLevelPatrolRateChart(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		OutputStream out;
		out = response.getOutputStream();
		int width = Integer.parseInt(request.getParameter("width"));
		int height = Integer.parseInt(request.getParameter("height"));
		JFreeChart chart = bo.createContractorLineLevelPatrolRateChart(bean);
		ChartUtilities.writeChartAsJPEG(out, 1.0f, chart, width, height, null);
		return null;
	}

	public ActionForward showYearLayingMethodExecuteResultInfo(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.showYearLayingMethodExecuteResultInfo(bean);
		request.setAttribute("year_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_city_mobile_year_laying_method_exeucte_result");
	}

}
