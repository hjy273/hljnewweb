package com.cabletech.planinfo.services;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.commons.generatorID.CustomID;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.commons.services.DBService;
import com.cabletech.commons.util.DateUtil;
import com.cabletech.planinfo.beans.TaskBean;
import com.cabletech.planinfo.beans.YearMonthPlanBean;
import com.cabletech.planinfo.dao.YearMonthPlanDAOImpl;
import com.cabletech.planinfo.domainobjects.PlanTaskList;
import com.cabletech.planinfo.domainobjects.PlanTaskSubline;
import com.cabletech.planinfo.domainobjects.SubTask;
import com.cabletech.planinfo.domainobjects.Task;
import com.cabletech.planinfo.domainobjects.TaskOperationList;
import com.cabletech.planinfo.domainobjects.YearMonthPlan;

public class YearMonthPlanBO extends PlanBaseService {
	Logger logger = Logger.getLogger(this.getClass().getName());

	public YearMonthPlanBO() {
	}

	YearMonthPlanDAOImpl dao = new YearMonthPlanDAOImpl();

	/**
	 * 检查年度/月度计划唯一性
	 * 
	 * @param id
	 *            String
	 * @return int
	 * @throws Exception
	 */
	public int checkYMPlanUnique(String id) throws Exception {
		int flag = -1;

		String sql = "select * from yearmonthplan where id = '" + id + "'";
		logger.info("唯一性检查sql" + sql);
		QueryUtil jutil = new QueryUtil();
		Vector vct = jutil.executeQueryGetVector(sql);
		if (vct == null || vct.size() == 0) {
			flag = 1;
		}
		return flag;
	}

	public YearMonthPlanBean getYMPlanInfo(String planid) throws Exception {
		YearMonthPlan data = loadYMPlan(planid);
		YearMonthPlanBean bean = new YearMonthPlanBean();
		BeanUtil.objectCopy(data, bean);
		if (bean.getMonth() != null && bean.getMonth().length() > 0) {
			bean.setMonth(bean.getMonth() + " 月");
		} else {
			bean.setMonth(" ");
		}
		if (bean.getStatus() != null && bean.getStatus().equals("1")) {
			bean.setStatus("通过审批");
			String approvecontent = "";
			String sql = "select approvecontent from planapprovemaster where planid = '"
					+ planid + "' order by APPROVEDATE desc,id desc ";
			java.sql.ResultSet rs = null;
			com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
			rs = util.executeQuery(sql);
			if (rs != null && rs.next()) {
				approvecontent = rs.getString(1);
			}
			rs.close();
			bean.setApprovecontent(approvecontent);

		} else {
			if (bean.getStatus() != null && bean.getStatus().equals("-1")) {
				bean.setStatus("未通过审批");
				String approvecontent = "";
				String sql = "select approvecontent from planapprovemaster where planid = '"
						+ planid + "' order by APPROVEDATE desc,id desc ";
				java.sql.ResultSet rs = null;
				com.cabletech.commons.hb.QueryUtil util = new com.cabletech.commons.hb.QueryUtil();
				rs = util.executeQuery(sql);
				if (rs != null && rs.next()) {
					approvecontent = rs.getString(1);
				}
				rs.close();
				bean.setApprovecontent(approvecontent);
			} else {
				bean.setStatus("待审批");
				bean.setApprovedate("");
				bean.setApprover("");
			}
		}
		bean.setApplyformdate(DateUtil.getNowDateString());
		return bean;
	}

