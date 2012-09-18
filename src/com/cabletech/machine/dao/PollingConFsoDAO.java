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
import com.cabletech.machine.beans.PollingConFsoBean;
import com.cabletech.sparepartmanage.dao.SeparepartBaseInfoDAO;

public class PollingConFsoDAO {

	private static Logger logger = Logger.getLogger(PollingConFsoDAO.class.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 * 代维填写接入层FSO的巡检任务的内容,同时修改mobileTask的状态
	 * @param bean
	 * @param pid
	 * @param tid
	 * @return
	 */
	public boolean addPollingConFso(PollingConFsoBean bean) {
		StringBuffer sb = new StringBuffer();
		OracleIDImpl ora = new OracleIDImpl();
		String fid = ora.getSeq("polling_con_fso", 10);
		sb.append("insert into polling_con_fso(fid,pid,is_clean,obve_temperature,is_machine_pilotlamp,")
				.append("search_light_power,power_colligation,power_label_check) values('").append(fid + "','")
				.append(bean.getPid() + "','").append(bean.getIsClean() + "','")
				.append(bean.getObveTemperature() + "','").append(bean.getIsMachinePilotlamp() + "','")
				.append(bean.getSearchLightPower() + "','").append(bean.getPowerColligation() + "','")
				.append(bean.getPowerLabelCheck() + "')");
		UpdateUtil exec = null;
		logger.info(sb.toString());
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sb.toString());
			return true;
		} catch (Exception e) {
			logger.error("增加FSO出现异常:" + e.getMessage());
		}
		return false;
	}

	public PollingConFsoBean getOneForm(String pid) {
		PollingConFsoBean bean = null;
		String sql = "select * from polling_con_fso pf where pf.pid='" + pid + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		logger.info(sql);
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(sql);
			boolean bl = rs.next();
			if (bl) {
				bean = new PollingConFsoBean();
				bean.setFid(rs.getString("fid"));
				bean.setPid(rs.getString("pid"));
				bean.setIsClean(rs.getString("is_clean"));
				bean.setObveTemperature(rs.getString("obve_temperature"));
				bean.setIsMachinePilotlamp(rs.getString("is_machine_pilotlamp"));
				bean.setSearchLightPower(rs.getString("search_light_power"));
				bean.setPowerColligation(rs.getString("power_colligation"));
				bean.setPowerLabelCheck(rs.getString("power_label_check"));
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
