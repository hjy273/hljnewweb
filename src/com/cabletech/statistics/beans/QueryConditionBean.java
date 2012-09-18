package com.cabletech.statistics.beans;

import java.util.*;

import com.cabletech.commons.base.*;
import com.cabletech.statistics.utils.*;

public class QueryConditionBean extends BaseBean{
    private String deptid;
    private String begindate;
    private String enddate;
    private String queryby = "ByDepart";
    private String sublineid;
    private String patrolid;
    private String regionid;
    private String year;
    private String month;
    private String cyctype;
    private String patrolname;
    private String lineid;

    public QueryConditionBean(){

        Date now = new Date();

        //开始 / 结束 日期
        Calendar beginCalendar = new GregorianCalendar();
        beginCalendar.setTime( now );
        beginCalendar.add( Calendar.DATE, -7 );

        int weekNum = beginCalendar.get( Calendar.DAY_OF_WEEK );
        beginCalendar.add( Calendar.DATE, - ( weekNum - 1 ) );

        Calendar endCalendar = ( Calendar )beginCalendar.clone();
        endCalendar.add( Calendar.DATE, 6 );
        //日期范围默认是一周
        begindate = CalendarUtil.FormatCalendar( beginCalendar, "yyyy/MM/dd" );
        enddate = CalendarUtil.FormatCalendar( endCalendar, "yyyy/MM/dd" );

        Calendar cal = new GregorianCalendar();
        cal.setTime( now );
        year = String.valueOf( cal.get( Calendar.YEAR ) );
        month = String.valueOf( cal.get( Calendar.MONTH ) );

        if( Integer.parseInt( month ) < 10 ){
            month = "0" + month;
        }

      //  System.out.println( "Year : " + year );
   //     System.out.println( "month : " + month );
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }

    }


    public String getDeptid(){

        return deptid;
    }


    public void setDeptid( String deptid ){

        this.deptid = deptid;
    }


    public String getBegindate(){

        return begindate;
    }


    public void setBegindate( String begindate ){

        this.begindate = begindate;
    }


    public String getEnddate(){

        return enddate;
    }


    public void setEnddate( String enddate ){

        this.enddate = enddate;
    }


    public String getQueryby(){

        return queryby;
    }


    public void setQueryby( String queryby ){

        this.queryby = queryby;
    }


    public String getSublineid(){

        return sublineid;
    }


    public void setSublineid( String sublineid ){

        this.sublineid = sublineid;
    }


    public String getPatrolid(){

        return patrolid;
    }


    public String getRegionid(){
        return regionid;
    }


    public String getYear(){
        return year;
    }


    public String getMonth(){
        return month;
    }


    public String getCyctype(){
        return cyctype;
    }


    public String getPatrolname(){
        return patrolname;
    }


    public void setPatrolid( String patrolid ){

        this.patrolid = patrolid;
    }


    public void setRegionid( String regionid ){
        this.regionid = regionid;
    }


    public void setYear( String year ){
        this.year = year;
    }


    public void setMonth( String month ){
        this.month = month;
    }


    public void setCyctype( String cyctype ){
        this.cyctype = cyctype;
    }


    public void setPatrolname( String patrolname ){
        this.patrolname = patrolname;
    }


    private void jbInit() throws Exception{
    }


    public String getLineid(){
        return lineid;
    }


    public void setLineid( String lineid ){
        this.lineid = lineid;
    }
}
