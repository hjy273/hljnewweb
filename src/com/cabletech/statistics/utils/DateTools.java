package com.cabletech.statistics.utils;

import java.util.*;
import com.cabletech.statistics.domainobjects.*;
import org.apache.log4j.*;

public class DateTools{
	private static Logger logger = Logger.getLogger("DateTools");

    public DateTools(){
    }


    /**
     * 为月检索重新计算以周为单位的开始、结束日期，4周
     * @param condition QueryCondition
     * @param year String
     * @param month String
     */
    public static void getWeeklyMonthBeginAndEnd( QueryCondition condition,
        String year,
        String month ) throws Exception{
        Calendar cal = Calendar.getInstance();

        int iYear = Integer.parseInt( year );
        int iMonth = Integer.parseInt( month ) - 1;
        int iDate = 1;

        cal.set( iYear, iMonth, iDate );
        int weekNum = cal.get( Calendar.DAY_OF_WEEK );

        iDate = iDate - ( weekNum - 1 );

        //开始时间
        cal.set( iYear, iMonth, iDate );
        condition.setBegindate( cal.getTime() );

        //结束时间
        cal.set( iYear, iMonth, iDate + 27 );
        condition.setEnddate( cal.getTime() );

        return;
    }


    /**
     * 为月检索重新计算以周为单位的开始、结束日期，自然月
     * @param condition QueryCondition
     * @param year String
     * @param month String
     * @throws Exception
     */
    public static void getMonthBeginAndEnd( QueryCondition condition,
        String year,
        String month ) throws Exception{
        Calendar cal = Calendar.getInstance();

        int iYear = Integer.parseInt( year );
        int iMonth = Integer.parseInt( month ) - 1;

        //开始时间
        cal.set( iYear, iMonth, 1 );
        condition.setBegindate( cal.getTime() );

        //结束时间
        cal.set( iYear, iMonth + 1, 1 );
        cal.add( Calendar.DATE, -1 );
        condition.setEnddate( cal.getTime() );
        return;
    }


    public static int getNumOfDaysInMonth( String begindate ) throws Exception{
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        int iYear = Integer.parseInt( begindate.substring( 0, 4 ) );

        //logger.info("年： " + iYear);

        int iMonth = Integer.parseInt( begindate.substring( 5, 7 ) ) - 1;

        //logger.info("月： " + iMonth);

        //开始时间
        calBegin.set( iYear, iMonth, 1 );

        //结束时间
        calEnd.set( iYear, iMonth + 1, 1 );
        calEnd.add( Calendar.DATE, -1 );

        long beginMill = calBegin.getTimeInMillis();
        long endMill = calEnd.getTimeInMillis();

        long iADay = 86400000;

        int iDays = ( int ) ( ( endMill - beginMill ) / iADay );

        return iDays + 1;
    }


    /**
     * 为月检索重新计算以周为单位的开始、结束日期
     * @param year String
     * @param month String
     * @return Vector 4周的开始、结束日期
     * @throws Exception
     */
    public Vector getMonthBeginAndEndVct( String year, String month ) throws
        Exception{
        Vector vct = new Vector();

        Calendar cal = Calendar.getInstance();

        int iYear = Integer.parseInt( year );
        int iMonth = Integer.parseInt( month ) - 1;
        int iDate = 1;

        cal.set( iYear, iMonth, iDate );
        int weekNum = cal.get( Calendar.DAY_OF_WEEK );

        iDate = iDate - ( weekNum - 1 );

        //开始时间
        for( int i = 0; i < 4; i++ ){
            Hashtable ht = new Hashtable();

            cal.set( iYear, iMonth, iDate );
            ht.put( "begindate", cal.getTime() );
            iDate = iDate + 6;

            cal.set( iYear, iMonth, iDate );
            ht.put( "enddate", cal.getTime() );
            iDate = iDate + 1;
            vct.add( ht );
        }
        return vct;
    }


    /**
     * 取得某年中第几周数
     * @param dateStr String
     * @return String
     * @throws Exception
     */
    public static String getWeekOfYear( String dateStr ) throws Exception{
        Calendar cal = Calendar.getInstance();
        int y = Integer.parseInt( dateStr.substring( 0, 4 ) );
        int m = Integer.parseInt( dateStr.substring( 5, 7 ) ) - 1;
        int d = Integer.parseInt( dateStr.substring( 8, 10 ) );


//        logger.info( "year : " + y );
//        logger.info( "Month : " + m );
//        logger.info( "Date : " + d );

        cal.set( y, m, d );

        logger.info( "Month : " + cal.get( Calendar.MONTH ) );

        return String.valueOf( cal.get( Calendar.WEEK_OF_YEAR ) );
    }


    /**
     * 功能:获得指定月的最后一天
     * 参数:年.月
     * 返回:月的最后一天的表示如:2006/03/31
     * */
    public static String getLastOfMonth( String year, String month ){
        int iyear = Integer.parseInt( year );
        int imonth = Integer.parseInt( month );
        int iday = 0;
        if( imonth == 2 ){ //是2月
            if( iyear % 400 == 0 || ( iyear % 4 == 0 && iyear % 100 != 0 ) ){
                iday = 29;
            }
            else{
                iday = 28;
            }
        }
        else{
            if( imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7 || imonth == 8 || imonth == 10 || imonth == 12 ){
                iday = 31;
            }
            else{
                iday = 30;
            }
        }
        return year + "/" + month + "/" + String.valueOf( iday );
    }
}
