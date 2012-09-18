package com.cabletech.statistics.dao;

import java.util.*;

import com.cabletech.commons.hb.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.services.*;

public class QueryLossDetailDAOImpl{
    public QueryLossDetailDAOImpl(){
    }


    /**
     * 取得漏检明细
     * @param condition QueryCondition
     * @return List
     * @throws Exception
     */
    public List getLossDetailReport( QueryCondition condition ) throws
        Exception{

        StatisticsBO bo = new StatisticsBO();
        List lossdetaillist = new ArrayList();
        List reportList = bo.QueryPatrolRate( condition );
        for( int i = 0; i < reportList.size(); i++ ){
            Plan plan = ( Plan )reportList.get( i );

            Vector planPatrolVct = plan.getPlanpatrol();
            for( int k = 0; k < planPatrolVct.size(); k++ ){

                PatrolPoint point = ( PatrolPoint )planPatrolVct.get( k );

                int ifInLineFlag = checkIfInLine( condition, point );

                if( ifInLineFlag == 1 &&
                    ( point.getTimesneedtopatroled() > point.getTimespatroled() ) ){

                    LossPatrol losspatrol = new LossPatrol();

                    losspatrol.setBegindate( plan.getBegindate() );
                    losspatrol.setEnddate( plan.getEnddate() );
                    losspatrol.setPlanname( plan.getPlanname() );
                    losspatrol.setPatrolname( plan.getPatrolname() );
                    losspatrol.setPointid( point.getPointid() );
                    losspatrol.setPointname( point.getPointname() );
                    losspatrol.setContractorname( plan.getContractorname() );
                    losspatrol.setPosition( point.getAddressInfo() );
                    losspatrol.setSublinename( point.getSublinename() );
                    losspatrol.setLosscount( point.getTimesneedtopatroled() -
                        point.getTimespatroled() );

                    lossdetaillist.add( losspatrol );
                }

            }

        }

        return lossdetaillist;
    }


    /**
     * 是否在制定线路中
     * @param condition QueryCondition
     * @return int
     * @throws Exception
     */
    private int checkIfInLine( QueryCondition condition, PatrolPoint pPoint ) throws
        Exception{
        int ifFlag = 0;

        if( condition.getLineid() != null && condition.getLineid().length() > 0 ){
            String sql =
                "select a.pointid from pointinfo a,sublineinfo b, lineinfo c ";
            sql += "where a.sublineid  = b.sublineid and b.lineid = c.lineid ";
            sql += "and c.lineid = '" + condition.getLineid() + "' ";
            sql += "and a.pointid = '" + pPoint.getPointid() + "' ";

            //System.out.println("****************" + sql);

            QueryUtil queryU = new QueryUtil();
            Vector vct = queryU.executeQueryGetVector( sql );

            if( vct != null && vct.size() > 0 ){
                ifFlag = 1;
            }

        }
        else{
            ifFlag = 1;
        }

        return ifFlag;
    }

}
