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
        setCellValue( 0, "�ƻ���Ϣ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
        setCellValue( 1, "�ƻ�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getPlanname() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);

        activeRow(2);
        setCellValue( 0, "" );
        setCellValue( 1, "�ƻ�ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getYear() + " �� " + plan.getMonth() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        for( int i = 0; i < taskVct.size(); i++ ){
            TaskBean task = ( TaskBean )taskVct.get( i );
            activeRow(3+i);
            setCellValue( 0, "" );
            if( i == 0){
                setCellValue( 1, "����" );
                ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            }else{
                setCellValue( 1, "" );
            }
            setCellValue( 2,
                task.getLineleveltext() + "    " + task.getDescribtion() +
                "    " + task.getExcutetimes() +
                "��" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            this.curRow.setHeight((short)480);
        }
        activeRow(3+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "�ƻ��ƶ���" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getCreator() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(4+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "�ƻ��ƶ�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getCreatedate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(5+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "��ά��λ�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getIfinnercheck() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(6+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "�ƶ���˾�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getStatus() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(7+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getApprover() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(8+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "��������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, plan.getApprovedate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(9+taskVct.size());
        setCellValue( 0, "�ƻ�ִ�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLineCenter(this.workbook) );
        setCellValue( 1, "��ά��λ" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getStaobject() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(10+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "ͳ��ʱ��" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getFormtime() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(11+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "�ƻ�������" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getPlancount() + "  ��� " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(12+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "ʵ�ʹ�����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getRealcount() + "  ��� " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(13+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "©�칤����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getLosscount() + "  ��� " );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(14+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "Ѳ����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getPatrolrate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        activeRow(15+taskVct.size());
        setCellValue( 0, "" );
        setCellValue( 1, "©����" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, patrolrate.getLossrate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight((short)480);
        this.curSheet.addMergedRegion( new Region(1, ( short )0, 8 + taskVct.size(), ( short )0 ) );
        this.curSheet.addMergedRegion( new Region(9 + taskVct.size(), ( short )0, 15 + taskVct.size(), ( short )0 ) );
        this.curSheet.addMergedRegion( new Region(3, ( short )1, 2 + taskVct.size(), ( short )1 ) );

    }

}
