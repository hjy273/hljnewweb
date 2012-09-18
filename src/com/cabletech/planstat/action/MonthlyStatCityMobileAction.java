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
 * ���ܣ����ƶ���ͳ�ƵĿ�����
 */
public class MonthlyStatCityMobileAction extends BaseDispatchAction {
	private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

	private PlanStatCommon common = new PlanStatCommon();

	private MonthlyStatCityMobileBO bo = new MonthlyStatCityMobileBO();

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
	public ActionForward queryMonthlyStat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// �ж��Ƿ������ƶ���˾��ͳ�Ƶ�Ȩ��
		try {
			// �õ���ѯҳ����������������б�
			List regionList = common.getRegionList();
			request.getSession().setAttribute("reginfo", regionList);
			return mapping.findForward("queryMonthlyStatCityMobile");
		} catch (Exception e) {
			logger.error("��ѯ����Χ��Ϣʱ����:" + e.getMessage());
			return forwardErrorPage(mapping, request, "error");
		}
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
	public ActionForward showPlanInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) form;
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
				"LOGIN_USER");
		String regionName = request.getParameter("regionname");
		String regionNameInSession = (String) request.getSession()
				.getAttribute("LOGIN_USER_REGION_NAME");
		// ��֯����bean������session���Ժ�ܶ�ط����õ����bean
		bean.getOrganizedBean(userInfo, regionName, regionNameInSession);
		List listGeneral = bo.getGeneralInfo(bean); // �õ���ͳ��������Ϣ���ؽ��
		if (listGeneral == null || listGeneral.size() == 0) { // �ж���û�и���ͳ�ƽ��
			return forwardInfoPage(mapping, request, "120207");
		}
		// �õ���·���������ֵ�ֵmap����ʡ����������
		Map lineTypeDictMap = bo.getLineTypeDictMap();
		List list = bo.getPlanInfo(bean, lineTypeDictMap); // �õ��ƻ���Ϣ
		request.getSession().setAttribute("planforcmmonthlystatinfo", list);
		request.getSession().setAttribute("lineTypeDictMap", lineTypeDictMap);
		request.getSession().setAttribute("listCMMOnthlyStatGeneral",
				listGeneral);
		request.getSession().setAttribute("CMMonthlyStatConBean", bean);
		return mapping.findForward("showMonthlyStatCityMobile");
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
	public ActionForward showGeneralInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		List list = (List) request.getSession().getAttribute(
				"listCMMOnthlyStatGeneral");// ��seesionȡ�����
		BasicDynaBean cmmonthlystatgeneralinfo = (BasicDynaBean) list.get(0); // ֻ��Ψһһ�м�¼
		request.getSession().setAttribute("cmmonthlystatgeneralinfo",
				cmmonthlystatgeneralinfo);
		return mapping.findForward("showGeneralInfoMonthlyStatCM");
	}

	/**
	 * ��ʾ���ƶ���˾��ͳ�ƽ���еĸ���ά��˾��ִ�����
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
	public ActionForward showContractorExeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getContractorExeInfo(bean); // �õ���άִ����Ϣ
		request.getSession().setAttribute("cmmonthlystatconexe", list);
		return mapping.findForward("showConExeMonthlyStatCM");
	}

	/**
	 * ��ʾ���ƶ���˾��ͳ�ƽ���е����мƻ�ִ�����
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
	public ActionForward showPlanExeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getPlanExeInfo(bean); // �õ��������мƻ�ִ����Ϣ
		request.getSession().setAttribute("cmmonthlystatplanexe", list);
		return mapping.findForward("showPlanExeMonthlyStatCM");
	}

	/**
	 * �õ����ƶ���˾��ͳ�ƽ���е������߶ζԱȱ�ͼ����
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
	public ActionForward showCompDataAllTypeSubline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		Map map = bo.getAllTypeSublinesInfo(bean); // �õ����������߶�����(���ϸ�δ�ϸ�δ�ƻ�)
		request.getSession().setAttribute("cmmonthlystat3sublinesmap", map);
		return mapping.findForward("showSublineCompMonthlyStatCM");
	}

	/**
	 * ��ʾ���ƶ���˾��ͳ�ƽ���еĶԱȷ�����Ϣ������
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
	public ActionForward showCompAnalysisInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		MonthlyStatCityMobileConBean bean = (MonthlyStatCityMobileConBean) request
				.getSession().getAttribute("CMMonthlyStatConBean");
		List list = bo.getVCompAnalysisInfo(bean); // �õ��������жԱȷ�����Ϣ������
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
		List list = bo.showMonthLayingMethodExecuteResultInfo(bean); // �õ��������мƻ�ִ����Ϣ
		request.setAttribute("month_laying_method_exeucte_result_list", list);
		return mapping.findForward("show_city_mobile_month_laying_method_exeucte_result");
	}

}
