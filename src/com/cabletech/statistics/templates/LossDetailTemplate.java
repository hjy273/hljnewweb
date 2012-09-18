package com.cabletech.statistics.templates;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;

public class LossDetailTemplate extends Template{
    public LossDetailTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    public void doExport( List list, ExcelStyle excelstyle ){
        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 2; //行索引
        if( list != null ){
            Iterator iter = list.iterator();
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "patrolname" ) == null ){
                    setCellValue( 0, "" );
                }
                else{
                    setCellValue( 0, record.get( "patrolname" ).toString() );
                }
                if( record.get( "linename" ) == null ){
                    setCellValue( 1, "" );
                }
                else{
                    setCellValue( 1, record.get( "linename" ).toString() );
                }
                if( record.get( "sublinename" ) == null ){
                    setCellValue( 2, "" );
                }
                else{
                    setCellValue( 2, record.get( "sublinename" ).toString() );
                }
                if( record.get( "pointname" ) == null ){
                    setCellValue( 3, "" );
                }
                else{
                    setCellValue( 3, record.get( "pointname" ).toString() );
                }
                if( record.get( "addressinfo" ) == null ){
                    setCellValue( 4, "" );
                }
                else{
                    setCellValue( 4, record.get( "addressinfo" ).toString() );
                }
                if( record.get( "isfocus" ) == null ){
                    setCellValue( 5, "" );
                }
                else{
                    setCellValue( 5, record.get( "isfocus" ).toString() );
                }
                if( record.get( "planexe" ) == null ){
                    setCellValue( 6, "" );
                }
                else{
                    setCellValue( 6, record.get( "planexe" ).toString() );
                }
                if( record.get( "losstime" ) == null ){
                    setCellValue( 7, "" );
                }
                else{
                    setCellValue( 7, record.get( "losstime" ).toString() );
                }
                r++; //下一行

            }

        }
    }
}
