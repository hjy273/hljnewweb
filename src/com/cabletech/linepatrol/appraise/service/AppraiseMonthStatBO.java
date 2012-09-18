package com.cabletech.linepatrol.appraise.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.stereotype.Service;

import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseMonthStatBean;
import com.cabletech.linepatrol.appraise.dao.AppraiseMonthStatDao;
import com.cabletech.linepatrol.appraise.module.AppraiseMonthStat;
import com.cabletech.linepatrol.appraise.module.AppraiseMonthStatContent;
import com.cabletech.linepatrol.appraise.module.AppraiseMonthStatDetail;

/**
 * 月考核统计业务类
 * 
 * @author liusq
 * 
 */
@Service
public class AppraiseMonthStatBO extends
		EntityManager<AppraiseMonthStat, String> {

	@Resource(name = "appraiseMonthStatDao")
	private AppraiseMonthStatDao dao;

	@Override
	protected HibernateDao<AppraiseMonthStat, String> getEntityDao() {
		return dao;
	}

	/**
	 * 考核统计
	 * @param bean
	 * @return
	 */
	public List<AppraiseMonthStat> monthStatAppraise(AppraiseMonthStatBean bean) {
		List<DynaBean> beanList = dao.monthStatAppraise(bean);
		List<AppraiseMonthStat> statList = null;
		Set<String> contractorSet = new HashSet<String>();
		Set<String> monthSet = new HashSet<String>();
		if (beanList != null && beanList.size() > 0) {
			statList = new ArrayList<AppraiseMonthStat>();
			AppraiseMonthStat monthStat = null;
			AppraiseMonthStatDetail monthDetail = null;
			for (int i = 0; i < beanList.size(); i++) {
				DynaBean dynaBean = (DynaBean) beanList.get(i);
				String contractorId = (String) dynaBean.get("contractor_id");
				String contractorName = (String) dynaBean.get("contractorname");
				String contractNo = dynaBean.get("contract_no") == null ? " "
						: (String) dynaBean.get("contract_no");
				String appraiseMonth = (String)dynaBean.get("appraise_time");
				String result = (String)dynaBean.get("result");
				String resultId=(String)dynaBean.get("id");
				float score = Float.valueOf(result);
				
				AppraiseMonthStatContent content = new AppraiseMonthStatContent(resultId,appraiseMonth, contractNo, score);
				
				if(contractorSet.add(contractorId)) {
					if(monthStat != null) {
						monthDetail.calculateScore();
						monthStat.addResultList(monthDetail);
						statList.add(monthStat);
					}
					monthStat = new AppraiseMonthStat();
					monthStat.setContractorName(contractorName);
					
					monthDetail = new AppraiseMonthStatDetail();
					monthSet.add(appraiseMonth);
					monthDetail.addContentList(content);
				}else{
					if(monthSet.add(appraiseMonth)){
						monthDetail.calculateScore();
						monthStat.addResultList(monthDetail);
						
						monthDetail = new AppraiseMonthStatDetail();
						monthSet.add(appraiseMonth);
						monthDetail.addContentList(content);
					}else{
						monthDetail.addContentList(content);
					}
				}
			}
			monthDetail.calculateScore();
			monthStat.addResultList(monthDetail);
			statList.add(monthStat);
		}
		return statList;
	}
}
