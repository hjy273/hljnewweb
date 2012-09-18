package com.cabletech.machine.dao;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.PollingContentBean;

public class PollingContentDAO {
	private static Logger logger = Logger.getLogger(PollingContentDAO.class
			.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";


	/**
	 * 代维填写核心层或者接入层SDH的巡检任务的内容,同时修改mobileTask的状态
	 * 
	 * @param bean
	 * @param pid
	 * @param tid
	 * @return
	 */
	public boolean addPollingContent(PollingContentBean bean) {
		StringBuffer sb = new StringBuffer();
		OracleIDImpl ora = new OracleIDImpl();
		String cid = ora.getSeq("polling_content", 10);
		sb.append("insert into polling_content(cid,pid,is_clean,obve_temperature,is_top_pilotlamp,is_veneer_pilotlamp,")
		.append("is_dustproof_clean,fan_state,machine_floor_clean,machine_temperature,machine_humidity,ddf_colligation,")
		.append("fiber_protect,ddf_cable_fast,odf_interface_fast,odf_label_check,ddf_cabel_check) values('")
		.append(cid + "','").append(bean.getPid() + "','").append(bean.getIsClean() + "','").append(bean.getObveTemperature() + "','")
		.append(bean.getIsToppilotlamp() + "','").append(bean.getIsVeneerpilotlamp() + "','").append(bean.getIsDustproofClean() + "','")
		.append(bean.getFanState() + "','").append(bean.getMachineFloorClean() + "','").append(bean.getMachineTemperature()+ "','")
		.append(bean.getMachineHumidity() + "','").append(bean.getDdfColligation() + "','").append(bean.getFiberProtect() + "','")
		.append(bean.getDdfCableFast() + "','").append(bean.getOdfInterfacefast() + "','").append(bean.getOdfLabelCheck() + "','")
		.append(bean.getDdfCabelCheck() + "')");
		UpdateUtil exec = null;
		logger.info(sb.toString());
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sb.toString());
			return true;
		} catch (Exception e) {
			logger.error("增加核心或SDH出现异常:" + e.getMessage());
		}
		return false;
	}

	/**
	 * 获取核心层或者SDH层机房的单个设备
	 * @param id
	 * @return
	 */
	public PollingContentBean getOneForm(String id) {
		PollingContentBean bean = null;
		QueryUtil qu = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select pc.cid,pc.is_clean,pc.obve_temperature,pc.is_top_pilotlamp,pc.is_veneer_pilotlamp,pc.is_dustproof_clean, ")
		.append(" pc.fan_state,pc.machine_floor_clean,pc.machine_temperature,pc.machine_humidity,pc.ddf_colligation, ")
		.append(" pc.fiber_protect,pc.ddf_cable_fast,pc.odf_interface_fast,pc.odf_label_check,pc.ddf_cabel_check ")
		.append(" from polling_content pc where pc.pid='" + id + "'");
		logger.info(sb.toString());
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sb.toString());
			if(rs.next()) {
				bean = new PollingContentBean();
				bean.setCid(rs.getString("cid"));
				bean.setIsClean(rs.getString("is_clean"));
				bean.setObveTemperature(rs.getString("obve_temperature"));
				bean.setIsToppilotlamp(rs.getString("is_top_pilotlamp"));
				bean.setIsVeneerpilotlamp(rs.getString("is_veneer_pilotlamp"));
				bean.setIsDustproofClean(rs.getString("is_dustproof_clean"));
				bean.setFanState(rs.getString("fan_state"));
				bean.setMachineFloorClean(rs.getString("machine_floor_clean"));
				bean.setMachineTemperature(rs.getString("machine_temperature"));
				bean.setMachineHumidity(rs.getString("machine_humidity"));
				bean.setDdfColligation(rs.getString("ddf_colligation"));
				bean.setFiberProtect(rs.getString("fiber_protect"));
				bean.setDdfCableFast(rs.getString("ddf_cable_fast"));
				bean.setOdfInterfacefast(rs.getString("odf_interface_fast"));
				bean.setOdfLabelCheck(rs.getString("odf_label_check"));
				bean.setDdfCabelCheck(rs.getString("ddf_cabel_check"));
			}
			return bean;
		} catch (Exception e) {
			logger.error("获取单个机房设备出现异常:" + e.getMessage());
			return null;
		}
	}

	private void initResponse(HttpServletResponse response, String outfilename)
			throws Exception {
		logger.info("开始reset");
		response.reset();
		logger.info("reset");
		response.setContentType(CONTENT_TYPE);
		logger.info("setContentType");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(outfilename.getBytes(), "iso-8859-1"));
		logger.info("setHeader");
	}
}
