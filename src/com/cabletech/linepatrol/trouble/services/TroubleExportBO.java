package com.cabletech.linepatrol.trouble.services;

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
import com.cabletech.linepatrol.trouble.module.TroubleNormGuide;
import com.cabletech.linepatrol.trouble.templates.TroubleTmplate;

public class TroubleExportBO {

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * ������ѯ�Ĺ���ͳ����Ϣ
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportTroubleStats(List list, HttpServletResponse response) throws ServiceException {
		try {
			initResponse(response, "����ͳ��.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troublestat");
			TroubleTmplate template = new TroubleTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportTroubles(list, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * ������ѯ�Ĺ���ָ����Ϣ
	 * 
	 * @param list
	 *            List
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@Transactional(readOnly = true)
	public void exportTroubleQuota(List list, TroubleNormGuide guide, String month, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "������ָ��.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troublequota");
			TroubleTmplate template = new TroubleTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportTroubleQuotas(list, guide, month, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * ����������ƻ�
	 * @param list
	 * @param guide
	 * @param year
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public void exportYearTroubleQuota(List list, TroubleNormGuide guide, String year, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "������ָ��.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troubleyearquota");
			TroubleTmplate template = new TroubleTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportYearTroubleQuotas(list, guide, year, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * ����������ƻ�
	 * @param list
	 * @param guide
	 * @param year
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public void exportYearTroubleQuota(Map map, TroubleNormGuide guide, String year, HttpServletResponse response)
			throws ServiceException {
		try {
			initResponse(response, "������ָ��.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troubleyearquota");
			TroubleTmplate template = new TroubleTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportYearTroubleQuotas(map, guide, year, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * ����������ƻ�
	 * @param list
	 * @param guide
	 * @param year
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly = true)
	public void exportTimeAreaTroubleQuota(Map map, TroubleNormGuide guide, String beginTime, String endTime,
			HttpServletResponse response) throws ServiceException {
		try {
			initResponse(response, "����ָ���б�.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.troubleyearquota");
			TroubleTmplate template = new TroubleTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportTimeAreaTroubleQuotas(map, guide, beginTime, endTime, excelstyle);
			template.write(out);
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * �����������ͷ
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
