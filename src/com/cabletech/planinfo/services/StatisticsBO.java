package com.cabletech.planinfo.services;

import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.planinfo.beans.PlanBean;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.templates.TaskPointsTemplate;
import com.cabletech.planinfo.templates.YMWPlanFormTemplate;

public class StatisticsBO {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public StatisticsBO() {

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 导出巡检员对应任务巡检点
	 * @param plan PlanBean
	 * @param taskVct Vector
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	//    public void ExportPatrolPointsList(String patrolName,
	//                                       Vector taskVct,
	//                                       HttpServletResponse response) throws
	//        Exception {
	//
	//        OutputStream out;
	//        initResponse(response, "任务巡检点信息报表.xls");
	//        out = response.getOutputStream();
	//
	//        String urlPath = getUrlPath(config, "report.planPointsList");
	//
	//        TaskPointsTemplate template = new TaskPointsTemplate(urlPath);
	//        template.ExportPatrolPointsList(patrolName, taskVct);
	//        template.write(out);
	//
	//    }
	//********************pzj 修改 加参数patrolid
	public void ExportPatrolPointsList(String patrolName, String patrolid, Vector taskVct, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "任务巡检点信息报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.planPointsList");

		TaskPointsTemplate template = new TaskPointsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportPatrolPointsList(patrolName, patrolid, taskVct, excelstyle);

		template.write(out);

	}

	public void ExportYearPlanResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "计划信息结果报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.yearplanresult");

		TaskPointsTemplate template = new TaskPointsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportYearPlanResult(list, excelstyle);
		template.write(out);

	}

	public void ExportMonthPlanResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "计划信息结果报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.monthplanresult");

		TaskPointsTemplate template = new TaskPointsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportMonthPlanResult(list, excelstyle);
		template.write(out);

	}

	public void ExportWeekPlanResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "计划信息结果报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.weekplanresult");

		TaskPointsTemplate template = new TaskPointsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportWeekPlanResult(list, excelstyle);
		template.write(out);

	}

	/**
	 * 周计划信息报表
	 * @param plan Plan
	 * @param taskVct Vector
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportWeekPlanform(PlanBean plan, Vector taskVct, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "计划信息报表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.weekPlanInfo");

		YMWPlanFormTemplate template = new YMWPlanFormTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportWeekPlanform(plan, taskVct, excelstyle);
		template.write(out);

	}

	/**
	 * 年月计划信息报表
	 * @param plan Plan
	 * @param taskVct Vector
	 * @param fID String
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportYMPlanform(YearMonthPlanBean plan, Vector taskVct, HttpServletResponse response) throws Exception {
		try {
			OutputStream out;
			initResponse(response, "计划信息报表.xls");
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.ymplaninfo");

			out = response.getOutputStream();

			YMWPlanFormTemplate template = new YMWPlanFormTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.ExportYMPlanform(plan, taskVct, excelstyle);
			template.write(out);
		} catch (Exception e) {
			//System.out.println( "ExportYMPlanform ERROR: " + e.getMessage() );
		}
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	private void jbInit() throws Exception {
	}

}
