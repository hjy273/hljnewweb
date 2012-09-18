package com.cabletech.statistics.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class PatrolLeakTemplate extends Template{
	private static Logger logger = Logger.getLogger(PatrolLeakTemplate.class
			.getName());

	public PatrolLeakTemplate(String urlPath) throws IOException {
		super(urlPath);

	}

	/**
	 *  导出正在执行计划信息
	 * @param list
	 * @param excelstyle
	 */
	public void exportCurrentPlanResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("planname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("planname").toString());
				}

				if (record.get("begindate") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("begindate").toString());
				}

				if (record.get("enddate") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("enddate").toString());
				}

				if (record.get("patrolname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("patrolname").toString());
				}

				if (record.get("plantimes") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("plantimes").toString());
				}

				if (record.get("patroltimes") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("patroltimes").toString());
				}
				
				if (record.get("leaktimes") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("leaktimes").toString());
				}

				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	
	/**
	 *  导出计划中巡检过的线段信息
	 * @param list
	 * @param excelstyle
	 */
	public void exportPlanPatorlSublineResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("sublinename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("sublinename").toString());
				}

				if (record.get("linename") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("linename").toString());
				}
				
				if (record.get("codename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("codename").toString());
				}				

				if (record.get("patroltimes") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("patroltimes").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	
	/**
	 *  导出计划中巡检过的巡检点信息
	 * @param list
	 * @param excelstyle
	 */
	public void exportPlanPatorlPointResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("pointname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("pointname").toString());
				}

				if (record.get("simid") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("simid").toString());
				}

				if (record.get("patroldate") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("patroldate").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	/**
	 *  导出计划中漏检的线段信息
	 * @param list
	 * @param excelstyle
	 */
	public void exportPlanLeakSublineResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("sublinename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("sublinename").toString());
				}

				if (record.get("linename") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("linename").toString());
				}
				
				if (record.get("codename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("codename").toString());
				}

				if (record.get("leaktimes") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("leaktimes").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}
	
	
	/**
	 *  导出计划中漏检的巡检点信息
	 * @param list
	 * @param excelstyle
	 */
	public void exportPlanLeakPointResult(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; // 行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);

				if (record.get("pointname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("pointname").toString());
				}

				if (record.get("plantimes") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("plantimes").toString());
				}

				if (record.get("patroltimes") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("patroltimes").toString());
				}
				
				if (record.get("leaktimes") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("leaktimes").toString());
				}
				r++; // 下一行
			}
			logger.info("成功写入");
		}
	}

}
