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
		logger.info("开始批量制定计划：选择 "+patrolArNum+"个巡检组,"+(patrolArNum-stencilList.size()==0 ?"":"有"+(patrolArNum-stencilList.size())+"个被忽略。"));
		ExecuteLogBO elbo = new ExecuteLogBO();
		ExecuteLog log = new ExecuteLog(user.getUserID(),ExecuteLog.TYPE_BATCH,"正在制定"+deptname+""+batch.getStartdate()+"-"+batch.getEnddate()+"批量计划");
		elbo.record(log);
		log.setResult("批量创建计划成功！选择 "+patrolArNum+"个巡检组,"+(patrolArNum-stencilList.size()==0 ?"":"有"+(patrolArNum-stencilList.size())+"个被忽略。"));
		batchBO.createBatchPlan4Stencil(deptname,user.getRegionid(),batch,stencilList,log);
		elbo.updateRecord(log);
		logger.info("批量制定计划结束："+log);
	}
}
