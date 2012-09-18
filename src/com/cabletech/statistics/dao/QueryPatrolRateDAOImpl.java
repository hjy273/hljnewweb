package com.cabletech.statistics.dao;

import java.util.*;

import org.apache.commons.beanutils.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.utils.*;
import com.cabletech.utils.*;

public class QueryPatrolRateDAOImpl{
    public QueryPatrolRateDAOImpl(){
    }


    /**
     * 巡检率统计
     * @param condition QueryCondition
     * @return List
     * @throws Exception
     */
    public List getPatrolRateReport( QueryCondition condition ) throws
        Exception{
        //初始化数据

        Vector planVct = getPlanListVct( condition );
        List reportList = new ArrayList();

        putData( planVct, reportList );

        return reportList;
    }


    /**
     * 巡检率统计
     * @return PatrolRate
     * @throws Exception
     */
    public PatrolRate getPatrolReteAsObj( QueryCondition condition ) throws
        Exception{
        PatrolRate data = new PatrolRate();
        //按查询条件获得计划列表
        Vector planVct = getPlanListVct( condition );

        if( planVct.size() > 0 ){ //如果有计划
            data.setIfhasrecord( "1" );
            List reportList = new ArrayList();
            //根据planVct内容,将巡检数据计算后装入reportlist
            putData( planVct, reportList );

            int plancount = 0;
            int realcount = 0;
            //统计计划数和实际巡检数
            for( int i = 0; i < reportList.size(); i++ ){
                Plan plan = ( Plan )reportList.get( i );
                plancount += plan.getPlancount();
                realcount += plan.getRealcount();
            }

            data.setPlancount( String.valueOf( plancount ) ); //计划数
            data.setRealcount( String.valueOf( realcount ) ); //实际巡检数
            data.setLosscount( String.valueOf( plancount - realcount ) ); //漏检数

            String patrolrate = "100%";
            String patrolrateNumber = "100";
            String lossrate = "0%";
            String lossrateNumber = "0";

            if( plancount > 0 ){
                patrolrate = String.valueOf( Math.floor( 100 * realcount / plancount ) ) + "%"; //巡检率
                patrolrateNumber = String.valueOf( ( int )Math.floor( 100 * realcount / plancount ) );

                float fPRate = 100 * realcount / plancount;
                float fLRate = 100 - fPRate;
                lossrate = String.valueOf( fLRate ) + "%";

                lossrateNumber = String.valueOf( ( int )fLRate );
            }

            data.setPatrolrate( patrolrate );
            data.setPatrolratenumber( patrolrateNumber );
            data.setLossrate( lossrate );
            data.setLossratenumber( lossrateNumber );
        }
        else{
            data.setIfhasrecord( "0" );
        }
        return data;
    }


    /**
     * 将巡检数据计算后装入list
     * @param planVct Vector
     * @param reportList List
     * @throws Exception
     */
    public void putData( Vector planVct, List reportList ) throws Exception{

        for( int i = 0; i < planVct.size(); i++ ){

            Plan plan = ( Plan )planVct.get( i );
            Vector planPatrolVct = plan.getPlanpatrol();

            int plancount = 0;
            int realcount = 0;

            for( int k = 0; k < planPatrolVct.size(); k++ ){

                PatrolPoint point = ( PatrolPoint )planPatrolVct.get( k );
                //System.out.println("点 ID : " + point.getPointid());
                plancount += point.getTimesneedtopatroled();
                realcount += point.getTimespatroled();
            }

            plan.setLosscount( plancount - realcount );
            plan.setPlancount( plancount );
            plan.setRealcount( realcount );

            String patrolrate = "100%";
            String lossrate = "0%";

            if( plancount > 0 ){
                patrolrate = String.valueOf( Math.floor( 100 * realcount /
                             plancount ) ) + "%";
                float fPRate = 100 * realcount / plancount;
                float fLRate = 100 - fPRate;
                lossrate = String.valueOf( fLRate ) + "%";
            }

            plan.setPatrolrate( patrolrate );
            plan.setLossrate( lossrate );

        //    System.out.println( "巡检率 ：" + plan.getPatrolrate() );
      //      System.out.println( "漏检率 ：" + plan.getLossrate() );

            reportList.add( plan );
        }

    }


