package com.cabletech.exterior.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.BaseAnalysisDAOImpl;
import com.cabletech.commons.hb.QueryUtil;

public class WatchExeAnalysisDAOImpl extends BaseAnalysisDAOImpl {
	private QueryUtil query;
	private Logger logger = Logger.getLogger("WatchExeAnalysisDAOImpl");
	public int queryWatchInfoNum(String sql) {
		int num = 0;
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while (rs.next()) {
				num = rs.getInt("num");
			}
			return num;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return num;
		} catch (Exception e) {
			logger.info("query 对象初始化失败：" + e);
			return num;
		}

	}
}

