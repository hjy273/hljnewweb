package com.cabletech.troublemanage.templates;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.troublemanage.beans.*;
import org.apache.poi.hssf.util.Region;

public class YMWTroubleFormTemplate extends Template{
    private static Logger logger = Logger.getLogger( "YMWTroubleFormTemplate" );
    public YMWTroubleFormTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    /**
     * 导出隐患处理通知单
     * @param plan Plan
     * @param taskVct Vector
     * @param fID String
     */
    public void ExportTroubleNoticeform( AccidentBean bean, Vector taskVct, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        activeRow( 1 );
        if( bean.getContractorid() == null ){
            setCellValue( 0,
                "隐患报告单位：" + "      "  +"            填报人："+ bean.getBconfirmman() + "        填报时间：" + bean.getReprottime() );

        }
        else{
            setCellValue( 0,
                "隐患报告单位：" + bean.getContractorid()  +"            填报人："+ bean.getBconfirmman() + "        填报时间：" + bean.getReprottime() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 2 );
        if( bean.getSendtime() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendtime().substring( 0, 10 ) );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 3 );
        if( bean.getSendtime() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendtime().substring( 11 ) );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 4 );
        if( bean.getLid() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getLid() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 5 );
        if( bean.getPid() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getPid() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 6 );
        if( bean.getWhosend() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getWhosend() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 7 );
        if( bean.getSendto() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendto() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 8 );
        if( bean.getSendtime() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendtime() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )720 );
        activeRow( 9 );
        if( bean.getResonandfix() == null ){
            setCellValue( 1, "" );
        }
        else{
            setCellValue( 1, bean.getResonandfix() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )1480 );
