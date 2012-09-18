package com.cabletech.commons.exceltemplates;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/**
 * ����excel��ز����Ĺ����࣬�ṩ�ܹ����£�����excel������sheet����ü���sheet�ȡ�
 * @author simon_zhang
 *
 */
public class ExcelUtil {
	
	private Logger logger = Logger.getLogger(ExcelUtil.class);
	private HSSFWorkbook workBook;
	private HSSFSheet currentSheet;
	
	public ExcelUtil(String inFile){
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(inFile));
			workBook = new HSSFWorkbook(fs);
		} catch (FileNotFoundException e1) {
			logger.error("�ļ�û���ҵ���");
			logger.error(e1.getStackTrace());
		} catch (IOException e1) {
			logger.error("��ȡ InputStream ��ʱ�������󣡾�����Ϣ��"+e1.getMessage());
			logger.error(e1.getStackTrace());
		}
	
	}
	public ExcelUtil(InputStream in){
		try {
			workBook = new HSSFWorkbook(in);
		} catch (IOException e) {
			logger.error("��ȡ InputStream ��ʱ�������󣡾�����Ϣ��"+e.getMessage());
			logger.error(e.getStackTrace());
		}
	}
	/**
	 * ͨ��index����sheet
	 * @param index 
	 */
	public void activeSheet(int index){
		this.currentSheet = workBook.getSheetAt(index);
	}
	/**
	 * ͨ�����Ƽ���sheet
	 * @param sheetName
	 */
	public void activeSheet(String sheetName){
		this.currentSheet = workBook.getSheet(sheetName);
	}
	/**
	 * ��õ�ǰ�����sheet
	 * @return
	 */
	public HSSFSheet getCurrentSheet() {
		return currentSheet;
	}
	
	public String getCellValue(HSSFRow row, int i){
		HSSFCell  cell = row.getCell(i);
		return getCellStringValue(cell);
	}
	/**
	 * ��õ�ǰ�е�����
	 * @param cell
	 * @return
	 */
	public String getCellStringValue(HSSFCell cell) {
		String cellValue = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().toString();
			if(cellValue.trim().equals("")||cellValue.trim().length()<=0){
				cellValue="";
			}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellValue = String.valueOf(cell.getNumericCellValue());
			if (HSSFDateUtil.isCellDateFormatted(cell)) {  
				Date date = cell.getDateCellValue();  
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd"); 
				cellValue = formater.format(date);
			} else {  
				double v = cell.getNumericCellValue();
				NumberFormat formatter = new DecimalFormat("#0.000");
				cellValue = formatter.format(v);
				//cellValue = String.valueOf(v);//����ʹ����ʱ����������磺0.1025ת����Ϊ0.10250000000000001
			}  
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue="";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		logger.info("cellֵ:"+cellValue);
		return cellValue;
	}
	/**
	 * ��ָ֤���еĵ�Ԫ���Ƿ�Ϊ��
	 * @param rank ��֤����
	 * @param row  ��֤��
	 * @return
	 */
	public boolean validateIsNull(int rank,HSSFRow row){
		boolean flag = true;
		for(int j=0;j<rank;j++){
			HSSFCell cell = row.getCell((short)j);
			if(cell == null){
				flag = false;
				break;
			}
			String ss = null;
			ss = getCellStringValue(cell);
			if(StringUtils.isBlank(ss) || ss.equals("&nbsp;")){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * ���õ�Ԫ���ֵ������ʽ
	 * @param index ��Ԫ�����
	 * @param value ֵ
	 * @param row    ��
	 * @param style  ��ʽ
	 */
	public void setCellStringValue(int index, String value, HSSFRow row, HSSFCellStyle style){
		HSSFCell cell = row.createCell((short)index);
		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
	}
	public HSSFWorkbook getWorkBook() {
		return workBook;
	}
}
