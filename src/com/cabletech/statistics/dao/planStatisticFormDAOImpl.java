package com.cabletech.statistics.dao;

import java.util.*;

import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.services.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.beans.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.utils.*;
import com.cabletech.utils.*;

public class planStatisticFormDAOImpl{
    public planStatisticFormDAOImpl(){

    }


    /**
     * 取得完整报表
     * @param condition QueryCondition
     * @return Hashtable
     * @throws Exception
     */
    public PlanStatisticForm getPlanStatistic( QueryConditionBean conBean ) throws
        Exception{

        PlanStatisticForm form = new PlanStatisticForm();

        String formName = "";
        String cyctype = conBean.getCyctype();
        form.setCyctype( cyctype );

        QueryCondition condition = new QueryCondition();

        if( cyctype.equals( "month" ) ){
            DateTools datetools = new DateTools();
            datetools.getMonthBeginAndEnd( condition, conBean.getYear(),
                conBean.getMonth() );
            conBean.setBegindate( DateUtil.DateToString( condition.getBegindate() ) );
            conBean.setEnddate( DateUtil.DateToString( condition.getEnddate() ) );
        }
        BeanUtil.objectCopy( conBean, condition );

        QueryPatrolRateDAOImpl PatrolDAO = new QueryPatrolRateDAOImpl();
        //????????????????????????????
        Vector planVct = PatrolDAO.getPlanListVct( condition ); //获得所有符合条件的计划----

        if( planVct.size() > 0 ){

            form.setIfhasrecord( "1" );

            putStatisticData( planVct, form ); //?????

            if( cyctype.equals( "month" ) ){
                formName = conBean.getPatrolname() + conBean.getYear() + "年" +
                           conBean.getMonth() + "月份巡检计划";
            }
            else{
                formName = ( ( Plan )planVct.get( 0 ) ).getPlanname();
            }

            form.setFormname( formName );

            Vector dailyPatrolVct = getDailyPatrol( conBean.getCyctype(),
                                    conBean.getPatrolid(),
                                    conBean.getBegindate(), conBean.getEnddate() ); //每天巡检情况

            form.setDailypatrolvct( dailyPatrolVct );
            putTaskVct( planVct, form );

        }
        else{
            form.setIfhasrecord( "0" );
        }

        return form;

    }


    /**
     * 将任务信息放到计划vct
     * @param planVct Vector
     * @param form PlanStatisticForm
     * @return Vector
     * @throws Exception
     */
    public void putTaskVct( Vector planVct, PlanStatisticForm form ) throws
        Exception{

        Vector newPlanVct = new Vector();
        int tasknum = 0;

        for( int i = 0; i < planVct.size(); i++ ){
            Plan plan = ( Plan )planVct.get( i );
            Vector taskVct = getTaskVctByPlanID( plan.getPlanid() );
            plan.setTaskvct( taskVct );

            newPlanVct.add( plan );

            tasknum = tasknum + taskVct.size();
        }

        form.setTasknum( tasknum );
        form.setPlanvct( newPlanVct );
    }


    /**
     * 每天巡检情况
     * @param cyctype String
     * @param patrolid String
     * @param begindate String
     * @return Vector
     * @throws Exception
     */
    private Vector getDailyPatrol( String cyctype, String patrolid,
        String begindate, String enddate ) throws
        Exception{
        Vector dailyPatrolVct = new Vector();

        int cyclength = 7;
        if( cyctype.equals( "month" ) ){
            cyclength = DateTools.getNumOfDaysInMonth( begindate );
            //System.out.println("天数 ：" + cyclength);
        }

        String[] dateStrArr = DateUtil.parseStringForDate( 1, begindate );

        int iYear = Integer.parseInt( dateStrArr[0] );
        int iMonth = Integer.parseInt( dateStrArr[1] ) - 1;
        int iDate = Integer.parseInt( dateStrArr[2] );

        Calendar cal = Calendar.getInstance();
        String nowdate = "2000/01/01";

        for( int i = 0; i < cyclength; i++ ){
            cal.set( iYear, iMonth, iDate + i );
            nowdate = DateUtil.DateToString( cal.getTime() );

            String sql = getDailyPatrolSQL( patrolid, nowdate );
            //System.out.println("getDailyPatrol SQL : \n" + sql);
            QueryUtil queryUtil = new QueryUtil();

            Vector resultVct = queryUtil.commonQueryWithFieldNum( sql, 3 );

            //for (int k = 0; k < resultVct.size(); k++) {

            Vector recordV = ( Vector )resultVct.get( 0 );

            DailyPatrol dailypatrol = new DailyPatrol();

            dailypatrol.setMinpoint( replaceNullStr( ( String )recordV.get( 0 ) ) );
            dailypatrol.setMaxpoint( replaceNullStr( ( String )recordV.get( 1 ) ) );
            dailypatrol.setPtimes( ( String )recordV.get( 2 ) );

            dailypatrol.setWorkcontent( dailypatrol.getMinpoint() +
                " - " + dailypatrol.getMaxpoint() );
            //隐患数
            sql = getDailyAccSQL( patrolid, nowdate );
            resultVct = queryUtil.commonQueryWithFieldNum( sql, 1 );
            recordV = ( Vector )resultVct.get( 0 );

            dailypatrol.setAccnum( ( String )recordV.get( 0 ) );
            //结束隐患

            dailypatrol.setDatestr( nowdate );

            dailyPatrolVct.add( dailypatrol );
            //}

        }

        return dailyPatrolVct;
    }


