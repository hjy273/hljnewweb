package com.cabletech.linepatrol.remedy.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.linepatrol.remedy.templates.StatByContractorTemplates;
import com.cabletech.linepatrol.remedy.templates.StatByTownTemplates;
import com.cabletech.linepatrol.remedy.templates.StatDetailByTownTemplates;

public class ExportStatDao {
	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * 按区县导出
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportStatByTown(List statList, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "各区县修缮统计信息.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.statbytown");
			StatByTownTemplates template = new StatByTownTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReport(statList, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出统计信息一览表异常:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 按修缮项导出
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportStatDetailByTown(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "各修缮项统计信息.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.statdetailbytown");
			StatDetailByTownTemplates template = new StatDetailByTownTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReport(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出统计信息一览表异常:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 按代维导出
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportStatByContractor(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "修缮项迁改详细信息.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.statbycontractor");
			StatByContractorTemplates template = new StatByContractorTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReport(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出统计信息一览表异常:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}
}
