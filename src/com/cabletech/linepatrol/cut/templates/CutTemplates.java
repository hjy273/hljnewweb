package com.cabletech.linepatrol.cut.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class CutTemplates extends Template {

	public CutTemplates(String urlPath) {
		super(urlPath);
	}

	/**
	 * 导出查询列表信息
	 * @param list
	 * @param excelstyle
	 */
	public void doExportCutQuery(List list, ExcelStyle excelstyle){
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("workorder_id") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("workorder_id").toString());
				}
				if (record.get("cut_name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("cut_name").toString());
				}
				if (record.get("cut_level") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("cut_level").toString());
				}
				if (record.get("contractorname") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("contractorname").toString());
				}
				if (record.get("apply_date") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("apply_date").toString());
				}
				if (record.get("feedback_hours") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("feedback_hours").toString());
				}
				if (record.get("username") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("username").toString());
				}
				if (record.get("cut_state") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("cut_state").toString());
				}
				r++;
			}
		}
	}
	
	/**
	 * 导出统计列表信息
	 * @param list
	 * @param excelstyle
	 */
	public void doExportCutStat(List list, ExcelStyle excelstyle){
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("cut_number") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("cut_number").toString());
				}
				if (record.get("total_budget") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("total_budget").toString());
				}
				if (record.get("total_time") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("total_time").toString());
				}
				if (record.get("total_bs") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("total_bs").toString());
				}
				r++;
			}
		}
	}
}
