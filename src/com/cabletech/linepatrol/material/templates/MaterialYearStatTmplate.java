package com.cabletech.linepatrol.material.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class MaterialYearStatTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public MaterialYearStatTmplate(String urlPath) {
		super(urlPath);
	}

/**
 * 导出材料使用年统计数据
 * @param list
 * @param excelstyle
 */
	public void doExportModel(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r =3; // 行索引
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("modelname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("modelname").toString());
				}
				if (record.get("typename") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("typename").toString());
				}
				if (record.get("unit") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("unit").toString());
				}
				/*if (record.get("unit") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("unit").toString());
				}*/
				setCellValue(3, 0+"");
				
				if (record.get("use_number") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("use_number").toString());
				}
				
				if (record.get("jan") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("jan").toString());
				}
				if (record.get("feb") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("feb").toString());
				}
				if (record.get("mar") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("mar").toString());
				}
				if (record.get("apr") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("apr").toString());
				}
				if (record.get("may") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("may").toString());
				}
				if (record.get("june") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("june").toString());
				}
				if (record.get("july") == null) {
					setCellValue(11, "");
				} else {
					setCellValue(11, record.get("july").toString());
				}
				if (record.get("aug") == null) {
					setCellValue(12, "");
				} else {
					setCellValue(12, record.get("aug").toString());
				}
				if (record.get("sep") == null) {
					setCellValue(13, "");
				} else {
					setCellValue(13, record.get("sep").toString());
				}
				if (record.get("oct") == null) {
					setCellValue(14, "");
				} else {
					setCellValue(14, record.get("oct").toString());
				}
				if (record.get("nov") == null) {
					setCellValue(15, "");
				} else {
					setCellValue(15, record.get("nov").toString());
				}
				if (record.get("dece") == null) {
					setCellValue(16, "");
				} else {
					setCellValue(16, record.get("dece").toString());
				}
				
				r++;
			}
		}
		}
	
	}
