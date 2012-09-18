package com.cabletech.toolsmanage.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.toolsmanage.Templates.ToolsTemplate;
import com.cabletech.toolsmanage.beans.ToolsInfoBean;

public class ToolExportDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public void exportStockResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "������ⵥһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolstockresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportRevokeResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������ϵ�һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolrevokeresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportRevokeResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������õ�һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.tooluseresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportUseResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportBackResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������������õ�һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolbackresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBackResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportMainResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������޵�һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolmainresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportMainResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportToMainResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������޵�һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.tooltomainresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportToMainResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportStorageResult(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolstorageresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStorageResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportBaseResult(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "������Ϣһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolbaseresult");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBaseResult(list, excelstyle);
		template.write(out);

	}

	public void exportStockList(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "������ⵥ��ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolstocklist");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportRevokeList(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������ϵ���ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolrevokelist");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportRevokeList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseList(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������õ���ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.tooluselist");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportUseList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportMainList(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������޵���ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.toolmainlist");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportMainList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportToMainList(List list, ToolsInfoBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������޵���ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.tooltomainlist");

		ToolsTemplate template = new ToolsTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportToMainList(list, bean, excelstyle);
		template.write(out);

	}
}
