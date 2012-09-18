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
	 * ������/�¶ȼƻ�Ψһ��
	 * 
	 * @param id
	 *            String
	 * @return int
	 * @throws Exception
	 */
	public int checkYMPlanUnique(String id) throws Exception {
		int flag = -1;

		String sql = "select * from yearmonthplan where id = '" + id + "'";
		logger.info("Ψһ�Լ��sql" + sql);
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
			bean.setMonth(bean.getMonth() + " ��");
		} else {
			bean.setMonth(" ");
		}
		if (bean.getStatus() != null && bean.getStatus().equals("1")) {
			bean.setStatus("ͨ������");
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
				bean.setStatus("δͨ������");
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
				bean.setStatus("������");
				bean.setApprovedate("");
				bean.setApprover("");
			}
		}
		bean.setApplyformdate(DateUtil.getNowDateString());
		return bean;
	}

	/**
	 * �������¼ƻ���Ϣ
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
				logger.error("�ع��쳣" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("HibernateException :" + e.getMessage());
			return false;
		} catch (InvocationTargetException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("�ع��쳣" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("InvocationTargetException :" + e.getMessage());
			return false;
		} catch (IllegalAccessException e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				logger.error("�ع��쳣" + e1.getMessage());
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.info("IllegalAccessException :" + e.getMessage());
			return false;
		}
	}

	/**
	 * ��üƻ��������б�
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
			// ��ȡ���������
			String ohql = " from TaskOperationList where taskid='" + taskid
					+ "'";
			List<TaskOperationList> operList = session.createQuery(ohql).list();
			List to_list = new ArrayList();
			if (operList != null && operList.size() > 0) {
				for (int i = 0; i < operList.size(); i++) {
					to_list.add(operList.get(i));
				}
			}
			// ȡ��·����
			taskbean
					.setLineleveltext(dao.getLineLevel(taskbean.getLinelevel()));
			taskbean.setTaskOpList(to_list);
			/*
			 * BeanUtil.objectCopy(task, taskbean); // ��ȡ��������� String ohql =
			 * "from TaskOperationList where taskid=" + taskid; Query query_to =
			 * session.createQuery(ohql); // query_to.setParameter("taskid",
			 * taskid); Iterator it_to = query_to.iterate(); List to_list = new
			 * ArrayList(); while (it_to.hasNext()) {
			 * to_list.add((TaskOperationList) it_to.next()); } // ȡ��·����
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
				// ɾ���ƻ����������
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
	 * ɾ��һ���ƻ�
	 * 
	 * @param plan
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public boolean removeYMPlan(YearMonthPlan plan) {
		logger.info("delete");

		// ɾ��taskoperationlist������
		String hql1 = "delete TaskOperationList where taskid in (select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		// ɾ��taskinfo��
		String hql2 = "delete Taskinfo where id in( select taskid from PlanTaskList where  planid ='"
				+ plan.getId() + "')";
		// ɾ��plantasklist��
		String hql3 = "delete PlanTaskList where planid = '" + plan.getId()
				+ "'";
		// ɾ��yearmonthplan
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
			logger.error("ɾ���ƻ�ʧ��!:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ͨ����ƻ��������¼ƻ�������
	 * 
	 * @param yearplanid
	 *            String
	 * @param regionid
	 *            String
	 * @param flag
	 *            int 1,����ƻ������¼ƻ� 2�����¼ƻ������ܼƻ�
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

			int goFlag = 0; // �Ƿ����ִ�е�flag

			if (flag == 1) {
				goFlag = 1;
			}
			// System.out.println( "ȡ��" );
			// ȡ��
			Vector pointVct = getSameLevelPoints((String) ht.get("linelevel"),
					patrolid);

			// if (flag == 2 && pointVct != null && pointVct.size() > 0) {
			if (flag != 1 && pointVct != null && pointVct.size() > 0) {
				goFlag = 1;
			}

			if (goFlag == 1) { // ��Ҫ������һ������
				// ������
				resultVct.add(tskB);
				TaskBO tbo = new TaskBO();
				tbo.addTask(task);
				// ��Ӧ����
				Vector tempVct = new Vector();
				tempVct = tbo.getTaskOperListByTaskid(oldTaskid);

				CustomID idFactory = new CustomID();
				String[] idArr = idFactory.getStrSeqs(tempVct.size(),
						"taskoperationlist", 20);
				logger.info("ȡ��:" + idArr.length);
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

				// ��Ӧ������
				// if (flag == 2) {
				if (flag != 1) {
					setPresetAllSubtask(task, patrolid);
				}
			}
		}

		return resultVct;
	}

	/**
	 * ȡ�üƻ���Ӧ�����б�
	 * 
	 * @param planid
	 *            String
	 * @param regionid
	 *            String
	 * @param flag
	 *            int 1,����ƻ������¼ƻ� 2�����¼ƻ������ܼƻ�
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getYPTaskList(String planid, String regionid, int flag)
			throws Exception {
		Vector taskVct = new Vector();
		String sql = "SELECT b.ID taskid, b.describtion taskname, b.excutetimes excutetimes,NVL (b.linelevel, ' ') linelevel, l.NAME lineleveltext  FROM plantasklist a, taskinfo b ,lineclassdic l WHERE l.CODE=b.LINELEVEL and a.taskid = b.ID AND a.planid ='"
				+ planid + "'";
		logger.info("�����������:" + sql);
		QueryUtil jutil = new QueryUtil();
		taskVct = jutil.executeQueryGetVector(sql);
		// �������
		taskVct = getYPTaskList(taskVct, flag);
		return taskVct;
	}

	/**
	 * �жϸ�Ѳ�츺����(ά����)�Ƿ�����ͬ����������
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
		logger.info("�жϸ�Ѳ�츺����:" + sql);
		QueryUtil jutil = new QueryUtil();
		Vector pointVct = new Vector();
		pointVct = jutil.executeQueryGetVector(sql);
		return pointVct;
	}

	/**
	 * �Զ���������ִ�д���
	 * 
	 * @param taskVct
	 *            Vector
	 * @param flag
	 *            int 1,����ƻ������¼ƻ� 2�����¼ƻ������ܼƻ�
	 * @return Vector
	 * @throws Exception
	 */
	public Vector getYPTaskList(Vector taskVct, int flag) throws Exception {
		// System.out.println( "FLAG:" + flag + "   ������:" + taskVct.size() );
		Vector newVct = new Vector();
		for (int i = 0; i < taskVct.size(); i++) {
			Hashtable ht = new Hashtable();
			Vector tempVct = (Vector) taskVct.get(i);
			int excutetimes = Integer.parseInt((String) tempVct.get(2));
			int elementUnit = 0;
			if (flag == 2) { // ��
				elementUnit = 4;
			} else {
				if (flag == 10) { // Ѯ
					elementUnit = 3;
				} else {
					if (flag == 15) { // ����
						elementUnit = 2;
					} else {
						if (flag == 30) { // ��
							elementUnit = 1;
						} else { // ��
							elementUnit = 12;
						}
					}
				}
			}
			// System.out.println( "�ƻ�id:" + tempVct.get( 0 ) + "   �ƻ�����:" +
			// tempVct.get( 1 ) );
			// System.out.println( "�¼ƻ�ִ�д���:" + excutetimes + "   ��Ҫ�������:" +
			// elementUnit );
			int modeN = excutetimes % elementUnit;
			int relexe = 0;
			relexe = excutetimes / elementUnit;
			if (modeN != 0) {
				relexe += 1;
			}
			// System.out.println( "�¼ƻ�ʵ��ִ�д���:" + relexe );
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
	 * ����Ĭ�ϲ�����
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
	 * ����insert���ִ��
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
	 * ���ƻ��������������üƻ���Ӧ����Ŀɾ��
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
	 * ȡ�üƻ���Ӧ�����б��ƻ�������
	 * 
	 * @param planid
	 *            String
	 * @param type
	 *            String �ƻ�����type=��PLAN��ʱΪѲ��ƻ�������Ϊ���¼ƻ�
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
		// System.out.println( "��¼���� : " + vct.size() );

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
				// Ϊ�����ݿ��п�ֵ�����
				int taskpointNum = 0;
				if (taskB.getTaskpoint() != null
						&& !"".equals(taskB.getTaskpoint())) {
					taskpointNum = Integer.parseInt(taskB.getTaskpoint());
				}

				// Ϊ����ǰ�ļƻ�ʵ�ʵ�������ʾ����
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
	 *            ����id
	 * @param level
	 *            ��·����
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
	 * �������/�¶ȼƻ�
	 * 
	 * @param data
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public void addYMPlan(YearMonthPlan data) throws Exception {
		dao.addPlan(data);
	}

	/**
	 * �������/�¶ȼƻ������Ӧ��
	 * 
	 * @param data
	 *            YearMonthPlan
	 * @throws Exception
	 */
	public void addPlanTaskList(PlanTaskList data) throws Exception {
		dao.addPlanTaskList(data);
	}

	/**
	 * �������/�¶ȼƻ�
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
	 * ����һ��YM�ƻ�
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
	 * ��һ���ƻ���Ӧ�������б����µ���һ���ƻ���
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
	 * ɾ��һ���޸ĵļƻ�����
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
	 * ����:ɾ�����ݿ��ж��������,������,������� ����:�� ����:����ɹ�����true ���򷵻�false;
	 * */
	public boolean deleSplithTask() {
		String sql1 = "delete from taskinfo where id not in (select distinct taskid from plantasklist)"; // ɾ��������е�����
		String sql2 = "delete from taskoperationlist where taskid not in (select distinct id from taskinfo )"; // ɾ�����������Ӧ���е�����
		String sql3 = "delete from subtaskinfo where taskid not in ( select distinct taskid from taskoperationlist)"; // ɾ����������е�����
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
			logger.warn("ɾ�����ݿ��ж��������,�������쳣:" + e.getMessage());
			return false;
		}
	}

	/**
	 * ����:�ж�ָ���ƻ����,�ƶ������ŵļ�¼�ڼƻ������Ӧ�����Ƿ���� ����:�ƻ����,������ ����:���ڷ���1 ���򷵻�0,������-1;
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
			logger.warn("�ж�ָ���ƻ����,�ƶ������ŵļ�¼�ڼƻ������Ӧ�����Ƿ�����쳣:" + e.getMessage());
			return -1;
		}
	}

	/**
	 * ����:ɾ��ɾ���ƻ��������ָ���ƻ���ŵ����м�¼ ����:�ƻ���� ����:����ɹ�����true ���򷵻�false;
	 * */
	public boolean delePlanTask(String planid) {
		String sql1 = "delete from plantasklist where planid='" + planid + "'";
		try {
			UpdateUtil up = new UpdateUtil();
			up.executeUpdate(sql1);
			return true;
		} catch (Exception e) {
			logger.warn("ɾ��ɾ���ƻ��������ָ���ƻ���ŵ����м�¼�쳣:" + e.getMessage());
			return false;
		}
	}

}