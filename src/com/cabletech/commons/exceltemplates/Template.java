package com.cabletech.commons.exceltemplates;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

public abstract class Template implements ExportInterface {
    protected HSSFWorkbook workbook = null;

    protected HSSFSheet curSheet = null;

    protected HSSFRow curRow = null;

    protected HSSFCellStyle curStyle = null;

    public Template(String urlPath) {
        readTemplate(urlPath);
    }

    private void readTemplate(String infile) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(infile));
            workbook = new HSSFWorkbook(fs);
            curStyle = workbook.createCellStyle();
        } catch (Exception e) {
        	System.out.println("出现异常:"+e.getMessage());
            this.workbook = null;
            this.curSheet = null;
            this.curRow = null;
            this.curStyle = null;
            // System.out.println( "template error:" + e.getMessage() );
        }
    }

    public void doExport(List list) {
        //
    };

    public boolean activeSheet(int sheetIndex) {
        this.curSheet = this.workbook.getSheetAt(sheetIndex);
        return (this.curSheet != null);
    }

    public boolean activeSheet(String sheetName) {
        this.curSheet = this.workbook.getSheet(sheetName);
        return (this.curSheet != null);
    }

    public void activeRow(int rowIdx) {
        if (curRow != null) {
            if (curRow.getRowNum() != rowIdx) {
                curRow = curSheet.createRow(rowIdx);
            }

        } else {
            curRow = curSheet.createRow(rowIdx);
        }

    }

    public void activeRowNew(int rowIdx) {
        if (curRow != null) {
            if (curRow.getRowNum() != rowIdx) {
                System.out
                        .println("curRow.getRowNum():" + curRow.getRowNum() + ",rowIdx:" + rowIdx);
                curRow = curSheet.createRow(rowIdx);
            }

        } else {
            curRow = curSheet.createRow(rowIdx);
        }

    }

    protected void setCellValue(int colIdx, String value) {
        if (curRow != null) {
            HSSFCell cell = curRow.getCell(colIdx);
            if (cell == null) {
                cell = curRow.createCell(colIdx);
            }
//            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            if (curStyle != null) {
                cell.setCellStyle(curStyle);
            }
            if (value != null) {
                cell.setCellValue(value);

            } else {
                cell.setCellValue("");
            }
        }

    }
    /**
     * 设置合并单元格样式
     * @param rowFrom 开始行
     * @param rowTo  结束行
     * @param colFrom 开始列
     * @param colTo 结束列
     * @param style 合并单元格样式
     */
    protected void setRegionStyle(int rowFrom,int rowTo,int colFrom,int colTo,HSSFCellStyle style){
        for(int i=rowFrom;i<=rowTo;i++){
            HSSFRow row_temp = this.curRow;
            for(int j=colFrom;j<=colTo;j++){
                HSSFCell cell_temp = row_temp.getCell(j);
                if(cell_temp ==null ){
                    cell_temp = row_temp.createCell(j);
                }
                cell_temp.setCellStyle(style);
            }
        }

    }
    /**
     * 单元格合并
     * @param rowFrom 开始行
     * @param rowTo	  结束行
     * @param colFrom 开始列
     * @param colTo 结束列
     */
    protected void addMergedRegion(int rowFrom,int rowTo ,int colFrom, int colTo){
    	CellRangeAddress cellRange = new CellRangeAddress(rowFrom,rowTo ,colFrom, colTo);
    	curSheet.addMergedRegion(cellRange);
    }

    protected void setCellValue(int colIdx, String value, HSSFCellStyle style) {
        if (curRow != null) {
            HSSFCell cell = curRow.getCell((short) colIdx);
            if (cell == null) {
                cell = curRow.createCell((short) colIdx);
            }
//            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
            
            if (value != null) {
                cell.setCellValue(value);

            } else {
                cell.setCellValue("");
            }
            if (style != null) {
                cell.setCellStyle(style);
            } else {
                if (curStyle != null) {
                    cell.setCellStyle(curStyle);
                }
            }
        }

    }

    protected void setCellValue(int colIdx, int rowIdx, String value) {

        this.activeRow(rowIdx);
        this.setCellValue(colIdx, value);
    }

    protected String getCellValue(int colIdx) {
        double numValue;
        if (curRow == null) {
            return "";
        }
        HSSFCell cell = curRow.getCell((short) colIdx);
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case HSSFCell.CELL_TYPE_NUMERIC:
            numValue = cell.getNumericCellValue();
            return String.valueOf(numValue);
        default:
            return "";

        }

    }

    protected HSSFFont createFont(int fontSize, String fontFamily, boolean isItalic,
            boolean isStrikeout, int colorIndex) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontSize);
        font.setFontName(fontFamily);
        font.setItalic(isItalic);
        font.setStrikeout(isStrikeout);
        font.setColor((short) colorIndex);
        return font;
    }

    protected String getCellValue(int colIdx, int rowIdx) {
        this.activeRow(rowIdx);
        return getCellValue(colIdx);
    }

    public void write(OutputStream out) throws IOException {

        try {
            workbook.write(out);
        } finally {
            out.close(); // 确保out能被关闭
        }
    }
    
    protected HSSFSheet cloneSheet(String sheetname ) {
    	//workbook.createSheet(sheetname);
    	HSSFSheet sheet = workbook.cloneSheet(0);
    	
    	try{
    		workbook.setSheetName(workbook.getNumberOfSheets() - 1, sheetname);
    		activeSheet(sheetname);
    	} catch(IllegalArgumentException e) {
    		// 名称已重复存在的情况
    		workbook.setSheetName(workbook.getNumberOfSheets() - 1, sheetname + "_2");
    		activeSheet(sheetname + "_2");
    	}
    	
    	return sheet;
    }
    
    /**
     * 
     * @param index
     * @param sheetname
     * @return
     */
    public boolean activeSheet(int index, String sheetname) {
        this.curSheet = this.workbook.getSheetAt(index);
   
        workbook.setSheetName(0, sheetname);
        return (this.curSheet != null);
    }   
    
    public void removeSheet(int index) {
    	workbook.removeSheetAt(index);
    }
}
