package com.cabletech.analysis.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.CurrentTimeBean;
import com.cabletech.analysis.dao.RealTimeOnlineDAO;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * ����ʵʱ����������ѯ�������ṩʡ���С���ά�û���ѯ���Թ�Ͻ����Ĵ�ά��ԱѲ������
 * 
 * @author Administrator
 * 
 */
public class RealTimeOnlineBO {
	private Logger logger = Logger.getLogger("RealTimeOnlineService");
	private RealTimeOnlineDAO onlineDao;
	private DateUtil dateUtil;
	private GisConInfo config;
	private int spacingtime = 30; // Ѳ���Ƿ����ߵ���Сʱ����������С���Ϊ30���ӡ�
	private long interval = 0L;

	/**
	 * ��ʼ��onlineDao\config\dateUtil\spacingtime\interval
	 */
	public RealTimeOnlineBO() {
		onlineDao = new RealTimeOnlineDAO();
		config = GisConInfo.newInstance();
		dateUtil = new DateUtil();
		try{
			spacingtime = Integer.parseInt(config.getSpacingTime());
		}catch(NumberFormatException e){
			spacingtime = 30;	
		}
		interval = 1000L * 60 * spacingtime; // ˢ�¼��,��λ����
	}

	/**
	 * ��ѯ���е�������������,���������ѯ������� user:�û���Ϣ�����û���Ϣ��Ϊ��ѯ������
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @param regionid
	 *            ��ѯָ���������Ϊnull�����û���������Ϊ׼��
	 * @return Map ʱ���interval,������map����
	 */
	public Map getAllOnlineNum(UserInfo user, String regionid) {
		//logger.info("getAllOnlineNum11");
		String sql = "";
		if (regionid == null) {
			regionid = user.getRegionid();
		}
		//logger.info("getAllOnlineNum22");
		sql = "select count(simid) simid,interval1 from ( select distinct(simid) simid,interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, region n "
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and t.regionid=n.regionid"
				+ " and n.regionid in (SELECT regionid FROM region CONNECT BY PRIOR regionid=parentregionid START WITH regionid='"
				+ regionid
				+ "')) group by simid,interval1 ) )group by interval1 order by interval1";

		logger.info("��ѯ���е�������������" + sql);
		List list = onlineDao.queryOnlineNum(sql);

		return listToMap(list);
	}

	/**
	 * <lu>��ϲ�ѯ����<lu>
	 * @return String ���ص��ַ���Ϊsql�е�case...when ...else .. end ����
	 */
	private String constructCondition() {
		logger.info("constructCondition111");
		/* ȡ��Ѳ�쿪ʼ����ʱ�� */
		String startTime = config.getPatrolStartTime(); // ��ʽ"6:30:00";
		String endTime = config.getPatrolEndTime(); // ��ʽ"21:30:00";

		String sql = "case ";
		logger.info("constructCondition2222");
		long currentStartTime = dateUtil.strTimeToLong(startTime);
		long currentEndTime = dateUtil.strTimeToLong(endTime) + interval;
		logger.info("currentStartTime = "+dateUtil.longTostrTime(currentStartTime, "HH:mm:ss"));
		logger.info("currentEndTime = "+dateUtil.longTostrTime(currentEndTime, "HH:mm:ss"));
		logger.info("constructCondition3333");
		while (currentStartTime <= currentEndTime) {
			logger.info("currentStartTime || currentEndTime "+dateUtil.longTostrTime(currentStartTime, "HH:mm:ss") +" = "+ dateUtil.longTostrTime(currentEndTime, "HH:mm:ss"));
			sql += " when TO_CHAR(r.arrivetime,'HH24:mi') BETWEEN '"
					+ dateUtil.longTostrTime((currentStartTime - interval),
							"HH:mm:ss")
					+ "' AND '"
					+ dateUtil
							.longTostrTime((currentStartTime - 1), "HH:mm:ss")
					+ "' THEN '"
					+ dateUtil.longTostrTime(currentStartTime, "HH:mm:ss")
					+ "'";
			currentStartTime = currentStartTime + interval;
		}
		sql += " else 'other' end";
		logger.info("case when :"+sql);
		return sql;
	}

	/**
	 * list��ת��Ϊmap
	 * 
	 * @param simInterval
	 *            ������������,ʱ���interval��Ϣ��list��
	 * @return ����ʱ���interval,������map����
	 */
	private Map listToMap(List simInterval) {
		Map map = new HashMap();
		int size = simInterval.size();
		String key = "";
		String value = "";
		for (int i = 0; i < size; i++) {
			key = ((BasicDynaBean) simInterval.get(i)).get("interval1")
					.toString();
			value = ((BasicDynaBean) simInterval.get(i)).get("simid")
					.toString();
			map.put(key, new Integer(Integer.parseInt(value)));
		}
		return map;
	}

