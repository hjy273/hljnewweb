package com.cabletech.statistics.templates;

import java.io.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.statistics.beans.*;
import com.cabletech.utils.*;

public class PlanAppRateTemplate extends Template{
    public PlanAppRateTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    public void doExport( ApproveRateBean appRate, ExcelStyle excelstyle ){
        activeSheet( 0 );
        this.curStyle = excelstyle.tenFourLine(this.workbook);

        setCellValue( 1, 1 ,appRate.getContractorname() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 2 ,appRate.getApproveplantype() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 3 ,appRate.getApprovetimes() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 4 ,appRate.getStatistictime() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 5 ,String.valueOf( appRate.getPlannum() ) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 6 ,String.valueOf( appRate.getAppplannum() ) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 7 ,String.valueOf( appRate.getUnapproveplannum() ) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 8 ,appRate.getApproverate() );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 9 ,appRate.getUnapproverate() );
        this.curRow.setHeight((short)480);

    }

}
