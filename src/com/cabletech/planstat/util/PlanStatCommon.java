package com.cabletech.planstat.util;

import java.util.List;

import org.apache.log4j.Logger;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.planstat.dao.MonthlyStatCityMobileDAOImpl;
import com.cabletech.planstat.dao.PlanStatBaseDAO;

public class PlanStatCommon extends BaseBisinessObject {
	private PlanStatBaseDAO dao = new MonthlyStatCityMobileDAOImpl();

	private Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

	private String sql = "";

	/**
	 * �õ�����������Χ�б�
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @return ���ز�ѯ��Χ�б�List���ͣ�����Ϊ��̬bean
	 */
	public List getRegionList() {
		sql = createRegionSql();
		logger.info("��ѯ��ΧSQL:" + sql);
		List regionList = dao.queryInfo(sql);
		if (regionList == null) {
			logger.info("��ѯ��ΧRegionListΪ��");
		}
		return regionList;
	}

	/**
	 * �����õ������б��SQL
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @return ���ع����õ������б��SQL
	 */
	public String createRegionSql() {
		sql = "select regionid, regionname "
				+ "from region r " + "where r.state is null"
				+ " and substr (r.regionid, 3, 4) != '1111' "
				+ " and substr (r.regionid, 3, 4) != '0000' "
				+ "order by r.regionid";
		return sql;
	}
}