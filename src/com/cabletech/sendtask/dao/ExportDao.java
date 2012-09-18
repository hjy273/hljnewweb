package com.cabletech.sendtask.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.sendtask.beans.SendTaskBean;
import com.cabletech.sendtask.templates.SendTaskTemplate;

public class ExportDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public boolean exportSendTaskResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "派单信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.sendtaskresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportSendTaskResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportQuerytotalResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "派单查询统计信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querytotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportQuerytotalResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 个人工单信息
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportQueryPersontotalResult(List list, String queryFlg, String username, SendTaskBean bean,
			String dataCount, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "个人工单信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querytotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportQueryPersontotalResult(list, queryFlg, username, bean, dataCount, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("个工工单导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	/**
	 * 个人工单统计
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPersonnumtotalResult(List list, SendTaskBean bean, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "个人工单统计一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querynumtotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPersonnumtotalResult(list, bean, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("个工工单导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportDepttotalResult(List list, SendTaskBean deptquerybean, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "部门工单统计信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querydepttotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportDeptTotalResult(list, deptquerybean, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportEndorseResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "签收派单一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.endorseresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportEndorseResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportReplyResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "回复派单一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.replyresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReplyResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}

	public boolean exportValidateResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "验证派单一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.validateresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportValidateResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			return false;
		}

	}
}
