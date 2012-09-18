package com.cabletech.statistics.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.statistics.beans.ApproveRateBean;
import com.cabletech.statistics.beans.QueryConditionBean;
import com.cabletech.statistics.dao.ApproveRateDAOImpl;
import com.cabletech.statistics.dao.MonthTaskStaDAOImpl;
import com.cabletech.statistics.dao.QueryLossDetailDAOImpl;
import com.cabletech.statistics.dao.QueryPatrolDetailDAOImpl;
import com.cabletech.statistics.dao.QueryPatrolRateDAOImpl;
import com.cabletech.statistics.dao.planStatisticFormDAOImpl;
import com.cabletech.statistics.domainobjects.PatrolRate;
import com.cabletech.statistics.domainobjects.PlanStatisticForm;
import com.cabletech.statistics.domainobjects.QueryCondition;
import com.cabletech.statistics.templates.ContractorPlanFormTemplate;
import com.cabletech.statistics.templates.LossDetailTemplate;
import com.cabletech.statistics.templates.PatrolDetailTemplate;
import com.cabletech.statistics.templates.PatrolRateTemplate;
import com.cabletech.statistics.templates.PlanAppRateTemplate;
import com.cabletech.statistics.templates.PlanFormTemplate;

public class StatisticsBO {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private static Properties config;

	public StatisticsBO() {

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * ȡ������ͨ����
	 * @param conBean ApproveRateBean
	 * @return ApproveRateBean
	 * @throws Exception
	 */
	public ApproveRateBean getApproveRate(ApproveRateBean conBean) throws Exception {
		ApproveRateDAOImpl appRateBo = new ApproveRateDAOImpl();
		return appRateBo.getApproveRate(conBean);
	}

	//��ѯѲ����ϸ
	public List QueryPatrolDetail(QueryCondition condition) throws Exception {
		QueryPatrolDetailDAOImpl queryDetail = new QueryPatrolDetailDAOImpl();
		List list = queryDetail.queryPatrolDetail(condition);
		return list;

	}

	//��ѯ�ƻ�Ѳ����
	public List QueryPatrolRate(QueryCondition condition) throws Exception {

		QueryPatrolRateDAOImpl queryLoss = new QueryPatrolRateDAOImpl();
		List list = queryLoss.getPatrolRateReport(condition);
		return list;

	}

	/**
	 * Ѳ����
	 * @param condition QueryCondition
	 * @return PatrolRate
	 * @throws Exception
	 */
	public PatrolRate getPatrolReteAsObj(QueryCondition condition) throws Exception {
		QueryPatrolRateDAOImpl rate = new QueryPatrolRateDAOImpl();

		return rate.getPatrolReteAsObj(condition);

	}

	//��ѯ©����ϸ
	public List QueryLossDetail(QueryCondition condition) throws Exception {

		QueryLossDetailDAOImpl queryLoss = new QueryLossDetailDAOImpl();
		List list = queryLoss.getLossDetailReport(condition);
		return list;
	}

	/**
	 * ����Ѳ����ϸ
	 * @param list List
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportPatrolDetail(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "Ѳ����ϸ����.xls");
		out = response.getOutputStream();
		String urlPath = getUrlPath(config, "report.patrolDetailTemplateFileName");
		PatrolDetailTemplate template = new PatrolDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);

	}

	/**
	 * ����©����ϸ
	 * @param list List
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportLossDetail(List list, HttpServletResponse response) throws Exception {
		//     System.out.println( "Export List :" + list );
		OutputStream out;
		initResponse(response, "©����ϸ����.xls");
		out = response.getOutputStream();
		String urlPath = getUrlPath(config, "report.lossDetailTemplateFileName");
		LossDetailTemplate template = new LossDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);

	}

	/**
	 * ����Ѳ����
	 * @param list List
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportPatrolRate(PatrolRate patrolrate, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "Ѳ���ʱ���.xls");
		out = response.getOutputStream();
		String urlPath = getUrlPath(config, "report.patrolRateTemplateFileName");
		PatrolRateTemplate template = new PatrolRateTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(patrolrate, excelstyle);
		template.write(out);

	}

	/**
	 * ����Ѳ�쵥λ�����ƻ���������Ѳ����
	 * @param patrolrate PatrolRate
	 * @param planbean YearMonthPlanBean
	 * @param taskVct Vector
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportContractorPlanForm(PatrolRate patrolrate, YearMonthPlanBean planbean, Vector taskVct,
			HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "��ά��λ�ƻ�ִ����������.xls");
		out = response.getOutputStream();
		String urlPath = getUrlPath(config, "report.contractorPlanForm");
		ContractorPlanFormTemplate template = new ContractorPlanFormTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(patrolrate, planbean, taskVct, excelstyle);
		template.write(out);

	}

	public void ExportPlanAppRate(ApproveRateBean appRate, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�ƻ�����ͨ���ʱ���.xls");
		out = response.getOutputStream();
		String urlPath = getUrlPath(config, "report.planAppRateForm");
		PlanAppRateTemplate template = new PlanAppRateTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(appRate, excelstyle);
		template.write(out);

	}

	/**
	 * ������������
	 * @param planform PlanStatisticForm
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportPlanForm(PlanStatisticForm planform, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		OutputStream out;
		initResponse(response, "�ƻ���������.xls");
		out = response.getOutputStream();

		String urlPath = getUrlPath(config, "report.weekformTemplateFileNamenew");

		if (planform.getCyctype().equals("month")) {
			if (planform.getSeatchby().equals("patrol")) { //Ѳ��ά���鰴�²�ѯ
				urlPath = getUrlPath(config, "report.weekformTemplateFileNamenew");
			} else {
				urlPath = getUrlPath(config, "report.monthformTemplateFileName");
			}
		}

		PlanFormTemplate template = new PlanFormTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportNew(planform, excelstyle, request);
		template.write(out);

	}

	private String getUrlPath(Properties config, String propertyItemName) throws IOException {
		String fileName = config.getProperty(propertyItemName);
		if (fileName != null && fileName != "") {
			String urlPath = ConfigPathUtil.getClassPathConfigFile(fileName);
			//          System.out.println( "ExcelFile:" + urlPath );
			return urlPath;

		} else {
			return null;
		}
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * ȡ�ñ���
	 * @param conBean QueryConditionBean
	 * @return PlanStatisticForm
	 * @throws Exception
	 */
	public PlanStatisticForm getPlanStatistic(QueryConditionBean conBean) throws Exception {
		planStatisticFormDAOImpl formbo = new planStatisticFormDAOImpl();
		return formbo.getPlanStatistic(conBean);
	}

	/**
	 * ÿ�¼ƻ�����ͳ��
	 * @param conBean QueryConditionBean
	 * @return Hashtable
	 * @throws Exception
	 */
	public Hashtable getMonthTaskStaVct(QueryConditionBean conBean) throws Exception {
		MonthTaskStaDAOImpl monthTaskStabo = new MonthTaskStaDAOImpl();
		return monthTaskStabo.getMonthTaskStaVct(conBean);
	}

	private void jbInit() throws Exception {
	}

}
