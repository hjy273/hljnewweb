package com.cabletech.linechange.templates;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.partmanage.action.*;
import com.cabletech.linechange.bean.ChangeInfoBean;
import org.apache.poi.hssf.util.Region;
import com.cabletech.linechange.bean.ChangeSurveyBean;
import com.cabletech.linechange.bean.ChangeBuildBean;
import com.cabletech.linechange.bean.ChangeCheckBean;

public class LineChangeTemplate extends Template{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    public LineChangeTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }

    public void exportSta( List list, ExcelStyle excelstyle ) throws Exception{
           DynaBean record;
           activeSheet( 0 );
           this.curStyle = excelstyle.defaultStyle( this.workbook );
           this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
           //设置页脚
                this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );

    }


    public void exportApplyResult( List list, ChangeInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
        this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
        //设置页脚
        this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
        int r = 0;
            if(  bean != null ){
                if( bean.getChangename() != null ){
                    if(!bean.getChangename().equals("")){
                        this.setCellValue( 0, r, "工程名称" );
                        this.setCellValue( 1, r, bean.getChangename() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getChangepro() != null  ){
                    if(!bean.getChangepro().equals("")){
                        this.setCellValue( 0, r, "工程性质" );
                        this.setCellValue( 1, r, bean.getChangepro() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getApproveresult() != null ){
                    if(!bean.getApproveresult().equals("")){
                        this.setCellValue( 0, r, "审定结果" );
                        this.setCellValue( 1, r, bean.getApproveresult() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getLineclass() != null ){
                    if(!bean.getLineclass().equals("")){
                        this.setCellValue( 0, r, "网络性质" );
                        if( bean.getLineclass().equals( "1" ) ){
                            this.setCellValue( 1, r, "一干" );
                        }
                        if( bean.getLineclass().equals( "2" ) ){
                            this.setCellValue( 1, r, "二干" );
                        }
                        if( bean.getLineclass().equals( "3" ) ){
                            this.setCellValue( 1, r, "汇聚环" );
                        }
                        if( bean.getLineclass().equals( "4" ) ){
                            this.setCellValue( 1, r, "接入网" );
                        }
                        if( bean.getLineclass().equals( "5" ) ){
                            this.setCellValue( 1, r, "骨干层" );
                        }
//                    this.setCellValue( 1, r, bean.getLineclass() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getBegintime() != null ){
                    if(!bean.getBegintime().equals("")){
                        this.setCellValue( 0, r, "开始时间" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getEndtime() != null ){
                    if(!bean.getEndtime().equals("")){
                        this.setCellValue( 0, r, "结束时间" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
            }
            else{
                this.setCellValue( 0, r, "查询条件" );
                this.setCellValue( 1, r, "所有" );
                this.setCellValue( 2, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                r++;
            }
            this.setCellValue( 0, r, "修缮申请一览表" );
            this.setCellValue( 1, r, "" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            this.setCellValue( 6, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )6 ) );
            this.curRow.setHeight( ( short )1000 );
            r++;

            this.setCellValue( 0, r, "工程名称" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 1, r, "工程性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 2, r, "工程地址" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 3, r, "网络性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 4, r, "审定结果" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 5, r, "工作状态" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 6, r, "申请日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

            r++;
    logger.info( "得到list" );
    if( list != null ){
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "changename" ).toString() );}

            if( record.get( "changepro" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "changepro" ).toString() );}

            if( record.get( "changeaddr" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "changeaddr" ).toString() );}

            if( record.get( "lineclass" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "lineclass" ).toString() );}

            if( record.get( "approveresult" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "approveresult" ).toString() );}

            if( record.get( "state" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "state" ).toString() );}

            if( record.get( "applytime" ) == null ){setCellValue( 6, "" );}
            else{setCellValue( 6, record.get( "applytime" ).toString() );}

            r++; //下一行
        }
        logger.info( "成功写入" );

    }
}

public void exportSurveyResult( List list, ChangeSurveyBean bean, ExcelStyle excelstyle ) throws Exception{
    DynaBean record;
    activeSheet( 0 );
    this.curStyle = excelstyle.defaultStyle( this.workbook );
    //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
    this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
    //设置页脚
    this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
    int r = 0;
        if(  bean != null ){
            if( bean.getPrincipal() != null ){
                if(!bean.getPrincipal().equals("")){
                    this.setCellValue( 0, r, "勘查负责人" );
                    this.setCellValue( 1, r, bean.getPrincipal() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                    r++;
                }
            }
            if( bean.getBudget() != null | bean.getMaxbudget() != null ){
                if( bean.getBudget().floatValue() != 0.0 ){
                    this.setCellValue( 0, r, "工程预算" );
                    this.setCellValue( 1, r, bean.getBudget().toString() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                    r++;
                }
            }
            if( bean.getApproveresult() != null ){
                if(!bean.getApproveresult().equals("")){
                    this.setCellValue( 0, r, "审定结果" );
                    this.setCellValue( 1, r, bean.getApproveresult() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                    r++;
                }
            }
            if( bean.getBegintime() != null ){
                if(!bean.getBegintime().equals("")){
                    this.setCellValue( 0, r, "审定开始时间" );
                    this.setCellValue( 1, r, bean.getBegintime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                    r++;
                }
            }
            if( bean.getEndtime() != null ){
                if(!bean.getEndtime().equals("")){
                    this.setCellValue( 0, r, "审定结束时间" );
                    this.setCellValue( 1, r, bean.getEndtime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                    r++;
                }
            }
        }
        else{
            this.setCellValue( 0, r, "查询条件" );
            this.setCellValue( 1, r, "所有" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

            this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }
        this.setCellValue( 0, r, "勘查审定一览表" );
        this.setCellValue( 1, r, "" );
        this.setCellValue( 2, r, "" );
        this.setCellValue( 3, r, "" );
        this.setCellValue( 4, r, "" );
        this.setCellValue( 5, r, "" );

        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )5 ) );
        this.curRow.setHeight( ( short )1000 );
        r++;

        this.setCellValue( 0, r, "工程名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "工程预算" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "勘查负责人" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "勘查日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "审定结果" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "审定日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
logger.info( "得到list" );
if( list != null ){
    Iterator iter = list.iterator();
    logger.info( "开始循环写入数据" );
    while( iter.hasNext() ){
        record = ( DynaBean )iter.next();
        activeRow( r );
        if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
        else{setCellValue( 0, record.get( "changename" ).toString() );}

        if( record.get( "budget" ) == null ){setCellValue( 1, "" );}
        else{setCellValue( 1, record.get( "budget" ).toString() );}

        if( record.get( "principal" ) == null ){setCellValue( 2, "" );}
        else{setCellValue( 2, record.get( "principal" ).toString() );}

        if( record.get( "surveydate" ) == null ){setCellValue( 3, "" );}
        else{setCellValue( 3, record.get( "surveydate" ).toString() );}

        if( record.get( "approveresult" ) == null ){setCellValue( 4, "" );}
        else{setCellValue( 4, record.get( "approveresult" ).toString() );}

        if( record.get( "approvedate" ) == null ){setCellValue( 5, "" );}
        else{setCellValue( 5, record.get( "approvedate" ).toString() );}

        r++; //下一行
    }
    logger.info( "成功写入" );

}
}
    public void exportBuildSetoutResult( List list, ChangeInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
        this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
        //设置页脚
        this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
        int r = 0;
            if(  bean != null ){
//                if( bean.getChangename() != null ){
//                    if(!bean.getChangename().equals("")){
//                        this.setCellValue( 0, r, "工程名称" );
//                        this.setCellValue( 1, r, bean.getChangename() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getChangepro() != null  ){
//                    if(!bean.getChangepro().equals("")){
//                        this.setCellValue( 0, r, "工程性质" );
//                        this.setCellValue( 1, r, bean.getChangepro() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getApproveresult() != null ){
//                    if(!bean.getApproveresult().equals("")){
//                        this.setCellValue( 0, r, "审定结果" );
//                        this.setCellValue( 1, r, bean.getApproveresult() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getLineclass() != null ){
//                    if(!bean.getLineclass().equals("")){
//                        this.setCellValue( 0, r, "网络性质" );
//                        if( bean.getLineclass().equals( "1" ) ){
//                            this.setCellValue( 1, r, "一干" );
//                        }
//                        if( bean.getLineclass().equals( "2" ) ){
//                            this.setCellValue( 1, r, "二干" );
//                        }
//                        if( bean.getLineclass().equals( "3" ) ){
//                            this.setCellValue( 1, r, "汇聚环" );
//                        }
//                        if( bean.getLineclass().equals( "4" ) ){
//                            this.setCellValue( 1, r, "接入网" );
//                        }
//                        if( bean.getLineclass().equals( "5" ) ){
//                            this.setCellValue( 1, r, "骨干层" );
//                        }
////                    this.setCellValue( 1, r, bean.getLineclass() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
                if( bean.getBegintime() != null ){
                    if(!bean.getBegintime().equals("")){
                        this.setCellValue( 0, r, "开始时间" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getEndtime() != null ){
                    if(!bean.getEndtime().equals("")){
                        this.setCellValue( 0, r, "结束时间" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
            }
            else{
                this.setCellValue( 0, r, "查询条件" );
                this.setCellValue( 1, r, "所有" );
                this.setCellValue( 2, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                r++;
            }
            this.setCellValue( 0, r, "施工准备一览表" );
            this.setCellValue( 1, r, "" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            this.setCellValue( 6, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )6 ) );
            this.curRow.setHeight( ( short )1000 );
            r++;

            this.setCellValue( 0, r, "工程名称" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 1, r, "工程性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 2, r, "工程地址" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 3, r, "网络性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 4, r, "审定结果" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 5, r, "工作状态" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 6, r, "申请日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

            r++;
    logger.info( "得到list" );
    if( list != null ){
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "changename" ).toString() );}

            if( record.get( "changepro" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "changepro" ).toString() );}

            if( record.get( "changeaddr" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "changeaddr" ).toString() );}

            if( record.get( "lineclass" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "lineclass" ).toString() );}

            if( record.get( "approveresult" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "approveresult" ).toString() );}

            if( record.get( "state" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "state" ).toString() );}

            if( record.get( "applytime" ) == null ){setCellValue( 6, "" );}
            else{setCellValue( 6, record.get( "applytime" ).toString() );}

            r++; //下一行
        }
        logger.info( "成功写入" );

    }
}
public void exportConsignResult( List list, ChangeInfoBean bean, ExcelStyle excelstyle ) throws Exception{
    DynaBean record;
    activeSheet( 0 );
    this.curStyle = excelstyle.defaultStyle( this.workbook );
    //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
    this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
    //设置页脚
    this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
    int r = 0;
        if(  bean != null ){
//                if( bean.getChangename() != null ){
//                    if(!bean.getChangename().equals("")){
//                        this.setCellValue( 0, r, "工程名称" );
//                        this.setCellValue( 1, r, bean.getChangename() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getChangepro() != null  ){
//                    if(!bean.getChangepro().equals("")){
//                        this.setCellValue( 0, r, "工程性质" );
//                        this.setCellValue( 1, r, bean.getChangepro() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getApproveresult() != null ){
//                    if(!bean.getApproveresult().equals("")){
//                        this.setCellValue( 0, r, "审定结果" );
//                        this.setCellValue( 1, r, bean.getApproveresult() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getLineclass() != null ){
//                    if(!bean.getLineclass().equals("")){
//                        this.setCellValue( 0, r, "网络性质" );
//                        if( bean.getLineclass().equals( "1" ) ){
//                            this.setCellValue( 1, r, "一干" );
//                        }
//                        if( bean.getLineclass().equals( "2" ) ){
//                            this.setCellValue( 1, r, "二干" );
//                        }
//                        if( bean.getLineclass().equals( "3" ) ){
//                            this.setCellValue( 1, r, "汇聚环" );
//                        }
//                        if( bean.getLineclass().equals( "4" ) ){
//                            this.setCellValue( 1, r, "接入网" );
//                        }
//                        if( bean.getLineclass().equals( "5" ) ){
//                            this.setCellValue( 1, r, "骨干层" );
//                        }
////                    this.setCellValue( 1, r, bean.getLineclass() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
            if( bean.getBegintime() != null ){
                if(!bean.getBegintime().equals("")){
                    this.setCellValue( 0, r, "开始时间" );
                    this.setCellValue( 1, r, bean.getBegintime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );
                    this.setCellValue( 6, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                    r++;
                }
            }
            if( bean.getEndtime() != null ){
                if(!bean.getEndtime().equals("")){
                    this.setCellValue( 0, r, "结束时间" );
                    this.setCellValue( 1, r, bean.getEndtime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    this.setCellValue( 5, r, "" );
                    this.setCellValue( 6, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                    r++;
                }
            }
        }
        else{
            this.setCellValue( 0, r, "查询条件" );
            this.setCellValue( 1, r, "所有" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            this.setCellValue( 6, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

            this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
            r++;
        }
        this.setCellValue( 0, r, "施工委托一览表" );
        this.setCellValue( 1, r, "" );
        this.setCellValue( 2, r, "" );
        this.setCellValue( 3, r, "" );
        this.setCellValue( 4, r, "" );
        this.setCellValue( 5, r, "" );
        this.setCellValue( 6, r, "" );

        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )6 ) );
        this.curRow.setHeight( ( short )1000 );
        r++;

        this.setCellValue( 0, r, "工程名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "工程性质" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "工程地址" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "网络性质" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "审定结果" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "工程状态" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 6, r, "填写日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        r++;
logger.info( "得到list" );
if( list != null ){
    Iterator iter = list.iterator();
    logger.info( "开始循环写入数据" );
    while( iter.hasNext() ){
        record = ( DynaBean )iter.next();
        activeRow( r );
        if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
        else{setCellValue( 0, record.get( "changename" ).toString() );}

        if( record.get( "changepro" ) == null ){setCellValue( 1, "" );}
        else{setCellValue( 1, record.get( "changepro" ).toString() );}

        if( record.get( "changeaddr" ) == null ){setCellValue( 2, "" );}
        else{setCellValue( 2, record.get( "changeaddr" ).toString() );}

        if( record.get( "lineclass" ) == null ){setCellValue( 3, "" );}
        else{setCellValue( 3, record.get( "lineclass" ).toString() );}

        if( record.get( "approveresult" ) == null ){setCellValue( 4, "" );}
        else{setCellValue( 4, record.get( "approveresult" ).toString() );}

        if( record.get( "state" ) == null ){setCellValue( 5, "" );}
        else{setCellValue( 5, record.get( "state" ).toString() );}

        if( record.get( "entrusttime" ) == null ){setCellValue( 6, "" );}
        else{setCellValue( 6, record.get( "entrusttime" ).toString() );}

        r++; //下一行
    }
    logger.info( "成功写入" );

}
}
    public void exportBuildResult( List list, ChangeBuildBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
        this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
        //设置页脚
        this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
        int r = 0;
            if(  bean != null ){
//                if( bean.getChangename() != null ){
//                    if(!bean.getChangename().equals("")){
//                        this.setCellValue( 0, r, "工程名称" );
//                        this.setCellValue( 1, r, bean.getChangename() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getChangepro() != null  ){
//                    if(!bean.getChangepro().equals("")){
//                        this.setCellValue( 0, r, "工程性质" );
//                        this.setCellValue( 1, r, bean.getChangepro() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getApproveresult() != null ){
//                    if(!bean.getApproveresult().equals("")){
//                        this.setCellValue( 0, r, "审定结果" );
//                        this.setCellValue( 1, r, bean.getApproveresult() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getLineclass() != null ){
//                    if(!bean.getLineclass().equals("")){
//                        this.setCellValue( 0, r, "网络性质" );
//                        if( bean.getLineclass().equals( "1" ) ){
//                            this.setCellValue( 1, r, "一干" );
//                        }
//                        if( bean.getLineclass().equals( "2" ) ){
//                            this.setCellValue( 1, r, "二干" );
//                        }
//                        if( bean.getLineclass().equals( "3" ) ){
//                            this.setCellValue( 1, r, "汇聚环" );
//                        }
//                        if( bean.getLineclass().equals( "4" ) ){
//                            this.setCellValue( 1, r, "接入网" );
//                        }
//                        if( bean.getLineclass().equals( "5" ) ){
//                            this.setCellValue( 1, r, "骨干层" );
//                        }
////                    this.setCellValue( 1, r, bean.getLineclass() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
                if( bean.getStarttime() != null ){
                    if(!bean.getStarttime().equals("")){
                        this.setCellValue( 0, r, "开始时间" );
                        this.setCellValue( 1, r, bean.getStarttime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                        r++;
                    }
                }
                if( bean.getEndtime() != null ){
                    if(!bean.getEndtime().equals("")){
                        this.setCellValue( 0, r, "结束时间" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                        r++;
                    }
                }
            }
            else{
                this.setCellValue( 0, r, "查询条件" );
                this.setCellValue( 1, r, "所有" );
                this.setCellValue( 2, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
                r++;
            }
            this.setCellValue( 0, r, "施工信息一览表" );
            this.setCellValue( 1, r, "" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )5 ) );
            this.curRow.setHeight( ( short )1000 );
            r++;

            this.setCellValue( 0, r, "工程名称" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 1, r, "施工开始日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 2, r, "施工结束日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 3, r, "工程性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 4, r, "施工单位名称" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 5, r, "填写日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

            r++;
    logger.info( "得到list" );
    if( list != null ){
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "changename" ).toString() );}

            if( record.get( "starttime" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "starttime" ).toString() );}

            if( record.get( "endtime" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "endtime" ).toString() );}

            if( record.get( "changepro" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "changepro" ).toString() );}

            if( record.get( "buildunit" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "buildunit" ).toString() );}

            if( record.get( "fillindate" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "fillindate" ).toString() );}

            r++; //下一行
        }
        logger.info( "成功写入" );

    }
}
public void exportCheckResult( List list, ChangeCheckBean bean, ExcelStyle excelstyle ) throws Exception{
    DynaBean record;
    activeSheet( 0 );
    this.curStyle = excelstyle.defaultStyle( this.workbook );
    //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
    this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
    //设置页脚
    this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
    int r = 0;
        if(  bean != null ){
//                if( bean.getChangename() != null ){
//                    if(!bean.getChangename().equals("")){
//                        this.setCellValue( 0, r, "工程名称" );
//                        this.setCellValue( 1, r, bean.getChangename() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getChangepro() != null  ){
//                    if(!bean.getChangepro().equals("")){
//                        this.setCellValue( 0, r, "工程性质" );
//                        this.setCellValue( 1, r, bean.getChangepro() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getApproveresult() != null ){
//                    if(!bean.getApproveresult().equals("")){
//                        this.setCellValue( 0, r, "审定结果" );
//                        this.setCellValue( 1, r, bean.getApproveresult() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getLineclass() != null ){
//                    if(!bean.getLineclass().equals("")){
//                        this.setCellValue( 0, r, "网络性质" );
//                        if( bean.getLineclass().equals( "1" ) ){
//                            this.setCellValue( 1, r, "一干" );
//                        }
//                        if( bean.getLineclass().equals( "2" ) ){
//                            this.setCellValue( 1, r, "二干" );
//                        }
//                        if( bean.getLineclass().equals( "3" ) ){
//                            this.setCellValue( 1, r, "汇聚环" );
//                        }
//                        if( bean.getLineclass().equals( "4" ) ){
//                            this.setCellValue( 1, r, "接入网" );
//                        }
//                        if( bean.getLineclass().equals( "5" ) ){
//                            this.setCellValue( 1, r, "骨干层" );
//                        }
////                    this.setCellValue( 1, r, bean.getLineclass() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
            if( bean.getBegintime() != null ){
                if(!bean.getBegintime().equals("")){
                    this.setCellValue( 0, r, "开始时间" );
                    this.setCellValue( 1, r, bean.getBegintime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )4 ) );
                    r++;
                }
            }
            if( bean.getEndtime() != null ){
                if(!bean.getEndtime().equals("")){
                    this.setCellValue( 0, r, "结束时间" );
                    this.setCellValue( 1, r, bean.getEndtime() );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );

                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )4 ) );
                    r++;
                }
            }
        }
        else{
            this.setCellValue( 0, r, "查询条件" );
            this.setCellValue( 1, r, "所有" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )4 ) );
            r++;
        }
        this.setCellValue( 0, r, "修缮验收一览表" );
        this.setCellValue( 1, r, "" );
        this.setCellValue( 2, r, "" );
        this.setCellValue( 3, r, "" );
        this.setCellValue( 4, r, "" );

        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )4 ) );
        this.curRow.setHeight( ( short )1000 );
        r++;

        this.setCellValue( 0, r, "工程名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "验收日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "验收负责人" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "审定结果" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "填写日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        r++;
logger.info( "得到list" );
if( list != null ){
    Iterator iter = list.iterator();
    logger.info( "开始循环写入数据" );
    while( iter.hasNext() ){
        record = ( DynaBean )iter.next();
        activeRow( r );
        if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
        else{setCellValue( 0, record.get( "changename" ).toString() );}

        if( record.get( "checkdate" ) == null ){setCellValue( 1, "" );}
        else{setCellValue( 1, record.get( "checkdate" ).toString() );}

        if( record.get( "checkperson" ) == null ){setCellValue( 2, "" );}
        else{setCellValue( 2, record.get( "checkperson" ).toString() );}

        if( record.get( "checkresult" ) == null ){setCellValue( 3, "" );}
        else{setCellValue( 3, record.get( "checkresult" ).toString() );}

        if( record.get( "fillintime" ) == null ){setCellValue( 4, "" );}
        else{setCellValue( 4, record.get( "fillintime" ).toString() );}

        r++; //下一行
    }
    logger.info( "成功写入" );

}
}
    public void exportPageonholeResult( List list, ChangeInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
        this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
        //设置页脚
        this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
        int r = 0;
            if(  bean != null ){
//                if( bean.getChangename() != null ){
//                    if(!bean.getChangename().equals("")){
//                        this.setCellValue( 0, r, "工程名称" );
//                        this.setCellValue( 1, r, bean.getChangename() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getChangepro() != null  ){
//                    if(!bean.getChangepro().equals("")){
//                        this.setCellValue( 0, r, "工程性质" );
//                        this.setCellValue( 1, r, bean.getChangepro() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getApproveresult() != null ){
//                    if(!bean.getApproveresult().equals("")){
//                        this.setCellValue( 0, r, "审定结果" );
//                        this.setCellValue( 1, r, bean.getApproveresult() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
//                if( bean.getLineclass() != null ){
//                    if(!bean.getLineclass().equals("")){
//                        this.setCellValue( 0, r, "网络性质" );
//                        if( bean.getLineclass().equals( "1" ) ){
//                            this.setCellValue( 1, r, "一干" );
//                        }
//                        if( bean.getLineclass().equals( "2" ) ){
//                            this.setCellValue( 1, r, "二干" );
//                        }
//                        if( bean.getLineclass().equals( "3" ) ){
//                            this.setCellValue( 1, r, "汇聚环" );
//                        }
//                        if( bean.getLineclass().equals( "4" ) ){
//                            this.setCellValue( 1, r, "接入网" );
//                        }
//                        if( bean.getLineclass().equals( "5" ) ){
//                            this.setCellValue( 1, r, "骨干层" );
//                        }
////                    this.setCellValue( 1, r, bean.getLineclass() );
//                        this.setCellValue( 2, r, "" );
//                        this.setCellValue( 3, r, "" );
//                        this.setCellValue( 4, r, "" );
//                        this.setCellValue( 5, r, "" );
//                        this.setCellValue( 6, r, "" );
//
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
//                            workbook ) );
//
//                        this.curRow.setHeight( ( short )500 );
//                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
//                        r++;
//                    }
//                }
                if( bean.getBegintime() != null ){
                    if(!bean.getBegintime().equals("")){
                        this.setCellValue( 0, r, "开始时间" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
                if( bean.getEndtime() != null ){
                    if(!bean.getEndtime().equals("")){
                        this.setCellValue( 0, r, "结束时间" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        this.setCellValue( 5, r, "" );
                        this.setCellValue( 6, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                        r++;
                    }
                }
            }
            else{
                this.setCellValue( 0, r, "查询条件" );
                this.setCellValue( 1, r, "所有" );
                this.setCellValue( 2, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )6 ) );
                r++;
            }
            this.setCellValue( 0, r, "修缮归档一览表" );
            this.setCellValue( 1, r, "" );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            this.setCellValue( 6, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )6 ) );
            this.curRow.setHeight( ( short )1000 );
            r++;

            this.setCellValue( 0, r, "工程名称" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 1, r, "工程预算" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 2, r, "工程结算" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 3, r, "网络性质" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 4, r, "是否需修改" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 5, r, "工程状态" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 6, r, "填写日期" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

            r++;
    logger.info( "得到list" );
    if( list != null ){
        Iterator iter = list.iterator();
        logger.info( "开始循环写入数据" );
        while( iter.hasNext() ){
            record = ( DynaBean )iter.next();
            activeRow( r );
            if( record.get( "changename" ) == null ){setCellValue( 0, "" );}
            else{setCellValue( 0, record.get( "changename" ).toString() );}

            if( record.get( "budget" ) == null ){setCellValue( 1, "" );}
            else{setCellValue( 1, record.get( "budget" ).toString() );}

            if( record.get( "square" ) == null ){setCellValue( 2, "" );}
            else{setCellValue( 2, record.get( "square" ).toString() );}

            if( record.get( "lineclass" ) == null ){setCellValue( 3, "" );}
            else{setCellValue( 3, record.get( "lineclass" ).toString() );}

            if( record.get( "ischangedatum" ) == null ){setCellValue( 4, "" );}
            else{setCellValue( 4, record.get( "ischangedatum" ).toString() );}

            if( record.get( "state" ) == null ){setCellValue( 5, "" );}
            else{setCellValue( 5, record.get( "state" ).toString() );}

            if( record.get( "pageonholedate" ) == null ){setCellValue( 6, "" );}
            else{setCellValue( 6, record.get( "pageonholedate" ).toString() );}

            r++; //下一行
        }
        logger.info( "成功写入" );

    }
}
public void exportChangeStat( List list, ChangeInfoBean bean, ExcelStyle excelstyle ) throws Exception{
    DynaBean record;
    activeSheet( 0 );
    this.curStyle = excelstyle.defaultStyle( this.workbook );
    //        this.curSheet.getHeader().setCenter("北京鑫干线网络科技发展有限公司");
    this.curSheet.getHeader().setRight( "日期：" + HSSFHeader.date() );
    //设置页脚
    this.curSheet.getFooter().setCenter( "第 " + HSSFFooter.page() + " 页   共 " + HSSFFooter.numPages() + " 页 " );
    int r = 0;
        this.setCellValue( 0, r, "修缮工程统计表" );
        this.setCellValue( 1, r, "" );
        this.setCellValue( 2, r, "" );
        this.setCellValue( 3, r, "" );
        this.setCellValue( 4, r, "" );
        this.setCellValue( 5, r, "" );
        this.setCellValue( 6, r, "" );
        this.setCellValue( 7, r, "" );
        this.setCellValue( 8, r, "" );
        this.setCellValue( 9, r, "" );
        this.setCellValue( 10, r, "" );
        this.setCellValue( 11, r, "" );

        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )11 ) );
        this.curRow.setHeight( ( short )1000 );
        r++;

        this.setCellValue( 0, r, "序号" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "工程名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "网络性质" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "整修性质" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "修缮日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "预算金额（万元）" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 6, r, "结算金额（万元）" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 7, r, "监理费（万元）" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 8, r, "完工情况" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 9, r, "是否验收" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 10, r, "已付款（万元）" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 11, r, "付款日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
logger.info( "得到list" );
if( list != null ){
    Iterator iter = list.iterator();
    logger.info( "开始循环写入数据" );
    int n = 1;
    float countbudget = 0;
    float countsquare = 0;
    float countsexpense = 0;
    float countsquared = 0;
    while( iter.hasNext() ){
        record = ( DynaBean )iter.next();
        activeRow( r );

        setCellValue( 0, Integer.toString(n) );

        if( record.get( "changename" ) == null ){setCellValue( 1, "" );}
        else{setCellValue( 1, record.get( "changename" ).toString() );}

        if( record.get( "lineclass" ) == null ){setCellValue( 2, "" );}
        else{setCellValue( 2, record.get( "lineclass" ).toString() );}

        if( record.get( "changepro" ) == null ){setCellValue( 3, "" );}
        else{setCellValue( 3, record.get( "changepro" ).toString() );}

        if( record.get( "startdate" ) == null ){setCellValue( 4, "" );}
        else{setCellValue( 4, record.get( "startdate" ).toString() );}

        if( record.get( "budget" ) == null ){setCellValue( 5, "" );}
        else{setCellValue( 5, record.get( "budget" ).toString() );}

        if( record.get( "square" ) == null ){setCellValue( 6, "" );}
        else{setCellValue( 6, record.get( "square" ).toString() );}

        if( record.get( "sexpense" ) == null ){setCellValue( 7, "" );}
        else{setCellValue( 7, record.get( "sexpense" ).toString() );}

        if( record.get( "buildstate" ) == null ){setCellValue( 8, "" );}
        else{setCellValue( 8, record.get( "buildstate" ).toString() );}

        if( record.get( "valistate" ) == null ){setCellValue( 9, "" );}
        else{setCellValue( 9, record.get( "valistate" ).toString() );}

        if( record.get( "squared" ) == null ){setCellValue( 10, "" );}
        else{setCellValue( 10, record.get( "squared" ).toString() );}
        if( record.get( "sqdate" ) == null ){setCellValue( 11, "" );}
       else{setCellValue( 11, record.get( "sqdate" ).toString() );}

        //setCellValue( 10, "" );
        if( record.get( "budget" ) != null ){
            countbudget = countbudget + Float.parseFloat( record.get( "budget" ).toString() );
        }
        if( record.get( "square" ) != null ){
            countsquare = countsquare + Float.parseFloat( record.get( "square" ).toString() );
        }
        if( record.get( "sexpense" ) != null ){
            countsexpense = countsexpense + Float.parseFloat( record.get( "sexpense" ).toString() );
        }
        if( record.get( "squared" ) != null ){
            countsquared = countsquared + Float.parseFloat( record.get( "squared" ).toString() );
        }
        r++; //下一行
        n++;
    }
    activeRow( r );
    setCellValue( 0, "合计" );
    setCellValue( 1, "" );
    setCellValue( 2, "" );
    setCellValue( 3, "" );
    setCellValue( 4, "" );
    setCellValue( 5, Float.toString(countbudget) );
    setCellValue( 6, Float.toString(countsquare) );
    setCellValue( 7, Float.toString(countsexpense) );
    setCellValue( 8, "" );
    setCellValue( 9, "" );
    setCellValue( 10, Float.toString(countsquared) );
    setCellValue( 11, "" );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineBoldCenter( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
    this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )4 ) );
    this.curSheet.addMergedRegion( new Region( r, ( short )7, r, ( short )9 ) );
    logger.info( "成功写入" );

}
}
}
