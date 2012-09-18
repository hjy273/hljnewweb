package com.cabletech.linepatrol.appraise.template;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.Region;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;

public class AppraiseTemplate extends Template {
	int index = 0;

	public AppraiseTemplate(String urlPath) {
		super(urlPath);
	}

	public void doExportAppriaseTable(AppraiseTable table, ExcelStyle excelStyle) {
		activeSheet(0);
		super.curStyle = excelStyle.tenFourLineCenter(super.workbook);
		setTitleRow(table, excelStyle);
		setTimeRow(table, excelStyle);
		int r = 5;
		int tableSize = table.getAppraiseItems().size();
		r = setAppraiseItem(table, r, tableSize);
		setSumRow(table, r);
	}

	/**
	 * 添加模板名称
	 * @param table
	 * @param excelStyle
	 */
	void setTitleRow(AppraiseTable table, ExcelStyle excelStyle) {
		HSSFCellStyle titleStyle = excelStyle.tenFourLineCenter(super.workbook);
		setFont(titleStyle, 16);
		activeRow(0);
		setCellValue(0, table.getTableName(), titleStyle);
	}

	/**
	 * 添加模板时间一行
	 * @param table
	 * @param excelStyle
	 */
	void setTimeRow(AppraiseTable table, ExcelStyle excelStyle) {
		HSSFCellStyle timeStyle = excelStyle.tenFourLineCenter(super.workbook);
		setFont(timeStyle, 12);
		activeRow(2);
		setCellValue(0, table.getYear() + "年", timeStyle);
	}

	/**
	 * 添加评分项
	 * @param table
	 * @param r
	 * @param tableSize
	 * @return
	 */
	int setAppraiseItem(AppraiseTable table, int r, int tableSize) {
		for (int i = 0; i < tableSize; i++) {
			AppraiseItem item = table.getAppraiseItems().get(i);
			int itemSize = item.getItemSize();
			Region regionItemName = new Region(r, (short) 0, r + itemSize - 1, (short) 0);
			Region regionWeight = new Region(r, (short) 1, r + itemSize - 1, (short) 1);
			super.curSheet.addMergedRegion(regionItemName);
			super.curSheet.addMergedRegion(regionWeight);

			activeRow(r);
			setCellValue(0, item.getItemName());
			setCellValue(1, String.valueOf(item.getWeight()));

			r = setAppraiseContent(r, item);
		}
		return r;
	}

	/**
	 * 添加评分内容
	 * @param r
	 * @param item
	 * @return
	 */
	int setAppraiseContent(int r, AppraiseItem item) {
		for (int itemIndex = 0; itemIndex < item.getAppraiseContents().size(); itemIndex++) {
			AppraiseContent content = item.getAppraiseContents().get(itemIndex);
			int contentSize = content.getAppraiseRules().size();
			Region regionContentDescription = new Region(r, (short) 2, r + contentSize - 1, (short) 2);
			Region regionContentWeight = new Region(r, (short) 3, r + contentSize - 1, (short) 3);
			super.curSheet.addMergedRegion(regionContentDescription);
			super.curSheet.addMergedRegion(regionContentWeight);
			activeRow(r);
			setCellValue(2, content.getContentDescription());
			setCellValue(3, String.valueOf(content.getWeight()));

			r = setAppraiseRule(r, content);
		}
		return r;
	}

	/**
	 * 添加评分细则
	 * @param r
	 * @param content
	 * @return
	 */
	int setAppraiseRule(int r, AppraiseContent content) {
		for (AppraiseRule appraiseRule : content.getAppraiseRules()) {
			activeRow(r);
			setCellValue(4, appraiseRule.getRuleDescription());
			setCellValue(5, String.valueOf(appraiseRule.getWeight()));
			setCellValue(6, " ");
			setCellValue(7, appraiseRule.getGradeDescription());
			r++;
		}
		return r;
	}

	/**
	 * 添加合计一行
	 * @param table
	 * @param r
	 */
	void setSumRow(AppraiseTable table, int r) {
		activeRow(r);
		setCellValue(0, "合计");
		setCellValue(1, String.valueOf(table.getAllWeight().get("tableWeight")));
		setCellValue(2, "");
		setCellValue(3, String.valueOf(table.getAllWeight().get("itemWeight")));
		setCellValue(4, "");
		setCellValue(5, String.valueOf(table.getAllWeight().get("contentWeight")));
		setCellValue(6, "");
		setCellValue(7, "");
	}

	/**
	 * 修改字体大小
	 * @param style
	 * @param size
	 */
	void setFont(HSSFCellStyle style, int size) {
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) size);
		font.setFontName("宋体");
		font.setItalic(false);
		font.setStrikeout(false);
		style.setFont(font);
	}
}
