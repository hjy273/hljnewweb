package com.cabletech.linepatrol.maintenance.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.maintenance.module.TestCableData;
import com.cabletech.linepatrol.maintenance.module.TestChipData;
import com.cabletech.linepatrol.maintenance.module.TestPlanLine;
import com.cabletech.linepatrol.maintenance.module.TestProblem;
import com.cabletech.linepatrol.maintenance.templates.TestPlanTmplate;
import com.cabletech.linepatrol.resource.model.RepeaterSection;

public class TestPlanExportBO {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * 导出查询的测试计划统计信息
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportTestPlanStats(List list, HttpServletResponse response) throws ServiceException {
		try {
			initResponse(response, "测试计划统计.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.testplanstat");
			TestPlanTmplate template = new TestPlanTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportTestPlans(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * 导出录入光缆数据信息
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportTestCableDate(TestPlanLine line, RepeaterSection res, TestCableData data,
			List<TestProblem> problems, Map<Object, TestChipData> chips, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "光缆录入数据.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.exprotcableinfo");
			TestPlanTmplate template = new TestPlanTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportCableData(line, res, data, problems, chips, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@Transactional(readOnly = true)
	public void exportAnaylseData(TestChipData coreData, String trunkName, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "数据分析.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.exprotanalysedata");
			TestPlanTmplate template = new TestPlanTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportAnaylseData(coreData, trunkName, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * 创建输出流的头
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param fileName
	 *            String
	 * @throws UnsupportedEncodingException
	 */
	@Transactional(readOnly = true)
	private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

}
