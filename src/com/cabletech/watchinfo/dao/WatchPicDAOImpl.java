package com.cabletech.watchinfo.dao;

import java.sql.ResultSet;
import java.util.List;
import org.apache.log4j.Logger;
import com.cabletech.commons.hb.QueryUtil;
import com.cabletech.commons.hb.UpdateUtil;

public class WatchPicDAOImpl {
	private Logger logger = Logger.getLogger(WatchPicDAOImpl.class.getName());

	public boolean updateData(String sql) {
		UpdateUtil util = null;
		try {
			util = new UpdateUtil();
			util.executeUpdate(sql);
			// util.close();
			return true;
		} catch (Exception e) {
			util = null;
			e.printStackTrace();
			return false;
		}
	}

	public ResultSet getResultset(String sql) throws Exception {

		QueryUtil query = new QueryUtil();
		return query.executeQuery(sql);

	}

	public List getResultList(String sql) {
		QueryUtil query = null;
		List list = null;
		try {
			query = new QueryUtil();
			list = query.queryBeans(sql);
		} catch (Exception ex) {
			logger.error("查询异常：" + ex.getMessage());
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * 依据SQL得到String型的返回值(多条记录组合成一条，用逗号分隔）
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public String getStringsBack(String sql) {
		ResultSet rs = null;
		String backString = "";
		try {
			QueryUtil query = new QueryUtil();
			logger.info("sql :" + sql);
			rs = query.executeQuery(sql);
			while (rs.next()) {
				backString += rs.getString("i");
				backString += ",";
			}
			rs.close();
			return backString;
		} catch (Exception ex) {
			logger.error("依据SQL得到String型的返回值: " + ex.getMessage());
			return null;
		}
	}

	public void deleteWatchAttachRelationship(String watch_attach_id) {
		String sql = "delete watchinfo_attach where id='" + watch_attach_id
				+ "'";
		UpdateUtil update;
		try {
			update = new UpdateUtil();
			update.executeUpdate(sql);
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void updateWatchMMSPicState(String relateUrl,int state) {

		try {
			UpdateUtil update = new UpdateUtil();
			// 如果属于
			String sql2 = "update mms_watch set handlestate="+state+" where attach='"
					+ relateUrl + "'";
			logger.info(sql2);
			update.executeUpdate(sql2);
		} catch (Exception e) {
			logger.error(e);
		}

	}
}
