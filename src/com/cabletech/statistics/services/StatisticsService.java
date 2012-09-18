package com.cabletech.statistics.services;

import java.util.*;
import javax.servlet.http.*;

import com.cabletech.commons.base.*;
import com.cabletech.planinfo.beans.*;
import com.cabletech.statistics.beans.*;
import com.cabletech.statistics.domainobjects.*;

public class StatisticsService extends BaseService{
    StatisticsBO bos;
    public StatisticsService(){
        bos = new StatisticsBO();
    }


    public ApproveRateBean getApproveRate( ApproveRateBean conBean ) throws
        Exception{
        return bos.getApproveRate( conBean );
    }


    public List GetPatrolDetailReport( QueryCondition condition ) throws
        Exception{
        List list;
        list = bos.QueryPatrolDetail( condition );
        return list;

    }


    public List GetPatrolRateReport( QueryCondition condition ) throws
        Exception{
        return bos.QueryPatrolRate( condition );
    }


    public PatrolRate getPatrolReteAsObj( QueryCondition condition ) throws
        Exception{
        return bos.getPatrolReteAsObj( condition );
    }


    public List GetLossDetailReport( QueryCondition condition ) throws
        Exception{

        List list = bos.QueryLossDetail( condition );
        return list;
    }


    public void ExportLossDetail( List list, HttpServletResponse response ) throws
        Exception{
        bos.ExportLossDetail( list, response );

    }


    public void ExportPatrolDetail( List list, HttpServletResponse response ) throws
        Exception{
        bos.ExportPatrolDetail( list, response );

    }


    public void ExportPatrolRate( PatrolRate patrolrate,
        HttpServletResponse response ) throws
        Exception{
        bos.ExportPatrolRate( patrolrate, response );

    }


    public void ExportContractorPlanForm( PatrolRate patrolrate,
        YearMonthPlanBean planbean,
        Vector taskVct,
        HttpServletResponse response ) throws
        Exception{

        bos.ExportContractorPlanForm( patrolrate, planbean, taskVct, response );

    }


    public void ExportPlanAppRate( ApproveRateBean appRate,
        HttpServletResponse response ) throws
        Exception{
        bos.ExportPlanAppRate( appRate, response );
    }


    /**
     * 导出完整报表
     * @param planform PlanStatisticForm
     * @param response HttpServletResponse
     * @throws Exception
     */
    public void ExportPlanForm( PlanStatisticForm planform,
        HttpServletResponse response,HttpServletRequest request ) throws
        Exception{
        bos.ExportPlanForm( planform, response,request );

    }


    /**
     * 取得完整报表
     * @param conBean QueryConditionBean
     * @return PlanStatisticForm
     * @throws Exception
     */
    public PlanStatisticForm getPlanStatistic( QueryConditionBean conBean ) throws
        Exception{
        return bos.getPlanStatistic( conBean );
    }


    /**
     * 取得月任务统计
     * @param conBean QueryConditionBean
     * @return Hashtable
     * @throws Exception
     */
    public Hashtable getMonthTaskStaVct( QueryConditionBean conBean ) throws
        Exception{
        return bos.getMonthTaskStaVct( conBean );
    }

}
