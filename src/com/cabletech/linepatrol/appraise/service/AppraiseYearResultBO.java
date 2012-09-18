package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseResultBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseConfirmResultDao;
import com.cabletech.linepatrol.appraise.dao.AppraiseYearResultDao;
import com.cabletech.linepatrol.appraise.module.AppraiseConfirmResult;
import com.cabletech.linepatrol.appraise.module.AppraiseYearResult;
@Service
public class AppraiseYearResultBO extends EntityManager<AppraiseYearResult, String> {
	@Autowired
	private AppraiseYearResultDao appraiseYearResultDao;
	@Autowired
	private AppraiseConfirmResultDao appraiseConfirmResultDao;
	@Override
	protected HibernateDao<AppraiseYearResult, String> getEntityDao() {
		// TODO Auto-generated method stub
		return appraiseYearResultDao;
	}
	/**
	 * 通过appraiseResultBean获得年度考核列表
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public List<AppraiseYearResult> getResultByAppraiseResultBean(AppraiseResultBean appraiseResultBean){
		return appraiseYearResultDao.getResultByAppraiseResultBean(appraiseResultBean);
	}
	/**
	 * 查询统计年度考核
	 * @param appraiseResultBean
	 * @return
	 */
	@Transactional
	public List<Map> queryYearResultStatByAppraiseResultBean(AppraiseResultBean appraiseResultBean){
		List<AppraiseYearResult> appraiseYearResults=appraiseYearResultDao.getResultByAppraiseResultBean(appraiseResultBean);
		Map appraiseYearResultStatMap=new HashMap();
		List<Map> appraiseYearResultStat=new ArrayList<Map>();
		List<AppraiseYearResult> appraiseYearResults2=new ArrayList<AppraiseYearResult>();
		String contractorId="";
		Float totleResult=0.0f;
		int count=0;
		if(appraiseYearResults.size()<=0){
		}else{
			contractorId=appraiseYearResults.get(0).getContractorId();
			for(int index=0;index<appraiseYearResults.size();index++){
				AppraiseYearResult appraiseYearResult=appraiseYearResults.get(index);
				if(!contractorId.equals(appraiseYearResult.getContractorId())){
					putIntoStatList(appraiseYearResultStatMap, appraiseYearResultStat, appraiseYearResults2,
							contractorId, totleResult, count);
					appraiseYearResultStatMap=new HashMap();
					appraiseYearResults2=new ArrayList<AppraiseYearResult>();
					contractorId="";
					totleResult=0.0f;
					count=0;
					contractorId=appraiseYearResult.getContractorId();
				}
				appraiseYearResults2.add(appraiseYearResult);
				totleResult+=appraiseYearResult.getResult();
				count++;
				if(index==appraiseYearResults.size()-1){
					putIntoStatList(appraiseYearResultStatMap, appraiseYearResultStat, appraiseYearResults2,
							contractorId, totleResult, count);
				}
			}
		}
		return appraiseYearResultStat;
	}
	private void putIntoStatList(Map appraiseYearResultStatMap, List<Map> appraiseYearResultStat,
			List<AppraiseYearResult> appraiseYearResults2, String contractorId, Float totleResult, int count) {
		appraiseYearResultStatMap.put("contractorId", contractorId);
		appraiseYearResultStatMap.put("avgResult", totleResult/count);
		appraiseYearResultStatMap.put("appraiseYearResultList", appraiseYearResults2);
		appraiseYearResultStat.add(appraiseYearResultStatMap);
	}
	@Transactional
	public void updateResultConfirmResult(AppraiseResultBean appraiseResultBean) {
		appraiseYearResultDao.updateResultConfirmResult(appraiseResultBean);
	}
	
	/**
	 * 考核结果确认
	 * @param appraiseResultBean
	 */
	@Transactional
	public void verifyAppraiseResult(AppraiseResultBean appraiseResultBean,UserInfo userInfo) {
		// TODO Auto-generated method stub
		appraiseYearResultDao.updateResultConfirmResult(appraiseResultBean);
		AppraiseConfirmResult appraiseConfirmResult=getConfirmResultByResultBean(appraiseResultBean, userInfo);
		appraiseConfirmResultDao.save(appraiseConfirmResult);
	}
	/**
	 * 通过resultBean对象初始化appraiseConfirmResult对象
	 * @param appraiseResultBean
	 * @param userInfo
	 * @return
	 */
	private AppraiseConfirmResult getConfirmResultByResultBean(AppraiseResultBean appraiseResultBean, UserInfo userInfo) {
		AppraiseConfirmResult appraiseConfirmResult=new AppraiseConfirmResult();
		appraiseConfirmResult.setContractorId(appraiseResultBean.getContractorId());
		appraiseConfirmResult.setResult(appraiseResultBean.getMark());
		appraiseConfirmResult.setResultId(appraiseResultBean.getId());
		appraiseConfirmResult.setConfirmDate(new Date());
		appraiseConfirmResult.setConfirmer(userInfo.getUserName());
		appraiseConfirmResult.setConfirmResult(appraiseResultBean.getConfirmResult());
		return appraiseConfirmResult;
	}
}
