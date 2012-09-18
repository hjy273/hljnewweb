package com.cabletech.statistics.dao;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.utils.*;

public class QueryPatrolDetailDAOImpl{
    private Logger logger = Logger.getLogger( this.getClass().getName() );
    /**
     * 巡检明细
     * @param condition QueryCondition
     * @return List
     * @throws Exception
     */
    public List queryPatrolDetail( QueryCondition condition ) throws
        Exception{
        //初始化数据
        prepareData( condition );

     //   System.out.println( "巡检员 ：" + condition.getPatrolid() );
   //     System.out.println( "线段 ：" + condition.getSublineid() );
   //     System.out.println( "部门 ：" + condition.getDeptid() );

        QueryUtil queryUtil = new QueryUtil();
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( this.makeSQL() );
        sqlBuild.addConstant( this.makeConstant() );

        sqlBuild.addConditionAnd( "a.executorid={0}", condition.getPatrolid() );
        sqlBuild.addConditionAnd( "f.contractorid={0}", condition.getDeptid() );

        sqlBuild.addDateFormatStrEnd( "a.executetime", DateUtil.DateToString( condition.getBegindate() ), ">=" );
        sqlBuild.addDateFormatStrEnd( "a.executetime", DateUtil.DateToString( condition.getEnddate() ), "<=" );

        sqlBuild.addConditionAnd( "a.lid={0}", condition.getSublineid() );
        sqlBuild.addConstant( this.makeOrderby() );
        logger.info( "SQL:" + sqlBuild.toSql() );
        List list = queryUtil.queryBeans( sqlBuild.toSql() );
        return list;
    }


    /**
     * 初始化数据
     * @param condition QueryCondition
     * @throws Exception
     */
    public void prepareData( QueryCondition condition ) throws
        Exception{
        String patrolManID = condition.getPatrolid();
        String sublineID = condition.getSublineid();
        String deptID = condition.getDeptid();
        String queryBy = condition.getQueryby();

        if( queryBy.equalsIgnoreCase( "ByDepart" ) ){
            deptID = condition.getDeptid();
            patrolManID = null;
            sublineID = null;
        }
        if( queryBy.equalsIgnoreCase( "ByPatrolMan" ) ){
            deptID = null;
            patrolManID = condition.getPatrolid();
            sublineID = null;
        }
        if( queryBy.equalsIgnoreCase( "BySubline" ) ){
            deptID = null;
            patrolManID = null;
            sublineID = condition.getSublineid();
            //System.out.println( "按巡检段查询" );
        }

        condition.setPatrolid( patrolManID );
        condition.setSublineid( sublineID );
        condition.setDeptid( deptID );

        return;
    }


    /**
     * 构造基本sql
     * @return String
     */
    public String makeSQL(){
        String sql = " \n";

        sql += " select																								\n";
        sql += " d.patrolname patrolname, f.contractorname contractorname, c.sublinename sublinename, b.pointname pointname, b.addressinfo position, to_char(a.executetime, 'yy/mm/dd hh24:mi') executetime	\n";
        sql += " from																								\n";
        sql += " planexecute a,																							\n";
        sql += " pointinfo b,																							\n";
        sql += " sublineinfo c,																							\n";
        sql += " patrolmaninfo d,																						\n";
        sql += " contractorinfo f																						\n";

        return sql;
    }


    /**
     * 添加基本查询条件
     * @return String
     */
    public String makeConstant(){
        String constant = "";

        constant += " 	a.pid = b.pointid                                                                                                                                                                       \n";
        constant += " and	a.lid = c.sublineid                                                                                                                                                                  \n";
        constant += " and	a.executorid = d.patrolid                                                                                                                                                            \n";
        constant += " and	d.parentid = f.contractorid                                                                                                                                                          \n";

        return constant;
    }


    /**
     * 添加排序
     * @return String
     */
    public String makeOrderby(){
        String orderby = "";
        orderby += " order by 	contractorname,   patrolname,   executetime \n";
        return orderby;
    }

}
