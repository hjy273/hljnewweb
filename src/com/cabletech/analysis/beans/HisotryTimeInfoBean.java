package com.cabletech.analysis.beans;

import java.util.Map;

import com.cabletech.commons.base.BaseBean;

/**
 * ѡ���������ڽ���������ͼ�ȵ����ʾ����Ϣ
 * 
 */
public class HisotryTimeInfoBean extends BaseBean {
	private String intersectPoint; // ʱ��㣬�磺6��00��00

	private String onlineNumber; // ��������

	private String smTotal; // ��������

	private Map onlineStatList; // ������Ϣ�б�

	private String onlineInfo; // ������Ϣ�����д�ά�û���¼ʱ�����ݿ����Ѵ���֯�õĽ����

	/**
	 * �õ�ʱ���
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
	 * �õ���������
	 * @return String
	 */
	public String getOnlineNumber() {
		return onlineNumber;
	}

	/**
	 * ������������
	 * @param onlineNumber
	 *            ��������
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	/**
	 * �õ�������Ϣ�����ڷ��д�ά�û���
	 * @return Map
	 */
	public Map getOnlineStatList() {
		return onlineStatList;
	}

	/**
	 * ����������Ϣ�����ڷ��д�ά�û���
	 * @param onlineStatList
	 *            ������Ϣ�б�
	 */
	public void setOnlineStatList(Map onlineStatList) {
		this.onlineStatList = onlineStatList;
	}

	/**
	 * �õ���������
	 * @return String
	 */
	public String getSmTotal() {
		return smTotal;
	}

	/**
	 * ���ö�������
	 * @param smTotal
	 *            ��������
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}

	/**
	 * �õ�������Ϣ�������д�ά�û���
	 * @return String
	 */
	public String getOnlineInfo() {
		return onlineInfo;
	}

	/**
	 * ����������Ϣ�������д�ά�û���
	 * @param onlineInfo
	 *            ������Ϣ�������ݿ���ȡ�������д�άʹ�ã��Ѿ���֯�ã�
	 */
	public void setOnlineInfo(String onlineInfo) {
		this.onlineInfo = onlineInfo;
	}

}
