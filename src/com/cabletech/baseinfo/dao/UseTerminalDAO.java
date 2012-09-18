package com.cabletech.baseinfo.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;
/**
 * UseTerminalDAO
 */
public class UseTerminalDAO {
	private QueryUtil query;
	private Logger logger = Logger.getLogger("UseTerminalDAO");
	/**
	 * 通过sql获得设备使用情况列表。
	 * @param sql sql语句
	 * @return list
	 */
	public List getUseTerminal(String sql) {
		List result = new ArrayList();
		try {
			query = new QueryUtil();
			result = query.queryBeans(sql);
			return result;
		} catch (Exception e) {
			logger.info("query 对象初始化失败：" + e);
			e.printStackTrace();
			return null;
		}
	}

}
