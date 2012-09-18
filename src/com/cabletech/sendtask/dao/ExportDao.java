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
			initResponse(response, "�ɵ���Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.sendtaskresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportSendTaskResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportQuerytotalResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "�ɵ���ѯͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querytotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportQuerytotalResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	/**
	 * ���˹�����Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportQueryPersontotalResult(List list, String queryFlg, String username, SendTaskBean bean,
			String dataCount, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���˹�����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querytotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportQueryPersontotalResult(list, queryFlg, username, bean, dataCount, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("������������������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	/**
	 * ���˹���ͳ��
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPersonnumtotalResult(List list, SendTaskBean bean, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���˹���ͳ��һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querynumtotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPersonnumtotalResult(list, bean, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("������������������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportDepttotalResult(List list, SendTaskBean deptquerybean, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "���Ź���ͳ����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.querydepttotalresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportDeptTotalResult(list, deptquerybean, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportEndorseResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "ǩ���ɵ�һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.endorseresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportEndorseResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportReplyResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "�ظ��ɵ�һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.replyresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportReplyResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	public boolean exportValidateResult(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "��֤�ɵ�һ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.validateresult");
			SendTaskTemplate template = new SendTaskTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportValidateResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}
}
