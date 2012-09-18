package com.cabletech.watchinfo.templates;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.watchinfo.beans.OneStaWatchBean;
import com.cabletech.watchinfo.beans.WatchBean;
import com.cabletech.watchinfo.beans.WatchStaResultBean;

public class WatchDetailTemplate extends Template{
    private static Logger logger =
        Logger.getLogger( "WatchDetailTemplate" );
    public WatchDetailTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    public void doExport( List lst, ExcelStyle excelstyle ){
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2;
        if(lst != null){
            Iterator iter = lst.iterator();
            while( iter.hasNext() ){
                DynaBean record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "executorname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "executorname" ).toString() );}

                if( record.get( "worktime" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "worktime" ).toString() );}

                if( record.get( "watchname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "watchname" ).toString() );}

                if( record.get( "sublinename" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "sublinename" ).toString() );}

                if( record.get( "content" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "content" ).toString() );}

                r++;
            }
        }
    }

    public void exportPointSta( WatchStaResultBean bean, ExcelStyle excelstyle ){

        activeSheet( 0 );

        this.curStyle = excelstyle.defaultStyleTenFont(this.workbook);
        OneStaWatchBean watchBean = ( OneStaWatchBean )bean.getWatchlist().
                                    get( 0 );
        String watchName = ( String )watchBean.getPlaceName();
        this.setCellValue( 2, 1, ( String )bean.getDaterange() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 2, ( String )bean.getContractor() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 3, watchName);
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 4, ( String )bean.getInfoneeded() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 5, ( String )bean.getInfodid() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 6, ( String )bean.getWatchexecuterate() );
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 7, ( String )bean.getUndorate());
        this.curRow.setHeight((short)480);
        this.setCellValue( 2, 8, ( String )bean.getAlertcount()  );
        this.curRow.setHeight((short)480);
    }
    /**
     * ����ͳ�Ʊ�
     * @param lst List
     */
    public void doExportSta( WatchStaResultBean bean, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyleTenFont(this.workbook);

        int r = 0;
        int iWatchSize = bean.getWatchlist().size();

        setCellValue( 0, r + 1, "��������ͳ����Ϣ" );
        setCellValue( 1, r + 1, "ͳ��ʱ��" );
        setCellValue( 2, r + 1, ( String )bean.getDaterange() );
        setCellValue( 3, r + 1, "" );
        setCellValue( 4, r + 1, "" );
        this.curSheet.addMergedRegion( new Region( r +1, ( short )2, r + 1, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 2, "" );
        setCellValue( 1, r + 2, "���δ�ά��λ" );
        setCellValue( 2, r + 2, ( String )bean.getContractor() );
        setCellValue( 3, r + 2, "" );
        setCellValue( 4, r + 2, "" );
        this.curSheet.addMergedRegion( new Region( r +2, ( short )2, r + 2, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 3, "" );
        setCellValue( 1, r + 3, "������������" );
        setCellValue( 2, r + 3, String.valueOf( iWatchSize ) );
        setCellValue( 3, r + 3, "" );
        setCellValue( 4, r + 3, "" );
        this.curSheet.addMergedRegion( new Region( r +3, ( short )2, r + 3, ( short )4 ) );
        this.curRow.setHeight((short)480);
        
        // �ܵĶ���������
        String infoneed = ( String )bean.getInfoneeded(); 

        for( int i = 0; i < iWatchSize; i++ ){

            OneStaWatchBean watchBean = ( OneStaWatchBean )bean.getWatchlist().
                                        get( i );
            String watchName = ( String )watchBean.getPlaceName() ;
            String num = ( String )watchBean.getInfoneed();
            this.activeRow(r + 4 + i);
            setCellValue( 0, "" );
            if( i == 0 ){
                setCellValue( 1, "���������б�" );
                setCellValue( 2, "��������" );
                setCellValue( 3, "������������" );
                setCellValue( 4, "��ռ����" );
                this.curRow.setHeight((short)450);
                r = r + 1;
                this.activeRow(r + 4 + i);
                setCellValue( 1, "" );
            }else{
                setCellValue( 1, "" );
            }
            setCellValue( 2, watchName );
            setCellValue( 3, num );
            setCellValue( 4, getPercent(num, infoneed) );
            //this.curSheet.addMergedRegion( new Region( r + 4 + i, ( short )2, r + 4 + i, ( short )4 ) );
            this.curRow.setHeight((short)450);
        }
        this.curSheet.addMergedRegion( new Region( r +3, ( short )1, r + 3 + iWatchSize, ( short )1 ) );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r + 3 + iWatchSize, ( short )0 ) );

        r = r + 3 + iWatchSize;
        setCellValue( 0, r + 1 , "��������ִ��ͳ��" );
        setCellValue( 1, r + 1 , "��������������Ϣ������" );
        setCellValue( 2, r + 1 , infoneed);
        setCellValue( 3, r + 1, "" );
        setCellValue( 4, r + 1, "" );
        this.curSheet.addMergedRegion( new Region( r + 1, ( short )2, r + 1, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 2,  "" );
        setCellValue( 1, r + 2,  "��������ִ����Ϣ������" );
        setCellValue( 2, r + 2, ( String )bean.getInfodid() );
        setCellValue( 3, r + 2, "" );
        setCellValue( 4, r + 2, "" );
        this.curSheet.addMergedRegion( new Region( r + 2, ( short )2, r + 2, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 3, "" );
        setCellValue( 1, r + 3, "��������ִ�������" );
        setCellValue( 2, r + 3, ( String )bean.getWatchexecuterate() );
        setCellValue( 3, r + 3, "" );
        setCellValue( 4, r + 3, "" );
        this.curSheet.addMergedRegion( new Region( r + 3, ( short )2, r + 3, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 4, "" );
        setCellValue( 1, r + 4, "��������δ�����" );
        setCellValue( 2, r + 4, ( String )bean.getUndorate() );
        setCellValue( 3, r + 4, "" );
        setCellValue( 4, r + 4, "" );
        this.curSheet.addMergedRegion( new Region( r + 4, ( short )2, r + 4, ( short )4 ) );
        
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 5, "" );
        setCellValue( 1, r + 5, "����������������" );
        setCellValue( 2, r + 5, ( String )bean.getAlertcount() );
        setCellValue( 3, r + 5, "" );
        setCellValue( 4, r + 5, "" );
        this.curSheet.addMergedRegion( new Region( r + 5, ( short )2, r + 5, ( short )4 ) );
        this.curRow.setHeight((short)480);
        setCellValue( 0, r + 6, "���������봦�����" );
        setCellValue( 1, r + 6, "" );
        setCellValue( 2, r + 6, "" );
        setCellValue( 3, r + 6, "" );
        setCellValue( 4, r + 6, "" );
        this.curSheet.addMergedRegion( new Region( r + 6, ( short )2, r + 6, ( short )4 ) );
        this.curRow.setHeight((short)1500);
        this.curSheet.addMergedRegion( new Region( r +1, ( short )0, r + 5, ( short )0 ) );
        setCellValue( 0, r + 6, "������˵��" );
        setCellValue( 1, r + 6, "" );
        setCellValue( 2, r + 6, "" );
        setCellValue( 3, r + 6, "" );
        setCellValue( 4, r + 6, "" );
        this.curSheet.addMergedRegion( new Region( r + 6, ( short )2, r + 6, ( short )4 ) );
        this.curRow.setHeight((short)1500);
        setCellValue( 0, r + 7, "" );
        setCellValue( 1, r + 7, "Ѳ��Աȷ��" );
        setCellValue( 2, r + 7, "" );
        setCellValue( 3, r + 7, "�γ�ȷ��" );
        setCellValue( 4, r + 7, "" );
        this.curSheet.addMergedRegion( new Region( r +6, ( short )0, r + 7, ( short )0 ) );
        this.curSheet.addMergedRegion( new Region( r +6, ( short )1, r + 6, ( short )4 ) );
        this.curSheet.addMergedRegion( new Region( r +7, ( short )1, r + 7, ( short )2 ) );
        this.curSheet.addMergedRegion( new Region( r +7, ( short )3, r + 7, ( short )4 ) );
        setCellValue( 0, r + 8, "�쵼��ʾ" );
        setCellValue( 1, r + 8, "" );
        setCellValue( 2, r + 8, "" );
        setCellValue( 3, r + 8, "" );
        setCellValue( 4, r + 8, "" );
        this.curSheet.addMergedRegion( new Region( r +8, ( short )1, r + 8, ( short )4 ) );
        this.curRow.setHeight((short)1500);
    }


    /**
     * ȡ����ռ�����İٷ���
     * @param num ����������
     * @param total �ܵĶ���������
     * @return
     */
    private String getPercent(String num , String total) {
    	double p = Double.parseDouble(num) / Double.parseDouble(total);
    	double r = (int)(p * 1000) + (p * 10000 % 10 < 5 ? 0 : 1);
    	return r/10 + "%";    	
    }

    public void doExportList( List lstbean, List lstvct, List listcheck ) throws Exception{

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
        font.setFontHeightInPoints( ( short )18 );
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
//        stylel.setBorderBottom( HSSFCellStyle.BORDER_THIN );
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
//        style2.setBorderBottom( HSSFCellStyle.BORDER_THIN );
//        style2.setBottomBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderLeft( HSSFCellStyle.BORDER_THIN );
//        style2.setLeftBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderRight( HSSFCellStyle.BORDER_THIN );
//        style2.setRightBorderColor( HSSFColor.BLACK.index );
//        style2.setBorderTop( HSSFCellStyle.BORDER_THIN );
//        style2.setTopBorderColor( HSSFColor.BLACK.index );
        style2.setFont( fontl );
        style2.setVerticalAlignment( HSSFCellStyle.VERTICAL_CENTER ); //��ֱ����
        style2.setAlignment( HSSFCellStyle.ALIGN_CENTER );
        //�˲����С����
        HSSFCellStyle style5 = this.workbook.createCellStyle();
//        style5.setBorderBottom( HSSFCellStyle.BORDER_THIN );
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

        //����ÿ�еĿ��
        int r = 0;
        this.setCellValue( 0, r, "ʩ������ͳ�����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )11 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style );

        this.setCellValue( 12, r, "ʩ�����غ˲����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )12, r, ( short )20 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style4 );

        this.curRow.setHeight( ( short )480 );

        r = r + 1; //r=1
        this.activeRow( r );
        this.setCellValue( 0, r, "���" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( stylel );
        this.setCellValue( 1, r, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( stylel );
        this.setCellValue( 2, r, "����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( stylel );
        this.setCellValue( 3, r, "����λ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( stylel );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( stylel );
        this.setCellValue( 5, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( stylel );
        this.setCellValue( 6, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( stylel );
        this.setCellValue( 7, r, "�������ش������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( stylel );
        this.setCellValue( 8, r, "��Ӱ�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( stylel );
        this.setCellValue( 9, r, "��Ӱ���������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( stylel );
        this.setCellValue( 10, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( stylel );
        this.setCellValue( 11, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( stylel );
        this.setCellValue( 12, r, "��·�Ƿ��ֳ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style5 );
        this.setCellValue( 13, r, "�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style5 );
        this.setCellValue( 14, r, "���ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style5 );
        this.setCellValue( 15, r, "����ҵ��Ӱ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style5 );
        this.setCellValue( 16, r, "���跽ʽ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style5 );
        this.setCellValue( 17, r, "о��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style5 );
        this.setCellValue( 18, r, "ҵ������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style5 );
        this.setCellValue( 19, r, "Ѳ�췽ʽ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )19 ) ) ).setCellStyle( style5 );
        this.setCellValue( 20, r, "�����Ƿ���Ҫ���" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )20 ) ) ).setCellStyle( style5 );

        //ѭ��дwatchbean��Ϣ
        for( int j = 0; j < lstbean.size(); j++ ){
            WatchBean bean = ( WatchBean )lstbean.get( j );
            Vector vct = ( Vector )lstvct.get( j );
            Vector checkvec = ( Vector )listcheck.get( j );
            r = r + 1;
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

            this.setCellValue( 0, r, Integer.toString( j + 1 ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style2 );
            this.setCellValue( 1, r, contractorname );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style2 );
            this.setCellValue( 2, r, bean.getInnerregion() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( style2 );
            this.setCellValue( 3, r, bean.getWatchplace() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( style2 );
            this.setCellValue( 4, r, bean.getPlacetype() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( style2 );
            this.setCellValue( 5, r, bean.getDangerlevel() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( style2 );
            this.setCellValue( 6, r, bean.getWatchreason() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( style2 );
            this.setCellValue( 7, r, bean.getEndwatchinfo() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( style2 );
            this.setCellValue( 8, r, bean.getInvolvedlinenumber() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );

            this.setCellValue( 10, r, bean.getBeginDate() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );
            this.setCellValue( 11, r, bean.getEndDate() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style2 );

            if( bean.getDealstatus().equals( "2" ) ){
                //��·�Ƿ��飬�����
                if( bean.getIfcheckintime().equals( "1" ) ){
                    this.setCellValue( 12, r, "��" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style2 );
                }
                if( bean.getIfcheckintime().equals( "-1" ) ){
                    this.setCellValue( 12, r, "��" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )12 ) ) ).setCellStyle( style2 );
                }

            }

            int checkvctsize = 0;
            int vctsize = 0;
            int size = 0;
            if( bean.getDealstatus().equals( "2" ) ){
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

                        this.setCellValue( 9, r + i, ( String )oneVct.get( 1 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 15, r + i, ( String )oneVct.get( 2 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 16, r + i, ( String )oneVct.get( 3 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 17, r + i, ( String )oneVct.get( 4 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 18, r + i, ( String )oneVct.get( 5 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 19, r + i, ( String )oneVct.get( 6 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )19 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 20, r + i, ( String )oneVct.get( 7 ) );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )20 ) ) ).setCellStyle( style2 );

                    }
                    else{
                        this.setCellValue( 9, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 15, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )15 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 16, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )16 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 17, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )17 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 18, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )18 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 19, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )19 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 20, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )20 ) ) ).setCellStyle( style2 );
                    }

                    if( i < checkvctsize ){
                        String check = checkvec.get( i ).toString();
                        String checkinfo = "";
                        checkinfo = check.substring( 1, check.indexOf(","));
                        String patroltime = "";
                        patroltime = check.substring(check.indexOf(",") + 2, check.length() - 12);

                        System.out.println( "checkinfo:" + checkinfo );
                        System.out.println("patroltime:" + patroltime);
                        this.setCellValue( 13, r + i, checkinfo );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 14, r + i, patroltime );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style2 );
                    }
                    else{
                        this.setCellValue( 13, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )13 ) ) ).setCellStyle( style2 );
                        this.setCellValue( 14, r + i, " " );
                        ( ( HSSFCell ) ( this.curRow.getCell( ( short )14 ) ) ).setCellStyle( style2 );
                    }
                }
            }
            //���úϲ��� ������vct.size()
            if( size > 0 ){
                this.curSheet.addMergedRegion( new Region( r, ( short )0, r + size - 1, ( short )0 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )1, r + size - 1, ( short )1 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )2, r + size - 1, ( short )2 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )3, r + size - 1, ( short )3 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )4, r + size - 1, ( short )4 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )5, r + size - 1, ( short )5 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )6, r + size - 1, ( short )6 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )7, r + size - 1, ( short )7 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )8, r + size - 1, ( short )8 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )10, r + size - 1, ( short )10 ) );
                this.curSheet.addMergedRegion( new Region( r, ( short )11, r + size - 1, ( short )11 ) );

                if( bean.getDealstatus().equals( "2" ) ){
                    this.curSheet.addMergedRegion( new Region( r, ( short )12, r + size - 1, ( short )12 ) );
                }
            }
            if( !vct.isEmpty() || !checkvec.isEmpty() ){
                r = r + size - 1;
            }
            else{
                r = r + size;
            }
            this.curRow.setHeight( ( short )480 );
        }
    }

    public void doExportList( List lstbean ) throws Exception{
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

        Iterator iter = lstbean.iterator();
 //       System.out.println( "size:" + lstbean.size() );
        int r = 0;

        this.setCellValue( 0, r, "ʩ������ͳ�����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )9 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style );

        this.setCellValue( 10, r, "ʩ�����غ˲����" );
        this.curSheet.addMergedRegion( new Region( r, ( short )10, r, ( short )11 ) );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style4 );

        this.curRow.setHeight( ( short )480 );
        r = r + 1;
        this.setCellValue( 0, r, "���" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )0, ( short )4000 );
        this.setCellValue( 1, r, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )1, ( short )4000 );
        this.setCellValue( 2, r, "����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )2, ( short )4000 );
        this.setCellValue( 3, r, "����λ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )3, ( short )4000 );
        this.setCellValue( 4, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )4, ( short )4000 );
        this.setCellValue( 5, r, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )5, ( short )4000 );
        this.setCellValue( 6, r, "����ԭ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )6, ( short )4000 );
        this.setCellValue( 7, r, "�������ش������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )7, ( short )4000 );
        this.setCellValue( 8, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )8, ( short )4000 );
        this.setCellValue( 9, r, "����ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( stylel );
        this.curSheet.setColumnWidth( ( short )9, ( short )4000 );

        this.setCellValue( 10, r, "��·�Ƿ��ֳ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )10, ( short )4000 );
        this.setCellValue( 11, r, "�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style5 );
        this.curSheet.setColumnWidth( ( short )11, ( short )4000 );

        r = r + 1;
        int xuhao = 1;
        while( iter.hasNext() ){
            WatchBean bean = ( WatchBean )iter.next();

            this.activeRow( r );
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

            this.setCellValue( 0, r, Integer.toString( xuhao ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( style2 );
            this.setCellValue( 1, r, contractorname );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style2 );
            this.setCellValue( 2, r, bean.getInnerregion() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( style2 );
            this.setCellValue( 3, r, bean.getWatchplace() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( style2 );
            this.setCellValue( 4, r, bean.getPlacetype() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( style2 );
            this.setCellValue( 5, r, bean.getDangerlevel() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )5 ) ) ).setCellStyle( style2 );
            this.setCellValue( 6, r, bean.getWatchreason() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )6 ) ) ).setCellStyle( style2 );
            this.setCellValue( 7, r, bean.getEndwatchinfo() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )7 ) ) ).setCellStyle( style2 );

            this.setCellValue( 8, r, bean.getBeginDate() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )8 ) ) ).setCellStyle( style2 );
            this.setCellValue( 9, r, bean.getEndDate() );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )9 ) ) ).setCellStyle( style2 );

            if( bean.getDealstatus().equals( "2" ) ){
                //��·�Ƿ��飬�����
                if( bean.getIfcheckintime().trim().equals( "1" ) ){
                    this.setCellValue( 10, r, "��" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );
                }
                if( bean.getIfcheckintime().trim().equals( "-1" ) ){
                    this.setCellValue( 10, r, "��" );
                    ( ( HSSFCell ) ( this.curRow.getCell( ( short )10 ) ) ).setCellStyle( style2 );
                }
                this.setCellValue( 11, r, bean.getCheckresult() );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )11 ) ) ).setCellStyle( style2 );
            }
            this.curRow.setHeight( ( short )480 );

            r++;
            xuhao++;
        }
        //д�����(Ҫ���ø�ʽ)//
    }



    public void ExportTempWatchResult( List list, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

//                if( record.get( "gpscoordinate" ) == null ){setCellValue( 0, "" );}
//                else{setCellValue( 0, record.get( "gpscoordinate" ).toString() );}
                
                if( record.get( "gpscoordinate" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "x" ).toString() );}
                
                if( record.get( "gpscoordinate" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "y" ).toString() );}

                if( record.get( "regionname" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "regionname" ).toString() );}

                if( record.get( "simid" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "simid" ).toString() );}

                if( record.get( "receivetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "receivetime" ).toString() );}

                if( record.get( "bedited" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "bedited" ).toString() );}

                if( record.get( "pointname" ) == null ){setCellValue( 6, "" );}
                else{setCellValue( 6, record.get( "pointname" ).toString() );}

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }


    public void ExportWatchResult( List list, ExcelStyle excelstyle ) throws Exception{
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        int r = 2; //������
        logger.info( "�õ�list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "��ʼѭ��д������" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );
                if( record.get( "placename" ) == null ){setCellValue( 0, "" );}
               else{setCellValue( 0, record.get( "placename" ).toString() );}

               if( record.get( "contractorname" ) == null ){setCellValue( 1, "" );}
               else{setCellValue( 1, record.get( "contractorname" ).toString() );}

               if( record.get( "innerregion" ) == null ){setCellValue( 2, "" );}
               else{setCellValue( 2, record.get( "innerregion" ).toString() );}

               if( record.get( "patrolname" ) == null ){setCellValue( 3, "" );}
               else{setCellValue( 3, record.get( "patrolname" ).toString() );}
               
               if( record.get( "placetype" ) == null ){setCellValue( 4, "" );}
               else{setCellValue( 4, record.get( "placetype" ).toString() );}

               if( record.get( "watchplace" ) == null ){setCellValue( 5, "" );}
               else{setCellValue( 5, record.get( "watchplace" ).toString() );}

               if( record.get( "begindate" ) == null ){setCellValue( 6, "" );}
               else{setCellValue( 6, record.get( "begindate" ).toString() );}

               if( record.get( "enddate" ) == null ){setCellValue( 7, "" );}
               else{setCellValue( 7, record.get( "enddate" ).toString() );}

                r++; //��һ��
            }
            logger.info( "�ɹ�д��" );
        }
    }

}
