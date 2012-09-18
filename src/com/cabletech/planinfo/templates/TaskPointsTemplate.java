package com.cabletech.planinfo.templates;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.utils.*;
import org.apache.poi.hssf.util.Region;

public class TaskPointsTemplate extends Template{
    private static Logger logger = Logger.getLogger( "TaskPointsTemplate" );
    public TaskPointsTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }

    //******************pzj 修改
     public void ExportPatrolPointsList( String patrolName, String patrolid,
         Vector pointList, ExcelStyle excelstyle ){
         activeSheet( 0 );

         this.curStyle = excelstyle.defaultStyle(this.workbook);
         setCellValue( 1, 0, patrolName + "对应责任巡检点一览表" );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
         this.curRow.setHeight( ( short )480 );
         setCellValue( 1, 1, "巡检点一览表" );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
         this.curRow.setHeight( ( short )480 );
         setCellValue( 1, 2, patrolid );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
         this.curRow.setHeight( ( short )480 );
         setCellValue( 1, 3, DateUtil.getNowDateString() );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
         this.curRow.setHeight( ( short )480 );

         activeRow( 5 );
         setCellValue( 0, "巡检责任人" );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
         setCellValue( 1, patrolName );
         ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
         setCellValue( 2, "" );
         this.curSheet.addMergedRegion( new Region( 5 , ( short )1, 5, ( short )2 ) );
         this.curRow.setHeight( ( short )480 );
         int customBeginLine = 6;
         int t = 0;

         if( pointList.size() > 0 ){
             for( int i = 0; i < pointList.size(); i++ ){
                 Vector oneUnitVct = ( Vector )pointList.get( i );

                 activeRow( customBeginLine + t );
                 if(t == 0){
                     setCellValue( 0, "任务" );
                     ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
                 }else{
                     setCellValue( 0, "" );
                 }
                 setCellValue( 1, " 线段 ：" + ( String )oneUnitVct.get( 1 ) );
                 ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.bold10FourLine(this.workbook) );
                 setCellValue( 2, "" );
                 this.curSheet.addMergedRegion( new Region( customBeginLine + t , ( short )1, customBeginLine + t, ( short )2 ) );
                 this.curRow.setHeight( ( short )480 );
                 t++;
                 Vector pointsVct = ( Vector )oneUnitVct.get( 2 );
                 for( int k = 0; k < pointsVct.size(); k++ ){
                     Vector tempV = ( Vector )pointsVct.get( k );
                     activeRow( customBeginLine + t );
                     setCellValue( 0, "" );
                     setCellValue( 1, ( String )tempV.get( 1 ) );
                     setCellValue( 2, ( String )tempV.get( 2 ) );
                     t++;
                 }
             }
             this.curSheet.addMergedRegion( new Region( customBeginLine, ( short )0, customBeginLine + t - 1, ( short )0 ) );
             activeRow( customBeginLine + t );
             setCellValue( 0, "领导确认" );
             ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
             setCellValue( 1, "" );
             setCellValue( 2, "" );
             this.curRow.setHeight((short)1500);
             this.curSheet.addMergedRegion( new Region( customBeginLine + t, ( short )1, customBeginLine + t, ( short )2 ) );

             activeRow( customBeginLine + t + 1 );
             setCellValue( 0, "备注" );
             ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
             setCellValue( 1, "" );
             setCellValue( 2, "" );
             this.curRow.setHeight( ( short )1500 );
             this.curSheet.addMergedRegion( new Region( customBeginLine + t + 1, ( short )1, customBeginLine + t + 1, ( short )2 ) );
         }
         else{
             activeRow( 5 );

             setCellValue( 0, "巡检责任人" );
             ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter( this.
                 workbook ) );
             setCellValue( 1, patrolName );
             ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
             setCellValue( 2, "该巡检员没有责任巡检点，请首先为其分配责任巡检段!" );
         }
     }


    public void ExportYearPlanResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "planname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "planname" ).toString() );}

                if( record.get( "year" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "year" ).toString() );}

                if( record.get( "approvestatus" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "approvestatus" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }


    public void ExportMonthPlanResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "planname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "planname" ).toString() );}

                if( record.get( "year" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "year" ).toString() );}

                if( record.get( "month" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "month" ).toString() );}

                if( record.get( "approvestatus" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "approvestatus" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }


    public void ExportWeekPlanResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //行索引
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){

                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "name" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "name" ).toString() );}

                if( record.get( "patrol" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "patrol" ).toString() );}

                if( record.get( "startdate" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "startdate" ).toString() );}

                if( record.get( "enddate" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "enddate" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }

}
