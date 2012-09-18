package com.cabletech.statistics.templates;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.planinfo.beans.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;
import org.apache.poi.hssf.util.Region;

public class ContractorPlanFormTemplate extends Template{

    public ContractorPlanFormTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    public void doExport( PatrolRate patrolrate, YearMonthPlanBean plan,
        Vector taskVct, ExcelStyle excelstyle ){

        activeSheet( 0 );
        this.curStyle = excelstyle.defaultStyle(this.workbook);

        activeRow(1);
        setCellValue( 0, "计划信息" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
        setCellValue( 1, "计划名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getPlanname() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);

        activeRow(2);
        setCellValue( 0, "" );
        setCellValue( 1, "计划时间" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getYear() + " 年 " + plan.getMonth() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        for( int i = 0; i < taskVct.size(); i++ ){
            TaskBean task = ( TaskBean )taskVct.get( i );
            activeRow(3+i);
            setCellValue( 0, "" );
            if( i == 0){
                setCellValue( 1, "任务" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            }else{
                setCellValue( 1, "" );
            }
            setCellValue( 2,
                task.getLineleveltext() + "    " + task.getDescribtion() +
                "    " + task.getExcutetimes() +
                "次" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            this.curRow.setHeight((short)480);
        }
        activeRow(3+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "计划制定人" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getCreator() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(4+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "计划制定日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getCreatedate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(5+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "代维单位审批情况" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getIfinnercheck() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(6+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "移动公司审批情况" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getStatus() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(7+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "审批人" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getApprover() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(8+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "审批日期" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getApprovedate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(9+taskVct.size());
        setCellValue( 0, "计划执行情况" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
        setCellValue( 1, "代维单位" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getStaobject() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(10+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "统计时间" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getFormtime() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(11+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "计划工作量" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getPlancount() + "  点次 " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(12+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "实际工作量" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getRealcount() + "  点次 " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(13+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "漏检工作量" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getLosscount() + "  点次 " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(14+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "巡检率" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getPatrolrate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(15+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "漏检率" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getLossrate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        this.curSheet.addMergedRegion( new Region(1, ( short )0, 8 + taskVct.size(), ( short )0 ) );
        this.curSheet.addMergedRegion( new Region(9 + taskVct.size(), ( short )0, 15 + taskVct.size(), ( short )0 ) );
        this.curSheet.addMergedRegion( new Region(3, ( short )1, 2 + taskVct.size(), ( short )1 ) );

    }

}
