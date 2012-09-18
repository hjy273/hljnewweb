package com.cabletech.watchinfo.dao;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.baseinfo.domainobjects.*;
import com.cabletech.commons.hb.*;
import com.cabletech.watchinfo.beans.*;

public class NewWatchStaDao{
    Logger logger = Logger.getLogger( this.getClass().getName() );
    public NewWatchStaDao(){
    }


    /**
     * ����:���һ���������Ѿ�ִ�е���Ч������Ϣ����
     * ����:����bean
     * ����ֵ
     * */
    public boolean getExeTimesOfOneWatch( OneStaWatchBean bean ){
        String watchid = bean.getPlaceID();
        int count = 0;
        //--�ڶ���ʱ���ڵĶ�����Ϣ,�����ظ��ĵ���Ϣ ʱ�����Ϊ20���� (1/72)
        String sql =
            "select we.EXECUTETIME extime, "
            + " TO_DATE( CONCAT(TO_CHAR(w.ENDDATE,'yyyy-mm-dd'),w.ORDERLYENDTIME),'yyyy-mm-dd hh24:mi:ss' ) btime,"
            + " TO_DATE( CONCAT(TO_CHAR(w.BEGINDATE,'yyyy-mm-dd'),w.ORDERLYBEGINTIME),'yyyy-mm-dd hh24:mi:ss' ) etime,"
            + "  w.ERROR "
            + " from watchinfo w,watchexecute we "
            + " where  w.PLACEID = we.WATCHID "
            + "        and TO_DATE(TO_CHAR(we.EXECUTETIME,'yyyy-mm-dd'),'yyyy-mm-dd') >= TO_DATE(TO_CHAR(w.BEGINDATE,'yyyy-mm-dd'),'yyyy-mm-dd')"
            + " 	   and TO_DATE(TO_CHAR(we.EXECUTETIME,'yyyy-mm-dd'),'yyyy-mm-dd') <= TO_DATE(TO_CHAR(w.ENDDATE,'yyyy-mm-dd'),'yyyy-mm-dd')"
            +
            "        and TO_DATE(TO_CHAR(we.EXECUTETIME,'hh24:mi:ss'),'hh24:mi:ss')>=TO_DATE(w.ORDERLYBEGINTIME,'hh24:mi:ss')-1/72"
            +
            "        and TO_DATE(TO_CHAR(we.EXECUTETIME,'hh24:mi:ss'),'hh24:mi:ss')<=TO_DATE(w.ORDERLYENDTIME,'hh24:mi:ss')+1/72"
            + "        and w.PLACEID='" + bean.getPlaceID() + "'	"
            + " order by EXECUTETIME ";
        try{
            QueryUtil qu = new QueryUtil();
            ResultSet rst;
            rst = qu.executeQuery( sql );
            java.util.Date begindate = new java.util.Date();
            java.util.Date enddate = new java.util.Date();
            java.util.Date exdate = new java.util.Date();

            if( rst != null && rst.next() ){
                begindate = rst.getDate( "btime" );
                exdate = rst.getDate( "extime" );
                java.util.Date bbdate = new java.util.Date( ( begindate.getTime() - 1000 * 120l ) ); //��20����
                java.util.Date eedate = new java.util.Date( begindate.getTime() + 1000 * 120l ); //��20����
                if( exdate.after( bbdate ) && exdate.before( eedate ) ){
                    count++;
                }
            }

            while( rst != null && rst.next() ){
                begindate.setTime( rst.getInt( "error" ) * 1000 * 60l );
                exdate = rst.getDate( "extime" );
                java.util.Date bbdate = new java.util.Date( ( begindate.getTime() - 1000 * 120l ) ); //��20����
                java.util.Date eedate = new java.util.Date( begindate.getTime() + 1000 * 120l ); //��20����
                if( exdate.after( bbdate ) && exdate.before( eedate ) ){
                    count++;
                }
            }
            bean.setInfodid( String.valueOf( count ) );
            return true;
        }
        catch( Exception e ){
            logger.warn( "���һ���������Ѿ�ִ�е���Ч������Ϣ�����쳣:" + e.getMessage() );
            return false;
        }
    }


