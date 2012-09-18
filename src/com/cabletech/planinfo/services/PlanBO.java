package com.cabletech.planinfo.services;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.CustomID;
import com.cabletech.commons.generatorID.GeneratorJugUUID;
import com.cabletech.commons.hb.JdbcPreparedUtil;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.sqlbuild.QuerySqlBuild;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planinfo.beans.PlanBean;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.dao.PlanDAOImpl;
import com.cabletech.planinfo.dao.YearMonthPlanDAOImpl;
import com.cabletech.planinfo.domainobjects.Plan;
import com.cabletech.planinfo.domainobjects.PlanTaskList;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.SubTask;
import com.cabletech.planinfo.domainobjects.Task;
import com.cabletech.planinfo.domainobjects.TaskOperationList;

public class PlanBO extends PlanBaseService {
	private static Logger logger = Logger.getLogger("PlanBO");

	/**
	 * addDepart
	 */
	PlanDAOImpl dao = new PlanDAOImpl();

	// PlanDAO dao=DaoFactory.createDao();

	/**
	 * 检查唯一性
	 * 
	 * @param id
	 *            String
	 * @return int
	 * @throws Exception
	 */
	public int checkWPlanUnique(String id) throws Exception {
		int flag = -1;

		String sql = "select * from plan where id = '" + id + "'";
		// System.out.println( "唯一性检查sql" + sql );
		QueryUtil jutil = new QueryUtil();

		Vector vct = jutil.executeQueryGetVector(sql);
		if (vct == null || vct.size() == 0) {
			flag = 1;
		}

		return flag;

	}

