package com.cabletech.statistics.templates;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.hssf.util.HSSFColor.*;
import com.cabletech.commons.exceltemplates.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;
import javax.servlet.http.HttpServletRequest;

public class PlanFormTemplate extends Template{
    public PlanFormTemplate( String urlPath ) throws IOException{
        super( urlPath );

    }


    public void doExportNew( PlanStatisticForm planform, ExcelStyle excelstyle,HttpServletRequest request ){

        activeSheet( 0 );
        int r = 0; //行索引
        this.curStyle = excelstyle.defaultStyleTenFont(this.workbook);
        String flag = planform.getIfhasrecord(); //是否有记录
        activeRow( r );

        if( flag.equals( "1" ) ){
            setCellValue( 0, planform.getFormname() );
            setCellValue( 1, "" );
            setCellValue( 2, "" );
            this.curRow.setHeight( ( short )1000 );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.titleFont(this.workbook) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.titleFont(this.workbook) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.titleFont(this.workbook) );
            this.curSheet.addMergedRegion( new Region( r, ( short )0, r, ( short )2 ) );
        }
        else{
            setCellValue( 0, "没有计划信息可用，不能生成报表！" );
            return;
        }

        Vector planVct = planform.getPlanvct();

        for( int i = 0; i < planVct.size(); i++ ){
            Plan plan = ( Plan )planVct.get( i );
            Vector taskvct = plan.getTaskvct();

            this.activeRow( r + 1 );

            this.setCellValue( 0, plan.getPlanname() );
            this.setCellValue( 1, "计划名称" );
            this.setCellValue( 2, plan.getPlanname() );
            this.curRow.setHeight( ( short )800 );

            this.setCellValue( 0, r + 2, "" );
            String PMType = ( String )request.getSession().getAttribute( "PMType" ); //获得巡检人员的管理方式
            if( PMType.equals( "group" ) ){ //按组管理
                this.setCellValue( 1, r + 2, "巡检维护组" );
            }
            else{ //按人管理
                this.setCellValue( 1, r + 2, "执 行 人" );
            }


            this.setCellValue( 2, r + 2, plan.getPatrolname() );
            this.curRow.setHeight( ( short )400 );
            this.setCellValue( 0, r + 3, "" );
            this.setCellValue( 1, r + 3, "计划时间" );
            this.setCellValue( 2, r + 3, plan.getBegindateStr() + "  至  " + plan.getEnddateStr() );
            this.curRow.setHeight( ( short )400 );
            this.setCellValue( 0, r + 4, "" );
            this.setCellValue( 1, r + 4, "工 作 量" );
            this.setCellValue( 2, r + 4, plan.getPlanpointCount() + "点次" );
            this.curRow.setHeight( ( short )400 );
            if( taskvct != null && taskvct.size() > 0 ){
                for( int j = 0; j < taskvct.size(); j++ ){
                    Task task = ( Task )taskvct.get( j );

                    this.setCellValue( 0, r + 5 + j, "" );
                    if(j == 0){
                        this.setCellValue( 1, r + 5 + j, "计划任务" );
                        this.curRow.setHeight( ( short )400 );
                    }else{
                        this.setCellValue( 1, r + 5 + j, "" );
                    }
                    if(task.getTaskname()==null){
                        this.setCellValue( 2, r + 5 + j,
                            "      " + "   " + task.getTaskdisc() + "   " + task.getExecutes() + " 次" );
                    }else{
                        this.setCellValue( 2, r + 5 + j,
                            task.getTaskname() + "   " + task.getTaskdisc() + "   " + task.getExecutes() + " 次" );
                    }
                }

            }
            else{
                this.setCellValue( 2, r + 5, "无" );
                this.curRow.setHeight( ( short )400 );
            }
            this.curSheet.addMergedRegion( new Region( r + 5, ( short )1, r + 4 + taskvct.size() , ( short )1 ) );

            this.setCellValue( 0, r + 5 + taskvct.size(), "");
            this.setCellValue( 1, r + 5 + taskvct.size(), "制 定 人" );
            this.setCellValue( 2, r + 5 + taskvct.size(), plan.getContractorname() );
            this.curRow.setHeight( ( short )400 );

            this.setCellValue( 0, r + 6 + taskvct.size(), "");
            this.setCellValue( 1, r + 6 + taskvct.size(), "制定日期" );
            this.setCellValue( 2, r + 6 + taskvct.size(), plan.getCreatedate() );
            this.curRow.setHeight( ( short )400 );
            this.curSheet.addMergedRegion( new Region( r+1, ( short )0, r + 6 + taskvct.size(), ( short )0 ) );

            r =  r + 6 + taskvct.size();

        }
        r = r + 1;

