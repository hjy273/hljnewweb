package com.cabletech.analysis.services;

import org.apache.log4j.Logger;

import com.cabletech.analysis.beans.HistoryWorkInfoConditionBean;
import com.cabletech.analysis.beans.HistoryWorkInfoCreateBOParam;
import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.commons.config.UserType;

/**
 * 该类strategy模式中的context环境类,通过其来调用组织WorkInfoHistoryBaseBO对象
 */
public abstract class WorkInfoHistoryContext {
	protected HistoryWorkInfoCreateBOParam theBoParam; //用于接收从Action类中传入的参数
	private Logger logger =Logger.getLogger(this.getClass().getName()); // 建立logger输出对象;
	/**
	 * 创建BO对象
	 * @param boParam 用于接收从Action类中传入的参数,是各参数列表的组合，包括smRangeID,givenDate等信息
	 * @return BO对象
	 */
	public  WorkInfoHistoryBaseBO createBO(HistoryWorkInfoCreateBOParam boParam) {
		this.theBoParam = boParam;
		if (theBoParam == null){
			logger.info("boParam is null");
		}else{
			logger.info(theBoParam.getUserInfo().getRegionID());
		}
		//依据参数创建BO对象
		WorkInfoHistoryBaseBO bo = createBOByUserAndRange(theBoParam);
		if (bo == null){
			logger.info("bo is null");
		}
		bo.setBoParam(theBoParam);
		return bo;
	}

	/**
	 * 依据实际情况返回WorkInfoHistoryBaseBO的子类对象
	 * @param boParam 用于接收从Action类中传入的参数,是各参数列表的组合，包括smRangeID,givenDate等信息
	 * @return WorkInfoHistoryBaseBO的子类对象
	 */
	public WorkInfoHistoryBaseBO createBOByUserAndRange(HistoryWorkInfoCreateBOParam boParam) {
		WorkInfoHistoryBaseBO bo = null;
		String id = boParam.getSmRangeID(); //短信的RangeID
		UserInfo userInfo = boParam.getUserInfo();
		HistoryWorkInfoConditionBean bean = boParam.getBean(); //传入的界面查询bean
		String userType = userInfo.getType();
		if (id == null) { //如果短信的RangeID为空，意味着与短信无关，则与历史曲线有关
			id = getRangeID(userType,bean); //得到适用于历史曲线的rangeID
		}
		if (UserType.PROVINCE.equals(userType)) { // 如果是省移动用户
			if ("11".equals(id)) { // 如果所选范围是“全省范围“
				bo =createBOProvinceMobile(null); //操作省移动BO子类中实现的方法
			} else {
				bo =createBOCityMobile(id); //操作市移动BO子类中实现的方法
			}
		} else if (UserType.SECTION.equals(userType)) { // 如果是市移动用户
			// 如果所选范围为用户所在地市
			if ("12".equals(id)) {
				bo =createBOCityMobile(userInfo.getRegionid()); //操作市移动BO子类中实现的方法
			} else {
				bo =createBOCityContractor(id); //操作市代维BO子类中实现的方法
			}
		} else if (UserType.CONTRACTOR.equals(userType)) { // 如果是市代维用户
			// 如果所选范围为用户所在代维公司
			if ("22".equals(id)) {
				bo =createBOCityContractor(userInfo.getDeptID()); //操作市代维BO子类中实现的方法
			} else {
				bo =createBOPatrolman(id); //操作巡检组BO子类中实现的方法
			}
		}
		return bo;
	}
	
	/**
	 * 依据bean得到rangeID
	 * @param userType 用户类型
	 * @param bean 界面输入的bean
	 * @return 
	 */
	public abstract String getRangeID(String userType,HistoryWorkInfoConditionBean bean);
	
	/**
	 * 创建省移动BO子类对象
	 * @param id rangeID或smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOProvinceMobile(String id);
	
	/**
	 * 创建市移动BO子类对象
	 * @param id rangeID或smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOCityMobile(String id);
	
	/**
	 * 创建市代维BO子类对象
	 * @param id rangeID或smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOCityContractor(String id);
	
	/**
	 * 创建巡检组BO子类对象
	 * @param id rangeID或smRangeID
	 * @return
	 */
	public abstract WorkInfoHistoryBaseBO createBOPatrolman(String id);
}
