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
	 * 移动查询代维
	 * @param user
	 * @return
	 */
	public List getConsByDeptId(UserInfo user) {
		return dao.getConsByDeptId(user);
	}

	/**
	 * 查询分布统计总表
	 * @param user
	 * @return
	 */
	public List getDistriReports(String contractorid, String startmonth, String endmonth) {
		return dao.getDistriReports(contractorid, startmonth, endmonth);
	}

	/**
	 * 查询分布统计总表所包含的代维
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
			initResponse(response, "分布统计总表.xls");
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
	 * 创建输出流的头
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
