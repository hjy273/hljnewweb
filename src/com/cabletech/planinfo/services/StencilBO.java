package com.cabletech.planinfo.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.GeneratorJugUUID;
import com.cabletech.commons.hb.JdbcPreparedUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.planinfo.beans.StencilSubTaskBean;
import com.cabletech.planinfo.beans.StencilTaskBean;
import com.cabletech.planinfo.dao.StencilDAOImpl;
import com.cabletech.planinfo.dao.YearMonthPlanDAOImpl;
import com.cabletech.planinfo.domainobjects.StencilSubTask;
import com.cabletech.planinfo.domainobjects.StencilSubTaskOper;
import com.cabletech.planinfo.domainobjects.StencilTask;
import com.cabletech.planinfo.domainobjects.StencilTaskPoint;

/**
 * �ƻ�ģ�����
 * @author Administrator
 *
 */
public class StencilBO extends BaseBisinessObject{
	private Logger logger = Logger.getLogger("StencilBO");
	private StencilDAOImpl dao = new StencilDAOImpl();
	private YearMonthPlanDAOImpl ymdao;
	public List getPatrol(UserInfo user){
		String sql = "";
		sql = "select p.PATROLID,p.PATROLNAME  from patrolmaninfo p ";
		if (user.getDeptype().equals("2")) {
			sql = sql + " where p.parentid='" + user.getDeptID() + "'";
		} else {
			sql = sql + " where p.regionid = '" + user.getRegionID() + "'";
		}
		sql += " and p.state is null ";
		logger.info("sql :"+sql);
		return dao.queryBean(sql);
	}
	
