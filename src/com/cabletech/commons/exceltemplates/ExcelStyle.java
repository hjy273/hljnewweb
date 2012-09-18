package com.cabletech.commons.exceltemplates;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.cabletech.baseinfo.domainobjects.UserInfo;

public class ExcelStyle extends Template{

    public ExcelStyle( String urlPath ) throws IOException{
        super( urlPath );
    }
    
//    public HSSFCellStyle cellStyle = null;
    
    public HSSFSheet setHeaderFooter( HSSFSheet sheet, UserInfo userinfo ){

        sheet.getHeader().setCenter( HSSFHeader.fontSize( ( short )9 ) + "北京鑫干线线路巡检系统" + "        日期：" + HSSFHeader.date() );
        sheet.getFooter().setCenter( HSSFHeader.fontSize( ( short )9 ) + "导出人：" + userinfo.getUserName() + "        第 "
            + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );

        return curSheet;
    }
    public static HSSFCellStyle leftright(HSSFCellStyle style){
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        return style;
    }

//单元格下边框线
    public HSSFCellStyle bottom( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格左边框线
    public HSSFCellStyle left( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格右边框线
    public HSSFCellStyle right( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        return style;
    }

//单元格上边框线
    public HSSFCellStyle top( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格左上边框线
    public HSSFCellStyle leftTop( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格左下边框线
    public HSSFCellStyle leftBottom( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        return style;
    }
    //单元格右下边框线
    public HSSFCellStyle rightBottom( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格边框线
    public HSSFCellStyle fourLine( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        return style;
    }

//单元格默认样式 字体9号，带边框线
    public HSSFCellStyle defaultStyle( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints( ( short )9 );
        font.setFontName( "宋体" );
        font.setItalic( false );
        font.setStrikeout( false );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setFont( font );
        style.setWrapText(true);
        return style;
    }
    //9号加粗宋体，带边框线，有背景色
    public HSSFCellStyle colorBoldFour( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )9 );
        font.setBoldweight( ( short )1000000 );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setFont( font );
        style.setFillBackgroundColor( HSSFColor.GREY_25_PERCENT.index ); //设置颜色
        style.setFillForegroundColor( HSSFColor.GREY_25_PERCENT.index );
        style.setFillPattern( HSSFCellStyle.ALIGN_FILL );
        return style;
    }

    //9号加粗宋体，带边框线
    public HSSFCellStyle boldFourLine( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )9 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );

        return style;
    }
    //10号加粗宋体，带边框线
    public HSSFCellStyle bold10FourLine ( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );

        return style;

    }

    //10号加粗字体，无边框,垂直左对齐
    public HSSFCellStyle tenBoldNoLine( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_LEFT );
        return style;

    }

    //10号字体，带边框，垂直左对齐
    public HSSFCellStyle tenFourLine ( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        HSSFFont font = workbook.createFont(); //定义字体
        
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_LEFT );
       
        return style;

    }


    public HSSFCellStyle tenBoldFourLine( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_LEFT );
        return style;

}

//10号字体，垂直左对齐
    public HSSFCellStyle tenNoLine( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        style.setFont( font );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_LEFT );
        return style;
    }

//10号字体，带边框，垂直居中
    public HSSFCellStyle tenFourLineCenter( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        style.setWrapText(true);
        return style;

    }


    public HSSFCellStyle tenBottom( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )10 );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );

        return style;
    }
    public HSSFCellStyle titleFont( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )18 );
        style.setFont( font );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        return style;
    }


    public HSSFCellStyle titleBoldFont( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont(); //定义字体
        font.setFontName( "宋体" );
        font.setFontHeightInPoints( ( short )18 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        return style;
    }

    //单元格默认样式 字体10号，带边框线
    public HSSFCellStyle defaultStyleTenFont( HSSFWorkbook workbook ){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints( ( short )10 );
        font.setFontName( "宋体" );
        font.setItalic( false );
        font.setStrikeout( false );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( HSSFColor.BLACK.index );
        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style.setLeftBorderColor( HSSFColor.BLACK.index );
        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style.setRightBorderColor( HSSFColor.BLACK.index );
        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setFont( font );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
        style.setAlignment( HSSFCellStyle.ALIGN_LEFT );
        style.setWrapText(true);
        return style;
    }

    //10号字体，带边框，垂直居中
        public HSSFCellStyle tenFourLineBoldCenter( HSSFWorkbook workbook ){
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont(); //定义字体
            font.setFontName( "宋体" );
            font.setFontHeightInPoints( ( short )10 );
            font.setBoldweight( ( short )1000000 );
            style.setFont( font );
            style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
            style.setBottomBorderColor( HSSFColor.BLACK.index );
            style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
            style.setLeftBorderColor( HSSFColor.BLACK.index );
            style.setBorderRight( HSSFCellStyle.BORDER_THIN );
            style.setRightBorderColor( HSSFColor.BLACK.index );
            style.setBorderTop( HSSFCellStyle.BORDER_THIN );
            style.setTopBorderColor( HSSFColor.BLACK.index );
            style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER );
            style.setAlignment( HSSFCellStyle.ALIGN_CENTER );
            style.setWrapText(true);
            return style;

    }

}
