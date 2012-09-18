package com.cabletech.statistics.dao;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;

public class NewPlanRateQueryDao{
    private Logger logger = Logger.getLogger( this.getClass().getName() );
    public NewPlanRateQueryDao(){
    }



    public PatrolRate getPatrolRete( QueryCondition condition ){
        PatrolRate data = new PatrolRate();

        String lineSql = "";
        String plansql = " (select id from plan "
                         + " where  begindate >=TO_DATE('" + DateUtil.DateToString( condition.getBegindate() )
                         + "','yyyy-MM-dd')"
                         + " and enddate <= TO_DATE('" + DateUtil.DateToString( condition.getEnddate() )
                         + "','yyyy-MM-dd')";
        if( condition.getPatrolid() != null ){
            plansql += "  and executorid='" + condition.getPatrolid() + "' )";
        }
        if( condition.getDeptid() != null ){
            plansql += " and executorid in ( select patrolid from patrolmaninfo where parentid ='"
                + condition.getDeptid() + "' ))";
        }
        String planExecute = "( select s.objectid id,SUM(t.EXCUTETIMES) plantimes"
                             + " from subtaskinfo s , taskinfo t where  s.taskid=t.id and taskid in "
                             + "	( select taskid from plantasklist where planid in" + plansql
                             + ") group by s.objectid) b";

        String realsql = "( select pid id,count(pid) realtimes from planexecute "
                         + " where  executetime>=  TO_DATE('" + DateUtil.DateToString( condition.getBegindate() )
                         + "','yyyy-MM-dd')" + " and executetime <= TO_DATE('"
                         + DateUtil.DateToString( condition.getEnddate() ) + " 23:59:59','yyyy-MM-dd HH24:MI:SS')"
                         + " and executorid in (select executorid from plan where id in " + plansql + ")"
                         + " group by pid	 ) a ,pointinfo po";

        String sql = " select b.id, b.plantimes,a.realtimes,po.isfocus from " + planExecute + "," + realsql
                     + " where po.pointid = b.id and b.id=a.id(+)";
       //单独处理线路
        if( condition.getLineid() != null ){
            lineSql =  " (SELECT pointid"
                    + " FROM POINTINFO"
                    + " WHERE sublineid IN ("
                    + " 					SELECT sublineid"
                    + " 					FROM SUBLINEINFO"
                    + "                     WHERE lineid =  '" + condition.getLineid() + "')) ";

            plansql += " and id in (select  distinct planid from plantasklist where taskid in "
                + "  (select distinct taskid from subtaskinfo where objectid in "
                + " (select pointid from pointinfo where sublineid in "
                + " (select sublineid from sublineinfo where lineid='" + condition.getLineid() + "')))))";

            planExecute = "( select s.objectid id,SUM(t.EXCUTETIMES) plantimes"
                        + " from subtaskinfo s , taskinfo t "
                        + " where  s.taskid=t.id "
                        + " and s.objectid in " + lineSql
                        + " and taskid in "
                        + "	( select taskid from plantasklist where planid in" + plansql
                        + " ) group by s.objectid) b";

            realsql = "( select pid id,count(pid) realtimes "
                      + " from planexecute "
                      + " where  executetime>=  TO_DATE('" + DateUtil.DateToString( condition.getBegindate() ) + "','yyyy-MM-dd')"
                      + " and executetime <= TO_DATE('" + DateUtil.DateToString( condition.getEnddate() ) + " 23:59:59','yyyy-MM-dd HH24:MI:SS')"
                      + " and pid in " + lineSql
                      + " and executorid in (select executorid from plan where id in " + plansql + ")"
                         + " group by pid	 ) a ,pointinfo po";

               sql = " select b.id, b.plantimes,a.realtimes,po.isfocus from " + planExecute + "," + realsql
                     + " where po.pointid = b.id and b.id=a.id(+)";


        }
        logger.warn( "**************:" + sql );
        String[][] lPoint;

        try{
            QueryUtil query = new QueryUtil();
            lPoint = query.executeQueryGetArray( sql, "0" );
            if( lPoint == null ){
                data.setIfhasrecord( "0" );
                return data;
            }
            int plancount = 0; //
            int realcount = 0; //
            int plancountadd = 0; //
            int realcountadd = 0; //

            for( int i = 0; i < lPoint.length; i++ ){
                plancount += Integer.parseInt( lPoint[i][1] ); //
                if( Integer.parseInt( lPoint[i][3] ) == 0 ){
                    plancountadd += Integer.parseInt( lPoint[i][1] );
                }
                else{
                    plancountadd += Integer.parseInt( lPoint[i][1] ) * 3;
                }


                if( Integer.parseInt( lPoint[i][1] ) < Integer.parseInt( lPoint[i][2] ) ){
                    realcount += Integer.parseInt( lPoint[i][1] );
                    if( Integer.parseInt( lPoint[i][3] ) == 0 ){
                        realcountadd += Integer.parseInt( lPoint[i][1] );
                    }
                    else{
                        realcountadd += Integer.parseInt( lPoint[i][1] ) * 3;
                    }
                }
                else{
                    realcount += Integer.parseInt( lPoint[i][2] );
                    if( Integer.parseInt( lPoint[i][3] ) == 0 ){
                        realcountadd += Integer.parseInt( lPoint[i][2] );
                    }
                    else{
                        realcountadd += Integer.parseInt( lPoint[i][2] ) * 3;
                    }

                }
            }

            logger.info( "plan:" + plancount + "    real:" + realcount );
            logger.info( "palnadd:" + plancountadd + "    realcountadd:" + realcountadd );

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

            String patrolrateadd = "100%";
            String lossrateadd = "0%";
            String patrolrateaddNumber = "100";
            String lossrateaddNumber = "0";

            if( plancount > 0 ){
                patrolrateadd = String.valueOf( Math.floor( 100 * realcountadd / plancountadd ) ) + "%"; //巡检率
                patrolrateaddNumber = String.valueOf( ( int )Math.floor( 100 * realcountadd / plancountadd ) );

                float fPRateadd = 100 * realcountadd / plancountadd;
                float fLRateadd = 100 - fPRateadd;
                lossrateadd = String.valueOf( fLRateadd ) + "%";
                lossrateaddNumber = String.valueOf( ( int )fLRateadd );
            }


            plancount = plancountadd;
            realcount = realcountadd;
            patrolrate = patrolrateadd;
            lossrate = lossrateadd;
            patrolrateNumber = patrolrateaddNumber;
            lossrateNumber = lossrateaddNumber;

            data.setPlancount( String.valueOf( plancount ) );
            data.setRealcount( String.valueOf( realcount ) );
            data.setLosscount( String.valueOf( plancount - realcount ) );

            data.setPatrolrate( patrolrate );
            data.setPatrolratenumber( patrolrateNumber );
            data.setLossrate( lossrate );
            data.setLossratenumber( lossrateNumber );
            data.setIfhasrecord( "1" );
            return data;
        }
        catch( Exception e ){
            data.setIfhasrecord( "0" );
            logger.warn( "查询巡检率异常:" + e.getMessage() );
            return data;
        }
    }
}
