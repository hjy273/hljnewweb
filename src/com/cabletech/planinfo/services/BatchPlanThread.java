package com.cabletech.planinfo.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.planinfo.beans.BatchBean;
import com.cabletech.planinfo.domainobjects.ExecuteLog;

public class BatchPlanThread implements Runnable{
	private Logger logger = Logger.getLogger("BatchPlanThread");
	private String deptname;
	private UserInfo user;
	private BatchBean batch;
	private List stencilList;
	private int patrolArNum;
	public BatchPlanThread(String deptname, UserInfo user, BatchBean batch, List stencilList,int patrolArNum){
		this.deptname = deptname;
		this.user = user;
		this.batch = batch;
		this.stencilList = stencilList;
		this.patrolArNum = patrolArNum;
	}
	public void run() {
		BatchPlanBO batchBO = new  BatchPlanBO();
		logger.info("��ʼ�����ƶ��ƻ���ѡ�� "+patrolArNum+"��Ѳ����,"+(patrolArNum-stencilList.size()==0 ?"":"��"+(patrolArNum-stencilList.size())+"�������ԡ�"));
		ExecuteLogBO elbo = new ExecuteLogBO();
		ExecuteLog log = new ExecuteLog(user.getUserID(),ExecuteLog.TYPE_BATCH,"�����ƶ�"+deptname+""+batch.getStartdate()+"-"+batch.getEnddate()+"�����ƻ�");
		elbo.record(log);
		log.setResult("���������ƻ��ɹ���ѡ�� "+patrolArNum+"��Ѳ����,"+(patrolArNum-stencilList.size()==0 ?"":"��"+(patrolArNum-stencilList.size())+"�������ԡ�"));
		batchBO.createBatchPlan4Stencil(deptname,user.getRegionid(),batch,stencilList,log);
		elbo.updateRecord(log);
		logger.info("�����ƶ��ƻ�������"+log);
	}
}