    /**
     * 取得计划列表
     * @param condition QueryCondition
     * @return Vector
     */
    public Vector getPlanListVct( QueryCondition condition ) throws Exception{

        Vector planListVct = new Vector();

        String patrolid = condition.getPatrolid(); //巡检维护组id
        QuerySqlBuild sqlbuild = QuerySqlBuild.newInstance( this.makePlanlistSQL() );
        sqlbuild.addConstant( this.makePlanlistConstant() );

        sqlbuild.addConditionAnd( "a.executorid={0}", condition.getPatrolid() );
        sqlbuild.addConditionAnd( "c.contractorid={0}", condition.getDeptid() );
        sqlbuild.addConditionAnd( "d.lineid={0}", condition.getLineid() );

        //sqlbuild.addConditionAnd("a.begindate>={0}", condition.getBegindate());
        //sqlbuild.addConditionAnd("a.enddate<={0}", condition.getEnddate());

        sqlbuild.addDateFormatStrEnd( "a.begindate",
            DateUtil.DateToString( condition.getBegindate() ), ">=" );
        sqlbuild.addDateFormatStrEnd( "a.begindate",
            DateUtil.DateToString( condition.getEnddate() ),
            "<=" );

        sqlbuild.addConstant( this.makePlanlistOrderby() );
        QueryUtil queryUtil = new QueryUtil();

        List resultList;
        Iterator iter;
        DynaBean record;

        //extor for String to Date
        DateUtil dateutil = new DateUtil();
    //    System.out.println( " :::::::::::: " + sqlbuild.toSql() );
        resultList = queryUtil.queryBeans( sqlbuild.toSql() );
        if( resultList.size() > 0 ){
            iter = resultList.iterator();
            while( iter.hasNext() ){
                record = ( DynaBean )iter.next();
                Plan plan = new Plan();

                plan.setPlanid( ( String )record.get( "planid" ) );
                plan.setPlanname( ( String )record.get( "planname" ) );
                plan.setContractorid( ( String )record.get( "contractorid" ) );
                plan.setContractorname( ( String )record.get( "contractorname" ) );
                plan.setPatrolid( ( String )record.get( "patrolid" ) );
                plan.setPatrolname( ( String )record.get( "patrolname" ) );
//                plan.setLineid( (String) record.get("lineid"));

                String begindate = ( String )record.get( "begindate" );
     //           System.out.println( "开始日期 ：" + begindate );
                String enddate = ( String )record.get( "enddate" );

                plan.setBegindateStr( begindate );
                plan.setEnddateStr( enddate );
                plan.setCreator( ( String )record.get( "creator" ) );
                plan.setCreatedate( ( String )record.get( "createdate" ) );

                plan.setWeeknum( DateTools.getWeekOfYear( begindate ) );

                plan.setBegindate( dateutil.StringToDate( begindate ) );
                plan.setEnddate( dateutil.StringToDate( enddate ) );

                //store the data into a Vector
                planListVct.add( plan );

            }

        }
        //取得计划对应点信息
        getPlanPoints( planListVct, patrolid );

        return planListVct;
    }


    /**
     * 将Plan对象的任务对应点基本属性、应巡检次数属性象加入
     * @param planListVct Vector
     * @throws Exception
     */
    public void getPlanPoints( Vector planListVct, String patrolid ) throws
        Exception{

        Vector newPlanListVct = new Vector();

        int loopN = 0;
        if( planListVct.size() > 0 ){
            loopN = planListVct.size();
//            System.out.print( "取得计划数量 : " + planListVct.size() );
        }

        for( int i = 0; i < loopN; i++ ){
            Plan plan = ( Plan )planListVct.get( i );

            QuerySqlBuild sqlbuild = QuerySqlBuild.newInstance( this.
                                     makePlanPointslistSQL() );
            sqlbuild.addConstant( this.makePlanPointslistConstant() );
            sqlbuild.addConditionAnd( "e.planid={0}", plan.getPlanid() );
            sqlbuild.addConditionAnd( "f.lineid={0}", plan.getLineid() );
            sqlbuild.addConstant( this.makePlanPointslistOrderby() );

            QueryUtil queryUtil = new QueryUtil();

   //         System.out.println( "+++++++++++++++++++++++ " + sqlbuild.toSql() );

            List resultList = queryUtil.queryBeans( sqlbuild.toSql() );

            Vector planpatrolVct = new Vector();

            if( resultList.size() > 0 ){
                Iterator iter = resultList.iterator();
                while( iter.hasNext() ){

                    DynaBean record = ( DynaBean )iter.next();

                    PatrolPoint point = new PatrolPoint();

                    point.setPointid( ( String )record.get( "pointid" ) );
                    point.setPointname( ( String )record.get( "pointname" ) );
                    point.setSublineid( ( String )record.get( "sublineid" ) );
                    point.setSublinename( ( String )record.get( "sublinename" ) );
                    point.setAddressInfo( ( String )record.get( "addressinfo" ) );
                    point.setLinetype( ( String )record.get( "linetype" ) );
                    point.setPatrolid( ( String )record.get( "patrolid" ) );
                    point.setTimesneedtopatroled( Integer.parseInt( ( String )
                        record.get( "executetimes" ) ) );

                    //取得巡检情况 begin

                    sqlbuild = QuerySqlBuild.newInstance(
                               "select count(id) from planexecute" );
                    sqlbuild.addConditionAnd( "executorid={0}",
                        patrolid );
                    sqlbuild.addConditionAnd( "pid={0}",
                        point.getPointid() );
                    sqlbuild.addDateFormatStrEnd( "executetime",
                        DateUtil.
                        DateToString( plan.getBegindate() ),
                        ">=" );
                    sqlbuild.addDateFormatStrEnd( "executetime",
                        DateUtil.
                        DateToString( plan.getEnddate() ),
                        "<=" );

                    QueryUtil queryUtil2 = new QueryUtil();
                    String[][] result = queryUtil2.
                                        executeQueryGetArray(
                                        sqlbuild.
                                        toSql(), "0" );

                    int patrolcount = Integer.parseInt( result[0][0] );
                    if( patrolcount > point.getTimesneedtopatroled() ){
                        patrolcount = point.getTimesneedtopatroled();
                    }
                    point.setTimespatroled( patrolcount );

                    //取得巡检情况 end

                    planpatrolVct.add( point );
                }
            }
            //store the data into plan object
            plan.setPlanpatrol( planpatrolVct );
            newPlanListVct.add( plan );
        }

        planListVct = newPlanListVct;

        return;
    }


