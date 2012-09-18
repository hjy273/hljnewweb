package com.cabletech.planinfo.services;

import java.sql.ResultSet;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.*;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.planinfo.beans.*;
import com.cabletech.planinfo.dao.PlanDAO;
import com.cabletech.planinfo.dao.PlanDAOImpl;
import com.cabletech.planinfo.dao.YearMonthPlanDAOImpl;
import com.cabletech.planinfo.domainobjects.*;

public class PlanInfoService extends BaseService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	TaskBO botsk;
	PlanBO boplan;
	YearMonthPlanBO ymplanbo;
	StatisticsBO stabo;
	PlanapproveBO pabo;

	public PlanInfoService() {

		botsk = new TaskBO();
		boplan = new PlanBO();
		ymplanbo = new YearMonthPlanBO();
		stabo = new StatisticsBO();
		pabo = new PlanapproveBO();
	}

	public void addPlanapprove(Planapprove data) throws Exception {
		pabo.addPlanapprove(data);
	}

	public void addPlanapproveWithSql(Planapprove data) throws Exception {
		pabo.addPlanapproveWithSql(data);
	}

	/* ���� */
	// ******************pzj �޸�
	public void ExportPatrolPointsList(String patrolNme, String patrolid,
			Vector pointsList, HttpServletResponse response) throws Exception {
		stabo.ExportPatrolPointsList(patrolNme, patrolid, pointsList, response);
	}

	// ********************
	// public void ExportPatrolPointsList(String patrolNme,
	// Vector pointsList,
	// HttpServletResponse response) throws
	// Exception {
	// stabo.ExportPatrolPointsList(patrolNme, pointsList, response);
	// }

	public void ExportYearPlanResult(List list, HttpServletResponse response)
			throws Exception {
		stabo.ExportYearPlanResult(list, response);
	}

	public void ExportMonthPlanResult(List list, HttpServletResponse response)
			throws Exception {
		stabo.ExportMonthPlanResult(list, response);
	}

	public void ExportWeekPlanResult(List list, HttpServletResponse response)
			throws Exception {
		stabo.ExportWeekPlanResult(list, response);
	}

	public void ExportWeekPlanform(PlanBean plan, Vector taskVct,
			HttpServletResponse response) throws Exception {
		stabo.ExportWeekPlanform(plan, taskVct, response);
	}

	public void ExportYMPlanform(YearMonthPlanBean plan, Vector taskVct,
			HttpServletResponse response) throws Exception {
		stabo.ExportYMPlanform(plan, taskVct, response);
	}

	/* ���/�¶ȼƻ� */
	public int checkYMPlanUnique(String id) throws Exception {
		return ymplanbo.checkYMPlanUnique(id);
	}

	public void romoveModifiedYMPlan(String Planid) throws Exception {
		ymplanbo.romoveModifiedYMPlan(Planid);
	}

	/**
	 * �����ƻ������Ӧ���¼
	 * 
	 * @param data
	 *            PlanTaskList
	 * @throws Exception
	 */
	public void createPlanTaskList(PlanTaskList data) throws Exception {
		ymplanbo.addPlanTaskList(data);
	}

	public void createYMPlan(YearMonthPlan ymplan) throws Exception {
		ymplanbo.addYMPlan(ymplan);
	}

	public YearMonthPlan loadymPlan(String id) throws Exception {
		return ymplanbo.loadYMPlan(id);
	}

	public YearMonthPlan updateYMPlan(YearMonthPlan ymplan) throws Exception {
		return ymplanbo.updateYMPlan(ymplan);
	}

	public void replaceTask(String planidWithM) throws Exception {
		ymplanbo.replaceTask(planidWithM);
	}

	public void delYMPlan(YearMonthPlan plan) throws Exception {
		ymplanbo.removeYMPlan(plan);
	}

	/* ���ƻ��������������üƻ���Ӧ����Ŀɾ�� */
	public void removeRelatedTask(String planid) throws Exception {
		ymplanbo.removeRelatedTask(planid);
	}

	/* ������ȼƻ���Ԥ�����¼ƻ� */
	public Vector preGetTaskByParentPlan(String yearplanid, String regionid,
			int flag, String patrolid) throws Exception {
		return ymplanbo.preGetTaskByParentPlan(yearplanid, regionid, flag,
				patrolid);
	}

	/* ����ƻ���Ӧ�����б� */
	public Vector getTasklistByPlanID(String planid, String type)
			throws Exception {
		return ymplanbo.getTasklistByPlanID(planid, type);
	}

	/* �ƻ� */
	public void createPlan(Plan plan) throws Exception {
		boplan.addPlan(plan);
	}

	public Plan loadPlan(String id) throws Exception {
		return boplan.loadPlan(id);
	}

	public void removeWPlan(Plan plan) throws Exception {
		boplan.removeWPlan(plan);
	}

	public Plan updateWPlan(Plan plan) throws Exception {
		return boplan.updateWPlan(plan);
	}

	/* ���/�¶ȼƻ� */
	public int checkWPlanUnique(String id) throws Exception {
		return boplan.checkWPlanUnique(id);
	}

	/* ȡ��ĳ���еڼ����� */
	public String getWeekOfYear(String dateStr) throws Exception {
		return boplan.getWeekOfYear(dateStr);
	}

	/* ���� */
	public void createTask(Task task) throws Exception {
		botsk.addTask(task);
	}

	public Task loadTask(String id) throws Exception {
		return botsk.loadTask(id);
	}

	public Task updateTask(Task task) throws Exception {
		return botsk.updateTask(task);
	}

	/* ɾ��ԭ�ж�Ӧ����ļ�¼ */
	public void removeRelatedOperationsAndSubtask(String taskid)
			throws Exception {
		botsk.removeRelatedOperationsAndSubtask(taskid);
	}

	/* ȡ��ĳ�����Ӧ�����б� */
	public Vector getTaskOperListByTaskid(String taskid) throws Exception {
		return botsk.getTaskOperListByTaskid(taskid);
	}

	/* ���������б� */
	public ArrayList getPointsSubtask(String taskid) throws Exception {
		return botsk.getPointsSubtaskList(taskid);
	}

	/* ���������� */
	public void createSubTask(SubTask subtask) throws Exception {
		botsk.addSubTask(subtask);
	}

	/* �������������Ӧ�� */
	public void addTaskoperationlist(TaskOperationList taskoperationlist)
			throws Exception {
		botsk.addTaskoperationlist(taskoperationlist);
	}

	/**
	 * ��ĳ��Ѳ��Ա������ ������ʾ
	 * 
	 * @param patrolid
	 *            Ѳ��Աid
	 * @param fID
	 *            2 ���߶�; 1 ������ʾ
	 * @param linelevel
	 *            ��·����
	 * @return
	 * @throws Exception
	 */
	// public Vector getPUnitListByPatrolid( String patrolid, String fID,
	// String linelevel ) throws
	// Exception{
	// return botsk.getPUnitListByPatrolid( patrolid, fID, linelevel );
	// }

	/* ��ĳ��Ѳ��Ա������ ������ʾ */
	// public Vector getPUnitListByPatrolid( String patrolid, String fID )
	// throws
	// Exception{
	// return botsk.getPUnitListByPatrolid( patrolid, fID );
	// }
	public Vector newGetPUnitListByPatrolid(String planid) throws Exception {
		return botsk.newGetPUnitListByPatrolid(planid);

	}

	/* ��ĳ����������� ������ʾ */

	public boolean deleSplithTask() {
		return ymplanbo.deleSplithTask();
	}

}