	/**
	 * 保存年月计划信息
	 * 
	 * @param ymPlan
	 * @param taskList
	 * @param objectman
	 * @param ctx
	 * @return boolean
	 */
	public boolean saveYMPlan(YearMonthPlan ymPlan, List taskList) {
		Session session = dao.getSession();
		DBService dbs = new DBService();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.clear();
			session.save(ymPlan);
			logger.info("ymPlan flush");
			logger.info("tasklist" + taskList.size());

			for (int i = 0; i < taskList.size(); i++) {
				TaskBean taskbean = (TaskBean) taskList.get(i);
				Task task = new Task();
				BeanUtil.objectCopy(taskbean, task);
				task.setId(dbs.getSeq("taskinfo", 20));
				logger.info("task=== " + task.getDescribtion());
				PlanTaskList ptList = new PlanTaskList();
				for (int j = 0; j < taskbean.getTaskOpList().size(); j++) {
					TaskOperationList toList = (TaskOperationList) taskbean
							.getTaskOpList().get(j);
					toList.setTaskid(task.getId());
					session.save(toList);
				}
				ptList.setId(dbs.getSeq("plantasklist", 50));
				ptList.setPlanid(ymPlan.getId());
				ptList.setTaskid(task.getId());
				session.save(ptList);
				session.save(task);
				task = null;

			}
			tx.commit();
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
		}
	}

	/**
	 * 获得计划子任务列表
	 * 
	 * @param planid
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
			Iterator it = query.iterate();
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

	public TaskBean getTaskInfo(Session session, String taskid)
			throws HibernateException, InvocationTargetException,
			IllegalAccessException {
		TaskBean taskbean = new TaskBean();
		String hql = "from Task where id=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", taskid);
		Iterator ittask = query.iterate();
		while (ittask.hasNext()) {
			Task task = (Task) ittask.next();
			BeanUtil.objectCopy(task, taskbean);
			// 获取任务操作码
			String ohql = " from TaskOperationList where taskid='" + taskid
					+ "'";
			List<TaskOperationList> operList = session.createQuery(ohql).list();
			List to_list = new ArrayList();
			if (operList != null && operList.size() > 0) {
				for (int i = 0; i < operList.size(); i++) {
					to_list.add(operList.get(i));
				}
			}
			// 取线路级别
			taskbean
					.setLineleveltext(dao.getLineLevel(taskbean.getLinelevel()));
			taskbean.setTaskOpList(to_list);
			/*
			 * BeanUtil.objectCopy(task, taskbean); // 获取任务操作码 String ohql =
			 * "from TaskOperationList where taskid=" + taskid; Query query_to =
			 * session.createQuery(ohql); // query_to.setParameter("taskid",
			 * taskid); Iterator it_to = query_to.iterate(); List to_list = new
			 * ArrayList(); while (it_to.hasNext()) {
			 * to_list.add((TaskOperationList) it_to.next()); } // 取线路级别
			 * taskbean
			 * .setLineleveltext(dao.getLineLevel(taskbean.getLinelevel()));
			 * logger.info("task " + taskbean.getDescribtion() +
			 * taskbean.getExcutetimes()); taskbean.setTaskOpList(to_list);
			 */
		}

		return taskbean;
	}

	public boolean saveOrUpdateYMPlan(YearMonthPlan ymPlan, List taskList) {
		DBService dbs = new DBService();
		UpdateUtil upd = null;
		String isnew = "y";
		String planid = ymPlan.getId();
		// Session session = dao.getSession();
		try {
			// String hql="from YearMonthPlan y where id='"+planid+"'";
			// Query query = session.createQuery(hql);
			// YearMonthPlan plan = (YearMonthPlan)query.uniqueResult();
			String usql = "update yearmonthplan y set y.status='"
					+ ymPlan.getStatus() + "' where id='" + ymPlan.getId()
					+ "'";
			upd = new UpdateUtil();
			upd.setAutoCommitFalse();
			String sql = "delete PlanTaskList where planid='" + ymPlan.getId()
					+ "'";
			upd.executeUpdate(usql);
			upd.executeUpdate(sql);
			for (int i = 0; i < taskList.size(); i++) {
				TaskBean taskbean = (TaskBean) taskList.get(i);
				Task task = new Task();
				BeanUtil.objectCopy(taskbean, task);
				// 删除计划任务关联表
				logger.info("task ===============" + task.getId()
						+ task.getDescribtion() + task.getExcutetimes() + "-"
						+ task.getLinelevel());
				PlanTaskList ptList = new PlanTaskList();
				isnew = "y";
				if (task.getId() == null || task.getId().equals("")) {

					isnew = "n";
					task.setId(dbs.getSeq("taskinfo", 20));
					logger.info("N  taskid: " + task.getId());
				}
				upd.executeUpdate("delete TASKOPERATIONLIST where taskid = '"
						+ task.getId() + "'");
				for (int j = 0; j < taskbean.getTaskOpList().size(); j++) {
					TaskOperationList toList = (TaskOperationList) taskbean
							.getTaskOpList().get(j);
					toList.setTaskid(task.getId());
					String tol = "insert into TASKOPERATIONLIST(id,taskid,operationid) values('"
							+ toList.getId()
							+ "','"
							+ toList.getTaskid()
							+ "','" + toList.getOperationid() + "')";
					upd.executeUpdate(tol);
				}
				ptList.setId(dbs.getSeq("plantasklist", 50));
				ptList.setPlanid(ymPlan.getId());
				ptList.setTaskid(task.getId());
				upd
						.executeUpdate("insert into Plantasklist(id,planid,taskid) values('"
								+ ptList.getId()
								+ "','"
								+ ptList.getPlanid()
								+ "','" + ptList.getTaskid() + "')");
				if ("n".equals(isnew)) {
					upd
							.executeUpdate("insert into taskinfo (id,excutetimes,describtion,regionid,linelevel) values('"
									+ task.getId()
									+ "','"
									+ task.getExcutetimes()
									+ "','"
									+ task.getDescribtion()
									+ "','"
									+ task.getRegionid()
									+ "','"
									+ task.getLinelevel() + "')");
				} else
					upd.executeUpdate("update taskinfo  set excutetimes='"
							+ task.getExcutetimes() + "',describtion='"
							+ task.getDescribtion() + "',linelevel='"
							+ task.getLinelevel() + "' where id='"
							+ task.getId() + "'");
			}
			upd.commit();
			upd.setAutoCommitTrue();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除一个计划
	 * 
	 * @param plan
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public boolean removeYMPlan(YearMonthPlan plan) {
		logger.info("delete");

		// 删除taskoperationlist关联表
		String hql1 = "delete TaskOperationList where taskid in (select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		// 删除taskinfo表
		String hql2 = "delete Taskinfo where id in( select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		// 删除plantasklist表
		String hql3 = "delete PlanTaskList where planid = '" + plan.getId()
				+ "'";
		// 删除yearmonthplan
		String hql4 = "delete YearMonthPlan where id = '" + plan.getId() + "'";
		try {
			UpdateUtil del = new UpdateUtil();
			del.executeUpdate(hql1);
			del.executeUpdate(hql2);
			del.executeUpdate(hql3);
			del.executeUpdate(hql4);
			del.commit();
			return true;
		} catch (Exception e) {
			logger.error("删除计划失败!:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 通过年计划，分配月计划的任务
	 * 
	 * @param yearplanid
	 *            String
	 * @param regionid
	 *            String
	 * @param flag
	 *            int 1,由年计划生成月计划 2，由月计划生成周计划
	 * @param patrolid
	 *            String
	 * @return Vector
	 * @throws Exception
	 */
	public Vector preGetTaskByParentPlan(String yearplanid, String regionid,
			int flag, String patrolid) throws Exception {
		// System.out.println( "flag:" + flag );
		Vector resultVct = new Vector();
		Vector vct = getYPTaskList(yearplanid, regionid, flag);
		// System.out.println( "VCTSIZE:" + vct.size() );
		DBService dbservice = new DBService();
		for (int i = 0; i < vct.size(); i++) {
			Hashtable ht = (Hashtable) vct.get(i); //
			String oldTaskid = (String) ht.get("taskid");
			String taskid = dbservice.getSeq("taskinfo", 20);

			Task task = new Task();
			task.setId(taskid);
			task.setDescribtion((String) ht.get("taskname"));
			task.setRegionid(regionid);
			task.setExcutetimes((String) ht.get("excutetimes"));
			task.setLinelevel((String) ht.get("linelevel"));

			TaskBean tskB = new TaskBean();
			tskB.setId(taskid);
			tskB.setDescribtion((String) ht.get("taskname"));
			tskB.setRegionid(regionid);
			tskB.setExcutetimes((String) ht.get("excutetimes"));
			tskB.setLinelevel((String) ht.get("linelevel"));
			tskB.setLineleveltext((String) ht.get("lineleveltext"));

			int goFlag = 0; // 是否继续执行的flag

			if (flag == 1) {
				goFlag = 1;
			}
			// System.out.println( "取得" );
			// 取得
			Vector pointVct = getSameLevelPoints((String) ht.get("linelevel"),
					patrolid);

			// if (flag == 2 && pointVct != null && pointVct.size() > 0) {
			if (flag != 1 && pointVct != null && pointVct.size() > 0) {
				goFlag = 1;
			}

			if (goFlag == 1) { // 需要进行下一步处理
				// 保存结果
				resultVct.add(tskB);
				TaskBO tbo = new TaskBO();
				tbo.addTask(task);
				// 对应操作
				Vector tempVct = new Vector();
				tempVct = tbo.getTaskOperListByTaskid(oldTaskid);

				CustomID idFactory = new CustomID();
				String[] idArr = idFactory.getStrSeqs(tempVct.size(),
						"taskoperationlist", 20);
				logger.info("取得:" + idArr.length);
				for (int k = 0; k < tempVct.size(); k++) {
					Vector oneRecordVct = (Vector) tempVct.get(k);
					String operationid = (String) oneRecordVct.get(0);
					// String TOListid = dbservice.getSeq("taskoperationlist",
					// 20);
					String TOListid = idArr[k];

					TaskOperationList TOList = new TaskOperationList();
					TOList.setId(TOListid);
					TOList.setOperationid(operationid);
					TOList.setTaskid(taskid);
					tbo.addTaskoperationlist(TOList);
				}

				// 对应操作点
				// if (flag == 2) {
				if (flag != 1) {
					setPresetAllSubtask(task, patrolid);
				}
			}
		}

		return resultVct;
	}

	/**
	 * 取得计划对应任务列表
	 * 
	 * @param planid
	 *            String
	 * @param regionid
	 *            String
	 * @param flag
	 *            int 1,由年计划生成月计划 2，由月计划生成周计划
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getYPTaskList(String planid, String regionid, int flag)
			throws Exception {
		Vector taskVct = new Vector();
		String sql = "SELECT b.ID taskid, b.describtion taskname, b.excutetimes excutetimes,NVL (b.linelevel, ' ') linelevel, l.NAME lineleveltext  FROM plantasklist a, taskinfo b ,lineclassdic l WHERE l.CODE=b.LINELEVEL and a.taskid = b.ID AND a.planid ='"
				+ planid + "'";
		logger.info("查找任务语句:" + sql);
		QueryUtil jutil = new QueryUtil();
		taskVct = jutil.executeQueryGetVector(sql);
		// 计算次数
		taskVct = getYPTaskList(taskVct, flag);
		return taskVct;
	}

	/**
	 * 判断该巡检负责人(维护组)是否有相同级别的任务点
	 * 
	 * @param string
	 *            String
	 * @param patrolid
	 *            String
	 * @return boolean
	 */
	private Vector getSameLevelPoints(String linelevel, String patrolid)
			throws Exception {

		String sql = "select a.pointid pointid from pointinfo a, sublineinfo b, lineinfo c  \n";
		sql += " where a.state='1' and a.sublineid = b.sublineid and b.lineid = c.lineid and  c.linetype = '"
				+ linelevel + "' and b.patrolid = '" + patrolid + "'  \n";
		logger.info("判断该巡检负责人:" + sql);
		QueryUtil jutil = new QueryUtil();
		Vector pointVct = new Vector();
		pointVct = jutil.executeQueryGetVector(sql);
		return pointVct;
	}

	/**
	 * 自动分配任务执行次数
	 * 
	 * @param taskVct
	 *            Vector
	 * @param flag
	 *            int 1,由年计划生成月计划 2，由月计划生成周计划
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getYPTaskList(Vector taskVct, int flag) throws Exception {
		// System.out.println( "FLAG:" + flag + "   任务数:" + taskVct.size() );
		Vector newVct = new Vector();
		for (int i = 0; i < taskVct.size(); i++) {
			Hashtable ht = new Hashtable();
			Vector tempVct = (Vector) taskVct.get(i);
			int excutetimes = Integer.parseInt((String) tempVct.get(2));
			int elementUnit = 0;
			if (flag == 2) { // 周
				elementUnit = 4;
			} else {
				if (flag == 10) { // 旬
					elementUnit = 3;
				} else {
					if (flag == 15) { // 半月
						elementUnit = 2;
					} else {
						if (flag == 30) { // 月
							elementUnit = 1;
						} else { // 年
							elementUnit = 12;
						}
					}
				}
			}
			// System.out.println( "计划id:" + tempVct.get( 0 ) + "   计划名称:" +
			// tempVct.get( 1 ) );
			// System.out.println( "月计划执行次数:" + excutetimes + "   需要分配次数:" +
			// elementUnit );
			int modeN = excutetimes % elementUnit;
			int relexe = 0;
			relexe = excutetimes / elementUnit;
			if (modeN != 0) {
				relexe += 1;
			}
			// System.out.println( "月计划实际执行次数:" + relexe );
			ht.put("taskid", (String) tempVct.get(0));
			ht.put("taskname", (String) tempVct.get(1));
			ht.put("excutetimes", String.valueOf(relexe));
			ht.put("linelevel", (String) tempVct.get(3));
			ht.put("lineleveltext", (String) tempVct.get(4));
			newVct.add(ht);
		}
		// System.out.println( "newVct:" + newVct.size() );
		return newVct;
	}

	/**
	 * 分配默认操作点
	 * 
	 * @param taskid
	 *            String
	 * @param patrolid
	 *            String
	 * @throws Exception
	 */
	public void setPresetAllSubtask(Task task, String patrolid)
			throws Exception {

		String sql = "select a.pointid pointid from pointinfo a, sublineinfo b, lineinfo c where a.state='1' and a.sublineid = b.sublineid and b.lineid = c.lineid and b.patrolid='"
				+ patrolid
				+ "' and c.linetype = '"
				+ task.getLinelevel()
				+ "' ";

		DBService dbservice = new DBService();
		Vector vct = dbservice.queryVector(sql, "");

		if (vct != null && vct.size() > 0) {
			CustomID idFactory = new CustomID();
			idFactory.doMakeSeq("subtaskinfo", 20);

			OracleIDImpl ora = new OracleIDImpl();
			String id = ora.getSeq("subtaskinfo", 20);

			for (int i = 0; i < vct.size(); i++) {
				Vector tempV = (Vector) vct.get(i);
				String pointid = (String) tempV.get(0);

				SubTask subtask = new SubTask();

				subtask.setTaskid(task.getId());
				subtask.setObjectid(pointid);

				id = ((String) (String.valueOf(Integer.parseInt(id) + i)));
				for (int k = 0; k < 20 - id.length(); k++) {
					id = "0" + id;
				}
				// //System.out.println("ID:" + id);
				subtask.setId(id);
				singleInsertSubtask(subtask);
			}
		}
		return;
	}

	public String makeSize(String str, int leng) {
		if (str.length() >= leng) {
			return str;
		} else {
			for (int i = 0; i < leng - str.length(); i++) {
				str = "0" + str;
			}
			return str;
		}
	}

	/**
	 * 单条insert语句执行
	 * 
	 * @param subtask
	 *            SubTask
	 * @return String
	 * @throws Exception
	 */
	private void singleInsertSubtask(SubTask subtask) throws Exception {
		String sql = " \n";
		sql += "insert into SUBTASKINFO (ID, TASKID, OBJECTID) values (SEQ_SUBTASKINFO_ID.nextval , '"
				+ subtask.getTaskid() + "', '" + subtask.getObjectid() + "')";
		UpdateUtil updateutil = new UpdateUtil();
		updateutil.executeUpdateWithCloseStmt(sql);

	}

	/**
	 * makeFrontSubSql
	 * 
	 * @param sql
	 *            String
	 */
	private String makeFrontSubSql(String sql) {

		sql = "insert into  SUBTASKINFO (id, taskid, objectid ) select id, taskid, objectid from ( \n"
				+ sql;
		sql = sql + ")      \n";

		return sql;
	}

	/**
	 * makeSubSql
	 * 
	 * @param sql
	 *            String
	 * @param subtask
	 *            SubTask
	 * @param i
	 *            int
	 */
	private String makeSubSql(String sql, SubTask subtask, int i) {
		if (i == 0) {
			sql = sql + " select '" + subtask.getId() + "' id, '"
					+ subtask.getTaskid() + "' taskid, '"
					+ subtask.getObjectid() + "' objectid   from dual  \n";

		} else {
			sql = sql + " union   \n";
			sql = sql + " select '" + subtask.getId() + "' id, '"
					+ subtask.getTaskid() + "' taskid, '"
					+ subtask.getObjectid() + "' objectid   from dual  \n";
		}

		return sql;
	}

	/**
	 * 将计划任务关联表中与该计划对应的条目删除
	 * 
	 * @param planid
	 *            String
	 * @throws Exception
	 */
	public void removeRelatedTask(String planid) throws Exception {
		String sql = "delete from plantasklist where planid = '" + planid + "'";

		UpdateUtil util = new UpdateUtil();
		util.executeUpdate(sql);
		// simon zhang
		util.commit();
		// this.deleSplithTask();
	}

	/**
	 * 取得计划对应任务列表，计划属性用
	 * 
	 * @param planid
	 *            String
	 * @param type
	 *            String 计划类型type=“PLAN”时为巡检计划，否则为年月计划
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getTasklistByPlanID(String planid, String type)
			throws Exception {

		Vector resultVct = new Vector();

		String sql = "SELECT l.NAME linelevel, b.ID taskid, b.describtion taskname, b.excutetimes excutetimes FROM plantasklist a, taskinfo b,lineclassdic l WHERE l.CODE = b.LINELEVEL and  a.taskid = b.ID AND a.planid='"
				+ planid + "'";
		if (type == "PLAN") {
			// sql=
			// "SELECT l.NAME linelevel, b.ID taskid, b.describtion taskname,b.excutetimes excutetimes,b.taskpoint taskpoint, taskop,b.linelevel,b.evaluatepoint evaluatepoint  FROM plantasklist a, taskinfo b,lineclassdic l WHERE l.CODE = b.LINELEVEL and  a.taskid = b.ID AND a.planid='"
			// +
			// planid + "'";

			sql = "select lineleveltext,taskid,taskname,excutetimes,taskpoint,wmsys.wm_concat(taskop),linelevel,evaluatepoint, factpointsnum from "
					+ " ( "
					+ "	SELECT l.NAME lineleveltext, b.ID taskid, b.describtion taskname,"
					+ " 		b.excutetimes excutetimes, b.taskpoint taskpoint,o.OPERATIONNAME taskop, b.linelevel linelevel,"
					+ " 		b.evaluatepoint evaluatepoint , b.factpointsnum factpointsnum "
					+
					// " 	FROM plantasklist a, taskinfo b, pointinfo p,lineclassdic l,taskoperationlist ol,taskoperation o"
					// +
					// " 	WHERE b.taskpoint=p.pointid and p.state='1' and l.code = b.linelevel AND a.taskid = b.ID and ol.taskid=b.ID and ol.operationid=o.id AND a.planid = '"+planid+"'"
					// +
					" 	FROM plantasklist a, taskinfo b, lineclassdic l,taskoperationlist ol,taskoperation o"
					+ " 	WHERE l.code = b.linelevel AND a.taskid = b.ID and ol.taskid=b.ID and ol.operationid=o.id AND a.planid = '"
					+ planid
					+ "'"
					+ " ) "
					+ " group by lineleveltext,taskid,taskname,excutetimes,taskpoint,linelevel,evaluatepoint,linelevel, factpointsnum ";
		}

		DBService dbservice = new DBService();
		Vector vct = dbservice.queryVector(sql, "");
		logger.info(sql);
		// System.out.println( "纪录数量 : " + vct.size() );

		for (int i = 0; i < vct.size(); i++) {
			Vector tempVct = (Vector) vct.get(i);

			TaskBean taskB = new TaskBean();
			taskB.setLineleveltext((String) tempVct.get(0));
			taskB.setId((String) tempVct.get(1));
			taskB.setDescribtion((String) tempVct.get(2));
			taskB.setExcutetimes((String) tempVct.get(3));
			if (type.equals("PLAN")) {
				taskB.setTaskpoint((String) tempVct.get(4));
				// taskB.setSubline(( String )tempVct.get( 5 ) );
				taskB.setTaskSubline(getSubLineName(taskB.getId()));
				taskB.setTaskop((String) tempVct.get(5));
				taskB.setLinelevel(tempVct.get(6).toString());
				taskB.setEvaluatepoint(tempVct.get(6).toString());

				// int taskpointNum = Integer.parseInt(taskB.getTaskpoint());
				// 为了数据库有空值而添加
				int taskpointNum = 0;
				if (taskB.getTaskpoint() != null
						&& !"".equals(taskB.getTaskpoint())) {
					taskpointNum = Integer.parseInt(taskB.getTaskpoint());
				}

				// 为了以前的计划实际点数能显示出来
				int realpoint = 0;
				if (tempVct.get(8) != null
						&& !"".equals(tempVct.get(8).toString())
						&& !"null".equals(tempVct.get(8).toString())) {
					realpoint = Integer.parseInt((tempVct.get(8).toString()));
				} else {
					realpoint = getRealPointNum(taskB.getId());
				}
				taskB.setRealPoint(realpoint + "");

				DecimalFormat decfmt = new DecimalFormat("##0.00");

				taskB
						.setRatio(decfmt
								.format(((double) taskpointNum / (double) realpoint) * 100)
								+ "%");
			}
			resultVct.add(taskB);
		}
		return resultVct;
	}

	/**
	 * 
	 * @param taskId
	 *            任务id
	 * @param level
	 *            线路级别
	 * @return
	 */
	private int getRealPointNum(String taskId) {
		int realpoint = 0;
		String sql = "select count(*) realPoint from pointinfo where state='1' and sublineid in (select sublineid from plantasksubline where taskid='"
				+ taskId + "')";
		QueryUtil query;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				realpoint = rs.getInt("realPoint");
			}
		} catch (Exception e) {
			e.printStackTrace();
			realpoint = 0;
		}

		return realpoint;
	}

	private List getSubLineName(String id) {
		String sql = "select s.sublinename  from taskinfo t,plantasksubline sl, sublineinfo s where t.id=sl.taskid and sl.sublineid=s.sublineid and t.id='"
				+ id + "'";
		List list = new ArrayList();
		QueryUtil query = null;
		PlanTaskSubline subline = null;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);

			while (rs.next()) {
				subline = new PlanTaskSubline();
				subline.setName(rs.getString("sublinename"));
				list.add(subline);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 新增年度/月度计划
	 * 
	 * @param data
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public void addYMPlan(YearMonthPlan data) throws Exception {
		dao.addPlan(data);
	}

	/**
	 * 新增年度/月度计划任务对应表
	 * 
	 * @param data
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public void addPlanTaskList(PlanTaskList data) throws Exception {
		dao.addPlanTaskList(data);
	}

	/**
	 * 载入年度/月度计划
	 * 
	 * @param ymplanid
	 *            String
	 * @return YearMonthPlan
	 * @throws Exception
	 */
	public YearMonthPlan loadYMPlan(String ymplanid) throws Exception {
		return dao.loadYMPlan(ymplanid);
	}

	/**
	 * 更新一个YM计划
	 * 
	 * @param ymplan
	 *            YearMonthPlan
	 * @return YearMonthPlan
	 * @throws Exception
	 */
	public YearMonthPlan updateYMPlan(YearMonthPlan ymplan) throws Exception {
		return dao.updateYMPlan(ymplan);
	}

	/**
	 * 把一个计划对应的任务列表，更新到另一个计划上
	 * 
	 * @param planidWithM
	 *            String
	 * @throws Exception
	 */
	public void replaceTask(String planidWithM) throws Exception {
		String originalPlanId = planidWithM.substring(0,
				planidWithM.length() - 1);
		String sql = "delete from plantasklist where planid='" + originalPlanId
				+ "'";

		UpdateUtil updateutil = new UpdateUtil();
		updateutil.executeUpdate(sql);

		sql = "update plantasklist set planid='" + originalPlanId
				+ "' where planid='" + planidWithM + "'";
		updateutil.executeUpdate(sql);
	}

	/**
	 * 删除一个修改的计划副本
	 * 
	 * @throws Exception
	 */
	public void romoveModifiedYMPlan(String Planid) throws Exception {
		Planid = Planid + "M";
		String sql = "delete from yearmonthplan where id='" + Planid + "'";

		UpdateUtil updateutil = new UpdateUtil();
		updateutil.executeUpdate(sql);
		// simon zhang
		updateutil.commit();
	}

	/**
	 * 功能:删除数据库中多余的任务,子任务,清除垃圾 参数:无 返回:清除成功返回true 否则返回false;
	 * */
	public boolean deleSplithTask() {
		String sql1 = "delete from taskinfo where id not in (select distinct taskid from plantasklist)"; // 删除任务表中的垃圾
		String sql2 = "delete from taskoperationlist where taskid not in (select distinct id from taskinfo )"; // 删除任务操作对应表中的垃圾
		String sql3 = "delete from subtaskinfo where taskid not in ( select distinct taskid from taskoperationlist)"; // 删除子任务表中的垃圾
		try {
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql1);
			up.commit();
			up.executeUpdate(sql2);
			up.commit();
			up.executeUpdate(sql3);
			up.commit();
			return true;
		} catch (Exception e) {
			logger.warn("删除数据库中多余的任务,子任务异常:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 功能:判断指导计划编号,制定任务编号的记录在计划任务对应表中是否存在 参数:计划编号,任务编号 返回:存在返回1 否则返回0,出错返回-1;
	 * */
	public int valiPlanTask(String planid, String taskid) {
		String sql1 = "select count(*) from plantasklist where planid='"
				+ planid + "' and taskid='" + taskid + "'";
		;
		String[][] str;
		try {
			QueryUtil qu = new QueryUtil();
			str = qu.executeQueryGetArray(sql1, "");
			if (str[0][0].equals("0")) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			logger.warn("判断指导计划编号,制定任务编号的记录在计划任务对应表中是否存在异常:" + e.getMessage());
			return -1;
		}
	}

	/**
	 * 功能:删除删除计划任务表中指定计划编号的所有记录 参数:计划编号 返回:清除成功返回true 否则返回false;
	 * */
	public boolean delePlanTask(String planid) {
		String sql1 = "delete from plantasklist where planid='" + planid + "'";
		try {
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql1);
			return true;
		} catch (Exception e) {
			logger.warn("删除删除计划任务表中指定计划编号的所有记录异常:" + e.getMessage());
			return false;
		}
	}

}