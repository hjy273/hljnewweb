package com.cabletech.linepatrol.safeguard.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class SafeguardTemplates extends Template {

	public SafeguardTemplates(String urlPath) {
		super(urlPath);
	}

	public void doExportSafeguardQuery(List list, ExcelStyle excelstyle){
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // ÐÐË÷Òý
		if (list != null && list.size() > 0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("safeguard_name") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("safeguard_name").toString());
				}
				if (record.get("contractorname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("contractorname").toString());
				}
				if (record.get("start_date") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("start_date").toString());
				}
				if (record.get("end_date") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("end_date").toString());
				}
				if (record.get("safeguard_level") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("safeguard_level").toString());
				}
				if (record.get("transact_state") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("transact_state").toString());
				}
				if (record.get("send_date") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("send_date").toString());
				}
				r++;
			}
		}
	}
}