    /**
     * ����:��÷��ϲ�ѯ�����Ķ�����Ϣ
     * sql:-- ������Ҫ�� = ÿ��Ĵ��� * �������� ��ȡ��
        select CEIL ( (TO_DATE(w.ORDERLYENDTIME,'hh24:mi:ss') -TO_DATE(w.ORDERLYBEGINTIME,'hh24:mi:ss'))*24 /w.error  * TO_NUMBER( TO_DATE( CONCAT(TO_CHAR(w.ENDDATE,'yyyy-mm-dd'),w.ORDERLYENDTIME),'yyyy-mm-dd hh24:mi:ss' ) - TO_DATE( CONCAT(TO_CHAR(w.BEGINDATE,'yyyy-mm-dd'),w.ORDERLYBEGINTIME),'yyyy-mm-dd hh24:mi:ss' ))) aa
        from watchinfo w
        --where w.PLACEID='000000000000000000000582'

     * */

    /**
     * ����:���ͳ�ƽ��
     * */
    public WatchStaResultBean getStaResultBean( WatchStaBean conditionBean, UserInfo userinfo ) throws
        Exception{

        WatchStaResultBean resultBean = new WatchStaResultBean();
        Vector watchListVct = new Vector(); // conditionBean, userinfo );

        if( watchListVct == null || watchListVct.size() == 0 ){ //û�п�����������
            return null;
        }
        else{
            String daterange = "";
            if( conditionBean.getBegindate().equals( "" ) && conditionBean.getEnddate().equals( "" ) ){
                daterange = "--";
            }
            else{
                if( conditionBean.getBegindate().equals( "" ) ){
                    daterange = conditionBean.getEnddate() + " ��ǰ";
                }
                else{
                    if( conditionBean.getEnddate().equals( "" ) ){
                        daterange = conditionBean.getBegindate() + " ����";
                    }
                    else{
                        daterange = conditionBean.getBegindate() + "  --  " + conditionBean.getEnddate();
                    }
                }
            }

            resultBean.setWatchlist( watchListVct );
            resultBean.setWatchamount( String.valueOf( watchListVct.size() ) );
            resultBean.setDaterange( daterange );

            int countNeed = 0;
            int countDid = 0;
            int countAlert = 0;

            for( int i = 0; i < watchListVct.size(); i++ ){
                OneStaWatchBean oneWatchBean = ( OneStaWatchBean )watchListVct.get( i );
                //ͳ��һ������
                countNeed += Integer.parseInt( oneWatchBean.getInfoneed() ); //��������������Ϣ����
                countDid += Integer.parseInt( oneWatchBean.getInfodid() ); //��������ִ����Ϣ����
            }
            putRateValues( resultBean, countNeed, countDid, countAlert );
            //System.out.println( "---->3 " + resultBean.getWatchlist() );
            return resultBean;
        }
    }


    /**
     * ���㲢���������
     * @param countNeed int
     * @param countDid int
     * @throws Exception
     */
    private void putRateValues( WatchStaResultBean resultBean, int countNeed,
        int countDid, int countAlert ) throws
        Exception{
        //System.out.println( "countNeed: " + countNeed + " countDid " + countDid + " countAlert " + countAlert );
        String workRate = "100%";
        String workRateNumber = "100";
        if( countNeed == 0 ){
            resultBean.setUndorate( "0" );
            resultBean.setUndoratenumber( "0" );
            resultBean.setWatchexecuterate( "0.0%" );
            resultBean.setWorkratenumber( "0" );
            resultBean.setInfodid( "0" );
            resultBean.setInfoneeded( "0" );
            resultBean.setAlertcount( "0" );
            //System.out.println( "����Ϊ0" );
        }
        else{
            workRateNumber = String.valueOf( ( int )Math.floor( 100 * countDid /
                             countNeed ) );

            workRate = String.valueOf( Math.floor( 100 * countDid /
                       countNeed ) ) + "%";

            float dRate = 100 * countDid / countNeed;

            float udRate = 100 - dRate;

            String undoRate = String.valueOf( udRate ) + "%";

            String undoRateNumber = "0";

            undoRateNumber = String.valueOf( ( int )udRate );

            resultBean.setUndorate( undoRate );
            resultBean.setUndoratenumber( undoRateNumber );
            resultBean.setWatchexecuterate( workRate );
            resultBean.setWorkratenumber( workRateNumber );
            resultBean.setInfodid( String.valueOf( countDid ) );
            resultBean.setInfoneeded( String.valueOf( countNeed ) );
            resultBean.setAlertcount( String.valueOf( countAlert ) );
        }
    }

}
