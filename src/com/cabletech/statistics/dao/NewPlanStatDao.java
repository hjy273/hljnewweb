package com.cabletech.statistics.dao;

import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.beans.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.utils.*;
import com.cabletech.utils.*;

public class NewPlanStatDao{
    private Logger logger = Logger.getLogger( this.getClass().getName() );
    public NewPlanStatDao(){
    }



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
        Vector planVct = this.getPlanListVct( condition );
        form.setPlanvct( planVct );
        //System.out.println( " planVct.size()" + planVct.size() );
        if( planVct.size() > 0 ){
            //System.out.println("3333333:");
            form.setIfhasrecord( "1" );

            // putStatisticData(planVct, form);

            if( cyctype.equals( "month" ) ){
                formName = conBean.getPatrolname() + conBean.getYear() + "年" +
                           conBean.getMonth() + "月份巡检计划";
            }
            else{
                formName = conBean.getPatrolname() + " " + conBean.getBegindate() + "至" + conBean.getEnddate() + "巡检计划";
                //( (Plan) planVct.get(0)).getPlanname();
            }
            //System.out.println( " formName" + formName );
            form.setFormname( formName );
            NewPlanRateQueryDao newDao = new NewPlanRateQueryDao();
            PatrolRate patrolrate = newDao.getPatrolRete( condition );
            form.setPlancount( patrolrate.getPlancount() );
            form.setLosscount( patrolrate.getLosscount() );
            form.setRealcount( String.valueOf( Integer.parseInt( patrolrate.getPlancount() )
                - Integer.parseInt( patrolrate.getLosscount() ) ) );
            form.setPatrolrate( patrolrate.getPatrolrate() );
            form.setLossrate( patrolrate.getLossrate() );
            form.setSeatchby( "patrol" );

        }
        else{
            form.setIfhasrecord( "0" );
        }
        return form;

    }



    private Vector getPlanListVct( QueryCondition condition ){
        Vector vPlan = new Vector();
        String sql = "";
        sql = "select p.ID,p.PLANNAME, patrol.PATROLNAME,TO_CHAR(p.BEGINDATE,'yyyy-MM-dd') begindate, TO_CHAR(p.ENDDATE,'yyyy-MM-dd') enddate,p.CREATOR,TO_CHAR(p.createdate,'yyyy-MM-dd') createdate "
              + " from plan p,patrolmaninfo patrol "
              + " where p.EXECUTORID = patrol.PATROLID(+) "
              + " and p.begindate >= TO_DATE('" + DateUtil.DateToString( condition.getBegindate() ) + "','yyyy-MM-dd')"
              + " and p.enddate <=TO_DATE('" + DateUtil.DateToString( condition.getEnddate() ) + "','yyyy-MM-dd')";
        if( condition.getPatrolid() != null && !condition.getPatrolid().equals( "" ) ){
            sql += " and p.executorid ='" + condition.getPatrolid() + "'";
        }
        try{
            ResultSet rst = null;
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            while( rst != null && rst.next() ){
                Plan pb = new Plan();
                pb.setPlanid( rst.getString( "id" ) );
                pb.setPlanname( rst.getString( "planname" ) );
                pb.setBegindateStr( rst.getString( "begindate" ) );
                pb.setEnddateStr( rst.getString( "enddate" ) );
                pb.setBegindate( DateUtil.StringToDate( rst.getString( "begindate" ) ) );
                pb.setCreatedate( rst.getString( "createdate" ) );
                pb.setPatrolname( rst.getString( "patrolname" ) );
                pb.setContractorname( rst.getString( "creator" ) );
                vPlan.add( pb );
            }
        }
        catch( Exception e ){
            logger.warn( "获得计划基本新息异常:" + e.getMessage() );
            return null;
        }
        if( vPlan == null ){
            return null;
        }
        for( int i = 0; i < vPlan.size(); i++ ){
            ( ( Plan )vPlan.get( i ) ).setTaskvct( getPlanVct( ( ( Plan )vPlan.get( i ) ).getPlanid() ) );
            ( ( Plan )vPlan.get( i ) ).setPlanpointCount( this.getPlanPoint( ( ( Plan )vPlan.get( i ) ).getPlanid() ) );
        }
        return vPlan;
    }



    private String getPlanPoint( String planid ){
        String sql = "";
        sql = "select  SUM(t.EXCUTETIMES)"
              + " from subtaskinfo s,taskinfo t "
              + " where s.TASKID=t.ID and  s.TASKID in( select distinct taskid from plantasklist where planid='"
              + planid + "')";
        try{
            QueryUtil qu = new QueryUtil();
            return qu.executeQueryGetArray( sql, "0" )[0][0];
        }
        catch( Exception e ){
            logger.warn( "获得异常:" + e.getMessage() );
            return "未能统计";
        }
    }



    private Vector getPlanVct( String planid ){
        Vector vtask = new Vector();
        ResultSet rst;
        String sql = "";
        sql = "select t.ID,ld.NAME,t.DESCRIBTION,t.EXCUTETIMES "
              + " from taskinfo t,lineclassdic ld "
              + " where t.LINELEVEL = ld.CODE(+) and t.id in "
              + "           (select taskid from plantasklist p where  p.PLANID = '" + planid + "')";

        try{
            QueryUtil qu = new QueryUtil();
            rst = qu.executeQuery( sql );
            while( rst != null && rst.next() ){
                Task tb = new Task();
                tb.setTaskid( rst.getString( "id" ) );
                tb.setTaskname( rst.getString( "name" ) );
                tb.setTaskdisc( rst.getString( "describtion" ) );
                tb.setExecutes( rst.getString( "excutetimes" ) );
                vtask.add( tb );
            }
            return vtask;
        }
        catch( Exception e ){
            logger.warn( "获得异常:" + e.getMessage() );
            return null;
        }
    }

}
