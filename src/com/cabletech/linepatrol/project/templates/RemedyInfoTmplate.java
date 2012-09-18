package com.cabletech.linepatrol.project.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

/**
 * 修缮部分内容的导出excel类
 * 
 * @author fjj
 * 
 */
public class RemedyInfoTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public RemedyInfoTmplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * 执行导出动作
	 * 
	 * @param list
	 *            List
	 * @param excelstyle
	 *            ExcelStyle
	 */
	public void doExportItem(List list, ExcelStyle excelstyle) {
		logger.info("导出修缮项目=========teplate===");
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("itemname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("itemname").toString());
				}
				if (record.get("regionname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("regionname").toString());
				}
				r++;
			}
		}
	}

	public void doExportType(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("typename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("typename").toString());
				}
				if (record.get("itemname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("itemname").toString());
				}
				if (record.get("price") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("price").toString());
				}
				if (record.get("unit") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("unit").toString());
				}
				r++;
			}
		}
	}

}
