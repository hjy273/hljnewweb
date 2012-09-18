package com.cabletech.planinfo.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.planinfo.dao.ExecuteLogDAOImpl;
import com.cabletech.planinfo.domainobjects.ExecuteLog;

/**
 * ��¼�ڸ��Ƽƻ��������ƶ��ƻ�ʱ����־��Ϣ
 */
public class ExecuteLogBO {
	private Logger logger = Logger.getLogger("ExecuteLogBO");
	public void record(ExecuteLog log){
		ExecuteLogDAOImpl dao = new ExecuteLogDAOImpl();
		logger.info("ExecuteLogBO record(ExecuteLog) :"+log);
		dao.insertLog(log);
	}
	public void updateRecord(ExecuteLog log){
		ExecuteLogDAOImpl dao = new ExecuteLogDAOImpl();
		logger.info("ExecuteLogBO updateRecord(ExecuteLog) :"+log);
		dao.updateLog(log);
	}
	public List getExecuteLogs(String userid){
		ExecuteLogDAOImpl dao = new ExecuteLogDAOImpl();
		return dao.getExecuteLogs(userid);
	}
}
