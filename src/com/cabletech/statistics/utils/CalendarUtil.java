package com.cabletech.statistics.utils;

import java.text.*;
import java.util.*;
import org.apache.log4j.Logger;

public class CalendarUtil{
    //const CYCLEMONTH="2";
    //const CYCLEWEEK="1";
    private static Logger logger = Logger.getLogger(CalendarUtil.class);
    public CalendarUtil(){
    }


    /**
     * 获取星期代表的数字 星期一为0，依次类推
     * @param calendar Calendar
     * @return int
     */
    public static int GetDayOfWeekInt( Calendar calendar ){
        switch( calendar.get( Calendar.DAY_OF_WEEK ) ){
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            default:
                return 6;
        }
    }


    /**
     * 获取有用的时间段，去掉头尾没有计划要执行的时间段，用于缩小查询范围
     * @param planTimeArea Date[]    计划生效时间段
     * @param limitTimeArea Date[]   查询限定条件时间段
     * @return Calendar[]
     */
    public static Calendar[] getRealTimeArea( Date[] planTimeArea, Date[] limitTimeArea ){
        if( planTimeArea == null || limitTimeArea == null
            || planTimeArea[0] == null || planTimeArea[1] == null
            || limitTimeArea[0] == null || limitTimeArea[0] == null ){
            return null;
        }
        Calendar[] realTimeArea = new GregorianCalendar[2];
        //只截取有效时间段 获取时间交集
        realTimeArea[0] = new GregorianCalendar();
        realTimeArea[1] = new GregorianCalendar();
        if( limitTimeArea[0].before( planTimeArea[0] ) ){
            realTimeArea[0].setTime( planTimeArea[0] );
        }
        else{
            realTimeArea[0].setTime( limitTimeArea[0] );
        }
        if( limitTimeArea[1].after( planTimeArea[1] ) ){
            realTimeArea[1].setTime( planTimeArea[1] );
        }
        else{
            realTimeArea[1].setTime( limitTimeArea[1] );
        }
        if( realTimeArea[0].after( realTimeArea[1] )
            || realTimeArea[0].equals( realTimeArea[1] ) ){
            return null;
        }
        else{
            return realTimeArea;
        }

    }


    /**
     * 以指定的时间格式将日期类型转化为字符串
     * @param date Date
     * @param formatStr String
     * @return String
     */
    public static String FormatDate( Date date, String formatStr ){
        SimpleDateFormat formatter;
        if( formatStr == null || formatStr.equals( "" ) ){
            formatter = new SimpleDateFormat( "yyyy-MM-dd" );
        }
        else{
            formatter = new SimpleDateFormat( formatStr );
        }
        return formatter.format( date );
    }


    /**
     * 以指定的时间格式将日历类型转化为字符串
     * @param calendar Calendar
     * @param formatStr String
     * @return String
     */
    public static String FormatCalendar( Calendar calendar, String formatStr ){
        Date date = calendar.getTime();
        return FormatDate( date, formatStr );
    }


    /**
     * 获取执行时间列表
     * @param cycType String        周期类型
     * @param cycString String      执行日字符串，周周期为7位、月周期为31位
     * @param validTimeArea Calendar[]  限定时间段
     * @return List
     */
    public static List GetExecuteTimeList( String cycType, String cycString,
        Calendar[] validTimeArea ){
        int i = 0;
        int c = 0;
        int cMax;
        int cMin;
        if( validTimeArea == null ){
            return null;
        }

        Calendar limitBegin = validTimeArea[0];
        Calendar limitEnd = validTimeArea[1];
        Calendar cycBegin;
        ArrayList timeList = new ArrayList();
        //周周期
        if( cycType.equalsIgnoreCase( "1" ) ){
            if( cycString.trim().length() < 7 ){
                logger.info( "错误：周执行时间字符串小于7位！" );
                return null;
            }
            cycBegin = ( Calendar )limitBegin.clone();
            while( cycBegin.before( limitEnd ) ){
                cMax = 6; //0-6 周一到周日
                cMin = CalendarUtil.GetDayOfWeekInt( cycBegin );
                for( c = cMin; c <= cMax; c++ ){
                    if( cycBegin.before( limitEnd ) ){
                        if( cycString.charAt( c ) == '1' ){
                            timeList.add( cycBegin.getTime() );
                            logger.info( "周执行日：" +
                                FormatCalendar( cycBegin, "yyyy-MM-dd" ) );
                        }
                    }
                    else{
                        break;
                    }
                    cycBegin.add( Calendar.DATE, 1 ); //加一天
                }
            }
            return timeList;
        }
        //月周期
        if( cycType.equalsIgnoreCase( "2" ) ){
            if( cycString.trim().length() < 31 ){
                logger.info( "错误：月执行时间字符串小于31位！" );
            }
            cycBegin = ( Calendar )limitBegin.clone();
            while( cycBegin.before( limitEnd ) ){
                cMax = cycBegin.getActualMaximum( Calendar.DAY_OF_MONTH ) - 1;
                cMin = cycBegin.get( Calendar.DAY_OF_MONTH ) - 1;
                for( c = cMin; c <= cMax; c++ ){
                    if( cycBegin.before( limitEnd ) ){
                        if( cycString.charAt( c ) == '1' ){
                            timeList.add( cycBegin.getTime() );
                            logger.info( "月执行日：" +
                                FormatCalendar( cycBegin, "yyyy-MM-dd" ) );
                        }
                    }
                    else{
                        break;
                    }
                    cycBegin.add( Calendar.DATE, 1 ); //加一天
                }
            }
            return timeList;
        }
        else{
            return null;
        }

    }

}
