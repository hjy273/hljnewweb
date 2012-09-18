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
	 * ��excel�����appraiseTable������
	 * @param in 
	 * @param appraiseTableBean
	 * @return
	 */
	public AppraiseTable inputAppraiseTableFormExcel(AppraiseTableBean appraiseTableBean) throws Exception{
		AppraiseTable appraiseTable = new AppraiseTable();
		String tableName = getCellValueString(sheet.getRow(0).getCell(0))+"��"+appraiseTableBean.getTitleTime()+"��";
		initTable(appraiseTableBean, tableName, appraiseTable);
		setTable(sheet, appraiseTable);
		return appraiseTable;
	}
	/**
	 * ��ʼ��sheet
	 * @param in
	 */
	public void initSheet(InputStream in) {
		excelUtil = new ExcelUtil(in);
		excelUtil.activeSheet(0);//���ǰ������
		sheet = excelUtil.getCurrentSheet(); //��ù�����
	}
	/**
	 * ��ʽ��֤
	 * @return
	 */
	public boolean checkFormat() {
		HSSFRow row = sheet.getRow(4);
		short lastNum = row.getLastCellNum();
		System.out.println("lastNum:" + lastNum);
		if (lastNum == 8) {

			String value = excelUtil.getCellValue(row, 0);
			if (!value.equals("��Ŀ")) {
				return false;
			}
			value = excelUtil.getCellValue(row, 2);
			if (!value.equals("����")) {
				return false;
			}
			value = excelUtil.getCellValue(row, 4);
			if (!value.equals("ϸ��")) {
				return false;
			}
			// TODO :��֤�����Ƿ�Ϊ�ա�
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * ��ʼ����ĳ�items�����ȫ������
	 * @param appraiseTableBean
	 * @param tableName
	 * @param appraiseTable
	 */
	private void initTable(AppraiseTableBean appraiseTableBean, String tableName, AppraiseTable appraiseTable) {
		BeanUtil.copyProperties(appraiseTableBean, appraiseTable);
		appraiseTable.setTableName(tableName);
	}

	/**
	 * ��item���뵽table��
	 * @param sheet
	 * @param appraiseTable
	 */
	private void setTable(HSSFSheet sheet, AppraiseTable appraiseTable) throws Exception{
		List<CellRangeAddress> cellRange = new ArrayList<CellRangeAddress>();
		getAllCellRangeAddress(sheet, cellRange);
		for (int firstRow = 5; firstRow < sheet.getPhysicalNumberOfRows(); firstRow++) {
			if (getCellValueString(sheet.getRow(firstRow).getCell(0)).equals("�ϼ�")) {
				break;
			}
			AppraiseItem appraiseItem = new AppraiseItem();
			initItem(sheet, appraiseTable, firstRow, appraiseItem);
			boolean flag = true;
			for (CellRangeAddress cellRangeAddress : cellRange) {
				//���ڴӱ���ȡ���ĺϲ���Ԫ���˳��������ģ�������ʹ�õ�ʱ��ֻ�����ñ����ķ������Ƚϵ�Ԫ�����ʼλ���Ƿ����
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
	 * ��Ӷ��е�item
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
	 * ��ӵ��е�item
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
	 * ��ʼ��item�г�contents�����ȫ������
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
	 * ��ñ������еĺϲ���Ԫ��
	 * @param sheet
	 * @param cellRange
	 */
	private void getAllCellRangeAddress(HSSFSheet sheet, List<CellRangeAddress> cellRange) {
		//sheet.getNumMergedRegions()�ǻ�����еĺϲ���Ԫ�����Ŀ
		for (int r = 0; r < sheet.getNumMergedRegions(); r++) {
			//���ϲ���Ԫ��浽sheet.getMergedRegion(r)ȡ���ĸ�ʽΪCellRangeAddress
			cellRange.add(sheet.getMergedRegion(r));
		}
	}

	/**
	 * ��contents���뵽item��
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
	 * ��ӵ��е�content
	 * @param sheet
	 * @param firstRow
	 * @param appraiseContent
	 */
	private void setSingleContent(HSSFSheet sheet, int firstRow, AppraiseContent appraiseContent) {
		CellRangeAddress cellRangeAddressContent = new CellRangeAddress(firstRow, firstRow, 0, 0);
		setRule(sheet, cellRangeAddressContent, appraiseContent);
	}

	/**
	 * ��Ӷ��е�content
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
	 * ��ʼ��content�г�rules�������������
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
	 * ��rules���뵽content��
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
	 * ��ʼ��rule
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
