/*
 * BatchPlanBo
 * 
 */
package com.cabletech.planinfo.services;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.commons.beanutils.DynaBean;
import org.apache.log4j.Logger;

import com.cabletech.baseinfo.beans.PatrolManBean;
import com.cabletech.baseinfo.domainobjects.PatrolMan;
import com.cabletech.baseinfo.domainobjects.UserInfo;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.GeneratorJugUUID;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planinfo.beans.BatchBean;
import com.cabletech.planinfo.beans.StencilSubTaskBean;
import com.cabletech.planinfo.beans.StencilTaskBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.dao.BatchPlanDAOImpl;
import com.cabletech.planinfo.domainobjects.ExecuteLog;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.StencilSubTask;
import com.cabletech.planinfo.domainobjects.StencilTask;
import com.cabletech.planinfo.domainobjects.SubTask;
import com.cabletech.planinfo.domainobjects.TaskOperationList;

public class BatchPlanBO extends PlanBaseService{
	private Logger logger = Logger.getLogger("BatchPlanBO");
	BatchPlanDAOImpl bdao ;
	public BatchPlanBO(){
		bdao = new BatchPlanDAOImpl();
	}
	/**
	 * 废弃
	 * 获得所有巡检员
	 * @param deptid
	 * @return
	 */
	
