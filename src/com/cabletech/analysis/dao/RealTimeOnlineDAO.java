package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;
/**
 * RealTimeOnlineDAO.java
 */
public class RealTimeOnlineDAO {
	private QueryUtil query;
	private Logger logger = Logger.getLogger("RealTimeOnlineDAO");

	/**
	 * ͨ��QueryUtil���ѯ�������������ص�list�а�������ʱ�䡢sim����
	 * 
	 * @param sql String sql���
	 * @return List ����list���
	 */
	public List queryOnlineNum(String sql) {
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

	// /**
	// * ��ѯ������Ա��ÿ��sim���ţ���ǰ�Ĺ���״̬ ���ؽ���а���ʱ��Σ���ʱ�̵�״̬
	// *
	// * @param sql
	// * @return Map
	// */
	// public Map queryPatrolOnline(String sql) {
	// return null;
	// }

	/**
	 * ���ݴ����sql��ѯ��������Ŀ��
	 * 
	 * @param sql String sql���
	 * @return int ���ض���������
	 */
	public int queryNoteNum(String sql) {
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
			logger.info("query �����ʼ��ʧ�ܣ�" + e);
			return num;
		}

	}
}
