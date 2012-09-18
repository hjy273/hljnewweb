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
		logger.info("��ʼ���Ƽƻ���"+plan.toString());
		ExecuteLogBO elbo = new ExecuteLogBO();
		ExecuteLog log = new ExecuteLog(plan.getUserID(),ExecuteLog.TYPE_COPY,"���ڸ��Ƽƻ�");
		//System.out.println(log);
		elbo.record(log);
		CopyPlanBO bo = new CopyPlanBO();
		bo.copyPlan(plan,log);
		//System.out.println("���� ��"+log);
		elbo.updateRecord(log);
		logger.info("���Ƽƻ�������"+log);
	}
	public CopyBean getPlan() {
		return plan;
	}
	public void setPlan(CopyBean plan) {
		this.plan = plan;
	}

}