	/**
	 * ��������ģ��
	 * @param stencil
	 * @param StencilTaskList
	 * @return
	 */
	public boolean  saveStencil(StencilTaskBean stencil,List StencilTaskList) {
		StencilTask stenciltask = new StencilTask();
		Session session = dao.getSession();
		DBService dbs = new DBService();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			BeanUtil.objectCopy(stencil, stenciltask);
			stenciltask.setID(dbs.getSeq("task_stencil",10));
			stenciltask.setCreatedate(new Date());
			session.save(stenciltask);//����ģ��
			
			logger.info("tasklist"+StencilTaskList.size());
			//����������
			for(int i=0;i<StencilTaskList.size();i++){
				StencilSubTaskBean taskbean = (StencilSubTaskBean)StencilTaskList.get(i);
				//taskbean.setID(dbs.getSeq("subtask_stencil", 10));
				taskbean.setID(GeneratorJugUUID.getUUIDByRandomNumber());
				taskbean.setStencilid(stenciltask.getID());	
				logger.info("taskbean :"+taskbean.toString());
				StencilSubTask task = new StencilSubTask();
				BeanUtil.objectCopy(taskbean, task);
				// ���ʵ�ʵ���
				task.setFactpointsnum(dao.getTaskFactNum(stencil.getPatrolid(), task.getLinelevel()));
				logger.info("task" +task.toString());
				//�����������
				
				for(int j=0;j<taskbean.getStencilsubtaskop().size();j++){					
					StencilSubTaskOper toList = (StencilSubTaskOper)taskbean.getStencilsubtaskop().get(j);
					toList.setTaskid(task.getID());
					//toList.setID(dbs.getSeq("subtaskoper_stencil", 10));
					toList.setID(GeneratorJugUUID.getUUIDByRandomNumber());
					session.save(toList);
				}
				//���������
				//String[] idArrP = idFactory.getStrSeqs( taskbean.getStenciltaskpoint().size(), "taskpoint_stencil", 20 );
				for(int k=0;k<taskbean.getStenciltaskpoint().size();k++){
					StencilTaskPoint taskpoint = (StencilTaskPoint)taskbean.getStenciltaskpoint().get(k);
					//taskpoint.setID(idArrP[k]);
					taskpoint.setID(GeneratorJugUUID.getUUIDByRandomNumber());
					taskpoint.setTaskid(task.getID());
					session.save(taskpoint);
					
				}
				session.save(task);
			}
			tx.commit();
			return true;
		} catch (HibernateException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("�ع��쳣"+e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("HibernateException :"+e.getMessage());
			return false;
		} catch (InvocationTargetException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("�ع��쳣"+e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("InvocationTargetException :"+e.getMessage());
			return false;
		} catch (IllegalAccessException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("�ع��쳣"+e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("IllegalAccessException :"+e.getMessage());
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	return false;
		}
	}

	
	/**
	 * ��ȡ�����񼯺�list
	 * @param stencilid
	 * @return
	 */
	public List getStencilTaskList(String stencilid){
		List taskList = new ArrayList();
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			long starttime = System.currentTimeMillis(); 
			StencilSubTaskBean  stencilbean =null;
			 tx = session.beginTransaction();
			 String hql = "from StencilSubTask where stencilid=:stencilid";
			 Query query = session.createQuery(hql);
			 query.setParameter("stencilid", stencilid);
			 List queryList = query.list();
			 Iterator it = queryList.iterator();
			 while(it.hasNext()){
				 stencilbean = new StencilSubTaskBean();
				 StencilSubTask stencil = (StencilSubTask)it.next();
				 BeanUtil.objectCopy(stencil, stencilbean);
				 stencilbean = getStencilTask(session,stencilbean);
				 stencilbean.setTaskpoint(getTaskPointNum(stencil.getID()));//����Ѳ�������
				 stencilbean.setSubline(getTaskSubline(stencil.getID()));//�����漰Ѳ���
				 taskList.add(stencilbean);
				 logger.info(stencilbean.toString());
				 //stencilbean.clear();
			 }
			 long endtime = System.currentTimeMillis();
			 long total = (endtime - starttime)/1000;
			 logger.info("ִ�в�ѯʱ�䣺"+total/60+"��"+total%60+"��");
			
		} catch (HibernateException e) {
			logger.info("HibernateException :"+e.getMessage());
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}
	public String getTaskPointNum(String taskid){
		String sql = "select count(*) count from taskpoint_stencil where taskid='"+taskid+"'";
		return dao.getTaskPointNum(sql);
	}
	public String getTaskSubline(String taskid){
		String sql = "select sublinename from sublineinfo where sublineid in ("
        + " select pt.sublineid  from taskpoint_stencil tp,pointinfo pt,subtask_stencil st where st.taskid=tp.taskid and tp.pointid=pt.pointid  and st.taskid='"+taskid+"'"
        + " group by pt.sublineid )";
		return dao.getTaskSubline(sql);
	}
	/**
	 * ��ȡ����������
	 * @param session
	 * @param stencil
	 * @return
	 * @throws HibernateException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private StencilSubTaskBean getStencilTask(Session session ,StencilSubTaskBean stencil) throws HibernateException, InvocationTargetException, IllegalAccessException {
		YearMonthPlanDAOImpl ymdao = new YearMonthPlanDAOImpl();
		String ohql = "from StencilSubTaskOper where taskid=:taskid";
		Query query_to = session.createQuery(ohql);
		query_to.setParameter("taskid", stencil.getID());
		List to_list = query_to.list();
		Iterator it_to = to_list.iterator();
		//List to_list = new ArrayList();
		while(it_to.hasNext()){
			stencil.getStencilsubtaskop().add((StencilSubTaskOper)it_to.next());	
		}
		//ȡ��·����
		stencil.setLineleveltext(ymdao.getLineLevel(stencil.getLinelevel()));
		
		//��ȡѲ�������
		String subpoint_hql = "from StencilTaskPoint where taskid=:taskid";
		Query query_point = session.createQuery(subpoint_hql);
		query_point.setParameter("taskid", stencil.getID());
		List point_list = query_point.list();
		Iterator it_point = point_list.iterator();
		while(it_point.hasNext()){
			stencil.getStenciltaskpoint().add((StencilTaskPoint)it_point.next());
		}

		return stencil;
	}
	/**
	 * ���沢��������ģ��
	 * @param stencil
	 * @param stencilTaskList
	 * @return
	 */
	public boolean saveOrUpdatePlan(StencilTask stencil, List stencilTaskList,List delTaskList) {
		DBService dbs = new DBService();
		JdbcPreparedUtil upd = null;
		String isnew = "y";
		try {
			upd = new JdbcPreparedUtil();
			int del_task_size = delTaskList.size();
			
			for(int i=0;i<del_task_size;i++){
				String taskid = (String)delTaskList.get(i);
				upd.setSQL("delete subtask_stencil where taskid=?");
				upd.setString(1,taskid );
				upd.executeUpdate();
				upd.setSQL("delete subtaskoper_stencil where taskid =?");
				upd.setString(1,taskid );
				upd.executeUpdate();
				upd.setSQL("delete taskpoint_stencil where taskid=?");
				upd.setString(1,taskid );
				upd.executeUpdate();
			}
			//����������
			String task_sql ="insert into subtask_stencil (taskid,stencilid,excutetimes,description,linelevel) values(?,?,?,?,?)";
			for(int i=0;i<stencilTaskList.size();i++){
				StencilSubTaskBean stenciltaskbean = (StencilSubTaskBean)stencilTaskList.get(i);
				StencilSubTask stenciltask = new StencilSubTask();
				BeanUtil.objectCopy(stenciltaskbean, stenciltask);
				if(stenciltask.getID()==null || "".equals(stenciltask.getID())){
					stenciltask.setID(GeneratorJugUUID.getUUIDByRandomNumber());
					String tol = "insert into subtaskoper_stencil (stoid,taskid,toid) values(?,?,?)";
					upd.setSQL(tol);
					for(int j=0;j<stenciltaskbean.getStencilsubtaskop().size();j++){
						StencilSubTaskOper toList = (StencilSubTaskOper)stenciltaskbean.getStencilsubtaskop().get(j);
						toList.setID(GeneratorJugUUID.getUUIDByRandomNumber());
						toList.setTaskid(stenciltask.getID());
						upd.setString(1,toList.getID());
						upd.setString(2, toList.getTaskid());
						upd.setString(3, toList.getToid());
						upd.addBatch();
					}
					upd.executeBatch();
					
					String sp_sql = "insert into taskpoint_stencil(id,taskid,pointid) values(?,?,?) ";
					upd.setSQL(sp_sql);
					for(int k=0;k<stenciltaskbean.getStenciltaskpoint().size();k++){
						StencilTaskPoint taskpoint = (StencilTaskPoint)stenciltaskbean.getStenciltaskpoint().get(k);
						taskpoint.setID(GeneratorJugUUID.getUUIDByRandomNumber());
						taskpoint.setTaskid(stenciltask.getID());
						upd.setString(1, taskpoint.getID());
						upd.setString(2, taskpoint.getTaskid());
						upd.setString(3, taskpoint.getPointid());
						upd.addBatch();
					}
					upd.executeBatch();
					
					upd.setSQL(task_sql);
					upd.setString(1, stenciltask.getID());
					upd.setString(2, stencil.getID());
					upd.setInt(3, stenciltask.getExcutetimes().intValue());
					upd.setString(4, stenciltask.getDescription());
					upd.setString(5, stenciltask.getLinelevel());
					upd.executeUpdate();
				}
			}
			upd.commit();
			upd.setAutoCommitTrue();
			return true;
		
		} catch (Exception e) {
			upd.rollback();
			e.printStackTrace();
			return false;
		}
	}
    /**
     * ɾ��ģ��
     * @param stencil
     * @return
     */
	public boolean removePlan(StencilTask stencil) {
		//ɾ��subtask_stencil��
    	String del_subtask= "delete subtask_stencil where stencilid ='"+stencil.getID()+"'";
    	//ɾ��task_stencil��
    	String del_task = "delete task_stencil where stencilid = '"+stencil.getID()+"'";
    	//ɾ��subtaskoper_stencil
    	String del_subtaskop = "delete subtaskoper_stencil where taskid in (select taskid from subtask_stencil where  stencilid ='"+stencil.getID()+"')";
    	//ɾ��taskpoint_stencil��
    	String  del_taskpoint = "delete taskpoint_stencil where taskid in( select taskid from subtask_stencil where  stencilid ='"+stencil.getID()+"')";
    	try {
			UpdateUtil del = new UpdateUtil();
			del.setAutoCommitFalse();
			del.executeUpdate(del_subtaskop);
			del.executeUpdate(del_taskpoint);
			del.executeUpdate(del_subtask);
			del.executeUpdate(del_task);
			del.commit();
			del.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("ɾ���ƻ�����ģ��ʧ��!:"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	/**
	 *  ͨ��stencil ID ����stencil����
	 * @param ID
	 * @return
	 */
	public StencilTask loadStencil(String ID) {
		// TODO Auto-generated method stub
		try {
			return dao.loadStencil(ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ���ݲ�ѯ��������ģ����Ϣ
	 * @param userinfo
	 * @param bean
	 * @param _regionid
	 * @param _workid
	 * @return
	 */
	public List queryStencil(UserInfo userinfo, StencilTaskBean bean, String _regionid, String _workid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select s.STENCILID, s.STENCILNAME, p.PATROLNAME patrolid, to_char(s.CREATEDATE,'yyyy/mm/dd hh24:mi') CREATEDATE, s.REGIONID, s.CONTRACTORID  from TASK_STENCIL  s,patrolmaninfo  p where s.patrolid=p.patrolid ");
		
			sql.append(" and s.regionid='");
			sql.append(userinfo.getRegionid());
			sql.append("' and s.contractorid='");
			sql.append(userinfo.getDeptID());
			sql.append("'");	
		
		if(_workid != null && !"".equals(_workid)){
			sql.append(" and s.patrolid='");
			sql.append(_workid);
			sql.append("'");
		}
		if(bean.getStencilname() != null ){
			sql.append(" and s.stencilname like '%");
			sql.append(bean.getStencilname());
			sql.append("%'");
		}
		sql.append(" order by  s.CREATEDATE desc ");
		
		logger.info("sql "+sql.toString());		
		return dao.queryBean(sql.toString());
	}

}
