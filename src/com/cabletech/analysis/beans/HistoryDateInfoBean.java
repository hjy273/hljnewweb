package com.cabletech.analysis.beans;

import com.cabletech.commons.base.BaseBean;

/**
 * ��ֹ���ڽ���������ͼ�ȵ����ʾ����Ϣ
 * 
 */
public class HistoryDateInfoBean extends BaseBean {
	private String intersectPoint; // ʱ��㣬ָ����

	private String onlineNumber; // ��������

	private String smTotal; // ��������

	private String distanceTotal; // Ѳ���ܾ���

	/**
	 * �õ�Ѳ�����
	 * @return String
	 */
	public String getDistanceTotal() {
		return distanceTotal;
	}

	/**
	 * ����Ѳ�����
	 * @param distanceTotal Ѳ���ܾ���
	 */
	public void setDistanceTotal(String distanceTotal) {
		this.distanceTotal = distanceTotal;
	}

	/**
	 * �õ�ʱ��㣬ָ����
	 * @return String
	 */
	public String getIntersectPoint() {
		return intersectPoint;
	}

	/**
	 * ����ʱ��㣬ָ����
	 * @param intersectPoint ʱ��㣬ָ����
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
	 * @param onlineNumber ��������
	 */
	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
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
	 * @param smTotal ��������
	 */
	public void setSmTotal(String smTotal) {
		this.smTotal = smTotal;
	}
	
	/**
	 * ������֯�õ�����tooltip��ʾ��Ϣ
	 * @param givenDate ѡ������
	 * @return String
	 */
	public  String getBackString(String givenDate){
		String backString = "��ѡ����:" + givenDate + "<br>" + "��������:"
		+ getOnlineNumber() + "<br>" + "����������"
		+ getSmTotal() + "<br>" + "Ѳ�����:"
		+ getDistanceTotal();
		return backString;
	}
}
