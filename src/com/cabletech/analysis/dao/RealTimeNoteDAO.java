package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.cabletech.commons.hb.QueryUtil;

/**
 * RealTimeNoteDAO
 */
public class RealTimeNoteDAO {
	private QueryUtil query;
	private Logger logger = Logger.getLogger("RealTimeNoteDAO");

	/**
	 * 通过sql语句查询短信发送信息。
	 * 
	 * @param sql
	 *            String sql语句
	 * @return ResultSet 返回结果集
	 */
	public ResultSet queryNoteNum(String sql) {
		ResultSet rs = null;
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("短信实时监控查询错误：" + e.getMessage());
			rs = null;
		}
		return rs;

	}
}
