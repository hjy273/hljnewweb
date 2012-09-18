package com.cabletech.statistics.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.statistics.templates.PatrolLeakTemplate;

public class ExportDao {

	private static Logger logger = Logger.getLogger(ExportDao.class.getName());
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * 导出正在执行计划信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportCurrentPlanInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "正在执行的计划信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.plancurrentinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportCurrentPlanResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 导出计划中巡检过的线段信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanPatorlSublineInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "已巡检的线段信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planpatrolsublineinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanPatorlSublineResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 导出计划中巡检过的巡检点信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanPatorlPointInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "已巡检的巡检点信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planpatrolpointinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanPatorlPointResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 导出计划中漏检的线段信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanLeakSublineInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "漏检的线段信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planleaksublineinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanLeakSublineResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 导出计划中巡检过的巡检点信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanLeakPointInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "漏检的巡检点信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planleanpointinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanLeakPointResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}
	}

}
