package com.cabletech.analysis.services;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.commons.config.UserType;

/**
 * template method模式中用于控制历史曲线的具体实现子类
 */
public class WorkInfoHistoryContextCurve extends WorkInfoHistoryContext {

	/**
	 * 创建省移动BO子类对象
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOProvinceMobile(String id){
		WorkInfoHistoryBOProvinceMobile bo = new WorkInfoHistoryBOProvinceMobile();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * 创建市移动BO子类对象
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityMobile(String id){
		WorkInfoHistoryBOCityMobile bo = new WorkInfoHistoryBOCityMobile();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * 创建市代维BO子类对象
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOCityContractor(String id){
		WorkInfoHistoryBOCityContractor bo = new WorkInfoHistoryBOCityContractor();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * 创建巡检组BO子类对象
	 * @param id rangeID
	 * @return
	 */
	public WorkInfoHistoryBaseBO createBOPatrolman(String id){
		WorkInfoHistoryBOPatrolman bo = new WorkInfoHistoryBOPatrolman();
		bo.setRangeID(id);
		return bo;
	}
	
	/**
	 * 依据bean得到rangeID
	 * @param userType 用户类型
	 * @param bean 界面输入的bean
	 */
	public String getRangeID(String userType,HistoryWorkInfoConditionBean bean){
		String rangeID = "";
		if ( bean != null){
			rangeID = bean.getRangeID();
		}else{
			if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
				rangeID = "11";
			} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
				rangeID = "12";
			}else{
				rangeID = "22";
			}
		}
		return rangeID;
	}
}
