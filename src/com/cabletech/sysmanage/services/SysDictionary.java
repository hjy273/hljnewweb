package com.cabletech.sysmanage.services;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.commons.hb.QueryUtil;

public class SysDictionary {
	private Logger logger = Logger.getLogger("SysDictionary");
	/**
	 *<p>����:�ж�Ѳ����Ա�����ǰ��黹�ǰ��˽��й���,Ĭ���ǰ�����й���
	 *<p>����:
	 *<p>����:����ǰ��������1,���ǰ��������0
	 */
	@Transactional(readOnly=true)
	public String isManageByArry() {
		String sql = "select type from sysdictionary where lable='Ѳ����Ա����ʽ'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("�ж�Ѳ����Ա����ʽ�����쳣:" + e.getMessage());
			return "1";
		}
	}

	/**
	 *<p>����:�жϳ������Ƿ���ʾ������Ϣ,Ĭ������ʾ��.
	 * 		������¼�ǹ��Ƿ���ʾ������Ϣ������.��typeֵΪ"1"ʱ,��ʾ������Ϣ.
	 *		��typeֵΪ"0"��Ϊnullʱ,����ʾ������Ϣ
	 * *<p>����:
	 *<p>����:����ǰ��������1,���ǰ��������0
	 */
	public String isShowFIB() {
		String sql = "select type from sysdictionary where lable='�Ƿ���ʾ������Ϣ'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("�жϳ������Ƿ���ʾ������Ϣ�쳣:" + e.getMessage());
			return "1";
		}
	}

	/**
	 *<p>����:�жϳ��������µ�������������ʱ�Ƿ��Ŀ�괦���˻������˷��Ͷ���,Ĭ���Ƿ��͵�.
	 * 		��typeֵΪ"1"ʱ,��ʾ��Ŀ�괦���˻������˷��Ͷ���.
	 *		��typeֵΪ"0"��Ϊnullʱ,��֮.
	 *<p>����:
	 *<p>����:������ͷ���1,�������ͷ���0.
	 */
	public String isSendSM() {
		String sql = "select type from sysdictionary where lable='�Ƿ��Ŀ�괦���˷��Ͷ���'";
		String[][] rst;
		try {
			QueryUtil query = new QueryUtil();
			rst = query.executeQueryGetArray(sql, "1");
			return rst[0][0];
		} catch (Exception e) {
			logger.error("�ж����µ�������������ʱ�Ƿ��Ŀ�괦���˻������˷��Ͷ����쳣:" + e.getMessage());

			return "1";
		}
	}
}
