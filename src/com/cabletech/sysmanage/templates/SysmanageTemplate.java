package com.cabletech.sysmanage.templates;

import com.cabletech.partmanage.action.PartRequisitionAction;
import java.io.IOException;
import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.commons.beanutils.DynaBean;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.Region;

public class SysmanageTemplate extends Template{
    private static Logger logger = Logger.getLogger( PartRequisitionAction.class.
                                   getName() );
    public SysmanageTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    /**
     * 使用 DynaBean
     * @param list List
     */
    public void exportConPerson( List list, ExcelStyle excelstyle ){

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

                if( record.get( "sex" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "sex" ).toString() );}

                if( record.get( "phone" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "phone" ).toString() );}

                if( record.get( "mobile" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "mobile" ).toString() );}

                if( record.get( "contractorname" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "contractorname" ).toString() );}

                if( record.get( "familyaddress" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "familyaddress" ).toString() );}

                r++; //下一行
            }
            logger.info( "成功写入" );
        }
    }

    public void exportUserOnlineTimeResult( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        int sum = 0;
        logger.info( "得到list" );
        if(list != null){
            Iterator iter = list.iterator();
            logger.info( "开始循环写入数据" );
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "username" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "username" ).toString() );}

                if( record.get( "loginip" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "loginip" ).toString() );}

                if( record.get( "logintime" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "logintime" ).toString() );}

                if( record.get( "logouttime" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "logouttime" ).toString() );}

                if( record.get( "onlinetime" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "onlinetime" ).toString() );
                sum += Integer.parseInt(record.get( "onlinetime" ).toString());
                }

                r++; //下一行
            }
            activeRow( r);
            setCellValue( 0, "累计登录: "+Integer.toString(list.size()) + " 次      累计登录时间: "+Integer.toString(sum)+" 秒");
            setCellValue( 1, "" );
            setCellValue( 2, "");
            setCellValue( 3, "" );
            setCellValue( 4, "" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )3 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )4 ) ) ).setCellStyle( excelstyle.tenFourLine( this.workbook ) );
            this.curRow.setHeight((short)480);
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )4 ) );

            logger.info( "成功写入" );
        }
    }
}