    /**
     * 构造基本sql， 取得计划列表
     * @return String
     */
    public String makePlanlistSQL(){
        String sql = " \n";

        sql += " select	distinct																							\n";
        sql += " a.id planid, a.creator creator, a.planname planname, c.contractorid contractorid, c.contractorname contractorname, a.executorid patrolid, b.patrolname patrolname, to_char(a.begindate,'yyyy/mm/dd') begindate, to_char(a.enddate,'yyyy/mm/dd') enddate, to_char(a.createdate,'yyyy/mm/dd') createdate	\n";
        sql += " from																								\n";
        sql += " plan a,																					\n";
        sql += " patrolmaninfo b,																							\n";
        sql += " contractorinfo c,																					\n";
        sql += " lineinfo d,																						\n";
        sql += " sublineinfo e, 																							\n";
        sql += " taskinfo f, 																							\n";
        sql += " subtaskinfo g ,																							\n";
        sql += " plantasklist h	,																						\n";
        sql += " pointinfo i																							\n";

        return sql;
    }


    /**
     * 添加基本查询条件， 取得计划列表
     * @return String
     */
    public String makePlanlistConstant(){
        String constant = "";

        constant += " 	a.executorid = b.patrolid                                                                                                                                                                       \n";
        constant += " and	b.parentid = c.contractorid                                                                                                                                                                  \n";
        constant += " and	h.planid = a.id                                                                                                                                                                  \n";
        constant += " and	h.taskid = f.id                                                                                                                                                                  \n";
        constant += " and	g.taskid = f.id                                                                                                                                                                  \n";
        constant += " and	g.objectid = i.pointid                                                                                                                                                                  \n";
        constant += " and	i.sublineid = e.sublineid                                                                                                                                                                  \n";
        constant += " and	e.lineid = d.lineid                                                                                                                                                                  \n";

        return constant;
    }


    /**
     * 添加排序，  取得计划列表
     * @return String
     */
    public String makePlanlistOrderby(){
        String orderby = " ";
        orderby +=
            " order by contractorname, patrolname, begindate ";
        return orderby;
    }


    /**
     * 取得每个计划对应点信息 / 巡检次数信息
     * @return String
     */
    public String makePlanPointslistSQL(){
        String sql = " \n";

        sql +=
            " select distinct                                                       \n";
        sql +=
            " c.pointid pointid,                                                    \n";
        sql +=
            " max(a.excutetimes) executetimes,                                      \n";
        sql +=
            " max(c.pointname) pointname,                                           \n";
        sql +=
            " max(c.addressinfo) addressinfo,                                       \n";
        sql +=
            " max(c.sublineid) sublineid,                                           \n";
        sql +=
            " max(d.sublinename) sublinename,                                       \n";
        sql +=
            " max(d.linetype) linetype,                                             \n";
        sql +=
            " max(c.patrolid) patrolid,                                            \n";
        sql +=
            " max(c.inumber) inumber                                                \n";
        sql +=
            " from                                                                  \n";
        sql +=
            " taskinfo a, subtaskinfo b, pointinfo c, sublineinfo d, plantasklist e, lineinfo f \n";

        return sql;
    }


    /**
     * 添加基本查询条件， 取得每个计划对应点信息 / 巡检次数信息
     * @return String
     */
    public String makePlanPointslistConstant(){
        String constant = "";

        constant += " a.id = b.taskid               \n";
        constant += " and b.objectid = c.pointid    \n";
        constant += " and c.sublineid = d.sublineid \n";
        constant += " and a.id = e.taskid           \n";
        constant += " and f.lineid = d.lineid           \n";
        //and e.planid = '0000000022200502'
        return constant;
    }


    /**
     * 添加排序， 取得每个计划对应点信息 / 巡检次数信息
     * @return String
     */
    public String makePlanPointslistOrderby(){
        String orderby = " ";
        orderby +=
            " group by pointid order by sublineid, linetype, inumber ";
        return orderby;
    }

}
