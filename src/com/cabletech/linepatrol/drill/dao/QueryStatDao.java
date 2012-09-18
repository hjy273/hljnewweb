package com.cabletech.linepatrol.drill.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.drill.workflow.DrillWorkflowBO;
import com.cabletech.linepatrol.drill.module.DrillQueryCondition;
import com.cabletech.linepatrol.drill.module.DrillTask;

@Repository
public class QueryStatDao extends HibernateDao<DrillTask, String> {

	private static final String PLAN_CANCEL = "999";
	
	/**
	 * 获得该区域下的代维单位
	 * 
	 * @param reginId
	 *            ：区域ID
	 * @return：代维单位列表
	 */
	public List<Contractor> getContractorInfo(String reginId) {
		String hql = "from Contractor con where con.regionid=?";
		return this.getHibernateTemplate().find(hql, reginId);
	}

	/**
	 * 按条件获得演练列表
	 * 
	 * @param drillQueryCondition
	 * @param userInfo
	 * @return
	 */
	public List drillQuery(DrillQueryCondition drillQueryCondition,
			UserInfo userInfo) {
		String name = drillQueryCondition.getName();
		String beginTime = drillQueryCondition.getBeginTime();
		String endTimeOrg = drillQueryCondition.getEndTime();
		String endTime = addOneDay(endTimeOrg);
		String[] levels = drillQueryCondition.getLevels();
		String[] state = drillQueryCondition.getState();
		String deptype = userInfo.getDeptype();
		String contractorId = userInfo.getDeptID();
		String conId = drillQueryCondition.getConId();

		String condition = "";
		String limit = "";
		if (name != null && !"".equals(name)) {
			condition += " and task.name like '%" + name + "%' ";
		}
		if (beginTime != null && !"".equals(beginTime)) {
			condition += " and task.begintime>to_date('" + beginTime
					+ "','yyyy/mm/dd') ";
		}
		if (endTime != null && !"".equals(endTime)) {
			condition += " and task.endTime<to_date('" + endTime
					+ "','yyyy/mm/dd') ";
		}
		if (levels!=null&&levels.length>0) {
			condition += " and task.drill_level in( "
					+ Integer.valueOf(levels[0]);
			for (int i = 1; i < levels.length; i++) {
				condition += "," + Integer.valueOf(levels[i]);
			}
			condition += ")";
		}
		if (state != null&&state.length>0) {
			condition += " and taskcon.state in (" + state[0];
			for(int i=1;i<state.length;i++){
				condition +=","+state[i];
			}
			condition+=")";
		}
		if (conId != null && !"".equals(conId)) {
			condition += " and taskcon.contractor_id='" + conId + "' ";
		}
		
		//是否取消
		String drillState = drillQueryCondition.getDrillState();
		if(StringUtils.isNotBlank(drillState)){
			if(StringUtils.equals(drillState, PLAN_CANCEL)){
				condition += " and task.drill_state='" + drillState + "'";
			} else {
				condition += " and (task.drill_state<>'" + PLAN_CANCEL + "' or task.drill_state is null)";
			}
		}
		
		if (deptype.equals("2")) {
			// condition += " and taskcon.contractor_id='" + contractorId +
			// "' ";
			limit += " and plan.contractor_id='" + contractorId + "' ";
		}
		StringBuffer buf = new StringBuffer("");
		StringBuffer buf1 = new StringBuffer("");
		if (drillQueryCondition.getTaskStates() != null) {
			String[] taskStates = drillQueryCondition.getTaskStates();
			buf.append(" and ( ");
			buf.append(" exists( ");
			buf.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf.append(" where jbpm_task.execution_id_='");
			buf.append(DrillWorkflowBO.DRILL_WORKFLOW);
			buf.append(".'||taskcon.id ");
			buf.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf.append(" jbpm_task.name_='");
				buf.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf.append(" or ");
				}
			}
			buf.append(" ) ");
			buf.append(" ) ");
			buf.append(" ) ");
			
