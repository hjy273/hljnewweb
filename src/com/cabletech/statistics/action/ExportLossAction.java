package com.cabletech.statistics.action;

import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.statistics.templates.LossDetailTemplate;

public class ExportLossAction extends StatisticsBaseAction {
	private static String CONTENT_TYPE = "application/vnd.ms-excel";
	private static Properties config;

	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String reportType;

		List list = (List) request.getSession().getAttribute("QueryResult");
		reportType = request.getParameter("reportType");
		if (reportType.equalsIgnoreCase("lossDetail")) {
			ExportLossDetail(list, response);
		}
		if (reportType.equalsIgnoreCase("patrolDetail")) {
			super.getService().ExportPatrolDetail(list, response);
		}
		/*
		         if (reportType.equalsIgnoreCase("patrolRate")) {
		    super.getService().ExportPatrolRate(list, response);
		         }
		 */

		return null;
	}

	/**
	* 导出漏检明细
	* @param list List
	* @param response HttpServletResponse
	* @throws Exception
	*/
	public void ExportLossDetail(List list, HttpServletResponse response) throws Exception {
		//System.out.println( "Export List :" + list );
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		OutputStream out;
		initResponse(response, "漏检明细报表.xls");
		out = response.getOutputStream();
		String urlPath = ReportConfig.getInstance().getUrlPath( "report.lossDetailTemplateFileName");
		//System.out.println( "urlPath:" + urlPath );
		LossDetailTemplate template = new LossDetailTemplate(urlPath);
		ExcelStyle excelstyle = new ExcelStyle(urlPath);
		template.doExport(list, excelstyle);
		template.write(out);

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
