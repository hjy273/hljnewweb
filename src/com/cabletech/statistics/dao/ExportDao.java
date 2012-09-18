package com.cabletech.statistics.dao;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.statistics.templates.PatrolLeakTemplate;

public class ExportDao {

	private static Logger logger = Logger.getLogger(ExportDao.class.getName());
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));

	}

	/**
	 * ��������ִ�мƻ���Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportCurrentPlanInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "����ִ�еļƻ���Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.plancurrentinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportCurrentPlanResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	/**
	 * �����ƻ���Ѳ������߶���Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanPatorlSublineInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "��Ѳ����߶���Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planpatrolsublineinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanPatorlSublineResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	/**
	 * �����ƻ���Ѳ�����Ѳ�����Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanPatorlPointInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "��Ѳ���Ѳ�����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planpatrolpointinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanPatorlPointResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

	/**
	 * �����ƻ���©����߶���Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanLeakSublineInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "©����߶���Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planleaksublineinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanLeakSublineResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}

	}

	/**
	 * �����ƻ���Ѳ�����Ѳ�����Ϣ
	 * @param list
	 * @param response
	 * @return
	 */
	public boolean exportPlanLeakPointInfo(List list, HttpServletResponse response) {
		try {
			OutputStream out;
			initResponse(response, "©���Ѳ�����Ϣһ����.xls");
			out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.planleanpointinfo");
			PatrolLeakTemplate template = new PatrolLeakTemplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPlanLeakPointResult(list, excelstyle);
			template.write(out);
			return true;
		} catch (Exception e) {
			logger.error("����������Ϣ�쳣:" + e.getMessage());
			return false;
		}
	}

}
