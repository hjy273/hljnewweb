package com.cabletech.machine.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.PollingConMicroBean;

public class PollingConMicroDAO {

	private static Logger logger = Logger.getLogger(PollingConMicroDAO.class.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * 代维填写接入微波层的巡检任务的内容
	 * @param bean
	 * @param pid
	 * @param tid
	 * @return
	 */
	public boolean addPollingConMicro(PollingConMicroBean bean) {
		StringBuffer sb = new StringBuffer();
		OracleIDImpl ora = new OracleIDImpl();
		String mid = ora.getSeq("polling_con_micro", 10);
		sb.append("insert into polling_con_micro(mid,pid,is_clean,obve_temperature,is_top_pilotlamp,")
				.append("is_veneer_pilotlamp,is_dustproof_clean,fan_state,outdoor_fast,machine_temperature,")
				.append("machine_humidity,ddf_colligation,label_check,cabel_check) values('").append(mid + "','")
				.append(bean.getPid() + "','").append(bean.getIsClean() + "','")
				.append(bean.getObveTemperature() + "','").append(bean.getIsToppilotlamp() + "','")
				.append(bean.getIsVeneerpilotlamp() + "','").append(bean.getIsDustproofClean() + "','")
				.append(bean.getFanState() + "','").append(bean.getOutdoorFast() + "','")
				.append(bean.getMachineTemperature() + "','").append(bean.getMachineHumidity() + "','")
				.append(bean.getDdfColligation() + "','").append(bean.getLabelCheck() + "','")
				.append(bean.getCabelCheck() + "')");
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

	public PollingConMicroBean getOneForm(String pid) {
		PollingConMicroBean bean = null;
		String sql = "select * from polling_con_micro pm where pm.pid='" + pid + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		logger.info(sql);
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			boolean bl = rs.next();
			if (bl) {
				bean = new PollingConMicroBean();
				bean.setMid(rs.getString("mid"));
				bean.setPid(rs.getString("pid"));
				bean.setIsClean(rs.getString("is_clean"));
				bean.setObveTemperature(rs.getString("obve_temperature"));
				bean.setFanState(rs.getString("fan_state"));
				bean.setIsToppilotlamp(rs.getString("is_top_pilotlamp"));
				bean.setIsVeneerpilotlamp(rs.getString("is_veneer_pilotlamp"));
				bean.setIsDustproofClean(rs.getString("is_dustproof_clean"));
				bean.setOutdoorFast(rs.getString("outdoor_fast"));
				bean.setMachineTemperature(rs.getString("machine_temperature"));
				bean.setMachineHumidity(rs.getString("machine_humidity"));
				bean.setDdfColligation(rs.getString("ddf_colligation"));
				bean.setLabelCheck(rs.getString("label_check"));
				bean.setCabelCheck(rs.getString("cabel_check"));
			}
		} catch (Exception e) {
			logger.error("获取单个机房设备出现异常:" + e.getMessage());
			return null;
		}
		return bean;
	}

	private void initResponse(HttpServletResponse response, String outfilename) throws Exception {
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
