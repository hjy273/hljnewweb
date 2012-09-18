package com.cabletech.planinfo.templates;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.Region;

import com.cabletech.commons.exceltemplates.ExcelStyle;
import com.cabletech.commons.exceltemplates.Template;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planinfo.beans.PlanBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;

public class YMWPlanFormTemplate extends Template{

    public YMWPlanFormTemplate( String urlPath ) throws IOException{
        super( urlPath );
    }


    /**
     * 导出月年计划
     * @param plan Plan
     * @param taskVct Vector
     * @param fID String
     */
    public void ExportYMPlanform( YearMonthPlanBean plan, Vector taskVct, ExcelStyle excelstyle ){

        activeSheet( 0 );

        setCellValue( 1, 0, plan.getPlanname() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 1, "代维单位年度/月度巡检计划信息报表" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 2, plan.getId() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
        this.curRow.setHeight((short)480);
        setCellValue( 1, 3, DateUtil.getNowDateString() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
        this.curRow.setHeight((short)480);

        setCellValue( 0, 5, "计划信息" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight( ( short )480 );

        setCellValue( 1, 5, "计划名称" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 5, plan.getPlanname() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 6, "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 6, "计划时间" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight( ( short )480 );
        setCellValue( 2, 6, plan.getYear() + " 年 " + plan.getMonth() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 7, "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 7, "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        this.curRow.setHeight( ( short )480 );

        for( int i = 0; i < taskVct.size(); i++ ){
            TaskBean task = ( TaskBean )taskVct.get( i );
            setCellValue( 0, 7 + i, "" );
            setCellValue( 1, 7 + i, "任务" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            setCellValue( 2, 7 + i,
                task.getLineleveltext() + "    " + task.getDescribtion() + "    " + task.getExcutetimes() + "次" );
            ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
            this.curRow.setHeight( ( short )480 );
        }
        setCellValue( 0, 7 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 7 + taskVct.size(), "计划制定人" );
        this.curRow.setHeight( ( short )480 );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 7 + taskVct.size(), plan.getCreator() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 8 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 8 + taskVct.size(), "计划制定日期" );
        this.curRow.setHeight( ( short )480 );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date time = DateUtil.parseDate(plan.getCreatedate());
        String ctime = format.format(time);
        setCellValue(2, 8 + taskVct.size(), ctime);
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 9 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 9 + taskVct.size(), "移动公司审批情况" );
        this.curRow.setHeight( ( short )480 );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 9 + taskVct.size(), plan.getStatus() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 10 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 10 + taskVct.size(), "审批人" );
        this.curRow.setHeight( ( short )480 );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 10 + taskVct.size(), plan.getApprover() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 0, 11 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
        setCellValue( 1, 11 + taskVct.size(), "审批日期" );
        this.curRow.setHeight( ( short )480 );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 11 + taskVct.size(), plan.getApprovedate() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );

        setCellValue( 0, 12 + taskVct.size(), "移动公司领导批示" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 1, 12 + taskVct.size(), plan.getApprovecontent() );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
        setCellValue( 2, 12 + taskVct.size(), "" );
        ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );

        this.curRow.setHeight( ( short )1500 );

        this.curSheet.addMergedRegion( new Region( 5, ( short )0, 11 + taskVct.size(), ( short )0 ) );
        this.curSheet.addMergedRegion( new Region( 7, ( short )1, 6 + taskVct.size(), ( short )1 ) );
        this.curSheet.addMergedRegion( new Region( 12 + taskVct.size(), ( short )1, 12 + taskVct.size(), ( short )2 ) );

    }


    /**
     * 导出周计划
     * @param plan Plan
     * @param taskVct Vector
     */
    public void ExportWeekPlanform( PlanBean plan, Vector taskVct, ExcelStyle excelstyle ){
        activeSheet( 0 );
        int row = 0;
        		//0
               setCellValue( 1, row, plan.getPlanname() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               
               row++;
               setCellValue( 1, row, "代维单位巡检计划信息报表" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               
               row++;
               setCellValue( 1, row, plan.getId() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               
               row++;
               setCellValue( 1, row, DateUtil.getNowDateString() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenBottom(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               row++;
               row++;
               setCellValue( 0, row, "计划信息" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               
               setCellValue( 1, row, "计划名称" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row, plan.getPlanname() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               //6
               row++;
               setCellValue( 0, row, "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row, "巡检负责人" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row, plan.getExecutorid() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               row++;
               setCellValue( 0, row, "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row, "计划时间" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row, plan.getBegindate().substring(0,plan.getBegindate().indexOf(" ")) + "  -  " + plan.getEnddate().substring(0,plan.getEnddate().indexOf(" ")) );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               row++;
               setCellValue( 0, row, "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row, "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               for( int i = 0; i < taskVct.size(); i++ ){
                   TaskBean task = ( TaskBean )taskVct.get( i );
                   setCellValue( 0, row + i, "" );
                   setCellValue( 1, row + i, "任务" );
                   ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
                   ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
                   List list = task.getTaskSubline();
                   String sublinesr="";
                   for(int k=0;k<list.size();k++){
                   	PlanTaskSubline subline = (PlanTaskSubline)list.get(k);
                   	sublinesr += subline.getName()+"  ";	
                   }
                   setCellValue( 2, row + i,
                       task.getLineleveltext() + "    " + task.getDescribtion() + "    " + task.getExcutetimes() + "次    计划点数 共:"+task.getTaskpoint()+"点  实际点数 共:"+task.getRealPoint()+"点 所占比率:"+task.getRatio()+"   \n巡检线段："+sublinesr );
                   ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
                   int len = sublinesr.length()+5;
                   
                   this.curRow.setHeight( ( short )(480*(len/32+2)) );
                   
                   //设置单元格属性
               }
               //巡检方式
               //row++;
               setCellValue(0,row+taskVct.size(),"");
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "巡检方式" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), plan.getPatrolmode().equals("01")?"手动":"自动" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               
               row++;
               setCellValue( 0, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "计划制定人" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), plan.getCreator() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               //10
               row++;
               setCellValue( 0, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "计划制定日期" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), plan.getCreatedate() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               row++;
               setCellValue( 0, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "代维单位审批情况" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), plan.getIfinnercheck() );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               //row++;
               setCellValue( 0, row + taskVct.size(), "巡检员/段长确认" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "                                  巡检员确认：" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );

               row++;
               setCellValue( 0, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.left(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "                                  段长确认：" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )480 );
               //14
               row++;
               setCellValue( 0, row + taskVct.size(), "代维单位领导意见" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )0 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 1, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )1 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               setCellValue( 2, row + taskVct.size(), "" );
               ( ( HSSFCell ) ( this.curRow.getCell( ( short )2 ) ) ).setCellStyle( excelstyle.tenFourLine(this.workbook) );
               this.curRow.setHeight( ( short )1500 );

               this.curSheet.addMergedRegion( new Region( 5, ( short )0, 10 + taskVct.size(), ( short )0 ) );
               this.curSheet.addMergedRegion( new Region( 8, ( short )1, 7 + taskVct.size(), ( short )1 ) );
               this.curSheet.addMergedRegion( new Region( 11 + taskVct.size(), ( short )0, 12 + taskVct.size(), ( short )0 ) );
               this.curSheet.addMergedRegion( new Region( 11 + taskVct.size(), ( short )1, 11 + taskVct.size(), ( short )2 ) );
               this.curSheet.addMergedRegion( new Region( 12 + taskVct.size(), ( short )1, 12 + taskVct.size(), ( short )2 ) );
               this.curSheet.addMergedRegion( new Region( 13 + taskVct.size(), ( short )1, 13 + taskVct.size(), ( short )2 ) );
    }
}
