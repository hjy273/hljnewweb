package com.cabletech.linecut.Templates;

//
import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.partmanage.action.*;

public class LineCutTemplate extends Template {
	private static Logger logger = Logger.getLogger(PartRequisitionAction.class
			.getName());

	public LineCutTemplate(String urlPath) throws IOException {
		super(urlPath);

	}

	/**
	 * 使用 DynaBean
	 * @param list List
	 */
	public void doExport(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);
		int r = 2; //行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("numerical") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("numerical").toString());
				}
				if (record.get("contractorname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("contractorname").toString());
				}

				if (record.get("name") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("name").toString());
				}

				if (record.get("sublinename") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("sublinename").toString());
				}

				if (record.get("address") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("address").toString());
				}

				if (record.get("essetime") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("essetime").toString());
				}

				if (record.get("updoc") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("updoc").toString());
				}

				if (record.get("isarchive") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("isarchive").toString());
				}

				r++; //下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportReLineCut(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; //行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("numerical") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("numerical").toString());
				}
				if (record.get("name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("name").toString());
				}

				if (record.get("sublinename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sublinename").toString());
				}

				if (record.get("protime") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("protime").toString());
				}

				if (record.get("address") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("address").toString());
				}

				if (record.get("audittime") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("audittime").toString());
				}

				if (record.get("isarchive") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("isarchive").toString());
				}

				r++; //下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportLineCutWork(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; //行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("numerical") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("numerical").toString());
				}
				if (record.get("name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("name").toString());
				}

				if (record.get("sublinename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sublinename").toString());
				}

				if (record.get("address") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("address").toString());
				}

				if (record.get("endvalue") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("endvalue").toString());
				}

				if (record.get("usetime") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("usetime").toString());
				}

				if (record.get("essetime") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("essetime").toString());
				}

				if (record.get("isarchive") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("isarchive").toString());
				}
				r++; //下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportLineCutRe(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; //行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("numerical") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("numerical").toString());
				}
				if (record.get("name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("name").toString());
				}

				if (record.get("sublinename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("sublinename").toString());
				}

				if (record.get("prousetime") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("prousetime").toString());
				}

				if (record.get("protime") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("protime").toString());
				}

				if (record.get("address") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("address").toString());
				}

				if (record.get("isarchive") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("isarchive").toString());
				}

				r++; //下一行
			}
			logger.info("成功写入");
		}
	}

	public void exportLineCutAcc(List list, ExcelStyle excelstyle) {

		DynaBean record;
		activeSheet(0);
		this.curStyle = excelstyle.defaultStyle(this.workbook);

		int r = 2; //行索引
		logger.info("得到list");
		if (list != null) {
			Iterator iter = list.iterator();
			logger.info("开始循环写入数据");
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("numerical") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("numerical").toString());
				}
				if (record.get("name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("name").toString());
				}

				if (record.get("address") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("address").toString());
				}

				if (record.get("essetime") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("essetime").toString());
				}

				if (record.get("audittime") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("audittime").toString());
				}

				if (record.get("auditresult") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("auditresult").toString());
				}

				if (record.get("isarchive") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("isarchive").toString());
				}

				r++; //下一行
			}
			logger.info("成功写入");
		}
	}
}
