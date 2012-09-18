package com.cabletech.linepatrol.remedy.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.struts.action.ActionForm;

import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.linepatrol.remedy.dao.RemedyReportDao;
import com.cabletech.linepatrol.remedy.templates.RemedyInfoTmplate;

public class RemedyReportManager extends RemedyBaseBO {
	private RemedyReportDao rrd = new RemedyReportDao();
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	public List queryAllTown() {
		return rrd.queryAllTown();
	}

	public List queryAllContractor(String regionId) {
		return rrd.queryAllContractor(regionId);
	}

	public List getCheckReport(String contractorId, String remedyDate, String townId) throws Exception {
		super.setRemedyBaseDao(new RemedyReportDao());
		List resultList = new ArrayList();
		List list = rrd.getCheckReport(contractorId, remedyDate, townId);
		String contractorName = super.getRemedyBaseDao().getRemedyApplyContractorName(contractorId);
		String townName = super.getRemedyBaseDao().getRemedyApplyTownName(townId);
		float sumTotalFee = 0;
		for (int i = 0; i < list.size(); i++) {
			BasicDynaBean bean = (BasicDynaBean) list.get(i);
			BigDecimal totalfee = (BigDecimal) bean.get("totalfee");
			sumTotalFee += totalfee.floatValue();
		}
		resultList.add(contractorName);
		resultList.add(townName);
		resultList.add(remedyDate);
		resultList.add(new Float(sumTotalFee));
		resultList.add(list);
		return resultList;
	}

	public String compositeCondition(HttpServletRequest request, ActionForm form) {
		// TODO Auto-generated method stub
		return "";
	}

	public List getNextProcessManList(String applyState) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean judge(String applyId) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * 
	 *list
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	public void exportStorage(List list, HttpServletResponse response) {
		try {
			initResponse(response, "验收报告.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath("report.remedyreprot");
			RemedyInfoTmplate template = new RemedyInfoTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.doExportReport(list, excelstyle);
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
