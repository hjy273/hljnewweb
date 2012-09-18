package com.cabletech.planinfo.services;

import org.apache.log4j.Logger;

import com.cabletech.planinfo.bean.CopyBean;
import com.cabletech.planinfo.domainobjects.ExecuteLog;


public class CopyPlanTread implements Runnable {
	private Logger logger = Logger.getLogger("CopyPlanTread");
	public CopyPlanTread(CopyBean plan){
		this.plan = plan;
	}
	private CopyBean plan;
	public void run() {
		logger.info("开始复制计划："+plan.toString());
		ExecuteLogBO elbo = new ExecuteLogBO();
		ExecuteLog log = new ExecuteLog(plan.getUserID(),ExecuteLog.TYPE_COPY,"正在复制计划");
		//System.out.println(log);
		elbo.record(log);
		CopyPlanBO bo = new CopyPlanBO();
		bo.copyPlan(plan,log);
		//System.out.println("结束 ："+log);
		elbo.updateRecord(log);
		logger.info("复制计划结束："+log);
	}
	public CopyBean getPlan() {
		return plan;
	}
	public void setPlan(CopyBean plan) {
		this.plan = plan;
	}

}
