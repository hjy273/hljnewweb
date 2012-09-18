package com.cabletech.linepatrol.drill.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class DrillTemplates extends Template {

	public DrillTemplates(String urlPath) {
		super(urlPath);
	}

	public void doExportDrillQuery(List list, ExcelStyle excelstyle){
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // ÐÐË÷Òý
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("task_name") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("task_name").toString());
				}
				if (record.get("drill_level") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("drill_level").toString());
				}
				if (record.get("task_begintime") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("task_begintime").toString());
				}
				if (record.get("task_endtime") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("task_endtime").toString());
				}
				if (record.get("plan_person_number") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("plan_person_number").toString());
				}
				if (record.get("plan_car_number") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("plan_car_number").toString());
				}
				if (record.get("plan_equipment_number") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("plan_equipment_number").toString());
				}
				if (record.get("summary_person_number") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("summary_person_number").toString());
				}
				if (record.get("summary_car_number") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("summary_car_number").toString());
				}
				if (record.get("sum_equipment_number") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("sum_equipment_number").toString());
				}
				if (record.get("createtime") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("createtime").toString());
				}
				r++;
			}
		}
	}
}
