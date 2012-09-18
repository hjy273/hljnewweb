package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.baseinfo.services.ContractorBO;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.module.AppraiseResult;

@Service
public class AppraiseYearEndBO extends AppraiseBO {
	@Transactional
	@Override
	public List queryStat(AppraiseResultBean appraiseResultBean,UserInfo userInfo) {
		ContractorBO contractorBO=new ContractorBO();
		Map cons=contractorBO.getContractorForMap(userInfo);
		List<AppraiseResult> appraiseResults=appraiseResultDao.queryStat(appraiseResultBean); 
		List yearEndStatList=new ArrayList();
		Map yearEndStatMap = new HashMap();
		List yearEndList=new ArrayList();
		Float sumResult=0.0f;
		int count=0;
		if(appraiseResults.size()<1){
		}else{
			String contractorId=appraiseResults.get(0).getContractorId();
			for(int index=0;index<appraiseResults.size();index++){
				AppraiseResult appraiseResult=appraiseResults.get(index);
				String conId=appraiseResult.getContractorId();
				if(!contractorId.equals(conId)){
					putIntoStatList(cons, yearEndStatList, yearEndStatMap, yearEndList, sumResult, count,
							appraiseResult);
					yearEndList=new ArrayList();
					yearEndStatMap=new HashMap();
					contractorId=conId;
					sumResult=0.0f;
					count=0;
				}
				yearEndList.add(appraiseResult);
				sumResult+=appraiseResult.getResult();
				count++;
				if(index==appraiseResults.size()-1){
					putIntoStatList(cons, yearEndStatList, yearEndStatMap, yearEndList, sumResult, count,
							appraiseResult);
				}
			}
		}
		
		return yearEndStatList;
	}

	private void putIntoStatList(Map cons, List yearEndStatList, Map yearEndStatMap, List yearEndList, Float sumResult,
			int count, AppraiseResult appraiseResult) {
		yearEndStatMap.put("avgResult", sumResult/count);
		yearEndStatMap.put("contractorName", cons.get(appraiseResult.getContractorId()));
		yearEndStatMap.put("yearEndList", yearEndList);
		yearEndStatList.add(yearEndStatMap);
	}
}
