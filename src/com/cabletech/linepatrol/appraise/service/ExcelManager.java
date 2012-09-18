package com.cabletech.linepatrol.appraise.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.exceltemplates.ExcelUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseTableBean;
import com.cabletech.linepatrol.appraise.module.AppraiseContent;
import com.cabletech.linepatrol.appraise.module.AppraiseItem;
import com.cabletech.linepatrol.appraise.module.AppraiseRule;
import com.cabletech.linepatrol.appraise.module.AppraiseTable;

public class ExcelManager {
	private HSSFSheet sheet = null;
	private ExcelUtil excelUtil = null;

	public ExcelManager(InputStream in) {
		initSheet(in);
	}

	/**
	 * 将excel表放在appraiseTable对象中
	 * @param in 
	 * @param appraiseTableBean
	 * @return
	 */
	public AppraiseTable inputAppraiseTableFormExcel(AppraiseTableBean appraiseTableBean) throws Exception{
		AppraiseTable appraiseTable = new AppraiseTable();
		String tableName = getCellValueString(sheet.getRow(0).getCell(0))+"（"+appraiseTableBean.getTitleTime()+"）";
		initTable(appraiseTableBean, tableName, appraiseTable);
		setTable(sheet, appraiseTable);
		return appraiseTable;
	}
	/**
	 * 初始化sheet
	 * @param in
	 */
	public void initSheet(InputStream in) {
		excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//激活当前工作表
		sheet = excelUtil.getCurrentSheet(); //获得工作表
	}
	/**
	 * 格式验证
	 * @return
	 */
	public boolean checkFormat() {
		HSSFRow row = sheet.getRow(4);
		short lastNum = row.getLastCellNum();
		System.out.println("lastNum:" + lastNum);
		if (lastNum == 8) {

			String value = excelUtil.getCellValue(row, 0);
			if (!value.equals("项目")) {
				return false;
			}
			value = excelUtil.getCellValue(row, 2);
			if (!value.equals("内容")) {
				return false;
			}
			value = excelUtil.getCellValue(row, 4);
			if (!value.equals("细则")) {
				return false;
			}
			// TODO :验证内容是否为空。
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 初始化表的除items以外的全部属性
	 * @param appraiseTableBean
	 * @param tableName
	 * @param appraiseTable
	 */
	private void initTable(AppraiseTableBean appraiseTableBean, String tableName, AppraiseTable appraiseTable) {
		BeanUtil.copyProperties(appraiseTableBean, appraiseTable);
		appraiseTable.setTableName(tableName);
	}

	/**
	 * 将item放入到table中
	 * @param sheet
	 * @param appraiseTable
	 */
	private void setTable(HSSFSheet sheet, AppraiseTable appraiseTable) throws Exception{
		List<CellRangeAddress> cellRange = new ArrayList<CellRangeAddress>();
		getAllCellRangeAddress(sheet, cellRange);
		for (int firstRow = 5; firstRow < sheet.getPhysicalNumberOfRows(); firstRow++) {
			if (getCellValueString(sheet.getRow(firstRow).getCell(0)).equals("合计")) {
				break;
			}
			AppraiseItem appraiseItem = new AppraiseItem();
			initItem(sheet, appraiseTable, firstRow, appraiseItem);
			boolean flag = true;
			for (CellRangeAddress cellRangeAddress : cellRange) {
				//由于从表中取出的合并单元格的顺序是随机的，所以在使用的时候只能是用遍历的方法来比较单元格的起始位置是否符合
				if (cellRangeAddress.getFirstRow() == firstRow && cellRangeAddress.getFirstColumn() == 0) {
					firstRow = setMulItem(sheet, cellRange, appraiseItem, cellRangeAddress);
					flag = false;
					break;
				}
			}
			if (flag) {
				setSingleItem(sheet, cellRange, firstRow, appraiseItem);
			}
			appraiseTable.addAppraiseItem(appraiseItem);
		}
	}

	/**
	 * 添加多行的item
	 * @param sheet
	 * @param cellRange
	 * @param appraiseItem
	 * @param cellRangeAddress
	 * @return
	 */
	private int setMulItem(HSSFSheet sheet, List<CellRangeAddress> cellRange, AppraiseItem appraiseItem,
			CellRangeAddress cellRangeAddress) {
		int firstRow;
		firstRow = cellRangeAddress.getLastRow();
		cellRange.remove(cellRangeAddress);
		setContent(sheet, cellRangeAddress, cellRange, appraiseItem);
		return firstRow;
	}
	/**
	 * 添加单行的item
	 * @param sheet
	 * @param cellRange
	 * @param firstRow
	 * @param appraiseItem
	 */
	private void setSingleItem(HSSFSheet sheet, List<CellRangeAddress> cellRange, int firstRow,
			AppraiseItem appraiseItem) {
		CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, firstRow, 0, 0);
		setContent(sheet, cellRangeAddress, cellRange, appraiseItem);
	}

	/**
	 * 初始化item中除contents以外的全部属性
	 * @param sheet
	 * @param appraiseTable
	 * @param firstRow
	 * @param appraiseItem
	 */
	private void initItem(HSSFSheet sheet, AppraiseTable appraiseTable, int firstRow, AppraiseItem appraiseItem) throws Exception{
		appraiseItem.setAppraiseTable(appraiseTable);
		if(getCellValueString(sheet.getRow(firstRow).getCell(0)).equals("")){
			throw new Exception();
		}
		appraiseItem.setItemName(getCellValueString(sheet.getRow(firstRow).getCell(0)));
		appraiseItem.setWeight(getCellValueInteger(sheet.getRow(firstRow).getCell(1)));
	}

	/**
	 * 获得表中所有的合并单元格
	 * @param sheet
	 * @param cellRange
	 */
	private void getAllCellRangeAddress(HSSFSheet sheet, List<CellRangeAddress> cellRange) {
		//sheet.getNumMergedRegions()是获得所有的合并单元格的数目
		for (int r = 0; r < sheet.getNumMergedRegions(); r++) {
			//将合并单元格存到sheet.getMergedRegion(r)取出的格式为CellRangeAddress
			cellRange.add(sheet.getMergedRegion(r));
		}
	}

	/**
	 * 将contents放入到item中
	 * @param sheet
	 * @param cellRangeAddress
	 * @param cellRange
	 * @param appraiseItem
	 */
	private void setContent(HSSFSheet sheet, CellRangeAddress cellRangeAddress, List<CellRangeAddress> cellRange,
			AppraiseItem appraiseItem) {
		for (int firstRow = cellRangeAddress.getFirstRow(); firstRow <= cellRangeAddress.getLastRow(); firstRow++) {
			AppraiseContent appraiseContent = new AppraiseContent();
			initContent(sheet, appraiseItem, firstRow, appraiseContent);
			boolean flag = true;
			for (CellRangeAddress cellRangeAddressContent : cellRange) {
				if (cellRangeAddressContent.getFirstRow() == firstRow && cellRangeAddressContent.getFirstColumn() == 2) {
					firstRow = setMulContent(sheet, cellRange, appraiseContent, cellRangeAddressContent);
					flag = false;
					break;
				}
			}
			if (flag) {
				setSingleContent(sheet, firstRow, appraiseContent);
			}
		}
	}

	/**
	 * 添加单行的content
	 * @param sheet
	 * @param firstRow
	 * @param appraiseContent
	 */
	private void setSingleContent(HSSFSheet sheet, int firstRow, AppraiseContent appraiseContent) {
		CellRangeAddress cellRangeAddressContent = new CellRangeAddress(firstRow, firstRow, 0, 0);
		setRule(sheet, cellRangeAddressContent, appraiseContent);
	}

	/**
	 * 添加多行的content
	 * @param sheet
	 * @param cellRange
	 * @param appraiseContent
	 * @param cellRangeAddressContent
	 * @return
	 */
	private int setMulContent(HSSFSheet sheet, List<CellRangeAddress> cellRange, AppraiseContent appraiseContent,
			CellRangeAddress cellRangeAddressContent) {
		int firstRow;
		firstRow = cellRangeAddressContent.getLastRow();
		cellRange.remove(cellRangeAddressContent);
		setRule(sheet, cellRangeAddressContent, appraiseContent);
		return firstRow;
	}

	/**
	 * 初始化content中除rules以外的所有属性
	 * @param sheet
	 * @param appraiseItem
	 * @param firstRow
	 * @param appraiseContent
	 */
	private void initContent(HSSFSheet sheet, AppraiseItem appraiseItem, int firstRow, AppraiseContent appraiseContent) {
		appraiseContent.setAppraiseItem(appraiseItem);
		appraiseContent.setContentDescription(getCellValueString(sheet.getRow(firstRow).getCell(2)));
		appraiseContent.setWeight(getCellValueInteger(sheet.getRow(firstRow).getCell(3)));
		appraiseItem.addAppraiseContent(appraiseContent);
	}

	/**
	 * 将rules放入到content中
	 * @param sheet
	 * @param cellRangeAddress
	 * @param appraiseContent
	 */
	private void setRule(HSSFSheet sheet, CellRangeAddress cellRangeAddress, AppraiseContent appraiseContent) {
		for (int firstRow = cellRangeAddress.getFirstRow(); firstRow <= cellRangeAddress.getLastRow(); firstRow++) {
			AppraiseRule appraiseRule = new AppraiseRule();
			initRule(sheet, appraiseContent, firstRow, appraiseRule);
		}
	}

	/**
	 * 初始化rule
	 * @param sheet
	 * @param appraiseContent
	 * @param firstRow
	 * @param appraiseRule
	 */
	private void initRule(HSSFSheet sheet, AppraiseContent appraiseContent, int firstRow, AppraiseRule appraiseRule) {
		appraiseRule.setAppraiseContent(appraiseContent);
		appraiseRule.setRuleDescription(getCellValueString(sheet.getRow(firstRow).getCell(4)));
		appraiseRule.setWeight(getCellValueInteger(sheet.getRow(firstRow).getCell(5)));
		appraiseRule.setGradeDescription(getCellValueString(sheet.getRow(firstRow).getCell(7)));
		appraiseContent.addAppraiseRule(appraiseRule);
	}
	private String getCellValueString(HSSFCell hssfCell){
		String obj="";
		int cellType=hssfCell.getCellType();
		if(cellType==1){
			 obj=String.valueOf(hssfCell.getStringCellValue());
		} else if (cellType == 2||cellType==0) {
			 obj = String.valueOf(hssfCell.getNumericCellValue());
		} else if (cellType == 3) {
            obj = hssfCell.getStringCellValue();
        }
		return obj;
	}
	private int getCellValueInteger(HSSFCell hssfCell){
		int obj=0;
		int cellType=hssfCell.getCellType();
		if(cellType==1||cellType == 3){
			 obj=Integer.valueOf(hssfCell.getStringCellValue());
		} else if (cellType == 2||cellType==0) {
			 obj =(int) (hssfCell.getNumericCellValue());
		} 
		return obj;
	}
}
