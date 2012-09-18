package com.cabletech.sparepartmanage.template;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class StorageTemplateInfo extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public StorageTemplateInfo(String urlPath) {
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
	public void doExport(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 3; // 行索引
		if(list != null) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("product_factory") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("product_factory").toString());
				}
				if (record.get("spare_part_name") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("spare_part_name").toString());
				}
				if (record.get("spare_part_model") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("spare_part_model").toString());
				}
				if (record.get("mobilenum") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("mobilenum").toString());
				}
				if (record.get("connum") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("connum").toString());
				}
				if (record.get("runningnum") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("runningnum").toString());
				}
				if (record.get("applyed") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("applyed").toString());
				}
				if (record.get("changed") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("changed").toString());
				}
				if (record.get("repairnum") == null) {
					setCellValue(8, "");
				} else {
					setCellValue(8, record.get("repairnum").toString());
				}
				if (record.get("scrapnum") == null) {
					setCellValue(9, "");
				} else {
					setCellValue(9, record.get("scrapnum").toString());
				}
				if (record.get("totalnum") == null) {
					setCellValue(10, "");
				} else {
					setCellValue(10, record.get("totalnum").toString());
				}
				r++;
			}
		}
		}


		/**
		 * 执行导出动作（）
		 * 
		 * @param list
		 *            List
		 * @param excelstyle
		 *            ExcelStyle
		 */
		public void doExportExcel(List list, ExcelStyle excelstyle) {
			DynaBean record;
			activeSheet(0);
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			int r = 3; // 行索引
			if(list != null) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					record = (DynaBean) iter.next();
					activeRow(r);
					if (record.get("product_factory") == null) {
						setCellValue(0, "");
					} else {
						setCellValue(0, record.get("product_factory").toString());
					}
					if (record.get("spare_part_name") == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, record.get("spare_part_name").toString());
					}
					if (record.get("spare_part_model") == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, record.get("spare_part_model").toString());
					}
					if (record.get("storage_number") == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, record.get("storage_number").toString());
					}
					r++;
				}
			}
		}
		
		/**
		 * 执行备件重新入库导出动作（）
		 * 
		 * @param list
		 *            List
		 * @param excelstyle
		 *            ExcelStyle
		 */
		public void doAgainStorageExport(List list, ExcelStyle excelstyle) {
			DynaBean record;
			activeSheet(0);
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			int r = 3; // 行索引
			if(list != null) {
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					record = (DynaBean) iter.next();
					activeRow(r);
					if (record.get("product_factory") == null) {
						setCellValue(0, "");
					} else {
						setCellValue(0, record.get("product_factory").toString());
					}
					if (record.get("spare_part_name") == null) {
						setCellValue(1, "");
					} else {
						setCellValue(1, record.get("spare_part_name").toString());
					}
					if (record.get("spare_part_model") == null) {
						setCellValue(2, "");
					} else {
						setCellValue(2, record.get("spare_part_model").toString());
					}
					if (record.get("name") == null) {
						setCellValue(3, "");
					} else {
						setCellValue(3, record.get("name").toString());
					}
					r++;
				}
			}
		}
		
	}
