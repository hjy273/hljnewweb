package com.cabletech.partmanage.Templates;

import java.util.List;
import java.util.Iterator;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.commons.beanutils.DynaBean;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.partmanage.action.PartRequisitionAction;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.Region;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.partmanage.beans.Part_requisitionBean;

public class RequisitionTemplate extends Template{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    public RequisitionTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    /**
     * ʹ�� DynaBean
     * @param list List
     */
    public void doExport( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

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
                this.setCellValue( 0, r, "����������" );
                this.setCellValue( 1, r, "        " + bean.getUsername() );
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
            if( !bean.getAuditresult().equals( "" ) ){
                this.setCellValue( 0, r, "����״̬" );
                this.setCellValue( 1, r, "        " + bean.getAuditresult() );
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
            if( !bean.getcontractorid().equals( "" ) ){
                this.setCellValue( 0, r, "���뵥λ" );
                this.setCellValue( 1, r, "        " + bean.getContractorname() );
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
            if( !bean.getReason().equals( "" ) ){
                this.setCellValue( 0, r, "����ԭ��" );
                this.setCellValue( 1, r, "        " + bean.getReason() );
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
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
        this.setCellValue( 0, r, "ά������������һ����" );
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

        this.setCellValue( 0, r, "���뵥��ˮ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "���뵥λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "reid" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "reid" ).toString() );
                }

                if( record.get( "reason" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "reason" ).toString() );
                }
                
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "contractorname" ).toString() );
                }

                if( record.get( "username" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "username" ).toString() );
                }

                if( record.get( "time" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "time" ).toString() );
                }

                if( record.get( "auditresult" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "auditresult" ).toString() );
                }
                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );

    }


    public void doExportUse( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        //������ʽ
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;
        
        int colIndex = 5;
        
        if("2".equals(bean.getTotaltype()) || "3".equals(bean.getTotaltype())) {
        	colIndex = 4;
        } 

        if( bean != null ){

            if( !bean.getcontractorid().equals( "" ) ){
                this.setCellValue( 0, r, "ʹ�õ�λ" );
                this.setCellValue( 1, r, "        " + bean.getContractorname() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex) );
                r++;
            }
            if( !bean.getName().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, "        " + bean.getName() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
            }
            if( !bean.getType().equals( "" ) ){
                this.setCellValue( 0, r, "�����ͺ�" );
                this.setCellValue( 1, r, "        " + bean.getType() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
            }
            if( !bean.getFactory().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, "        " + bean.getFactory() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
            }
            if( !bean.getUsereason().equals( "" ) ){
                this.setCellValue( 0, r, "������;" );
                this.setCellValue( 1, r, "        " + bean.getUsereason() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
            }
            
            if(!"".equals(bean.getLevel())){
            	this.setCellValue(0,r,"��·����");
            	this.setCellValue(1,r,"        " + bean.getLevel());
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
                 this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                 r++;
            }
            
            if(!"".equals(bean.getSubline())){
            	this.setCellValue(0,r,"������·");
            	this.setCellValue(1,r,"        " + bean.getSubline());
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
                 this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                 r++;
            }
            
            if(!"".equals(bean.getSublineId())){
            	this.setCellValue(0,r,"�м̶�");
            	this.setCellValue(1,r,"        " + bean.getSublineId());
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
                 this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                 r++;
            }
            
            if(!"".equals(bean.getLinechangename())){
            	this.setCellValue(0,r,"�������");
            	this.setCellValue(1,r,"        " + bean.getLinechangename());
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
                 this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                 r++;
            }
            
            if(!"".equals(bean.getCutchangename())){
            	this.setCellValue(0,r,"��������");
            	this.setCellValue(1,r,"        " + bean.getCutchangename());
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
                 this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex) );
                 r++;
            }

            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
            }
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
                r++;
           }
        }
        else{
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )colIndex ) );
            r++;

        }        
        
        this.setCellValue( 0, r, "����ʹ��һ����" );
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
        
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )colIndex ) );
        this.curRow.setHeight( ( short )1000 );
        r++;

        this.setCellValue( 0, r, "��λ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "�����ͺ�" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );

        if(colIndex == 5) {
        	this.setCellValue( 4, r, "��Ʒ����" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            this.setCellValue( 5, r, "��Ʒ����" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );        	
        } else {
            this.setCellValue( 4, r, "ʹ����" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        }
        
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "contractorname" ).toString() );
                }

                if( record.get( "name" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "type" ).toString() );
                }

                if( record.get( "usenewnumber" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "usenewnumber" ).toString() );
                }

                if(colIndex == 5) {
                	if( record.get( "useoldnumber" ) == null ){
                        setCellValue( 5, "" );
                    }
                    else{
                        setCellValue( 5, record.get( "useoldnumber" ).toString() );
                    }
                }                

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportRequisitonResult( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

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
                this.setCellValue( 0, r, "����������" );
                this.setCellValue( 1, r, "        " + bean.getUsername() );
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
            if( !bean.getAuditresult().equals( "" ) ){
                this.setCellValue( 0, r, "����״̬" );
                this.setCellValue( 1, r, "        " + bean.getAuditresult() );
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
            if( !bean.getReason().equals( "" ) ){
                this.setCellValue( 0, r, "����ԭ��" );
                this.setCellValue( 1, r, "        " + bean.getReason() );
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
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
        else{
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
        this.setCellValue( 0, r, "ά���������뵥һ����" );
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

        this.setCellValue( 0, r, "���뵥��ˮ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "���뵥λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "����״̬" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;

        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "reid" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "reid" ).toString() );
                }

                if( record.get( "contractorname" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "contractorname" ).toString() );
                }

                if( record.get( "username" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "username" ).toString() );
                }

                if( record.get( "time" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "time" ).toString() );
                }

                if( record.get( "reason" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "reason" ).toString() );
                }

                if( record.get( "auditresult" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "auditresult" ).toString() );
                }

                this.curRow.setHeight( ( short )360 );
                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportStockResult( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

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
                        this.setCellValue( 0, r, "���������" );
                        this.setCellValue( 1, r, "        " + bean.getUsername() );
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
                    if( !bean.getReid().equals( "" ) ){
                        this.setCellValue( 0, r, "��Ӧ���뵥���" );
                        this.setCellValue( 1, r, "        " + bean.getReid() );
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
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "��ʼʱ��" );
                        this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "����ʱ��" );
                        this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
                else{
                    this.setCellValue( 0, r, "��ѯ����" );
                    this.setCellValue( 1, r, "        ����" );
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
                this.setCellValue( 0, r, "ά��������ⵥһ����" );
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

                this.setCellValue( 0, r, "��ⵥ��ˮ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 1, r, "��ⵥλ" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 2, r, "�����" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 3, r, "���ʱ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 4, r, "��Ӧ���뵥���" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 5, r, "��Ӧ����ԭ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "stockid" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "stockid" ).toString() );
                }

                if( record.get( "contractorname" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "contractorname" ).toString() );
                }

                if( record.get( "username" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "username" ).toString() );
                }

                if( record.get( "stocktime" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "stocktime" ).toString() );
                }

                if( record.get( "reid" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "reid" ).toString() );
                }

                if( record.get( "reason" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "reason" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportUseResult( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

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
                this.setCellValue( 0, r, "����������" );
                this.setCellValue( 1, r, "        " + bean.getUsername() );
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
            if( !bean.getUsereason().equals( "" ) ){
                this.setCellValue( 0, r, "������;" );
                this.setCellValue( 1, r, "        " + bean.getUsereason() );
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
            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
        else{
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
        this.setCellValue( 0, r, "���ϳ��ⵥһ����" );
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

        this.setCellValue( 0, r, "���ⵥ��ˮ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "���ⵥλ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "������;" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "��ע��Ϣ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "useid" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "useid" ).toString() );
                }

                if( record.get( "contractorname" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "contractorname" ).toString() );
                }

                if( record.get( "username" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "username" ).toString() );
                }

                if( record.get( "usetime" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "usetime" ).toString() );
                }

                if( record.get( "usereason" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "usereason" ).toString() );
                }

                if( record.get( "useremark" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "useremark" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportOldStockResult( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

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
                        this.setCellValue( 0, r, "���������" );
                        this.setCellValue( 1, r, "        " + bean.getUsername() );
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
                    if( !bean.getReid().equals( "" ) ){
                        this.setCellValue( 0, r, "������Դ" );
                        this.setCellValue( 1, r, "        " + bean.getReid() );
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
                        this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
                        this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
                    this.setCellValue( 0, r, "��ѯ����" );
                    this.setCellValue( 1, r, "        ����" );
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
                this.setCellValue( 0, r, "���ɲ�����ⵥһ����" );
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

                this.setCellValue( 0, r, "������ⵥ��ˮ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 1, r, "��λ����" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 2, r, "�����" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 3, r, "���ʱ��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
                this.setCellValue( 4, r, "������Դ" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "oldid" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "oldid" ).toString() );
                }

                if( record.get( "contractorname" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "contractorname" ).toString() );
                }

                if( record.get( "username" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "username" ).toString() );
                }

                if( record.get( "oldtime" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "oldtime" ).toString() );
                }

                if( record.get( "oldreason" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "oldreason" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportStorageResult( List list, Part_requisitionBean bean, ExcelStyle excelstyle, String newlownumber,
        String newhignumber,
        String oldlownumber,
        String oldhignumber
        ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

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
                this.setCellValue( 1, r, " " + bean.getName() );
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
                this.setCellValue( 1, r, " " + bean.getType() );
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
            if( !newlownumber.equals("") | !newhignumber.equals("")){
                this.setCellValue( 0, r, "�²��Ͽ��" );
                if(!newlownumber.equals("")){
                    this.setCellValue( 1, r, " ���� " + newlownumber );
                }else{
                    this.setCellValue( 1, r, "" );
                }
                if(!newhignumber.equals("")){
                    this.setCellValue( 2, r, "С�� " + newhignumber );
                }else{
                    this.setCellValue( 2, r, "" );
                }
                if( newlownumber.equals( "" ) && !newhignumber.equals( "" ) ){
                    this.setCellValue( 1, r, "С�� " + newhignumber );
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
            if(!oldlownumber.equals("") | !oldhignumber.equals("")){
                this.setCellValue( 0, r, "�ɲ��Ͽ��" );
                if(!oldlownumber.equals("")){
                    this.setCellValue( 1, r, " ���� " + oldlownumber );
                }else{
                    this.setCellValue( 1, r, "" );
                }
                if(!oldhignumber.equals("")){
                    this.setCellValue( 2, r, "С�� " + oldhignumber );
                }else{
                     this.setCellValue( 2, r, "" );
                }
                if(oldlownumber.equals("") && !oldhignumber.equals("")){
                    this.setCellValue( 1, r, "С�� " + oldhignumber );
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
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "    ����" );
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
        this.setCellValue( 0, r, "����ʹ��һ����" );
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
        this.setCellValue( 4, r, "�²���Ӧ�п��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "�²��Ͽ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 6, r, "�ɲ��Ͽ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );


        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "contractorname" ).toString() );
                }

                if( record.get( "name" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "type" ).toString() );
                }

                if( record.get( "newshould" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "newshould" ).toString() );
                }

                if( record.get( "newesse" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "newesse" ).toString() );
                }
                if( record.get( "oldnumber" ) == null ){
                    setCellValue( 6, "" );
                }
                else{
                    setCellValue( 6, record.get( "oldnumber" ).toString() );
                }
                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportBaseInfo( List list, ExcelStyle excelstyle ){

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
                if( record.get( "name" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "type" ).toString() );
                }

                if( record.get( "factory" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "factory" ).toString() );
                }

                if( record.get( "remark" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "remark" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }


    public void exportStockList( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );

        int r = 0; //������
        int n = 1; //���

        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                if( bean != null ){
                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "      ���������" );
                        this.setCellValue( 2, r, bean.getUsername() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getReid().equals( "" ) ){
                        this.setCellValue( 0, r, "      ��Ӧ���뵥���" );
                        this.setCellValue( 2, r, bean.getReid() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ��ʼʱ��" );
                        this.setCellValue( 2, r, bean.getBegintime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ����ʱ��" );
                        this.setCellValue( 2, r, bean.getEndtime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "      ��ѯ����" );
                    this.setCellValue( 2, r, "����" );
                    this.setCellValue( 1, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                    this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                    r++;

                }
                this.setCellValue( 0, r, "ά��������ⵥ��ϸ��Ϣ" );
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

        List stocklist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( stocklist.size() != 0 ){
            Iterator iter = stocklist.iterator();

            for( int i = 0; i < stocklist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��ⵥ��" );
                if( record.get( "stockid" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "stockid" ).toString() );
                }
                setCellValue( 3, r, "���ʱ��" );
                if( record.get( "stocktime" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "stocktime" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "�����" );
                if( record.get( "username" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "username" ).toString() );
                }
                setCellValue( 3, r, "��ⵥλ" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "contractorname" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );

                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "���뵥��" );
                if( record.get( "reid" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "reid" ).toString() );
                }
                setCellValue( 3, r, "����ԭ��" );
                if( record.get( "reason" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "reason" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );

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
                        if( record2.get( "stocknumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "stocknumber" ).toString() );
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
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.
                            workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }


    public void exportUseList( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        int r = 0; //������
        int n = 1; //���

        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                if( bean != null ){
                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "      ����������" );
                        this.setCellValue( 2, r, bean.getUsername() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                        r++;
                    }
                    if( !bean.getUsereason().equals( "" ) ){
                        this.setCellValue( 0, r, "      ������;" );
                        this.setCellValue( 2, r, bean.getUsereason() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ��ʼʱ��" );
                        this.setCellValue( 2, r, bean.getBegintime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ����ʱ��" );
                        this.setCellValue( 2, r, bean.getEndtime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "      ��ѯ����" );
                    this.setCellValue( 2, r, "����" );
                    this.setCellValue( 1, r, "" );
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
                    this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                    this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                    r++;

                }
                this.setCellValue( 0, r, "ά�����ϳ��ⵥ��ϸ��Ϣ" );
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
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "������;" );
                if( record.get( "username" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "username" ).toString() );
                }
                setCellValue( 3, r, "" );
                setCellValue( 4, r, "����ʱ��" );
                if( record.get( "usetime" ) == null ){
                    setCellValue( 5, r, "" );
                }
                else{
                    setCellValue( 5, r, record.get( "usetime" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��ע��Ϣ" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "contractorname" ).toString() );
                }
                setCellValue( 4, r, "" );
                setCellValue( 5, r, "" );
                this.curRow.setHeight( ( short )360 );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )5 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );

                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "��������" );
                setCellValue( 2, r, "�²��ϳ���" );
                setCellValue( 3, r, "�ɲ��ϳ���" );
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
                        if( record2.get( "usenewnumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "usenewnumber" ).toString() );
                        }
                        if( record2.get( "useoldnumber" ) == null ){
                            setCellValue( 3, r, "" );
                        }
                        else{
                            setCellValue( 3, r, record2.get( "useoldnumber" ).toString() );
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
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.
                            workbook ) );
                        r++;
                    }
                }
                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }


    public void exportOldUseList( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){
        DynaBean record;
        DynaBean record2;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        int r = 0; //������
        int n = 1; //���

        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
                this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
                //����ҳ��
                this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
                if( bean != null ){
                    if( !bean.getUsername().equals( "" ) ){
                        this.setCellValue( 0, r, "      ���������" );
                        this.setCellValue( 2, r, bean.getUsername() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getReid().equals( "" ) ){
                        this.setCellValue( 0, r, "      ������Դ" );
                        this.setCellValue( 2, r, bean.getReid() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getBegintime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ��ʼʱ��" );
                        this.setCellValue( 2, r, bean.getBegintime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                    if( !bean.getEndtime().equals( "" ) ){
                        this.setCellValue( 0, r, "      ����ʱ��" );
                        this.setCellValue( 2, r, bean.getEndtime() );
                        this.setCellValue( 1, r, "" );
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
                        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                        this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                        r++;
                    }
                }
                else{
                    this.setCellValue( 0, r, "      ��ѯ����" );
                    this.setCellValue( 2, r, "����" );
                    this.setCellValue( 1, r, "" );
                    this.setCellValue( 3, r, "" );
                    this.setCellValue( 4, r, "" );

                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldNoLine( this.
                        workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenNoLine( this.workbook ) );
                    this.curRow.setHeight( ( short )500 );
                    this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )1 ) );
                    this.curSheet.addMergedRegion( new Region( r, ( short )2, r, ( short )4 ) );
                    r++;

                }
                this.setCellValue( 0, r, "���ɲ�����ⵥ��ϸ��Ϣ" );
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

        List stocklist = ( List )list.get( 0 );
        List partlist = ( List )list.get( 1 );
        if( stocklist.size() != 0 ){
            Iterator iter = stocklist.iterator();

            for( int i = 0; i < stocklist.size(); i++ ){
                record = ( DynaBean )iter.next();

                setCellValue( 0, r, Integer.toString( n ) );
                setCellValue( 1, r, "��ⵥ���" );
                if( record.get( "oldid" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "oldid" ).toString() );
                }
                setCellValue( 3, r, "���ʱ��" );
                if( record.get( "oldtime" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "oldtime" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftTop( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "���������" );
                if( record.get( "username" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "username" ).toString() );
                }
                setCellValue( 3, r, "��ⵥλ" );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "contractorname" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );

                r++;
                setCellValue( 0, r, "" );
                setCellValue( 1, r, "������Դ" );
                if( record.get( "oldreason" ) == null ){
                    setCellValue( 2, r, "" );
                }
                else{
                    setCellValue( 2, r, record.get( "oldreason" ).toString() );
                }
                setCellValue( 3, r, "��ע��Ϣ" );
                if( record.get( "oldremark" ) == null ){
                    setCellValue( 4, r, "" );
                }
                else{
                    setCellValue( 4, r, record.get( "oldremark" ).toString() );
                }
                this.curRow.setHeight( ( short )360 );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.boldFourLine( this.
                    workbook ) );

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

                    if( record2.get( "oldid" ).toString().equals( record.get( "oldid" ).toString() ) ){
                        setCellValue( 0, r, "" );
                        if( record2.get( "name" ) == null ){
                            setCellValue( 1, r, "" );
                        }
                        else{
                            setCellValue( 1, r, record2.get( "name" ).toString() );
                        }
                        if( record2.get( "oldnumber" ) == null ){
                            setCellValue( 2, r, "" );
                        }
                        else{
                            setCellValue( 2, r, record2.get( "oldnumber" ).toString() );
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
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left( this.
                            workbook ) );
                        r++;
                    }
                }

                n++;
            }
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.leftBottom( this.workbook ) );
        }
    }

    public void doExportStock( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        //������ʽ
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getcontractorid().equals( "" ) ){
                this.setCellValue( 0, r, "��ⵥλ" );
                this.setCellValue( 1, r, "        " + bean.getContractorname() );
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
            if( !bean.getName().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, "        " + bean.getName() );
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
            if( !bean.getType().equals( "" ) ){
                this.setCellValue( 0, r, "�����ͺ�" );
                this.setCellValue( 1, r, "        " + bean.getType() );
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

            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
        else{
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
        this.setCellValue( 0, r, "�������һ����" );
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

        this.setCellValue( 0, r, "��λ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "�����ͺ�" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "���ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "contractorname" ).toString() );
                }

                if( record.get( "name" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "type" ).toString() );
                }

                if( record.get( "stocktime" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "stocktime" ).toString() );
                }

                if( record.get( "stocknumber" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "stocknumber" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }
    public void doExportOldStock( List list, Part_requisitionBean bean, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        //������ʽ
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        if( bean != null ){

            if( !bean.getcontractorid().equals( "" ) ){
                this.setCellValue( 0, r, "��ⵥλ" );
                this.setCellValue( 1, r, "        " + bean.getContractorname() );
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
            if( !bean.getName().equals( "" ) ){
                this.setCellValue( 0, r, "��������" );
                this.setCellValue( 1, r, "        " + bean.getName() );
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
            if( !bean.getType().equals( "" ) ){
                this.setCellValue( 0, r, "�����ͺ�" );
                this.setCellValue( 1, r, "        " + bean.getType() );
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

            if( !bean.getBegintime().equals( "" ) ){
                this.setCellValue( 0, r, "��ʼʱ��" );
                this.setCellValue( 1, r, "        " + bean.getBegintime() );
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
            if( !bean.getEndtime().equals( "" ) ){
                this.setCellValue( 0, r, "����ʱ��" );
                this.setCellValue( 1, r, "        " + bean.getEndtime() );
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
        else{
            this.setCellValue( 0, r, "��ѯ����" );
            this.setCellValue( 1, r, "        ����" );
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
        this.setCellValue( 0, r, "���ɲ������һ����" );
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

        this.setCellValue( 0, r, "��λ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "������λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "�����ͺ�" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "���ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( list != null ){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "contractorname" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "contractorname" ).toString() );
                }

                if( record.get( "name" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "type" ).toString() );
                }

                if( record.get( "stocktime" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "stocktime" ).toString() );
                }

                if( record.get( "oldnumber" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "oldnumber" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }
    public void doExportRequisitions( List list, Part_requisitionBean bean,List lists, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        //������ʽ
        this.curStyle = excelstyle.defaultStyle( this.workbook );
        //        this.curSheet.getHeader().setCenter("�����θ�������Ƽ���չ���޹�˾");
        this.curSheet.getHeader().setRight( "���ڣ�" + HSSFHeader.date() );
        //����ҳ��
        this.curSheet.getFooter().setCenter( "�� " + HSSFFooter.page() + " ҳ   �� " + HSSFFooter.numPages() + " ҳ " );
        int r = 0;

        this.setCellValue( 0, r, "ά���������뵥��ϸ��Ϣ��" );
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

        if(bean.getContractorId()!=null){
            this.setCellValue(0,r,"��ά��λ");
            this.setCellValue(1,r,bean.getContractorname());
        }
        if(bean.getUsername()!=null){
            this.setCellValue(2,r,"������");
            this.setCellValue(3,r,bean.getUsername());
        }
        if(bean.getTime()!=null){
            this.setCellValue(4,r,"����ʱ��");
            this.setCellValue(5,r,bean.getTime());
        }
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        r++;

        if(bean.getReason()!=null){
            this.setCellValue( 0, r, "��������" );
            this.setCellValue( 1, r, bean.getReason() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        if(bean.getRemark()!=null){
            this.setCellValue( 0, r, "��ע��Ϣ" );
            this.setCellValue( 1, r, bean.getReason() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        if(bean.getAudituserid()!=null){
            this.setCellValue( 0, r, "�� �� ��" );
            this.setCellValue( 1, r, bean.getAuditusername() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        if(bean.getAudittime()!=null){
            this.setCellValue( 0, r, "��������" );
            this.setCellValue( 1, r, bean.getAudittime() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        if(bean.getAuditresult()!=null){
            this.setCellValue( 0, r, "�������" );
            this.setCellValue( 1, r, bean.getAuditresult() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        if(bean.getAuditremark()!=null){
            this.setCellValue( 0, r, "������ע" );
            this.setCellValue( 1, r, bean.getAuditremark() );
            this.setCellValue( 2, r, "" );
            this.setCellValue( 3, r, "" );
            this.setCellValue( 4, r, "" );
            this.setCellValue( 5, r, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r, ( short )5 ) );
            r++;
        }

        this.setCellValue( 0, r, "�����뵥������Ĳ���:" );
        this.setCellValue( 1, r, "" );
        this.setCellValue( 2, r, "" );
        this.setCellValue( 3, r, "" );
        this.setCellValue( 4, r, "" );
        this.setCellValue( 5, r, "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )5 ) );
        r++;

        this.setCellValue( 0, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 1, r, "������λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 2, r, "����ͺ�" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 3, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        this.setCellValue( 5, r, "�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( excelstyle.tenBoldFourLine( this.workbook ) );
        r++;
        logger.info( "�õ�list" );
        if( lists != null ){
            Iterator iter = lists.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "name" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "name" ).toString() );
                }

                if( record.get( "unit" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "unit" ).toString() );
                }

                if( record.get( "type" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "type" ).toString() );
                }

                if( record.get( "renumber" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "renumber" ).toString() );
                }

                if( record.get( "audnumber" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "audnumber" ).toString() );
                }

                if( record.get( "stocknumber" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "stocknumber" ).toString() );
                }

                r++; //��һ��
            }
        }
        logger.info( "�ɹ�д��" );
    }
}
