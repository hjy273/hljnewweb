package com.cabletech.linepatrol.safeguard.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cabletech.baseinfo.domainobjects.Contractor;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.linepatrol.safeguard.workflow.SafeguardWorkflowBO;
import com.cabletech.linepatrol.safeguard.beans.SafeguardTaskBean;
import com.cabletech.linepatrol.safeguard.module.SafeguardTask;

@Repository
public class SafeguardQueryStatDao extends HibernateDao<SafeguardTask, String> {
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
	 * 根据条件查询保障列表
	 * 
	 * @param safeguardTaskBean
	 *            ：保障任务Bean，获得查询条件
	 * @return
	 */
	public List safeguardQuery(SafeguardTaskBean safeguardTaskBean,
			UserInfo userInfo) {
		String conId = safeguardTaskBean.getConId();
		String startDate = safeguardTaskBean.getStartDate();
		String endDateOrg = safeguardTaskBean.getEndDate();
		String endDate = addOneDay(endDateOrg);
		String[] levels = safeguardTaskBean.getLevels();
		String safeguardName = safeguardTaskBean.getSafeguardName();
		String deptype = userInfo.getDeptype();
		String deptId = userInfo.getDeptID();

		String condition = "";
		String limit = "";
		if (conId != null && !"".equals(conId)) {
			condition += "and taskCon.contractor_id='" + conId + "' ";
		}
		if (startDate != null && !"".equals(startDate)) {
			condition += "and task.start_date>to_date('" + startDate
					+ "','yyyy/mm/dd') ";
		}
		if (endDate != null && !"".equals(endDate)) {
			condition += "and task.end_date<to_date('" + endDate
					+ "','yyyy/mm/dd') ";
		}
		if(levels != null){
//			String[] levels = level.split(",");
			condition += "and task.safeguard_level in (" + levels[0];
			for (int i = 1; i < levels.length; i++) {
				condition += "," + levels[i];
			}
			condition += ")";
		}
		if (safeguardName != null && !"".equals(safeguardName)) {
			condition += "and task.safeguard_name like '%" + safeguardName
					+ "%'";
		}
		//是否取消
		String safeguardState = safeguardTaskBean.getSafeguardState();
		if(StringUtils.isNotBlank(safeguardState)){
			if(StringUtils.equals(safeguardState, PLAN_CANCEL)){
				condition += " and taskCon.transact_state='" + safeguardState + "'";
			} else {
				condition += " and (taskCon.transact_state<>'" + PLAN_CANCEL + "' or taskCon.transact_state is null)";
			}
		}
		
		if (deptype.equals("2")) {
			limit += " and plan.contractor_id='" + deptId + "' ";
		}
		StringBuffer buf = new StringBuffer("");
		StringBuffer buf1 = new StringBuffer("");
		if (safeguardTaskBean.getTaskStates() != null) {
			String[] taskStates = safeguardTaskBean.getTaskStates();
			buf.append(" and exists( ");
			buf.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf.append(" where jbpm_task.execution_id_='");
			buf.append(SafeguardWorkflowBO.SAFEGUARD_WORKFLOW);
			buf.append(".'||taskCon.id ");
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

			buf1.append(" and ( ");
			buf1.append(" exists( ");
			buf1.append(" select dbid_ from jbpm4_task jbpm_task ");
			buf1.append(" where jbpm_task.execution_id_='");
			buf1.append(SafeguardWorkflowBO.SAFEGUARD_WORKFLOW);
			buf1.append(".'||taskCon.id ");
			buf1.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf1.append(" jbpm_task.name_='");
				buf1.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf1.append(" or ");
				}
			}
			buf1.append(" ) ");
			buf1.append(" ) or exists( ");
			buf1
					.append(" select dbid_ from jbpm4_task jbpm_task,lp_special_endplan end_plan ");
			buf1
					.append(" where end_plan.plan_id=plan.id and jbpm_task.execution_id_='");
			buf1.append(SafeguardWorkflowBO.SAFEGUARD_SUB_WORKFLOW);
			buf1.append(".'||end_plan.id ");
			buf1.append(" and ( ");
			for (int i = 0; i < taskStates.length; i++) {
				buf1.append(" jbpm_task.name_='");
				buf1.append(taskStates[i] + "' ");
				if (i < taskStates.length - 1) {
					buf1.append(" or ");
				}
			}
			buf1.append(" ) ");
			buf1.append(" ) or exists( ");
			buf1
					.append(" select dbid_ from jbpm4_task jbpm_task,lp_safeguard_plan remake_plan  ");
			buf1
					.append(" where remake_plan.scheme_id=plan.id and jbpm_task.execution_id_='");
			buf1.append(SafeguardWorkflowBO.SAFEGUARD_REPLAN_SUB);
			buf1.append(".'||remake_plan.plan_id ");
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
		String sql = "select distinct task.id task_id,'' plan_id,'' summary_id,coninfo.contractorname,taskCon.id taskcon_id,'1' as flag_pos,"
				+ "task.safeguard_code,task.safeguard_name,taskCon.contractor_id,coninfo.contractorname,"
				+ "to_char(task.start_date,'yyyy-mm-dd hh24:mi:ss') start_date,to_char(task.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') send_date,"
				+ "decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,"
				+ "decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state "
				+ " ,taskCon.transact_state  safeguard_state " 
				+ "from lp_safeguard_task task,lp_safeguard_con taskCon,contractorinfo coninfo "
				+ "where task.id=taskCon.safeguard_id and coninfo.contractorid=taskCon.contractor_id "
				+ condition
				+ buf.toString()
				+ "union "
				+ "select distinct task.id task_id,plan.id plan_id,'' summary_id,coninfo.contractorname,taskCon.id taskcon_id,'2' as flag_pos,"
				+ "task.safeguard_code,task.safeguard_name,taskCon.contractor_id,coninfo.contractorname,"
				+ "to_char(task.start_date,'yyyy-mm-dd hh24:mi:ss') start_date,to_char(task.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') send_date,"
				+ "decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,"
				+ "decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state "
				+ " ,taskCon.transact_state  safeguard_state " 
				+ "from lp_safeguard_task task,lp_safeguard_con taskCon,lp_safeguard_scheme plan,contractorinfo coninfo  "
				+ "where task.id=taskCon.safeguard_id and task.id=plan.safeguard_id and plan.contractor_id=taskCon.contractor_id and coninfo.contractorid=taskCon.contractor_id "
				+ condition
				+ buf1.toString()
				+ limit
				+ "union "
				+ "select distinct task.id task_id,plan.id plan_id,summary.id summary_id,coninfo.contractorname,taskCon.id taskcon_id,'3' as flag_pos,"
				+ "task.safeguard_code,task.safeguard_name,taskCon.contractor_id,coninfo.contractorname,"
				+ "to_char(task.start_date,'yyyy-mm-dd hh24:mi:ss') start_date,to_char(task.end_date,'yyyy-mm-dd hh24:mi:ss') end_date,to_char(task.send_date,'yyyy-mm-dd hh24:mi:ss') send_date,"
				+ "decode(task.safeguard_level,'1','一级','2','二级','3','三级','4','特级') safeguard_level,"
				+ "decode(taskCon.transact_state,'1','制定保障方案','2','保障方案审核','3','保障方案审核不通过','4','制定保障总结','5','保障总结审核','6','保障总结审核不通过','7','评分','8','完成') transact_state "
				+ " ,taskCon.transact_state  safeguard_state " 
				+ "from lp_safeguard_task task,lp_safeguard_con taskCon,lp_safeguard_scheme plan,lp_safeguard_sum summary,contractorinfo coninfo "
				+ "where task.id=taskCon.safeguard_id and task.id=plan.safeguard_id and plan.id=summary.scheme_id and plan.contractor_id=taskCon.contractor_id and coninfo.contractorid=taskCon.contractor_id "
				+ condition + buf1.toString() + limit;
		sql += " order by task_id desc";
		logger.info("***********保障查询语句：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
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
	 * 保障统计
	 * 
	 * @param safeguardTaskBean
	 * @param userInfo
	 * @return
	 */
	public List safeguardStat(SafeguardTaskBean safeguardTaskBean,
			UserInfo userInfo) {
		String conId = safeguardTaskBean.getConId();
		String startDate = safeguardTaskBean.getStartDate();
		String endDateOrg = safeguardTaskBean.getEndDate();
		String endDate = addOneDay(endDateOrg);
		String level = safeguardTaskBean.getLevel();
		String safeguardName = safeguardTaskBean.getSafeguardName();

		String condition = "";
		if (conId != null && !"".equals(conId)) {
			condition += "and taskCon.contractor_id='" + conId + "' ";
		}
		if (startDate != null && !"".equals(startDate)) {
			condition += "and task.start_date>to_date('" + startDate
					+ "','yyyy/mm/dd') ";
		}
		if (endDate != null && !"".equals(endDate)) {
			condition += "and task.end_date<to_date('" + endDate
					+ "','yyyy/mm/dd') ";
		}
		if (level != null) {
			String[] levels = level.split(",");
			condition += "and task.safeguard_level in (" + levels[0];
			for (int i = 1; i < levels.length; i++) {
				condition += "," + levels[i];
			}
			condition += ")";
		}
		if (safeguardName != null && !"".equals(safeguardName)) {
			condition += "and task.safeguard_name like '%" + safeguardName
					+ "%'";
		}
		String sql = "select coninfo.contractorname, count(summary.id) safeguard_number, sum(summary.fact_responder) total_person_number, "
				+ " sum(summary.fact_responding_unit) total_car_number,sum(summary.equipment_number) total_equipment_number,sum(trunc((sp.end_date-sp.start_date)*24,2)) total_time "
				+ " from lp_safeguard_scheme plan,lp_safeguard_sum summary,contractorinfo coninfo,lp_safeguard_con taskcon,lp_safeguard_task task,userinfo userinfo,lp_special_plan sp,lp_safeguard_plan splan "
				+ " where task.id=plan.safeguard_id and task.id=taskcon.safeguard_id and taskcon.contractor_id=coninfo.contractorid and plan.maker=userinfo.userid and plan.id=splan.scheme_id and splan.plan_id=sp.id and sp.plan_type='002' "
				+ " and userinfo.deptid=coninfo.contractorid and plan.id=summary.scheme_id and taskcon.transact_state='8' "
				+ condition;
		sql += " group by coninfo.contractorname ";
		logger.info("保障统计执行语句：" + sql);
		return this.getJdbcTemplate().queryForBeans(sql);
	}
	
	
	/**
	 * 通过PDA查询正在执行的保障
	 * @param userInfo
	 * @return
	 */
	public List<Map> QuerySafeguardFromPDA(UserInfo userInfo, String contractorId, String safeguradName) {
		String sql = "select TASK.SAFEGUARD_NAME safeguardName,SCHEME.PLAN_RESPONDER planResponder,SCHEME.PLAN_RESPONDING_UNIT planRespondingUnit,SCHEME.EQUIPMENT_NUMBER equipmentNum,CON.CONTRACTORNAME contractorName,decode(TASK.SAFEGUARD_LEVEL,'1','一级','2','二级','3','三级','4','特级') safeguardLevel,TASK.START_DATE startDate,TASK.END_DATE endDate,decode(SCHEME.IS_ALL,'0','否','1','是') isAll "
				+ "from lp_safeguard_task task,lp_safeguard_scheme scheme,contractorinfo con,lp_safeguard_con lcon "
				+ "where scheme.contractor_id like '%"+contractorId+"%' and task.safeguard_Name like '%"+safeguradName+"%' and TASK.ID=SCHEME.SAFEGUARD_ID and SCHEME.CONTRACTOR_ID=CON.CONTRACTORID and TASK.START_DATE<sysdate and TASK.END_DATE>sysdate and TASK.REGION_ID='"
				+ userInfo.getRegionID() + "' and LCON.SAFEGUARD_ID=TASK.ID and LCON.TRANSACT_STATE='4' and scheme.contractor_id=lcon.contractor_id";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 获得代维单位
	 * @param userInfo
	 * @return
	 */
	public List<Map<String,String>> getContractorForList(UserInfo userInfo){
		String sql="select contractorId,contractorname from contractorinfo con where con.state is null and CON.REGIONID='"+userInfo.getRegionID()+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