        this.setCellValue( 0, r, "计划统计" );
        this.setCellValue( 1, r, "应检点次" );
        this.setCellValue( 2, r, planform.getPlancount() );
        this.curRow.setHeight( ( short )400 );
        this.setCellValue( 0, r + 1, "" );
        this.setCellValue( 1, r + 1, "实检点次" );
        this.setCellValue( 2, r + 1, planform.getRealcount() );
        this.curRow.setHeight( ( short )400 );
        this.setCellValue( 0, r + 2, "" );
        this.setCellValue( 1, r + 2, "漏检点次" );
        this.setCellValue( 2, r + 2, planform.getLosscount() );
        this.curRow.setHeight( ( short )400 );
        this.setCellValue( 0, r + 3, "" );
        this.setCellValue( 1, r + 3, "巡 检 率" );
        this.setCellValue( 2, r + 3, planform.getPatrolrate() );
        this.curRow.setHeight( ( short )400 );
        this.setCellValue( 0, r + 4, "" );
        this.setCellValue( 1, r + 4, "巡 检 率" );
        this.setCellValue( 2, r + 4, planform.getLossrate() );
        this.curRow.setHeight( ( short )400 );
        this.curSheet.addMergedRegion( new Region( r, ( short )0, r + 4, ( short )0 ) );

    }


    public void doExport( PlanStatisticForm planform ){

        activeSheet( 1 );
        HSSFCellStyle style = this.workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints( ( short )10 );
        font.setFontName( "宋体" );
        font.setItalic( false );
        font.setStrikeout( false );
        style.setFont( font );
        style.setBorderBottom( HSSFCellStyle.BORDER_THIN );
        style.setBottomBorderColor( BLACK.index );
        setCellValue( 1, 0, planform.getFormname() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style );
        setCellValue( 1, 3, DateUtil.getNowDateString() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( style );

        activeSheet( 2 );
        String flag = planform.getIfhasrecord(); //是否有记录

        font = workbook.createFont();
        font.setFontHeightInPoints( ( short )9 );
        font.setFontName( "宋体" );
        font.setItalic( false );
        font.setStrikeout( false );

        curStyle.setFont( font );

        int li = 0; //行索引

        activeRow( 0 );
        setCellValue( 0, flag );

        if( flag.equals( "1" ) ){
            activeRow( 1 );
            setCellValue( 0, "巡检员巡检计划与完成汇总表" );
            setCellValue( 1, planform.getFormname() );
        }
        else{
            activeRow( 1 );
            setCellValue( 0, "没有计划信息可用，不能生成报表！" );
            return;
        }

        Vector planVct = planform.getPlanvct();
        Vector dailyVct = planform.getDailypatrolvct();

        activeRow( 2 );
        setCellValue( 0, String.valueOf( dailyVct.size() ) );
        setCellValue( 1, String.valueOf( planVct.size() ) );

        li = 3;
        for( int i = 0; i < planVct.size(); i++ ){
            Plan plan = ( Plan )planVct.get( i );
            Vector taskVct = plan.getTaskvct();

            activeRow( li );

            setCellValue( 0, String.valueOf( taskVct.size() ) );

            for( int k = 1; k < taskVct.size() + 1; k++ ){

                Task task = ( Task )taskVct.get( k - 1 );
                setCellValue( k,
                    task.getTaskname() + "      " + task.getTimes() +
                    "   次" );
            }

            li++;
            activeRow( li );

            setCellValue( 0, plan.getPlanname() );
            setCellValue( 1, plan.getPatrolname() );
            setCellValue( 2,
                plan.getBegindateStr() + "   -   " +
                plan.getEnddateStr() );
            setCellValue( 3, String.valueOf( plan.getPlancount() ) + "点 * 次" );
            setCellValue( 4, plan.getCreator() );
            setCellValue( 5, plan.getCreatedate() );
            setCellValue( 6, "第 " + plan.getWeeknum() + " 周计划信息" );

            li++;
        }

        for( int i = 0; i < dailyVct.size(); i++ ){
            DailyPatrol daily = ( DailyPatrol )dailyVct.get( i );

            activeRow( li );
            setCellValue( 0, daily.getDatestr() );
            setCellValue( 1, daily.getWorkcontent() );
            setCellValue( 2, daily.getPtimes() );
            setCellValue( 3, daily.getAccnum() );
            li++;
        }

        activeRow( li );
        setCellValue( 0, planform.getPlancount() );
        setCellValue( 1, planform.getRealcount() );
        setCellValue( 2, planform.getLosscount() );
        setCellValue( 3, planform.getPatrolrate() );
        setCellValue( 4, planform.getLossrate() );

        setCellValue( 5, planform.getPatrolratenumber() );
        setCellValue( 6, planform.getLossratenumber() );
        li++;

        activeRow( li );
        setCellValue( 0, "巡检率" );
        setCellValue( 1, planform.getPatrolratenumber() );
        li++;

        activeRow( li );
        setCellValue( 0, "漏检率" );
        setCellValue( 1, planform.getLossratenumber() );

        //sheet2 巡检率 漏检率
        activeSheet( 1 );

        activeRow( 99 );
        setCellValue( 0, "巡检率" );
        setCellValue( 1, planform.getPatrolratenumber() );
        li++;

        activeRow( 100 );
        setCellValue( 0, "漏检率" );
        setCellValue( 1, planform.getLossratenumber() );

        activeRow( 101 );
        setCellValue( 0, planform.getPatrolratenumber() );
        setCellValue( 1, planform.getLossratenumber() );

    }

}
