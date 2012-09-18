package com.cabletech.toolsmanage.Templates;

import java.io.*;
import java.util.*;
import org.apache.commons.beanutils.*;
import org.apache.log4j.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.partmanage.action.*;
import org.apache.poi.hssf.util.Region;
import com.cabletech.toolsmanage.beans.ToolsInfoBean;

public class ToolsTemplate extends Template{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    public ToolsTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }
    public void exportStockResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                int r = 0;

                if(  bean != null ){

                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "�����" );
                        this.setCellValue( 1, r, bean.getUsername() );
                        this.setCellValue( 2, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "��ʼʱ��" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "����ʱ��" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "��ѯ����" );
                    this.setCellValue( 1, r, "����" );
                    this.setCellValue( 2, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                    r++;
                }
                this.setCellValue( 0, r, "������ⵥһ����" );
                this.setCellValue( 1, r, "" );
                this.setCellValue( 2, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )2 ) );
                this.curRow.setHeight( ( short )1000 );
                r++;

                this.setCellValue( 0, r, "��ⵥλ" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 1, r, "�����" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 2, r, "���ʱ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "contractorname" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "username" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "time" ).toString() );}

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportRevokeResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                int r = 0;

                if(  bean != null ){

                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "������" );
                        this.setCellValue( 1, r, bean.getUsername() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )3 ) );
                        r++;
                    }
                    if( !bean.getRemark().equals( "" ) ){
                        this.setCellValue( 0, r, "����ԭ��" );
                        this.setCellValue( 1, r, bean.getRemark() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )3 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "��ʼʱ��" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )3 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "����ʱ��" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );
                        this.setCellValue( 3, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )3 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "��ѯ����" );
                    this.setCellValue( 1, r, "����" );
                    this.setCellValue( 2, r, "" );
                    this.setCellValue( 3, r, "" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )3 ) );
                    r++;
                }
                this.setCellValue( 0, r, "�������ϵ�һ����" );
                this.setCellValue( 1, r, "" );
                this.setCellValue( 2, r, "" );
                this.setCellValue( 3, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )3 ) );
                this.curRow.setHeight( ( short )1000 );
                r++;

                this.setCellValue( 0, r, "���ϵ�λ" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 1, r, "������" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 2, r, "����ʱ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 3, r, "����ԭ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "contractorname" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "username" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "time" ).toString() );}

                if( record.get( "remark" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "remark" ).toString() );}
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportUseResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if(  bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getUsername() );
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
            if( !bean.getUsename().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getUsename() );
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
            if( !bean.getUseunit().equals( "" ) ){
                this.setCellValue( 0, r, "���õ�λ" );
                this.setCellValue( 1, r, bean.getUseunit() );
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
            if( !bean.getUseremark().equals( "" ) ){
                this.setCellValue( 0, r, "����ԭ��" );
                this.setCellValue( 1, r, bean.getUseremark() );
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
            if( !bean.getBack().equals( "" ) ){
                this.setCellValue( 0, r, "�黹���" );
                this.setCellValue( 1, r, bean.getBack() );
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
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
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
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "����" );
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
        this.setCellValue( 0, r, "�������õ�һ����" );
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

        this.setCellValue( 0, r, "���õ�λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "������;" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "useunit" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "useunit" ).toString() );}

                if( record.get( "usename" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "usename" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "time" ).toString() );}

                if( record.get( "useremark" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "useremark" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "username" ).toString() );}
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportBackResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getUsername() );
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
            if( !bean.getUsename().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getUsename() );
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
            if( !bean.getUseunit().equals( "" ) ){
                this.setCellValue( 0, r, "���õ�λ" );
                this.setCellValue( 1, r, bean.getUseunit() );
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
            if( !bean.getUseremark().equals( "" ) ){
                this.setCellValue( 0, r, "����ԭ��" );
                this.setCellValue( 1, r, bean.getUseremark() );
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
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
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
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "����" );
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
        this.setCellValue( 0, r, "�������õ�һ����" );
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

        this.setCellValue( 0, r, "���õ�λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "������;" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "useunit" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "useunit" ).toString() );}

                if( record.get( "usename" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "usename" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "time" ).toString() );}

                if( record.get( "useremark" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "useremark" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "username" ).toString() );}
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportMainResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                int r = 0;

                if( bean != null ){

                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "������" );
                        this.setCellValue( 1, r, bean.getUsername() );
                        this.setCellValue( 2, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                    if( !bean.getMainremark().equals( "" ) ){
                        this.setCellValue( 0, r, "����ԭ��" );
                        this.setCellValue( 1, r, bean.getMainremark() );
                        this.setCellValue( 2, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }

                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "��ʼʱ��" );
                        this.setCellValue( 1, r, bean.getBegintime() );
                        this.setCellValue( 2, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "����ʱ��" );
                        this.setCellValue( 1, r, bean.getEndtime() );
                        this.setCellValue( 2, r, "" );

                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "��ѯ����" );
                    this.setCellValue( 1, r, "����" );
                    this.setCellValue( 2, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )2 ) );
                    r++;
                }
                this.setCellValue( 0, r, "�������޵�һ����" );
                this.setCellValue( 1, r, "" );
                this.setCellValue( 2, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleBoldFont( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )2 ) );
                this.curRow.setHeight( ( short )1000 );
                r++;

                this.setCellValue( 0, r, "������" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 1, r, "����ʱ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 2, r, "����ԭ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "username" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "username" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "time" ).toString() );}

                if( record.get( "mainremark" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "mainremark" ).toString() );}

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportToMainResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getUsername() );
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
            if( !bean.getMainunit().equals( "" ) ){
                this.setCellValue( 0, r, "ά�޵�λ" );
                this.setCellValue( 1, r, bean.getMainunit() );
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
            if( !bean.getMainname().equals( "" ) ){
                this.setCellValue( 0, r, "������" );
                this.setCellValue( 1, r, bean.getMainname() );
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
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
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
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "����" );
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
        this.setCellValue( 0, r, "�������õ�һ����" );
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

        this.setCellValue( 0, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "ά�޵�λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "mainname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "mainname" ).toString() );}

                if( record.get( "mainunit" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "mainunit" ).toString() );}

                if( record.get( "username" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "username" ).toString() );}

                if( record.get( "time" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "time" ).toString() );}

                if( record.get( "mainremark" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "mainremark" ).toString() );}
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportStorageResult( List list, ToolsInfoBean bean, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getContractorname().equals( "" ) ){
                 this.setCellValue( 0, r, "��λ����" );
                 this.setCellValue( 1, r, " " + bean.getContractorname() );
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
            if( !bean.getName().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, bean.getName() );
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
            if( !bean.getType().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, bean.getType() );
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
            if( !bean.getEsselownumber().equals("") | !bean.getEssehighnumber().equals("")){
                this.setCellValue( 0, r, "�����" );
                if(!bean.getEsselownumber().equals("")){
                    this.setCellValue( 1, r, " ���� " + bean.getEsselownumber() );
                }else{
                    this.setCellValue( 1, r, "" );
                }
                if(!bean.getEssehighnumber().equals("")){
                    this.setCellValue( 2, r, "С�� " + bean.getEssehighnumber() );
                }else{
                    this.setCellValue( 2, r, "" );
                }
                if( bean.getEsselownumber().equals( "" ) && !bean.getEssehighnumber().equals( "" ) ){
                    this.setCellValue( 1, r, "С�� " + bean.getEssehighnumber() );
                    this.setCellValue( 2, r, "" );
                }

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
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
            if(!bean.getPortlownumber().equals("") | !bean.getPorthighnumber().equals("")){
                this.setCellValue( 0, r, "�ɲ��Ͽ��" );
                if(!bean.getPortlownumber().equals("")){
                    this.setCellValue( 1, r, " ���� " + bean.getPortlownumber() );
                }else{
                    this.setCellValue( 1, r, "" );
                }
                if(!bean.getPorthighnumber().equals("")){
                    this.setCellValue( 2, r, "С�� " + bean.getPorthighnumber() );
                }else{
                     this.setCellValue( 2, r, "" );
                }
                if(bean.getPortlownumber().equals("") && !bean.getPorthighnumber().equals("")){
                    this.setCellValue( 1, r, "С�� " + bean.getPorthighnumber() );
                    this.setCellValue( 2, r, "");
                }
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
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
        }
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "����" );
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
        this.setCellValue( 0, r, "�������һ����" );
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

        this.setCellValue( 0, r, "��λ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "����ͺ�" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 6, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) != null ){
                    setCellValue( 0, record.get( "contractorname" ).toString() );
                }
                else{
                    setCellValue( 0, "" );
                }
                if( record.get( "name" ) != null ){
                    setCellValue( 1, record.get( "name" ).toString() );
                }
                else{
                    setCellValue( 1, "" );
                }
                if( record.get( "unit" ) != null ){

                    setCellValue( 2, record.get( "unit" ).toString() );
                }
                else{
                    setCellValue( 2, "" );
                }
                if( record.get( "type" ) != null ){

                    setCellValue( 3, record.get( "type" ).toString() );
                }
                else{
                    setCellValue( 3, "" );
                }
                if( record.get( "essenumber" ) != null ){

                    setCellValue( 4, record.get( "essenumber" ).toString() );
                }
                else{
                    setCellValue( 4, "" );
                }
                if( record.get( "portmainnumber" ) != null ){

                    setCellValue( 5, record.get( "portmainnumber" ).toString() );
                }
                else{
                    setCellValue( 5, "" );
                }
                if( record.get( "mainnumber" ) != null ){

                    setCellValue( 6, record.get( "mainnumber" ).toString() );
                }
                else{
                    setCellValue( 6, "" );
                }
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportBaseResult( List list, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        int r = 2; //������
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "name" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "name" ).toString() );}

                if( record.get( "unit" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "unit" ).toString() );}

                if( record.get( "type" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "type" ).toString() );}

                if( record.get( "source" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "source" ).toString() );}

                if( record.get( "factory" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "factory" ).toString() );}
                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );

        }
    }


    public void exportStockList( List list, ToolsInfoBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                int r = 0;

                if( bean != null ){

                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 1, r, "�����" );
                        this.setCellValue( 2, r, bean.getUsername() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );

                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 1, r, "��ʼʱ��" );
                        this.setCellValue( 2, r, bean.getBegintime() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 1, r, "����ʱ��" );
                        this.setCellValue( 2, r, bean.getEndtime() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 1, r, "��ѯ����" );
                    this.setCellValue( 2, r, "����" );
                    this.setCellValue( 0, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                        workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                    r++;
                }
                this.setCellValue( 0, r, "������ⵥ��ϸ��Ϣ" );
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
        int n = 1; //���
        List stocklist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( stocklist.size() != 0 ){
            Iterator iter = stocklist.iterator();

            for( int i = 0; i < stocklist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��λ����" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 3, r, "������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "username" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "���ʱ��" );
                if( record.get( "time" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "time" ).toString() );
                }
                setCellValue( 3, r, "��ע��Ϣ" );
                if( record.get( "remark" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "remark" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );

                r++;

                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "�������" );
                setCellValue( 3, r, "������λ" );
                setCellValue( 4, r, "����ͺ�" );
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                r++;
                for( int j = 0; j < partlist.size(); j++ ){
                    record2 = ( DynaBean )partlist.get( j );
                    if( record2.get( "stockid" ).toString().equals( record.get( "stockid" ).toString() ) ){
                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "enumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "enumber" ).toString() );
                        }
                        if( record2.get( "unit" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "unit" ).toString() );
                        }
                        if( record2.get( "type" ) == null ){
                            setCellValue( 4, r, "" );
                        }
                        else{
                            setCellValue( 4, r, record2.get( "type" ).toString() );
                        }
                        this.curRow.setHeight( ( short )360 );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }

    public void exportRevokeList( List list, ToolsInfoBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                int r = 0;

                if( bean != null ){

                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 1, r, "������" );
                        this.setCellValue( 2, r, bean.getUsername() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getRemark().equals( "" ) ){
                        this.setCellValue( 1, r, "����ԭ��" );
                        this.setCellValue( 2, r, bean.getRemark() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 1, r, "��ʼʱ��" );
                        this.setCellValue( 2, r, bean.getBegintime() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 1, r, "����ʱ��" );
                        this.setCellValue( 2, r, bean.getEndtime() );
                        this.setCellValue( 0, r, "" );
                        this.setCellValue( 3, r, "" );
                        this.setCellValue( 4, r, "" );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                            workbook ) );
                        this.curRow.setHeight( ( short )500 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 1, r, "��ѯ����" );
                    this.setCellValue( 2, r, "����" );
                    this.setCellValue( 0, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                    r++;
                }
                this.setCellValue( 0, r, "�������ϵ�һ����" );
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
        int n = 1; //���
        List stocklist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( stocklist.size() != 0 ){
            Iterator iter = stocklist.iterator();

            for( int i = 0; i < stocklist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��λ����" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 3, r, "������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "username" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "����ʱ��" );
                if( record.get( "time" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "time" ).toString() );
                }
                setCellValue( 3, r, "����ԭ��" );
                if( record.get( "remark" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "remark" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );

                r++;

                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "�������" );
                setCellValue( 3, r, "������λ" );
                setCellValue( 4, r, "����ͺ�" );
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );

                r++;

                for( int j = 0; j < partlist.size(); j++ ){
                    record2 = ( DynaBean )partlist.get( j );

                    if( record2.get( "stockid" ).toString().equals( record.get( "stockid" ).toString() ) ){
                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "enumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "enumber" ).toString() );
                        }
                        if( record2.get( "unit" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "unit" ).toString() );
                        }
                        if( record2.get( "type" ) == null ){
                            setCellValue( 4, r, "" );
                        }
                        else{
                            setCellValue( 4, r, record2.get( "type" ).toString() );
                        }
                        this.curRow.setHeight( ( short )360 );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }

    public void exportUseList( List list, ToolsInfoBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 1, r, "������" );
                this.setCellValue( 2, r, bean.getUsername() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getUsename().equals( "" ) ){
                this.setCellValue( 1, r, "������" );
                this.setCellValue( 2, r, bean.getUsename() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getUseunit().equals( "" ) ){
                this.setCellValue( 1, r, "���õ�λ" );
                this.setCellValue( 2, r, bean.getUseunit() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getUseremark().equals( "" ) ){
                this.setCellValue( 1, r, "����ԭ��" );
                this.setCellValue( 2, r, bean.getUseremark() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getUseremark().equals( "" ) ){
                this.setCellValue( 1, r, "�黹���" );
                this.setCellValue( 2, r, bean.getUseremark() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 1, r, "��ʼʱ��" );
                this.setCellValue( 2, r, bean.getBegintime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 1, r, "����ʱ��" );
                this.setCellValue( 2, r, bean.getEndtime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.
                    workbook ) );
                this.curRow.setHeight( ( short )500 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
        }
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 1, r, "��ѯ����" );
            this.setCellValue( 2, r, "����" );
            this.setCellValue( 0, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );

            this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }
        this.setCellValue( 0, r, "�������õ���ϸ��Ϣ" );
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

        int n = 1; //���
        List uselist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( uselist.size() != 0 ){
            Iterator iter = uselist.iterator();

            for( int i = 0; i < uselist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��λ����" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "username" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "����ʱ��" );
                if( record.get( "time" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "time" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "���õ�λ" );
                if( record.get( "useunit" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "useunit" ).toString() );
                }

                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "������" );
                if( record.get( "usename" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "usename" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "����ԭ��" );
                if( record.get( "useremark" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "useremark" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );

                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "��������" );
                setCellValue( 3, r, "��������" );
                setCellValue( 4, r, "������λ" );
                setCellValue( 5, r, "����ͺ�" );
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                r++;
                logger.info( "д��һ�����������Ϣ" );

                for( int j = 0; j < partlist.size(); j++ ){
                    record2 = ( DynaBean )partlist.get( j );
                    if( record2.get( "useid" ).toString().equals( record.get( "useid" ).toString() ) ){
                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "enumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "enumber" ).toString() );
                        }
                        if( record2.get( "bnumber" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "bnumber" ).toString() );
                        }
                        if( record2.get( "unit" ) == null ){
                            setCellValue( 4, r, "" );
                        }
                        else{
                            setCellValue( 4, r, record2.get( "unit" ).toString() );
                        }
                        if( record2.get( "type" ) == null ){
                            setCellValue( 5, r, "" );
                        }
                        else{
                            setCellValue( 5, r, record2.get( "type" ).toString() );
                        }
                        this.curRow.setHeight( ( short )360 );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }

    public void exportMainList( List list, ToolsInfoBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 1, r, "������" );
                this.setCellValue( 2, r, bean.getUsername() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getMainremark().equals( "" ) ){
                this.setCellValue( 1, r, "����ԭ��" );
                this.setCellValue( 2, r, bean.getMainremark() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }

            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 1, r, "��ʼʱ��" );
                this.setCellValue( 2, r, bean.getBegintime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 1, r, "����ʱ��" );
                this.setCellValue( 2, r, bean.getEndtime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );

                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                r++;
            }
        }
        else{
            this.setCellValue( 1, r, "��ѯ����" );
            this.setCellValue( 2, r, "����" );
            this.setCellValue( 0, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );

            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
            r++;
        }
        this.setCellValue( 0, r, "�������޵�һ����" );
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
        int n = 1; //���
        List uselist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( uselist.size() != 0 ){
            Iterator iter = uselist.iterator();
            for( int i = 0; i < uselist.size(); i++ ){
                record = ( DynaBean )iter.next();
                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��λ����" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "username" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "����ʱ��" );
                if( record.get( "time" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "time" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "����ԭ��" );
                if( record.get( "mainremark" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "mainremark" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "��������" );
                setCellValue( 3, r, "������λ" );
                setCellValue( 4, r, "����ͺ�" );
                setCellValue( 5, r, "��������" );
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                r++;
                for( int j = 0; j < partlist.size(); j++ ){
                    record2 = ( DynaBean )partlist.get( j );
                    if( record2.get( "mainid" ).toString().equals( record.get( "mainid" ).toString() ) ){
                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "enumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "enumber" ).toString() );
                        }
                        if( record2.get( "unit" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "unit" ).toString() );
                        }
                        if( record2.get( "type" ) == null ){
                            setCellValue( 4, r, "" );
                        }
                        else{
                            setCellValue( 4, r, record2.get( "type" ).toString() );
                        }
                        if( record2.get( "style" ) == null ){
                            setCellValue( 5, r, "" );
                        }
                        else{
                            setCellValue( 5, r, record2.get( "style" ).toString() );
                        }
                        this.curRow.setHeight( ( short )360 );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }

    public void exportToMainList( List list, ToolsInfoBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getUsername().equals( "" ) ){
                this.setCellValue( 1, r, "������" );
                this.setCellValue( 2, r, bean.getUsername() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
            if( !bean.getMainunit().equals( "" ) ){
                this.setCellValue( 1, r, "ά�޵�λ" );
                this.setCellValue( 2, r, bean.getMainunit() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
            if( !bean.getMainname().equals( "" ) ){
                this.setCellValue( 1, r, "������" );
                this.setCellValue( 2, r, bean.getMainname() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 1, r, "��ʼʱ��" );
                this.setCellValue( 2, r, bean.getBegintime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 1, r, "����ʱ��" );
                this.setCellValue( 2, r, bean.getEndtime() );
                this.setCellValue( 0, r, "" );
                this.setCellValue( 3, r, "" );
                this.setCellValue( 4, r, "" );
                this.setCellValue( 5, r, "" );
                this.setCellValue( 6, r, "" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
                r++;
            }
        }
        else{
            logger.info("���ԡ���������������������");
            this.setCellValue( 1, r, "��ѯ����" );
            this.setCellValue( 2, r, "����" );
            this.setCellValue( 0, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            this.setCellValue( 6, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
            this.curRow.setHeight( ( short )500 );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )6 ) );
            r++;
        }
        this.setCellValue( 0, r, "�������޵���ϸ��Ϣ" );
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
        int n = 1; //���
        List stocklist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( stocklist.size() != 0 ){
            Iterator iter = stocklist.iterator();

            for( int i = 0; i < stocklist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��λ����" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 3, r, "������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "username" ).toString() );
                }
                setCellValue( 5, r, "����ʱ��" );
                if( record.get( "time" ) == null ){
                    setCellValue( 6, r, "" );
                }
                else{
                    setCellValue( 6, r, record.get( "time" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "ά�޵�λ" );
                if( record.get( "mainunit" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "mainunit" ).toString() );
                }
                setCellValue( 3, r, "������" );
                if( record.get( "mainname" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "mainname" ).toString() );
                }
                setCellValue( 5, r, "��ϵ�绰" );
                if( record.get( "mainphone" ) == null ){
                    setCellValue( 6, r, "" );
                }
                else{
                    setCellValue( 6, r, record.get( "mainphone" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "ά�޵�ַ" );
                if( record.get( "mainadd" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "mainadd" ).toString() );
                }
                setCellValue( 3, r, "����ԭ��" );
                if( record.get( "mainremark" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "mainremark" ).toString() );
                }
                setCellValue( 5, r, "" );
                setCellValue( 6, r, "" );
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )4, r, ( short )6 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.workbook ) );
                r++;

                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "��������" );
                setCellValue( 3, r, "������λ" );
                setCellValue( 4, r, "����ͺ�" );
                setCellValue( 5, r, "��������" );
                setCellValue( 6, r, "" );
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )5, r, ( short )6 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.colorBoldFour( this.
                    workbook ) );
                r++;

                logger.info( "д��һ����������Ϣ" );
                for( int j = 0; j < partlist.size(); j++ ){
                    record2 = ( DynaBean )partlist.get( j );

                    if( record2.get( "mainid" ).toString().equals( record.get( "mainid" ).toString() ) ){

                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "enumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "enumber" ).toString() );
                        }
                        if( record2.get( "unit" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "unit" ).toString() );
                        }
                        if( record2.get( "type" ) == null ){
                            setCellValue( 4, r, "" );
                        }
                        else{
                            setCellValue( 4, r, record2.get( "type" ).toString() );
                        }
                        if( record2.get( "style" ) == null ){
                            setCellValue( 5, r, "" );
                        }
                        else{
                            setCellValue( 5, r, record2.get( "style" ).toString() );
                        }
                        setCellValue( 6, r, "" );

                        this.curRow.setHeight( ( short )360 );
                        this.curSheet.addMergedRegion( new Region( r, ( short )5, r, ( short )6 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }
}
