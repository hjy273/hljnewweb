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
 * 负责excel相关操作的工具类，提供能够如下：加载excel，激活sheet，获得激活sheet等。
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
			logger.error("文件没有找到！");
			logger.error(e1.getStackTrace());
		} catch (IOException e1) {
			logger.error("读取 InputStream 流时发生错误！具体信息："+e1.getMessage());
			logger.error(e1.getStackTrace());
		}
	
	}
	public ExcelUtil(InputStream in){
		try {
			workBook = new HSSFWorkbook(in);
		} catch (IOException e) {
			logger.error("读取 InputStream 流时发生错误！具体信息："+e.getMessage());
			logger.error(e.getStackTrace());
		}
	}
	/**
	 * 通过index激活sheet
	 * @param index 
	 */
	public void activeSheet(int index){
		this.currentSheet = workBook.getSheetAt(index);
	}
	/**
	 * 通过名称激活sheet
	 * @param sheetName
	 */
	public void activeSheet(String sheetName){
		this.currentSheet = workBook.getSheet(sheetName);
	}
	/**
	 * 获得当前激活的sheet
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
	 * 获得当前行的数据
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
				//cellValue = String.valueOf(v);//这样使用有时会出现问题如：0.1025转换后为0.10250000000000001
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
		logger.info("cell值:"+cellValue);
		return cellValue;
	}
	/**
	 * 验证指定行的单元格是否为空
	 * @param rank 验证列数
	 * @param row  验证行
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
	 * 设置单元格的值，及样式
	 * @param index 单元格的列
	 * @param value 值
	 * @param row    行
	 * @param style  样式
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
