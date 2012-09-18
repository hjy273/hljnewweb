package com.cabletech.planstat.services;

import java.util.List;
import com.cabletech.planstat.beans.PlanExeResultBean;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.planstat.domainobjects.PlanStat;

public interface PlanExeResultBO{
   public List getContractorInfoList( UserInfo userinfo);
   public List getPatrolmanInfoList( UserInfo userinfo);
   public List getPlanExeResult(UserInfo userinfo,PlanExeResultBean bean);
   public List getSubLineInfo(String strPlanID);
   public PlanStat getPlanStat( String planid ) throws Exception;
   public List getPatrolList(String strPlanID);
   public List getLeakList(String strPlanID);
   public List getWorkdayList(String strPlanID);
   public List getWorkdayText(String strPlanID,String strpatroldate);
   public boolean saveReason(String planid, String reason) ;
   
}
