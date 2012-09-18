package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;

/**
 * template method模式中用于控制短信的具体实现子类
 */
public class WorkInfoHistoryContextSM extends WorkInfoHistoryContext {

	/**
	 * 创建省移动BO子类对象
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOProvinceMobile(String id){
		WorkInfoHistoryBOProvinceMobile bo = new WorkInfoHistoryBOProvinceMobile();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * 创建市移动BO子类对象
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityMobile(String id){
		WorkInfoHistoryBOCityMobile bo = new WorkInfoHistoryBOCityMobile();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * 创建市代维BO子类对象
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityContractor(String id){
		WorkInfoHistoryBOCityContractor bo = new WorkInfoHistoryBOCityContractor();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * 创建巡检组BO子类对象
	 * @param id smRangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOPatrolman(String id){
		WorkInfoHistoryBOPatrolman bo = new WorkInfoHistoryBOPatrolman();
		theBoParam.setSmRangeID(id);
		return bo;
	}
	
	/**
	 * 无需实现
	 */
	public String getRangeID(String userType, HistoryWorkInfoConditionBean bean) {
		return null;
	}
}
