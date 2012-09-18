package com.cabletech.baseinfo.services;

import java.util.*;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planstat.beans.PatrolStatConditionBean;
import com.cabletech.planstat.services.PlanProgressBO;

public class InitDisplayPlanBO extends BaseBisinessObject{
	private Logger logger = Logger.getLogger("InitDisplayPlanBO");
	//将开始的
	public List getShallStartPlan(UserInfo user){
		String sql = "select p.id,p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate,m.patrolname" +
				" from plan p ,patrolmaninfo m where p.executorid = m.patrolid and p.begindate > sysdate and p.begindate < sysdate+5";
		if(user.getType().equals("12")){
			sql += " and m.regionid='"+user.getRegionid()+"'";
		}
		if(user.getType().equals("22")){
			sql += " and m.parentid='"+user.getDeptID()+"'";
		}
		List shallStartPlan = new ArrayList();
		QueryUtil query;
		try {
			query = new QueryUtil();
			shallStartPlan = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return shallStartPlan;
	}
	//正在进行的
	public List getProgressPlan(UserInfo user){
		List progressPlan = new ArrayList();
		PlanProgressBO bo = new PlanProgressBO();
		PatrolStatConditionBean bean = new PatrolStatConditionBean();
		bean.setConID(user.getDeptID());
		progressPlan = bo.getPlanProgressList(user, bean);
		return progressPlan;
	}
	//已结束的 
	public List getFulfillPlan(UserInfo user){
		String sql = "select p.id,p.planname,to_char(p.begindate,'YYYY-MM-DD') begindate,to_char(p.enddate,'YYYY-MM-DD') enddate"
			+ " ,m.patrolname,ps.patrolp from plan p,plan_stat ps,patrolmaninfo m"
			+ " where p.id=ps.planid and m.patrolid=ps.executorid and p.enddate >= sysdate-5 and p.enddate <sysdate" ;
		if(user.getType().equals("12")){
			sql += " and m.regionid='"+user.getRegionID()+"'";
		}
		if(user.getType().equals("22")){
			sql += " and m.parentid='"+user.getDeptID()+"'";
		}
		List fulfillPlan = new ArrayList();
		QueryUtil query;
		try {
			query = new QueryUtil();
			fulfillPlan = query.queryBeans(sql);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return fulfillPlan;
	}
	
}
