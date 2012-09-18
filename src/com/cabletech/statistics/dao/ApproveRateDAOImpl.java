package com.cabletech.statistics.dao;

import java.util.*;

import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.sqlbuild.*;
import com.cabletech.statistics.beans.*;
import org.apache.log4j.Logger;

public class ApproveRateDAOImpl{
    private Logger logger = Logger.getLogger(ApproveRateDAOImpl.class);
    public ApproveRateDAOImpl(){
    }


    /**
     * 取得计划通过率
     * @param conBean ApproveRateBean
     * @return ApproveRateBean
     * @throws Exception
     */
    public ApproveRateBean getApproveRate( ApproveRateBean conBean ) throws
        Exception{
        ApproveRateBean resultBean = new ApproveRateBean();

        if( conBean.getCyctype().equals( "1" ) ){
            conBean.setBeginyear( null );
            conBean.setBeginmonth( null );
            conBean.setEndyear( null );
            conBean.setEndmonth( null );
            conBean.setBeginYM( null );
            conBean.setEndYM( null );

            conBean.setStatistictime( conBean.getStatisticyear() + "年" );
        }
        else{
            conBean.setStatisticyear( null );
            conBean.setStatistictime( conBean.getBeginyear() + "年" +
                conBean.getBeginmonth() + "月" + "  -  " +
                conBean.getEndyear() + "年" +
                conBean.getEndmonth() + "月" );
        }

        if( conBean.getApproveplantype().equals( "0" ) ){
            conBean.setApproveplantype( "年计划与月计划" );
        }
        else{
            if( conBean.getApproveplantype().equals( "1" ) ){
                conBean.setApproveplantype( "仅年计划" );
            }
            else{
                conBean.setApproveplantype( "仅月计划" );
            }
        }

        BeanUtil.objectCopy( conBean, resultBean );

        Vector planVct = getPlanVct( conBean );
        putApproveData( resultBean, planVct, conBean );

        return resultBean;
    }


    /**
     * 取得审批通过率
     * @param resultBean ApproveRateBean
     * @param planVct Vector
     */
    private void putApproveData( ApproveRateBean resultBean, Vector planVct,
        ApproveRateBean conBean ) throws
        Exception{

        int iTimes = Integer.parseInt( conBean.getApprovetimes(), 10 );

        int appPlanNum = 0;
        int plannum = planVct.size();

        resultBean.setPlannum( plannum );

        for( int i = 0; i < planVct.size(); i++ ){
            Vector onePlanVct = ( Vector )planVct.get( i );
            String planid = ( String )onePlanVct.get( 1 );

            String sql =
                "select status from planapprovemaster where planid = '" +
                planid + "' order by approvedate";
            QueryUtil queryutil = new QueryUtil();
            Vector tempVct = queryutil.commonQueryWithFieldNum( sql, 1 );

            for( int k = 0; k < iTimes; k++ ){

                if( tempVct.size() < k || tempVct.size() == 0 || tempVct == null ){
                    break;
                }

                Vector inTempVct = ( Vector )tempVct.get( k );
                String status = ( String )inTempVct.get( 0 );

                if( status.equals( "1" ) ){
                    appPlanNum++;
                    break;
                }
            }

        }

        resultBean.setAppplannum( appPlanNum );

        String apprate = "100%";
        String appratenumber = "100";
        String unapprate = "0%";
        String unappratenumber = "0";
        if( plannum > 0 ){
            apprate = String.valueOf( 100 * appPlanNum / plannum ) + "%";
            appratenumber = String.valueOf( ( int )100 * appPlanNum / plannum );
            unapprate = String.valueOf( 100 - ( 100 * appPlanNum / plannum ) ) +
                        "%";
            unappratenumber = String.valueOf( ( int ) ( 100 - ( 100 * appPlanNum / plannum ) ) );

            resultBean.setIfhasrecord( "1" );
        }
        else{
            resultBean.setIfhasrecord( "0" );
        }

        resultBean.setApprovetimes( resultBean.getApprovetimes() + "次审批" );
        resultBean.setUnapproveplannum( plannum - appPlanNum );
        resultBean.setApproverate( apprate );
        resultBean.setUnapproverate( unapprate );
        resultBean.setAppratenumber( appratenumber );
        resultBean.setUnappratenumber( unappratenumber );

    }


    /**
     * 取得符合条件年月计划
     * @param conBean ApproveRateBean
     * @return Vector
     */
    private Vector getPlanVct( ApproveRateBean conBean ) throws Exception{
        Vector planVct = new Vector();
        String sql =
            "select a.plantype plantype, a.id planid, a.year year, a.month month  from yearmonthplan a, contractorinfo b";
        QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance( sql );

        sqlBuild.addConstant( " a.regionid = b.regionid " );
        sqlBuild.addConditionAnd( "b.contractorid = {0}",
            conBean.getContractorid() );
        
        sqlBuild.addConditionAnd( "a.regionid = {0}",
                conBean.getRegionid() );
        String bym = null;
        String eym = null;
        if(conBean.getBeginyear()!=null)
        	bym = conBean.getBeginyear()+conBean.getBeginmonth();
        if(conBean.getEndyear() != null)
        	eym = conBean.getEndyear()+conBean.getEndmonth();
        sqlBuild.addConditionAnd( "((concat(a.year,a.month) >= {0}", bym);
        sqlBuild.addConditionAnd( "concat(a.year,a.month) <= {0})", eym );
        if(bym != null && eym != null)
        	sqlBuild.addConstant(" or a.month is null )");
        sqlBuild.addConditionAnd( "a.year >= {0}", conBean.getBeginyear() );
        sqlBuild.addConditionAnd( "a.year <= {0}", conBean.getEndyear() );
        sqlBuild.addConditionAnd( "a.year = {0}", conBean.getStatisticyear() );
        //sqlBuild.addConditionAnd( "a.month >= {0}", conBean.getBeginmonth() );
        //sqlBuild.addConditionAnd( "a.month <= {0}", conBean.getEndmonth() );
        //logger.info("统计规则："+conBean.getApproveplantype());
        if( conBean.getApproveplantype().equals( "仅年计划" ) ){
            sqlBuild.addConstant( " and a.month is null " );
        }
        else{
            if( conBean.getApproveplantype().equals( "仅月计划" ) ){
                sqlBuild.addConstant( " and a.month is not null " );
            }
        }

        sqlBuild.addConstant( " order by plantype, year" );

        sql = sqlBuild.toSql();

        logger.info( " SQL :" + sql );

        QueryUtil queryutil = new QueryUtil();
        planVct = queryutil.commonQueryWithFieldNum( sql, 4 );

        return planVct;
    }
}
