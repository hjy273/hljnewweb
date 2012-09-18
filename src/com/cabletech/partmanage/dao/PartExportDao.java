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
		initResponse(response, "维护材料审批一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisition");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, bean, excelstyle);

		template.write(out);

	}

	public void ExportUse(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "材料使用一览表.xls");
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
		initResponse(response, "维护材料申请单一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisitionresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportRequisitonResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportStockResult(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "维护材料入库单一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstockresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockResult(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseResult(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "材料出库单一览表.xls");
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
		initResponse(response, "利旧材料入库单一览表.xls");
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
		initResponse(response, "材料库存一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstorageresult");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStorageResult(list, bean, excelstyle, newlownumber, newhignumber, oldlownumber, oldhignumber);
		template.write(out);

	}

	public void exportBaseInfo(List list, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "维护材料信息一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portbaseinfo");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportBaseInfo(list, excelstyle);
		template.write(out);

	}

	public void exportStockList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "维护材料入库单详细信息.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.stocklist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportStockList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportUseList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "维护材料出库单详细信息.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.uselist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportUseList(list, bean, excelstyle);
		template.write(out);

	}

	public void exportOldUseList(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "利旧材料入库单详细信息.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.olduselist");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.exportOldUseList(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportStock(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "材料入库一览表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portstockstat");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportStock(list, bean, excelstyle);
		template.write(out);

	}

	public void ExportOldStock(List list, Part_requisitionBean bean, HttpServletResponse response) throws Exception {

		OutputStream out;
		initResponse(response, "利旧材料入库一览表.xls");
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
		initResponse(response, "维护材料申请单详细信息表.xls");
		out = response.getOutputStream();

		String urlPath = ReportConfig.getInstance().getUrlPath( "report.portrequisitions");

		RequisitionTemplate template = new RequisitionTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExportRequisitions(list, bean, lists, excelstyle);
		template.write(out);

	}
}
