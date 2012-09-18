package com.cabletech.analysis.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.UserType;

public class WorkInfoHistoryCommon extends BaseBisinessObject {
	private WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();

	private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

	private String sql = "";

	/**
	 * �õ���ѯ��Χ�б� ʡ�ƶ��û�����ʾ��ȫʡ���򡱺͸�����;���ƶ��û�����ʾ�û������м���Ͻ����ά; �д�ά�û�:��ʾ������ά��˾�������ܸ�Ѳ����
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @return ���ز�ѯ��Χ�б�List���ͣ�����Ϊ��̬bean
	 */
	public List getRangeList(UserInfo userInfo) {
		sql = createRangeSql(userInfo);
		logger.info("��ѯ��ΧSQL:" + sql);
		List rangeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (rangeList == null) {
			logger.info("��ѯ��ΧrangeListΪ��");
		}
		return rangeList;
	}

	/**
	 * �����õ��û���Χ��SQL
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @return ���ع����õ��û���Χ��SQL
	 */
	public String createRangeSql(UserInfo userInfo) {
		String userType = userInfo.getType();
		if (UserType.PROVINCE.equals(userType)) { // ʡ�ƶ��û�
			sql = "select regionid rangeid, regionname rangename "
					+ "from region r " + "where r.state is null"
					+ " and substr (r.regionid, 3, 4) != '1111' "
					+ " and substr (r.regionid, 3, 4) != '0000' "
					+ "order by r.regionid";
		} else if (UserType.SECTION.equals(userType)) { // ���ƶ��û�
			sql = "select c.contractorid rangeid,c.contractorname rangename "
					+ "from contractorinfo c " + "where c.regionid = '"
					+ userInfo.getRegionid() + "'" + " and c.state is null "
					+ "order by c.contractorname";
		} else if (UserType.CONTRACTOR.equals(userType)) { // �д�ά�û�
			sql = "select p.patrolid rangeid,p.patrolname rangename "
					+ "from patrolmaninfo p,userinfo u "
					+ "where p.parentid = u.deptid" + " and u.userid = '"
					+ userInfo.getUserID() + "'" + " and p.state is null "
					+ "order by p.patrolname";
		}
		return sql;
	}
}
