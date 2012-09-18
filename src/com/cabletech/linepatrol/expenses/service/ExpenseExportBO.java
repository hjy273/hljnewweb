package com.cabletech.linepatrol.expenses.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.config.ReportConfig;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.ctf.exception.ServiceException;
import com.cabletech.linepatrol.expenses.model.ExpenseAffirm;
import com.cabletech.linepatrol.expenses.template.ExpenseTmplate;

public class ExpenseExportBO  {
	
	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	
	/**
	 * 导出光缆费用模板
	 * @param map
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public void exportExpense(Map<String,Map> map,String yearmonth,
			HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "光缆费用.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.expenselist");
			ExpenseTmplate template = new ExpenseTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportExpense(map,yearmonth,excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}

	}
	
	/**
	 * 导出管道费用模板
	 * @param map
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public void exportPipeExpense(Map<String,Object>  map,String yearmonth,
			HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "管道费用.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.expensepipelist");
			ExpenseTmplate template = new ExpenseTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPipeExpense(map,yearmonth,excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}

	}
	
	/**
	 * 导出管道费用结算确认函
	 * @param expenses
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public void exportPipeSettlementExpense(Map<String,Object> expenses,
			ExpenseAffirm affirm,Contractor c ,
			String beginMonth,String endMonth,
			HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "管道费用结算.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.pipesettlementexpenselist");
			ExpenseTmplate template = new ExpenseTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportPipeSettlementExpense(expenses, affirm, c, 
					beginMonth, endMonth, excelstyle);
			template.write(out);
		}catch(Exception e){
			e.getStackTrace();
		}
	}
	
	
	/**
	 * 导出光缆费用结算确认函
	 * @param expenses
	 * @param response
	 * @throws ServiceException
	 */
	@Transactional(readOnly=true)
	public void exportSettlementExpense(Map<String,Map> expenses,
			ExpenseAffirm affirm,Contractor c ,
			String beginMonth,String endMonth,
			HttpServletResponse response) throws ServiceException {
		try{
			initResponse(response, "光缆费用结算.xls");
			OutputStream out = response.getOutputStream();
			String urlPath = ReportConfig.getInstance().getUrlPath( "report.settlementexpenselist");
			ExpenseTmplate template = new ExpenseTmplate(urlPath);
			ExcelStyle excelstyle = new ExcelStyle(urlPath);
			template.exportSettlementExpense(expenses,affirm,
					c,beginMonth,endMonth,excelstyle);
			template.write(out);
		}catch(Exception e){
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
	@Transactional(readOnly=true)
	private void initResponse(HttpServletResponse response, String fileName)
	throws UnsupportedEncodingException {
		response.reset();
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(), "iso-8859-1"));
	}

}
