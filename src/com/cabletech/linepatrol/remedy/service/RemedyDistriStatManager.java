package com.cabletech.linepatrol.remedy.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.linepatrol.remedy.dao.RemedyDistriStatDao;
import com.cabletech.linepatrol.remedy.templates.RemedyInfoTmplate;

public class RemedyDistriStatManager extends BaseBisinessObject {
	private RemedyDistriStatDao dao = new RemedyDistriStatDao();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * �ƶ���ѯ��ά
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user) {
		return dao.getConsByDeptId(user);
	}

	/**
	 * ��ѯ�ֲ�ͳ���ܱ�
	 * @param user
	 * @return
	 */
	public List getDistriReports(String contractorid, String startmonth, String endmonth) {
		return dao.getDistriReports(contractorid, startmonth, endmonth);
	}

	/**
	 * ��ѯ�ֲ�ͳ���ܱ��������Ĵ�ά
	 * @param user
	 * @return
	 */
	public List getDistriReportCon(String contractorid, String startmonth, String endmonth) {
		return dao.getDistriReportCon(contractorid, startmonth, endmonth);
	}

	/**
	 * 
	 * 
	 *sumfee,time,size,map
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	public void exportStorage(String sumfee, String time, int size, Map map, HttpServletResponse response) {
		try {
			initResponse(response, "�ֲ�ͳ���ܱ�.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.remedydistristat");
			RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportReprot(sumfee, time, size, map, excelstyle);
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
	private void initResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}
}