//        activeRow( 10 );
//        if( bean.getBreportman() == null ){
//            setCellValue( 0, "乙方填报人:" + "" );
//        }
//        else{
//            setCellValue( 0, "乙方填报人:" + bean.getBreportman() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        this.curRow.setHeight( ( short )1000 );
//        activeRow( 11 );
//        if( bean.getBconfirmman() == null ){
//            setCellValue( 0, "甲方确认人:" + "" );
//        }
//        else{
//            setCellValue( 0, "甲方确认人:" + bean.getBconfirmman() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        this.curRow.setHeight( ( short )1000 );
    }


    /**
     * 导出障碍处理通知单
     * @param plan Plan
     * @param taskVct Vector
     * @param fID String
     */
    public void ExportAccidentNoticeform( AccidentBean bean, Vector taskVct, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        activeRow( 1 );
        if( bean.getContractorid() == null ){
            setCellValue( 0,
                "障碍报告单位：" + "      "  +"            填报人："+ bean.getBconfirmman() + "        填报时间：" + bean.getReprottime() );


        }
        else{
            setCellValue( 0,
                "障碍报告单位：" + bean.getContractorid()  +"            填报人："+ bean.getBconfirmman() + "        填报时间：" + bean.getReprottime() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curRow.setHeight( ( short )480 );
        activeRow( 2 );
        if( bean.getSendtime() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendtime().substring( 0, 10 ) );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )480 );
        activeRow( 3 );
        if( bean.getSendtime() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getSendtime().substring( 11 ) );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )480 );
        activeRow( 4 );
        if( bean.getLid() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getLid() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )480 );
        activeRow( 5 );
        if( bean.getPid() == null ){
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 2, bean.getPid() );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curRow.setHeight( ( short )480 );
        activeRow( 6 );
        if( bean.getResonandfix() == null ){
            setCellValue( 1, "" );
            setCellValue( 2, "" );
        }
        else{
            setCellValue( 1, bean.getResonandfix() );
            setCellValue( 2, "" );
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curSheet.addMergedRegion( new Region( 6 , ( short )1, 6, ( short )2 ) );
        this.curRow.setHeight( ( short )1000 );
//        activeRow( 7 );
//        if( bean.getNoticetime() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getNoticetime() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 8 );
//        if( bean.getCooperateman() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getCooperateman() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 9 );
//        if( bean.getTesttime() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getTesttime() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 10 );
//        if( bean.getTestman() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getTestman() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 11 );
//        if( bean.getDistance() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getDistance() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 12 );
//        if( bean.getRealdistance() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getRealdistance() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 13 );
//        if( bean.getFixedman() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getFixedman() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 14 );
//        if( bean.getMonitor() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getMonitor() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//        activeRow( 15 );
//        if( bean.getCommander() == null ){
//            setCellValue( 2, "" );
//        }
//        else{
//            setCellValue( 2, bean.getCommander() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )480 );
//
//        for( int i = 0; i < taskVct.size(); i++ ){
//            activeRow( 17 + i );
//            AccidentDetailBean dbean = ( AccidentDetailBean )taskVct.elementAt( i );
//            if( dbean.getCorecode() == null ){
//                setCellValue( 1, "" );
//            }
//            else{
//                setCellValue( 1, dbean.getCorecode() );
//            }
//            if( dbean.getTempfixedtime() == null ){
//                setCellValue( 2, "" );
//            }
//            else{
//                setCellValue( 2, dbean.getTempfixedtime() );
//            }
//            if( dbean.getFixedtime() == null ){
//                setCellValue( 3, "" );
//            }
//            else{
//                setCellValue( 3, dbean.getFixedtime() );
//            }
//            if( dbean.getDiachronic() == null ){
//                setCellValue( 4, "" );
//            }
//            else{
//                setCellValue( 4, dbean.getDiachronic() );
//            }
//            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//            this.curRow.setHeight( ( short )480 );
//        }
//        activeRow( 17 + taskVct.size() );
//        setCellValue( 0, "故障原因与查修过程" );
//        if( bean.getResonandfix() == null ){
//            setCellValue( 1, "" );
//        }
//        else{
//            setCellValue( 1, bean.getResonandfix() );
//        }
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter( this.
//            workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
//        this.curRow.setHeight( ( short )1000 );
//        activeRow( 18 + taskVct.size() );
//        if( bean.getBreportman() == null ){
//            setCellValue( 0, "乙方填报人:" + "" );
//        }
//        else{
//            setCellValue( 0, "乙方填报人:" + bean.getBreportman() );
//        }
//        setCellValue( 1, "" );
//        setCellValue( 2, "" );
//        setCellValue( 3, "" );
//        setCellValue( 4, "" );
//        this.curSheet.addMergedRegion( new Region( 18 + taskVct.size(), ( short )0, 18 + taskVct.size(), ( short )4 ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        this.curRow.setHeight( ( short )1000 );
//        activeRow( 19 + taskVct.size() );
//        if( bean.getBconfirmman() == null ){
//            setCellValue( 0, "甲方确认人:" + "" );
//        }
//        else{
//            setCellValue( 0, "甲方确认人:" + bean.getBconfirmman() );
//        }
//        setCellValue( 1, "" );
//        setCellValue( 2, "" );
//        setCellValue( 3, "" );
//        setCellValue( 4, "" );
//        this.curSheet.addMergedRegion( new Region( 19 + taskVct.size(), ( short )0, 19 + taskVct.size(), ( short )4 ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
//        this.curRow.setHeight( ( short )1000 );
    }


    public void exportUndoneTrouble( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        int r = 2; //行索引
        logger.info( "得到list" );
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );

            if( record.get( "sendtime" ) == null ){
                setCellValue( 0, "" );
            }
            else{
                setCellValue( 0, record.get( "sendtime" ).toString() );
            }

            if( record.get( "reason" ) == null ){
                setCellValue( 1, "" );
            }
            else{
                setCellValue( 1, record.get( "reason" ).toString() );
            }

            if( record.get( "emlevel" ) == null ){
                setCellValue( 2, "" );
            }
            else{
                setCellValue( 2, record.get( "emlevel" ).toString() );
            }

            if( record.get( "subline" ) == null ){
                setCellValue( 3, "" );
            }
            else{
                setCellValue( 3, record.get( "subline" ).toString() );
            }

            if( record.get( "point" ) == null ){
                setCellValue( 4, "" );
            }
            else{
                setCellValue( 4, record.get( "point" ).toString() );
            }

            if( record.get( "contractor" ) == null ){
                setCellValue( 5, "" );
            }
            else{
                setCellValue( 5, record.get( "contractor" ).toString() );
            }
            r++; //下一行
        }
        logger.info( "成功写入" );
    }


    public void exportTroubleResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        int r = 2; //行索引
        logger.info( "得到list" );
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );

            if( record.get( "contractor" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "contractor" ).toString() );}

            if( record.get( "sendtime" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "sendtime" ).toString() );}

            if( record.get( "reason" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "reason" ).toString() );}

            if( record.get( "emlevel" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "emlevel" ).toString() );}

            if( record.get( "subline" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "subline" ).toString() );}

            if( record.get( "point" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "point" ).toString() );}

            if( record.get( "status" ) == null ){setCellValue( 6, "" );}
            else{setCellValue( 6, record.get( "status" ).toString() );}


            r++; //下一行
        }
        logger.info( "成功写入" );
    }


    public void exportUndoneAccident( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        int r = 2; //行索引
        logger.info( "得到list" );
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "contractor" ) == null ){
                setCellValue( 0, "" );
            }
            else{
                setCellValue( 0, record.get( "contractor" ).toString() );
            }

            if( record.get( "sendtime" ) == null ){
                setCellValue( 1, "" );
            }
            else{
                setCellValue( 1, record.get( "sendtime" ).toString() );
            }

            if( record.get( "reason" ) == null ){
                setCellValue( 2, "" );
            }
            else{
                setCellValue( 2, record.get( "reason" ).toString() );
            }

            if( record.get( "emlevel" ) == null ){
                setCellValue( 3, "" );
            }
            else{
                setCellValue( 3, record.get( "emlevel" ).toString() );
            }

            if( record.get( "subline" ) == null ){
                setCellValue( 4, "" );
            }
            else{
                setCellValue( 4, record.get( "subline" ).toString() );
            }

            if( record.get( "point" ) == null ){
                setCellValue( 5, "" );
            }
            else{
                setCellValue( 5, record.get( "point" ).toString() );
            }
            r++; //下一行
        }
        logger.info( "成功写入" );
    }


    public void exportAccidentResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        int r = 2; //行索引
        logger.info( "得到list" );
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "contractor" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "contractor" ).toString() );}

            if( record.get( "sendtime" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "sendtime" ).toString() );}

            if( record.get( "reason" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "reason" ).toString() );}

            if( record.get( "emlevel" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "emlevel" ).toString() );}

            if( record.get( "subline" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "subline" ).toString() );}

            if( record.get( "point" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "point" ).toString() );}

            if( record.get( "status" ) == null ){setCellValue( 6, "" );}
            else{setCellValue( 6, record.get( "status" ).toString() );}

            r++; //下一行
        }
        logger.info( "成功写入" );
    }
}
