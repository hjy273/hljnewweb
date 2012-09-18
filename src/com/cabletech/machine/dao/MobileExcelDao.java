package com.cabletech.machine.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

public class MobileExcelDao {

	private static Logger logger = Logger.getLogger(EquipmentInfoDAO.class
			.getName());
	
	/**
	 * 取得核心屋或接入层SDH的内容
	 * @return
	 */
	public List getContentCheckInfo(HashMap queryMap, String typeStr) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select mt.tid, pt.pid, con.contractorname, us.username, TO_CHAR(mt.executetime,'yyyy-mm-dd') executetime , ei.equipment_name,");
		sql.append("pc.is_clean, pc.obve_temperature, pc.is_top_pilotlamp, pc.is_veneer_pilotlamp, pc.is_dustproof_clean,");
		sql.append("pc.fan_state, pc.machine_floor_clean, pc.machine_temperature, pc.machine_humidity, pc.ddf_colligation, ");
		sql.append("pc.fiber_protect,pc.ddf_cable_fast, pc.odf_interface_fast, pc.odf_label_check, pc.ddf_cabel_check,");
		sql.append("pt.auditresult, pt.checkremark ");
		sql.append(" from mobile_task mt , polling_task pt , polling_content pc , equipment_info ei, userinfo us, contractorinfo con ");
		sql.append(" where mt.state = '4' and mt.tid = pt.tid  and pt.pid = pc.pid and pt.eid = ei.eid ");
		sql.append(" and mt.userid = us.userid(+) and mt.contractorid = con.contractorid and mt.machinetype = '" + typeStr + "'");
		
		String contractorid = String.valueOf(queryMap.get("contractorid"));
		String userid = String.valueOf(queryMap.get("userid"));
		String checkuser = String.valueOf(queryMap.get("checkuser"));
		String state = String.valueOf(queryMap.get("state"));
		String begtime = String.valueOf(queryMap.get("begtime"));
		String endtime = String.valueOf(queryMap.get("endtime"));
		String condition = "";
//		if (!"0".equals(type)) {
//			condition += " and mt.machinetype='" + type + "'";
//		}
		if (!"0".equals(contractorid)) {
			condition += " and mt.contractorid='" + contractorid + "'";
		}
		if (!"0".equals(userid) && !"null".equals(userid) && !"".equals(userid)) {
			condition += " and mt.userid='" + userid + "'";
		}
		if (!"0".equals(checkuser) && !"null".equals(checkuser) && !"".equals(checkuser)) {
			condition += " and mt.checkuser='" + checkuser + "'";
		}
		if (!"0".equals(state) && !"null".equals(state) && !"".equals(state)) {
			condition += " and mt.state='" + state + "'";
		}
		if (!"null".equals(begtime) && !"".equals(begtime)) {
			condition += " and mt.maketime >= TO_DATE('" + begtime
					+ "','YYYY-MM-DD')";
		}
		if (!"null".equals(endtime) && !"".equals(endtime)) {
			condition += " and mt.maketime <= to_date('" + endtime
					+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
		}
		sql.append(condition);
		sql.append(" order by mt.tid, pt.pid ");
		
		logger.info("按巡检类型导出的Sql语句:" + sql.toString());
		
		List checkedInfo = null;
		try {
			QueryUtil qu = new QueryUtil();
			checkedInfo = qu.queryBeans(sql.toString());
		} catch (Exception e) {
			logger.error("获取核心层或接入层SDH核查后任务列表出现异常:" + e.getMessage());
		}
		
