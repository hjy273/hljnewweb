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
	 * ͨ��sql����ѯ���ŷ�����Ϣ��
	 * 
	 * @param sql
	 *            String sql���
	 * @return ResultSet ���ؽ����
	 */
	public ResultSet queryNoteNum(String sql) {
		ResultSet rs = null;
		try {
			query = new QueryUtil();
			rs = query.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("����ʵʱ��ز�ѯ����" + e.getMessage());
			rs = null;
		}
		return rs;

	}
}
