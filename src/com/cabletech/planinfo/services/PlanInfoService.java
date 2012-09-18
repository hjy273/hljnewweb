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

	/* 报表 */
	// ******************pzj 修改
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

	/* 年度/月度计划 */
	public int checkYMPlanUnique(String id) throws Exception {
		return ymplanbo.checkYMPlanUnique(id);
	}

	public void romoveModifiedYMPlan(String Planid) throws Exception {
		ymplanbo.romoveModifiedYMPlan(Planid);
	}

	/**
	 * 新增计划任务对应表纪录
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

	/* 将计划任务关联表中与该计划对应的条目删除 */
	public void removeRelatedTask(String planid) throws Exception {
		ymplanbo.removeRelatedTask(planid);
	}

	/* 根据年度计划，预生成月计划 */
	public Vector preGetTaskByParentPlan(String yearplanid, String regionid,
			int flag, String patrolid) throws Exception {
		return ymplanbo.preGetTaskByParentPlan(yearplanid, regionid, flag,
				patrolid);
	}

	/* 载入计划对应任务列表 */
	public Vector getTasklistByPlanID(String planid, String type)
			throws Exception {
		return ymplanbo.getTasklistByPlanID(planid, type);
	}

	/* 计划 */
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

	/* 年度/月度计划 */
	public int checkWPlanUnique(String id) throws Exception {
		return boplan.checkWPlanUnique(id);
	}

	/* 取得某年中第几周数 */
	public String getWeekOfYear(String dateStr) throws Exception {
		return boplan.getWeekOfYear(dateStr);
	}

	/* 任务 */
	public void createTask(Task task) throws Exception {
		botsk.addTask(task);
	}

	public Task loadTask(String id) throws Exception {
		return botsk.loadTask(id);
	}

	public Task updateTask(Task task) throws Exception {
		return botsk.updateTask(task);
	}

	/* 删除原有对应任务的纪录 */
	public void removeRelatedOperationsAndSubtask(String taskid)
			throws Exception {
		botsk.removeRelatedOperationsAndSubtask(taskid);
	}

	/* 取得某任务对应操作列表 */
	public Vector getTaskOperListByTaskid(String taskid) throws Exception {
		return botsk.getTaskOperListByTaskid(taskid);
	}

	/* 点子任务列表 */
	public ArrayList getPointsSubtask(String taskid) throws Exception {
		return botsk.getPointsSubtaskList(taskid);
	}

	/* 单个子任务 */
	public void createSubTask(SubTask subtask) throws Exception {
		botsk.addSubTask(subtask);
	}

	/* 新增任务操作对应表 */
	public void addTaskoperationlist(TaskOperationList taskoperationlist)
			throws Exception {
		botsk.addTaskoperationlist(taskoperationlist);
	}

	/**
	 * 将某个巡检员的任务 分类显示
	 * 
	 * @param patrolid
	 *            巡检员id
	 * @param fID
	 *            2 按线段; 1 按点显示
	 * @param linelevel
	 *            线路级别
	 * @return
	 * @throws Exception
	 */
	// public Vector getPUnitListByPatrolid( String patrolid, String fID,
	// String linelevel ) throws
	// Exception{
	// return botsk.getPUnitListByPatrolid( patrolid, fID, linelevel );
	// }

	/* 将某个巡检员的任务 分类显示 */
	// public Vector getPUnitListByPatrolid( String patrolid, String fID )
	// throws
	// Exception{
	// return botsk.getPUnitListByPatrolid( patrolid, fID );
	// }
	public Vector newGetPUnitListByPatrolid(String planid) throws Exception {
		return botsk.newGetPUnitListByPatrolid(planid);

	}

	/* 将某个任务的任务 分类显示 */

	public boolean deleSplithTask() {
		return ymplanbo.deleSplithTask();
	}

}
