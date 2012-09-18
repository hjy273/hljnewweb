package com.cabletech.statistics.templates;

import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;

public class PatrolRateTemplate extends Template{

    public PatrolRateTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    /*
        public void doExport(List list) {

            Plan record;
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
                record = (Plan) iter.next();
                activeRow(r);

                setCellValue(0, record.getPatrolname());
                setCellValue(1, record.getContractorname());
                setCellValue(2, String.valueOf(record.getPlancount()));
                setCellValue(3, String.valueOf(record.getLosscount()));
                setCellValue(4, String.valueOf(record.getPatrolrate()));
                setCellValue(5, DateUtil.DateToString(record.getBegindate()));
                setCellValue(6, DateUtil.DateToString(record.getEnddate()));

                r++; //下一行

            }

        }
     */

    /**
     * 导出巡检率
     * @param patrolrate PatrolRate
     */
    public void doExport( PatrolRate patrolrate, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.tenFourLine(this.workbook);
        setCellValue( 0, 1, patrolrate.getStatype());
        setCellValue( 1, 1, patrolrate.getStaobject() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 2, patrolrate.getFormtime() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 3, patrolrate.getPlancount() + "  点次 " );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 4, patrolrate.getRealcount() + "  点次 " );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 5, patrolrate.getLosscount() + "  点次 " );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 6, patrolrate.getPatrolrate() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 7, patrolrate.getLossrate() );
        this.curRow.setHeight((short)480);

    }
}
