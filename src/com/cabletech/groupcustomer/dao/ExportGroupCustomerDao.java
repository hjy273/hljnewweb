package com.cabletech.groupcustomer.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.groupcustomer.bean.GroupCustomerBean;
import com.cabletech.groupcustomer.templates.GroupCustomerTemplates;

public class ExportGroupCustomerDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public ExportGroupCustomerDao() {
	}

	/**
	 * 初始化reponse
	 * @param response
	 * @param outfilename
	 * @throws Exception
	 */
	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * 导出客户资料
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportGroupCustomerResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "集团客户资料信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.groupcustomerresult");
			GroupCustomerTemplates template = new GroupCustomerTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportGroupCustomerResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			logger.error("导出报表信息异常_dddddddddd:" + e.getStackTrace());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 导出客户分析资料
	 * @param list
	 * @param response
	 * @param customerNumStr
	 * @param parserpercent
	 * @param bean
	 * @return
	 */
	public boolean exportGroupCustomerParserResult(List list, HttpServletResponse response, String customerNumStr,
			String parserpercent, GroupCustomerBean bean) {
		try {
			OutputStream out;
			initResponse(response, "集团客户分析信息一览表.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.groupcustomerparserresult");
			GroupCustomerTemplates template = new GroupCustomerTemplates(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportGroupCustomerParserResult(list, excelstyle, customerNumStr, parserpercent, bean);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("导出报表信息异常:" + e.getMessage());
			logger.error("导出报表信息异常_dddddddddd:" + e.getStackTrace());
			e.printStackTrace();
			return false;
		}

	}
}
