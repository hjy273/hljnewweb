package com.cabletech.statistics.utils;

import java.util.*;
import com.cabletech.statistics.domainobjects.*;
import org.apache.log4j.*;

public class DateTools{
	private static Logger logger = Logger.getLogger("DateTools");

    public DateTools(){
    }


    /**
     * Ϊ�¼������¼�������Ϊ��λ�Ŀ�ʼ���������ڣ�4��
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

        //��ʼʱ��
        cal.set( iYear, iMonth, iDate );
        condition.setBegindate( cal.getTime() );

        //����ʱ��
        cal.set( iYear, iMonth, iDate + 27 );
        condition.setEnddate( cal.getTime() );

        return;
    }


    /**
     * Ϊ�¼������¼�������Ϊ��λ�Ŀ�ʼ���������ڣ���Ȼ��
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

        //��ʼʱ��
        cal.set( iYear, iMonth, 1 );
        condition.setBegindate( cal.getTime() );

        //����ʱ��
        cal.set( iYear, iMonth + 1, 1 );
        cal.add( Calendar.DATE, -1 );
        condition.setEnddate( cal.getTime() );
        return;
    }


    public static int getNumOfDaysInMonth( String begindate ) throws Exception{
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        int iYear = Integer.parseInt( begindate.substring( 0, 4 ) );

        //logger.info("�꣺ " + iYear);

        int iMonth = Integer.parseInt( begindate.substring( 5, 7 ) ) - 1;

        //logger.info("�£� " + iMonth);

        //��ʼʱ��
        calBegin.set( iYear, iMonth, 1 );

        //����ʱ��
        calEnd.set( iYear, iMonth + 1, 1 );
        calEnd.add( Calendar.DATE, -1 );

        long beginMill = calBegin.getTimeInMillis();
        long endMill = calEnd.getTimeInMillis();

        long iADay = 86400000;

        int iDays = ( int ) ( ( endMill - beginMill ) / iADay );

        return iDays + 1;
    }


    /**
     * Ϊ�¼������¼�������Ϊ��λ�Ŀ�ʼ����������
     * @param year String
     * @param month String
     * @return Vector 4�ܵĿ�ʼ����������
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

        //��ʼʱ��
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
     * ȡ��ĳ���еڼ�����
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
     * ����:���ָ���µ����һ��
     * ����:��.��
     * ����:�µ����һ��ı�ʾ��:2006/03/31
     * */
    public static String getLastOfMonth( String year, String month ){
        int iyear = Integer.parseInt( year );
        int imonth = Integer.parseInt( month );
        int iday = 0;
        if( imonth == 2 ){ //��2��
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