	/**
	 * �����ÿ��ʱ����ڲ��ظ���sim������,����������
	 * 
	 * @param simInterval
	 *            List ����simid,ʱ���interval��Ϣ��list,����Ҫ��list��ʱ��ν�������
	 * @return Map ����ʱ���interval,������map����
	 */
	private Map countOnlineNum(List simInterval) {
		Map map = new HashMap();
		int size = simInterval.size();
		String prevValue = "";
		String currentValue = "";
		int patrolNum = 0;
		for (int i = 0; i < size; i++) {
			currentValue = ((BasicDynaBean) simInterval.get(i))
					.get("interval1").toString();
			if (i == 0) {
				prevValue = ((BasicDynaBean) simInterval.get(i)).get(
						"interval1").toString();
			} else {
				prevValue = ((BasicDynaBean) simInterval.get(i - 1)).get(
						"interval1").toString();
			}
			if (!currentValue.equals(prevValue)) {
				map.put(prevValue, new Integer(patrolNum));
				patrolNum = 1;
			} else {
				patrolNum++;
			}
		}

		return map;
	}

	/**
	 * ��ȡָ�������Լ���ά��������Ա�� ���connidΪnull,��ô��ѯ�Ľ��Ǳ�����ÿʱ�����д�ά��λ����������
	 * ����map�����а���ʱ���,��ʱ�̵���������. ע���ṩ�����ƶ���ѯʹ��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @param connid
	 *            ��ѯָ����ά�����Ϊnull�����û���������Ϊ׼��
	 * @return Map ʱ���interval,������map����
	 */
	public Map getAreaOnlineNum(UserInfo user, String connid) {
		String sql = "select distinct(simid),interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, region n ,CONTRACTORINFO c"
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and t.regionid=n.regionid and c.contractorid = t.contractorid";
		if (connid == null) {
			sql += " and n.regionid ='" + user.getRegionid() + "'";
		} else {
			sql += " and c.contractorid = '" + connid + "'";
		}
		sql += " ) group by simid,interval1 ) order by interval1";
		logger.info("��ѯ���ڵ�������������" + sql);
		List list = onlineDao.queryOnlineNum(sql);

		return countOnlineNum(list);
	}