    /**
     * getDailyAccSQL
     *
     * @param patrolid String
     * @param nowdate String
     * @return String
     */
    private String getDailyAccSQL( String patrolid, String nowdate ){
        String sql = "\n";
        sql += " select count(*) accnum from  PLANEXECUTE \n";
        sql += " where  EXECUTORID = '" + patrolid + "'  \n";
        sql += " and to_char(EXECUTETIME,'yyyy/mm/dd') =  '" + nowdate + "' \n";
        sql += " and OPERATIONCODE like 'A%' \n";
        return sql;
    }


    /**
     * getDailyPatrolSQL
     *
     * @param patrolid String
     * @param nowdate Date
     * @return String
     */
    private String getDailyPatrolSQL( String patrolid, String nowdate ) throws
        Exception{
        String sql = "\n";

        sql +=
            " select min(nvl(substr (pname, 2),' ')) minpoint, max(nvl(substr (pname, 2),' ')) maxpoint, count(*) ptimes from ( \n";
        sql += " select a.pid , count(a.pid), b.pointname pname from  PLANEXECUTE a, pointinfo b                \n";
        sql += " where  a.EXECUTORID = '" + patrolid + "'  \n";
        sql += " and to_char(a.EXECUTETIME,'yyyy/mm/dd') =  '" + nowdate +
            "' and a.pid = b.pointid group by a.pid, b.pointname ) \n";

        return sql;
    }


    /**
     * 将巡检数据计算后装入
     * @param planVct Vector
     * @param ht Hashtable
     * @throws Exception
     */
    public void putStatisticData( Vector planVct, PlanStatisticForm form ) throws
        Exception{

        if( planVct.size() > 0 ){

            int plancount = 0;
            int realcount = 0;

            for( int i = 0; i < planVct.size(); i++ ){

                Plan plan = ( Plan )planVct.get( i );
                Vector planPatrolVct = plan.getPlanpatrol();

                int plancount4Plan = 0;

                for( int k = 0; k < planPatrolVct.size(); k++ ){

                    PatrolPoint point = ( PatrolPoint )planPatrolVct.get( k );
                    plancount += point.getTimesneedtopatroled();
                    realcount += point.getTimespatroled();

                    plancount4Plan += point.getTimesneedtopatroled();
                }

                plan.setPlancount( plancount4Plan );

            }

            String patrolrate = "100%";
            String patrolrateNmuber = "100";
            String lossrate = "0%";
            String lossrateNumber = "0";

            if( plancount > 0 ){
                patrolrate = String.valueOf( Math.floor( 100 * realcount /
                             plancount ) ) + "%";
                patrolrateNmuber = String.valueOf( ( int )Math.floor( 100 *
                                   realcount /
                                   plancount ) );
                float fPRate = 100 * realcount / plancount;
                float fLRate = 100 - fPRate;
                lossrate = String.valueOf( fLRate ) + "%";
                lossrateNumber = String.valueOf( ( int )fLRate );
            }
            form.setPlancount( String.valueOf( plancount ) );
            form.setRealcount( String.valueOf( realcount ) );
            form.setLosscount( String.valueOf( plancount - realcount ) );
            form.setPatrolrate( patrolrate );
            form.setLossrate( lossrate );

            form.setPatrolratenumber( patrolrateNmuber );
            form.setLossratenumber( lossrateNumber );
        }
    }


    /**
     * 将计划对应任务取得
     * @param planid String
     * @param form PlanStatisticForm
     * @throws Exception
     */
    public Vector getTaskVctByPlanID( String planid ) throws
        Exception{

        Vector resultVct = new Vector();

        String sql = "select b.id taskid, b.describtion taskname, b.excutetimes excutetimes from plantasklist a, taskinfo b where a.taskid = b.id and a.planid='" +
                     planid + "'";

        DBService dbservice = new DBService();
        Vector vct = dbservice.queryVector( sql, "" );

        //System.out.println("纪录数量 : " + vct.size());

        for( int i = 0; i < vct.size(); i++ ){
            Vector tempVct = ( Vector )vct.get( i );

            Task task = new Task();

            task.setTaskid( ( String )tempVct.get( 0 ) );
            task.setTaskname( ( String )tempVct.get( 1 ) );
            task.setTimes( ( String )tempVct.get( 2 ) );

            resultVct.add( task );

        }
        return resultVct;
    }


    public String replaceNullStr( String oStr ){
        if( oStr == null ){
            oStr = " ";
        }
        return oStr;
    }

}
