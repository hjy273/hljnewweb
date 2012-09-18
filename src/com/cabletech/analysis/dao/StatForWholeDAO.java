package com.cabletech.analysis.dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.commons.hb.QueryUtil;

/**
 * ȫʡѲ��ƻ���ͳ��
 */
public class StatForWholeDAO {
	private Logger logger = Logger.getLogger("StatForWholeDAO");
	private QueryUtil query = null;
	/**
	 * �������װΪ��̬bean,��������\������
	 * 
	 * @param sql
	 * @return List
	 */
	public List queryBeans(String sql) {
		try {
			query = new QueryUtil();
			return query.queryBeans(sql);
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("���ݿ������쳣��"+e.getMessage());
			return null;
		}
	}

	/**
	 * ��ѯ�ϸ��������벻�ϸ���
	 * �ּ�����ʾ5�������������
	 * 
	 * @param sql
	 * @return ResultSet
	 */
	public ResultSet queryCheckOutRate(String sql) {
		try {
			query = new QueryUtil();
			return query.executeQuery(sql);
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("���ݿ������쳣��"+e.getMessage());
			return null;
		}
	}

	/**
	 * ��ѯѲ���߶�����
	 * 
	 * @param sql
	 * @return Integer
	 */
	public Integer countSublineNum(String sql) {
		Integer num = new Integer(0);
		try {
			query = new QueryUtil();
			ResultSet rs = query.executeQuery(sql);
			while(rs.next()){
				num = new Integer(rs.getInt("num"));
			}
		} catch (Exception e) {
			query = null;
			e.printStackTrace();
			logger.info("���ݿ������쳣��"+e.getMessage());
			
		}
		return num;
	}
}