	/**
	 * 保存计划 包括计划信息、子任务信息
	 * 
	 * @param plan
	 * @param taskList
	 * @return
	 */
	public boolean savePlan(Plan plan, List taskList) {
		Session session = dao.getSession();
		DBService dbs = new DBService();
		Transaction tx = null;
		CustomID idFactory = new CustomID();
		try {
			long starttime = System.currentTimeMillis();
			tx = session.beginTransaction();
			plan.setCreatedate(new java.util.Date());
			session.save(plan);
			logger.info("开始保存计划信息  计划名称 "+plan.getPlanname()+" :" +plan.getId());
			for (int i = 0; i < taskList.size(); i++) {
				TaskBean taskbean = (TaskBean) taskList.get(i);
				//taskbean.setId(dbs.getSeq("taskinfo", 20));
				taskbean.setId(GeneratorJugUUID.getUUIDByRandomNumber());
				Task task = new Task();
				BeanUtil.objectCopy(taskbean, task);
				//logger.info("task" + task);
				PlanTaskList ptList = new PlanTaskList();
				for (int j = 0; j < taskbean.getTaskOpList().size(); j++) {
					TaskOperationList toList = (TaskOperationList) taskbean
							.getTaskOpList().get(j);
					//toList.setId( dbs.getSeq( "taskoperationlist", 20 ) );
					toList.setId(GeneratorJugUUID.getUUIDByRandomNumber());
					toList.setTaskid(task.getId());
					session.save(toList);
				}
//				String[] idArr = idFactory.getStrSeqs(taskbean.getTaskPoint()
//						.size(), "subtaskinfo", 20);
				for (int k = 0; k < taskbean.getTaskPoint().size(); k++) {
					SubTask subtask = (SubTask) taskbean.getTaskPoint().get(k);
					//subtask.setId(idArr[k]);
					subtask.setId(GeneratorJugUUID.getUUIDByRandomNumber());
					subtask.setTaskid(task.getId());
					session.save(subtask);
				}
//				String[] SidArr = idFactory.getStrSeqs(taskbean.getTaskSubline()
//						.size(), "plantasksubline", 10,"tsblid");
				
				int arrSize = taskbean.getTaskSubline().size();
				logger.info("任务描述："+taskbean.getDescribtion()+" : "+taskbean.getId());
				logger.info("要巡检线段: ");
				if(arrSize <= 0){
					try {
						tx.rollback();
					} catch (HibernateException e1) {
						logger.error("回滚异常" + e1.getMessage());
						e1.printStackTrace();
					}
					logger.info("没有发现线段 ");
					return false; 
				}
				for(int l=0; l<arrSize;l++){
					PlanTaskSubline tasksubline = (PlanTaskSubline)taskbean.getTaskSubline().get(l);
					tasksubline.setTaskid(task.getId());
					//tasksubline.setID(SidArr[l]);
					tasksubline.setID(GeneratorJugUUID.getUUIDByRandomNumber());
					session.save(tasksubline);
				}
				//ptList.setId(dbs.getSeq("plantasklist", 50));
				ptList.setId(GeneratorJugUUID.getUUIDByRandomNumber());
				ptList.setPlanid(plan.getId());
				ptList.setTaskid(task.getId());
				task.setTaskpoint(taskbean.getTaskPoint().size()+"");
				// 添加实际点数 guixy by 2009-8-28
				task.setFactpointsnum(dao.getTaskFactNum(plan.getExecutorid(), task.getLinelevel()));
				session.save(ptList);
				session.save(task);
			}
			tx.commit();
			long endtime = System.currentTimeMillis();
			long total=(endtime - starttime)/1000;
			logger.info("执行时间："+total/60+"分"+total%60+"秒");
			return true;
		} catch (HibernateException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("回滚异常" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("HibernateException :" + e.getMessage());
			return false;
		} catch (InvocationTargetException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("回滚异常" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("InvocationTargetException :" + e.getMessage());
			return false;
		} catch (IllegalAccessException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("回滚异常" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("IllegalAccessException :" + e.getMessage());
			return false;
		} catch (Exception e) {
			logger.equals("PlanBoException:"+e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得计划子任务列表
	 * 
	 * @param planid
	 *            计划id
	 * @return
	 */
	public List getTaskList(String planid) {
		List taskList = new ArrayList();
		Session session = dao.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "from PlanTaskList where planid=:planid";
			Query query = session.createQuery(hql);
			query.setParameter("planid", planid);
			List tasklists = query.list();
			Iterator it = tasklists.iterator();
			while (it.hasNext()) {
				PlanTaskList ptList = (PlanTaskList) it.next();
				taskList.add(getTaskInfo(session, ptList.getTaskid()));
			}

		} catch (HibernateException e) {
			logger.info("HibernateException :" + e.getMessage());
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

	/**
	 * 获得一个task对象
	 * 
	 * @param session
	 * @param taskid
	 * @return
	 * @throws HibernateException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public TaskBean getTaskInfo(Session session, String taskid)
			throws HibernateException, InvocationTargetException,
			IllegalAccessException {
		TaskBean taskbean = new TaskBean();
		long starttime = System.currentTimeMillis();
		logger.info("开始计算时间");
		String hql = "from Task where id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", taskid);
		List tasks = query.list();
		Iterator ittask = tasks.iterator();
		YearMonthPlanDAOImpl ymdao = new YearMonthPlanDAOImpl();
		while (ittask.hasNext()) {
			Task task = (Task) ittask.next();
			logger.info("taskbean +"+task.toString());
			BeanUtil.objectCopy(task, taskbean);
			// 获取任务操作码
			String ohql = "from TaskOperationList where taskid='" + taskid+"'";
			Query query_to = session.createQuery(ohql);
			// query_to.setParameter("taskid", taskid);
			List taskOPers = query_to.list();
			Iterator it_to = taskOPers.iterator();
			// List to_list = new ArrayList();
			while (it_to.hasNext()) {
				taskbean.getTaskOpList().add((TaskOperationList) it_to.next());
			}
			// 取线路级别
			taskbean.setLineleveltext(ymdao.getLineLevel(taskbean
					.getLinelevel()));
			logger.info("task " + taskbean.getDescribtion()
					+ taskbean.getExcutetimes());
			// 获取巡检任务点
			long start2 = System.currentTimeMillis();
			String subpoint_hql = "from SubTask where taskid=:taskid";
			Query query_point = session.createQuery(subpoint_hql);
			query_point.setParameter("taskid", taskid);
			List it_points = query_point.list();
			//query_point.scroll();
			query_point.setCacheable(true);
			Iterator iter=it_points.iterator();
			long end2 = System.currentTimeMillis();
			long total2=(end2 - start2)/1000;
			logger.info("时间："+total2/60+"分"+total2%60+"秒");
			while (iter.hasNext()) {
				taskbean.getTaskPoint().add((SubTask) iter.next());
			}
			
			// taskbean.setTaskOpList(to_list);
		}
		long endtime = System.currentTimeMillis();
		long total=(endtime - starttime)/1000;
		logger.info("执行时间："+total/60+"分"+total%60+"秒");
		
		taskbean.setTaskpoint(getTaskPointNum(taskbean.getId()));
		taskbean.setSubline(getTaskSubline(taskbean.getId()));
		taskbean.setTaskSubline(getPlanTaskSubline(taskbean.getId()));
		endtime = System.currentTimeMillis();
		total=(endtime - starttime)/1000;
		logger.info("执行时间："+total/60+"分"+total%60+"秒");
		return taskbean;
	}
	public List getPlanTaskSubline(String taskid){
		
		String sql = "select pt.sublineid sublineid from subtaskinfo tp,pointinfo pt,taskinfo st where st.id=tp.taskid and tp.objectid=pt.pointid  and st.id='"+taskid+"'"
	        + " group by pt.sublineid" ;
		QueryUtil query = null;
		List sublineList = new ArrayList();
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				PlanTaskSubline tasksubline = new PlanTaskSubline();
				tasksubline.setSublineid(rs.getString("sublineid"));
				sublineList.add(tasksubline);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询巡检线段id 异常 ："+e.getMessage());
		}
		
		return sublineList;
	}
	public String getTaskPointNum(String taskid){
		String sql = "select count(*) count from subtaskinfo where taskid='"+taskid+"'";
		return dao.getTaskPointNum(sql);
	}
	public String getTaskSubline(String taskid){
		String sql = "select sublinename from sublineinfo where sublineid in ("
        + " select pt.sublineid  from subtaskinfo tp,pointinfo pt,taskinfo st where st.id=tp.taskid and tp.objectid=pt.pointid  and st.id='"+taskid+"'"
        + " group by pt.sublineid )";
		return dao.getTaskSubline(sql);
	}
	/**
	 * 更新计划信息
	 * 
	 * @param plan
	 * @param taskList
	 * @return
	 */
	public boolean saveOrUpdatePlan(Plan plan, List taskList,List delTaskList) {
		JdbcPreparedUtil  upd = null;
		String isnew = "y";
		
		try {
			long starttime = System.currentTimeMillis();
			upd = new JdbcPreparedUtil ();
		
			String del_List = "delete PlanTaskList where taskid=?";
			String del_task = "delete from taskinfo where ID =?";
			String del_taskOper = "delete TASKOPERATIONLIST where taskid =?";
			String del_taskpoint = "delete subtaskinfo where taskid=?";
			String del_tasksubline = "delete plantasksubline where taskid=?";
			int del_task_size = delTaskList.size();
			for(int i=0;i< del_task_size;i++){
				String taskid = delTaskList.get(i).toString();
				upd.setSQL(del_List);
				upd.setString(1, taskid);
				upd.executeUpdate();
				upd.setSQL(del_task);
				upd.setString(1, taskid);
				upd.executeUpdate();
				upd.setSQL(del_taskOper);
				upd.setString(1, taskid);
				upd.executeUpdate();
				upd.setSQL(del_taskpoint);
				upd.setString(1,taskid);
				upd.executeUpdate();
				upd.setSQL(del_tasksubline);
				upd.setString(1, taskid);
				upd.executeUpdate();
			}
			//upd.executeBatch();
			
			logger.info("size " + taskList.size());
			
			for (int i = 0; i < taskList.size(); i++) {
				TaskBean taskbean = (TaskBean) taskList.get(i);
				Task task = new Task();
				BeanUtil.objectCopy(taskbean, task);
				task.setTaskpoint(taskbean.getTaskPoint().size()+"");
				
				PlanTaskList ptList = new PlanTaskList();
				if (task.getId() == null || "".equals(task.getId())) {
					task.setId(GeneratorJugUUID.getUUIDByRandomNumber());
					logger.info("taskid: " + task.getId());
					//更新任务操作
					String tol = "insert into TASKOPERATIONLIST(id,taskid,operationid) values(?,?,?)";
					upd.setSQL(tol);
					for (int j = 0; j < taskbean.getTaskOpList().size(); j++) {
						TaskOperationList toList = (TaskOperationList) taskbean
								.getTaskOpList().get(j);
						toList.setTaskid(task.getId());
						toList.setId(GeneratorJugUUID.getUUIDByRandomNumber());
						upd.setString(1, toList.getId());
						upd.setString(2, toList.getTaskid());
						upd.setString(3, toList.getOperationid());
						upd.addBatch();
					}
					upd.executeBatch();
					
					//更新任务点
					long start1 = System.currentTimeMillis();
					String sp_sql = "insert into subtaskinfo(id,taskid,objectid) values(?,?,?) ";
					upd.setSQL(sp_sql);
					for (int k = 0; k < taskbean.getTaskPoint().size(); k++) {
						SubTask subtask = (SubTask) taskbean.getTaskPoint().get(k);
						//subtask.setId(idArr[k]);
						subtask.setId(GeneratorJugUUID.getUUIDByRandomNumber());
						subtask.setTaskid(task.getId());
						upd.setString(1,subtask.getId());
						upd.setString(2, subtask.getTaskid());
						upd.setString(3, subtask.getObjectid());
						upd.addBatch();
						if(k%50 == 0){
							upd.executeBatch();
						}
					}
					upd.executeBatch();
					long end1 = System.currentTimeMillis();
					long total1=(end1 - start1)/1000;
					logger.info("执行时间："+total1/60+"分"+total1%60+"秒");
					
					//更新任务线段
					int sublinesize =taskbean.getTaskSubline().size();
					if(sublinesize <=0){
						try {
							upd.rollback();
						} catch (Exception e1) {
							logger.error("回滚异常" + e1.getMessage());
							e1.printStackTrace();
						}
						logger.info("没有发现线段 ");
						return false;
					}
					upd.setSQL("insert into  plantasksubline(tsblid,sublineid,taskid) values (?,?,?)");
					for(int l=0; l<sublinesize;l++){
						PlanTaskSubline tasksubline = (PlanTaskSubline)taskbean.getTaskSubline().get(l);
						tasksubline.setTaskid(task.getId());
						tasksubline.setID(GeneratorJugUUID.getUUIDByRandomNumber());
						
						upd.setString(1, tasksubline.getID());
						upd.setString(2, tasksubline.getSublineid());
						upd.setString(3, tasksubline.getTaskid());
						upd.addBatch();
						
					}
					upd.executeBatch();
					
					//更新计划任务关系
					ptList.setId(GeneratorJugUUID.getUUIDByRandomNumber());
					ptList.setPlanid(plan.getId());
					ptList.setTaskid(task.getId());
					upd.setSQL("insert into Plantasklist(id,planid,taskid) values(?,?,?)");
					upd.setString(1, ptList.getId());
					upd.setString(2, ptList.getPlanid());
					upd.setString(3,ptList.getTaskid());
					upd.executeUpdate();
					
					//更新任务
					System.out.println(task.toString());
					upd.setSQL("insert into taskinfo (id,excutetimes,describtion,regionid,linelevel,taskpoint) values(?,?,?,?,?,?)");
					upd.setString(1, task.getId());
					upd.setString(2, task.getExcutetimes());
					upd.setString(3, task.getDescribtion());
					upd.setString(4, task.getRegionid());
					upd.setString(5, task.getLinelevel());
					upd.setString(6, task.getTaskpoint());
					upd.executeUpdate();
						
				}
				
			}
			
			upd.commit();
			long endtime = System.currentTimeMillis();
			long total=(endtime - starttime)/1000;
			logger.info("执行时间："+total/60+"分"+total%60+"秒");
			upd.setAutoCommitTrue();
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			upd.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除计划信息
	 * 
	 * @param plan
	 * @return
	 */
	public boolean removePlan(Plan plan) {
		// 删除taskoperationlist关联表
		String del_taskop = "delete TaskOperationList where taskid in (select taskid from PlanTaskList where  planid ='"+ plan.getId() + "')";
		// 删除taskinfo表
		String del_task = "delete Taskinfo where id in( select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		// 删除plantasklist表
		String del_plantasklist = "delete PlanTaskList where planid = '" + plan.getId()
				+ "'";
		// 删除yearmonthplan
		String del_plan = "delete plan where id = '" + plan.getId() + "'";
		// 删除subtask表
		String del_taskpoint = "delete subtaskinfo where taskid in( select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		//任务线段
		String del_tasksubline = "delete plantasksubline where taskid in (select taskid from PlanTaskList where  planid ='"+ plan.getId() + "')";
		logger.info("delsql "+del_taskop);
		logger.info("" + del_tasksubline);
		logger.info(""+del_taskpoint);
		logger.info(""+del_task);
		logger.info(""+del_plantasklist);
		logger.info(""+del_plan);
		try {
			UpdateUtil del = new UpdateUtil();
			del.setAutoCommitFalse();
			del.executeUpdate(del_taskop);
			del.executeUpdate(del_tasksubline);
			del.executeUpdate(del_taskpoint);
			del.executeUpdate(del_task);
			del.executeUpdate(del_plantasklist);
			del.executeUpdate(del_plan);
			del.executeUpdate("delete from taskinfo where id not in(select t.id from plantasklist pt,taskinfo t where pt.taskid=t.id)");
			del.commit();
			del.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			logger.error("删除计划失败!:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List queryPlan(UserInfo user, PlanBean bean, String _regionid,
			String _workID,String createDate) {
		String sql = "select a.id id,a.executorid, a.planname name, b.patrolname patrol, to_char(a.begindate,'yyyy/mm/dd') "
				+ "startdate, to_char(a.enddate,'yyyy/mm/dd') enddate from plan a, patrolmaninfo b  ";
		QuerySqlBuild sqlBuild = QuerySqlBuild.newInstance(sql);
		sqlBuild
				.addConstant("a.executorid = b.patrolid(+)  and (a.status is null or a.status ='2')");
		sqlBuild.addConditionAnd("a.executorid = {0}", bean.getExecutorid());
		sqlBuild.addConditionAnd("a.begindate >= {0}", DateUtil
				.StringToDate(bean.getBegindate()));
		sqlBuild.addConditionAnd("a.enddate <= {0}", DateUtil.StringToDate(bean
				.getEnddate()));
		
		sqlBuild.addConditionAnd("a.createdate >= {0}", DateUtil
				.StringToDate(createDate,"yyyy/MM/dd HH:mm:ss"));
		
		sqlBuild
				.addConditionAnd("a.ifinnercheck = {0}", bean.getIfinnercheck());
		if (_regionid == null || _regionid.equals("")) {
			sqlBuild.addTableRegion("a.regionid", user.getRegionid());
		} else {
			sqlBuild.addTableRegion("a.regionid", _regionid);
		}

		if (_workID == null || _workID.equals("")) {
			if (user.getType().equals("22")) {
				sqlBuild
						.addConstant(" and b.PARENTID in( SELECT contractorid FROM contractorinfo "
								+ "CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
								+ user.getDeptID() + "')");
			}
		} else {
			sqlBuild
					.addConstant(" and b.PARENTID in( SELECT contractorid FROM contractorinfo "
							+ "CONNECT BY PRIOR contractorid=PARENTCONTRACTORID START WITH contractorid='"
							+ _workID + "')");
		}

		sqlBuild
				.addConstant(" order by begindate desc,a.regionid,a.planname,b.patrolname ");

		sql = sqlBuild.toSql();
		logger.info("查询计划 " + sql);
		return dao.queryPlan(sql);
	}
	/**
	 * 加载计划任务模板,返回模板信息 
	 * @param userinfo
	 * @param pratolid 
	 * @return list
	 */
	public List getStencilList(UserInfo userinfo, String pratolid) {
		// TODO Auto-generated method stub
		String sql = "select s.STENCILID, s.STENCILNAME, s.patrolid, to_char(s.CREATEDATE,'yyyy/mm/dd hh24:mi') CREATEDATE, s.REGIONID, s.CONTRACTORID  from TASK_STENCIL  s where s.patrolid='"+pratolid+"'";
		return dao.queryPlan(sql);
	}
	/**
	 * 验证计划名称是否重复
	 * @param planname 计划名称
	 * @return 存在返回 true ，否则 false
	 */
	public boolean checkPlanName(String planname) {
		String sql = "select count(id) c from plan where planname in('"+planname+"')";
		QueryUtil query;
		int i=0;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				i = rs.getInt("c");
			}
			if(i==0){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			logger.error("验证计划名称时异常："+e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		
	}
	/**
	 * 判断开始日期 是否在其他已制定计划中
	 * @param begindate 开始日期
	 * @param executorid 执行人id
	 * @return
	 */
	public boolean checkDate(String begindate, String executorid) {
		String sql = " select  count(id) c from PLAN where begindate <= to_date('"+begindate+"','yyyy/mm/dd') and enddate >= to_date('"+begindate+"','yyyy/mm/dd') and EXECUTORID='"+executorid+"'";
		QueryUtil query;
		int i=0;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				i = rs.getInt("c");
			}
			if(i==0){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			logger.error("验证计划名称时异常："+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	// -------------------------------
	public void addPlan(Plan data) throws Exception {
		dao.addPlan(data);
	}

	public Plan loadPlan(String planid) throws Exception {
		return dao.loadPlan(planid);
	}

	/**
	 * 取得最后一次的审批意见
	 * 
	 * @param planid
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public String getLatestAppContent(String planid) throws Exception {
		String appContent = "";
		String sql = "select APPROVECONTENT from PLANAPPROVEMASTER where planid = '"
				+ planid + "' order by APPROVEDATE desc ";

		QueryUtil jutil = new QueryUtil();
		String[][] tempArr = jutil.executeQueryGetArray(sql, "");

		if (tempArr != null) {
			appContent = tempArr[0][0];
		}

		return appContent;
	}

	/**
	 * 取得某年中第几周数
	 * 
	 * @param dateStr
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public String getWeekOfYear(String dateStr) throws Exception {
		Calendar cal = Calendar.getInstance();
		int y = Integer.parseInt(dateStr.substring(0, 4));
		int m = Integer.parseInt(dateStr.substring(5, 7)) - 1;
		int d = Integer.parseInt(dateStr.substring(8, 10));

		// System.out.println( "year : " + y );
		// System.out.println( "Month : " + m );
		// System.out.println( "Date : " + d );

		cal.set(y, m, d);

		// System.out.println( "Month : " + cal.get( Calendar.MONTH ) );

		return String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
	}

	public void removeWPlan(Plan plan) throws Exception {
		dao.removeWPlan(plan);
	}

	public Plan updateWPlan(Plan plan) throws Exception {
		return dao.updateWPlan(plan);
	}
public static void main(String [] args){
	ArrayList list = new ArrayList();
	int size = list.size();
	System.out.println("size " +size);
	
}
}