	public List getPatrolList(String deptid){
		String sql = "select p.patrolid id,p.patrolname patrolname from patrolmaninfo p where  p.parentid='"+deptid+"'";
		QueryUtil query;
		try {
			query = new QueryUtil();
			List patrollist = query.queryBeans(sql);
			return patrollist;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取单位单位下所有巡检人员的计划模板
	 * @param deptid
	 * @return
	 */
	public List getPatrolStencilList(String deptid){
		/* 取得所有巡检员信息*/
		String hql = "from PatrolMan  where parentid=:deptid and state is  NULL";//
		List patrollist = new ArrayList();
		Session session = bdao.getSession();
		Transaction tx = null;
		try {
			PatrolManBean pmb = null;
			 tx = session.beginTransaction();
			 Query query = session.createQuery(hql);
			 query.setParameter("deptid", deptid);
			// query.setParameter("state", "1");
			 Iterator it = query.iterate();
			 while(it.hasNext()){
				 pmb = new PatrolManBean();
				 PatrolMan pm = (PatrolMan)it.next();
				 logger.info("巡检组名"+pm.getPatrolName());
				 BeanUtil.objectCopy(pm, pmb);
				 String hql_stencil = "from  StencilTask where patrolid=:patrolid";
				 Query query_st = session.createQuery(hql_stencil);
				 query_st.setParameter("patrolid", pm.getPatrolID());
				 Iterator it_st = query_st.iterate();
				 while(it_st.hasNext()){
					 StencilTaskBean stencil = new StencilTaskBean();
					 StencilTask st = (StencilTask)it_st.next();
					 BeanUtil.objectCopy(st, stencil);
					 pmb.getTemp().add(stencil);
				 }
				 patrollist.add(pmb);
			 }
			return patrollist;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
		
	}
	 /**
	  * 在List中查找含有key的名称 DynaBean
	  * @param patrollist
	  * @param key
	  * @return
	  */
	public String searchDynaPatrol(List patrollist,String key){
		String value="";
		for(int j= 0 ; j< patrollist.size();j++){
			DynaBean pmb =(DynaBean)patrollist.get(j);
			if(pmb.get("id").equals(key))
				value = (String) pmb.get("patrolname");
		}
		return value;
	}
	/**
	  * 在List中查找含有key的名称 PatrolManBean
	  * @param patrollist
	  * @param key
	  * @return
	  */
	public String searchPatrol(List patrollist,String key){
		String value="";
		for(int j= 0 ; j< patrollist.size();j++){
			PatrolManBean pmb =(PatrolManBean)patrollist.get(j);
			if(pmb.getPatrolID().equals(key))
				value = (String) pmb.getPatrolName();
		}
		return value;
	}
	/**
	 * 通过模板创建批量计划
	 * @param deptname
	 * @param regionid
	 * @param batch
	 * @param stencilList
	 * @return
	 */
	public boolean createBatchPlan4Stencil(String deptname, String regionid, BatchBean batch, List stencilList,ExecuteLog log) {
		TaskBO tbo = new TaskBO();
		UpdateUtil update = null;
		DBService dbs = new DBService();
		String nowdate = DateUtil.getNowDateString();
		int index = batch.getTaskopname().indexOf(batch.getStartdate().substring(0,3));
		String str = batch.getTaskopname().substring(index,batch.getTaskopname().length());
		int seq = 0;
		try {
			update = new UpdateUtil();
			update.setAutoCommitFalse();
			batch.setId(dbs.getSeq("batchplan",10));
			String sql_batch =  "insert into batchplan (id,batchname,startdate,enddate,contractorid,createdate) values ('"+batch.getId()+"','"+batch.getBatchname()+"',to_date('"+batch.getStartdate()+"','yyyy/mm/dd'),to_date('"+batch.getEnddate()+"','yyyy/mm/dd'),'"+batch.getContractorid()+"',sysdate)";
			update.executeUpdate(sql_batch);
			logger.info("共有计划数："+stencilList.size());
			for(int i = 0; i< stencilList.size();i++){
				StencilTask stencil = (StencilTask)stencilList.get(i);
				List taskstencil = tbo.toLoad(stencil.getID());
				//plan 计划
				String id = dbs.getSeq("plan",18);
				String plan_sql = "insert into plan(id,planname,executorid,creator,createdate," +
	 			" begindate,enddate,regionid,ifinnercheck,patrolmode)" 
	 			+" values ('"+id+"','"+deptname+stencil.getStencilname()+str+"','"+stencil.getPatrolid()+"','"+deptname+"',sysdate"
	 			+",to_date('"+batch.getStartdate()+"','yyyy/mm/dd'),to_date('"+batch.getEnddate()+"','yyyy/mm/dd'),'"+regionid+"','1','"+batch.getPatrolmode()+"')";
				update.executeUpdate(plan_sql);
//				更新batchplan 、plan关联表
				String batch_plan_sql = "insert into batch_plan(id,planid,batchid) values('"+dbs.getSeq("batch_plan",20)+"','"+id+"','"+batch.getId()+"')";
				logger.info(batch_plan_sql);
				update.executeUpdate(batch_plan_sql);
				//子任务
				for(int j=0;j<taskstencil.size();j++){
//					String taskid =  dbs.getSeq("taskinfo",20);
					String taskid = GeneratorJugUUID.getUUIDByRandomNumber();
					TaskBean task = (TaskBean)taskstencil.get(j);
					String task_sql = "insert into taskinfo (id,DESCRIBTION,REGIONID,EXCUTETIMES,LINELEVEL,taskpoint, factpointsnum)values ('"
							+ taskid+"','"+task.getDescribtion()+"','"+regionid+"','"+task.getExcutetimes()+"','"+task.getLinelevel()+"','"+task.getTaskPoint().size()+ "', '" + task.getFactpointsnum()  + "')";
//					String plan_task_sql = "insert into plantasklist (id,planid,taskid) values('"+dbs.getSeq("plantasklist",50)+"','"+id+"','"+taskid+"')";
					String plan_task_sql = "insert into plantasklist (id,planid,taskid) values('"+GeneratorJugUUID.getUUIDByRandomNumber()+"','"+id+"','"+taskid+"')";
					logger.info(task_sql);
					logger.info(plan_task_sql);
					update.executeUpdate(task_sql);
					update.executeUpdate(plan_task_sql);
					///巡检类型
					for(int k=0;k<task.getTaskOpList().size();k++){
						String opId = GeneratorJugUUID.getUUIDByRandomNumber();
						TaskOperationList taskop = (TaskOperationList)task.getTaskOpList().get(k);
//						String op_sql = "insert into taskoperationlist (id,taskid,OPERATIONID) values ('"+dbs.getSeq("taskoperationlist",20)+"','"+taskid+"','"+taskop.getOperationid()+"')";
						String op_sql = "insert into taskoperationlist (id,taskid,OPERATIONID) values ('"+opId+"','"+taskid+"','"+taskop.getOperationid()+"')";
						logger.info(op_sql);
						update.executeUpdate(op_sql);
					}
					
					//xunjian point
					for(int m=0;m<task.getTaskPoint().size();m++){
						String idStr = GeneratorJugUUID.getUUIDByRandomNumber();
							SubTask taskpoint = (SubTask)task.getTaskPoint().get(m);
//							String point_sql = "insert into subtaskinfo (id,taskid,objectid) values (SEQ_SUBTASKINFO_ID.nextval,'"+taskid+"','"+taskpoint.getObjectid()+"')";
							String point_sql = "insert into subtaskinfo (id,taskid,objectid) values ('" + idStr + "','"+taskid+"','"+taskpoint.getObjectid()+"')";
							logger.info(point_sql);
							update.executeUpdate(point_sql);
					}
					//巡检线段
					for(int n=0;n<task.getTaskSubline().size();n++){
						String tsblid = GeneratorJugUUID.getUUIDByRandomNumber();
						PlanTaskSubline tasksubline = (PlanTaskSubline)task.getTaskSubline().get(n);
//						String subline_sql = "insert into plantasksubline (tsblid,sublineid,taskid) values (SEQ_PLANTASKSUBLINE_ID.nextval,'"+tasksubline.getSublineid()+"','"+taskid+"')";
						String subline_sql = "insert into plantasksubline (tsblid,sublineid,taskid) values ('" + tsblid + "','"+tasksubline.getSublineid()+"','"+taskid+"')";
						seq ++;
						logger.info(subline_sql);
						update.executeUpdate(subline_sql);	
					}
				}
			}
			logger.info("计划制定结束！");
			log.setResult(log.getResult()+" 有"+stencilList.size()+"个巡检组的计划被创建成功。");
			update.commit();
			update.setAutoCommitTrue();	
			return true;
		} catch (Exception e) {
			logger.info("sq :"+seq);
			log.setResult("批量计划创建失败。请与管理员联系!");
			update = null;
			update.rollback();
			update.setAutoCommitTrue();
			logger.error(e);
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 *  废弃
	 * 批量创建计划  废弃
	 * @param deptname
	 * @param regionid
	 * @param batch
	 * @param batchpatrol
	 * @return
	 */
	public boolean createBatchPlan(String deptname, String regionid, BatchBean batch, List batchpatrol) {
		DBService dbs = new DBService();
		/* 1.create batchPlan*/
		batch.setId(dbs.getSeq("batchplan",10));
		String sql1 = "insert into batchplan (id,batchname,startdate,enddate,contractorid,createdate) values ('"+batch.getId()+"','"+batch.getBatchname()+"',to_date('"+batch.getStartdate()+"','yyyy/mm/dd'),to_date('"+batch.getEnddate()+"','yyyy/mm/dd'),'"+batch.getContractorid()+"',sysdate)";
		logger.info("BATCH_SQL"+sql1);
		/* 2.create plan */
		String nowdate = DateUtil.getNowDateString();
		int index = batch.getBatchname().indexOf(nowdate.substring(0,3));
		String str = batch.getBatchname().substring(index,batch.getBatchname().length());
		UpdateUtil update = null;
		try {
			update = new UpdateUtil();
		} catch (Exception e1) {
			logger.error(e1);
			e1.printStackTrace();
			return false;
		}
		try {
			update.setAutoCommitFalse();
			update.executeUpdate(sql1);
			for(int i=0;i<batchpatrol.size(); i++){
				PatrolManBean patrol = (PatrolManBean)batchpatrol.get(i);
				//create plan
				String id = dbs.getSeq("plan",18);
				 String plan_sql = "insert into plan(id,planname,executorid,creator,createdate," +
				 			" begindate,enddate,regionid,ifinnercheck)" 
				 			+" values ('"+id+"','"+deptname+patrol.getPatrolName()+str+"','"+patrol.getPatrolID()+"','"+deptname+"',sysdate"
				 			+",to_date('"+batch.getStartdate()+"','yyyy/mm/dd'),to_date('"+batch.getEnddate()+"','yyyy/mm/dd'),'"+regionid+"','1')";
				logger.info(plan_sql);
				update.executeUpdate(plan_sql);
				//更新batchplan 、plan关联表
				String batch_plan_sql = "insert into batch_plan(id,planid,batchid) values('"+dbs.getSeq("batch_plan",20)+"','"+id+"','"+batch.getId()+"')";
				logger.info(batch_plan_sql);
				update.executeUpdate(batch_plan_sql);
				List level = (List)getLineLevel(patrol.getPatrolID());
				
				//create taskinfo
				for(int j=0;j<level.size();j++){
					String taskid =  dbs.getSeq("taskinfo",20);
					String task_sql = "insert into taskinfo (id,DESCRIBTION,REGIONID,EXCUTETIMES,LINELEVEL)values ('"
							+taskid+"','"+batch.getTaskopname()+"','"+regionid+"','1','"+level.get(j).toString()+"')";
					String plan_task_sql = "insert into plantasklist (id,planid,taskid) values('"+dbs.getSeq("plantasklist",50)+"','"+id+"','"+taskid+"')";
					logger.info(task_sql);
					logger.info(plan_task_sql);
					update.executeUpdate(task_sql);
					update.executeUpdate(plan_task_sql);
					//巡检类型
					String op_sql = "insert into taskoperationlist (id,taskid,OPERATIONID) values ('"+dbs.getSeq("taskoperationlist",20)+"','"+taskid+"','"+batch.getTaskoperation()+"')";
					logger.info(op_sql);
					update.executeUpdate(op_sql);
					//巡检线段
					for(int k=0;k<level.size();k++){
						List subline = (List)getSublineID(patrol.getPatrolID(),level.get(k).toString());
						for(int n=0;n<subline.size();n++){
							String subline_sql = "insert into plantasksubline (tsblid,sublineid,taskid) values ('"+dbs.getSeq("plantasksubline",10)+"','"+subline.get(n).toString()+"','"+taskid+"')";
							logger.info(subline_sql);
							update.executeUpdate(subline_sql);
						}
					}
					//xunjian point
					for(int l=0;l<level.size();l++){
						List point = (List)getPointID(patrol.getPatrolID(),level.get(l).toString());
						for(int n=0;n<point.size();n++){
							String point_sql = "insert into subtaskinfo (id,taskid,objectid) values (SEQ_SUBTASKINFO_ID.nextval,'"+taskid+"','"+point.get(n).toString()+"')";
							logger.info(point_sql);
							update.executeUpdate(point_sql);
						}
					}
				}
				
				
			}
			update.commit();
			update.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			update.rollback();
			update.setAutoCommitTrue();
			logger.error(e);
			e.printStackTrace();
			return false;
		}
	}
	/**
	 *  废弃
	 *  获取巡检点
	 * @param patrolID
	 * @param level
	 * @return
	 */
	private List getPointID(String patrolID, String level) {
		String sql = "select p.pointid field from lineinfo l,sublineinfo s,pointinfo p  where l.lineid=s.lineid and s.sublineid=p.sublineid and s.patrolid='"+patrolID+"' and l.linetype='"+level+"'";
		return queryList(sql);
		
	}
	
	/**
	 *  废弃
	 *  获取线段id
	 * @param patrolID
	 * @param level
	 * @return
	 */
	private List getSublineID(String patrolID, String  level) {
		String sql = "select s.sublineid field  from lineinfo l,sublineinfo s where l.lineid=s.lineid  and s.patrolid='"+patrolID+"' and l.linetype='"+level+"'";
		return queryList(sql);
		
	}
	
	/**
	 *  废弃
	 *  获取线路级别
	 * @param patrolid
	 * @return
	 */
	public List getLineLevel(String patrolid){
		String sql = "select linetype field from (select l.linetype from lineinfo l,sublineinfo s  where l.lineid=s.lineid  and s.patrolid='"+patrolid+"') group by linetype";
		return queryList(sql);
		
	}
	
	private List queryList(String sql){
		List result = new ArrayList();
		QueryUtil query;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()){
				result.add(rs.getString("field"));
			}
			return result;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 查询批量计划
	 * @param user
	 * @param batchname
	 * @return
	 */
	public List queryBatch(UserInfo user,String batchname) {
		String sql = "select id,batchname,to_char(startdate,'yyyy/mm/dd') startdate,to_char(enddate,'yyyy/mm/dd')enddate,to_char(createdate,'yyyy/mm/dd')createdate from batchplan where contractorid='"+user.getDeptID()+"' ";
		logger.info(batchname);
		if(batchname != null){
			sql += " and batchname like ('%"+batchname+"%')";
		}
		logger.info(sql);
		QueryUtil query;
		try {
			query = new QueryUtil();
			List list = query.queryBeans(sql);
			return list;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 删除批量计划
	 * @param id
	 * @return
	 */
	public boolean removeBatch(String id) {
		UpdateUtil del = null;
		String delbatch = "delete batchplan where id='"+id+"'";
		String delbatch_plan="delete batch_plan where batchid='"+id+"'";
		String delplan="delete plan where id in(select planid from batch_plan where batchid='"+id+"')";
		String delplan_task ="delete plantasklist where planid in(select planid from batch_plan where batchid='"+id+"')";
		String deltask = "delete taskinfo where id in (select taskid from plantasklist where planid in(select planid from batch_plan where batchid='"+id+"'))";
		String deltaskOp="delete taskoperationlist where taskid in(select taskid from plantasklist where planid in(select planid from batch_plan where batchid='"+id+"'))";
		String deltaskpoint="delete subtaskinfo where taskid in(select taskid from plantasklist where planid in(select planid from batch_plan where batchid='"+id+"'))";
		String deltasksubline="delete plantasksubline where taskid in(select taskid from plantasklist where planid in(select planid from batch_plan where batchid='"+id+"'))";
		try {
			del = new UpdateUtil();
			del.setAutoCommitFalse();
			del.executeUpdate(deltasksubline);
			del.executeUpdate(deltaskpoint);
			del.executeUpdate(deltaskOp);
			del.executeUpdate(deltask);
			del.executeUpdate(delplan_task);
			del.executeUpdate(delplan);
			del.executeUpdate(delbatch_plan);
			del.executeUpdate(delbatch);
			del.commit();
			del.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			del.rollback();
			logger.error(e);
			e.printStackTrace();
			return false;
		}

	}
	
}