			buf1.append(" and ( ");
			buf1.append(" exists( ");
			buf1.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf1.append(" where jbpm_task.execution_id_='");
			buf1.append(DrillWorkflowBO.DRILL_WORKFLOW);
			buf1.append(".'||taskcon.id ");
			buf1.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf1.append(" jbpm_task.name_='");
				buf1.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf1.append(" or ");
				}
			}
			buf1.append(" ) ");
			buf1.append(" ) ");
			buf1.append(" or exists( ");
			buf1
					.append(" select dbid_ from jbpm4_task jbpm_task,lp_drillplan_modify plan_modify ");
			buf1.append(" where plan_modify.plan_id=plan.id ");
			buf1.append(" and jbpm_task.execution_id_='");
			buf1.append(DrillWorkflowBO.DRILL_SUB_WORKFLOW);
			buf1.append(".'||plan_modify.id ");
			buf1.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf1.append(" jbpm_task.name_='");
				buf1.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf1.append(" or ");
				}
			}
			buf1.append(" ) ");
			buf1.append(" ) ");
			buf1.append(" ) ");
		}

		String sql = "select  distinct task.id task_id,task.creator creator ,'' as plan_id,'' as summary_id,task.name task_name,taskcon.contractor_id,taskcon.id taskcon_id,'1' as flag_pos,coninfo.contractorname,"
				+ "decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level, "
				+ "to_char(task.begintime,'yyyy-mm-dd hh24:mi:ss') task_begintime,to_char(task.endtime,'yyyy-mm-dd hh24:mi:ss') task_endtime,to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') createtime,"
				+ "0 as plan_person_number,0 as summary_person_number,0 as plan_equipment_number,0 as sum_equipment_number,"
				+ "0 as plan_car_number,0 as summary_car_number,case when task.drill_state is null then 'null' else task.drill_state end as state,taskcon.contractor_id contractor_id "
				+ "from lp_drilltask task,lp_drilltask_con taskcon,contractorinfo coninfo "
				+ "where task.id=taskcon.drill_id and coninfo.contractorid=taskcon.contractor_id "
				+ condition
				+ buf.toString()
				+ "union "
				+ "select  distinct task.id task_id,task.creator creator,plan.id plan_id,'' as summary_id,task.name task_name,taskcon.contractor_id,taskcon.id taskcon_id,'2' as flag_pos,coninfo.contractorname,"
				+ "decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level, "
				+ "to_char(task.begintime,'yyyy-mm-dd hh24:mi:ss') task_begintime,to_char(task.endtime,'yyyy-mm-dd hh24:mi:ss') task_endtime,to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') createtime,"
				+ "plan.person_number plan_person_number,0 as summary_person_number,plan.equipment_number as plan_equipment_number,0 as sum_equipment_number,"
				+ "plan.car_number plan_car_number,0 as summary_car_number,case when task.drill_state is null then 'null' else task.drill_state end as state,taskcon.contractor_id contractor_id "
				+ "from lp_drilltask task,lp_drillplan plan,lp_drilltask_con taskcon,contractorinfo coninfo "
				+ "where task.id=plan.task_id and task.id=taskcon.drill_id and coninfo.contractorid=taskcon.contractor_id and plan.contractor_id=taskcon.contractor_id "
				+ condition
				+ buf1.toString()
				+ limit
				+ "union "
				+ "select  distinct task.id task_id,task.creator creator,plan.id plan_id,summary.id summary_id,task.name task_name,taskcon.contractor_id,taskcon.id taskcon_id,'3' as flag_pos,coninfo.contractorname,"
				+ "decode(task.drill_level,'0','自定义演练','1','一般演练','2','重点演练') as drill_level, "
				+ "to_char(task.begintime,'yyyy-mm-dd hh24:mi:ss') task_begintime,to_char(task.endtime,'yyyy-mm-dd hh24:mi:ss') task_endtime,to_char(task.createtime,'yyyy-mm-dd hh24:mi:ss') createtime,"
				+ "plan.person_number plan_person_number,summary.person_number summary_person_number,plan.equipment_number as plan_equipment_number,summary.equipment_number as sum_equipment_number,"
				+ "plan.car_number plan_car_number,summary.car_number summary_car_number,case when task.drill_state is null then 'null' else task.drill_state end as state,taskcon.contractor_id contractor_id "
				+ "from lp_drilltask task,lp_drillplan plan,lp_drillsummary summary,lp_drilltask_con taskcon,contractorinfo coninfo "
				+ "where task.id=plan.task_id and plan.id=summary.plan_id and task.id=taskcon.drill_id and coninfo.contractorid=taskcon.contractor_id and plan.contractor_id=taskcon.contractor_id "
				+ condition + buf1.toString() + limit;
		sql += " order by task_id desc";
		logger.info("演练查询语句：" + sql);
		List list = this.getJdbcTemplate().queryForBeans(sql);
		return list;
	}

	// 增加一天
	private String addOneDay(String strDate) {
		String addDate = "";
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if (strDate != null && !"".equals(strDate)) {
			try {
				Date date = df.parse(strDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				addDate = df.format(calendar.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return addDate;
	}

	/**
	 * 演练统计
	 * 
	 * @param drillQueryCondition
	 *            ：演练统计条件
	 * @param userInfo
	 * @return
	 */
	public List drillStat(DrillQueryCondition drillQueryCondition,
			UserInfo userInfo) {
		String beginTime = drillQueryCondition.getBeginTime();
		String endTimeOrg = drillQueryCondition.getEndTime();
		String endTime = addOneDay(endTimeOrg);
		String[] levels = drillQueryCondition.getLevels();
		String conId = drillQueryCondition.getConId();
		String sql = "select coninfo.contractorname, count(summary.id) drill_number, sum(summary.person_number) total_person_number, "
				+ "sum(summary.car_number) total_car_number,sum(summary.equipment_number) total_equipment_number "
				+ "from lp_drillplan plan,lp_drillsummary summary,contractorinfo coninfo,lp_drilltask_con taskcon,lp_drilltask task,userinfo userinfo "
				+ "where task.id=plan.task_id and task.id=taskcon.drill_id and taskcon.contractor_id=coninfo.contractorid and plan.creator=userinfo.userid "
				+ "and userinfo.deptid=coninfo.contractorid and plan.id=summary.plan_id and taskcon.state='8' ";
		if (conId != null && !"".equals(conId)) {
			sql += " and taskcon.contractor_id='" + conId + "' ";
		}
		if (levels!=null&&levels.length > 0) {
			sql += " and task.drill_level in( " + Integer.valueOf(levels[0]);
			for (int i = 1; i < levels.length; i++) {
				sql += "," + Integer.valueOf(levels[i]);
			}
			sql += ")";
		}
		if (beginTime != null && !"".equals(beginTime)) {
			sql += " and plan.begintime>to_date('" + beginTime
					+ "','yyyy/mm/dd') ";
		}
		if (endTime != null && !"".equals(endTime)) {
			sql += " and plan.endTime<to_date('" + endTime + "','yyyy/mm/dd') ";
		}
		sql += " group by coninfo.contractorname";
		logger.info("演练统计：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
}