		return checkedInfo;
	}
	
	/**
	 * 取得接入层微波层的内容
	 * @return
	 */
	public List getMicroCheckInfo(HashMap queryMap, String typeStr) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select mt.tid, pt.pid, con.contractorname, us.username, TO_CHAR(mt.executetime,'yyyy-mm-dd') executetime , eia.equipment_name equipment_namea, eib.equipment_name equipment_nameb, ");
		sql.append("pc.is_clean, pc.obve_temperature, pc.is_top_pilotlamp, pc.is_veneer_pilotlamp, pc.is_dustproof_clean,");
		sql.append("pc.fan_state, pc.outdoor_fast, pc.machine_temperature, pc.machine_humidity, pc.ddf_colligation, ");
		sql.append("pc.label_check, pc.cabel_check , ");
		sql.append("pt.auditresult, pt.checkremark ");
		sql.append(" from mobile_task mt , polling_task pt , polling_con_micro pc , equipment_info eia, equipment_info eib,userinfo us, contractorinfo con ");
		sql.append(" where mt.state = '4' and mt.tid = pt.tid  and pt.pid = pc.pid and pt.aeid = eia.eid and pt.beid = eib.eid ");
		sql.append(" and mt.userid = us.userid(+) and mt.contractorid = con.contractorid and mt.machinetype = '" + typeStr + "'");
		
		String contractorid = String.valueOf(queryMap.get("contractorid"));
		String userid = String.valueOf(queryMap.get("userid"));
		String checkuser = String.valueOf(queryMap.get("checkuser"));
		String state = String.valueOf(queryMap.get("state"));
		String begtime = String.valueOf(queryMap.get("begtime"));
		String endtime = String.valueOf(queryMap.get("endtime"));
		String condition = "";
//		if (!"0".equals(type)) {
//			condition += " and mt.machinetype='" + type + "'";
//		}
		if (!"0".equals(contractorid)) {
			condition += " and mt.contractorid='" + contractorid + "'";
		}
		if (!"0".equals(userid) && !"null".equals(userid) && !"".equals(userid)) {
			condition += " and mt.userid='" + userid + "'";
		}
		if (!"0".equals(checkuser) && !"null".equals(checkuser) && !"".equals(checkuser)) {
			condition += " and mt.checkuser='" + checkuser + "'";
		}
		if (!"0".equals(state) && !"null".equals(state) && !"".equals(state)) {
			condition += " and mt.state='" + state + "'";
		}
		if (!"null".equals(begtime) && !"".equals(begtime)) {
			condition += " and mt.maketime >= TO_DATE('" + begtime
					+ "','YYYY-MM-DD')";
		}
		if (!"null".equals(endtime) && !"".equals(endtime)) {
			condition += " and mt.maketime <= to_date('" + endtime
					+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
		}
		sql.append(condition);
		sql.append(" order by mt.tid, pt.pid ");
		
		logger.info("按巡检类型导出的Sql语句:" + sql.toString());
		
		List checkedInfo = null;
		try {
			QueryUtil qu = new QueryUtil();
			checkedInfo = qu.queryBeans(sql.toString());
		} catch (Exception e) {
			logger.error("获取核心层或接入层SDH核查后任务列表出现异常:" + e.getMessage());
		}
		
		return checkedInfo;
	}
	
	/**
	 * 取得接入层FSO层的内容
	 * @return
	 */
	public List getFsoCheckInfo(HashMap queryMap, String typeStr) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select mt.tid, pt.pid, con.contractorname, us.username, TO_CHAR(mt.executetime,'yyyy-mm-dd') executetime , eia.equipment_name equipment_namea, eib.equipment_name equipment_nameb, ");
		sql.append("pt.Equipment_model, pt.Machine_NO, pt.Power_Type, ");
		sql.append("pc.is_clean, pc.obve_temperature, pc.is_machine_pilotlamp, pc.search_light_power, pc.power_colligation,");
		sql.append("pc.power_label_check, ");
		sql.append("pt.auditresult, pt.checkremark ");
		sql.append(" from mobile_task mt , polling_task pt , polling_con_fso pc , equipment_info eia,  equipment_info eib, userinfo us, contractorinfo con ");
		sql.append(" where mt.state = '4' and mt.tid = pt.tid  and pt.pid = pc.pid and pt.aeid = eia.eid and pt.beid = eib.eid  ");
		sql.append(" and mt.userid = us.userid(+) and mt.contractorid = con.contractorid and mt.machinetype = '" + typeStr + "'");
		
		String contractorid = String.valueOf(queryMap.get("contractorid"));
		String userid = String.valueOf(queryMap.get("userid"));
		String checkuser = String.valueOf(queryMap.get("checkuser"));
		String state = String.valueOf(queryMap.get("state"));
		String begtime = String.valueOf(queryMap.get("begtime"));
		String endtime = String.valueOf(queryMap.get("endtime"));
		String condition = "";
