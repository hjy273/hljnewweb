package com.cabletech.analysis.beans;

import java.util.Iterator;
import java.util.Map;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.base.BaseBean;
import com.cabletech.commons.config.GisConInfo;
import com.cabletech.commons.config.UserType;
import com.cabletech.commons.util.DateUtil;

/**
 * ѡ���������ڽ���������ͼ�ȵ����ʾ����Ϣ
 * 
 */
public class HistoryTimeInfoBean extends BaseBean {
	private String intersectPoint; // ʱ��㣬�磺6��00��00

	private String onlineNumber; // ��������

	private String smTotal; // ��������

	private Map onlineStatList; // ������Ϣ�б�

	private String onlineInfo; // ������Ϣ�����д�ά�û���¼ʱ�����ݿ����Ѵ���֯�õĽ����
    
	private GisConInfo config = GisConInfo.newInstance();

	private DateUtil dateUtil = new DateUtil();

	/**
	 * �õ�ʱ���
	 * 
	 * @return String
	 */
	public String getIntersectPoint() {
		return intersectPoint;
	}

	/**
	 * ����ʱ���
	 * @param intersectPoint
	 *            ʱ��㣬�磺06��00��00
	 */
	public void setIntersectPoint(String intersectPoint) {
		this.intersectPoint = intersectPoint;
	}
    
	/**
	 * 
	 * @param boParam  �����BO��������װ��
	 * @return interSectPoint
	 */
	public String getFinalIntersectPoint(HistoryWorkInfoCreateBOParam boParam) {
		String givenDateAndTime = boParam.getGivenDateAndTime();
		String endPoint = givenDateAndTime.substring(givenDateAndTime
				.indexOf(" ") + 1); // �õ���ֹʱ��ʱ����
		long spacingtime = 1000L * 60 * Integer.parseInt(config
				.getSpacingTime());
		long startDateAndTimeLong = dateUtil
				.strDateAndTimeToLong(boParam.getGivenDateAndTime())
				- spacingtime; // �õ���ʼʱ�������
		String startPoint = dateUtil.longTostrTime(startDateAndTimeLong,
				"HH:mm:ss"); // �õ���ʼʱ��ʱ����
		String interSectPoint = startPoint + "-" + endPoint;
        return interSectPoint;
	}
	
	/**
	 * �õ���������
	 * 
	 * @return String
	 */
	public String getOnlineNumber() {
		return onlineNumber;
	}

	/**
	 * ������������
	 * 
	 * @param onlineNumber
	 *            ��������
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	/**
	 * �õ�������Ϣ�����ڷ��д�ά�û���
	 * 
	 * @return Map
	 */
	public Map getOnlineStatList() {
		return onlineStatList;
	}

	/**
	 * ����������Ϣ�����ڷ��д�ά�û���
	 * 
	 * @param onlineStatList
	 *            ������Ϣ�б�
	 */
	public void setOnlineStatList(Map onlineStatList) {
		this.onlineStatList = onlineStatList;
	}

	/**
	 * �õ���������
	 * 
	 * @return String
	 */
	public String getSmTotal() {
		return smTotal;
	}

	/**
	 * ���ö�������
	 * 
	 * @param smTotal
	 *            ��������
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}

	/**
	 * �õ�������Ϣ�������д�ά�û���
	 * 
	 * @return String
	 */
	public String getOnlineInfo() {
		return onlineInfo;
	}

	/**
	 * ����������Ϣ�������д�ά�û���
	 * 
	 * @param onlineInfo
	 *            ������Ϣ�������ݿ���ȡ�������д�άʹ�ã��Ѿ���֯�ã�
	 */
	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

	/**
	 * ������֯�õ�����tooltip��ʾ��Ϣ
	 * 
	 * @param givenDate
	 *            ѡ��ʱ��
	 * @return String
	 */
	public String getBackString(UserInfo userInfo) {
		String backString = "��ѡʱ��:" + getIntersectPoint() + "<br>"
				+ "��������:" + getOnlineNumber() + "<br>" + "����������"
				+ getSmTotal() + "<br>";
		// ������д�ά�û�,��ѯ�����������Ѿ���֯���ˣ���������֯
		if (UserType.CONTRACTOR.equals(userInfo.getType())) {
			backString += "������Ա�б�:" + "<br>" + getOnlineInfo();
		} else { // �����д�ά����Ҫ��֯��������Ա�б�������Ϣ
			backString += "������Ա�б�:" + "<br>";
			Map map = getOnlineStatList();
			Iterator it = map.keySet().iterator();
			int i = 0; // ������ż����������ÿѭ�����μ���һ��<br>
			while (it.hasNext()) {
				i++;
				// key����Ϊregionname,����Ϊcontractorname
				String key = it.next().toString();
				String onlineNumber = (String) map.get(key); // ��������
				backString += key + ":" + onlineNumber + "��     ";
				if (i % 2 == 0) {
					backString += "<br>";
				}
			}
		}
		return backString;
	}

}
