package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBisinessObject;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * ����ΪѲ����̷�����ҵ���߼�������
 */
public class WorkInfoHistoryBO extends BaseBisinessObject {
	private Logger logger;

	private String sql = "";

	private WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl;

	private GisConInfo config = GisConInfo.newInstance();

	private DateUtil dateUtil = new DateUtil();

	/**
	 * ���췽��
	 * 
	 */
	public WorkInfoHistoryBO() {
		workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();
		// ����logger�������
		logger = Logger.getLogger(this.getClass().getName());
	}



	/**
	 * �õ��û�������ֹ���ڷ�Χ��������������ͼ��
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @return �����û�������ֹ���ڷ�Χ��������������ͼ�б�Map����
	 */
	public Map getOnlineNumberForDays(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo) {
		// ����SQL
		sql = createOnlineNumberSql(bean, userInfo);
		logger.info("��ѯ��ѡ��ֹ��������������SQL:" + sql);
		List onlineNumberList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberList == null) {
			logger.info("��ѡ��ֹ���������������б�Ϊ��");
		}
		// ��Listת��ΪMap��
		return listToMap(onlineNumberList);
	}

	/**
	 * �õ��û���ѡ���������ڸ���ʱ�ε�������������ͼ��
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @param givenDate
	 *            �û���ѡ�������ڡ�
	 * @return �����û���ѡ���������ڸ���ʱ�ε�������������ͼ�б�Map����
	 */
	public Map getOnlineNumberForHours(String givenDate, UserInfo userInfo,
			HistoryWorkInfoConditionBean bean) {
		sql = createOnlineNumberForHoursSql(givenDate, userInfo, bean);
		logger.info("��ѯ��ָ������������������������01ͼSQL:" + sql);
		List onlineNumberForHoursList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberForHoursList == null) {
			logger.info("ָ������������������������01ͼ�б�Ϊ��");
		}
		// ������д�ά��¼��ѡ���˾����Ѳ���飬��ô������01ͼ
		if (UserType.CONTRACTOR.equals(userInfo.getType())
				&& (!"22".equals(bean.getRangeID()))) {
			return getFinal01GraphicMap(onlineNumberForHoursList);
		}
		// ��Listת��ΪMap
		return listToMap(onlineNumberForHoursList);
	}

	/**
	 * �õ�����ĳһ���ToolTip��Ϣ
	 * 
	 * @param givenDate
	 *            �������� �磺2007-11-11
	 * @param userInfo
	 *            �û�
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @return HistoryDateInfoBean
	 */
	public HistoryDateInfoBean getOnlineInfoGivenDay(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		sql = createOnlineInfoGivenDaySql(givenDate, userInfo, bean); // ��֯SQL
		logger.info("��ʾ�������ָ���������Ϣ������Ϣ��SQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("��ʾ�������ָ���������Ϣ�б�Ϊ��");
		}
		HistoryDateInfoBean backBean = new HistoryDateInfoBean();
		// ��֯����bean,��һͳ�ƶ���һ��ֻ��Ψһһ����¼
		backBean.setSmTotal(((BasicDynaBean) list.get(0)).get("totalnum")
				.toString());
		backBean.setOnlineNumber(((BasicDynaBean) list.get(0)).get(
				"onlinenumber").toString());
		backBean.setDistanceTotal(((BasicDynaBean) list.get(0)).get("dailykm")
				.toString());
		return backBean;
	}

	/**
	 * �õ�����ĳ��ʱ�ε�ToolTip��Ϣ
	 * 
	 * @param givenDateAndTime
	 *            ����ʱ�� �磺9:00:00,ʵ����Ӧѡ��08:30:00��09:00:00����Ϣ��������09:00:00)
	 * @param userInfo
	 *            �û�
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean getOnlineInfoGivenHour(String givenDateAndTime,
			UserInfo userInfo) {
		sql = createOnlineInfoGivenHourSql(givenDateAndTime, userInfo); // ��֯SQL
		logger.info("��ʾ�������ָ�������ʱ����ϢSQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("��ʾ�������ָ�������ʱ����Ϣ�б�Ϊ��");
		}
		HistoryTimeInfoBean backBean = new HistoryTimeInfoBean();
		backBean = organizeBean(list, userInfo, givenDateAndTime); // ��֯Bean
		return backBean;
	}

	/**
	 * �õ�������Ϣ�����Ż��ܺͶ���ͼ����ʹ��֮��
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @param givenDate
	 *            �û���ѡ�������ڡ�
	 * @param smRangeID
	 *            ʡ�ƶ���¼����ֵҪôΪ��11",ҪôΪô����ID�����ƶ���¼����ֵҪôΪ��21",ҪôΪ���и���ά��˾ID��
	 *            �д�ά��¼����ֵҪôΪ��22",ҪôΪ�û�������ά��˾�µ�Ѳ����ID��
	 * @return ���ض�����Ϣ�б�List����
	 */
	public List getSMInfoAllType(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo, String smRangeID, String givenDate) {
		sql = createSMAllTypeSql(bean, userInfo, smRangeID, givenDate);
		if ("0".equals(givenDate)) { // ���û��ѡ�����ĳ�죬��Ϊ����
			logger.info("��ֹ��������ʷ������ϢSQL:" + sql);
		} else {
			logger.info(givenDate + "�����ʱ����ʷ������ϢSQL:" + sql);
		}
		List smAllTypeList = null;
		smAllTypeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (smAllTypeList == null) {
			if ("0".equals(givenDate)) { // ���û��ѡ�����ĳ�죬��Ϊ����
				logger.info("��ֹ��������ʷ������Ϣ�б�Ϊ��");
			} else {
				logger.info(givenDate + "�����ʱ����ʷ������Ϣ�б�Ϊ��");
			}
		}
		return smAllTypeList;
	}



	/**
	 * �����õ�������ѡĳһ���ʱ�����������ֲ���SQL
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @param givenDate
	 *            �û���ѡ�������ڡ�
	 * @return ���ع����õ��û���Χ��SQL
	 */
	public String createOnlineNumberForHoursSql(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		String userType = userInfo.getType();
		String rangeID = bean.getRangeID();
		String sql = "";
		sql = "Select TO_CHAR(so.intersectpoint,"
				+ "'yyyy-mm-dd hh24:mi:ss') operatedate,"
				+ "SUM(so.onlinenumber) onlinenumber "
				+ "from stat_onlinenumber so "
				+ "where (so.intersectpoint between to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') " + "and to_date('" + givenDate
				+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) ";
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			// �����ѡ��Χ���ǡ�ȫʡ��Χ������Ϊ�������ID
			if (!"11".equals(rangeID)) {
				sql += "and so.regionid = '" + rangeID + "' ";
			}
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			// �����ѡ��Χ�����û����ڵ��У���Ϊ�����ά��˾ID
			if (!"12".equals(rangeID)) {
				sql += "and so.contractorid = '" + rangeID + "' ";
			} else {
				sql += "and so.regionid = '" + userInfo.getRegionid() + "' ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			// �����ѡ��Χ���û����ڴ�ά��˾
			if ("22".equals(rangeID)) {
				sql += "and so.contractorid = '" + userInfo.getDeptID() + "' ";
			} else {
				sql = "select s.simid," + "to_char(s.firstsmreceivetime,"
						+ "'hh24:mi:ss') firstsmreceivetime ," + "s.flag "
						+ "from stat_onlinestatus s,"
						+ "patrolmaninfo p,terminalinfo t "
						+ "where s.simid = t.simnumber "
						+ "and p.patrolid = t.ownerid "
						+ "and p.state is null "
						+ "and (s.firstsmreceivetime between to_date('"
						+ givenDate + "', 'yyyy-mm-dd hh24:mi:ss') "
						+ "and to_date('" + givenDate
						+ "', 'yyyy-mm-dd hh24:mi:ss') + 1) "
						+ "and p.patrolid = '" + rangeID + "' "
						+ "order by s.simid";
				return sql;
			}
		}
		sql += "group by intersectpoint order by operatedate";
		return sql;
	}

	/**
	 * �����õ�������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @return ����������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 */
	public String createOnlineNumberSql(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo) {
		String userType = userInfo.getType();
		String sql = "";
		String rangeID = bean.getRangeID();
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		sql = "select to_char(s.operatedate, 'YYYY-MM-DD') operatedate,"
				+ "sum(s.onlinenumber) onlinenumber ";
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			sql += "from stat_regionmessagedaily s where ";
			if (!"11".equals(rangeID)) { // �����ѡ��Χ���ǡ�ȫʡ��Χ������Ϊ�������ID
				sql += "s.regionid = '" + rangeID + "' and ";
			}
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			// �����ѡ��ΧΪ�û����ڵ���
			if ("12".equals(rangeID)) {
				sql += "from stat_regionmessagedaily s where ";
				sql += "s.regionid = '" + userInfo.getRegionid() + "' and ";
			} else {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + rangeID + "' and ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			// �����ѡ��ΧΪ�û����ڴ�ά��˾
			if ("22".equals(rangeID)) {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + userInfo.getDeptID()
						+ "' and ";
			} else {
				sql += "from stat_messagedaily s ";
				sql += "where s.patrolmanid = '" + rangeID + "' and ";
			}
		}
		sql += "s.operatedate >=TO_DATE ('" + startDate + "', 'YYYY-MM-DD') "
				+ "and s.operatedate <=TO_DATE ('" + endDate
				+ "', 'YYYY-MM-DD') " + "group by s.operatedate "
				+ "order by operatedate";
		return sql;
	}

	/**
	 * �����õ��������Ͷ���SQL
	 * 
	 * @param userInfo
	 *            �û���Ϣ��
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @param givenDate
	 *            �û���ѡ�������ڡ�
	 * @param smRangeID
	 *            ʡ�ƶ���¼����ֵҪôΪ��11",ҪôΪô����ID�����ƶ���¼����ֵҪôΪ��21",ҪôΪ���и���ά��˾ID��
	 *            �д�ά��¼����ֵҪôΪ��22",ҪôΪ�û�������ά��˾�µ�Ѳ����ID��
	 * @return ���صõ��������Ͷ���SQL
	 */
	public String createSMAllTypeSql(HistoryWorkInfoConditionBean bean,
			UserInfo userInfo, String smRangeID, String givenDate) {
		String userType = userInfo.getType();
		String sql = "";
		String startDate = "";
		String endDate = "";
		if (bean != null) {
			startDate = bean.getStartDate(); // �õ��û��������ʼ����
			endDate = bean.getEndDate(); // �õ��û��������ֹ����
		}
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			if ("11".equals(smRangeID)) { // �����ѡ��Χ�ǡ�ȫʡ��Χ��
				sql = "SELECT sr.regionid rangeid,r.regionname rangename,"
						+ "SUM(sr.totalnum) totalnum,"
						+ "SUM(sr.patrolnum) patrolnum,"
						+ "SUM(sr.watchnum) watchnum,"
						+ "SUM(sr.troublenum) troublenum,"
						+ "SUM(sr.collectnum) collectnum,"
						+ "SUM(sr.othernum) othernum, SUM(sr.dailykm) dailykm "
						+ "FROM STAT_REGIONMESSAGEDAILY sr, REGION r "
						+ "WHERE sr.regionid = r.regionid  ";
				// ����û�ѡ���˾���ĳһ��
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sr.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sr.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sr.regionid,r.regionname "
						+ "ORDER BY r.regionname";
			} else { // �����ѡ��ΧΪ�������
				sql = "SELECT sc.contractorid rangeid,"
						+ "con.contractorname rangename,"
						+ "SUM(sc.totalnum) totalnum,"
						+ "SUM(sc.patrolnum) patrolnum,"
						+ "SUM(sc.watchnum) watchnum, "
						+ "SUM(sc.troublenum) troublenum,"
						+ "SUM(sc.collectnum) collectnum,"
						+ "SUM(sc.othernum) othernum,"
						+ "SUM(sc.dailykm) dailykm "
						+ "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con "
						+ "WHERE sc.contractorid = con.contractorid "
						+ "AND con.regionid = '" + smRangeID + "' ";
				// ����û�ѡ���˾���ĳһ��
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sc.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sc.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sc.contractorid,con.contractorname "
						+ "ORDER BY con.contractorname";
			}
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			if ("12".equals(smRangeID)) { // �����ѡ��Χ���û����ڵ���
				sql = "SELECT sc.contractorid rangeid,"
						+ "con.contractorname rangename,"
						+ "SUM(sc.dailykm) dailykm,SUM(sc.totalnum) totalnum,"
						+ "SUM(sc.patrolnum) patrolnum,"
						+ "SUM(sc.watchnum) watchnum,"
						+ "SUM(sc.collectnum) collectnum,"
						+ "SUM(sc.troublenum) troublenum,"
						+ "SUM(sc.othernum) othernum "
						+ "FROM STAT_CONMESSAGEDAILY sc, CONTRACTORINFO con "
						+ "WHERE sc.contractorid = con.contractorid "
						+ "AND con.regionid = '" + userInfo.getRegionid()
						+ "' ";
				// ����û�ѡ���˾���ĳһ��
				if (bean == null && givenDate != null 
						&& !"".equals(givenDate)) {
					sql += "AND sc.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (sc.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY sc.contractorid,con.contractorname "
						+ "ORDER BY con.contractorname";
			} else { // �����ѡ��Χ�Ǿ���Ĵ�ά��˾ID
				sql = "select p.patrolname rangename,t.simid,"
						+ "SUM(t.totalnum) totalnum,"
						+ "SUM(t.patrolnum) patrolnum,"
						+ "SUM(t.troublenum) troublenum,"
						+ "SUM(t.collectnum) collectnum,"
						+ "SUM(t.watchnum) watchnum,"
						+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
						+ "sum(t.avgsendtime) avgsendtime,"
						+ "sum(t.avgsenddistance) avgsenddistance "
						+ "from stat_messagedaily t, patrolmaninfo p "
						+ "where p.patrolid = t.patrolmanid "
						+ "and p.parentid = '" + smRangeID
						+ "' and p.state is null ";
				// ����û�ѡ���˾���ĳһ��
				if (bean == null && givenDate != null
						&& !"".equals(givenDate)) {
					sql += "AND t.operatedate = TO_DATE ('" + givenDate
							+ "', 'yyyy-mm-dd') ";
				} else {
					sql += "AND (t.operatedate between TO_DATE ('" + startDate
							+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
							+ "', 'yyyy-mm-dd')) ";
				}
				sql += "GROUP BY t.simid,p.patrolname "
						+ "order by p.patrolname, t.simid";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			sql = "select p.patrolname rangename,t.simid,"
					+ "SUM(t.totalnum) totalnum,"
					+ "SUM(t.patrolnum) patrolnum,"
					+ "SUM(t.troublenum) troublenum,"
					+ "SUM(t.collectnum) collectnum,"
					+ "SUM(t.watchnum) watchnum,"
					+ "SUM(t.othernum) othernum,SUM(t.dailykm) dailykm,"
					+ "sum(t.avgsendtime) avgsendtime,"
					+ "sum(t.avgsenddistance) avgsenddistance "
					+ "from stat_messagedaily t, patrolmaninfo p "
					+ "where p.patrolid = t.patrolmanid and p.state is null ";
			// ���ѡ���˾���ĳһ��
			if (bean == null && givenDate != null && !"".equals(givenDate)) {
				sql += "AND t.operatedate = TO_DATE ('" + givenDate
						+ "', 'yyyy-mm-dd') ";
			} else {
				sql += "AND (t.operatedate between TO_DATE ('" + startDate
						+ "', 'yyyy-mm-dd') and " + "TO_DATE ('" + endDate
						+ "', 'yyyy-mm-dd')) ";
			}
			if ("22".equals(smRangeID)) { // �����ѡ��Χ���û����ڴ�ά��˾
				sql += "and p.parentid = '" + userInfo.getDeptID() + "' ";
			} else { // �����ѡ��Χ�Ǿ���Ѳ��ά����
				sql += "and p.patrolid = '" + smRangeID + "' ";
			}
			sql += "GROUP BY t.simid,p.patrolname "
					+ "order by p.patrolname, t.simid";
		}
		return sql;
	}

	/**
	 * list��ת��Ϊmap
	 * 
	 * @param onlineNumberList
	 *            ������������,ʱ��Σ������ڣ�interval��Ϣ��list��
	 * @return ����ʱ��Σ������ڣ�interval,������map����
	 */
	private Map listToMap(List onlineNumberList) {
		Map map = new HashMap();
		int size = onlineNumberList.size();
		String key = "";
		String value = "";
		for (int i = 0; i < size; i++) {
			// ʱ��Σ������ڣ�
			key = ((BasicDynaBean) onlineNumberList.get(i)).get("operatedate")
					.toString();
			value = ((BasicDynaBean) onlineNumberList.get(i)).get(
					"onlinenumber")// ��������
					.toString();
			map.put(key, new Integer(Integer.parseInt(value)));
		}
		return map;
	}

	/**
	 * �õ�SIM����һ���е�ĳ��ʱ���Ƿ����ߵ�01ͼ��������,����Map
	 * 
	 * @param zeroOneList
	 *            01ͼ�б�����ÿ��SIM������Ӧ�ľ���ĳ������߱�־����λ��
	 * @return Map
	 */
	private Map getFinal01GraphicMap(List zeroOneList) {
		/* ȡ��Ѳ�쿪ʼ����ʱ�� */
		Map map = new HashMap();
		int size = zeroOneList.size();
		String key = "";
		List flagList;
		for (int i = 0; i < size; i++) {
			key = ((BasicDynaBean) zeroOneList.get(i)).get("simid").toString();
			flagList = new ArrayList();
			String flag = ((BasicDynaBean) zeroOneList.get(i)).get("flag")
					.toString(); // һ���и���ʱ�ε����߱�־����λ��
			for (int j = 0; j < flag.length(); j++) {
				// ��ÿһ����־ȡ������list
				flagList.add(new Integer(String.valueOf(flag.charAt(j))));
			}
			map.put(key, flagList); // sim��������Ӧ��ʱ�α�־λ����map
		}
		return map;
	}

	/**
	 * ����"��ʾ�������ָ��������Ϣ"��SQL
	 * 
	 * @param givenDate
	 *            ��������
	 * @param userInfo
	 *            �û�
	 * @param bean
	 *            ��ѯ������������bean,�����ѯʱ�û��������Ϣ��
	 * @return String
	 */
	public String createOnlineInfoGivenDaySql(String givenDate,
			UserInfo userInfo, HistoryWorkInfoConditionBean bean) {
		String userType = userInfo.getType();
		String rangeID = bean.getRangeID();
		String sql = "";
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			sql = "select sum(s.totalnum) totalnum,"
					+ "sum(s.onlinenumber) onlinenumber,"
					+ "sum(s.dailykm) dailykm "
					+ "from stat_regionmessagedaily s where ";
			if (!"11".equals(rangeID)) { // �����ѡ��Χ���ǡ�ȫʡ��Χ������Ϊ�������ID
				sql += "s.regionid = '" + rangeID + "' and ";
			}
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			sql = "select  s.totalnum,s.onlinenumber,s.dailykm ";
			// �����ѡ��ΧΪ�û����ڵ���
			if ("12".equals(rangeID)) {
				sql += "from stat_regionmessagedaily s where ";
				sql += "s.regionid = '" + userInfo.getRegionid() + "' and ";
			} else {
				sql += "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + rangeID + "' and ";
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			// �����ѡ��ΧΪ�û����ڴ�ά��˾
			if ("22".equals(rangeID)) {
				sql = "select  s.totalnum,s.onlinenumber,s.dailykm "
						+ "from stat_conmessagedaily s ";
				sql += "where s.contractorid = '" + userInfo.getDeptID()
						+ "' and ";
			} else { // ���ѡ���˾���Ѳ���飬����Ҫ������ͣ���Ϊһ����������ܶ�Ӧ���SIM��
				sql = "select  sum(s.totalnum) totalnum,"
						+ "sum(s.onlinenumber) onlinenumber,"
						+ "sum(s.dailykm) dailykm "
						+ "from stat_messagedaily s "
						+ "where s.patrolmanid = '" + rangeID + "' and "
						+ "s.operatedate = TO_DATE ('" + givenDate
						+ "', 'YYYY-MM-DD') " + "group by s.patrolmanid ";
				return sql;
			}
		}
		sql += "s.operatedate = TO_DATE ('" + givenDate + "', 'YYYY-MM-DD')";
		return sql;
	}

	/**
	 * ����"��ʾ�������ָ�������ʱ����Ϣ"��SQL
	 * 
	 * @param givenDateAndTime
	 *            ����ʱ�� �磺9:00:00,ʵ����Ӧѡ��08:30:00��09:00:00����Ϣ��������09:00:00)
	 * @param userInfo
	 *            �û�
	 * @return String
	 */
	public String createOnlineInfoGivenHourSql(String givenDateAndTime,
			UserInfo userInfo) {
		String userType = userInfo.getType();
		String sql = "";
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			// ��ʡ�ƶ��û�ѡ��ȫʡ����ʱ���Ż����µ�URL���ӣ�Ҳ�Ͳ��д˹���
			sql += "select so.regionid,r.regionname rangename,so.smtotalnum,"
					+ "sum(so.onlinenumber) totalnumber "
					+ "from STAT_ONLINENUMBER so,region r "
					+ "where so.intersectpoint = to_date('" + givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') "
					+ "and so.regionid = r.regionid and r.state is null "
					+ "and substr (r.regionid, 3, 4) != '1111' "
					+ "and substr (r.regionid, 3, 4) != '0000' "
					+ "group by so.regionid,so.smtotalnum,r.regionname "
					+ "order by so.regionid";
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			// �����ƶ��û�ѡ�����ڵ���ʱ���Ż����µ�URL���ӣ�Ҳ�Ͳ��д˹���
			sql += "select so.contractorid,c.contractorname rangename,"
					+ "so.smtotalnum,"
					+ "sum(so.onlinenumber) totalnumber "
					+ "from STAT_ONLINENUMBER so,contractorinfo c "
					+ "where so.intersectpoint = to_date('"
					+ givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') "
					+ "and so.regionid = '"
					+ userInfo.getRegionid()
					+ "' "
					+ "and so.contractorid = c.contractorid "
					+ "and c.state is null "
					+ "group by so.contractorid,so.smtotalnum,c.contractorname "
					+ "order by so.contractorid";
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			// //���д�ά�û�ѡ�����ڴ�ά��˾ʱ���Ż����µ�URL���ӣ�Ҳ�Ͳ��д˹���
			sql += "select so.smtotalnum,so.onlinenumber,so.onlinesiminfo "
					+ "from STAT_ONLINENUMBER so "
					+ "where so.intersectpoint = to_date('" + givenDateAndTime
					+ "','yyyy-mm-dd hh24:mi:ss') " + "and so.regionid = '"
					+ userInfo.getRegionid() + "' " + "and so.contractorid='"
					+ userInfo.getDeptID() + "'";
		}
		WorkInfoHistoryBaseBO bo = new WorkInfoHistoryBOCityContractor();
		bo.organizeBean(null);
		return sql;
	}

	/**
	 * �������list��֯����Ҫ��HisotryTimeInfoBean
	 * 
	 * @param list
	 *            ��ѯ���õİ���������������������������������Ϣ���б�
	 * @param userInfo
	 *            �û�
	 * @param givenDateAndTime
	 *            ����ʱ�� �磺9:00:00,ʵ����Ӧѡ��08:30:00��09:00:00����Ϣ��������09:00:00)
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean organizeBean(List list, UserInfo userInfo,
			String givenDateAndTime) {
		HistoryTimeInfoBean bean = new HistoryTimeInfoBean();
		String userType = userInfo.getType();
		int size = list.size();
		int smnum = 0;
		int onlinenum = 0;
		Map map = new HashMap();
		int smnumeachloop = 0;
		int onlinenumeachloop = 0;
		String regionanme = "";
		String contractorname = "";
		if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
			for (int i = 0; i < size; i++) { // ͨ����ѭ�����õ�����������������������Ա����������Ϣ
				smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("smtotalnum").toString());
				onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list
						.get(i)).get("totalnumber").toString());
				regionanme = ((BasicDynaBean) list.get(i)).get("rangename")
						.toString();
				smnum += smnumeachloop;
				onlinenum += onlinenumeachloop;
				map.put(regionanme, String.valueOf(onlinenumeachloop));
			}
			bean.setOnlineStatList(map);
		} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
			for (int i = 0; i < size; i++) { // ͨ����ѭ�����õ�����������������������Ա����������Ϣ
				smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
						.get("smtotalnum").toString());
				onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list
						.get(i)).get("totalnumber").toString());
				contractorname = ((BasicDynaBean) list.get(i)).get("rangename")
						.toString();
				smnum += smnumeachloop;
				onlinenum += onlinenumeachloop;
				map.put(contractorname, String.valueOf(onlinenumeachloop));
			}
			bean.setOnlineStatList(map);
		} else if (UserType.CONTRACTOR.equals(userType)) { // ������д�ά�û�
			smnum = Integer.parseInt(((BasicDynaBean) list.get(0)).get(
					"smtotalnum").toString());
			onlinenum = Integer.parseInt(((BasicDynaBean) list.get(0)).get(
					"onlinenumber").toString());
			String onlineInfo = ((BasicDynaBean) list.get(0)).get(
					"onlinesiminfo").toString();
			bean.setOnlineInfo(onlineInfo);
		}
		bean.setSmTotal(String.valueOf(smnum)); // �ö���������bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // ����������������bean
		String endPoint = givenDateAndTime.substring(givenDateAndTime
				.indexOf(" ") + 1); // �õ���ֹʱ��ʱ����
		long spacingtime = 1000L * 60 * Integer.parseInt(config
				.getSpacingTime());
		long startDateAndTimeLong = dateUtil
				.strDateAndTimeToLong(givenDateAndTime)
				- spacingtime; // �õ���ʼʱ�������
		String startPoint = dateUtil.longTostrTime(startDateAndTimeLong,
				"HH:mm:ss"); // �õ���ʼʱ��ʱ����
		String interSectPoint = startPoint + "-" + endPoint;
		bean.setIntersectPoint(interSectPoint); // ��ʱ��η�Χ��bean
		return bean;
	}
}