//		if (!"0".equals(type)) {
//			condition += " and mt.machinetype='" + type + "'";
//		}
		if (!"0".equals(contractorid)) {
			condition += " and mt.contractorid='" + contractorid + "'";
		}
		if (!"0".equals(userid) && !"null".equals(userid) && !"".equals(userid)) {
			condition += " and mt.userid='" + userid + "'";
		}
		if (!"0".equals(checkuser) && !"null".equals(checkuser) && !"".equals(checkuser)) {
			condition += " and mt.checkuser='" + checkuser + "'";
		}
		if (!"0".equals(state) && !"null".equals(state) && !"".equals(state)) {
			condition += " and mt.state='" + state + "'";
		}
		if (!"null".equals(begtime) && !"".equals(begtime)) {
			condition += " and mt.maketime >= TO_DATE('" + begtime
					+ "','YYYY-MM-DD')";
		}
		if (!"null".equals(endtime) && !"".equals(endtime)) {
			condition += " and mt.maketime <= to_date('" + endtime
					+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
		}
		sql.append(condition);
		sql.append(" order by mt.tid, pt.pid ");
		
		logger.info("按巡检类型导出的Sql语句:" + sql.toString());
		
		List checkedInfo = null;
		try {
			QueryUtil qu = new QueryUtil();
			checkedInfo = qu.queryBeans(sql.toString());
		} catch (Exception e) {
			logger.error("获取核心层或接入层SDH核查后任务列表出现异常:" + e.getMessage());
		}
		
		return checkedInfo;
	}
	
	/**
	 * 取得光交维护层的内容
	 * @return
	 */
	public List getFiberCheckInfo(HashMap queryMap, String typeStr) {
		StringBuffer sql = new StringBuffer();
		
		
		sql.append("select mt.tid, pt.pid, con.contractorname, us.username, TO_CHAR(mt.executetime,'yyyy-mm-dd') executetime , ei.equipment_name,");
		sql.append("pc.is_update, pc.is_clean, pc.is_fiberbox_clean, pc.is_colligation, pc.is_fiber_check,");
		sql.append("pc.is_tailfiber_check,");
		sql.append("pt.auditresult, pt.checkremark ");
		sql.append(" from mobile_task mt , polling_task pt , polling_con_fiber pc , equipment_info ei, userinfo us, contractorinfo con ");
		sql.append(" where mt.state = '4' and mt.tid = pt.tid  and pt.pid = pc.pid and pt.eid = ei.eid ");
		sql.append(" and mt.userid = us.userid(+) and mt.contractorid = con.contractorid and mt.machinetype = '" + typeStr + "'");
		
		
		String contractorid = String.valueOf(queryMap.get("contractorid"));
		String userid = String.valueOf(queryMap.get("userid"));
		String checkuser = String.valueOf(queryMap.get("checkuser"));
		String state = String.valueOf(queryMap.get("state"));
		String begtime = String.valueOf(queryMap.get("begtime"));
		String endtime = String.valueOf(queryMap.get("endtime"));
		String condition = "";
		
		if (!"0".equals(contractorid)) {
			condition += " and mt.contractorid='" + contractorid + "'";
		}
		if (!"0".equals(userid) && !"null".equals(userid) && !"".equals(userid)) {
			condition += " and mt.userid='" + userid + "'";
		}
		if (!"0".equals(checkuser) && !"null".equals(checkuser) && !"".equals(checkuser)) {
			condition += " and mt.checkuser='" + checkuser + "'";
		}
		if (!"0".equals(state) && !"null".equals(state) && !"".equals(state)) {
			condition += " and mt.state='" + state + "'";
		}
		if (!"null".equals(begtime) && !"".equals(begtime)) {
			condition += " and mt.maketime >= TO_DATE('" + begtime
					+ "','YYYY-MM-DD')";
		}
		if (!"null".equals(endtime) && !"".equals(endtime)) {
			condition += " and mt.maketime <= to_date('" + endtime
					+ " 23:59:59','YYYY-MM-DD hh24:mi:ss')";
		}
		sql.append(condition);
		sql.append(" order by mt.tid, pt.pid ");
		
		logger.info("按巡检类型导出的Sql语句:" + sql.toString());
		
		List checkedInfo = null;
		try {
			QueryUtil qu = new QueryUtil();
			checkedInfo = qu.queryBeans(sql.toString());
		} catch (Exception e) {
			logger.error("获取光交维护核查后任务列表出现异常:" + e.getMessage());
		}
		
		return checkedInfo;
	}
}