	/**
	 * ���ݵ�½�û�,�Լ�Ѳ������Ϣ��ѯѲ��Ա�������. ���Ѳ������ϢΪnull,��ô�����ر���λ����������.
	 * ��Ѳ������Ϣ��Ϊnull,����halfBin(List)����,��listת��λÿʱ�̵�����״̬.��:01110 111 return Map
	 * ����map�����а���ʱ���,��ʱ�̵���������. ע���ṩ����ά�û���ѯʹ��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @param patrolid
	 *            Ѳ����id
	 * @return Map ʱ���interval,������map����
	 */
	public Map getConOnlineNum(UserInfo user, String patrolid) {
		String sql = "select distinct(simid),interval1 from ( "
				+ "		select simid,interval1 from ( "
				+ "			select simid,to_char(arrivetime,'hh24:mi:ss') arrivetime,"
				+ constructCondition()
				+ " interval1 "
				+ "			from RECIEVEMSGLOG r ,terminalinfo t, CONTRACTORINFO c,patrolmaninfo p"
				+ "         where arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy/MM/dd")
				+ "','YYYY-MM-DD hh24:mi:ss')"
				+ " and t.simnumber = r.simid and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (patrolid == null) {
			sql += " and c.contractorid='" + user.getDeptID() + "'";
		} else {
			sql += " and p.patrolid='" + patrolid + "'";
		}
		sql += " ) group by simid,interval1 ) ";
		if (patrolid == null) {
			sql += " order by interval1";
		} else {
			sql += " order by simid,interval1";
		}
		logger.info("��ѯ��ά��Ѳ������������" + sql);
		List list = onlineDao.queryOnlineNum(sql);
		if (patrolid == null) {
			return countOnlineNum(list);
		} else {
			return halfBin(list);
		}
	}

	/**
	 * �����ݽ��д���listת��λÿʱ�̵�����״̬.��:01110111, ����Map
	 * 
	 * @param simidInterval
	 *            List ����simid,ʱ���interval��Ϣ��list,����Ҫʹlist��simid,ʱ��ν�������
	 * @return Map map�а�����simidΪ��ֵ��map���󣬸�map������
	 */
	private Map halfBin(List simidInterval) {
		int size = simidInterval.size();
		Map map = new HashMap();
		String prevValue = "";
		String currentValue = "";
		Map simMap = new HashMap();
		for (int i = 0; i < size; i++) {
			currentValue = ((BasicDynaBean) simidInterval.get(i)).get("simid")
					.toString();
			if (i == 0) {
				prevValue = ((BasicDynaBean) simidInterval.get(i)).get("simid")
						.toString();
			} else {
				prevValue = ((BasicDynaBean) simidInterval.get(i - 1)).get(
						"simid").toString();
			}
			// ��prevValue��currentValue����
			if (!prevValue.equals(currentValue)) {
				map.put(prevValue, simMap);
				simMap = new HashMap();
			} else {
				String interval = ((BasicDynaBean) simidInterval.get(i)).get(
						"interval1").toString();
				simMap.put(interval, new Integer(1));
			}
		}
		return map;
	}

	/**
	 * ��ȡĳʱ����ڵ�(����������ά��λ)��������Ա��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @param time
	 *            �鿴��ʱ���
	 * @param currentRegion
	 *            ��ǰ����id,��ѯ��ĳ������
	 * @param connid
	 *            ��ά��λid����ѯ��ĳ����ά��λ
	 * @return com.cabletech.analyse.beans.CurrentTimeBean
	 * @see com.cabletech.analysis.beans.CurrentTimeBean
	 */
	public CurrentTimeBean getSegmentOnlineNum(UserInfo user, String time,
			String currentRegion, String connid) {
		long intervalTime = dateUtil.strDateAndTimeToLong(time); // �鿴ʱ�����Ϣ
		logger.info("connid="+connid);
		CurrentTimeBean currentTime = new CurrentTimeBean();
		String sql = constructNumSql(user, currentRegion, connid, intervalTime);
		String noteNumSql = noteNumSql(user, currentRegion, connid,
				intervalTime);
		logger.info("sql : " + sql);
		List onlineNumList = onlineDao.queryOnlineNum(sql);
		if (onlineNumList != null) {
			logger.info("onlineNumList : " + onlineNumList.size());
		}
		currentTime.setIntersectPoint(dateUtil.longTostrTime(intervalTime
				- interval, "HH:mm:ss")
				+ "-" + dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss"));
		//statOnlineNum();// ͳ����������onlineNumList.size()
		if(UserType.CONTRACTOR.equals(user.getType())){
			currentTime.setOnlineNum(onlineNumList.size());
		}else{
			currentTime.setOnlineNum(statOnlineNum(onlineNumList,connid));
		}
		currentTime.setNumList(onlineNumList);
		//logger.info("sql_noteNum : " + noteNumSql);
		currentTime.setNoteNum(onlineDao.queryNoteNum(noteNumSql));
		return currentTime;
	}
	
	/**
	 * ����connid�Ƿ�Ϊ���ж����ͳ����������
	 * @param onlineNumList
	 * @param connid
	 * @return
	 */
	private int statOnlineNum(List onlineNumList, String connid) {
		int count = 0;
		logger.info("connid " +connid);
		if(connid != null && !"".equals(connid)){
			
			return onlineNumList.size();
		}else{
			int size = onlineNumList.size();
			for(int i=0;i<size;i++){
				BasicDynaBean dynaBean = (BasicDynaBean)onlineNumList.get(i);
				count += Integer.parseInt(dynaBean.get("count").toString());
			}
			return count;
		}
	}
	
	public static void main(String []args){
		List onlineNumList = new ArrayList(); 
		RealTimeOnlineBO b = new RealTimeOnlineBO();
		String connid = "11111";
		b.statOnlineNum(onlineNumList,connid);
	}
	/**
	 * ��������������ѯ���
	 * 
	 * @param user
	 *            ��¼�û���Ϣ
	 * @param currentRegion
	 *            ��ǰѡ������
	 * @param connid
	 *            ��ά��λid
	 * @param intervalTime
	 *            ���ʱ��
	 * @return String ���ز�ѯ���
	 */
	private String noteNumSql(UserInfo user, String currentRegion,
			String connid, long intervalTime) {
		String sql = "";
		String commonCondition = " where TO_CHAR(r.arrivetime,'hh24:mi') BETWEEN '"
				+ dateUtil.longTostrTime(intervalTime - interval, "HH:mm:ss")
				+ "' AND  '"
				+ dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss")
				+ "' "
				+ " and arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy-MM-dd")
				+ "','YYYY-MM-DD hh24:mi:ss') "
				+ " and t.simnumber = r.simid and t.regionid=n.regionid";
		String sql_province = " select  count(simid) num from recievemsglog r ,terminalinfo t,region n"
				+ commonCondition;
		String sql_section = "select  count(simid) num from recievemsglog r ,terminalinfo t,region n ,CONTRACTORINFO c "
				+ commonCondition + " and c.contractorid = t.contractorid ";
		String sql_contractor = "select count(simid) num  from recievemsglog r ,terminalinfo t,region n ,patrolmaninfo p ,CONTRACTORINFO c"
				+ commonCondition
				+ " and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (UserType.PROVINCE.equals(user.getType())) {
			if (currentRegion == null) {
				sql = sql_province;
			} else {
				sql = sql_section + " and n.regionid = '" + currentRegion + "'";
			}
		} else if (UserType.SECTION.equals(user.getType())) {
			if (connid == null && UserType.SECTION.equals(user.getType())) {
				sql = sql_section + " and n.regionid = '" + user.getRegionid()
						+ "'";
			} else {
				sql = sql_contractor + " and c.contractorid = '" + connid + "'";
			}
		} else {
			sql = sql_contractor + " and c.contractorid = '" + user.getDeptID()
					+ "'";
		}
		return sql;
	}

	/**
	 * ���ݵ�ǰ�û��Լ�����������ϲ�ѯָ��ʱ���������Ա��Ϣ��sql��
	 * 
	 * @param user
	 *            �û���Ϣ
	 * @param currentRegion
	 *            ��ǰ����id,��ѯ��ĳ������
	 * @param connid
	 *            ��ά��λid����ѯ��ĳ����ά��λ
	 * @param intervalTime
	 *            �鿴ʱ������Ϣ
	 * @return String ���ز�ѯָ��ʱ���������Ա��Ϣ��sql��
	 */
	private String constructNumSql(UserInfo user, String currentRegion,
			String connid, long intervalTime) {
		String sql = "";
		String commonCondition = " where TO_CHAR(r.arrivetime,'hh24:mi') BETWEEN '"
				+ dateUtil.longTostrTime(intervalTime - interval, "HH:mm:ss")
				+ "' AND  '"
				+ dateUtil.longTostrTime(intervalTime - 1, "HH:mm:ss")
				+ "' "
				+ " and arrivetime>to_date('"
				+ DateUtil.getNowDateString("yyyy-MM-dd")
				+ "','YYYY-MM-DD hh24:mi:ss') "
				+ " and t.simnumber = r.simid and t.regionid=n.regionid";

		String sql_province = "select count(simid) count,groupname from ( select distinct(simid),n.regionname groupname from recievemsglog r ,terminalinfo t,region n"
				+ commonCondition + " order by n.regionname";
		String sql_section = "select count(simid) count,groupname from ( select distinct(simid),c.contractorname groupname from recievemsglog r ,terminalinfo t,region n ,CONTRACTORINFO c "
				+ commonCondition + " and c.contractorid = t.contractorid ";
		String sql_contractor = "select distinct(simid) count,p.patrolname groupname from recievemsglog r ,terminalinfo t,region n ,patrolmaninfo p ,CONTRACTORINFO c"
				+ commonCondition
				+ " and c.contractorid = t.contractorid and t.ownerid=p.patrolid ";
		if (UserType.PROVINCE.equals(user.getType())) {
			if (currentRegion == null) {
				sql = sql_province + ")  group by groupname";
			} else {
				sql = sql_section + " and n.regionid = '" + currentRegion
						+ "' order by c.contractorname )  group by groupname";
			}
		} else if (UserType.SECTION.equals(user.getType())) {
			if (connid == null && UserType.SECTION.equals(user.getType())) {
				sql = sql_section + " and n.regionid = '" + user.getRegionid()
						+ "' order by c.contractorname )  group by groupname";
			} else {
				sql = sql_contractor + " and c.contractorid = '" + connid
						+ "' order by p.patrolname";
			}
		} else {
			sql = sql_contractor + " and c.contractorid = '" + user.getDeptID()
					+ "' order by p.patrolname";
		}
		return sql;
	}

	/**
	 * ��beanת��Ϊhtml�ı�����
	 * 
	 * @param bean
	 *            CurrentTimeBean
	 * @return html �ı��ַ�����
	 * @see com.cabletech.analysis.beans.CurrentTimeBean
	 */
	public String compagesHtmlText(CurrentTimeBean bean) {
		int size = bean.getNumList().size();
		String onlinerTemp = "";
		for (int i = 0; i < size; i++) {
			BasicDynaBean dynaBean = (BasicDynaBean) bean.getNumList().get(i);
			onlinerTemp += dynaBean.get("groupname") + " :"
					+ dynaBean.get("count") + "<br>";
		}
		String htmlString = "��ѡ����:"
				+ bean.getIntersectPoint()
				+ "<br>"
				+ "��������:"
				+ bean.getOnlineNum()
				+ "<br>"
				+ "����ִ��������"
				+ bean.getNoteNum()
				+ "<br>"
				+ "������Ա�б�:<br>"
				+ onlinerTemp;
				//+ " 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br> 123545552<br>";
		return htmlString;
	}

}
