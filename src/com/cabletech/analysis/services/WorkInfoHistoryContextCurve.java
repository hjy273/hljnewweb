package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.commons.config.UserType;

/**
 * template methodģʽ�����ڿ�����ʷ���ߵľ���ʵ������
 */
public class WorkInfoHistoryContextCurve extends WorkInfoHistoryContext {

	/**
	 * ����ʡ�ƶ�BO�������
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOProvinceMobile(String id){
		WorkInfoHistoryBOProvinceMobile bo = new WorkInfoHistoryBOProvinceMobile();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * �������ƶ�BO�������
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityMobile(String id){
		WorkInfoHistoryBOCityMobile bo = new WorkInfoHistoryBOCityMobile();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * �����д�άBO�������
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityContractor(String id){
		WorkInfoHistoryBOCityContractor bo = new WorkInfoHistoryBOCityContractor();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * ����Ѳ����BO�������
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOPatrolman(String id){
		WorkInfoHistoryBOPatrolman bo = new WorkInfoHistoryBOPatrolman();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * ����bean�õ�rangeID
	 * @param userType �û�����
	 * @param bean ���������bean
	 */
	public String getRangeID(String userType,HistoryWorkInfoConditionBean bean){
		String rangeID = "";
		if ( bean != null){
			rangeID = bean.getRangeID();
		}else{
			if (UserType.PROVINCE.equals(userType)) { // �����ʡ�ƶ��û�
				rangeID = "11";
			} else if (UserType.SECTION.equals(userType)) { // ��������ƶ��û�
				rangeID = "12";
			}else{
				rangeID = "22";
			}
		}
		return rangeID;
	}
}
