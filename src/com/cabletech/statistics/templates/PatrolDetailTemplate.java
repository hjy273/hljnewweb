package com.cabletech.statistics.templates;

import java.io.*;
import java.util.*;

import org.apache.commons.beanutils.*;
import org.apache.poi.hssf.usermodel.*;
import com.cabletech.commons.exceltemplates.*;

public class PatrolDetailTemplate extends Template{
    public PatrolDetailTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    /*
         public void doExport(List list) {

        PatrolDetail record;
        activeSheet(0);

        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints( (short) 9);
        font.setFontName("宋体");
        font.setItalic(false);
        font.setStrikeout(false);

        curStyle.setFont(font);

        int r = 3; //行索引
        int c = 0; //列索引

        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            record = (PatrolDetail) iter.next();
            activeRow(r);

            setCellValue(0, record.getPatrolMan());
            setCellValue(1, record.getDepart());
            setCellValue(2, record.getSubline());
            setCellValue(3, record.getPoint());
            setCellValue(4, record.getPosition());
            setCellValue(5, record.getExecuteTime());

            r++; //下一行


        }

         }
     */

    /**
     * 使用 DynaBean
     * @param list List
     */
    public void doExport( List list, ExcelStyle excelstyle ){

        DynaBean record;
        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);
        int r = 3; //行索引
        if( list != null){
            Iterator iter = list.iterator();
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                activeRow( r );

                if( record.get( "patrolname" ) == null ){setCellValue( 0, "" );}
                else{setCellValue( 0, record.get( "patrolname" ).toString() );}

                if( record.get( "contractorname" ) == null ){setCellValue( 1, "" );}
                else{setCellValue( 1, record.get( "contractorname" ).toString() );}

                if( record.get( "sublinename" ) == null ){setCellValue( 2, "" );}
                else{setCellValue( 2, record.get( "sublinename" ).toString() );}

                if( record.get( "pointname" ) == null ){setCellValue( 3, "" );}
                else{setCellValue( 3, record.get( "pointname" ).toString() );}

                if( record.get( "position" ) == null ){setCellValue( 4, "" );}
                else{setCellValue( 4, record.get( "position" ).toString() );}

                if( record.get( "executetime" ) == null ){setCellValue( 5, "" );}
                else{setCellValue( 5, record.get( "executetime" ).toString() );}

                r++; //下一行

            }
        }
    }

}
