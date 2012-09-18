package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * template methodģʽ�����ڿ��ƶ��ŵľ���ʵ������
 */
public class WorkInfoHistoryContextSM extends WorkInfoHistoryContext {

	/**
	 * ����ʡ�ƶ�BO�������
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOProvinceMobile(String id){
		WorkInfoHistoryBOProvinceMobile bo = new WorkInfoHistoryBOProvinceMobile();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * �������ƶ�BO�������
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityMobile(String id){
		WorkInfoHistoryBOCityMobile bo = new WorkInfoHistoryBOCityMobile();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * �����д�άBO�������
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityContractor(String id){
		WorkInfoHistoryBOCityContractor bo = new WorkInfoHistoryBOCityContractor();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * ����Ѳ����BO�������
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOPatrolman(String id){
		WorkInfoHistoryBOPatrolman bo = new WorkInfoHistoryBOPatrolman();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * ����ʵ��
	 */
	public String getRangeID(String userType, HistoryWorkInfoConditionBean bean) {
		return null;
	}
}
