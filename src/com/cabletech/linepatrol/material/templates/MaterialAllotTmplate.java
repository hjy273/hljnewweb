package com.cabletech.linepatrol.material.templates;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

/**
 * 材料调拨
 * @author fjj
 *
 */
public class MaterialAllotTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public MaterialAllotTmplate(String urlPath) {
		super(urlPath);
	}


	/**
	 * 导出材料调拨明细
	 * @param list
	 * @param excelstyle
	 */
	public void doExportAllotItemss(List list, ExcelStyle excelstyle) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("oldconname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("oldconname").toString());
				}
				if (record.get("oldaddr") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("oldaddr").toString());
				}
				if (record.get("basename") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("basename").toString());
				}

				if (record.get("changedate") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("changedate").toString());
				}

				if (record.get("newconname") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("newconname").toString());
				}
				if (record.get("newaddr") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("newaddr").toString());
				}
				if (record.get("newstock") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("newstock").toString());
				}
				if (record.get("oldstock") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("oldstock").toString());
				}
				r++;
			}
		}
	}


	/**
	 * 导出材料调拨单
	 * @param list
	 * @param excelstyle
	 */
	public void doExportAllots(List list, ExcelStyle excelstyle) {
		System.out.println("导出材料调拨单================= ");
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 2; // 行索引
		if(list != null && list.size()>0) {
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				Object id = record.get("id");
				Object username = record.get("username");
				Object changedate = record.get("changedate");
				if (id == null &&username== null &&changedate== null) {
					setCellValue(0, "");
				}else {
					String title = username+""+changedate+" 000"+id;
					setCellValue(0,title);
				}
				if (record.get("username") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("username").toString());
				}
				if (record.get("changedate") == null) {
					setCellValue(2, "");
				} else {setCellValue(2, "");
					setCellValue(2, record.get("changedate").toString());
				}

				if (record.get("remark") == null) {
					this.curRow.setHeight((short)420);
					setCellValue(3, "");
				} else {
					this.curRow.setHeight((short)420);
					setCellValue(3, record.get("remark").toString());
				}
				r++;
			}
		}
	}

}
