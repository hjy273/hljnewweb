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
	 * ͨ��sql����豸ʹ������б�
	 * @param sql sql���
	 * @return list
	 */
	public List getUseTerminal(String sql) {
		List result = new ArrayList();
		try {
			query = new QueryUtil();
			result = query.queryBeans(sql);
			return result;
		} catch (Exception e) {
			logger.info("query �����ʼ��ʧ�ܣ�" + e);
			e.printStackTrace();
			return null;
		}
	}

}
