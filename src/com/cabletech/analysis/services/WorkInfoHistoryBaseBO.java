package com.cabletech.analysis.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryTimeInfoBean;
import com.cabletech.analysis.beans.HistoryDateInfoBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.analysis.dao.WorkInfoHistoryDAOImpl;
import com.cabletech.commons.base.BaseBisinessObject;

public abstract class WorkInfoHistoryBaseBO extends BaseBisinessObject {
	
	protected HistoryWorkInfoCreateBOParam boParam; //����Ĳ���������װ��
	
	protected String rangeID; //���߹���������ID

	protected Logger logger = Logger.getLogger(this.getClass().getName()); // ����logger�������;

	protected String sql = "";

	protected WorkInfoHistoryDAOImpl workInfoHistoryDAOImpl = new WorkInfoHistoryDAOImpl();

	/**
	 * �õ��û�������ֹ���ڷ�Χ��������������ͼ��
	 * @return �����û�������ֹ���ڷ�Χ��������������ͼ�б�Map����
	 */
	public Map getOnlineNumberForDays() {
		// ����SQL
		String sql = createOnlineNumberSql();
		logger.info("��ѯ��ѡ��ֹ��������������SQL:" + sql);
		List onlineNumberList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberList == null) {
			logger.info("��ѡ��ֹ���������������б�Ϊ��");
		}
		// ��Listת��ΪMap��
		return listToMap(onlineNumberList);
	}
    
	/**
	 * �����õ�������ʱ����ֹ��Χ��ÿ������������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public abstract String createOnlineNumberSql();

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
	 * �õ��û���ѡ���������ڸ���ʱ�ε�������������ͼ��
	 * @return �����û���ѡ���������ڸ���ʱ�ε�������������ͼ�б�Map����
	 */
	public Map getOnlineNumberForHours() {
		sql = createOnlineNumberForHoursSql();
		logger.info("��ѯ��ָ������������������������01ͼSQL:" + sql);
		List onlineNumberForHoursList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (onlineNumberForHoursList == null) {
			logger.info("ָ������������������������01ͼ�б�Ϊ��");
		}
		// ��Listת��ΪMap
		return listToMap(onlineNumberForHoursList);

	}
    
	/**
	 * �����õ�������ѡĳһ���ʱ�����������ֲ���SQL
	 * @return ��֯�õ�SQL
	 */
	public abstract String createOnlineNumberForHoursSql();
    
	/**
	 * �õ�����ĳһ���ToolTip��Ϣ
	 * @return HistoryDateInfoBean
	 */
	public HistoryDateInfoBean getOnlineInfoGivenDay() {
		sql = createOnlineInfoGivenDaySql(); // ��֯SQL
		logger.info("��ʾ�������ָ���������Ϣ������Ϣ��SQL:" + sql);
		List list = workInfoHistoryDAOImpl.queryInfo(sql);
		if (list == null) {
			logger.info("��ʾ�������ָ���������Ϣ�б�Ϊ��");
			return null;
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
	 * ����"��ʾ�������ָ��������Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public abstract String createOnlineInfoGivenDaySql();

	/**
	 * �õ�����ĳ��ʱ�ε�ToolTip��Ϣ
	 * 
	 * @param givenDateAndTime
	 *            ����ʱ�� �磺9:00:00,ʵ����Ӧѡ��08:30:00��09:00:00����Ϣ��������09:00:00)
	 * @param userInfo
	 *            �û�
	 * @return HisotryTimeInfoBean
	 */
	public HistoryTimeInfoBean getOnlineInfoGivenHour() {
		List listWillTurnToBean;
		sql = createOnlineInfoGivenHourSql(); // ��֯SQL
		logger.info("��ʾ�������ָ�������ʱ����ϢSQL:" + sql);
		listWillTurnToBean = workInfoHistoryDAOImpl.queryInfo(sql);
		if (listWillTurnToBean == null || listWillTurnToBean.size() == 0) {
			logger.info("��ʾ�������ָ�������ʱ����Ϣ�б�Ϊ��");
			return null;
		}
		HistoryTimeInfoBean backBean = new HistoryTimeInfoBean();
		backBean = organizeBean(listWillTurnToBean); // ��֯Bean
		return backBean;
	}   

	/**
	 * 
	 * @param list ����ļ���ת����HistoryTimeInfoBean��list���д�ά�û��������û���list�ڲ��ṹ��ͬ
	 * @return HistoryTimeInfoBean 
	 */
	public HistoryTimeInfoBean organizeBean(List list) {
		HistoryTimeInfoBean bean = new HistoryTimeInfoBean();
		setBackBeanValue(list, bean); //���÷���beanֵ���д�ά��BO������д�����������
		bean.setIntersectPoint(bean.getFinalIntersectPoint(boParam)); // ��ʱ��η�Χ��bean
		return bean;
	}
	
	/**
	 * ���÷���beanֵ����Ӧ�ڷ��д�ά�û���
	 * @param list ����ļ���ת����HistoryTimeInfoBean��list���д�ά�û��������û���list�ڲ��ṹ��ͬ
	 * @param bean �û������ϲ�ѯʱ���������bean
	 */
	public void setBackBeanValue(List list, HistoryTimeInfoBean bean) {
		int size = list.size();
		int smnum = 0;
		int onlinenum = 0;
		Map map = new HashMap();
		int smnumeachloop = 0;
		int onlinenumeachloop = 0;
		String rangename = "";
		for (int i = 0; i < size; i++) { // ͨ����ѭ�����õ�����������������������Ա����������Ϣ
			smnumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i)).get(
					"smtotalnum").toString());
			onlinenumeachloop = Integer.parseInt(((BasicDynaBean) list.get(i))
					.get("totalnumber").toString());
			rangename = ((BasicDynaBean) list.get(i)).get("rangename")
					.toString();
			smnum += smnumeachloop;
			onlinenum += onlinenumeachloop;
			map.put(rangename, String.valueOf(onlinenumeachloop));
		}
		bean.setOnlineStatList(map);
		bean.setSmTotal(String.valueOf(smnum)); // �ö���������bean
		bean.setOnlineNumber(String.valueOf(onlinenum)); // ����������������bean

	}

	/**
	 * ����"��ʾ�������ָ�������ʱ����Ϣ"��SQL
	 * @return ��֯�õ�SQL
	 */
	public abstract String createOnlineInfoGivenHourSql();

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
	public List getSMInfoAllType() {
		sql = createSMAllTypeSql();
		if ("0".equals(boParam.getGivenDate())) { // ���û��ѡ�����ĳ�죬��Ϊ����
			logger.info("��ֹ��������ʷ������ϢSQL:" + sql);
		} else {
			logger.info(boParam.getGivenDate() + "�����ʱ����ʷ������ϢSQL:" + sql);
		}
		List smAllTypeList = null;
		smAllTypeList = workInfoHistoryDAOImpl.queryInfo(sql);
		if (smAllTypeList == null) {
			if ("0".equals(boParam.getGivenDate())) { // ���û��ѡ�����ĳ�죬��Ϊ����
				logger.info("��ֹ��������ʷ������Ϣ�б�Ϊ��");
			} else {
				logger.info(boParam.getGivenDate() + "�����ʱ����ʷ������Ϣ�б�Ϊ��");
			}
		}
		return smAllTypeList;
	}
  
	/**
	 * �����õ��������Ͷ���SQL
	 * @return ��֯�õ�SQL
	 */
	public abstract String createSMAllTypeSql();

	/**
	 * ���ӷ�����ֻ��Ѳ����BO�����õ�����д���õ�SIM����һ���е�ĳ��ʱ���Ƿ����ߵ�01ͼ��������,����Map
	 * @return Ѳ����BO���෵��MAP����������null
	 */
	public Map getFinal01GraphicMap() {
		return null;
	}

	/**
	 * ���á���ʷ���ߡ����õ����յ�rangeID
	 * @param rangeID ��ID��ʵ��ID����ʡ�ƶ�ID�����ƶ�ID���д�ά��˾ID����Ѳ����ID
	 */
	public void setRangeID(String rangeID) {
		this.rangeID = rangeID;
	}

	/**
	 * ���ô�context���д����ķ�װ��Ĳ���
	 * @param boParam
	 */
	public void setBoParam(HistoryWorkInfoCreateBOParam boParam) {
		this.boParam = boParam;
	}
}
