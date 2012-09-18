package com.cabletech.linepatrol.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.DynaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.baseinfo.domainobjects.UserInfo;
import com.cabletech.ctf.dao.HibernateDao;
import com.cabletech.ctf.service.EntityManager;
import com.cabletech.linepatrol.appraise.beans.AppraiseDailyBean;
import com.cabletech.linepatrol.appraise.service.AppraiseDailyDailyBO;
import com.cabletech.linepatrol.appraise.service.AppraiseDailySpecialBO;
import com.cabletech.linepatrol.project.beans.RemedyExamBean;
import com.cabletech.linepatrol.project.constant.RemedyConstant;
import com.cabletech.linepatrol.project.dao.RemedyApplyDao;
import com.cabletech.linepatrol.project.dao.RemedyExamDao;
import com.cabletech.linepatrol.project.domain.ProjectRemedyApply;

/**
 * 工厂管理日常考核业务类
 * @author liusq
 *
 */
@Service
@Transactional
public class RemedyExamManager extends EntityManager<ProjectRemedyApply, String> {

	@Resource(name = "remedyExamDao")
	private RemedyExamDao dao;
	
	@Resource(name = "remedyApplyDao")
	private RemedyApplyDao remedyApplyDao;
	
	@Autowired
	private AppraiseDailyDailyBO appraiseDailyBO;
	@Autowired
	private AppraiseDailySpecialBO appraiseDailySpecialBO;
	
	@Override
	protected HibernateDao<ProjectRemedyApply, String> getEntityDao() {
		return dao;
	}

	/**
	 * 获得待考核列表
	 * @param bean
	 * @param userInfo
	 * @return
	 */
	public List<DynaBean> getExamListByCondition(RemedyExamBean bean, UserInfo userInfo) {
		return dao.getExamListByCondition(bean, userInfo);
	}
	
	/**
	 * 考核工程申请单
	 * @param appraiseDailyBean
	 */
	public void examRemedy(AppraiseDailyBean appraiseDailyBean,List<AppraiseDailyBean> speicalBeans) {
		//保存日常考核信息
		appraiseDailyBO.saveAppraiseDailyByBean(appraiseDailyBean);
		//保存专项日常考核信息
		appraiseDailySpecialBO.saveAppraiseDailyByBean(speicalBeans);
		//改变状态为已考核
		remedyApplyDao.changeStateById(appraiseDailyBean.getBusinessId(), RemedyConstant.FINISH);
	}
}
