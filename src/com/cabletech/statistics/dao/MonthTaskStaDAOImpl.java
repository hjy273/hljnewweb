package com.cabletech.statistics.dao;

import java.util.*;

import org.apache.log4j.*;
import com.cabletech.commons.beans.*;
import com.cabletech.commons.hb.*;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.statistics.beans.*;
import com.cabletech.statistics.domainobjects.*;
import com.cabletech.statistics.utils.*;
import com.cabletech.utils.*;

public class MonthTaskStaDAOImpl{
    private Logger logger = Logger.getLogger( MonthTaskStaDAOImpl.class );

    public MonthTaskStaDAOImpl(){
        try{
            jbInit();
        }
        catch( Exception ex ){
            ex.printStackTrace();
        }
    }


    private void jbInit() throws Exception{
    }


    /**
     * ȡ�����������ͳ��
     * @param conBean QueryConditionBean
     * @return Hashtable
     * @throws Exception
     */
    public Hashtable getMonthTaskStaVct( QueryConditionBean conBean ) throws
        Exception{
        Hashtable MonthTaskHt = new Hashtable();

        String cyctype = conBean.getCyctype();
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
        Vector planVct = PatrolDAO.getPlanListVct( condition );

        if( planVct.size() > 0 ){

            MonthTaskHt.put( "ifhasrecord", "1" ); //�м�¼����ʾ

            MonthTaskHt.put( "year", conBean.getYear() ); //��
            MonthTaskHt.put( "month", conBean.getMonth() ); //��
            MonthTaskHt.put( "patrol", conBean.getPatrolname() ); //Ѳ��Ա

            Vector opVct = getTaskOperations( conBean.getRegionid() );
            MonthTaskHt.put( "oplist", opVct ); //operation

            Vector planWithOpVct = new Vector();

            for( int i = 0; i < planVct.size(); i++ ){
                Plan plan = ( Plan )planVct.get( i );
                Vector reOpVct = getRelativeTaskOperations( plan.getPlanid() );
                Vector onePlanVct = new Vector();

                onePlanVct.add( plan.getWeeknum() );
                reputVector( onePlanVct, opVct, reOpVct );

                planWithOpVct.add( onePlanVct );

            }

        }
        else{
            MonthTaskHt.put( "ifhasrecord", "0" ); //�޼�¼
        }

        return MonthTaskHt;
    }


    /**
     * ������װ���ݷ�װ
     * @param planOpVct Vector
     * @param opVct Vector
     * @param reOpVct Vector
     * @return Vector
     */
    private Vector reputVector( Vector onePlanVct, Vector opVct, Vector reOpVct ){
        for( int i = 0; i < opVct.size(); i++ ){
            Vector oneOp = ( Vector )opVct.get( i );
            String oneOpId = ( String )oneOp.get( 0 );

            int ifHasFlag = 0;

            for( int k = 0; k < reOpVct.size(); k++ ){

                Vector oneReOp = ( Vector )reOpVct.get( k );
                String oneReOpId = ( String )oneReOp.get( 0 );

                if( oneReOpId.equals( oneOpId ) ){
                    ifHasFlag = 1;
                    break;
                }
            }

            if( ifHasFlag == 1 ){
                onePlanVct.add( "1" );
            }
            else{
                onePlanVct.add( "0" );
            }
        }

        return onePlanVct;
    }


    /**
     * ȡ�ò����б�
     * @param regionid String
     * @return Vector
     * @throws Exception
     */
    public Vector getTaskOperations( String regionid ) throws Exception{
        Vector vct = new Vector();

        String sql = " select distinct id , operationname from taskoperation ";
        //if (regionid.length() > 0) {
        //sql = sql + " where regionid = '" + regionid + "'";
        //sql = sql + " ";
        //}

        logger.info( " ȡ��Ѳ����� ��" + sql );

        QueryUtil queryutil = new QueryUtil();
        vct = queryutil.executeQueryGetStringVector( sql, "" );

        return vct;
    }


    /**
     * ȡ�üƻ���Ӧ�����б�
     * @param planid String
     * @return Vector
     * @throws Exception
     */
    public Vector getRelativeTaskOperations( String planid ) throws Exception{
        Vector vct = new Vector();

        String sql = " \n";
        sql += " select	top.id id,	top.operationname name	from       \n";
        sql += " plan plan,	plantasklist ptl,	taskoperation top,	taskoperationlist topl	where          \n";
        sql += " plan.id = '" + planid + "'      \n";
        sql += " and	plan.id = ptl.planid	and	ptl.taskid = topl.taskid	and	topl.operationid = top.id         \n";

        logger.info( "ȡ�üƻ���ӦѲ����� ��" + sql );

        QueryUtil queryutil = new QueryUtil();
        vct = queryutil.executeQueryGetStringVector( sql, "" );

        return vct;
    }

}
