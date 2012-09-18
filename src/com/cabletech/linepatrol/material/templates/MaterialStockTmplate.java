package com.cabletech.linepatrol.material.templates;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;

public class MaterialStockTmplate extends Template {
	private static Logger logger = Logger.getLogger("Template");

	public MaterialStockTmplate(String urlPath) {
		super(urlPath);
	}

	/**
	 * �������Ͽ����Ϣ
	 * 
	 * @param list
	 * @param excelstyle
	 */
	public void doExportStockByMob(List list, ExcelStyle excelstyle,
			String news, String olds, String sum) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 0; // ������
		if (list != null && list.size() > 0) {
			activeRow(r);
			this.curStyle = rowLineStyle(this.workbook);
			setCellValue(0, "��ά����");
			setCellValue(1, "��������");
			setCellValue(2, "�������");
			setCellValue(3, "��������");
			setCellValue(4, "��ŵ�ַ");
			setCellValue(5, "�������");
			setCellValue(6, "���ɿ��");
			setCellValue(7, "�ϼ�");
			r++;
			Iterator iter = list.iterator();
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("contractorname") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("contractorname").toString());
				}
				if (record.get("typename") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("typename").toString());
				}
				if (record.get("modelname") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("modelname").toString());
				}

				if (record.get("name") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("name").toString());
				}

				if (record.get("address") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("address").toString());
				}
				if (record.get("newstock") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("newstock").toString());
				}
				if (record.get("oldstock") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("oldstock").toString());
				}
				if (record.get("sum") == null) {
					setCellValue(7, "");
				} else {
					setCellValue(7, record.get("sum").toString());
				}
				r++;
			}

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			// ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) )
			// ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
			setCellValue(0, "�ܼƣ�    �������: " + news + "      ���ɿ��: " + olds
					+ "     �ϼ�: " + sum);
			setCellValue(1, "");
			setCellValue(2, "");
			setCellValue(3, "");
			setCellValue(4, "");
			setCellValue(5, "");
			setCellValue(6, "");
			setCellValue(7, "");
			this.curRow.setHeight((short) 450);
			this.curSheet
					.addMergedRegion(new Region(r, (short) 0, r, (short) 7));
		}
	}

	/**
	 * �������Ͽ����Ϣ
	 * 
	 * @param list
	 * @param excelstyle
	 */
	public void doExportStockByCon(List list, ExcelStyle excelstyle,
			String news, String olds, String sum) {
		DynaBean record;
		activeSheet(0);
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		int r = 0; // ������
		if (list != null && list.size() > 0) {
			activeRow(r);
			this.curStyle = rowLineStyle(this.workbook);
			setCellValue(0, "��������");
			setCellValue(1, "�������");
			setCellValue(2, "��������");
			setCellValue(3, "��ŵ�ַ");
			setCellValue(4, "�������");
			setCellValue(5, "���ɿ��");
			setCellValue(6, "�ϼ�");
			r++;
			Iterator iter = list.iterator();
			super.curStyle = excelstyle.defaultStyle(super.workbook);
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("typename") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("typename").toString());
				}
				if (record.get("modelname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("modelname").toString());
				}

				if (record.get("name") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("name").toString());
				}

				if (record.get("address") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("address").toString());
				}
				if (record.get("newstock") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("newstock").toString());
				}
				if (record.get("oldstock") == null) {
					setCellValue(5, "");
				} else {
					setCellValue(5, record.get("oldstock").toString());
				}
				if (record.get("sum") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("sum").toString());
				}
				r++;
			}

			super.curStyle = excelstyle.defaultStyle(super.workbook);
			activeRow(r);
			// ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) )
			// ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
			setCellValue(0, "�ܼƣ�    �������: " + news + "      ���ɿ��: " + olds
					+ "     �ϼ�: " + sum);
			setCellValue(1, "");
			setCellValue(2, "");
			setCellValue(3, "");
			setCellValue(4, "");
			setCellValue(5, "");
			setCellValue(6, "");
			this.curRow.setHeight((short) 450);
			this.curSheet
					.addMergedRegion(new Region(r, (short) 0, r, (short) 6));

		}
	}

	public void doExportMaterialStockRecord(Map map, ExcelStyle excelstyle,
			String useType) {
		// TODO Auto-generated method stub
		DynaBean record;
		super.curStyle = excelstyle.defaultStyle(super.workbook);
		List list = new ArrayList();
		if (!"1".equals(useType)) {
			activeSheet(0);
			list = (List) map.get("record_in_list");
		}
		doExportRecord(list);
		if (!"0".equals(useType)) {
			activeSheet(1);
			list = (List) map.get("record_out_list");
		}
		doExportRecord(list);
		if ("1".equals(useType)) {
			super.removeSheet(0);
		}
		if ("0".equals(useType)) {
			super.removeSheet(1);
		}
	}

	private void doExportRecord(List list) {
		int r = 2; // ������
		DynaBean record;
		if (list != null && list.size() > 0) {
			activeRow(r);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				record = (DynaBean) iter.next();
				activeRow(r);
				if (record.get("material_name") == null) {
					setCellValue(0, "");
				} else {
					setCellValue(0, record.get("material_name").toString());
				}
				if (record.get("contractorname") == null) {
					setCellValue(1, "");
				} else {
					setCellValue(1, record.get("contractorname").toString());
				}

				if (record.get("address") == null) {
					setCellValue(2, "");
				} else {
					setCellValue(2, record.get("address").toString());
				}

				if (record.get("storage_type_name") == null) {
					setCellValue(3, "");
				} else {
					setCellValue(3, record.get("storage_type_name").toString());
				}
				if (record.get("record_date_string") == null) {
					setCellValue(4, "");
				} else {
					setCellValue(4, record.get("record_date_string").toString());
				}
				if (record.get("count") == null) {
					setCellValue(5, "0");
				} else {
					setCellValue(5, ((BigDecimal) record.get("count"))
							.toString());
				}
				if (record.get("source") == null) {
					setCellValue(6, "");
				} else {
					setCellValue(6, record.get("source").toString());
				}
				r++;
			}
		}
	}

	// 10�����壬���߿򣬴�ֱ����
	public HSSFCellStyle twFourLineCenter(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont(); // ��������
		font.setFontName("����");
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight((short) 1000000);
		style.setFont(font);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		return style;

	}

	/**
	 * ��������ʽ
	 * 
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle rowLineStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		this.curRow.setHeight((short) 600);
		this.curSheet.setDefaultColumnWidth((short) 15);
		font.setFontHeightInPoints((short) 10);
		font.setFontName("����");
		font.setItalic(false);
		font.setStrikeout(false);
		font.setBoldweight((short) 1000000);
		style.setFont(font);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderRight((short) 1);
		style.setWrapText(true);
		return style;
	}

}
