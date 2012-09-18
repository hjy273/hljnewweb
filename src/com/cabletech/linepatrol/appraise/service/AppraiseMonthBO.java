package com.cabletech.linepatrol.appraise.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.commons.beans.BeanUtil;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;
@Service
public class AppraiseMonthBO extends AppraiseBO {
	/**
	 * 获得考核页面中参考指标所需要的信息
	 * @param contractorId
	 * @param theStartDate
	 * @return
	 */
	@Transactional
	public List queryForAppraiseMonth(String contractorId,String theStartDate){
		return appraiseResultDao.queryForAppraiseMonth(contractorId, theStartDate);
	}
	/**
	 * 获得考核页面中参考指标的合计信息
	 * @param contractorId
	 * @param theStartDate
	 * @return
	 */
	@Transactional
	public List queryForAppraiseMonthAll(String contractorId,String theStartDate){
		return appraiseResultDao.queryForAppraiseMonthAll(contractorId, theStartDate);
	}
	/**
	 * 查询统计月考核表
	 * @param appraiseResultBean
	 * @param userInfo
	 * @return
	 */
	@Transactional
	@Override
	public List queryStat(AppraiseResultBean appraiseResultBean,UserInfo userInfo) {
		ContractorBO contractorBO=new ContractorBO();
		Map con=contractorBO.getContractorForMap(userInfo);
		List<AppraiseResult> appraiseResults=appraiseResultDao.queryStat(appraiseResultBean);
		if(appraiseResults.size()<=0){
			return null;
		}
		List monthStatList=new ArrayList();
		Map monthStatMap=new HashMap();
		List conStatList=new ArrayList();
		Map conStatMap=new HashMap();
		List monthList=new ArrayList();
		
		String contractorId=appraiseResults.get(0).getContractorId();
		Date appraiseMonth=appraiseResults.get(0).getAppraiseTime();
		
		float sumResult=0.0F;
		int i=0;
		int size=appraiseResults.size();
		for(int index=0;index<size;index++){
			AppraiseResult appraiseResult=appraiseResults.get(index);
			if(!appraiseResult.getAppraiseTime().equals(appraiseMonth)){
				appraiseMonth=appraiseResult.getAppraiseTime();
				setConStatMap(conStatMap, monthList, sumResult, i);
				conStatList.add(conStatMap);
				sumResult=0.0f;
				i=0;
				monthList=new ArrayList();
				conStatMap=new HashMap();
			}
			if(!appraiseResult.getContractorId().equals(contractorId)){
				String conId=contractorId;
				contractorId=appraiseResult.getContractorId();
				setMonthStatMap(con, monthStatMap, conStatList, contractorId);
				monthStatList.add(monthStatMap);
				conStatList=new ArrayList();
				monthStatMap=new HashMap();
			}
			sumResult+=appraiseResult.getResult();
			AppraiseResultBean appResultBean=new AppraiseResultBean();
			BeanUtil.copyProperties(appraiseResult, appResultBean);
			appResultBean.setAppriaseMonthFormat();
			monthList.add(appResultBean);
			i++;
			if(index+1==size){
				setConStatMap(conStatMap, monthList, sumResult, i);
				conStatList.add(conStatMap);
				setMonthStatMap(con, monthStatMap, conStatList, contractorId);
				monthStatList.add(monthStatMap);
				
			}
		}
		return monthStatList;
	}
	private void setMonthStatMap(Map con, Map monthStatMap, List conStatList, String contractorId) {
		monthStatMap.put("contractorName", con.get(contractorId));
		monthStatMap.put("conStatList", conStatList);
	}
	private void setConStatMap(Map conStatMap, List monthList, float sumResult, int i) {
		conStatMap.put("sumResult", sumResult);
		conStatMap.put("avgResult", sumResult/i);
		conStatMap.put("monthList", monthList);
	}
	
	public void setAppraiseTimeFormat(List<Map> maps,String type){
		String appraiseTime=null;
		for(Map map:maps){
			if(type.equals(AppraiseConstant.APPRAISE_MONTH)){
				appraiseTime=map.get("APPRAISETIME").toString().split("-")[0]+"-"+map.get("APPRAISETIME").toString().split("-")[1];
			}else if(type.equals(AppraiseConstant.APPRAISE_YEAREND)){
				appraiseTime=map.get("APPRAISETIME").toString().split("-")[0];
			}
			map.put("APPRAISETIME", appraiseTime);
		}
	}
}
