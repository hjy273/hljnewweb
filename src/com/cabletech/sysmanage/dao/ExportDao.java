package com.cabletech.sysmanage.dao;

import java.util.Properties;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.OutputStream;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import java.util.List;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletResponse;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.sysmanage.templates.SysmanageTemplate;

public class ExportDao {

	private static Logger logger = Logger.getLogger("ToolExportDao");
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	public boolean exportConPerson(List list, HttpServletResponse response) throws Exception {
		try {
			OutputStream out;
			initResponse(response, "��Ա��Ϣһ����.xls");
			out = response.getOutputStream();

			String urlPath = ReportConfig.getInstance().getUrlPath("report.conperson");

			SysmanageTemplate template = new SysmanageTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportConPerson(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportUserOnlineTimeResult(List list, HttpServletResponse response) throws Exception {
		try {
			OutputStream out;
			initResponse(response, "�û�������Ϣһ����.xls");
			out = response.getOutputStream();

			String urlPath = ReportConfig.getInstance().getUrlPath("report.useronlinetimeresult");

			SysmanageTemplate template = new SysmanageTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportUserOnlineTimeResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

}
