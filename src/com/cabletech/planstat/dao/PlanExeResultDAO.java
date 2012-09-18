package com.cabletech.planstat.dao;


import java.util.List;
import com.cabletech.planstat.domainobjects.PlanStat;

public interface PlanExeResultDAO{
    public List getBackInfoList( String hql );
    public PlanStat getPlanStat(String planid) throws Exception;

}
