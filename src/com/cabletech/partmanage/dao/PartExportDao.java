package com.cabletech.partmanage.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.partmanage.Templates.RequisitionTemplate;
import com.cabletech.partmanage.beans.Part_requisitionBean;

public class PartExportDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public void ExportRequisition(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {
		OutputStream out;
		initResponse(response, "ά����������һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisition");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, bean, excelstyle);

		template.write(out);

	}

	public void ExportUse(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "����ʹ��һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portuse");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportUse(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportRequisitionResult(List list, Part_requisitionBean bean, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "ά���������뵥һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisitionresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportRequisitonResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportStockResult(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "ά��������ⵥһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstockresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseResult(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "���ϳ��ⵥһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portuseresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportUseResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportOldStockResult(List list, Part_requisitionBean bean, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "���ɲ�����ⵥһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portoldstockresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportOldStockResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportStorageResult(List list, Part_requisitionBean bean, String newlownumber, String newhignumber,
			String oldlownumber, String oldhignumber,

			HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "���Ͽ��һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstorageresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStorageResult(list, bean, excelstyle, newlownumber, newhignumber, oldlownumber, oldhignumber);
		template.write(out);

	}

	public void exportBaseInfo(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "ά��������Ϣһ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portbaseinfo");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBaseInfo(list, excelstyle);
		template.write(out);

	}

	public void exportStockList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "ά��������ⵥ��ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.stocklist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "ά�����ϳ��ⵥ��ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.uselist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportUseList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportOldUseList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "���ɲ�����ⵥ��ϸ��Ϣ.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.olduselist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportOldUseList(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportStock(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "�������һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstockstat");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportStock(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportOldStock(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "���ɲ������һ����.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portoldstockstat");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportOldStock(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportRequisition(List list, Part_requisitionBean bean, List lists, HttpServletResponse response)
			throws Exception {

		OutputStream out;
		initResponse(response, "ά���������뵥��ϸ��Ϣ��.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisitions");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportRequisitions(list, bean, lists, excelstyle);
		template.write(out);

	}
}
