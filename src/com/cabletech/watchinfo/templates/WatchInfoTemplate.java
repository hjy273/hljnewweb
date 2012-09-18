package com.cabletech.watchinfo.templates;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.utils.*;
import com.cabletech.watchinfo.beans.*;

public class WatchInfoTemplate extends Template{
    public WatchInfoTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    public void doExport( WatchBean bean, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyleTenFont(this.workbook);
        this.setCellValue( 2, 1, bean.getPlaceName());
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 2, bean.getInnerregion() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 3, bean.getPlacetype() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 4, bean.getWatchplace() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 5, bean.getWatchreason());
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 6, bean.getDangerlevel());
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 7, bean.getWatchwidth() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 8, bean.getPrincipal() );
        this.curRow.setHeight((short)480);
        
        // modify by guixy 2008-11-13
        //this.setCellValue( 2, 9, bean.getGpscoordinate() );
        this.setCellValue( 2, 9, "���ȣ�" + bean.getX());
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 10, "γ�ȣ�" + bean.getY() );
        
        
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 11, bean.getBeginDate() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 12, bean.getEndDate() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 13, bean.getOrderlyBeginTime() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 14, bean.getOrderlyEndTime() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 15, bean.getError() );
        this.curRow.setHeight((short)480);
    }


    /**
     * ����ʩ���˲�ͳ�Ʊ��й�����Ϣ��
     * @param bean WatchBean
     * @param vct Vector
     */
    public void doWatchConstructExport( Vector checkvec, WatchBean bean, Vector vct ) throws SQLException{
        activeSheet( 0 );
//�������ʽ..ʩ�����
        HSSFCellStyle style = this.workbook.createCellStyle();
//        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        style.setBottomBorderColor( HSSFColor.BLACK.index );
//        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        style.setLeftBorderColor( HSSFColor.BLACK.index );
//        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        style.setRightBorderColor( HSSFColor.BLACK.index );
//        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        style.setFillBackgroundColor( HSSFColor.GOLD.index ); //������ɫ
        style.setFillForegroundColor( HSSFColor.GOLD.index );
        style.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont font = this.workbook.createFont(); //��������
        font.setFontName( "����" );
        font.setFontHeightInPoints( ( short )16 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );

//�˲����
        HSSFCellStyle style4 = this.workbook.createCellStyle();
//        style4.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        style4.setBottomBorderColor( HSSFColor.BLACK.index );
//        style4.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        style4.setLeftBorderColor( HSSFColor.BLACK.index );
//        style4.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        style4.setRightBorderColor( HSSFColor.BLACK.index );
//        style4.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        style4.setTopBorderColor( HSSFColor.BLACK.index );
        style4.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style4.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        HSSFPalette palette = this.workbook.getCustomPalette();

//replacing the standard red with freebsd.org red
        palette.setColorAtIndex( HSSFColor.RED.index,
            ( byte )147, //RGB red (0-255)
            ( byte )112, //RGB green
            ( byte )219 //RGB blue
            );
//replacing lime with freebsd.org gold
        palette.setColorAtIndex( HSSFColor.VIOLET.index, ( byte )204, ( byte )153, ( byte )255 );

        style4.setFillBackgroundColor( HSSFColor.VIOLET.index ); //������ɫ
        style4.setFillForegroundColor( HSSFColor.VIOLET.index );
        style4.setFillPattern( HSSFCellStyle.ALIGN_FILL );
        style4.setFont( font );

//ʩ�����С������ʽ
        HSSFCellStyle stylel = this.workbook.createCellStyle();
// sx       stylel.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        stylel.setBottomBorderColor( HSSFColor.BLACK.index );
//        stylel.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        stylel.setLeftBorderColor( HSSFColor.BLACK.index );
//        stylel.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        stylel.setRightBorderColor( HSSFColor.BLACK.index );
//        stylel.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        stylel.setTopBorderColor( HSSFColor.BLACK.index );
        stylel.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        stylel.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        stylel.setFillBackgroundColor( HSSFColor.LIGHT_GREEN.index ); //������ɫ
        stylel.setFillForegroundColor( HSSFColor.LIGHT_GREEN.index );
        stylel.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont fontl = this.workbook.createFont(); //��������
        fontl.setFontHeightInPoints( ( short )10 );
        fontl.setFontName( "����" );
        stylel.setFont( fontl );

        HSSFCellStyle style2 = this.workbook.createCellStyle();
// dd       style2.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        style2.setBottomBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        style2.setLeftBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        style2.setRightBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        style2.setTopBorderColor( HSSFColor.BLACK.index );
        style2.setFont( fontl );
        style2.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����

//�˲����С����
        HSSFCellStyle style5 = this.workbook.createCellStyle();
//dd        style5.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        style5.setBottomBorderColor( HSSFColor.BLACK.index );
//        style5.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        style5.setLeftBorderColor( HSSFColor.BLACK.index );
//        style5.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        style5.setRightBorderColor( HSSFColor.BLACK.index );
//        style5.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        style5.setTopBorderColor( HSSFColor.BLACK.index );
        style5.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style5.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        style5.setFillBackgroundColor( HSSFColor.PALE_BLUE.index ); //������ɫ
        style5.setFillForegroundColor( HSSFColor.PALE_BLUE.index );
        style5.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont font5 = this.workbook.createFont(); //��������
        font5.setFontHeightInPoints( ( short )10 );
        font5.setFontName( "����" );
        style5.setFont( font5 );
        String contractorname = "";
        String sqlcon = "select contractorname from contractorinfo where contractorid = '" + bean.getContractorid()
                        + "'";
        try{
            ResultSet rscon = null;
            com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
            rscon = util.executeQuery( sqlcon );
            if( rscon.next() ){
                contractorname = rscon.getString( 1 );
            }
            rscon.close();
        }
        catch( Exception e ){
            e.printStackTrace();
        }

//д�����(Ҫ���ø�ʽ)
        int r = 0;
        this.setCellValue( 0, r, bean.getPlaceName() + "  ʩ������ͳ�����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )10 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style );

//        if( bean.getDealstatus().equals( "2" ) ){
        this.setCellValue( 11, r, bean.getPlaceName() + "  ʩ�����غ˲����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )11, r, ( short )18 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style4 );
//        }
        this.curRow.setHeight( ( short )480 );

//дС����(Ҫ���ø�ʽ)
        r = r + 1;
        this.activeRow( r );
        this.setCellValue( 0, r, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )0, ( short )4000 );
        this.setCellValue( 1, r, "����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )1, ( short )4000 );
        this.setCellValue( 2, r, "����λ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )2, ( short )4000 );
        this.setCellValue( 3, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )3, ( short )4000 );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )4, ( short )4000 );
        this.setCellValue( 5, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )5, ( short )4000 );
        this.setCellValue( 6, r, "�������ش������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )6, ( short )4000 );

        this.setCellValue( 7, r, "��Ӱ�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )7, ( short )4000 );
        this.setCellValue( 8, r, "��Ӱ���������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )8, ( short )4000 );

        this.setCellValue( 9, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )9, ( short )4000 );
        this.setCellValue( 10, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )10, ( short )4000 );

//        if( bean.getDealstatus().equals( "2" ) ){
        this.setCellValue( 11, r, "��·�Ƿ��ֳ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )11, ( short )4000 );
        this.setCellValue( 12, r, "�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )12, ( short )4000 );
        this.setCellValue( 13, r, "����ҵ��Ӱ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )13, ( short )4000 );
        this.setCellValue( 14, r, "���跽ʽ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )14, ( short )4000 );
        this.setCellValue( 15, r, "о��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )15, ( short )4000 );
        this.setCellValue( 16, r, "ҵ������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )16, ( short )4000 );
        this.setCellValue( 17, r, "Ѳ�췽ʽ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )17, ( short )4000 );
        this.setCellValue( 18, r, "�����Ƿ���Ҫ���" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )18, ( short )4000 );

//        }

        r = r + 1;
        this.setCellValue( 0, r, contractorname );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style2 );
        this.setCellValue( 1, r, bean.getInnerregion() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style2 );
        this.setCellValue( 2, r, bean.getWatchplace() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( style2 );
        this.setCellValue( 3, r, bean.getPlacetype() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( style2 );
        this.setCellValue( 4, r, bean.getDangerlevel() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( style2 );
        this.setCellValue( 5, r, bean.getWatchreason() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( style2 );
        this.setCellValue( 6, r, bean.getEndwatchinfo() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( style2 );
        this.setCellValue( 7, r, bean.getInvolvedlinenumber() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( style2 );

        this.setCellValue( 9, r, bean.getBeginDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
        this.setCellValue( 10, r, bean.getEndDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );

        if( bean.getIfcheckintime().trim().equals( "1" ) ){
            this.setCellValue( 11, r, "��" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style2 );
        }
        if( bean.getIfcheckintime().trim().equals( "-1" ) ){
            this.setCellValue( 11, r, "��" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style2 );
        }
        int checkvctsize = 0;
        int vctsize = 0;
        int size = 0;
//        if( bean.getDealstatus().equals( "2" ) ){

        checkvctsize = checkvec.size();
        vctsize = vct.size();
        if( checkvctsize > vctsize ){
            size = checkvctsize;
        }
        else{
            size = vctsize;
        }
        for( int i = 0; i < size; i++ ){
            if( i < vctsize ){
                Vector oneVct = ( Vector )vct.get( i );

                //��·�Ƿ��飬�����

                this.setCellValue( 8, r + i, ( String )oneVct.get( 1 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );
                this.setCellValue( 13, r + i, ( String )oneVct.get( 2 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style2 );
                this.setCellValue( 14, r + i, ( String )oneVct.get( 3 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style2 );
                this.setCellValue( 15, r + i, ( String )oneVct.get( 4 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style2 );
                this.setCellValue( 16, r + i, ( String )oneVct.get( 5 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style2 );
                this.setCellValue( 17, r + i, ( String )oneVct.get( 6 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style2 );
                this.setCellValue( 18, r + i, ( String )oneVct.get( 7 ) );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style2 );
            }
            else{
                this.setCellValue( 8, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );
                this.setCellValue( 13, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style2 );
                this.setCellValue( 14, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style2 );
                this.setCellValue( 15, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style2 );
                this.setCellValue( 16, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style2 );
                this.setCellValue( 17, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style2 );
                this.setCellValue( 18, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style2 );
            }

            if( i < checkvctsize ){
                String checkinfo = checkvec.get( i ).toString();
                checkinfo = checkinfo.substring( 1, checkinfo.length() - 1 );
                this.setCellValue( 12, r + i, checkinfo );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style2 );
            }
            else{
                this.setCellValue( 12, r + i, " " );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style2 );
            }
        }
//        }

        this.curRow.setHeight( ( short )480 );

        if( size > 0 ){
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r + size - 1, ( short )0 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r + size - 1, ( short )1 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r + size - 1, ( short )2 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )3, r + size - 1, ( short )3 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )4, r + size - 1, ( short )4 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )5, r + size - 1, ( short )5 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )6, r + size - 1, ( short )6 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )7, r + size - 1, ( short )7 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )9, r + size - 1, ( short )9 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )10, r + size - 1, ( short )10 ) );
            if( bean.getDealstatus().equals( "2" ) ){
                this.curSheet.addMergedRegion( new Region( r, ( short )11, r + size - 1, ( short )11 ) );
            }
        }
    }


//����ʩ��������Ϣ���й�����Ϣ��
    public void doWatchConstructExport( WatchBean bean, Vector vct ) throws SQLException{
        activeSheet( 0 );
//�������ʽ..ʩ�����
        HSSFCellStyle style = this.workbook.createCellStyle();
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        style.setFillBackgroundColor( HSSFColor.GOLD.index ); //������ɫ
        style.setFillForegroundColor( HSSFColor.GOLD.index );
        style.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont font = this.workbook.createFont(); //��������
        font.setFontName( "����" );
        font.setFontHeightInPoints( ( short )16 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );
        HSSFPalette palette = this.workbook.getCustomPalette();

//replacing the standard red with freebsd.org red
        palette.setColorAtIndex( HSSFColor.RED.index,
            ( byte )147, //RGB red (0-255)
            ( byte )112, //RGB green
            ( byte )219 //RGB blue
            );
//replacing lime with freebsd.org gold
        palette.setColorAtIndex( HSSFColor.VIOLET.index, ( byte )204, ( byte )153, ( byte )255 );
//ʩ�����С������ʽ
        HSSFCellStyle stylel = this.workbook.createCellStyle();
        stylel.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        stylel.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        stylel.setFillBackgroundColor( HSSFColor.LIGHT_GREEN.index ); //������ɫ
        stylel.setFillForegroundColor( HSSFColor.LIGHT_GREEN.index );
        stylel.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont fontl = this.workbook.createFont(); //��������
        fontl.setFontHeightInPoints( ( short )10 );
        fontl.setFontName( "����" );
        stylel.setFont( fontl );

        HSSFCellStyle style2 = this.workbook.createCellStyle();
        style2.setFont( fontl );
        style2.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����

        String contractorname = "";
        String sqlcon = "select contractorname from contractorinfo where contractorid = '" + bean.getContractorid()
                        + "'";
        try{
            ResultSet rscon = null;
            com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
            rscon = util.executeQuery( sqlcon );
            if( rscon.next() ){
                contractorname = rscon.getString( 1 );
            }
            rscon.close();
        }
        catch( Exception e ){
            e.printStackTrace();
        }

//д�����(Ҫ���ø�ʽ)
        int r = 0;
        this.setCellValue( 0, r, bean.getPlaceName() + "  ʩ������ͳ�����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )10 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style );

        this.curRow.setHeight( ( short )480 );

//дС����(Ҫ���ø�ʽ)
        r = r + 1;
        this.activeRow( r );
        this.setCellValue( 0, r, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )0, ( short )4000 );
        this.setCellValue( 1, r, "����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )1, ( short )4000 );
        this.setCellValue( 2, r, "����λ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )2, ( short )4000 );
        this.setCellValue( 3, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )3, ( short )4000 );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )4, ( short )4000 );
        this.setCellValue( 5, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )5, ( short )4000 );
        this.setCellValue( 6, r, "�������ش������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )6, ( short )4000 );

        this.setCellValue( 7, r, "��Ӱ�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )7, ( short )4000 );
        this.setCellValue( 8, r, "��Ӱ���������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )8, ( short )4000 );

        this.setCellValue( 9, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )9, ( short )4000 );
        this.setCellValue( 10, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )10, ( short )4000 );

        r = r + 1;
        this.setCellValue( 0, r, contractorname );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style2 );
        this.setCellValue( 1, r, bean.getInnerregion() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style2 );
        this.setCellValue( 2, r, bean.getWatchplace() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( style2 );
        this.setCellValue( 3, r, bean.getPlacetype() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( style2 );
        this.setCellValue( 4, r, bean.getDangerlevel() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( style2 );
        this.setCellValue( 5, r, bean.getWatchreason() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( style2 );
        this.setCellValue( 6, r, bean.getEndwatchinfo() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( style2 );
        this.setCellValue( 7, r, bean.getInvolvedlinenumber() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( style2 );

        this.setCellValue( 9, r, bean.getBeginDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
        this.setCellValue( 10, r, bean.getEndDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );
        int size = 0;
        size = vct.size();
        for( int i = 0; i < size; i++ ){
            Vector oneVct = ( Vector )vct.get( i );
            this.setCellValue( 8, r + i, ( String )oneVct.get( 1 ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );
        }

        this.curRow.setHeight( ( short )480 );

        if( size > 0 ){
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r + size - 1, ( short )0 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )1, r + size - 1, ( short )1 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )2, r + size - 1, ( short )2 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )3, r + size - 1, ( short )3 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )4, r + size - 1, ( short )4 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )5, r + size - 1, ( short )5 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )6, r + size - 1, ( short )6 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )7, r + size - 1, ( short )7 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )9, r + size - 1, ( short )9 ) );
            this.curSheet.addMergedRegion( new Region( r, ( short )10, r + size - 1, ( short )10 ) );
        }

    }


//����ʩ���˲鱨���޹�����Ϣ��
    public void doWatchConstructExportNoCable( WatchBean bean ){

        activeSheet( 0 );
        //�������ʽ..ʩ�����
        HSSFCellStyle style = this.workbook.createCellStyle();
        //        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        //        style.setBottomBorderColor( HSSFColor.BLACK.index );
        //        style.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        //        style.setLeftBorderColor( HSSFColor.BLACK.index );
        //        style.setBorderRight( HSSFCellStyle.BORDER_THIN );
        //        style.setRightBorderColor( HSSFColor.BLACK.index );
        //        style.setBorderTop( HSSFCellStyle.BORDER_THIN );
        //        style.setTopBorderColor( HSSFColor.BLACK.index );
        style.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        style.setFillBackgroundColor( HSSFColor.GOLD.index ); //������ɫ
        style.setFillForegroundColor( HSSFColor.GOLD.index );
        style.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont font = this.workbook.createFont(); //��������
        font.setFontName( "����" );
        font.setFontHeightInPoints( ( short )16 );
        font.setBoldweight( ( short )1000000 );
        style.setFont( font );

        //�˲����
        HSSFCellStyle style4 = this.workbook.createCellStyle();
        //        style4.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        //        style4.setBottomBorderColor( HSSFColor.BLACK.index );
        //        style4.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        //        style4.setLeftBorderColor( HSSFColor.BLACK.index );
        //        style4.setBorderRight( HSSFCellStyle.BORDER_THIN );
        //        style4.setRightBorderColor( HSSFColor.BLACK.index );
        //        style4.setBorderTop( HSSFCellStyle.BORDER_THIN );
        //        style4.setTopBorderColor( HSSFColor.BLACK.index );
        style4.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style4.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        HSSFPalette palette = this.workbook.getCustomPalette();

        //replacing the standard red with freebsd.org red
        palette.setColorAtIndex( HSSFColor.RED.index,
            ( byte )147, //RGB red (0-255)
            ( byte )112, //RGB green
            ( byte )219 //RGB blue
            );
        //replacing lime with freebsd.org gold
        palette.setColorAtIndex( HSSFColor.VIOLET.index, ( byte )204, ( byte )153, ( byte )255 );

        style4.setFillBackgroundColor( HSSFColor.VIOLET.index ); //������ɫ
        style4.setFillForegroundColor( HSSFColor.VIOLET.index );
        style4.setFillPattern( HSSFCellStyle.ALIGN_FILL );
        style4.setFont( font );

        //ʩ�����С������ʽ
        HSSFCellStyle stylel = this.workbook.createCellStyle();
        stylel.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        stylel.setBottomBorderColor( HSSFColor.BLACK.index );
        stylel.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        stylel.setLeftBorderColor( HSSFColor.BLACK.index );
        stylel.setBorderRight( HSSFCellStyle.BORDER_THIN );
        stylel.setRightBorderColor( HSSFColor.BLACK.index );
        stylel.setBorderTop( HSSFCellStyle.BORDER_THIN );
        stylel.setTopBorderColor( HSSFColor.BLACK.index );
        stylel.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        stylel.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        stylel.setFillBackgroundColor( HSSFColor.LIGHT_GREEN.index ); //������ɫ
        stylel.setFillForegroundColor( HSSFColor.LIGHT_GREEN.index );
        stylel.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont fontl = this.workbook.createFont(); //��������
        fontl.setFontHeightInPoints( ( short )10 );
        fontl.setFontName( "����" );
        stylel.setFont( fontl );

        HSSFCellStyle style2 = this.workbook.createCellStyle();
        style2.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style2.setBottomBorderColor( HSSFColor.BLACK.index );
        style2.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style2.setLeftBorderColor( HSSFColor.BLACK.index );
        style2.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style2.setRightBorderColor( HSSFColor.BLACK.index );
        style2.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style2.setTopBorderColor( HSSFColor.BLACK.index );
        style2.setFont( fontl );
        style2.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����

        //�˲����С����
        HSSFCellStyle style5 = this.workbook.createCellStyle();
        style5.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style5.setBottomBorderColor( HSSFColor.BLACK.index );
        style5.setBorderLeft( HSSFCellStyle.BORDER_THIN );
        style5.setLeftBorderColor( HSSFColor.BLACK.index );
        style5.setBorderRight( HSSFCellStyle.BORDER_THIN );
        style5.setRightBorderColor( HSSFColor.BLACK.index );
        style5.setBorderTop( HSSFCellStyle.BORDER_THIN );
        style5.setTopBorderColor( HSSFColor.BLACK.index );
        style5.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style5.setAlignment( HSSFCellStyle.ALIGN_CENTER );

        style5.setFillBackgroundColor( HSSFColor.PALE_BLUE.index ); //������ɫ
        style5.setFillForegroundColor( HSSFColor.PALE_BLUE.index );
        style5.setFillPattern( HSSFCellStyle.ALIGN_FILL );

        HSSFFont font5 = this.workbook.createFont(); //��������
        font5.setFontHeightInPoints( ( short )10 );
        font5.setFontName( "����" );
        style5.setFont( font5 );
        String contractorname = "";
        String sqlcon = "select contractorname from contractorinfo where contractorid = '" + bean.getContractorid()
                        + "'";
        try{
            java.sql.ResultSet rs = null;
            com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
            rs = util.executeQuery( sqlcon );
            if( rs.next() ){
                contractorname = rs.getString( 1 );
            }
            rs.close();
        }
        catch( Exception e ){
            e.printStackTrace();
        }

        //д�����(Ҫ���ø�ʽ)
        int r = 0;
        this.setCellValue( 0, r, bean.getPlaceName() + "  ʩ������ͳ�����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )8 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style );

        if( bean.getDealstatus().equals( "2" ) ){
            this.setCellValue( 9, r, bean.getPlaceName() + "  ʩ�����غ˲����" );
            this.curSheet.addMergedRegion( new Region( r, ( short )9, r, ( short )10 ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style4 );
        }
        this.curRow.setHeight( ( short )480 );

        //дС����(Ҫ���ø�ʽ)
        r = r + 1;

        this.activeRow( r );
        this.setCellValue( 0, r, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )0, ( short )4000 );
        this.setCellValue( 1, r, "����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )1, ( short )4000 );
        this.setCellValue( 2, r, "����λ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )2, ( short )4000 );
        this.setCellValue( 3, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )3, ( short )4000 );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )4, ( short )4000 );
        this.setCellValue( 5, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )5, ( short )4000 );
        this.setCellValue( 6, r, "�������ش������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )6, ( short )4000 );
        this.setCellValue( 7, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )7, ( short )4000 );
        this.setCellValue( 8, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )8, ( short )4000 );

        if( bean.getDealstatus().equals( "2" ) ){
            this.setCellValue( 9, r, "��·�Ƿ��ֳ����" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style5 );
            this.curSheet.setColumnWidth( ( short )9, ( short )4000 );
            this.setCellValue( 10, r, "�����" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style5 );
            this.curSheet.setColumnWidth( ( short )10, ( short )4000 );
        }

        r = r + 1;
        this.setCellValue( 0, r, contractorname );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style2 );
        this.setCellValue( 1, r, bean.getInnerregion() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style2 );
        this.setCellValue( 2, r, bean.getWatchplace() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( style2 );
        this.setCellValue( 3, r, bean.getPlacetype() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( style2 );
        this.setCellValue( 4, r, bean.getDangerlevel() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( style2 );
        this.setCellValue( 5, r, bean.getWatchreason() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( style2 );
        this.setCellValue( 6, r, bean.getEndwatchinfo() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( style2 );

        this.setCellValue( 7, r, bean.getBeginDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( style2 );
        this.setCellValue( 8, r, bean.getEndDate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );

        if( bean.getDealstatus().equals( "2" ) ){
            //��·�Ƿ��飬�����
            if( bean.getIfcheckintime().trim().equals( "1" ) ){
                this.setCellValue( 9, r, "��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
            }
            if( bean.getIfcheckintime().trim().equals( "-1" ) ){
                this.setCellValue( 9, r, "��" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
            }
            this.setCellValue( 10, r, bean.getCheckresult() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );
        }
        this.curRow.setHeight( ( short )480 );

    }

}
