package com.cabletech.machine.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cabletech.commons.config.ConfigPathUtil;
import com.cabletech.commons.generatorID.impl.OracleIDImpl;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;
import com.cabletech.machine.beans.PollingConFiberBean;

public class PollingConFiberDAO {

	private static Logger logger = Logger.getLogger(PollingConFiberDAO.class.getName());

	private static String CONTENT_TYPE = "application/vnd.ms-excel";

	/**
	 *  add one pollingConFiberBean
	 * @param bean
	 * @return
	 */
	public boolean addPollingConFiber(PollingConFiberBean bean) {
		StringBuffer sb = new StringBuffer();
		OracleIDImpl ora = new OracleIDImpl();

		String id = ora.getSeq("polling_con_fiber", 10);
		sb.append("insert into polling_con_fiber(ID,PID,IS_UPDATE,IS_CLEAN,IS_FIBERBOX_CLEAN,IS_COLLIGATION,IS_FIBER_CHECK,IS_TAILFIBER_CHECK)");
		sb.append("values('").append(id + "','").append(bean.getPid() + "','").append(bean.getIsUpdate() + "','")
				.append(bean.getIsClean() + "','").append(bean.getIsFiberBoxClean() + "','")
				.append(bean.getIsColligation() + "','").append(bean.getIsFiberCheck() + "','")
				.append(bean.getIsTailFiberCheck() + "')");

		UpdateUtil exec = null;
		logger.info(sb.toString());
		try {
			exec = new UpdateUtil();
			exec.executeUpdate(sb.toString());
			return true;
		} catch (Exception e) {
			logger.error("增加光交维护出现异常:" + e.getMessage());
		}
		return false;
	}

	/**
	 * get one pollingConFiberBean
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public PollingConFiberBean getOneForm(String id) {

		String querySql = "select * from polling_con_fiber where pid='" + id + "'";
		QueryUtil qu = null;
		ResultSet rs = null;
		PollingConFiberBean bean = null;
		try {
			qu = new QueryUtil();
			rs = qu.executeQuery(querySql);
			boolean queryTag = rs.next();
			if (queryTag) {
				bean = new PollingConFiberBean();
				bean.setId(rs.getString("ID"));
				bean.setPid(rs.getString("PID"));
				bean.setIsUpdate(rs.getString("IS_UPDATE"));
				bean.setIsClean(rs.getString("IS_CLEAN"));
				bean.setIsColligation(rs.getString("IS_COLLIGATION"));
				bean.setIsFiberBoxClean(rs.getString("IS_FIBERBOX_CLEAN"));
				bean.setIsFiberCheck(rs.getString("IS_FIBER_CHECK"));
				bean.setIsTailFiberCheck(rs.getString("IS_TAILFIBER_CHECK"));
			}

		} catch (Exception e) {
			logger.error("查询一个光交维护信息出现异常:" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
}
