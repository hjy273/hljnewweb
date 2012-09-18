package com.cabletech.planinfo.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.HibernateDaoSupport;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.planinfo.domainobjects.ExecuteLog;

public class ExecuteLogDAOImpl extends HibernateDaoSupport{
	private Logger logger = Logger.getLogger("ExecuteLogDAOImpl");
	public void insertLog(ExecuteLog log){
		String sql = "insert into EXECUTELOG (id,userid,createdate,type,result) " +
				" values ('"+log.getId()+"','"+log.getUserId()+"',sysdate,'"+log.getType()+"','"+log.getResult()+"')";
		UpdateUtil insert = null;
		logger.info("insert sql :"+sql);
		try {
			insert = new UpdateUtil();
			insert.setAutoCommitFalse();
			insert.executeUpdate(sql);
			insert.commit();
			insert.setAutoCommitTrue();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			insert.commit();
		}
	}
	public void updateLog(ExecuteLog log){
		String sql  = "update EXECUTELOG set enddate=sysdate ,result='"+log.getResult()+"',exception = '"+log.getException()+"' " +
				" where id='"+log.getId()+"'";
		UpdateUtil update = null;
		logger.info("update sql :"+sql);
		try {
			
			update = new UpdateUtil();
			update.setAutoCommitFalse();
			update.executeUpdate(sql);
			update.commit();
			update.setAutoCommitTrue();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			update.commit();
		}
	}
	public List getExecuteLogs(String userid) {
		String querysql = "select id,userid,to_char(createdate,'yyyy/mm/dd hh24:mi:ss') createdate,to_char(enddate,'yyyy/mm/dd hh24:mi:ss') enddate," +
				" decode(type,'0','复制','1','批量','复制') type,result from EXECUTELOG where userid='"+userid+"' order by createdate desc";
		System.out.println("query sql :"+querysql);
		QueryUtil query = null;
		try {
			query = new QueryUtil();
			return query.queryBeans(querysql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
