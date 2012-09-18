package com.cabletech.troublemanage.services;

import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.troublemanage.beans.AccidentBean;
import com.cabletech.troublemanage.templates.YMWTroubleFormTemplate;

public class StatisticsBO {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private static Logger logger = Logger.getLogger("StatisticsBO");

	public StatisticsBO() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 隐患处理通知单
	 * @param plan Plan
	 * @param taskVct Vector
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportTroubleNoticeform(AccidentBean bean, Vector taskVct, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "隐患处理通知单.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.TroubleNoticeForm");

		YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportTroubleNoticeform(bean, taskVct, excelstyle);
		template.write(out);

	}

	/**
	 * 障碍处理通知单
	 * @param plan Plan
	 * @param taskVct Vector
	 * @param response HttpServletResponse
	 * @throws Exception
	 */
	public void ExportAccidentNoticeform(AccidentBean bean, Vector taskVct, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "障碍处理通知单.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.AccidentNoticeForm");

		YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.ExportAccidentNoticeform(bean, taskVct, excelstyle);
		template.write(out);

	}

	public boolean exportUndoneTrouble(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "未处理隐患列表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.undonetrouble");

			YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportUndoneTrouble(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportTroubleResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "隐患查询列表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troubleresult");

			YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportTroubleResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportUndoneAccident(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "未处理障碍列表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.undoneaccident");

			YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportUndoneAccident(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportAccidentResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "障碍查询列表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.accidentresult");

			YMWTroubleFormTemplate template = new YMWTroubleFormTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportAccidentResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
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
