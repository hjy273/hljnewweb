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
     * ��ȡ���ڴ�������� ����һΪ0����������
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
     * ��ȡ���õ�ʱ��Σ�ȥ��ͷβû�мƻ�Ҫִ�е�ʱ��Σ�������С��ѯ��Χ
     * @param planTimeArea Date[]    �ƻ���Чʱ���
     * @param limitTimeArea Date[]   ��ѯ�޶�����ʱ���
     * @return Calendar[]
     */
    public static Calendar[] getRealTimeArea( Date[] planTimeArea, Date[] limitTimeArea ){
        if( planTimeArea == null || limitTimeArea == null
            || planTimeArea[0] == null || planTimeArea[1] == null
            || limitTimeArea[0] == null || limitTimeArea[0] == null ){
            return null;
        }
        Calendar[] realTimeArea = new GregorianCalendar[2];
        //ֻ��ȡ��Чʱ��� ��ȡʱ�佻��
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
     * ��ָ����ʱ���ʽ����������ת��Ϊ�ַ���
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
     * ��ָ����ʱ���ʽ����������ת��Ϊ�ַ���
     * @param calendar Calendar
     * @param formatStr String
     * @return String
     */
    public static String FormatCalendar( Calendar calendar, String formatStr ){
        Date date = calendar.getTime();
        return FormatDate( date, formatStr );
    }


    /**
     * ��ȡִ��ʱ���б�
     * @param cycType String        ��������
     * @param cycString String      ִ�����ַ�����������Ϊ7λ��������Ϊ31λ
     * @param validTimeArea Calendar[]  �޶�ʱ���
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
        //������
        if( cycType.equalsIgnoreCase( "1" ) ){
            if( cycString.trim().length() < 7 ){
                logger.info( "������ִ��ʱ���ַ���С��7λ��" );
                return null;
            }
            cycBegin = ( Calendar )limitBegin.clone();
            while( cycBegin.before( limitEnd ) ){
                cMax = 6; //0-6 ��һ������
                cMin = CalendarUtil.GetDayOfWeekInt( cycBegin );
                for( c = cMin; c <= cMax; c++ ){
                    if( cycBegin.before( limitEnd ) ){
                        if( cycString.charAt( c ) == '1' ){
                            timeList.add( cycBegin.getTime() );
                            logger.info( "��ִ���գ�" +
                                FormatCalendar( cycBegin, "yyyy-MM-dd" ) );
                        }
                    }
                    else{
                        break;
                    }
                    cycBegin.add( Calendar.DATE, 1 ); //��һ��
                }
            }
            return timeList;
        }
        //������
        if( cycType.equalsIgnoreCase( "2" ) ){
            if( cycString.trim().length() < 31 ){
                logger.info( "������ִ��ʱ���ַ���С��31λ��" );
            }
            cycBegin = ( Calendar )limitBegin.clone();
            while( cycBegin.before( limitEnd ) ){
                cMax = cycBegin.getActualMaximum( Calendar.DAY_OF_MONTH ) - 1;
                cMin = cycBegin.get( Calendar.DAY_OF_MONTH ) - 1;
                for( c = cMin; c <= cMax; c++ ){
                    if( cycBegin.before( limitEnd ) ){
                        if( cycString.charAt( c ) == '1' ){
                            timeList.add( cycBegin.getTime() );
                            logger.info( "��ִ���գ�" +
                                FormatCalendar( cycBegin, "yyyy-MM-dd" ) );
                        }
                    }
                    else{
                        break;
                    }
                    cycBegin.add( Calendar.DATE, 1 ); //��һ��
                }
            }
            return timeList;
        }
        else{
            return null;
        }

    }

}
